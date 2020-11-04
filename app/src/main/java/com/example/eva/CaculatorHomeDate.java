package com.example.eva;

import com.example.eva.model.CyclePeriod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaculatorHomeDate {
    CyclePeriod cyclePeriod;


    public CaculatorHomeDate(CyclePeriod cyclePeriod) {
        this.cyclePeriod = cyclePeriod;
    }
    public static String geCurrentDate(){
        Calendar calendar=Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("EEE dd/MM");
        return df.format(calendar.getTime());
    }


}
