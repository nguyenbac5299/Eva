package com.example.eva;

import android.util.Log;

import com.example.eva.model.CyclePeriod;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Caculator {
    public static final int EQUAL = 1;
    public static final int BEFORE = 2;
    public static final int APTER = 3;

    public static final int IN_PERIOD = 4;
    public static final int IN_FIRST_NORMAL = 5;
    public static final int IN_OVULATION = 6;
    public static final int IS_OVULATION = 7;
    public static final int IN_SECOND_NORMAL = 8;
    public static final int IN_ANOTHER = 9;
    public static final int IN_LATE = 10;

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public static String getEndDate(CyclePeriod cyclePeriod) {
        if (cyclePeriod.getUserBeginDate() != null) {
            return getDate(cyclePeriod.getUserBeginDate(), cyclePeriod.getUserPeriod() - 1);
        }
        return getDate(cyclePeriod.getBeginDate(), cyclePeriod.getPeriod() - 1);
    }

    public static String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        return sdf.format(calendar.getTime());
    }

    public static String getCurrentDateHome() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE dd-MM-yyyy");
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getDate(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date.valueOf(date));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd/MM");
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
        Date ovulationDate = Date.valueOf(cyclePeriod.getOvulationDate());
        Date beginFertilityDate = Date.valueOf(cyclePeriod.getBeginFertility());
        Date endFertilityDate = Date.valueOf(cyclePeriod.getEndFertility());
        Date nextBeginCycleDate = Date.valueOf(cyclePeriod.getNextBeginCycle());

        if (cyclePeriod.getUserBeginDate() == null && inLate(currentDate, beginDate))
            return IN_LATE;
        else if (cyclePeriod.getUserBeginDate() != null && cyclePeriod.getUserEndDate() != null) {
            Date userBeginDate = Date.valueOf(cyclePeriod.getUserBeginDate());
            Date userEndDate = Date.valueOf(cyclePeriod.getUserEndDate());
            if (inPeriod(currentDate, userBeginDate, userEndDate))
                return IN_PERIOD;
            else if (inFirstNormal(currentDate, userEndDate, beginFertilityDate))
                return IN_FIRST_NORMAL;
            else if (isOvulation(currentDate, ovulationDate))
                return IS_OVULATION;
            else if (inFertility(currentDate, beginFertilityDate, endFertilityDate))
                return IN_OVULATION;
            else if (inSecondNormal(currentDate, endFertilityDate, nextBeginCycleDate))
                return IN_SECOND_NORMAL;
            else return IN_ANOTHER;
        } else if (isOvulation(currentDate, ovulationDate))
            return IS_OVULATION;
        else if (inFertility(currentDate, beginFertilityDate, endFertilityDate))
            return IN_OVULATION;
        else if (inSecondNormal(currentDate, endFertilityDate, nextBeginCycleDate))
            return IN_SECOND_NORMAL;
        else return IN_ANOTHER;
    }


    public static boolean inLate(Date currentDate, Date beginDate) {
        int begin = compareDate(currentDate, beginDate);
        if (begin == APTER)
            return true;
        return false;
    }

    public static boolean inSecondNormal(Date currentDate, Date endFertilityDate, Date nextBeginCycleDate) {
        int begin = compareDate(currentDate, endFertilityDate);
        int end = compareDate(currentDate, nextBeginCycleDate);
        if (begin == APTER && end == BEFORE)
            return true;
        return false;
    }

    //Phải sửa
    public static boolean inPeriod(Date currentDate, Date beginDate, Date endDate) {
        int begin = compareDate(currentDate, beginDate);
        int end = compareDate(currentDate, endDate);
        if (begin == EQUAL || end == EQUAL || ((begin == APTER) && (end == BEFORE)))
            return true;
        else return false;
    }

    public static boolean inCycle(CyclePeriod cyclePeriod) {
        if (cyclePeriod.getUserBeginDate() != null) {
            Date beginDate = Date.valueOf(cyclePeriod.getUserBeginDate());
            Date nextBeginDate = Date.valueOf(cyclePeriod.getNextBeginCycle());
            Date currentDate = Date.valueOf(getCurrentDate());
            int begin = compareDate(currentDate, beginDate);
            int end = compareDate(currentDate, nextBeginDate);
            if (begin == EQUAL || (begin == APTER && end == BEFORE))
                return true;
            else
                return false;
        }
        return false;
    }

    public static long countFirstNormalDay(CyclePeriod cyclePeriod) {
        Date endDate;
        if (cyclePeriod.getUserBeginDate() == null)
            endDate = Date.valueOf(cyclePeriod.getEndDate());
        else
            endDate = Date.valueOf(cyclePeriod.getUserEndDate());
        Date beginFertility = Date.valueOf(getDate(cyclePeriod.getBeginFertility(), -1));
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
        return (countDay(nextCycle, endFertility) - 1);
    }

    public static long countDay(Date date1, Date date2) {
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

    public static boolean isConflictDate(String beginDay, String endDay, String checkDay) {
        Date beginDate = Date.valueOf(beginDay);
        Date endDate = Date.valueOf(endDay);
        Date checkDate = Date.valueOf(checkDay);
        int begin = compareDate(checkDate, beginDate);
        int end = compareDate(checkDate, endDate);
        if (begin == EQUAL || end == EQUAL || (begin == APTER && end == BEFORE))
            return true;
        return false;
    }

    public static boolean checkEndUserDate(String beginDay, String endDay) {
        Date beginDate = Date.valueOf(beginDay);
        Date endDate = Date.valueOf(endDay);
        if (compareDate(beginDate, endDate) == BEFORE)
            return true;
        return false;
    }

    public static String getBeginNextCycle(CyclePeriod cyclePeriod) {
        if (cyclePeriod.getUserBeginDate() == null || cyclePeriod.getUserBeginDate().equals(""))
            return getDate(cyclePeriod.getBeginDate(), cyclePeriod.getCycle());
        else
            return getDate(cyclePeriod.getUserBeginDate(), cyclePeriod.getCycle());
    }

    public static String getOvulationDate(CyclePeriod cyclePeriod) {
        if (cyclePeriod.getUserBeginDate() != null && cyclePeriod.getUserCycle() != 0)
            return getDate(cyclePeriod.getUserBeginDate(), cyclePeriod.getUserCycle() - cyclePeriod.getUserCycle() / 2 +1);
        else if (cyclePeriod.getUserBeginDate() != null)
            return getDate(cyclePeriod.getUserBeginDate(), cyclePeriod.getCycle() - cyclePeriod.getCycle() / 2 +1);
        return getDate(cyclePeriod.getBeginDate(), cyclePeriod.getCycle() - cyclePeriod.getCycle() / 2 +1);
    }

    public static String getBeginFertility(CyclePeriod cyclePeriod) {
        return getDate(cyclePeriod.getOvulationDate(), -5);
    }

    public static String getEndFertility(CyclePeriod cyclePeriod) {
        return getDate(cyclePeriod.getOvulationDate(), 5);
    }

    public static String getDate(String date, int addDay) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_MONTH, addDay);
        return sdf.format(calendar.getTime());
    }

    public static int getMonth(CyclePeriod cyclePeriod) {
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
    //caculator for CalendarFragment
    public static List<String> getListDay(String beginDay, String endDay) {
        List<String> listDay = new ArrayList<>();
        listDay.add(beginDay);
        while (!beginDay.equals(endDay)) {
            beginDay = getDate(beginDay, 1);
            listDay.add(beginDay);
        }
        return listDay;
    }

    //home
    public static String getStartFirstNormal(CyclePeriod cyclePeriod) {
        if (cyclePeriod.getUserEndDate() != null)
            return getDate(cyclePeriod.getUserEndDate(), 1);
        else return getDate(cyclePeriod.getEndDate(), 1);
    }

    public static String getEndFirstNormal(CyclePeriod cyclePeriod) {
        return getDate(cyclePeriod.getBeginFertility(), -1);
    }

    public static String getStartSecondNormal(CyclePeriod cyclePeriod) {
        return getDate(cyclePeriod.getEndFertility(), 1);
    }

    public static String getEndSecondNormal(CyclePeriod cyclePeriod) {
        return getDate(cyclePeriod.getNextBeginCycle(), -1);
    }

    public static String showDateTime(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date1 = Date.valueOf(date);
        return sdf.format(date1);

    }
    //Phải sửa
    public static CyclePeriod getCyclePeriod(CyclePeriod cyclePeriod) {
        Log.d("BacNT", "cycle: " + cyclePeriod.getCycle() + "|" + cyclePeriod.getPeriod());
        cyclePeriod.setEndDate(getEndDate(cyclePeriod));
        cyclePeriod.setOvulationDate(getOvulationDate(cyclePeriod));
        cyclePeriod.setBeginFertility(getBeginFertility(cyclePeriod));
        cyclePeriod.setEndFertility(getEndFertility(cyclePeriod));
        cyclePeriod.setMonth(getMonth(cyclePeriod));
        if (cyclePeriod.getNextBeginCycle() == null)
            cyclePeriod.setNextBeginCycle(getBeginNextCycle(cyclePeriod));
        return cyclePeriod;
    }

}
