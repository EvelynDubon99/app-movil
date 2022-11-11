package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class Favoritos {
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @SerializedName("_id")
    public String _id;
    @SerializedName("usuario")
    public String usuario;

    public String getUsuario() {
        return usuario;
    }


    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    @SerializedName("lugar")
    public String lugar;


    public Boolean getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }

    @SerializedName("favoritos")
    public Boolean favoritos;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @SerializedName("fecha")
    public String fecha;
}
