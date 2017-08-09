package com.dssoft.infosetas.base_datos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dssoft.infosetas.R;

/**
 * Created by Angel on 23/05/2017.
 */

public class BDAdapter
{

    private final String crearTrabla = "Create Table Setas (id_seta Integer PRIMARY KEY AUTOINCREMENT, nombre Integer, nombre_comun" +
            "Integer, nombre_ordenar Integer, comestible Integer, fotos String, foto_list Integer)";
    private final String eliminarTaabla = "DROP TABLE IF EXISTS Plantas";
    private final String nombreBD = "BDSetas";
    private final int versionBD = 1;

    private Context context;
    private SQLiteDatabase db;
    private SetasSQLiteHelper setasSQLiteHelper;


    public BDAdapter(Context context)
    {
        this.context = context;
        setasSQLiteHelper = new SetasSQLiteHelper(context,nombreBD,null,versionBD);
    }

    public void abrirBD()//Se abre la BD en modo lectura
    {
        db = setasSQLiteHelper.getReadableDatabase();
    }

    public void cerrarBD()
    {
        setasSQLiteHelper.close();
    }

    //Se obtiene todos los registro de la BD
    public Cursor obtenerRegistros()
    {
        return db.rawQuery("Select * from Setas", null);
    }


    private class SetasSQLiteHelper extends SQLiteOpenHelper
    {

        public SetasSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(crearTrabla);
            insertarDatos(db);
        }

        @Override
        //Se ejecuta cuando se actualzia la version de la BD
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            //Se elimina la Tabla
            db.execSQL(eliminarTaabla);

