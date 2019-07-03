package com.example.newsapplication.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.newsapplication.databinding.LayoutNewsItemBinding;
import com.example.newsapplication.listeners.NewsItemClickListener;
import com.example.newsapplication.model.News;
import com.example.newsapplication.utils.ImageUtils;

public class NewsItemViewHolder extends RecyclerView.ViewHolder {
    public LayoutNewsItemBinding binding;

    public NewsItemViewHolder(LayoutNewsItemBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void onBind(final News item, final NewsItemClickListener listener) {
        ImageUtils.setBackgroundImage(binding.ivNewsItem, item.getUrlToImage());
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onNewsItemClicked(getAdapterPosition());
            }
        });
    }
}
