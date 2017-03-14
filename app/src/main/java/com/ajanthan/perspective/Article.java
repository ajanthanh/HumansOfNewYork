package com.ajanthan.perspective;

import java.io.Serializable;

/**
 * Created by ajanthan on 16-02-02.
 */
public class Article implements Serializable {
    private String title;
    private String date;
    private String body;
    private String imgUrl;

    Article(String title, String date, String body, String imgUrl) {
        this.title = title;
        this.date = date;
        this.body= body.trim();
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
