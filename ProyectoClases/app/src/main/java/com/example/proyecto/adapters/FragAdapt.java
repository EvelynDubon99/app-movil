package com.example.proyecto.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto.fragments.ComentarioFragment;
import com.example.proyecto.fragments.DetalleFragment;
import com.example.proyecto.fragments.FavLugarFragment;
import com.example.proyecto.fragments.FavoritosFragment;

public class FragAdapt extends FragmentStateAdapter {
    public FragAdapt(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FavoritosFragment();
            default:
                return new FavLugarFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
