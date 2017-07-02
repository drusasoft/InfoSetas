package com.dssoft.infosetas;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 12/06/2017.
 */

public class PantallaZoom extends AppCompatActivity
{

    @BindView(R.id.toolBar_pantalla_zoom) Toolbar toolbar;
    @BindView(R.id.titToolBarZoom) TextView titToolbar;
    @BindView(R.id.imgToolBarZoom) ImageView imgToolBar;
    @BindView(R.id.layout_pantalla_zoom) LinearLayout layoutPantallaZoom;
    @BindView(R.id.imgZoom) PhotoView imgZoom;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_zoom);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String nombreSeta = getIntent().getStringExtra("nombreSeta");
        String comestible = getIntent().getStringExtra("comestible");
        int foto = getIntent().getIntExtra("foto",0);
        titToolbar.setText(nombreSeta);

        setColorPantalla(comestible);

        imgZoom.setImageResource(foto);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case android.R.id.home: onBackPressed();
                                    return true;

        }

        return false;
    }



    //Se cambia el color de la pantalla segun la comestibilidad de la seta
    private void setColorPantalla(String comestible)
    {

        if(comestible.equals("venenosa"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkOrange));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryOrange));
            layoutPantallaZoom.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentOrange));

            imgToolBar.setImageResource(R.drawable.seta_venenosa_small);

            return;
        }


        if(comestible.equals("mortal"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkRed));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryRed));
            layoutPantallaZoom.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentRed));

            imgToolBar.setImageResource(R.drawable.seta_mortal_small);

            return;
        }

        imgToolBar.setImageResource(R.drawable.seta_buena_small);

    }


}
