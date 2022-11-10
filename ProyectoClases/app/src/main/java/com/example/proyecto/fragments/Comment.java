package com.example.proyecto.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.proyecto.Model.ComenLug;
import com.example.proyecto.Model.Comentario;
import com.example.proyecto.Model.Comentario2;
import com.example.proyecto.Model.ComentarioModel;
import com.example.proyecto.R;
import com.example.proyecto.adapters.ComenLugAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioLugService;
import com.example.proyecto.api.ComentarioService;
import com.example.proyecto.databinding.ComentarioBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Comment extends AppCompatDialogFragment {
    private EditText escomment;
    private RatingBar calif;
    private TextView id_res, id_user;
    private Button comments;
    private ComentarioBinding binding;
    private SharedPreferences sharedPreferences;
    ComenLugAdapter adapter;
    public Comment(ComenLugAdapter adapter){this.adapter = adapter;}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        binding = ComentarioBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        android.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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

        builder.setPositiveButton("Agregar Comentario", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                comentario(

                        id_user.getText().toString(),
                        id_res.getText().toString(),
                        escomment.getText().toString(),
                        String.valueOf(calif.getRating())

                );

            }
        });

        return  builder.create();
    }

    public void comentario( String id_user, String id_res, String escomment, String calif) {
        ComentarioLugService comentarioLugService = Api.getRetrofitInstance().create(ComentarioLugService.class);
        Call<Comentario2> call = comentarioLugService.postComment(id_user, id_res, escomment, calif);
        call.enqueue(new Callback<Comentario2>() {
            @Override
            public void onResponse(Call<Comentario2> call, Response<Comentario2> response) {
                Call<List<ComenLug>> comCall = comentarioLugService.getLug(id_res);
                comCall.enqueue(new Callback<List<ComenLug>>() {
                    @Override
                    public void onResponse(Call<List<ComenLug>> call, Response<List<ComenLug>> response) {
                        adapter.reloadData(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<ComenLug>> call, Throwable t) {
                        System.out.print(t);
                    }
                });

            }

            @Override
            public void onFailure(Call<Comentario2> call, Throwable t) {
                System.out.println(t.toString());
            }
        });


    }
}
