package com.example.greatinternetofthings.adapter;

import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.Collection;

public class ColumnarChartAxisAdapter extends IndexAxisValueFormatter {

    int type;

    public ColumnarChartAxisAdapter(int type){
        this.type=type;
    }

    @Override
    public String getFormattedValue(float value) {
        int position=(int)(value/10);
        if(type==ConstantAssemble.AXIS_FORMATTER_X){
            if(position>=8||position<0){
                return "";
            }
            return ConstantAssemble.columnnarXCoordinateValue[position];
        }
        else{
            if(position>=6||position<0){
                return "";
            }
            return ConstantAssemble.columnarYCoordinateValue[position];
        }
    }
}
