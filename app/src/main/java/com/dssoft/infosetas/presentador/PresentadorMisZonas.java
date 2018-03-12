package com.dssoft.infosetas.presentador;

import com.dssoft.infosetas.R;
import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaZonas;
import com.dssoft.infosetas.modelo.DataManagerBD;
import com.dssoft.infosetas.modelo.DataManagerGPS;
import com.dssoft.infosetas.pojos.Zona;
import java.util.List;

/**
 * Created by Angel on 14/02/2018.
 */

public class PresentadorMisZonas implements PresentadorMvpMisZonas
{
    private VistaZonas vista;
    private DataManagerBD dataManagerBD;
    private DataManagerGPS dataManagerGPS;


    public PresentadorMisZonas(DataManagerBD dataManagerBD)
    {
        this.dataManagerBD = dataManagerBD;
    }


    public PresentadorMisZonas(DataManagerBD dataManagerBD, DataManagerGPS dataManagerGPS)
    {
        this.dataManagerBD = dataManagerBD;
        this.dataManagerGPS = dataManagerGPS;
    }


    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaZonas) vista;
    }


    @Override
    public void getListaZonas()
    {
        List<Zona> listZonas = dataManagerBD.getListaZonas();

        vista.mostrarZonas(listZonas);
    }


    @Override
    public void borrarZona(List<Zona> listZonasSeleccionadas)
    {
        boolean result = dataManagerBD.delZona(listZonasSeleccionadas);

        if(result)
        {
           getListaZonas();

        }else
        {
            vista.mostrarMensaje(R.string.txtErrorBorrarZonas);
        }

    }


    @Override
    //Se verifica si el disposiivo dispone de GPS
    public boolean comprobarExistenciaGPS()
    {
        return dataManagerGPS.existeGPS();
    }


    @Override
    //Se verifica si el GPS esta habilitado
    public boolean comprobarGPSHabilitado() {

        return dataManagerGPS.habilitadoGPS();
    }


}
