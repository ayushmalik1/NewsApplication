package com.example.newsapplication.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapplication.R;
import com.example.newsapplication.listeners.ButtonClickListener;
import com.example.newsapplication.model.News;
import com.example.newsapplication.utils.ImageUtils;

import java.util.ArrayList;

public class NewsPagerAdapter extends PagerAdapter {
    private final Context context;
    private final ArrayList<News> newsList;
    private final ButtonClickListener listener;
    private News news;

    public NewsPagerAdapter(Context context, ArrayList<News> newsList, News newsObject, ButtonClickListener buttonClickListener) {
        this.context = context;
        this.newsList = newsList;
        this.news = newsObject;
        this.listener = buttonClickListener;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (ConstraintLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.fragment_news_detail, container, false);

        TextView title = itemView.findViewById(R.id.tv_news_title);
        TextView description = itemView.findViewById(R.id.tv_description);
        ImageView imageView = itemView.findViewById(R.id.iv_news);
        Button readMore = itemView.findViewById(R.id.btn_read_more);
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClicked(news.getUrl());
            }
        });

        if (news != null) {
            ImageUtils.setBackgroundImage(imageView, news.getUrlToImage());
            description.setText(news.getContent());
            title.setText(news.getTitle());

        }

        ((ViewPager) container).addView(itemView);

        return itemView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((ConstraintLayout) object);
    }

    @Override
    public float getPageWidth(int position) {
        return super.getPageWidth(position);
    }

    public void setNewsObject(News news) {
        this.news = news;
    }
}
