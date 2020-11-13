package com.example.eva.model;

import java.io.Serializable;

public class CyclePeriod implements Serializable {
    private int id;
    private int cycle=28;
    private int period=5;
    private int month;
    private String beginDate;
    private String endDate;
    private String userBeginDate;
    private String userEndDate;
    private int userCycle;
    private int userPeriod;


    public void setUserCycle(int userCycle) {
        this.userCycle = userCycle;
    }

    public void setUserPeriod(int userPeriod) {
        this.userPeriod = userPeriod;
    }

    public int getUserCycle() {
        return userCycle;
    }

    public int getUserPeriod() {
        return userPeriod;
    }

    public String getUserBeginDate() {
        return userBeginDate;
    }

    public String getUserEndDate() {
        return userEndDate;
    }

    public void setUserBeginDate(String userBeginDate) {
        this.userBeginDate = userBeginDate;
    }

    public void setUserEndDate(String userEndDate) {
        this.userEndDate = userEndDate;
    }

    private String beginFertility;
    private String endFertility;
    private String ovulationDate;
    private PMS expression;
    private String nextBeginCycle;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNextBeginCycle(String nextBeginCycle) {
        this.nextBeginCycle = nextBeginCycle;
    }

    public String getNextBeginCycle() {
        return nextBeginCycle;
    }

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

    public CyclePeriod(int cycle, int period, String beginDate) {
        this.cycle = cycle;
        this.period = period;
        this.beginDate = beginDate;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getBeginFertility() {
        return beginFertility;
    }

    public void setBeginFertility(String beginFertiity) {
        this.beginFertility = beginFertiity;
    }

    public void setEndFertility(String endFertility) {
        this.endFertility = endFertility;
    }

    public String getEndFertility() {
        return endFertility;
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
