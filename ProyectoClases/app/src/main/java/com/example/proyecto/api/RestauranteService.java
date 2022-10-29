package com.example.proyecto.api;

import com.example.proyecto.Model.Restaurante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestauranteService {
    @GET("api/restaurante/{userId}")
    Call<List<Restaurante>> getRestaurante(@Path("userId") String userId);






}
