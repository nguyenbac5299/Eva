package com.example.eva.model;

import java.io.Serializable;
import java.util.Calendar;

public class CyclePeriod implements Serializable {
    private int cycle=28;
    private int period=5;
    private int month;
    private String beginDate;
    private String endDate;
    private String beginOvulation;
    private String endOvulation;
    private String ovulationDate;
    private PMS expression;

    public PMS getExpression() {
        return expression;
    }

    public void setExpression(PMS expression) {
        this.expression = expression;
    }

    public int getCycle() {
        return cycle;
    }

    public int getPeriod() {
        return period;
    }

    public void setCycle(int cycle) {
        this.cycle = cycle;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public CyclePeriod() {
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getBeginOvulation() {
        return beginOvulation;
    }

    public void setBeginOvulation(String beginOvulation) {
        this.beginOvulation = beginOvulation;
    }

    public void setEndOvulation(String endOvulation) {
        this.endOvulation = endOvulation;
    }

    public String getEndOvulation() {
        return endOvulation;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getOvulationDate() {
        return ovulationDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setOvulationDate(String ovulationDate) {
        this.ovulationDate = ovulationDate;
    }
}
