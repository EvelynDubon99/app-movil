package com.example.proyecto.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Model.Favres;
import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavResService;
import com.example.proyecto.databinding.FragmentDetalleBinding;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class DetalleFragment extends Fragment implements View.OnClickListener {

    private FragmentDetalleBinding binding;
    private FloatingActionsMenu menufloating;
    private FloatingActionButton comment, maps, waze, fav;
    private Restaurante restaurante;
    SharedPreferences sharedPreferences;





    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public static DetalleFragment newInstance(String param1, String param2) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DetalleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getParentFragmentManager().setFragmentResultListener("key", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String key, @NonNull Bundle bundle) {
                // We use a String here, but any type that can be put in a Bundle is supported
                String result = bundle.getString("bundleKey");
                // Do something with the result...
            }
        });
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding =FragmentDetalleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = getActivity().getIntent().getExtras();
        menufloating = view.findViewById(R.id.menufloating);
        comment = view.findViewById(R.id.comment);
        maps = view.findViewById(R.id.maps);
        waze = view.findViewById(R.id.waze);
        maps.setOnClickListener(this);

        waze.setOnClickListener(this);
        fav = view.findViewById(R.id.fav);

        fav.setOnClickListener(this);

        comment.setOnClickListener(this);



        TextView itNombre = binding.itemDetailNombre;
        TextView itDept = binding.itemDetailDept;
        TextView itDes = binding.descripcion;

        TextView itcal = binding.cal;

        itDes.setText(bundle.getString("des"));
        itcal.setText(bundle.getString("cal"));
        itNombre.setText(bundle.getString("eNombre"));
        itDept.setText(bundle.getString("eDepartamento"));

        CollapsingToolbarLayout toolbarLayout = view.findViewById(R.id.collapsing_toolbar);
        toolbarLayout.setTitle(bundle.getString("eNombre"));
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        ImageView detailImage = view.findViewById(R.id.item_detail_image);
        Glide.with(this).load(bundle.getString("eUrl")).into(detailImage);
        return view;
    }




    @Override
    public void onClick(View view) {
        Bundle bundle = getActivity().getIntent().getExtras();
        switch (view.getId()){
            case R.id.comment:
                FechaFragment fechaFragment = new FechaFragment();
                fechaFragment.show(getParentFragmentManager(), "fecha");
                break;
            case R.id.fav:
                agregarfav();
                Intent intent2 = new Intent(getContext(), Favoritos.class);
                startActivity(intent2);
                break;
            case R.id.maps:
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + bundle.getString("latitud") + "," + bundle.getString("longitud"));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
            case R.id.waze:
                String waze = bundle.getString("waze");
                Uri chosser = Uri.parse(waze);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, chosser);
                startActivity(intent1);
                break;







        }

    }

    public void agregarfav(){
        Favres favres = new Favres();
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("_id"," ");
        Bundle bundle = getActivity().getIntent().getExtras();
        String id_res = bundle.getString("id");
        FavResService favResService = Api.getRetrofitInstance().create(FavResService.class);
        Call<Favres> call = favResService.postfav(id_user,id_res);
        call.enqueue(new Callback<Favres>() {
            @Override
            public void onResponse(Call<Favres> call, Response<Favres> response) {
                Favres favres = response.body();

            }

            @Override
            public void onFailure(Call<Favres> call, Throwable t) {
                new Exception(t.getMessage());

            }
        });


    }

}