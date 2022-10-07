package com.example.proyecto.fragments;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LugaresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LugaresFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LugarAdapter adapter = new LugarAdapter(new ArrayList<>());

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Lugar> mLugar;
    private LugarService lugarService;
    Menu menu;


    public LugaresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LugaresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LugaresFragment newInstance(String param1, String param2) {
        LugaresFragment fragment = new LugaresFragment();
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
        View view = inflater.inflate(R.layout.fragment_lugares, container, false);
        lugarService = Api.getRetrofitInstance().create(LugarService.class);
        Call<List<Lugar>> lugarCall = lugarService.getLugar();
        lugarCall.enqueue(new Callback<List<Lugar>>() {
            @Override
            public void onResponse(Call<List<Lugar>> call, Response<List<Lugar>> response) {

                RecyclerView rvLugar = (RecyclerView) view.findViewById(R.id.lugar_list);

                rvLugar.setAdapter(adapter);
                rvLugar.setLayoutManager(new LinearLayoutManager(getContext()));

                adapter.reloadData(response.body());

            }

            @Override
            public void onFailure(Call<List<Lugar>> call, Throwable t) {
                System.out.print("Error");
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
        }


        return super.onOptionsItemSelected(item);
    }

}