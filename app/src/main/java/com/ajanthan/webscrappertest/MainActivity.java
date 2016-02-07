package com.ajanthan.webscrappertest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends Activity implements OnArticleScrapCompleted {

    private RecyclerView rArticlesList;
    private ArticleRecyclerViewAdapter articleRecyclerViewAdapter;


    // URL Address
    private String url = "http://www.humansofnewyork.com?page=";
    private int page;
    private boolean loadmore=false;

    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_listview);
        ArrayList<Article> articles = new ArrayList<Article>();
        page = 1;
        rArticlesList = (RecyclerView) findViewById(R.id.articleList);
        articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this, articles);
        rArticlesList.setAdapter(articleRecyclerViewAdapter);
        rArticlesList.setLayoutManager(new LinearLayoutManager(this));

        new RequestArticle(articleRecyclerViewAdapter, url + page,this).execute();
        page++;

        rArticlesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = articleRecyclerViewAdapter.getItemCount();
                int firstVisibleItem = articleRecyclerViewAdapter.getCurrent_postion();


                if(totalItemCount-2<firstVisibleItem&&loadmore==false){
                    loadmore=true;
                    new RequestArticle(articleRecyclerViewAdapter, url + page,MainActivity.this).execute();
                    page++;
                }
            }
        });
    }

    @Override
    public void onTaskCompleted() {
        loadmore=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}