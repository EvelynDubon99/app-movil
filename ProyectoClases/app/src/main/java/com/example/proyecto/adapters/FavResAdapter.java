package com.example.proyecto.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Model.Favres;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavResService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavResAdapter extends RecyclerView.Adapter<FavResAdapter.ViewHolder>  {
    private List<Favres> mFavres;
    private Context context;
    private SharedPreferences sharedPreferences;


    public FavResAdapter(List<Favres> mFavres){
        this.mFavres = mFavres;
    }

    public void reloadData(List<Favres> favres){
        this.mFavres = favres;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavResAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_favres, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favres favres = mFavres.get(position);
        holder.idFavres = favres.get_id();
        CheckBox favs = holder.mFavs;
        TextView nombre = holder.mNombre;
        nombre.setText(favres.restaurante.getNombre());
        TextView departament = holder.mDepartamento;
        departament.setText(favres.restaurante.getDepartamento());
        ImageView img = holder.mImge;
        TextView fecha = holder.mFecha;
        fecha.setText(favres.fecha);
        RatingBar calificacion = holder.mCalificacion;
        if(favres.restaurante.calificacion == null){
            favres.restaurante.calificacion = "0";
        }
        calificacion.setRating(Float.parseFloat(favres.restaurante.calificacion));
        Glide.with(this.context).load(favres.restaurante.getImg()).into(img);

        if(favres.getFavoritos() != true){
            favs.setChecked(false);

        }else {
            favs.setChecked(true);
        }

        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FavResService favResService = Api.getRetrofitInstance().create(FavResService.class);
                Call<String> call = favResService.deletefav(favres.get_id());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                        String id_user = sharedPreferences.getString("_id"," ");
                        FavResService fs = Api.getRetrofitInstance().create(FavResService.class);
                        Call<List<Favres>> comCall = favResService.getuser(id_user);
                        comCall.enqueue(new Callback<List<Favres>>() {
                            @Override
                            public void onResponse(Call<List<Favres>> call, Response<List<Favres>> response) {
                                reloadData(response.body());

                            }

                            @Override
                            public void onFailure(Call<List<Favres>> call, Throwable t) {

                                System.out.print(t);
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
        return mFavres.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        String idFavres;
        RatingBar mCalificacion;
        TextView mNombre, mDepartamento, mFecha;
        ImageView mImge;
        CheckBox mFavs;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mFavs = (CheckBox) itemView.findViewById(R.id.favs);
            mImge = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mDepartamento = (TextView)  itemView.findViewById(R.id.departamento);
            mFecha = (TextView) itemView.findViewById(R.id.fecha_vis);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    public void ordnarLista(int i) {
        switch (i){
            case 0:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        return f1.restaurante.getNombre().compareTo(
                                f2.restaurante.getNombre());
                    }
                });
                break;
            case 1:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        return f2.restaurante.getNombre().compareTo(
                                f1.restaurante.getNombre());
                    }
                });
                break;
            case 2:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        return f1.restaurante.getDepartamento().compareTo(
                                f2.restaurante.getDepartamento());
                    }
                });
                break;
            case 3:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        return f2.restaurante.getDepartamento().compareTo(
                                f1.restaurante.getDepartamento());
                    }
                });
                break;
            case 4:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        if(f1.restaurante.getCalificacion() == null){
                            f1.restaurante.setCalificacion("0");
                        }
                        if(f2.restaurante.getCalificacion() == null){
                            f2.restaurante.setCalificacion("0");
                        }
                        return f2.restaurante.getCalificacion().compareTo(
                                f1.restaurante.getCalificacion());
                    }
                });
                break;
            case 5:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        if(f1.restaurante.getCalificacion() == null){
                            f1.restaurante.setCalificacion("0");
                        }
                        if(f2.restaurante.getCalificacion() == null){
                            f2.restaurante.setCalificacion("0");
                        }
                        return f1.restaurante.getCalificacion().compareTo(
                                f2.restaurante.getCalificacion());
                    }
                });
            case 6:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
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
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {

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
