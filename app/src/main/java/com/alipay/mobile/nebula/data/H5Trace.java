package com.alipay.mobile.nebula.data;

import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.provider.H5TraceProvider;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5Trace {
    private static boolean sEnabled = false;

    public static void setEnabled(boolean sEnabled2) {
        sEnabled = sEnabled2;
    }

    public static boolean isTraceEnable() {
        return sEnabled;
    }

    private static JSONObject formatParam(String[] params) {
        JSONObject jobj = new JSONObject();
        if (!(params == null || params.length == 0)) {
            for (int i = 0; i < params.length / 2; i++) {
                jobj.put(params[i], (Object) params[i + 1]);
            }
        }
        return jobj;
    }

    public static void event(String name, String viewId, String... params) {
        if (isTraceEnable()) {
            H5TraceProvider provider = (H5TraceProvider) H5Utils.getProvider(H5TraceProvider.class.getName());
            if (provider != null) {
                provider.event(name, viewId, formatParam(params));
            }
        }
    }

    public static void sessionBegin(String name, String viewId, String... params) {
        if (isTraceEnable()) {
            H5TraceProvider provider = (H5TraceProvider) H5Utils.getProvider(H5TraceProvider.class.getName());
            if (provider != null) {
                provider.sessionBegin(name, viewId, formatParam(params));
            }
        }
    }

    public static void sessionEnd(String name, String viewId, String... params) {
        if (isTraceEnable()) {
            H5TraceProvider provider = (H5TraceProvider) H5Utils.getProvider(H5TraceProvider.class.getName());
            if (provider != null) {
                provider.sessionEnd(name, viewId, formatParam(params));
            }
        }
    }
}
