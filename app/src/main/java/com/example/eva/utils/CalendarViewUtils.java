package com.example.eva.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eva.Caculator;
import com.example.eva.R;
import com.example.eva.adapter.PMSAdapterCalendar;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.example.eva.model.ItemPMS;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class CalendarViewUtils {
    public static List<ItemPMS> getListMens(Context context, boolean isLittle,
                                            boolean isMedium, boolean isMuch) {
        List<ItemPMS> listMens = new ArrayList<>();
        listMens.add(new ItemPMS(R.drawable.ic_mf_s_l, isLittle, context.getResources().getString(R.string.menstruation_light)));
        listMens.add(new ItemPMS(R.drawable.ic_mf_m_l, isMedium, context.getResources().getString(R.string.menstruation_medium)));
        listMens.add(new ItemPMS(R.drawable.ic_mf_l_l, isMuch, context.getResources().getString(R.string.menstruation_heavy)));
        return listMens;
    }

    public static List<ItemPMS> getListMood(Context context, boolean isNormal, boolean isHappy,
                                            boolean isSad, boolean isAngry, boolean isWorry,
                                            boolean isTired, boolean isFear) {
        List<ItemPMS> listMood = new ArrayList<>();
        listMood.add(new ItemPMS(R.drawable.ic_mood_normal, isNormal, context.getResources().getString(R.string.mood_normal)));
        listMood.add(new ItemPMS(R.drawable.ic_mood_happy, isHappy, context.getResources().getString(R.string.mood_happy)));
        listMood.add(new ItemPMS(R.drawable.ic_mood_sad, isSad, context.getResources().getString(R.string.mood_sad)));
        listMood.add(new ItemPMS(R.drawable.ic_mood_angry, isAngry, context.getResources().getString(R.string.mood_angry)));
        listMood.add(new ItemPMS(R.drawable.ic_mood_anxious, isWorry, context.getResources().getString(R.string.mood_worry)));
        listMood.add(new ItemPMS(R.drawable.ic_mood_tired, isTired, context.getResources().getString(R.string.mood_tired)));
        listMood.add(new ItemPMS(R.drawable.ic_mood_panicky, isFear, context.getResources().getString(R.string.mood_fear)));
        return listMood;
    }

    public static List<ItemPMS> getListSporty(Context context, boolean isNoSporting, boolean isRun,
                                              boolean isCycling, boolean isGym, boolean isSwimming,
                                              boolean isAerobics) {
        List<ItemPMS> listSporty = new ArrayList<>();
        listSporty.add(new ItemPMS(R.drawable.ic_not_exercise, isNoSporting, context.getResources().getString(R.string.no_sporty)));
        listSporty.add(new ItemPMS(R.drawable.ic_running, isRun, context.getResources().getString(R.string.running)));
        listSporty.add(new ItemPMS(R.drawable.ic_cycling, isCycling, context.getResources().getString(R.string.cycling)));
        listSporty.add(new ItemPMS(R.drawable.ic_gym, isGym, context.getResources().getString(R.string.gym)));
        listSporty.add(new ItemPMS(R.drawable.ic_swimming, isSwimming, context.getResources().getString(R.string.swimming)));
        listSporty.add(new ItemPMS(R.drawable.ic_aerobics, isAerobics, context.getResources().getString(R.string.earobics)));
        return listSporty;
    }

    public static List<ItemPMS> getListSex(Context context, boolean isNope, boolean isProtected,
                                           boolean isUnProtected) {
        List<ItemPMS> listSex = new ArrayList<>();
        listSex.add(new ItemPMS(R.drawable.ic_sex_no_l, isNope, context.getResources().getString(R.string.nope)));
        listSex.add(new ItemPMS(R.drawable.ic_sex_protect_l, isProtected, context.getResources().getString(R.string.protect)));
        listSex.add(new ItemPMS(R.drawable.ic_sex_noprotect_l, isUnProtected, context.getResources().getString(R.string.unprotected)));
        return listSex;
    }

    public static List<ItemPMS> getListCharge(Context context, boolean isNoDischarge, boolean isSpotting,
                                              boolean isSticky, boolean isWatery,
                                              boolean isEggWhite, boolean isUnusual) {
        List<ItemPMS> listCharge = new ArrayList<>();
        listCharge.add(new ItemPMS(R.drawable.ic_no_discharge, isNoDischarge, context.getResources().getString(R.string.no_discharge)));
        listCharge.add(new ItemPMS(R.drawable.ic_spotting, isSpotting, context.getResources().getString(R.string.spotting)));
        listCharge.add(new ItemPMS(R.drawable.ic_sticky, isSticky, context.getResources().getString(R.string.sticky)));
        listCharge.add(new ItemPMS(R.drawable.ic_watery, isWatery, context.getResources().getString(R.string.watery)));
        listCharge.add(new ItemPMS(R.drawable.ic_eggwhite, isEggWhite, context.getResources().getString(R.string.eggWhite)));
        listCharge.add(new ItemPMS(R.drawable.ic_unusual, isUnusual, context.getResources().getString(R.string.unusual)));
        return listCharge;
    }

    public static List<ItemPMS> getListMedicine(Context context, boolean isMedicine) {
        List<ItemPMS> listMedicine = new ArrayList<>();
        listMedicine.add(new ItemPMS(R.drawable.ic_others_medicine_l, isMedicine, context.getResources().getString(R.string.medicine)));
        return listMedicine;
    }

    public static List<ItemPMS> getListSymptom(Context context, boolean isGood, boolean isAcne,
                                               boolean isStomachache, boolean isHeadache,
                                               boolean isDizziness, boolean isBloadting,
                                               boolean isBackache, boolean isBreastPain,
                                               boolean isNausea) {
        List<ItemPMS> listSymptom = new ArrayList<>();
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_ok, isGood, context.getResources().getString(R.string.symptom_good)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_acne, isAcne, context.getResources().getString(R.string.symptom_acne)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_cramps, isStomachache, context.getResources().getString(R.string.symptom_stomachache)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_headache, isHeadache, context.getResources().getString(R.string.symptom_headache)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_dizziness, isDizziness, context.getResources().getString(R.string.symptom_dizziness)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_bloating, isBloadting, context.getResources().getString(R.string.symptom_bloating)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_backaches, isBackache, context.getResources().getString(R.string.symptom_backache)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_tender, isBreastPain, context.getResources().getString(R.string.symptom_breast_pain)));
        listSymptom.add(new ItemPMS(R.drawable.ic_sy_nausea, isNausea, context.getResources().getString(R.string.symptom_nausea)));
        return listSymptom;
    }

    public static void setupRecyclerView(Context context, View view, LinearLayout layout,
                                         int idRecyclerView, ArrayList<ItemPMS> list, OnItemClickListener listener) {
        boolean status = false;
        for (ItemPMS itemPMS : list) {
            if (itemPMS.isStatus()) {
                status = true;
                break;
            }
        }
        if (status) {
            RecyclerView recyclerView;
            layout.setVisibility(View.VISIBLE);
            recyclerView = view.findViewById(idRecyclerView);
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false));
            PMSAdapterCalendar adapter = new PMSAdapterCalendar(list, listener);
            recyclerView.setAdapter(adapter);
        } else {
            layout.setVisibility(View.GONE);
        }
    }

    public static void updateDatabase(DBManager db, List<CyclePeriod> listCycle, int id, int cycle,
                                      int period,
                                      String userBeginDate, String userEndDate) {

        if (id != 1) {
            CyclePeriod cyclePeriod = listCycle.get(id - 2);
            cyclePeriod.setNextBeginCycle(userBeginDate);
            Date userBegin = Date.valueOf(userBeginDate);
            Date userBeginPre = Date.valueOf(cyclePeriod.getUserBeginDate());
            int userCycle = (int) Caculator.countDay(userBegin, userBeginPre);
            if (userCycle > 0) {
                cyclePeriod.setUserCycle(userCycle);
            }
            cyclePeriod = Caculator.getCyclePeriod(cyclePeriod);
            db.updateCyclePeriod(cyclePeriod);
        }

        CyclePeriod cyclePeriod = listCycle.get(id - 1);
        cyclePeriod.setUserBeginDate(userBeginDate);
        cyclePeriod.setUserEndDate(userEndDate);
        long userPeriod = Caculator.countDay(Date.valueOf(cyclePeriod.getUserEndDate()),
                Date.valueOf(cyclePeriod.getUserBeginDate())) + 1;
        cyclePeriod.setUserPeriod((int) userPeriod);
        cyclePeriod.setUserCycle(0);
        cyclePeriod.setCycle(cycle);
        cyclePeriod.setNextBeginCycle(Caculator.getBeginNextCycle(cyclePeriod));
        cyclePeriod = Caculator.getCyclePeriod(cyclePeriod);
        db.updateCyclePeriod(cyclePeriod);

        CyclePeriod secondCyclePeriod = new CyclePeriod(cycle, period, cyclePeriod.getNextBeginCycle());
        secondCyclePeriod.setId(id + 1);
        secondCyclePeriod = Caculator.getCyclePeriod(secondCyclePeriod);
        db.updateCyclePeriod(secondCyclePeriod);


        CyclePeriod thirdCyclePeriod = new CyclePeriod(cycle, period, secondCyclePeriod.getNextBeginCycle());
        thirdCyclePeriod.setId(id + 2);
        thirdCyclePeriod = Caculator.getCyclePeriod(thirdCyclePeriod);
        if (listCycle.get(listCycle.size() - 1).getId() >= id + 2)
            db.updateCyclePeriod(thirdCyclePeriod);
        else db.addCyclePeriod(thirdCyclePeriod);

        CyclePeriod fourthCyclePeriod = new CyclePeriod(cycle, period, thirdCyclePeriod.getNextBeginCycle());
        fourthCyclePeriod.setId(id + 3);
        fourthCyclePeriod = Caculator.getCyclePeriod(fourthCyclePeriod);
        if (listCycle.get(listCycle.size() - 1).getId() >= id + 3) {
            db.updateCyclePeriod(fourthCyclePeriod);
        } else db.addCyclePeriod(fourthCyclePeriod);

        CyclePeriod fiveCyclePeriod = new CyclePeriod(cycle, period, fourthCyclePeriod.getNextBeginCycle());
        fiveCyclePeriod.setId(id + 4);
        fiveCyclePeriod = Caculator.getCyclePeriod(fiveCyclePeriod);
        if (listCycle.get(listCycle.size() - 1).getId() >= id + 4) {
            db.updateCyclePeriod(fiveCyclePeriod);
        } else db.addCyclePeriod(fiveCyclePeriod);
    }

    public static boolean checkFirstCycle(String sDate1, String sDate2) {
        Date date1 = Date.valueOf(sDate1);
        Date date2 = Date.valueOf(sDate2);
        if (Caculator.countDay(date1, date2) > 15)
            return true;
        return false;
    }

    public static boolean isConflictDate(String oldBeginDate, String oldEndDate, String userBeginDate, String userEndDate) {
        if (Caculator.isConflictDate(oldBeginDate, oldEndDate, userBeginDate) ||
                Caculator.isConflictDate(oldBeginDate, oldEndDate, userEndDate) ||
                Caculator.isConflictDate(
                        Caculator.getDate(oldBeginDate, -15), oldBeginDate, userEndDate) ||
                Caculator.isConflictDate(oldBeginDate,
                        Caculator.getDate(oldEndDate, 15), userBeginDate
                )) {
            return true;
        }
        return false;
    }
    public static String formatDate(CalendarDay calendarDay) {
        String date;
        if (calendarDay.getMonth() >= 10) {
            if (calendarDay.getDay() < 10)
                date = calendarDay.getYear() + "-" + calendarDay.getMonth() + "-0" + calendarDay.getDay();
            else
                date = calendarDay.getYear() + "-" + calendarDay.getMonth() + "-" + calendarDay.getDay();
        } else {

            if (calendarDay.getDay() < 10)
                date = calendarDay.getYear() + "-0" + calendarDay.getMonth() + "-0" + calendarDay.getDay();
            else
                date = calendarDay.getYear() + "-0" + calendarDay.getMonth() + "-" + calendarDay.getDay();
        }
        return date;
    }

    public static boolean isNetworkAvaliable(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if ((connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED)
                || (connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null && connectivityManager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .getState() == NetworkInfo.State.CONNECTED)) {
            return true;
        } else {
            return false;
        }
    }
}
