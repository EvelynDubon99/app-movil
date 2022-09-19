package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class Restaurante {

    @SerializedName("nombre")
    public String nombre;
    @SerializedName("departamento")
    public String departamento;
    @SerializedName("calificacion")
    public String calificacion;
    @SerializedName("img")
    public String img;

    public Restaurante(){

    }

    public Restaurante(String nombre, String departamento, String calificacion,
                        String img){
        this.nombre = nombre;
        this.departamento = departamento;
        this.calificacion = calificacion;
        this.img = img;
    }

}
