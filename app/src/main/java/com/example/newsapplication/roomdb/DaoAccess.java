package com.example.newsapplication.roomdb;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.newsapplication.model.News;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllNews(List<News> newsList);


    @Query("SELECT * FROM News")
    LiveData<List<News>> fetchAllNews();


    @Query("SELECT * FROM News WHERE id =:newsId")
    LiveData<News> getNewsById(int newsId);

    @Query("SELECT * FROM News WHERE category =:category")
    LiveData<List<News>> getNewsByCategory(String category);

    @Query("DELETE FROM News")
    public void deleteTable();

}