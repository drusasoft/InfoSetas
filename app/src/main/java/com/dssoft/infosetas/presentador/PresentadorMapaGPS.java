package com.dssoft.infosetas.presentador;

import android.os.CountDownTimer;
import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaMapaGPS;
import com.dssoft.infosetas.modelo.DataManagerGPS;

/**
 * Created by Angel on 06/03/2018.
 */

public class PresentadorMapaGPS implements PresentadorMvpMapaGPS
{

    private VistaMapaGPS vista;
    private DataManagerGPS dataManagerGPS;
    private CountDownTimer countDownTimer;
    private boolean mostrarNuevaUbicacion=true;


    public PresentadorMapaGPS(DataManagerGPS dataManagerGPS)
    {
        this.dataManagerGPS = dataManagerGPS;
        dataManagerGPS.setPresentador(this);
    }


    @Override
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaMapaGPS) vista;
    }


    @Override
    public void getLocalizacionGPS()
    {
        dataManagerGPS.getPosicion();
    }


    @Override
    public void stopLocalizacionGPS()
    {
        dataManagerGPS.stopPosicion();//se detiene la obtencion de la posicion del GPS
    }


    @Override
    public void mostrarLocalizacionGPS(String coordenadas)
    {
        if(mostrarNuevaUbicacion)
        {
            mostrarNuevaUbicacion = false;
            vista.mostrarLocalizacionGPS(coordenadas);
        }

    }


    @Override
    //Se inicia una cuenta Atras y al final de la misma se muestra la nueva ubicacion proporcionada por el GPS
    public void iniciarCuentaAtras()
    {

        countDownTimer = new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                String cuenta = String.valueOf(millisUntilFinished/1000);
                vista.mostrarCuentaAtras(cuenta);
            }

            @Override
            public void onFinish()
            {
                vista.mostrarCuentaAtras("0");
                mostrarNuevaUbicacion = true;
            }

        }.start();

    }


    @Override
    //Se para la Cuenta Atras
    public void stopCuentaAtras()
    {
        if(countDownTimer != null)
            countDownTimer.cancel();
    }

}
