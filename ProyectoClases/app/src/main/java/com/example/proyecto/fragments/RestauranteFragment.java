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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.proyecto.Cercademi;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Home;
import com.example.proyecto.LoadingDialog;
import com.example.proyecto.Login;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;
import com.example.proyecto.adapters.RestauranteAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.RestauranteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RestauranteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestauranteFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RestauranteAdapter adapter = new RestauranteAdapter(new ArrayList<>());
    RecyclerView rvRestau;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Restaurante> mRestaurante;
    private RestauranteService restauranteService;

    Menu menu;
    private SharedPreferences sharedPreferences;


    public RestauranteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestauranteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestauranteFragment newInstance(String param1, String param2) {
        RestauranteFragment fragment = new RestauranteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View view = inflater.inflate(R.layout.fragment_restaurante, container, false);
        rvRestau= (RecyclerView) view.findViewById(R.id.restaurante_list);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        restauranteService = Api.getRetrofitInstance().create(RestauranteService.class);
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<Restaurante>> restauranteCall = restauranteService.getRestaurante(id_u);
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
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
        } if (id == R.id.ultima_fecha){
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


        rvRestau.scrollToPosition(0);

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        Call<List<Restaurante>> restauranteCall = restauranteService.getRestaurante(id_u);
        restauranteCall.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {
                System.out.print(t.toString());
            }
        });
    }
}