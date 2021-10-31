package com.example.garrapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ListasAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public ListasAdapter(FragmentManager fml, Context context , int totalTabs){
        super(fml);
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
                EncontradosTabFragment encontradosTabFragment = new EncontradosTabFragment();
                return encontradosTabFragment;

            case 1:
                PerdidosTabFragment perdidosTabFragment = new PerdidosTabFragment();
                return perdidosTabFragment;
            default:
                return  null;
        }

    }


}
