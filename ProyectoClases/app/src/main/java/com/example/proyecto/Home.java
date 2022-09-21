package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button lugares;
    private Button mas;
    private Button menos;
    private Button acDep;
    private Button opDep;
    private Button acNom;
    private Button opNom;
    private RestauranteAdapter restauranteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        lugares = findViewById(R.id.lugares);
        mas = findViewById(R.id.mas);
        menos = findViewById(R.id.menos);
        acDep = findViewById(R.id.acDep);
        opDep = findViewById(R.id.opDep);
        acNom = findViewById(R.id.acNom);
        opNom = findViewById(R.id.opNom);
        lugares.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Lugares.class));
            }
        });

        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                masPopulares(view);
            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menosPopulares(view);
            }
        });
        acDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acDepartamento(view);
            }
        });
        opDep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opDepartamento(view);
            }
        });
        acNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                acNombre(view);
            }
        });
        opNom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                opNombre(view);
            }
        });

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

    private void masPopulares(View view) {

        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Restaurante>> restauranteCall = restauranteService.getMasPopular();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void menosPopulares(View view) {

        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Restaurante>> restauranteCall = restauranteService.getMenosPopular();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void acDepartamento(View view) {

        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Restaurante>> restauranteCall = restauranteService.getACdep();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void opDepartamento(View view) {

        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Restaurante>> restauranteCall = restauranteService.getOpdep();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void acNombre(View view) {

        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Restaurante>> restauranteCall = restauranteService.getACnom();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void opNombre(View view) {

        RecyclerView rvRestau = (RecyclerView) findViewById(R.id.restaurante_list);

        RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(this));
        Call<List<Restaurante>> restauranteCall = restauranteService.getOpnom();
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                Toast.makeText(Home.this, "Error al obtener los restaurantes", Toast.LENGTH_SHORT).show();
            }
        });

    }


}




