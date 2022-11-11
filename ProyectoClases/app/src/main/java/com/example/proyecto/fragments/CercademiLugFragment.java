package com.example.proyecto.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.Model.Lugar;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;
import com.example.proyecto.adapters.CercaLugAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.LugarService;
import com.example.proyecto.api.RestauranteService;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CercademiLugFragment extends Fragment {


    private List<Lugar> mLugar;
    private LugarService lugarService;
    private SharedPreferences sharedPreferences;
    private Number milatitud, milongitud;
    private CercaLugAdapter adapter = new CercaLugAdapter(new ArrayList<>());
    private RecyclerView rvLug;
    private  FusedLocationProviderClient fusedLocationProviderClient;
    private Menu menu;

    public CercademiLugFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_cercademi_lug, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        Geocoder geocoder = new Geocoder(getContext(),
                                Locale.getDefault());
                        List<Address> addresses = null;
                        try {

                            addresses = geocoder.getFromLocation(
                                    location.getLatitude(), location.getLongitude(), 1

                            );

                            milatitud = addresses.get(0).getLatitude();
                            milongitud = addresses.get(0).getLongitude();
                            rvLug = (RecyclerView) view.findViewById(R.id.lugar_list);
                            sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                            String id_u = sharedPreferences.getString("_id", " ");
                            lugarService = Api.getRetrofitInstance().create(LugarService.class);
                            rvLug.setAdapter(adapter);
                            rvLug.setLayoutManager(new LinearLayoutManager(getContext()));

                            Call<List<Lugar>> call = lugarService.getLugar2(id_u, milatitud, milongitud);
                            call.enqueue(new Callback<List<Lugar>>() {
                                @Override
                                public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {
                                    adapter.reloadData(response.body());
                                    System.out.println(response.body());
                                    adapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<List<Lugar>> call, Throwable t) {

                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION},44);

        }
        return view;
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.cercademi_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.cinco){
            adapter.ditancia(0);
        }if(id == R.id.diez){
            adapter.ditancia(1);
        }if(id == R.id.veinte){
            adapter.ditancia(2);
        }if(id == R.id.cincuenta){
            adapter.ditancia(3);
        }if(id == R.id.cien){
            adapter.ditancia(4);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onResume() {
        super.onResume();

    }
}