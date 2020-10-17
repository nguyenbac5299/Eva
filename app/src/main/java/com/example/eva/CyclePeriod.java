package com.example.eva;

import java.io.Serializable;

public class CyclePeriod implements Serializable {
    private int cycle=28;
    private int period=5;
    private int beginDay;
    private int endDay;
    private int beginMonth;
    private int beginYear;
    private int endMonth;
    private int endYear;


    public CyclePeriod(int cycle, int period, int beginDay, int endDay) {
        this.cycle = cycle;
        this.period = period;
        this.beginDay = beginDay;
        this.endDay = endDay;


    }

    public int getBeginMonth() {
        return beginMonth;
    }

    public int getBeginYear() {
        return beginYear;
    }

    public int getEndMonth() {
        return endMonth;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setBeginMonth(int beginMonth) {
        this.beginMonth = beginMonth;
    }

    public void setBeginYear(int beginYear) {
        this.beginYear = beginYear;
    }

    public void setEndMonth(int endMonth) {
        this.endMonth = endMonth;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public int getCycle() {
        return cycle;
    }

    public int getPeriod() {
        return period;
    }

    public int getBeginDay() {
        return beginDay;
    }

    public int getEndDay() {
        return endDay;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setBeginDay(int beginDay) {
        this.beginDay = beginDay;
    }

    public void setEndDay(int endDay) {
        this.endDay = endDay;
    }

    public CyclePeriod() {
    }
}
