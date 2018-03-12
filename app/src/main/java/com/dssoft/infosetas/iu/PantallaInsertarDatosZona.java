package com.dssoft.infosetas.iu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.pojos.Zona;
import com.dssoft.infosetas.presentador.PresentadorInsertarDatosZona;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Angel on 20/02/2018.
 */

public class PantallaInsertarDatosZona extends AppCompatActivity implements VistaInsertarDatosZonas
{

    @BindView(R.id.toolBar_pantalla_insertar_datos_zona) Toolbar toolbar;
    @BindView(R.id.layout_insertar_datos_zona) RelativeLayout layoutDatosZona;
    @BindView(R.id.editNombreZona) EditText editNombreZona;
    @BindView(R.id.editDescripcionZona) EditText editDescripcionZona;

    private PresentadorInsertarDatosZona presentadorInsertarDatosZona;
    private String latitud, longitud, nombreZona, descripcionZona;
    private boolean nombreMayusculas, descripcionMayusculas;

    private final int NUEVA_ZONA = 101;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_insertar_datos_zona);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        latitud = getIntent().getStringExtra("latitud");
        longitud = getIntent().getStringExtra("longitud");

        InfoSetas infoSetas = (InfoSetas) getApplicationContext();
        presentadorInsertarDatosZona = new PresentadorInsertarDatosZona(infoSetas.getDataManagerBD());
        presentadorInsertarDatosZona.setVista(this);

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_insertar_datos_zona);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home: Intent intent = getIntent();
                                    setResult(RESULT_CANCELED, intent);
                                    onBackPressed();
                                    return true;
        }

        return false;
    }


    @OnClick(R.id.btnSaveZona)
    //Se insertan los datos de la nueva en la BD
    public void guardarDatosZona()
    {
        nombreZona = editNombreZona.getText().toString().trim();
        descripcionZona = editDescripcionZona.getText().toString().trim();

        if(nombreZona.length()>0)
        {
            Zona zona = new Zona();
            zona.setNombre(nombreZona);
            zona.setDescripcion(descripcionZona);
            zona.setLatitud(latitud);
            zona.setLongitud(longitud);

            presentadorInsertarDatosZona.grabarNuevaZona(zona);

        }else
        {
            Snackbar.make(layoutDatosZona, R.string.txtNombreVacio, Snackbar.LENGTH_LONG).show();
        }

    }


    @OnTextChanged(R.id.editNombreZona)
    //Se pone la primera letra en mayusculas
    public void editNombreMayusculas(CharSequence text)
    {

        if(text.length()==1 && !nombreMayusculas)
        {
            nombreMayusculas = true;
            String texto = text.toString();
            editNombreZona.setText(texto.toUpperCase());
            editNombreZona.setSelection(1);
        }else
        {
            if(text.length() == 0)
                nombreMayusculas = false;
        }

    }


    @OnTextChanged(R.id.editDescripcionZona)
    //Se pone la primera letra en mayusculas
    public void editDescripcionMayusculas(CharSequence text)
    {

        if(text.length()==1 && !descripcionMayusculas)
        {
            descripcionMayusculas = true;
            String texto = text.toString();
            editDescripcionZona.setText(texto.toUpperCase());
            editDescripcionZona.setSelection(1);
        }else
        {
            if(text.length() == 0)
                descripcionMayusculas = false;
        }

    }


    @Override
    //Se muestra un dialogo informando que los datos se han guardado correctamente en la BD
    public void mostrarMensajeOK()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titMensajeOK);
        builder.setMessage(R.string.txtMensajeOK);

        builder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                Intent intent = getIntent();
                intent.putExtra("nombreZona", nombreZona);
                intent.putExtra("descripcionZona", descripcionZona);
                intent.putExtra("latitud", latitud);
                intent.putExtra("longitud", longitud);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        builder.create().show();

    }


    @Override
    //Se muestra un dialogo informando que los datos no se han guardado correctamente en la BD
    public void mostrarMensajeKO()
    {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.titMensajeKO);
        builder.setMessage(R.string.txtMensajeKO);

        builder.setPositiveButton(R.string.btn_aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.create().show();

    }
}
