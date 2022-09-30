package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class Restaurante {
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @SerializedName("nombre")
    public String nombre;
    @SerializedName("departamento")
    public String departamento;
    @SerializedName("calificacion")
    public String calificacion;
    @SerializedName("img")
    public String img;

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCalificacion() {
        return calificacion;
    }

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
