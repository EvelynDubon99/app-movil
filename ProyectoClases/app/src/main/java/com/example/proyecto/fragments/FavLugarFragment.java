package com.example.proyecto.fragments;

import android.content.Context;
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

import com.example.proyecto.Model.Favlug;
import com.example.proyecto.R;
import com.example.proyecto.adapters.FavLugAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavLugService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavLugarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavLugarFragment extends Fragment {
    private FavLugAdapter adapter = new FavLugAdapter(new ArrayList<>());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Favlug> mFavlug;
    private FavLugService favLugService;
    private SharedPreferences sharedPreferences;
    RecyclerView rvFavres;
    Menu menu;

    public FavLugarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavLugarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavLugarFragment newInstance(String param1, String param2) {
        FavLugarFragment fragment = new FavLugarFragment();
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
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        View view = inflater.inflate(R.layout.fragment_fav_lugar, container, false);
        favLugService = Api.getRetrofitInstance().create(FavLugService.class);
        rvFavres = (RecyclerView) view.findViewById(R.id.favres_list);
        rvFavres.setAdapter(adapter);
        rvFavres.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<Favlug>> comCall = favLugService.getuser(id_u);
        comCall.enqueue(new Callback<List<Favlug>>() {
            @Override
            public void onResponse(Call<List<Favlug>> call, Response<List<Favlug>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Favlug>> call, Throwable t) {
                System.out.print(t.toString());

            }
        });

        return view;
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
            adapter.ordnarLista(7);
        }if(id == R.id.fecha_reciente){
            adapter.ordnarLista(6);
        }


        rvFavres.scrollToPosition(0);

        return super.onOptionsItemSelected(item);
    }
}