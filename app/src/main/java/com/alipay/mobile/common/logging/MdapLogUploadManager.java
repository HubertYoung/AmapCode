package com.alipay.mobile.common.logging;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.mobilesdk.socketcraft.monitor.MonitorItemConstants;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.common.logging.api.network.NetworkInfoGetter;
import com.alipay.mobile.common.logging.http.ConfigChangeBroadCastReceiver;
import com.alipay.mobile.common.logging.strategy.LogStrategyManager;
import com.alipay.mobile.common.logging.uploader.HttpUploader;
import com.alipay.mobile.common.logging.uploader.RpcUploader;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MdapLogUploadManager {
    public static int a = 52428800;
    public static final List<String> b = new ArrayList<String>() {
        {
            add("RPC");
            add("MMTP");
            add("MASS");
            add(MonitorItemConstants.PARTITION_NAME);
            add("MISC");
            add("mdaplog");
            add("network");
            add("keybiztrace");
            add("footprint");
            add(LogCategory.CATEGORY_EXCEPTION);
            add("crash");
            add(LogCategory.CATEGORY_HIGHAVAIL);
            add(LogCategory.CATEGORY_APM);
            add("trafficLog");
            add(LogCategory.CATEGORY_DATAFLOW);
            add(LogCategory.CATEGORY_BATTERY);
        }
    };
    public static final Comparator<File> c = new e();
    private static MdapLogUploadManager e;
    private ConfigChangeBroadCastReceiver d;
    private Context f;
    private File g;

    public static synchronized MdapLogUploadManager a(Context context) {
        MdapLogUploadManager mdapLogUploadManager;
        synchronized (MdapLogUploadManager.class) {
            try {
                if (e == null) {
                    e = new MdapLogUploadManager(context);
                }
                mdapLogUploadManager = e;
            }
        }
        return mdapLogUploadManager;
    }

    public static MdapLogUploadManager a() {
        if (e != null) {
            return e;
        }
        throw new IllegalStateException("need createInstance before use");
    }

    private MdapLogUploadManager(Context context) {
        this.f = context;
        this.g = new File(context.getFilesDir().getAbsolutePath() + "/mdap/upload/");
        if (!this.g.exists()) {
            this.g.mkdirs();
        }
        c();
    }

    public static void a(int maxSize) {
        a = maxSize;
    }

    private void c() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.alipay.mobile.client.multi.CONFIG_CHANGE");
        this.d = new ConfigChangeBroadCastReceiver();
        if (this.f != null) {
            this.f.registerReceiver(this.d, filter);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:32:0x0097 A[SYNTHETIC, Splitter:B:32:0x0097] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final synchronized void b() {
        /*
            r17 = this;
            monitor-enter(r17)
            r2 = 0
            java.io.File r7 = new java.io.File     // Catch:{ all -> 0x007a }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ all -> 0x007a }
            r11.<init>()     // Catch:{ all -> 0x007a }
            r0 = r17
            android.content.Context r12 = r0.f     // Catch:{ all -> 0x007a }
            java.io.File r12 = r12.getFilesDir()     // Catch:{ all -> 0x007a }
            java.lang.String r12 = r12.getAbsolutePath()     // Catch:{ all -> 0x007a }
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x007a }
            java.lang.String r12 = "/mdap/"
            java.lang.StringBuilder r11 = r11.append(r12)     // Catch:{ all -> 0x007a }
            java.lang.String r11 = r11.toString()     // Catch:{ all -> 0x007a }
            r7.<init>(r11)     // Catch:{ all -> 0x007a }
            boolean r11 = com.alipay.mobile.common.logging.util.LoggingUtil.isOfflineMode()     // Catch:{ all -> 0x007a }
            if (r11 == 0) goto L_0x005c
            r0 = r17
            android.content.Context r11 = r0.f     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r12 = "mdap"
            java.io.File r2 = r11.getExternalFilesDir(r12)     // Catch:{ Throwable -> 0x0060 }
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0060 }
            java.lang.String r11 = "upload"
            r3.<init>(r2, r11)     // Catch:{ Throwable -> 0x0060 }
            boolean r11 = r3.exists()     // Catch:{ Throwable -> 0x0132, all -> 0x0136 }
            if (r11 != 0) goto L_0x0046
            r3.mkdirs()     // Catch:{ Throwable -> 0x0132, all -> 0x0136 }
        L_0x0046:
            r2 = r3
        L_0x0047:
            r0 = r17
            android.content.Context r11 = r0.f     // Catch:{ Throwable -> 0x007d }
            java.lang.String r12 = "mdap"
            java.io.File r7 = r11.getExternalFilesDir(r12)     // Catch:{ Throwable -> 0x007d }
            if (r7 == 0) goto L_0x005c
            boolean r11 = r7.exists()     // Catch:{ Throwable -> 0x007d }
            if (r11 != 0) goto L_0x005c
            r7.mkdirs()     // Catch:{ Throwable -> 0x007d }
        L_0x005c:
            if (r7 != 0) goto L_0x0097
        L_0x005e:
            monitor-exit(r17)
            return
        L_0x0060:
            r9 = move-exception
        L_0x0061:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007a }
            java.lang.String r12 = "MdapLogUploadManager"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x007a }
            java.lang.String r14 = "syncLog backupFileDir: "
            r13.<init>(r14)     // Catch:{ all -> 0x007a }
            java.lang.StringBuilder r13 = r13.append(r9)     // Catch:{ all -> 0x007a }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x007a }
            r11.error(r12, r13)     // Catch:{ all -> 0x007a }
            goto L_0x0047
        L_0x007a:
            r11 = move-exception
        L_0x007b:
            monitor-exit(r17)
            throw r11
        L_0x007d:
            r9 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007a }
            java.lang.String r12 = "MdapLogUploadManager"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x007a }
            java.lang.String r14 = "syncLog logFileDir: "
            r13.<init>(r14)     // Catch:{ all -> 0x007a }
            java.lang.StringBuilder r13 = r13.append(r9)     // Catch:{ all -> 0x007a }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x007a }
            r11.error(r12, r13)     // Catch:{ all -> 0x007a }
            goto L_0x005c
        L_0x0097:
            java.io.File[] r8 = r7.listFiles()     // Catch:{ Throwable -> 0x0117 }
            if (r8 == 0) goto L_0x005e
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r12 = "MdapLogUploadManager"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r14 = "syncLog: "
            r13.<init>(r14)     // Catch:{ Throwable -> 0x0117 }
            int r14 = r8.length     // Catch:{ Throwable -> 0x0117 }
            java.lang.StringBuilder r13 = r13.append(r14)     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r13 = r13.toString()     // Catch:{ Throwable -> 0x0117 }
            r11.info(r12, r13)     // Catch:{ Throwable -> 0x0117 }
            int r12 = r8.length     // Catch:{ Throwable -> 0x0117 }
            r11 = 0
        L_0x00b8:
            if (r11 >= r12) goto L_0x005e
            r5 = r8[r11]     // Catch:{ Throwable -> 0x0117 }
            if (r5 == 0) goto L_0x00ee
            boolean r13 = r5.exists()     // Catch:{ Throwable -> 0x0117 }
            if (r13 == 0) goto L_0x00ee
            boolean r13 = r5.isFile()     // Catch:{ Throwable -> 0x0117 }
            if (r13 == 0) goto L_0x00ee
            java.lang.String r13 = r5.getName()     // Catch:{ Throwable -> 0x00f1 }
            java.lang.String r6 = com.alipay.mobile.common.logging.util.LoggingUtil.getMdapStyleName(r13)     // Catch:{ Throwable -> 0x00f1 }
            java.io.File r10 = new java.io.File     // Catch:{ Throwable -> 0x00f1 }
            r0 = r17
            java.io.File r13 = r0.g     // Catch:{ Throwable -> 0x00f1 }
            r10.<init>(r13, r6)     // Catch:{ Throwable -> 0x00f1 }
            boolean r13 = com.alipay.mobile.common.logging.util.LoggingUtil.isOfflineMode()     // Catch:{ Throwable -> 0x00f1 }
            if (r13 == 0) goto L_0x00eb
            if (r2 == 0) goto L_0x00eb
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x00f1 }
            r1.<init>(r2, r6)     // Catch:{ Throwable -> 0x00f1 }
            com.alipay.mobile.common.logging.util.FileUtil.copyFile(r5, r1)     // Catch:{ Throwable -> 0x00f1 }
        L_0x00eb:
            com.alipay.mobile.common.logging.util.FileUtil.moveFile(r5, r10)     // Catch:{ Throwable -> 0x00f1 }
        L_0x00ee:
            int r11 = r11 + 1
            goto L_0x00b8
        L_0x00f1:
            r4 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r14 = "MdapLogUploadManager"
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0117 }
            r15.<init>()     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r16 = r5.getName()     // Catch:{ Throwable -> 0x0117 }
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r16 = ", syncLog: "
            java.lang.StringBuilder r15 = r15.append(r16)     // Catch:{ Throwable -> 0x0117 }
            java.lang.StringBuilder r15 = r15.append(r4)     // Catch:{ Throwable -> 0x0117 }
            java.lang.String r15 = r15.toString()     // Catch:{ Throwable -> 0x0117 }
            r13.error(r14, r15)     // Catch:{ Throwable -> 0x0117 }
            goto L_0x00ee
        L_0x0117:
            r9 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r11 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x007a }
            java.lang.String r12 = "MdapLogUploadManager"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ all -> 0x007a }
            java.lang.String r14 = "syncLog, move fail: "
            r13.<init>(r14)     // Catch:{ all -> 0x007a }
            java.lang.StringBuilder r13 = r13.append(r9)     // Catch:{ all -> 0x007a }
            java.lang.String r13 = r13.toString()     // Catch:{ all -> 0x007a }
            r11.error(r12, r13)     // Catch:{ all -> 0x007a }
            goto L_0x005e
        L_0x0132:
            r9 = move-exception
            r2 = r3
            goto L_0x0061
        L_0x0136:
            r11 = move-exception
            r2 = r3
            goto L_0x007b
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.MdapLogUploadManager.b():void");
    }

    public static boolean a(String logCategory) {
        if (TextUtils.isEmpty(logCategory)) {
            return false;
        }
        return b.contains(logCategory);
    }

    public final synchronized void a(String logCategory, String uploadUrl, Bundle bundle) {
        if (LogStrategyManager.getInstance().isRealTimeLogCategory(logCategory) && !a(logCategory)) {
            if (LoggerFactory.getProcessInfo().isToolsProcess()) {
                LoggerFactory.getTraceLogger().warn((String) "MdapLogUploadManager", (String) "upload real time logCategory in tool!!");
            }
            new RpcUploader(this.g, this.f).a(logCategory, bundle);
        } else if (!LoggerFactory.getProcessInfo().isPushProcess() || bundle == null || !"maxLogCount".equals(bundle.getString("event"))) {
            b(logCategory, uploadUrl, bundle);
        } else {
            NetworkInfoGetter netWorkInfo = LoggerFactory.getLogContext().getNetworkInfoGetter();
            if (netWorkInfo == null || netWorkInfo.isNetworkAvailable() || netWorkInfo.isConnect()) {
                b(logCategory, uploadUrl, bundle);
            } else {
                LoggerFactory.getTraceLogger().info("MdapLogUploadManager", "upload time is maxLogCount and network is not available!!! Do not upload category = " + logCategory);
            }
        }
    }

    private void b(String logCategory, String uploadUrl, Bundle bundle) {
        LoggerFactory.getTraceLogger().info("MdapLogUploadManager", "http upload logCategory = " + logCategory);
        new HttpUploader(this.g, this.f).a(logCategory, uploadUrl, bundle);
    }
}
