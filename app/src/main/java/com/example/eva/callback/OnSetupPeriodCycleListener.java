package com.example.eva.callback;

public interface OnSetupPeriodCycleListener {
    void onChangeCycle(int cycle);
    void onChangePeriod(int period);
    void onChangeCalendar(int year, int month, int dayOfMonth);
    void onFinishSetup(boolean status);
}
