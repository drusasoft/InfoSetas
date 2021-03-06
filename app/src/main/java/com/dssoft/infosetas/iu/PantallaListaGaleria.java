package com.dssoft.infosetas.iu;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.adaptadores.AdaptadorListaSetas;
import com.dssoft.infosetas.adaptadores.AdaptadorSpinnerSetas;
import com.dssoft.infosetas.pojos.Seta;
import com.dssoft.infosetas.presentador.PresentadorLista;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 21/06/2017.
 */

public class PantallaListaGaleria extends AppCompatActivity implements VistaLista, AdapterView.OnItemSelectedListener, SearchView.OnQueryTextListener, SearchView.OnCloseListener
{

    @BindView(R.id.layout_pantalla_lista) LinearLayout layoutPantallaLista;
    @BindView(R.id.toolbar_lista_setas) Toolbar toolbar;
    @BindView(R.id.listview_setas) ListView listViewSetas;
    @BindView(R.id.spinner_setas) Spinner spinnerSetas;
    @BindView(R.id.buscador_setas) SearchView buscador;
    @BindView(R.id.cardViewFavoritas) CardView cardViewFavoritas;
    @BindColor(R.color.colorPrimaryDarkOrange) int colorNaranja;

    private PresentadorLista presentadorLista;
    private AdaptadorListaSetas als;
    private List<Seta> listSetas = new ArrayList<Seta>();//Aqui se almacenan las setas que se van a mostrar en el ListView

    private AnimationDrawable animacionFondo;
    private boolean pulsacionSpinner = false;//Esta variable es por la mierda del spinner porque para evitar que se haga la pulsacion automatica que sucede cuado se crea la pantalla
    private boolean animacionFondoIniciada;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_lista_setas);
        ButterKnife.bind(this);

        //Se cambia el color de la statusbar y se pone transparente
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        String[] arrayTipos = getResources().getStringArray(R.array.tipos_array);
        AdaptadorSpinnerSetas ass = new AdaptadorSpinnerSetas(this, arrayTipos);
        ass.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinnerSetas.setAdapter(ass);
        spinnerSetas.setOnItemSelectedListener(this);

        //Se crea el Presentador de esta pantalla y se le pasa la capa modelo (DataManagerBD) creada en la variable global
        InfoSetas infoSetas = (InfoSetas) getApplication();
        presentadorLista = new PresentadorLista(infoSetas.getDataManagerBD());
        presentadorLista.setVista(this);

        buscador.setOnQueryTextListener(this);
        buscador.setOnCloseListener(this);

        listSetas = presentadorLista.obtenerSetasBD(getApplicationContext());//Se obtienen todas las setas de la BD

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

        //Si se hace una pulsacion larga sobre un elemento de la lista se añade o quita el elemento de la lista de favoritos
        listViewSetas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
            {


                if(listSetas.get(position).isFavorita())
                {
                    presentadorLista.delFavorita(listSetas.get(position).getNombre());
                }else
                {
                    presentadorLista.addFavorita(listSetas.get(position).getNombre());
                }

                return true;
            }

        });

        buscador.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Si se pulsa sobre el buscador se oculta el spinner
                spinnerSetas.setVisibility(View.GONE);

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home: onBackPressed();
        }

        return true;
    }


    @Override
    //Se ejecuta cuando se selecciona un item del spinner
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

        if(pulsacionSpinner)
        {

            //Se obtiene la lista de setas de la capa modelo (segun el tipo de seta seleccionado en el spinner)
            switch (position)
            {

                case 0: presentadorLista.getSetas(0);
                    break;

                case 1: presentadorLista.getSetas(1);
                    break;

                case 2: presentadorLista.getSetas(2);
                    break;

                case 3: presentadorLista.getSetas(3);
                    break;

                case 4: presentadorLista.getSetas(4);
                    break;

                case 5: presentadorLista.getSetas(5);
                    break;

                case 6: presentadorLista.getSetas(6);
                    break;

            }

        }else
        {
            pulsacionSpinner = true;//ahora ya se permite pulsar el spinner (es para evitar la mierda de la pulsacion automatica que se produce la primera vez cuando se carga)
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    //**********************************************************************************************
    //Listeners del Buscador
    //**********************************************************************************************

    @Override
    //Se ejecuta cuando se cierra el buscador
    public boolean onClose()
    {
        //Si se cierra el buscador se muestra el spinner
        spinnerSetas.setVisibility(View.VISIBLE);

        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {

        return false;
    }

    @Override
    //Se ejecuta cada vez que escribimos o borramos una letra en el buscador
    public boolean onQueryTextChange(String newText)
    {

        //Si se borra la palabra escrita en el buscador
        if(newText.length() == 0)
        {

            presentadorLista.finBusqueda();

        }else
        {
            newText = newText.toLowerCase();
            presentadorLista.buscarSeta(newText, colorNaranja);//Se busca la palabra en la lista de setas

        }

        return false;
    }
    //**********************************************************************************************
        //Fin Listeners del Buscador
    //**********************************************************************************************


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


    //Se inicia la animacion (AnimationDrawable) de la imagen de fondo de la pantalla principal
    private void animacion_fondo()
    {

        //Obtener hora del Systema
        Long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");//HH devuelve la hora en formato 24h y hh en formato de 12
        String hora = sdf.format(date);

        StringTokenizer stringTokenizer = new StringTokenizer(hora,":");
        String token = stringTokenizer.nextToken();
        int hh = Integer.valueOf(token);

        //Dependiendo de la hora del sistema entonces se muestra el fondo animado correcpondiente
        if(hh>6 && hh<15)
        {
            layoutPantallaLista.setBackgroundResource(R.drawable.animacion_pantallalista);
        }else
        {
            if(hh>=15 && hh<21)
            {
                layoutPantallaLista.setBackgroundResource(R.drawable.animacion_pantallalista_tarde);
            }else
            {
                layoutPantallaLista.setBackgroundResource(R.drawable.animacion_pantallalista_noche);
            }
        }

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
    //Se muestra la lista de setas enviada por la capa modelo
    public void mostrarListaSetas(List<Seta> listSetas)
    {
        this.listSetas = listSetas;

        if(listSetas.size() == 0 && presentadorLista.isMostrandoFav())
        {
            efecto_mostrar_circular(cardViewFavoritas);
        }else
        {
            cardViewFavoritas.setVisibility(View.INVISIBLE);
        }

        als.notifyDataSetChanged();
    }


}
