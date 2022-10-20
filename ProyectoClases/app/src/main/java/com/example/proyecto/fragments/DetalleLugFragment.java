package com.example.proyecto.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto.Favoritos;
import com.example.proyecto.Model.Favlug;
import com.example.proyecto.Model.Lugar;
import com.example.proyecto.R;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.FavLugService;
import com.example.proyecto.databinding.FragmentDetalleBinding;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleLugFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleLugFragment extends Fragment implements View.OnClickListener {
    private FragmentDetalleBinding binding;
    private FloatingActionsMenu menufloating;
    private FloatingActionButton comment, maps, waze, fav;
    private Lugar lugar;
    private SharedPreferences sharedPreferences;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleLugFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleLugFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleLugFragment newInstance(String param1, String param2) {
        DetalleLugFragment fragment = new DetalleLugFragment();
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
                Comment comment = new Comment();
                comment.show(getParentFragmentManager(), "comentario");
                break;
            case R.id.fav:
                agregarfav();
                Intent intent2 = new Intent(getContext(), Favoritos.class);
                startActivity(intent2);
                break;
            case R.id.maps:
                Uri gmmIntentUri = Uri.parse("geo:" + bundle.getString("latitud") + "," + bundle.getString("longitud")+"?z=18");
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
        Favlug favlug = new Favlug();
        sharedPreferences = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
        String id_user = sharedPreferences.getString("_id"," ");
        Bundle bundle = getActivity().getIntent().getExtras();
        String id_lugar = bundle.getString("id");
        FavLugService favLugService = Api.getRetrofitInstance().create(FavLugService.class);
        Call<Favlug> call = favLugService.postfav(id_user, id_lugar);
        call.enqueue(new Callback<Favlug>() {
            @Override
            public void onResponse(Call<Favlug> call, Response<Favlug> response) {
                Favlug favlug1 = response.body();
            }

            @Override
            public void onFailure(Call<Favlug> call, Throwable t) {

            }
        });

    }
}