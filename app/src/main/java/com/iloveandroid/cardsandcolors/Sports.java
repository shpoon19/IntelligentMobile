package com.iloveandroid.cardsandcolors;

public class Sports {
    private String news;
    private String title;
    private String info;
    private int imageResource;

    public Sports(String news, String title, String info, int imageResource) {
        this.news = news;
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }
}
