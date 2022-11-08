package com.example.proyecto.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.Model.Ubicacion;
import com.example.proyecto.R;
import com.example.proyecto.adapters.CercaResAdapter;
import com.example.proyecto.api.Api;
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


public class CercademiResFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private List<Restaurante> mRestaurante;
    private RestauranteService restauranteService;
    private SharedPreferences sharedPreferences;
    private TextView milatitud2, milongitud2;
    private Number milatitud, milongitud;
    CercaResAdapter adapter = new CercaResAdapter(new ArrayList<>());
    RecyclerView rvRestau;
    FusedLocationProviderClient fusedLocationProviderClient;


    public CercademiResFragment() {
        // Required empty public constructor
    }


    public static CercademiResFragment newInstance(String param1, String param2) {
        CercademiResFragment fragment = new CercademiResFragment();
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
        View view = inflater.inflate(R.layout.fragment_cercademi_res, container, false);
        milatitud2 = view.findViewById(R.id.milatitud);
        milongitud2 = view.findViewById(R.id.milongitud);
        milatitud = 14.514777685376021;
        milongitud = -90.57205469408528;
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
                           milatitud2.setText(""+ addresses.get(0).getLatitude());

                            milongitud2.setText(""+ addresses.get(0).getLongitude());

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
        rvRestau = (RecyclerView) view.findViewById(R.id.restaurante_list);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        restauranteService = Api.getRetrofitInstance().create(RestauranteService.class);
        rvRestau.setAdapter(adapter);
        rvRestau.setLayoutManager(new LinearLayoutManager(getContext()));

        Call<List<Restaurante>> call = restauranteService.getRestauranteCerca(id_u, milatitud, milongitud);
        call.enqueue(new Callback<List<Restaurante>>() {
            @Override
            public void onResponse(Call<List<Restaurante>> call, Response<List<Restaurante>> response) {
                adapter.reloadData(response.body());
                System.out.println(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Restaurante>> call, Throwable t) {

            }
        });

        return view;
    }
}