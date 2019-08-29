package com.alipay.mobile.common.transport.utils;

import android.text.TextUtils;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpParams;

public final class HttpClientUtils {
    public static String removeParamter(HttpUriRequest httpUriRequest, String key) {
        HttpParams params = httpUriRequest.getParams();
        if (params == null) {
            return "";
        }
        String paramStr = (String) params.getParameter(key);
        if (TextUtils.isEmpty(paramStr)) {
            return "";
        }
        try {
            params.removeParameter(key);
            return paramStr;
        } catch (Throwable e) {
            LogCatUtil.warn("HttpClientUtils", "", e);
            return "";
        }
    }
}
