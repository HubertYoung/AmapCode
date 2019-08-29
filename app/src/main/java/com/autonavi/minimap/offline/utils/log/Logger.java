package com.autonavi.minimap.offline.utils.log;

public abstract class Logger {
    public static final boolean CREATE_LOG_FILE = true;
    public static final boolean DBG = false;
    public static final String LINE = "------->";
    public static final String TAG = null;
    private static Class<? extends Logger> mLoggerClass;
    private String mTag;

    public void d(String str) {
    }

    public void d(String str, Throwable th) {
    }

    /* access modifiers changed from: protected */
    public abstract void debug(String str);

    /* access modifiers changed from: protected */
    public abstract void debug(String str, Throwable th);

    public void e(String str) {
    }

    public void e(String str, Throwable th) {
    }

    /* access modifiers changed from: protected */
    public abstract void error(String str);

    /* access modifiers changed from: protected */
    public abstract void error(String str, Throwable th);

    public void i(String str) {
    }

    public void i(String str, Throwable th) {
    }

    /* access modifiers changed from: protected */
    public abstract void info(String str);

    /* access modifiers changed from: protected */
    public abstract void info(String str, Throwable th);

    public void w(String str) {
    }

    public void w(String str, Throwable th) {
    }

    /* access modifiers changed from: protected */
    public abstract void warn(String str);

    /* access modifiers changed from: protected */
    public abstract void warn(String str, Throwable th);

    public Logger() {
    }

    public Logger(String str) {
        this.mTag = str;
    }

    public static void registerLogger(Class<? extends Logger> cls) {
        mLoggerClass = cls;
    }

    public static void unregisterLogger() {
        mLoggerClass = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:18:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.autonavi.minimap.offline.utils.log.Logger getLogger(java.lang.String r3) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "["
            r0.<init>(r1)
            r0.append(r3)
            java.lang.String r3 = "]"
            r0.append(r3)
            java.lang.String r3 = r0.toString()
            java.lang.Class<? extends com.autonavi.minimap.offline.utils.log.Logger> r0 = mLoggerClass
            r1 = 0
            if (r0 == 0) goto L_0x0037
            java.lang.Class<? extends com.autonavi.minimap.offline.utils.log.Logger> r0 = mLoggerClass     // Catch:{ InstantiationException -> 0x0033, IllegalAccessException -> 0x002e }
            java.lang.Object r0 = r0.newInstance()     // Catch:{ InstantiationException -> 0x0033, IllegalAccessException -> 0x002e }
            com.autonavi.minimap.offline.utils.log.Logger r0 = (com.autonavi.minimap.offline.utils.log.Logger) r0     // Catch:{ InstantiationException -> 0x0033, IllegalAccessException -> 0x002e }
            r0.mTag = r3     // Catch:{ InstantiationException -> 0x0029, IllegalAccessException -> 0x0024 }
            r1 = r0
            goto L_0x0037
        L_0x0024:
            r1 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
            goto L_0x002f
        L_0x0029:
            r1 = move-exception
            r2 = r1
            r1 = r0
            r0 = r2
            goto L_0x0034
        L_0x002e:
            r0 = move-exception
        L_0x002f:
            r0.printStackTrace()
            goto L_0x0037
        L_0x0033:
            r0 = move-exception
        L_0x0034:
            r0.printStackTrace()
        L_0x0037:
            if (r1 != 0) goto L_0x003e
            com.autonavi.minimap.offline.utils.log.Log r1 = new com.autonavi.minimap.offline.utils.log.Log
            r1.<init>(r3)
        L_0x003e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.offline.utils.log.Logger.getLogger(java.lang.String):com.autonavi.minimap.offline.utils.log.Logger");
    }

    public String getTag() {
        return this.mTag;
    }
}
