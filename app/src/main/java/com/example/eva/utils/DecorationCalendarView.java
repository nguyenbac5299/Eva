package com.example.eva.utils;

import android.content.Context;

import com.example.eva.Constant;
import com.example.eva.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DecorationCalendarView {
    static final String DATE_FORMAT = "yyyy-MM-dd";

    public static void setEvent(Context context, MaterialCalendarView view, List<String> dateList, int isUser) {
        List<LocalDate> localDateList = new ArrayList<>();

        for (String string : dateList) {
            LocalDate calendar = getLocalDate(string);
            if (calendar != null) {
                localDateList.add(calendar);
            }
        }

        List<CalendarDay> datesLeft = new ArrayList<>();
        List<CalendarDay> datesCenter = new ArrayList<>();
        List<CalendarDay> datesRight = new ArrayList<>();
        List<CalendarDay> datesIndependent = new ArrayList<>();


        for (LocalDate localDate : localDateList) {

            boolean right = false;
            boolean left = false;

            for (LocalDate day1 : localDateList) {


                if (localDate.isEqual(day1.plusDays(1))) {
                    left = true;
                }
                if (day1.isEqual(localDate.plusDays(1))) {
                    right = true;
                }
            }

            if (left && right) {
                datesCenter.add(CalendarDay.from(localDate));
            } else if (left) {
                datesLeft.add(CalendarDay.from(localDate));
            } else if (right) {
                datesRight.add(CalendarDay.from(localDate));
            } else {
                datesIndependent.add(CalendarDay.from(localDate));
            }
        }
        if (isUser == Constant.IS_CURRENT) {
            setDecor(context, view, datesCenter, R.drawable.ic_circle, R.color.colorTextBlack);
            setDecor(context, view, datesLeft, R.drawable.ic_circle, R.color.colorTextBlack);
            setDecor(context, view, datesRight, R.drawable.ic_circle, R.color.colorTextBlack);
            setDecor(context, view, datesIndependent, R.drawable.ic_circle, R.color.colorTextBlack);
        } else if (isUser == Constant.IS_USER) {
            setDecor(context, view, datesCenter, R.drawable.bg_period_center, R.color.colorWhite);
            setDecor(context, view, datesLeft, R.drawable.bg_period_left, R.color.colorWhite);
            setDecor(context, view, datesRight, R.drawable.bg_period_right, R.color.colorWhite);
            setDecor(context, view, datesIndependent, R.drawable.p_independent, R.color.colorWhite);
        } else if (isUser == Constant.IS_GUESS) {
            setDecor(context, view, datesCenter, R.drawable.bg_guess_center, R.color.colorMainPink);
            setDecor(context, view, datesLeft, R.drawable.bg_guess_right, R.color.colorMainPink);
            setDecor(context, view, datesRight, R.drawable.bg_guess_left, R.color.colorMainPink);
            setDecor(context, view, datesIndependent, R.drawable.bg_independent, R.color.colorMainPink);
        } else if (isUser == Constant.IS_OVULATION) {
            setDecor(context, view, datesCenter, R.drawable.ic_ovulation, R.color.colorWhite);
            setDecor(context, view, datesLeft, R.drawable.ic_ovulation, R.color.colorWhite);
            setDecor(context, view, datesRight, R.drawable.ic_ovulation, R.color.colorWhite);
            setDecor(context, view, datesIndependent, R.drawable.ic_ovulation, R.color.colorWhite);
        } else if (isUser == Constant.IS_FERTILITY) {
            setDecor(context, view, datesCenter, 0, R.color.colorCircleOvulation);
            setDecor(context, view, datesLeft, 0, R.color.colorCircleOvulation);
            setDecor(context, view, datesRight, 0, R.color.colorCircleOvulation);
            setDecor(context, view, datesIndependent, 0, R.color.colorCircleOvulation);
        }
    }

    static void setDecor(Context context, MaterialCalendarView view, List<CalendarDay> calendarDayList, int drawable, int color) {
        EventDecorator eventDecorator = new EventDecorator(context, drawable, calendarDayList, color);
        view.addDecorator(eventDecorator);
    }


    public static LocalDate getLocalDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        try {
            Date input = sdf.parse(date);
            Calendar cal = Calendar.getInstance();
            cal.setTime(input);
            return LocalDate.of(cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH) + 1,
                    cal.get(Calendar.DAY_OF_MONTH));


        } catch (NullPointerException e) {
            return null;
        } catch (ParseException e) {
            return null;
        }
    }

    private static void decorateOneDay(Context context, String day, int color, int drawable, MaterialCalendarView view) {
        List<CalendarDay> listDay = new ArrayList<>();
        LocalDate calendar = DecorationCalendarView.getLocalDate(day);
        listDay.add(CalendarDay.from(calendar));
        EventDecorator eventDecorator = new EventDecorator(context, drawable, listDay, color);
        view.addDecorator(eventDecorator);
    }

    public static EventDecorator decorateSelectedDay(Context context, MaterialCalendarView view,
                                                     EventDecorator eventDecorator, String day,
                                                     List<String> listUserCycle, List<String> listBeginUser,
                                                     List<String> listEndUser, List<String> listBeginGuess,
                                                     List<String> listGuessCycle, List<String> listEndGuess) {
        List<CalendarDay> listSelected = new ArrayList<>();
        LocalDate calendar = DecorationCalendarView.getLocalDate(day);
        listSelected.add(CalendarDay.from(calendar));
        int drawable, color;
        if (listUserCycle.contains(day)) {
            if (listBeginUser.contains(day)) {
                drawable = R.drawable.bg_select_period_left;
                color = R.color.colorMainPink;
            } else if (listEndUser.contains(day)) {
                drawable = R.drawable.bg_select_period_right;
                color = R.color.colorMainPink;
            } else {
                drawable = R.drawable.bg_select_period_in;
                color = R.color.colorMainPink;
            }
        } else if (listGuessCycle.contains(day)) {
            if (listBeginGuess.contains(day)) {
                drawable = R.drawable.bg_select_guess_left;
                color = R.color.colorWhite;
            } else if (listEndGuess.contains(day)) {
                drawable = R.drawable.bg_select_guess_right;
                color = R.color.colorWhite;
            } else {
                drawable = R.drawable.bg_select_guess_in;
                color = R.color.colorWhite;
            }
        } else {
            drawable = R.drawable.ic_bg_select;
            color = R.color.colorWhite;
        }
        if (eventDecorator != null)
            view.removeDecorator(eventDecorator);
        eventDecorator = new EventDecorator(context, drawable, listSelected, color);
        view.addDecorator(eventDecorator);
        return eventDecorator;
    }

    public static void decorateCurrentDay(Context context, MaterialCalendarView view,
                                          List<String> listCurrent, List<String> listBeginGuess,
                                          List<String> listEndGuess, List<String> listGuessCycle,
                                          List<String> listEndUser, List<String> listBeginUser,
                                          List<String> listUserCycle) {
        if (listBeginGuess.contains(listCurrent.get(0)))
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorMainPink,
                    R.drawable.bg_first_current_guess,
                    view);

        else if (listEndGuess.contains(listCurrent.get(0)))
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorMainPink,
                    R.drawable.bg_end_current_guess,
                    view);
        else if (listGuessCycle.contains(listCurrent.get(0)))
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorMainPink,
                    R.drawable.bg_in_current_guess,
                    view);

        else if (listEndUser.contains(listCurrent.get(0)))
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorWhite,
                    R.drawable.bg_period_last,
                    view);
        else if (listBeginUser.contains(listCurrent.get(0)))
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorWhite,
                    R.drawable.bg_period_begin,
                    view);
        else if (listUserCycle.contains(listCurrent.get(0)))
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorWhite,
                    R.drawable.bg_current_date,
                    view);
        else
            decorateOneDay(context,
                    listCurrent.get(0),
                    R.color.colorTextBlack,
                    R.drawable.ic_circle,
                    view);
    }
}
