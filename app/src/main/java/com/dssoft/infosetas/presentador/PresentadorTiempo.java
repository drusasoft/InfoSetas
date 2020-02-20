package com.dssoft.infosetas.presentador;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import androidx.core.content.ContextCompat;

import com.dssoft.infosetas.R;
import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaTiempo;
import com.dssoft.infosetas.modelo.DataManagerBD;
import com.dssoft.infosetas.modelo.DataManagerWS;
import com.dssoft.infosetas.pojos.DatosPrevision;
import com.dssoft.infosetas.pojos.PrevisionTiempo;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Angel on 09/10/2017.
 */

public class PresentadorTiempo implements PresentadorMvpTiempo
{

    private VistaTiempo vista;
    private DataManagerBD dataManagerBD;
    private DataManagerWS dataManagerWS;
    private Context context;

    //El presentador recibe el modelo para poder llamar a sus metodos y recuperar asi los datos necesarios
    public PresentadorTiempo(DataManagerBD dataManagerBD, DataManagerWS dataManagerWS, Context context)
    {
        this.dataManagerBD = dataManagerBD;
        this.dataManagerWS = dataManagerWS;
        this.context = context;
    }

    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaTiempo) vista;
    }



    @Override
    //Se comprueba con geocoder si el municipio y pais intoducido existen
    public String comprobarLocalidad(Context context, String localidadad, String pais) throws IOException {

        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

        List<Address> listDireccion = geocoder.getFromLocationName(localidadad+"\n"+pais,1);

        if(listDireccion.size() > 0)
        {
            //Se obtiene el codigo ISO del pais
            return listDireccion.get(0).getCountryCode().toLowerCase();

        }else
        {
            return "null";
        }

    }


    @Override
    //Se comprueba si la localidad pasada como parametro ya existe o no en la BD
    public boolean compLocalidadBD(String localidad, String pais)
    {
        return dataManagerBD.compLocalidad(localidad, pais);
    }


    @Override
    //Se obtiene la lista de localidades guardadas en la BD
    public List<String> getLocalidadesBD()
    {
        return dataManagerBD.getLocalidades();
    }


    @Override
    //Se guarda la localidad en la BD
    public void addLocalidadBD(String localidad, String pais, String cod_pais)
    {
        dataManagerBD.addLocalidad(localidad, pais, cod_pais);
    }


    @Override
    //Se borra la localidad de la BD
    public void delLocalidadBD(String localidad)
    {
        StringTokenizer tokenizer = new StringTokenizer(localidad,",");
        String municipio = tokenizer.nextToken().trim();
        String cod_pais = tokenizer.nextToken().trim();

        //Se elimina la localidad de la BD
        dataManagerBD.delLocalidad(municipio, cod_pais);

    }

    @Override
    //Se llama al metodo que se conecta al WS del Tiempo para obtener la prevision
    public void getPrevisionTiempo(String localidad, PresentadorMvpTiempo presentadorMvpTiempo, String wsKey, String idioma, Context context)
    {
        dataManagerWS.getPrevision(presentadorMvpTiempo, localidad, wsKey, idioma, context);
    }


    @Override
    //Se llama al metodo que muestea el cuadro de dialogo con el error correspondiente
    public void mostrarDialogError(String titulo, String mensaje)
    {
        vista.mostrarDialogError(titulo, mensaje);
    }


    @Override
    //Se llama al metodo que muestra los datos Meteorologicos
    public void mostrarPrevision(PrevisionTiempo previsionTiempo, String localidad)
    {
        //Guardamos los datos obtenidos de prevision del WS en una lista con el formato que quiero yo
        List<DatosPrevision> listPrevision = new ArrayList<DatosPrevision>();
        DatosPrevision datosPrevision = new DatosPrevision();
        datosPrevision.setLocalidad(localidad);

        for(int i=0; i< previsionTiempo.getList().size();i++)
        {

            long milisegundundos = previsionTiempo.getList().get(i).getDt() * 1000;
            Date fecha = new Date(milisegundundos);
            String fechaNorm = new SimpleDateFormat("dd-MM-yyyy").format(fecha);

            Calendar cal = Calendar.getInstance();
            cal.setTime(fecha);
            int numDia = cal.get(Calendar.DAY_OF_WEEK);//devuelve del 1-7 desde el domingo al sabado

            datosPrevision.setFecha(fechaNorm);
            datosPrevision.setDiaSemana(getDiaSemana(numDia));
            datosPrevision.setDescripcion(previsionTiempo.getList().get(i).getWeather().get(0).getDescription());
            datosPrevision.setIcono(previsionTiempo.getList().get(i).getWeather().get(0).getIcon());
            datosPrevision.setTempMax(previsionTiempo.getList().get(i).getTemp().getMax());
            datosPrevision.setTempMin(previsionTiempo.getList().get(i).getTemp().getMin());

            listPrevision.add(datosPrevision);
            datosPrevision = new DatosPrevision();

        }

        vista.mostrarPrevision(listPrevision);
    }


    //Se obtiene el dia de la semana en funcion de la fecha
    private String getDiaSemana(int numDia)
    {
        switch(numDia)
        {
            case 1: return context.getString(R.string.diaSemana_1);

            case 2: return context.getString(R.string.diaSemana_2);

            case 3: return context.getString(R.string.diaSemana_3);

            case 4: return context.getString(R.string.diaSemana_4);

            case 5: return context.getString(R.string.diaSemana_5);

            case 6: return context.getString(R.string.diaSemana_6);

            case 7: return context.getString(R.string.diaSemana_7);
        }

        return "";
    }



}
