package com.dssoft.infosetas.iu;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.DatosPrevision;
import com.dssoft.infosetas.presentador.PresentadorTiempo;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import java.util.List;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Angel on 08/10/2017.
 */

public class PantallaTiempo extends AppCompatActivity implements VistaTiempo, AdapterView.OnItemSelectedListener
{

    @BindView(R.id.toolBarTiempo) Toolbar toolBarTiempo;
    @BindView(R.id.layout_pantalla_tiempo) LinearLayout layoutPantallaTiempo;
    @BindView(R.id.spinnerLocalidades) Spinner spinnerLocalidades;
    @BindView(R.id.cardViewFormulario) CardView cardViewFormulario;
    @BindView(R.id.cardViewPrevision) CardView cardViewPrevision;
    @BindView(R.id.editLocalidad) EditText editLocalidad;
    @BindView(R.id.editPais) EditText editPais;
    @BindView(R.id.txtTiempoLocalidad) TextView txtLocalidad;
    @BindView(R.id.txtTiempoHoy) TextView txtTiempo;
    @BindView(R.id.txtFechaHoy) TextView txtFecha;
    @BindView(R.id.txtTempMaxHoy) TextView txtTempMax;
    @BindView(R.id.txtTempMinHoy) TextView txtTempMin;
    @BindView(R.id.txtFechaDia2) TextView txtFechaDia2;
    @BindView(R.id.txtFechaDia3) TextView txtFechaDia3;
    @BindView(R.id.txtFechaDia4) TextView txtFechaDia4;
    @BindView(R.id.txtFechaDia5) TextView txtFechaDia5;
    @BindView(R.id.imgTiempoHoy) ImageView imgTiempoHoy;
    @BindView(R.id.imgTiempoDia2) ImageView imgTiempoDia2;
    @BindView(R.id.imgTiempoDia3) ImageView imgTiempoDia3;
    @BindView(R.id.imgTiempoDia4) ImageView imgTiempoDia4;
    @BindView(R.id.imgTiempoDia5) ImageView imgTiempoDia5;

    @BindString(R.string.ws_key) String wsKey;
    @BindString(R.string.titErrorDialog_1) String titError;
    @BindString(R.string.titErrorDialog_2) String titErrorLocalidad;
    @BindString(R.string.tit_dialog_guardar) String titDialogGuardar;
    @BindString(R.string.txtErrorDialog_2) String txtError;
    @BindString(R.string.errGeoLocalidad) String txtErrorLocalidad;
    @BindString(R.string.errGeoLocalidad2) String txtErrorLocalidad2;
    @BindString(R.string.errFormLocalidad) String txtErrorForm;
    @BindString(R.string.txt_dialog_guardar) String txtDialogGuardar;
    @BindString(R.string.tit_progress_dialog) String titConectar;
    @BindString(R.string.txt_progress_dialog) String txtConectar;

    private PresentadorTiempo presentadorTiempo;
    private AnimationDrawable animacionFondo;
    private boolean animacionFondoIniciada, mostrarMenu, mostrarActualizar, modEditLocalidad=true, modEditPais=true;
    private String localidad, pais, cod_pais;

