package com.dssoft.infosetas.modelo;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import com.dssoft.infosetas.presentador.PresentadorBase;
import com.dssoft.infosetas.presentador.PresentadorMapaGPS;
import java.util.List;

/**
 * Created by Angel on 04/03/2018.
 */

public class DataManagerGPS
{
    private LocationManager locationManager;
    private LocalizacionListener localizacionListener;
    private Context context;
    private PresentadorMapaGPS presentadorMapaGPS;

    public DataManagerGPS(Context context)
    {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        localizacionListener = new LocalizacionListener();
        this.context = context;
    }


    //Se asocia el Presentador de la pantalla MapaGps para poder llamar a sus metodos desde esta clase
    public void setPresentador(PresentadorBase presentador)
    {
        presentadorMapaGPS = (PresentadorMapaGPS) presentador;
    }


    //Se compryeba si el dispositivo dispone de GPS
    public boolean existeGPS()
    {
        List<String> listProveedores = locationManager.getAllProviders();

        if(listProveedores.contains(locationManager.GPS_PROVIDER))
            return  true;

        return false;
    }


    //Se comprueba si el GPS esta habilitado
    public boolean habilitadoGPS()
    {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            return true;

        return false;

    }


    //Se obtiene las coordenadas del GPS
    public void getPosicion()
    {
        //Se requiere comprobar si se tiene permiso del usuario
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0, localizacionListener);

    }


    //Se desregistra el Listener que obtiene la Posicion del GPS
    public void stopPosicion()
    {
        locationManager.removeUpdates(localizacionListener);
    }


    //**********************************************************************************************
        //Listener que recibe la localizacion enviada por el GPS
    //**********************************************************************************************
    private class LocalizacionListener implements LocationListener
    {

        @Override
        public void onLocationChanged(Location location)
        {
            String latitud = String.valueOf(location.getLatitude());
            String longitud = String.valueOf(location.getLongitude());

            //Se muestra la posicion obtenida del GPS en el Mapa
            presentadorMapaGPS.mostrarLocalizacionGPS(latitud+":"+longitud);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    }


}
