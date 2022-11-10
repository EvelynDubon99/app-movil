package com.example.proyecto.api;

import com.example.proyecto.Model.ComenLug;
import com.example.proyecto.Model.Comentario;
import com.example.proyecto.Model.Comentario2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentarioLugService {

    @FormUrlEncoded
    @POST("api/comentlug")
    Call<Comentario2>postComment(@Field("usuario") String usuario,
                                 @Field("lugar") String lugar,
                                 @Field("comentarios") String comentarios,
                                 @Field("calificacion") String calificacion);


    @GET("api/comentlug/lugar/{lugar}")
    Call<List<ComenLug>>getLug(@Path("lugar") String lugar);

    @DELETE("api/comentlug/{id}")
    Call<String>deleteComment(@Path("id") String id);
}
