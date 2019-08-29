package defpackage;

import android.database.sqlite.SQLiteDiskIOException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteFullException;
import android.os.Looper;
import android.text.TextUtils;
import com.autonavi.common.tool.CrashLogUtil;
import com.autonavi.common.tool.dumpcrash;
import java.io.File;
import java.lang.Thread.UncaughtExceptionHandler;

/* renamed from: bmr reason: default package */
/* compiled from: ErrorHandler */
public class bmr implements UncaughtExceptionHandler {
    static UncaughtExceptionHandler a = null;
    private static volatile boolean b = false;
    /* access modifiers changed from: private */
    public static volatile Thread c;

    bmr() {
    }

    private static boolean b(Throwable th) {
        boolean z;
        synchronized (bmr.class) {
            z = b;
            if (!b) {
                b = true;
                c = Thread.currentThread();
            }
        }
        if (!z) {
            return true;
        }
        if (CrashLogUtil.debugMode()) {
            Thread.currentThread();
        }
        if (c != null && Thread.currentThread() == Looper.getMainLooper().getThread()) {
            d();
            return false;
        } else if (c == null && Thread.currentThread() == Looper.getMainLooper().getThread()) {
            bmx.b();
            return false;
        } else {
            if (th == null) {
                synchronized (bmr.class) {
                    try {
                        bmr.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return false;
        }
    }

    public static void a() {
        new Thread(new Runnable() {
            public final void run() {
                File x = CrashLogUtil.getControler().x();
                if (x != null && x.exists()) {
                    final StringBuilder sb = new StringBuilder();
                    bmx.a(x, (a) new a() {
                        public final boolean a(String str) {
                            if (TextUtils.isEmpty(str)) {
                                return false;
                            }
                            if (str.contains("heap_dump_pid:")) {
                                CrashLogUtil.setHeapErrorPid(str.substring(str.indexOf("heap_dump_pid:") + 14));
                            } else if (str.contains("exceptionTime:")) {
                                CrashLogUtil.setExceptionTime(str.substring(str.indexOf("exceptionTime:") + 14));
                            } else if (str.contains("app_init_time:")) {
                                CrashLogUtil.setApplicationInitTime(str.substring(str.indexOf("app_init_time:") + 14));
                            } else if (str.contains("except_version:")) {
                                CrashLogUtil.setExceptionVersion(str.substring(str.indexOf("except_version:") + 15));
                            } else {
                                StringBuilder sb = sb;
                                sb.append(str);
                                sb.append(10);
                            }
                            return false;
                        }
                    });
                    x.delete();
                    CrashLogUtil.recordCrash(sb.toString(), null, true, null, false, false);
                }
            }
        }).start();
    }

    public static void a(String str, Thread thread, boolean z) {
        boolean z2;
        StackTraceElement[] stackTraceElementArr;
        if (b(null)) {
            new StringBuilder("begin java native exception crashThread: ").append(thread == null ? "null" : thread.toString());
            CrashLogUtil.getControler();
            boolean z3 = true;
            if (thread == null) {
                thread = Thread.currentThread();
                z2 = true;
            } else {
                z2 = false;
            }
            if (z) {
                stackTraceElementArr = null;
            } else {
                stackTraceElementArr = thread.getStackTrace();
            }
            try {
                CrashLogUtil.getControler().B();
            } catch (Throwable th) {
                try {
                    th.printStackTrace();
                } catch (Throwable th2) {
                    c = null;
                    throw th2;
                }
            }
            try {
                CrashLogUtil.getControler().a(null, null, str);
            } catch (Throwable th3) {
                th3.printStackTrace();
            }
            if (CrashLogUtil.getJniReportOtherThread()) {
                if (z2) {
                    z3 = false;
                }
            }
            CrashLogUtil.recordCrash(dumpcrash.collectMoreInfo(str, thread, stackTraceElementArr, z3), null, false, thread, z, false);
            CrashLogUtil.getControler().C();
            if (CrashLogUtil.needShowInstallErrorDialog(thread, null)) {
                CrashLogUtil.showInstallErrorDialog(thread, null);
            }
            c = null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x005c A[SYNTHETIC, Splitter:B:29:0x005c] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0067 A[SYNTHETIC, Splitter:B:36:0x0067] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x0090  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(final java.lang.Thread r4, final java.lang.Throwable r5) {
        /*
            if (r5 == 0) goto L_0x001f
            java.lang.Class r0 = r5.getClass()
            java.lang.Class<java.util.concurrent.TimeoutException> r1 = java.util.concurrent.TimeoutException.class
            boolean r0 = r0.equals(r1)
            if (r0 == 0) goto L_0x001f
            r5.getMessage()
            android.os.Looper r5 = android.os.Looper.getMainLooper()
            java.lang.Thread r5 = r5.getThread()
            if (r4 != r5) goto L_0x001e
            d()
        L_0x001e:
            return
        L_0x001f:
            boolean r0 = b(r5)
            if (r0 != 0) goto L_0x0026
            return
        L_0x0026:
            com.autonavi.common.tool.CrashLogUtil.getControler()
            java.lang.String r0 = com.autonavi.common.tool.CrashLogUtil.getCrashNotifyFilePath()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x006f
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
            r2.<init>(r0)     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
            java.io.File r2 = r2.getParentFile()     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
            boolean r3 = r2.exists()     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
            if (r3 != 0) goto L_0x0046
            r2.mkdirs()     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
        L_0x0046:
            java.io.FileOutputStream r2 = new java.io.FileOutputStream     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
            r3 = 0
            r2.<init>(r0, r3)     // Catch:{ Throwable -> 0x0065, all -> 0x0059 }
            r0 = 1
            r2.write(r0)     // Catch:{ Throwable -> 0x0057, all -> 0x0054 }
            r2.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006f
        L_0x0054:
            r4 = move-exception
            r1 = r2
            goto L_0x005a
        L_0x0057:
            r1 = r2
            goto L_0x0065
        L_0x0059:
            r4 = move-exception
        L_0x005a:
            if (r1 == 0) goto L_0x0064
            r1.close()     // Catch:{ IOException -> 0x0060 }
            goto L_0x0064
        L_0x0060:
            r5 = move-exception
            r5.printStackTrace()
        L_0x0064:
            throw r4
        L_0x0065:
            if (r1 == 0) goto L_0x006f
            r1.close()     // Catch:{ IOException -> 0x006b }
            goto L_0x006f
        L_0x006b:
            r0 = move-exception
            r0.printStackTrace()
        L_0x006f:
            bmr$2 r0 = new bmr$2
            r0.<init>(r4, r5)
            java.lang.Thread r4 = java.lang.Thread.currentThread()
            android.os.Looper r5 = android.os.Looper.getMainLooper()
            java.lang.Thread r5 = r5.getThread()
            if (r4 != r5) goto L_0x0090
            java.lang.Thread r4 = new java.lang.Thread
            java.lang.String r5 = "CrashTask"
            r4.<init>(r0, r5)
            r4.start()
            d()
            return
        L_0x0090:
            r0.run()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bmr.a(java.lang.Thread, java.lang.Throwable):void");
    }

    public void uncaughtException(Thread thread, Throwable th) {
        a(thread, th);
    }

    private static void d() {
        try {
            Looper.loop();
        } catch (Throwable th) {
            a(Thread.currentThread(), th);
        } finally {
            bmx.b();
        }
    }

    public static void b() {
        UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        if (defaultUncaughtExceptionHandler != null && !(defaultUncaughtExceptionHandler instanceof bmr)) {
            a = defaultUncaughtExceptionHandler;
            Thread.setDefaultUncaughtExceptionHandler(new bmr());
        }
    }

    static /* synthetic */ boolean a(Throwable th) {
        for (Throwable th2 = th; th2 != null; th2 = th2.getCause()) {
            if (th2.toString().contains("No space left on device")) {
                return true;
            }
        }
        Throwable th3 = null;
        while (th != null) {
            if (th instanceof SQLiteException) {
                th3 = th;
            }
            th = th.getCause();
        }
        if (th3 != null) {
            if ((th3 instanceof SQLiteFullException) || (th3 instanceof SQLiteDiskIOException)) {
                return true;
            }
            if (th3 instanceof SQLiteException) {
                String localizedMessage = th3.getLocalizedMessage();
                if (localizedMessage != null && localizedMessage.contains("cannot rollback - no transaction is active")) {
                    return true;
                }
            }
        }
        return false;
    }
}
