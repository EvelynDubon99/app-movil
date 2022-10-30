package com.example.proyecto.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.proyecto.Model.FechaRes;
import com.example.proyecto.Model.User;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.Fechares;
import com.example.proyecto.api.UserService;
import com.example.proyecto.databinding.PerfilupdateBinding;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Perfilmod extends AppCompatDialogFragment {
    private UserService userService;
    private SharedPreferences sharedPreferences;
    private EditText nombre, apellido, correo, numero, contra, confcontra;
    private CountryCodePicker nacionalidad;
    private PerfilupdateBinding binding;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = PerfilupdateBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        nombre =  view.findViewById(R.id.nombre);
        apellido = view.findViewById(R.id.apellido);
        correo = view.findViewById(R.id.correo);
        numero = view.findViewById(R.id.numero);
        nacionalidad = view.findViewById(R.id.nacionalidad);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");

        userService = Api.getRetrofitInstance().create(UserService.class);

        Call<User> call = userService.getUsuario(id_u);
        try{
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    nombre.setText(response.body().getNombre());
                    apellido.setText(response.body().getApellido());
                    correo.setText(response.body().getCorreo());
                    numero.setText(response.body().getNumero());

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    System.out.println(t.toString());

                }
            });}catch (Exception e){
            System.out.println(e.toString());
        }

        builder.setPositiveButton("Actualizar Perfil", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user = new User();
                user.setNombre(nombre.getText().toString());
                user.setApellido(apellido.getText().toString());
                user.setCorreo(correo.getText().toString());
                user.setNumero(numero.getText().toString());

                Call<User> call = userService.updateuser(id_u, user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        nombre.getText().toString();
                        apellido.getText().toString();
                        correo.getText().toString();
                        numero.getText().toString();
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        System.out.println(t);
                    }
                });


            }
        });
        return builder.create();


    }




}
