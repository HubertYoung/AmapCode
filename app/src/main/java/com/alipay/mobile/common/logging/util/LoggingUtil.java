package com.alipay.mobile.common.logging.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.alipay.mobile.common.logging.api.LogContext;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.ProcessInfo;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TimeZone;

public class LoggingUtil {
    private static int a = -1;
    private static int b = -1;
    private static int c = -1;
    private static Random d;

    public interface FillBufferHandler {
        Object handleKey(Object obj);

        Object handleValue(Object obj);
    }

    public static boolean isDebuggable(Context context) {
        int i;
        if (a < 0) {
            if (context == null) {
                return false;
            }
            try {
                if ((context.getPackageManager().getApplicationInfo(context.getPackageName(), 16384).flags & 2) == 2) {
                    i = 1;
                } else {
                    i = 0;
                }
                a = i;
            } catch (Throwable e) {
                Log.e("LoggingUtil", "isDebuggable", e);
                a = 0;
            }
        }
        if (a == 1) {
            return true;
        }
        return false;
    }

    public static void reflectErrorLog(String log) {
        if (log != null) {
            reflectErrorLog("LoggingUtil", log, true);
        }
    }

    public static void reflectErrorLog(String tag, String log, boolean showThreadName) {
        try {
            StringBuilder sb = new StringBuilder();
            if (showThreadName) {
                sb.append('[').append(Thread.currentThread().getName()).append(']');
            }
            sb.append(log);
            Class.forName(AndroidLogger.ANDROID_UTIL_LOG).getMethod("e", new Class[]{String.class, String.class}).invoke(null, new Object[]{tag, sb.toString()});
        } catch (Throwable th) {
        }
    }

