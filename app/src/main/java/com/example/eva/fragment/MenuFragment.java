package com.example.eva.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.R;
import com.example.eva.activity.MedicineRemindActivity;
import com.example.eva.adapter.RemindAdapter;
import com.example.eva.model.Remind;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener {
    int mPeriod;
    int mCycle;
    View mView;
    ImageView imageMinusPeriod, imagePlusPeriod, imageMinusCycle, imagePlusCycle;
    LinearLayout linearRemind, linearSettings, linearDetailRemind, linearDetailSetting;
    RelativeLayout layoutMedicine;
    Boolean mStatusVisibleRemind = false;
    Boolean mStatusVisibleSetting = false;

    RecyclerView recyclerView;
    List<Remind> listRemind;
    RadioGroup radioGroupLanguage;
    RadioButton radioVietnamese;
    RadioButton radioEnglish;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_menu, container, false);
        init();
        getData();
        return mView;
    }

    private void getData() {
    }

    private void init() {
        imageMinusCycle = mView.findViewById(R.id.image_minus_cycle);
        imageMinusPeriod = mView.findViewById(R.id.image_minus_period);
        imagePlusCycle = mView.findViewById(R.id.image_plus_cycle);
        imagePlusPeriod = mView.findViewById(R.id.image_plus_period);


        layoutMedicine = mView.findViewById(R.id.layout_medicine);
        linearRemind = mView.findViewById(R.id.linear_remind);
        linearSettings = mView.findViewById(R.id.linear_setting);
        linearDetailRemind = mView.findViewById(R.id.linear_detail_remind);
        linearDetailRemind.setVisibility(View.GONE);
        linearDetailSetting = mView.findViewById(R.id.linear_detail_setting);
        linearDetailSetting.setVisibility(View.GONE);

        radioGroupLanguage = mView.findViewById(R.id.radio_group);
        radioVietnamese = mView.findViewById(R.id.radio_vietnamese);
        radioEnglish = mView.findViewById(R.id.radio_english);
        radioVietnamese.setChecked(true);

        radioGroupChangeListener();


        imagePlusPeriod.setOnClickListener(this);
        imagePlusCycle.setOnClickListener(this);
        imageMinusPeriod.setOnClickListener(this);
        imageMinusCycle.setOnClickListener(this);
        linearRemind.setOnClickListener(this);
        linearSettings.setOnClickListener(this);
        layoutMedicine.setOnClickListener(this);

        listRemind = new ArrayList<>();

        listRemind.add(new Remind(R.drawable.ic_start_period, getResources().getString(R.string.start_period_title), getResources().getString(R.string.start_period_content), true));
        listRemind.add(new Remind(R.drawable.ic_end_period, getResources().getString(R.string.end_period_title), getResources().getString(R.string.end_period_content), false));
        listRemind.add(new Remind(R.drawable.ic_start_pergant, getResources().getString(R.string.start_fertility_title), getResources().getString(R.string.start_fertility_content), false));
        listRemind.add(new Remind(R.drawable.ic_end_pergant, getResources().getString(R.string.end_fertility_title), getResources().getString(R.string.end_fertility_content), false));
        listRemind.add(new Remind(R.drawable.ic_ovulation, getResources().getString(R.string.ovulation), getResources().getString(R.string.ovulation), false));


        recyclerView = mView.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        RemindAdapter adapter = new RemindAdapter(listRemind);
        recyclerView.setAdapter(adapter);
    }

    private void radioGroupChangeListener() {
        radioGroupLanguage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_english:
                        Toast.makeText(getActivity(), "English", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.radio_vietnamese:
                        Toast.makeText(getActivity(), "Vietnam", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getActivity(), "ok", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_minus_cycle:
                mCycle--;
                break;
            case R.id.image_minus_period:
                mPeriod--;
                break;
            case R.id.image_plus_cycle:
                mCycle++;
                break;
            case R.id.image_plus_period:
                mPeriod++;
                break;
            case R.id.linear_remind:
                visibleDetailRemind();
                break;
            case R.id.linear_setting:
                visibleDetailSeting();
                break;
            case R.id.layout_medicine:
                startActivityMedicine();
        }
    }

    private void startActivityMedicine() {

        Intent intent = new Intent(getActivity(), MedicineRemindActivity.class);
        startActivity(intent);


    }

    private void visibleDetailSeting() {
        mStatusVisibleSetting = !mStatusVisibleSetting;
        if (mStatusVisibleSetting) {
            linearDetailSetting.setVisibility(View.VISIBLE);
        } else {
            linearDetailSetting.setVisibility(View.GONE);
        }
    }

    private void visibleDetailRemind() {
//        Intent intent= new Intent(getActivity(), RemindActivity.class);
//        startActivity(intent);
        mStatusVisibleRemind = !mStatusVisibleRemind;
        if (mStatusVisibleRemind) {
            linearDetailRemind.setVisibility(View.VISIBLE);

        } else {
            linearDetailRemind.setVisibility(View.GONE);

        }

    }
}
