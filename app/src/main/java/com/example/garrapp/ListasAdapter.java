package com.example.garrapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ListasAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public ListasAdapter (FragmentManager fm, Context context , int totalTabs){
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;

    }
    @Override
    public int getCount() {
        return totalTabs;
    }



    public Fragment getItem (int position){
        switch (position){
            case 0:
                EncontradosTabFragment EncontradosTabFragment = new EncontradosTabFragment();
                return EncontradosTabFragment;

            case 1:
                PerdidosTabFragment PerdidosTabFragment = new PerdidosTabFragment();
                return PerdidosTabFragment;
            default:
                return  null;
        }

    }


}
