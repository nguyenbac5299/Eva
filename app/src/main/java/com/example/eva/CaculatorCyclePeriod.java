package com.example.eva;

import android.util.Log;

import com.example.eva.model.CyclePeriod;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class
CaculatorCyclePeriod {
    public static final int EQUAL = 1;
    public static final int BEFORE = 2;
    public static final int APTER = 3;

    public static final int IN_PERIOD = 4;
    public static final int IN_FIRST_NORMAL = 5;
    public static final int IN_OVULATION = 6;
    public static final int IS_OVULATION = 7;
    public static final int IN_SECOND_NORMAL = 8;
    public static final int IN_NEXT_CYCLE = 9;

    //static CyclePeriod cyclePeriod;
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//    public CaculatorCyclePeriod(CyclePeriod cyclePeriod) {
//        this.cyclePeriod = cyclePeriod;
//    }

    public static String caculatorEndDate(CyclePeriod cyclePeriod) {
        return caculatorDate(cyclePeriod.getBeginDate(), cyclePeriod.getPeriod() - 1);
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentDateHome() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
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

    public static int getStage(CyclePeriod cyclePeriod) {
        Date currentDate = Date.valueOf(getCurrentDate());
        Date beginDate = Date.valueOf(cyclePeriod.getBeginDate());
        Date endDate = Date.valueOf(cyclePeriod.getEndDate());
        Date ovulationDate = Date.valueOf(cyclePeriod.getOvulationDate());
        Date beginFertilityDate = Date.valueOf(cyclePeriod.getBeginFertility());
        Date endFertilityDate = Date.valueOf(cyclePeriod.getEndFertility());
        Date nextBeginCycleDate = Date.valueOf(cyclePeriod.getNextBeginCycle());
        if (inPeriod(currentDate, beginDate, endDate))
            return IN_PERIOD;
        else if (isOvulation(currentDate, ovulationDate))
            return IS_OVULATION;
        else if (inFertility(currentDate, beginFertilityDate, endFertilityDate))
            return IN_OVULATION;
        else if (inFirstNormal(currentDate, endDate, beginFertilityDate))
            return IN_FIRST_NORMAL;
        else if (inSecondNormal(currentDate, endFertilityDate, nextBeginCycleDate))
            return IN_SECOND_NORMAL;
        else return IN_NEXT_CYCLE;
    }

    public static boolean inSecondNormal(Date currentDate, Date endFertilityDate, Date nextBeginCycleDate) {
        int begin = compareDate(currentDate, endFertilityDate);
        int end = compareDate(currentDate, nextBeginCycleDate);
        if (begin == APTER && end == BEFORE)
            return true;
        return false;
    }

    public static boolean inPeriod(Date currentDate, Date beginDate, Date endDate) {
        int begin = compareDate(currentDate, beginDate);
        int end = compareDate(currentDate, endDate);
        if (begin == EQUAL || end == EQUAL || ((begin == APTER) && (end == BEFORE)))
            return true;
        else return false;
    }

    public static boolean inCycle(CyclePeriod cyclePeriod) {
        Date beginDate = Date.valueOf(cyclePeriod.getBeginDate());
        Date beginNextDate = Date.valueOf(cyclePeriod.getNextBeginCycle());
        Date currentDate = Date.valueOf(getCurrentDate());
        int begin = compareDate(currentDate, beginDate);
        int end = compareDate(currentDate, beginNextDate);
        if (begin == EQUAL || (begin == APTER && end == BEFORE))
            return true;
        else
            return false;

    }

    public static long countPeriodDay(CyclePeriod cyclePeriod) {
        Date currentDate = Date.valueOf(getCurrentDate());
        Date beginDate = Date.valueOf(cyclePeriod.getBeginDate());
        return countDay(currentDate, beginDate);
    }


    public static long countFirstNormalDay(CyclePeriod cyclePeriod) {
        Date endDate = Date.valueOf(cyclePeriod.getEndDate());
        Date beginFertility = Date.valueOf(caculatorDate(cyclePeriod.getBeginFertility(), -1));
        return countDay(beginFertility, endDate);
    }

    public static long countFirstFertility(CyclePeriod cyclePeriod) {
        Date beginFertility = Date.valueOf(cyclePeriod.getBeginFertility());
        Date ovulationDate = Date.valueOf(cyclePeriod.getOvulationDate());
        return countDay(ovulationDate, beginFertility);
    }

    public static long countSecondFertility(CyclePeriod cyclePeriod) {
        Date ovulationDate = Date.valueOf(cyclePeriod.getOvulationDate());
        Date endFertility = Date.valueOf(cyclePeriod.getEndFertility());
        return countDay(endFertility, ovulationDate);
    }

    public static long countSecondNormal(CyclePeriod cyclePeriod) {
        Date nextCycle = Date.valueOf(cyclePeriod.getNextBeginCycle());
        Date endFertility = Date.valueOf(cyclePeriod.getEndFertility());
        return countDay(nextCycle, endFertility);
    }


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

    public static boolean inFertility(Date curentDate, Date beginOvulationDate, Date endOvulationDate) {
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

    public static String caculatorBeginNextCycle(CyclePeriod cyclePeriod) {
        if (cyclePeriod.getUserBeginDate() == null || cyclePeriod.getUserBeginDate().equals(""))
            return caculatorDate(cyclePeriod.getBeginDate(), cyclePeriod.getCycle());
        else
            return caculatorDate(cyclePeriod.getUserBeginDate(), cyclePeriod.getCycle());
    }

    public static String caculatorOvulationDate(CyclePeriod cyclePeriod) {
        return caculatorDate(cyclePeriod.getBeginDate(), cyclePeriod.getCycle() - 14);
    }

    public static String caculatorBeginFertility(CyclePeriod cyclePeriod) {
        return caculatorDate(cyclePeriod.getOvulationDate(), -5);
    }

    public static String caculatorEndFertility(CyclePeriod cyclePeriod) {
        return caculatorDate(cyclePeriod.getOvulationDate(), 5);
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

    public static CyclePeriod caculatorCyclePeriod(CyclePeriod cyclePeriod) {
        cyclePeriod.setEndDate(caculatorEndDate(cyclePeriod));
        cyclePeriod.setOvulationDate(caculatorOvulationDate(cyclePeriod));
        cyclePeriod.setBeginFertility(caculatorBeginFertility(cyclePeriod));
        cyclePeriod.setEndFertility(caculatorEndFertility(cyclePeriod));
        cyclePeriod.setMonth(caculatorMonth(cyclePeriod));
        cyclePeriod.setNextBeginCycle(caculatorBeginNextCycle(cyclePeriod));
        return cyclePeriod;
    }

    public static int getCurrentMonth() {
        int month;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        try {
            calendar.setTime(sdf.parse(getCurrentDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        month = Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
        return month;
    }


    public static int caculatorMonth(CyclePeriod cyclePeriod) {
        int month;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM");
        try {
            calendar.setTime(sdf.parse(cyclePeriod.getBeginDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        month = Integer.parseInt(simpleDateFormat.format(calendar.getTime()));
        return month;
    }

}
