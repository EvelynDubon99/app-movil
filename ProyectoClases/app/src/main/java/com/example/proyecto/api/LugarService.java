package com.example.proyecto.api;

import com.example.proyecto.Model.Lugar;
import com.example.proyecto.Model.Restaurante;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LugarService {
    @GET("api/lugar")
    Call<List<Lugar>> getLugar();
    @GET("api/lugar/mas")
    Call<List<Lugar>> getMasPopular();
    @GET("api/lugar/menos")
    Call<List<Lugar>> getMenosPopular();
    @GET("api/lugar/acDep")
    Call<List<Lugar>> getACdep();
    @GET("api/lugar/opDep")
    Call<List<Lugar>> getOpdep();
    @GET("api/lugar/acNom")
    Call<List<Lugar>> getACnom();
    @GET("api/lugar/opNom")
    Call<List<Lugar>> getOpnom();
}
