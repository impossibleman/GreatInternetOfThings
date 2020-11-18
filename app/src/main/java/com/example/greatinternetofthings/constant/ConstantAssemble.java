package com.example.greatinternetofthings.constant;

import android.Manifest;

import com.example.greatinternetofthings.R;

public class ConstantAssemble {
    public static int DRAG_MODE_MOVE = 0;
    public static int DRAG_MODE_NORMAL = 1;

    public static int SQL_OPERATE_STORE = 0;
    public static int SQL_OPERATE_GET = 1;

    public static String DATABASE_NAME_USUALLY_USE_ITEM = "usuallyuse";
    public static String TABLE_NAME_USUALLY_USE_ITEM = "usuallyuseitem";
    public static int DATABASE_VERSION = 1;

    public static int MONITOR_STATE_NORMAL =0;
    public static int MONITOR_STATE_WARNING = 1;
    public static int MONITOR_STATE_ALARM = 2;

    public static int AXIS_FORMATTER_X=0;
    public static int AXIS_FORMATTER_Y=1;

    public static int CHART_TYPE_COLUMNAR=0;
    public static int CHART_TYPE_PIE=1;
    public static int CHART_TYPE_LINE=2;

    public static int AUTO_TRUN_NET=0;
    public static int AUTO_TRUN_NATIVE=1;

    public static int ACTIVITY_REQUEST_CODE_BLUETEETH_CONNECT=0;

    public static int ACTIVITY_RESULT_CODE_SUCCESS=0;

    public static String[] itemNames = {
            "小麦",
            "玉米",
            "葡萄",
            "苹果"
    };

    public static String[] monitorType = {
            "soilTemperature",
            "waterContent",
            "saltContent",
            "soilPH",
            "airTemperature",
            "airHumidity",
            "atomsphericPressure",
            "illuminationStrength",
            "nutritionPercent",
            "nutritionWaterContent",
            "nutritionMicroelement"
    };

    public static String[] monitorTypeName={
      "土壤温度",
      "土壤湿度",
      "土壤盐度",
      "土壤PH",
      "空气温度",
      "空气湿度",
      "气压",
      "光照",
      "植物养分",
      "植物水分",
      "植物微量元素",
    };

    public static float[] monitorInitValue = {
            15.9f,
            32f,
            4.2f,
            7.6f,
            21f,
            16f,
            120f,
            45f,
            24.6f,
            65f,
            7.8f
    };

    public static String[] columnnarXCoordinateValue={
      "2018",
      "6月",
      "9月",
      "12月",
      "2019",
      "6月",
      "9月",
      "12月",
    };

    public static String[] columnarYCoordinateValue={
      "50吨",
      "100吨",
      "150吨",
      "200吨",
      "250吨",
      "300吨",
    };

    public static String[] lineXCoordinateValue={
            "6:00",
            "10:00",
            "14:00",
            "18:00",
            "22:00",
            "2:00",
    };

    public static String[] lineYCoordinateValue={
            "0",
            "5",
            "10",
            "15",
            "20",
            "25",
            "30",
            "35",
            "40",
    };

    public static int[] nativeImagePath={
            R.drawable.vegetables04,
            R.drawable.fruits01,
            R.drawable.meat01,
            R.drawable.vegetables01,
            R.drawable.vegetables04,
            R.drawable.fruits01,
    };

    public static int[] articleImagePath={
            R.drawable.carrot01,
            R.drawable.cauliflower01,
            R.drawable.chicken01,
            R.drawable.greens01,
    };

    public static String[] articleTitles={
            "关于葡萄的种植技巧",
            "关于土豆的培育技巧",
            "养殖业指南",
            "大型饲养场建立方案",
    };

    public static String[] permissions = {
            Manifest.permission.SYSTEM_ALERT_WINDOW
    };
}
