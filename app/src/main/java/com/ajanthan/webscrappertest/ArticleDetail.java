package com.ajanthan.webscrappertest;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ajanthan on 16-02-03.
 */
public class ArticleDetail extends Activity implements OnImageDownloadCompleted {

    private static String articleKey = "article";

    private TextView tvBody;
    private ImageView ivImg;

    private ArticleExtended article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_detail);

        tvBody = (TextView) findViewById(R.id.body);
        ivImg = (ImageView) findViewById(R.id.img);

        Bitmap defaultImg = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        article = new ArticleExtended((Article) getIntent().getSerializableExtra(articleKey),defaultImg);
        tvBody.setText(article.getBody());
        ivImg.setImageBitmap(article.getImg());
        new DownloadArticleImage(article.getImgUrl(),this,0).execute();
    }

    @Override
    public void onTaskCompleted(Bitmap img, int position) {
        ivImg.setImageBitmap(img);
        Log.e("CallBack", "completed");
    }
}
