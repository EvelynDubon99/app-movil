package com.example.proyecto.Model;

import com.google.gson.annotations.SerializedName;

public class FechaLug {
    @SerializedName("usuario")
    public User user;
    @SerializedName("ligar")
    public Lugar lugar;
    @SerializedName("fecha")
    public String fecha;
}
