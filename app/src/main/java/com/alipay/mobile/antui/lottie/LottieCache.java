package com.alipay.mobile.antui.lottie;

import android.content.Context;
import android.text.TextUtils;
import com.alipay.mobile.antui.utils.AuiLogger;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LottieCache {
    private static LottieCache mInstance = null;
    private final Map<String, WeakReference<String>> mLottieMap = new ConcurrentHashMap();

    public static synchronized LottieCache getInstance() {
        LottieCache lottieCache;
        synchronized (LottieCache.class) {
            try {
                if (mInstance == null) {
                    mInstance = new LottieCache();
                }
                lottieCache = mInstance;
            }
        }
        return lottieCache;
    }

    private LottieCache() {
    }

    public void putFileJson(String fileName, String jsonString) {
        if (!TextUtils.isEmpty(fileName) && !TextUtils.isEmpty(jsonString)) {
            this.mLottieMap.put(fileName, new WeakReference(jsonString));
        }
    }

    public String getFileJson(String fileName) {
        if (TextUtils.isEmpty(fileName)) {
            return null;
        }
        WeakReference jsonObjectWeakReference = this.mLottieMap.get(fileName);
        if (jsonObjectWeakReference != null && jsonObjectWeakReference.get() != null) {
            return (String) jsonObjectWeakReference.get();
        }
        this.mLottieMap.remove(fileName);
        return null;
    }

    public void clear() {
        this.mLottieMap.clear();
    }

    public static String getFileJson(Context context, String fileName) {
        if (context == null || TextUtils.isEmpty(fileName)) {
            return null;
        }
        String lottieString = getInstance().getFileJson(fileName);
        if (!TextUtils.isEmpty(lottieString)) {
            return lottieString;
        }
        try {
            lottieString = fromAssetFileName(context, fileName);
        } catch (Exception e) {
            AuiLogger.mtBizReport("ExtConfigManager", "getExtTypeface error");
        }
        if (TextUtils.isEmpty(lottieString)) {
            return lottieString;
        }
        getInstance().putFileJson(fileName, lottieString);
        return lottieString;
    }

    public static String fromAssetFileName(Context context, String fileName) {
        try {
            InputStream stream = context.getAssets().open(fileName);
            if (stream == null) {
                return "";
            }
            byte[] b = new byte[stream.available()];
            stream.read(b);
            String lottieString = new String(b);
            try {
                stream.close();
                return lottieString;
            } catch (IOException e) {
                var5 = e;
                String str = lottieString;
                throw new IllegalStateException("Unable to find file " + fileName, var5);
            }
        } catch (IOException e2) {
            var5 = e2;
            throw new IllegalStateException("Unable to find file " + fileName, var5);
        }
    }
}
