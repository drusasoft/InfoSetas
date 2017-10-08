package com.dssoft.infosetas.pojos;

import android.text.Spannable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Angel on 23/05/2017.
 */

public class Seta
{

    private String nombre;
    private String nombre_comun;
    private String nombre_ordenar;
    private String comestible;
    private String fotos;
    private int foto_lista;
    private boolean favorita;
    private Spannable spanableNombre;
    private Spannable spanableNombreComun;

    public Seta()
    {

    }

    public Seta(String nombre, String nombre_comun, String nombre_ordenar, String comestible, String fotos, int foto_lista)
    {
        this.nombre = nombre;
        this.nombre_comun = nombre_comun;
        this.nombre_ordenar = nombre_ordenar;
        this.comestible = comestible;
        this.fotos = fotos;
        this.foto_lista = foto_lista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre_comun() {
        return nombre_comun;
    }

    public void setNombre_comun(String nombre_comun) {
        this.nombre_comun = nombre_comun;
    }

    public String getNombre_ordenar() {
        return nombre_ordenar;
    }

    public void setNombre_ordenar(String nombre_ordenar) {
        this.nombre_ordenar = nombre_ordenar;
    }

    public String getComestible() {
        return comestible;
    }

    public void setComestible(String comestible) {
        this.comestible = comestible;
    }

    public String getFotos() {
        return fotos;
    }

    public void setFotos(String fotos) {
        this.fotos = fotos;
    }

    public int getFoto_lista() {
        return foto_lista;
    }

    public void setFoto_lista(int foto_lista) {
        this.foto_lista = foto_lista;
    }

    public boolean isFavorita() {
        return favorita;
    }

    public void setFavorita(boolean favorita) {
        this.favorita = favorita;
    }

    public Spannable getSpanableNombre() {
        return spanableNombre;
    }

    public void setSpanableNombre(Spannable spanableNombre) {
        this.spanableNombre = spanableNombre;
    }

    public Spannable getSpanableNombreComun() {
        return spanableNombreComun;
    }

    public void setSpanableNombreComun(Spannable spanableNombreComun) {
        this.spanableNombreComun = spanableNombreComun;
    }
}
