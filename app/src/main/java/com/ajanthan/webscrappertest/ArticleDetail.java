package com.ajanthan.webscrappertest;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.WindowCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
    private FloatingActionButton fabFavourite;

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
        fabFavourite = (FloatingActionButton) findViewById(R.id.favouriteButton);

        article = ArticlesModel.getInstance().getArticles().get((int)getIntent().getSerializableExtra(articleKey));
        tvDate.setText(article.getDate());
        tvBody.setText(article.getBody());
        Picasso.with(this)
                .load(article.getImgUrl())
                .into(ivImage);
        setFavouriteIcon();
        fabFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                article.setIsFavourite(!article.getIsFavourite());
                setFavouriteIcon();
            }
        });
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

    private void setFavouriteIcon(){
        //must be version greater than lollipop in order for the favourite icons to toggle as below
        if(article.getIsFavourite()){
            fabFavourite.setImageDrawable(getDrawable(R.drawable.ic_favorite_border_white_24dp));
        }else{
            fabFavourite.setImageDrawable(getDrawable(R.drawable.ic_favorite_white_24dp));
        }
    }
}
