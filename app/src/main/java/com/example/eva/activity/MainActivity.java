package com.example.eva.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.eva.CaculatorCyclePeriod;
import com.example.eva.CaculatorHomeDate;
import com.example.eva.model.CyclePeriod;
import com.example.eva.R;
import com.example.eva.fragment.CalendarFragment;
import com.example.eva.fragment.ChartFragment;
import com.example.eva.fragment.HomeFragment;
import com.example.eva.fragment.MenuFragment;
import com.example.eva.model.PMS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    CyclePeriod mCyclePeriod;

    FragmentManager mFragmentManager;
    HomeFragment mHomeFragment;
    ChartFragment mChartFragment;
    CalendarFragment mCalendarFragment;
    MenuFragment mMenuFragment;
    ImageView mImageHome, mImageCalendar, mImageChart, mImageMenu;

    int mCycle;
    int mPeriod;

    ActionBar mActionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        init();

    }


    private void init() {
        mActionBar=getSupportActionBar();

        mImageHome=findViewById(R.id.image_home);
        mImageCalendar=findViewById(R.id.image_calendar);
        mImageChart=findViewById(R.id.image_chart);
        mImageMenu=findViewById(R.id.image_menu);

        mImageHome.setOnClickListener(this);
        mImageCalendar.setOnClickListener(this);
        mImageChart.setOnClickListener(this);
        mImageMenu.setOnClickListener(this);

        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction= mFragmentManager.beginTransaction();
        mHomeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.frame_fragment_main,mHomeFragment,"HOME").commit();
        mImageHome.setImageResource(R.drawable.ic_baseline_home_on_24);
        mActionBar.setTitle(getResources().getString(R.string.home));

        Bundle bundle=new Bundle();
        bundle.putSerializable("CYCLEPERIOD",mCyclePeriod);
        mHomeFragment.setArguments(bundle);
    }

    private void getData() {
        Intent intent = getIntent();
        mCyclePeriod = (CyclePeriod) intent.getSerializableExtra("CYCLEPERIOD");

        CaculatorCyclePeriod caculatorCyclePeriod=new CaculatorCyclePeriod(mCyclePeriod);
        mCyclePeriod=CaculatorCyclePeriod.caculatorCyclePeriod();
        Log.d("BacNT","OK");
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.image_home){
            Log.d("MainActivity", "clickHome");
            mActionBar.setTitle(getResources().getString(R.string.home));
            mImageHome.setImageResource(R.drawable.ic_baseline_home_on_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_off_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_off_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_off_24);

            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            HomeFragment homeFragment=new HomeFragment();
            fragmentTransaction.replace(R.id.frame_fragment_main, homeFragment).commit();
            Bundle bundle=new Bundle();
            bundle.putSerializable("CYCLEPERIOD",mCyclePeriod);
            homeFragment.setArguments(bundle);
        }
        if(id==R.id.image_calendar){

            mActionBar.setTitle(getResources().getString(R.string.calendar));
            mImageHome.setImageResource(R.drawable.ic_baseline_home_off_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_off_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_off_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_on_24);
            Log.d("MainActivity", "clickCalendar");
            mCalendarFragment=new CalendarFragment();
            FragmentTransaction fragmentTransaction= mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment_main,mCalendarFragment,"CALENDAR").commit();

        }
        if(id==R.id.image_chart){
           mActionBar.setTitle(getResources().getString(R.string.chart));
            mImageHome.setImageResource(R.drawable.ic_baseline_home_off_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_off_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_on_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_off_24);

            mChartFragment=new ChartFragment();
            FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment_main,mChartFragment,"CHART").commit();
        }
        if(id==R.id.image_menu){
            mActionBar.setTitle(getResources().getString(R.string.more));
            mImageHome.setImageResource(R.drawable.ic_baseline_home_off_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_on_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_off_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_off_24);

            mMenuFragment=new MenuFragment();
            FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment_main,mMenuFragment,"MENU").commit();
        }
    }
}