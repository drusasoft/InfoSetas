package com.dssoft.infosetas.presentador;
import com.dssoft.infosetas.iu.VistaBase;
import com.dssoft.infosetas.iu.VistaDetalles;
import com.dssoft.infosetas.modelo.DataManagerFB;
import com.dssoft.infosetas.pojos.SetaFireBase;

/**
 * Created by Angel on 14/09/2017.
 */

//Presentado usado en la Pantalla
public class PresentadorDetalles implements PresentadorMvpDetalles
{

    private DataManagerFB dataManagerFB;
    private VistaDetalles vista;

    public PresentadorDetalles(DataManagerFB dataManagerFB)
    {
        this.dataManagerFB = dataManagerFB;
    }


    @Override
    // El presentador recibe su vista para poder llamar a sus métodos.
    public void setVista(VistaBase vista)
    {
        this.vista = (VistaDetalles) vista;
    }


    @Override
    public void getDetallesSeta(String nombre, PresentadorMvpDetalles presentadorMvpDetalles)
    {
        //Se llama al metodo de la capa Modelo que se conecta al servidor FireBase y se obtiene los detalles de las setas
        dataManagerFB.getDetallesSeta(nombre, presentadorMvpDetalles);

    }

    @Override
    public void mostrarDatosSeta(SetaFireBase seta)
    {
        //Se llama al metodo para mostrar los datos de la seta
        vista.mostrarDatosSetas(seta);
    }

    @Override
    public void mostrarDialogError(int titError, int txtError)
    {
        //Se llama al metodo que muestra el dialogo de error
        vista.mostrarDialogError(titError, txtError);
    }


}
