package com.example.eva.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.R;

public class TimeMedicineFragment extends Fragment {

    TimePicker timePicker;
    Button buttonCancel, buttonSave;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_medicine, container, false);
        timePicker=view.findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        buttonCancel=view.findViewById(R.id.button_cancel);
        buttonSave=view.findViewById(R.id.button_save);

        onClickListener();

        return view;
    }

    private void onClickListener() {

    }
}
