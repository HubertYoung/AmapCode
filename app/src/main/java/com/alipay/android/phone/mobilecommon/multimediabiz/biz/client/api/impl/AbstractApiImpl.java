package com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl;

import android.util.Base64;
import android.webkit.CookieManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.DjangoClient;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.infos.BaseApiInfo;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.ConnectionManager;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.config.EnvSwitcher;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.exception.DjangoClientException;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.ByteUtil;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoConstant;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.DjangoUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.util.HttpClientUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.HttpMultipartMode;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.apache.entity.mine.MultipartEntityBuilder;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.http.util.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.AppUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.Logger;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.UuidManager;
import com.alipay.mobile.common.transport.utils.NetworkUtils;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import java.util.Arrays;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;

public class AbstractApiImpl {
    protected ConnectionManager<HttpClient> connectionManager;
    protected DjangoClient djangoClient;

    public AbstractApiImpl(DjangoClient djangoClient2, ConnectionManager<HttpClient> connectionManager2) {
        this.djangoClient = djangoClient2;
        this.connectionManager = connectionManager2;
    }

    /* access modifiers changed from: protected */
    public MultipartEntityBuilder genMultipartEntityBuilder() {
        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
        multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        multipartEntityBuilder.setCharset(DjangoConstant.DEFAULT_CHARSET);
        return multipartEntityBuilder;
    }

    /* access modifiers changed from: protected */
    public String genAclString(String id, String timestamp) {
        return EnvSwitcher.getAclString(id, timestamp, this.connectionManager);
    }

    /* access modifiers changed from: protected */
    public String getCookieString() {
        return String.format(DjangoConstant.COOKIE_FORMAT, new Object[]{this.connectionManager.getUid(), this.connectionManager.getAcl()});
    }

    public String getTraceId() {
        return a(false);
    }

    public String getTokenTraceId() {
        return a(true);
    }

    private String a(boolean isGetToken) {
        try {
            byte[] uuidBytes = ByteUtil.UUIDToByteArray(UuidManager.get().getUUID());
            byte[] timeStampBytes = ByteUtil.longToByteArray(a(this.djangoClient, isGetToken));
            byte[] channelBytes = a();
            byte[] appIdBytes = ByteUtil.intToByteArray(this.connectionManager.getAppId());
            byte[] formatBytes = new byte[(uuidBytes.length + 0 + timeStampBytes.length + 4 + appIdBytes.length + 1)];
            System.arraycopy(uuidBytes, 0, formatBytes, 0, uuidBytes.length);
            int destPos = uuidBytes.length + 0;
            System.arraycopy(timeStampBytes, 0, formatBytes, destPos, timeStampBytes.length);
            int destPos2 = destPos + timeStampBytes.length;
            System.arraycopy(channelBytes, 0, formatBytes, destPos2, 4);
            int destPos3 = destPos2 + 4;
            System.arraycopy(appIdBytes, 0, formatBytes, destPos3, appIdBytes.length);
            System.arraycopy(new byte[]{1}, 0, formatBytes, destPos3 + appIdBytes.length, 1);
            return Base64.encodeToString(formatBytes, 11);
        } catch (Exception e) {
            Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, (String) "getTraceId exception", new Object[0]);
            return "";
        }
    }

    private static long a(DjangoClient djangoClient2, boolean isGetToken) {
        long serverTime = 0;
        int count = 3;
        while (count > 0) {
            try {
                serverTime = djangoClient2.getCorrectServerTime();
            } catch (DjangoClientException e) {
                Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e, (String) "getCorrectServerTime exception", new Object[0]);
            }
            if (serverTime > 0) {
                return serverTime;
            }
            if (isGetToken) {
                Logger.P(DjangoClient.LOG_TAG, "getServerTime use local timestamp", new Object[0]);
                return System.currentTimeMillis();
            }
            try {
                djangoClient2.getTokenApi().getTokenString();
                count--;
            } catch (Exception e2) {
                Logger.E((String) DjangoClient.LOG_TAG, (Throwable) e2, (String) "getTokenString exception", new Object[0]);
                return serverTime;
            }
        }
        return serverTime;
    }

    private static byte[] a() {
        return new byte[]{(byte) DjangoUtils.convertNetworkType(NetworkUtils.getNetworkType(AppUtils.getApplicationContext())), (byte) AppUtils.getMinorVersion(AppUtils.getApplicationContext()), (byte) AppUtils.getMainVersion(AppUtils.getApplicationContext()), 2};
    }

    /* access modifiers changed from: protected */
    public HttpGet createHttpGet(BaseApiInfo apiInfo, List<NameValuePair> params, boolean bNeedCookie) {
        return createHttpGet(apiInfo, params, bNeedCookie, false);
    }

    /* access modifiers changed from: protected */
    public HttpGet createHttpGet(BaseApiInfo apiInfo, List<NameValuePair> params, boolean bNeedCookie, boolean useConnPool) {
        HttpGet method = new HttpGet(HttpClientUtils.urlAppendParams(apiInfo.getUrlApi(), params));
        if (bNeedCookie) {
            method.addHeader("Cookie", getCookieString());
        }
        if (DjangoClient.DEBUG) {
            Logger.D(DjangoClient.LOG_TAG, Arrays.toString(method.getAllHeaders()), new Object[0]);
        }
        return method;
    }

    /* access modifiers changed from: protected */
    public HttpGet createHttpGet(BaseApiInfo apiInfo, List<NameValuePair> params) {
        return createHttpGet(apiInfo, params, true);
    }

    private static boolean a(String fileId) {
        if (TextUtils.isEmpty(fileId) || !fileId.startsWith("A*")) {
            return false;
        }
        return true;
    }

    /* access modifiers changed from: protected */
    public HttpGet createHttpGet(String uri, String fileId) {
        HttpGet method = new HttpGet(uri);
        if (a(fileId)) {
            String cookie = CookieManager.getInstance().getCookie(ReadSettingServerUrl.getInstance().getGWFURL(AppUtils.getApplicationContext()));
            if (!TextUtils.isEmpty(cookie)) {
                method.addHeader("Cookie", cookie);
            }
        }
        if (DjangoClient.DEBUG) {
            Logger.D(DjangoClient.LOG_TAG, Arrays.toString(method.getAllHeaders()), new Object[0]);
        }
        return method;
    }

    /* access modifiers changed from: protected */
    public HttpPost createHttpPost(BaseApiInfo apiInfo, List<NameValuePair> params, boolean bNeedCookie) {
        HttpPost method = new HttpPost(HttpClientUtils.urlAppendParams(apiInfo.getUrlApi(), params));
        if (bNeedCookie) {
            method.addHeader("Cookie", getCookieString());
        }
        if (DjangoClient.DEBUG) {
            Logger.D(DjangoClient.LOG_TAG, Arrays.toString(method.getAllHeaders()), new Object[0]);
        }
        return method;
    }

    /* access modifiers changed from: protected */
    public HttpPost createHttpPost(BaseApiInfo apiInfo, List<NameValuePair> params) {
        return createHttpPost(apiInfo, params, true);
    }

    /* access modifiers changed from: protected */
    public HttpDelete createHttpDelete(BaseApiInfo apiInfo, List<NameValuePair> params) {
        HttpDelete method = new HttpDelete(HttpClientUtils.urlAppendParams(apiInfo.getUrlApi(), params));
        method.addHeader("Cookie", getCookieString());
        if (DjangoClient.DEBUG) {
            Logger.D(DjangoClient.LOG_TAG, Arrays.toString(method.getAllHeaders()), new Object[0]);
        }
        return method;
    }
}
