package com.example.eva.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.utils.UnifiedNativeUtils;
import com.example.eva.activity.MedicineRemindActivity;
import com.example.eva.activity.PeriodicRemindActivity;
import com.example.eva.adapter.RemindAdapter;
import com.example.eva.callback.OnChangeRemindListener;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.callback.OnMenuListener;
import com.example.eva.callback.OnSwitchChangeListener;
import com.example.eva.data.DBManager;
import com.example.eva.model.Remind;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment implements View.OnClickListener, OnItemClickListener, OnSwitchChangeListener {

    public static final int REQUEST_CODE = 1;
    int mPeriod, mOldPeriod;
    int mCycle, mOldCycle;
    View mView;
    ImageView imageMinusPeriod, imagePlusPeriod, imageMinusCycle, imagePlusCycle, imageMore;
    LinearLayout linearRemind, linearDetailRemind;
    Switch mSwitchMedicine;

    TextView mTextPeriod, mTextCycle;
    RelativeLayout layoutMedicine;
    Boolean mStatusVisibleRemind = false;

    RecyclerView recyclerView;
    List<Remind> listRemind;
    Remind remindMedicine;

    OnMenuListener iListener;
    OnChangeRemindListener remindListener;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor mEditor;
    Boolean mMedicine;
    DBManager dbManager;
    List<Remind> reminds;
    RemindAdapter adapter;
    private UnifiedNativeUtils mUnifiedNativeUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_menu, container, false);
        getData();
        init();
        mUnifiedNativeUtils = new UnifiedNativeUtils();
        mUnifiedNativeUtils.refreshAd(getActivity(), Constant.MEDIUM_NATIVE_AD, R.id.fl_remind_native_ad);
        return mView;
    }

    private void getData() {
        dbManager = new DBManager(getContext());
        sharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mCycle = sharedPreferences.getInt(Constant.CYCLE, 5);
        mOldCycle = mCycle;
        mPeriod = sharedPreferences.getInt(Constant.PERIOD, 28);
        mOldPeriod = mPeriod;
        remindMedicine = dbManager.getRemind(5);
        mMedicine = remindMedicine.isChooseSwitch();
        mEditor = sharedPreferences.edit();

    }

    private void init() {

        mTextPeriod = mView.findViewById(R.id.text_period);
        mTextCycle = mView.findViewById(R.id.text_cycle);
        mSwitchMedicine = mView.findViewById(R.id.switch_medicine);
        mSwitchMedicine.setChecked(mMedicine);

        mTextPeriod.setText(mPeriod + "");
        mTextCycle.setText(mCycle + "");

        imageMinusCycle = mView.findViewById(R.id.image_minus_cycle);
        imageMinusPeriod = mView.findViewById(R.id.image_minus_period);
        imagePlusCycle = mView.findViewById(R.id.image_plus_cycle);
        imagePlusPeriod = mView.findViewById(R.id.image_plus_period);
        imageMore = mView.findViewById(R.id.image_more);

        layoutMedicine = mView.findViewById(R.id.layout_medicine);
        linearRemind = mView.findViewById(R.id.linear_remind);
        linearDetailRemind = mView.findViewById(R.id.linear_detail_remind);
        linearDetailRemind.setVisibility(View.GONE);

        imagePlusPeriod.setOnClickListener(this);
        imagePlusCycle.setOnClickListener(this);
        imageMinusPeriod.setOnClickListener(this);
        imageMinusCycle.setOnClickListener(this);
        linearRemind.setOnClickListener(this);
        layoutMedicine.setOnClickListener(this);

        reminds = dbManager.getListRemindPeriodic();
        listRemind = new ArrayList<>();
        listRemind.add(new Remind(R.drawable.ic_mc_start, getResources().getString(R.string.start_period_title), reminds.get(0).getContent(), reminds.get(0).isChooseSwitch()));
        listRemind.add(new Remind(R.drawable.ic_mc_end, getResources().getString(R.string.end_period_title), reminds.get(1).getContent(), reminds.get(1).isChooseSwitch()));
        listRemind.add(new Remind(R.drawable.ic_ovulation_start, getResources().getString(R.string.start_fertility_title), reminds.get(2).getContent(), reminds.get(2).isChooseSwitch()));
        listRemind.add(new Remind(R.drawable.ic_ovulation_end, getResources().getString(R.string.end_fertility_title), reminds.get(3).getContent(), reminds.get(3).isChooseSwitch()));
        listRemind.add(new Remind(R.drawable.ic_ovulation, getResources().getString(R.string.ovulation), reminds.get(4).getContent(), reminds.get(4).isChooseSwitch()));

        recyclerView = mView.findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RemindAdapter(listRemind, this, this);
        recyclerView.setAdapter(adapter);

    }

    private void radioGroupChangeListener() {
        mSwitchMedicine.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mMedicine = isChecked;
                remindMedicine.setChooseSwitch(mMedicine);
                dbManager.updateRemind(remindMedicine);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_minus_cycle:
                if (mCycle > 20) {
                    mCycle--;
                    imageMinusCycle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_minus_pink));
                    imagePlusCycle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_plus_pink));
                } else
                    imageMinusCycle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_minus_grey));
                mTextCycle.setText(mCycle + "");
                break;
            case R.id.image_minus_period:
                if (mPeriod > 2) {
                    imageMinusPeriod.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_minus_pink));
                    imagePlusPeriod.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_plus_pink));
                    mPeriod--;
                } else
                    imageMinusPeriod.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_minus_grey));
                mTextPeriod.setText(mPeriod + "");
                break;
            case R.id.image_plus_cycle:
                if (mCycle < 36) {
                    imagePlusCycle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_plus_pink));
                    imageMinusCycle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_minus_pink));
                    mCycle++;
                } else
                    imagePlusCycle.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_plus_grey));
                mTextCycle.setText(mCycle + "");
                break;
            case R.id.image_plus_period:
                if (mPeriod < 14) {
                    imagePlusPeriod.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_plus_pink));
                    imageMinusPeriod.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_minus_pink));
                    mPeriod++;
                } else
                    imagePlusPeriod.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_plus_grey));
                mTextPeriod.setText(mPeriod + "");
                break;
            case R.id.linear_remind:
                visibleDetailRemind();
                break;
            case R.id.layout_medicine:
                startActivityMedicine();
        }
    }

    private void startActivityMedicine() {

        Intent intent = new Intent(getActivity(), MedicineRemindActivity.class);
        startActivity(intent);

    }

    private void visibleDetailRemind() {

        mStatusVisibleRemind = !mStatusVisibleRemind;
        if (mStatusVisibleRemind) {
            linearDetailRemind.setVisibility(View.VISIBLE);
            imageMore.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_up));
        } else {
            linearDetailRemind.setVisibility(View.GONE);
            imageMore.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_down));
        }

    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(getActivity(), PeriodicRemindActivity.class);
        intent.putExtra("ID", position);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == getActivity().RESULT_OK) {
                int id = data.getIntExtra(PeriodicRemindActivity.ITEM, 0);
                String content = data.getStringExtra(PeriodicRemindActivity.MESSAGE);
                listRemind.get(id).setContent(content);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onPause() {
        Log.d("BacNT", "onPause");
        super.onPause();

        if (mOldCycle != mCycle) {
            mEditor.putInt(Constant.CYCLE, mCycle);
            iListener.onChangeMenuCycle(mCycle);
        }
        if (mOldPeriod != mPeriod) {
            mEditor.putInt(Constant.PERIOD, mPeriod);
            iListener.onChangeMenuPeriod(mPeriod);
        }
        mEditor.apply();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            iListener = (OnMenuListener) context;
            remindListener = (OnChangeRemindListener) context;
        } catch (ClassCastException e) {

        }

    }

    @Override
    public void switchChange(int position, boolean isChecked) {
        switch (position) {
            case 0:
                reminds.get(0).setChooseSwitch(isChecked);
                dbManager.updateRemind(reminds.get(0));
                break;
            case 1:
                reminds.get(1).setChooseSwitch(isChecked);
                dbManager.updateRemind(reminds.get(1));
                break;
            case 2:
                reminds.get(2).setChooseSwitch(isChecked);
                dbManager.updateRemind(reminds.get(2));
                break;
            case 3:
                reminds.get(3).setChooseSwitch(isChecked);
                dbManager.updateRemind(reminds.get(3));
                break;
            case 4:
                reminds.get(4).setChooseSwitch(isChecked);
                dbManager.updateRemind(reminds.get(4));
                break;
            default:
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        remindListener.onChangeRemind();
    }
}
