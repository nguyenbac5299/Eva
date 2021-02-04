package com.example.eva.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.eva.Caculator;
import com.example.eva.Constant;
import com.example.eva.R;
import com.example.eva.utils.UnifiedNativeUtils;
import com.example.eva.activity.PMSActivity;
import com.example.eva.callback.OnItemClickListener;
import com.example.eva.callback.OnUserDateChangeListener;
import com.example.eva.data.DBManager;
import com.example.eva.model.CyclePeriod;
import com.example.eva.model.ItemPMS;
import com.example.eva.model.PMS;
import com.example.eva.utils.CalendarViewUtils;
import com.example.eva.utils.DecorationCalendarView;
import com.example.eva.utils.EventDecorator;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CalendarFragment extends Fragment implements View.OnClickListener, OnItemClickListener {

    OnUserDateChangeListener IListener;
    MaterialCalendarView mCalendarView;
    Button mButtonSave, mButtonCancel;

    boolean statusEdit = false;
    String mUserBeginDate, mUserEndDate;
    LinearLayout mLayoutNoteEdit, mLayoutButtonEdit, mLayoutButtonNote;
    RelativeLayout mLayoutEdit, mLayoutTutorial;

    DBManager mDbManager;
    List<CyclePeriod> mListCycle;
    List<String> mListUserCycle, mListGuessCycle, mListOvulation, mListFertility, mListDatePMS,
            mListCurrent, mListBeginUser, mListEndUser, mListBeginGuess, mListEndGuess;

    //PMS
    PMS mPms;
    List<ItemPMS> mListMens, mListMood, mListSporty, mListMedicine, mListSymptom, mListHealthy, mListSex, mListCharge;
    private boolean isGood, isAcne, isStomachache, isHeadache, isDizziness, isBloadting, isBackache,
            isBreastPain, isNausea, isMedicine, isNoSporting, isRun, isCycling, isGym, isSwimming,
            isAerobics, isNormal, isHappy, isSad, isAngry, isWorry, isTired, isFear, isMedium, isMuch, isLittle, isTemperature, isWeight,
            isProtected, isNope, isUnProtected,
            isNoDischarge, isSpotting, isSticky, isWatery, isEggWhite, isUnusual;

    LinearLayout layoutPMS, layoutNote, layoutMood, layoutSymptom, layoutMedicine, layoutHealthy,
            layoutMenstruation, layoutSporty, layoutSex, layoutCharge;
    TextView mTextNote;
    int mPeriod, mCycle;
    int mId;
    boolean mChooseEndDate = false, mIsEdit = false;
    static boolean mStatusAd;
    public static String TAG = "CalendarFragment";
    View view;
    View mViewLiner;
    String mSelectDate;
    EventDecorator mEventDecorator;
    UnifiedNativeUtils mUnifiedNativeUtils;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        init();

        mUnifiedNativeUtils = new UnifiedNativeUtils();
        mUnifiedNativeUtils.refreshAd(getActivity(), Constant.SMALL_NATIVE_AD, R.id.fl_calendar_native_ad);

        mDbManager = new DBManager(getContext());
        mListCurrent = new ArrayList<>();
        mListCurrent.add(Caculator.getCurrentDate());

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        mPeriod = sharedPreferences.getInt(Constant.PERIOD, 5);
        mCycle = sharedPreferences.getInt(Constant.CYCLE, 28);

        decorateCalendarView(true);
        eventClickCalendar();
        addDataPMS();
        return view;
    }

    private void init() {
        mCalendarView = view.findViewById(R.id.calendar_view);
        mCalendarView.setDateTextAppearance(R.style.CustomTextAppearance);
        mCalendarView.setWeekDayTextAppearance(R.style.CustomWeekTextCalendar);

        Display mDisplay = getActivity().getWindowManager().getDefaultDisplay();
        int width = mDisplay.getWidth();
        int Height = mDisplay.getHeight();

        float screenSize = (float) Height / width;
        if (screenSize < 1.8f)
            mCalendarView.setTileHeightDp(47);
        else if (screenSize < 1.9f)
            mCalendarView.setTileHeightDp(48);
        else if (screenSize <= 2)
            mCalendarView.setTileHeightDp(50);

        else if (screenSize <= 2.1f)
            mCalendarView.setTileHeightDp(52);

        mLayoutButtonEdit = view.findViewById(R.id.layout_button_edit);
        mLayoutButtonNote = view.findViewById(R.id.layout_button_note);
        mButtonSave = view.findViewById(R.id.button_save_period);
        mButtonCancel = view.findViewById(R.id.button_cancel_period);
        layoutPMS = view.findViewById(R.id.layout_calendar_pms);
        layoutNote = view.findViewById(R.id.layout_calendar_note);
        mTextNote = view.findViewById(R.id.text_note);
        layoutMood = view.findViewById(R.id.layout_calendar_mood);
        layoutSymptom = view.findViewById(R.id.layout_calendar_symptom);
        layoutMedicine = view.findViewById(R.id.layout_calendar_medicine);
        layoutHealthy = view.findViewById(R.id.layout_calendar_healthy);
        layoutSporty = view.findViewById(R.id.layout_calendar_sporty);
        layoutMenstruation = view.findViewById(R.id.layout_calendar_menstruation);
        layoutSex = view.findViewById(R.id.layout_calendar_sex);
        mLayoutNoteEdit = view.findViewById(R.id.layout_edit_note);
        layoutCharge = view.findViewById(R.id.layout_calendar_charge);
        mViewLiner = view.findViewById(R.id.view_linear_calendar);

        mLayoutEdit = view.findViewById(R.id.layout_edit);
        mLayoutTutorial = view.findViewById(R.id.layout_tutorial);

        mLayoutButtonNote.setOnClickListener(this);
        mLayoutButtonEdit.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        layoutPMS.setOnClickListener(this);

    }

    private void addDataPMS() {
        mListMens = CalendarViewUtils.getListMens(getContext(), isLittle, isMedium, isMuch);
        mListMood = CalendarViewUtils.getListMood(getContext(), isNormal, isHappy, isSad, isAngry,
                isWorry, isTired, isFear);
        mListSporty = CalendarViewUtils.getListSporty(getContext(), isNoSporting, isRun, isCycling,
                isGym, isSwimming, isAerobics);
        mListSex = CalendarViewUtils.getListSex(getContext(), isNope, isProtected, isUnProtected);
        mListCharge = CalendarViewUtils.getListCharge(getContext(), isNoDischarge, isSpotting,
                isSticky, isWatery, isEggWhite, isUnusual);
        mListMedicine = CalendarViewUtils.getListMedicine(getContext(), isMedicine);
        mListSymptom = CalendarViewUtils.getListSymptom(getContext(), isGood, isAcne, isStomachache,
                isHeadache, isDizziness, isBloadting, isBackache, isBreastPain, isNausea);
    }

    private void showPMS(String selectDate) {
        mPms = mDbManager.getPMS(selectDate);
        if (mPms != null) {
            layoutPMS.setVisibility(View.VISIBLE);
            layoutNote.setVisibility(View.GONE);
            setupStatus();
            getPMS();
            mListHealthy = new ArrayList<>();
            if (mPms != null) {
                mListHealthy.add(new ItemPMS(R.drawable.ic_weight, isWeight, mPms.getWeight() + " kg"));
                mListHealthy.add(new ItemPMS(R.drawable.ic_tem_edit, isTemperature, mPms.getTemperature() + " °C"));
            }
            if (mPms != null && (mPms.getNote() != null)) {
                if (!mPms.getNote().equals("")) {
                    layoutNote.setVisibility(View.VISIBLE);
                    mTextNote.setText(mPms.getNote());
                }
            }
            setStatus();
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutCharge,
                    R.id.recycle_charge_calendar, (ArrayList<ItemPMS>) mListCharge, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutSex,
                    R.id.recycle_sex_calendar, (ArrayList<ItemPMS>) mListSex, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutMenstruation,
                    R.id.recycle_menstruation_calendar, (ArrayList<ItemPMS>) mListMens, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutMood,
                    R.id.recycle_mood_calendar, (ArrayList<ItemPMS>) mListMood, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutMedicine,
                    R.id.recycle_medicine_calendar, (ArrayList<ItemPMS>) mListMedicine, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutSymptom,
                    R.id.recycle_symptom_calendar, (ArrayList<ItemPMS>) mListSymptom, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutSporty,
                    R.id.recycle_sporty_calendar, (ArrayList<ItemPMS>) mListSporty, this);
            CalendarViewUtils.setupRecyclerView(getContext(), view, layoutHealthy,
                    R.id.recycle_healthy_calendar, (ArrayList<ItemPMS>) mListHealthy, this);
        } else {
            layoutPMS.setVisibility(View.GONE);
            setupStatus();
            setStatus();
        }
    }

    private void decorateCalendarView(boolean change) {
        if (change)
            getData();
        DecorationCalendarView.setEvent(getContext(), mCalendarView, mListUserCycle, Constant.IS_USER);
        DecorationCalendarView.setEvent(getContext(), mCalendarView, mListGuessCycle, Constant.IS_GUESS);
        DecorationCalendarView.setEvent(getContext(), mCalendarView, mListFertility, Constant.IS_FERTILITY);
        DecorationCalendarView.setEvent(getContext(), mCalendarView, mListOvulation, Constant.IS_OVULATION);
        DecorationCalendarView.decorateCurrentDay(getContext(), mCalendarView, mListCurrent,
                mListBeginGuess, mListEndGuess, mListGuessCycle, mListEndUser, mListBeginUser,
                mListUserCycle);
    }
    private void decorateSelectView() {
        mCalendarView.removeDecorators();
        List<String> listDay = new ArrayList<>();
        if (mUserEndDate.equals("")) {
            mUserEndDate = Caculator.getDate(mUserBeginDate, mPeriod - 1);
            listDay.addAll(Caculator.getListDay(mUserBeginDate, mUserEndDate));
        } else {
            listDay.addAll(Caculator.getListDay(mUserBeginDate, mUserEndDate));
        }
        DecorationCalendarView.setEvent(getContext(), mCalendarView, listDay, Constant.IS_USER);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        IListener = (OnUserDateChangeListener) context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_button_edit:
                mIsEdit = true;
                edit();
                break;
            case R.id.button_cancel_period:
                mIsEdit = false;
                cancel();
                break;
            case R.id.button_save_period:
                mIsEdit = false;
                save();
                break;
            case R.id.layout_button_note:
                note();
                break;
            case R.id.layout_calendar_pms:
                note();
        }
    }

    private void note() {
        mLayoutButtonNote.setEnabled(false);
        if (CalendarViewUtils.isNetworkAvaliable(getContext())) {
            mStatusAd = !mStatusAd;
            if (mStatusAd)
                showInterstitialAd();
            else
                startPMSActivity();
        } else
            startPMSActivity();
    }

    private void startPMSActivity() {
        boolean isPeriodDay = false;
        if (mSelectDate == null)
            mSelectDate = Caculator.getCurrentDate();
        if (mListUserCycle.contains(mSelectDate))
            isPeriodDay = true;
        Intent intent = new Intent(getActivity().getApplication(), PMSActivity.class);
        intent.putExtra("listMens", (Serializable) mListMens);
        intent.putExtra("listMood", (Serializable) mListMood);
        intent.putExtra("listSporty", (Serializable) mListSporty);
        intent.putExtra("listMedicine", (Serializable) mListMedicine);
        intent.putExtra("listSymptom", (Serializable) mListSymptom);
        intent.putExtra("selectedDate", mSelectDate);
        intent.putExtra("listSex", (Serializable) mListSex);
        intent.putExtra("listCharge", (Serializable) mListCharge);
        intent.putExtra("isPeriodDay", isPeriodDay);
        startActivity(intent);
        mLayoutButtonNote.setEnabled(true);
    }

    private void showInterstitialAd() {
        InterstitialAd mInterstitialAd;
        mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }

            @Override
            public void onAdClosed() {
                startPMSActivity();
            }
        });
    }

    private void edit() {
        layoutPMS.setVisibility(View.GONE);
        mUserBeginDate = "";
        mUserEndDate = "";
        statusEdit = !statusEdit;
        setVisibilityEdit();
//        mCalendarView.removeDecorators();
        mButtonSave.setEnabled(false);
        eventClickCalendar();
        // eventSwitch();
    }

    private void eventClickCalendar() {
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (mIsEdit == true) {
                    mButtonSave.setEnabled(true);
                    if ((mUserBeginDate.equals("") || mUserBeginDate == null)) {
                        mChooseEndDate = true;
                        CalendarDay calendarDay = mCalendarView.getSelectedDate();
                        mUserBeginDate = CalendarViewUtils.formatDate(calendarDay);
                        decorateSelectView();
                        Log.d(TAG, "select begin:" + mUserBeginDate);

                    } else if (mChooseEndDate) {
                        mChooseEndDate = false;
                        CalendarDay calendarDay = mCalendarView.getSelectedDate();
                        mUserEndDate = CalendarViewUtils.formatDate(calendarDay);
                        if (Caculator.checkEndUserDate(mUserBeginDate, mUserEndDate)) {
                            decorateSelectView();
                            Log.d(TAG, "select end:" + mUserEndDate);
                        }
                    } else {
                        mButtonSave.setEnabled(false);
                        mUserBeginDate = "";
                        mUserEndDate = "";
                        mCalendarView.removeDecorators();
                    }
                } else {
                    String dateSelected = CalendarViewUtils.formatDate(date);
                    mEventDecorator= DecorationCalendarView.decorateSelectedDay(getContext(), mCalendarView,
                            mEventDecorator, dateSelected, mListUserCycle, mListBeginUser,
                            mListEndUser, mListBeginGuess, mListGuessCycle, mListEndGuess);
                    mSelectDate = dateSelected;
                    showPMS(dateSelected);
                }
            }
        });
    }

    private void editListCycle() {
        if (isFirstCycle()) {
            mId = 1;
        } else {
            for (int i = 0; i < mListCycle.size(); i++) {
                CyclePeriod cyclePeriod = mListCycle.get(i);
                if (cyclePeriod.getUserBeginDate() != null && cyclePeriod.getUserEndDate() != null &&
                        CalendarViewUtils.isConflictDate(cyclePeriod.getUserBeginDate(),
                                cyclePeriod.getUserEndDate(), mUserBeginDate, mUserEndDate)) {
                    mId = cyclePeriod.getId();
                    Log.d(TAG, "id: " + mId);
                    break;
                } else if (cyclePeriod.getUserBeginDate() == null) {
                    mId = cyclePeriod.getId();
                    Log.d(TAG, "id: " + mId);
                    break;
                }
            }
        }
        CalendarViewUtils.updateDatabase(mDbManager, mListCycle, mId, mCycle, mPeriod,
                mUserBeginDate, mUserEndDate);
    }

    private boolean isFirstCycle() {
        CyclePeriod cyclePeriod = mListCycle.get(0);
        if (CalendarViewUtils.checkFirstCycle(cyclePeriod.getUserBeginDate(), mUserBeginDate))
            return true;
        return false;
    }

    private void setVisibilityEdit() {
        if (statusEdit) {
            mLayoutTutorial.setVisibility(View.VISIBLE);
            mLayoutEdit.setVisibility(View.VISIBLE);
            mLayoutNoteEdit.setVisibility(View.GONE);
            mViewLiner.setVisibility(View.GONE);
        } else {
            mLayoutEdit.setVisibility(View.GONE);
            mLayoutTutorial.setVisibility(View.GONE);
            mLayoutNoteEdit.setVisibility(View.VISIBLE);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void cancel() {
        layoutPMS.setVisibility(View.VISIBLE);
        mCalendarView.removeDecorators();
        statusEdit = !statusEdit;
        setVisibilityEdit();
        decorateCalendarView(false);
    }

    private void save() {
        layoutPMS.setVisibility(View.VISIBLE);
        mCalendarView.removeDecorators();
        editListCycle();
        Log.d(TAG, "userBeginDate: " + mUserBeginDate + "");
        statusEdit = !statusEdit;
        setVisibilityEdit();
        decorateCalendarView(true);
        IListener.changeUserDate();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mSelectDate != null)
            showPMS(mSelectDate);
        else showPMS(Caculator.getCurrentDate());
    }

    @Override
    public void onItemClick(int position) {
        note();
    }

    private void getPMS() {
        if (mPms != null && (mPms.getPms() != null)) {
            ArrayList<String> PMSs = new ArrayList<>(Arrays.asList(mPms.getPms().split(",")));
            if (PMSs.contains(getResources().getString(R.string.menstruation_light)))
                isLittle = true;
            if (PMSs.contains(getResources().getString(R.string.menstruation_heavy)))
                isMuch = true;
            if (PMSs.contains(getResources().getString(R.string.menstruation_medium)))
                isMedium = true;
            if (PMSs.contains(getResources().getString(R.string.mood_normal)))
                isNormal = true;
            if (PMSs.contains(getResources().getString(R.string.mood_fear)))
                isFear = true;
            if (PMSs.contains(getResources().getString(R.string.mood_happy)))
                isHappy = true;
            if (PMSs.contains(getResources().getString(R.string.mood_sad)))
                isSad = true;
            if (PMSs.contains(getResources().getString(R.string.mood_angry)))
                isAngry = true;
            if (PMSs.contains(getResources().getString(R.string.mood_worry)))
                isWorry = true;
            if (PMSs.contains(getResources().getString(R.string.mood_tired)))
                isTired = true;
            if (PMSs.contains(getResources().getString(R.string.no_sporty)))
                isNoSporting = true;
            if (PMSs.contains(getResources().getString(R.string.running)))
                isRun = true;
            if (PMSs.contains(getResources().getString(R.string.cycling)))
                isCycling = true;
            if (PMSs.contains(getResources().getString(R.string.gym)))
                isGym = true;
            if (PMSs.contains(getResources().getString(R.string.swimming)))
                isSwimming = true;
            if (PMSs.contains(getResources().getString(R.string.earobics)))
                isAerobics = true;
            if (PMSs.contains(getResources().getString(R.string.medicine)))
                isMedicine = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_good)))
                isGood = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_acne)))
                isAcne = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_stomachache)))
                isStomachache = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_headache)))
                isHeadache = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_dizziness)))
                isDizziness = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_bloating)))
                isBloadting = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_backache)))
                isBackache = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_breast_pain)))
                isBreastPain = true;
            if (PMSs.contains(getResources().getString(R.string.symptom_nausea)))
                isNausea = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.nope)))
                isNope = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.protect)))
                isProtected = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.unprotected)))
                isUnProtected = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.no_discharge)))
                isNoDischarge = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.spotting)))
                isSpotting = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.sticky)))
                isSticky = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.watery)))
                isWatery = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.eggWhite)))
                isEggWhite = true;
            if (PMSs.contains(getContext().getResources().getString(R.string.unusual)))
                isUnusual = true;
            if (mPms.getWeight() != 0.0f)
                isWeight = true;
            if (mPms.getTemperature() != 0.0f)
                isTemperature = true;
        }
    }
    private void setStatus() {

        mListMens.get(0).setStatus(isLittle);
        mListMens.get(1).setStatus(isMedium);
        mListMens.get(2).setStatus(isMuch);

        mListMood.get(0).setStatus(isNormal);
        mListMood.get(1).setStatus(isHappy);
        mListMood.get(2).setStatus(isSad);
        mListMood.get(3).setStatus(isAngry);
        mListMood.get(4).setStatus(isWorry);
        mListMood.get(5).setStatus(isTired);
        mListMood.get(6).setStatus(isFear);

        mListSporty.get(0).setStatus(isNoSporting);
        mListSporty.get(1).setStatus(isRun);
        mListSporty.get(2).setStatus(isCycling);
        mListSporty.get(3).setStatus(isGym);
        mListSporty.get(4).setStatus(isSwimming);
        mListSporty.get(5).setStatus(isAerobics);

        mListMedicine.get(0).setStatus(isMedicine);

        mListSex.get(0).setStatus(isNope);
        mListSex.get(1).setStatus(isProtected);
        mListSex.get(2).setStatus(isUnProtected);

        mListCharge.get(0).setStatus(isNoDischarge);
        mListCharge.get(1).setStatus(isSpotting);
        mListCharge.get(2).setStatus(isSticky);
        mListCharge.get(3).setStatus(isWatery);
        mListCharge.get(4).setStatus(isEggWhite);
        mListCharge.get(5).setStatus(isUnusual);

        mListSymptom.get(0).setStatus(isGood);
        mListSymptom.get(1).setStatus(isAcne);
        mListSymptom.get(2).setStatus(isStomachache);
        mListSymptom.get(3).setStatus(isHeadache);
        mListSymptom.get(4).setStatus(isDizziness);
        mListSymptom.get(5).setStatus(isBloadting);
        mListSymptom.get(6).setStatus(isBackache);
        mListSymptom.get(7).setStatus(isBreastPain);
    }

    private void getData() {
        if (mListFertility == null && mListUserCycle == null && mListOvulation == null && mListFertility == null) {
            mListUserCycle = new ArrayList<>();
            mListGuessCycle = new ArrayList<>();
            mListOvulation = new ArrayList<>();
            mListFertility = new ArrayList<>();
            mListBeginUser = new ArrayList<>();
            mListBeginGuess = new ArrayList<>();
            mListEndUser = new ArrayList<>();
            mListEndGuess = new ArrayList<>();
        } else {
            mListFertility.clear();
            mListUserCycle.clear();
            mListGuessCycle.clear();
            mListOvulation.clear();
            mListBeginUser.clear();
            mListBeginGuess.clear();
            mListEndUser.clear();
            mListEndGuess.clear();
        }
        mListCycle = mDbManager.getListCycle();

        for (CyclePeriod cyclePeriod : mListCycle) {
            if (cyclePeriod.getUserPeriod() != 0) {
                mListUserCycle.addAll(Caculator.getListDay(
                        cyclePeriod.getUserBeginDate(), cyclePeriod.getUserEndDate()));
                mListBeginUser.add(cyclePeriod.getUserBeginDate());
                mListEndUser.add(cyclePeriod.getUserEndDate());
                mListOvulation.add(cyclePeriod.getOvulationDate());
                mListFertility.addAll(Caculator.getListDay(
                        cyclePeriod.getBeginFertility(), cyclePeriod.getEndFertility()));
            } else {
                mListGuessCycle.addAll(Caculator.getListDay(
                        cyclePeriod.getBeginDate(), cyclePeriod.getEndDate()));
                mListBeginGuess.add(cyclePeriod.getBeginDate());
                mListEndGuess.add(cyclePeriod.getEndDate());
                mListOvulation.add(cyclePeriod.getOvulationDate());
                mListFertility.addAll(Caculator.getListDay(
                        cyclePeriod.getBeginFertility(), cyclePeriod.getEndFertility()));
            }
        }
    }
    private void setupStatus() {
        isGood = false;
        isAcne = false;
        isStomachache = false;
        isHeadache = false;
        isDizziness = false;
        isBloadting = false;
        isBackache = false;
        isBreastPain = false;
        isNausea = false;
        isMedicine = false;
        isNoSporting = false;
        isRun = false;
        isCycling = false;
        isGym = false;
        isSwimming = false;
        isAerobics = false;
        isNormal = false;
        isHappy = false;
        isSad = false;
        isAngry = false;
        isWorry = false;
        isTired = false;
        isFear = false;
        isMedium = false;
        isMuch = false;
        isLittle = false;
        isTemperature = false;
        isWeight = false;
        isNope = false;
        isProtected = false;
        isUnProtected = false;
        isNoSporting = false;
        isSpotting = false;
        isSticky = false;
        isWatery = false;
        isEggWhite = false;
        isUnusual = false;
    }
}
