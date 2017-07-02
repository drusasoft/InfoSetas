package com.dssoft.infosetas;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.multidex.MultiDex;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.FirebaseApp;

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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        MultiDex.install(this);
        ButterKnife.bind(this);

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

    @OnClick(R.id.layout_btn_setas)
    public void irPantallaSetas()
    {
        Intent intent = new Intent(this, PantallaListaSetas.class);
        startActivity(intent);
    }


    @OnClick(R.id.layout_btn_galeria)
    public void irPantallaGaleria()
    {
        Intent intent = new Intent(this, PantallaListaGaleria.class);
        startActivity(intent);
    }

    //Se inicia la animacion (AnimationDrawable) de la imagen de fondo de la pantalla principal
    private void animacion_fondo()
    {
        layoutPantallaPrincipal.setBackgroundResource(R.drawable.animacion_pantallaprincipal);

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
