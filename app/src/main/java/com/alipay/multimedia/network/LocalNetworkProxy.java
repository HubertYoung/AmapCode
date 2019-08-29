package com.alipay.multimedia.network;

import android.text.TextUtils;
import android.util.Base64;
import com.alipay.multimedia.common.logging.MLog;
import com.alipay.multimedia.utils.MusicUtils;
import java.io.IOException;

public class LocalNetworkProxy {
    private static final String LOCAL_IP_ADDRESS = "127.0.0.1";
    private static int LOCAL_PORT = 1746;
    private static final int RETRY_COUNT = 10;
    private static final String TAG = "LocalNetworkProxy";
    private static LocalNetworkProxy sInstance;
    private HttpServer mHttpServer = null;
    private int mRetryBindCount = 0;

    public static LocalNetworkProxy getInstance() {
        if (sInstance == null) {
            synchronized (LocalNetworkProxy.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new LocalNetworkProxy();
                    }
                }
            }
        }
        return sInstance;
    }

    public synchronized void start() {
        MLog.i(TAG, "start...");
        if (this.mHttpServer == null) {
            this.mHttpServer = new HttpServer("127.0.0.1", LOCAL_PORT);
            try {
                this.mHttpServer.start();
            } catch (IOException e) {
                MLog.e(TAG, "start.e=" + e);
                if (this.mHttpServer != null) {
                    this.mHttpServer.stop();
                    this.mHttpServer = null;
                }
                if (this.mRetryBindCount < 10) {
                    LOCAL_PORT += 1000;
                    MLog.i(TAG, "start.retry.mRetryBindCount=" + this.mRetryBindCount + ", LOCAL_PORT=" + LOCAL_PORT);
                    this.mRetryBindCount++;
                    start();
                }
            }
        } else {
            MLog.i(TAG, "mHttpServer has already start...");
        }
    }

    public void stop() {
        MLog.i(TAG, "stop...");
        if (this.mHttpServer != null) {
            this.mHttpServer.stop();
            this.mHttpServer = null;
        }
        this.mRetryBindCount = 0;
    }

    public String genLocalUrl(String realUrl, String jsonExtra) {
        if (TextUtils.isEmpty(realUrl)) {
            return realUrl;
        }
        if (!MusicUtils.isHttp(realUrl) && !realUrl.startsWith("A*")) {
            return realUrl;
        }
        StringBuilder sb = new StringBuilder(256);
        sb.append(getRequestUrl());
        if (TextUtils.isEmpty(realUrl)) {
            throw new IllegalArgumentException("realUrl is empty or null");
        }
        String base64Url = Base64.encodeToString(realUrl.getBytes(), 0);
        sb.append("?realurl=").append(base64Url).append("&");
        if (!TextUtils.isEmpty(jsonExtra)) {
            sb.append("jsonextra=").append(Base64.encodeToString(jsonExtra.getBytes(), 0)).append("&");
        }
        sb.append("sign=" + MusicUtils.sign(base64Url)).append("&");
        String result = sb.toString().replace("\n", "");
        MLog.i(TAG, "genLocalUrl.result=" + sb.toString());
        return result;
    }

    public String getRealUrl(String localUrl) {
        String value;
        if (TextUtils.isEmpty(localUrl) || !MusicUtils.isHttp(localUrl)) {
            return localUrl;
        }
        int realUrlStartIndex = localUrl.indexOf("realurl=");
        if (realUrlStartIndex <= 0) {
            return null;
        }
        int realUrlEndIndex = localUrl.indexOf("&", realUrlStartIndex);
        if (realUrlEndIndex <= 0) {
            value = localUrl.substring(realUrlStartIndex + 8);
        } else {
            value = localUrl.substring(realUrlStartIndex + 8, realUrlEndIndex);
        }
        String realUrl = new String(Base64.decode(value, 0));
        MLog.i(TAG, "getRealUrl.localUrl=" + localUrl + ",value=" + value + ",realUrl=" + realUrl);
        return realUrl;
    }

    public String getRequestUrl() {
        return "http://127.0.0.1:" + LOCAL_PORT;
    }

    public int getErrorCode() {
        if (this.mHttpServer != null) {
            return this.mHttpServer.getErrorCode();
        }
        return 0;
    }

    public void stopMusicFile(boolean isError) {
        if (this.mHttpServer != null) {
            this.mHttpServer.stopMusicFile(isError);
        }
    }
}
