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
            case 0: listSetas = dataManagerBD.getTodasSetas();
                    break;

            case 1: listSetas = dataManagerBD.getSetasComestibles();
                    break;

            case 2: listSetas = dataManagerBD.getSetasSinInteres();
                    break;

            case 3: listSetas = dataManagerBD.getSetasPrecaucion();
                    break;

            case 4: listSetas = dataManagerBD.getSetasToxicas();
                    break;

            case 5: listSetas = dataManagerBD.getSetasMortales();
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


}
