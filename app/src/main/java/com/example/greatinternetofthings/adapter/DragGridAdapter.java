package com.example.greatinternetofthings.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.constant.NativeResource;
import com.example.greatinternetofthings.datastructor.AgricultureTypeItem;
import com.example.greatinternetofthings.interfaces.OnRLItemClicklistener;

import java.util.ArrayList;
import java.util.List;

public class DragGridAdapter extends RecyclerView.Adapter<DragGridAdapter.ViewHolder>{

    OnRLItemClicklistener listener;
    List<AgricultureTypeItem> gridMenus;
    int changedPosition;
    boolean isMoving = false;

    public DragGridAdapter(List<AgricultureTypeItem> data) {
        gridMenus=data;
    }

    public void setOnItemClickListener(OnRLItemClicklistener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return gridMenus.size();
    }

    public void ExchangePosition(int originalPosition, int currentPosition, boolean isMove) {
        Log.d("TAG","do exchange");
        AgricultureTypeItem original = gridMenus.get(originalPosition);
        gridMenus.remove(originalPosition);
        gridMenus.add(currentPosition, original);
        changedPosition = currentPosition;
        isMoving = isMove;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_drag_grid, null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        AgricultureTypeItem currentItem = gridMenus.get(position);
        holder.ivItemIcon.setImageResource(currentItem.getImgResource());
        holder.tvName.setText(currentItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.OnClick(position);
                }
            }
        });
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
