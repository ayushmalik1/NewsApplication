package com.example.newsapplication.adapters;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.newsapplication.fragments.TabsFragment;

public class TabsAdapter extends FragmentStatePagerAdapter {
    private String[] categories = {"Sports", "Science", "Health", "Technology"};

    public TabsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putString("category", categories[i]);
        TabsFragment fragment = new TabsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return categories[position];
    }
}
