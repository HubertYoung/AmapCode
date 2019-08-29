package com.alipay.multimedia.adjuster.utils;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Patterns;
import com.alipay.multimedia.adjuster.config.ConfigMgr;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AliCdnUtils {
    private static final int IMAGE_MAX_BITMAP_DIMENSION = 2048;
    private static final String TAG = "AliCdnUtils";
    private static Random mRandom = new Random();
    private static ExecutorService mSingleExecutor = null;

    public static int getMaxTextureSize() {
        return 2048;
    }

    public static boolean checkUrlWithFuzzy(String url, String[] list) {
        if (TextUtils.isEmpty(url) || list == null || list.length <= 0) {
            return false;
        }
        for (String item : list) {
            if (url.contains(item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkUrlHostWithExact(String host, String[] list) {
        if (TextUtils.isEmpty(host) || list == null || list.length <= 0) {
            return false;
        }
        for (String item : list) {
            if (host.equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }

    public static int getScreenScale() {
        return ConfigMgr.getInc().getCdnConfigItem().mScreenScale;
    }

    public static boolean checkUrl(String url) {
        if (Patterns.WEB_URL.matcher(url).matches()) {
            return true;
        }
        return false;
    }

    public static String getUrlDecoderString(String str, String format) {
        String result = "";
        if (str == null) {
            return "";
        }
        try {
            result = URLDecoder.decode(str, format);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isHttp(Uri uri) {
        if (uri == null) {
            return false;
        }
        String scheme = uri.getScheme();
        if (("https".equalsIgnoreCase(scheme) || "http".equalsIgnoreCase(scheme)) && hasHost(uri)) {
            return true;
        }
        return false;
    }

    public static boolean hasHost(Uri uri) {
        String host = uri.getHost();
        return host != null && !"".equals(host);
    }

    public static int generateRandom(int min, int max) {
        return (mRandom.nextInt(max) % ((max - min) + 1)) + min;
    }

    public static synchronized ExecutorService getSingleExecutor() {
        ExecutorService executorService;
        synchronized (AliCdnUtils.class) {
            try {
                if (mSingleExecutor == null) {
                    ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
                    mSingleExecutor = newSingleThreadExecutor;
                    allTimeout(newSingleThreadExecutor);
                }
                executorService = mSingleExecutor;
            }
        }
        return executorService;
    }

    private static void allTimeout(ExecutorService executor) {
        if (executor instanceof ThreadPoolExecutor) {
            try {
                ((ThreadPoolExecutor) executor).allowCoreThreadTimeOut(true);
            } catch (Throwable e) {
                Log.D(TAG, "allTimeout exp=" + e.toString(), new Object[0]);
            }
        }
    }
}
