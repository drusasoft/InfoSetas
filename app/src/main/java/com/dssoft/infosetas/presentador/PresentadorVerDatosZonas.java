package com.dssoft.infosetas.presentador;

import com.dssoft.infosetas.R;
import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaVerDatosZonas;
import com.dssoft.infosetas.modelo.DataManagerBD;

/**
 * Created by Angel on 02/03/2018.
 */

public class PresentadorVerDatosZonas implements PresentadorMvpVerDatosZonas
{

    private DataManagerBD dataManagerBD;
    private VistaVerDatosZonas vista;

    public PresentadorVerDatosZonas(DataManagerBD dataManagerBD)
    {
        this.dataManagerBD = dataManagerBD;
    }


    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaVerDatosZonas) vista;
    }


    @Override
    public void modificarZona(int id_zona, String nombre, String descripcion)
    {
        boolean result = dataManagerBD.updateZona(id_zona, nombre, descripcion);

        if(result)
        {
           vista.mostrarMensaje(R.string.txtCambiosOK);
        }else
        {
            vista.mostrarMensaje(R.string.txtErrorCambios);
        }
    }

}
