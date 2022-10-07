package com.example.proyecto.adapters;

import android.content.Context;
import android.content.Intent;
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
import com.example.proyecto.Model.Lugar;
import com.example.proyecto.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LugarAdapter extends RecyclerView.Adapter<LugarAdapter.ViewHolder> {
    private List<Lugar> mLugar;
    private Context context;
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
        View contactView = inflater.inflate(R.layout.item_restaurante, parent, false);
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
        lugarCalificacion.setRating(Float.parseFloat(lugar.calificacion));
        TextView lugarUrl = holder.mUrlImg;
        lugarUrl.setText(lugar.img);
        ImageView lugarImage = holder.mLugarImage;

        Glide.with(this.context).load(lugar.img).into(lugarImage);
    }

    @Override
    public int getItemCount() {
        return mLugar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mLugarImage;
        private TextView mNombre;
        private TextView mDepartamento;
        private RatingBar mCalificacion;
        private TextView mUrlImg;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            mLugarImage = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mUrlImg = (TextView) itemView.findViewById(R.id.url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            Intent intent = new Intent(view.getContext(), ItemsDetail.class);
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
