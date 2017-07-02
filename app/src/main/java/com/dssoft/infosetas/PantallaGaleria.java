package com.dssoft.infosetas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.dssoft.infosetas.fragments.PagerFragmentComestibles;

import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Angel on 21/06/2017.
 */

public class PantallaGaleria extends AppCompatActivity
{

    @BindView(R.id.layout_principal_galeria) LinearLayout layoutGaleria;
    @BindView(R.id.toolBar_pantalla_galeria) Toolbar toolBar;
    @BindView(R.id.titToolBarGaleria) TextView titToolbar;
    @BindView(R.id.imgToolBarGaleria) ImageView imgToolbar;
    @BindView(R.id.imgGaleria_1) ImageView imgGaleria_1;
    @BindView(R.id.imgGaleria_2) ImageView imgGaleria_2;
    @BindView(R.id.imgGaleria_3) ImageView imgGaleria_3;
    @BindView(R.id.imgGaleria_4) ImageView imgGaleria_4;
    @BindView(R.id.imgSwitcherGaleria) ImageSwitcher imgSwitcherGaleria;

    private int[] arrayFotos;
    private String nombreSeta, comestible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_galeria);
        ButterKnife.bind(this);

        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Se obtinen los datos pasados como parametro
        nombreSeta = getIntent().getStringExtra("nombreSeta");
        comestible = getIntent().getStringExtra("comestible");
        String fotos = getIntent().getStringExtra("fotos");
        titToolbar.setText(nombreSeta);

        //se cambia el color de la ventana (segun el tipo de seta)
        setColorPantalla(comestible);

        //Se muestran las imagenes en los ImageViews
        mostrarImagenes(fotos);
    }


    @OnClick(R.id.imgGaleria_1)
    public void loadImage1()
    {
        imgSwitcherGaleria.setBackgroundResource(arrayFotos[0]);
        imgSwitcherGaleria.setTag(arrayFotos[0]);
    }


    @OnClick(R.id.imgGaleria_2)
    public void loadImage2()
    {
        imgSwitcherGaleria.setBackgroundResource(arrayFotos[1]);
        imgSwitcherGaleria.setTag(arrayFotos[1]);
    }


    @OnClick(R.id.imgGaleria_3)
    public void loadImage3()
    {
        imgSwitcherGaleria.setBackgroundResource(arrayFotos[2]);
        imgSwitcherGaleria.setTag(arrayFotos[2]);
    }


    @OnClick(R.id.imgGaleria_4)
    public void loadImage4()
    {
        imgSwitcherGaleria.setBackgroundResource(arrayFotos[3]);
        imgSwitcherGaleria.setTag(arrayFotos[3]);
    }


    @OnClick(R.id.imgSwitcherGaleria)
    public void irPantallaZoom()
    {

        Intent intent = new Intent(this, PantallaZoom.class);
        intent.putExtra("nombreSeta", nombreSeta);
        intent.putExtra("comestible", comestible);
        intent.putExtra("foto", Integer.valueOf(imgSwitcherGaleria.getTag().toString()));
        startActivity(intent);

    }


    //Se obitiene el array d¡con las ids de las imagenes
    //y se carga cada imagen en cada ImageView
    private void mostrarImagenes(String fotos)
    {

        arrayFotos = new int[4];
        int i=0;

        StringTokenizer st = new StringTokenizer(fotos,"-");

        while(st.hasMoreTokens())
        {
            arrayFotos[i] = Integer.parseInt(st.nextToken());
            i++;
        }

        Factory viewFactory = new Factory();

        imgSwitcherGaleria.setFactory(viewFactory);
        imgSwitcherGaleria.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imgSwitcherGaleria.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        imgSwitcherGaleria.setBackgroundResource(arrayFotos[0]);

        imgGaleria_1.setBackgroundResource(arrayFotos[0]);
        imgGaleria_2.setBackgroundResource(arrayFotos[1]);
        imgGaleria_3.setBackgroundResource(arrayFotos[2]);
        imgGaleria_4.setBackgroundResource(arrayFotos[3]);


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

            toolBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryOrange));
            layoutGaleria.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentOrange));

            imgToolbar.setImageResource(R.drawable.seta_venenosa_small);

            return;
        }


        if(comestible.equals("mortal"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkRed));

            toolBar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryRed));
            layoutGaleria.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentRed));

            imgToolbar.setImageResource(R.drawable.seta_mortal_small);

            return;
        }


        imgToolbar.setImageResource(R.drawable.seta_buena_small);


    }


    //*********************************************************************************************
    //Clase Interna para ImageSwitcher
    //*********************************************************************************************
    public class Factory implements ViewSwitcher.ViewFactory {

        @Override
        public View makeView()
        {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // TODO Auto-generated method stub
            return imageView;

        }


    }

}
