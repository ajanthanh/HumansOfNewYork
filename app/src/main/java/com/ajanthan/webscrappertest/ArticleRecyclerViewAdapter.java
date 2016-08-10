package com.ajanthan.webscrappertest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ajanthan on 15-11-26.
 */
public class ArticleRecyclerViewAdapter extends RecyclerView.Adapter<ArticleRecyclerViewAdapter.ArticleViewHolder> {

    private LayoutInflater mInflate;
    private ArticlesModel articlesModel;
    private ArrayList<Article> articles;
    private Context mContext;
    private int current_postion;


    public ArticleRecyclerViewAdapter(Context context) {
        mInflate = LayoutInflater.from(context);
        articlesModel = ArticlesModel.getInstance();
        this.articles= articlesModel.getArticles();
        mContext = context;
        Log.e("pokemon", "SHA BANG1");
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
        holder.tbFavourite.setChecked(articles.get(position).getIsFavourite());
        Picasso.with(mContext)
                .load(articles.get(position).getImgUrl())
                .into(holder.ivImage);
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

    public void update() {
        articles = articlesModel.getArticles();
        notifyDataSetChanged();

        Log.e("pokemon",articles.size()+"");
    }

    public int getCurrent_postion() {
        return current_postion;
    }

    public ArrayList<Article> getArticles(){
        return articles;
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvArticleDescription;
        private TextView tvDate;
        private ImageView ivImage;
        private ToggleButton tbFavourite;
        private Context context;
        private SharedPreferences prefs;

        public ArticleViewHolder(View itemView, Context context) {
            super(itemView);
            tvArticleDescription = (TextView) itemView.findViewById(R.id.description);
            tvDate = (TextView) itemView.findViewById(R.id.date);
            ivImage = (ImageView) itemView.findViewById(R.id.logo);
            tbFavourite = (ToggleButton) itemView.findViewById(R.id.favouriteButton);
            this.context = context;
            prefs = context.getSharedPreferences("article", Context.MODE_PRIVATE);
            itemView.setOnClickListener(this);

            tbFavourite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    articles.get(getLayoutPosition()).setIsFavourite(tbFavourite.isChecked());
                }
            });
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(context, ArticleDetail.class);
            i.putExtra("article", getLayoutPosition());
            context.startActivity(i);
        }
    }
}
