package com.example.garrapp;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TusReportesAdapter extends FragmentPagerAdapter {

    private Context context;
    int totalTabs;

    public TusReportesAdapter (FragmentManager fm, Context context , int totalTabs){
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
                TusReportesEncontradosTabFragment tusReportesEncontradosTabFragment  = new TusReportesEncontradosTabFragment();
                return tusReportesEncontradosTabFragment;

            case 1:
                TusReportesPerdidosTabFragment tusReportesPerdidosTabFragment = new TusReportesPerdidosTabFragment();
                return tusReportesPerdidosTabFragment;
            default:
                return  null;
        }

    }


}
