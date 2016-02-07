package com.ajanthan.webscrappertest;

/**
 * Created by ajanthan on 16-02-05.
 */
public interface ArticleBase {
    public String getTitle();

    public void setTitle(String title);

    public String getDate();

    public void setDate(String date);

    public String getBody();

    public void setBody(String body);

    public String getImgUrl();

    public void setImgUrl(String imgUrl);
}
