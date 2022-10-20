package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class ComenLug {
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @SerializedName("_id")
    String _id;
    @SerializedName("lugar")
    public String lugar;

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    @SerializedName("usuario")
    public User user;
    @SerializedName("comentarios")
    public String comentarios;
    @SerializedName("calificacion")
    public String calificacion;
}
