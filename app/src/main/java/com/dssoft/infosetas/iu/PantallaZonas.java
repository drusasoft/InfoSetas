package com.dssoft.infosetas.iu;

import android.animation.Animator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.adaptadores.AdaptadorListaZonas;
import com.dssoft.infosetas.pojos.Zona;
import com.dssoft.infosetas.presentador.PresentadorMisZonas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnItemLongClick;
import butterknife.Optional;

/**
 * Created by Angel on 13/02/2018.
 */

public class PantallaZonas extends AppCompatActivity implements VistaZonas
{

    @Nullable @BindView(R.id.layoutPantallaZonas) RelativeLayout layoutPantallaZonas;
    @Nullable @BindView(R.id.toolBar_pantalla_zonas) Toolbar toolbar;
    @Nullable @BindView(R.id.cardViewNoZonas) CardView cardViewZonas;
    @Nullable @BindView(R.id.listViewZonas) ListView listViewZonas;
    @Nullable @BindView(R.id.txtMapaDialog) TextView txtDialogMapa;
    @Nullable @BindView(R.id.txtGpsDialog) TextView txtDialogGps;
    @Nullable @BindView(R.id.layoutImageMap) LinearLayout layoutImageMap;
    @Nullable @BindView(R.id.layoutImageGps) LinearLayout layoutImageGps;

    @Nullable @BindView(R.id.btnNuevaZona) FloatingActionButton btnNuevaZona;
    @Nullable @BindView(R.id.btnBorrarZona) FloatingActionButton btnBorrarZona;

    @BindDrawable(R.drawable.borde_tiempo) Drawable borde_tiempo;
    @BindColor(R.color.colorPrimaryDarkGrey) int colorGris;
    @BindColor(R.color.colorPrimaryDark) int colorVerde;

