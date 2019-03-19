package com.dssoft.infosetas.iu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.presentador.PresentadorMapaGPS;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.StringTokenizer;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Angel on 06/03/2018.
 */

public class PantallaMapaGPS extends AppCompatActivity implements VistaMapaGPS
{

    @BindView(R.id.toolBar_pantalla_mapa_gps) Toolbar toolbar;
    @BindView(R.id.layout_pantalla_mapa_gps) RelativeLayout layoutMapaGPS;
    @BindView(R.id.layout_cuenta_atras) LinearLayout layoutCuantaAtras;
    @BindView(R.id.textViewCuentaAtras) TextView textViewCuantaAtras;
    @BindView(R.id.btnSaveZonaGps) FloatingActionButton btnSaveZonaGps;
    @BindString(R.string.txtCuentaAtras) String txtCuentaAtras;
    @BindString(R.string.titDialogGPS) String titDialogGPS;
    @BindString(R.string.titMarcadorPosicion) String titMarcadorPosicion;

    private GoogleMap mapa;
    private BitmapDescriptor imgMarcador;
    private BitmapDescriptor imgMarcadorZona;
    private ProgressDialog progressDialog;
    private PresentadorMapaGPS presentadorMapaGPS;
    private Marker marcadorPosicion;
    private String latitud, longitud;

    private final int NUEVA_ZONA = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_mapa_gps);
        ButterKnife.bind(this);

        //Se cambia el color de la statusbar y se pone transparente
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        InfoSetas infoSetas = (InfoSetas) getApplicationContext();
        presentadorMapaGPS = new PresentadorMapaGPS(infoSetas.getDataManagerGPS());
        presentadorMapaGPS.setVista(this);

        imgMarcador = BitmapDescriptorFactory.fromResource(R.drawable.zona_marcador);
        imgMarcadorZona = BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher);


        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_gps)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
              configurarMapa(googleMap);
            }

        });

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_mapa_gps);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    protected void onDestroy()
    {
        super.onDestroy();

        presentadorMapaGPS.stopLocalizacionGPS();//se detiene la obtencion de la posicion del GPS
        presentadorMapaGPS.stopCuentaAtras();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_mapas, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.menuVistaNormal: mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;

            case R.id.menuVistaSatelite: mapa.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;

            case R.id.menuVistaTerreno: mapa.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;

            case R.id.menuVistaHibrido: mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;

        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NUEVA_ZONA && resultCode == RESULT_OK)
        {

            //Se muestra la nueva zona guardada en el mapa
            String nombreZona = data.getStringExtra("nombreZona");
            String descripcionZona = data.getStringExtra("descripcionZona");
            double lat = Double.valueOf(data.getStringExtra("latitud"));
            double lng = Double.valueOf(data.getStringExtra("longitud"));
            LatLng latLng = new LatLng(lat, lng);

            //Se añade un marcador indicando la zona que hemos guardado
            Marker marcador = mapa.addMarker(new MarkerOptions()
                    .title(nombreZona)
                    .snippet(descripcionZona)
                    .icon(imgMarcadorZona)
                    .position(latLng)
            );

            marcador.showInfoWindow();//Con esto se hace que se muestre la info del marcador

        }

        //Se inicia la cuenta Atras de nuevo
        presentadorMapaGPS.iniciarCuentaAtras();
    }


    @OnClick(R.id.btnSaveZonaGps)
    public void guardarNuevaZona()
    {
        //Se para la cuenta Atras
        presentadorMapaGPS.stopCuentaAtras();
        textViewCuantaAtras.setText("");

        Intent intent = new Intent(this, PantallaInsertarDatosZona.class);
        intent.putExtra("latitud", latitud);
        intent.putExtra("longitud", longitud);
        startActivityForResult(intent, NUEVA_ZONA);
    }


    private void configurarMapa(GoogleMap googleMap)
    {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(titDialogGPS);
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        //Se ejecuta en caso de que se cancela el ProgressDialog
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                Snackbar.make(layoutMapaGPS,
                        R.string.txtConexionGpsCancelada, Snackbar.LENGTH_LONG).show();
                presentadorMapaGPS.stopLocalizacionGPS();//se detiene la obtencion de la posicion del GPS
            }
        });

        //Se conecta con el GPS
        presentadorMapaGPS.getLocalizacionGPS();

    }


    @Override
    //Se oculta el progress dialog y se añade un marcador en el mapa con las coordenadas devuletas por el GPS
    public void mostrarLocalizacionGPS(String coordenadas)
    {
        if(progressDialog != null)
            progressDialog.dismiss();

        StringTokenizer stringTokenizer = new StringTokenizer(coordenadas,":");
        latitud = stringTokenizer.nextToken();
        longitud = stringTokenizer.nextToken();
        LatLng latLng = new LatLng(Double.valueOf(latitud), Double.valueOf(longitud));

        if(marcadorPosicion == null)
        {
            //Se muestra un marcador en el mapa con nuestra posicion Actual
            marcadorPosicion = mapa.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(titMarcadorPosicion)
                    .icon(imgMarcador)
            );

        }else
        {
            marcadorPosicion.setPosition(latLng);
        }

        marcadorPosicion.showInfoWindow();//Se muestra la info definida en el marcador (Titulo)

        //Nos posicionamos en el marcador creado
        CameraUpdate camUpt = CameraUpdateFactory.newLatLngZoom(latLng, (float)18.0);
        mapa.animateCamera(camUpt);


        if(layoutCuantaAtras.getVisibility() == View.INVISIBLE)
            layoutCuantaAtras.setVisibility(View.VISIBLE);

        presentadorMapaGPS.iniciarCuentaAtras();//se inicia la cuenta atra de nuevo para obtener una nueva posicion

    }


    @Override
    //Se muestra la cuenta atras en el TextView
    public void mostrarCuentaAtras(String cuenta)
    {
        if(!cuenta.equals("0"))
        {
            textViewCuantaAtras.setText(txtCuentaAtras+" "+cuenta);
        }else
        {
            textViewCuantaAtras.setText(R.string.txtNuevaConexionGps);
        }

    }


}
