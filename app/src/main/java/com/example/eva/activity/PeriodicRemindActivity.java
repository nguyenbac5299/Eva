package com.example.eva.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.eva.R;
import com.example.eva.callback.OnTimePickerPeriodicListener;
import com.example.eva.data.DBManager;
import com.example.eva.fragment.TimePickerFragment;
import com.example.eva.model.Remind;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PeriodicRemindActivity extends AppCompatActivity implements OnTimePickerPeriodicListener {

    public static final String ITEM = "item";
    public static final String MESSAGE = "message";

    FrameLayout mFrameLayout;
    TextView mTextTime, mTextTimeTitle, mTextTitle;
    EditText mEditText;
    boolean mVisible;
    int mId;
    SimpleDateFormat mSimpleDateFormat;
    DBManager mDbManager;
    Remind mRemind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periodic_remind);
        this.setFinishOnTouchOutside(false);
        getData();
        init();
        addEvent();
    }

    private void getData() {
        mDbManager = new DBManager(this);
        Bundle extras = this.getIntent().getExtras();
        mId = extras.getInt("ID");
    }


    private void addEvent() {
        mTextTimeTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVisible = !mVisible;
                if (mVisible)
                    mFrameLayout.setVisibility(View.VISIBLE);
                else
                    mFrameLayout.setVisibility(View.GONE);
            }
        });
    }

    private void init() {

        mTextTitle = findViewById(R.id.text_title);
        mTextTimeTitle = findViewById(R.id.text_time_title);
        mTextTime = findViewById(R.id.text_time_periodic);
        mEditText = findViewById(R.id.edit_message);
        mFrameLayout = findViewById(R.id.layout_time_picker_periodic);
        Button buttonSave = findViewById(R.id.button_save_remind);
        Button buttonCancel = findViewById(R.id.button_cancel_remind);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionOne = fragmentManager.beginTransaction();
        TimePickerFragment timePickerFragment = new TimePickerFragment();
        fragmentTransactionOne
                .add(R.id.layout_time_picker_periodic, timePickerFragment, "PERIODIC")
                .commit();
        mFrameLayout.setVisibility(View.GONE);
        mSimpleDateFormat = new SimpleDateFormat("HH:mm");
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEditText.getText().equals("")) {
                    updateDatabase();
                    Intent intent = new Intent();
                    intent.putExtra(ITEM, mId);
                    intent.putExtra(MESSAGE, mRemind.getContent());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }else {
                }
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        switch (mId) {
            case 0:
                mRemind = mDbManager.getRemind(0);
                mTextTime.setText(mRemind.getTime());
                mEditText.setText(mRemind.getContent());
                mTextTitle.setText(getResources().getString(R.string.start_period_title));
                break;
            case 1:
                mRemind = mDbManager.getRemind(1);
                mTextTime.setText(mRemind.getTime());
                mEditText.setText(mRemind.getContent());
                mTextTitle.setText(getResources().getString(R.string.end_period_title));
                break;
            case 2:
                mRemind = mDbManager.getRemind(2);
                mTextTime.setText(mRemind.getTime());
                mEditText.setText(mRemind.getContent());
                mTextTitle.setText(getResources().getString(R.string.start_fertility_title));
                break;
            case 3:
                mRemind = mDbManager.getRemind(3);
                mTextTime.setText(mRemind.getTime());
                mEditText.setText(mRemind.getContent());
                mTextTitle.setText(getResources().getString(R.string.end_fertility_title));
                break;
            case 4:
                mRemind = mDbManager.getRemind(4);
                mTextTime.setText(mRemind.getTime());
                mEditText.setText(mRemind.getContent());
                mTextTitle.setText(getResources().getString(R.string.ovulation));
                break;
            default:
                break;
        }
    }


    @Override
    public void onSetTime(Calendar calendar, boolean save, String tag) {
        if (save) {
            mTextTime.setText(mSimpleDateFormat.format(calendar.getTime()) + "");
        }
        mVisible = true;
        mFrameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    private void updateDatabase() {
        mRemind.setTime(mTextTime.getText().toString());
        mRemind.setContent(mEditText.getText().toString());
        mDbManager.updateRemind(mRemind);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}