    public static void reflectErrorLog(String log, Throwable e) {
        if (log != null && e != null) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append('[').append(Thread.currentThread().getName()).append(']');
                sb.append(log);
                Class.forName(AndroidLogger.ANDROID_UTIL_LOG).getMethod("e", new Class[]{String.class, String.class, Throwable.class}).invoke(null, new Object[]{"LoggingUtil", sb.toString(), e});
            } catch (Throwable th) {
            }
        }
    }

    public static void reflectErrorLogAutomationCrash(String message) {
        reflectErrorLog("automationcrash", "Force Start parse for automation", false);
        reflectErrorLog(message);
        reflectErrorLog("automationcrash", "Force End parse for automation", false);
    }

    public static String getNowTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Shanghai"));
        StringBuilder buf = new StringBuilder();
        int Y = calendar.get(1);
        int M = calendar.get(2) + 1;
        int D = calendar.get(5);
        int h = calendar.get(11);
        int m = calendar.get(12);
        int s = calendar.get(13);
        int S = calendar.get(14);
        buf.append(Y).append('-');
        if (M < 10) {
            buf.append('0');
        }
        buf.append(M).append('-');
        if (D < 10) {
            buf.append('0');
        }
        buf.append(D).append(' ');
        if (h < 10) {
            buf.append('0');
        }
        buf.append(h).append(':');
        if (m < 10) {
            buf.append('0');
        }
        buf.append(m).append(':');
        if (s < 10) {
            buf.append('0');
        }
        buf.append(s).append(':');
        if (S < 100) {
            buf.append('0');
        }
        if (S < 10) {
            buf.append('0');
        }
        buf.append(S);
        return buf.toString();
    }

    public static StringBuilder appendParam(StringBuilder buffer, String value) {
        if (buffer == null) {
            return null;
        }
        buffer.append(',');
        if (value == null) {
            return buffer;
        }
        buffer.append(value.replace(',', ' '));
        return buffer;
    }

    public static StringBuilder appendExtParam(StringBuilder buf, Map<String, String> params) {
        if (buf == null) {
            return null;
        }
        buf.append(',');
        if (params == null || params.size() == 0) {
            return buf;
        }
        boolean isFirst = true;
        for (Entry entry : params.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (value == null) {
                value = "";
            }
            if (!(key == null || value == null)) {
                String value2 = fliterChar(value);
                if (isFirst) {
                    isFirst = false;
                } else {
                    buf.append('^');
                }
                buf.append(key.replace(',', ' ').replace('^', ' ').replace('=', ' ')).append('=').append(value2.replace(',', ' ').replace('^', ' '));
            }
        }
        return buf;
    }

    public static File getCommonExternalStorageDir() {
        File dir;
        try {
            dir = new File(Environment.getExternalStorageDirectory(), "amap");
        } catch (Throwable t) {
            Log.e("LoggingUtil", "getCommonExternalStorageDir: " + t);
            dir = new File("/sdcard/amap");
        }
        try {
            if (!dir.exists()) {
                dir.mkdirs();
            } else if (dir.isFile()) {
                FileUtil.deleteFileNotDir(dir);
                dir.mkdirs();
            }
        } catch (Throwable th) {
        }
        return dir;
    }

    public static String getCommonExternalStoragePath() {
        File dir = getCommonExternalStorageDir();
        if (dir == null) {
            return null;
        }
        return dir.getAbsolutePath();
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0021 A[SYNTHETIC, Splitter:B:7:0x0021] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.io.File getStorageFilesDir(android.content.Context r5, java.lang.String r6) {
        /*
            r1 = 0
            boolean r4 = isOfflineForExternalFile()
            if (r4 == 0) goto L_0x0037
            boolean r4 = com.alipay.mobile.common.logging.util.FileUtil.isCanUseSdCard()
            if (r4 == 0) goto L_0x0037
            java.io.File r0 = getCommonExternalStorageDir()     // Catch:{ Throwable -> 0x0036 }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0036 }
            java.lang.String r4 = r5.getPackageName()     // Catch:{ Throwable -> 0x0036 }
            r3.<init>(r0, r4)     // Catch:{ Throwable -> 0x0036 }
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0036 }
            r2.<init>(r3, r6)     // Catch:{ Throwable -> 0x0036 }
        L_0x001f:
            if (r2 != 0) goto L_0x003a
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0039 }
            java.io.File r4 = r5.getFilesDir()     // Catch:{ Throwable -> 0x0039 }
            r1.<init>(r4, r6)     // Catch:{ Throwable -> 0x0039 }
        L_0x002a:
            if (r1 == 0) goto L_0x0035
            boolean r4 = r1.exists()
            if (r4 != 0) goto L_0x0035
            r1.mkdirs()
        L_0x0035:
            return r1
        L_0x0036:
            r4 = move-exception
        L_0x0037:
            r2 = r1
            goto L_0x001f
        L_0x0039:
            r4 = move-exception
        L_0x003a:
            r1 = r2
            goto L_0x002a
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.LoggingUtil.getStorageFilesDir(android.content.Context, java.lang.String):java.io.File");
    }

    public static boolean isOfflineForExternalFile() {
        boolean z;
        int i;
        if (c < 0) {
            String releaseType = LoggerFactory.getLogContext().getReleaseType();
            if (isOfflineMode() || LogContext.RELEASETYPE_RC.equals(releaseType) || LogContext.RELEASETYPE_RCPRE.equals(releaseType)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            c = i;
        }
        if (c == 1) {
            return true;
        }
        return false;
    }

    public static boolean isOfflineMode() {
        boolean z;
        int i;
        if (b < 0) {
            String releaseType = LoggerFactory.getLogContext().getReleaseType();
            if ("dev".equals(releaseType) || "test".equals(releaseType) || LogContext.RELEASETYPE_TESTPRE.equals(releaseType)) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                i = 1;
            } else {
                i = 0;
            }
            b = i;
        }
        if (b == 1) {
            return true;
        }
        return false;
    }

    public static boolean isWalletProcessRuning(Context context) {
        try {
            for (RunningAppProcessInfo info : ((ActivityManager) context.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses()) {
                if (!"com.eg.android.AlipayGphone".equals(info.processName)) {
                    if ("com.eg.android.AlipayGphoneRC".equals(info.processName)) {
                    }
                }
                return true;
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error((String) "LoggingUtil", e);
        }
        return false;
    }

    public static String stackTraceToString(String suffix) {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        if (elements == null) {
            return "";
        }
        StringBuilder buf = new StringBuilder();
        buf.append(suffix).append("-StackTrace:");
        for (StackTraceElement element : elements) {
            buf.append("###\tat ").append(element);
        }
        buf.append("###");
        return buf.toString();
    }

    public static String throwableToString(Throwable throwable) {
        return Log.getStackTraceString(throwable);
    }

    public static Pair<String, String> backTrackInvoker() {
        return backTrackInvoker(2);
    }

    public static Pair<String, String> backTrackInvoker(int level) {
        StackTraceElement[] tracks = Thread.currentThread().getStackTrace();
        if (tracks == null) {
            return Pair.create("", "");
        }
        if (level <= 0) {
            level = 1;
        }
        int level2 = level + 3;
        if (level2 >= tracks.length) {
            level2 = tracks.length - 1;
        }
        StackTraceElement track = tracks[level2];
        if (track == null) {
            return Pair.create("", "");
        }
        return Pair.create(track.getClassName(), track.getMethodName());
    }

    public static String[] obtainThreadsStackTrace() {
        return getThreadsStackTrace(true);
    }

    public static String acquireThreadsStackTrace() {
        return getThreadsStackTrace(false)[0];
    }

    public static String[] getThreadsStackTrace(boolean isIncludeThreadsName) {
        StringBuilder stackTracesBuf = new StringBuilder(40000);
        StringBuilder threadsNameBuf = null;
        boolean isFirstThread = true;
        if (isIncludeThreadsName) {
            threadsNameBuf = new StringBuilder();
        }
        try {
            for (Entry entry : Thread.getAllStackTraces().entrySet()) {
                StackTraceElement[] stackTraces = (StackTraceElement[]) entry.getValue();
                String threadName = ((Thread) entry.getKey()).getName();
                stackTracesBuf.append(10);
                stackTracesBuf.append("ThreadName=").append(threadName);
                stackTracesBuf.append("\n");
                if (threadsNameBuf != null) {
                    if (isFirstThread) {
                        isFirstThread = false;
                    } else {
                        threadsNameBuf.append(MergeUtil.SEPARATOR_KV);
                    }
                    threadsNameBuf.append(threadName);
                }
                for (StackTraceElement valueOf : stackTraces) {
                    stackTracesBuf.append(String.valueOf(valueOf));
                    stackTracesBuf.append("\n");
                }
                stackTracesBuf.append("\n");
            }
        } catch (Throwable t) {
            Log.e("LoggingUtil", "getThreadsStackTrace", t);
        }
        return new String[]{stackTracesBuf.toString(), String.valueOf(threadsNameBuf)};
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0026 A[SYNTHETIC, Splitter:B:16:0x0026] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static byte[] gzipDataByBytes(byte[] r7, int r8, int r9) {
        /*
            java.io.ByteArrayOutputStream r0 = new java.io.ByteArrayOutputStream
            r0.<init>()
            r2 = 0
            java.util.zip.GZIPOutputStream r3 = new java.util.zip.GZIPOutputStream     // Catch:{ Throwable -> 0x001c }
            r3.<init>(r0)     // Catch:{ Throwable -> 0x001c }
            r3.write(r7, r8, r9)     // Catch:{ Throwable -> 0x0038, all -> 0x0035 }
            r3.finish()     // Catch:{ Throwable -> 0x0038, all -> 0x0035 }
            byte[] r4 = r0.toByteArray()     // Catch:{ Throwable -> 0x0038, all -> 0x0035 }
            r3.close()     // Catch:{ Throwable -> 0x002d }
        L_0x0018:
            r0.close()     // Catch:{ Throwable -> 0x002f }
        L_0x001b:
            return r4
        L_0x001c:
            r1 = move-exception
        L_0x001d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0023 }
            r5.<init>(r1)     // Catch:{ all -> 0x0023 }
            throw r5     // Catch:{ all -> 0x0023 }
        L_0x0023:
            r5 = move-exception
        L_0x0024:
            if (r2 == 0) goto L_0x0029
            r2.close()     // Catch:{ Throwable -> 0x0031 }
        L_0x0029:
            r0.close()     // Catch:{ Throwable -> 0x0033 }
        L_0x002c:
            throw r5
        L_0x002d:
            r5 = move-exception
            goto L_0x0018
        L_0x002f:
            r5 = move-exception
            goto L_0x001b
        L_0x0031:
            r6 = move-exception
            goto L_0x0029
        L_0x0033:
            r6 = move-exception
            goto L_0x002c
        L_0x0035:
            r5 = move-exception
            r2 = r3
            goto L_0x0024
        L_0x0038:
            r1 = move-exception
            r2 = r3
            goto L_0x001d
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.LoggingUtil.gzipDataByBytes(byte[], int, int):byte[]");
    }

    public static byte[] gzipDataByString(String inputString) {
        try {
            byte[] inputData = inputString.getBytes("UTF-8");
            return gzipDataByBytes(inputData, 0, inputData.length);
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public static String getZhizhiSetting(Context context, String uriString, String defaultVal) {
        if (context == null || TextUtils.isEmpty(uriString)) {
            return defaultVal;
        }
        Cursor cursor = null;
        String targetVal = defaultVal;
        try {
            Cursor cursor2 = context.getContentResolver().query(Uri.parse(uriString), null, null, null, null);
            if (cursor2 != null && cursor2.getCount() > 0) {
                cursor2.moveToFirst();
                targetVal = cursor2.getString(0);
            }
            if (cursor2 != null && !cursor2.isClosed()) {
                try {
                    cursor2.close();
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
        }
        LoggerFactory.getTraceLogger().info("LoggingUtil", "getZhizhiSetting, uri: " + uriString + ", value: " + targetVal);
        return targetVal;
    }

    public static String concatArray(String separator, Object... array) {
        if (array == null || array.length == 0) {
            return "";
        }
        if (array.length == 1) {
            return String.valueOf(array[0]);
        }
        if (array.length == 2) {
            return String.valueOf(array[0]) + separator + String.valueOf(array[1]);
        }
        StringBuilder buf = new StringBuilder();
        boolean isFirst = true;
        for (Object obj : array) {
            if (isFirst) {
                isFirst = false;
            } else {
                buf.append(separator);
            }
            buf.append(obj);
        }
        return buf.toString();
    }

    public static boolean isScreenOn(Context context) {
        try {
            PowerManager pm = (PowerManager) context.getSystemService("power");
            if (pm != null && !pm.isScreenOn()) {
                return false;
            }
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error("LoggingUtil", "isScreenOn error", e);
        }
        return true;
    }

    public static String getSystemProperty(String key, String def) {
        if (TextUtils.isEmpty(key)) {
            return def;
        }
        try {
            return (String) Class.forName("android.os.SystemProperties").getDeclaredMethod("get", new Class[]{String.class, String.class}).invoke(null, new Object[]{key, def});
        } catch (Throwable t) {
            LoggerFactory.getTraceLogger().error("LoggingUtil", "getSystemProperty", t);
            return def;
        }
    }

    public static String formatTimespanToHHmmssSSS(long timespan) {
        if (timespan < 0) {
            return "";
        }
        long second = timespan / 1000;
        long minute = second / 60;
        StringBuilder result = new StringBuilder();
        result.append(minute / 60).append("h:");
        result.append(minute % 60).append("m:");
        result.append(second % 60).append("s:");
        result.append(timespan % 1000);
        return result.toString();
    }

    public static Random getCommonRandom() {
        if (d == null) {
            d = new Random();
        }
        return d;
    }

    public static int randomInteger(int min, int max) {
        return ((int) (getCommonRandom().nextFloat() * ((float) (max - min)))) + min;
    }

    public static String getMdapStyleName(String originalName) {
        return System.currentTimeMillis() + "_" + originalName;
    }

    public static boolean loadLibrary(Context context, String soShortName) {
        String fileName;
        if (TextUtils.isEmpty(soShortName)) {
            return false;
        }
        try {
            System.loadLibrary(soShortName);
            LoggerFactory.getTraceLogger().info("LoggingUtil", "success to System.loadLibrary : " + soShortName);
            return true;
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error("LoggingUtil", "failed to System.load : " + fileName, e);
            return false;
        }
    }

    public static String getTopActivity() {
        String rString = null;
        try {
            List taskList = ((ActivityManager) LoggerFactory.getLogContext().getApplicationContext().getSystemService(WidgetType.ACTIVITY)).getRunningTasks(3);
            if (taskList == null || taskList.size() <= 0) {
                return null;
            }
            for (RunningTaskInfo taskInfo : taskList) {
                if (taskInfo != null && taskInfo.topActivity.getPackageName().equals(LoggerFactory.getProcessInfo().getPackageName())) {
                    rString = taskInfo.topActivity.getClassName();
                }
            }
            return rString;
        } catch (Throwable th) {
            return null;
        }
    }

    public static void fillBufferWithParams(StringBuilder buffer, Map<?, ?> params, FillBufferHandler handler) {
        if (buffer != null && params != null && params.size() != 0) {
            try {
                for (Entry entry : params.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    if (handler != null) {
                        key = handler.handleKey(key);
                        value = handler.handleKey(value);
                        if (key == null) {
                        }
                    }
                    buffer.append(", ").append(key).append(": ").append(value);
                }
            } catch (Throwable t) {
                LoggerFactory.getTraceLogger().error("LoggingUtil", "fillBufferWithParams.outer", t);
            }
        }
    }

    /* JADX WARNING: type inference failed for: r1v1, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r0v0, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v1, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v2 */
    /* JADX WARNING: type inference failed for: r0v3 */
    /* JADX WARNING: type inference failed for: r0v4, types: [java.lang.Object] */
    /* JADX WARNING: type inference failed for: r0v5 */
    /* JADX WARNING: type inference failed for: r0v6 */
    /* JADX WARNING: type inference failed for: r0v7 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r0v2
      assigns: []
      uses: []
      mth insns count: 34
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void fillBufferWithParams(java.lang.StringBuilder r8, android.os.Bundle r9, com.alipay.mobile.common.logging.util.LoggingUtil.FillBufferHandler r10) {
        /*
            if (r8 == 0) goto L_0x000a
            if (r9 == 0) goto L_0x000a
            int r4 = r9.size()
            if (r4 != 0) goto L_0x000b
        L_0x000a:
            return
        L_0x000b:
            java.util.Set r4 = r9.keySet()     // Catch:{ Throwable -> 0x0044 }
            java.util.Iterator r4 = r4.iterator()     // Catch:{ Throwable -> 0x0044 }
        L_0x0013:
            boolean r5 = r4.hasNext()     // Catch:{ Throwable -> 0x0044 }
            if (r5 == 0) goto L_0x000a
            java.lang.Object r1 = r4.next()     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0044 }
            r0 = r1
            java.lang.Object r3 = r9.get(r1)     // Catch:{ Throwable -> 0x0044 }
            if (r10 == 0) goto L_0x0030
            java.lang.Object r0 = r10.handleKey(r0)     // Catch:{ Throwable -> 0x0051 }
            java.lang.Object r3 = r10.handleKey(r3)     // Catch:{ Throwable -> 0x0051 }
        L_0x002e:
            if (r0 == 0) goto L_0x0013
        L_0x0030:
            java.lang.String r5 = ", "
            java.lang.StringBuilder r5 = r8.append(r5)     // Catch:{ Throwable -> 0x0044 }
            java.lang.StringBuilder r5 = r5.append(r0)     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r6 = ": "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ Throwable -> 0x0044 }
            r5.append(r3)     // Catch:{ Throwable -> 0x0044 }
            goto L_0x0013
        L_0x0044:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r4 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r5 = "LoggingUtil"
            java.lang.String r6 = "fillBufferWithParams.outer"
            r4.error(r5, r6, r2)
            goto L_0x000a
        L_0x0051:
            r2 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0044 }
            java.lang.String r6 = "LoggingUtil"
            java.lang.String r7 = "fillBufferWithParams.inner"
            r5.error(r6, r7, r2)     // Catch:{ Throwable -> 0x0044 }
            goto L_0x002e
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.LoggingUtil.fillBufferWithParams(java.lang.StringBuilder, android.os.Bundle, com.alipay.mobile.common.logging.util.LoggingUtil$FillBufferHandler):void");
    }

    public static String fliterChar(String value) {
        if (!TextUtils.isEmpty(value)) {
            return value.replace(MultipartUtility.LINE_FEED, "###").replace("\n", "###").replace("\r", "###");
        }
        return value;
    }

    public static boolean isProcessStartByClickLauncherIcon() {
        if (!LoggerFactory.getProcessInfo().isMainProcess()) {
            return false;
        }
        Map startupReason = LoggerFactory.getProcessInfo().getStartupReason();
        if (startupReason != null) {
            return "com.eg.android.AlipayGphone.AlipayLogin".equals(startupReason.get(ProcessInfo.SR_COMPONENT_NAME));
        }
        return false;
    }
}
