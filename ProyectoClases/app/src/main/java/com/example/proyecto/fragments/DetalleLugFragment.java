package com.example.proyecto.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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


public class DetalleLugFragment extends Fragment implements View.OnClickListener {
    private FragmentDetalleBinding binding;
    private FloatingActionsMenu menufloating;
    private FloatingActionButton comment, maps, waze;
    private CheckBox fav;
    private Lugar lugar;
    private SharedPreferences sharedPreferences;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =FragmentDetalleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        Bundle bundle = getActivity().getIntent().getExtras();
        menufloating = view.findViewById(R.id.menufloating);
        fav = view.findViewById(R.id.fav);
        if(bundle.get("favs").toString().isEmpty()){
            fav.setChecked(false);
        }else{
            fav.setChecked(true);
        }
        maps = view.findViewById(R.id.maps);
        comment = view.findViewById(R.id.comment);
        waze = view.findViewById(R.id.waze);
        maps.setOnClickListener(this);
        waze.setOnClickListener(this);
        comment.setOnClickListener(this);
        fav.setOnClickListener(this);


        TextView itNombre = binding.itemDetailNombre;
        TextView itDept = binding.itemDetailDept;
        TextView itDes = binding.descripcion;
        TextView itcal = binding.cal;

        itDes.setText(bundle.getString("des"));
        itcal.setText(bundle.getString("cal") +" " + "/ 5");
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
    public void onResume(){
        super.onResume();
        Bundle bundle = getActivity().getIntent().getExtras();
        if(bundle.get("favs").toString().isEmpty()){
            fav.setChecked(false);
        }else{
            fav.setChecked(true);
        }

        TextView itNombre = binding.itemDetailNombre;
        TextView itDept = binding.itemDetailDept;
        TextView itDes = binding.descripcion;
        TextView itcal = binding.cal;

        itDes.setText(bundle.getString("des"));
        itcal.setText(bundle.getString("cal") +" " + "/ 5");
        itNombre.setText(bundle.getString("eNombre"));
        itDept.setText(bundle.getString("eDepartamento"));
    }
    @Override
    public void onClick(View view) {
        Bundle bundle = getActivity().getIntent().getExtras();
        switch (view.getId()){

            case R.id.comment:
                FechaLugFragment fragment = new FechaLugFragment();
                fragment.show(getParentFragmentManager(), "fecha");
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
            case R.id.fav:
                if(fav.isChecked()){
                    agregarfav();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(R.layout.done);
                    builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }else{
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                    dialog.setView(R.layout.deletefavoritos);
                    dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteFavoritos(bundle.getString("favs"));
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                            dialog.setView(R.layout.error);
                            dialog.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            AlertDialog alertDialog = dialog.create();
                            alertDialog.show();
                        }
                    });
                    dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Bundle bundle = getActivity().getIntent().getExtras();
                            if(bundle.get("favs").toString().isEmpty()){
                                fav.setChecked(false);
                            }else{
                                fav.setChecked(true);
                            }
                        }
                    });
                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();
                }

        }

    }


    private void deleteFavoritos(String id_fav) {
        FavLugService favLugService = Api.getRetrofitInstance().create(FavLugService.class);
        Call<String> call = favLugService.deletefav(id_fav);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
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