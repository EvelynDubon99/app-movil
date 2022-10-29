package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto.adapters.FragAdap;
import com.example.proyecto.adapters.FragmentAdapter;

import com.example.proyecto.fragments.ComentarioFragment;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ItemsDetail extends AppCompatActivity {

    ViewPager2 viewPager2;
    TabLayout tabLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_detail);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2.setAdapter(new FragAdap(this));
        Bundle bundle = getIntent().getExtras();

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position){
                            case 0:{
                                tab.setText("Detalle");
                                tab.setIcon(getResources().getDrawable(R.drawable.ic_list));
                                break;
                            }
                            case 1:{
                                tab.setText("Comentarios");
                                tab.setIcon(getResources().getDrawable(R.drawable.ic_comment));
                                break;
                            }

                        }
                    }
                }); tabLayoutMediator.attach();




    }
}