package com.dssoft.infosetas.iu;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dssoft.infosetas.InfoSetas;
import com.dssoft.infosetas.R;
import com.dssoft.infosetas.fragments.PagerFragmentSetas;
import com.dssoft.infosetas.pojos.SetaFireBase;
import com.dssoft.infosetas.presentador.PresentadorDetalles;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 17/05/2017.
 */

public class PantallaDetallesSeta extends AppCompatActivity implements VistaDetalles
{

    @BindView(R.id.layoutPantallaDetalles) LinearLayout layoutPrincipal;
    @BindView(R.id.toolBar_pantalla_comestibles) Toolbar toolbar;
    @BindView(R.id.viewPager_Comestibles) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;
    @BindView(R.id.imgToolBar) ImageView imgTooBar;
    @BindView(R.id.titToolBar) TextView titTooBar;

    private PresentadorDetalles presentadorDetalles;

    private final int NUM_PAGINAS=4;
    private String[] titPestañas;
    private PagerAdapter pagerAdapter;

    private ProgressDialog progressDialog;
    private SetaFireBase setaDetalles;
    private String fotos, comestible, idioma;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_detalles_seta);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Se obtiene el idioma de la App
        idioma = getIntent().getStringExtra("Idioma");

        //Esto lo hago por precaucion porque alguna vez el idioma se ha pasado como nulo y entonces ha dado un NullPointerException en la Clase DataManagerFB
        if(idioma == null)
        {
            if(Locale.getDefault().getLanguage().equals("es"))
                idioma = "es";
            else
                idioma = "en";
        }

        //Se carga el Array de Strings con los titulos del TabLayout
        titPestañas = new String[]{getString(R.string.titTabLayout_1), getString(R.string.titTabLayout_2), getString(R.string.titTabLayout_3), getString(R.string.titTabLayout_4)};

        //Se crea el Presentador de esta pantalla y se le pasa la capa modelo (DataManagerFB) creada en la variable global
        InfoSetas infoSetas = (InfoSetas) getApplication();
        presentadorDetalles = new PresentadorDetalles(infoSetas.getDataManagerFB(), idioma);
        presentadorDetalles.setVista(this);

        //Se obtiene el nombre de la seta pasado como paremetro
        String nombreSeta = getIntent().getStringExtra("nombreSeta");
        comestible = getIntent().getStringExtra("comestible");
        fotos = getIntent().getStringExtra("fotos");
        titTooBar.setText(nombreSeta);

        setColorPantalla(comestible);


        if(comprobarConexion())
        {

            mostrarProgresDialog();

            //Se llama al metodo del presentador que se encarga de obtener los datos de la seta indicada
            presentadorDetalles.getDetallesSeta(nombreSeta, presentadorDetalles);

            //Se crea el ViewPager
            pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);

        }else
        {
            mostrarDialogError(R.string.titErrorDialog_1, R.string.txtErrorDialog_2);
        }

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


    @Override
    public void mostrarDatosSetas(SetaFireBase seta)
    {
        if(progressDialog != null)
            progressDialog.dismiss();

        setaDetalles = seta;
        pagerAdapter.notifyDataSetChanged();//Se actualiza el contenido del viewpager

    }


    @Override
    public void mostrarDialogError(int titError, int txtError)
    {

        if(progressDialog != null)
            progressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(titError);
        builder.setMessage(txtError);

        builder.setPositiveButton(R.string.btnAceptar, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }

        });

        builder.create().show();

    }



    //Se cambia el color de la pantalla segun la comestibilidad de la seta
    private void setColorPantalla(String comestible)
    {

        if(comestible.equals("precaucion"))
        {
            imgTooBar.setImageResource(R.drawable.cuidado_small);

            return;
        }

        if(comestible.equals("sin_interes"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            /*Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkGrey));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryGrey));
            tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryGrey));
            layoutPrincipal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentGrey));*/

            imgTooBar.setImageResource(R.drawable.seta_regular_small);

            return;
        }

        if(comestible.equals("toxica"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            /*Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkOrange));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryOrange));
            tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryOrange));
            layoutPrincipal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentOrange));*/

            imgTooBar.setImageResource(R.drawable.seta_venenosa_small);

            return;
        }


        if(comestible.equals("mortal"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            /*Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkRed));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryRed));
            tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryRed));
            layoutPrincipal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentRed));*/

            imgTooBar.setImageResource(R.drawable.skull_ico);

            return;
        }

        imgTooBar.setImageResource(R.drawable.seta_buena_small);

    }


    private boolean comprobarConexion()
    {
        ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();

        return (networkInfo != null);
    }


    private void mostrarProgresDialog()
    {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle(R.string.titProgressDialog);
        progressDialog.setMessage(getString(R.string.txtProgressDialog));
        progressDialog.setCancelable(false);
        progressDialog.show();

    }



    //*********************************************************************************************
        //Clase PagerAdapter para el ViewPager
    //*********************************************************************************************
    private class MainPagerAdapter extends FragmentStatePagerAdapter
    {

        public MainPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Bundle bundle = new Bundle();
            bundle.putInt("numPagina", position);
            bundle.putParcelable("setaDetalles", setaDetalles);
            bundle.putString("fotos", fotos);
            bundle.putString("comestible",comestible);
            bundle.putString("Idioma", idioma);

            PagerFragmentSetas pagerFragmentSetas = new PagerFragmentSetas();
            pagerFragmentSetas.setArguments(bundle);

            return pagerFragmentSetas;
        }

        @Override
        public int getCount() {
            return NUM_PAGINAS;
        }

        //Aqui se estblece el Titulo de cada pagina en el PagerTitleStrip en funcion de la posicion

        @Override
        public CharSequence getPageTitle(int position)
        {
            return titPestañas[position];
        }

        @Override
        //Con este metodo se consigue que se actualice el contenido del viewpager cuando se llama a pagerAdapter.notifyDataSetChanged();
        public int getItemPosition(Object object)
        {
            return POSITION_NONE;
        }
    }


}
