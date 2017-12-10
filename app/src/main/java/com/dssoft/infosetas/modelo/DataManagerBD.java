package com.dssoft.infosetas.modelo;

import android.content.Context;
import android.database.Cursor;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.dssoft.infosetas.pojos.Localidad;
import com.dssoft.infosetas.pojos.Seta;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 11/09/2017.
 */

public class DataManagerBD
{

    private List<Seta> listSetas = new ArrayList<Seta>();//Esta lista contiene las setas que se pasaran a la activity para mostrarlas en el ListView correspondiente
    private List<Seta> listSetasAux = new ArrayList<Seta>();//Esta lista es una copia de la lista listSetas y se usa para que en caso de funalizar una busqueda se muestre la lista de setas previa a la busqueda
    private List<Seta> listSetasBD = new ArrayList<Seta>();//Esta lista contiene todas las setas almacenadas en la BD
    private List<String> listFavoritas = new ArrayList<>();//Lista con los nombre de las setas favoritas guardadas en la BD
    private List<Localidad> listLocalidades = new ArrayList<Localidad>();//Lista que contiene todas las Localidades almacenadas en la BD
    private BDAdapter mBDAdapter;


    public DataManagerBD(BDAdapter bdAdapter)
    {
        mBDAdapter = bdAdapter;
    }

    private void abrirBDLectura()
    {
        mBDAdapter.abrirBD();
    }

    private void cerrarBD()
    {
        mBDAdapter.cerrarBD();
    }


    //Se obtienen todas las Localidades de la BD y se guardan en una lista en memoria
    public void obtenerLocalidadesBD()
    {

        abrirBDLectura();

        listLocalidades.clear();

        Cursor cursor = mBDAdapter.getLocalidades();

        if(cursor.getCount() > 0)
        {

            cursor.moveToFirst();

            do
            {

                Localidad localidad = new Localidad();
                localidad.setNombre(cursor.getString(0));
                localidad.setPais(cursor.getString(1));
                localidad.setCodPais(cursor.getString(2));

                listLocalidades.add(localidad);

            }while (cursor.moveToNext());

        }

        cerrarBD();

    }

    //Se obtinen las setas Favoritas de la BD y se guardan sus nombre en una lista
    private void obtenerfavoritasBD()
    {
        abrirBDLectura();

        listFavoritas.clear();
        Cursor cursor = mBDAdapter.obtenerFavoritasBD();

        if(cursor.getCount()>0)
        {
            cursor.moveToFirst();

            do
            {
                listFavoritas.add(cursor.getString(0));

            }while(cursor.moveToNext());

        }

        cerrarBD();

    }

    //Se verifica que setas obtenidas de la BD estan tambien en la lista de favoritas y se cambia su campo para indicarlo
    private void checkFavoritas()
    {

        for(Seta seta:listSetasBD)
        {

            if(listFavoritas.contains(seta.getNombre()))
                seta.setFavorita(true);

        }

    }

    private void ordenarLista()
    {

        Seta setamenor;

        for(int i=0;i<listSetasBD.size();i++)
        {
            setamenor = listSetasBD.get(i);

            for(int j=i+1; j<listSetasBD.size();j++)
            {

                if(listSetasBD.get(j).getNombre_ordenar().compareTo(setamenor.getNombre_ordenar())<0)
                {

                    Seta aux = listSetasBD.get(j);
                    listSetasBD.set(i, aux);
                    listSetasBD.set(j, setamenor);
                    setamenor = aux;

                }

            }

        }

        listSetas.addAll(listSetasBD);
        listSetasAux.addAll(listSetas);

    }


    //Se obtienen toda las setas de la BD y se almacenan en una Lista en memoria
    public List<Seta> obtenerSetasBD(Context context)
    {

        listSetas.clear();
        listSetasBD.clear();
        listSetasAux.clear();

        abrirBDLectura();

        Cursor cursor = mBDAdapter.obtenerRegistros();
        cursor.moveToFirst();

        do
        {
            String nombre = context.getResources().getString(cursor.getInt(1));
            String nombre_comun = context.getResources().getString(cursor.getInt(2));
            String nombre_ordenar = context.getResources().getString(cursor.getInt(3));
            String comestible = context.getResources().getString(cursor.getInt(4));
            String fotos = cursor.getString(5);
            int foto_list = cursor.getInt(6);

            Seta seta = new Seta(nombre, nombre_comun, nombre_ordenar, comestible, fotos, foto_list);
            listSetasBD.add(seta);

        }while(cursor.moveToNext());

        cerrarBD();

        ordenarLista();

        obtenerfavoritasBD();

        checkFavoritas();

        return listSetas;

    }


