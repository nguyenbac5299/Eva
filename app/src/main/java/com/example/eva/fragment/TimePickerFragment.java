package com.example.eva.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.R;
import com.example.eva.callback.OnTimePickerPeriodicListener;

import java.util.Calendar;

public class TimePickerFragment extends Fragment {

    TimePicker mTimePicker;
    Calendar mCalendar;
    OnTimePickerPeriodicListener mPeriodicListener;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_picker, container, false);
        mTimePicker = view.findViewById(R.id.time_picker);
        mTimePicker.setIs24HourView(true);

        mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                getTime();
                mPeriodicListener.onSetTime(mCalendar, true, getTag());
            }
        });

        return view;
    }

    private void getTime() {
        mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
        mCalendar.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mPeriodicListener = (OnTimePickerPeriodicListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnArticleSelectedListener");
        }
    }
}
