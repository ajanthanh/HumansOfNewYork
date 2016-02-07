package com.ajanthan.webscrappertest;

import android.graphics.Bitmap;

/**
 * Created by ajanthan on 16-02-05.
 */
public class ArticleExtended implements ArticleBase {

    public enum State {DEFAULT, LOW_RES, HIGH_RES}

    private Article article;
    private Bitmap img;
    private State state;

    ArticleExtended(Article article, Bitmap img) {
        this.article = article;
        this.img = img;
        this.state = State.DEFAULT;
    }

    public Article getArticle() {
        return article;
    }

    public Bitmap getImg() {
        return img;
    }

    public void setImg(Bitmap bitmap) {
        this.img=bitmap;
        state=State.LOW_RES;
    }

    public State getState(){
        return state;
    }

    @Override
    public String getTitle() {
        return article.getTitle();
    }

    @Override
    public void setTitle(String title) {
        article.setTitle(title);
    }

    @Override
    public String getDate() {
        return article.getDate();
    }

    @Override
    public void setDate(String date) {
        article.setDate(date);
    }

    @Override
    public String getBody() {
        return article.getBody();
    }

    @Override
    public void setBody(String body) {
        article.setBody(body);
    }

    @Override
    public String getImgUrl() {
        return article.getImgUrl();
    }

    @Override
    public void setImgUrl(String imgUrl) {
        article.setImgUrl(imgUrl);
    }
}
