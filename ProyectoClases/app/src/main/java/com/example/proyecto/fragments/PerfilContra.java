package com.example.proyecto.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.proyecto.Model.User;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.UserService;
import com.example.proyecto.databinding.PerfilcontraBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilContra extends AppCompatDialogFragment {
    private SharedPreferences sharedPreferences;
    private UserService userService;
    private EditText contra, confirm;
    private PerfilcontraBinding binding;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = PerfilcontraBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        contra = view.findViewById(R.id.pass);
        confirm = view.findViewById(R.id.confir);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        builder.setPositiveButton("Cambiar Contraseña", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                User user = new User();
                user.setContra(contra.getText().toString());
                userService = Api.getRetrofitInstance().create(UserService.class);
                Call<User> call = userService.updateuser(id_u, user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        contra.setText(user.contra);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });


        return builder.create();
    }
}
