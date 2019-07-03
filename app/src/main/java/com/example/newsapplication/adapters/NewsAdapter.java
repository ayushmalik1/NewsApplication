package com.example.newsapplication.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.newsapplication.R;
import com.example.newsapplication.databinding.LayoutNewsItemBinding;
import com.example.newsapplication.listeners.NewsItemClickListener;
import com.example.newsapplication.model.News;
import com.example.newsapplication.viewholders.NewsItemViewHolder;

import java.util.ArrayList;

public class NewsAdapter extends RecyclerView.Adapter<NewsItemViewHolder> {
    private final Context mContext;
    private final NewsItemClickListener listener;
    private ArrayList<News> mNewsList;

    public NewsAdapter(Context context, ArrayList<News> newsList, NewsItemClickListener listener) {
        this.mNewsList = newsList;
        this.mContext = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutNewsItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_news_item, viewGroup, false);
        return new NewsItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsItemViewHolder newsItemViewHolder, int i) {
        newsItemViewHolder.onBind(mNewsList.get(i), listener);
        newsItemViewHolder.binding.setNews(mNewsList.get(i));
    }

    @Override
    public int getItemCount() {
        return mNewsList != null ? mNewsList.size() : 0;
    }


}
