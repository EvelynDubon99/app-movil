package com.example.proyecto.Model;


import com.google.gson.annotations.SerializedName;

public class Comentario {


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @SerializedName("_id")
    String _id;
    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }


    public String getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(String restaurante) {
        this.restaurante = restaurante;
    }

    @SerializedName("restaurante")
    public String restaurante;
    @SerializedName("usuario")
    public User user;

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    @SerializedName("comentarios")
    public String comentarios;
    @SerializedName("calificacion")
    public String calificacion;


}
