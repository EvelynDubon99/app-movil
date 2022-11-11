package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto.Model.Restaurante;
import com.example.proyecto.adapters.FragmentAdapter;
import com.example.proyecto.adapters.RestauranteAdapter;
import com.example.proyecto.api.Api;
import com.example.proyecto.api.RestauranteService;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);

        viewPager2.setAdapter(new FragmentAdapter(this));


        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:{
                                tab.setText("Restaurante");
                                tab.setIcon(getResources().getDrawable(R.drawable.ic_restaurant));

                                break;
                            }
                            case 1:{
                                tab.setText("Lugares");
                                tab.setIcon(getResources().getDrawable(R.drawable.ic_location));
                                break;
                            }
                            case 2:{
                                tab.setText("Perfil");
                                tab.setIcon(getResources().getDrawable(R.drawable.ic_person));
                                break;
                            }
                        }
                    }
                }); tabLayoutMediator.attach();




    }

}




