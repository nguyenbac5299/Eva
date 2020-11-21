package com.example.eva.callback;

import java.util.Calendar;

public interface OnTimePickerPeriodicListener {
    void onSetTime(Calendar calendar, boolean save, String tag);
}
