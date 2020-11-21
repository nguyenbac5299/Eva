package com.example.eva.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.CaculatorCyclePeriod;
import com.example.eva.R;
import com.example.eva.model.CyclePeriod;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    PieChart pieChart;
    CyclePeriod mCyclePeriod;
    View mView;
    LinearLayout mLayoutHome;
    TextView mTextDate, mTextPeriod,mTextNumberDate,mTextDescription;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        mCyclePeriod= (CyclePeriod) getArguments().getSerializable("CYCLEPERIOD");

        showPieChart();
        showTextView();

        return mView;

    }

    private void showTextView() {
        mLayoutHome=mView.findViewById(R.id.layout_home);
        mTextDate=mView.findViewById(R.id.text_date);
        mTextPeriod=mView.findViewById(R.id.text_period);
        mTextNumberDate=mView.findViewById(R.id.text_number_date);
        mTextDescription=mView.findViewById(R.id.text_description_period);
        int state=CaculatorCyclePeriod.getStage(mCyclePeriod);
        mTextDate.setText(CaculatorCyclePeriod.getCurrentDateHome());

        if(state==CaculatorCyclePeriod.IN_PERIOD){
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.circle_pink_textview));
            setTextColor(false);
            return;
        }
        else if(state==CaculatorCyclePeriod.IN_FIRST_NORMAL){
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.circle_normal_textview));
            setTextColor(true);
            return;
        }
        else if(state==CaculatorCyclePeriod.IN_OVULATION){
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.circle_ovulation_blur_textview));
            setTextColor(false);

        }
        else if(state==CaculatorCyclePeriod.IS_OVULATION){
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.circle_ovulation_textview));
            setTextColor(false);
            return;
        }
        else if(state==CaculatorCyclePeriod.IN_SECOND_NORMAL){
            mLayoutHome.setBackground(getResources().getDrawable(R.drawable.circle_normal_textview));
            setTextColor(true);

            return;
        }

    }

    private void setTextColor(boolean isNormal){
        if(!isNormal){
            mTextDate.setTextColor(getResources().getColor(R.color.colorWhite));
            mTextPeriod.setTextColor(getResources().getColor(R.color.colorWhite));
            mTextNumberDate.setTextColor(getResources().getColor(R.color.colorWhite));
            mTextDescription.setTextColor(getResources().getColor(R.color.colorWhite));
        }
        else {
            mTextDate.setTextColor(getResources().getColor(R.color.colorBlueText));
            mTextPeriod.setTextColor(getResources().getColor(R.color.colorBlueText));
            mTextNumberDate.setTextColor(getResources().getColor(R.color.colorBlueText));
            mTextDescription.setTextColor(getResources().getColor(R.color.colorBlueText));
        }
    }

    private void showPieChart(){
        pieChart=mView.findViewById(R.id.piechart);
        pieChart.getDescription().setEnabled(false);
        pieChart.setRotationEnabled(false);
        pieChart.setExtraOffsets(10,10,10,10);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(70f);
        //pieChart.setCenterText("dateCurrent");

        pieChart.setTransparentCircleRadius(80f);

        ArrayList<PieEntry> yValues=new ArrayList<>();
        yValues.add(new PieEntry(mCyclePeriod.getPeriod()));
        yValues.add(new PieEntry(CaculatorCyclePeriod.countFirstNormalDay(mCyclePeriod)));
        yValues.add(new PieEntry(CaculatorCyclePeriod.countFirstFertility(mCyclePeriod)));
        yValues.add(new PieEntry(1));
        yValues.add(new PieEntry(CaculatorCyclePeriod.countSecondFertility(mCyclePeriod)));
        yValues.add(new PieEntry(CaculatorCyclePeriod.countSecondNormal(mCyclePeriod)));

        pieChart.animateY(1000, Easing.EaseInCubic);

        PieDataSet dataSet=new PieDataSet(yValues,"DateCycle");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors=new ArrayList<>();

        colors.add(getResources().getColor(R.color.colorMainPink));
        colors.add(getResources().getColor(R.color.colorCircleNormal));
        colors.add(getResources().getColor(R.color.colorCircleOvulationBlur));
        colors.add(getResources().getColor(R.color.colorCircleOvulation));
        colors.add(getResources().getColor(R.color.colorCircleOvulationBlur));
        colors.add(getResources().getColor(R.color.colorCircleNormal));
        dataSet.setColors(colors);

        PieData data=new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.BLACK);
        Legend legend=pieChart.getLegend();
        legend.setEnabled(false);

        pieChart.setData(data);
        pieChart.invalidate();
    }

}
