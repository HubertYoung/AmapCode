package com.autonavi.common.tool;

import android.os.Looper;
import android.text.TextUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import java.io.File;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

public class dumpcrash {
    public static final int GETMAPS_FLAG_1 = 1;
    public static final int GETMAPS_FLAG_10 = 16;
    public static final int GETMAPS_FLAG_2 = 2;
    public static final int GETMAPS_FLAG_4 = 4;
    public static final int GETMAPS_FLAG_8 = 8;

    class a {
        File a;
        long b;

        public a(File file, long j) {
            this.a = file;
            this.b = j;
        }
    }

    public static native int getApkFileInfos(String str, String str2, String[] strArr, int[] iArr, int[] iArr2);

    public static native int[] getApkInfo(String str, String str2);

    public static native String getMaps(int i);

    public static native String getNativeThreadBacktrace(int i);

    public static native String getThreadInfos();

    public static native long getUsableSpace(String str);

    public static native void install(String str, String str2);

    public static native void recordKeyValue(String str, String str2);

    public static native void recordLog(String str);

    public static native void setCrashNotifyDir(String str);

    public static native void setCrashNotifyFilePath(String str);

    public static native void setRepotOtherThread(boolean z);

    public static native void uninstall();

    dumpcrash() {
    }

    static {
        try {
            System.loadLibrary(CrashLogUtil.DUMPCRASH_VERSION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void recordInLocal(String str, boolean z) {
        bmr.a(str, null, z);
    }

    static void recordInLocal(String str, long j, boolean z) {
        if (z) {
            bmr.a(str, null, z);
            return;
        }
        Map<Thread, StackTraceElement[]> a2 = bmw.a();
        if (a2.size() != 0) {
            for (Entry next : a2.entrySet()) {
                if (getThreadNativePeerValue((Thread) next.getKey()) == j) {
                    bmr.a(str, (Thread) next.getKey(), z);
                    return;
                }
            }
        }
        bmr.a(str, null, z);
    }

    private static long getThreadNativePeerValue(Thread thread) {
        if (thread == null) {
            return -1;
        }
        try {
            Field declaredField = Thread.class.getDeclaredField("nativePeer");
            declaredField.setAccessible(true);
            return declaredField.getLong(thread);
        } catch (Throwable unused) {
            return -1;
        }
    }

    public static String collectMoreInfo(String str, Thread thread, StackTraceElement[] stackTraceElementArr, boolean z) {
        try {
            StringBuffer stringBuffer = new StringBuffer(str);
            String str2 = null;
            if (z) {
                str2 = getCurrentJavaStackTrace(thread, stackTraceElementArr);
                if (!TextUtils.isEmpty(str2)) {
                    stringBuffer.append(str2);
                }
            }
            if (str.contains("(SIGABRT)")) {
                stringBuffer.append(getOtherJavaStackTrace(thread));
                stringBuffer.append(CrashLogUtil.getLogcat());
                if (str.contains("(SI_USER)")) {
                    stringBuffer.append(readAnrTraces());
                }
            } else if (str.contains("(SIGSEGV)") || str.contains("(SIGBUS)") || str.contains("(SIGILL)")) {
                stringBuffer.append(CrashLogUtil.getLogcat());
                bms.a().d();
                stringBuffer.append("\nFDinfo:\n");
                for (String append : bmx.a(true)) {
                    stringBuffer.append("\t");
                    stringBuffer.append(append);
                    stringBuffer.append("\n");
                }
            }
            String deleteModuleIfError = ModuleCollector.deleteModuleIfError(str, CrashLogUtil.getApplication());
            if (!TextUtils.isEmpty(deleteModuleIfError)) {
                stringBuffer.append(deleteModuleIfError);
            }
            if (!TextUtils.isEmpty(str2)) {
                String deleteModuleIfControllerProxyError = ModuleCollector.deleteModuleIfControllerProxyError(str2, CrashLogUtil.getApplication());
                if (!TextUtils.isEmpty(deleteModuleIfControllerProxyError)) {
                    stringBuffer.append(deleteModuleIfControllerProxyError);
                }
            }
            return stringBuffer.toString();
        } catch (Throwable unused) {
            return str;
        }
    }

    private static String getCurrentJavaStackTrace(Thread thread, StackTraceElement[] stackTraceElementArr) {
        StringBuilder sb = new StringBuilder("\nJavaStack:\n");
        sb.append(getJavaStackTrace(thread, thread, stackTraceElementArr));
        return sb.toString();
    }

    private static String getJavaStackTrace(Thread thread, Thread thread2, StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr == null || stackTraceElementArr.length == 0) {
            if (thread != null) {
                PrintStream printStream = System.out;
                new StringBuilder("dc: TN: ").append(thread.toString());
            }
            if (stackTraceElementArr == null) {
                PrintStream printStream2 = System.out;
            } else {
                PrintStream printStream3 = System.out;
            }
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (thread == thread2) {
            int i2 = 0;
            while (true) {
                if (i2 >= stackTraceElementArr.length) {
                    break;
                }
                int i3 = i2 + 1;
                if (stackTraceElementArr[i2].getMethodName().equals("recordInLocal")) {
                    i2 = i3;
                    break;
                }
                i2 = i3;
            }
            if (i2 < stackTraceElementArr.length) {
                i = i2;
            }
        } else {
            sb.append(thread2.toString());
            sb.append(Token.SEPARATOR);
            sb.append(thread2.getState());
            sb.append("\n");
        }
        while (i < stackTraceElementArr.length) {
            sb.append("  ");
            sb.append(stackTraceElementArr[i].toString());
            sb.append("\n");
            i++;
        }
        return sb.toString();
    }

    private static String getOtherJavaStackTrace(Thread thread) {
        Map<Thread, StackTraceElement[]> a2 = bmw.a();
        if (a2.size() < 2) {
            return "";
        }
        StringBuilder sb = new StringBuilder("\nOtherJavaStack:\n");
        for (Thread next : a2.keySet()) {
            if (!next.equals(thread) && next == Looper.getMainLooper().getThread()) {
                sb.append(getJavaStackTrace(thread, next, a2.get(next)));
            }
        }
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0079, code lost:
        r10.append(r11);
        r10.append("\n");
     */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x00d3  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00f2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String readAnrTraces() {
        /*
            r0 = 0
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00cb }
            java.lang.String r2 = "/data/anr"
            r1.<init>(r2)     // Catch:{ Throwable -> 0x00cb }
            java.io.File[] r1 = r1.listFiles()     // Catch:{ Throwable -> 0x00cb }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x00cb }
            r2.<init>()     // Catch:{ Throwable -> 0x00cb }
            r3 = 0
            if (r1 == 0) goto L_0x0029
            int r4 = r1.length     // Catch:{ Throwable -> 0x00cb }
            r5 = 0
        L_0x0016:
            if (r5 >= r4) goto L_0x0029
            r6 = r1[r5]     // Catch:{ Throwable -> 0x00cb }
            com.autonavi.common.tool.dumpcrash$a r7 = new com.autonavi.common.tool.dumpcrash$a     // Catch:{ Throwable -> 0x00cb }
            long r8 = r6.lastModified()     // Catch:{ Throwable -> 0x00cb }
            r7.<init>(r6, r8)     // Catch:{ Throwable -> 0x00cb }
            r2.add(r7)     // Catch:{ Throwable -> 0x00cb }
            int r5 = r5 + 1
            goto L_0x0016
        L_0x0029:
            int r1 = r2.size()     // Catch:{ Throwable -> 0x00cb }
            com.autonavi.common.tool.dumpcrash$a[] r1 = new com.autonavi.common.tool.dumpcrash.a[r1]     // Catch:{ Throwable -> 0x00cb }
            java.lang.Object[] r1 = r2.toArray(r1)     // Catch:{ Throwable -> 0x00cb }
            com.autonavi.common.tool.dumpcrash$a[] r1 = (com.autonavi.common.tool.dumpcrash.a[]) r1     // Catch:{ Throwable -> 0x00cb }
            com.autonavi.common.tool.dumpcrash$1 r2 = new com.autonavi.common.tool.dumpcrash$1     // Catch:{ Throwable -> 0x00cb }
            r2.<init>()     // Catch:{ Throwable -> 0x00cb }
            java.util.Arrays.sort(r1, r2)     // Catch:{ Throwable -> 0x00cb }
            int r2 = r1.length     // Catch:{ Throwable -> 0x00cb }
            r5 = r0
            r6 = r5
            r4 = 0
        L_0x0041:
            if (r4 >= r2) goto L_0x00d1
            r7 = r1[r4]     // Catch:{ Throwable -> 0x00c9 }
            java.io.File r7 = r7.a     // Catch:{ Throwable -> 0x00c9 }
            java.io.BufferedReader r6 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x00c6 }
            java.io.FileReader r8 = new java.io.FileReader     // Catch:{ Throwable -> 0x00c6 }
            r8.<init>(r7)     // Catch:{ Throwable -> 0x00c6 }
            r6.<init>(r8)     // Catch:{ Throwable -> 0x00c6 }
            r10 = r5
        L_0x0052:
            r5 = 0
            r8 = 0
            r9 = 0
        L_0x0055:
            java.lang.String r11 = r6.readLine()     // Catch:{ IOException -> 0x00b1 }
            if (r11 == 0) goto L_0x00aa
            r12 = 1
            if (r5 != 0) goto L_0x006d
            java.lang.String r13 = "----- pid"
            boolean r13 = r11.startsWith(r13)     // Catch:{ IOException -> 0x00b1 }
            if (r13 == 0) goto L_0x006d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00b1 }
            r5.<init>()     // Catch:{ IOException -> 0x00b1 }
            r10 = r5
            r5 = 1
        L_0x006d:
            if (r5 == 0) goto L_0x0055
            java.lang.String r13 = "----- end"
            boolean r13 = r11.startsWith(r13)     // Catch:{ IOException -> 0x00b1 }
            if (r13 == 0) goto L_0x0082
            if (r9 == 0) goto L_0x0052
            r10.append(r11)     // Catch:{ IOException -> 0x00b1 }
            java.lang.String r5 = "\n"
            r10.append(r5)     // Catch:{ IOException -> 0x00b1 }
            goto L_0x00aa
        L_0x0082:
            if (r8 != 0) goto L_0x009d
            java.lang.String r13 = "Cmd line:"
            boolean r13 = r11.startsWith(r13)     // Catch:{ IOException -> 0x00b1 }
            if (r13 == 0) goto L_0x009d
            bmt r8 = com.autonavi.common.tool.CrashLogUtil.getControler()     // Catch:{ IOException -> 0x00b1 }
            java.lang.String r8 = r8.p()     // Catch:{ IOException -> 0x00b1 }
            boolean r8 = r11.endsWith(r8)     // Catch:{ IOException -> 0x00b1 }
            if (r8 != 0) goto L_0x009b
            r10 = r0
        L_0x009b:
            r9 = r8
            r8 = 1
        L_0x009d:
            if (r8 == 0) goto L_0x00a1
            if (r9 == 0) goto L_0x0055
        L_0x00a1:
            r10.append(r11)     // Catch:{ IOException -> 0x00b1 }
            java.lang.String r11 = "\n"
            r10.append(r11)     // Catch:{ IOException -> 0x00b1 }
            goto L_0x0055
        L_0x00aa:
            r6.close()     // Catch:{ Throwable -> 0x00c2 }
            r5 = r10
            goto L_0x00b6
        L_0x00af:
            r0 = move-exception
            goto L_0x00be
        L_0x00b1:
            r5 = move-exception
            r5.printStackTrace()     // Catch:{ all -> 0x00af }
            goto L_0x00aa
        L_0x00b6:
            if (r5 != 0) goto L_0x00bc
            int r4 = r4 + 1
            r6 = r7
            goto L_0x0041
        L_0x00bc:
            r6 = r7
            goto L_0x00d1
        L_0x00be:
            r6.close()     // Catch:{ Throwable -> 0x00c2 }
            throw r0     // Catch:{ Throwable -> 0x00c2 }
        L_0x00c2:
            r1 = move-exception
            r6 = r7
            r5 = r10
            goto L_0x00ce
        L_0x00c6:
            r1 = move-exception
            r6 = r7
            goto L_0x00ce
        L_0x00c9:
            r1 = move-exception
            goto L_0x00ce
        L_0x00cb:
            r1 = move-exception
            r5 = r0
            r6 = r5
        L_0x00ce:
            r1.printStackTrace()
        L_0x00d1:
            if (r5 == 0) goto L_0x00f2
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "\nANR("
            r0.<init>(r1)
            java.lang.String r1 = r6.getPath()
            r0.append(r1)
            java.lang.String r1 = "):\n"
            r0.append(r1)
            java.lang.String r1 = r5.toString()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            return r0
        L_0x00f2:
            java.lang.String r0 = ""
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.dumpcrash.readAnrTraces():java.lang.String");
    }
}
