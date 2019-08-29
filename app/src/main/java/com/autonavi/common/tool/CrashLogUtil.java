package com.autonavi.common.tool;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.sqlite.SQLiteException;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Environment;
import android.os.Process;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class CrashLogUtil {
    public static final String DUMPCRASH_VERSION = "dumpcrash";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(NetworkDiagnoseUtil.FORMAT_FULL);
    private static final String SPLITE_Symbol = "/";
    private static final int SystemRootStateDisable = 0;
    private static final int SystemRootStateEnable = 1;
    private static final int SystemRootStateUnknow = -1;
    private static String Tag = "";
    private static String UPLOAD_URL = null;
    private static Application application = null;
    private static Date applicationInitTime = null;
    private static bmt controler = null;
    private static String[] dumpAllThreadsWhiteList = {"main", "JavaScriptThread", "JsServiceThread"};
    private static String gApplicationInitTime = "";
    private static String gCrashNotifyFilePath = null;
    private static String gExceptionTime = "";
    private static String gExceptionVersion = "";
    private static String gHeapErrorPid = "";
    private static volatile boolean jniReportOtherThread = false;
    private static bne mActivityStatusMonitor = null;
    private static boolean mAppIsLunchForeground = false;
    private static boolean mIsDebugMode = false;
    private static StringBuilder mRecentTaskMessage = new StringBuilder();
    private static Map<String, Object> offlineMapRecords = new HashMap();
    private static bmv onInstallErrorListener = null;
    private static boolean soCorrupt = false;
    private static int systemRootState = -1;

    public static boolean debugMode() {
        return false;
    }

    public static bmt getControler() {
        return controler;
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00da A[SYNTHETIC, Splitter:B:44:0x00da] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e5 A[SYNTHETIC, Splitter:B:49:0x00e5] */
    /* JADX WARNING: Removed duplicated region for block: B:56:0x00f2  */
    /* JADX WARNING: Removed duplicated region for block: B:59:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void initCrashLog(defpackage.bmt r4) {
        /*
            if (r4 != 0) goto L_0x000a
            java.lang.RuntimeException r4 = new java.lang.RuntimeException
            java.lang.String r0 = "CrashLogUtil.initCrashLog controler == null!"
            r4.<init>(r0)
            throw r4
        L_0x000a:
            controler = r4
            android.app.Application r0 = r4.o()
            application = r0
            java.util.Date r0 = new java.util.Date
            r0.<init>()
            applicationInitTime = r0
            r0 = 0
            mIsDebugMode = r0
            android.app.Application r0 = application
            initAppLunchForeground(r0)
            java.lang.String r0 = ""
            java.io.File r1 = r4.x()
            if (r1 == 0) goto L_0x0031
            java.io.File r4 = r4.x()
            java.lang.String r0 = r4.getAbsolutePath()
        L_0x0031:
            bmt r4 = controler
            java.lang.String r4 = r4.k()
            boolean r1 = android.text.TextUtils.isEmpty(r4)
            if (r1 == 0) goto L_0x003f
            java.lang.String r4 = ""
        L_0x003f:
            com.autonavi.common.tool.dumpcrash.install(r0, r4)
            int r4 = android.os.Build.VERSION.SDK_INT
            r0 = 21
            if (r4 < r0) goto L_0x004c
            r4 = 1
            setJniRepotOtherThread(r4)
        L_0x004c:
            startMonitors()
            defpackage.bmr.b()
            bmt r4 = controler
            int r4 = r4.O()
            defpackage.bnc.a = r4
            bmt r4 = controler
            bnh r4 = defpackage.bnh.a(r4)
            r4.b()
            bmt r4 = controler
            android.app.Application r4 = r4.o()
            defpackage.bmo.a(r4)
            defpackage.bmr.a()
            bms r4 = defpackage.bms.a()
            bmt r0 = controler
            java.lang.String r0 = r0.G()
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 != 0) goto L_0x00f5
            r4.a = r0
            java.lang.String r0 = r4.a
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x009e
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r4.a
            r0.<init>(r1)
            boolean r1 = r0.exists()     // Catch:{ Exception -> 0x009a }
            if (r1 != 0) goto L_0x009e
            r0.mkdirs()     // Catch:{ Exception -> 0x009a }
            goto L_0x009e
        L_0x009a:
            r0 = move-exception
            r0.printStackTrace()
        L_0x009e:
            java.lang.String r0 = r4.a
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 != 0) goto L_0x00ee
            java.io.File r0 = new java.io.File
            java.lang.String r1 = r4.a
            java.lang.String r2 = ".fd_tmp_file.txt"
            r0.<init>(r1, r2)
            r4.b = r0
            java.io.File r0 = r4.b
            boolean r0 = r0.exists()
            if (r0 != 0) goto L_0x00ee
            r0 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x00d4 }
            java.io.File r2 = r4.b     // Catch:{ Exception -> 0x00d4 }
            r1.<init>(r2)     // Catch:{ Exception -> 0x00d4 }
            r0 = 145(0x91, float:2.03E-43)
            r1.write(r0)     // Catch:{ Exception -> 0x00cd, all -> 0x00ca }
            r1.close()     // Catch:{ IOException -> 0x00de }
            goto L_0x00ee
        L_0x00ca:
            r4 = move-exception
            r0 = r1
            goto L_0x00e3
        L_0x00cd:
            r0 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
            goto L_0x00d5
        L_0x00d2:
            r4 = move-exception
            goto L_0x00e3
        L_0x00d4:
            r1 = move-exception
        L_0x00d5:
            r1.printStackTrace()     // Catch:{ all -> 0x00d2 }
            if (r0 == 0) goto L_0x00ee
            r0.close()     // Catch:{ IOException -> 0x00de }
            goto L_0x00ee
        L_0x00de:
            r0 = move-exception
            r0.printStackTrace()
            goto L_0x00ee
        L_0x00e3:
            if (r0 == 0) goto L_0x00ed
            r0.close()     // Catch:{ IOException -> 0x00e9 }
            goto L_0x00ed
        L_0x00e9:
            r0 = move-exception
            r0.printStackTrace()
        L_0x00ed:
            throw r4
        L_0x00ee:
            java.io.File r0 = r4.b
            if (r0 == 0) goto L_0x00f5
            r4.c()
        L_0x00f5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.initCrashLog(bmt):void");
    }

    public static void setJniRepotOtherThread(boolean z) {
        jniReportOtherThread = z;
        dumpcrash.setRepotOtherThread(z);
    }

    public static boolean getJniReportOtherThread() {
        return jniReportOtherThread;
    }

    public static boolean isInited() {
        return controler != null;
    }

    public static void initAppLunchForeground(Application application2) {
        StringBuilder sb = new StringBuilder();
        mRecentTaskMessage = sb;
        sb.append("initAppLunchForeground start \n");
        try {
            List<Class<?>> e = controler.e();
            if (e == null || e.isEmpty()) {
                sb.append("getAppLunchActivitys == null\n");
                return;
            }
            try {
                boolean z = true;
                List<RecentTaskInfo> recentTasks = ((ActivityManager) controler.o().getSystemService(WidgetType.ACTIVITY)).getRecentTasks(1, 1);
                if (recentTasks == null || recentTasks.size() <= 0) {
                    StringBuilder sb2 = new StringBuilder("(recentTasks != null && recentTasks.size() > 0) (recentTasks != null : ");
                    if (recentTasks == null) {
                        z = false;
                    }
                    sb2.append(z);
                    sb2.append(")");
                    sb.append(sb2.toString());
                    sb.append("\n");
                    sb.append("initAppLunchForeground end \n");
                    mRecentTaskMessage = sb;
                }
                RecentTaskInfo recentTaskInfo = recentTasks.get(0);
                if (recentTaskInfo != null) {
                    Intent intent = recentTaskInfo.baseIntent;
                    if (intent != null) {
                        ComponentName component = intent.getComponent();
                        if (component != null) {
                            for (Class next : e) {
                                StringBuilder sb3 = new StringBuilder("act.getName() : ");
                                sb3.append(next.getName());
                                sb.append(sb3.toString());
                                sb.append("\n");
                                if (!mAppIsLunchForeground) {
                                    mAppIsLunchForeground = TextUtils.equals(component.getClassName(), next.getName());
                                }
                            }
                            StringBuilder sb4 = new StringBuilder("RecentTask ComponentName: ");
                            sb4.append(component.getClassName());
                            sb.append(sb4.toString());
                            sb.append("\n");
                        } else {
                            sb.append("taskInfo.baseIntent.getComponent() == null \n");
                        }
                    } else {
                        sb.append("taskInfo.baseIntent == null \n");
                    }
                } else {
                    sb.append("recentTasks.get(0) == null \n");
                }
                sb.append("initAppLunchForeground end \n");
                mRecentTaskMessage = sb;
            } catch (Throwable th) {
                StringBuilder sb5 = new StringBuilder("initAppLunchForeground Exception : ");
                sb5.append(th.getMessage());
                sb.append(sb5.toString());
                sb.append("\n");
            }
        } catch (Throwable th2) {
            th2.printStackTrace();
            sb.append("getAppLunchActivitys == null\n");
        }
    }

    public static void setCrashUploadUrl(String str) {
        UPLOAD_URL = str;
    }

    public static void setTag(String str) {
        Tag = str;
    }

    public static void setOnInstallErrorListener(bmv bmv) {
        onInstallErrorListener = bmv;
    }

    public static boolean needShowInstallErrorDialog(Thread thread, Throwable th) {
        if (onInstallErrorListener == null || controler.y() == null) {
            return false;
        }
        if (soCorrupt) {
            return true;
        }
        try {
            if (!onInstallErrorListener.a(th)) {
                return false;
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    public static void showInstallErrorDialog(Thread thread, Throwable th) {
        if (onInstallErrorListener != null) {
            try {
                onInstallErrorListener.b(th);
            } catch (Throwable unused) {
                bmx.b();
            }
        }
    }

    public static void putFlag(String str, Object obj) {
        offlineMapRecords.put(str, obj);
    }

    private static boolean isSqliteException(Throwable th) {
        return bmx.a(th, SQLiteException.class);
    }

    static String getLogcat() {
        final StringBuffer stringBuffer = new StringBuffer();
        final StringBuffer stringBuffer2 = new StringBuffer();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Callable<Object>() {
            public final Object call() throws Exception {
                try {
                    stringBuffer.append("-----main log-----\n");
                    Process exec = Runtime.getRuntime().exec("logcat -d -v threadtime -b main -t 1000");
                    exec.waitFor();
                    stringBuffer.append(bmu.a(exec.getInputStream()));
                } catch (Throwable unused) {
                }
                return null;
            }
        });
        arrayList.add(new Callable<Object>() {
            public final Object call() throws Exception {
                try {
                    stringBuffer2.append("-----system log-----\n");
                    Process exec = Runtime.getRuntime().exec("logcat -d -v threadtime -b system -t 1000");
                    exec.waitFor();
                    stringBuffer2.append(bmu.a(exec.getInputStream()));
                } catch (Throwable unused) {
                }
                return null;
            }
        });
        bmx.a(arrayList, 2000, arrayList.size());
        StringBuilder sb = new StringBuilder("\nLogcat:\n");
        sb.append(stringBuffer);
        sb.append(stringBuffer2);
        return sb.toString();
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(5:1|2|(1:4)(2:5|(3:7|8|(2:11|9)))|12|13) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:12:0x0053 */
    @android.annotation.SuppressLint({"NewApi"})
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getAppendInfo(java.lang.Throwable r4) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = getLogcat()     // Catch:{ Throwable -> 0x005c }
            r0.append(r1)     // Catch:{ Throwable -> 0x005c }
            boolean r1 = isSqliteException(r4)     // Catch:{ Throwable -> 0x005c }
            if (r1 == 0) goto L_0x001c
            android.app.Application r1 = application     // Catch:{ Throwable -> 0x005c }
            java.lang.String r1 = com.autonavi.common.tool.DatabaseCollector.getDatabaseInfo(r4, r1)     // Catch:{ Throwable -> 0x005c }
            r0.append(r1)     // Catch:{ Throwable -> 0x005c }
            goto L_0x0053
        L_0x001c:
            java.lang.Class<android.content.res.Resources$NotFoundException> r1 = android.content.res.Resources.NotFoundException.class
            boolean r1 = defpackage.bmx.a(r4, r1)     // Catch:{ Throwable -> 0x005c }
            if (r1 == 0) goto L_0x0053
            bms r1 = defpackage.bms.a()     // Catch:{ Throwable -> 0x0053 }
            r1.d()     // Catch:{ Throwable -> 0x0053 }
            java.lang.String r1 = "FDinfo:\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x0053 }
            r1 = 1
            java.util.List r1 = defpackage.bmx.a(r1)     // Catch:{ Throwable -> 0x0053 }
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x0053 }
        L_0x0039:
            boolean r2 = r1.hasNext()     // Catch:{ Throwable -> 0x0053 }
            if (r2 == 0) goto L_0x0053
            java.lang.Object r2 = r1.next()     // Catch:{ Throwable -> 0x0053 }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Throwable -> 0x0053 }
            java.lang.String r3 = "\t"
            r0.append(r3)     // Catch:{ Throwable -> 0x0053 }
            r0.append(r2)     // Catch:{ Throwable -> 0x0053 }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x0053 }
            goto L_0x0039
        L_0x0053:
            android.app.Application r1 = application     // Catch:{ Throwable -> 0x005c }
            java.lang.String r4 = com.autonavi.common.tool.ModuleCollector.getInfo(r4, r1)     // Catch:{ Throwable -> 0x005c }
            r0.append(r4)     // Catch:{ Throwable -> 0x005c }
        L_0x005c:
            java.lang.String r4 = r0.toString()
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.getAppendInfo(java.lang.Throwable):java.lang.String");
    }

    /* JADX WARNING: type inference failed for: r2v0, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v1 */
    /* JADX WARNING: type inference failed for: r2v2, types: [java.io.Closeable] */
    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v5 */
    /* JADX WARNING: type inference failed for: r2v6 */
    /* JADX WARNING: type inference failed for: r2v7 */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v10 */
    /* JADX WARNING: type inference failed for: r2v11 */
    /* JADX WARNING: type inference failed for: r2v13 */
    /* JADX WARNING: type inference failed for: r2v14 */
    /* JADX WARNING: type inference failed for: r2v15, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARNING: type inference failed for: r2v16 */
    /* JADX WARNING: type inference failed for: r2v17 */
    /* JADX WARNING: type inference failed for: r2v18 */
    /* JADX WARNING: type inference failed for: r2v19 */
    /* JADX WARNING: type inference failed for: r2v20 */
    /* JADX WARNING: type inference failed for: r2v21 */
    /* JADX WARNING: type inference failed for: r2v22 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Unknown variable types count: 5 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String getExceptionString(java.lang.Throwable r8) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            java.io.StringWriter r2 = new java.io.StringWriter     // Catch:{ Throwable -> 0x00a4, all -> 0x008f }
            r2.<init>()     // Catch:{ Throwable -> 0x00a4, all -> 0x008f }
            java.io.PrintWriter r3 = new java.io.PrintWriter     // Catch:{ Throwable -> 0x00a4, all -> 0x008f }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x00a4, all -> 0x008f }
            r8.printStackTrace(r3)     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            r0.append(r2)     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            java.lang.String r8 = getAppendInfo(r8)     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            r0.append(r8)     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            bmt r8 = controler     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            java.io.File r8 = r8.v()     // Catch:{ Throwable -> 0x008b, all -> 0x0086 }
            boolean r2 = r8.exists()     // Catch:{ Throwable -> 0x0084, all -> 0x0081 }
            if (r2 == 0) goto L_0x0056
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x0084, all -> 0x0081 }
            r2.<init>(r8)     // Catch:{ Throwable -> 0x0084, all -> 0x0081 }
            java.lang.String r4 = "\nInitError("
            r0.append(r4)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            java.text.SimpleDateFormat r4 = SIMPLE_DATE_FORMAT     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            java.util.Date r5 = new java.util.Date     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            long r6 = r8.lastModified()     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            r5.<init>(r6)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            java.lang.String r4 = r4.format(r5)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            r0.append(r4)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            java.lang.String r4 = "):"
            r0.append(r4)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            java.lang.String r4 = defpackage.bmu.a(r2)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            r0.append(r4)     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            goto L_0x0057
        L_0x0056:
            r2 = r1
        L_0x0057:
            bmt r4 = controler     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            java.io.File r4 = r4.u()     // Catch:{ Throwable -> 0x007f, all -> 0x007c }
            boolean r5 = r4.exists()     // Catch:{ Throwable -> 0x00a8, all -> 0x007a }
            if (r5 == 0) goto L_0x00a8
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Throwable -> 0x00a8, all -> 0x007a }
            r5.<init>(r4)     // Catch:{ Throwable -> 0x00a8, all -> 0x007a }
            java.lang.String r1 = "\noffline_error:"
            r0.append(r1)     // Catch:{ Throwable -> 0x0078, all -> 0x0075 }
            java.lang.String r1 = defpackage.bmu.a(r5)     // Catch:{ Throwable -> 0x0078, all -> 0x0075 }
            r0.append(r1)     // Catch:{ Throwable -> 0x0078, all -> 0x0075 }
            goto L_0x0078
        L_0x0075:
            r0 = move-exception
            r1 = r5
            goto L_0x0094
        L_0x0078:
            r1 = r5
            goto L_0x00a8
        L_0x007a:
            r0 = move-exception
            goto L_0x0094
        L_0x007c:
            r0 = move-exception
            r4 = r1
            goto L_0x0094
        L_0x007f:
            r4 = r1
            goto L_0x00a8
        L_0x0081:
            r0 = move-exception
            r2 = r1
            goto L_0x0089
        L_0x0084:
            r2 = r1
            goto L_0x008d
        L_0x0086:
            r0 = move-exception
            r8 = r1
            r2 = r8
        L_0x0089:
            r4 = r2
            goto L_0x0094
        L_0x008b:
            r8 = r1
            r2 = r8
        L_0x008d:
            r4 = r2
            goto L_0x00a8
        L_0x008f:
            r0 = move-exception
            r8 = r1
            r2 = r8
            r3 = r2
            r4 = r3
        L_0x0094:
            defpackage.bmu.a(r3)
            defpackage.bmu.a(r2)
            defpackage.bmu.a(r8)
            defpackage.bmu.a(r1)
            defpackage.bmu.a(r4)
            throw r0
        L_0x00a4:
            r8 = r1
            r2 = r8
            r3 = r2
            r4 = r3
        L_0x00a8:
            defpackage.bmu.a(r3)
            defpackage.bmu.a(r2)
            defpackage.bmu.a(r8)
            defpackage.bmu.a(r1)
            defpackage.bmu.a(r4)
            java.lang.String r8 = r0.toString()
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.getExceptionString(java.lang.Throwable):java.lang.String");
    }

    /* JADX WARNING: Exception block dominator not found, dom blocks: [] */
    /* JADX WARNING: Missing exception handler attribute for start block: B:29:0x008a */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0090 A[Catch:{ Throwable -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009f A[Catch:{ Throwable -> 0x0146 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x00b0 A[Catch:{ Throwable -> 0x0146 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static java.lang.String lastAppendInfo(java.lang.String r8, boolean r9, java.lang.Thread r10) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0146 }
            r0.<init>(r8)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
            r1 = 0
            bmt r2 = getControler()     // Catch:{ Throwable -> 0x0014 }
            java.lang.String r2 = r2.P()     // Catch:{ Throwable -> 0x0014 }
            goto L_0x0015
        L_0x0014:
            r2 = r1
        L_0x0015:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0146 }
            if (r3 != 0) goto L_0x0028
            java.lang.String r3 = "SoHotfix:"
            r0.append(r3)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r2)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r2 = "\n"
            r0.append(r2)     // Catch:{ Throwable -> 0x0146 }
        L_0x0028:
            bmt r2 = getControler()     // Catch:{ Throwable -> 0x0031 }
            java.lang.String r2 = r2.Q()     // Catch:{ Throwable -> 0x0031 }
            goto L_0x0032
        L_0x0031:
            r2 = r1
        L_0x0032:
            boolean r3 = android.text.TextUtils.isEmpty(r2)     // Catch:{ Throwable -> 0x0146 }
            if (r3 != 0) goto L_0x0081
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0146 }
            r3.<init>(r2)     // Catch:{ Throwable -> 0x0146 }
            java.io.File[] r2 = r3.listFiles()     // Catch:{ Throwable -> 0x0146 }
            if (r2 == 0) goto L_0x0081
            int r3 = r2.length     // Catch:{ Throwable -> 0x0146 }
            r4 = 0
        L_0x0045:
            if (r4 >= r3) goto L_0x0081
            r5 = r2[r4]     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r6 = r5.getPath()     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r7 = ".so"
            boolean r6 = r6.endsWith(r7)     // Catch:{ Throwable -> 0x0146 }
            if (r6 == 0) goto L_0x007e
            java.lang.String r6 = "\tfile="
            r0.append(r6)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r6 = r5.getPath()     // Catch:{ Throwable -> 0x0146 }
            r0.append(r6)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r6 = " len="
            r0.append(r6)     // Catch:{ Throwable -> 0x0146 }
            long r6 = r5.length()     // Catch:{ Throwable -> 0x0146 }
            r0.append(r6)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r6 = " md5="
            r0.append(r6)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r5 = defpackage.bmq.a(r5)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r5)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r5 = "\n"
            r0.append(r5)     // Catch:{ Throwable -> 0x0146 }
        L_0x007e:
            int r4 = r4 + 1
            goto L_0x0045
        L_0x0081:
            bmt r2 = getControler()     // Catch:{ Throwable -> 0x008a }
            java.lang.String r2 = r2.R()     // Catch:{ Throwable -> 0x008a }
            r1 = r2
        L_0x008a:
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0146 }
            if (r2 != 0) goto L_0x009d
            java.lang.String r2 = "ABTest:"
            r0.append(r2)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
        L_0x009d:
            if (r9 == 0) goto L_0x00b0
            android.app.Application r10 = application     // Catch:{ Throwable -> 0x0146 }
            bmt r1 = controler     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r1 = r1.q()     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = defpackage.bmo.a(r10, r1)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            goto L_0x0133
        L_0x00b0:
            android.app.Application r1 = application     // Catch:{ Throwable -> 0x0146 }
            bmt r2 = controler     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r2 = r2.q()     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = defpackage.bmo.a(r1, r2, r10)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            java.lang.StringBuilder r10 = mRecentTaskMessage     // Catch:{ Throwable -> 0x0146 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0146 }
            if (r10 != 0) goto L_0x00d5
            java.lang.String r10 = "\n"
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            java.lang.StringBuilder r10 = mRecentTaskMessage     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = r10.toString()     // Catch:{ Throwable -> 0x0146 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
        L_0x00d5:
            bms r10 = defpackage.bms.a()     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = r10.b()     // Catch:{ Throwable -> 0x0146 }
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0146 }
            if (r1 != 0) goto L_0x0107
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = "\n"
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = getAllThreadNameInfo()     // Catch:{ Throwable -> 0x0146 }
            boolean r1 = android.text.TextUtils.isEmpty(r10)     // Catch:{ Throwable -> 0x0146 }
            if (r1 != 0) goto L_0x0107
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r10 = "\n"
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
        L_0x0107:
            java.util.List<java.lang.String> r10 = defpackage.bmp.a     // Catch:{ Throwable -> 0x0146 }
            if (r10 == 0) goto L_0x0133
            java.util.List<java.lang.String> r10 = defpackage.bmp.a     // Catch:{ Throwable -> 0x0146 }
            int r10 = r10.size()     // Catch:{ Throwable -> 0x0146 }
            if (r10 <= 0) goto L_0x0133
            java.lang.String r10 = "custom:\n"
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            java.util.List<java.lang.String> r10 = defpackage.bmp.a     // Catch:{ Throwable -> 0x0146 }
            java.util.Iterator r10 = r10.iterator()     // Catch:{ Throwable -> 0x0146 }
        L_0x011e:
            boolean r1 = r10.hasNext()     // Catch:{ Throwable -> 0x0146 }
            if (r1 == 0) goto L_0x0133
            java.lang.Object r1 = r10.next()     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x0146 }
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r1 = "\n"
            r0.append(r1)     // Catch:{ Throwable -> 0x0146 }
            goto L_0x011e
        L_0x0133:
            java.lang.String r10 = "IsHeapError: "
            r0.append(r10)     // Catch:{ Throwable -> 0x0146 }
            r0.append(r9)     // Catch:{ Throwable -> 0x0146 }
            r9 = 10
            r0.append(r9)     // Catch:{ Throwable -> 0x0146 }
            java.lang.String r9 = r0.toString()     // Catch:{ Throwable -> 0x0146 }
            r8 = r9
            goto L_0x014a
        L_0x0146:
            r9 = move-exception
            r9.printStackTrace()
        L_0x014a:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.lastAppendInfo(java.lang.String, boolean, java.lang.Thread):java.lang.String");
    }

    private static String getAllThreadNameInfo() {
        try {
            Map<Thread, StackTraceElement[]> a = bmw.a();
            if (a.isEmpty()) {
                return null;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("AllThreadName:\n");
            for (Thread name : a.keySet()) {
                sb.append(name.getName());
                sb.append("\n");
            }
            return sb.toString();
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x006e A[Catch:{ Throwable -> 0x0036, Throwable -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0073 A[Catch:{ Throwable -> 0x0036, Throwable -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x008e A[Catch:{ Throwable -> 0x0036, Throwable -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x009f A[Catch:{ Throwable -> 0x0036, Throwable -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x011d A[Catch:{ Throwable -> 0x0036, Throwable -> 0x0148 }] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0121 A[Catch:{ Throwable -> 0x0036, Throwable -> 0x0148 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void recordCrash(java.lang.String r6, java.lang.Throwable r7, boolean r8, java.lang.Thread r9, boolean r10, boolean r11) {
        /*
            r10 = 1
            r0 = 0
            boolean[] r1 = new boolean[r10]     // Catch:{ Throwable -> 0x002c }
            r1[r0] = r0     // Catch:{ Throwable -> 0x002c }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x002c }
            r2.<init>()     // Catch:{ Throwable -> 0x002c }
            r2.append(r6)     // Catch:{ Throwable -> 0x002c }
            android.app.Application r3 = application     // Catch:{ Throwable -> 0x002c }
            bmt r4 = controler     // Catch:{ Throwable -> 0x002c }
            java.lang.String r4 = r4.q()     // Catch:{ Throwable -> 0x002c }
            java.lang.String r7 = com.autonavi.common.tool.SoCollector.getSoInfo(r7, r3, r4, r1)     // Catch:{ Throwable -> 0x002c }
            r2.append(r7)     // Catch:{ Throwable -> 0x002c }
            java.lang.String r7 = r2.toString()     // Catch:{ Throwable -> 0x002c }
            boolean r6 = r1[r0]     // Catch:{ Throwable -> 0x0027 }
            soCorrupt = r6     // Catch:{ Throwable -> 0x0027 }
            r6 = r7
            goto L_0x0030
        L_0x0027:
            r6 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
            goto L_0x002d
        L_0x002c:
            r7 = move-exception
        L_0x002d:
            r7.printStackTrace()     // Catch:{ Throwable -> 0x0148 }
        L_0x0030:
            java.lang.String r7 = lastAppendInfo(r6, r8, r9)     // Catch:{ Throwable -> 0x0036 }
            r6 = r7
            goto L_0x003a
        L_0x0036:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ Throwable -> 0x0148 }
        L_0x003a:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x004f }
            r7.<init>()     // Catch:{ Exception -> 0x004f }
            r7.append(r6)     // Catch:{ Exception -> 0x004f }
            java.lang.String r1 = getOtherThreadsInfo(r9)     // Catch:{ Exception -> 0x004f }
            r7.append(r1)     // Catch:{ Exception -> 0x004f }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x004f }
            r6 = r7
            goto L_0x0053
        L_0x004f:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ Throwable -> 0x0148 }
        L_0x0053:
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0068 }
            r7.<init>()     // Catch:{ Exception -> 0x0068 }
            r7.append(r6)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r9 = getNativeOtherThreadsInfo(r9)     // Catch:{ Exception -> 0x0068 }
            r7.append(r9)     // Catch:{ Exception -> 0x0068 }
            java.lang.String r7 = r7.toString()     // Catch:{ Exception -> 0x0068 }
            r6 = r7
            goto L_0x006c
        L_0x0068:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ Throwable -> 0x0148 }
        L_0x006c:
            if (r8 == 0) goto L_0x0073
            java.lang.String r6 = heapErrorbuildDumpLog(r6)     // Catch:{ Throwable -> 0x0148 }
            goto L_0x008c
        L_0x0073:
            java.lang.String r6 = buildDumpLog(r6)     // Catch:{ Throwable -> 0x0148 }
            if (r11 == 0) goto L_0x008c
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0148 }
            r7.<init>()     // Catch:{ Throwable -> 0x0148 }
            r7.append(r6)     // Catch:{ Throwable -> 0x0148 }
            java.lang.String r6 = defpackage.bmp.a()     // Catch:{ Throwable -> 0x0148 }
            r7.append(r6)     // Catch:{ Throwable -> 0x0148 }
            java.lang.String r6 = r7.toString()     // Catch:{ Throwable -> 0x0148 }
        L_0x008c:
            if (r11 != 0) goto L_0x0092
            java.lang.String r6 = bringStatusAndLogToEnd(r6)     // Catch:{ Throwable -> 0x0148 }
        L_0x0092:
            bmt r7 = controler     // Catch:{ Throwable -> 0x0148 }
            java.io.File r7 = r7.w()     // Catch:{ Throwable -> 0x0148 }
            defpackage.bmu.a(r6, r7, r0)     // Catch:{ Throwable -> 0x0148 }
            boolean r7 = mIsDebugMode     // Catch:{ Throwable -> 0x0148 }
            if (r7 != 0) goto L_0x0147
            bmt r7 = controler     // Catch:{ Throwable -> 0x0148 }
            java.io.File r7 = r7.s()     // Catch:{ Throwable -> 0x0148 }
            if (r7 == 0) goto L_0x0146
            boolean r8 = r7.exists()     // Catch:{ Throwable -> 0x0148 }
            if (r8 != 0) goto L_0x00af
            goto L_0x0146
        L_0x00af:
            r8 = 0
            boolean r9 = mAppIsLunchForeground     // Catch:{ Throwable -> 0x0116 }
            if (r9 != 0) goto L_0x0114
            bnc r9 = new bnc     // Catch:{ Throwable -> 0x0116 }
            bmt r11 = controler     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r11 = r11.G()     // Catch:{ Throwable -> 0x0116 }
            r9.<init>(r11)     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r11 = r9.d     // Catch:{ Throwable -> 0x0116 }
            boolean r11 = r9.a(r11)     // Catch:{ Throwable -> 0x0116 }
            if (r11 == 0) goto L_0x00d8
            java.io.File r11 = new java.io.File     // Catch:{ Throwable -> 0x0116 }
            java.io.File r1 = r9.e     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r2 = r9.d     // Catch:{ Throwable -> 0x0116 }
            r11.<init>(r1, r2)     // Catch:{ Throwable -> 0x0116 }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0116 }
            java.lang.String r2 = defpackage.bnc.c     // Catch:{ Throwable -> 0x0116 }
            r1.<init>(r11, r2)     // Catch:{ Throwable -> 0x0116 }
            goto L_0x00d9
        L_0x00d8:
            r1 = r8
        L_0x00d9:
            int r11 = defpackage.bnc.a     // Catch:{ Throwable -> 0x0112 }
            if (r11 <= 0) goto L_0x011b
            java.io.File r11 = new java.io.File     // Catch:{ Throwable -> 0x0112 }
            java.io.File r2 = r9.e     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r9 = r9.d     // Catch:{ Throwable -> 0x0112 }
            r11.<init>(r2, r9)     // Catch:{ Throwable -> 0x0112 }
            boolean r9 = r11.exists()     // Catch:{ Throwable -> 0x0112 }
            if (r9 != 0) goto L_0x00f2
            boolean r9 = r11.mkdirs()     // Catch:{ Throwable -> 0x0112 }
            if (r9 == 0) goto L_0x011b
        L_0x00f2:
            java.io.File r9 = new java.io.File     // Catch:{ Throwable -> 0x0112 }
            java.lang.String r2 = defpackage.bnc.b     // Catch:{ Throwable -> 0x0112 }
            r9.<init>(r11, r2)     // Catch:{ Throwable -> 0x0112 }
            boolean r2 = r9.exists()     // Catch:{ Throwable -> 0x0112 }
            if (r2 != 0) goto L_0x0105
            boolean r2 = r9.createNewFile()     // Catch:{ Throwable -> 0x0112 }
            if (r2 == 0) goto L_0x011b
        L_0x0105:
            int r11 = defpackage.bnc.a(r11)     // Catch:{ Throwable -> 0x0112 }
            int r11 = r11 + r10
            java.lang.String r11 = java.lang.String.valueOf(r11)     // Catch:{ Throwable -> 0x0112 }
            defpackage.bmu.a(r11, r9, r0)     // Catch:{ Throwable -> 0x0112 }
            goto L_0x011b
        L_0x0112:
            r9 = move-exception
            goto L_0x0118
        L_0x0114:
            r1 = r8
            goto L_0x011b
        L_0x0116:
            r9 = move-exception
            r1 = r8
        L_0x0118:
            r9.printStackTrace()     // Catch:{ Throwable -> 0x0148 }
        L_0x011b:
            if (r1 == 0) goto L_0x0121
            defpackage.bmu.a(r6, r1, r0)     // Catch:{ Throwable -> 0x0148 }
            goto L_0x0137
        L_0x0121:
            java.lang.String r9 = "crash"
            java.io.File r7 = java.io.File.createTempFile(r9, r8, r7)     // Catch:{ Throwable -> 0x0148 }
            boolean r9 = r7.exists()     // Catch:{ Throwable -> 0x0148 }
            if (r9 == 0) goto L_0x0133
            boolean r9 = r7.canWrite()     // Catch:{ Throwable -> 0x0148 }
            if (r9 != 0) goto L_0x0134
        L_0x0133:
            r7 = r8
        L_0x0134:
            defpackage.bmu.a(r6, r7, r10)     // Catch:{ Throwable -> 0x0148 }
        L_0x0137:
            bnh r6 = defpackage.bnh.a(r8)     // Catch:{ Throwable -> 0x0148 }
            r6.a()     // Catch:{ Throwable -> 0x0148 }
            bnh r6 = defpackage.bnh.a(r8)     // Catch:{ Throwable -> 0x0148 }
            r6.c()     // Catch:{ Throwable -> 0x0148 }
            goto L_0x0147
        L_0x0146:
            return
        L_0x0147:
            return
        L_0x0148:
            r6 = move-exception
            r6.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.recordCrash(java.lang.String, java.lang.Throwable, boolean, java.lang.Thread, boolean, boolean):void");
    }

    private static String bringStatusAndLogToEnd(String str) {
        int indexOf = str.indexOf("===[START] KeyValue");
        int indexOf2 = str.indexOf("===[END] KeyValue & Log===");
        if (indexOf <= 0 || indexOf2 <= 0) {
            return str;
        }
        String substring = str.substring(indexOf, indexOf2 + "===[END] KeyValue & Log===".length());
        if (TextUtils.isEmpty(substring)) {
            return str;
        }
        String replace = str.replace(substring, "");
        StringBuilder sb = new StringBuilder();
        sb.append(replace);
        sb.append(substring);
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append(sb2);
        sb3.append("\n");
        return sb3.toString();
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0036 A[SYNTHETIC, Splitter:B:20:0x0036] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x003e A[Catch:{ Throwable -> 0x0043 }] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0039 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void appendUploadFlag(java.io.File[] r6, java.lang.String r7) {
        /*
            int r0 = r6.length     // Catch:{ Throwable -> 0x0043 }
            r1 = 0
        L_0x0002:
            if (r1 >= r0) goto L_0x0042
            r2 = r6[r1]     // Catch:{ Throwable -> 0x0043 }
            r3 = 0
            java.io.FileWriter r4 = new java.io.FileWriter     // Catch:{ Throwable -> 0x0030 }
            r5 = 1
            r4.<init>(r2, r5)     // Catch:{ Throwable -> 0x0030 }
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            r2.<init>()     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            r2.append(r7)     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            java.lang.String r3 = "\n"
            r2.append(r3)     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            java.lang.String r2 = r2.toString()     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            r4.write(r2)     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            r4.flush()     // Catch:{ Throwable -> 0x002a, all -> 0x0028 }
            r4.close()     // Catch:{ Throwable -> 0x0043 }
            goto L_0x0039
        L_0x0028:
            r6 = move-exception
            goto L_0x003c
        L_0x002a:
            r2 = move-exception
            r3 = r4
            goto L_0x0031
        L_0x002d:
            r6 = move-exception
            r4 = r3
            goto L_0x003c
        L_0x0030:
            r2 = move-exception
        L_0x0031:
            r2.printStackTrace()     // Catch:{ all -> 0x002d }
            if (r3 == 0) goto L_0x0039
            r3.close()     // Catch:{ Throwable -> 0x0043 }
        L_0x0039:
            int r1 = r1 + 1
            goto L_0x0002
        L_0x003c:
            if (r4 == 0) goto L_0x0041
            r4.close()     // Catch:{ Throwable -> 0x0043 }
        L_0x0041:
            throw r6     // Catch:{ Throwable -> 0x0043 }
        L_0x0042:
            return
        L_0x0043:
            r6 = move-exception
            r6.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.appendUploadFlag(java.io.File[], java.lang.String):void");
    }

    public static void appendUploadFlag(File[] fileArr) {
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("uploadtime=");
            sb.append(System.currentTimeMillis());
            sb.append(" pid:");
            sb.append(Process.myPid());
            sb.append(" NetworkType:");
            sb.append(controler.d());
        } catch (Throwable unused) {
        }
        appendUploadFlag(fileArr, sb.toString());
    }

    public static String encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            byte[] bytes = URLEncoder.encode(str, "UTF-8").getBytes("UTF-8");
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (bytes[i] + 16);
                byte b = bytes[i] & 255;
                if (b < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(b));
            }
            return stringBuffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void setHeapErrorPid(String str) {
        gHeapErrorPid = str;
    }

    public static void setExceptionTime(String str) {
        gExceptionTime = str;
    }

    public static void setApplicationInitTime(String str) {
        gApplicationInitTime = str;
    }

    public static void setExceptionVersion(String str) {
        gExceptionVersion = str;
    }

    private static String heapErrorbuildDumpLog(String str) throws Exception {
        String str2;
        StringBuilder sb = new StringBuilder();
        PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        application.getSystemService("phone");
        sb.append("Version:");
        sb.append(TextUtils.isEmpty(gExceptionVersion) ? controler.k() : gExceptionVersion);
        sb.append("/");
        sb.append(controler.j());
        sb.append(10);
        if (mIsDebugMode) {
            sb.append("DEBUG MODE LOG !!!\n");
        }
        sb.append("dic:");
        sb.append(getDic());
        sb.append(10);
        sb.append("diu:");
        sb.append(getDiu());
        sb.append(10);
        sb.append("tid:");
        sb.append(getTid());
        sb.append(10);
        sb.append("div:");
        sb.append(getDiv());
        sb.append(10);
        sb.append("dibv:");
        sb.append(getDibv());
        sb.append(10);
        sb.append("DeviceName:");
        sb.append(Build.DEVICE);
        sb.append(10);
        sb.append("Android-Version:");
        sb.append(VERSION.RELEASE);
        sb.append(10);
        sb.append("encrypt:nb\n");
        sb.append("DeviceID:");
        sb.append(encode(getAdiu()));
        sb.append(10);
        sb.append("DeviceRoot:");
        sb.append(isRootDevice());
        sb.append(10);
        sb.append("Foreground:false\n");
        String mIUIVersion = getMIUIVersion();
        if (mIUIVersion != null) {
            sb.append("MiUI:");
            sb.append(mIUIVersion);
            sb.append(10);
        }
        Date installedTime = installedTime(packageInfo);
        new Date();
        sb.append("InstalledTime:");
        sb.append(SIMPLE_DATE_FORMAT.format(installedTime));
        sb.append(10);
        sb.append("ExceptionTime:");
        sb.append(gExceptionTime);
        sb.append(10);
        sb.append("AmapProcessStartTime:");
        sb.append(gApplicationInitTime);
        sb.append(10);
        if (applicationInitTime != null) {
            sb.append("ApplicationInitTime:\n");
        }
        File dataDirectory = Environment.getDataDirectory();
        if (dataDirectory != null && dataDirectory.exists()) {
            long usableSpace = dataDirectory.getUsableSpace();
            sb.append("DataFreeSize:");
            sb.append(usableSpace);
            sb.append(10);
        }
        sb.append("PID:");
        sb.append(gHeapErrorPid);
        sb.append(10);
        sb.append("CurrentPage:\n");
        try {
            sb.append("ProcessMemeryInfo:\n");
            String E = getControler().E();
            if (E == null || E.isEmpty()) {
                E = "unknown";
            }
            sb.append("ExternalStoragePath:");
            sb.append(E);
            sb.append(10);
            sb.append("ExternalStorageSize:");
            long j = 0;
            sb.append(E.equals("unknown") ? 0 : new File(E).getUsableSpace());
            sb.append(10);
            String D = getControler().D();
            if (D == null || D.isEmpty()) {
                D = "unknown";
            }
            sb.append("InternalStoragePath:");
            sb.append(D);
            sb.append(10);
            sb.append("InternalStorageSize:");
            if (!D.equals("unknown")) {
                j = new File(D).getUsableSpace();
            }
            sb.append(j);
            sb.append(10);
            sb.append("ABI:");
            sb.append(SystemProperties.get("ro.product.cpu.abi", "unknown"));
            sb.append(10);
            sb.append("VMHeap:");
            sb.append(SystemProperties.get("dalvik.vm.heapsize", "unknown"));
            sb.append(10);
            sb.append("NetworkType:");
            sb.append(controler.d());
            sb.append(10);
            sb.append("Operator:");
            sb.append(controler.c());
            sb.append(10);
            sb.append("CurrentCity:");
            sb.append(controler.r());
            sb.append(10);
            sb.append("Debugable:");
            sb.append(SystemProperties.get("ro.debuggable", "unknown"));
            sb.append(10);
            sb.append("FingerPrint:");
            sb.append(SystemProperties.get("ro.build.fingerprint", "unknown"));
            sb.append(10);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("Exception:(");
        sb3.append(packageInfo.versionName);
        sb3.append(")");
        sb3.append(str);
        String sb4 = sb3.toString();
        sb.append("Tag:");
        if (TextUtils.isEmpty(Tag)) {
            str2 = "";
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(Tag);
            sb5.append(":");
            str2 = sb5.toString();
        }
        sb.append(str2);
        sb.append(bmq.a(sb2.trim()));
        sb.append(":");
        sb.append(bmq.a(sb4.trim()));
        sb.append(10);
        sb.append(sb4);
        sb.append("\n\n");
        return sb.toString();
    }

    private static String buildDumpLog(String str) throws Exception {
        String str2;
        StringBuilder sb = new StringBuilder();
        PackageInfo packageInfo = application.getPackageManager().getPackageInfo(application.getPackageName(), 0);
        application.getSystemService("phone");
        sb.append("Version:");
        sb.append(controler.k());
        sb.append("/");
        sb.append(controler.j());
        sb.append(10);
        if (mIsDebugMode) {
            sb.append("DEBUG MODE LOG !!!\n");
        }
        sb.append("dic:");
        sb.append(getDic());
        sb.append(10);
        sb.append("diu:");
        sb.append(getDiu());
        sb.append(10);
        sb.append("tid:");
        sb.append(getTid());
        sb.append(10);
        sb.append("div:");
        sb.append(getDiv());
        sb.append(10);
        sb.append("dibv:");
        sb.append(getDibv());
        sb.append(10);
        sb.append("DeviceName:");
        sb.append(Build.DEVICE);
        sb.append(10);
        sb.append("Android-Version:");
        sb.append(VERSION.RELEASE);
        sb.append(10);
        sb.append("encrypt:nb\n");
        sb.append("DeviceID:");
        sb.append(encode(getAdiu()));
        sb.append(10);
        sb.append("DeviceRoot:");
        sb.append(isRootDevice());
        sb.append(10);
        sb.append("Foreground:");
        sb.append(mAppIsLunchForeground);
        sb.append(10);
        String mIUIVersion = getMIUIVersion();
        if (mIUIVersion != null) {
            sb.append("MiUI:");
            sb.append(mIUIVersion);
            sb.append(10);
        }
        for (Entry next : offlineMapRecords.entrySet()) {
            if (next != null) {
                String str3 = (String) next.getKey();
                Object value = next.getValue();
                if (!TextUtils.isEmpty(str3) && value != null) {
                    sb.append(str3);
                    sb.append(":");
                    sb.append(value.toString());
                    sb.append(10);
                }
            }
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(NetworkDiagnoseUtil.FORMAT_FULL);
        Date installedTime = installedTime(packageInfo);
        Date date = new Date();
        sb.append("InstalledTime:");
        sb.append(simpleDateFormat.format(installedTime));
        sb.append(10);
        sb.append("ExceptionTime:");
        sb.append(simpleDateFormat.format(date));
        sb.append(10);
        String str4 = "";
        if (applicationInitTime != null) {
            str4 = simpleDateFormat.format(applicationInitTime);
        }
        sb.append("AmapProcessStartTime:");
        sb.append(str4);
        sb.append(10);
        if (applicationInitTime != null) {
            sb.append("ApplicationInitTime:");
            sb.append(simpleDateFormat.format(applicationInitTime));
            sb.append(10);
        }
        File dataDirectory = Environment.getDataDirectory();
        if (dataDirectory != null && dataDirectory.exists()) {
            long usableSpace = dataDirectory.getUsableSpace();
            sb.append("DataFreeSize:");
            sb.append(usableSpace);
            sb.append(10);
        }
        sb.append("PID:");
        sb.append(Process.myPid());
        sb.append(10);
        sb.append("CurrentPage:");
        sb.append(controler.b());
        sb.append(10);
        try {
            sb.append("ProcessMemeryInfo:");
            sb.append(GetMemoryInfoString());
            sb.append(10);
            String E = getControler().E();
            if (E == null || E.isEmpty()) {
                E = "unknown";
            }
            sb.append("ExternalStoragePath:");
            sb.append(E);
            sb.append(10);
            sb.append("ExternalStorageSize:");
            long j = 0;
            sb.append(E.equals("unknown") ? 0 : new File(E).getUsableSpace());
            sb.append(10);
            String D = getControler().D();
            if (D == null || D.isEmpty()) {
                D = "unknown";
            }
            sb.append("InternalStoragePath:");
            sb.append(D);
            sb.append(10);
            sb.append("InternalStorageSize:");
            if (!D.equals("unknown")) {
                j = new File(D).getUsableSpace();
            }
            sb.append(j);
            sb.append(10);
            sb.append("ABI:");
            sb.append(SystemProperties.get("ro.product.cpu.abi", "unknown"));
            sb.append(10);
            sb.append("VMHeap:");
            sb.append(SystemProperties.get("dalvik.vm.heapsize", "unknown"));
            sb.append(10);
            sb.append("NetworkType:");
            sb.append(controler.d());
            sb.append(10);
            sb.append("Operator:");
            sb.append(controler.c());
            sb.append(10);
            sb.append("CurrentCity:");
            sb.append(controler.r());
            sb.append(10);
            sb.append("Debugable:");
            sb.append(SystemProperties.get("ro.debuggable", "unknown"));
            sb.append(10);
            sb.append("FingerPrint:");
            sb.append(SystemProperties.get("ro.build.fingerprint", "unknown"));
            sb.append(10);
        } catch (Throwable th) {
            th.printStackTrace();
        }
        String sb2 = sb.toString();
        StringBuilder sb3 = new StringBuilder("Exception:(");
        sb3.append(packageInfo.versionName);
        sb3.append(")");
        sb3.append(str);
        String sb4 = sb3.toString();
        sb.append("Tag:");
        if (TextUtils.isEmpty(Tag)) {
            str2 = "";
        } else {
            StringBuilder sb5 = new StringBuilder();
            sb5.append(Tag);
            sb5.append(":");
            str2 = sb5.toString();
        }
        sb.append(str2);
        sb.append(bmq.a(sb2.trim()));
        sb.append(":");
        sb.append(bmq.a(sb4.trim()));
        sb.append(10);
        sb.append(sb4);
        sb.append("\n\n");
        return sb.toString();
    }

    private static String GetMemoryInfoString() {
        MemoryInfo memoryInfo = new MemoryInfo();
        Debug.getMemoryInfo(memoryInfo);
        StringBuffer stringBuffer = new StringBuffer(128);
        try {
            Field declaredField = memoryInfo.getClass().getDeclaredField("otherStats");
            declaredField.setAccessible(true);
            int[] iArr = (int[]) declaredField.get(memoryInfo);
            if (iArr != null && iArr.length > 0) {
                for (int i = 0; i < iArr.length; i++) {
                    stringBuffer.append(iArr[i]);
                    if (i < iArr.length - 1) {
                        stringBuffer.append(',');
                    }
                }
            }
        } catch (Throwable unused) {
        }
        StringBuilder sb = new StringBuilder();
        sb.append(memoryInfo.getTotalPss());
        sb.append("/");
        sb.append(memoryInfo.dalvikPss);
        sb.append("/");
        sb.append(memoryInfo.nativePss);
        sb.append("/");
        sb.append(memoryInfo.otherPss);
        sb.append("/");
        sb.append(Runtime.getRuntime().maxMemory());
        sb.append("/");
        sb.append(stringBuffer.toString());
        return sb.toString();
    }

    private static String getDic() {
        try {
            return controler.g();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getDiu() {
        try {
            return controler.h();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getAdiu() {
        try {
            return controler.i();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getTid() {
        try {
            return controler.f();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getDiv() {
        try {
            return controler.M();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getDibv() {
        try {
            return controler.N();
        } catch (Throwable unused) {
            return "";
        }
    }

    private static String getMIUIVersion() {
        BufferedReader bufferedReader;
        String str = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(new File("/system/build.prop")));
            String readLine = bufferedReader.readLine();
            while (true) {
                if (readLine != null) {
                    if (readLine.contains("ro.miui.ui.version.name")) {
                        str = readLine.substring(readLine.indexOf("=") + 1);
                        break;
                    }
                    readLine = bufferedReader.readLine();
                }
            }
            bufferedReader.close();
            break;
        } catch (Throwable unused) {
        }
        return str;
    }

    private static boolean isRootDevice() {
        if (systemRootState == 1) {
            return true;
        }
        if (systemRootState == 0) {
            return false;
        }
        String[] strArr = {"/system/bin/", "/system/xbin/", "/system/sbin/", "/sbin/", "/vendor/bin/"};
        int i = 0;
        while (i < 5) {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append(strArr[i]);
                sb.append("su");
                if (new File(sb.toString()).exists()) {
                    systemRootState = 1;
                    return true;
                }
                i++;
            } catch (Throwable unused) {
            }
        }
        systemRootState = 0;
        return false;
    }

    @SuppressLint({"NewApi"})
    private static Date installedTime(PackageInfo packageInfo) throws NameNotFoundException {
        if (VERSION.SDK_INT >= 9) {
            return new Date(packageInfo.lastUpdateTime);
        }
        ApplicationInfo applicationInfo = application.getPackageManager().getApplicationInfo(application.getPackageName(), 0);
        if (applicationInfo != null) {
            return new Date(new File(applicationInfo.sourceDir).lastModified());
        }
        return null;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x005c, code lost:
        if (r2 == null) goto L_0x0080;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:19:?, code lost:
        r2.destroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x007d, code lost:
        if (r2 == null) goto L_0x0080;
     */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x006e A[SYNTHETIC, Splitter:B:28:0x006e] */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x0073 A[SYNTHETIC, Splitter:B:32:0x0073] */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x007a A[SYNTHETIC, Splitter:B:40:0x007a] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getProcessStartTime() {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 0
            int r2 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0077, all -> 0x0069 }
            java.lang.Runtime r3 = java.lang.Runtime.getRuntime()     // Catch:{ Throwable -> 0x0077, all -> 0x0069 }
            java.lang.String r4 = "ls -ld /proc/"
            java.lang.String r2 = java.lang.String.valueOf(r2)     // Catch:{ Throwable -> 0x0077, all -> 0x0069 }
            java.lang.String r2 = r4.concat(r2)     // Catch:{ Throwable -> 0x0077, all -> 0x0069 }
            java.lang.Process r2 = r3.exec(r2)     // Catch:{ Throwable -> 0x0077, all -> 0x0069 }
            java.io.InputStream r3 = r2.getInputStream()     // Catch:{ Throwable -> 0x0078, all -> 0x0066 }
            java.io.BufferedReader r1 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            java.io.InputStreamReader r4 = new java.io.InputStreamReader     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            r4.<init>(r3)     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            r1.<init>(r4)     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            r2.waitFor()     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            java.lang.String r1 = r1.readLine()     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            java.lang.String r4 = "(\\d{1,4}[-|\\/|年|\\.]\\d{1,2}[-|\\/|月|\\.]\\d{1,2}([日|号])?(\\s)*(\\d{1,2}([点|时])?((:)?\\d{1,2}(分)?((:)?\\d{1,2}(秒)?)?)?)?(\\s)*(PM|AM)?)"
            r5 = 10
            java.util.regex.Pattern r4 = java.util.regex.Pattern.compile(r4, r5)     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            java.util.regex.Matcher r4 = r4.matcher(r1)     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            if (r1 != 0) goto L_0x0057
            boolean r1 = r4.find()     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            if (r1 == 0) goto L_0x0057
            int r1 = r4.groupCount()     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            r5 = 1
            if (r1 <= r5) goto L_0x0057
            java.lang.String r1 = r4.group()     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
            r0.append(r1)     // Catch:{ Throwable -> 0x0064, all -> 0x0062 }
        L_0x0057:
            if (r3 == 0) goto L_0x005c
            r3.close()     // Catch:{ Throwable -> 0x005c }
        L_0x005c:
            if (r2 == 0) goto L_0x0080
        L_0x005e:
            r2.destroy()     // Catch:{ Throwable -> 0x0080 }
            goto L_0x0080
        L_0x0062:
            r0 = move-exception
            goto L_0x006c
        L_0x0064:
            r1 = r3
            goto L_0x0078
        L_0x0066:
            r0 = move-exception
            r3 = r1
            goto L_0x006c
        L_0x0069:
            r0 = move-exception
            r2 = r1
            r3 = r2
        L_0x006c:
            if (r3 == 0) goto L_0x0071
            r3.close()     // Catch:{ Throwable -> 0x0071 }
        L_0x0071:
            if (r2 == 0) goto L_0x0076
            r2.destroy()     // Catch:{ Throwable -> 0x0076 }
        L_0x0076:
            throw r0
        L_0x0077:
            r2 = r1
        L_0x0078:
            if (r1 == 0) goto L_0x007d
            r1.close()     // Catch:{ Throwable -> 0x007d }
        L_0x007d:
            if (r2 == 0) goto L_0x0080
            goto L_0x005e
        L_0x0080:
            java.lang.String r0 = r0.toString()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.tool.CrashLogUtil.getProcessStartTime():java.lang.String");
    }

    public static void uploadCrash(boolean z) {
        if (z) {
            bnh.a((bmt) null).b();
        } else {
            bnh.a((bmt) null).a();
        }
    }

    public static File getBackupFileName() {
        return controler.w();
    }

    static File getErrorSoUploadDir() {
        return getControler().t();
    }

    public static void uninstall() {
        dumpcrash.uninstall();
        stopMonitors();
    }

    private static void startMonitors() {
        try {
            bne bne = new bne(controler.o());
            mActivityStatusMonitor = bne;
            if (!bne.b) {
                bne.a.registerActivityLifecycleCallbacks(bne);
                bne.b = true;
            }
        } catch (Throwable unused) {
        }
    }

    private static void stopMonitors() {
        try {
            if (mActivityStatusMonitor != null) {
                bne bne = mActivityStatusMonitor;
                if (bne.b) {
                    bne.a.unregisterActivityLifecycleCallbacks(bne);
                    bne.b = false;
                }
            }
        } catch (Throwable unused) {
        }
    }

    @Deprecated
    public static void addCustomData(String str) {
        bmp.a(str);
    }

    public static Application getApplication() {
        return application;
    }

    public static void setCrashNotifyFilePath(String str) {
        if (!TextUtils.isEmpty(str)) {
            gCrashNotifyFilePath = str;
            File file = new File(str);
            dumpcrash.setCrashNotifyFilePath(file.getAbsolutePath());
            dumpcrash.setCrashNotifyDir(file.getParentFile().getAbsolutePath());
        }
    }

    public static String getCrashNotifyFilePath() {
        return gCrashNotifyFilePath;
    }

    private static boolean matchThreadName(ArrayList<Pattern> arrayList, String str) {
        Iterator<Pattern> it = arrayList.iterator();
        while (it.hasNext()) {
            if (it.next().matcher(str).matches()) {
                return true;
            }
        }
        return false;
    }

    private static String getOtherThreadsInfo(Thread thread) {
        ArrayList arrayList;
        if (dumpAllThreadsWhiteList != null) {
            arrayList = new ArrayList();
            for (String compile : dumpAllThreadsWhiteList) {
                try {
                    arrayList.add(Pattern.compile(compile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            arrayList = null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n+++ +++ +++ +++ +++ +++ +++ +++ Java Thread Begin +++ +++ +++ +++ +++ +++ +++ +++\n");
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        int i = 0;
        int i2 = 0;
        for (Entry next : allStackTraces.entrySet()) {
            Thread thread2 = (Thread) next.getKey();
            StackTraceElement[] stackTraceElementArr = (StackTraceElement[]) next.getValue();
            if (!thread2.getName().equals(thread.getName()) && (arrayList == null || matchThreadName(arrayList, thread2.getName()))) {
                i2++;
                sb.append("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n");
                sb.append("pid: ");
                sb.append(Process.myPid());
                sb.append(", tid: ");
                sb.append(thread2.getId());
                sb.append(", name: ");
                sb.append(thread2.getName());
                sb.append("\n");
                sb.append("\n");
                sb.append("java stacktrace:\n");
                for (StackTraceElement stackTraceElement : stackTraceElementArr) {
                    sb.append("    at ");
                    sb.append(stackTraceElement.toString());
                    sb.append("\n");
                }
                sb.append("\n");
                i++;
            }
        }
        if (allStackTraces.size() > 1) {
            if (i == 0) {
                sb.append("--- --- --- --- --- --- --- --- --- --- --- --- --- --- --- ---\n");
            }
            sb.append("total JVM threads (exclude the crashed thread): ");
            sb.append(allStackTraces.size() - 1);
            sb.append("\n");
            if (arrayList != null) {
                sb.append("JVM threads matched whitelist: ");
                sb.append(i2);
                sb.append("\n");
            }
            sb.append("dumped JVM threads:");
            sb.append(i);
            sb.append("\n");
            sb.append("+++ +++ +++ +++ +++ +++ +++ +++ Java Thread End +++ +++ +++ +++ +++ +++ +++ +++\n");
        }
        return sb.toString();
    }

    private static String getNativeOtherThreadsInfo(Thread thread) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n+++ +++ +++ +++ +++ +++ +++ +++ Native Thread Begin +++ +++ +++ +++ +++ +++ +++ +++\n");
        sb.append(getNativeThreadBacktrace((int) thread.getId()));
        sb.append("\n+++ +++ +++ +++ +++ +++ +++ +++ +++  Native Thread End +++ +++ +++ +++ +++ +++ +++\n");
        return sb.toString();
    }

    public static String getNativeThreadBacktrace(int i) {
        return dumpcrash.getNativeThreadBacktrace(i);
    }
}
