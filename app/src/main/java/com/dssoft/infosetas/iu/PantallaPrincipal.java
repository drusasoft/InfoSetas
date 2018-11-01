package com.dssoft.infosetas.iu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.dssoft.infosetas.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PantallaPrincipal extends AppCompatActivity
{

    @BindView(R.id.toolBar_pantalla_principal) Toolbar toolbar;
    @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
    @BindView(R.id.leftDrawer) NavigationView nView;
    @BindView(R.id.layout_pantalla_principal) LinearLayout layoutPantallaPrincipal;

    private ActionBarDrawerToggle drawerToggle;
    private AnimationDrawable animacionFondo;
    private boolean animacionFondoIniciada;
    private InterstitialAd mInterstitialAd;

    private boolean permisoGps;
    private static final int RC_HANDLE_GPS_PERM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        MultiDex.install(this);
        ButterKnife.bind(this);

        //Inicializa el SDK de Google Mobile Ads
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2712815213167664~2533509012");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close)
        {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

        drawerLayout.addDrawerListener(drawerToggle);

        animacion_fondo();//Se crea la animacion de la imagen de fondo de la pantalla principal

        //Se comprueba si el permiso de acceso a ubicacion esta concedido por el usuario
        int rfl = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(rfl != PackageManager.PERMISSION_GRANTED)
        {
            permisoGps = false;
        }else
        {
            permisoGps = true;
        }

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_principal);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Se carga el anuncio Interstitial
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2712815213167664/8493069249");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        //Si se cierra el Instersticioal se vuelve a crear uno nuevo
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed()
            {
                super.onAdClosed();

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState)
    {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();//necesario para que se muestre el icono del NavigationDrawer
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if(animacionFondoIniciada && !animacionFondo.isRunning())
            animacionFondo.start();//se vuelve a iniciar la animacion del fondo de pantalla


        nView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener()
        {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {

                if(drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawers();

                switch (item.getItemId())
                {

                    case R.id.drawer_opcion1: irPantallaSetas();
                                                break;

                    case R.id.drawer_opcion2: irPantallaGaleria();
                                                break;

                    case R.id.drawer_opcion3: irPantallaTiempo();
                                                break;

                    case R.id.drawer_opcion4: irPantallaZonas();
                                                break;

                    case R.id.drawer_opcion5: irPantallaAyuda();
                        break;

                    case R.id.drawer_opcion6: irPoliticasPrivacidad();
                        break;
                }


                return false;
            }

        });

    }


    @Override
    protected void onPause()
    {
        super.onPause();

        if(animacionFondoIniciada && animacionFondo.isRunning())
            animacionFondo.stop();//se pausa la animacion del fondo de pantalla
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);//necesario para que se muestre el icono del NavigationDrawer
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        //Necesario para que se abra y cierra le NavigationDrawer al pulsar el boton
        if(drawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        return false;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            if(drawerLayout.isDrawerOpen(GravityCompat.START))
            {
                drawerLayout.closeDrawers();
                return true;
            }else
            {
                finish();
            }
        }

        return false;
    }


    @Override
    //Se comprueba si el usuario ha dado permiso para usar la camara o no
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        if(requestCode == RC_HANDLE_GPS_PERM)
        {

            if(grantResults.length != 0)
            {

                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    permisoGps = true;

                }else
                {
                    Toast.makeText(this, R.string.txtUbicacion, Toast.LENGTH_LONG).show();
                }

            }

            return;
        }

    }


    @OnClick(R.id.layout_btn_setas)
    public void irPantallaSetas()
    {

        if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();

        Intent intent = new Intent(this, PantallaListaSetas.class);
        startActivity(intent);
    }


    @OnClick(R.id.layout_btn_galeria)
    public void irPantallaGaleria()
    {

        if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();

        Intent intent = new Intent(this, PantallaListaGaleria.class);
        startActivity(intent);
    }


    @OnClick(R.id.layout_btn_tiempo)
    public void irPantallaTiempo()
    {

         if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();

         Intent intent = new Intent(this, PantallaTiempo.class);
         startActivity(intent);

    }


    @OnClick(R.id.layout_btn_zona)
    public void irPantallaZonas()
    {

        if(permisoGps)
        {
            if(mInterstitialAd.isLoaded())
                mInterstitialAd.show();

            Intent intent = new Intent(this, PantallaZonas.class);
            startActivity(intent);

        }else
        {
            //Se comprueba si el usuario ha concedido permiso para localizacion
            //y si no esta concedido se solicita
            int rfl = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

            if(rfl != PackageManager.PERMISSION_GRANTED)
            {

                Snackbar.make(layoutPantallaPrincipal, R.string.txtUbicacion, Snackbar.LENGTH_LONG).show();

                final String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
                ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_GPS_PERM);

            }

        }

    }


    private void irPantallaAyuda()
    {
        if(mInterstitialAd.isLoaded())
            mInterstitialAd.show();

        Intent intent = new Intent(this, PantallaAyuda.class);
        startActivity(intent);
    }


    private void irPoliticasPrivacidad()
    {
        Intent intentP = new Intent(Intent.ACTION_VIEW, Uri.parse("https://utilities.000webhostapp.com/setas_policy.html"));
        startActivity(intentP);
    }


    //Se inicia la animacion (AnimationDrawable) de la imagen de fondo de la pantalla principal
    private void animacion_fondo()
    {

        //Obtener hora del Systema
        Long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//HH devuelve la hora en formato 24h y hh en formato de 12
        String hora = sdf.format(date);

        StringTokenizer stringTokenizer = new StringTokenizer(hora,":");
        String tokenHora = stringTokenizer.nextToken();
        int hh = Integer.valueOf(tokenHora);


        //Dependiendo de la hora del sistema entonces se muestra el fondo animado correcpondiente
        if(hh>6 && hh<15)
        {
            layoutPantallaPrincipal.setBackgroundResource(R.drawable.animacion_pantallaprincipal);
        }else
        {
            if(hh>=15 && hh<21)
            {
                layoutPantallaPrincipal.setBackgroundResource(R.drawable.animacion_pantallaprincipal_tarde);
            }else
            {
                layoutPantallaPrincipal.setBackgroundResource(R.drawable.animacion_pantallaprincipal_noche);
            }
        }


        layoutPantallaPrincipal.post(new Runnable() {
            @Override
            public void run()
            {
                animacionFondo = (AnimationDrawable) layoutPantallaPrincipal.getBackground();
                animacionFondo.start();
                animacionFondoIniciada = true;

            }
        });
    }



}
