package com.example.proyecto.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.proyecto.fragments.ComLugarFragment;
import com.example.proyecto.fragments.ComentarioFragment;
import com.example.proyecto.fragments.DetalleFragment;
import com.example.proyecto.fragments.DetalleLugFragment;

public class FragLugAdpt extends FragmentAdapter{
    public FragLugAdpt(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new DetalleLugFragment();
            default:
                return new ComLugarFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
