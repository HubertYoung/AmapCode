package com.alipay.tianyan.mobilesdk;

import com.alipay.mobile.common.logging.api.http.BaseHttpClient;
import com.alipay.mobile.common.logging.api.monitor.BatteryID;
import com.alipay.mobile.common.logging.api.monitor.BatteryModel;
import com.alipay.mobile.common.logging.api.monitor.DataflowID;
import com.alipay.mobile.common.logging.api.monitor.DataflowModel;

public class TianyanLoggingDelegator {

    public interface CommonSimpleDelegate {
        void acceptTimeTicksMadly();

        String getBundleByClass(String str);
    }

    public interface ConfigServiceDelegate {
        String getConfigValueByKey(String str, String str2);
    }

    public interface LoggingHttpClientGetter {
        BaseHttpClient getHttpClient();
    }

    public interface MonitorContextDelegate {
        boolean isPowerConsumeAccept(BatteryID batteryID, String str);

        boolean isTraficConsumeAccept(DataflowID dataflowID, String str);

        void notePowerConsume(BatteryModel batteryModel);

        void noteTraficConsume(DataflowModel dataflowModel);
    }

    public static boolean putCommonSimpleDelegate(CommonSimpleDelegate delegate) {
        return TianyanLoggingDispatcher.putCommonSimpleDelegate(delegate);
    }

    public static boolean removeCommonSimpleDelegate(CommonSimpleDelegate delegate) {
        return TianyanLoggingDispatcher.removeCommonSimpleDelegate(delegate);
    }

    public static boolean putConfigServiceDelegate(ConfigServiceDelegate delegate) {
        return TianyanLoggingDispatcher.putConfigServiceDelegate(delegate);
    }

    public static boolean removeConfigServiceDelegate(ConfigServiceDelegate delegate) {
        return TianyanLoggingDispatcher.removeConfigServiceDelegate(delegate);
    }

    public static boolean putMonitorContextDelegate(MonitorContextDelegate delegate) {
        return TianyanLoggingDispatcher.putMonitorContextDelegate(delegate);
    }

    public static boolean removeMonitorContextDelegate(MonitorContextDelegate delegate) {
        return TianyanLoggingDispatcher.removeMonitorContextDelegate(delegate);
    }

    public static void setMonitorBackground(boolean isBackground) {
        TianyanLoggingDispatcher.sIsMonitorBackground = isBackground;
    }

    public static void setFrameworkBackground(boolean isBackground) {
        TianyanLoggingDispatcher.sIsFrameworkBackground = isBackground;
    }

    public static void setStrictBackground(boolean isBackground) {
        TianyanLoggingDispatcher.sIsStrictBackground = isBackground;
    }

    public static void setRelaxedBackground(boolean isBackground) {
        TianyanLoggingDispatcher.sIsRelaxedBackground = isBackground;
    }
}
