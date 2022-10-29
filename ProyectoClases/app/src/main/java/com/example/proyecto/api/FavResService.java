package com.example.proyecto.api;

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

public interface FavResService {
    @GET("api/fav/usuario/{usuario}")
    Call<List<Favres>>getuser(@Path("usuario") String usuario);

    @FormUrlEncoded
    @POST("api/fav")
    Call<Favres>postfav(@Field("usuario") String usuario,
                            @Field("restaurante") String restaurante
                            );

    @DELETE("api/fav/{id}")
    Call<String>deletefav(@Path("id")String id);

    @DELETE("api/fav/{id}")
    Call<Favres>deletefav2(@Path("id")String id);


}
