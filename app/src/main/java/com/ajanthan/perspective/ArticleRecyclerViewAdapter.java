package com.ajanthan.perspective;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
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
    private ArrayList<Article> articles = new ArrayList<Article>();
    private Context mContext;
    private int current_postion;

    public ArticleRecyclerViewAdapter(Context context, ArrayList<Article> articles) {
        mInflate = LayoutInflater.from(context);

        if (articles != null) {
            this.articles = articles;
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

    public void update(ArrayList<Article> addedArticles) {
        articles.addAll(addedArticles);
        notifyDataSetChanged();
    }

    public int getCurrent_postion() {
        return current_postion;
    }

    public ArrayList<Article> getArticles() {
        return articles;
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
            i.putExtra("article", articles.get(getLayoutPosition()));
            context.startActivity(i);
        }
    }
}