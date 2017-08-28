package com.dssoft.infosetas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import com.dssoft.infosetas.adaptadores.AdaptadorListaSetas;
import com.dssoft.infosetas.adaptadores.AdaptadorSpinnerSetas;
import com.dssoft.infosetas.base_datos.BDAdapter;
import com.dssoft.infosetas.pojos.Seta;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 21/06/2017.
 */

public class PantallaListaGaleria extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    @BindView(R.id.layout_pantalla_lista) LinearLayout layoutPantallaLista;
    @BindView(R.id.toolbar_lista_setas) Toolbar toolbar;
    @BindView(R.id.listview_setas) ListView listViewSetas;
    @BindView(R.id.spinner_setas) Spinner spinnerSetas;

    private List<Seta> listSetas = new ArrayList<Seta>();
    private List<Seta> listSetasAux = new ArrayList<Seta>();
    private AnimationDrawable animacionFondo;
    private boolean animacionFondoIniciada;
    private AdaptadorListaSetas als;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_lista_setas);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String[] arrayTipos = getResources().getStringArray(R.array.tipos_array);
        AdaptadorSpinnerSetas ass = new AdaptadorSpinnerSetas(this, arrayTipos);
        ass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinnerSetas.setAdapter(ass);
        spinnerSetas.setOnItemSelectedListener(this);

        obtenerListaSetas();//Se obtiene la lista de setas de la BD
        als = new AdaptadorListaSetas(this, listSetas);
        listViewSetas.setAdapter(als);

        animacion_fondo();

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_lista);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    protected void onResume()
    {
        super.onResume();

        listViewSetas.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), PantallaGaleria.class);
                intent.putExtra("nombreSeta", listSetas.get(position).getNombre());
                intent.putExtra("comestible", listSetas.get(position).getComestible());
                intent.putExtra("fotos", listSetas.get(position).getFotos());
                startActivity(intent);
            }

        });

        if(animacionFondoIniciada && !animacionFondo.isRunning())
            animacionFondo.start();//se vuelve a iniciar la animacion del fondo de pantalla

    }


    @Override
    protected void onPause()
    {
        super.onPause();

        if(animacionFondoIniciada && animacionFondo.isRunning())
            animacionFondo.stop();//se pausa la animacion del fondo de pantalla
    }


    //Se obtienen los datos de la BD
    //Y se crea la lista de setas
    private void obtenerListaSetas()
    {
        listSetas.clear();

        BDAdapter bd = new BDAdapter(this);
        bd.abrirBD();

        Cursor cursor = bd.obtenerRegistros();
        cursor.moveToFirst();

        do
        {

            String nombre = getResources().getString(cursor.getInt(1));
            String nombre_comun = getResources().getString(cursor.getInt(2));
            String nombre_ordenar = getResources().getString(cursor.getInt(3));
            String comestible = getResources().getString(cursor.getInt(4));
            String fotos = cursor.getString(5);
            int foto_list = cursor.getInt(6);

            Seta seta = new Seta(nombre, nombre_comun, nombre_ordenar, comestible, fotos, foto_list);
            listSetasAux.add(seta);

        }while(cursor.moveToNext());

        bd.cerrarBD();

        ordenarLista();

    }


    //Se ordena la lista de setas por ordena alfabetico
    private void ordenarLista()
    {

        Seta setamenor;

        for(int i=0;i<listSetasAux.size();i++)
        {
            setamenor = listSetasAux.get(i);

            for(int j=i+1; j<listSetasAux.size();j++)
            {

                if(listSetasAux.get(j).getNombre_ordenar().compareTo(setamenor.getNombre_ordenar())<0)
                {

                    Seta aux = listSetasAux.get(j);
                    listSetasAux.set(i, aux);
                    listSetasAux.set(j, setamenor);
                    setamenor = aux;

                }

            }

        }

        listSetas.addAll(listSetasAux);

    }


    //Se inicia la animacion (AnimationDrawable) de la imagen de fondo de la pantalla principal
    private void animacion_fondo()
    {
        layoutPantallaLista.setBackgroundResource(R.drawable.animacion_pantallalista);

        layoutPantallaLista.post(new Runnable() {
            @Override
            public void run()
            {
                animacionFondo = (AnimationDrawable) layoutPantallaLista.getBackground();
                animacionFondo.start();
                animacionFondoIniciada = true;

            }
        });
    }


    @Override
    //Se ejecuta cuando se selecciona un item del spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

        listSetas.clear();

        switch (position)
        {

            case 0: listSetas.addAll(listSetasAux);
                break;

            case 1: for(Seta seta: listSetasAux)
            {
                if(seta.getComestible().equals("comestible"))
                    listSetas.add(seta);
            }

                break;

            case 2: for(Seta seta: listSetasAux)
            {
                if(seta.getComestible().equals("sin_interes"))
                    listSetas.add(seta);
            }

                break;

            case 3: for(Seta seta: listSetasAux)
            {
                if(seta.getComestible().equals("precaucion"))
                    listSetas.add(seta);
            }

                break;

            case 4: for(Seta seta: listSetasAux)
            {
                if(seta.getComestible().equals("toxica"))
                    listSetas.add(seta);
            }

                break;

            case 5: for(Seta seta: listSetasAux)
            {
                if(seta.getComestible().equals("mortal"))
                    listSetas.add(seta);
            }

                break;

        }

        als.notifyDataSetChanged();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
