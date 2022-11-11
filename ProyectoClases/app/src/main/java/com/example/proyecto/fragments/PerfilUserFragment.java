package com.example.proyecto.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.Model.User;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.UserService;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PerfilUserFragment extends Fragment implements View.OnClickListener {
    private UserService userService;
    private Button cerrar, contras;
    private SharedPreferences sharedPreferences;
    private TextInputEditText nombre, correo, nacionalidad, telefono;
    private User user;
    private TextView nombre_user, apellido_user;
    private FloatingActionButton editPer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil_user, container, false);
        cerrar = view.findViewById(R.id.cerrar);
        nombre = view.findViewById(R.id.nombre_completo);
        correo = view.findViewById(R.id.correo);
        nacionalidad = view.findViewById(R.id.nacionalidad);
        telefono = view.findViewById(R.id.telefono);
        nombre_user = view.findViewById(R.id.nombre_user);
        apellido_user = view.findViewById(R.id.apellido_user);
        editPer = view.findViewById(R.id.editPer);
        contras = view.findViewById(R.id.contras);
        cerrar.setOnClickListener(this);
        contras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PerfilContra perfilContra = new PerfilContra();
                perfilContra.show(getParentFragmentManager(), "updatecontra");
            }
        });

        editPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perfilmod perfilmod = new Perfilmod(nombre_user, apellido_user, nombre, correo, telefono);
                perfilmod.show(getParentFragmentManager(), "update");
            }
        });



        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");

        userService = Api.getRetrofitInstance().create(UserService.class);

        Call<User> call = userService.getUsuario(id_u);
        try{
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println(response.body());
                nombre_user.setText(response.body().getNombre());
                apellido_user.setText(response.body().getApellido());
                nombre.setText(response.body().getNombre() + " "+response.body().getApellido());
                nacionalidad.setText(response.body().getNacionalidad());
                telefono.setText(response.body().getNumero());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.toString());

            }
        });}catch (Exception e){
            System.out.println(e.toString());
        }







        // Inflate the layout for this fragment
        return view ;
    }



    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        Call<User> call = userService.getUsuario(id_u);
        try{
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    System.out.println(response.body());
                    nombre_user.setText(response.body().getNombre());
                    apellido_user.setText(response.body().getApellido());
                    nombre.setText(response.body().getNombre() + " "+response.body().getApellido());
                    correo.setText(response.body().getCorreo());
                    nacionalidad.setText(response.body().getNacionalidad());
                    telefono.setText(response.body().getNumero());

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println(t.toString());

                }
            });}catch (Exception e){
            System.out.println(e.toString());
        }

    }
    @Override
    public void onClick(View view) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        getActivity().finish();
    }


}