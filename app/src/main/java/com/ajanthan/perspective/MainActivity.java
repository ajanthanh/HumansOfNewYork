package com.ajanthan.perspective;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnArticleScrapCompleted {

    private RecyclerView rArticlesList;
    private ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    private ProgressBar progressBar;

    // URL Address
    private String url = "https://be1uo0y30d.execute-api.us-east-1.amazonaws.com/prod/data?limit=10&offset=";
    private int page;
    private boolean loadmore=false;

    ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_listview);

        final ActionBar abar = getSupportActionBar();
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_main);
        ArrayList<Article> articles = new ArrayList<Article>();

        page = 0;
        rArticlesList = (RecyclerView) findViewById(R.id.articleList);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        articleRecyclerViewAdapter = new ArticleRecyclerViewAdapter(this, articles);
        rArticlesList.setAdapter(articleRecyclerViewAdapter);
        rArticlesList.setLayoutManager(new LinearLayoutManager(this));

        new JsonRequestArticle(articleRecyclerViewAdapter, url + page,this).execute();
        page+=10;

        rArticlesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = articleRecyclerViewAdapter.getItemCount();
                int firstVisibleItem = articleRecyclerViewAdapter.getCurrent_postion();

                if(totalItemCount-2<firstVisibleItem&&loadmore==false){
                    loadmore=true;
                    new JsonRequestArticle(articleRecyclerViewAdapter, url + page,MainActivity.this).execute();
                    page+=10;
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
    protected void onPause() {
        super.onPause();
        ArrayList<Article> articles = articleRecyclerViewAdapter.getArticles();

        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(articles);
        prefsEditor.putString("articles", json);
        prefsEditor.commit();
    }
}
