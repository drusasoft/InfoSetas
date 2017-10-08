package com.dssoft.infosetas.presentador;

/**
 * Created by Angel on 12/09/2017.
 */

public interface PresentadorMvpLista extends PresentadorBase
{

    void getSetas(int tipo_seta);
    void buscarSeta(String nombre, int colorNaranja);
    void finBusqueda();
    void addFavorita(String nombreSeta);
    void delFavorita(String nombreSeta);
    boolean isMostrandoFav();

}
