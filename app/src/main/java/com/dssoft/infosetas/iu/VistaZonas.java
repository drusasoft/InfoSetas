package com.dssoft.infosetas.iu;

import com.dssoft.infosetas.pojos.Zona;

import java.util.List;

/**
 * Created by Angel on 13/02/2018.
 */

public interface VistaZonas extends VistaBase
{

    public void mostrarZonas(List<Zona> listZonas);
    public void mostrarMensaje(int mensaje);

}
