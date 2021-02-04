package com.example.eva.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eva.callback.OnChangeRemindListener;
import com.example.eva.model.CyclePeriod;
import com.example.eva.service.Notification;
import com.example.eva.R;
import com.example.eva.callback.OnUserDateChangeListener;
import com.example.eva.adapter.MainViewPagerAdapter;
import com.example.eva.callback.OnMenuListener;
import com.example.eva.data.DBManager;
import com.example.eva.fragment.CalendarFragment;
import com.example.eva.fragment.ChartCycleFragment;
import com.example.eva.fragment.HomeFragment;
import com.example.eva.fragment.MenuFragment;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMenuListener, OnUserDateChangeListener, OnChangeRemindListener {

    TabLayout mTabMain;
    ViewPager mViewPagerMain;
    TextView mTextTitle;
    DBManager mDbManager;
    List<CyclePeriod> mListCycle;
    int mIdPage;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_demo);
        init();
        mDbManager = new DBManager(this);
        getData();
        Notification.startNotification(MainActivity.this, mDbManager);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
    }

    private void getData() {
        mListCycle = mDbManager.getListCycle();
    }

    private void init() {
        Display mDisplay = this.getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int Height = mDisplay.getHeight();
        float screenSize = (float) Height / width;


        mViewPagerMain = (ViewPager) findViewById(R.id.viewpager_main);
        setupViewPager(mViewPagerMain);
        mTabMain = (TabLayout) findViewById(R.id.tab_main);
        if (screenSize < 1.8f) {
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    0,
                    1.5f
            );
            mTabMain.setLayoutParams(param);
        }
        mTabMain.setupWithViewPager(mViewPagerMain);
        setupTabIcons();

        mTextTitle = new TextView(this);
        mTextTitle.setText(getResources().getString(R.string.home));
        mTextTitle.setTextSize(17);
        mTextTitle.setTypeface(null, Typeface.BOLD);
        mTextTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        mTextTitle.setGravity(Gravity.CENTER);
        mTextTitle.setTextColor(getResources().getColor(R.color.colorMainPink));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mTextTitle);

    }

    private void setupTabIcons() {

        TextView tabHome = (TextView) LayoutInflater
                .from(this)
                .inflate(R.layout.custom_main_tab, null);
        tabHome.setText(getResources().getString(R.string.home));
        tabHome.setCompoundDrawablesWithIntrinsicBounds(0,
                R.drawable.ic_tab_home_pressed, 0, 0);
        mTabMain.getTabAt(0).setCustomView(tabHome);

        TextView tabCalendar = (TextView) LayoutInflater
                .from(this)
                .inflate(R.layout.custom_main_tab, null);
        tabCalendar.setText(getResources().getString(R.string.calendar));
        tabCalendar.setCompoundDrawablesWithIntrinsicBounds(0,
                R.drawable.ic_tab_calendar_pressed, 0, 0);
        mTabMain.getTabAt(1).setCustomView(tabCalendar);

        TextView tabChart = (TextView) LayoutInflater
                .from(this)
                .inflate(R.layout.custom_main_tab, null);
        tabChart.setText(getResources().getString(R.string.chart));
        tabChart.setCompoundDrawablesWithIntrinsicBounds(0,
                R.drawable.ic_tab_report_pressed, 0, 0);
        mTabMain.getTabAt(2).setCustomView(tabChart);

        TextView tabMore = (TextView) LayoutInflater
                .from(this)
                .inflate(R.layout.custom_main_tab, null);
        tabMore.setText(getResources().getString(R.string.more));
        tabMore.setCompoundDrawablesWithIntrinsicBounds(0,
                R.drawable.ic_tab_more_pressed, 0, 0);
        mTabMain.getTabAt(3).setCustomView(tabMore);
    }

    private void setupViewPager(ViewPager viewPager) {
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFrag(new HomeFragment(), getResources().getString(R.string.home));
        adapter.addFrag(new CalendarFragment(), getResources().getString(R.string.calendar));
        adapter.addFrag(new ChartCycleFragment(), getResources().getString(R.string.chart));
        adapter.addFrag(new MenuFragment(), getResources().getString(R.string.more));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int title = 0;
                switch (position) {
                    case 0:
                        title = R.string.home;
                        mIdPage = 0;
                        break;
                    case 1:
                        title = R.string.calendar;
                        mIdPage = 1;
                        break;
                    case 2:
                        title = R.string.chart;
                        mIdPage = 2;
                        break;
                    case 3:
                        title = R.string.more;
                        mIdPage = 3;
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
        for (CyclePeriod cyclePeriod : mListCycle) {
            if(cyclePeriod.getUserCycle()==0){
                cyclePeriod.setCycle(cycle);
                mDbManager.updateCyclePeriod(cyclePeriod);
            }
        }
    }

    @Override
    public void onChangeMenuPeriod(int period) {
        for (CyclePeriod cyclePeriod : mListCycle) {
            if(cyclePeriod.getUserPeriod()==0){
                cyclePeriod.setPeriod(period);
                mDbManager.updateCyclePeriod(cyclePeriod);
            }
        }
    }

    @Override
    public void changeUserDate() {
        Log.d("BacNT", "change");
        mViewPagerMain.getAdapter().notifyDataSetChanged();
        setupTabIcons();
    }

    @Override
    public void onBackPressed() {
        if (mIdPage != 0)
            mViewPagerMain.setCurrentItem(0);
        else finish();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onChangeRemind() {
        Notification.startNotification(MainActivity.this, mDbManager);
    }

}