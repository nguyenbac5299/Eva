package com.example.eva.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eva.R;
import com.example.eva.callback.OnTimePickerPeriodicListener;
import com.example.eva.data.DBManager;
import com.example.eva.fragment.TimePickerFragment;
import com.example.eva.model.Remind;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MedicineRemindActivity extends AppCompatActivity implements View.OnClickListener, OnTimePickerPeriodicListener {

    FragmentManager mFragmentManager;
    TimePickerFragment mTimeMedicineFragmentOne, mTimeMedicineFragmentTwo, mTimeMedicineFragmentThree;

    RelativeLayout mLayoutRemindOne, mLayoutRemindTwo, mLayoutRemindThree;
    FrameLayout mLayoutTimeOne, mLayoutTimeTwo, mLayoutTimeThree;
    ImageView imagePlusRemind, imageMinusRemind;
    TextView mTextNumber, mTextTime1, mTextTime2, mTextTime3;
    EditText mEditMessage;
    Button mButtonSave, mButtonCancel;

    int mNumberRemind = 3;
    Boolean mStatusOne = false, mStatusTwo = false, mStatusThree = false;
    String mTime1 = "", mTime2 = "", mTime3 = "", mMessage;
    SimpleDateFormat simpleDateFormat;
    DBManager mDbManager;
    Remind mRemind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_medicine_remind);
        this.setFinishOnTouchOutside(false);
        try {
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        init();

    }
    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    private void getData() throws ParseException {
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        mDbManager = new DBManager(this);
        mRemind = mDbManager.getRemind(5);
        mMessage = mRemind.getContent();
        String time = mRemind.getTime();
        String[] times = time.split(",");
        mNumberRemind=times.length;
        switch (mNumberRemind){
            case 1:
                mTime1=times[0];
                break;
            case 2:
                mTime1=times[0];
                mTime2=times[1];
                break;
            case 3:
                mTime1=times[0];
                mTime2=times[1];
                mTime3=times[2];
                break;
        }

    }

    private void init() {

        imagePlusRemind = findViewById(R.id.image_plus_remind);
        imageMinusRemind = findViewById(R.id.image_minus_remind);
        mTextNumber = findViewById(R.id.text_number);
        mTextNumber.setText(mNumberRemind + "");

        mLayoutRemindOne = findViewById(R.id.layout_remind_one);
        mLayoutRemindTwo = findViewById(R.id.layout_remind_two);
        mLayoutRemindThree = findViewById(R.id.layout_remind_three);

        mLayoutTimeOne = findViewById(R.id.layout_time_picker_one);
        mLayoutTimeTwo = findViewById(R.id.layout_time_picker_two);
        mLayoutTimeThree = findViewById(R.id.layout_time_picker_three);

        mButtonCancel = findViewById(R.id.button_cancel_remind_medicine);
        mButtonSave = findViewById(R.id.button_save_remind_medicine);

        mTextTime1 = findViewById(R.id.text_time_medicine1);
        mTextTime2 = findViewById(R.id.text_time_medicine2);
        mTextTime3 = findViewById(R.id.text_time_medicine3);
        mTextTime1.setText(mTime1);
        mTextTime2.setText(mTime2);
        mTextTime3.setText(mTime3);

        mLayoutTimeOne.setVisibility(View.GONE);
        mLayoutTimeTwo.setVisibility(View.GONE);
        mLayoutTimeThree.setVisibility(View.GONE);

        mEditMessage = findViewById(R.id.edit_message_medicine);
        mEditMessage.setText(mMessage);


        setVisibleRemind();

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionOne = mFragmentManager.beginTransaction();
        mTimeMedicineFragmentOne = new TimePickerFragment();
        fragmentTransactionOne
                .add(R.id.layout_time_picker_one, mTimeMedicineFragmentOne, "ONE")
                .commit();

        FragmentTransaction fragmentTransactionTwo = mFragmentManager.beginTransaction();
        mTimeMedicineFragmentTwo = new TimePickerFragment();
        fragmentTransactionTwo
                .add(R.id.layout_time_picker_two, mTimeMedicineFragmentTwo, "TWO")
                .commit();

        FragmentTransaction fragmentTransactionThree = mFragmentManager.beginTransaction();
        mTimeMedicineFragmentThree = new TimePickerFragment();
        fragmentTransactionThree
                .add(R.id.layout_time_picker_three, mTimeMedicineFragmentThree, "THREE")
                .commit();


        imageMinusRemind.setOnClickListener(this);
        imagePlusRemind.setOnClickListener(this);
        mLayoutRemindOne.setOnClickListener(this);
        mLayoutRemindTwo.setOnClickListener(this);
        mLayoutRemindThree.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);

    }

    private void setVisibleRemind() {
        switch (mNumberRemind) {
            case 1:
                mLayoutRemindOne.setVisibility(View.VISIBLE);
                mLayoutRemindTwo.setVisibility(View.GONE);
                mLayoutRemindThree.setVisibility(View.GONE);
                imageMinusRemind.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_grey));
                imagePlusRemind.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_pink));
                break;
            case 2:
                mLayoutRemindOne.setVisibility(View.VISIBLE);
                mLayoutRemindTwo.setVisibility(View.VISIBLE);
                mLayoutRemindThree.setVisibility(View.GONE);
                imageMinusRemind.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_pink));
                imagePlusRemind.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_pink));
                break;
            case 3:
                mLayoutRemindOne.setVisibility(View.VISIBLE);
                mLayoutRemindTwo.setVisibility(View.VISIBLE);
                mLayoutRemindThree.setVisibility(View.VISIBLE);
                imagePlusRemind.setImageDrawable(getResources().getDrawable(R.drawable.ic_plus_grey));
                imageMinusRemind.setImageDrawable(getResources().getDrawable(R.drawable.ic_minus_pink));
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
                setVisibleTimePicker(mStatusOne, "ONE");
                break;
            case R.id.layout_remind_two:
                mStatusTwo = !mStatusTwo;
                setVisibleTimePicker(mStatusTwo, "TWO");
                break;
            case R.id.layout_remind_three:
                mStatusThree = !mStatusThree;
                setVisibleTimePicker(mStatusThree, "THREE");
                break;
            case R.id.button_cancel_remind_medicine:
                finish();
                break;
            case R.id.button_save_remind_medicine:
                saveRemindMedicine();
                break;
        }
    }

    private void saveRemindMedicine() {
        String time="";
        switch (mNumberRemind){
            case 1:
                time=mTime1;
                break;
            case 2:
                time=mTime1+","+mTime2;
                break;
            case 3:
                time=mTime1+","+mTime2+","+mTime3;
                break;
        }
        mRemind.setTime(time);
        mRemind.setContent(mMessage);
        mDbManager.updateRemind(mRemind);
        finish();
    }

    private void setVisibleTimePicker(boolean status, String tag) {

        if (status) {
            if (tag.equals("ONE")) {
                mLayoutTimeOne.setVisibility(View.VISIBLE);
                mLayoutTimeTwo.setVisibility(View.GONE);
                mLayoutTimeThree.setVisibility(View.GONE);
            }else if(tag.equals("TWO")){
                mLayoutTimeOne.setVisibility(View.GONE);
                mLayoutTimeTwo.setVisibility(View.VISIBLE);
                mLayoutTimeThree.setVisibility(View.GONE);
            }else if(tag.equals("THREE")){
                mLayoutTimeOne.setVisibility(View.GONE);
                mLayoutTimeTwo.setVisibility(View.GONE);
                mLayoutTimeThree.setVisibility(View.VISIBLE);
            }
        }
        else {
            mLayoutTimeOne.setVisibility(View.GONE);
            mLayoutTimeTwo.setVisibility(View.GONE);
            mLayoutTimeThree.setVisibility(View.GONE);
        }


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
            mStatusOne = true;
            setVisibleTimePicker(mStatusOne, tag);

        } else if (tag.equals("TWO")) {
            if (save) {
                mTime2 = time;
                mTextTime2.setText(time);
            }
            mStatusTwo = true;
            setVisibleTimePicker(mStatusTwo, tag);

        } else if (tag.equals("THREE")) {
            if (save) {
                mTime3 = time;
                mTextTime3.setText(time);
            }
            mStatusThree = true;
            setVisibleTimePicker(mStatusThree, tag);
        }

    }
}