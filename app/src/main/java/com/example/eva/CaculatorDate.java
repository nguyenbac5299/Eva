package com.example.eva;

import android.util.Log;

import com.example.eva.model.CyclePeriod;
import com.example.eva.model.DateCyclePeriod;

import java.util.Date;

public class CaculatorDate {
    CyclePeriod cyclePeriod;
    DateCyclePeriod.Date beginDate;
    int period;
    int cycle;

    public CaculatorDate(CyclePeriod cyclePeriod) {
        this.cyclePeriod = cyclePeriod;
        this.beginDate=cyclePeriod.getDate().getBeginDate();
        this.period=cyclePeriod.getPeriod();
        this.cycle=cyclePeriod.getCycle();
    }
    public DateCyclePeriod countEndDay() {
        int beginDay = beginDate.getDay();
        int beginMonth = beginDate.getMonth();
        int beginYear = beginDate.getYear();
        DateCyclePeriod.Date endDate;
        DateCyclePeriod.Date ovulationday;


        if (beginMonth == 2) {
            if (isLeap(beginYear)) {
                endDate = caculatorDate( 29, period);
                ovulationday=caculatorDate(29, (cycle-14));

            } else {
                endDate = caculatorDate(28,period);
                ovulationday=caculatorDate(28,(cycle-14));
            }
        } else if (beginMonth == 1 || beginMonth == 3 || beginMonth == 5 || beginMonth == 7
                    || beginMonth == 8 || beginMonth == 10 || beginMonth == 12) {
            endDate = caculatorDate( 31, period);
            ovulationday=caculatorDate(31,(cycle-14));
        } else {
            endDate = caculatorDate(30,period);
            ovulationday=caculatorDate(30,(cycle-14));
        }
        DateCyclePeriod date=new DateCyclePeriod();
        date.setEndDate(endDate);
        date.setOvulationDay(ovulationday);
        return date;
    }


    private DateCyclePeriod.Date caculatorDate(int dayPerMonth, int addmumber) {
        int newDay = beginDate.getDay() + addmumber -1;
        Log.d("BacNT","newDay: "+newDay);
        int newMonth = beginDate.getMonth();
        int newYear = beginDate.getYear();
        if (newDay > dayPerMonth) {
            newDay = newDay % dayPerMonth;
            Log.d("BacNT","newDay: "+newDay);
            newMonth = newMonth + ((beginDate.getDay()+addmumber-1) / dayPerMonth);
            Log.d("BacNT","newM: "+newMonth);
            if (newMonth > 12) {
                newMonth = newMonth % 12;
                newYear++;
            }
        }

        DateCyclePeriod date=new DateCyclePeriod();
        return date.new Date(newDay, newMonth, newYear);
    }
    private DateCyclePeriod.Date caculatorNextBeginDate(int dayPerMonth){
        int beginNextDay= beginDate.getDay()+ cycle;
        int beginNextMonth=beginDate.getMonth();
        int beginNextYear=beginDate.getYear();
        if (beginNextDay > dayPerMonth) {
            beginNextMonth = beginNextMonth % dayPerMonth;
            beginNextMonth = beginNextMonth + (beginNextDay / dayPerMonth);
            if (beginNextMonth > 12) {
                beginNextMonth = beginNextMonth % 12;
                beginNextYear++;
            }
        }
        return new DateCyclePeriod().new Date(beginNextDay,beginNextMonth,beginNextYear);
    }

    public boolean isLeap(int year) {
        boolean isLeap = false;
        if (year % 4 == 0)//chia hết cho 4 là năm nhuận
        {
            if (year % 100 == 0)
            //nếu vừa chia hết cho 4 mà vừa chia hết cho 100 thì không phải năm nhuận
            {
                if (year % 400 == 0)//chia hết cho 400 là năm nhuận
                    isLeap = true;
                else
                    isLeap = false;//không chia hết cho 400 thì không phải năm nhuận
            } else//chia hết cho 4 nhưng không chia hết cho 100 là năm nhuận
                isLeap = true;
        } else {
            isLeap = false;
        }
        return isLeap;
    }


}
