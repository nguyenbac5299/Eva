package com.example.eva.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.eva.fragment.CalendarFragment;
import com.example.eva.fragment.ChartCycleFragment;
import com.example.eva.fragment.HomeFragment;
import com.example.eva.fragment.MenuFragment;

import java.util.ArrayList;
import java.util.List;

public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public MainViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return POSITION_NONE;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                return new CalendarFragment();
            case 2:
                return new ChartCycleFragment();
            case 3:
                MenuFragment menuFragment = new MenuFragment();
                Bundle bundleMenu = new Bundle();
                menuFragment.setArguments(bundleMenu);
                return menuFragment;
            default:
                HomeFragment homeFragment1 = new HomeFragment();
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

