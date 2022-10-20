package com.example.proyecto.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.NumberRangeFormatter;
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
import com.example.proyecto.ItemsDetail;
import com.example.proyecto.Model.Favlug;
import com.example.proyecto.Model.Favres;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavLugService;
import com.example.proyecto.api.FavResService;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private Context context;
    Restaurante restaurante;
    FavResService favResService;
    Menu menu;
    SharedPreferences sharedPreferences;


    public RestauranteAdapter(List<Restaurante> mRestaurante){ this.mRestaurante = mRestaurante;}

    public void reloadData(List<Restaurante> restaurante){
        this.mRestaurante = restaurante;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestauranteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_restaurante, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RestauranteAdapter.ViewHolder holder, int position) {

        Restaurante restaurante = mRestaurante.get(position);
        CheckBox favs = holder.mFavs;
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
        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Favres favres = new Favres();
                sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
                String id_user = sharedPreferences.getString("_id"," ");

                String id_res= restaurante.get_id();
                FavResService favResService = Api.getRetrofitInstance().create(FavResService.class);
                Call<Favres> call = favResService.postfav(id_user, id_res);
                call.enqueue(new Callback<Favres>() {
                    @Override
                    public void onResponse(Call<Favres> call, Response<Favres> response) {
                        Favres favres1 = response.body();
                        Intent intent = new Intent(context, Favoritos.class);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Favres> call, Throwable t) {

                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String _id, coordenadax, coordenaday, waze, des, cal;
        private ImageView mRestauranteImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private RatingBar mCalificacion;
        private TextView mUrlImg;
        private CheckBox mFavs;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calif);
            mFavs = (CheckBox) itemView.findViewById(R.id.fav);
            mRestauranteImage = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mUrlImg = (TextView) itemView.findViewById(R.id.url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){

            Intent intent = new Intent(view.getContext(), ItemsDetail.class);
            intent.putExtra("id",_id);
            intent.putExtra("cal", cal );
            intent.putExtra("des", des);
            intent.putExtra("latitud", coordenadax);
            intent.putExtra("longitud", coordenaday);
            intent.putExtra("waze", waze);
            intent.putExtra("eNombre",mNombre.getText().toString());
            intent.putExtra("eDepartamento", mDepartamento.getText().toString());
            intent.putExtra("eUrl", mUrlImg.getText().toString());
             view.getContext().startActivity(intent);
        }

    }


    public void ordnarLista(int i) {
        switch (i){
            case 0:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.getNombre().compareTo(r2.getNombre());
                    }
                });
                break;
            case 1:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r2.getNombre().compareTo(r1.getNombre());
                    }
                });
                break;
            case 2:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.getDepartamento().compareTo(r2.getDepartamento());
                    }
                });
                break;
            case 3:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r2.getDepartamento().compareTo(r1.getDepartamento());
                    }
                });
                break;
            case 4:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r2.getCalificacion().compareTo(r1.getCalificacion()) ;
                    }
                });
                break;
            case 5:
                Collections.sort(mRestaurante, new Comparator<Restaurante>() {
                    @Override
                    public int compare(Restaurante r1, Restaurante r2) {
                        return r1.getCalificacion().compareTo(r2.getCalificacion());
                    }
                });


        }
        notifyDataSetChanged();

    }



}
