package com.example.greatinternetofthings.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.datastructor.BluetoothItem;
import com.example.greatinternetofthings.interfaces.OnRLItemClicklistener;

import java.util.List;

public class ShowDeviceAdpater extends RecyclerView.Adapter<ShowDeviceAdpater.ViewHolder> {

    OnRLItemClicklistener listener;
    List<BluetoothItem> items;

    public ShowDeviceAdpater(List<BluetoothItem> items) {
        this.items = items;
    }

    public void setOnItemClickListener(OnRLItemClicklistener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bluetooth_device, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        BluetoothItem currentItem = items.get(position);
        int iconId;
        Log.d("TAG","Device type: ---"+currentItem.getType());
        switch (currentItem.getType()) {
            case 0:
                iconId = R.drawable.ic_phone_android_black_24dp;
                break;
            case 1:
                iconId = R.drawable.ic_laptop_windows_black_24dp;
                break;
            default:
                iconId = R.drawable.ic_phone_android_black_24dp;
        }
        holder.ivDeviceIcon.setImageResource(iconId);
        holder.tvDeviceName.setText(currentItem.getName());
        holder.tvDeviceAddress.setText(currentItem.getAddress());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    listener.OnClick(position);
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivDeviceIcon;
        TextView tvDeviceName;
        TextView tvDeviceAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDeviceIcon = itemView.findViewById(R.id.iv_device_icon);
            tvDeviceName = itemView.findViewById(R.id.tv_device_name);
            tvDeviceAddress = itemView.findViewById(R.id.tv_device_address);
        }
    }
}
