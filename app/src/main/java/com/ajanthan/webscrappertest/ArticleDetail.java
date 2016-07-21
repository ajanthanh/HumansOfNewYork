package com.ajanthan.webscrappertest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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
        setContentView(R.layout.article_detail);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_article);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvTitle = (TextView) findViewById(R.id.title);
        tvDate = (TextView) findViewById(R.id.date);
        tvBody = (TextView) findViewById(R.id.body);
        ivImage = (ImageView) findViewById(R.id.img);

        article = (Article)getIntent().getSerializableExtra(articleKey);
        tvTitle.setText(article.getBody());
        tvDate.setText(article.getDate()+" | Humans Of New York");
        tvBody.setText(article.getBody());
        Picasso.with(this)
                .load(article.getImgUrl())
                .into(ivImage);
    }
}
