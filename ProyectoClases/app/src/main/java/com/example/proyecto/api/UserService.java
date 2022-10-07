package com.example.proyecto.api;


import com.example.proyecto.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserService {
    @FormUrlEncoded
    @POST("api/users")
    Call<User>postUser(
                       @Field("nombre") String nombre,
                       @Field("apellido") String apellido,
                       @Field("correo") String correo,
                       @Field("nacionalidad") String nacionalidad,
                       @Field("numero") String numero,
                       @Field("contra") String contra,
                       @Field("confcontra") String confcontra
                       );


    @FormUrlEncoded
    @POST("api/users/login")
    Call<User>postLogin(@Field("correo") String correo,
                         @Field("contra") String contra);
}

