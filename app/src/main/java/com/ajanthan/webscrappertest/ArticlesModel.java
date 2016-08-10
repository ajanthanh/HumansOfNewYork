package com.ajanthan.webscrappertest;

import java.util.ArrayList;

/**
 * Created by ajanthan on 16-08-09.
 */
public class ArticlesModel {
    private ArrayList<Article> articles;
    private Integer pageNumber;

    private static ArticlesModel instance;

    private ArticlesModel(){
        articles = new ArrayList<>();
        pageNumber =0;
    }

    public static ArticlesModel getInstance(){
        if(instance == null){
            instance = new ArticlesModel();
        }
        return instance;
    }

    public ArrayList<Article> getArticles(){
        return articles;
    }

    public void addArticles(ArrayList<Article> newArticles){
        articles.addAll(newArticles);
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }
}
