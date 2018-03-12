package com.dssoft.infosetas.presentador;

import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaInsertarDatosZonas;
import com.dssoft.infosetas.modelo.DataManagerBD;
import com.dssoft.infosetas.pojos.Zona;

/**
 * Created by Angel on 22/02/2018.
 */

public class PresentadorInsertarDatosZona implements PresentadorMvpInsertarDatosZona
{

    private DataManagerBD dataManagerBD;
    private VistaInsertarDatosZonas vista;

    public PresentadorInsertarDatosZona(DataManagerBD dataManagerBD)
    {
        this.dataManagerBD = dataManagerBD;
    }


    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaInsertarDatosZonas) vista;
    }


    @Override
    public void grabarNuevaZona(Zona nuevaZona)
    {
        boolean resultBd = dataManagerBD.addZona(nuevaZona);

        if(resultBd)
        {
            vista.mostrarMensajeOK();
        }else
        {
            vista.mostrarMensajeKO();
        }
    }

}
