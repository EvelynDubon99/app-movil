package com.example.proyecto.api;

import com.example.proyecto.Model.Comentario;
import com.example.proyecto.Model.ComentarioModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ComentarioService {
    @FormUrlEncoded
    @POST("api/coment")
    Call<ComentarioModel>postComment(@Field("usuario") String usuario,
                                     @Field("restaurante") String restaurante,
                                     @Field("comentarios") String comentarios,
                                     @Field("calificacion") String calificacion
                                );



    @GET("api/coment/restaurante/{restaurante}")
    Call<List<Comentario>>getRes(@Path("restaurante") String restaurante);

    @DELETE("api/coment/{id}")
    Call<String>deleteComment(@Path("id") String id);
}
