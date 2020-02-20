package com.dssoft.infosetas.iu;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
import java.util.Locale;
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

    private String idioma;
    private boolean permisoGps;
    private static final int RC_HANDLE_GPS_PERM = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        //Inicializa el SDK de Google Mobile Ads
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-2712815213167664~2533509012");

        //Se cambia el color de la statusbar y se pone transparente
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //Se obtiene el idioma de la app de las preferencias
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        idioma = prefs.getString("Idioma", null);

        if(idioma == null)
        {
            if(Locale.getDefault().getLanguage().equals("es"))
                idioma = "es";
            else
                idioma = "en";
        }

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
    protected void onDestroy()
    {
        super.onDestroy();

        //Al cerrar la App se elimina el idioma de las Preferencia de usuario
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(prefs.getString("Idioma", null) != null)
        {
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("Idioma");
            editor.commit();
        }

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

                    case R.id.drawer_opcion5: cambiarIdioma();
                                              break;

                    case R.id.drawer_opcion6: irPantallaAyuda();
                        break;

                    case R.id.drawer_opcion7: irPoliticasPrivacidad();
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
        intent.putExtra("Idioma", idioma);
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
         intent.putExtra("Idioma", idioma);
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
        intent.putExtra("Idioma", idioma);
        startActivity(intent);
    }


    private void irPoliticasPrivacidad()
    {
        Intent intentP = new Intent(Intent.ACTION_VIEW, Uri.parse("https://utilities.000webhostapp.com/setas_policy.html"));
        startActivity(intentP);
    }


    //Se cambia el idioma de la Aplicacion
    private void cambiarIdioma()
    {
        if(idioma.equals("es"))
            idioma = "en";
        else
            idioma = "es";

        Locale myLocale = new Locale(idioma);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(myLocale);
        res.updateConfiguration(conf,dm);

        //Se guarda en prefencias el valor actual de la variable Idioma
        // ya que al refrescar la actividad se vuelve a crear de nuevo y se pierde el valor de las variables
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Idioma", idioma);
        editor.commit();

        //Se refresca la Actividad
        Intent refreshIntent = new Intent(this, PantallaPrincipal.class);
        refreshIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(refreshIntent);

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
