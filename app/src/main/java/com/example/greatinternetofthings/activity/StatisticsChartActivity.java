package com.example.greatinternetofthings.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.greatinternetofthings.R;
import com.example.greatinternetofthings.adapter.ColumnarChartAxisAdapter;
import com.example.greatinternetofthings.adapter.LineChartAxisAdapter;
import com.example.greatinternetofthings.constant.ConstantAssemble;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.List;

public class StatisticsChartActivity extends AppCompatActivity {

    Toolbar toolbar;
    BarChart bc_growth;
    PieChart pc_proportion;
    LineChart lc_trend;
    int chartType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_columnar_chart);
        toolbar = findViewById(R.id.toolbar);
        bc_growth = findViewById(R.id.ch_bar);
        pc_proportion = findViewById(R.id.ch_pie);
        lc_trend = findViewById(R.id.ch_line);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle("数据统计");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        Bundle bundle = getIntent().getExtras();
        chartType = bundle.getInt("charttype", ConstantAssemble.CHART_TYPE_COLUMNAR);
        InitChartSettings();
    }

    void InitChartSettings() {
        if (chartType == ConstantAssemble.CHART_TYPE_COLUMNAR) {
            bc_growth.setVisibility(View.VISIBLE);
            bc_growth.setMaxVisibleValueCount(30);
            Description description = new Description();
            description.setText("每月产量统计");
            bc_growth.setDescription(description);

            ColumnarChartAxisAdapter formatter = new ColumnarChartAxisAdapter(ConstantAssemble.AXIS_FORMATTER_X);
            XAxis xAxis = bc_growth.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(3.0f);
            xAxis.setLabelCount(7);
            xAxis.setAxisMaximum(100f);
            xAxis.setAxisMinimum(0f);
            xAxis.setValueFormatter(formatter);

            ColumnarChartAxisAdapter yFormatter = new ColumnarChartAxisAdapter(ConstantAssemble.AXIS_FORMATTER_Y);
            YAxis leftAxis = bc_growth.getAxisLeft();
            leftAxis.setLabelCount(10);
            leftAxis.setSpaceTop(50f);
            leftAxis.setValueFormatter(yFormatter);
            leftAxis.setAxisMaximum(100f);
            leftAxis.setAxisMinimum(0f);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
            bc_growth.getAxisRight().setEnabled(false);
        } else if (chartType == ConstantAssemble.CHART_TYPE_PIE) {
            pc_proportion.setVisibility(View.VISIBLE);
            Description description = new Description();
            description.setText("产量占比统计");
            pc_proportion.setDescription(description);
            pc_proportion.setDrawHoleEnabled(false);
            pc_proportion.setUsePercentValues(true);
            pc_proportion.setDrawEntryLabels(true);
        }
        else if(chartType == ConstantAssemble.CHART_TYPE_LINE){
            lc_trend.setVisibility(View.VISIBLE);
            Description description = new Description();
            description.setText("温度变化");
            lc_trend.setDescription(description);

            LineChartAxisAdapter formatter = new LineChartAxisAdapter(ConstantAssemble.AXIS_FORMATTER_X);
            XAxis xAxis = lc_trend.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setGranularity(3.0f);
            xAxis.setLabelCount(7);
            xAxis.setAxisMaximum(50f);
            xAxis.setAxisMinimum(0f);
            xAxis.setValueFormatter(formatter);

            LineChartAxisAdapter yFormatter = new LineChartAxisAdapter(ConstantAssemble.AXIS_FORMATTER_Y);
            YAxis leftAxis = lc_trend.getAxisLeft();
            leftAxis.setLabelCount(10);
            leftAxis.setSpaceTop(50f);
            leftAxis.setValueFormatter(yFormatter);
            leftAxis.setAxisMaximum(100f);
            leftAxis.setAxisMinimum(0f);
            leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        }
        CreateLocalData();
    }

    void CreateLocalData() {
        if (chartType == ConstantAssemble.CHART_TYPE_COLUMNAR) {
            List<BarEntry> entries = new ArrayList<>();
            entries.add(new BarEntry(1, 8));
            entries.add(new BarEntry(14, 14));
            entries.add(new BarEntry(27, 15));
            entries.add(new BarEntry(40, 41));
            entries.add(new BarEntry(53, 13));

            BarDataSet set;
            if (bc_growth.getData() != null && bc_growth.getData().getDataSetCount() > 0) {
                Log.d("TAG", "refresh data");
                set = (BarDataSet) bc_growth.getData().getDataSetByIndex(0);
                set.setValues(entries);
                set.setBarBorderColor(getResources().getColor(R.color.red));
                set.setBarBorderWidth(2f);
                bc_growth.getData().notifyDataChanged();
                bc_growth.notifyDataSetChanged();
            } else {
                Log.d("TAG", "create new data");
                set = new BarDataSet(entries, "产量统计");
                List<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set);
                BarData data = new BarData(dataSets);
                data.setValueTextSize(15f);
                data.setBarWidth(3f);
                bc_growth.setData(data);
            }
        } else if (chartType == ConstantAssemble.CHART_TYPE_PIE) {
List<PieEntry> pieEntries=new ArrayList<>();
            pieEntries.add(new PieEntry(15.6f,"玉米"));
            pieEntries.add(new PieEntry(25.5f,"小麦"));
            pieEntries.add(new PieEntry(14.2f,"苹果"));
            pieEntries.add(new PieEntry(11.8f,"葡萄"));

            PieDataSet dataSets=new PieDataSet(pieEntries,"产量分布");
            dataSets.setValueLineWidth(5);
            dataSets.setColors(getResources().getColor(R.color.black),getResources().getColor(R.color.lightenblue),getResources().getColor(R.color.blue),getResources().getColor(R.color.red));
            PieData data=new PieData(dataSets);
            data.setValueTextColor(getResources().getColor(R.color.black));
            pc_proportion.setData(data);
        }
        else if (chartType == ConstantAssemble.CHART_TYPE_LINE){
            List<Entry> lineEntries=new ArrayList<>();
            lineEntries.add(new Entry(2,15));
            lineEntries.add(new Entry(15,35));
            lineEntries.add(new Entry(28,55));
            lineEntries.add(new Entry(41,25));

            LineDataSet dataSets=new LineDataSet(lineEntries,"数值变化");
            LineData data=new LineData(dataSets);
            lc_trend.setData(data);
        }
    }
}
