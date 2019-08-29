package com.alibaba.analytics.core.selfmonitor.exception;

import com.alibaba.analytics.core.Variables;
import com.alibaba.analytics.utils.AppInfoUtil;
import com.alibaba.analytics.utils.StringUtils;
import com.alibaba.appmonitor.delegate.SdkMeta;
import com.alibaba.appmonitor.event.EventType;
import com.alibaba.appmonitor.event.UTEvent;
import com.alibaba.appmonitor.pool.BalancedPool;
import com.alibaba.appmonitor.pool.ReuseJSONArray;
import com.alibaba.appmonitor.pool.ReuseJSONObject;
import com.alibaba.appmonitor.util.UTUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ExceptionEventBuilder {
    private static final String AP_MODULE = "APPMONITOR";
    private static final String AP_MONITOR_POINT = "sdk-exception";
    private static final String UT_COMMON_MONITOR_POINT = "ut-common-exception";
    private static final String UT_MONITOR_POINT = "ut-exception";

    public enum ExceptionType {
        UT,
        AP,
        COMMON
    }

    public static void log(ExceptionType exceptionType, Throwable th) {
        if (th != null) {
            try {
                UTEvent uTEvent = (UTEvent) BalancedPool.getInstance().poll(UTEvent.class, new Object[0]);
                uTEvent.eventId = EventType.ALARM.getEventId();
                HashMap hashMap = new HashMap();
                hashMap.put("meta", SdkMeta.getSDKMetaData());
                ReuseJSONArray reuseJSONArray = (ReuseJSONArray) BalancedPool.getInstance().poll(ReuseJSONArray.class, new Object[0]);
                reuseJSONArray.add(simulateDumpAlarmEvent(exceptionType, th));
                hashMap.put("data", reuseJSONArray);
                uTEvent.args.put(EventType.ALARM.getAggregateEventArgsKey(), JSON.toJSONString(hashMap));
                uTEvent.arg1 = AP_MODULE;
                uTEvent.arg2 = getPoint(exceptionType);
                UTUtil.sendAppException(uTEvent);
                BalancedPool.getInstance().offer(reuseJSONArray);
            } catch (Throwable th2) {
                th2.printStackTrace();
            }
        }
    }

    private static String getPoint(ExceptionType exceptionType) {
        if (ExceptionType.UT == exceptionType) {
            return UT_MONITOR_POINT;
        }
        return ExceptionType.COMMON == exceptionType ? UT_COMMON_MONITOR_POINT : AP_MONITOR_POINT;
    }

    private static JSONObject simulateDumpAlarmEvent(ExceptionType exceptionType, Throwable th) throws IOException {
        JSONObject jSONObject = (JSONObject) BalancedPool.getInstance().poll(ReuseJSONObject.class, new Object[0]);
        if (Variables.getInstance().getContext() != null) {
            jSONObject.put((String) "pname", (Object) AppInfoUtil.getCurProcessName(Variables.getInstance().getContext()));
        }
        jSONObject.put((String) "page", (Object) AP_MODULE);
        jSONObject.put((String) "monitorPoint", (Object) getPoint(exceptionType));
        jSONObject.put((String) "arg", (Object) th.getClass().getSimpleName());
        jSONObject.put((String) "successCount", (Object) Integer.valueOf(0));
        jSONObject.put((String) "failCount", (Object) Integer.valueOf(1));
        ArrayList arrayList = new ArrayList();
        String errorMsg = getErrorMsg(th);
        if (errorMsg != null) {
            JSONObject jSONObject2 = (JSONObject) BalancedPool.getInstance().poll(ReuseJSONObject.class, new Object[0]);
            jSONObject2.put((String) "errorCode", (Object) errorMsg);
            jSONObject2.put((String) "errorCount", (Object) Integer.valueOf(1));
            arrayList.add(jSONObject2);
        }
        jSONObject.put((String) "errors", (Object) arrayList);
        return jSONObject;
    }

    private static String getErrorMsg(Throwable th) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(th.getClass().getName());
        StackTraceElement[] stackTrace = th.getStackTrace();
        if (stackTrace != null) {
            for (StackTraceElement stackTraceElement : stackTrace) {
                sb.append(stackTraceElement.toString());
            }
        }
        String sb2 = sb.toString();
        return StringUtils.isBlank(sb2) ? th.toString() : sb2;
    }
}
