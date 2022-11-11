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
import com.example.proyecto.Model.Comentario;
import com.example.proyecto.Model.Favres;
import com.example.proyecto.R;
import com.example.proyecto.adapters.FavResAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavResService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FavoritosFragment extends Fragment {
  private FavResAdapter adapter = new FavResAdapter(new ArrayList<>());

    private List<Favres> mFavres;
    private FavResService favResService;
    private Favres favres;
    private SharedPreferences sharedPreferences;
    private Menu menu;
    RecyclerView rvFavres;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        favResService = Api.getRetrofitInstance().create(FavResService.class);
        rvFavres = (RecyclerView) view.findViewById(R.id.favres_list);
        rvFavres.setAdapter(adapter);
        rvFavres.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<Favres>> comCall = favResService.getuser(id_u);
        comCall.enqueue(new Callback<List<Favres>>() {
            @Override
            public void onResponse(Call<List<Favres>> call, Response<List<Favres>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Favres>> call, Throwable t) {

                System.out.print(t);
            }
        });




        return view;
    }

    @Override
    public void onResume(){
        super.onResume();
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        Call<List<Favres>> comCall = favResService.getuser(id_u);
        comCall.enqueue(new Callback<List<Favres>>() {
            @Override
            public void onResponse(Call<List<Favres>> call, Response<List<Favres>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Favres>> call, Throwable t) {

                System.out.print(t);
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.sort2_menu, menu);
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
        } if (id == R.id.ultima_fecha){
            adapter.ordnarLista(6);
        }if(id == R.id.fecha_reciente){
            adapter.ordnarLista(7);
        }


        rvFavres.scrollToPosition(0);

        return super.onOptionsItemSelected(item);
    }
}