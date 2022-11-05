package com.example.proyecto.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Model.Favres;
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
                        mFavres.remove(holder.getAbsoluteAdapterPosition());
                        notifyItemRemoved(holder.getAbsoluteAdapterPosition());
                        notifyDataSetChanged();
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
        TextView mNombre, mDepartamento, mFecha;
        ImageView mImge;
        CheckBox mFavs;
        int posicion2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFavs = (CheckBox) itemView.findViewById(R.id.favs);
            mImge = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView)  itemView.findViewById(R.id.departamento);
            mFecha = (TextView) itemView.findViewById(R.id.fecha_vis);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
    public void ordenar(int i){
        switch (i){
            case 0:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        return f1.getFecha().compareTo(f2.getFecha());
                    }
                });
            case 1:
                Collections.sort(mFavres, new Comparator<Favres>() {
                    @Override
                    public int compare(Favres f1, Favres f2) {
                        return f2.getFecha().compareTo(f1.getFecha());
                    }
                });
        }
    }
}
