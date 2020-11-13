package com.example.eva.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.eva.R;
import com.example.eva.callback.OnSetupPeriodCycleListener;

import java.util.Calendar;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class SetupCalendarFragment extends Fragment {
    OnSetupPeriodCycleListener mListener;
    int mYear, mMonth, mDayOfMonth;
    Button mButtonFinish;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_celendar, container, false);
        CalendarView calendarView = view.findViewById(R.id.calendar_view);

        mButtonFinish=view.findViewById(R.id.button_finish);
        mButtonFinish.setVisibility(View.GONE);

        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(YEAR);
        mMonth = calendar.get(MONTH);
        mDayOfMonth = calendar.get(DAY_OF_MONTH);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDayOfMonth = dayOfMonth;
                Log.d("BacNT",dayOfMonth+"/"+month+"/"+year);
                mListener.onChangeCalendar(mYear, mMonth, mDayOfMonth);
                mButtonFinish.setVisibility(View.VISIBLE);
            }
        });
        mListener.onChangeCalendar(mYear, mMonth, mDayOfMonth);

        mButtonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onFinishSetup(true);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnSetupPeriodCycleListener) context;
    }
}
