package com.example.proyecto.api;

import com.example.proyecto.Model.Favlug;
import com.example.proyecto.Model.Favres;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FavLugService {
    @GET("/api/favlug/usuario/{usuario}")
    Call<List<Favlug>>getuser(@Path("usuario") String usuario);

    @FormUrlEncoded
    @POST("api/favlug")
    Call<Favlug>postfav(@Field("usuario") String usuario,
                        @Field("lugar") String lugar);

    @DELETE("api/favlug/{id}")
    Call<String>deletefav(@Path("id") String id);


}
