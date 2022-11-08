package com.example.proyecto.api;

import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.Model.Ubicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestauranteService {
    @GET("api/restaurante/{userId}")
    Call<List<Restaurante>> getRestaurante(@Path("userId") String userId);


    @GET("api/restaurante/{userId}/{milatitud}/{milongitud}")
    Call<List<Restaurante>> getRestauranteCerca(@Path("userId") String userId,
                                                @Path("milatitud") Number milatitud,
                                                @Path("milongitud") Number milongitud);







}
