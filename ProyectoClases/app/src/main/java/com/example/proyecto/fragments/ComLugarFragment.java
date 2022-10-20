package com.example.proyecto.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyecto.Model.ComenLug;
import com.example.proyecto.R;
import com.example.proyecto.adapters.ComenLugAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.ComentarioLugService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComLugarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComLugarFragment extends Fragment {
    private ComenLugAdapter adapter = new ComenLugAdapter(new ArrayList<>());

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<ComenLug> mComenlug;
    private ComentarioLugService comentarioLugService;
    private ComenLug comenLug;
    private SharedPreferences sharedPreferences;

    public ComLugarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComLugarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComLugarFragment newInstance(String param1, String param2) {
        ComLugarFragment fragment = new ComLugarFragment();
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
        View view = inflater.inflate(R.layout.fragment_com_lugar, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        comentarioLugService = Api.getRetrofitInstance().create(ComentarioLugService.class);
        RecyclerView rvComent = (RecyclerView) view.findViewById(R.id.comentario_list);
        rvComent.setAdapter(adapter);
        rvComent.setLayoutManager(new LinearLayoutManager(getContext()));
        Call<List<ComenLug>> call = comentarioLugService.getLug(bundle.getString("id"));
        call.enqueue(new Callback<List<ComenLug>>() {
            @Override
            public void onResponse(Call<List<ComenLug>> call, Response<List<ComenLug>> response) {
                adapter.reloadData(response.body());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ComenLug>> call, Throwable t) {

            }
        });



        return view;
    }
}