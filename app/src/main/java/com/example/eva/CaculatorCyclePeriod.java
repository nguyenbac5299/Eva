package com.example.eva;

import android.util.Log;

import com.example.eva.model.CyclePeriod;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CaculatorCyclePeriod {
   public static final int EQUAL = 1;
    public static final int BEFORE = 2;
    public static final int APTER = 3;

    public static final int INPERIOD = 4;
    public static final int INFIRSTNORMAL = 5;
    public static final int INOVULATION = 6;
    public static final int ISOVULATION = 7;
    public static final int INSECONDNORMAL = 8;

    static CyclePeriod mCyclePeriod;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public CaculatorCyclePeriod(CyclePeriod cyclePeriod) {
        this.mCyclePeriod = cyclePeriod;
    }

    public static String caculatorEndDate() {
        return caculatorDate(mCyclePeriod.getBeginDate(), mCyclePeriod.getPeriod() - 1);
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");

        Log.d("MainActivity", "" + simpleDateFormat.format(calendar.getTime()));
        return simpleDateFormat.format(calendar.getTime());
    }

    public static int compareDate(Date date1, Date date2) {
        if (date1.equals(date2))
            return EQUAL;
        else if (date1.before(date2))
            return BEFORE;
        else
            return APTER;
    }

    public static int getStage() {
        Date currentDate = Date.valueOf(getCurrentDate());
        Date beginDate = Date.valueOf(mCyclePeriod.getBeginDate());
        Date endDate = Date.valueOf(mCyclePeriod.getEndDate());
        Date ovulaytionDate = Date.valueOf(mCyclePeriod.getOvulationDate());
        Date beginOvulationDate = Date.valueOf(mCyclePeriod.getBeginOvulation());
        Date endOvulationDate = Date.valueOf(mCyclePeriod.getEndOvulation());
        if (inPeriod(currentDate, beginDate, endDate))
            return INPERIOD;
        else if (isOvulation(currentDate, ovulaytionDate))
            return ISOVULATION;
        else if (inOvulation(currentDate, beginOvulationDate, endOvulationDate))
            return INOVULATION;
        else if (inFirstNormal(currentDate, endDate, beginOvulationDate))
            return INFIRSTNORMAL;
        else
            return INSECONDNORMAL;
    }

    public static boolean inPeriod(Date currentDate, Date beginDate, Date endDate) {
        int begin = compareDate(currentDate, beginDate);
        int end = compareDate(currentDate, endDate);
        if (begin == EQUAL || end == EQUAL || ((begin == APTER) && (end == BEFORE)))
            return true;
        else return false;
    }

    public static long countPeriodDay(){
        Date currentDate = Date.valueOf(getCurrentDate());
        Date beginDate = Date.valueOf(mCyclePeriod.getBeginDate());
        return countDay(currentDate, beginDate);
    }
//    public static long countOvulation(){
//
//    }

    private static long countDay(Date date1, Date date2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTime(date1);
        calendar2.setTime(date2);
        long countDay = (calendar1.getTime().getTime() - calendar2.getTime().getTime()) / (24 * 3600 * 1000);
        return countDay;
    }


    public static boolean inFirstNormal(Date currentDate, Date endDate, Date beginOvulation) {
        int begin = compareDate(currentDate, endDate);
        int end = compareDate(currentDate, beginOvulation);
        if (begin == APTER && end == BEFORE)
            return true;
        else return false;

    }

    public static boolean inOvulation(Date curentDate, Date beginOvulationDate, Date endOvulationDate) {
        int begin = compareDate(curentDate, beginOvulationDate);
        int end = compareDate(curentDate, endOvulationDate);
        if (begin == EQUAL || end == EQUAL || (begin == APTER && end == BEFORE))
            return true;
        else return false;
    }

    public static boolean isOvulation(Date currentDate, Date ovulationDate) {
        if (compareDate(currentDate, ovulationDate) == EQUAL)
            return true;
        else
            return false;
    }


    public static String caculatorOvulationDate() {
        return caculatorDate(mCyclePeriod.getBeginDate(), mCyclePeriod.getCycle() - 14);
    }

    public static String caculatorBeginOvulation() {
        return caculatorDate(mCyclePeriod.getOvulationDate(), -4);
    }

    public static String caculatorEndOvulation() {
        return caculatorDate(mCyclePeriod.getOvulationDate(), 4);
    }

    private static String caculatorDate(String date, int addDay) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        return sdf.format(calendar.getTime());
    }

    public static CyclePeriod caculatorCyclePeriod() {
        CyclePeriod cyclePeriod = mCyclePeriod;
        cyclePeriod.setEndDate(caculatorEndDate());
        cyclePeriod.setOvulationDate(caculatorOvulationDate());
        cyclePeriod.setBeginOvulation(caculatorBeginOvulation());
        cyclePeriod.setEndOvulation(caculatorEndOvulation());
        cyclePeriod.setMonth(caculatorMonth());
        return cyclePeriod;
    }

    public static String getNextBeginDate() {
        return caculatorDate(mCyclePeriod.getBeginDate(), mCyclePeriod.getCycle());
    }
    public static int caculatorMonth() {
        int month;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        try {
            calendar.setTime(sdf.parse(mCyclePeriod.getBeginDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        month=Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
        return month;
    }

}
