package com.example.newsapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.newsapplication.model.NewsModel;
import com.example.newsapplication.network.GetService;
import com.example.newsapplication.network.RetrofitInstance;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabsViewModel extends ViewModel {
    private final String API_KEY = "d836adbcccda44c48556b6ceef532463";
    private final String path = "top-headlines";
    private MutableLiveData<NewsModel> model = new MutableLiveData<>();

    public void getNewsFromApi(String category, int pageSize, int page) {
        HashMap<String, String> queryMap = new HashMap<>();
        queryMap.put("pageSize", String.valueOf(pageSize));
        queryMap.put("page", String.valueOf(page));
        queryMap.put("country", "in");
        queryMap.put("category", category.toLowerCase());
        queryMap.put("apiKey", API_KEY);
        GetService service = RetrofitInstance.getRetrofitInstance().create(GetService.class);

        Call<NewsModel> call = service.getNews(path, queryMap);
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                Log.d("Response", "" + response);
                model.setValue(response.body());

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {

            }
        });
    }

    public LiveData<NewsModel> getNewsData() {
        return model;
    }
}
