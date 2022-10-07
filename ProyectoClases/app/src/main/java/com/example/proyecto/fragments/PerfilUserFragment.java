package com.example.proyecto.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.Login;
import com.example.proyecto.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUserFragment extends Fragment implements View.OnClickListener {
    TextView correos;
    Button cerrar;

    SharedPreferences sharedPreferences;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public PerfilUserFragment() {
        // Required empty public constructor
    }

    public static PerfilUserFragment newInstance(String param1, String param2) {
        PerfilUserFragment fragment = new PerfilUserFragment();
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
        View view = inflater.inflate(R.layout.fragment_perfil_user, container, false);
        cerrar = view.findViewById(R.id.cerrar);
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);

        cerrar.setOnClickListener(this);



        // Inflate the layout for this fragment
        return view ;
    }




    @Override
    public void onClick(View view) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();

        getActivity().finish();
    }
}