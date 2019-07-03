package com.example.newsapplication.fragments;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapplication.MainActivity;
import com.example.newsapplication.R;
import com.example.newsapplication.adapters.NewsPagerAdapter;
import com.example.newsapplication.listeners.ButtonClickListener;
import com.example.newsapplication.model.News;
import com.example.newsapplication.roomdb.NewsDatabase;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class NewsPagerFragment extends Fragment implements ButtonClickListener {
    private int currentPage = 0;
    private String category;
    private int id;
    private ArrayList<News> mNewsList = new ArrayList<>();
    private NewsPagerAdapter adapter;
    private News mNewsObject;
    private ViewPager viewPager;
    private CircleIndicator indicator;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        adapter = new NewsPagerAdapter(getContext(), mNewsList, mNewsObject,this);
        viewPager.setAdapter(adapter);
        View backArrow = view.findViewById(R.id.back_arrow);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        indicator = view.findViewById(R.id.indicator);

        getNewsListFromDb();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                getNewsObjectFromDb(position);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void getNewsObjectFromDb(final int position) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(getContext());
        newsDatabase.daoAccess().getNewsById(mNewsList.get(position).getId()).observe(this, new Observer<News>() {
            @Override
            public void onChanged(@Nullable News news) {
                adapter.setNewsObject(news);
                adapter.notifyDataSetChanged();
                indicator.setViewPager(viewPager);
                viewPager.setCurrentItem(position);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void getNewsListFromDb() {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(getContext());
        newsDatabase.daoAccess().getNewsByCategory(category).observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(@Nullable List<News> newsList) {
                if (newsList != null) {
                    mNewsList.clear();
                    mNewsList.addAll(newsList);
                    getNewsObjectFromDb(id);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_news_viewpager, container, false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt("id");
            category = getArguments().getString("category");
        }
    }


    @Override
    public void onButtonClicked(String url) {
        ((MainActivity) getActivity()).loadWebView(url);
    }
}
