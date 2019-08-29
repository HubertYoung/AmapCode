package com.alipay.mobile.nebula.provider;

import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.nebula.log.H5BehaviorLogConfig;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5MonitorLogConfig;
import java.util.Map;

public interface H5LogProvider {
    void autoClick(H5Event h5Event);

    void behaviorLog(H5LogData h5LogData, H5BehaviorLogConfig h5BehaviorLogConfig);

    @Deprecated
    void exceptionLog(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9);

    void h5BehaviorLogger(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, int i, String str12, String str13);

    void h5RemoteLogClickLogger(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, int i, String str11, String str12);

    boolean isPageStarted(Object obj);

    @Deprecated
    void log(String str, String str2, String str3, String str4, String str5);

    @Deprecated
    void log(String str, String str2, String str3, String str4, String str5, String str6);

    void logAutoBehavorPageEnd(String str, String str2, Object obj, String str3, Map<String, String> map);

    void logAutoBehavorPageStart(String str, Object obj);

    void logAutoBehavorPageStart(String str, Object obj, boolean z);

    void logTinyTrackerEnd(Object obj, String str, String str2, Map<String, String> map);

    void logTinyTrackerStart(Object obj, String str);

    @Deprecated
    void logV2(String str, String str2, String str3, String str4, String str5, String str6);

    @Deprecated
    void logV2(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    void monitorLog(H5LogData h5LogData, H5MonitorLogConfig h5MonitorLogConfig);

    void mtBizReport(String str, String str2, String str3, Map<String, String> map);

    @Deprecated
    void performanceLogger(String str, String str2, String str3, String str4, String str5, String str6, String str7);

    void upload();
}
