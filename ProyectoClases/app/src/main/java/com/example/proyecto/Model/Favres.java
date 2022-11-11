package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class Favres {

    public String get_id() {
        return _id;
    }
    public void set_id(String _id) {
        this._id = _id;
    }
    @SerializedName("_id")
    public String _id;
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    @SerializedName("usuario")
    public String usuario;
    public String getUsuario() {
        return usuario;
    }

    @SerializedName("favoritos")
    public Boolean favoritos;

    @SerializedName("restaurante")
    public Restaurante restaurante;
    public Boolean getFavoritos() {
        return favoritos;
    }

    public String getFecha() {


        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    @SerializedName("fecha")
    public String fecha;

    public void setFavoritos(Boolean favoritos) {
        this.favoritos = favoritos;
    }


}
