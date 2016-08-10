package com.ajanthan.webscrappertest;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements OnArticleScrapCompleted {

    private RecyclerView rArticlesList;
    private ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    private ProgressBar progressBar;

    // URL Address
    private String url = "https://be1uo0y30d.execute-api.us-east-1.amazonaws.com/prod/data?limit=10&offset=";
    private ArticlesModel articlesModel;
    private boolean loadmore=false;

    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_listview);

        final ActionBar abar = getSupportActionBar();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_main);
        articlesModel = ArticlesModel.getInstance();

        rArticlesList = (RecyclerView) findViewById(R.id.articleList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this);
        rArticlesList.setAdapter(articleRecyclerViewAdapter);
        rArticlesList.setLayoutManager(new LinearLayoutManager(this));

        if(articlesModel.getPageNumber()==0){
            Log.e("pokemon","SHA BANG");
            new JsonRequestArticle(articleRecyclerViewAdapter, url + articlesModel.getPageNumber(),this).execute();
            articlesModel.setPageNumber(articlesModel.getPageNumber()+10);
        }

        rArticlesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = articleRecyclerViewAdapter.getItemCount();
                int firstVisibleItem = articleRecyclerViewAdapter.getCurrent_postion();

                if(totalItemCount-2<firstVisibleItem&&loadmore==false){
                    loadmore=true;
                    new JsonRequestArticle(articleRecyclerViewAdapter, url + articlesModel.getPageNumber(),MainActivity.this).execute();
                    articlesModel.setPageNumber(articlesModel.getPageNumber()+10);
                }
            }
        });
    }
    
    @Override
    public void onTaskCompleted() {
        loadmore=false;
        progressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
