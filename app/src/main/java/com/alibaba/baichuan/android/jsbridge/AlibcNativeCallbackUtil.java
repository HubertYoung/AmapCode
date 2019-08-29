package com.alibaba.baichuan.android.jsbridge;

import android.webkit.ValueCallback;
import java.util.concurrent.ConcurrentHashMap;

public class AlibcNativeCallbackUtil {
    public static final String SEPERATER = "/";
    private static ConcurrentHashMap a = new ConcurrentHashMap();

    public static void clearAllNativeCallback() {
        a.clear();
    }

    public static void clearNativeCallback(String str) {
        a.remove(str);
    }

    public static ValueCallback getNativeCallback(String str) {
        return (ValueCallback) a.get(str);
    }

    public static void putNativeCallbak(String str, ValueCallback valueCallback) {
        a.put(str, valueCallback);
    }
}
