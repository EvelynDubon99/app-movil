package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.proyecto.Model.Lugar;
import com.example.proyecto.adapters.LugarAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.LugarService;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Lugares extends AppCompatActivity {

    private List<Lugar> mLugar;
    private LugarService lugarService;
    private Button restaurantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares);
        restaurantes = findViewById(R.id.restaurantes);

        restaurantes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Lugares.this, Home.class));
            }
        });

        lugarService = Api.getRetrofitInstance().create(LugarService.class);
        RecyclerView rvLugar = (RecyclerView) findViewById(R.id.lugar_list);

        LugarAdapter adapter = new LugarAdapter(new ArrayList<>());
        rvLugar.setAdapter(adapter);
        rvLugar.setLayoutManager(new LinearLayoutManager(this));

        Call<List<Lugar>> lugarCall = lugarService.getLugar();
        lugarCall.enqueue(new Callback<List<Lugar>>() {
            @Override
            public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                adapter.reloadData(response.body());

            }

            @Override
            public void onFailure(Call<List<Lugar>> call, Throwable t) {
                Toast.makeText(Lugares.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }

}