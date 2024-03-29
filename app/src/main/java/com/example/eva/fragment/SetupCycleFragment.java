package com.example.eva.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.callback.OnSetupPeriodCycleListener;
import com.shawnlin.numberpicker.NumberPicker;

public class SetupCycleFragment extends Fragment {
    OnSetupPeriodCycleListener IListener;
    int mCycle = Constant.DEFAULT_CYCLE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Setup", "CycleFragment");
        View view = inflater.inflate(R.layout.fragment_setup_cycle, container, false);

        NumberPicker numberPicker = view.findViewById(R.id.number_picker_cycle);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCycle = newVal;
                IListener.onChangeCycle(mCycle);
            }
        });
        IListener.onChangeCycle(mCycle);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IListener = (OnSetupPeriodCycleListener) context;
    }
}
