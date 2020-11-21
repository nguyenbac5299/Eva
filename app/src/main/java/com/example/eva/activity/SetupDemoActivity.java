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

import com.example.eva.CaculatorCyclePeriod;
import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.adapter.SetupViewPagerAdapter;
import com.example.eva.callback.OnSetupPeriodCycleListener;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public  class SetupDemoActivity extends AppCompatActivity implements OnSetupPeriodCycleListener {

    TabLayout mTabLayout;
    ViewPager mViewPager;
    CyclePeriod mCyclePeriod;

    DBManager dbManager;
    int mCycle;
    int mPeriod;
    SharedPreferences sharedPreferences;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_demo);

        sharedPreferences=getSharedPreferences(Constant.SHARED_PREFERENCES, MODE_PRIVATE);
        String FirstTime=sharedPreferences.getString(Constant.FIRST_TIME,"");

        if(FirstTime.equals("Yes")){
            Intent intent=new Intent(this, MainDemoActivity.class);
 //           intent.putExtra("CYCLEPERIOD", mCyclePeriod);
            startActivity(intent);
        }



        dbManager=new DBManager(this);

        TextView textView = new TextView(this);
        textView.setText(getResources().getString(R.string.app_name));
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.colorMainPink));
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
            //Tinh toan ngay
            //CaculatorCyclePeriod caculatorCyclePeriod=new CaculatorCyclePeriod(mCyclePeriod);
            mCyclePeriod.setUserBeginDate(mCyclePeriod.getBeginDate());
            mCyclePeriod.setUserPeriod(mPeriod);
            mCyclePeriod.setUserCycle(mCycle);
            mCyclePeriod=CaculatorCyclePeriod.caculatorCyclePeriod(mCyclePeriod);
            dbManager.addCyclePeriod(mCyclePeriod);

            CyclePeriod nextCyclePeriod=new CyclePeriod(mCycle, mPeriod,mCyclePeriod.getNextBeginCycle());
            nextCyclePeriod=CaculatorCyclePeriod.caculatorCyclePeriod(nextCyclePeriod);
            dbManager.addCyclePeriod(nextCyclePeriod);


            CyclePeriod nextNextCyclePeriod=new CyclePeriod(mCycle, mPeriod,nextCyclePeriod.getNextBeginCycle());
            nextNextCyclePeriod=CaculatorCyclePeriod.caculatorCyclePeriod(nextNextCyclePeriod);
            dbManager.addCyclePeriod(nextNextCyclePeriod);

            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString(Constant.FIRST_TIME,"Yes");
            editor.putInt(Constant.PERIOD, mPeriod);
            editor.putInt(Constant.CYCLE, mCycle);
            editor.apply();

            Intent intent=new Intent(this, MainDemoActivity.class);
            startActivity(intent);
        }
    }
}