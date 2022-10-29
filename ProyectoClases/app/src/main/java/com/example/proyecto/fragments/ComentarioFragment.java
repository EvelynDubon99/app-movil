package com.example.proyecto.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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
import com.example.proyecto.databinding.FragmentComentarioBinding;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComentarioFragment extends Fragment implements View.OnClickListener{
    private FragmentComentarioBinding binding;
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
    private FloatingActionButton comment;
    private SharedPreferences sharedPreferences;



    public ComentarioFragment() {
        // Required empty public constructor
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

        binding = FragmentComentarioBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        Bundle bundle = getActivity().getIntent().getExtras();
        delete = view.findViewById(R.id.delete);
        comment = view.findViewById(R.id.comment);
        comment.setOnClickListener(this);






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



    @Override
    public void onClick(View view) {
        Bundle bundle = getActivity().getIntent().getExtras();
        DialogComment dialogComment = new DialogComment(adapter);
        dialogComment.show(getParentFragmentManager(), "comentario");

    }
}