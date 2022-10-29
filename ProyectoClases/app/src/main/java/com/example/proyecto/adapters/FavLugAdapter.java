package com.example.proyecto.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavLugAdapter extends RecyclerView.Adapter<FavLugAdapter.ViewHolder>{
    private List<Favlug> mFavlug;
    private Context context;

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
    public void onBindViewHolder(@NonNull FavLugAdapter.ViewHolder holder, int position) {
        Favlug favlug = mFavlug.get(position);
        CheckBox favs = holder.mFavs;
        TextView nombre = holder.mNombre;
        nombre.setText(favlug.lugar.getNombre());
        TextView departament = holder.mDepartamento;
        departament.setText(favlug.lugar.getDepartamento());
        ImageView img = holder.mImge;
        TextView fecha = holder.mFecha;
        fecha.setText(favlug.fecha);
        if(favlug.fecha == null){
            fecha.setText("Fecha de visita");
        }
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
                Call<Favlug> call = favLugService.deletefav(favlug.get_id());
                call.enqueue(new Callback<Favlug>() {
                    @Override
                    public void onResponse(Call<Favlug> call, Response<Favlug> response) {
                        Favlug favlug1 = response.body();
                        Intent intent = new Intent(context, Favoritos.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Favlug> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFavlug.size();
    }

    public class ViewHolder extends   RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mNombre, mDepartamento, mFecha;
        Button mFechaVis;
        ImageView mImge;
        CheckBox mFavs;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mFechaVis = (Button) itemView.findViewById(R.id.fecha);
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
}
