package com.dssoft.infosetas.fragments;

import android.animation.Animator;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.dssoft.infosetas.iu.PantallaAyuda;
import com.dssoft.infosetas.iu.PantallaZoom;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.SetaFireBase;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.StringTokenizer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;
import butterknife.Unbinder;

/**
 * Created by Angel on 18/05/2017.
 */

public class PagerFragmentSetas extends Fragment
{

    @Nullable @BindView(R.id.cardViewNombre) CardView cardViewNombre;
    @Nullable @BindView(R.id.cardViewDescripcion) CardView cardViewDescripcion;
    @Nullable @BindView(R.id.cardViewHabitat) CardView cardViewHabitat;
    @Nullable @BindView(R.id.cardViewComestibilidad) CardView cardViewComestibilidad;
    @Nullable @BindView(R.id.cardViewObservaciones) CardView cardViewObservaciones;
    @Nullable @BindView(R.id.cardViewGaleria) CardView cardViewGaleria;

    @Nullable @BindView(R.id.txtDescripcion) TextView txtDescripcion;
    @Nullable @BindView(R.id.txtHabitat) TextView txtHabitat;
    @Nullable @BindView(R.id.txtComestibilidad) TextView txtComestibilidad;
    @Nullable @BindView(R.id.txtObservaciones) TextView txtObservaciones;
    @Nullable @BindView(R.id.txtNombre) TextView txtNombre;
    @Nullable @BindView(R.id.txtNombreComun) TextView txtNombreComun;

    @Nullable @BindView(R.id.imgGaleria_1) ImageView imageView1;
    @Nullable @BindView(R.id.imgGaleria_2) ImageView imageView2;
    @Nullable @BindView(R.id.imgGaleria_3) ImageView imageView3;
    @Nullable @BindView(R.id.imgGaleria_4) ImageView imageView4;
    @Nullable @BindView(R.id.imageSwitcherDetalles) ImageSwitcher imgSwitcher;

    private int[] arrayFotos;
    private int numPagina;
    private SetaFireBase setaDetalles;
    private Unbinder unbinder;
    private Context context;
    private String fotos, comestible;
    private ImageView imgSeleccionada;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Recuperamos el parametro pasado al crear la clase en el MainPageAdapter
        Bundle bundle = getArguments();
        numPagina = bundle.getInt("numPagina",0);
        setaDetalles = bundle.getParcelable("setaDetalles");
        fotos = bundle.getString("fotos");
        comestible = bundle.getString("comestible");
        context = getContext();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {


        switch (numPagina)
        {

            case 0: final View rootPag1 = (ViewGroup) inflater.inflate(R.layout.layout_pagina_1, container, false);
                    unbinder = ButterKnife.bind(this, rootPag1);

                    if(setaDetalles != null)
                    {
                        txtNombre.setText(setaDetalles.getNombre());
                        txtNombreComun.setText(setaDetalles.getNombre_comun());
                        txtDescripcion.setText(setaDetalles.getDescripcion());

                        //Se ejecuta cuanado el layout ya esta disponible, y entonces se ejecuta la animacion de mostrar circular para cada cardview
                        //si no se hace asi peta, ya que la animacion se haria cuando el layout aun no esta disponible
                        rootPag1.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
                            {
                                rootPag1.removeOnLayoutChangeListener(this);
                                efecto_mostrar_circular(cardViewNombre);
                                efecto_mostrar_circular(cardViewDescripcion);

                                //Se carga el banner
                                AdView mAdView = (AdView) rootPag1.findViewById(R.id.banner_pantalla_detalles_1);
                                AdRequest adRequest = new AdRequest.Builder().build();
                                mAdView.loadAd(adRequest);

                            }
                        });


                    }

                    return rootPag1;


            case 1: final View rootPag2 = (ViewGroup) inflater.inflate(R.layout.layout_pagina_2, container, false);
                    unbinder = ButterKnife.bind(this, rootPag2);

