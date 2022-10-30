package com.example.proyecto.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyecto.Model.User;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.UserService;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilUserFragment extends Fragment implements View.OnClickListener {
    private UserService userService;
    private Button cerrar;
    private SharedPreferences sharedPreferences;
    private TextInputEditText nombre, correo, nacionalidad, telefono;
    private User user;
    private TextView nombre_user, apellido_user;
    private FloatingActionButton editPer;



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
        nombre = view.findViewById(R.id.nombre_completo);
        correo = view.findViewById(R.id.correo);
        nacionalidad = view.findViewById(R.id.nacionalidad);
        telefono = view.findViewById(R.id.telefono);
        nombre_user = view.findViewById(R.id.nombre_user);
        apellido_user = view.findViewById(R.id.apellido_user);
        editPer = view.findViewById(R.id.editPer);
        cerrar.setOnClickListener(this);
        editPer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Perfilmod perfilmod = new Perfilmod();
                perfilmod.show(getParentFragmentManager(), "update");
            }
        });



        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_u = sharedPreferences.getString("_id", " ");

        userService = Api.getRetrofitInstance().create(UserService.class);

        Call<User> call = userService.getUsuario(id_u);
        try{
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                System.out.println(response.body());
                nombre_user.setText(response.body().getNombre());
                apellido_user.setText(response.body().getApellido());
                nombre.setText(response.body().getNombre() + " "+response.body().getApellido());
                correo.setText(response.body().getCorreo());
                nacionalidad.setText(response.body().getNacionalidad());
                telefono.setText(response.body().getNumero());

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.toString());

            }
        });}catch (Exception e){
            System.out.println(e.toString());
        }







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