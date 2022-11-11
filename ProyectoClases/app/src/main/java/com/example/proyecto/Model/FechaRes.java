package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class FechaRes {
    @SerializedName("usuario")
    public User user;
    @SerializedName("restaurante")
    public Restaurante restaurante;

    public String getFecha() {
        return fecha;
    }

    @SerializedName("fecha")
    public String fecha;
}
