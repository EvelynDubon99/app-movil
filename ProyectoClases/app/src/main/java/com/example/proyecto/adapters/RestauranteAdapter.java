package com.example.proyecto.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.number.NumberRangeFormatter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Home;
import com.example.proyecto.ItemsDetail;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private Context context;
    Restaurante restaurante;
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


        Glide.with(this.context).load(restaurante.img).into(restauranteImage);
    }

    @Override
    public int getItemCount() {
        return mRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private String _id;
        private ImageView mRestauranteImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private RatingBar mCalificacion;
        private TextView mUrlImg;

        public ViewHolder(@NonNull View itemView){
            super(itemView);


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
