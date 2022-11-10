package com.example.proyecto.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Model.Lugar;
import com.example.proyecto.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CercaLugAdapter extends RecyclerView.Adapter<CercaLugAdapter.ViewHolder> {
    private List<Lugar> mLugar;
    private Context context;
    private Lugar lugar;
    private List<Lugar> lugarList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    public CercaLugAdapter(List<Lugar> mLugar){this.mLugar = mLugar;}
    public void reloadData(List<Lugar> lugar){
        this.mLugar = lugar;
        this.lugarList.addAll(lugar);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CercaLugAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View contactView = inflater.inflate(R.layout.item_cercalug, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lugar lugar = mLugar.get(position);
        Float disnt = lugar.distancia;
        TextView distan = holder.mDistancia;
        distan.setText(Float.toString(lugar.distancia) + " " + "km");
        TextView nombre = holder.mNombre;
        nombre.setText(lugar.nombre);
        TextView departamento = holder.mDepartamento;
        departamento.setText(lugar.departamento);
        RatingBar calif = holder.mCalificacion;
        if (lugar.calificacion == null){
            lugar.calificacion = "0";
        }
        calif.setRating(Float.parseFloat(lugar.calificacion));
        TextView url = holder.mUrlImg;
        url.setText(lugar.img);
        ImageView imgen = holder.mImg;
        holder._id = lugar.get_id();
        holder.coordenadax =lugar.getCoordenadax();
        holder.coordenaday = lugar.getCoordenaday();
        holder.waze = lugar.getWaze();
        holder.cal = lugar.getCalificacion();
        Glide.with(this.context).load(lugar.img).into(imgen);


    }
    public void ditancia(int i){
        switch (i){
            case 0:
                mLugar = lugarList;
                List<Lugar> mC = new ArrayList<>();
                mC.clear();
                for (Lugar lugar:
                mLugar){
                    if (lugar.getDistancia() <= 5){
                        mC.add(lugar);
                    }
                }
                this.mLugar = mC;
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar l1, Lugar l2) {
                        return l1.distancia.compareTo(l2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 1:
                mLugar = lugarList;
                List<Lugar> mCo = new ArrayList<>();
                mCo.clear();
                for (Lugar lugar:
                        mLugar){
                    if (lugar.getDistancia()  <= 10){
                        mCo.add(lugar);
                    }
                }
                this.mLugar = mCo;
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar l1, Lugar l2) {
                        return l1.distancia.compareTo(l2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 2:
                mLugar = lugarList;
                List<Lugar> mCop = new ArrayList<>();
                mCop.clear();
                for (Lugar lugar:
                        mLugar){
                    if (lugar.getDistancia()<= 20){
                        mCop.add(lugar);
                    }
                }
                this.mLugar = mCop;
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar l1, Lugar l2) {
                        return l1.distancia.compareTo(l2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 3:
                mLugar = lugarList;
                List<Lugar> mCopi = new ArrayList<>();
                mCopi.clear();
                for (Lugar lugar:
                        mLugar){
                    if (lugar.getDistancia() > 20){
                        if (lugar.getDistancia() <= 50 ){
                            mCopi.add(lugar);}
                    }
                }

                this.mLugar = mCopi;
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar l1, Lugar l2) {
                        return l1.distancia.compareTo(l2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
            case 4:
                mLugar = lugarList;
                List<Lugar> mCopia = new ArrayList<>();
                mCopia.clear();
                for (Lugar lugar:
                        mLugar){
                    if (lugar.getDistancia() > 50){
                        if(lugar.getDistancia()  <=100){
                        mCopia.add(lugar);}
                    }
                }
                this.mLugar = mCopia;
                Collections.sort(mLugar, new Comparator<Lugar>() {
                    @Override
                    public int compare(Lugar l1, Lugar l2) {
                        return l1.distancia.compareTo(l2.distancia);
                    }
                });
                notifyDataSetChanged();
                break;
        }
    }
    @Override
    public int getItemCount() {
        return mLugar.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private String _id, coordenadax, coordenaday, waze, des, cal;
        private ImageView mImg;
        private TextView mNombre, mDepartamento, mDistancia, mUrlImg;
        private RatingBar mCalificacion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calif);
            mDistancia = (TextView) itemView.findViewById(R.id.distancia);
            mImg = (ImageView) itemView.findViewById(R.id.image);
            mNombre = (TextView) itemView.findViewById(R.id.name);
            mDepartamento = (TextView) itemView.findViewById(R.id.departamento);
            mCalificacion = (RatingBar) itemView.findViewById(R.id.calificacion);
            mUrlImg = (TextView) itemView.findViewById(R.id.url);
        }
    }
}
