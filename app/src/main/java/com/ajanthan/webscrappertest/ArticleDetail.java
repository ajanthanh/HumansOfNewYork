package com.ajanthan.webscrappertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;

/**
 * Created by ajanthan on 16-02-03.
 */
public class ArticleDetail extends AppCompatActivity {

    private static String articleKey = "article";

    private TextView tvTitle;
    private TextView tvDate;
    private TextView tvBody;
    private ImageView ivImage;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 11) {
            requestFeature();
        }
        supportRequestWindowFeature(WindowCompat.FEATURE_ACTION_BAR);
        setContentView(R.layout.article_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvDate = (TextView) findViewById(R.id.date);
        tvBody = (TextView) findViewById(R.id.body);
        ivImage = (ImageView) findViewById(R.id.img);

        article = (Article)getIntent().getSerializableExtra(articleKey);
        tvDate.setText(article.getDate());
        tvBody.setText(article.getBody());
        Picasso.with(this)
                .load(article.getImgUrl())
                .into(ivImage);
    }

    private void requestFeature() {
        try {
            Field fieldImpl = ActionBarActivity.class.getDeclaredField("mImpl");
            fieldImpl.setAccessible(true);
            Object impl = fieldImpl.get(this);

            Class<?> cls = Class.forName("android.support.v7.app.ActionBarActivityDelegate");

            Field fieldHasActionBar = cls.getDeclaredField("mHasActionBar");
            fieldHasActionBar.setAccessible(true);
            fieldHasActionBar.setBoolean(impl, true);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
