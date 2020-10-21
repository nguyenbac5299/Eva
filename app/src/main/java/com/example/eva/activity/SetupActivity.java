package com.example.eva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eva.model.CyclePeriod;
import com.example.eva.R;
import com.example.eva.callback.OnSetupPeriodCycleListener;
import com.example.eva.fragment.SetupCalendarFragment;
import com.example.eva.fragment.SetupCycleFragment;
import com.example.eva.fragment.SetupPeriodFragment;
import com.example.eva.model.DateCyclePeriod;

import java.util.Date;


public class SetupActivity extends AppCompatActivity implements OnSetupPeriodCycleListener {

    CyclePeriod mCyclePeriod;

    TextView textDetail;
    Button buttonNext;

    CyclePeriod cyclePeriod;
    FragmentManager fragmentManager;
    SetupCalendarFragment fragmentSetupCalendar;
    SetupPeriodFragment fragmentSetupPeriod;
    SetupCycleFragment fragmentSetupCycle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        init();
        onClickEvent();
    }

    private void onClickEvent() {
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.linear_fragment);
                if (currentFragment instanceof SetupPeriodFragment) {
                    if (fragmentSetupCycle==null){
                    Log.d("Setup", "Fragment Cycle");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentSetupCycle = new SetupCycleFragment();
                    fragmentTransaction.replace(R.id.linear_fragment, fragmentSetupCycle, "CYCLE")
                            .addToBackStack("CYCLE")
                            .commit();}
                    textDetail.setText(getResources().getString(R.string.detail_setup_cycle));
                } else if (currentFragment instanceof SetupCycleFragment) {
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentSetupCalendar = new SetupCalendarFragment();
                    fragmentTransaction.replace(R.id.linear_fragment, fragmentSetupCalendar)
                            .addToBackStack("CALENDAR")
                            .commit();
                    textDetail.setText(getResources().getString(R.string.detail_setup_calendar));
                } else {
                    Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                    intent.putExtra("CYCLEPERIOD",mCyclePeriod);
                    startActivity(intent);
                }
            }
        });


    }

    private void init() {

        mCyclePeriod= new CyclePeriod();
        textDetail = findViewById(R.id.text_fragment);
        buttonNext = findViewById(R.id.button_next);
        //setup Fragment
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentSetupPeriod = new SetupPeriodFragment();
        fragmentTransaction.add(R.id.linear_fragment, fragmentSetupPeriod, "PERIOD");
        fragmentTransaction.addToBackStack("PERIOD").commit();
        textDetail.setText(getResources().getString(R.string.detail_setup_period));

    }


    @Override
    public void onChangeCycle(int cycle) {
        mCyclePeriod.setCycle(cycle);
    }

    @Override
    public void onChangePeriod(int period) {
        mCyclePeriod.setPeriod(period);
    }

    @Override
    public void onChangeCalendar(int year, int month, int dayOfMonth) {
        DateCyclePeriod date =new DateCyclePeriod();
        DateCyclePeriod.Date beginDate=date.new Date(dayOfMonth,month,year);
        date.setBeginDate(beginDate);
        mCyclePeriod.setDate(date);
        Log.d("BacNT",""+ dayOfMonth+"/"+month+"/"+year);
    }


}