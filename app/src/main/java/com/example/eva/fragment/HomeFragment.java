package com.example.eva.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.R;
import com.example.eva.model.CyclePeriod;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    PieChart pieChart;
    CyclePeriod mCyclePeriod;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mCyclePeriod= (CyclePeriod) getArguments().getSerializable("CYCLEPERIOD");

        String dateCurrent="";

        pieChart=view.findViewById(R.id.piechart);
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
        yValues.add(new PieEntry(5));
        yValues.add(new PieEntry(7));
        yValues.add(new PieEntry(5));
        yValues.add(new PieEntry(1));
        yValues.add(new PieEntry(5));
        yValues.add(new PieEntry(9));

        pieChart.animateY(1000, Easing.EaseInCubic);

        PieDataSet dataSet=new PieDataSet(yValues,"DateCycle");
        dataSet.setSliceSpace(5f);
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors=new ArrayList<>();

        colors.add(getResources().getColor(R.color.colorPinkButton));
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
        return view;
    }
}
