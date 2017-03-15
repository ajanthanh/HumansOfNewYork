package com.ajanthan.perspective;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ajanthan on 16-02-02.
 */
public class JsonRequestArticle extends AsyncTask<Void, Void, ArrayList<Article>> {
    private ArrayList<Article> articles = new ArrayList<Article>();
    private ArticleRecyclerViewAdapter articleRecyclerViewAdapter;
    private String url;
    private OnArticleScrapCompleted listener;

    public JsonRequestArticle(ArticleRecyclerViewAdapter articleRecyclerViewAdapter, String url, OnArticleScrapCompleted listener) {
        this.articleRecyclerViewAdapter = articleRecyclerViewAdapter;
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected ArrayList<Article> doInBackground(Void... params) {
        String result;
        URL url = null;
        try {
            url = new URL(this.url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            result=rd.readLine();
            rd.close();
            result = result.substring(1,result.length()-1);
            JSONObject jsonObject = new JSONObject(result);
            JSONArray posts = jsonObject.getJSONObject("response").getJSONArray("posts");
            for (int i = 0; i < posts.length(); i++) {
                JSONObject jo = posts.getJSONObject(i);
                String image = jo.getJSONArray("photos").getJSONObject(0).getJSONArray("alt_sizes").getJSONObject(0).getString("url");
                articles.add(new Article(jo.getString("slug"),
                        jo.getString("date"),
                        parseBody(jo.getString("caption")),
                        image));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
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

    private String parseBody(String body){
        //Todo find a better way to do this
        body = body.replace("<p>","");
        body = body.replace("</p>", "");
        body = body.replace("<br>","");
        body = body.replace("</br>","");
        body = body.replace("<br/>","");

        body = body.replace("&ldquo;","\"");
        body = body.replace("&rdquo;","\"");
        body = body.replace("&rsquo;","'");
        body = body.replace("(&frac12;)","'");
        
        //Bug fix: remove links surrounded by <a> tags in body
        body = body.replaceAll("<a.*?</a>", "");
        body = body.trim();
        Log.e("pokemon2",body);
        return body;
    }
}

