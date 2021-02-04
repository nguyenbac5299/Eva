package com.example.eva.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.eva.Caculator;
import com.example.eva.model.CyclePeriod;
import com.example.eva.model.PMS;
import com.example.eva.model.Remind;
import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CYCLE_PERIOD_MANAGER";
    public static final String TABLE_CYCLE = "CYCLE_PERIOD";
    public static final String MONTH = "MONTH";
    public static final String BEGIN_DATE = "BEGIN_DATE";
    public static final String END_DATE = "END_DATE";
    public static final String OVULATION_DATE = "OVULATIONDATE";
    public static final String BEGIN_FERTILITY = "BEGIN_FERTILITY";
    public static final String END_FERTILITY = "END_FERTILITY";
    public static final String CYCLE = "CYCLE";
    public static final String PERIOD = "PERIOD";
    public static final String ID_CYCLE = "ID_CYCLE";
    public static final String NEXT_BEGIN_CYCLE = "NEXT_BEGINDATE";
    public static final String USER_BEGIN_DATE = "USER_BEGIN_DATE";
    public static final String USER_END_DATE = "USER_END_DATE";
    public static final String USER_CYCLE = "USER_CYCLE";
    public static final String USER_PERIOD = "USER_PERIOD";
    //table pms
    public static final String TABLE_PMS = "PMS";
    //ID+DATE(1-2)
    public static final String ID_PMS = "ID_PMS";
    public static final String DATE = "DATE";
    public static final String PMS = "PMS";
    public static final String NOTE = "NOTE";
    public static final String WEIGHT = "WEIGHT";
    public static final String TEMPERATURE = "TEMPERATURE";

    //table remind
    public static final String TABLE_REMIND = "REMIND";
    public static final String ID_REMIND = "ID";
    public static final String STATUS = "STATUS";
    public static final String TIME = "TIME";
    public static final String MESSAGE = "MESSAGE";
    public static final String DEFAUL_TIME = "08:00";

    private static int VERSION = 1;
    private Context context;

    private String SQLQueryCycle = "CREATE TABLE " + TABLE_CYCLE + " (" +
            ID_CYCLE + " integer primary key, " +
            MONTH + " integer, " +
            BEGIN_DATE + " TEXT, " +
            END_DATE + " TEXT, " +
            OVULATION_DATE + " TEXT, " +
            BEGIN_FERTILITY + " TEXT, " +
            END_FERTILITY + " TEXT, " +
            CYCLE + " integer, " +
            NEXT_BEGIN_CYCLE + " TEXT, " +
            PERIOD + " integer, " +
            USER_BEGIN_DATE + " TEXT, " +
            USER_END_DATE + " TEXT, " +
            USER_CYCLE + " integer, " +
            USER_PERIOD + " integer) ";

    private String SQLQueryPMS = "CREATE TABLE " + TABLE_PMS + " (" +
            ID_PMS + " integer primary key, " +
            DATE + " TEXT, " +
            PMS + " TEXT, " +
            NOTE + " TEXT, " +
            WEIGHT + " REAL, " +
            TEMPERATURE + " REAL)";

    private String SQLQueryRemind = "CREATE TABLE " + TABLE_REMIND + " (" +
            ID_REMIND + " integer primary key, " +
            STATUS + " integer, " +
            TIME + " TEXT, " +
            MESSAGE + " TEXT); ";

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    public void addRemind(Remind remind) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (remind.isChooseSwitch())
            values.put(STATUS, 1);
        else
            values.put(STATUS, 0);
        values.put(TIME, remind.getTime());
        values.put(MESSAGE, remind.getContent());
        database.insert(TABLE_REMIND, null, values);
        database.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQueryCycle);
        db.execSQL(SQLQueryPMS);
        db.execSQL(SQLQueryRemind);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addPMS(PMS pms) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, pms.getDate());
        values.put(PMS, pms.getPms());
        values.put(NOTE, pms.getNote());
        values.put(WEIGHT, pms.getWeight());
        values.put(TEMPERATURE, pms.getTemperature());
        database.insert(TABLE_PMS, null, values);
        database.close();
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
        database.insert(TABLE_CYCLE, null, values);
        database.close();
    }

    public PMS getPMS(String date) {
        PMS pms = null;
        String selectQuery = "SELECT * FROM " + TABLE_PMS + " WHERE " + DATE + " LIKE " + "'" + date + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                pms = new PMS();
                pms.setId(cursor.getInt(0));
                pms.setDate(cursor.getString(1));
                pms.setPms(cursor.getString(2));
                pms.setNote(cursor.getString(3));
                pms.setWeight(cursor.getFloat(4));
                pms.setTemperature(cursor.getFloat(5));
            } while (cursor.moveToNext());
        }
        db.close();
        return pms;
    }

    public Remind getRemind(int id) {
        Remind remind = null;
        String selectQuery = "SELECT * FROM " + TABLE_REMIND +" WHERE " + ID_REMIND + " = " + (id+1);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            remind = new Remind();
            remind.setID(cursor.getInt(0));
            int i = cursor.getInt(1);
            if (i == 0)
                remind.setChooseSwitch(false);
            else
                remind.setChooseSwitch(true);
            remind.setTime(cursor.getString(2));
            remind.setContent(cursor.getString(3));
        }
        db.close();
        return remind;
    }
    public int updateRemind(Remind remind) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (remind.isChooseSwitch())
            values.put(STATUS, 1);
        else
            values.put(STATUS, 0);
        values.put(TIME, remind.getTime());
        values.put(MESSAGE, remind.getContent());
        return sqLiteDatabase.update(TABLE_REMIND, values, ID_REMIND + "=?", new String[]{String.valueOf(remind.getID())});
    }

    public List<Remind> getListRemindPeriodic(){
        List<Remind> listRemind=new ArrayList<>();
        String selectQuery="SELECT * FROM "+ TABLE_REMIND+" WHERE "+ ID_REMIND+" < 6";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            do {
                Remind remind=new Remind();
                remind.setID(cursor.getInt(0));
                int i = cursor.getInt(1);
                if (i == 0)
                    remind.setChooseSwitch(false);
                else
                    remind.setChooseSwitch(true);
                remind.setTime(cursor.getString(2));
                remind.setContent(cursor.getString(3));
                listRemind.add(remind);
            }while (cursor.moveToNext());
        }
        db.close();
        return listRemind;
    }
    public Remind getRemindMedicine(){
        Remind remind=null;
        String selectQuery="SELECT * FROM "+ TABLE_REMIND+" WHERE "+ ID_REMIND+" = 6";
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            remind=new Remind();
                remind.setID(cursor.getInt(0));
                int i = cursor.getInt(1);
                if (i == 0)
                    remind.setChooseSwitch(false);
                else
                    remind.setChooseSwitch(true);
                remind.setTime(cursor.getString(2));
                remind.setContent(cursor.getString(3));
        }
