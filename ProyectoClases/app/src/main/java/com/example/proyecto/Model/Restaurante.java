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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @SerializedName("_id")
    public String _id;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("departamento")
    public String departamento;
    @SerializedName("calificacion")
    public String calificacion;
    @SerializedName("img")
    public String img;
    @SerializedName("descripcion")
    public String descripcion;
    @SerializedName("coordenadax")
    public String coordenadax;
    @SerializedName("coordenaday")
    public String coordenaday;

    public String getWaze() {
        return waze;
    }

    public void setWaze(String waze) {
        this.waze = waze;
    }

    @SerializedName("waze")
    public String waze;

    public String getNombre() {
        return nombre;
    }

    public String getDepartamento() {
        return departamento;
    }

    public String getCalificacion() {
        return calificacion;
    }
    public String getImg() {
        return img;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCoordenadax() {
        return coordenadax;
    }

    public void setCoordenadax(String coordenadax) {
        this.coordenadax = coordenadax;
    }

    public String getCoordenaday() {
        return coordenaday;
    }

    public void setCoordenaday(String coordenaday) {
        this.coordenaday = coordenaday;
    }
    public Restaurante(){

    }

    public Restaurante(String nombre, String departamento, String calificacion,
                        String img, String descripcion, String coordenadax,
                       String coordenaday){
        this.nombre = nombre;
        this.departamento = departamento;
        this.calificacion = calificacion;
        this.img = img;
        this.descripcion = descripcion;
        this.coordenadax = coordenadax;
        this.coordenaday = coordenaday;
    }

}
