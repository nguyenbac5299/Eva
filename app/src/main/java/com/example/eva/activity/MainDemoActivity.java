package com.example.eva.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eva.Constant;
import com.example.eva.Notification;
import com.example.eva.R;
import com.example.eva.service.RemindReceiver;
import com.example.eva.adapter.MainViewPagerAdapter;
import com.example.eva.callback.OnMenuListener;
import com.example.eva.data.DBManager;
import com.example.eva.fragment.CalendarFragment;
import com.example.eva.fragment.ChartFragment;
import com.example.eva.fragment.HomeFragment;
import com.example.eva.fragment.MenuFragment;
import com.example.eva.model.CyclePeriod;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainDemoActivity extends AppCompatActivity implements OnMenuListener {

    CyclePeriod mCyclePeriod;
    SharedPreferences sharedPreferences;

    MainViewPagerAdapter adapter;
    ActionBar mActionBar;
    TabLayout mTabMain;
    ViewPager mViewPagerMain;
    TextView mTextTitle;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo);

        getData();
        init();
        Notification.startNotification(MainDemoActivity.this);
    }


    private void init() {
        mActionBar = getSupportActionBar();
        mViewPagerMain = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(mViewPagerMain);

        mTabMain = (TabLayout) findViewById(R.id.tab_main);
        mTabMain.setupWithViewPager(mViewPagerMain);
        setupTabIcons();


        mTextTitle = new TextView(this);
        mTextTitle.setText(getResources().getString(R.string.home));
        mTextTitle.setTextSize(23);
        mTextTitle.setTypeface(null, Typeface.BOLD);
        mTextTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mTextTitle.setGravity(Gravity.CENTER);
        mTextTitle.setTextColor(getResources().getColor(R.color.colorMainPink));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mTextTitle);

    }

    private void getData() {

        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCES, MODE_PRIVATE);
        DBManager dbManager = new DBManager(this);
        mCyclePeriod = dbManager.getCurrentCycle();
    }

    private void setupTabIcons() {

        TextView tabHome = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_main_tab, null);
        tabHome.setText(getResources().getString(R.string.home));
        tabHome.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_home_pressed, 0, 0);
        mTabMain.getTabAt(0).setCustomView(tabHome);

        TextView tabCalendar = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_main_tab, null);
        tabCalendar.setText(getResources().getString(R.string.calendar));
        tabCalendar.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_calendar_pressed, 0, 0);
        mTabMain.getTabAt(1).setCustomView(tabCalendar);

        TextView tabChart = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_main_tab, null);
        tabChart.setText(getResources().getString(R.string.chart));
        tabChart.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_report_pressed, 0, 0);
        mTabMain.getTabAt(2).setCustomView(tabChart);

        TextView tabMore = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_main_tab, null);
        tabMore.setText(getResources().getString(R.string.more));
        tabMore.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_more_pressed, 0, 0);
        mTabMain.getTabAt(3).setCustomView(tabMore);
    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new MainViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mCyclePeriod);
        adapter.addFrag(new HomeFragment(), getResources().getString(R.string.home));
        adapter.addFrag(new CalendarFragment(), getResources().getString(R.string.calendar));
        adapter.addFrag(new ChartFragment(), getResources().getString(R.string.chart));
        adapter.addFrag(new MenuFragment(), getResources().getString(R.string.more));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int title;
                switch (position) {
                    case 0:
                        title = R.string.home;
                        break;
                    case 1:
                        title = R.string.calendar;
                        break;
                    case 2:
                        title = R.string.chart;
                        break;
                    case 3:
                        title = R.string.more;
                        break;
                    default:
                        title = R.string.home;
                        break;
                }
                mTextTitle.setText(getResources().getString(title));
                getSupportActionBar().setCustomView(mTextTitle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onChangeMenuCycle(int cycle) {
    }

    @Override
    public void onChangeMenuPeriod(int period) {
    }
}