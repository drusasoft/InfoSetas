package com.dssoft.infosetas.presentador;

import android.content.Context;
import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaLista;
import com.dssoft.infosetas.modelo.DataManagerBD;
import com.dssoft.infosetas.pojos.Seta;
import java.util.List;

/**
 * Created by Angel on 12/09/2017.
 */

//Capa Presentador utilizado en las pantallas PantallaListaGaleria y PantallaListaSetas
public class PresentadorLista implements PresentadorMvpLista
{

    private VistaLista vista;
    private DataManagerBD dataManagerBD;
    private boolean mostrandoFav = false;//Para sabe si se esta mostrando la lista de setas favoritas

    //El presentador recibe el modelo para poder llamar a sus metodos y recuperar asi los datos necesarios
    public PresentadorLista(DataManagerBD dataManagerBD)
    {
        this.dataManagerBD = dataManagerBD;
    }

    @Override
    // El presentador recibe su vista para poder llamar a sus métodos.
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaLista) vista;
    }


    public List<Seta> obtenerSetasBD(Context context)
    {
        return dataManagerBD.obtenerSetasBD(context);
    }


    @Override
    //Se obtiene la lista con tipo de setas indicado como parametro
    public void getSetas(int tipo_seta)
    {

        List<Seta> listSetas = null;

        switch(tipo_seta)
        {
            case 0: mostrandoFav = false;
                    listSetas = dataManagerBD.getTodasSetas();
                    break;

            case 1: mostrandoFav = true;
                    listSetas = dataManagerBD.getSetasFavoritas();
                    break;

            case 2: mostrandoFav = false;
                    listSetas = dataManagerBD.getSetasComestibles();
                    break;

            case 3: mostrandoFav = false;
                    listSetas = dataManagerBD.getSetasSinInteres();
                    break;

            case 4: mostrandoFav = false;
                    listSetas = dataManagerBD.getSetasPrecaucion();
                    break;

            case 5: mostrandoFav = false;
                    listSetas = dataManagerBD.getSetasToxicas();
                    break;

            case 6: mostrandoFav = false;
                    listSetas = dataManagerBD.getSetasMortales();
                    break;

        }

        //Se llama al metodo de la vista para mostrar la lista de setas elegidas
        vista.mostrarListaSetas(listSetas);

    }


    @Override
    public void buscarSeta(String nombre, int colorNaranja)
    {
        //Se obtiene la lista de setas que contienen la palabra buscada
        List<Seta> listSetas = dataManagerBD.buscarSeta(nombre, colorNaranja);

        //Se llama al metodo de la vista para mostrar la lista de setas buscadas
        vista.mostrarListaSetas(listSetas);
    }


    @Override
    public void finBusqueda()
    {
        //Se obtiene la list con las setas que contienen la palabra buscada
        List<Seta> listSetas = dataManagerBD.finBusqueda();

        //Se muestra dicha lista
        vista.mostrarListaSetas(listSetas);

    }


    @Override
    //Se añade la seta a la lista de favoritas
    public void addFavorita(String nombreSeta)
    {
        //Se añade la seta a la lista de favoritas
        List<Seta> listSetas = dataManagerBD.addFavorita(nombreSeta);

        //Se muestra dicha lista
        vista.mostrarListaSetas(listSetas);

    }


    @Override
    //Se elimian la seta de la lista de favoritas
    public void delFavorita(String nombreSeta)
    {
        //Se añade la seta a la lista de favoritas
        List<Seta> listSetas = dataManagerBD.delFavorita(nombreSeta, mostrandoFav);

        //Se muestra dicha lista
        vista.mostrarListaSetas(listSetas);

    }

    @Override
    //Para sabe si se ha elejodo la opcion del spinner de mostrar favoritas
    public boolean isMostrandoFav()
    {
        return mostrandoFav;
    }


}
