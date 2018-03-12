package com.dssoft.infosetas.presentador;

import com.dssoft.infosetas.pojos.Zona;

import java.util.List;

/**
 * Created by Angel on 14/02/2018.
 */

public interface PresentadorMvpMisZonas extends PresentadorBase
{

    public void getListaZonas();
    public void borrarZona(List<Zona> zonasSeleccionadas);
    public boolean comprobarExistenciaGPS();
    public boolean comprobarGPSHabilitado();
}
