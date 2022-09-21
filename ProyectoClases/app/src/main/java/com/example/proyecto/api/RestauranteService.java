package com.example.proyecto.api;

import com.example.proyecto.Model.Restaurante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestauranteService {
    @GET("api/restaurante")
    Call<List<Restaurante>> getRestaurante();
    @GET("api/mas")
    Call<List<Restaurante>> getMasPopular();
    @GET("api/menos")
    Call<List<Restaurante>> getMenosPopular();
    @GET("api/acDep")
    Call<List<Restaurante>> getACdep();
    @GET("api/opDep")
    Call<List<Restaurante>> getOpdep();
    @GET("api/acNom")
    Call<List<Restaurante>> getACnom();
    @GET("api/opNom")
    Call<List<Restaurante>> getOpnom();
}
