package com.example.newsapplication.roomdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.newsapplication.model.News;

@Database(entities = {News.class}, version = 3, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static NewsDatabase instance;
    private static String DB_NAME = "db_task";

    public static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
        }
        return instance;

    }

    public abstract DaoAccess daoAccess();
}