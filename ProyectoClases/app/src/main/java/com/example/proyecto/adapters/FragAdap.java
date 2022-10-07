package com.example.proyecto.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.proyecto.fragments.ComentarioFragment;
import com.example.proyecto.fragments.DetalleFragment;
import com.example.proyecto.fragments.FavoritosFragment;
import com.example.proyecto.fragments.LugaresFragment;
import com.example.proyecto.fragments.RestauranteFragment;

public class FragAdap extends FragmentAdapter{
    public FragAdap(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DetalleFragment();
            default:
                return new ComentarioFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
