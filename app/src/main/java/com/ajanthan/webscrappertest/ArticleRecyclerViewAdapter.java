package com.ajanthan.webscrappertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-26.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder> implements OnImageDownloadCompleted {

    private LayoutInflater mInflate;
    private ArrayList<ArticleExtended> articles = new ArrayList<ArticleExtended>();
    private Context mContext;
    private CountDownTimer alarmToneTimer;
    private int current_postion;


    public ArticleRecyclerViewAdapter(Context context, ArrayList<Article> articles) {
        mInflate = LayoutInflater.from(context);
        if (articles == null) {
            this.articles = new ArrayList<ArticleExtended>();     //TODO handle null articles
        } else {
            Bitmap defaultImg = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
            for(int i=0;i<articles.size();i++){
                this.articles.add(new ArticleExtended(articles.get(i),defaultImg));
                new DownloadArticleImage(articles.get(i).getImgUrl(),this,i).execute();
            }
        }
        mContext = context;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.article_card, parent, false);
        ArticleViewHolder holder = new ArticleViewHolder(view, mContext);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.tvArticleDescription.setText(articles.get(position).getBody());
        holder.tvDate.setText(articles.get(position).getDate());
        holder.ivImage.setImageBitmap(articles.get(position).getImg());
        current_postion = position;
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
    }

    public void update(ArrayList<Article> addedArticles) {
        for(int i=0;i<addedArticles.size();i++){
            Bitmap defaultImg = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
            articles.add(new ArticleExtended(addedArticles.get(i),defaultImg));
            new DownloadArticleImage(articles.get(articles.size()-1).getImgUrl(),this,articles.size()-1).execute();
        }
        notifyDataSetChanged();
    }

    public int getCurrent_postion() {
        return current_postion;
    }

    @Override
    public void onTaskCompleted(Bitmap img, int position) {
        articles.get(position).setImg(img);
        Log.e("CallBack", String.valueOf(position));
        notifyDataSetChanged();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvArticleDescription;
        private TextView tvDate;
        private ImageView ivImage;
        private Context context;
        private SharedPreferences prefs;

        public ArticleViewHolder(View itemView, Context context) {
            super(itemView);
            tvArticleDescription = (TextView) itemView.findViewById(R.id.description);
            tvDate = (TextView) itemView.findViewById(R.id.date);
            ivImage = (ImageView) itemView.findViewById(R.id.logo);
            this.context = context;
            prefs = context.getSharedPreferences("article", Context.MODE_PRIVATE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, ArticleDetail.class);
            i.putExtra("article", articles.get(this.getPosition()).getArticle());
            context.startActivity(i);
        }
    }
}
