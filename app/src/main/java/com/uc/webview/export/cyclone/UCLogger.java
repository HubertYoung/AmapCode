package com.uc.webview.export.cyclone;

import android.os.AsyncTask;
import android.util.Pair;
import android.webkit.ValueCallback;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

@Constant
/* compiled from: ProGuard */
public class UCLogger {
    private static final Class[] PARAMS_WITHOUT_THROWABLE = {String.class, String.class};
    private static final Class[] PARAMS_WITH_THROWABLE = {String.class, String.class, Throwable.class};
    private static boolean bAllowAllLevel = false;
    private static boolean bAllowAllTag = false;
    private static String sAllowLevels = "[all]";
    private static String sAllowTags = "[all]";
    /* access modifiers changed from: private */
    public static AsyncTask<Object, Object, Object> sAsyncTask = null;
    private static ArrayList<Pair<Pair<String, String>, UCLogger>> sCachedLoggers = null;
    /* access modifiers changed from: private */
    public static ValueCallback<Object[]> sCallbackChannel = null;
    private static boolean sEnabled = false;
    /* access modifiers changed from: private */
    public static final ConcurrentLinkedQueue<Object[]> sLogItems = new ConcurrentLinkedQueue<>();
    private static Class<?> sLogcatChannel;
    private String mLevel;
    private String mTag;

    private UCLogger(String str, String str2) {
        this.mLevel = str;
        StringBuilder sb = new StringBuilder();
        sb.append(UCCyclone.logExtraTag);
        sb.append(str2);
        this.mTag = sb.toString();
    }

    public static int createToken(String str, String str2) {
        if (sCachedLoggers == null) {
            synchronized (UCLogger.class) {
                try {
                    if (sCachedLoggers == null) {
                        sCachedLoggers = new ArrayList<>(20);
                    }
                }
            }
        }
        int i = -1;
        try {
            synchronized (sCachedLoggers) {
                if (sCachedLoggers.add(new Pair(new Pair(str, str2), create(str, str2)))) {
                    i = sCachedLoggers.size() - 1;
                }
            }
        } catch (Throwable unused) {
        }
        return i;
    }

