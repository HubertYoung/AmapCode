package com.alipay.mobile.common.transportext.biz.spdy.http;

import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import com.alipay.mobile.common.transportext.biz.spdy.internal.http.SpdyRequestRetryHandler;
import com.alipay.mobile.common.transportext.biz.spdy.longlink.SpdyLongLinkUtils;
import com.alipay.mobile.common.transportext.util.InnerLogUtil;
import java.io.IOException;
import java.net.HttpURLConnection;

public class ZSpdyRequestRetryHandler implements SpdyRequestRetryHandler {
    public boolean retryRequest(IOException exception, int executionCount, boolean okHttpRetry, OkHttpClient okHttpClient, HttpURLConnection httpURLConnection) {
        String operationType = httpURLConnection.getRequestProperty("Operation-Type");
        if (okHttpRetry && operationType != null && SpdyLongLinkUtils.getSpdyLongLinkOperType().equals(operationType)) {
            LogCatUtil.printInfo(InnerLogUtil.MWALLET_SPDY_TAG, operationType + " can conntinue retry !!");
            return true;
        } else if (!isRetry(executionCount, okHttpRetry)) {
            return false;
        } else {
            LogCatUtil.debug(InnerLogUtil.MWALLET_SPDY_TAG, "Spdy auto retry. count=" + (executionCount + 1));
            return true;
        }
    }

    private boolean isRetry(int executionCount, boolean okHttpRetry) {
        if (executionCount < 3) {
            return true;
        }
        return false;
    }
}
