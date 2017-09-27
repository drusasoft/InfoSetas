package com.dssoft.infosetas.presentador;

import com.dssoft.infosetas.pojos.SetaFireBase;

/**
 * Created by Angel on 14/09/2017.
 */

public interface PresentadorMvpDetalles extends PresentadorBase
{

    void getDetallesSeta(String nombre, PresentadorMvpDetalles presentadorMvpDetalles);
    void mostrarDatosSeta(SetaFireBase seta);
    void mostrarDialogError(int titError, int txtError);

}
