package com.alipay.mobile.common.transport.http;

import android.text.TextUtils;
import com.alipay.mobile.common.transport.context.TransportContext;
import com.alipay.mobile.common.transport.httpdns.AlipayHttpDnsClient;
import com.alipay.mobile.common.transport.httpdns.HttpDns;
import com.alipay.mobile.common.transport.iprank.AlipayDNSHelper;
import com.alipay.mobile.common.transport.utils.LogCatUtil;
import com.alipay.mobile.common.transport.utils.MiscUtils;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.TransportConstants;
import com.alipay.mobile.common.transport.utils.TransportEnvUtil;
import com.autonavi.widget.ui.BalloonLayout;
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.Header;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.protocol.HttpContext;

public class ZHttpRequestRetryHandler implements HttpRequestRetryHandler {
    public static final byte MAX_TIMES = 3;
    static final String TAG = "ZHttpRequestRetryHandler";
    private final boolean a = true;

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean sent;
        LogCatUtil.verbose(TAG, "retryRequest: executionCount=[" + executionCount + "], exception[" + exception.toString() + "]");
        context.setAttribute(TransportConstants.KEY_HTTP_RETRY_COUNT, Integer.valueOf(executionCount));
        if (!NetworkUtils.isNetworkAvailable(TransportEnvUtil.getContext())) {
            LogCatUtil.verbose(TAG, "Network unavailable, no retry");
            return false;
        }
        HttpUrlRequest httpUrlRequest = (HttpUrlRequest) context.getAttribute(TransportConstants.KEY_ORIGIN_REQUEST);
        if (httpUrlRequest == null) {
            LogCatUtil.verbose(TAG, "httpUrlRequest is null, what happened?");
            return false;
        }
        removeOldIpsWhenUserTimeout(context, httpUrlRequest);
        if (httpUrlRequest.isCanceled()) {
            LogCatUtil.verbose(TAG, "httpUrlRequest is already canceled");
            return false;
        } else if (!isCanRetryForStandardHttpRequest(httpUrlRequest)) {
            return false;
        } else {
            Boolean x = a(context, httpUrlRequest);
            if (x != null) {
                return x.booleanValue();
            }
            if (isShutdown(exception)) {
                LogCatUtil.info(TAG, "Connection shutdown, no retry");
                return false;
            } else if (executionCount >= 3) {
                LogCatUtil.verbose(TAG, " >= 3,  no retry");
                return false;
            } else {
                Object targetHostObj = context.getAttribute("http.target_host");
                if (targetHostObj != null && (targetHostObj instanceof String)) {
                    String targetHost = (String) targetHostObj;
                    if (targetHost.contains("127.0.0.1") || targetHost.contains("localhost")) {
                        LogCatUtil.verbose(TAG, "host=[" + targetHost + "] no retry.");
                        return false;
                    }
                }
                if ((exception instanceof NoHttpResponseException) || (exception instanceof ClientProtocolException)) {
                    a(httpUrlRequest);
                    if (!isRepeatable(context)) {
                        LogCatUtil.info(TAG, exception.getClass().getSimpleName() + "isRepeatable==false, no retry");
                        return false;
                    }
                    logRetry(context);
                    LogCatUtil.error(TAG, exception.getClass().getSimpleName() + " retry. exception:", exception);
                    return true;
                } else if ((exception instanceof SocketException) || (exception instanceof SSLException) || (exception instanceof SocketTimeoutException) || (exception instanceof ConnectionPoolTimeoutException) || (exception instanceof UnknownHostException) || (exception instanceof IOException)) {
                    LogCatUtil.error(TAG, exception.getClass().getSimpleName() + " retry. exception:", exception);
                    logRetry(context);
                    return true;
                } else {
                    Boolean b = (Boolean) context.getAttribute("http.request_sent");
                    if (b == null || !b.booleanValue()) {
                        sent = false;
                    } else {
                        sent = true;
                    }
                    if (!isRepeatable(context)) {
                        LogCatUtil.info(TAG, exception.getClass().getSimpleName() + "isRepeatable==false, no retry");
                        return false;
                    }
                    logRetry(context);
                    LogCatUtil.error(TAG, exception.getClass().getSimpleName() + ", sent=[" + sent + "]  retry.", exception);
                    return true;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public void removeOldIpsWhenUserTimeout(HttpContext context, HttpUrlRequest httpUrlRequest) {
        TransportContext transportContext = (TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT);
        if (transportContext != null && System.currentTimeMillis() - transportContext.startExecutionTime >= BalloonLayout.DEFAULT_DISPLAY_DURATION) {
            LogCatUtil.warn((String) TAG, (String) "removeOldIpsWhenUserTimeout. ");
            a(httpUrlRequest);
        }
    }

    private static Boolean a(HttpContext context, HttpUrlRequest httpUrlRequest) {
        return isRetryForRpc(httpUrlRequest, (TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT));
    }

    public static Boolean isRetryForRpc(HttpUrlRequest httpUrlRequest, TransportContext transportContext) {
        if (transportContext == null) {
            return Boolean.FALSE;
        }
        if (transportContext.bizType != 1) {
            return null;
        }
        String operationType = httpUrlRequest.getTag(TransportConstants.KEY_OPERATION_TYPE);
        if (TextUtils.isEmpty(operationType)) {
            return null;
        }
        HttpUriRequest httpUriRequest = httpUrlRequest.getHttpUriRequest();
        if (httpUriRequest == null) {
            return null;
        }
        Header header = httpUriRequest.getFirstHeader("Retryable");
        if (header == null || !TextUtils.equals("1", header.getValue())) {
            LogCatUtil.debug(TAG, "rpc can't retry");
            return Boolean.FALSE;
        }
        LogCatUtil.debug(TAG, "opeType: " + operationType + " ,rpc allow retry");
        return Boolean.TRUE;
    }

    public static boolean isShutdown(IOException exception) {
        if (exception == null) {
            return false;
        }
        Throwable rootCause = MiscUtils.getRootCause(exception);
        if (rootCause == null) {
            return false;
        }
        String s = rootCause.toString();
        if (TextUtils.isEmpty(s) || !s.contains("Connection already shutdown")) {
            return false;
        }
        return true;
    }

    private static void a(HttpUrlRequest httpUrlRequest) {
        if (httpUrlRequest != null) {
            try {
                String host = httpUrlRequest.getHttpUriRequest().getURI().getHost();
                LogCatUtil.debug(TAG, "removeOldIpsAndUpdateDns,host=[" + host + "]");
                AlipayDNSHelper.getInstance().removeIpsInIpRank(host);
                AlipayHttpDnsClient.getDnsClient().setErrorByHost(host);
                HttpDns.getInstance().getGetAllByNameHelper().clearCache();
            } catch (Throwable ex) {
                LogCatUtil.error((String) TAG, ex);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void logRetry(HttpContext context) {
        try {
            ((TransportContext) context.getAttribute(TransportConstants.KEY_NET_CONTEXT)).getCurrentDataContainer().putDataItem("RETRY", "T");
        } catch (Throwable e) {
            LogCatUtil.warn((String) TAG, "logRetry exception: " + e.toString());
        }
    }

    /* access modifiers changed from: protected */
    public boolean isRepeatable(HttpContext context) {
        Object isRepeatableObj = context.getAttribute(TransportConstants.KEY_IS_REPEATABLE);
        if (isRepeatableObj == null) {
            return true;
        }
        try {
            if (((Boolean) isRepeatableObj).booleanValue()) {
                return true;
            }
            LogCatUtil.warn((String) TAG, (String) "isRepeatable==false, no retry.");
            return false;
        } catch (Exception e) {
            LogCatUtil.warn((String) TAG, "isRepeatable exceptoin=[" + e.toString() + "]  no retry");
            return false;
        }
    }

    public static final int getRetryCount(HttpContext context) {
        try {
            Integer counter = (Integer) context.getAttribute(TransportConstants.KEY_HTTP_RETRY_COUNT);
            if (counter == null) {
                return 0;
            }
            return counter.intValue();
        } catch (Throwable e) {
            LogCatUtil.error(TAG, "getRetryCount", e);
            return 0;
        }
    }

    public static final boolean isCanRetryForStandardHttpRequest(HttpUrlRequest httpUrlRequest) {
        if (!httpUrlRequest.isUseHttpStdRetryModel()) {
            return true;
        }
        String requestMethod = httpUrlRequest.getRequestMethod();
        if (TextUtils.equals(requestMethod, "GET") || TextUtils.equals(requestMethod, RequestMethodConstants.PUT_METHOD) || TextUtils.equals(requestMethod, RequestMethodConstants.HEAD_METHOD)) {
            return true;
        }
        LogCatUtil.verbose(TAG, "UseHttpStdRetryStrategy model, " + requestMethod + " request method don't support retry!");
        return false;
    }
}
