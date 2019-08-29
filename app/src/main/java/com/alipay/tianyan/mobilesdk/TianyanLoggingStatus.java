package com.alipay.tianyan.mobilesdk;

import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;

public class TianyanLoggingStatus {
    public static boolean isMonitorBackground() {
        return TianyanLoggingDispatcher.sIsMonitorBackground;
    }

    public static boolean isFrameworkBackground() {
        return TianyanLoggingDispatcher.sIsFrameworkBackground;
    }

    public static boolean isStrictBackground() {
        return TianyanLoggingDispatcher.sIsStrictBackground;
    }

    public static boolean isRelaxedBackground() {
        return TianyanLoggingDispatcher.sIsRelaxedBackground;
    }

    public static void acceptTimeTicksMadly() {
        TianyanLoggingDispatcher.acceptTimeTicksMadly();
    }

    public static String getBundleByClass(String className) {
        return TianyanLoggingDispatcher.getBundleByClass(className);
    }

    public static String getConfigValueByKey(String configKey, String defaultValue) {
        return TianyanLoggingDispatcher.getConfigValueByKey(configKey, defaultValue);
    }

    public static void notePowerConsume(BatteryModel batteryModel) {
        TianyanLoggingDispatcher.notePowerConsume(batteryModel);
    }

    public static boolean isPowerConsumeAccept(BatteryID type, String diagnose) {
        return TianyanLoggingDispatcher.isPowerConsumeAccept(type, diagnose);
    }

    public static void noteTraficConsume(DataflowModel dataflowModel) {
        TianyanLoggingDispatcher.noteTraficConsume(dataflowModel);
    }

    public static boolean isTraficConsumeAccept(DataflowID type, String url) {
        return TianyanLoggingDispatcher.isTraficConsumeAccept(type, url);
    }
}
