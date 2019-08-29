package com.alipay.mobile.h5container.api;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.List;

public class H5PageCount {
    private static long sRamSize = -100;
    private static List<String> urls = new ArrayList();

    public static void addUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            synchronized (urls) {
                urls.add(url);
            }
        }
    }

    public static void removeUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            synchronized (urls) {
                urls.remove(url);
            }
        }
    }

    public static String getAll() {
        StringBuilder builder = new StringBuilder();
        synchronized (urls) {
            for (int i = 0; i < urls.size(); i++) {
                if (i != 0) {
                    builder.append("->");
                }
                builder.append(urls.get(i));
            }
        }
        return builder.toString();
    }

    public static String totalRamMemorySize(Context context) {
        return String.valueOf(getTotalMemory(context) / 1048576);
    }

    @TargetApi(16)
    public static long getTotalMemory(Context context) {
        if (sRamSize == -1) {
            return sRamSize;
        }
        if (sRamSize == -100) {
            synchronized (H5PageCount.class) {
                try {
                    if (VERSION.SDK_INT >= 16) {
                        MemoryInfo memoryInfo = new MemoryInfo();
                        ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getMemoryInfo(memoryInfo);
                        sRamSize = memoryInfo.totalMem;
                    } else {
                        sRamSize = -1;
                    }
                } catch (Throwable th) {
                    sRamSize = -1;
                }
            }
        }
        return sRamSize;
    }
}
