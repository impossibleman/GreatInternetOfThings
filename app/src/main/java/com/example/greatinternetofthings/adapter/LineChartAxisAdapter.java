package com.example.greatinternetofthings.adapter;

import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

public class LineChartAxisAdapter extends IndexAxisValueFormatter {

    int type;

    public LineChartAxisAdapter(int type){
        this.type=type;
    }

    @Override
    public String getFormattedValue(float value) {
        int position;
        String formattedValue;
        if(type==ConstantAssemble.AXIS_FORMATTER_X){
            position=(int)(value/10);
            if(position>=6||position<0){
                return "";
            }
            formattedValue=ConstantAssemble.lineXCoordinateValue[position];
        }
        else{
            position=(int)(value/10);
            if(position>=9||position<0){
                return "";
            }
            formattedValue=ConstantAssemble.lineYCoordinateValue[position];
        }
        return formattedValue;
    }
}