            //Se vuelve a crear la tabla y se insertan los datos
            db.execSQL(crearTrabla);
            insertarDatos(db);
        }

        private void insertarDatos(SQLiteDatabase db)
        {
            String stringFotos="";
            db.beginTransaction();

            stringFotos = String.valueOf(R.drawable.arvensis1)+"-"+String.valueOf(R.drawable.arvensis2)+"-"+String.valueOf(R.drawable.arvensis3)+"-"+String.valueOf(R.drawable.arvensis4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta1+", "+R.string.nombre_comunSeta1+
            ", "+R.string.nombre_ordenarSeta1+", "+R.string.comestibilidad_seta1+", '"+stringFotos+"', "+R.drawable.arvensis_list+")");

            stringFotos = String.valueOf(R.drawable.campestris1)+"-"+String.valueOf(R.drawable.campestris2)+"-"+String.valueOf(R.drawable.campestris3)+"-"+String.valueOf(R.drawable.campestris4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta2+", "+R.string.nombre_comunSeta2+
                    ", "+R.string.nombre_ordenarSeta2+", "+R.string.comestibilidad_seta2+", '"+stringFotos+"', "+R.drawable.campestris_list+")");

            stringFotos = String.valueOf(R.drawable.xanthodermus1)+"-"+String.valueOf(R.drawable.xanthodermus2)+"-"+String.valueOf(R.drawable.xanthodermus3)+"-"+String.valueOf(R.drawable.xanthodermus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta3+", "+R.string.nombre_comunSeta3+
                    ", "+R.string.nombre_ordenarSeta3+", "+R.string.comestibilidad_seta3+", '"+stringFotos+"', "+R.drawable.xanthodermus_list+")");

            stringFotos = String.valueOf(R.drawable.cylindracea1)+"-"+String.valueOf(R.drawable.cylindracea2)+"-"+String.valueOf(R.drawable.cylindracea3)+"-"+String.valueOf(R.drawable.cylindracea4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta4+", "+R.string.nombre_comunSeta4+
                    ", "+R.string.nombre_ordenarSeta4+", "+R.string.comestibilidad_seta4+", '"+stringFotos+"', "+R.drawable.cylindracea_list+")");


            stringFotos = String.valueOf(R.drawable.albatrellus1)+"-"+String.valueOf(R.drawable.albatrellus2)+"-"+String.valueOf(R.drawable.albatrellus3)+"-"+String.valueOf(R.drawable.albatrellus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta5+", "+R.string.nombre_comunSeta5+
                    ", "+R.string.nombre_ordenarSeta5+", "+R.string.comestibilidad_seta5+", '"+stringFotos+"', "+R.drawable.albatrellus_list+")");

            stringFotos = String.valueOf(R.drawable.caesarea1)+"-"+String.valueOf(R.drawable.caesarea2)+"-"+String.valueOf(R.drawable.caesarea3)+"-"+String.valueOf(R.drawable.caesarea4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta6+", "+R.string.nombre_comunSeta6+
                    ", "+R.string.nombre_ordenarSeta6+", "+R.string.comestibilidad_seta6+", '"+stringFotos+"', "+R.drawable.caesarea_list+")");

            stringFotos = String.valueOf(R.drawable.muscaria1)+"-"+String.valueOf(R.drawable.muscaria2)+"-"+String.valueOf(R.drawable.muscaria3)+"-"+String.valueOf(R.drawable.muscaria4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta7+", "+R.string.nombre_comunSeta7+
                    ", "+R.string.nombre_ordenarSeta7+", "+R.string.comestibilidad_seta7+", '"+stringFotos+"', "+R.drawable.muscaria_list+")");

            stringFotos = String.valueOf(R.drawable.ovoidea1)+"-"+String.valueOf(R.drawable.ovoidea2)+"-"+String.valueOf(R.drawable.ovoidea3)+"-"+String.valueOf(R.drawable.ovoidea4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta8+", "+R.string.nombre_comunSeta8+
                    ", "+R.string.nombre_ordenarSeta8+", "+R.string.comestibilidad_seta8+", '"+stringFotos+"', "+R.drawable.ovoidea_list+")");


            stringFotos = String.valueOf(R.drawable.phalloides1)+"-"+String.valueOf(R.drawable.phalloides2)+"-"+String.valueOf(R.drawable.phalloides3)+"-"+String.valueOf(R.drawable.phalloides4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta9+", "+R.string.nombre_comunSeta9+
                    ", "+R.string.nombre_ordenarSeta9+", "+R.string.comestibilidad_seta9+", '"+stringFotos+"', "+R.drawable.phalloides_list+")");

            stringFotos = String.valueOf(R.drawable.rubescens1)+"-"+String.valueOf(R.drawable.rubescens2)+"-"+String.valueOf(R.drawable.rubescens3)+"-"+String.valueOf(R.drawable.rubescens4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta10+", "+R.string.nombre_comunSeta10+
                    ", "+R.string.nombre_ordenarSeta10+", "+R.string.comestibilidad_seta10+", '"+stringFotos+"', "+R.drawable.rubescens_list+")");

            stringFotos = String.valueOf(R.drawable.submembranacea1)+"-"+String.valueOf(R.drawable.submembranacea2)+"-"+String.valueOf(R.drawable.submembranacea3)+"-"+String.valueOf(R.drawable.submembranacea4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta11+", "+R.string.nombre_comunSeta11+
                    ", "+R.string.nombre_ordenarSeta11+", "+R.string.comestibilidad_seta11+", '"+stringFotos+"', "+R.drawable.submembranacea_list+")");

            stringFotos = String.valueOf(R.drawable.mellea1)+"-"+String.valueOf(R.drawable.mellea2)+"-"+String.valueOf(R.drawable.mellea3)+"-"+String.valueOf(R.drawable.mellea4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta12+", "+R.string.nombre_comunSeta12+
                    ", "+R.string.nombre_ordenarSeta12+", "+R.string.comestibilidad_seta12+", '"+stringFotos+"', "+R.drawable.mellea_list+")");

            stringFotos = String.valueOf(R.drawable.astraeus1)+"-"+String.valueOf(R.drawable.astraeus2)+"-"+String.valueOf(R.drawable.astraeus3)+"-"+String.valueOf(R.drawable.astraeus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta13+", "+R.string.nombre_comunSeta13+
                    ", "+R.string.nombre_ordenarSeta13+", "+R.string.comestibilidad_seta13+", '"+stringFotos+"', "+R.drawable.arvensis_list+")");

            stringFotos = String.valueOf(R.drawable.oreja1)+"-"+String.valueOf(R.drawable.oreja2)+"-"+String.valueOf(R.drawable.oreja3)+"-"+String.valueOf(R.drawable.oreja4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta14+", "+R.string.nombre_comunSeta14+
                    ", "+R.string.nombre_ordenarSeta14+", "+R.string.comestibilidad_seta14+", '"+stringFotos+"', "+R.drawable.oreja_list +")");

            stringFotos = String.valueOf(R.drawable.baeospora1)+"-"+String.valueOf(R.drawable.baeospora2)+"-"+String.valueOf(R.drawable.baeospora3)+"-"+String.valueOf(R.drawable.baeospora4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta15+", "+R.string.nombre_comunSeta15+
                    ", "+R.string.nombre_ordenarSeta15+", "+R.string.comestibilidad_seta15+", '"+stringFotos+"', "+R.drawable.baeospora_list+")");

            stringFotos = String.valueOf(R.drawable.edulis1)+"-"+String.valueOf(R.drawable.edulis2)+"-"+String.valueOf(R.drawable.edulis3)+"-"+String.valueOf(R.drawable.edulis4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta16+", "+R.string.nombre_comunSeta16+
                    ", "+R.string.nombre_ordenarSeta16+", "+R.string.comestibilidad_seta16+", '"+stringFotos+"', "+R.drawable.edulis_list+")");

            stringFotos = String.valueOf(R.drawable.satanas1)+"-"+String.valueOf(R.drawable.satanas2)+"-"+String.valueOf(R.drawable.satanas3)+"-"+String.valueOf(R.drawable.satanas4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta17+", "+R.string.nombre_comunSeta17+
                    ", "+R.string.nombre_ordenarSeta17+", "+R.string.comestibilidad_seta17+", '"+stringFotos+"', "+R.drawable.satanas_list+")");

            stringFotos = String.valueOf(R.drawable.gambosa1)+"-"+String.valueOf(R.drawable.gambosa2)+"-"+String.valueOf(R.drawable.gambosa3)+"-"+String.valueOf(R.drawable.gambosa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta18+", "+R.string.nombre_comunSeta18+
                    ", "+R.string.nombre_ordenarSeta18+", "+R.string.comestibilidad_seta18+", '"+stringFotos+"', "+R.drawable.gambosa_list+")");

            stringFotos = String.valueOf(R.drawable.calvatia1)+"-"+String.valueOf(R.drawable.calvatia2)+"-"+String.valueOf(R.drawable.calvatia3)+"-"+String.valueOf(R.drawable.calvatia4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta19+", "+R.string.nombre_comunSeta19+
                    ", "+R.string.nombre_ordenarSeta19+", "+R.string.comestibilidad_seta19+", '"+stringFotos+"', "+R.drawable.calvatia_list+")");

            stringFotos = String.valueOf(R.drawable.cantharellus1)+"-"+String.valueOf(R.drawable.cantharellus2)+"-"+String.valueOf(R.drawable.cantharellus3)+"-"+String.valueOf(R.drawable.cantharellus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta20+", "+R.string.nombre_comunSeta20+
                    ", "+R.string.nombre_ordenarSeta20+", "+R.string.comestibilidad_seta20+", '"+stringFotos+"', "+R.drawable.cantharellus_list+")");

            stringFotos = String.valueOf(R.drawable.chondrostereum1)+"-"+String.valueOf(R.drawable.chondrostereum2)+"-"+String.valueOf(R.drawable.chondrostereum3)+"-"+String.valueOf(R.drawable.chondrostereum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta21+", "+R.string.nombre_comunSeta21+
                    ", "+R.string.nombre_ordenarSeta21+", "+R.string.comestibilidad_seta21+", '"+stringFotos+"', "+R.drawable.chondrostereum_list+")");

            stringFotos = String.valueOf(R.drawable.truncatus1)+"-"+String.valueOf(R.drawable.truncatus2)+"-"+String.valueOf(R.drawable.truncatus3)+"-"+String.valueOf(R.drawable.truncatus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta22+", "+R.string.nombre_comunSeta22+
                    ", "+R.string.nombre_ordenarSeta22+", "+R.string.comestibilidad_seta22+", '"+stringFotos+"', "+R.drawable.truncatus_list+")");

            stringFotos = String.valueOf(R.drawable.alexandri1)+"-"+String.valueOf(R.drawable.alexandri2)+"-"+String.valueOf(R.drawable.alexandri3)+"-"+String.valueOf(R.drawable.alexandri4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta23+", "+R.string.nombre_comunSeta23+
                    ", "+R.string.nombre_ordenarSeta23+", "+R.string.comestibilidad_seta23+", '"+stringFotos+"', "+R.drawable.alexandri_list+")");

            stringFotos = String.valueOf(R.drawable.candida1)+"-"+String.valueOf(R.drawable.candida2)+"-"+String.valueOf(R.drawable.candida3)+"-"+String.valueOf(R.drawable.candida4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta24+", "+R.string.nombre_comunSeta24+
                    ", "+R.string.nombre_ordenarSeta24+", "+R.string.comestibilidad_seta24+", '"+stringFotos+"', "+R.drawable.candida_list+")");

            stringFotos = String.valueOf(R.drawable.geotropa1)+"-"+String.valueOf(R.drawable.geotropa2)+"-"+String.valueOf(R.drawable.geotropa3)+"-"+String.valueOf(R.drawable.geotropa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta25+", "+R.string.nombre_comunSeta25+
                    ", "+R.string.nombre_ordenarSeta25+", "+R.string.comestibilidad_seta25+", '"+stringFotos+"', "+R.drawable.geotropa_list+")");

            stringFotos = String.valueOf(R.drawable.odora1)+"-"+String.valueOf(R.drawable.odora2)+"-"+String.valueOf(R.drawable.odora3)+"-"+String.valueOf(R.drawable.odora4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta26+", "+R.string.nombre_comunSeta26+
                    ", "+R.string.nombre_ordenarSeta26+", "+R.string.comestibilidad_seta26+", '"+stringFotos+"', "+R.drawable.odora_list+")");


            stringFotos = String.valueOf(R.drawable.rivulosa1)+"-"+String.valueOf(R.drawable.rivulosa2)+"-"+String.valueOf(R.drawable.rivulosa3)+"-"+String.valueOf(R.drawable.rivulosa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta27+", "+R.string.nombre_comunSeta27+
                    ", "+R.string.nombre_ordenarSeta27+", "+R.string.comestibilidad_seta27+", '"+stringFotos+"', "+R.drawable.rivulosa_list+")");

            stringFotos = String.valueOf(R.drawable.comatus1)+"-"+String.valueOf(R.drawable.comatus2)+"-"+String.valueOf(R.drawable.comatus3)+"-"+String.valueOf(R.drawable.comatus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta28+", "+R.string.nombre_comunSeta28+
                    ", "+R.string.nombre_ordenarSeta28+", "+R.string.comestibilidad_seta28+", '"+stringFotos+"', "+R.drawable.comatus_list+")");

            stringFotos = String.valueOf(R.drawable.mucosus1)+"-"+String.valueOf(R.drawable.mucosus2)+"-"+String.valueOf(R.drawable.mucosus3)+"-"+String.valueOf(R.drawable.mucosus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta29+", "+R.string.nombre_comunSeta29+
                    ", "+R.string.nombre_ordenarSeta29+", "+R.string.comestibilidad_seta29+", '"+stringFotos+"', "+R.drawable.mucosus_list+")");

            stringFotos = String.valueOf(R.drawable.violaceus1)+"-"+String.valueOf(R.drawable.violaceus2)+"-"+String.valueOf(R.drawable.violaceus3)+"-"+String.valueOf(R.drawable.violaceus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta30+", "+R.string.nombre_comunSeta30+
                    ", "+R.string.nombre_ordenarSeta30+", "+R.string.comestibilidad_seta30+", '"+stringFotos+"', "+R.drawable.violaceus_list+")");

            stringFotos = String.valueOf(R.drawable.sulphurinus1)+"-"+String.valueOf(R.drawable.sulphurinus2)+"-"+String.valueOf(R.drawable.sulphurinus3)+"-"+String.valueOf(R.drawable.sulphurinus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta31+", "+R.string.nombre_comunSeta31+
                    ", "+R.string.nombre_ordenarSeta31+", "+R.string.comestibilidad_seta31+", '"+stringFotos+"', "+R.drawable.sulphurinus_list+")");


            stringFotos = String.valueOf(R.drawable.cyathus1)+"-"+String.valueOf(R.drawable.cyathus2)+"-"+String.valueOf(R.drawable.cyathus3)+"-"+String.valueOf(R.drawable.cyathus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta32+", "+R.string.nombre_comunSeta32+
                    ", "+R.string.nombre_ordenarSeta32+", "+R.string.comestibilidad_seta32+", '"+stringFotos+"', "+R.drawable.cyathus_list+")");

            stringFotos = String.valueOf(R.drawable.amianthinum1)+"-"+String.valueOf(R.drawable.amianthinum2)+"-"+String.valueOf(R.drawable.amianthinum3)+"-"+String.valueOf(R.drawable.amianthinum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta33+", "+R.string.nombre_comunSeta33+
                    ", "+R.string.nombre_ordenarSeta33+", "+R.string.comestibilidad_seta33+", '"+stringFotos+"', "+R.drawable.amianthinum_list+")");

            stringFotos = String.valueOf(R.drawable.entoloma1)+"-"+String.valueOf(R.drawable.entoloma2)+"-"+String.valueOf(R.drawable.entoloma3)+"-"+String.valueOf(R.drawable.entoloma4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta34+", "+R.string.nombre_comunSeta34+
                    ", "+R.string.nombre_ordenarSeta34+", "+R.string.comestibilidad_seta34+", '"+stringFotos+"', "+R.drawable.entoloma_list+")");

            stringFotos = String.valueOf(R.drawable.fomes1)+"-"+String.valueOf(R.drawable.fomes2)+"-"+String.valueOf(R.drawable.fomes3)+"-"+String.valueOf(R.drawable.fomes4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta35+", "+R.string.nombre_comunSeta35+
                    ", "+R.string.nombre_ordenarSeta35+", "+R.string.comestibilidad_seta35+", '"+stringFotos+"', "+R.drawable.fomes_list+")");

            stringFotos = String.valueOf(R.drawable.galerina1)+"-"+String.valueOf(R.drawable.galerina2)+"-"+String.valueOf(R.drawable.galerina3)+"-"+String.valueOf(R.drawable.galerina4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta36+", "+R.string.nombre_comunSeta36+
                    ", "+R.string.nombre_ordenarSeta36+", "+R.string.comestibilidad_seta36+", '"+stringFotos+"', "+R.drawable.galerina_list+")");

            stringFotos = String.valueOf(R.drawable.ganoderma1)+"-"+String.valueOf(R.drawable.ganoderma2)+"-"+String.valueOf(R.drawable.ganoderma3)+"-"+String.valueOf(R.drawable.ganoderma4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta37+", "+R.string.nombre_comunSeta37+
                    ", "+R.string.nombre_ordenarSeta37+", "+R.string.comestibilidad_seta37+", '"+stringFotos+"', "+R.drawable.ganoderma_list+")");

            stringFotos = String.valueOf(R.drawable.roseus1)+"-"+String.valueOf(R.drawable.roseus2)+"-"+String.valueOf(R.drawable.roseus3)+"-"+String.valueOf(R.drawable.roseus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta38+", "+R.string.nombre_comunSeta38+
                    ", "+R.string.nombre_ordenarSeta38+", "+R.string.comestibilidad_seta38+", '"+stringFotos+"', "+R.drawable.roseus_list+")");

            stringFotos = String.valueOf(R.drawable.esculenta1)+"-"+String.valueOf(R.drawable.esculenta2)+"-"+String.valueOf(R.drawable.esculenta3)+"-"+String.valueOf(R.drawable.esculenta4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta39+", "+R.string.nombre_comunSeta39+
                    ", "+R.string.nombre_ordenarSeta39+", "+R.string.comestibilidad_seta39+", '"+stringFotos+"', "+R.drawable.esculenta_list+")");

            stringFotos = String.valueOf(R.drawable.lacunosa1)+"-"+String.valueOf(R.drawable.lacunosa2)+"-"+String.valueOf(R.drawable.lacunosa3)+"-"+String.valueOf(R.drawable.lacunosa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta40+", "+R.string.nombre_comunSeta40+
                    ", "+R.string.nombre_ordenarSeta40+", "+R.string.comestibilidad_seta40+", '"+stringFotos+"', "+R.drawable.lacunosa_list+")");

            stringFotos = String.valueOf(R.drawable.repandum1)+"-"+String.valueOf(R.drawable.repandum2)+"-"+String.valueOf(R.drawable.repandum3)+"-"+String.valueOf(R.drawable.repandum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta41+", "+R.string.nombre_comunSeta41+
                    ", "+R.string.nombre_ordenarSeta41+", "+R.string.comestibilidad_seta41+", '"+stringFotos+"', "+R.drawable.repandum_list+")");

            stringFotos = String.valueOf(R.drawable.aurantiaca1)+"-"+String.valueOf(R.drawable.aurantiaca2)+"-"+String.valueOf(R.drawable.aurantiaca3)+"-"+String.valueOf(R.drawable.aurantiaca4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta42+", "+R.string.nombre_comunSeta42+
                    ", "+R.string.nombre_ordenarSeta42+", "+R.string.comestibilidad_seta42+", '"+stringFotos+"', "+R.drawable.aurantiaca_list+")");

            stringFotos = String.valueOf(R.drawable.marzuolus1)+"-"+String.valueOf(R.drawable.marzuolus2)+"-"+String.valueOf(R.drawable.marzuolus3)+"-"+String.valueOf(R.drawable.marzuolus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta43+", "+R.string.nombre_comunSeta43+
                    ", "+R.string.nombre_ordenarSeta43+", "+R.string.comestibilidad_seta43+", '"+stringFotos+"', "+R.drawable.marzuolus_list+")");

            stringFotos = String.valueOf(R.drawable.russula1)+"-"+String.valueOf(R.drawable.russula2)+"-"+String.valueOf(R.drawable.russula3)+"-"+String.valueOf(R.drawable.russula4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta44+", "+R.string.nombre_comunSeta44+
                    ", "+R.string.nombre_ordenarSeta44+", "+R.string.comestibilidad_seta44+", '"+stringFotos+"', "+R.drawable.russula_list+")");

            stringFotos = String.valueOf(R.drawable.fasciculare1)+"-"+String.valueOf(R.drawable.fasciculare2)+"-"+String.valueOf(R.drawable.fasciculare3)+"-"+String.valueOf(R.drawable.fasciculare4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta45+", "+R.string.nombre_comunSeta45+
                    ", "+R.string.nombre_ordenarSeta45+", "+R.string.comestibilidad_seta45+", '"+stringFotos+"', "+R.drawable.fasciculare_list+")");

            stringFotos = String.valueOf(R.drawable.geophylla1)+"-"+String.valueOf(R.drawable.geophylla2)+"-"+String.valueOf(R.drawable.geophylla3)+"-"+String.valueOf(R.drawable.geophylla4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta46+", "+R.string.nombre_comunSeta46+
                    ", "+R.string.nombre_ordenarSeta46+", "+R.string.comestibilidad_seta46+", '"+stringFotos+"', "+R.drawable.geophylla_list+")");

            stringFotos = String.valueOf(R.drawable.inonnotus1)+"-"+String.valueOf(R.drawable.inonnotus2)+"-"+String.valueOf(R.drawable.inonnotus3)+"-"+String.valueOf(R.drawable.inonnotus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta47+", "+R.string.nombre_comunSeta47+
                    ", "+R.string.nombre_ordenarSeta47+", "+R.string.comestibilidad_seta47+", '"+stringFotos+"', "+R.drawable.inonnotus_list+")");

            stringFotos = String.valueOf(R.drawable.tortilis1)+"-"+String.valueOf(R.drawable.tortilis2)+"-"+String.valueOf(R.drawable.tortilis3)+"-"+String.valueOf(R.drawable.tortilis4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta48+", "+R.string.nombre_comunSeta48+
                    ", "+R.string.nombre_ordenarSeta48+", "+R.string.comestibilidad_seta48+", '"+stringFotos+"', "+R.drawable.tortilis_list+")");

            stringFotos = String.valueOf(R.drawable.deliciosus1)+"-"+String.valueOf(R.drawable.deliciosus2)+"-"+String.valueOf(R.drawable.deliciosus3)+"-"+String.valueOf(R.drawable.deliciosus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta49+", "+R.string.nombre_comunSeta49+
                    ", "+R.string.nombre_ordenarSeta49+", "+R.string.comestibilidad_seta49+", '"+stringFotos+"', "+R.drawable.deliciosus_list+")");

            stringFotos = String.valueOf(R.drawable.mairei1)+"-"+String.valueOf(R.drawable.mairei2)+"-"+String.valueOf(R.drawable.mairei3)+"-"+String.valueOf(R.drawable.mairei4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta50+", "+R.string.nombre_comunSeta50+
                    ", "+R.string.nombre_ordenarSeta50+", "+R.string.comestibilidad_seta50+", '"+stringFotos+"', "+R.drawable.mairei_list+")");

            stringFotos = String.valueOf(R.drawable.vietus1)+"-"+String.valueOf(R.drawable.vietus2)+"-"+String.valueOf(R.drawable.vietus3)+"-"+String.valueOf(R.drawable.vietus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta51+", "+R.string.nombre_comunSeta51+
                    ", "+R.string.nombre_ordenarSeta51+", "+R.string.comestibilidad_seta51+", '"+stringFotos+"', "+R.drawable.vietus_list+")");

            stringFotos = String.valueOf(R.drawable.lepidum1)+"-"+String.valueOf(R.drawable.lepidum2)+"-"+String.valueOf(R.drawable.lepidum3)+"-"+String.valueOf(R.drawable.lepidum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta52+", "+R.string.nombre_comunSeta52+
                    ", "+R.string.nombre_ordenarSeta52+", "+R.string.comestibilidad_seta52+", '"+stringFotos+"', "+R.drawable.lepidum_list+")");

            stringFotos = String.valueOf(R.drawable.oxycedri1)+"-"+String.valueOf(R.drawable.oxycedri2)+"-"+String.valueOf(R.drawable.oxycedri3)+"-"+String.valueOf(R.drawable.oxycedri4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta53+", "+R.string.nombre_comunSeta53+
                    ", "+R.string.nombre_ordenarSeta53+", "+R.string.comestibilidad_seta53+", '"+stringFotos+"', "+R.drawable.oxycedri_list+")");

            stringFotos = String.valueOf(R.drawable.cristata1)+"-"+String.valueOf(R.drawable.cristata2)+"-"+String.valueOf(R.drawable.cristata3)+"-"+String.valueOf(R.drawable.cristata4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta54+", "+R.string.nombre_comunSeta54+
                    ", "+R.string.nombre_ordenarSeta54+", "+R.string.comestibilidad_seta54+", '"+stringFotos+"', "+R.drawable.cristata_list+")");

            stringFotos = String.valueOf(R.drawable.nuda1)+"-"+String.valueOf(R.drawable.nuda2)+"-"+String.valueOf(R.drawable.nuda3)+"-"+String.valueOf(R.drawable.nuda4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta55+", "+R.string.nombre_comunSeta55+
                    ", "+R.string.nombre_ordenarSeta55+", "+R.string.comestibilidad_seta55+", '"+stringFotos+"', "+R.drawable.nuda_list+")");


            stringFotos = String.valueOf(R.drawable.perlatum1)+"-"+String.valueOf(R.drawable.perlatum2)+"-"+String.valueOf(R.drawable.perlatum3)+"-"+String.valueOf(R.drawable.perlatum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta56+", "+R.string.nombre_comunSeta56+
                    ", "+R.string.nombre_ordenarSeta56+", "+R.string.comestibilidad_seta56+", '"+stringFotos+"', "+R.drawable.perlatum_list+")");

            stringFotos = String.valueOf(R.drawable.procera1)+"-"+String.valueOf(R.drawable.procera2)+"-"+String.valueOf(R.drawable.procera3)+"-"+String.valueOf(R.drawable.procera4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta57+", "+R.string.nombre_comunSeta57+
                    ", "+R.string.nombre_ordenarSeta57+", "+R.string.comestibilidad_seta57+", '"+stringFotos+"', "+R.drawable.procera_list+")");

            stringFotos = String.valueOf(R.drawable.oreades1)+"-"+String.valueOf(R.drawable.oreades2)+"-"+String.valueOf(R.drawable.oreades3)+"-"+String.valueOf(R.drawable.oreades4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta58+", "+R.string.nombre_comunSeta58+
                    ", "+R.string.nombre_ordenarSeta58+", "+R.string.comestibilidad_seta58+", '"+stringFotos+"', "+R.drawable.oreades_list+")");

            stringFotos = String.valueOf(R.drawable.quercophilus1)+"-"+String.valueOf(R.drawable.quercophilus2)+"-"+String.valueOf(R.drawable.quercophilus3)+"-"+String.valueOf(R.drawable.quercophilus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta59+", "+R.string.nombre_comunSeta59+
                    ", "+R.string.nombre_ordenarSeta59+", "+R.string.comestibilidad_seta59+", '"+stringFotos+"', "+R.drawable.quercophilus_list+")");

            stringFotos = String.valueOf(R.drawable.paludosa1)+"-"+String.valueOf(R.drawable.paludosa2)+"-"+String.valueOf(R.drawable.paludosa3)+"-"+String.valueOf(R.drawable.paludosa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta60+", "+R.string.nombre_comunSeta60+
                    ", "+R.string.nombre_ordenarSeta60+", "+R.string.comestibilidad_seta60+", '"+stringFotos+"', "+R.drawable.paludosa_list+")");

            stringFotos = String.valueOf(R.drawable.purpurascens1)+"-"+String.valueOf(R.drawable.purpurascens2)+"-"+String.valueOf(R.drawable.purpurascens3)+"-"+String.valueOf(R.drawable.purpurascens4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta61+", "+R.string.nombre_comunSeta61+
                    ", "+R.string.nombre_ordenarSeta61+", "+R.string.comestibilidad_seta61+", '"+stringFotos+"', "+R.drawable.purpurascens_list+")");

            stringFotos = String.valueOf(R.drawable.involutus1)+"-"+String.valueOf(R.drawable.involutus2)+"-"+String.valueOf(R.drawable.involutus3)+"-"+String.valueOf(R.drawable.involutus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta62+", "+R.string.nombre_comunSeta62+
                    ", "+R.string.nombre_ordenarSeta62+", "+R.string.comestibilidad_seta62+", '"+stringFotos+"', "+R.drawable.involutus_list+")");

            stringFotos = String.valueOf(R.drawable.phyllogena1)+"-"+String.valueOf(R.drawable.phyllogena2)+"-"+String.valueOf(R.drawable.phyllogena3)+"-"+String.valueOf(R.drawable.phyllogena4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta63+", "+R.string.nombre_comunSeta63+
                    ", "+R.string.nombre_ordenarSeta63+", "+R.string.comestibilidad_seta63+", '"+stringFotos+"', "+R.drawable.phyllogena_list+")");

            stringFotos = String.valueOf(R.drawable.erinaceus1)+"-"+String.valueOf(R.drawable.erinaceus2)+"-"+String.valueOf(R.drawable.erinaceus3)+"-"+String.valueOf(R.drawable.erinaceus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta64+", "+R.string.nombre_comunSeta64+
                    ", "+R.string.nombre_ordenarSeta64+", "+R.string.comestibilidad_seta64+", '"+stringFotos+"', "+R.drawable.erinaceus_list+")");

            stringFotos = String.valueOf(R.drawable.torulosus1)+"-"+String.valueOf(R.drawable.torulosus2)+"-"+String.valueOf(R.drawable.torulosus3)+"-"+String.valueOf(R.drawable.torulosus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta65+", "+R.string.nombre_comunSeta65+
                    ", "+R.string.nombre_ordenarSeta65+", "+R.string.comestibilidad_seta65+", '"+stringFotos+"', "+R.drawable.torulosus_list+")");

            stringFotos = String.valueOf(R.drawable.highlandensis1)+"-"+String.valueOf(R.drawable.highlandensis2)+"-"+String.valueOf(R.drawable.highlandensis3)+"-"+String.valueOf(R.drawable.highlandensis4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta66+", "+R.string.nombre_comunSeta66+
                    ", "+R.string.nombre_ordenarSeta66+", "+R.string.comestibilidad_seta66+", '"+stringFotos+"', "+R.drawable.highlandensis_list+")");

            stringFotos = String.valueOf(R.drawable.betulinus1)+"-"+String.valueOf(R.drawable.betulinus2)+"-"+String.valueOf(R.drawable.betulinus3)+"-"+String.valueOf(R.drawable.betulinus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta67+", "+R.string.nombre_comunSeta67+
                    ", "+R.string.nombre_ordenarSeta67+", "+R.string.comestibilidad_seta67+", '"+stringFotos+"', "+R.drawable.betulinus_list+")");

            stringFotos = String.valueOf(R.drawable.eryngii1)+"-"+String.valueOf(R.drawable.eryngii2)+"-"+String.valueOf(R.drawable.eryngii3)+"-"+String.valueOf(R.drawable.eryngii4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta68+", "+R.string.nombre_comunSeta68+
                    ", "+R.string.nombre_ordenarSeta68+", "+R.string.comestibilidad_seta68+", '"+stringFotos+"', "+R.drawable.eryngii_list+")");

            stringFotos = String.valueOf(R.drawable.butyracea1)+"-"+String.valueOf(R.drawable.butyracea2)+"-"+String.valueOf(R.drawable.butyracea3)+"-"+String.valueOf(R.drawable.butyracea4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta69+", "+R.string.nombre_comunSeta69+
                    ", "+R.string.nombre_ordenarSeta69+", "+R.string.comestibilidad_seta69+", '"+stringFotos+"', "+R.drawable.butyracea_list+")");

            stringFotos = String.valueOf(R.drawable.cyanoxantha1)+"-"+String.valueOf(R.drawable.cyanoxantha2)+"-"+String.valueOf(R.drawable.cyanoxantha3)+"-"+String.valueOf(R.drawable.cyanoxantha4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta70+", "+R.string.nombre_comunSeta70+
                    ", "+R.string.nombre_ordenarSeta70+", "+R.string.comestibilidad_seta70+", '"+stringFotos+"', "+R.drawable.cyanoxantha_list+")");

            stringFotos = String.valueOf(R.drawable.sanguinaria1)+"-"+String.valueOf(R.drawable.sanguinaria2)+"-"+String.valueOf(R.drawable.sanguinaria3)+"-"+String.valueOf(R.drawable.sanguinaria4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta71+", "+R.string.nombre_comunSeta71+
                    ", "+R.string.nombre_ordenarSeta71+", "+R.string.comestibilidad_seta71+", '"+stringFotos+"', "+R.drawable.sanguinaria_list+")");

            stringFotos = String.valueOf(R.drawable.sarcodon1)+"-"+String.valueOf(R.drawable.sarcodon2)+"-"+String.valueOf(R.drawable.sarcodon3)+"-"+String.valueOf(R.drawable.sarcodon4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta72+", "+R.string.nombre_comunSeta72+
                    ", "+R.string.nombre_ordenarSeta72+", "+R.string.comestibilidad_seta72+", '"+stringFotos+"', "+R.drawable.sarcodon_list+")");

            stringFotos = String.valueOf(R.drawable.coronaria1)+"-"+String.valueOf(R.drawable.coronaria2)+"-"+String.valueOf(R.drawable.coronaria3)+"-"+String.valueOf(R.drawable.coronaria4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta73+", "+R.string.nombre_comunSeta73+
                    ", "+R.string.nombre_ordenarSeta73+", "+R.string.comestibilidad_seta73+", '"+stringFotos+"', "+R.drawable.coronaria_list+")");

            stringFotos = String.valueOf(R.drawable.crispa1)+"-"+String.valueOf(R.drawable.crispa2)+"-"+String.valueOf(R.drawable.crispa3)+"-"+String.valueOf(R.drawable.crispa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta74+", "+R.string.nombre_comunSeta74+
                    ", "+R.string.nombre_ordenarSeta74+", "+R.string.comestibilidad_seta74+", '"+stringFotos+"', "+R.drawable.crispa_list+")");

            stringFotos = String.valueOf(R.drawable.flavida1)+"-"+String.valueOf(R.drawable.flavida2)+"-"+String.valueOf(R.drawable.flavida3)+"-"+String.valueOf(R.drawable.flavida4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta75+", "+R.string.nombre_comunSeta75+
                    ", "+R.string.nombre_ordenarSeta75+", "+R.string.comestibilidad_seta75+", '"+stringFotos+"', "+R.drawable.flavida_list+")");

            stringFotos = String.valueOf(R.drawable.aeruginosa1)+"-"+String.valueOf(R.drawable.aeruginosa2)+"-"+String.valueOf(R.drawable.aeruginosa3)+"-"+String.valueOf(R.drawable.aeruginosa4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta76+", "+R.string.nombre_comunSeta76+
                    ", "+R.string.nombre_ordenarSeta76+", "+R.string.comestibilidad_seta76+", '"+stringFotos+"', "+R.drawable.aeruginosa_list+")");

            stringFotos = String.valueOf(R.drawable.luteus1)+"-"+String.valueOf(R.drawable.luteus2)+"-"+String.valueOf(R.drawable.luteus3)+"-"+String.valueOf(R.drawable.luteus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta77+", "+R.string.nombre_comunSeta77+
                    ", "+R.string.nombre_ordenarSeta77+", "+R.string.comestibilidad_seta77+", '"+stringFotos+"', "+R.drawable.luteus_list+")");

            stringFotos = String.valueOf(R.drawable.aurantia1)+"-"+String.valueOf(R.drawable.aurantia2)+"-"+String.valueOf(R.drawable.aurantia3)+"-"+String.valueOf(R.drawable.aurantia4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta78+", "+R.string.nombre_comunSeta78+
                    ", "+R.string.nombre_ordenarSeta78+", "+R.string.comestibilidad_seta78+", '"+stringFotos+"', "+R.drawable.aurantia_list+")");

            stringFotos = String.valueOf(R.drawable.equestre1)+"-"+String.valueOf(R.drawable.equestre2)+"-"+String.valueOf(R.drawable.equestre3)+"-"+String.valueOf(R.drawable.equestre4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta79+", "+R.string.nombre_comunSeta79+
                    ", "+R.string.nombre_ordenarSeta79+", "+R.string.comestibilidad_seta79+", '"+stringFotos+"', "+R.drawable.equestre_list+")");

            stringFotos = String.valueOf(R.drawable.portentosum1)+"-"+String.valueOf(R.drawable.portentosum2)+"-"+String.valueOf(R.drawable.portentosum3)+"-"+String.valueOf(R.drawable.portentosum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta80+", "+R.string.nombre_comunSeta80+
                    ", "+R.string.nombre_ordenarSeta80+", "+R.string.comestibilidad_seta80+", '"+stringFotos+"', "+R.drawable.portentosum_list+")");

            stringFotos = String.valueOf(R.drawable.terreum1)+"-"+String.valueOf(R.drawable.terreum2)+"-"+String.valueOf(R.drawable.terreum3)+"-"+String.valueOf(R.drawable.terreum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta81+", "+R.string.nombre_comunSeta81+
                    ", "+R.string.nombre_ordenarSeta81+", "+R.string.comestibilidad_seta81+", '"+stringFotos+"', "+R.drawable.terreum_list+")");

            stringFotos = String.valueOf(R.drawable.melanosporum1)+"-"+String.valueOf(R.drawable.melanosporum2)+"-"+String.valueOf(R.drawable.melanosporum3)+"-"+String.valueOf(R.drawable.melanosporum4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta82+", "+R.string.nombre_comunSeta82+
                    ", "+R.string.nombre_ordenarSeta82+", "+R.string.comestibilidad_seta82+", '"+stringFotos+"', "+R.drawable.melanosporum_list+")");

            stringFotos = String.valueOf(R.drawable.xerocomus1)+"-"+String.valueOf(R.drawable.xerocomus2)+"-"+String.valueOf(R.drawable.xerocomus3)+"-"+String.valueOf(R.drawable.xerocomus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta83+", "+R.string.nombre_comunSeta83+
                    ", "+R.string.nombre_ordenarSeta83+", "+R.string.comestibilidad_seta83+", '"+stringFotos+"', "+R.drawable.xerocomus_list+")");

            stringFotos = String.valueOf(R.drawable.xerocomus1)+"-"+String.valueOf(R.drawable.xerocomus2)+"-"+String.valueOf(R.drawable.xerocomus3)+"-"+String.valueOf(R.drawable.xerocomus4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta84+", "+R.string.nombre_comunSeta84+
                    ", "+R.string.nombre_ordenarSeta84+", "+R.string.comestibilidad_seta84+", '"+stringFotos+"', "+R.drawable.xerocomus_list+")");

            stringFotos = String.valueOf(R.drawable.lacustris1)+"-"+String.valueOf(R.drawable.lacustris2)+"-"+String.valueOf(R.drawable.lacustris3)+"-"+String.valueOf(R.drawable.lacustris4);
            db.execSQL("Insert into Setas values(null, "+ R.string.nombreSeta85+", "+R.string.nombre_comunSeta85+
                    ", "+R.string.nombre_ordenarSeta85+", "+R.string.comestibilidad_seta85+", '"+stringFotos+"', "+R.drawable.lacustris_list+")");



            db.setTransactionSuccessful();
            db.endTransaction();

        }
    }
}
