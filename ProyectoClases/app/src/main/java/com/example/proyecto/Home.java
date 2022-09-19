package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.adapters.RestauranteAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.RestauranteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {

    private List<Restaurante> mRestaurante;
    private RestauranteService restauranteService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        restauranteService = Api.getRetrofitInstance().create(RestauranteService.class);
        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Restaurante>> restauranteCall = restauranteService.getRestaurante();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }
}