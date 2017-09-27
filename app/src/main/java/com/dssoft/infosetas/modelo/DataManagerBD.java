package com.dssoft.infosetas.modelo;

import android.content.Context;
import android.database.Cursor;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import com.dssoft.infosetas.pojos.Seta;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 11/09/2017.
 */

public class DataManagerBD
{

    private List<Seta> listSetas = new ArrayList<Seta>();//Esta lista contiene las setas que se pasaran a la activity para mostrarlas en el ListView correspondiente
    private List<Seta> listSetasAux = new ArrayList<Seta>();//Esta lista es una copia de la lista klistSetas y se usa para que en caso de funalizar una busqueda se muestre la lista de setas previa a la busqueda
    private List<Seta> listSetasBD = new ArrayList<Seta>();//Esta lista contiene todas las setas almacenadas en la BD
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


    //Se devuelve las lista con todas las setas de la BD
    public List<Seta> getTodasSetas()
    {

        listSetas.clear();
        listSetasAux.clear();

        listSetas.addAll(listSetasBD);
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

}
