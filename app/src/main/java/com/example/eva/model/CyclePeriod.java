package com.example.eva.model;

import java.io.Serializable;

public class CyclePeriod implements Serializable {
    private int cycle=28;
    private int period=5;
    private DateCyclePeriod date;
    private Expression expression;

    public Expression getExpression() {
        return expression;
    }

    public void setDate(DateCyclePeriod date) {
        this.date = date;
    }

    public DateCyclePeriod getDate() {
        return date;
    }

    public void setExpression(Expression expression) {
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

}
