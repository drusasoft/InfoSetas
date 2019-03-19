package com.dssoft.infosetas.iu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dssoft.infosetas.R;
import com.github.chrisbanes.photoview.PhotoView;
import butterknife.BindColor;
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
    @BindColor(R.color.colorPrimaryDark) int verdeOscuro;
    @BindColor(R.color.colorPrimaryDarkOrange) int naranjaOscuro;
    @BindColor(R.color.colorPrimaryDarkRed) int rojoOscuro;
    @BindColor(R.color.colorPrimaryDarkGrey) int grisOscuro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_zoom);
        ButterKnife.bind(this);

        String nombreSeta = getIntent().getStringExtra("nombreSeta");
        String comestible = getIntent().getStringExtra("comestible");
        int foto = getIntent().getIntExtra("foto",0);
        titToolbar.setText(nombreSeta);

        setColorToolBar(comestible);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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


    //Se cambia el color de la flecha volver de la Toolbar
    private void setColorToolBar(String comestible)
    {

        toolbar.getContext().setTheme(R.style.AppTheme_ToolbarStyleComestible);

        switch(comestible)
        {
            case "sin_interes":  toolbar.getContext().setTheme(R.style.AppTheme_ToolbarStyleSinInteres);
                                    break;

            case "toxica":       toolbar.getContext().setTheme(R.style.AppTheme_ToolbarStyleToxica);
                                    break;

            case "mortal":       toolbar.getContext().setTheme(R.style.AppTheme_ToolbarStyleMortal);
                                    break;

        }

    }


    //Se cambia el color de la pantalla segun la comestibilidad de la seta
    private void setColorPantalla(String comestible)
    {

        //Se cambia el color de la statusbar, toolbar y pagertabstrip
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.verde_claro));

        titToolbar.setTextColor(verdeOscuro);
        toolbar.getContext().setTheme(R.style.AppTheme_ToolbarStyleComestible);

        if(comestible.equals("precaucion"))
        {
            imgToolBar.setImageResource(R.drawable.cuidado_small);

            return;
        }

        if(comestible.equals("sin_interes"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccentGrey));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentGrey));
            layoutPantallaZoom.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentGrey));

            titToolbar.setTextColor(grisOscuro);

            imgToolBar.setImageResource(R.drawable.seta_regular_small);

            return;
        }

        if(comestible.equals("toxica"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccentOrange));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentOrange));
            layoutPantallaZoom.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentOrange));

            titToolbar.setTextColor(naranjaOscuro);

            imgToolBar.setImageResource(R.drawable.seta_venenosa_small);

            return;
        }


        if(comestible.equals("mortal"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorAccentRed));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentRed));
            layoutPantallaZoom.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentRed));

            titToolbar.setTextColor(rojoOscuro);

            imgToolBar.setImageResource(R.drawable.skull_ico);

            return;
        }

        imgToolBar.setImageResource(R.drawable.seta_buena_small);

    }


}
