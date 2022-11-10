package com.example.proyecto.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.example.proyecto.Model.Favres;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavResService;
import com.example.proyecto.api.RestauranteService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CercaResAdapter extends RecyclerView.Adapter<CercaResAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private Context context;
    Restaurante restaurante;
    private List<Restaurante> restaurantesList = new ArrayList<>();
    SharedPreferences sharedPreferences;


    public CercaResAdapter(List<Restaurante> mRestaurante){
        this.mRestaurante = mRestaurante;
    }
    public void reloadData(List<Restaurante> restaurantes){
        this.mRestaurante = restaurantes;
        this.restaurantesList.addAll(restaurantes);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CercaResAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_cercares, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Restaurante restaurante = mRestaurante.get(position);
        Float disnt = restaurante.distancia;
        TextView distan = holder.mDistanccia;
        distan.setText(Float.toString(restaurante.distancia) +" "+ "km");
        TextView restauranteName = holder.mNombre;
        restauranteName.setText(restaurante.nombre);
        TextView restauranteDepartamento = holder.mDepartamento;
        restauranteDepartamento.setText(restaurante.departamento);
        RatingBar restauranteCalificacion = holder.mCalificacion;
        if(restaurante.calificacion == null){
            restaurante.calificacion = "0";
        }
        restauranteCalificacion.setRating(Float.parseFloat(restaurante.calificacion));
        TextView restauranteUrl = holder.mUrlImg;
        restauranteUrl.setText(restaurante.img);

        ImageView restauranteImage = holder.mRestauranteImage;
        holder._id = restaurante.get_id();
        holder.coordenadax = restaurante.getCoordenadax();
        holder.coordenaday = restaurante.getCoordenaday();
        holder.waze = restaurante.getWaze();
        holder.des = restaurante.getDescripcion();
        holder.cal = restaurante.getCalificacion();
        Glide.with(this.context).load(restaurante.img).into(restauranteImage);

    }
    public void ditancia(int i){
        switch (i){
            case 0:
                mRestaurante = restaurantesList;
                List<Restaurante> mCopia = new ArrayList<>();
                mCopia.clear();
                for (Restaurante restaurente:
                     mRestaurante ) {
                    if(restaurente.getDistancia() <= 5){
                        mCopia.add(restaurente);
                    }
                }

                this.mRestaurante = mCopia;
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.distancia.compareTo(r2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 1:
                mRestaurante = restaurantesList;
                List<Restaurante> mCopia2 = new ArrayList<>();
                mCopia2.clear();
                for (Restaurante restaurente:
                        mRestaurante ) {
                    if(restaurente.getDistancia() <= 10){
                        mCopia2.add(restaurente);
                    }
                }
                this.mRestaurante = mCopia2;
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.distancia.compareTo(r2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 2:
                mRestaurante = restaurantesList;
                List<Restaurante> mCopia3 = new ArrayList<>();
                mCopia3.clear();
                for (Restaurante restaurente:
                        mRestaurante ) {
                    if(restaurente.getDistancia() <= 20){
                        mCopia3.add(restaurente);
                    }
                }
                this.mRestaurante = mCopia3;
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.distancia.compareTo(r2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 3:
                mRestaurante = restaurantesList;
                List<Restaurante> mCopia4 = new ArrayList<>();
                mCopia4.clear();
                for (Restaurante restaurente:
                        mRestaurante ) {
                    if( restaurente.getDistancia() > 20){
                        if(restaurente.getDistancia() <= 50){
                            mCopia4.add(restaurente);
                        }

                    }
                }
                this.mRestaurante = mCopia4;
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.distancia.compareTo(r2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 4:
                mRestaurante = restaurantesList;
                List<Restaurante> mCopia5 = new ArrayList<>();
                mCopia5.clear();
                for (Restaurante restaurente:
                        mRestaurante ) {
                    if(restaurente.getDistancia() > 50){
                        if (restaurante.getDistancia() <=100){
                            mCopia5.add(restaurente);
                        }
                    }
                }
                this.mRestaurante = mCopia5;
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.distancia.compareTo(r2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;

        }

    }
    @Override
    public int getItemCount() {
        return mRestaurante.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private String _id, coordenadax, coordenaday, waze, des, cal;
        private ImageView mRestauranteImage;
        private TextView mNombre, mFecha, mDistanccia;
        private TextView mDepartamento;
        private RatingBar mCalificacion;
        private TextView mUrlImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCalificacion = (RatingBar) itemView.findViewById(R.id.calif);
            mDistanccia = (TextView) itemView.findViewById(R.id.distancia);
            mRestauranteImage = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mUrlImg = (TextView) itemView.findViewById(R.id.url);
        }
    }






}
