package com.dssoft.infosetas.iu;

import com.dssoft.infosetas.pojos.DatosPrevision;

import java.util.List;

/**
 * Created by Angel on 09/10/2017.
 */

public interface VistaTiempo extends VistaBase
{

    void mostrarDialogError(String titulo, String mensaje);
    void mostrarPrevision(List<DatosPrevision> listPrevision);

}
