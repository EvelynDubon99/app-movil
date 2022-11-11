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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Model.Favlug;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavLugService;
import com.example.proyecto.fragments.FechaFragment;


import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavLugAdapter extends RecyclerView.Adapter<FavLugAdapter.ViewHolder>{
    private List<Favlug> mFavlug;
    private Context context;
    private SharedPreferences sharedPreferences;

    public FavLugAdapter(List<Favlug> mFavlug){this.mFavlug = mFavlug;}
    public void reloadData(List<Favlug> favlug){
        this.mFavlug = favlug;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FavLugAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_favlug, parent, false);
        FavLugAdapter.ViewHolder viewHolder = new FavLugAdapter.ViewHolder(contactView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favlug favlug = mFavlug.get(position);
        holder.idFavlug = favlug.get_id();
        CheckBox favs = holder.mFavs;
        TextView nombre = holder.mNombre;
        nombre.setText(favlug.lugar.getNombre());
        TextView departament = holder.mDepartamento;
        departament.setText(favlug.lugar.getDepartamento());
        RatingBar calificacion = holder.mCalificacion;
        if(favlug.lugar.calificacion == null){
            favlug.lugar.calificacion = "0";
        }
        calificacion.setRating(Float.parseFloat(favlug.lugar.calificacion));
        ImageView img = holder.mImge;
        TextView fecha = holder.mFecha;
        fecha.setText(favlug.fecha);


        Glide.with(this.context).load(favlug.lugar.getImg()).into(img);
        if(favlug.getFavoritos() != true){
            favs.setChecked(false);

        }else {
            favs.setChecked(true);
        }
        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavLugService favLugService =  Api.getRetrofitInstance().create(FavLugService.class);
                Call<String> call = favLugService.deletefav(favlug.get_id());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                        String id_user = sharedPreferences.getString("_id"," ");
                        FavLugService fs = Api.getRetrofitInstance().create(FavLugService.class);
                        Call<List<Favlug>> cal = favLugService.getuser(id_user);
                        cal.enqueue(new Callback<List<Favlug>>() {
                            @Override
                            public void onResponse(Call<List<Favlug>> call, Response<List<Favlug>> response) {
                                reloadData(response.body());
                            }

                            @Override
                            public void onFailure(Call<List<Favlug>> call, Throwable t) {

                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(t.toString());
                    }
                });
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setView(R.layout.error);
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
        return mFavlug.size();
    }

    public class ViewHolder extends   RecyclerView.ViewHolder implements View.OnClickListener{
        RatingBar mCalificacion;
        String idFavlug;
        TextView mNombre, mDepartamento, mFecha;
        ImageView mImge;
        CheckBox mFavs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mFavs = (CheckBox) itemView.findViewById(R.id.favs);
            mImge = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView)  itemView.findViewById(R.id.departamento);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mFecha = (TextView) itemView.findViewById(R.id.fecha_vis);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
public void ordnarLista(int i){
        switch (i){
            case 0:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        return f1.lugar.getNombre().compareTo(f2.lugar.getNombre());
                    }
                });
                break;
            case 1:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        return f2.lugar.getNombre().compareTo(f1.lugar.getNombre());
                    }
                });
                break;
            case 2:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        return f1.lugar.getDepartamento().compareTo(f2.lugar.getDepartamento());
                    }
                });
                break;
            case 3:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        return f2.lugar.getDepartamento().compareTo(f1.lugar.getDepartamento());
                    }
                });
                break;
            case 4:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        if(f1.lugar.getCalificacion() == null){
                            f1.lugar.setCalificacion("0");
                        }
                        if(f2.lugar.getCalificacion() == null){
                            f2.lugar.setCalificacion("0");
                        }
                        return f2.lugar.getCalificacion().compareTo(f1.lugar.getCalificacion());
                    }
                });
                break;
            case 5:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        if(f1.lugar.getCalificacion() == null){
                            f1.lugar.setCalificacion("0");
                        }
                        if(f2.lugar.getCalificacion() == null){
                            f2.lugar.setCalificacion("0");
                        }
                        return f1.lugar.getCalificacion().compareTo(f2.lugar.getCalificacion());
                    }
                });
                break;
            case 6:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        if(f1.getFecha() == null){
                            f1.setFecha(" ");
                        }
                        if(f2.getFecha() == null){
                            f2.setFecha(" ");
                        }

                        return f1.getFecha().compareTo(
                                f2.getFecha());
                    }
                });
                break;
            case 7:
                Collections.sort(mFavlug, new Comparator<Favlug>() {
                    @Override
                    public int compare(Favlug f1, Favlug f2) {
                        if(f2.getFecha() == null){
                            f2.setFecha(" ");
                        }
                        if(f1.getFecha() == null){
                            f1.setFecha(" ");
                        }
                        return f2.getFecha().compareTo(
                                f1.getFecha());
                    }
                });
                break;
        }
        notifyDataSetChanged();
}

}
