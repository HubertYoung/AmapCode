package com.alipay.mobile.quinox.log;

import com.alipay.mobile.quinox.utils.ReflectUtil;
import com.alipay.mobile.quinox.utils.StringUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class Log {
    private static AndroidLogger sAndroidLogger = AndroidLogger.getInstance();
    /* access modifiers changed from: private */
    public static J2seLogger sJ2seLogger = J2seLogger.getInstance();
    private static Logger sTargetLogger;

    static class AndroidLogger extends Logger {
        public static final String ANDROID_UTIL_LOG = "android.util.Log";
        private static final String TAG = "AndroidLogger";
        private static AndroidLogger instance;
        private Method[] LEVEL_TO_METHOD = new Method[LEVEL_TO_LVL.length];
        private Method METHOD_GET_STACK_TRACE_STRING;

        /* JADX WARNING: Can't wrap try/catch for region: R(2:4|5) */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0051, code lost:
            r0 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0052, code lost:
            com.alipay.mobile.quinox.log.Log.J2seLogger.getInstance().e((java.lang.String) TAG, r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:15:0x005b, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:5:?, code lost:
            com.alipay.mobile.quinox.log.Log.J2seLogger.getInstance().e((java.lang.String) TAG, (java.lang.String) "Failed to find class: android.util.Log");
         */
        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:4:0x0013 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private AndroidLogger() {
            /*
                r9 = this;
                r9.<init>()
                java.lang.String[] r0 = LEVEL_TO_LVL
                int r0 = r0.length
                java.lang.reflect.Method[] r0 = new java.lang.reflect.Method[r0]
                r9.LEVEL_TO_METHOD = r0
                r0 = 0
                java.lang.String r1 = "android.util.Log"
                java.lang.Class r1 = java.lang.Class.forName(r1)     // Catch:{ Throwable -> 0x0013 }
                r0 = r1
                goto L_0x001e
            L_0x0013:
                com.alipay.mobile.quinox.log.Log$J2seLogger r1 = com.alipay.mobile.quinox.log.Log.J2seLogger.getInstance()     // Catch:{ Throwable -> 0x0051 }
                java.lang.String r2 = "AndroidLogger"
                java.lang.String r3 = "Failed to find class: android.util.Log"
                r1.e(r2, r3)     // Catch:{ Throwable -> 0x0051 }
            L_0x001e:
                if (r0 == 0) goto L_0x0050
                java.lang.String[] r1 = LEVEL_TO_LVL     // Catch:{ Throwable -> 0x0051 }
                int r1 = r1.length     // Catch:{ Throwable -> 0x0051 }
                r2 = 0
                r3 = 0
            L_0x0025:
                r4 = 1
                if (r3 >= r1) goto L_0x0042
                java.lang.reflect.Method[] r5 = r9.LEVEL_TO_METHOD     // Catch:{ Throwable -> 0x0051 }
                java.lang.String[] r6 = LEVEL_TO_LVL     // Catch:{ Throwable -> 0x0051 }
                r6 = r6[r3]     // Catch:{ Throwable -> 0x0051 }
                r7 = 2
                java.lang.Class[] r7 = new java.lang.Class[r7]     // Catch:{ Throwable -> 0x0051 }
                java.lang.Class<java.lang.String> r8 = java.lang.String.class
                r7[r2] = r8     // Catch:{ Throwable -> 0x0051 }
                java.lang.Class<java.lang.String> r8 = java.lang.String.class
                r7[r4] = r8     // Catch:{ Throwable -> 0x0051 }
                java.lang.reflect.Method r4 = com.alipay.mobile.quinox.utils.ReflectUtil.getMethod(r0, r6, r7)     // Catch:{ Throwable -> 0x0051 }
                r5[r3] = r4     // Catch:{ Throwable -> 0x0051 }
                int r3 = r3 + 1
                goto L_0x0025
            L_0x0042:
                java.lang.String r1 = "getStackTraceString"
                java.lang.Class[] r3 = new java.lang.Class[r4]     // Catch:{ Throwable -> 0x0051 }
                java.lang.Class<java.lang.Throwable> r4 = java.lang.Throwable.class
                r3[r2] = r4     // Catch:{ Throwable -> 0x0051 }
                java.lang.reflect.Method r0 = com.alipay.mobile.quinox.utils.ReflectUtil.getMethod(r0, r1, r3)     // Catch:{ Throwable -> 0x0051 }
                r9.METHOD_GET_STACK_TRACE_STRING = r0     // Catch:{ Throwable -> 0x0051 }
            L_0x0050:
                return
            L_0x0051:
                r0 = move-exception
                com.alipay.mobile.quinox.log.Log$J2seLogger r1 = com.alipay.mobile.quinox.log.Log.J2seLogger.getInstance()
                java.lang.String r2 = "AndroidLogger"
                r1.e(r2, r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.quinox.log.Log.AndroidLogger.<init>():void");
        }

        public static AndroidLogger getInstance() {
            if (instance == null) {
                synchronized (AndroidLogger.class) {
                    if (instance == null) {
                        instance = new AndroidLogger();
                    }
                }
            }
            return instance;
        }

        public int verbose(String str, String str2) {
            return invoke(this.LEVEL_TO_METHOD[0], str, str2);
        }

        public int debug(String str, String str2) {
            return invoke(this.LEVEL_TO_METHOD[1], str, str2);
        }

        public int info(String str, String str2) {
            return invoke(this.LEVEL_TO_METHOD[2], str, str2);
        }

        public int warn(String str, String str2) {
            return invoke(this.LEVEL_TO_METHOD[3], str, str2);
        }

        public int error(String str, String str2) {
            return invoke(this.LEVEL_TO_METHOD[4], str, str2);
        }

        public String getStackTraceString(Throwable th) {
            String str = "";
            if (th == null) {
                return str;
            }
            try {
                if (this.METHOD_GET_STACK_TRACE_STRING != null) {
                    str = (String) this.METHOD_GET_STACK_TRACE_STRING.invoke(null, new Object[]{th});
                }
            } catch (Throwable th2) {
                Log.sJ2seLogger.w((String) TAG, th2);
            }
            return StringUtil.isEmpty(str) ? th.getMessage() : str;
        }

        private static int invoke(Method method, Object... objArr) {
            int intValue;
            if (method != null) {
                try {
                    synchronized (AndroidLogger.class) {
                        intValue = ((Integer) method.invoke(null, objArr)).intValue();
                    }
                    return intValue;
                } catch (Throwable th) {
                    J2seLogger.getInstance().e((String) TAG, th);
                }
            }
            return -1;
        }
    }

    public static class J2seLogger extends Logger {
        private static J2seLogger instance;
        private static DateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss:SSS", Locale.US);
        StringBuilder buf = new StringBuilder();

        private J2seLogger() {
        }

        public static J2seLogger getInstance() {
            if (instance == null) {
                synchronized (J2seLogger.class) {
                    if (instance == null) {
                        instance = new J2seLogger();
                    }
                }
            }
            return instance;
        }

        public int verbose(String str, String str2) {
            return println(LEVEL_TO_LVL[0], str, str2);
        }

        public int debug(String str, String str2) {
            return println(LEVEL_TO_LVL[1], str, str2);
        }

        public int info(String str, String str2) {
            return println(LEVEL_TO_LVL[2], str, str2);
        }

        public int warn(String str, String str2) {
            return println(LEVEL_TO_LVL[3], str, str2);
        }

        public int error(String str, String str2) {
            return println(LEVEL_TO_LVL[4], str, str2);
        }

        public String getStackTraceString(Throwable th) {
            if (th == null) {
                return "";
            }
            try {
                StringWriter stringWriter = new StringWriter();
                PrintWriter printWriter = new PrintWriter(stringWriter);
                th.printStackTrace(printWriter);
                printWriter.close();
                return stringWriter.toString();
            } catch (Throwable unused) {
                return th.getMessage();
            }
        }

        private synchronized int println(String str, String str2, String str3) {
            int i;
            this.buf.append(sDateFormat.format(new Date()));
            StringBuilder sb = this.buf;
            sb.append(" [");
            sb.append(str);
            sb.append("] [");
            sb.append(str2);
            sb.append("] : ");
            if (str3 != null && str3.length() > 0) {
                this.buf.append(str3);
            }
            i = 0;
            try {
                ReflectUtil.invokeMethod(ReflectUtil.getFieldValue(System.class, (String) "out"), (String) "println", new Class[]{String.class}, new Object[]{this.buf.toString()});
                this.buf.delete(0, this.buf.length());
            } catch (Throwable th) {
                this.buf.delete(0, this.buf.length());
                throw th;
            }
            return i;
        }
    }

    private Log() {
    }

    public static void setLogger(Logger logger) {
        sTargetLogger = logger;
    }

    public static void setLogLevel(int i) {
        if (sTargetLogger != null) {
            sTargetLogger.setLogLevel(i);
        }
        sAndroidLogger.setLogLevel(i);
        sJ2seLogger.setLogLevel(i);
    }

    public static void v(String str, String str2) {
        if ((sTargetLogger == null || -1 == sTargetLogger.v(str, str2)) && -1 == sAndroidLogger.v(str, str2)) {
            sJ2seLogger.v(str, str2);
        }
    }

    public static void v(String str, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.v(str, th)) && -1 == sAndroidLogger.v(str, th)) {
            sJ2seLogger.v(str, th);
        }
    }

    public static void v(String str, String str2, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.v(str, str2, th)) && -1 == sAndroidLogger.v(str, str2, th)) {
            sJ2seLogger.v(str, str2, th);
        }
    }

    public static void d(String str, String str2) {
        if ((sTargetLogger == null || -1 == sTargetLogger.d(str, str2)) && -1 == sAndroidLogger.d(str, str2)) {
            sJ2seLogger.d(str, str2);
        }
    }

    public static void d(String str, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.d(str, th)) && -1 == sAndroidLogger.d(str, th)) {
            sJ2seLogger.d(str, th);
        }
    }

    public static void d(String str, String str2, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.d(str, str2, th)) && -1 == sAndroidLogger.d(str, str2, th)) {
            sJ2seLogger.d(str, str2, th);
        }
    }

    public static void i(String str, String str2) {
        if ((sTargetLogger == null || -1 == sTargetLogger.i(str, str2)) && -1 == sAndroidLogger.i(str, str2)) {
            sJ2seLogger.i(str, str2);
        }
    }

    public static void i(String str, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.i(str, th)) && -1 == sAndroidLogger.i(str, th)) {
            sJ2seLogger.i(str, th);
        }
    }

    public static void i(String str, String str2, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.i(str, str2, th)) && -1 == sAndroidLogger.i(str, str2, th)) {
            sJ2seLogger.i(str, str2, th);
        }
    }

    public static void w(String str, String str2) {
        if ((sTargetLogger == null || -1 == sTargetLogger.w(str, str2)) && -1 == sAndroidLogger.w(str, str2)) {
            sJ2seLogger.w(str, str2);
        }
    }

    public static void w(String str, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.w(str, th)) && -1 == sAndroidLogger.w(str, th)) {
            sJ2seLogger.w(str, th);
        }
    }

    public static void w(String str, String str2, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.w(str, str2, th)) && -1 == sAndroidLogger.w(str, str2, th)) {
            sJ2seLogger.w(str, str2, th);
        }
    }

    public static void e(String str, String str2) {
        if ((sTargetLogger == null || -1 == sTargetLogger.e(str, str2)) && -1 == sAndroidLogger.e(str, str2)) {
            sJ2seLogger.e(str, str2);
        }
    }

    public static void e(String str, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.e(str, th)) && -1 == sAndroidLogger.e(str, th)) {
            sJ2seLogger.e(str, th);
        }
    }

    public static void e(String str, String str2, Throwable th) {
        if ((sTargetLogger == null || -1 == sTargetLogger.e(str, str2, th)) && -1 == sAndroidLogger.e(str, str2, th)) {
            sJ2seLogger.e(str, str2, th);
        }
    }
}
