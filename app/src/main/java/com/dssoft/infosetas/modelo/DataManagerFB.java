package com.dssoft.infosetas.modelo;

import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.SetaFireBase;
import com.dssoft.infosetas.presentador.PresentadorMvpDetalles;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Angel on 14/09/2017.
 */

//Clase de la capa modelo que se encarga de obtener los datos almacenados en el servidor FireBase
public class DataManagerFB
{

    private FirebaseDatabase database;

    public DataManagerFB()
    {
        database = FirebaseDatabase.getInstance();
    }


    //Se obtiene del Servidor FireBase los datos de la seta cuyo nombre hemos recibido como parametro
    public SetaFireBase getDetallesSeta(String nombreSeta, final PresentadorMvpDetalles presentadorDetalles)
    {

        final DatabaseReference databaseReference = database.getReference("setas_esp").child(nombreSeta);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //Se desregistra el listener
                databaseReference.removeEventListener(this);

                //Obtenemos los datos de la seta Firebase y lo guardamos en una variable
                SetaFireBase setaDetalles = dataSnapshot.getValue(SetaFireBase.class);

                //Se llama los metodos del presentador que se encargan de mostrar los datos o el error en la capa vista
                if(setaDetalles != null)
                {
                    presentadorDetalles.mostrarDatosSeta(setaDetalles);
                }else
                {
                    presentadorDetalles.mostrarDialogError(R.string.titErrorDialog_1, R.string.txtErrorDialog_1);
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                presentadorDetalles.mostrarDialogError(R.string.titErrorDialog_1, R.string.txtErrorDialog_1);
            }

        });

        return null;

    }

}
