package com.example.newsapplication.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapplication.MainActivity;
import com.example.newsapplication.R;
import com.example.newsapplication.adapters.NewsAdapter;
import com.example.newsapplication.listeners.NewsItemClickListener;
import com.example.newsapplication.listeners.PaginationScrollListener;
import com.example.newsapplication.model.News;
import com.example.newsapplication.model.NewsModel;
import com.example.newsapplication.roomdb.NewsDatabase;
import com.example.newsapplication.viewmodel.TabsViewModel;

import java.util.ArrayList;
import java.util.List;


public class TabsFragment extends Fragment implements NewsItemClickListener {
    private String mCategory;
    private TabsViewModel viewModel;
    private RecyclerView mRecyclerView;
    private NewsAdapter mNewsAdapter;
    private ArrayList<News> mNewsList = new ArrayList<>();
    private final int PAGE_SIZE = 10;
    private int page = 1;
    private boolean isLoading = false;
    private int totalItems;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tabs, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(TabsViewModel.class);
        if (getArguments() != null) {
            this.mCategory = getArguments().getString("category");
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        mNewsList.clear();
        isLoading = true;
        viewModel.getNewsData().observe(this, new Observer<NewsModel>() {
            @Override
            public void onChanged(@Nullable NewsModel newsModel) {

                if (newsModel != null) {
                    addNewsListToDb(newsModel.getArticles());
                    mNewsList.addAll(newsModel.getArticles());
                    mNewsAdapter.notifyDataSetChanged();
                    isLoading = false;
                    totalItems = newsModel.getTotalResults();
                }

            }
        });
        viewModel.getNewsFromApi(mCategory, PAGE_SIZE, page);
    }

    private void addNewsListToDb(final List<News> articles) {
        final NewsDatabase newsDatabase = NewsDatabase.getInstance(getContext());
        for (News news : articles) {
            news.setCategory(mCategory);
        }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                newsDatabase.daoAccess().insertAllNews(articles);
            }
        });

    }

    private void initView(View view) {
        mRecyclerView = view.findViewById(R.id.rv_news_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mNewsAdapter = new NewsAdapter(this.getContext(), mNewsList, this);
        mRecyclerView.setAdapter(mNewsAdapter);
        mRecyclerView.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                page++;
                isLoading = true;
                viewModel.getNewsFromApi(mCategory, PAGE_SIZE, page);

            }

            @Override
            public boolean isLastPage() {
                return mNewsList.size() == totalItems;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    @Override
    public void onNewsItemClicked(int id) {
        if (getActivity() != null)
            ((MainActivity) getActivity()).addDetailFragment(id, mCategory);
    }
}
