package com.example.eva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.eva.CaculatorDate;
import com.example.eva.model.CyclePeriod;
import com.example.eva.R;
import com.example.eva.fragment.CalendarFragment;
import com.example.eva.fragment.ChartFragment;
import com.example.eva.fragment.HomeFragment;
import com.example.eva.fragment.MenuFragment;
import com.example.eva.model.DateCyclePeriod;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        getData();

    }


    private void init() {
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
    }

    private void getData() {
        Intent intent = getIntent();
        mCyclePeriod = (CyclePeriod) intent.getSerializableExtra("CYCLEPERIOD");

        mCycle = mCyclePeriod.getCycle();
        mPeriod = mCyclePeriod.getPeriod();
        Log.d("BacNT","cycle:" +mCycle);
        Log.d("BacNT","period: "+mPeriod);
        Log.d("BacNT",mCyclePeriod.getDate().getBeginDate().getDay()+"/"+mCyclePeriod.getDate().getBeginDate().getMonth()+"/"+mCyclePeriod.getDate().getBeginDate().getYear());
        DateCyclePeriod dateCyclePeriod=new DateCyclePeriod();
        DateCyclePeriod.Date endDate=dateCyclePeriod.new Date();
        CaculatorDate caculatorDate=new CaculatorDate(mCyclePeriod);
        dateCyclePeriod = caculatorDate.countEndDay();
        mCyclePeriod.setDate(dateCyclePeriod);
        Log.d("BacNT",mCyclePeriod.getDate().getEndDate().getDay()+"/"+mCyclePeriod.getDate().getEndDate().getMonth()+"/"+mCyclePeriod.getDate().getEndDate().getYear());
        Log.d("BacNT",mCyclePeriod.getDate().getOvulationDay().getDay()+"/"+mCyclePeriod.getDate().getOvulationDay().getMonth()+"/"+mCyclePeriod.getDate().getOvulationDay().getYear());

    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.image_home){

            mImageHome.setImageResource(R.drawable.ic_baseline_home_on_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_off_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_off_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_off_24);

            mHomeFragment=new HomeFragment();
            FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment_main,mHomeFragment,"HOME").commit();
        }
        if(id==R.id.image_calendar){

            mImageHome.setImageResource(R.drawable.ic_baseline_home_off_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_off_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_off_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_on_24);

            mCalendarFragment=new CalendarFragment();
            FragmentTransaction fragmentTransaction= mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment_main,mCalendarFragment,"CALENDAR").commit();

        }
        if(id==R.id.image_chart){

            mImageHome.setImageResource(R.drawable.ic_baseline_home_off_24);
            mImageMenu.setImageResource(R.drawable.ic_baseline_menu_off_24);
            mImageChart.setImageResource(R.drawable.ic_baseline_equalizer_on_24);
            mImageCalendar.setImageResource(R.drawable.ic_baseline_calendar_off_24);

            mChartFragment=new ChartFragment();
            FragmentTransaction fragmentTransaction=mFragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_fragment_main,mChartFragment,"CHART").commit();
        }
        if(id==R.id.image_menu){

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