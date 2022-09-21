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
import com.example.proyecto.Model.Lugar;
import com.example.proyecto.R;

import java.util.List;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.ViewHolder> {
    private List<Lugar> mLugar;
    private Context context;

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
        View contactView = inflater.inflate(R.layout.item_restaurante, parent, false);
        LugarAdapter.ViewHolder viewHolder = new LugarAdapter.ViewHolder(contactView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LugarAdapter.ViewHolder holder, int position) {
        Lugar lugar = mLugar.get(position);
        TextView lugarNameTextView = holder.mNombre;
        lugarNameTextView.setText(lugar.nombre);
        TextView lugarDepartamentoTextView = holder.mDepartamento;
        lugarDepartamentoTextView.setText(lugar.departamento);
        TextView lugarCalificacionTextView = holder.mCalificacion;
        lugarCalificacionTextView.setText(lugar.calificacion);
        ImageView lugarImage = holder.mRestauranteImage;

        Glide.with(this.context).load(lugar.img).into(lugarImage);
    }

    @Override
    public int getItemCount() {
        return mLugar.size();
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
