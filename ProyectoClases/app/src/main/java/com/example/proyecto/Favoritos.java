package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.proyecto.adapters.FragAdap;
import com.example.proyecto.adapters.FragAdapt;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Favoritos extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2.setAdapter(new FragAdapt(this));
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
                        }
                    }
                }); tabLayoutMediator.attach();





    }
}