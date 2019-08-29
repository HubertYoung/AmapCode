package com.alipay.android.phone.mobilesdk.permission.guide.a;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimedia.api.MultimediaImageService;
import com.alipay.android.phone.mobilesdk.permission.guide.e;
import com.alipay.android.phone.mobilesdk.permission.guide.provider.DataProvider;
import com.alipay.android.phone.mobilesdk.permission.utils.d;
import com.alipay.android.phone.mobilesdk.permission.utils.h;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import com.alipay.mobile.framework.LauncherApplicationAgent;
import com.alipay.mobile.framework.service.common.TaskScheduleService;
import com.alipay.mobile.quinox.utils.Constants;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.ClientPGInfoRespPB;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.PgDataPB;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.PgTemplateInfoDataPB;
import com.alipay.mobileappcommon.biz.rpc.pginfo.model.PgTemplateInfoPB;
import com.squareup.wire.Wire;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* compiled from: PermissionGuideManager */
public class c {
    private static c e;
    private long a;
    private long b;
    private long c;
    private e d;
    private int f = 0;

    /* compiled from: PermissionGuideManager */
    public class a {
        public final long a;
        public final long b;
        public final long c;
        public final long d;
        public final long e;
        public final long f;

        /* synthetic */ a(c x0, long x1, long x2, long x3, long x4, long x5, long x6, byte b2) {
            this(x1, x2, x3, x4, x5, x6);
        }

        private a(long fSPSB, long fSPDB, long fDPDB, long lgSPSB, long lgSPDB, long lgDPDB) {
            this.a = fSPSB;
            this.b = fSPDB;
            this.c = fDPDB;
            this.d = lgSPSB;
            this.e = lgSPDB;
            this.f = lgDPDB;
        }
    }

