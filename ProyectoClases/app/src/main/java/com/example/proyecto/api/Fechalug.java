package com.example.proyecto.api;

import com.example.proyecto.Model.FechaLug;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Fechalug {
    @FormUrlEncoded
    @POST("api/fechalug")
    Call<FechaLug> postFecha(@Field("usuario")String usuario,
                             @Field("lugar")String lugar,
                             @Field("fecha")String fecha);
}
