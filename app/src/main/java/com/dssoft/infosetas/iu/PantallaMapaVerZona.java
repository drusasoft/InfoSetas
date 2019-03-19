package com.dssoft.infosetas.iu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import com.dssoft.infosetas.R;
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
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 02/03/2018.
 */

public class PantallaMapaVerZona extends AppCompatActivity
{

    @BindView(R.id.toolBar_pantalla_mapa_ver_zona) Toolbar toolbar;

    private GoogleMap mapa;
    private BitmapDescriptor imgMarcador;
    private LatLng coordenadas;
    private String nombre, descripcion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_mapa_ver_zona);
        ButterKnife.bind(this);

        //Se cambia el color de la statusbar y se pone transparente
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        String lat = getIntent().getStringExtra("latitud");
        String lon = getIntent().getStringExtra("longitud");
        nombre = getIntent().getStringExtra("nombre_zona");
        descripcion = getIntent().getStringExtra("descripcion_zona");
        double latitud = Double.valueOf(lat);
        double longitud = Double.valueOf(lon);
        coordenadas = new LatLng(latitud, longitud);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(nombre.toUpperCase());

        imgMarcador = BitmapDescriptorFactory.fromResource(R.drawable.zona_marcador);

        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapa_ver_zona)).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap)
            {
                configurarMapa(googleMap);
            }

        });

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_mapa_ver_zona);
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

        switch(item.getItemId())
        {
            case android.R.id.home: onBackPressed();
                                    return true;

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


    private void configurarMapa(GoogleMap googleMap)
    {
        mapa = googleMap;
        mapa.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        Marker marcador = mapa.addMarker(new MarkerOptions()
                .position(coordenadas)
                .title(nombre)
                .snippet(descripcion)
                .icon(imgMarcador)
        );

        marcador.showInfoWindow();

        //Nos posicionamos en el marcador creado
        CameraUpdate camUpt = CameraUpdateFactory.newLatLngZoom(coordenadas, (float)18.0);
        mapa.animateCamera(camUpt);

    }

}
