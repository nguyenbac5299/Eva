package com.example.eva.model;

import java.io.Serializable;

public class DateCyclePeriod implements Serializable {
    private Date endDate;
    private Date beginDate;
    private Date ovulationDay;

    public Date getEndDate() {
        return endDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public Date getOvulationDay() {
        return ovulationDay;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setOvulationDay(Date ovulationDay) {
        this.ovulationDay = ovulationDay;
    }

    public class Date implements Serializable {
        private int Day;
        private int Month;
        private int Year;

        public int getDay() {
            return Day;
        }

        public int getMonth() {
            return Month;
        }

        public int getYear() {
            return Year;
        }

        public void setDay(int day) {
            Day = day;
        }

        public void setMonth(int month) {
            Month = month;
        }

        public void setYear(int year) {
            Year = year;
        }

        public Date() {
        }

        public Date(int day, int month, int year) {
            Day = day;
            Month = month;
            Year = year;
        }
    }

}
