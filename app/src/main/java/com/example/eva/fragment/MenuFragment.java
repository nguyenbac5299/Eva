package com.example.eva.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.activity.MedicineRemindActivity;
import com.example.eva.activity.PeriodicRemindActivity;
import com.example.eva.adapter.RemindAdapter;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.callback.OnMenuListener;
import com.example.eva.callback.OnSwitchChangeListener;
import com.example.eva.model.Remind;

import java.util.ArrayList;
import java.util.List;

public class
MenuFragment extends Fragment implements View.OnClickListener, OnItemClickListener, OnSwitchChangeListener {

    int mPeriod;
    int mCycle;
    View mView;
    ImageView imageMinusPeriod, imagePlusPeriod, imageMinusCycle, imagePlusCycle;
    LinearLayout linearRemind, linearSettings, linearDetailRemind, linearDetailSetting;
    Switch mSwitchMedicine;

    TextView mTextPeriod, mTextCycle;
    RelativeLayout layoutMedicine;
    Boolean mStatusVisibleRemind = false;
    Boolean mStatusVisibleSetting = false;

    RecyclerView recyclerView;
    List<Remind> listRemind;
    RadioGroup radioGroupLanguage;
    RadioButton radioVietnamese;
    RadioButton radioEnglish;

    OnMenuListener iListener;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor mEditor;
    Boolean mBeginPeriod, mEndPeriod, mBeginFertility, mEndFertility,mOvulation, mMedicine;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_menu, container, false);

        getData();
        init();
        return mView;
    }

    private void getData() {
        sharedPreferences =getActivity().getSharedPreferences(Constant.SHARED_PREFERENCES,Context.MODE_PRIVATE);
        mCycle=sharedPreferences.getInt(Constant.CYCLE,5);
        mPeriod=sharedPreferences.getInt(Constant.PERIOD,28);

        mBeginPeriod=sharedPreferences.getBoolean(Constant.SWITCH_BEGIN_PERIOD,true);
        mEndPeriod=sharedPreferences.getBoolean(Constant.SWITCH_END_PERIOD,false);
        mBeginFertility=sharedPreferences.getBoolean(Constant.SWITCH_BEGIN_FERTILITY,false);
        mEndFertility=sharedPreferences.getBoolean(Constant.SWITCH_END_FERTILITY,false);
        mOvulation=sharedPreferences.getBoolean(Constant.SWITCH_OVULATION,false);
        mMedicine=sharedPreferences.getBoolean(Constant.SWITCH_MEDICINE,false);

        mEditor=sharedPreferences.edit();
    }

    private void init() {

        mTextPeriod = mView.findViewById(R.id.text_period);
        mTextCycle = mView.findViewById(R.id.text_cycle);
        mSwitchMedicine=mView.findViewById(R.id.switch_medicine);
        mSwitchMedicine.setChecked(mMedicine);

        mTextPeriod.setText(mPeriod+"");
        mTextCycle.setText(mCycle+"");

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

        listRemind.add(new Remind(R.drawable.ic_mc_start, getResources().getString(R.string.start_period_title), getResources().getString(R.string.start_period_content), mBeginPeriod));
        listRemind.add(new Remind(R.drawable.ic_mc_end, getResources().getString(R.string.end_period_title), getResources().getString(R.string.end_period_content), mEndPeriod));
        listRemind.add(new Remind(R.drawable.ic_ovulation_start, getResources().getString(R.string.start_fertility_title), getResources().getString(R.string.start_fertility_content), mBeginFertility));
        listRemind.add(new Remind(R.drawable.ic_ovulation_end, getResources().getString(R.string.end_fertility_title), getResources().getString(R.string.end_fertility_content), mEndFertility));
        listRemind.add(new Remind(R.drawable.ic_ovulation, getResources().getString(R.string.ovulation), getResources().getString(R.string.ovulation), mOvulation));


        recyclerView = mView.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        RemindAdapter adapter = new RemindAdapter(listRemind, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void radioGroupChangeListener() {
        mSwitchMedicine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMedicine=isChecked;
                mEditor.putBoolean(Constant.SWITCH_MEDICINE,mMedicine).apply();
            }
        });


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
                if (mCycle > 20)
                    mCycle--;
                mTextCycle.setText(mCycle + "");
                break;
            case R.id.image_minus_period:
                if (mPeriod > 2)
                    mPeriod--;
                mTextPeriod.setText(mPeriod + "");
                break;
            case R.id.image_plus_cycle:
                if (mCycle < 36)
                    mCycle++;
                mTextCycle.setText(mCycle + "");
                break;
            case R.id.image_plus_period:
                if (mPeriod < 14)
                    mPeriod++;
                mTextPeriod.setText(mPeriod + "");
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

        mStatusVisibleRemind = !mStatusVisibleRemind;
        if (mStatusVisibleRemind) {
            linearDetailRemind.setVisibility(View.VISIBLE);

        } else {
            linearDetailRemind.setVisibility(View.GONE);
        }

    }


    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PeriodicRemindActivity.class);
        intent.putExtra("ID",position);
        startActivity(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        mEditor.putInt(Constant.PERIOD, mPeriod);
        mEditor.putInt(Constant.CYCLE, mCycle);
        mEditor.apply();
        iListener.onChangeMenuCycle(mCycle);
        iListener.onChangeMenuPeriod(mPeriod);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iListener= (OnMenuListener) context;
        }catch (ClassCastException e){

        }

    }

    @Override
    public void switchChange(int position, boolean isChecked) {
        switch (position){
            case 0:
                mEditor.putBoolean(Constant.SWITCH_BEGIN_PERIOD,isChecked).apply();
                break;
            case 1:
                mEditor.putBoolean(Constant.SWITCH_END_PERIOD,isChecked).apply();
                break;
            case 2:
                mEditor.putBoolean(Constant.SWITCH_BEGIN_FERTILITY,isChecked).apply();
                break;
            case 3:
                mEditor.putBoolean(Constant.SWITCH_END_FERTILITY,isChecked).apply();
                break;
            case 4:
                mEditor.putBoolean(Constant.SWITCH_OVULATION,isChecked).apply();
                break;
            default:
                break;
        }
    }
}
