package com.dssoft.infosetas.iu;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import com.dssoft.infosetas.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_ayuda);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

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
                case R.id.imgPartesSeta: intent.putExtra("nombreSeta", "Partes Setas");
                                         intent.putExtra("comestible", "comestible");
                                         intent.putExtra("foto", R.drawable.partes_setas_large);
                                         options = ActivityOptions.makeSceneTransitionAnimation(this,imgPartesSetas,"img_zoom");

                                         break;

                case R.id.imgSombreros: intent.putExtra("nombreSeta", "Tipos Sombreros");
                                        intent.putExtra("comestible", "comestible");
                                        intent.putExtra("foto", R.drawable.tipos_sombrero_large);
                                        options = ActivityOptions.makeSceneTransitionAnimation(this,imgSombreros,"img_zoom");

                                        break;

                case R.id.imgDesarrollo: intent.putExtra("nombreSeta", "Desarrollo Seta");
                                         intent.putExtra("comestible", "comestible");
                                         intent.putExtra("foto", R.drawable.desarrollo_seta_large);
                                         options = ActivityOptions.makeSceneTransitionAnimation(this,imgDesarrollo,"img_zoom");

            }

            startActivity(intent, options.toBundle());

        }

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
