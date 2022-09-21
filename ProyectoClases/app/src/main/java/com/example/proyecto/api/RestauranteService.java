package com.example.proyecto.api;

import com.example.proyecto.Model.Restaurante;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestauranteService {
    @GET("api/restaurante")
    Call<List<Restaurante>> getRestaurante();
    @GET("api/restaurante/mas")
    Call<List<Restaurante>> getMasPopular();
    @GET("api/restaurante/menos")
    Call<List<Restaurante>> getMenosPopular();
    @GET("api/restaurante/acDep")
    Call<List<Restaurante>> getACdep();
    @GET("api/restaurante/opDep")
    Call<List<Restaurante>> getOpdep();
    @GET("api/restaurante/acNom")
    Call<List<Restaurante>> getACnom();
    @GET("api/restaurante/opNom")
    Call<List<Restaurante>> getOpnom();
}
