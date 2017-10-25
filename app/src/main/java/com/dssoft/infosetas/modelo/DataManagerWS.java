package com.dssoft.infosetas.modelo;

import android.content.Context;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.PrevisionTiempo;
import com.dssoft.infosetas.presentador.PresentadorMvpTiempo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Angel on 13/10/2017.
 */

public class DataManagerWS
{

    private final String URL_BASE="http://api.openweathermap.org/data/2.5/";
    //http://api.openweathermap.org/data/2.5/forecast/daily?q=madrid,es&mode=json&units=metric&lang=es&cnt=6&APPID=8da3fe404852b2987901d2ccb4d3de97

    public void getPrevision(final PresentadorMvpTiempo presentadorTiempo, final String localidad, String key, final Context context)
    {

        final String mode = "json";
        final String units = "metric";
        final String lang = "es";
        final String cnt = "6";

        //para obtener los datos del WS
        //Primero creamos un objeto del tipo Retrofit y parseamos los datos Json en un objeto
        //mediante GSON usando la clase GsonConverterFactory
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //El siguiente paso es pasar al objeto retrofit la interfaz qye hemos creado con los metodos para conectar con el ws
        RetrofitObject service = retrofit.create(RetrofitObject.class);

        //Se crea la llamada al WS
        Call<PrevisionTiempo> call = service.getTiempo(localidad, mode, units, lang, cnt, key);

        call.enqueue(new Callback<PrevisionTiempo>() {
            @Override
            public void onResponse(Call<PrevisionTiempo> call, Response<PrevisionTiempo> response)
            {

                if(response != null)
                {
                    PrevisionTiempo previsionTiempo = response.body();

                    if(previsionTiempo != null)
                    {

                        String localidadMostrar = localidad.substring(0, localidad.indexOf(","));

                        presentadorTiempo.mostrarPrevision(previsionTiempo, localidadMostrar.toUpperCase());

                    }else
                    {
                        presentadorTiempo.mostrarDialogError(context.getResources().getString(R.string.titErrorDialog_1), context.getResources().getString(R.string.errWSTiempo));
                    }

                }else
                {
                    presentadorTiempo.mostrarDialogError(context.getResources().getString(R.string.titErrorDialog_1), context.getResources().getString(R.string.errWSTiempo));
                }

            }

            @Override
            public void onFailure(Call<PrevisionTiempo> call, Throwable t)
            {

                presentadorTiempo.mostrarDialogError(context.getResources().getString(R.string.titErrorDialog_1), context.getResources().getString(R.string.errWSTiempo));

            }
        });

    }

}
