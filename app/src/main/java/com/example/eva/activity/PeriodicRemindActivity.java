package com.example.eva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.eva.R;
import com.example.eva.callback.OnTimePickerPeriodicListener;
import com.example.eva.fragment.TimePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PeriodicRemindActivity extends AppCompatActivity implements OnTimePickerPeriodicListener {

    public static final int BEGIN_PERIOD = 0;
    public static final int END_PERIOD = 1;
    public static final int BEGIN_FERTILITY = 2;
    public static final int END_FERTILITY = 3;
    public static final int OVULATION = 4;

    FrameLayout frameLayout;
    TextView textTime, textTimeTitle;
    EditText editText;
    boolean mVisible, mIsBeforeTwoDay, mIsBeforeOneDay, mIsDay;
    CheckBox checkBeforeTwoDay, checkBeforeOneDay, checkInDay;
    int id;
    SimpleDateFormat simpleDateFormat;
    Calendar mCalendar;
    String mTimeBeginPeriod, mTimeEndPeriod, mTimeBeginFertility, mTimeEndFertility, mTimeOvulation;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_remind);

        getData();
        init();
        addEvent();


    }

    private void getData() {
        Bundle extras = this.getIntent().getExtras();
        id = extras.getInt("ID");
    }


    private void addEvent() {
        textTimeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVisible = !mVisible;
                if (mVisible)
                    frameLayout.setVisibility(View.VISIBLE);
                else
                    frameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void init() {
        textTimeTitle = findViewById(R.id.text_time_title);
        textTime = findViewById(R.id.text_time_periodic);
        editText=findViewById(R.id.edit_message);
        frameLayout = findViewById(R.id.layout_time_picker_periodic);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionOne = fragmentManager.beginTransaction();
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        fragmentTransactionOne.add(R.id.layout_time_picker_periodic, timePickerFragment, "PERIODIC").commit();
        frameLayout.setVisibility(View.GONE);
        simpleDateFormat = new SimpleDateFormat("HH:mm");

        switch (id){
            case 0:
                editText.setText(getResources().getString(R.string.remind_message_begin_period));
                break;
            case 1:
                editText.setText(getResources().getString(R.string.remind_message_end_period));
                break;
            case 2:
                editText.setText(getResources().getString(R.string.remind_message_begin_fertility));
                break;
            case 3:
                editText.setText(getResources().getString(R.string.remind_message_end_fertility));
                break;
            case 4:
                editText.setText(getResources().getString(R.string.remind_message_ovulation));
                break;
            default:
                break;
        }
    }


    @Override
    public void onSetTime(Calendar calendar, boolean save, String tag) {
        if (save) {
            mCalendar = calendar;
            textTime.setText(simpleDateFormat.format(calendar.getTime()) + "");
        }
        mVisible=false;
        frameLayout.setVisibility(View.GONE);
    }
}