    private c() {
        Application context = LauncherApplicationAgent.getInstance().getApplicationContext();
        b((Context) context);
        File dir = new File(context.getFilesDir(), Constants.DIR_NAME_PERMISSIONS);
        if (!dir.exists() && !dir.mkdirs()) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", "Failed to mkdirs:" + dir);
        }
        this.a = a.a((Context) context, (String) "pgFatigue");
        this.b = a.a((Context) context, (String) "samePgFatigue");
        this.c = a.a((Context) context, (String) "differencePgFatigue");
        LoggerFactory.getTraceLogger().debug("Permissions", "new PermissionGuideManager(): mPGFatigue=" + this.a + ", mSamePGFatigue=" + this.b + ", mDifferencePGFatigue=" + this.c);
        File recordsFile = new File(dir, Constants.File_RECORDS);
        if (recordsFile.exists()) {
            byte[] bytes = d.a(recordsFile);
            if (bytes != null && bytes.length > 0) {
                try {
                    this.d = (e) new Wire((Class<?>[]) new Class[0]).parseFrom(bytes, e.class);
                } catch (IOException e2) {
                    LoggerFactory.getTraceLogger().warn((String) "Permissions", (Throwable) e2);
                }
            }
        }
    }

    private static boolean a(Context context) {
        b fileLock = new b();
        try {
            return fileLock.a(context);
        } finally {
            fileLock.a();
        }
    }

    private static void e() {
        Application context = LauncherApplicationAgent.getInstance().getApplicationContext();
        a.a(context);
        if (!a((Context) context)) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "clear, can't acquire lock.");
            return;
        }
        LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "clear all permission guide data!");
        File dir = new File(context.getFilesDir(), Constants.DIR_NAME_PERMISSIONS);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    if (!file.exists()) {
                        LoggerFactory.getTraceLogger().debug("Permissions", "No need to delete file:" + file + ", it is not exist.");
                    } else if (file.delete()) {
                        LoggerFactory.getTraceLogger().debug("Permissions", "Success to delete file:" + file);
                    } else {
                        LoggerFactory.getTraceLogger().debug("Permissions", "Failed to delete file:" + file + ", try to delete again on VM exit.");
                    }
                }
            }
        }
    }

    public static c a() {
        if (e == null) {
            synchronized (c.class) {
                try {
                    if (e == null) {
                        e = new c();
                    }
                }
            }
        }
        return e;
    }

    public final synchronized void a(Context context, ClientPGInfoRespPB infoRespPB) {
        File dir = new File(context.getFilesDir(), Constants.DIR_NAME_PERMISSIONS);
        if (dir.exists() || dir.mkdirs()) {
            List pgData = infoRespPB.pgData;
            if (pgData != null && !pgData.isEmpty()) {
                a(context, pgData, dir, infoRespPB.fatigueData, infoRespPB.pgDeleteData, infoRespPB.lastTime);
            }
        } else {
            LoggerFactory.getTraceLogger().error((String) "Permissions", "Failed to mkdirs:" + dir);
        }
    }

    /* access modifiers changed from: private */
    public void a(Context context, List<PgTemplateInfoDataPB> pgData, File dir, List<PgDataPB> fatigueData, List<String> pgDeleteData, String lastUpdateServerTime) {
        if (context == null) {
            this.f = 0;
            return;
        }
        b fileLock = new b();
        try {
            if (!fileLock.a(context)) {
                if (this.f < 3) {
                    LoggerFactory.getTraceLogger().warn((String) "Permissions", (String) "Can't get file lock, write failure, retry...");
                    this.f++;
                    final Context context2 = context;
                    final List<PgTemplateInfoDataPB> list = pgData;
                    final File file = dir;
                    final List<PgDataPB> list2 = fatigueData;
                    final List<String> list3 = pgDeleteData;
                    final String str = lastUpdateServerTime;
                    a((Runnable) new Runnable() {
                        public final void run() {
                            c.this.a(context2, list, file, list2, list3, str);
                        }
                    }, (String) "RetryProcessPermissionGuideFileTask", TimeUnit.SECONDS);
                } else {
                    LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "Can't get file lock, write failure!");
                    this.f = 0;
                }
                return;
            }
            this.f = 0;
            MultimediaImageService multimediaImageService = (MultimediaImageService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(MultimediaImageService.class.getName());
            for (PgTemplateInfoDataPB templateInfoDataPB : pgData) {
                PgTemplateInfoPB pgTemplateInfoPB = templateInfoDataPB.value;
                File file2 = new File(dir, e.a(templateInfoDataPB.key));
                LoggerFactory.getTraceLogger().verbose("Permissions", "Save [" + pgTemplateInfoPB + "] to file:" + file2 + ", bRet=" + d.a(pgTemplateInfoPB.toByteArray(), file2));
                if (!TextUtils.isEmpty(pgTemplateInfoPB.imgUrl)) {
                    multimediaImageService.loadOriginalImage(pgTemplateInfoPB.imgUrl, null, null, null, "antbasic_permissions");
                }
                if (!TextUtils.isEmpty(pgTemplateInfoPB.mobileTemplateconfigImgurl)) {
                    multimediaImageService.loadOriginalImage(pgTemplateInfoPB.mobileTemplateconfigImgurl, null, null, null, "antbasic_permissions");
                }
            }
            if (pgDeleteData != null) {
                if (!pgDeleteData.isEmpty()) {
                    if (!dir.exists() || !dir.isDirectory()) {
                        LoggerFactory.getTraceLogger().info("Permissions", "Dir: " + dir + ", dir.exists() && dir.isDirectory() == false");
                    } else {
                        for (String a2 : pgDeleteData) {
                            File file3 = new File(dir, e.a(a2));
                            if (!file3.exists()) {
                                LoggerFactory.getTraceLogger().debug("Permissions", "No need to delete file:" + file3 + ", it is not exist.");
                            } else if (file3.delete()) {
                                LoggerFactory.getTraceLogger().debug("Permissions", "Success to delete file:" + file3);
                            } else {
                                LoggerFactory.getTraceLogger().debug("Permissions", "Failed to delete file:" + file3 + ", try to delete again on VM exit.");
                            }
                        }
                    }
                }
            }
            if (fatigueData != null && !fatigueData.isEmpty()) {
                for (PgDataPB pgDataPB : fatigueData) {
                    if ("pgFatigue".equals(pgDataPB.key)) {
                        try {
                            long pgFatigue = Long.parseLong(pgDataPB.value);
                            if (pgFatigue >= 0) {
                                a.a(context, (String) "pgFatigue", pgFatigue);
                                LoggerFactory.getTraceLogger().debug("Permissions", "update pgFatigue:" + this.a + "=>" + pgFatigue + " minute");
                                this.a = pgFatigue;
                            }
                        } catch (Throwable e2) {
                            LoggerFactory.getTraceLogger().warn((String) "Permissions", e2);
                        }
                    } else if ("samePgFatigue".equals(pgDataPB.key)) {
                        try {
                            long samePgFatigue = Long.parseLong(pgDataPB.value);
                            if (samePgFatigue >= 0) {
                                a.a(context, (String) "samePgFatigue", samePgFatigue);
                                LoggerFactory.getTraceLogger().debug("Permissions", "update samePgFatigue:" + this.b + "=>" + samePgFatigue + " minute");
                                this.b = samePgFatigue;
                            }
                        } catch (Throwable e3) {
                            LoggerFactory.getTraceLogger().warn((String) "Permissions", e3);
                        }
                    } else if ("differencePgFatigue".equals(pgDataPB.key)) {
                        try {
                            long differencePgFatigue = Long.parseLong(pgDataPB.value);
                            if (differencePgFatigue >= 0) {
                                a.a(context, (String) "differencePgFatigue", differencePgFatigue);
                                LoggerFactory.getTraceLogger().debug("Permissions", "update differencePgFatigue:" + this.c + "=>" + differencePgFatigue + " minute");
                                this.c = differencePgFatigue;
                            }
                        } catch (Throwable e4) {
                            LoggerFactory.getTraceLogger().warn((String) "Permissions", e4);
                        }
                    }
                }
            }
            if (!TextUtils.isEmpty(lastUpdateServerTime)) {
                a.d(context, lastUpdateServerTime);
            }
            fileLock.a();
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("Permissions", "processPermissionGuide", tr);
        } finally {
            fileLock.a();
        }
    }

    public final synchronized long a(String permission) {
        long time;
        time = 0;
        if (permission != null) {
            if (this.d != null && this.d.b != null && !this.d.b.isEmpty()) {
                int i = 0;
                int N = this.d.b.size();
                while (true) {
                    if (i >= N) {
                        break;
                    }
                    d record = this.d.b.get(i);
                    if (!TextUtils.equals(record.c, permission)) {
                        time = record.d.longValue();
                        break;
                    }
                    i++;
                }
            }
        }
        LoggerFactory.getTraceLogger().debug("Permissions", "getLastGuideTimeNon(" + permission + ")=" + time);
        return time;
    }

    public final synchronized long b(String permission) {
        long time;
        time = 0;
        if (permission != null) {
            if (this.d != null && this.d.b != null && !this.d.b.isEmpty()) {
                int i = 0;
                int N = this.d.b.size();
                while (true) {
                    if (i >= N) {
                        break;
                    }
                    d record = this.d.b.get(i);
                    if (TextUtils.equals(record.c, permission)) {
                        time = record.d.longValue();
                        break;
                    }
                    i++;
                }
            }
        }
        LoggerFactory.getTraceLogger().debug("Permissions", "getLastGuideTime(" + permission + ")=" + time);
        return time;
    }

    public final synchronized long a(String bizType, String permission) {
        long time;
        time = 0;
        if (permission != null) {
            if (this.d != null && this.d.b != null && !this.d.b.isEmpty()) {
                int i = 0;
                int N = this.d.b.size();
                while (true) {
                    if (i >= N) {
                        break;
                    }
                    d record = this.d.b.get(i);
                    if (TextUtils.equals(record.b, bizType) && TextUtils.equals(record.c, permission)) {
                        time = record.d.longValue();
                        break;
                    }
                    i++;
                }
            }
        }
        LoggerFactory.getTraceLogger().debug("Permissions", "getLastGuideTime(" + bizType + "," + permission + ")=" + time);
        return time;
    }

    public final synchronized void a(Context context, d... recordAry) {
        if (recordAry != null) {
            try {
                if (recordAry.length != 0) {
                    if (!LoggerFactory.getProcessInfo().isMainProcess()) {
                        LoggerFactory.getTraceLogger().info("Permissions", "addPermissionGuideRecord via content provider!");
                        ContentValues values = new ContentValues(recordAry.length);
                        for (int i = 0; i < recordAry.length; i++) {
                            values.put(String.valueOf(i), recordAry[i].toByteArray());
                        }
                        context.getContentResolver().insert(DataProvider.c(context), values);
                    } else {
                        ArrayList records = new ArrayList();
                        records.addAll(Arrays.asList(recordAry));
                        if (this.d == null) {
                            this.d = new e();
                        } else {
                            records.addAll(this.d.b);
                        }
                        com.alipay.android.phone.mobilesdk.permission.utils.e.a(records, new Comparator<d>() {
                            public final /* synthetic */ int compare(Object obj, Object obj2) {
                                return a((d) obj, (d) obj2);
                            }

                            private static int a(d lhs, d rhs) {
                                if (!lhs.b.equals(rhs.b) || !lhs.c.equals(rhs.c)) {
                                    return -1;
                                }
                                return 0;
                            }
                        });
                        this.d.b = records;
                        LoggerFactory.getTraceLogger().debug("Permissions", "addPermissionGuideRecord(" + h.a((T[]) recordAry) + ")");
                        d.a(this.d.toByteArray(), new File(new File(context.getFilesDir(), Constants.DIR_NAME_PERMISSIONS), Constants.File_RECORDS));
                    }
                }
            } catch (Throwable tr) {
                LoggerFactory.getTraceLogger().error("Permissions", "addPermissionGuideRecord", tr);
            }
        }
        LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "addPermissionGuideRecord, recordAry is empty!");
        return;
    }

    public static PgTemplateInfoPB a(Context context, String fileName) {
        if (!a(context)) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "getPgTemplateInfo, can't acquire lock.");
            return null;
        }
        PgTemplateInfoPB pgTemplateInfo = null;
        File dir = new File(context.getFilesDir(), Constants.DIR_NAME_PERMISSIONS);
        if (dir.exists()) {
            File file = new File(dir, fileName);
            if (file.exists()) {
                byte[] bytes = d.a(file);
                if (bytes != null && bytes.length >= 0) {
                    try {
                        pgTemplateInfo = (PgTemplateInfoPB) new Wire((Class<?>[]) new Class[0]).parseFrom(bytes, PgTemplateInfoPB.class);
                    } catch (IOException e2) {
                        LoggerFactory.getTraceLogger().warn((String) "Permissions", (Throwable) e2);
                    }
                }
            }
        }
        LoggerFactory.getTraceLogger().debug("Permissions", "getPgTemplateInfo(" + fileName + ") : " + pgTemplateInfo);
        return pgTemplateInfo;
    }

    public static boolean a(Context context, String bizType, String permissionName) {
        if (!a(context)) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "hasPgTemplateInfo, can't acquire lock.");
            return false;
        }
        File dir = new File(context.getFilesDir(), Constants.DIR_NAME_PERMISSIONS);
        if (!dir.exists()) {
            return false;
        }
        return new File(dir, e.a(bizType, permissionName)).exists();
    }

    public final long b() {
        return this.a;
    }

    public final long c() {
        return this.b;
    }

    public final long d() {
        return this.c;
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.alipay.android.phone.mobilesdk.permission.guide.a.c.a b(java.lang.String r29, java.lang.String r30) {
        /*
            r28 = this;
            com.alipay.mobile.common.logging.api.ProcessInfo r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()     // Catch:{ Throwable -> 0x0072 }
            boolean r5 = r5.isMainProcess()     // Catch:{ Throwable -> 0x0072 }
            if (r5 == 0) goto L_0x0032
            com.alipay.android.phone.mobilesdk.permission.guide.a.c$a r4 = new com.alipay.android.phone.mobilesdk.permission.guide.a.c$a     // Catch:{ Throwable -> 0x0072 }
            r0 = r28
            long r6 = r0.a     // Catch:{ Throwable -> 0x0072 }
            r0 = r28
            long r8 = r0.b     // Catch:{ Throwable -> 0x0072 }
            r0 = r28
            long r10 = r0.c     // Catch:{ Throwable -> 0x0072 }
            r0 = r28
            r1 = r30
            r2 = r29
            long r12 = r0.a(r1, r2)     // Catch:{ Throwable -> 0x0072 }
            long r14 = r28.b(r29)     // Catch:{ Throwable -> 0x0072 }
            long r16 = r28.a(r29)     // Catch:{ Throwable -> 0x0072 }
            r18 = 0
            r5 = r28
            r4.<init>(r5, r6, r8, r10, r12, r14, r16, r18)     // Catch:{ Throwable -> 0x0072 }
        L_0x0031:
            return r4
        L_0x0032:
            com.alipay.mobile.framework.LauncherApplicationAgent r5 = com.alipay.mobile.framework.LauncherApplicationAgent.getInstance()     // Catch:{ Throwable -> 0x0072 }
            android.app.Application r21 = r5.getApplicationContext()     // Catch:{ Throwable -> 0x0072 }
            if (r21 != 0) goto L_0x0049
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0072 }
            java.lang.String r6 = "Permissions"
            java.lang.String r7 = "obtain data but context is null!"
            r5.warn(r6, r7)     // Catch:{ Throwable -> 0x0072 }
            r4 = 0
            goto L_0x0031
        L_0x0049:
            r22 = 0
            android.content.ContentResolver r4 = r21.getContentResolver()     // Catch:{ all -> 0x0065 }
            android.net.Uri r5 = com.alipay.android.phone.mobilesdk.permission.guide.provider.DataProvider.b(r21)     // Catch:{ all -> 0x0065 }
            r6 = 0
            r7 = 0
            r8 = 0
            r9 = 0
            android.database.Cursor r22 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0065 }
            if (r22 != 0) goto L_0x0082
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0065 }
            java.lang.String r6 = "query fatigue via content provider but cursor is null!"
            r5.<init>(r6)     // Catch:{ all -> 0x0065 }
            throw r5     // Catch:{ all -> 0x0065 }
        L_0x0065:
            r5 = move-exception
            if (r22 == 0) goto L_0x0071
            boolean r6 = r22.isClosed()     // Catch:{ Throwable -> 0x0072 }
            if (r6 != 0) goto L_0x0071
            r22.close()     // Catch:{ Throwable -> 0x0072 }
        L_0x0071:
            throw r5     // Catch:{ Throwable -> 0x0072 }
        L_0x0072:
            r23 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r6 = "Permissions"
            java.lang.String r7 = "obtain data error!"
            r0 = r23
            r5.error(r6, r7, r0)
            r4 = 0
            goto L_0x0031
        L_0x0082:
            r22.moveToFirst()     // Catch:{ all -> 0x0065 }
            r5 = 0
            r0 = r22
            long r24 = r0.getLong(r5)     // Catch:{ all -> 0x0065 }
            r5 = 1
            r0 = r22
            long r10 = r0.getLong(r5)     // Catch:{ all -> 0x0065 }
            r5 = 2
            r0 = r22
            long r12 = r0.getLong(r5)     // Catch:{ all -> 0x0065 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0065 }
            java.lang.String r6 = "Permissions"
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ all -> 0x0065 }
            java.lang.String r8 = "query fatigue data successful, spsb: %s, spdb: %s, dpdb: %s"
            r9 = 3
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x0065 }
            r20 = 0
            r0 = r28
            long r0 = r0.a     // Catch:{ all -> 0x0065 }
            r26 = r0
            java.lang.Long r26 = java.lang.Long.valueOf(r26)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 1
            r0 = r28
            long r0 = r0.b     // Catch:{ all -> 0x0065 }
            r26 = r0
            java.lang.Long r26 = java.lang.Long.valueOf(r26)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 2
            r0 = r28
            long r0 = r0.c     // Catch:{ all -> 0x0065 }
            r26 = r0
            java.lang.Long r26 = java.lang.Long.valueOf(r26)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            java.lang.String r7 = java.lang.String.format(r7, r8, r9)     // Catch:{ all -> 0x0065 }
            r5.info(r6, r7)     // Catch:{ all -> 0x0065 }
            r22.close()     // Catch:{ all -> 0x0065 }
            android.net.Uri r5 = com.alipay.android.phone.mobilesdk.permission.guide.provider.DataProvider.c(r21)     // Catch:{ all -> 0x0065 }
            r6 = 0
            r7 = 0
            r8 = 2
            java.lang.String[] r8 = new java.lang.String[r8]     // Catch:{ all -> 0x0065 }
            r9 = 0
            r8[r9] = r29     // Catch:{ all -> 0x0065 }
            r9 = 1
            r8[r9] = r30     // Catch:{ all -> 0x0065 }
            r9 = 0
            android.database.Cursor r22 = r4.query(r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0065 }
            if (r22 != 0) goto L_0x00f9
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException     // Catch:{ all -> 0x0065 }
            java.lang.String r6 = "query permission record via content provider but cursor is null!"
            r5.<init>(r6)     // Catch:{ all -> 0x0065 }
            throw r5     // Catch:{ all -> 0x0065 }
        L_0x00f9:
            r22.moveToFirst()     // Catch:{ all -> 0x0065 }
            r5 = 0
            r0 = r22
            long r14 = r0.getLong(r5)     // Catch:{ all -> 0x0065 }
            r5 = 1
            r0 = r22
            long r16 = r0.getLong(r5)     // Catch:{ all -> 0x0065 }
            r5 = 2
            r0 = r22
            long r18 = r0.getLong(r5)     // Catch:{ all -> 0x0065 }
            com.alipay.mobile.common.logging.api.trace.TraceLogger r5 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0065 }
            java.lang.String r6 = "Permissions"
            java.util.Locale r7 = java.util.Locale.US     // Catch:{ all -> 0x0065 }
            java.lang.String r8 = "obtainPermissionGuideResult, permissionName: %s, bizType: %s, fSPSB: %s, fSPDB: %s, fDPDB: %s, lgSPSB: %s, lgSPDB: %s, lgDPDB: %s"
            r9 = 8
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x0065 }
            r20 = 0
            r9[r20] = r29     // Catch:{ all -> 0x0065 }
            r20 = 1
            r9[r20] = r30     // Catch:{ all -> 0x0065 }
            r20 = 2
            java.lang.Long r26 = java.lang.Long.valueOf(r24)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 3
            java.lang.Long r26 = java.lang.Long.valueOf(r10)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 4
            java.lang.Long r26 = java.lang.Long.valueOf(r12)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 5
            java.lang.Long r26 = java.lang.Long.valueOf(r14)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 6
            java.lang.Long r26 = java.lang.Long.valueOf(r16)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            r20 = 7
            java.lang.Long r26 = java.lang.Long.valueOf(r18)     // Catch:{ all -> 0x0065 }
            r9[r20] = r26     // Catch:{ all -> 0x0065 }
            java.lang.String r7 = java.lang.String.format(r7, r8, r9)     // Catch:{ all -> 0x0065 }
            r5.info(r6, r7)     // Catch:{ all -> 0x0065 }
            com.alipay.android.phone.mobilesdk.permission.guide.a.c$a r6 = new com.alipay.android.phone.mobilesdk.permission.guide.a.c$a     // Catch:{ all -> 0x0065 }
            r20 = 0
            r7 = r28
            r8 = r24
            r6.<init>(r7, r8, r10, r12, r14, r16, r18, r20)     // Catch:{ all -> 0x0065 }
            if (r22 == 0) goto L_0x0174
            boolean r5 = r22.isClosed()     // Catch:{ Throwable -> 0x0072 }
            if (r5 != 0) goto L_0x0174
            r22.close()     // Catch:{ Throwable -> 0x0072 }
        L_0x0174:
            r4 = r6
            goto L_0x0031
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.android.phone.mobilesdk.permission.guide.a.c.b(java.lang.String, java.lang.String):com.alipay.android.phone.mobilesdk.permission.guide.a.c$a");
    }

    private static void a(Runnable runnable, String name, TimeUnit unit) {
        try {
            ((TaskScheduleService) LauncherApplicationAgent.getInstance().getMicroApplicationContext().findServiceByInterface(TaskScheduleService.class.getName())).schedule(runnable, name, 3, unit);
        } catch (Throwable tr) {
            LoggerFactory.getTraceLogger().error("Permissions", "schedule", tr);
        }
    }

    private static void b(Context context) {
        String curOsVersion = VERSION.RELEASE;
        String curRomVersion = LoggerFactory.getDeviceProperty().getRomVersion();
        String lastOsVersion = a.e(context);
        String lastRomVersion = a.f(context);
        String lastProductVersion = a.d(context);
        if (curOsVersion == null || curRomVersion == null) {
            LoggerFactory.getTraceLogger().error((String) "Permissions", (String) "fail to get system value or product version.");
        } else if (!lastOsVersion.equals(curOsVersion)) {
            if (!TextUtils.isEmpty(lastOsVersion)) {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", "found os version changed, delete permission guide data! curOsVersion: " + curOsVersion + ", lastOsVersion: " + lastOsVersion);
                e();
            }
            a.f(context, curOsVersion);
            a.g(context, curRomVersion);
            a.e(context, "10.1.38");
        } else if (!lastRomVersion.equals(curRomVersion)) {
            if (!TextUtils.isEmpty(lastRomVersion)) {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", "found rom version changed, delete permission guide data! curRomVersion: " + curRomVersion + ", lastRomVersion: " + lastRomVersion);
                e();
            }
            a.f(context, curOsVersion);
            a.g(context, curRomVersion);
            a.e(context, "10.1.38");
        } else if (!lastProductVersion.equals("10.1.38")) {
            if (!TextUtils.isEmpty(lastProductVersion)) {
                LoggerFactory.getTraceLogger().warn((String) "Permissions", "found app product version changed, delete permission guide data! curProductVersion: 10.1.38, lastProductVersion: " + lastProductVersion);
                e();
            }
            a.f(context, curOsVersion);
            a.g(context, curRomVersion);
            a.e(context, "10.1.38");
        }
    }
}
