package com.example.eva.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.eva.Caculator;
import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.adapter.SetupViewPagerAdapter;
import com.example.eva.callback.OnSetupPeriodCycleListener;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.example.eva.model.Remind;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public  class SetupActivity extends AppCompatActivity implements OnSetupPeriodCycleListener {


    ViewPager mViewPager;
    CyclePeriod mCyclePeriod;

    DBManager mDbManager;
    int mCycle;
    int mPeriod;
    SharedPreferences mSharedPreferences;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_demo);

        mSharedPreferences =getSharedPreferences(Constant.SHARED_PREFERENCES, MODE_PRIVATE);
        String FirstTime= mSharedPreferences.getString(Constant.FIRST_TIME,"");

        if(FirstTime.equals("Yes")){
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        init();

        mDbManager =new DBManager(this);
        mCyclePeriod = new CyclePeriod();
    }

    private void init() {
        TextView textView = new TextView(this);
        textView.setText(getResources().getString(R.string.app_name));
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorMainPink));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
        //viewpager
        TabLayout tabLayout=findViewById(R.id.tab_setup);
        mViewPager=findViewById(R.id.viewpager_setup);

        SetupViewPagerAdapter setupViewPagerAdapter=new SetupViewPagerAdapter(
                getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(setupViewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onChangeCycle(int cycle) {
        mCycle=cycle;
        mCyclePeriod.setCycle(mCycle);
    }

    @Override
    public void onChangePeriod(int period) {
        mPeriod=period;
        mCyclePeriod.setPeriod(mPeriod);
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
            mCyclePeriod.setUserBeginDate(mCyclePeriod.getBeginDate());
            mCyclePeriod.setUserPeriod(mPeriod);
            mCyclePeriod.setUserCycle(mCycle);
            String userEndDate= Caculator.getDate(mCyclePeriod.getUserBeginDate(),
                    mCyclePeriod.getUserPeriod() - 1);
            mCyclePeriod.setUserEndDate(userEndDate);
            mCyclePeriod= Caculator.getCyclePeriod(mCyclePeriod);
            mDbManager.addCyclePeriod(mCyclePeriod);

            CyclePeriod secondCyclePeriod=new CyclePeriod(mCycle,
                    mPeriod,
                    mCyclePeriod.getNextBeginCycle());
            secondCyclePeriod= Caculator.getCyclePeriod(secondCyclePeriod);
            mDbManager.addCyclePeriod(secondCyclePeriod);


            CyclePeriod thirdCyclePeriod=new CyclePeriod(mCycle,
                    mPeriod,
                    secondCyclePeriod.getNextBeginCycle());
            thirdCyclePeriod= Caculator.getCyclePeriod(thirdCyclePeriod);
            mDbManager.addCyclePeriod(thirdCyclePeriod);

            CyclePeriod fourthCyclePeriod=new CyclePeriod(mCycle,
                    mPeriod,
                    thirdCyclePeriod.getNextBeginCycle());
            fourthCyclePeriod= Caculator.getCyclePeriod(fourthCyclePeriod);
            mDbManager.addCyclePeriod(fourthCyclePeriod);

            //setup RemindDB
            mDbManager.addRemind(new Remind(true,
                    Constant.DEFAULT_TIME,
                    getResources().getString(R.string.remind_message_begin_period)));
            mDbManager.addRemind(new Remind(false,
                    Constant.DEFAULT_TIME,
                    getResources().getString(R.string.remind_message_end_period)));
            mDbManager.addRemind(new Remind(false,
                    Constant.DEFAULT_TIME,
                    getResources().getString(R.string.remind_message_begin_fertility)));
            mDbManager.addRemind(new Remind(false,
                    Constant.DEFAULT_TIME,
                    getResources().getString(R.string.remind_message_end_fertility)));
            mDbManager.addRemind(new Remind(false,
                    Constant.DEFAULT_TIME,
                    getResources().getString(R.string.remind_message_ovulation)));
            mDbManager.addRemind(new Remind(false,
                    Constant.DEFAULT_TIME+","+Constant.DEFAULT_TIME_MEDICINE_2+
                            "," +Constant.DEFAULT_TIME_MEDICINE_3,
                    getResources().getString(R.string.medicine_message_detail)));

            SharedPreferences.Editor editor= mSharedPreferences.edit();
            editor.putString(Constant.FIRST_TIME,"Yes");
            editor.putInt(Constant.PERIOD, mPeriod);
            editor.putInt(Constant.CYCLE, mCycle);
            editor.putInt(Constant.ID_CYCLE_PERIOD,1);
            editor.apply();

            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}