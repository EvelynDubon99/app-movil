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
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.Model.Comentario;
import com.example.proyecto.R;
import com.example.proyecto.adapters.ComentarioAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioService;
import com.example.proyecto.api.RestauranteService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComentarioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComentarioFragment extends Fragment {
    private ComentarioAdapter adapter = new ComentarioAdapter(new ArrayList<>());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<Comentario> mComentario;
    private ComentarioService comentarioService;
    private Comentario comentario;
    private Button delete;
    private SharedPreferences sharedPreferences;


    public ComentarioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComentarioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComentarioFragment newInstance(String param1, String param2) {
        ComentarioFragment fragment = new ComentarioFragment();
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


        View view = inflater.inflate(R.layout.fragment_comentario, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        delete = view.findViewById(R.id.delete);




        comentarioService = Api.getRetrofitInstance().create(ComentarioService.class);
        RecyclerView rvComent = (RecyclerView) view.findViewById(R.id.comentario_list);
        rvComent.setAdapter(adapter);
        rvComent.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<Comentario>> comCall = comentarioService.getRes(bundle.getString("id"));
        comCall.enqueue(new Callback<List<Comentario>>() {
            @Override
            public void onResponse(Call<List<Comentario>> call, Response<List<Comentario>> response) {

                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Comentario>> call, Throwable t) {
                System.out.print(t);
            }
        });
        return view;
    }


}