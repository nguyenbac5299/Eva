package com.example.eva.utils;

import android.content.Context;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.HashSet;
import java.util.List;

public class EventDecorator implements DayViewDecorator {
    Context mContext;
    private int mDrawable;
    private HashSet<CalendarDay> mDates;
    private int mColor;

    public EventDecorator(Context context, int drawable, List<CalendarDay> calendarDays1, int color) {
        mContext = context;
        mDrawable = drawable;
        this.mDates = new HashSet<>(calendarDays1);
        this.mColor = color;
    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return mDates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        // apply drawable to dayView
        if (mDrawable == 0) {
            //
        } else
            view.setSelectionDrawable(mContext.getResources().getDrawable(mDrawable));
        // white text color
        view.addSpan(new ForegroundColorSpan(mContext.getResources().getColor(mColor)));
    }
}
