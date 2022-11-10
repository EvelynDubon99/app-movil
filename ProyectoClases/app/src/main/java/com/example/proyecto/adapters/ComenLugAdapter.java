package com.example.proyecto.adapters;

import android.app.AlertDialog;
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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyecto.Favoritos;
import com.example.proyecto.Home;
import com.example.proyecto.Model.ComenLug;
import com.example.proyecto.Model.Comentario;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioLugService;
import com.example.proyecto.api.ComentarioService;


import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComenLugAdapter extends RecyclerView.Adapter<ComenLugAdapter.ViewHolder> {
    private List<ComenLug> mComenLug;
    private Context context;
    private SharedPreferences sharedPreferences;

    public ComenLugAdapter(List<ComenLug> mComenLug){this.mComenLug = mComenLug;}
    public void reloadData(List<ComenLug> comentario){
        this.mComenLug = comentario;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_comentario, parent, false);
        ComenLugAdapter.ViewHolder viewHolder = new ComenLugAdapter.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ComenLug comenLug = mComenLug.get(position);
        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        String id = comenLug.user.get_id();
        Button delete = holder.mDelete;
        TextView nombre = holder.mNombre;
        nombre.setText(comenLug.user.getNombre());
        TextView apellido = holder.mApellido;
        apellido.setText(comenLug.user.getApellido());
        TextView commen =holder.mComment;
        commen.setText(comenLug.comentarios);
        RatingBar calificacion = holder.mCalificacion;
        if(comenLug.calificacion == null){
            comenLug.calificacion = "0";
        }
        calificacion.setRating(Float.parseFloat(comenLug.calificacion));
        if(Objects.equals(id_u, id)){
            delete.setVisibility(View.VISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setView(R.layout.comentarioeliminado);
                dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ComentarioLugService comentarioLugService = Api.getRetrofitInstance().create(ComentarioLugService.class);
                        Call<String> comCall = comentarioLugService.deleteComment(comenLug.get_id());
                        comCall.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                mComenLug.remove(holder.getAbsoluteAdapterPosition());
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
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        return mComenLug.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNombre, mApellido, mComment;
        private RatingBar mCalificacion;
        private Button mDelete;

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
