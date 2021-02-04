package com.example.eva.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class PMS implements Parcelable {
    int id;
    String date;
    String pms;
    float weight;
    float temperature;
    String note;

    protected PMS(Parcel in) {
        id = in.readInt();
        date = in.readString();
        pms = in.readString();
        weight = in.readFloat();
        temperature = in.readFloat();
        note = in.readString();
    }

    public static final Creator<PMS> CREATOR = new Creator<PMS>() {
        @Override
        public PMS createFromParcel(Parcel in) {
            return new PMS(in);
        }

        @Override
        public PMS[] newArray(int size) {
            return new PMS[size];
        }
    };

    public void setNote(String note) {
        this.note = note;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getNote() {
        return note;
    }

    public PMS(int id, String date, String pms) {
        this.id = id;
        this.date = date;
        this.pms = pms;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getPms() {
        return pms;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPms(String pms) {
        this.pms = pms;
    }

    public PMS() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(date);
        dest.writeString(pms);
        dest.writeFloat(weight);
        dest.writeFloat(temperature);
        dest.writeString(note);
    }

    public static class Symptom {
        public static final int GOOD = 0;
        public static final int ACNE = 1;
        public static final int STOMACHACHE = 2; //ĐAU BỤNG
        public static final int HEADACHE = 3; //ĐAU ĐẦU
        public static final int DIZZINESS = 4; //CHÓNG MẶT
        public static final int BLOADTING = 5;// ĐẦY HƠI
        public static final int BACKACHE = 6;//ĐAU LƯNG
        public static final int BREAST_PAIN= 7; //ĐAU NGỰC
        public static final int NAUSEA = 8; //BUỒN NÔN
    }

    public static class HealthyState {
        public static int weight;
        public static int temperature;
    }

    public static class Medicine {
        public static boolean medicine;
    }

    public static class Sporting {
        public static final int NO_SPORTING = 0;
        public static final int RUN = 1;
        public static final int CYCLING = 2;
        public static final int GYM = 3;
        public static final int SWIMMING = 4;
        public static final int AEROBICS = 5;

    }

    public static class Mood {
        public static final int NORMAL = 0;
        public static final int HAPPY = 1;
        public static final int SAD = 2;
        public static final int ANGRY = 3;
        public static final int WORRY = 4;
        public static final int TIRED = 5;
        public static final int FEAR = 6;
    }

    public static class Menstruation {
        public static final int MEDIUM = 0;
        public static final int MUCH = 1;
        public static final int LITTLE = 2;
    }

    public class Note {
        String note;
    }


}
