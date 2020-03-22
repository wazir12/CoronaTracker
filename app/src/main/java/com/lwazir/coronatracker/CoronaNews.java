package com.lwazir.coronatracker;

public class CoronaNews {

    String sName;
    String title;
    String description;
    String publishedAt;
    String url;

    public CoronaNews(String sName, String title,String publishedAt, String url) {
        this.sName = sName;
        this.title = title;

        this.publishedAt=publishedAt;
        this.url = url;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }



}