//        db.close();
        return remind;
    }

    public List<CyclePeriod> getListCycle() {
        List<CyclePeriod> listCycle = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CYCLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CyclePeriod cyclePeriod = new CyclePeriod();
                cyclePeriod.setId(cursor.getInt(0));
                cyclePeriod.setMonth(cursor.getInt(1));
                cyclePeriod.setBeginDate(cursor.getString(2));
                cyclePeriod.setEndDate(cursor.getString(3));
                cyclePeriod.setOvulationDate(cursor.getString(4));
                cyclePeriod.setBeginFertility(cursor.getString(5));
                cyclePeriod.setEndFertility(cursor.getString(6));
                cyclePeriod.setCycle(cursor.getInt(7));
                cyclePeriod.setNextBeginCycle(cursor.getString(8));
                cyclePeriod.setPeriod(cursor.getInt(9));
                cyclePeriod.setUserBeginDate(cursor.getString(10));
                cyclePeriod.setUserEndDate(cursor.getString(11));
                cyclePeriod.setUserCycle(cursor.getInt(12));
                cyclePeriod.setUserPeriod(cursor.getInt(13));
                listCycle.add(cyclePeriod);
            } while (cursor.moveToNext());
        }
        db.close();
        return listCycle;
    }

    public CyclePeriod getCurrentCycle(int id) {
        CyclePeriod currentCycle = null;
        //String selectQuery = "SELECT * FROM " + TABLE_CYCLE + " WHERE " + ID_CYCLE + " >= " + id;
        String selectQuery = "SELECT * FROM " + TABLE_CYCLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                CyclePeriod cyclePeriod = new CyclePeriod();
                cyclePeriod.setBeginDate(cursor.getString(2));
                String userBeginDate = cursor.getString(10);
                String nextBeginCycle=cursor.getString(8);
                cyclePeriod.setNextBeginCycle(nextBeginCycle);
                cyclePeriod.setUserBeginDate(userBeginDate);
                if (userBeginDate == null || Caculator.inCycle(cyclePeriod)) {
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
                    db.close();
                    return currentCycle;
                }

            } while (cursor.moveToNext());
        }
        db.close();
        return currentCycle;
    }

    public int updatePMS(PMS pms) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DATE, pms.getDate());
        values.put(PMS, pms.getPms());
        values.put(NOTE, pms.getNote());
        values.put(WEIGHT, pms.getWeight());
        values.put(TEMPERATURE, pms.getTemperature());
        return sqLiteDatabase.update(TABLE_PMS, values, ID_PMS + "=?", new String[]{String.valueOf(pms.getId())});
    }

    public int updateCyclePeriod(CyclePeriod cyclePeriod) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
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
        return sqLiteDatabase.update(TABLE_CYCLE, values, ID_CYCLE + "=?", new String[]{String.valueOf(cyclePeriod.getId())});

    }
}
