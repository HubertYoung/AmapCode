package defpackage;

import android.os.Environment;
import android.text.TextUtils;
import com.amap.bundle.logs.AMapLog;
import java.text.SimpleDateFormat;

/* renamed from: tj reason: default package */
/* compiled from: DriveLog */
public class tj {
    private static final String a;
    private static final Object b = new Object();
    private static final SimpleDateFormat c = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static void a(String str, String str2) {
        AMapLog.d(str, b(str2));
    }

    private static String b(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("\t-->\t");
        sb.append(a(0));
        sb.append("\n\t\t-->");
        sb.append(a(1));
        return sb.toString();
    }

    private static String a(int i) {
        if (i < 0) {
            return "";
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i2 = 2;
        while (i2 < stackTrace.length && (stackTrace[i2].getClassName().equals(tj.class.getName()) || stackTrace[i2].getMethodName().equals("getStackTrace"))) {
            i2++;
        }
        if (i2 + i >= stackTrace.length) {
            i = (stackTrace.length - i2) - 1;
        }
        StackTraceElement stackTraceElement = stackTrace[i2 + i];
        StringBuilder sb = new StringBuilder(80);
        sb.append(stackTraceElement.getMethodName());
        if (stackTraceElement.isNativeMethod()) {
            sb.append("(Native Method)");
        } else {
            String fileName = stackTraceElement.getFileName();
            if (fileName == null) {
                sb.append("(Unknown Source)");
            } else {
                int lineNumber = stackTraceElement.getLineNumber();
                sb.append('(');
                sb.append(fileName);
                if (lineNumber >= 0) {
                    sb.append(':');
                    sb.append(lineNumber);
                }
                sb.append(')');
            }
        }
        return sb.toString();
    }

    private static String b(int i) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 2; i2 < stackTrace.length; i2++) {
            if (!stackTrace[i2].getClassName().equals(tj.class.getName()) && !stackTrace[i2].getMethodName().equals("getStackTrace")) {
                stringBuffer.append(stackTrace[i2]);
                stringBuffer.append("\n\t\t\t");
                i--;
                if (i < 0) {
                    break;
                }
            }
        }
        return stringBuffer.toString();
    }

    public static void b(String str, String str2) {
        if (bno.a) {
            AMapLog.i(str, b(str2));
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder();
            sb.append(c(str));
            sb.append(str2);
            sb.append("\t-->\t");
            sb.append(a(0));
            a2.c("NaviMonitor", sb.toString());
        }
    }

    public static void c(String str, String str2) {
        if (bno.a && bno.a && bno.a && !TextUtils.isEmpty("NaviMonitor")) {
            AMapLog.d(str, b(str2));
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append("\n\t stack: ");
            sb.append(b(Integer.MAX_VALUE));
            String sb2 = sb.toString();
            ku a2 = ku.a();
            StringBuilder sb3 = new StringBuilder();
            sb3.append(c(str));
            sb3.append(sb2);
            a2.c("NaviMonitor", sb3.toString());
        }
    }

    private static String c(String str) {
        StringBuilder sb = new StringBuilder("[");
        sb.append(str);
        sb.append("][");
        sb.append(Thread.currentThread().getName());
        sb.append("] ");
        return sb.toString();
    }

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/autonavi/");
        a = sb.toString();
    }

    private static synchronized String a() {
        String format;
        synchronized (tj.class) {
            format = c.format(Long.valueOf(System.currentTimeMillis()));
        }
        return format;
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(13:4|5|6|(1:8)|9|(1:11)(1:12)|13|(3:21|(1:23)(1:24)|25)(2:(1:18)(1:19)|20)|26|35|36|38|39) */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0098, code lost:
        if (r2 == null) goto L_0x00a0;
     */
    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:31:0x0097 */
    /* JADX WARNING: Missing exception handler attribute for start block: B:38:0x00a0 */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:38:0x00a0=Splitter:B:38:0x00a0, B:31:0x0097=Splitter:B:31:0x0097} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void a(java.lang.String r6) {
        /*
            boolean r0 = com.autonavi.minimap.ajx3.AjxConstant.RELEASE
            if (r0 != 0) goto L_0x00a4
            java.lang.String r0 = "drive-performance-"
            java.lang.Object r1 = b
            monitor-enter(r1)
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r4 = a     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            boolean r4 = r3.exists()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            if (r4 != 0) goto L_0x001a
            r3.mkdirs()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
        L_0x001a:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r3.<init>()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r4 = a     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r3.append(r4)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r4 = "performance_eyrie.log"
            r3.append(r4)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r3 = r3.toString()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.io.File r4 = new java.io.File     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r4.<init>(r3)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            boolean r3 = r4.exists()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            if (r3 != 0) goto L_0x0041
            r4.createNewFile()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r3.<init>(r4)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            goto L_0x0047
        L_0x0041:
            java.io.FileWriter r3 = new java.io.FileWriter     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r5 = 1
            r3.<init>(r4, r5)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
        L_0x0047:
            r2 = r3
            java.lang.String r3 = a()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            if (r6 == 0) goto L_0x0064
            java.lang.String r4 = "\n"
            boolean r4 = r6.contains(r4)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            if (r4 == 0) goto L_0x0064
            if (r6 == 0) goto L_0x0059
            goto L_0x005b
        L_0x0059:
            java.lang.String r6 = ""
        L_0x005b:
            r2.write(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r6 = "\r\n"
            r2.write(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            goto L_0x008d
        L_0x0064:
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r4.<init>()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r4.append(r3)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r3 = "|"
            r4.append(r3)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r4.append(r0)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r0 = "|"
            r4.append(r0)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r0 = r4.toString()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            r2.write(r0)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            if (r6 == 0) goto L_0x0083
            goto L_0x0085
        L_0x0083:
            java.lang.String r6 = ""
        L_0x0085:
            r2.write(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            java.lang.String r6 = "\r\n"
            r2.write(r6)     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
        L_0x008d:
            r2.flush()     // Catch:{ Exception -> 0x0098, all -> 0x0091 }
            goto L_0x009a
        L_0x0091:
            r6 = move-exception
            if (r2 == 0) goto L_0x0097
            r2.close()     // Catch:{ IOException -> 0x0097 }
        L_0x0097:
            throw r6     // Catch:{ all -> 0x009e }
        L_0x0098:
            if (r2 == 0) goto L_0x00a0
        L_0x009a:
            r2.close()     // Catch:{ IOException -> 0x00a0 }
            goto L_0x00a0
        L_0x009e:
            r6 = move-exception
            goto L_0x00a2
        L_0x00a0:
            monitor-exit(r1)     // Catch:{ all -> 0x009e }
            return
        L_0x00a2:
            monitor-exit(r1)     // Catch:{ all -> 0x009e }
            throw r6
        L_0x00a4:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.tj.a(java.lang.String):void");
    }
}
