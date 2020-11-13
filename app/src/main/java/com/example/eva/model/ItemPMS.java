package com.example.eva.model;

public class ItemPMS {
    int image;
    boolean status;
    String text;

    public ItemPMS(int image, boolean status, String text) {
        this.image = image;
        this.status = status;
        this.text = text;
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
