package com.ut.mini.internal;

import com.alibaba.analytics.utils.ILogger;
import com.autonavi.minimap.ajx3.util.LogHelper;
import com.taobao.tlog.adapter.AdapterForTLog;
import com.taobao.wireless.security.sdk.securesignature.SecureSignatureDefine;
import java.util.HashMap;

public class LogAdapter implements ILogger {
    private boolean isNoClassDefFoundError = false;
    private HashMap<String, Integer> mTlogMap = new HashMap<>();

    public LogAdapter() {
        this.mTlogMap.put(SecureSignatureDefine.SG_KEY_SIGN_VERSION, Integer.valueOf(5));
        this.mTlogMap.put("D", Integer.valueOf(4));
        this.mTlogMap.put(LogHelper.DEFAULT_LEVEL, Integer.valueOf(3));
        this.mTlogMap.put("W", Integer.valueOf(2));
        this.mTlogMap.put("E", Integer.valueOf(1));
        this.mTlogMap.put("L", Integer.valueOf(0));
    }

    public boolean isValid() {
        if (this.isNoClassDefFoundError) {
            return false;
        }
        try {
            return AdapterForTLog.isValid();
        } catch (Throwable unused) {
            this.isNoClassDefFoundError = true;
            return false;
        }
    }

    public int getLogLevel() {
        return this.mTlogMap.get(AdapterForTLog.getLogLevel("Analytics")).intValue();
    }

    public void logd(String str, String str2) {
        AdapterForTLog.logd(str, str2);
    }

    public void logw(String str, String str2) {
        AdapterForTLog.logw(str, str2);
    }

    public void logw(String str, String str2, Throwable th) {
        AdapterForTLog.logw(str, str2, th);
    }

    public void logi(String str, String str2) {
        AdapterForTLog.logi(str, str2);
    }

    public void loge(String str, String str2) {
        AdapterForTLog.loge(str, str2);
    }

    public void loge(String str, String str2, Throwable th) {
        AdapterForTLog.loge(str, str2, th);
    }
}
