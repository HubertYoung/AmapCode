package com.alipay.inside.android.phone.mrpc.core;

import android.text.TextUtils;
import com.alipay.inside.android.phone.mrpc.core.utils.MiscUtils;
import java.io.IOException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.protocol.HttpContext;

public class ZHttpRequestRetryHandler implements HttpRequestRetryHandler {
    static final String TAG = "ZHttpRequestRetryHandler";

    public boolean retryRequest(IOException iOException, int i, HttpContext httpContext) {
        return false;
    }

    public static boolean isShutdown(IOException iOException) {
        if (iOException == null) {
            return false;
        }
        Throwable rootCause = MiscUtils.getRootCause(iOException);
        if (rootCause == null) {
            return false;
        }
        String th = rootCause.toString();
        if (!TextUtils.isEmpty(th) && th.contains("Connection already shutdown")) {
            return true;
        }
        return false;
    }
}
