package com.ajanthan.webscrappertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ajanthan on 16-02-02.
 */
public class DownloadArticleImage extends AsyncTask<Void, Void, Void> {

    private String url;
    private OnImageDownloadCompleted listener;
    private Bitmap bitmap;
    private int position;

    public DownloadArticleImage(String url, OnImageDownloadCompleted listener, int postion) {
        this.url = url;
        this.listener = listener;
        this.position=postion;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            InputStream input = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;        //Why???
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listener.onTaskCompleted(bitmap,position);
    }
}

