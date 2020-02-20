package com.dssoft.infosetas.presentador;

import android.content.Context;

import com.dssoft.infosetas.pojos.Localidad;
import com.dssoft.infosetas.pojos.PrevisionTiempo;

import java.io.IOException;
import java.util.List;

/**
 * Created by Angel on 09/10/2017.
 */

public interface PresentadorMvpTiempo extends PresentadorBase
{

    String comprobarLocalidad(Context context, String localidadad, String pais) throws IOException;
    boolean compLocalidadBD(String localidad, String pais);
    List<String> getLocalidadesBD();
    void addLocalidadBD(String localidad, String pais, String cod_pais);
    void delLocalidadBD(String localidad);
    void getPrevisionTiempo(String localidad, PresentadorMvpTiempo presentadorMvpTiempo, String wsKey, String idioma, Context context);
    void mostrarDialogError(String titulo, String mensaje);
    void mostrarPrevision(PrevisionTiempo previsionTiempo, String localidad);
}
