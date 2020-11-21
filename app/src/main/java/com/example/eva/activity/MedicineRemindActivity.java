package com.example.eva.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.callback.OnTimePickerPeriodicListener;
import com.example.eva.fragment.TimePickerFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MedicineRemindActivity extends AppCompatActivity implements View.OnClickListener, OnTimePickerPeriodicListener {

    FragmentManager mFragmentManager;
    TimePickerFragment mTimeMedicineFragmentOne, mTimeMedicineFragmentTwo, mTimeMedicineFragmentThree;

    RelativeLayout mLayoutRemindOne, mLayoutRemindTwo, mLayoutRemindThree;
    FrameLayout mLayoutTimeOne, mLayoutTimeTwo, mLayoutTimeThree;
    ImageView imagePlusRemind, imageMinusRemind;
    TextView mTextNumber, mTextTitle;
    TextView mTextTime1, mTextTime2, mTextTime3;
    EditText mEditMessage;

    int mNumberRemind = 3;
    Boolean mStatusOne = false, mStatusTwo = false, mStatusThree = false;
    String mTime1, mTime2, mTime3, mMessage;
    SimpleDateFormat simpleDateFormat;


    SharedPreferences sharedPreferences;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_remind);

        try {
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        init();

    }

    private void getData() throws ParseException {
        sharedPreferences = getSharedPreferences(Constant.SHARED_PREFERENCES, MODE_PRIVATE);
        mNumberRemind = sharedPreferences.getInt(Constant.NUMBER_REMIND, 3);

        mTime1 = sharedPreferences.getString(Constant.TIME_MEDICINE_REMIND_1, "08:00");
        mTime2 = sharedPreferences.getString(Constant.TIME_MEDICINE_REMIND_2, "12:30");
        mTime3 = sharedPreferences.getString(Constant.TIME_MEDICINE_REMIND_3, "18:30");

        mMessage = sharedPreferences.getString(Constant.MESSAGE_MEDICINE_REMIND, getResources().getString(R.string.medicine_message_detail));

        simpleDateFormat = new SimpleDateFormat("HH:mm");


    }

    private void init() {

        mTextTitle = new TextView(this);
        mTextTitle.setText(getResources().getString(R.string.medicine_title));
        mTextTitle.setTextSize(23);
        mTextTitle.setTypeface(null, Typeface.BOLD);
        mTextTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mTextTitle.setGravity(Gravity.CENTER);
        mTextTitle.setTextColor(getResources().getColor(R.color.colorMainPink));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mTextTitle);


        imagePlusRemind = findViewById(R.id.image_plus_remind);
        imageMinusRemind = findViewById(R.id.image_minus_remind);
        mTextNumber = findViewById(R.id.text_number);
        mTextNumber.setText(mNumberRemind+"");

        mLayoutRemindOne = findViewById(R.id.layout_remind_one);
        mLayoutRemindTwo = findViewById(R.id.layout_remind_two);
        mLayoutRemindThree = findViewById(R.id.layout_remind_three);

        mLayoutTimeOne = findViewById(R.id.layout_time_picker_one);
        mLayoutTimeTwo = findViewById(R.id.layout_time_picker_two);
        mLayoutTimeThree = findViewById(R.id.layout_time_picker_three);

        mTextTime1 = findViewById(R.id.text_time_medicine1);
        mTextTime2 = findViewById(R.id.text_time_medicine2);
        mTextTime3 = findViewById(R.id.text_time_medicine3);
        mTextTime1.setText(mTime1);
        mTextTime2.setText(mTime2);
        mTextTime3.setText(mTime3);

        mLayoutTimeOne.setVisibility(View.GONE);
        mLayoutTimeTwo.setVisibility(View.GONE);
        mLayoutTimeThree.setVisibility(View.GONE);

        mEditMessage = findViewById(R.id.edit_message);
        mEditMessage.setText(mMessage);


        setVisibleRemind();

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionOne = mFragmentManager.beginTransaction();
        mTimeMedicineFragmentOne = new TimePickerFragment();
        fragmentTransactionOne.add(R.id.layout_time_picker_one, mTimeMedicineFragmentOne, "ONE").commit();

        FragmentTransaction fragmentTransactionTwo = mFragmentManager.beginTransaction();
        mTimeMedicineFragmentTwo = new TimePickerFragment();
        fragmentTransactionTwo.add(R.id.layout_time_picker_two, mTimeMedicineFragmentTwo, "TWO").commit();

        FragmentTransaction fragmentTransactionThree = mFragmentManager.beginTransaction();
        mTimeMedicineFragmentThree = new TimePickerFragment();
        fragmentTransactionThree.add(R.id.layout_time_picker_three, mTimeMedicineFragmentThree, "THREE").commit();


        imageMinusRemind.setOnClickListener(this);
        imagePlusRemind.setOnClickListener(this);
        mLayoutRemindOne.setOnClickListener(this);
        mLayoutRemindTwo.setOnClickListener(this);
        mLayoutRemindThree.setOnClickListener(this);

    }

    private void setVisibleRemind() {
        switch (mNumberRemind) {
            case 1:
                mLayoutRemindOne.setVisibility(View.VISIBLE);
                mLayoutRemindTwo.setVisibility(View.GONE);
                mLayoutRemindThree.setVisibility(View.GONE);
                break;
            case 2:
                mLayoutRemindOne.setVisibility(View.VISIBLE);
                mLayoutRemindTwo.setVisibility(View.VISIBLE);
                mLayoutRemindThree.setVisibility(View.GONE);
                break;
            case 3:
                mLayoutRemindOne.setVisibility(View.VISIBLE);
                mLayoutRemindTwo.setVisibility(View.VISIBLE);
                mLayoutRemindThree.setVisibility(View.VISIBLE);
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_minus_remind:
                changeNumberRemind(false);
                break;
            case R.id.image_plus_remind:
                changeNumberRemind(true);
                break;
            case R.id.layout_remind_one:
                mStatusOne = !mStatusOne;
                setVisibleTimePicker(mStatusOne, mLayoutTimeOne);
                break;
            case R.id.layout_remind_two:
                mStatusTwo = !mStatusTwo;
                setVisibleTimePicker(mStatusTwo, mLayoutTimeTwo);
                break;
            case R.id.layout_remind_three:
                mStatusThree = !mStatusThree;
                setVisibleTimePicker(mStatusThree, mLayoutTimeThree);
                break;
        }
    }

    private void setVisibleTimePicker(boolean status, FrameLayout layoutTimePicker) {
        if (status)
            layoutTimePicker.setVisibility(View.VISIBLE);
        else
            layoutTimePicker.setVisibility(View.GONE);
    }

    private void changeNumberRemind(boolean plus) {
        if (plus) {
            if (mNumberRemind < 3)
                mNumberRemind++;
        } else {
            if (mNumberRemind > 1)
                mNumberRemind--;
        }
        mTextNumber.setText(mNumberRemind + "");
        setVisibleRemind();
    }


    @Override
    public void onSetTime(Calendar calendar, boolean save, String tag) {

        String time = simpleDateFormat.format(calendar.getTime());

        if (tag.equals("ONE")) {
            if (save) {
                mTime1 = time;
                mTextTime1.setText(time);
            }
            mStatusOne = false;
            setVisibleTimePicker(mStatusOne, mLayoutTimeOne);

        } else if (tag.equals("TWO")) {
            if (save) {
                mTime2 = time;
                mTextTime2.setText(time);
            }
            mStatusTwo = false;
            setVisibleTimePicker(mStatusTwo, mLayoutTimeTwo);

        } else if (tag.equals("THREE")) {
            if (save) {
                mTime3 = time;
                mTextTime3.setText(time);
            }
            mStatusThree = false;
            setVisibleTimePicker(mStatusThree, mLayoutTimeThree);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMessage = mEditMessage.getText().toString();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(Constant.NUMBER_REMIND, mNumberRemind);
        editor.putString(Constant.TIME_MEDICINE_REMIND_1, mTime1);
        editor.putString(Constant.TIME_MEDICINE_REMIND_2, mTime2);
        editor.putString(Constant.TIME_MEDICINE_REMIND_3, mTime3);
        editor.putString(Constant.MESSAGE_MEDICINE_REMIND, mMessage);
        editor.apply();

    }
}