    public static boolean print(int i, String str, Throwable... thArr) {
        if (i >= 0 && sEnabled) {
            UCLogger uCLogger = (UCLogger) sCachedLoggers.get(i).second;
            if (uCLogger != null) {
                uCLogger.print(str, thArr);
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0035, code lost:
        if (r0.contains(r1.toString()) != false) goto L_0x0037;
     */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0042  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.uc.webview.export.cyclone.UCLogger create(java.lang.String r3, java.lang.String r4) {
        /*
            boolean r0 = sEnabled
            if (r0 == 0) goto L_0x0039
            java.lang.Class<?> r0 = sLogcatChannel
            if (r0 != 0) goto L_0x000c
            android.webkit.ValueCallback<java.lang.Object[]> r0 = sCallbackChannel
            if (r0 == 0) goto L_0x0039
        L_0x000c:
            boolean r0 = bAllowAllLevel
            if (r0 != 0) goto L_0x0018
            java.lang.String r0 = sAllowLevels
            boolean r0 = r0.contains(r3)
            if (r0 == 0) goto L_0x0039
        L_0x0018:
            boolean r0 = bAllowAllTag
            if (r0 != 0) goto L_0x0037
            java.lang.String r0 = sAllowTags
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "["
            r1.<init>(r2)
            r1.append(r4)
            java.lang.String r2 = "]"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            boolean r0 = r0.contains(r1)
            if (r0 == 0) goto L_0x0039
        L_0x0037:
            r0 = 1
            goto L_0x003a
        L_0x0039:
            r0 = 0
        L_0x003a:
            if (r0 == 0) goto L_0x0042
            com.uc.webview.export.cyclone.UCLogger r0 = new com.uc.webview.export.cyclone.UCLogger
            r0.<init>(r3, r4)
            return r0
        L_0x0042:
            r3 = 0
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.UCLogger.create(java.lang.String, java.lang.String):com.uc.webview.export.cyclone.UCLogger");
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:23:0x003d */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0041 A[Catch:{ Throwable -> 0x0088 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void print(java.lang.String r10, java.lang.Throwable... r11) {
        /*
            r9 = this;
            boolean r0 = sEnabled
            if (r0 != 0) goto L_0x0005
            return
        L_0x0005:
            r0 = 0
            if (r11 == 0) goto L_0x0012
            int r1 = r11.length
            if (r1 <= 0) goto L_0x0012
            r1 = r11[r0]
            if (r1 == 0) goto L_0x0012
            r11 = r11[r0]
            goto L_0x0013
        L_0x0012:
            r11 = 0
        L_0x0013:
            r1 = 3
            r2 = 2
            r3 = 1
            java.lang.Class<?> r4 = sLogcatChannel     // Catch:{ Throwable -> 0x003d }
            if (r4 == 0) goto L_0x003d
            java.lang.Class<?> r4 = sLogcatChannel     // Catch:{ Throwable -> 0x003d }
            java.lang.String r5 = r9.mLevel     // Catch:{ Throwable -> 0x003d }
            if (r11 == 0) goto L_0x0023
            java.lang.Class[] r6 = PARAMS_WITH_THROWABLE     // Catch:{ Throwable -> 0x003d }
            goto L_0x0025
        L_0x0023:
            java.lang.Class[] r6 = PARAMS_WITHOUT_THROWABLE     // Catch:{ Throwable -> 0x003d }
        L_0x0025:
            if (r11 == 0) goto L_0x0032
            java.lang.Object[] r7 = new java.lang.Object[r1]     // Catch:{ Throwable -> 0x003d }
            java.lang.String r8 = r9.mTag     // Catch:{ Throwable -> 0x003d }
            r7[r0] = r8     // Catch:{ Throwable -> 0x003d }
            r7[r3] = r10     // Catch:{ Throwable -> 0x003d }
            r7[r2] = r11     // Catch:{ Throwable -> 0x003d }
            goto L_0x003a
        L_0x0032:
            java.lang.Object[] r7 = new java.lang.Object[r2]     // Catch:{ Throwable -> 0x003d }
            java.lang.String r8 = r9.mTag     // Catch:{ Throwable -> 0x003d }
            r7[r0] = r8     // Catch:{ Throwable -> 0x003d }
            r7[r3] = r10     // Catch:{ Throwable -> 0x003d }
        L_0x003a:
            com.uc.webview.export.cyclone.UCCyclone.invoke(r4, r5, r6, r7)     // Catch:{ Throwable -> 0x003d }
        L_0x003d:
            android.webkit.ValueCallback<java.lang.Object[]> r4 = sCallbackChannel     // Catch:{ Throwable -> 0x0088 }
            if (r4 == 0) goto L_0x0087
            java.util.concurrent.ConcurrentLinkedQueue<java.lang.Object[]> r4 = sLogItems     // Catch:{ Throwable -> 0x0088 }
            r5 = 7
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0088 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0088 }
            java.lang.Long r6 = java.lang.Long.valueOf(r6)     // Catch:{ Throwable -> 0x0088 }
            r5[r0] = r6     // Catch:{ Throwable -> 0x0088 }
            int r6 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0088 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Throwable -> 0x0088 }
            r5[r3] = r6     // Catch:{ Throwable -> 0x0088 }
            int r3 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x0088 }
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch:{ Throwable -> 0x0088 }
            r5[r2] = r3     // Catch:{ Throwable -> 0x0088 }
            java.lang.String r2 = r9.mLevel     // Catch:{ Throwable -> 0x0088 }
            r5[r1] = r2     // Catch:{ Throwable -> 0x0088 }
            r1 = 4
            java.lang.String r2 = r9.mTag     // Catch:{ Throwable -> 0x0088 }
            r5[r1] = r2     // Catch:{ Throwable -> 0x0088 }
            r1 = 5
            r5[r1] = r10     // Catch:{ Throwable -> 0x0088 }
            r10 = 6
            r5[r10] = r11     // Catch:{ Throwable -> 0x0088 }
            r4.add(r5)     // Catch:{ Throwable -> 0x0088 }
            android.os.AsyncTask<java.lang.Object, java.lang.Object, java.lang.Object> r10 = sAsyncTask     // Catch:{ Throwable -> 0x0088 }
            if (r10 != 0) goto L_0x0087
            com.uc.webview.export.cyclone.UCLogger$1 r10 = new com.uc.webview.export.cyclone.UCLogger$1     // Catch:{ Throwable -> 0x0088 }
            r10.<init>()     // Catch:{ Throwable -> 0x0088 }
            java.lang.Object[] r11 = new java.lang.Object[r0]     // Catch:{ Throwable -> 0x0088 }
            android.os.AsyncTask r10 = r10.execute(r11)     // Catch:{ Throwable -> 0x0088 }
            sAsyncTask = r10     // Catch:{ Throwable -> 0x0088 }
        L_0x0087:
            return
        L_0x0088:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.UCLogger.print(java.lang.String, java.lang.Throwable[]):void");
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(15:2|3|4|(1:9)(1:8)|10|(3:12|13|(11:15|(1:17)|18|20|21|(1:23)(1:24)|(1:26)(1:27)|28|29|30|(4:32|71|46|47)(2:53|54)))|19|20|21|(0)(0)|(0)(0)|28|29|30|(0)(0)) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:20:0x0043 */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0050  */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006f A[Catch:{ Throwable -> 0x00ba }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00b8 A[DONT_GENERATE] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void setup(java.lang.Object[] r8) {
        /*
            java.lang.Class<com.uc.webview.export.cyclone.UCLogger> r0 = com.uc.webview.export.cyclone.UCLogger.class
            monitor-enter(r0)
            r1 = 0
            r2 = r8[r1]     // Catch:{ all -> 0x00bc }
            java.lang.Boolean r2 = (java.lang.Boolean) r2     // Catch:{ all -> 0x00bc }
            r3 = 1
            r4 = r8[r3]     // Catch:{ all -> 0x00bc }
            java.lang.Boolean r4 = (java.lang.Boolean) r4     // Catch:{ all -> 0x00bc }
            r5 = 2
            r5 = r8[r5]     // Catch:{ all -> 0x00bc }
            android.webkit.ValueCallback r5 = (android.webkit.ValueCallback) r5     // Catch:{ all -> 0x00bc }
            r6 = 3
            r6 = r8[r6]     // Catch:{ all -> 0x00bc }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ all -> 0x00bc }
            r7 = 4
            r8 = r8[r7]     // Catch:{ all -> 0x00bc }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ all -> 0x00bc }
            if (r2 == 0) goto L_0x0027
            boolean r2 = r2.booleanValue()     // Catch:{ all -> 0x00bc }
            if (r2 == 0) goto L_0x0027
            sEnabled = r3     // Catch:{ all -> 0x00bc }
            goto L_0x0029
        L_0x0027:
            sEnabled = r1     // Catch:{ all -> 0x00bc }
        L_0x0029:
            r1 = 0
            if (r4 == 0) goto L_0x0041
            boolean r2 = r4.booleanValue()     // Catch:{ Throwable -> 0x0043 }
            if (r2 == 0) goto L_0x0041
            boolean r2 = r4.booleanValue()     // Catch:{ Throwable -> 0x0043 }
            if (r2 == 0) goto L_0x003e
            java.lang.String r1 = "android.util.Log"
            java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Throwable -> 0x0043 }
        L_0x003e:
            sLogcatChannel = r1     // Catch:{ Throwable -> 0x0043 }
            goto L_0x0043
        L_0x0041:
            sLogcatChannel = r1     // Catch:{ Throwable -> 0x0043 }
        L_0x0043:
            sCallbackChannel = r5     // Catch:{ all -> 0x00bc }
            if (r6 == 0) goto L_0x004a
            sAllowLevels = r6     // Catch:{ all -> 0x00bc }
            goto L_0x004e
        L_0x004a:
            java.lang.String r1 = ""
            sAllowLevels = r1     // Catch:{ all -> 0x00bc }
        L_0x004e:
            if (r8 == 0) goto L_0x0053
            sAllowTags = r8     // Catch:{ all -> 0x00bc }
            goto L_0x0057
        L_0x0053:
            java.lang.String r8 = ""
            sAllowTags = r8     // Catch:{ all -> 0x00bc }
        L_0x0057:
            java.lang.String r8 = sAllowLevels     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = "[all]"
            boolean r8 = r8.contains(r1)     // Catch:{ all -> 0x00bc }
            bAllowAllLevel = r8     // Catch:{ all -> 0x00bc }
            java.lang.String r8 = sAllowTags     // Catch:{ all -> 0x00bc }
            java.lang.String r1 = "[all]"
            boolean r8 = r8.contains(r1)     // Catch:{ all -> 0x00bc }
            bAllowAllTag = r8     // Catch:{ all -> 0x00bc }
            java.util.ArrayList<android.util.Pair<android.util.Pair<java.lang.String, java.lang.String>, com.uc.webview.export.cyclone.UCLogger>> r8 = sCachedLoggers     // Catch:{ Throwable -> 0x00ba }
            if (r8 == 0) goto L_0x00b8
            java.util.ArrayList<android.util.Pair<android.util.Pair<java.lang.String, java.lang.String>, com.uc.webview.export.cyclone.UCLogger>> r8 = sCachedLoggers     // Catch:{ Throwable -> 0x00ba }
            monitor-enter(r8)     // Catch:{ Throwable -> 0x00ba }
            java.util.ArrayList<android.util.Pair<android.util.Pair<java.lang.String, java.lang.String>, com.uc.webview.export.cyclone.UCLogger>> r1 = sCachedLoggers     // Catch:{ all -> 0x00b5 }
            int r1 = r1.size()     // Catch:{ all -> 0x00b5 }
            int r1 = r1 - r3
        L_0x0079:
            if (r1 < 0) goto L_0x00b2
            java.util.ArrayList<android.util.Pair<android.util.Pair<java.lang.String, java.lang.String>, com.uc.webview.export.cyclone.UCLogger>> r2 = sCachedLoggers     // Catch:{ all -> 0x00b5 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x00b5 }
            android.util.Pair r2 = (android.util.Pair) r2     // Catch:{ all -> 0x00b5 }
            java.lang.Object r3 = r2.first     // Catch:{ all -> 0x00b5 }
            android.util.Pair r3 = (android.util.Pair) r3     // Catch:{ all -> 0x00b5 }
            java.lang.Object r3 = r3.first     // Catch:{ all -> 0x00b5 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ all -> 0x00b5 }
            java.lang.Object r4 = r2.first     // Catch:{ all -> 0x00b5 }
            android.util.Pair r4 = (android.util.Pair) r4     // Catch:{ all -> 0x00b5 }
            java.lang.Object r4 = r4.second     // Catch:{ all -> 0x00b5 }
            java.lang.String r4 = (java.lang.String) r4     // Catch:{ all -> 0x00b5 }
            com.uc.webview.export.cyclone.UCLogger r3 = create(r3, r4)     // Catch:{ all -> 0x00b5 }
            java.lang.Object r4 = r2.second     // Catch:{ all -> 0x00b5 }
            if (r4 != 0) goto L_0x009d
            if (r3 != 0) goto L_0x00a3
        L_0x009d:
            java.lang.Object r4 = r2.second     // Catch:{ all -> 0x00b5 }
            if (r4 == 0) goto L_0x00af
            if (r3 != 0) goto L_0x00af
        L_0x00a3:
            java.util.ArrayList<android.util.Pair<android.util.Pair<java.lang.String, java.lang.String>, com.uc.webview.export.cyclone.UCLogger>> r4 = sCachedLoggers     // Catch:{ all -> 0x00b5 }
            android.util.Pair r5 = new android.util.Pair     // Catch:{ all -> 0x00b5 }
            java.lang.Object r2 = r2.first     // Catch:{ all -> 0x00b5 }
            r5.<init>(r2, r3)     // Catch:{ all -> 0x00b5 }
            r4.set(r1, r5)     // Catch:{ all -> 0x00b5 }
        L_0x00af:
            int r1 = r1 + -1
            goto L_0x0079
        L_0x00b2:
            monitor-exit(r8)     // Catch:{ all -> 0x00b5 }
            monitor-exit(r0)
            return
        L_0x00b5:
            r1 = move-exception
            monitor-exit(r8)     // Catch:{ all -> 0x00b5 }
            throw r1     // Catch:{ Throwable -> 0x00ba }
        L_0x00b8:
            monitor-exit(r0)
            return
        L_0x00ba:
            monitor-exit(r0)
            return
        L_0x00bc:
            r8 = move-exception
            monitor-exit(r0)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.cyclone.UCLogger.setup(java.lang.Object[]):void");
    }
}
