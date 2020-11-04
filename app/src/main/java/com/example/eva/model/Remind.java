package com.example.eva.model;

public class Remind {
    int image;
    String title;
    String content;
    boolean chooseSwitch;

    public Remind(int image, String title, String content, boolean chooseSwitch) {
        this.image = image;
        this.title = title;
        this.content = content;
        this.chooseSwitch = chooseSwitch;
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
