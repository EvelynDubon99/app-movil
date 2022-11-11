package com.example.proyecto.api;

import com.example.proyecto.Model.Lugar;
import com.example.proyecto.Model.Restaurante;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LugarService {
    @GET("api/lugar/{userId}")
    Call<List<Lugar>> getLugar(@Path("userId")String userId);

    @GET("api/lugar/{userId}/{milatitud}/{milongitud}")
    Call<List<Lugar>> getLugar2(@Path("userId")String userId,
                                @Path("milatitud") Number milatitud,
                                @Path("milongitud") Number milongitud);


}
