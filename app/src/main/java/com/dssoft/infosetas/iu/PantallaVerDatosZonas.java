package com.dssoft.infosetas.iu;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.presentador.PresentadorVerDatosZonas;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * Created by Angel on 28/02/2018.
 */

public class PantallaVerDatosZonas extends AppCompatActivity implements VistaVerDatosZonas
{

    @BindView(R.id.layout_ver_datos_zonas) RelativeLayout layoutVerDatosZonas;
    @BindView(R.id.toolBar_pantalla_ver_datos_zona) Toolbar toolbar;
    @BindView(R.id.editVerNombreZona) EditText editTextNombre;
    @BindView(R.id.editVerDescripcionZona) EditText editTextDescripcion;
    @BindView(R.id.btnEditZona) FloatingActionButton btnEdit;
    @BindView(R.id.btnSaveEditZona) FloatingActionButton btnSave;

    private PresentadorVerDatosZonas presentadorVerDatosZonas;
    private int id_zona;
    private String nombre, descripcion, latitud, longitud;
    private boolean nombreMayusculas, descripcionMayusculas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_ver_datos_zona);
        ButterKnife.bind(this);

        id_zona = getIntent().getIntExtra("id_zona", 0);
        nombre = getIntent().getStringExtra("nombre_zona");
        descripcion = getIntent().getStringExtra("descripcion_zona");
        latitud = getIntent().getStringExtra("latitud");
        longitud = getIntent().getStringExtra("longitud");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(nombre.toUpperCase());

        InfoSetas infoSetas = (InfoSetas) getApplicationContext();
        presentadorVerDatosZonas = new PresentadorVerDatosZonas(infoSetas.getDataManagerBD());
        presentadorVerDatosZonas.setVista(this);

        editTextNombre.setText(nombre);
        editTextDescripcion.setText(descripcion);
        btnEdit.setVisibility(View.VISIBLE);

        //Se carga el banner
        AdView mAdView = (AdView) findViewById(R.id.banner_pantalla_ver_datos_zona);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
            case android.R.id.home: onBackPressed();
        }

        return true;
    }


    @OnClick(R.id.btnEditZona)
    public void pulsarEdit()
    {

        editTextNombre.setTypeface(Typeface.DEFAULT);
        editTextDescripcion.setTypeface(Typeface.DEFAULT);
        editTextNombre.setEnabled(true);
        editTextDescripcion.setEnabled(true);

        btnEdit.setVisibility(View.INVISIBLE);
        btnSave.setVisibility(View.VISIBLE);
    }



    @OnClick(R.id.btnSaveEditZona)
    public void pulsarSave()
    {
        String nombre = editTextNombre.getText().toString().trim();
        String descripcion = editTextDescripcion.getText().toString().trim();

        if(nombre.length()>0)
        {

            presentadorVerDatosZonas.modificarZona(id_zona, nombre, descripcion);

            this.nombre = nombre;
            this.descripcion = descripcion;
            getSupportActionBar().setTitle(nombre.toUpperCase());

            editTextNombre.setTypeface(Typeface.DEFAULT_BOLD);
            editTextDescripcion.setTypeface(Typeface.DEFAULT_BOLD);
            editTextNombre.setEnabled(false);
            editTextDescripcion.setEnabled(false);

            btnEdit.setVisibility(View.VISIBLE);
            btnSave.setVisibility(View.INVISIBLE);

        }else
        {
            Toast.makeText(this, R.string.txtNombreVacio, Toast.LENGTH_LONG).show();
        }

    }



    @OnClick(R.id.btnEditMap)
    public void verMapa()
    {
        Intent intent = new Intent(this, PantallaMapaVerZona.class);
        intent.putExtra("latitud", latitud);
        intent.putExtra("longitud", longitud);
        intent.putExtra("nombre_zona", nombre);
        intent.putExtra("descripcion_zona", descripcion);

        startActivity(intent);

    }



    @OnTextChanged(R.id.editVerNombreZona)
    //Se pone la primera letra en mayusculas
    public void editNombreMayusculas(CharSequence text)
    {

        if(text.length()==1 && !nombreMayusculas)
        {
            nombreMayusculas = true;
            String texto = text.toString();
            editTextNombre.setText(texto.toUpperCase());
            editTextNombre.setSelection(1);
        }else
        {
            if(text.length() == 0)
                nombreMayusculas = false;
        }

    }


    @OnTextChanged(R.id.editVerDescripcionZona)
    //Se pone la primera letra en mayusculas
    public void editDescripcionMayusculas(CharSequence text)
    {

        if(text.length()==1 && !descripcionMayusculas)
        {
            descripcionMayusculas = true;
            String texto = text.toString();
            editTextDescripcion.setText(texto.toUpperCase());
            editTextDescripcion.setSelection(1);
        }else
        {
            if(text.length() == 0)
                descripcionMayusculas = false;
        }

    }


    @Override
    public void mostrarMensaje(int contenido)
    {
        Snackbar.make(layoutVerDatosZonas, contenido, Snackbar.LENGTH_LONG).show();
    }
}
