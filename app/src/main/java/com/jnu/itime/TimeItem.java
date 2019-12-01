package com.jnu.itime;

public class TimeItem {
    private int imageId;
    private String title;
    private String description;
    private String date;
    private String countdown;

    public TimeItem(int imageId, String title, String description, String date, String countdown) {
        this.imageId = imageId;
        this.title = title;
        this.description = description;
        this.date = date;
        this.countdown = countdown;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountdown() {
        return countdown;
    }

    public void setCountdown(String countdown) {
        this.countdown = countdown;
    }
}
