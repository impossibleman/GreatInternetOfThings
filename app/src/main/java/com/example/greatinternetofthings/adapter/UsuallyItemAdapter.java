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
import com.example.greatinternetofthings.datastructor.AgricultureTypeItem;

import java.util.List;

public class UsuallyItemAdapter extends RecyclerView.Adapter<UsuallyItemAdapter.ViewHolder> {

    OnItemClickListener listener;
    List<AgricultureTypeItem> usuallyItems;

    public UsuallyItemAdapter(List<AgricultureTypeItem> items){
        usuallyItems=items;
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return usuallyItems.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drag_grid,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.onClick(position);
                }
            }
        });
        AgricultureTypeItem item=usuallyItems.get(position);
        holder.ivItemIcon.setImageResource(item.getImgResource());
        holder.tvName.setText(item.getName());
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivItemIcon;
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivItemIcon = itemView.findViewById(R.id.iv_item_icon);
            tvName = itemView.findViewById(R.id.tv_name);
        }
    }
}
