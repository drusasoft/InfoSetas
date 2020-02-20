package com.dssoft.infosetas.iu;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.dssoft.infosetas.R;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PantallaAyuda extends AppCompatActivity
{

    @BindView(R.id.toolBar_pantalla_ayuda) Toolbar toolbar;
    @BindView(R.id.layout_ayuda) LinearLayout layoutAyuda;
    @BindView(R.id.scroll_ayuda_setas) ScrollView scrollAyuda;
    @BindView(R.id.imgPartesSeta) ImageView imgPartesSetas;
    @BindView(R.id.imgSombreros) ImageView imgSombreros;
    @BindView(R.id.imgDesarrollo) ImageView imgDesarrollo;

    @BindString(R.string.titPantallaZoom_1) String stringPartesSeta;
    @BindString(R.string.titPantallaZoom_2) String stringTiposSombrero;
    @BindString(R.string.titPantallaZoom_3) String stringDesarrolloSeta;


    private String idioma;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_ayuda);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(R.string.titPantallaAyuda);

        //Se obtiene el idioma de la App, Pasado como parametro
        idioma = getIntent().getStringExtra("Idioma");

        //Si el idioma no es el Español, se cargan las imagenes en Ingles
        if(idioma.equals("en"))
            cargarImagenes();

        layoutAyuda.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
            {
                layoutAyuda.removeOnLayoutChangeListener(this);
                efecto_mostrar_circular(scrollAyuda);
            }
        });


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home: onBackPressed();
        }

        return true;
    }

    @OnClick({R.id.imgPartesSeta, R.id.imgSombreros, R.id.imgDesarrollo})
    public void irPantallaZoom(View view)
    {

        if(view != null)
        {
            Intent intent = new Intent(this, PantallaZoom.class);
            ActivityOptions options=null;

            switch (view.getId())
            {
                case R.id.imgPartesSeta: intent.putExtra("nombreSeta", stringPartesSeta);
                                         intent.putExtra("comestible", "comestible");

                                         if(idioma.equals("es"))
                                             intent.putExtra("foto", R.drawable.partes_setas_large);
                                         else
                                             intent.putExtra("foto", R.drawable.partes_setas_large_eng);

                                         options = ActivityOptions.makeSceneTransitionAnimation(this,imgPartesSetas,"img_zoom");

                                         break;

                case R.id.imgSombreros: intent.putExtra("nombreSeta", stringTiposSombrero);
                                        intent.putExtra("comestible", "comestible");

                                        if(idioma.equals("es"))
                                            intent.putExtra("foto", R.drawable.tipos_sombrero_large);
                                        else
                                            intent.putExtra("foto", R.drawable.tipos_sombrero_large_eng);

                                        options = ActivityOptions.makeSceneTransitionAnimation(this,imgSombreros,"img_zoom");

                                        break;

                case R.id.imgDesarrollo: intent.putExtra("nombreSeta", stringDesarrolloSeta);
                                         intent.putExtra("comestible", "comestible");

                                         if(idioma.equals("es"))
                                            intent.putExtra("foto", R.drawable.desarrollo_seta_large);
                                         else
                                             intent.putExtra("foto", R.drawable.desarrollo_seta_large_eng);

                                         options = ActivityOptions.makeSceneTransitionAnimation(this,imgDesarrollo,"img_zoom");

            }

            startActivity(intent, options.toBundle());

        }

    }



    private void cargarImagenes()
    {
        imgPartesSetas.setImageResource(R.drawable.partes_setas_small_eng);
        imgSombreros.setImageResource(R.drawable.tipos_sombrero_small_eng);
        imgDesarrollo.setImageResource(R.drawable.desarrollo_seta_small_eng);
    }


    private void efecto_mostrar_circular(View view)
    {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight())/2;
        int cy = (view.getTop() + view.getBottom())/2;

        // get the final radius for the clipping circle
        int finalRadious = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadious);
        anim.setDuration(1500);//Establezco una duracion mayor al la animacion para ver mejor el efecto

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();


    }
}
