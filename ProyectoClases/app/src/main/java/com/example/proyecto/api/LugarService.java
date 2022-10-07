package com.example.proyecto.api;

import com.example.proyecto.Model.Lugar;
import com.example.proyecto.Model.Restaurante;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LugarService {
    @GET("api/lugar")
    Call<List<Lugar>> getLugar();

}
