package com.example.newsapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.newsapplication.fragments.HomeFragment;
import com.example.newsapplication.fragments.NewsPagerFragment;
import com.example.newsapplication.fragments.NewsWebFragment;
import com.example.newsapplication.roomdb.NewsDatabase;

public class MainActivity extends AppCompatActivity {

    private View toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NewsDatabase newsDatabase = NewsDatabase.getInstance(this);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                newsDatabase.daoAccess().deleteTable();
            }
        });

        Fragment fragment = HomeFragment.getInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.fragment_container, fragment).commit();
    }

    public void addDetailFragment(int id, String category) {
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.GONE);
        Fragment fragment = new NewsPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        bundle.putString("category", category);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        ft.add(R.id.fragment_container, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        toolbar.setVisibility(View.VISIBLE);

    }

    public void loadWebView(String url) {
        toolbar = findViewById(R.id.main_toolbar);
        toolbar.setVisibility(View.GONE);
        Fragment fragment = new NewsWebFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction().addToBackStack(null);
        ft.add(R.id.fragment_container, fragment).commit();
    }
}