                    if(setaDetalles != null)
                    {
                        txtHabitat.setText(setaDetalles.getHabitat());
                        txtComestibilidad.setText(setaDetalles.getComestibilidad());

                        //Se ejecuta cuanado el layout ya esta disponible, y entonces se ejecuta la animacion de mostrar circular para cada cardview
                        //si no se hace asi peta, ya que la animacion se haria cuando el layout aun no esta disponible
                        rootPag2.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
                            {
                                rootPag2.removeOnLayoutChangeListener(this);
                                efecto_mostrar_circular(cardViewHabitat);
                                efecto_mostrar_circular(cardViewComestibilidad);

                                //Se carga el banner
                                AdView mAdView = (AdView) rootPag2.findViewById(R.id.banner_pantalla_detalles_2);
                                AdRequest adRequest = new AdRequest.Builder().build();
                                mAdView.loadAd(adRequest);
                            }
                        });
                    }

                    return rootPag2;


            case 2: final View rootPag3 = (ViewGroup) inflater.inflate(R.layout.layout_pagina_3, container, false);
                    unbinder = ButterKnife.bind(this, rootPag3);

                    if(setaDetalles != null)
                    {

                        txtObservaciones.setText(setaDetalles.getObservaciones());

                        //Se ejecuta cuanado el layout ya esta disponible, y entonces se ejecuta la animacion de mostrar circular para cada cardview
                        //si no se hace asi peta, ya que la animacion se haria cuando el layout aun no esta disponible
                        rootPag3.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
                            {
                                rootPag3.removeOnLayoutChangeListener(this);
                                efecto_mostrar_circular(cardViewObservaciones);

                                //Se carga el banner
                                AdView mAdView = (AdView) rootPag3.findViewById(R.id.banner_pantalla_detalles_3);
                                AdRequest adRequest = new AdRequest.Builder().build();
                                mAdView.loadAd(adRequest);
                            }

                        });

                    }

                    return rootPag3;


            case 3: final View rootPag4 = (ViewGroup) inflater.inflate(R.layout.layout_pagina_4, container, false);
                    unbinder = ButterKnife.bind(this, rootPag4);

                    if(setaDetalles != null)
                    {

                        //Se obtiene el id de recurso de cada foto para mostrarlas en la galeria
                        arrayFotos = new int[4];
                        int i =0;

                        StringTokenizer st = new StringTokenizer(fotos,"-");

                        while(st.hasMoreTokens())
                        {
                            arrayFotos[i] = Integer.parseInt(st.nextToken());
                            i++;
                        }

                        Factory viewFactory = new Factory();

                        imgSwitcher.setFactory(viewFactory);
                        imgSwitcher.setInAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_in_left));
                        imgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(getContext(), android.R.anim.slide_out_right));
                        imgSwitcher.setBackgroundResource(arrayFotos[0]);
                        imgSwitcher.setTag(arrayFotos[0]);

                        imageView1.setBackgroundResource(arrayFotos[0]);
                        imageView2.setBackgroundResource(arrayFotos[1]);
                        imageView3.setBackgroundResource(arrayFotos[2]);
                        imageView4.setBackgroundResource(arrayFotos[3]);
                        imgSeleccionada = imageView1;

                        /*Picasso.with(getContext()).load(R.drawable.arvensis1).into(imageView1);
                        Picasso.with(getContext()).load(R.drawable.arvensis2).into(imageView2);
                        Picasso.with(getContext()).load(R.drawable.arvensis3).into(imageView3);
                        Picasso.with(getContext()).load(R.drawable.arvensis4).into(imageView4);*/

                        //Se ejecuta cuanado el layout ya esta disponible, y entonces se ejecuta la animacion de mostrar circular para cada cardview
                        //si no se hace asi peta, ya que la animacion se haria cuando el layout aun no esta disponible
                        rootPag4.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                            @Override
                            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
                            {
                                rootPag4.removeOnLayoutChangeListener(this);
                                efecto_mostrar_circular(cardViewGaleria);

                            }
                        });

                    }

                    return rootPag4;

        }

        return null;
    }

    @Optional
    @OnClick(R.id.imgGaleria_1)
    public void loadImage1()
    {
        if(imgSwitcher != null)
        {
            imgSwitcher.setBackgroundResource(arrayFotos[0]);
            imgSwitcher.setTag(arrayFotos[0]);
            imgSeleccionada = imageView1;
        }

    }


    @Optional
    @OnClick(R.id.imgGaleria_2)
    public void loadImage2()
    {
        if(imgSwitcher != null)
        {
            imgSwitcher.setBackgroundResource(arrayFotos[1]);
            imgSwitcher.setTag(arrayFotos[1]);
            imgSeleccionada = imageView2;
        }

    }


    @Optional
    @OnClick(R.id.imgGaleria_3)
    public void loadImage3()
    {
        if(imgSwitcher != null)
        {
            imgSwitcher.setBackgroundResource(arrayFotos[2]);
            imgSwitcher.setTag(arrayFotos[2]);
            imgSeleccionada = imageView3;
        }

    }


    @Optional
    @OnClick(R.id.imgGaleria_4)
    public void loadImage4()
    {
        if(imgSwitcher != null)
        {
            imgSwitcher.setBackgroundResource(arrayFotos[3]);
            imgSwitcher.setTag(arrayFotos[3]);
            imgSeleccionada = imageView4;
        }

    }

    @Optional
    @OnClick(R.id.imageSwitcherDetalles)
    public void irPantallaZoom()
    {
        Intent intent = new Intent(context, PantallaZoom.class);
        intent.putExtra("nombreSeta",setaDetalles.getNombre());
        intent.putExtra("comestible",comestible);
        intent.putExtra("foto", Integer.valueOf(imgSwitcher.getTag().toString()));

        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(),imgSeleccionada,"img_zoom");
        startActivity(intent,options.toBundle());
    }

    @Optional
    @OnClick(R.id.txtIrAyuda)
    public void irpantallaAyuda()
    {
        Intent intent = new Intent(context, PantallaAyuda.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        unbinder.unbind();
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



    //*********************************************************************************************
    //Clase Interna para ImageSwitcher
    //*********************************************************************************************
    public class Factory implements ViewSwitcher.ViewFactory {

        @Override
        public View makeView()
        {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new ImageSwitcher.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // TODO Auto-generated method stub
            return imageView;

        }


    }

}

