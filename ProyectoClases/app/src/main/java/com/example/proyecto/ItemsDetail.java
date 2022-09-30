package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.proyecto.databinding.ActivityItemsDetailBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class ItemsDetail extends AppCompatActivity {

    ActivityItemsDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityItemsDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle bundle = getIntent().getExtras();

        TextView itNombre = binding.itemDetailNombre;
        TextView itDept = binding.itemDetailDept;

        itNombre.setText(bundle.getString("eNombre"));
        itDept.setText(bundle.getString("eDepartamento"));

        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.collapsing_toolbar);
        toolbarLayout.setTitle(bundle.getString("eNombre"));
        toolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        toolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        ImageView detailImage = findViewById(R.id.item_detail_image);
        Glide.with(this).load(bundle.getString("eUrl")).into(detailImage);

    }
}