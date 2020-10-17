package com.example.eva.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.R;
import com.example.eva.callback.OnSetupPeriodCycleListener;
import com.shawnlin.numberpicker.NumberPicker;

public class SetupPeriodFragment extends Fragment {

    OnSetupPeriodCycleListener mListener;
    int mPeriod = 5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setup_period, container, false);

        NumberPicker numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mPeriod = newVal;
                mListener.onChangePeriod(mPeriod);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mListener = (OnSetupPeriodCycleListener) context;
        } catch (ClassCastException e) {
        }
    }
}
