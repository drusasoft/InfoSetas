package com.dssoft.infosetas.modelo;

import com.dssoft.infosetas.pojos.PrevisionTiempo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Angel on 15/10/2017.
 */

public interface RetrofitObject
{

    @GET("forecast/daily")
    Call<PrevisionTiempo> getTiempo(@Query("q") String localidad, @Query("mode") String modo,
                                    @Query("units") String unidades, @Query("lang") String lenguaje,
                                    @Query("cnt") String cnt, @Query("APPID") String key);
}
