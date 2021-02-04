package com.example.eva.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.Caculator;
import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.utils.UnifiedNativeUtils;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;


import java.sql.Date;
import java.util.ArrayList;

public class HomeFragment extends Fragment {
    SharedPreferences mSharedPreferences;

    CyclePeriod mCyclePeriod;
    View mView;
    LinearLayout mLayoutHome, mLayoutDetail, mLayoutNotOvulation;
    TextView mTextDate, mTextPeriod, mTextNumberDate, mTextDescription;
    TextView mTextTitleHome, mTextNumberDay, mTextStartDay, mTextEndDay, mTextDayOvulation;
    int mIDCyclePeriod;
    private UnifiedNativeUtils mUnifiedNativeUtils;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        getData();
        showPieChart();
        showTextView();
        mUnifiedNativeUtils=new UnifiedNativeUtils();
        mUnifiedNativeUtils.refreshAd(getActivity(), Constant.SMALL_NATIVE_AD, R.id.fl_adplaceholder);
        return mView;
    }

    private void getData() {
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mIDCyclePeriod = mSharedPreferences.getInt(Constant.ID_CYCLE_PERIOD, 1);
        DBManager dbManager = new DBManager(getContext());
        mCyclePeriod = dbManager.getCurrentCycle(mIDCyclePeriod);
        if (mIDCyclePeriod != mCyclePeriod.getId()) {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putInt(Constant.ID_CYCLE_PERIOD, mCyclePeriod.getId());
            editor.apply();
        }
    }

    private void decorateTextView(){
        int state = Caculator.getStage(mCyclePeriod);
        mTextDate.setText(Caculator.getCurrentDateHome());
        String textPeriod;
        String textNumber;
        String textDescription;

        if (state == Caculator.IN_PERIOD) {
            Log.d("BacNT", "IN PERIOD");
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.bg_circle_pink_textview));
            setTextColor(false);

            textPeriod = getContext().getResources().getString(R.string.period);
            long day = Caculator.countDay(
                    Date.valueOf(Caculator.getCurrentDate()),
                    Date.valueOf(mCyclePeriod.getUserBeginDate()));
            textNumber = getContext().getResources().getString(R.string.day) + " " + (day + 1);
            textDescription = getContext().getResources().getString(R.string.home_end_period_in) + " " +
                    Caculator.getDate(mCyclePeriod.getUserEndDate());

            showText(textPeriod, textNumber, textDescription);
            showDetail(false, R.drawable.bg_home_detail_period, R.color.colorPinkText,
                    R.string.detail_period, mCyclePeriod.getUserPeriod(),
                    mCyclePeriod.getUserBeginDate(), mCyclePeriod.getUserEndDate());
            return;
        } else if (state == Caculator.IN_FIRST_NORMAL) {
            Log.d("BacNT", "IN FIRST NORMAL");
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.bg_circle_normal_textview));
            setTextColor(true);
            textPeriod = getContext().getResources().getString(R.string.window_fertility_in);
            long day = Caculator.countDay(
                    Date.valueOf(mCyclePeriod.getBeginFertility()),
                    Date.valueOf(Caculator.getCurrentDate()));
            textNumber = day + " " + getContext().getResources().getString(R.string.days);
            textDescription = getContext().getResources().getString(R.string.home_normal) + " < 10%";
            showText(textPeriod, textNumber, textDescription);
            showDetail(false, R.drawable.bg_home_detail_normal, R.color.colorBlueText,
                    R.string.detail_normal, (int) Caculator.countFirstNormalDay(mCyclePeriod),
                    Caculator.getStartFirstNormal(mCyclePeriod),
                    Caculator.getEndFirstNormal(mCyclePeriod));
            return;
        } else if (state == Caculator.IN_OVULATION) {
            Log.d("BacNT", "IN OVULATION");
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.bg_circle_ovulation_blur_textview));
            setTextColor(false);
            textPeriod = getContext().getResources().getString(R.string.window_fertility);
            long day = Caculator.countDay(
                    Date.valueOf(Caculator.getCurrentDate()),
                    Date.valueOf(mCyclePeriod.getBeginFertility()));
            textNumber = getContext().getResources().getString(R.string.day) + " " + (day + 1);
            textDescription = getContext().getResources().getString(R.string.home_begin_fertility);
            showText(textPeriod, textNumber, textDescription);
            showDetail(false, R.drawable.bg_home_detail_fertility, R.color.colorTextFertility,
                    R.string.detail_fertility,
                    (int)Caculator.countFirstFertility(mCyclePeriod)+
                            (int)Caculator.countSecondFertility(mCyclePeriod),
                    mCyclePeriod.getBeginFertility(), mCyclePeriod.getEndFertility());
            return;
        } else if (state == Caculator.IS_OVULATION) {
            Log.d("BacNT", "IS OVULATION");
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.bg_circle_ovulation_textview));
            setTextColor(false);
            textPeriod = "";
            textNumber = getContext().getResources().getString(R.string.ovulation);
            textDescription = getContext().getResources().getString(R.string.home_ovulation);
            showText(textPeriod, textNumber, textDescription);
            showDetail(true,R.drawable.bg_home_detail_fertility, R.color.colorTextFertility,
                    R.string.detail_ovulation,1,mCyclePeriod.getOvulationDate(),
                    mCyclePeriod.getOvulationDate());
            return;
        } else if (state == Caculator.IN_SECOND_NORMAL) {
            Log.d("BacNT", "IN SECOND NORMAL");
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.bg_circle_normal_textview));
            setTextColor(true);
            textPeriod = getContext().getResources().getString(R.string.home_period_in);
            long day = Caculator.countDay(
                    Date.valueOf(mCyclePeriod.getNextBeginCycle()),
                    Date.valueOf(Caculator.getCurrentDate()));
            textNumber = day + " " + getContext().getResources().getString(R.string.days);
            textDescription = getContext().getResources().getString(R.string.home_normal) + " < 10%";
            showText(textPeriod, textNumber, textDescription);
            showDetail(false, R.drawable.bg_home_detail_normal, R.color.colorBlueText,
                    R.string.detail_normal, (int) Caculator.countSecondNormal(mCyclePeriod),
                    Caculator.getStartSecondNormal(mCyclePeriod),
                    Caculator.getEndSecondNormal(mCyclePeriod));
            return;
        } else if (state == Caculator.IN_LATE) {
            Log.d("BacNT", "IN LATE");
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.bg_circle_grey_textview));
            setTextColor(false);
            textPeriod = getContext().getResources().getString(R.string.period);
            long day = Caculator.countDay(
                    Date.valueOf(Caculator.getCurrentDate()),
                    Date.valueOf(mCyclePeriod.getBeginDate())
            );
            textNumber = getContext().getResources().getString(R.string.late) + " " +
                    day +" "+ getContext().getResources().getString(R.string.days);
            textDescription = "";
            showText(textPeriod, textNumber, textDescription);
            showDetail(false, R.drawable.bg_home_detail_period, R.color.colorPinkText,
                    R.string.detail_period_guess, mCyclePeriod.getPeriod(),
                    mCyclePeriod.getBeginDate(), mCyclePeriod.getEndDate());
            return;
        }
    }

    private void showTextView() {
        Display mDisplay= getActivity().getWindowManager().getDefaultDisplay();
        int width= mDisplay.getWidth();
        int Height= mDisplay.getHeight();
        float screenSize=(float) Height/width;
        mLayoutHome = mView.findViewById(R.id.layout_home);
        if(screenSize<1.8f){
            mLayoutHome.getLayoutParams().width=(int) getContext().getResources().getDimension(R.dimen.len_380);
            mLayoutHome.getLayoutParams().height=(int) getContext().getResources().getDimension(R.dimen.len_380);
        }else if (screenSize<1.9f){
            mLayoutHome.getLayoutParams().width=(int) getContext().getResources().getDimension(R.dimen.len_390);
            mLayoutHome.getLayoutParams().height=(int) getContext().getResources().getDimension(R.dimen.len_390);
        }

        mTextDate = mView.findViewById(R.id.text_date);
        mTextPeriod = mView.findViewById(R.id.text_period);
        mTextNumberDate = mView.findViewById(R.id.text_number_date);
        mTextDescription = mView.findViewById(R.id.text_description_period);

        mTextTitleHome = mView.findViewById(R.id.text_title_home);
        mTextNumberDay = mView.findViewById(R.id.text_number_day);
        mTextStartDay = mView.findViewById(R.id.text_start_day);
        mTextEndDay = mView.findViewById(R.id.text_end_day);
        mTextDayOvulation = mView.findViewById(R.id.text_day_ovulation);
        mLayoutDetail = mView.findViewById(R.id.layout_detail);
        mLayoutNotOvulation = mView.findViewById(R.id.layout_not_ovulation);
        decorateTextView();
        mLayoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decorateTextView();
            }
        });
    }

    private void showText(String textPeriod, String numberDate, String description) {
        mTextPeriod.setText(textPeriod);
        mTextNumberDate.setText(numberDate);
        mTextDescription.setText(description);
    }
    private void setTextColor(boolean isNormal) {
        if (!isNormal) {
            mTextDate.setTextColor(getResources().getColor(R.color.colorWhite));
            mTextPeriod.setTextColor(getResources().getColor(R.color.colorWhite));
            mTextNumberDate.setTextColor(getResources().getColor(R.color.colorWhite));
            mTextDescription.setTextColor(getResources().getColor(R.color.colorWhite));

        } else {
            mTextDate.setTextColor(getResources().getColor(R.color.colorBlueText));
            mTextPeriod.setTextColor(getResources().getColor(R.color.colorBlueText));
            mTextNumberDate.setTextColor(getResources().getColor(R.color.colorBlueText));
            mTextDescription.setTextColor(getResources().getColor(R.color.colorBlueText));

        }
    }

    private void showDetail(boolean isOvulation, int background, int colorText, int stringTitle,
                            int numberDay, String startDay, String endDay) {
        setVisibleOvulation(isOvulation);
        mLayoutDetail.setBackground(getContext().getResources().getDrawable(background));
        mTextTitleHome.setTextColor(getContext().getResources().getColor(colorText));
        mTextNumberDay.setTextColor(getContext().getResources().getColor(colorText));
        mTextStartDay.setTextColor(getContext().getResources().getColor(colorText));
        mTextEndDay.setTextColor(getContext().getResources().getColor(colorText));
        mTextTitleHome.setText(getContext().getResources().getString(stringTitle));
        mTextNumberDay.setText(numberDay + " " + getContext().getResources().getString(R.string.days));
        mTextStartDay.setText(Caculator.showDateTime(startDay));
        mTextEndDay.setText(Caculator.showDateTime(endDay));
        mTextDayOvulation.setTextColor(getContext().getResources().getColor(colorText));
        mTextDayOvulation.setText(startDay);
    }
    private void setVisibleOvulation(boolean isOvulation) {
        if (isOvulation) {
            mTextDayOvulation.setVisibility(View.VISIBLE);
            mLayoutNotOvulation.setVisibility(View.GONE);
        } else {
            mTextDayOvulation.setVisibility(View.GONE);
            mLayoutNotOvulation.setVisibility(View.VISIBLE);
        }
    }

    private void showPieChart() {
        PieChart pieChart = mView.findViewById(R.id.piechart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setExtraOffsets(10, 10, 10, 10);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(77f);
        pieChart.setTransparentCircleRadius(83f);

        ArrayList<PieEntry> yValues = new ArrayList<>();
        if (mCyclePeriod.getUserPeriod() != 0)
            yValues.add(new PieEntry(mCyclePeriod.getUserPeriod()));
        else
            yValues.add(new PieEntry(mCyclePeriod.getPeriod()));
        yValues.add(new PieEntry(Caculator.countFirstNormalDay(mCyclePeriod)));
        yValues.add(new PieEntry(Caculator.countFirstFertility(mCyclePeriod)));
        yValues.add(new PieEntry(1));
        yValues.add(new PieEntry(Caculator.countSecondFertility(mCyclePeriod)));
        yValues.add(new PieEntry(Caculator.countSecondNormal(mCyclePeriod)));

        PieDataSet dataSet = new PieDataSet(yValues, "DateCycle");
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(getResources().getColor(R.color.colorMainPink));
        colors.add(getResources().getColor(R.color.colorCircleNormal));
        colors.add(getResources().getColor(R.color.colorCircleOvulationBlur));
        colors.add(getResources().getColor(R.color.colorCircleOvulation));
        colors.add(getResources().getColor(R.color.colorCircleOvulationBlur));
        colors.add(getResources().getColor(R.color.colorCircleNormal));
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.WHITE);
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("BacNT", "click pieChart");
                if (e == null) {
                    return;
                }
                if (yValues.get(0) == e) {
                    Log.d("BacNT", "pieChart : 1");
                    if(mCyclePeriod.getUserBeginDate()==null){
                        showDetail(false, R.drawable.bg_home_detail_period, R.color.colorPinkText,
                                R.string.detail_period_guess, mCyclePeriod.getPeriod(),
                                mCyclePeriod.getBeginDate(), mCyclePeriod.getEndDate());
                    }else {
                        showDetail(false, R.drawable.bg_home_detail_period, R.color.colorPinkText,
                                R.string.detail_period, mCyclePeriod.getUserPeriod(),
                                mCyclePeriod.getUserBeginDate(), mCyclePeriod.getUserEndDate());
                    }

                } else if (yValues.get(1)==e) {
                    Log.d("BacNT", "pieChart : 2");
                    showDetail(false, R.drawable.bg_home_detail_normal, R.color.colorBlueText,
                            R.string.detail_normal, (int) Caculator.countFirstNormalDay(mCyclePeriod),
                            Caculator.getStartFirstNormal(mCyclePeriod),
                            Caculator.getEndFirstNormal(mCyclePeriod));
                } else if (yValues.get(2) == e) {
                    Log.d("BacNT", "pieChart : 3");
                    showDetail(false, R.drawable.bg_home_detail_fertility,
                            R.color.colorTextFertility, R.string.detail_fertility,
                            (int)Caculator.countFirstFertility(mCyclePeriod)+
                                    (int)Caculator.countSecondFertility(mCyclePeriod),
                            mCyclePeriod.getBeginFertility(), mCyclePeriod.getEndFertility());
                } else if (yValues.get(3) == e) {
                    Log.d("BacNT", "pieChart : 4");
                    showDetail(true,R.drawable.bg_home_detail_fertility, R.color.colorTextFertility,
                            R.string.detail_ovulation,1,mCyclePeriod.getOvulationDate(),
                            mCyclePeriod.getOvulationDate());
                } else if (yValues.get(4) == e) {
                    Log.d("BacNT", "pieChart : 5");
                    showDetail(false, R.drawable.bg_home_detail_fertility, R.color.colorTextFertility,
                            R.string.detail_fertility,
                            (int)Caculator.countFirstFertility(mCyclePeriod)+
                                    (int)Caculator.countSecondFertility(mCyclePeriod),
                            mCyclePeriod.getBeginFertility(), mCyclePeriod.getEndFertility());
                }else if (yValues.get(5)==e){
                    Log.d("BacNT", "pieChart : 6");
                    showDetail(false, R.drawable.bg_home_detail_normal, R.color.colorBlueText,
                            R.string.detail_normal, (int) Caculator.countSecondNormal(mCyclePeriod),
                            Caculator.getStartSecondNormal(mCyclePeriod),
                            Caculator.getEndSecondNormal(mCyclePeriod));
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

}
