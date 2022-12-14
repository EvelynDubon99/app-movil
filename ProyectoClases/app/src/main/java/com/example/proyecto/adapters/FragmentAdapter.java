package com.example.proyecto.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto.FavoritosFragment;
import com.example.proyecto.LugaresFragment;
import com.example.proyecto.RestauranteFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    public FragmentAdapter(@NonNull FragmentActivity fragmentActivity) {

        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new RestauranteFragment();
            case 1:
                return new LugaresFragment();
            default:
                return new FavoritosFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3 ;
    }
}
