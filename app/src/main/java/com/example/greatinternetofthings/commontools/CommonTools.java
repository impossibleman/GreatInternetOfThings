package com.example.greatinternetofthings.commontools;

import com.example.greatinternetofthings.constant.ConstantAssemble;

public class CommonTools {

    public static int CheckMonitorValueState(int index, float currentValue) {
        if (index == 0) {
            if (currentValue < 5 || currentValue > 35) {
                return ConstantAssemble.MONITOR_STATE_ALARM;
            } else if (currentValue < 15 || currentValue > 30) {
                return ConstantAssemble.MONITOR_STATE_WARNING;
            }
        }
        return ConstantAssemble.MONITOR_STATE_NORMAL;
    }
}
