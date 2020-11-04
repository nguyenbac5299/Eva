package com.example.eva.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.eva.fragment.SetupCalendarFragment;
import com.example.eva.fragment.SetupCycleFragment;
import com.example.eva.fragment.SetupPeriodFragment;

public class SetupViewPagerAdapter extends FragmentStatePagerAdapter {
    public SetupViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SetupPeriodFragment();

            case 1:
                return new SetupCycleFragment();

            case 2:
                return new SetupCalendarFragment();

            default:
                return new SetupCycleFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        switch (position){
            case 0:
                title="1";
                break;
            case 1:
                title="2";
                break;
            case 2:
                title="3";
                break;
        }
        return title;
    }
}
