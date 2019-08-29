package com.alipay.multimedia.network;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.alipay.mobile.common.transport.multimedia.DjgHttpManager;
import com.alipay.mobile.common.transport.utils.ReadSettingServerUrl;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.MicroApplicationContext;
import com.alipay.multimedia.common.logging.MLog;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class NetDownloader {
    private static final String TAG = "NetDownloader";
    /* access modifiers changed from: private */
    public static DjgHttpManager mDjgHttpManager = new DjgHttpManager(getApplicationContext());
    private String mFileId;
    private ExecutorService mService = Executors.newFixedThreadPool(1);
    private String mUrl;

    public static Context getApplicationContext() {
        return getMicroApplicationContext().getApplicationContext();
    }

    public static MicroApplicationContext getMicroApplicationContext() {
        return LauncherApplicationAgent.getInstance().getMicroApplicationContext();
    }

    public NetDownloader(String url, String fileId) {
        this.mUrl = url;
        this.mFileId = fileId;
    }

    public HttpResponse getHead() {
        return getHead(this.mUrl);
    }

    private boolean isAftsID() {
        if (TextUtils.isEmpty(this.mFileId) || !this.mFileId.startsWith("A*")) {
            return false;
        }
        return true;
    }

    public HttpResponse getHead(String url) {
        final HttpGet method = new HttpGet(url);
        method.addHeader("Range", "bytes=0-1");
        if (isAftsID()) {
            String cookie = CookieManager.getInstance().getCookie(ReadSettingServerUrl.getInstance().getGWFURL(getApplicationContext()));
            MLog.i(TAG, "getHead.url=" + url + ",cookie=" + cookie);
            if (!TextUtils.isEmpty(cookie)) {
                method.addHeader("Cookie", cookie);
            }
        }
        HttpResponse response = null;
        try {
            response = (HttpResponse) this.mService.submit(new Callable<HttpResponse>() {
                public HttpResponse call() {
                    HttpResponse response = null;
                    try {
                        return NetDownloader.mDjgHttpManager.execute((HttpUriRequest) method);
                    } catch (IOException e) {
                        MLog.e(NetDownloader.TAG, "getHead.e=" + e);
                        return response;
                    }
                }
            }).get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            MLog.e(TAG, "getHead future get exp=" + e.getMessage());
        }
        if (response == null) {
            MLog.e(TAG, "getHead response is null..");
        }
        return response;
    }

    public HttpResponse download(String url, long rangeStart, long rangeEnd) {
        final HttpGet method = new HttpGet(url);
        method.addHeader("Range", "bytes=" + rangeStart + "-" + rangeEnd);
        if (isAftsID()) {
            String cookie = CookieManager.getInstance().getCookie(ReadSettingServerUrl.getInstance().getGWFURL(getApplicationContext()));
            if (!TextUtils.isEmpty(cookie)) {
                method.addHeader("Cookie", cookie);
            }
        }
        HttpResponse response = null;
        try {
            response = (HttpResponse) this.mService.submit(new Callable<HttpResponse>() {
                public HttpResponse call() {
                    HttpResponse response = null;
                    try {
                        return NetDownloader.mDjgHttpManager.execute((HttpUriRequest) method);
                    } catch (IOException e) {
                        MLog.e(NetDownloader.TAG, "download.e=" + e);
                        return response;
                    }
                }
            }).get(10, TimeUnit.SECONDS);
        } catch (Exception e) {
            MLog.e(TAG, "download future get exp=" + e.getMessage());
        }
        if (response == null) {
            MLog.e(TAG, "download response is null..");
        }
        return response;
    }

    public HttpResponse download(long rangeStart, long rangeEnd) {
        return download(this.mUrl, rangeStart, rangeEnd);
    }
}
