package com.example.eva.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.eva.CaculatorCyclePeriod;
import com.example.eva.model.CyclePeriod;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CYCLE_PERIOD_MANAGER";
    public static final String TABLE_NAME = "CYCLE_PERIOD";
    public static final String MONTH = "MONTH";
    public static final String BEGIN_DATE = "BEGIN_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String OVULATION_DATE = "OVULATIONDATE";
    public static final String BEGIN_FERTILITY = "BEGIN_FERTILITY";
    public static final String END_FERTILITY = "END_FERTILITY";
    public static final String CYCLE = "CYCLE";
    public static final String PERIOD = "PERIOD";
    public static final String ID = "ID";
    public static final String NEXT_BEGIN_CYCLE = "NEXT_BEGINDATE";
    public static final String USER_BEGIN_DATE = "USER_BEGIN_DATE";
    public static final String USER_END_DATE = "USER_END_DATE";
    public static final String USER_CYCLE="USER_CYCLE";
    public static final String USER_PERIOD="USER_PERIOD";


    private static int VERSION = 1;
    private Context context;

    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            ID + " integer primary key, " +
            MONTH + " integer, " +
            BEGIN_DATE + " TEXT, " +
            END_DATE + " TEXT, " +
            OVULATION_DATE + " TEXT, " +
            BEGIN_FERTILITY + " TEXT, " +
            END_FERTILITY + " TEXT, " +
            CYCLE + " integer, " +
            NEXT_BEGIN_CYCLE + " TEXT, " +
            PERIOD + " integer, " +
            USER_BEGIN_DATE+" TEXT, "+
            USER_END_DATE+" TEXT, "+
            USER_CYCLE+" integer, "+
            USER_PERIOD+" integer) ";

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
        Log.d("BacNT", "createDatabase");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addCyclePeriod(CyclePeriod cyclePeriod) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MONTH, cyclePeriod.getMonth());
        values.put(BEGIN_DATE, cyclePeriod.getBeginDate());
        values.put(END_DATE, cyclePeriod.getEndDate());
        values.put(OVULATION_DATE, cyclePeriod.getOvulationDate());
        values.put(BEGIN_FERTILITY, cyclePeriod.getBeginFertility());
        values.put(END_FERTILITY, cyclePeriod.getEndFertility());
        values.put(CYCLE, cyclePeriod.getCycle());
        values.put(NEXT_BEGIN_CYCLE, cyclePeriod.getNextBeginCycle());
        values.put(PERIOD, cyclePeriod.getPeriod());
        values.put(USER_BEGIN_DATE, cyclePeriod.getUserBeginDate());
        values.put(USER_END_DATE, cyclePeriod.getUserEndDate());
        values.put(USER_CYCLE, cyclePeriod.getUserCycle());
        values.put(USER_PERIOD, cyclePeriod.getUserPeriod());
        database.insert(TABLE_NAME, null, values);
        database.close();

    }

    public CyclePeriod getCurrentCycle() {

        CyclePeriod currentCycle = null;
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CyclePeriod cyclePeriod = new CyclePeriod();
                cyclePeriod.setBeginDate(cursor.getString(2));
                cyclePeriod.setNextBeginCycle(cursor.getString(8));
                if (CaculatorCyclePeriod.inCycle(cyclePeriod)) {
                    db.close();
                    cyclePeriod.setId(cursor.getInt(0));
                    cyclePeriod.setMonth(cursor.getInt(1));
                    cyclePeriod.setEndDate(cursor.getString(3));
                    cyclePeriod.setOvulationDate(cursor.getString(4));
                    cyclePeriod.setBeginFertility(cursor.getString(5));
                    cyclePeriod.setEndFertility(cursor.getString(6));
                    cyclePeriod.setCycle(cursor.getInt(7));
                    cyclePeriod.setPeriod(cursor.getInt(9));
                    cyclePeriod.setUserBeginDate(cursor.getString(10));
                    cyclePeriod.setUserEndDate(cursor.getString(11));
                    cyclePeriod.setUserCycle(cursor.getInt(12));
                    cyclePeriod.setUserPeriod(cursor.getInt(13));
                    currentCycle = cyclePeriod;
                    return currentCycle;
                }

            } while (cursor.moveToNext());
        }
        db.close();
        return currentCycle;
    }
}
