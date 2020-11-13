package com.example.eva.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eva.fragment.CalendarFragment;
import com.example.eva.fragment.ChartFragment;
import com.example.eva.fragment.HomeFragment;
import com.example.eva.fragment.MenuFragment;
import com.example.eva.model.CyclePeriod;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    CyclePeriod mCyclePeriod;
    int mCycle, mPeriod;
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior, CyclePeriod cyclePeriod, int cycle, int period) {
        super(fm, behavior);
        mCyclePeriod=cyclePeriod;
        mCycle=cycle;
        mPeriod=period;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment=new HomeFragment();
                Bundle bundleHome=new Bundle();
                bundleHome.putSerializable("CYCLEPERIOD",mCyclePeriod);
                homeFragment.setArguments(bundleHome);
                return homeFragment;
            case 1:
                return new CalendarFragment();
            case 2:
                return new ChartFragment();
            case 3:
                MenuFragment menuFragment=new MenuFragment();
                Bundle bundleMenu=new Bundle();
                bundleMenu.putInt("CYCLE", mCycle);
                bundleMenu.putInt("PERIOD", mPeriod);
                menuFragment.setArguments(bundleMenu);
                return menuFragment;
            default:
                HomeFragment homeFragment1=new HomeFragment();
                Bundle bundle1=new Bundle();
                bundle1.putSerializable("CYCLEPERIOD",mCyclePeriod);
                homeFragment1.setArguments(bundle1);
                return homeFragment1;
        }
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}

