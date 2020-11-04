package com.example.eva.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eva.R;
import com.example.eva.fragment.TimeMedicineFragment;

public class MedicineRemindActivity extends AppCompatActivity implements View.OnClickListener {

    FragmentManager mFragmentManager;
    TimeMedicineFragment mTimeMedicineFragmentOne, mTimeMedicineFragmentTwo, mTimeMedicineFragmentThree;

    RelativeLayout mLayoutRemindOne, mLayoutRemindTwo, mLayoutRemindThree;
    FrameLayout mLayoutTimeOne, mLayoutTimeTwo, mLayoutTimeThree;
    ImageView imagePlusRemind, imageMinusRemind;
    TextView mTextNumber, mTextTitle;

    int mNumberRemind=3;
    Boolean mStatusOne=false, mStatusTwo=false, mStatusThree=false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_remind);

        init();

    }

    private void init() {

        mTextTitle= new TextView(this);
        mTextTitle.setText(getResources().getString(R.string.medicine_title));
        mTextTitle.setTextSize(23);
        mTextTitle.setTypeface(null, Typeface.BOLD);
        mTextTitle.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        mTextTitle.setGravity(Gravity.CENTER);
        mTextTitle.setTextColor(getResources().getColor(R.color.colorPinkButton));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(mTextTitle);


        imagePlusRemind=findViewById(R.id.image_plus_remind);
        imageMinusRemind=findViewById(R.id.image_minus_remind);
        mTextNumber=findViewById(R.id.text_number);

        mLayoutRemindOne=findViewById(R.id.layout_remind_one);
        mLayoutRemindTwo=findViewById(R.id.layout_remind_two);
        mLayoutRemindThree=findViewById(R.id.layout_remind_three);

        mLayoutTimeOne=findViewById(R.id.layout_time_picker_one);
        mLayoutTimeTwo=findViewById(R.id.layout_time_picker_two);
        mLayoutTimeThree=findViewById(R.id.layout_time_picker_three);

        mLayoutTimeOne.setVisibility(View.GONE);
        mLayoutTimeTwo.setVisibility(View.GONE);
        mLayoutTimeThree.setVisibility(View.GONE);

        setVisibleRemind();

        mFragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransactionOne= mFragmentManager.beginTransaction();
        mTimeMedicineFragmentOne = new TimeMedicineFragment();
        fragmentTransactionOne.add(R.id.layout_time_picker_one,mTimeMedicineFragmentOne,"ONE").commit();

        FragmentTransaction fragmentTransactionTwo= mFragmentManager.beginTransaction();
        mTimeMedicineFragmentTwo = new TimeMedicineFragment();
        fragmentTransactionTwo.add(R.id.layout_time_picker_two,mTimeMedicineFragmentTwo,"TWO").commit();

        FragmentTransaction fragmentTransactionThree= mFragmentManager.beginTransaction();
        mTimeMedicineFragmentThree = new TimeMedicineFragment();
        fragmentTransactionThree.add(R.id.layout_time_picker_three,mTimeMedicineFragmentThree,"THREE").commit();


        imageMinusRemind.setOnClickListener(this);
        imagePlusRemind.setOnClickListener(this);
        mLayoutRemindOne.setOnClickListener(this);
        mLayoutRemindTwo.setOnClickListener(this);
        mLayoutRemindThree.setOnClickListener(this);

    }

    private void setVisibleRemind() {
        switch (mNumberRemind){
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
        switch (v.getId()){
            case R.id.image_minus_remind:
                changeNumberRemind(false);
                break;
            case R.id.image_plus_remind:
                changeNumberRemind(true);
                break;
            case R.id.layout_remind_one:
                mStatusOne=!mStatusOne;
                setVisibleTimePicker(mStatusOne, mLayoutTimeOne);
                break;
            case R.id.layout_remind_two:
                mStatusTwo=!mStatusTwo;
                setVisibleTimePicker(mStatusTwo, mLayoutTimeTwo);
                break;
            case R.id.layout_remind_three:
                mStatusThree=!mStatusThree;
                setVisibleTimePicker(mStatusThree, mLayoutTimeThree);
                break;
        }
    }

    private void setVisibleTimePicker(boolean status, FrameLayout layoutTimePicker ) {
        if(status)
            layoutTimePicker.setVisibility(View.VISIBLE);
        else
            layoutTimePicker.setVisibility(View.GONE);
    }

    private void changeNumberRemind(boolean plus) {
        if(plus){
            if(mNumberRemind<3)
                mNumberRemind++;
        }else {
            if (mNumberRemind>1)
                mNumberRemind--;
        }
        mTextNumber.setText(mNumberRemind+"");
        setVisibleRemind();
    }
}