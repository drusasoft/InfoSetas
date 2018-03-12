package com.dssoft.infosetas.presentador;

/**
 * Created by Angel on 06/03/2018.
 */

public interface PresentadorMvpMapaGPS extends PresentadorBase
{
    public void getLocalizacionGPS();
    public void stopLocalizacionGPS();
    public void mostrarLocalizacionGPS(String coordenadas);
    public void iniciarCuentaAtras();
    public void stopCuentaAtras();
}
