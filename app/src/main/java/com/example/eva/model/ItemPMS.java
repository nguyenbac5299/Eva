package com.example.eva.model;

import android.os.Parcel;

import java.io.Serializable;

public class ItemPMS implements Serializable {
    int image;
    boolean status;
    String text;

    public ItemPMS(int image, boolean status, String text) {
        this.image = image;
        this.status = status;
        this.text = text;
    }

    protected ItemPMS(Parcel in) {
        image = in.readInt();
        status = in.readByte() != 0;
        text = in.readString();
    }

    public int getImage() {
        return image;
    }

    public boolean isStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setText(String text) {
        this.text = text;
    }
}
