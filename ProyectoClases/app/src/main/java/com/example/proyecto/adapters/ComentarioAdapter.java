package com.example.proyecto.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Favoritos;
import com.example.proyecto.Home;
import com.example.proyecto.Login;

import com.example.proyecto.Model.Comentario;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioService;
import com.example.proyecto.fragments.ComentarioFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.nio.MappedByteBuffer;
import java.util.List;
import java.util.Objects;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.ViewHolder> {
    private List<Comentario> mComentario;
    private Context context;
    private SharedPreferences sharedPreferences;
    private ComentarioFragment comentarioFragment ;




    public ComentarioAdapter(List<Comentario> mComentario){this.mComentario = mComentario;}

    public void reloadData(List<Comentario> comentario){
        this.mComentario = comentario;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ComentarioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();

        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_comentario, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioAdapter.ViewHolder holder, int position) {
        Comentario comentario = mComentario.get(position);
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        String id = comentario.user.get_id();
        TextView nombre = holder.mNombre;
        Button delete = holder.mDelete;
        nombre.setText(comentario.user.getNombre());
        TextView apellido = holder.mApellido;
        apellido.setText(comentario.user.getApellido());
        TextView commen =holder.mComment;
        commen.setText(comentario.comentarios);
        RatingBar calificacion = holder.mCalificacion;
        if(comentario.calificacion == null){
            comentario.calificacion = "0";
        }
        calificacion.setRating(Float.parseFloat(comentario.calificacion));

        if(Objects.equals(id_u, id)){
            delete.setVisibility(View.VISIBLE);
        }
        holder.posicion = holder.getAbsoluteAdapterPosition();

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ComentarioService comentarioService = Api.getRetrofitInstance().create(ComentarioService.class);
                Call<String> comCall = comentarioService.deleteComment(comentario.get_id());
                comCall.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        mComentario.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                        notifyDataSetChanged();



                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setView(R.layout.comentarioeliminado);
                dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog = dialog.create();
                alertDialog.show();
            }
        });



    }


    @Override
    public int getItemCount() {
        return mComentario.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mNombre, mApellido, mComment;
        private RatingBar mCalificacion;
        private Button mDelete;
        int posicion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDelete = (Button) itemView.findViewById(R.id.delete);
            mNombre = (TextView) itemView.findViewById(R.id.nombre);
            mApellido = (TextView) itemView.findViewById(R.id.apellido);
            mComment  = (TextView) itemView.findViewById(R.id.commen);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
