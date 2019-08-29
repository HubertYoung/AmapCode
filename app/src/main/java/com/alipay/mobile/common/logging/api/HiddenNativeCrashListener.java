package com.alipay.mobile.common.logging.api;

import android.text.TextUtils;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi;
import com.alipay.mobile.common.nativecrash.NativeCrashHandlerApi.ReportCrashListener;
import java.lang.reflect.Method;

public class HiddenNativeCrashListener implements ReportCrashListener {
    private Object a;
    private String b;

    public Object onReportCrash(String bizKey, String crashInfo, String filePath, String callStack, boolean isReturnJVM) {
        if (this.a != null) {
            try {
                Method method = this.a.getClass().getDeclaredMethod("onReportCrash", new Class[]{String.class, String.class, String.class, String.class, Boolean.TYPE});
                method.setAccessible(true);
                return method.invoke(this.a, new Object[]{bizKey, crashInfo, filePath, callStack, Boolean.valueOf(isReturnJVM)});
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().warn((String) "HiddenNativeCrashListener", tr);
            }
        }
        return null;
    }

    public void setAgentListener(String keyBiz, Object agentListener) {
        this.b = keyBiz;
        this.a = agentListener;
        NativeCrashHandlerApi.putReportCrashListener(keyBiz, this);
    }

    public void unsetAgentListener() {
        this.a = null;
        if (!TextUtils.isEmpty(this.b)) {
            NativeCrashHandlerApi.removeReportCrashListener(this.b);
            this.b = null;
        }
    }
}
