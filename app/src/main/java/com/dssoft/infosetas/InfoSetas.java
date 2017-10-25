package com.dssoft.infosetas;

import android.app.Application;

import com.dssoft.infosetas.modelo.BDAdapter;
import com.dssoft.infosetas.modelo.DataManagerBD;
import com.dssoft.infosetas.modelo.DataManagerFB;
import com.dssoft.infosetas.modelo.DataManagerWS;

/**
 * Created by Angel on 11/09/2017.
 */

public class InfoSetas extends Application
{

    DataManagerBD dataManagerBD;
    DataManagerFB dataManagerFB;
    DataManagerWS dataManagerWS;

    @Override
    public void onCreate()
    {
        super.onCreate();

        BDAdapter bdAdapter = new BDAdapter(getApplicationContext());
        dataManagerBD = new DataManagerBD(bdAdapter);

        dataManagerFB = new DataManagerFB();

        dataManagerWS = new DataManagerWS();

    }

    public DataManagerBD getDataManagerBD()
    {
        return  dataManagerBD;
    }

    public DataManagerFB getDataManagerFB() {
        return dataManagerFB;
    }

    public DataManagerWS getDataManagerWS() {
        return dataManagerWS;
    }
}