    private AnimationDrawable animacionFondo;
    private LinearLayout layoutDialog;
    private PresentadorMisZonas presentadorMisZonas;
    private AdaptadorListaZonas alz;
    private List<Zona> listZonas;
    private List<Zona> listZonasSeleccionadas = new ArrayList<Zona>();
    private Activity activity;
    private boolean pantallaDisponible;
    private boolean animacionFondoIniciada;
    private boolean existeGPS;
    private int zonasSeleccionadas;
    private String opcionSeleccionada="mapa";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_zonas);
        ButterKnife.bind(this);
        activity = this;

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Se cambia el color de la statusbar y se pone transparente
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        InfoSetas infoSetas = (InfoSetas) getApplicationContext();
        presentadorMisZonas = new PresentadorMisZonas(infoSetas.getDataManagerBD(), infoSetas.getDataManagerGPS());
        presentadorMisZonas.setVista(this);
        existeGPS = presentadorMisZonas.comprobarExistenciaGPS();

        animacion_fondo();

        //Cuando la pantalla ya esta dispnible
        layoutPantallaZonas.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
            {
                layoutPantallaZonas.removeOnLayoutChangeListener(this);

                //Se obtienen todas las zonas de la BD
                presentadorMisZonas.getListaZonas();
                pantallaDisponible = true;
            }
        });

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_zonas);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if(animacionFondoIniciada && !animacionFondo.isRunning())
            animacionFondo.start();//se vuelve a iniciar la animacion del fondo de pantalla

        //Se actualiza el adaptador para si se añadido una nueva zona que se muestre en la lista
        if(pantallaDisponible)
        {
            //Se obtienen todas las zonas de la BD
            presentadorMisZonas.getListaZonas();
        }

    }


    @Override
    protected void onPause()
    {
        super.onPause();

        if(animacionFondoIniciada && animacionFondo.isRunning())
            animacionFondo.stop();//se pausa la animacion del fondo de pantalla
    }


    @Optional
    @OnClick(R.id.btnNuevaZona)
    public void mostrarDialogoNuevaZona()
    {

        opcionSeleccionada = "mapa";
        
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        layoutDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.layout_dialog_nueva_zona, null);
        builder.setView(layoutDialog);
        ButterKnife.bind(this, layoutDialog);
        builder.setCancelable(false);

        //se muestra seleccionada la opcion mapa por defecto
        layoutImageMap.setBackground(borde_tiempo);
        layoutImageGps.setBackground(null);
        txtDialogMapa.setTextColor(colorVerde);
        txtDialogGps.setTextColor(colorGris);

        if(!existeGPS)//Si no hay GPS se oculata la opcion en el cudro de dialogo
        {
            layoutImageGps.setVisibility(View.GONE);
            txtDialogGps.setVisibility(View.GONE);
        }

        builder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                ButterKnife.bind(activity);//Vuelvo ha cargar los view de la actividad ya que estos se han puesto a null al cargar los view del Cuadro de Dialogo

                if(opcionSeleccionada.equals("mapa"))
                {
                    Intent intent = new Intent(getApplicationContext(), PantallaMapaNuevaZona.class);
                    startActivity(intent);
                }else
                {

                    //Se comprueba si esta activado el GPG
                    if(presentadorMisZonas.comprobarGPSHabilitado())
                    {
                        Intent intent = new Intent(getApplicationContext(), PantallaMapaGPS.class);
                        startActivity(intent);
                    }else
                    {
                        mostrarDialogConfiguracion();
                    }

                }

            }
        });

        builder.setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                ButterKnife.bind(activity);//Vuelvo ha cargar los view de la actividad ya que estos se han puesto a null al cargar los view del Cuadro de Dialogo
            }
        });

        builder.create().show();

    }


    @Optional
    @OnClick(R.id.btnBorrarZona)
    public void mostrarDialogoBorrarZona()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(zonasSeleccionadas >1)
        {
            builder.setTitle(R.string.titDialogBorrarZonas);
            builder.setMessage(R.string.txtDialogBorrarZonas);
        }else
        {
            builder.setTitle(R.string.titDialogBorrarZona);
            builder.setMessage(R.string.txtDialogBorrarZona);
        }

        builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                presentadorMisZonas.borrarZona(listZonasSeleccionadas);
            }
        });

        builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }


    @Optional
    @OnItemClick(R.id.listViewZonas)
    //Si se pulsa sobre un elemento de la lista se va a la pantalla detalles y si elemento estaba seleccionado se deselecciona
    public void oneItemClick(int position)
    {

        //Se comprueba si el elemento estaba seleccionado
        if(listZonas.get(position).isSeleccionado())
        {
            //se deselecciona el elemento
            listZonas.get(position).setSeleccionado(false);
            listZonasSeleccionadas.remove(listZonas.get(position));
            alz.notifyDataSetChanged();

            boolean quedanSeleccionados=false;
            for(Zona zona:listZonas)
            {
                if(zona.isSeleccionado())
                {
                    quedanSeleccionados = true;
                    break;
                }
            }

            if(!quedanSeleccionados)
            {
                btnBorrarZona.setVisibility(View.INVISIBLE);
                btnNuevaZona.setVisibility(View.VISIBLE);
            }

        }else
        {

            //Se deseleccionan todos los elemtos de la lista (por si los hubiera)
            for(Zona zona:listZonas)
                zona.setSeleccionado(false);

            alz.notifyDataSetChanged();

            btnBorrarZona.setVisibility(View.INVISIBLE);
            btnNuevaZona.setVisibility(View.VISIBLE);

            //Se va a la pantalla detalles
            Intent intent = new Intent(this, PantallaVerDatosZonas.class);
            intent.putExtra("id_zona", listZonas.get(position).getId_zona());
            intent.putExtra("nombre_zona", listZonas.get(position).getNombre());
            intent.putExtra("descripcion_zona", listZonas.get(position).getDescripcion());
            intent.putExtra("latitud", listZonas.get(position).getLatitud());
            intent.putExtra("longitud", listZonas.get(position).getLongitud());

            startActivity(intent);

        }

    }


    @Optional
    @OnItemLongClick(R.id.listViewZonas)
    //Si se realiza una pulsacion larga sobre un elemento de la lista este se muestra seleccionado
    public boolean oneItemLongClick(int position)
    {

        listZonas.get(position).setSeleccionado(true);
        listZonasSeleccionadas.add(listZonas.get(position));
        alz.notifyDataSetChanged();

        zonasSeleccionadas = 0;
        for(Zona zona:listZonas)
        {
            if(zona.isSeleccionado())
                zonasSeleccionadas++;
        }

        btnBorrarZona.setVisibility(View.VISIBLE);
        btnNuevaZona.setVisibility(View.INVISIBLE);

        return true;
    }


    @Optional
    @OnClick(R.id.layoutOpcionMapa)
    public void selecMapa()
    {
        layoutImageMap.setBackground(borde_tiempo);
        layoutImageGps.setBackground(null);
        txtDialogMapa.setTextColor(colorVerde);
        txtDialogGps.setTextColor(colorGris);

        opcionSeleccionada = "mapa";
    }


    @Optional
    @OnClick(R.id.layoutOpcionGps)
    public void selecGps()
    {
        layoutImageMap.setBackground(null);
        layoutImageGps.setBackground(borde_tiempo);
        txtDialogGps.setTextColor(colorVerde);
        txtDialogMapa.setTextColor(colorGris);

        opcionSeleccionada = "gps";
    }


    @Override
    public void mostrarZonas(List<Zona> listaZonas)
    {

        btnBorrarZona.setVisibility(View.INVISIBLE);
        btnNuevaZona.setVisibility(View.VISIBLE);

        if(listaZonas.size()>0 && alz == null)
        {
            listZonas = listaZonas;

            alz = new AdaptadorListaZonas(this, listZonas);
            listViewZonas.setAdapter(alz);

            cardViewZonas.setVisibility(View.INVISIBLE);
            efecto_mostrar_circular(listViewZonas);


        }else
        {

            if(listaZonas.size()>0 && alz != null)
            {
                listZonasSeleccionadas.clear();//Se borra la lista de zonas seleccionadas
                listZonas = listaZonas;

                alz.notifyDataSetChanged();

                cardViewZonas.setVisibility(View.INVISIBLE);
                efecto_mostrar_circular(listViewZonas);

            }else
            {
                listZonasSeleccionadas.clear();//Se borra la lista de zonas seleccionadas
                listViewZonas.setVisibility(View.INVISIBLE);

                //Se muestra el texto de Lista vacia
                efecto_mostrar_circular(cardViewZonas);
            }


        }
    }


    @Override
    public void mostrarMensaje(int mensaje)
    {
        Snackbar.make(layoutPantallaZonas, mensaje, Snackbar.LENGTH_LONG).show();
    }


    //Se le pregunta al usuario si quiere ir a la Pantalla de configuracion para activar GPS
    public void mostrarDialogConfiguracion()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titDialogUbicacionZona);
        builder.setMessage(R.string.txtDialogUbicacionZona);

        builder.setPositiveButton(R.string.btnSi, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                //Este es el codigo para mostrar la pantalla de configuracion de localizacion del telefono
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        builder.setNegativeButton(R.string.btnNo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.create().show();
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
            layoutPantallaZonas.setBackgroundResource(R.drawable.animacion_pantallalista);
        }else
        {
            if(hh>=15 && hh<21)
            {
                layoutPantallaZonas.setBackgroundResource(R.drawable.animacion_pantallalista_tarde);
            }else
            {
                layoutPantallaZonas.setBackgroundResource(R.drawable.animacion_pantallalista_noche);
            }
        }

        layoutPantallaZonas.post(new Runnable() {
            @Override
            public void run()
            {
                animacionFondo = (AnimationDrawable) layoutPantallaZonas.getBackground();
                animacionFondo.start();
                animacionFondoIniciada = true;

            }
        });

    }

}
