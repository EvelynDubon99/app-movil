package com.example.proyecto.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.proyecto.fragments.CercademiLugFragment;
import com.example.proyecto.fragments.CercademiResFragment;

public class FragAdaptCerca extends FragmentStateAdapter {
    public FragAdaptCerca(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new CercademiResFragment();
            default:
                return new CercademiLugFragment();
        }


    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
