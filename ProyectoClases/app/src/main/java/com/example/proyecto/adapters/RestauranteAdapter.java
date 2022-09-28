package com.example.proyecto.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Home;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;

import java.util.List;

public class RestauranteAdapter extends RecyclerView.Adapter<RestauranteAdapter.ViewHolder> {
    private List<Restaurante> mRestaurante;
    private Context context;

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
        TextView restauranteNameTextView = holder.mNombre;
        restauranteNameTextView.setText(restaurante.nombre);
        TextView restauranteDepartamentoTextView = holder.mDepartamento;
        restauranteDepartamentoTextView.setText(restaurante.departamento);
        TextView restauranteCalificacionTextView = holder.mCalificacion;
        restauranteCalificacionTextView.setText(restaurante.calificacion);
        ImageView restauranteImage = holder.mRestauranteImage;

        Glide.with(this.context).load(restaurante.img).into(restauranteImage);
    }

    @Override
    public int getItemCount() {
        return mRestaurante.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mRestauranteImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private TextView mCalificacion;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            mRestauranteImage = (ImageView) itemView.findViewById(R.id.restaurente_image);
            mNombre = (TextView) itemView.findViewById(R.id.restaurente_name);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (TextView) itemView.findViewById(R.id.calificacion);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), Home.class);
            view.getContext().startActivity(intent);
        }

    }
}
