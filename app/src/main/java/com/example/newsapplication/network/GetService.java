package com.example.newsapplication.network;

import com.example.newsapplication.model.NewsModel;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface GetService {


    @GET
    Call<NewsModel> getNews(@Url String path, @QueryMap HashMap<String,String> queryMap);
}
