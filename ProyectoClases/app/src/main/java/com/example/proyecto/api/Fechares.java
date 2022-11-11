package com.example.proyecto.api;

import com.example.proyecto.Model.FechaRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Fechares {
    @FormUrlEncoded
    @POST("api/fechares")
    Call<FechaRes>postFecha(@Field("usuario")String usuario,
                            @Field("restaurante")String restaurante,
                            @Field("fecha")String fecha);
}