    //Se busca el nombre en la lista de setas que se estan mostrando y se añaden esas setas en otra lista
    //y se devuelve para que se muestren en el ListView
    public List<Seta> buscarSeta(String nombre, int colorNaranja)
    {

        //si el texto escrito esta contenido en el nombre y nombre_normal de cada seta
        //y si es asi se añade dicha seta en la lista de busqueda que despues se muestra en el Listview
       listSetas.clear();

        for(Seta seta: listSetasAux)
        {
            //Si el texto escrito aparece en el nombre de la seta
            if(seta.getNombre_ordenar().contains(nombre))
            {

                seta.setSpanableNombreComun(null);

                //Se obtiene la posicion del texto encontrado
                int posicion = seta.getNombre_ordenar().indexOf(nombre);

                //Se crea un objeto spanable con el texto del titulo y dentro de el resaltamos en color naranja
                //la palabra buscada que aparece dentro del texto titulo
                Spannable spannableText = new SpannableString(seta.getNombre());
                spannableText.setSpan(new ForegroundColorSpan(colorNaranja), posicion, posicion + nombre.length(), 0);

                seta.setSpanableNombre(spannableText);

                listSetas.add(seta);

            }else
            {

                //Si el texto escrito aparece en el nombre comun de la seta
                if(seta.getNombre_comun().toLowerCase().contains(nombre))
                {

                    seta.setSpanableNombre(null);

                    //Se obtiene la posicion del texto encontrado
                    int posicion = seta.getNombre_comun().toLowerCase().indexOf(nombre);

                    //Se crea un objeto spanable con el texto del titulo y dentro de el resaltamos en color naranja
                    //la palabra buscada que aparece dentro del texto titulo
                    Spannable spannableText = new SpannableString(seta.getNombre_comun());
                    spannableText.setSpan(new ForegroundColorSpan(colorNaranja), posicion, posicion + nombre.length(), 0);

                    seta.setSpanableNombreComun(spannableText);

                    listSetas.add(seta);

                }else
                {
                    //Si esta seta no contiene la palabra buscada
                    //Se pone a null los campos setSpanableNombreComun y SpanableNombreComun por si se han modificado en alguna busqueda anterior
                    seta.setSpanableNombre(null);
                    seta.setSpanableNombreComun(null);
                }

            }

        }

        return listSetas;

    }


    //Se pone a null los campos setSpanableNombreComun y SpanableNombreComun en todas las setas por
    // si se han modificado en alguna busqueda anterior
    public List<Seta> finBusqueda()
    {

        for(Seta seta:listSetasBD)
        {
            seta.setSpanableNombre(null);
            seta.setSpanableNombreComun(null);
        }

        //Se vuelve cargar la lista de setas que se estaban mostrnado previo a la busqueda
        listSetas.clear();
        listSetas.addAll(listSetasAux);

        return listSetas;

    }


    //Se añade una seta en la lista de favoritas
    public List<Seta> addFavorita(String nombreSeta)
    {

        //Se añade el nombre de la seta a la lista de favoritas
        listFavoritas.add(nombreSeta);

        //Se indica que es favorita en el resto de lista
        for(Seta seta:listSetasBD)
        {
            if(seta.getNombre().equals(nombreSeta))
            {
                seta.setFavorita(true);
                break;
            }
        }

        //Se añade el nombre de la seta favorita de la BD
        mBDAdapter.addFavoritaBD(nombreSeta);

        return listSetas;

    }


    //Se quita una seta de la lista de Favoritas
    public List<Seta> delFavorita(String nombreSeta, boolean mostrandoFav)
    {

        Seta setaAux=null;

        //Se quita el nombre de la seta a la lista de favoritas
        listFavoritas.remove(nombreSeta);

        //Se indica que ya no es favorita en el resto de lista
        for(Seta seta:listSetasBD)
        {
            if(seta.getNombre().equals(nombreSeta))
            {
                seta.setFavorita(false);
                setaAux = seta;
                break;
            }
        }

        //Si se esta mostrand0 en estos momentos la lista de setas favoritas, entonces de deja de mostrar dicha seta
        if(mostrandoFav)
        {
            listSetas.remove(setaAux);
            listSetasAux.remove(setaAux);
        }

        //Se elimina el nombre de la seta favorita de la BD
        mBDAdapter.delFavoritaBD(nombreSeta);

        return listSetas;

    }


