package com.example.eva.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.eva.Caculator;
import com.example.eva.R;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChartCycleFragment extends Fragment {

    BarChart mBarChartPeriod;
    BarChart mBarChartCycle;
    ArrayList<BarEntry> mUserPeriods;
    ArrayList<BarEntry> mUserCycles;
    List<String> mUserBeginDates;
    ArrayList<String> mAxisLabelCycle ;
    ArrayList<String> mAxisLabelPeriod ;
    TextView mTextCycle, mTextPeriod, mTextChartDate, mTextStartPeriod, mTextStatusPeriod;
    View mView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("BacNT", "Chart Cycle");
        mView = inflater.inflate(R.layout.fragment_chart_cycle, container, false);
        mBarChartCycle = mView.findViewById(R.id.bar_chart_cycle);
        mBarChartPeriod = mView.findViewById(R.id.bar_chart_period);
        init();
        getData();
        showBarChart(mBarChartCycle, mUserCycles, true);
        showBarChart(mBarChartPeriod, mUserPeriods, false);
        return mView;
    }

    private void init() {
        mTextCycle = mView.findViewById(R.id.text_cycle_guess);
        mTextPeriod = mView.findViewById(R.id.text_period_guess);
        mTextChartDate = mView.findViewById(R.id.text_chart_date);
        mTextStatusPeriod = mView.findViewById(R.id.text_status_period);
        mTextStartPeriod = mView.findViewById(R.id.text_start_period);
    }

    private void showBarChart(BarChart barChart, ArrayList<BarEntry> list, boolean cycle) {

        barChart.getDescription().setEnabled(false);
        barChart.setMaxVisibleValueCount(4);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.setPinchZoom(false);
        if (cycle) {
            barChart.getAxisLeft().setAxisMinimum(0);
        } else {
            barChart.getAxisLeft().setAxisMinimum(0);
        }
        barChart.setDrawBarShadow(false);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setDrawGridLines(false);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().setEnabled(false);
        barChart.getAxisLeft().setEnabled(false);
        barChart.getXAxis().setDrawGridLines(false);
        // add a nice and smooth animation
        barChart.animateY(1000);
        barChart.getLegend().setEnabled(false);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.setTouchEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        //
        barChart.getXAxis().setEnabled(true);
        barChart.getXAxis().setAxisMaximum(7);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.getAxisLeft().setGranularity(0);
        barChart.getAxisLeft().setGranularityEnabled(true);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                if(cycle){
                    if((int) value<mAxisLabelCycle.size())
                        return mAxisLabelCycle.get((int) value);
                    else return "";
                }else {
                    if((int) value<mAxisLabelPeriod.size())
                        return mAxisLabelPeriod.get((int) value);
                    else return "";
                }
            }
        });

        xAxis.setCenterAxisLabels(false);
        barChart.invalidate();
        BarDataSet bardataset = new BarDataSet(list, "");
        bardataset.setValueFormatter(new ValueFormatter() {
            private DecimalFormat mFormat;

            @Override
            public String getFormattedValue(float value) {
                mFormat = new DecimalFormat("##");
                return mFormat.format(value);
            }
        });
        BarData data = new BarData(bardataset);
        if (cycle)
            bardataset.setColor(getContext().getResources().getColor(R.color.colorBlueOvulation));
        else
            bardataset.setColor(getContext().getResources().getColor(R.color.colorMainPink));
        barChart.setData(data);
    }

    private void getData() {
        DBManager dbManager = new DBManager(getContext());
        ArrayList<CyclePeriod> listCycle = (ArrayList<CyclePeriod>) dbManager.getListCycle();
        mUserCycles = new ArrayList<>();
        mUserPeriods = new ArrayList<>();
        mUserBeginDates = new ArrayList<>();
        mAxisLabelCycle=new ArrayList<>();
        mAxisLabelPeriod=new ArrayList<>();
        int lastUserPeriod = 0;
        int sumCycle = 0;
        int sumPeriod = 0;
        int i = 0;
        for (CyclePeriod cyclePeriod : listCycle) {
            if (cyclePeriod.getUserPeriod() != 0) {
                mUserPeriods.add(new BarEntry(i, cyclePeriod.getUserPeriod()));
                mUserBeginDates.add(cyclePeriod.getUserBeginDate());
                sumPeriod += cyclePeriod.getUserPeriod();
                lastUserPeriod = cyclePeriod.getUserPeriod();
                String date=Caculator.showDateTime(cyclePeriod.getUserBeginDate()).substring(0,5);
                mAxisLabelPeriod.add(date);
            } else break;
            if (cyclePeriod.getUserCycle() != 0) {
                mUserCycles.add(new BarEntry(i, cyclePeriod.getUserCycle()));
                sumCycle += cyclePeriod.getUserCycle();
                String date=Caculator.showDateTime(cyclePeriod.getUserBeginDate()).substring(0,5);
                mAxisLabelCycle.add(date);
            }
            i++;
        }

        int periodLength = (int) Math.round((double) sumPeriod / (mUserPeriods.size()));
        int cycleLength = (int) Math.round((double) sumCycle / (mUserCycles.size()));
        mTextCycle.setText(cycleLength + " " + getContext().getResources().getString(R.string.days));
        mTextPeriod.setText(periodLength + " " + getContext().getResources().getString(R.string.days));

        CyclePeriod cyclePeriod = listCycle.get(i - 1);
        mTextChartDate.setText(getContext().getResources().getString(R.string.recent_period) + " " + showDateTime(cyclePeriod.getUserBeginDate()) + " - " + showDateTime(cyclePeriod.getUserEndDate()));

        long number = Caculator.countDay(Date.valueOf(cyclePeriod.getUserBeginDate()), Date.valueOf(cyclePeriod.getBeginDate()));
        if (number == 0) {
            mTextStartPeriod.setText(getContext().getResources().getString(R.string.chart_stable));
        } else if (number == 1) {
            mTextStartPeriod.setText(getContext().getResources().getString(R.string.chart_late) + " " + number + " " +
                    getContext().getResources().getString(R.string.day));
        } else if (number == -1) {
            mTextStartPeriod.setText(getContext().getResources().getString(R.string.chart_early) + " " + -number + " " +
                    getContext().getResources().getString(R.string.day));
        } else if (number > 0) {
            mTextStartPeriod.setText(getContext().getResources().getString(R.string.chart_late) + " " + number + " " +
                    getContext().getResources().getString(R.string.days));
        } else if (number < 0) {
            mTextStartPeriod.setText(getContext().getResources().getString(R.string.chart_early) + " " + -number + " " +
                    getContext().getResources().getString(R.string.days));
        }

        int status = lastUserPeriod - periodLength;
        if (status > 1)
            mTextStatusPeriod.setText(getContext().getResources().getString(R.string.ending_late));
        else if (status < -1)
            mTextStatusPeriod.setText(getContext().getResources().getString(R.string.ending_early));
        else
            mTextStatusPeriod.setText(getContext().getResources().getString(R.string.chart_stable));
    }

    private String showDateTime(String day) {
        Date date = Date.valueOf(day);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM");
        return sdf.format(date);
    }


}
