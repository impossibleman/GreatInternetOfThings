package com.example.greatinternetofthings.datastructor;

import android.graphics.DashPathEffect;
import android.graphics.Typeface;

import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.model.GradientColor;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.List;

public class StatisticsDetail implements IBarDataSet {

    int id;
    float recordValue;
    int recordDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(float recordValue) {
        this.recordValue = recordValue;
    }

    public int getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(int recordDate) {
        this.recordDate = recordDate;
    }

    @Override
    public boolean isStacked() {
        return false;
    }

    @Override
    public int getStackSize() {
        return 0;
    }

    @Override
    public int getBarShadowColor() {
        return 0;
    }

    @Override
    public float getBarBorderWidth() {
        return 0;
    }

    @Override
    public int getBarBorderColor() {
        return 0;
    }

    @Override
    public int getHighLightAlpha() {
        return 0;
    }

    @Override
    public String[] getStackLabels() {
        return new String[0];
    }

    @Override
    public int getHighLightColor() {
        return 0;
    }

    @Override
    public float getYMin() {
        return 0;
    }

    @Override
    public float getYMax() {
        return 0;
    }

    @Override
    public float getXMin() {
        return 0;
    }

    @Override
    public float getXMax() {
        return 0;
    }

    @Override
    public int getEntryCount() {
        return 0;
    }

    @Override
    public void calcMinMax() {

    }

    @Override
    public void calcMinMaxY(float fromX, float toX) {

    }

    @Override
    public BarEntry getEntryForXValue(float xValue, float closestToY, DataSet.Rounding rounding) {
        return null;
    }

    @Override
    public BarEntry getEntryForXValue(float xValue, float closestToY) {
        return null;
    }

    @Override
    public List<BarEntry> getEntriesForXValue(float xValue) {
        return null;
    }

    @Override
    public BarEntry getEntryForIndex(int index) {
        return null;
    }

    @Override
    public int getEntryIndex(float xValue, float closestToY, DataSet.Rounding rounding) {
        return 0;
    }

    @Override
    public int getEntryIndex(BarEntry e) {
        return 0;
    }

    @Override
    public int getIndexInEntries(int xIndex) {
        return 0;
    }

    @Override
    public boolean addEntry(BarEntry e) {
        return false;
    }

    @Override
    public void addEntryOrdered(BarEntry e) {

    }

    @Override
    public boolean removeFirst() {
        return false;
    }

    @Override
    public boolean removeLast() {
        return false;
    }

    @Override
    public boolean removeEntry(BarEntry e) {
        return false;
    }

    @Override
    public boolean removeEntryByXValue(float xValue) {
        return false;
    }

    @Override
    public boolean removeEntry(int index) {
        return false;
    }

    @Override
    public boolean contains(BarEntry entry) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public String getLabel() {
        return null;
    }

    @Override
    public void setLabel(String label) {

    }

    @Override
    public YAxis.AxisDependency getAxisDependency() {
        return null;
    }

    @Override
    public void setAxisDependency(YAxis.AxisDependency dependency) {

    }

    @Override
    public List<Integer> getColors() {
        return null;
    }

    @Override
    public int getColor() {
        return 0;
    }

    @Override
    public GradientColor getGradientColor() {
        return null;
    }

    @Override
    public List<GradientColor> getGradientColors() {
        return null;
    }

    @Override
    public GradientColor getGradientColor(int index) {
        return null;
    }

    @Override
    public int getColor(int index) {
        return 0;
    }

    @Override
    public boolean isHighlightEnabled() {
        return false;
    }

    @Override
    public void setHighlightEnabled(boolean enabled) {

    }

    @Override
    public void setValueFormatter(ValueFormatter f) {

    }

    @Override
    public ValueFormatter getValueFormatter() {
        return null;
    }

    @Override
    public boolean needsFormatter() {
        return false;
    }

    @Override
    public void setValueTextColor(int color) {

    }

    @Override
    public void setValueTextColors(List<Integer> colors) {

    }

    @Override
    public void setValueTypeface(Typeface tf) {

    }

    @Override
    public void setValueTextSize(float size) {

    }

    @Override
    public int getValueTextColor() {
        return 0;
    }

    @Override
    public int getValueTextColor(int index) {
        return 0;
    }

    @Override
    public Typeface getValueTypeface() {
        return null;
    }

    @Override
    public float getValueTextSize() {
        return 0;
    }

    @Override
    public Legend.LegendForm getForm() {
        return null;
    }

    @Override
    public float getFormSize() {
        return 0;
    }

    @Override
    public float getFormLineWidth() {
        return 0;
    }

    @Override
    public DashPathEffect getFormLineDashEffect() {
        return null;
    }

    @Override
    public void setDrawValues(boolean enabled) {

    }

    @Override
    public boolean isDrawValuesEnabled() {
        return false;
    }

    @Override
    public void setDrawIcons(boolean enabled) {

    }

    @Override
    public boolean isDrawIconsEnabled() {
        return false;
    }

    @Override
    public void setIconsOffset(MPPointF offset) {

    }

    @Override
    public MPPointF getIconsOffset() {
        return null;
    }

    @Override
    public void setVisible(boolean visible) {

    }

    @Override
    public boolean isVisible() {
        return false;
    }
}
