package com.example.proyecto.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.example.proyecto.Home;
import com.example.proyecto.ItemDetalleLug;
import com.example.proyecto.ItemsDetail;
import com.example.proyecto.Model.Favlug;
import com.example.proyecto.Model.Lugar;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavLugService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.ViewHolder> {
    private List<Lugar> mLugar;

    private Context context;
    private SharedPreferences sharedPreferences;
    Menu menu;

    public LugarAdapter(List<Lugar> mLugar){ this.mLugar = mLugar;}

    public void reloadData(List<Lugar> lugar){
        this.mLugar = lugar;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LugarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_lugar, parent, false);
        LugarAdapter.ViewHolder viewHolder = new LugarAdapter.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LugarAdapter.ViewHolder holder, int position) {

        Lugar lugar = mLugar.get(position);
        TextView lugarName = holder.mNombre;
        lugarName.setText(lugar.nombre);
        TextView lugarDepartamento = holder.mDepartamento;
        lugarDepartamento.setText(lugar.departamento);
        RatingBar lugarCalificacion = holder.mCalificacion;
        if(lugar.calificacion == null){
            lugar.calificacion = "0";
        }
        lugarCalificacion.setRating(Float.parseFloat(lugar.calificacion));
        TextView lugarUrl = holder.mUrlImg;
        lugarUrl.setText(lugar.img);
        ImageView lugarImage = holder.mLugarImage;
        CheckBox favs = holder.mFavs;
        holder._id = lugar.get_id();
        holder.coordenadax =lugar.getCoordenadax();
        holder.coordenaday = lugar.getCoordenaday();
        holder.waze = lugar.getWaze();
        holder.descripcion = lugar.getDescripcion();
        holder.cal = lugar.getCalificacion();
        Glide.with(this.context).load(lugar.img).into(lugarImage);

        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favlug favlug = new Favlug();
                sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("_id"," ");

                String id_lugar = lugar.get_id();
                FavLugService favLugService = Api.getRetrofitInstance().create(FavLugService.class);
                Call<Favlug> call = favLugService.postfav(id_user, id_lugar);
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
        return mLugar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private  String _id, coordenadax, coordenaday, waze, descripcion, cal;
        private ImageView mLugarImage;
        private TextView mNombre, mDepartamento, mUrlImg;
        private CheckBox mFavs;
        private RatingBar mCalificacion;


        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mFavs = (CheckBox) itemView.findViewById(R.id.fav);
            mLugarImage = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mUrlImg = (TextView) itemView.findViewById(R.id.url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), ItemDetalleLug.class);
            intent.putExtra("id",_id);
            intent.putExtra("des", descripcion);
            intent.putExtra("cal", cal);
            intent.putExtra("latitud", coordenadax);
            intent.putExtra("longitud", coordenaday);
            intent.putExtra("waze", waze);
            intent.putExtra("descripcion", descripcion);
            intent.putExtra("eNombre",mNombre.getText().toString());
            intent.putExtra("eDepartamento", mDepartamento.getText().toString());
            intent.putExtra("eUrl", mUrlImg.getText().toString());
            view.getContext().startActivity(intent);
        }

    }
    public void ordnarLista(int i) {
        switch (i){
            case 0:
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar r1, Lugar r2) {
                        return r1.getNombre().compareTo(r2.getNombre());
                    }
                });
                break;
            case 1:
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar r1, Lugar r2) {
                        return r2.getNombre().compareTo(r1.getNombre());
                    }
                });
                break;
            case 2:
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar r1, Lugar r2) {
                        return r1.getDepartamento().compareTo(r2.getDepartamento());
                    }
                });
                break;
            case 3:
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar r1, Lugar r2) {
                        return r2.getDepartamento().compareTo(r1.getDepartamento());
                    }
                });
                break;
            case 4:
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar r1, Lugar r2) {
                        return r2.getCalificacion().compareTo(r1.getCalificacion()) ;
                    }
                });
                break;
            case 5:
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar r1, Lugar r2) {
                        return r1.getCalificacion().compareTo(r2.getCalificacion());
                    }
                });


        }
        notifyDataSetChanged();

    }

}
