package com.example.proyecto.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto.fragments.PerfilUserFragment;
import com.example.proyecto.fragments.FavoritosFragment;
import com.example.proyecto.fragments.LugaresFragment;
import com.example.proyecto.fragments.RestauranteFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    int num;
    public FragmentAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.num = behavior;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new RestauranteFragment();
            case 1:
                return new LugaresFragment();
            case 2:
                return new PerfilUserFragment();
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return num;
    }
}
