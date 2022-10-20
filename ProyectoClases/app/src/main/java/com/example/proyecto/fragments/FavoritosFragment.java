package com.example.proyecto.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoritosFragment extends Fragment {
  private FavResAdapter adapter = new FavResAdapter(new ArrayList<>());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Favres> mFavres;
    private FavResService favResService;
    private Favres favres;
    private SharedPreferences sharedPreferences;

    public FavoritosFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
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


        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favoritos, container, false);

        favResService = Api.getRetrofitInstance().create(FavResService.class);
        RecyclerView rvFavres = (RecyclerView) view.findViewById(R.id.favres_list);
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
}