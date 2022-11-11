package com.example.proyecto.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.Cercademi;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Model.Lugar;
import com.example.proyecto.R;
import com.example.proyecto.adapters.LugarAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.LugarService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LugaresFragment extends Fragment {

    LugarAdapter adapter = new LugarAdapter(new ArrayList<>());


    private List<Lugar> mLugar;
    private LugarService lugarService;
    Menu menu;
    RecyclerView rvLugar;
    private SharedPreferences sharedPreferences;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_lugares, container, false);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        lugarService = Api.getRetrofitInstance().create(LugarService.class);
        rvLugar = (RecyclerView) view.findViewById(R.id.lugar_list);
        rvLugar.setAdapter(adapter);
        rvLugar.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<Lugar>> lugarCall = lugarService.getLugar(id_u);
        lugarCall.enqueue(new Callback<List<Lugar>>() {
            @Override
            public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<Lugar>> call, Throwable t) {
                System.out.print(t.toString());
            }
        });
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aznombre){
            adapter.ordnarLista(0);
        } if (id == R.id.za_nombre){
            adapter.ordnarLista(1);
        }if (id == R.id.az_departamento){

            adapter.ordnarLista(2);
        }if (id == R.id.za_departamento){

            adapter.ordnarLista(3);
        }if (id == R.id.mas_populares){

            adapter.ordnarLista(4);
        }if (id == R.id.menos_populares){

            adapter.ordnarLista(5);
        }if (id == R.id.ultima_fecha){
            adapter.ordnarLista(6);
        }if(id == R.id.fecha_reciente){
            adapter.ordnarLista(7);
        }if(id == R.id.favorito){
            Intent intent = new Intent(getContext(), Favoritos.class);
            startActivity(intent);
        }if(id == R.id.Cerca_mi){
            Intent intent = new Intent(getContext(), Cercademi.class);
            startActivity(intent);
        }
        rvLugar.scrollToPosition(0);


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        Call<List<Lugar>> lugarCall = lugarService.getLugar(id_u);
        lugarCall.enqueue(new Callback<List<Lugar>>() {
            @Override
            public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();



            }

            @Override
            public void onFailure(Call<List<Lugar>> call, Throwable t) {
                System.out.print(t.toString());
            }
        });
    }
}