    private List<String> listLocalidades;
    private List<PresentadorTiempo> listPrevision;
    private ArrayAdapter<String> spinnerAdapter;
    private boolean permitirSeleccion=true;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_tiempo);
        ButterKnife.bind(this);

        setSupportActionBar(toolBarTiempo);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Se crea el Presentador de esta pantalla y se le pasa la capa modelo (DataManagerBD) creada en la variable global
        InfoSetas infoSetas = (InfoSetas) getApplication();
        presentadorTiempo = new PresentadorTiempo(infoSetas.getDataManagerBD(), infoSetas.getDataManagerWS());
        presentadorTiempo.setVista(this);

        animacion_fondo();//Se crea la animacion de la imagen de fondo de la pantalla

        //Se obtiene la lista de localidades de la BD
        listLocalidades = presentadorTiempo.getLocalidadesBD();
        spinnerAdapter = new ArrayAdapter(this, R.layout.spinner_item_localidades, listLocalidades);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_dropdown);
        spinnerLocalidades.setAdapter(spinnerAdapter);
        spinnerLocalidades.setOnItemSelectedListener(this);

        if(listLocalidades.size()>0)
        {
            mostrarMenu = true;
            mostrarActualizar = false;
            getSupportActionBar().setTitle("");
            spinnerLocalidades.setVisibility(View.VISIBLE);
            cardViewFormulario.setVisibility(View.INVISIBLE);

            invalidateOptionsMenu();//Se ocultan o muestran los items del menu que correspondan

        }else
        {
            mostrarMenu = false;
            getSupportActionBar().setTitle(R.string.titPantallaTiempo);
            spinnerLocalidades.setVisibility(View.INVISIBLE);
            cardViewFormulario.setVisibility(View.VISIBLE);

            invalidateOptionsMenu();//Se ocultan o muestran los items del menu que correspondan
        }

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantallatiempo);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    protected void onResume()
    {
        super.onResume();

        if(animacionFondoIniciada && !animacionFondo.isRunning())
            animacionFondo.start();//Se inicia la animacion de fondo

    }


    @Override
    protected void onPause()
    {
        super.onPause();

        if(animacionFondoIniciada && animacionFondo.isRunning())
            animacionFondo.stop();//Se para la animacion de fondo

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_tiempo, menu);
        return true;
    }


    @Override
    //Este metodo es llamado para mostrar u ocultar las opciones de menu segun sea necesario
    public boolean onPrepareOptionsMenu(Menu menu)
    {

        MenuItem menuActualizar = menu.findItem(R.id.menu_actualizar);
        MenuItem menuBuscar = menu.findItem(R.id.menu_buscar);
        MenuItem menuEliminar = menu.findItem(R.id.menu_eliminar);

        if(mostrarMenu)
        {

            if(mostrarActualizar)
            {

                menuActualizar.setVisible(true);
                menuBuscar.setVisible(true);
                menuEliminar.setVisible(false);

            }else
            {
                menuActualizar.setVisible(true);
                menuBuscar.setVisible(true);
                menuEliminar.setVisible(true);
            }


        }else
        {
            menuActualizar.setVisible(false);
            menuBuscar.setVisible(false);
            menuEliminar.setVisible(false);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {

            case R.id.menu_actualizar:   mostrarDialogConexion();

                                         if(listLocalidades.size()>0)
                                         {
                                             //Se obtiene la prevision del tiempo del ws
                                             presentadorTiempo.getPrevisionTiempo(spinnerLocalidades.getSelectedItem().toString().toLowerCase(), presentadorTiempo, wsKey, getApplicationContext());

                                         }else
                                         {
                                             //Se obtiene la prevision del tiempo del ws
                                             presentadorTiempo.getPrevisionTiempo(localidad.toLowerCase()+","+cod_pais, presentadorTiempo, wsKey, getApplicationContext());
                                         }


                                         return true;

            case R.id.menu_buscar:      cardViewPrevision.setVisibility(View.INVISIBLE);
                                        spinnerLocalidades.setVisibility(View.INVISIBLE);
                                        getSupportActionBar().setTitle(R.string.titPantallaTiempo);
                                        efecto_mostrar_circular(cardViewFormulario);

                                        mostrarMenu = false;
                                        invalidateOptionsMenu();
                                        return true;

            case R.id.menu_eliminar:    //Se elimina la localidad de la BD
                                        presentadorTiempo.delLocalidadBD(spinnerLocalidades.getSelectedItem().toString());

                                        //Se quita la localidad borrada del spinner
                                        permitirSeleccion = false;
                                        listLocalidades.remove(spinnerLocalidades.getSelectedItemPosition());
                                        spinnerAdapter.notifyDataSetChanged();

                                        if(spinnerLocalidades.getCount()>0)
                                        {
                                            mostrarMenu = true;
                                            mostrarActualizar = false;
                                            permitirSeleccion = true;

                                            spinnerLocalidades.setSelection(0);//Se selecciona el primer elemento del spinner

                                        }else
                                        {
                                            cardViewPrevision.setVisibility(View.INVISIBLE);
                                            getSupportActionBar().setTitle(R.string.titPantallaTiempo);
                                            efecto_mostrar_circular(cardViewFormulario);
                                            permitirSeleccion = true;

                                            mostrarMenu = false;
                                            spinnerLocalidades.setVisibility(View.INVISIBLE);
                                            invalidateOptionsMenu();
                                        }

                                        return true;

        }

        return false;

    }


    @OnClick(R.id.btnLocalidad)
    public void nuevaLocalidad()
    {

        mostrarDialogConexion();

        if(comprobarConexion())
        {

            localidad = editLocalidad.getText().toString().trim();
            pais = editPais.getText().toString().trim();

            if(localidad.length()>0 && pais.length()>0)
            {

                try
                {
                    //Se comprueba si la localidad existe con Geocoder
                    cod_pais = presentadorTiempo.comprobarLocalidad(this, localidad, pais);

                    if(!cod_pais.equals("null"))
                    {

                        //Se comprueba si la localidad introducida ya existe en la BD
                        if(!presentadorTiempo.compLocalidadBD(localidad, pais))
                        {
                            mostrarDialog(titDialogGuardar, txtDialogGuardar);

                        }else
                        {
                            //Se obtiene la prevision del tiempo del ws
                            presentadorTiempo.getPrevisionTiempo(localidad.toLowerCase()+","+cod_pais, presentadorTiempo, wsKey, getApplicationContext());

                        }

                    }else
                    {
                        mostrarDialogError(titErrorLocalidad, txtErrorLocalidad);
                    }


                } catch (IOException e)
                {
                    mostrarDialogError(titErrorLocalidad, txtErrorLocalidad2);
                }



            }else
            {
                mostrarDialogError(titErrorLocalidad, txtErrorForm);
            }


        }else
        {
            mostrarDialogError(titError, txtError);
        }

    }


    @OnTextChanged(R.id.editLocalidad)
    public void cambioTextoLocalidad(CharSequence text)
    {

        if(text.length()==1 && modEditLocalidad)
        {
            modEditLocalidad = false;
            String texto = text.toString().toUpperCase();
            editLocalidad.setText(texto);
            editLocalidad.setSelection(1);

        }else
        {
            if(text.length()==0)
                modEditLocalidad = true;
        }

    }


    @OnTextChanged(R.id.editPais)
    public void cambioTextoPais(CharSequence text)
    {
        if(text.length()==1 && modEditPais)
        {
            modEditPais = false;
            String texto = text.toString().toUpperCase();
            editPais.setText(texto);
            editPais.setSelection(1);

        }else
        {
            if(text.length()==0)
                modEditPais = true;
        }
    }


    //Se muestra el mensaje de error correspondiente en un Cuadro de Dialogo
    @Override
    public void mostrarDialogError(String titulo, String mensaje)
    {

        if(progressDialog != null)
            progressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);

        builder.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }

        });

        builder.create().show();

    }


    @Override
    //Se muestra los datos de la prevision meteorologica obtenido del WS, este metodo es llamado desde el Presentador
    public void mostrarPrevision(List<DatosPrevision> listPrevision)
    {

        if(progressDialog != null)
            progressDialog.dismiss();


        editLocalidad.setText("");
        editPais.setText("");
        cardViewFormulario.setVisibility(View.INVISIBLE);

        if(listLocalidades.size()>0)
        {
            mostrarMenu = true;
            mostrarActualizar = false;
            getSupportActionBar().setTitle("");
            spinnerLocalidades.setVisibility(View.VISIBLE);

        }else
        {
            mostrarMenu = true;
            mostrarActualizar = true; //con esto se hace que solo se muestre la opcion de actualizar
            spinnerLocalidades.setVisibility(View.INVISIBLE);
        }

        invalidateOptionsMenu();

        //Se muestran los datos del Tiempo
        txtLocalidad.setText(listPrevision.get(0).getLocalidad());
        txtTiempo.setText(listPrevision.get(0).getDescripcion());
        txtFecha.setText(listPrevision.get(0).getDiaSemana()+" "+listPrevision.get(0).getFecha());
        txtTempMax.setText("Temp Max: "+listPrevision.get(0).getTempMax());
        txtTempMin.setText("Temp Min: "+listPrevision.get(0).getTempMin());

        //Se muestra el icono correspondiente del tiempo
        loadIconoTiempo(imgTiempoHoy, listPrevision.get(0).getIcono());

        //Se muestra la prevision de los siguiente dias
        loadIconoTiempo(imgTiempoDia2, listPrevision.get(1).getIcono());
        txtFechaDia2.setText(listPrevision.get(1).getDiaSemana());

        loadIconoTiempo(imgTiempoDia3, listPrevision.get(2).getIcono());
        txtFechaDia3.setText(listPrevision.get(2).getDiaSemana());

        loadIconoTiempo(imgTiempoDia4, listPrevision.get(3).getIcono());
        txtFechaDia4.setText(listPrevision.get(3).getDiaSemana());

        loadIconoTiempo(imgTiempoDia5, listPrevision.get(4).getIcono());
        txtFechaDia5.setText(listPrevision.get(4).getDiaSemana());


        efecto_mostrar_circular(cardViewPrevision);
        permitirSeleccion = true;

    }


    //Se muestra el cuadro de dialogo con el mensaje correspondiente
    private void mostrarDialog(String titulo, String mensaje)
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titulo);
        builder.setMessage(mensaje);
        builder.setCancelable(false);


        builder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                //Se guarda la localidad en la BD
                presentadorTiempo.addLocalidadBD(localidad, pais, cod_pais);

                //Se actualiza el spinner con la nueva localidad guardada en la BD
                listLocalidades.clear();
                listLocalidades.addAll(presentadorTiempo.getLocalidadesBD());

                //Se muestra en el spinner la localidad nueva que se ha añadido (que es el ultimo de la lista)
                if(listLocalidades.size()>0)
                {
                    permitirSeleccion = false;
                    spinnerLocalidades.setSelection(listLocalidades.size()-1);
                    spinnerAdapter.notifyDataSetChanged();
                }

                //Se obtiene la prevision del tiempo del ws
                presentadorTiempo.getPrevisionTiempo(localidad.toLowerCase()+","+cod_pais, presentadorTiempo, wsKey, getApplicationContext());

            }

        });

        builder.setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();

                //Se obtiene la prevision del tiempo del ws
                presentadorTiempo.getPrevisionTiempo(localidad.toLowerCase()+","+cod_pais, presentadorTiempo, wsKey, getApplicationContext());
            }
        });

        builder.create().show();

    }


    //Se muestra en Cuadro de dailogo mientras se obtienen los datos del ws tiempo
    private void mostrarDialogConexion()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(titConectar);
        progressDialog.setMessage(txtConectar);

        progressDialog.show();

    }


    //Se comprueba si hay conexion a Internet
    private boolean comprobarConexion()
    {
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        return (networkInfo != null);
    }


    //Se muestra el icono de tiempo correspendiente a los datos obtenidos del Servidor
    private void loadIconoTiempo(ImageView imgView, String icono)
    {
        switch(icono)
        {
            case("01d"): Picasso.with(this).load(R.drawable.icono_01d).into(imgView);
                break;

            case("01n"): Picasso.with(this).load(R.drawable.icono_01n).into(imgView);
                break;

            case("02d"): Picasso.with(this).load(R.drawable.icono_02d).into(imgView);
                break;

            case("02n"): Picasso.with(this).load(R.drawable.icono_02n).into(imgView);
                break;

            case("03d"): Picasso.with(this).load(R.drawable.icono_03d).into(imgView);
                break;

            case("03n"): Picasso.with(this).load(R.drawable.icono_03n).into(imgView);
                break;

            case("04d"): Picasso.with(this).load(R.drawable.icono_04d).into(imgView);
                break;

            case("04n"): Picasso.with(this).load(R.drawable.icono_04d).into(imgView);
                break;

            case("09d"): Picasso.with(this).load(R.drawable.icono_09d).into(imgView);
                break;

            case("09n"): Picasso.with(this).load(R.drawable.icono_09d).into(imgView);
                break;

            case("10d"): Picasso.with(this).load(R.drawable.icono_10d).into(imgView);
                break;

            case("10n"): Picasso.with(this).load(R.drawable.icono_10n).into(imgView);
                break;

            case("11d"): Picasso.with(this).load(R.drawable.icono_11d).into(imgView);
                break;

            case("11n"): Picasso.with(this).load(R.drawable.icono_11d).into(imgView);
                break;

            case("13d"): Picasso.with(this).load(R.drawable.icono_13d).into(imgView);
                break;

            case("13n"): Picasso.with(this).load(R.drawable.icono_13d).into(imgView);
                break;

            case("50d"): Picasso.with(this).load(R.drawable.icono_50d).into(imgView);
                break;

            case("50n"): Picasso.with(this).load(R.drawable.icono_50d).into(imgView);
                break;
        }
    }


    //Se inicia la animacion (AnimationDrawable) de la imagen de fondo de la pantalla principal
    private void animacion_fondo()
    {
        layoutPantallaTiempo.setBackgroundResource(R.drawable.animacion_pantalla_tiempo);

        layoutPantallaTiempo.post(new Runnable() {
            @Override
            public void run()
            {
                animacionFondo = (AnimationDrawable) layoutPantallaTiempo.getBackground();
                animacionFondo.start();
                animacionFondoIniciada = true;

            }
        });
    }


    private void efecto_mostrar_circular(final View view)
    {

        //Se muestra el efecto circular dentro del LayoutListener
        //Para evitar que se de el error de (Cannot start this animator on a detached view), que ha ocurrido algun usuario
        layoutPantallaTiempo.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom)
            {
                layoutPantallaTiempo.removeOnLayoutChangeListener(this);

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

        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

        if(permitirSeleccion)
        {
            mostrarDialogConexion();

            //Se obtiene la prevision del tiempo del ws
            presentadorTiempo.getPrevisionTiempo(listLocalidades.get(position).toLowerCase(), presentadorTiempo, wsKey, getApplicationContext());
        }

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
