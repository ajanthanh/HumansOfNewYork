package com.ajanthan.webscrappertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ajanthan on 16-02-02.
 */
public class RequestArticle extends AsyncTask<Void, Void, ArrayList<Article>> {
    private ArrayList<Article> articles = new ArrayList<Article>();
    private ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    private String url;
    private OnArticleScrapCompleted listener;

    public RequestArticle(ArticleRecyclerViewAdapter articleRecyclerViewAdapter, String url,OnArticleScrapCompleted listener){
        this.articleRecyclerViewAdapter = articleRecyclerViewAdapter;
        this.url=url;
        this.listener=listener;
        Log.e("RequestedPages",url);
    }

    @Override
    protected ArrayList<Article> doInBackground(Void... params) {
        try {
            String desc,date;
            Bitmap bitmap;
            Document document = Jsoup.connect(url).get();
            Elements description=document.select("a[class=fancybox]");
            for(int i=0;i<description.size();i++) {
                // Connect to the web site
                // Locate the content attribute

                // Using Elements to get the Meta data
                description = document.select("a[class=fancybox]");

                desc = Jsoup.parse(description.get(i).attr("title")).text();
                description = document.select("a[class=fancybox] img[src]");
                String img = description.get(i).attr("src");
                InputStream input = new java.net.URL(img).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

                description = document.select("div[id=month]");
                date = description.get(i).text();
                articles.add(new Article("",date,desc,img));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return articles;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> articles) {
        super.onPostExecute(articles);
        articleRecyclerViewAdapter.update(articles);
        listener.onTaskCompleted();
    }
}

