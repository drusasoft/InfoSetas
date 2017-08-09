package com.dssoft.infosetas.pojos;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Angel on 02/06/2017.
 */

public class SetaFireBase implements Parcelable
{

    private String nombre;
    private String nombre_comun;
    private String habitat;
    private String comestibilidad;
    private String observaciones;
    private String descripcion;

    protected SetaFireBase()
    {

    }

    protected SetaFireBase(Parcel in) {
        nombre = in.readString();
        nombre_comun = in.readString();
        habitat = in.readString();
        comestibilidad = in.readString();
        observaciones = in.readString();
        descripcion = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(nombre_comun);
        dest.writeString(habitat);
        dest.writeString(comestibilidad);
        dest.writeString(observaciones);
        dest.writeString(descripcion);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SetaFireBase> CREATOR = new Creator<SetaFireBase>() {
        @Override
        public SetaFireBase createFromParcel(Parcel in) {
            return new SetaFireBase(in);
        }

        @Override
        public SetaFireBase[] newArray(int size) {
            return new SetaFireBase[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre_comun() {
        return nombre_comun;
    }

    public void setNombre_comun(String nombre_comun) {
        this.nombre_comun = nombre_comun;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getComestibilidad() {
        return comestibilidad;
    }

    public void setComestibilidad(String comestibilidad) {
        this.comestibilidad = comestibilidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }


}
