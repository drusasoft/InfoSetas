package com.dssoft.infosetas;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.dssoft.infosetas.fragments.PagerFragmentSetas;
import com.dssoft.infosetas.pojos.SetaFireBase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Angel on 17/05/2017.
 */

public class PantallaDetallesSeta extends AppCompatActivity
{

    @BindView(R.id.layoutPantallaDetalles) LinearLayout layoutPrincipal;
    @BindView(R.id.toolBar_pantalla_comestibles) Toolbar toolbar;
    @BindView(R.id.viewPager_Comestibles) ViewPager viewPager;
    @BindView(R.id.PagerTabStrip_Comestibles) PagerTabStrip pagerTabStrip;
    @BindView(R.id.imgToolBar) ImageView imgTooBar;
    @BindView(R.id.titToolBar) TextView titTooBar;

    private static int NUM_PAGINAS=4;
    private String[] titPestañas = new String[]{"Descripción", "Hábitat y Comestibilidad", "Observaciones", "Galería"};
    private PagerAdapter pagerAdapter;

    private ProgressDialog progressDialog;
    private SetaFireBase setaDetalles;
    private String fotos, comestible;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_pantalla_detalles_seta);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Se obtiene el nombre de la seta pasado como paremetro
        String nombreSeta = getIntent().getStringExtra("nombreSeta");
        comestible = getIntent().getStringExtra("comestible");
        fotos = getIntent().getStringExtra("fotos");
        titTooBar.setText(nombreSeta);

        setColorPantalla(comestible);


        if(comprobarConexion())
        {

            mostrarProgresDialog();

            //Se obtienen los datos de la seta de Firebase
            getDatosFirebase(nombreSeta);

            //Se crea el ViewPager
            pagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(pagerAdapter);
            pagerTabStrip.setTabIndicatorColor(ContextCompat.getColor(this,R.color.colorBlanco));

        }else
        {
            mostrarDialogError(R.string.titErrorDialog_1, R.string.txtErrorDialog_2);
        }

    }




    private void getDatosFirebase(String nombreSeta)
    {
        //FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = database.getReference("setas_esp").child(nombreSeta);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                progressDialog.dismiss();

                //Se desregistra el listener
                databaseReference.removeEventListener(this);

                //Obtenemos los datos de la seta Firebase y lo guardamos en una variable
                setaDetalles = dataSnapshot.getValue(SetaFireBase.class);

                if(setaDetalles != null)
                {
                    pagerAdapter.notifyDataSetChanged();//Se actualiza el contenido del viewpager
                }else
                {
                    mostrarDialogError(R.string.titErrorDialog_1, R.string.txtErrorDialog_1);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

                progressDialog.dismiss();
                mostrarDialogError(R.string.titErrorDialog_1, R.string.txtErrorDialog_1);
            }

        });
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
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkGrey));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryGrey));
            pagerTabStrip.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryGrey));
            layoutPrincipal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentGrey));

            imgTooBar.setImageResource(R.drawable.seta_regular_small);

            return;
        }

        if(comestible.equals("toxica"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkOrange));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryOrange));
            pagerTabStrip.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryOrange));
            layoutPrincipal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentOrange));

            imgTooBar.setImageResource(R.drawable.seta_venenosa_small);

            return;
        }


        if(comestible.equals("mortal"))
        {
            //Se cambia el color de la statusbar, toolbar y pagertabstrip
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDarkRed));

            toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryRed));
            pagerTabStrip.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryRed));
            layoutPrincipal.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccentRed));

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


    private void mostrarDialogError(int titError, int txtError)
    {

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
