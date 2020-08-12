package com.example.greatinternetofthings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.constant.ConstantAssemble;

import java.util.List;
import java.util.zip.Inflater;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder> {

    public ArticlesAdapter() {
    }

    @Override
    public int getItemCount() {
        return ConstantAssemble.articleImagePath.length;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivCover.setImageResource(ConstantAssemble.articleImagePath[position]);
        holder.tvTitle.setText(ConstantAssemble.articleTitles[position]);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivCover;
        TextView tvTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ivCover = itemView.findViewById(R.id.iv_cover);
            this.tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
}
