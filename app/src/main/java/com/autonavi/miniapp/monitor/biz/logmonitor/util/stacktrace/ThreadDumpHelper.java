package com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Process;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.trace.TraceLogger;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.link.protocol.http.MultipartUtility;
import com.autonavi.miniapp.monitor.util.MonitorUtils;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ThreadDumpHelper {
    private static final int MAX_DUMP_ALL_STACKTRACES_TIMES = 20;
    private static final String SP_FILE_NAME;
    private static final String TAG = "ThreadDumpHelper";
    private Context mContext;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append(LoggerFactory.getProcessInfo().getProcessTag());
        sb.append("_TraceDumpTimes");
        SP_FILE_NAME = sb.toString();
    }

    public ThreadDumpHelper(Context context) {
        this.mContext = context;
    }

    private Set<Integer> getPIDsByUID() {
        HashSet hashSet = new HashSet();
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.mContext.getSystemService(WidgetType.ACTIVITY)).getRunningAppProcesses();
            int userId = LoggerFactory.getProcessInfo().getUserId();
            for (RunningAppProcessInfo next : runningAppProcesses) {
                if (next.uid == userId) {
                    hashSet.add(Integer.valueOf(next.pid));
                }
            }
        } catch (Throwable th) {
            LoggerFactory.getTraceLogger().error(TAG, "getPIDsByUID", th);
        }
        return hashSet;
    }

    private String[] listFiles(String str) {
        if (str == null || str.length() <= 0) {
            return null;
        }
        File file = new File(str);
        if (!file.exists() || !file.isDirectory()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return null;
        }
        String[] strArr = new String[listFiles.length];
        for (int i = 0; i < listFiles.length; i++) {
            strArr[i] = listFiles[i].getName();
        }
        return strArr;
    }

    private boolean clearFile(String str) {
        String[] listFiles = listFiles(str);
        boolean z = true;
        if (listFiles == null) {
            return true;
        }
        int i = 0;
        while (true) {
            if (i >= listFiles.length) {
                break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append(listFiles[i]);
            if (!doClearFile(sb.toString())) {
                z = false;
                break;
            }
            i++;
        }
        return z;
    }

    private File getLastModifiedFile(String str) {
        String[] listFiles = listFiles(str);
        if (listFiles == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(listFiles[0]);
        File file = new File(sb.toString());
        for (int i = 1; i < listFiles.length; i++) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append(listFiles[i]);
            File file2 = new File(sb2.toString());
            if (file2.lastModified() > file.lastModified()) {
                file = file2;
            }
        }
        return file;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x00bf, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x00c1, code lost:
        r14 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x00c2, code lost:
        r1 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:?, code lost:
        r1.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:?, code lost:
        r2.close();
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x00bf A[ExcHandler: all (th java.lang.Throwable), Splitter:B:3:0x000d] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00d3 A[SYNTHETIC, Splitter:B:45:0x00d3] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00dd A[SYNTHETIC, Splitter:B:51:0x00dd] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String dumpTraces(java.io.File r14, int r15) {
        /*
            r13 = this;
            java.lang.StringBuffer r0 = new java.lang.StringBuffer
            r0.<init>()
            r1 = 0
            java.io.RandomAccessFile r2 = new java.io.RandomAccessFile     // Catch:{ Throwable -> 0x00c7 }
            java.lang.String r3 = "r"
            r2.<init>(r14, r3)     // Catch:{ Throwable -> 0x00c7 }
            long r3 = r2.length()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r5 = 1
            long r3 = r3 - r5
            r2.seek(r3)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r14 = 0
            r1 = 0
        L_0x0019:
            r7 = 0
            int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r9 < 0) goto L_0x00bb
            int r9 = r2.read()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r10 = 10
            if (r9 == r10) goto L_0x002b
            r10 = 13
            if (r9 != r10) goto L_0x0093
        L_0x002b:
            java.lang.String r9 = r2.readLine()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            if (r9 == 0) goto L_0x005c
            java.lang.String r10 = "---- end"
            boolean r10 = r9.contains(r10)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            if (r10 == 0) goto L_0x005c
            java.lang.String r10 = "----- end "
            java.lang.String r11 = ""
            java.lang.String r10 = r9.replace(r10, r11)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r11 = " -----"
            java.lang.String r12 = ""
            java.lang.String r10 = r10.replace(r11, r12)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            int r10 = java.lang.Integer.parseInt(r10)     // Catch:{ Throwable -> 0x004e, all -> 0x00bf }
            goto L_0x0059
        L_0x004e:
            r10 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r12 = "ThreadDumpHelper"
            r11.error(r12, r10)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r10 = 0
        L_0x0059:
            if (r10 != r15) goto L_0x005c
            r1 = 1
        L_0x005c:
            if (r9 == 0) goto L_0x007d
            java.lang.String r10 = "----- pid"
            boolean r10 = r9.contains(r10)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            if (r10 == 0) goto L_0x007d
            if (r1 == 0) goto L_0x007d
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r15.<init>()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r15.append(r9)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r1 = "\n"
            r15.append(r1)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r15 = r15.toString()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r0.insert(r14, r15)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            goto L_0x00bb
        L_0x007d:
            if (r1 == 0) goto L_0x0093
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r10.<init>()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r10.append(r9)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r9 = "\n"
            r10.append(r9)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r9 = r10.toString()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r0.insert(r14, r9)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
        L_0x0093:
            r9 = 0
            long r3 = r3 - r5
            int r9 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            if (r9 < 0) goto L_0x009e
            r2.seek(r3)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            goto L_0x0019
        L_0x009e:
            r2.seek(r7)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r7.<init>()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r8 = r2.readLine()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r7.append(r8)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r8 = "\n"
            r7.append(r8)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            java.lang.String r7 = r7.toString()     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            r0.insert(r14, r7)     // Catch:{ Throwable -> 0x00c1, all -> 0x00bf }
            goto L_0x0019
        L_0x00bb:
            r2.close()     // Catch:{ IOException -> 0x00d6 }
            goto L_0x00d6
        L_0x00bf:
            r14 = move-exception
            goto L_0x00db
        L_0x00c1:
            r14 = move-exception
            r1 = r2
            goto L_0x00c8
        L_0x00c4:
            r14 = move-exception
            r2 = r1
            goto L_0x00db
        L_0x00c7:
            r14 = move-exception
        L_0x00c8:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r15 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x00c4 }
            java.lang.String r2 = "ThreadDumpHelper"
            r15.error(r2, r14)     // Catch:{ all -> 0x00c4 }
            if (r1 == 0) goto L_0x00d6
            r1.close()     // Catch:{ IOException -> 0x00d6 }
        L_0x00d6:
            java.lang.String r14 = r0.toString()
            return r14
        L_0x00db:
            if (r2 == 0) goto L_0x00e0
            r2.close()     // Catch:{ IOException -> 0x00e0 }
        L_0x00e0:
            throw r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.ThreadDumpHelper.dumpTraces(java.io.File, int):java.lang.String");
    }

    private int getDumpAllStackTracesTimes() {
        return this.mContext.getSharedPreferences(SP_FILE_NAME, 0).getInt("dumpAllStackTracesTimes", 0);
    }

    private void setDumpAllStackTracesTimes(int i) {
        Editor edit = this.mContext.getSharedPreferences(SP_FILE_NAME, 0).edit();
        edit.putInt("dumpAllStackTracesTimes", i);
        edit.commit();
    }

    private String getThreadsTraces(int i, boolean z) {
        String str;
        int dumpAllStackTracesTimes = getDumpAllStackTracesTimes();
        if (listFiles("/data/anr/") == null) {
            return "listFiles(\"/data/anr/\") is null";
        }
        if (z || dumpAllStackTracesTimes < 20) {
            if (clearFile("/data/anr/")) {
                str = getTracesBySendSignal(i);
            } else {
                str = getTracesBySendSignal(i);
                setDumpAllStackTracesTimes(dumpAllStackTracesTimes + 1);
            }
            return str;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(dumpAllStackTracesTimes);
        sb.append(" times exceed MAX_DUMP_ALL_STACKTRACES_TIMES 20");
        return sb.toString();
    }

    private String getTracesBySendSignal(int i) {
        try {
            Process.sendSignal(i, 3);
            Thread.sleep(2500);
        } catch (InterruptedException unused) {
        }
        return dumpTraces(getLastModifiedFile("/data/anr/"), i);
    }

    private boolean doClearFile(String str) {
        boolean z = true;
        try {
            File file = new File(str);
            if (file.isDirectory()) {
                return true;
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write("");
            fileWriter.close();
            return z;
        } catch (Throwable unused) {
            LoggerFactory.getTraceLogger().warn((String) "dumpAllStackTraces", (String) "Clear ANR File fail");
            z = false;
        }
    }

    public String dumpAllStackTraces(boolean z) {
        Set<Integer> pIDsByUID = getPIDsByUID();
        if (pIDsByUID == null || pIDsByUID.size() == 0) {
            return "pidSet is empty";
        }
        StringBuilder sb = new StringBuilder();
        for (Integer intValue : pIDsByUID) {
            String threadsTraces = getThreadsTraces(intValue.intValue(), z);
            if (threadsTraces != null) {
                sb.append(threadsTraces);
                sb.append("\n###\n");
                LoggerFactory.getTraceLogger().warn((String) "dumpAllStackTraces", threadsTraces);
            }
        }
        return sb.toString();
    }

    public boolean logAllThreadsTraces(Context context, String str) {
        String[] obtainThreadsStackTrace = MonitorUtils.obtainThreadsStackTrace();
        if (TextUtils.isEmpty(obtainThreadsStackTrace[0])) {
            return false;
        }
        TraceLogger traceLogger = LoggerFactory.getTraceLogger();
        StringBuilder sb = new StringBuilder("Processes(");
        sb.append(str);
        sb.append(") All Threads Traces: ###");
        sb.append(obtainThreadsStackTrace[0]);
        traceLogger.warn((String) "monitor", sb.toString());
        return true;
    }

    private List<String> getStackTraceFilepath() {
        ArrayList arrayList = new ArrayList();
        try {
            Object invoke = Class.forName("android.os.SystemProperties").getMethod("get", new Class[]{String.class}).invoke(null, new Object[]{"dalvik.vm.stack-trace-file"});
            if (invoke != null && (invoke instanceof String) && new File((String) invoke).exists()) {
                arrayList.add((String) invoke);
            }
        } catch (Exception e) {
            LoggerFactory.getTraceLogger().error((String) TAG, (Throwable) e);
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        File[] listFiles = new File("/data/anr").listFiles();
        if (listFiles != null) {
            for (File file : listFiles) {
                if (file != null && file.exists() && file.isFile() && file.getName().contains(this.mContext.getPackageName())) {
                    arrayList.add(file.getAbsolutePath());
                }
            }
            if (!arrayList.isEmpty()) {
                return arrayList;
            }
            for (File file2 : listFiles) {
                if (file2 != null && file2.exists() && file2.isFile()) {
                    arrayList.add(file2.getAbsolutePath());
                }
            }
        }
        return arrayList;
    }

    public String dumpLastAnrTrace() {
        StringBuilder sb = new StringBuilder();
        List<String> stackTraceFilepath = getStackTraceFilepath();
        if (stackTraceFilepath == null || stackTraceFilepath.size() <= 0) {
            return null;
        }
        for (String next : stackTraceFilepath) {
            String dumpTempStackTrace = dumpTempStackTrace(new File(next));
            sb.append(next);
            sb.append(MultipartUtility.LINE_FEED);
            sb.append(dumpTempStackTrace);
        }
        return sb.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:33:0x00ad, code lost:
        if (r2 != null) goto L_0x0091;
     */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x00ca A[SYNTHETIC, Splitter:B:41:0x00ca] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.lang.String dumpTempStackTrace(java.io.File r8) {
        /*
            r7 = this;
            long r0 = r8.length()
            r2 = 52428800(0x3200000, double:2.5903269E-316)
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            r1 = 0
            if (r0 <= 0) goto L_0x000d
            return r1
        L_0x000d:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.io.BufferedReader r2 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x009a, all -> 0x0097 }
            java.io.FileReader r3 = new java.io.FileReader     // Catch:{ Throwable -> 0x009a, all -> 0x0097 }
            r3.<init>(r8)     // Catch:{ Throwable -> 0x009a, all -> 0x0097 }
            r2.<init>(r3)     // Catch:{ Throwable -> 0x009a, all -> 0x0097 }
            r8 = 0
            r3 = 0
        L_0x001e:
            java.lang.String r4 = r2.readLine()     // Catch:{ Throwable -> 0x0095 }
            if (r4 == 0) goto L_0x0091
            java.lang.String r5 = "----- pid"
            boolean r5 = r4.contains(r5)     // Catch:{ Throwable -> 0x0095 }
            if (r5 == 0) goto L_0x005a
            java.lang.String r5 = r2.readLine()     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r6 = "Cmd line:"
            boolean r6 = r5.contains(r6)     // Catch:{ Throwable -> 0x0095 }
            if (r6 == 0) goto L_0x005b
            android.content.Context r6 = r7.mContext     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ Throwable -> 0x0095 }
            boolean r6 = r5.contains(r6)     // Catch:{ Throwable -> 0x0095 }
            if (r6 == 0) goto L_0x005b
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0095 }
            r3.<init>()     // Catch:{ Throwable -> 0x0095 }
            r3.append(r4)     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r4 = "\n"
            r3.append(r4)     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0095 }
            r0.append(r3)     // Catch:{ Throwable -> 0x0095 }
            r3 = 1
            goto L_0x005b
        L_0x005a:
            r5 = r4
        L_0x005b:
            java.lang.String r4 = "----- end"
            boolean r4 = r5.contains(r4)     // Catch:{ Throwable -> 0x0095 }
            if (r4 == 0) goto L_0x007a
            if (r3 == 0) goto L_0x007a
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0095 }
            r3.<init>()     // Catch:{ Throwable -> 0x0095 }
            r3.append(r5)     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r4 = "\n"
            r3.append(r4)     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r3 = r3.toString()     // Catch:{ Throwable -> 0x0095 }
            r0.append(r3)     // Catch:{ Throwable -> 0x0095 }
            r3 = 0
        L_0x007a:
            if (r3 == 0) goto L_0x001e
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0095 }
            r4.<init>()     // Catch:{ Throwable -> 0x0095 }
            r4.append(r5)     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r5 = "\n"
            r4.append(r5)     // Catch:{ Throwable -> 0x0095 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0095 }
            r0.append(r4)     // Catch:{ Throwable -> 0x0095 }
            goto L_0x001e
        L_0x0091:
            r2.close()     // Catch:{ IOException -> 0x00b0 }
            goto L_0x00b0
        L_0x0095:
            r8 = move-exception
            goto L_0x009c
        L_0x0097:
            r8 = move-exception
            r2 = r1
            goto L_0x00c8
        L_0x009a:
            r8 = move-exception
            r2 = r1
        L_0x009c:
            java.lang.String r8 = android.util.Log.getStackTraceString(r8)     // Catch:{ all -> 0x00c7 }
            java.lang.String r3 = "\nexception on reading anr file, start:\n"
            r0.append(r3)     // Catch:{ all -> 0x00c7 }
            r0.append(r8)     // Catch:{ all -> 0x00c7 }
            java.lang.String r8 = "\nexception on reading anr file, end.\n"
            r0.append(r8)     // Catch:{ all -> 0x00c7 }
            if (r2 == 0) goto L_0x00b0
            goto L_0x0091
        L_0x00b0:
            java.lang.String r8 = r0.toString()
            java.lang.String r2 = "----- pid"
            boolean r8 = r8.contains(r2)
            if (r8 == 0) goto L_0x00c6
            java.lang.String r8 = "----- pid"
            int r8 = r0.lastIndexOf(r8)
            java.lang.String r1 = r0.substring(r8)
        L_0x00c6:
            return r1
        L_0x00c7:
            r8 = move-exception
        L_0x00c8:
            if (r2 == 0) goto L_0x00cd
            r2.close()     // Catch:{ IOException -> 0x00cd }
        L_0x00cd:
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.miniapp.monitor.biz.logmonitor.util.stacktrace.ThreadDumpHelper.dumpTempStackTrace(java.io.File):java.lang.String");
    }
}
