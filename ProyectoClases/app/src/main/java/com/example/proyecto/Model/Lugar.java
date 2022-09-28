package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class Lugar {
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("departamento")
    public String departamento;
    @SerializedName("calificacion")
    public String calificacion;
    @SerializedName("img")
    public String img;

    public Lugar(){

    }

    public Lugar(String nombre, String departamento, String calificacion,
                       String img){
        this.nombre = nombre;
        this.departamento = departamento;
        this.calificacion = calificacion;
        this.img = img;
    }
}
