package com.example.eva.model;

public class Remind {
    int ID;
    int image;
    String title;
    String content;
    boolean chooseSwitch;
    String time;

    public Remind(int image, String title, String content, boolean chooseSwitch) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.chooseSwitch = chooseSwitch;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Remind( boolean chooseSwitch, String time, String content) {
        this.content = content;
        this.chooseSwitch = chooseSwitch;
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public Remind() {
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public boolean isChooseSwitch() {
        return chooseSwitch;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setChooseSwitch(boolean chooseSwitch) {
        this.chooseSwitch = chooseSwitch;
    }
}
