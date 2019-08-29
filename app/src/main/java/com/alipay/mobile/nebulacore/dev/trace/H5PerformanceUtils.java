package com.alipay.mobile.nebulacore.dev.trace;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.ConditionVariable;
import android.view.View;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class H5PerformanceUtils {
    private static int a = 0;

    private H5PerformanceUtils() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static Bitmap takeScreenShot(H5Page page) {
        if (page == null) {
            return null;
        }
        final View pageRootView = page.getRootView();
        if (pageRootView == null) {
            return null;
        }
        int width = pageRootView.getWidth() / 2;
        int height = pageRootView.getHeight() / 2;
        if (width <= 0 || height <= 0) {
            H5Log.d("H5PerformanceUtils", "cannot takeScreenShot for url " + page.getUrl() + " width: " + width + ", height: " + height);
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
        final Canvas canvas = new Canvas(bitmap);
        final ConditionVariable cv = new ConditionVariable();
        canvas.scale(0.5f, 0.5f);
        H5Utils.runOnMain(new Runnable() {
            public final void run() {
                pageRootView.draw(canvas);
                cv.open();
            }
        });
        cv.block(5000);
        return bitmap;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054 A[SYNTHETIC, Splitter:B:29:0x0054] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static long getRamSize() {
        /*
            r4 = 0
            r0 = 0
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Exception -> 0x003e }
            java.io.FileReader r6 = new java.io.FileReader     // Catch:{ Exception -> 0x003e }
            java.lang.String r7 = "/proc/meminfo"
            r6.<init>(r7)     // Catch:{ Exception -> 0x003e }
            r7 = 8192(0x2000, float:1.14794E-41)
            r1.<init>(r6, r7)     // Catch:{ Exception -> 0x003e }
            java.lang.String r3 = r1.readLine()     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            boolean r6 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            if (r6 == 0) goto L_0x0026
            r1.close()     // Catch:{ IOException -> 0x001f }
        L_0x001e:
            return r4
        L_0x001f:
            r2 = move-exception
            java.lang.String r6 = "H5PerformanceUtils"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r2)
            goto L_0x001e
        L_0x0026:
            java.lang.String r6 = "\\s+"
            java.lang.String[] r6 = r3.split(r6)     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            r7 = 1
            r6 = r6[r7]     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            long r4 = java.lang.Long.parseLong(r6)     // Catch:{ Exception -> 0x0062, all -> 0x005f }
            r1.close()     // Catch:{ IOException -> 0x0037 }
            goto L_0x001e
        L_0x0037:
            r2 = move-exception
            java.lang.String r6 = "H5PerformanceUtils"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r2)
            goto L_0x001e
        L_0x003e:
            r2 = move-exception
        L_0x003f:
            java.lang.String r6 = "H5PerformanceUtils"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r2)     // Catch:{ all -> 0x0051 }
            if (r0 == 0) goto L_0x001e
            r0.close()     // Catch:{ IOException -> 0x004a }
            goto L_0x001e
        L_0x004a:
            r2 = move-exception
            java.lang.String r6 = "H5PerformanceUtils"
            com.alipay.mobile.nebula.util.H5Log.e(r6, r2)
            goto L_0x001e
        L_0x0051:
            r4 = move-exception
        L_0x0052:
            if (r0 == 0) goto L_0x0057
            r0.close()     // Catch:{ IOException -> 0x0058 }
        L_0x0057:
            throw r4
        L_0x0058:
            r2 = move-exception
            java.lang.String r5 = "H5PerformanceUtils"
            com.alipay.mobile.nebula.util.H5Log.e(r5, r2)
            goto L_0x0057
        L_0x005f:
            r4 = move-exception
            r0 = r1
            goto L_0x0052
        L_0x0062:
            r2 = move-exception
            r0 = r1
            goto L_0x003f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.nebulacore.dev.trace.H5PerformanceUtils.getRamSize():long");
    }

    public static int getNumCores() {
        if (a == 0) {
            try {
                a = new File("/sys/devices/system/cpu/").listFiles(new FileFilter() {
                    public boolean accept(File pathname) {
                        return Pattern.matches("cpu[0-9]", pathname.getName());
                    }
                }).length;
            } catch (Exception e) {
                H5Log.e("H5PerformanceUtils", "getNumCores exception", e);
                a = 1;
            }
        }
        return a;
    }

    public static List<String> getAllThreadsTraces() {
        List traces = new ArrayList();
        try {
            for (Entry entry : Thread.getAllStackTraces().entrySet()) {
                StringBuilder sb = new StringBuilder();
                sb.append("ThreadName=").append(((Thread) entry.getKey()).getName());
                sb.append("\n");
                for (StackTraceElement stackTrace : (StackTraceElement[]) entry.getValue()) {
                    sb.append(String.valueOf(stackTrace));
                    sb.append("\n");
                }
                sb.append("\n");
                traces.add(sb.toString());
            }
            return traces;
        } catch (Throwable t) {
            H5Log.e("H5PerformanceUtils", "getAllStackTraces", t);
            return null;
        }
    }
}
