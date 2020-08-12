package com.example.greatinternetofthings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.commontools.CommonTools;
import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.example.greatinternetofthings.datastructor.MonitorDetail;

import java.util.List;

public class MonitorItemAdapter extends ArrayAdapter {

    List<MonitorDetail> items;

    public MonitorItemAdapter(Context context,int resId,List<MonitorDetail> items){
        super(context,resId);
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.item_monitor,null);
            viewHolder.tvName=convertView.findViewById(R.id.tv_name);
            viewHolder.tvValue=convertView.findViewById(R.id.tv_value);
            viewHolder.tvState=convertView.findViewById(R.id.tv_state);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        MonitorDetail currentDetail=items.get(position);
        float stateValue=currentDetail.getValue();
        viewHolder.tvName.setText(currentDetail.getName());
        viewHolder.tvValue.setText(stateValue+"");
        viewHolder.tvState.setText(currentDetail.getState());
        if(position==0){
            int stateCheckResult=CommonTools.CheckMonitorValueState(position,stateValue);
            if(stateCheckResult== ConstantAssemble.MONITOR_STATE_NORMAL){
                viewHolder.tvState.setTextColor(getContext().getResources().getColor(R.color.green));
                viewHolder.tvState.setText("正常");
            }
            else if(stateCheckResult== ConstantAssemble.MONITOR_STATE_WARNING){
                viewHolder.tvState.setTextColor(getContext().getResources().getColor(R.color.yellow));
                viewHolder.tvState.setText("警告");
            }
            else{
                viewHolder.tvState.setTextColor(getContext().getResources().getColor(R.color.red));
                viewHolder.tvState.setText("危险");
            }
        }
        return convertView;
    }

    class ViewHolder{
        TextView tvName;
        TextView tvValue;
        TextView tvState;
    }
}
