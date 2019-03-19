package com.dssoft.infosetas.iu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.dssoft.infosetas.R;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 18/02/2018.
 */

public class PantallaMapaNuevaZona extends AppCompatActivity implements GoogleMap.OnMapLongClickListener
{

    @BindView(R.id.toolBar_pantalla_mapa_nueva_zona) Toolbar toolbar;

    private GoogleMap mapa;
    private Marker marcador;
    private BitmapDescriptor imgMarcador;
    private LatLng coordenadasPulsadas;

    private final int NUEVA_ZONA = 101;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_mapa_nueva_zona);
        ButterKnife.bind(this);

        //Se cambia el color de la statusbar y se pone transparente
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imgMarcador = BitmapDescriptorFactory.fromResource(R.drawable.zona_marcador);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_nueva_zona)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
               configurarMapa(googleMap);
            }

        });


        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_mapa_nueva_zona);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

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

        if(resultCode == RESULT_OK && requestCode == NUEVA_ZONA)
        {

            if(coordenadasPulsadas != null)
            {

                String nombreZona = data.getStringExtra("nombreZona");
                String descripcionZona = data.getStringExtra("descripcionZona");

                //Se añade un marcador donde hemos realizado la pulsacion larga
                marcador = mapa.addMarker(new MarkerOptions()
                        .title(nombreZona)
                        .snippet(descripcionZona)
                        .icon(imgMarcador)
                        .position(coordenadasPulsadas)
                );

                marcador.showInfoWindow();//Con esto se hace que se muestre la info del marcador

            }else
            {
                Toast.makeText(this, R.string.txtErrorCoordenadas, Toast.LENGTH_LONG).show();
            }

        }
    }


    private void configurarMapa(GoogleMap googleMap)
    {
        mapa = googleMap;
        mapa.setOnMapLongClickListener(this);
    }


    private void dialogGuardarPosicion()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titPantallaDatosNuevaZona);
        builder.setMessage(R.string.txtDialogMapaNuevaZona);

        builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                Intent intent = new Intent(getApplicationContext(), PantallaInsertarDatosZona.class);
                intent.putExtra("latitud", String.valueOf(coordenadasPulsadas.latitude));
                intent.putExtra("longitud", String.valueOf(coordenadasPulsadas.longitude));
                startActivityForResult(intent, NUEVA_ZONA);
            }
        });

        builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }


    @Override
    public void onMapLongClick(LatLng latLng)
    {
        coordenadasPulsadas = latLng;

        if(coordenadasPulsadas != null)
        {
            dialogGuardarPosicion();
        }else
        {
            Toast.makeText(this, R.string.txtErrorCoordenadas, Toast.LENGTH_LONG).show();
        }

    }


}
