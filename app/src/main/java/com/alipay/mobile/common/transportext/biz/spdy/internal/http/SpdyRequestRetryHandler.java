package com.alipay.mobile.common.transportext.biz.spdy.internal.http;

import com.alipay.mobile.common.transportext.biz.spdy.OkHttpClient;
import java.io.IOException;
import java.net.HttpURLConnection;

public interface SpdyRequestRetryHandler {
    boolean retryRequest(IOException iOException, int i, boolean z, OkHttpClient okHttpClient, HttpURLConnection httpURLConnection);
}
