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
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ComLugarFragment extends Fragment implements View.OnClickListener {
    private ComenLugAdapter adapter = new ComenLugAdapter(new ArrayList<>());




    private List<ComenLug> mComenlug;
    private ComentarioLugService comentarioLugService;
    private ComenLug comenLug;
    private SharedPreferences sharedPreferences;
    private FloatingActionButton comment;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_com_lugar, container, false);
        Bundle bundle = getActivity().getIntent().getExtras();
        comment = view.findViewById(R.id.comment);
        comment.setOnClickListener(this);

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
    @Override
    public void onResume(){
        super.onResume();
        Bundle bundle = getActivity().getIntent().getExtras();
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
    }
    @Override
    public void onClick(View view) {
        Bundle bundle = getActivity().getIntent().getExtras();
        Comment comment = new Comment(adapter);
        comment.show(getParentFragmentManager(), "comentario");
    }
}