package com.example.proyecto.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.proyecto.Home;
import com.example.proyecto.ItemsDetail;
import com.example.proyecto.Model.Comentario;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.Model.User;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioService;
import com.example.proyecto.databinding.ComentarioBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogComment extends AppCompatDialogFragment {
    private EditText escomment;
    private RatingBar calif;
    private TextView id_res, id_user;
    private Button comments;
    private ComentarioBinding binding;
    private SharedPreferences sharedPreferences;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = ComentarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle bundle = getActivity().getIntent().getExtras();
        builder.setView(view);
        escomment = view.findViewById(R.id.escomment);
        calif = view.findViewById(R.id.calif);
        id_res = view.findViewById(R.id.id_res);
        id_res.setText(bundle.getString("id"));
        id_user = view.findViewById(R.id.id_user);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        id_user.setText(id_u);




        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                comentario(

                        id_user.getText().toString(),
                        id_res.getText().toString(),
                        escomment.getText().toString(),
                        String.valueOf(calif.getRating())

                );
                Intent intent = new Intent(getContext(), ComentarioFragment.class);
                startActivity(intent);


            }
        });

        return  builder.create();



    }
    public void comentario( String id_user, String id_res, String escomment, String calif) {
        ComentarioService comentarioService = Api.getRetrofitInstance().create(ComentarioService.class);
        Call<Comentario> call = comentarioService.postComment(id_user, id_res, escomment, calif);
        call.enqueue(new Callback<Comentario>() {
            @Override
            public void onResponse(Call<Comentario> call, Response<Comentario> response) {
                Comentario comentario = response.body();

            }

            @Override
            public void onFailure(Call<Comentario> call, Throwable t) {

            }
        });


    }
}
