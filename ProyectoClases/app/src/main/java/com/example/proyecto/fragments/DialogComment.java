package com.example.proyecto.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.proyecto.Model.Comentario;
import com.example.proyecto.Model.ComentarioModel;
import com.example.proyecto.R;
import com.example.proyecto.adapters.ComentarioAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioService;
import com.example.proyecto.databinding.ComentarioBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DialogComment extends AppCompatDialogFragment{

    private EditText escomment;
    private RatingBar calif;
    private TextView id_res, id_user;
    private Button comments;
    private ComentarioBinding binding;
    private SharedPreferences sharedPreferences;
    ComentarioAdapter adapter;

    public DialogComment(ComentarioAdapter adapter) {
        this.adapter = adapter;
    }


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
        ComentarioService comentarioService = Api.getRetrofitInstance().create(ComentarioService.class);
        Call<ComentarioModel> call = comentarioService.postComment(id_user, id_res, escomment, calif);
        call.enqueue(new Callback<ComentarioModel>() {
            @Override
            public void onResponse(Call<ComentarioModel> call, Response<ComentarioModel> response) {

                Call<List<Comentario>> comCall = comentarioService.getRes(id_res);
                comCall.enqueue(new Callback<List<Comentario>>() {
                    @Override
                    public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {

                        adapter.reloadData(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Comentario>> call, Throwable t) {
                        System.out.print(t);
                    }
                });




            }

            @Override
            public void onFailure(Call<ComentarioModel> call, Throwable t) {
                System.out.println(t.toString());

            }
        });




    }
}
