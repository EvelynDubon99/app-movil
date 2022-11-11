package com.example.proyecto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.os.Handler;

import com.example.proyecto.adapters.FragAdap;
import com.example.proyecto.adapters.FragLugAdpt;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ItemDetalleLug extends AppCompatActivity {
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detalle_lug);
        final LoadingDialog loadingDialog = new LoadingDialog(ItemDetalleLug.this);
        viewPager2 = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2.setAdapter(new FragLugAdpt(this));
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

        loadingDialog.startLoading();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingDialog.dismissDialog();
            }
        }, 1600);


    }


}