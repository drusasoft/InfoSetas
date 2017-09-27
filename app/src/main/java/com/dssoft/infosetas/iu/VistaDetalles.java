package com.dssoft.infosetas.iu;

import com.dssoft.infosetas.pojos.SetaFireBase;

/**
 * Created by Angel on 15/09/2017.
 */

public interface VistaDetalles extends VistaBase
{

    void mostrarDatosSetas(SetaFireBase seta);
    void mostrarDialogError(int titError, int txtError);

}