    //Se devuelve las lista con todas las setas de la BD
    public List<Seta> getTodasSetas()
    {

        listSetas.clear();
        listSetasAux.clear();

        listSetas.addAll(listSetasBD);
        listSetasAux.addAll(listSetas);

        return listSetas;
    }


    //Se devuelve las lista con las setas favoritas
    public List<Seta> getSetasFavoritas()
    {
        listSetas.clear();
        listSetasAux.clear();

        for(Seta seta:listSetasBD)
        {

            if(listFavoritas.contains(seta.getNombre()))
                listSetas.add(seta);

        }

        listSetasAux.addAll(listSetas);

        return listSetas;

    }


    //Se devuelve las lista con las setas comestibles
    public List<Seta> getSetasComestibles()
    {
        listSetas.clear();
        listSetasAux.clear();

        for(Seta seta:listSetasBD)
        {
            if(seta.getComestible().equals("comestible"))
                listSetas.add(seta);
        }

        listSetasAux.addAll(listSetas);

        return listSetas;

    }


    //Se devuelve las lista con las setas que hay que consumir con precaucion
    public List<Seta> getSetasPrecaucion()
    {
        listSetas.clear();
        listSetasAux.clear();

        for(Seta seta:listSetasBD)
        {
            if(seta.getComestible().equals("precaucion"))
                listSetas.add(seta);
        }

        listSetasAux.addAll(listSetas);

        return listSetas;

    }


    //Se devuelve las lista con todas las setas Toxicas
    public List<Seta> getSetasToxicas()
    {

        listSetas.clear();
        listSetasAux.clear();

        for(Seta seta:listSetasBD)
        {
            if(seta.getComestible().equals("toxica"))
                listSetas.add(seta);
        }

        listSetasAux.addAll(listSetas);

        return listSetas;

    }


    //Se devuelve las lista con todas las setas Mortales
    public List<Seta> getSetasMortales()
    {
        listSetas.clear();
        listSetasAux.clear();

        for(Seta seta:listSetasBD)
        {
            if(seta.getComestible().equals("mortal"))
                listSetas.add(seta);
        }

        listSetasAux.addAll(listSetas);

        return listSetas;
    }


    //Se devuelve las lista con todas las setas Sin Interes culinario
    public List<Seta> getSetasSinInteres()
    {
        listSetas.clear();
        listSetasAux.clear();

        for(Seta seta:listSetasBD)
        {
            if(seta.getComestible().equals("sin_interes"))
                listSetas.add(seta);
        }

        listSetasAux.addAll(listSetas);

        return listSetas;
    }


    //Se crea la lista de Localidades (a partir de la lista obtenida de la BD) que se van a mostrar en el Spinner
    public List<String> getLocalidades()
    {
        obtenerLocalidadesBD();

        List<String> listMunicipio = new ArrayList<String>();

        for(Localidad localidad: listLocalidades)
        {
            String municipio = localidad.getNombre()+", "+localidad.getCodPais();
            listMunicipio.add(municipio);
        }

        return listMunicipio;
    }


    //Se añade una Localidad en la BD
    public void addLocalidad(String localidad, String pais, String cod_pais)
    {
        //Se guarda la Localidad en la BD
        mBDAdapter.addLocalidad(localidad, pais, cod_pais);

        //Se añade la localidad a la lista de localidades
        Localidad local = new Localidad();
        local.setNombre(localidad);
        local.setPais(pais);
        local.setCodPais(cod_pais);

        listLocalidades.add(local);

    }


    //Se elimina una Localidad de la BD
    public void delLocalidad(String localidad, String cod_pais)
    {

        //Se elimina la localidad de la BD
        mBDAdapter.delLocalidad(localidad, cod_pais);

        //Se elimina la localidad de la lista de Localidades
        Localidad localidadBorrar = null;
        for(Localidad local: listLocalidades)
        {
            if(local.getNombre().equals(localidad) && local.getCodPais().equals(cod_pais))
            {
                localidadBorrar = local;
                break;
            }
        }

        if(localidadBorrar != null)
            listLocalidades.remove(localidadBorrar);

    }


    //Se comprueba si la Localidad pasada como parametro ya exite en la BD
    public boolean compLocalidad(String nombre, String pais)
    {

        //Se comprueba si la localidad existe en la lista creada con los datos de la BD
        for(Localidad localidad:listLocalidades)
        {

            if(localidad.getNombre().equals(nombre) && localidad.getPais().equals(pais))
            {
                return true;
            }

        }

        return false;
    }

}
