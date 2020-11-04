package com.example.eva.activity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eva.R;
import com.example.eva.adapter.SetupViewPagerAdapter;
import com.example.eva.callback.OnSetupPeriodCycleListener;
import com.example.eva.model.CyclePeriod;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public  class SetupDemoActivity extends AppCompatActivity implements OnSetupPeriodCycleListener {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    CyclePeriod mCyclePeriod;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_demo);


        TextView textView = new TextView(this);
        textView.setText(getResources().getString(R.string.app_name));
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorPinkButton));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);


        mCyclePeriod = new CyclePeriod();

        mTabLayout=findViewById(R.id.tab_setup);
        mViewPager=findViewById(R.id.viewpager_setup);

        SetupViewPagerAdapter setupViewPagerAdapter=new SetupViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(setupViewPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);

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
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mCyclePeriod.setBeginDate(dateFormat.format(calendar.getTime()));
        Log.d("SetupActivity", "BeginDate: " + mCyclePeriod.getBeginDate() + "");

    }

    @Override
    public void onFinishSetup(boolean status) {
        if(status){
            Intent intent=new Intent(this, MainDemoActivity.class);
            intent.putExtra("CYCLEPERIOD", mCyclePeriod);
            startActivity(intent);
        }
    }
}