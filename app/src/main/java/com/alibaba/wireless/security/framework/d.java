package com.alibaba.wireless.security.framework;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge;
import com.alibaba.wireless.security.framework.utils.b;
import com.alibaba.wireless.security.framework.utils.c;
import com.alibaba.wireless.security.framework.utils.f;
import com.alibaba.wireless.security.open.SecException;
import com.autonavi.minimap.offline.model.FilePathHelper;
import com.autonavi.sdk.log.util.LogConstant;
import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class d implements ISGPluginManager {
    private static String[] n = {"armeabi", "armeabi-v7a", "x86", "arm64-v8a", "x86_64"};
    private static String o = null;
    private static volatile boolean p = true;
    HashMap<Class, Object> a = new HashMap<>();
    c b = null;
    /* access modifiers changed from: private */
    public Context c;
    private final HashMap<String, c> d = new HashMap<>();
    private IRouterComponent e = null;
    private boolean f = true;
    private String g = null;
    private String h = null;
    private b i = null;
    private boolean j = false;
    private File k = null;
    private File l = null;
    private File m = null;

    static class a {
        File a;
        File b;
        File c;
        boolean d;

        public a(File file, File file2, File file3, boolean z) {
            this.a = file;
            this.b = file2;
            this.c = file3;
            this.d = z;
        }
    }

    private int a(String str, String str2) {
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int length = split.length < split2.length ? split.length : split2.length;
        for (int i2 = 0; i2 < length; i2++) {
            int parseInt = Integer.parseInt(split[i2]);
            int parseInt2 = Integer.parseInt(split2[i2]);
            if (parseInt > parseInt2) {
                return 1;
            }
            if (parseInt < parseInt2) {
                return -1;
            }
        }
        return 0;
    }

    private PackageInfo a(String str, a aVar, String str2) throws SecException {
        PackageInfo packageInfo;
        try {
            packageInfo = this.c.getPackageManager().getPackageArchiveInfo(aVar.a.getAbsolutePath(), 133);
        } catch (Throwable th) {
            String valueOf = String.valueOf(th);
            StringBuilder sb = new StringBuilder();
            sb.append(aVar.a.getAbsolutePath());
            a(100043, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "getPackageArchiveInfo failed", valueOf, c(sb.toString()), aVar.c != null ? c(aVar.c.getAbsolutePath()) : "", str2);
            if (aVar.a.exists() && !b(aVar.a)) {
                aVar.a.delete();
            }
            packageInfo = null;
        }
        if (packageInfo != null) {
            return packageInfo;
        }
        StringBuilder sb2 = new StringBuilder();
        sb2.append(str);
        sb2.append("[");
        sb2.append(str2);
        sb2.append("]");
        a(100043, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "packageInfo == null", sb2.toString(), c(aVar.a.getAbsolutePath()), aVar.c != null ? c(aVar.c.getAbsolutePath()) : "", c());
        throw new SecException(SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR);
    }

    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x051c A[Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x05b2 A[Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00ab A[SYNTHETIC, Splitter:B:37:0x00ab] */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ed A[Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0103 A[ADDED_TO_REGION, Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x01c9 A[Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x01cd A[Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01ea A[Catch:{ SecException -> 0x0610, all -> 0x05a6, all -> 0x0674 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0253 A[SYNTHETIC, Splitter:B:68:0x0253] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alibaba.wireless.security.framework.c a(java.lang.String r38, com.alibaba.wireless.security.framework.d.a r39, android.content.Context r40, java.lang.String r41) throws com.alibaba.wireless.security.open.SecException {
        /*
            r37 = this;
            r9 = r37
            r10 = r38
            r11 = r39
            r12 = r41
            java.io.File r1 = r11.a
            java.lang.String r13 = r1.getAbsolutePath()
            java.io.File r1 = r11.b
            java.lang.String r14 = r1.getAbsolutePath()
            java.lang.Boolean r1 = java.lang.Boolean.FALSE
            java.lang.Boolean r2 = java.lang.Boolean.FALSE
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            r3.append(r14)
            java.lang.String r4 = java.io.File.separator
            r3.append(r4)
            r3.append(r10)
            java.lang.String r4 = "_"
            r3.append(r4)
            java.io.File r4 = r11.a
            long r4 = r4.lastModified()
            r6 = 1000(0x3e8, double:4.94E-321)
            long r4 = r4 / r6
            r3.append(r4)
            java.lang.String r4 = ".pkgInfo"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            com.alibaba.wireless.security.framework.a r15 = new com.alibaba.wireless.security.framework.a
            r15.<init>(r3)
            r16 = 0
            boolean r3 = r15.a()     // Catch:{ all -> 0x0674 }
            r18 = 1
            r19 = 0
            r8 = 0
            if (r3 == 0) goto L_0x00a3
            android.content.Context r3 = r9.c     // Catch:{ all -> 0x0674 }
            boolean r3 = com.alibaba.wireless.security.framework.utils.f.a(r3)     // Catch:{ all -> 0x0674 }
            if (r3 != 0) goto L_0x00a3
            org.json.JSONObject r3 = r15.b()     // Catch:{ Exception | JSONException -> 0x009d }
            java.lang.String r4 = "version"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ Exception | JSONException -> 0x009d }
            java.lang.String r5 = "dependencies"
            java.lang.String r5 = r3.getString(r5)     // Catch:{ Exception | JSONException -> 0x0099 }
            java.lang.String r6 = "hasso"
            java.lang.String r6 = r3.getString(r6)     // Catch:{ Exception | JSONException -> 0x0096 }
            boolean r6 = java.lang.Boolean.parseBoolean(r6)     // Catch:{ Exception | JSONException -> 0x0096 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r6)     // Catch:{ Exception | JSONException -> 0x0096 }
            java.lang.String r1 = "pluginclass"
            java.lang.String r1 = r3.getString(r1)     // Catch:{ JSONException -> 0x0095, Exception -> 0x0093 }
            java.lang.String r7 = "thirdpartyso"
            boolean r3 = r3.getBoolean(r7)     // Catch:{ Exception | JSONException -> 0x0090 }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ Exception | JSONException -> 0x0090 }
            r2 = r3
            r3 = r1
            r1 = 1
            goto L_0x00a9
        L_0x0090:
            r3 = r1
            r1 = r6
            goto L_0x00a1
        L_0x0093:
            r1 = r6
            goto L_0x0096
        L_0x0095:
            r1 = r6
        L_0x0096:
            r3 = r19
            goto L_0x00a1
        L_0x0099:
            r3 = r19
            r5 = r3
            goto L_0x00a1
        L_0x009d:
            r3 = r19
            r4 = r3
            r5 = r4
        L_0x00a1:
            r6 = r1
            goto L_0x00a8
        L_0x00a3:
            r6 = r1
            r3 = r19
            r4 = r3
            r5 = r4
        L_0x00a8:
            r1 = 0
        L_0x00a9:
            if (r1 != 0) goto L_0x00ed
            android.content.pm.PackageInfo r1 = r9.a(r10, r11, r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r4 = r1.versionName     // Catch:{ all -> 0x0674 }
            android.content.pm.ApplicationInfo r2 = r1.applicationInfo     // Catch:{ all -> 0x0674 }
            android.os.Bundle r2 = r2.metaData     // Catch:{ all -> 0x0674 }
            java.lang.String r3 = "dependencies"
            java.lang.String r2 = r2.getString(r3)     // Catch:{ all -> 0x0674 }
            android.content.pm.ApplicationInfo r3 = r1.applicationInfo     // Catch:{ all -> 0x0674 }
            android.os.Bundle r3 = r3.metaData     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "hasso"
            boolean r3 = r3.getBoolean(r5, r8)     // Catch:{ all -> 0x0674 }
            java.lang.Boolean r6 = java.lang.Boolean.valueOf(r3)     // Catch:{ all -> 0x0674 }
            android.content.pm.ApplicationInfo r3 = r1.applicationInfo     // Catch:{ all -> 0x0674 }
            android.os.Bundle r3 = r3.metaData     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "pluginclass"
            java.lang.String r3 = r3.getString(r5)     // Catch:{ all -> 0x0674 }
            android.content.pm.ApplicationInfo r5 = r1.applicationInfo     // Catch:{ all -> 0x0674 }
            android.os.Bundle r5 = r5.metaData     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = "thirdpartyso"
            boolean r5 = r5.getBoolean(r7, r8)     // Catch:{ all -> 0x0674 }
            java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)     // Catch:{ all -> 0x0674 }
            r15.a = r1     // Catch:{ all -> 0x0674 }
            r15.a(r1, r10)     // Catch:{ all -> 0x0674 }
            r7 = r4
            r4 = r3
            r3 = r5
            r5 = r6
            r6 = r2
            goto L_0x00f5
        L_0x00ed:
            r7 = r4
            r4 = r3
            r3 = r2
            r36 = r6
            r6 = r5
            r5 = r36
        L_0x00f5:
            android.content.Context r1 = r9.c     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.getPackageName()     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "com.eg.android.AlipayGphone"
            boolean r1 = r2.equals(r1)     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x019b
            if (r7 == 0) goto L_0x019b
            java.lang.String r1 = "6.4.3448869"
            boolean r1 = r7.contains(r1)     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x019b
            r20 = 2
            java.lang.String r21 = android.os.Build.FINGERPRINT     // Catch:{ all -> 0x0674 }
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x011c
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            goto L_0x011e
        L_0x011c:
            java.lang.String r1 = ""
        L_0x011e:
            r22 = r1
            java.lang.Class<com.alibaba.wireless.security.framework.d> r1 = com.alibaba.wireless.security.framework.d.class
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ all -> 0x0674 }
            java.lang.String r23 = r1.toString()     // Catch:{ all -> 0x0674 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r13)     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = "("
            r1.append(r8)     // Catch:{ all -> 0x0674 }
            java.io.File r8 = r11.a     // Catch:{ all -> 0x0674 }
            r25 = r3
            long r2 = r8.getTotalSpace()     // Catch:{ all -> 0x0674 }
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "),"
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r14)     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r1.toString()     // Catch:{ all -> 0x0674 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            android.content.Context r2 = r9.c     // Catch:{ all -> 0x0674 }
            android.content.pm.ApplicationInfo r2 = r2.getApplicationInfo()     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = r2.sourceDir     // Catch:{ all -> 0x0674 }
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = ","
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.io.File r2 = new java.io.File     // Catch:{ all -> 0x0674 }
            android.content.Context r3 = r9.c     // Catch:{ all -> 0x0674 }
            android.content.pm.ApplicationInfo r3 = r3.getApplicationInfo()     // Catch:{ all -> 0x0674 }
            java.lang.String r3 = r3.sourceDir     // Catch:{ all -> 0x0674 }
            r2.<init>(r3)     // Catch:{ all -> 0x0674 }
            long r2 = r2.lastModified()     // Catch:{ all -> 0x0674 }
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.lang.String r26 = r1.toString()     // Catch:{ all -> 0x0674 }
            r1 = r9
            r2 = 100088(0x186f8, float:1.40253E-40)
            r27 = r25
            r3 = r20
            r28 = r4
            r4 = r21
            r29 = r5
            r5 = r22
            r30 = r6
            r6 = r23
            r31 = r7
            r7 = r8
            r32 = r15
            r15 = 0
            r8 = r26
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            goto L_0x01a8
        L_0x019b:
            r27 = r3
            r28 = r4
            r29 = r5
            r30 = r6
            r31 = r7
            r32 = r15
            r15 = 0
        L_0x01a8:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r10)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "("
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            r8 = r31
            r1.append(r8)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = ")"
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r1.toString()     // Catch:{ all -> 0x0674 }
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x01cd
            r1 = r7
        L_0x01ca:
            r6 = r30
            goto L_0x01e2
        L_0x01cd:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "->"
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            goto L_0x01ca
        L_0x01e2:
            boolean r1 = r9.a(r6, r1, r15)     // Catch:{ all -> 0x0674 }
            r5 = 199(0xc7, float:2.79E-43)
            if (r1 != 0) goto L_0x0253
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 199(0xc7, float:2.79E-43)
            java.lang.String r4 = "loadRequirements failed"
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x01f8
            goto L_0x020d
        L_0x01f8:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = "->"
            r1.append(r8)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r7 = r1
        L_0x020d:
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x0227
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = "src:"
            r1.<init>(r8)     // Catch:{ all -> 0x0674 }
            java.io.File r8 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r8.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r9.c(r8)     // Catch:{ all -> 0x0674 }
            r1.append(r8)     // Catch:{ all -> 0x0674 }
            goto L_0x023c
        L_0x0227:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = "zipfile:"
            r1.<init>(r8)     // Catch:{ all -> 0x0674 }
            java.io.File r8 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r8.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r9.c(r8)     // Catch:{ all -> 0x0674 }
            r1.append(r8)     // Catch:{ all -> 0x0674 }
        L_0x023c:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r8 = r1
            java.lang.String r10 = ""
            r1 = r9
            r11 = 199(0xc7, float:2.79E-43)
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r10
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            com.alibaba.wireless.security.open.SecException r1 = new com.alibaba.wireless.security.open.SecException     // Catch:{ all -> 0x0674 }
            r1.<init>(r11)     // Catch:{ all -> 0x0674 }
            throw r1     // Catch:{ all -> 0x0674 }
        L_0x0253:
            r9.b(r10, r8)     // Catch:{ SecException -> 0x0610 }
            java.lang.String r1 = ""
            java.lang.String r2 = ""
            r4 = r29
            boolean r3 = r4.booleanValue()     // Catch:{ all -> 0x0674 }
            if (r3 == 0) goto L_0x0320
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "libsg"
            r1.<init>(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r10)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "so-"
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r8)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = ".so"
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.lang.String r20 = r1.toString()     // Catch:{ all -> 0x0674 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "sg"
            r1.<init>(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r10)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "so-"
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r8)     // Catch:{ all -> 0x0674 }
            java.lang.String r21 = r1.toString()     // Catch:{ all -> 0x0674 }
            java.io.File r3 = r11.c     // Catch:{ all -> 0x0674 }
            boolean r2 = r11.d     // Catch:{ all -> 0x0674 }
            r1 = r9
            r22 = r2
            r2 = r13
            r23 = r3
            r3 = r14
            r33 = r4
            r4 = r23
            r15 = 199(0xc7, float:2.79E-43)
            r5 = r20
            r23 = r6
            r6 = r22
            boolean r1 = r1.a(r2, r3, r4, r5, r6)     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x031b
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 107(0x6b, float:1.5E-43)
            java.lang.String r4 = ""
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x02c2
            r6 = r7
            goto L_0x02d7
        L_0x02c2:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "->"
            r1.append(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r6 = r1
        L_0x02d7:
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x02f1
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "src:"
            r1.<init>(r5)     // Catch:{ all -> 0x0674 }
            java.io.File r5 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r9.c(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r5)     // Catch:{ all -> 0x0674 }
            goto L_0x0306
        L_0x02f1:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "zipfile:"
            r1.<init>(r5)     // Catch:{ all -> 0x0674 }
            java.io.File r5 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r9.c(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r5)     // Catch:{ all -> 0x0674 }
        L_0x0306:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r7 = r1
            java.lang.String r8 = ""
            r1 = r9
            r5 = r23
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            com.alibaba.wireless.security.open.SecException r1 = new com.alibaba.wireless.security.open.SecException     // Catch:{ all -> 0x0674 }
            r2 = 107(0x6b, float:1.5E-43)
            r1.<init>(r2)     // Catch:{ all -> 0x0674 }
            throw r1     // Catch:{ all -> 0x0674 }
        L_0x031b:
            r5 = r20
            r6 = r21
            goto L_0x0328
        L_0x0320:
            r33 = r4
            r23 = r6
            r15 = 199(0xc7, float:2.79E-43)
            r5 = r1
            r6 = r2
        L_0x0328:
            r3 = r28
            if (r3 != 0) goto L_0x0392
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 199(0xc7, float:2.79E-43)
            java.lang.String r4 = "miss pluginclass"
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x033b
            r6 = r7
            goto L_0x0350
        L_0x033b:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "->"
            r1.append(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r6 = r1
        L_0x0350:
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x036a
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "src:"
            r1.<init>(r5)     // Catch:{ all -> 0x0674 }
            java.io.File r5 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r9.c(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r5)     // Catch:{ all -> 0x0674 }
            goto L_0x037f
        L_0x036a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "zipfile:"
            r1.<init>(r5)     // Catch:{ all -> 0x0674 }
            java.io.File r5 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r9.c(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r5)     // Catch:{ all -> 0x0674 }
        L_0x037f:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r7 = r1
            java.lang.String r8 = ""
            r1 = r9
            r5 = r23
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            com.alibaba.wireless.security.open.SecException r1 = new com.alibaba.wireless.security.open.SecException     // Catch:{ all -> 0x0674 }
            r1.<init>(r15)     // Catch:{ all -> 0x0674 }
            throw r1     // Catch:{ all -> 0x0674 }
        L_0x0392:
            java.lang.String r4 = r3.trim()     // Catch:{ all -> 0x0674 }
            boolean r1 = r11.d     // Catch:{ all -> 0x0674 }
            java.lang.ClassLoader r3 = r9.b(r13, r14, r1)     // Catch:{ all -> 0x0674 }
            java.lang.Class r1 = r9.a(r3, r4)     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x041e
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = "load "
            r1.<init>(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r4)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = " failed from plugin "
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            com.alibaba.wireless.security.framework.utils.a.b(r1)     // Catch:{ all -> 0x0674 }
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 199(0xc7, float:2.79E-43)
            java.lang.String r5 = "clazz == null"
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x03c7
            r6 = r7
            goto L_0x03dc
        L_0x03c7:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r6 = "->"
            r1.append(r6)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r6 = r1
        L_0x03dc:
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x03f6
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = "src:"
            r1.<init>(r7)     // Catch:{ all -> 0x0674 }
            java.io.File r7 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r9.c(r7)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            goto L_0x040b
        L_0x03f6:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = "zipfile:"
            r1.<init>(r7)     // Catch:{ all -> 0x0674 }
            java.io.File r7 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r9.c(r7)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
        L_0x040b:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r7 = r1
            r1 = r9
            r8 = r4
            r4 = r5
            r5 = r23
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            com.alibaba.wireless.security.open.SecException r1 = new com.alibaba.wireless.security.open.SecException     // Catch:{ all -> 0x0674 }
            r1.<init>(r15)     // Catch:{ all -> 0x0674 }
            throw r1     // Catch:{ all -> 0x0674 }
        L_0x041e:
            java.lang.Object r1 = r1.newInstance()     // Catch:{ InstantiationException -> 0x0606, IllegalAccessException -> 0x05fc, SecException -> 0x04f6 }
            r15 = r1
            com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin r15 = (com.alibaba.wireless.security.open.initialize.ISecurityGuardPlugin) r15     // Catch:{ InstantiationException -> 0x0606, IllegalAccessException -> 0x05fc, SecException -> 0x04f6 }
            com.alibaba.wireless.security.framework.c r25 = new com.alibaba.wireless.security.framework.c     // Catch:{ InstantiationException -> 0x0606, IllegalAccessException -> 0x05fc, SecException -> 0x04f6 }
            r1 = r25
            r2 = r13
            r13 = r3
            r3 = r9
            r4 = r10
            r11 = r5
            r5 = r13
            r34 = r6
            r6 = r32
            r35 = r7
            r7 = r15
            r1.<init>(r2, r3, r4, r5, r6, r7)     // Catch:{ InstantiationException -> 0x0606, IllegalAccessException -> 0x05fc, SecException -> 0x04f4 }
            java.lang.String r1 = r37.getMainPluginName()     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            boolean r1 = r10.equalsIgnoreCase(r1)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r2 = 3
            r3 = 2
            if (r1 == 0) goto L_0x04ae
            boolean r1 = r9.f     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            boolean r4 = r9.j     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r4 == 0) goto L_0x044d
            r1 = r1 | 2
        L_0x044d:
            java.lang.String r4 = r9.g     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r4 == 0) goto L_0x045b
            java.lang.String r4 = r9.g     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            int r4 = r4.length()     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r4 <= 0) goto L_0x045b
            r1 = r1 | 4
        L_0x045b:
            android.content.Context r4 = r9.c     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            boolean r4 = com.alibaba.wireless.security.framework.utils.f.a(r4)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r4 == 0) goto L_0x0465
            r1 = r1 | 8
        L_0x0465:
            android.content.Context r4 = r9.c     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            boolean r4 = com.alibaba.wireless.security.framework.utils.f.b(r4)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r4 == 0) goto L_0x046f
            r1 = r1 | 16
        L_0x046f:
            com.alibaba.wireless.security.framework.b r4 = r9.i     // Catch:{ Exception -> 0x0481 }
            if (r4 == 0) goto L_0x047e
            com.alibaba.wireless.security.framework.b r4 = r9.i     // Catch:{ Exception -> 0x0481 }
            org.json.JSONObject r4 = r4.a()     // Catch:{ Exception -> 0x0481 }
            java.lang.String r4 = r4.toString()     // Catch:{ Exception -> 0x0481 }
            goto L_0x0483
        L_0x047e:
            java.lang.String r4 = ""
            goto L_0x0483
        L_0x0481:
            java.lang.String r4 = ""
        L_0x0483:
            r21 = 0
            r5 = 4
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r6 = 0
            r5[r6] = r1     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r5[r18] = r4     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            java.io.File r1 = r9.l     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r5[r3] = r1     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            java.lang.String r1 = r9.h     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r5[r2] = r1     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r19 = r15
            r20 = r40
            r22 = r25
            r23 = r34
            r24 = r5
            com.alibaba.wireless.security.framework.IRouterComponent r1 = r19.onPluginLoaded(r20, r21, r22, r23, r24)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r9.e = r1     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            goto L_0x04c4
        L_0x04ae:
            com.alibaba.wireless.security.framework.SGPluginExtras.slot = r16     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            com.alibaba.wireless.security.framework.IRouterComponent r1 = r9.e     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r4 = 0
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r19 = r15
            r20 = r40
            r21 = r1
            r22 = r25
            r23 = r34
            r24 = r5
            r19.onPluginLoaded(r20, r21, r22, r23, r24)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
        L_0x04c4:
            r6 = r33
            boolean r1 = r6.booleanValue()     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r1 == 0) goto L_0x060d
            r5 = r27
            boolean r1 = r5.booleanValue()     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            if (r1 != 0) goto L_0x060d
            r1 = r34
            java.lang.String r1 = com.alibaba.wireless.security.framework.utils.f.a(r13, r1)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            com.alibaba.wireless.security.framework.IRouterComponent r4 = r9.e     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r5 = 10102(0x2776, float:1.4156E-41)
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r6 = 0
            r2[r6] = r10     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r2[r18] = r8     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r2[r3] = r1     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            r4.doCommand(r5, r2)     // Catch:{ InstantiationException -> 0x04f0, IllegalAccessException -> 0x04ec, SecException -> 0x04f4 }
            goto L_0x060d
        L_0x04ec:
            r0 = move-exception
            r1 = r0
            goto L_0x0600
        L_0x04f0:
            r0 = move-exception
            r1 = r0
            goto L_0x060a
        L_0x04f4:
            r0 = move-exception
            goto L_0x04fa
        L_0x04f6:
            r0 = move-exception
            r11 = r5
            r35 = r7
        L_0x04fa:
            r10 = r0
            java.io.File r13 = new java.io.File     // Catch:{ all -> 0x0674 }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r14)     // Catch:{ all -> 0x0674 }
            java.lang.String r2 = java.io.File.separator     // Catch:{ all -> 0x0674 }
            r1.append(r2)     // Catch:{ all -> 0x0674 }
            r1.append(r11)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r13.<init>(r1)     // Catch:{ all -> 0x0674 }
            int r1 = r10.getErrorCode()     // Catch:{ all -> 0x0674 }
            r2 = 103(0x67, float:1.44E-43)
            if (r1 != r2) goto L_0x05b2
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 103(0x67, float:1.44E-43)
            java.lang.String r4 = ""
            java.lang.String r5 = r10.toString()     // Catch:{ all -> 0x0674 }
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x0532
            r6 = r35
        L_0x052f:
            r11 = r39
            goto L_0x054a
        L_0x0532:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r6 = "->"
            r1.append(r6)     // Catch:{ all -> 0x0674 }
            r6 = r35
            r1.append(r6)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r6 = r1
            goto L_0x052f
        L_0x054a:
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x0564
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = "src:"
            r1.<init>(r7)     // Catch:{ all -> 0x0674 }
            java.io.File r7 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r9.c(r7)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
            goto L_0x0579
        L_0x0564:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = "zipfile:"
            r1.<init>(r7)     // Catch:{ all -> 0x0674 }
            java.io.File r7 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r7.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r9.c(r7)     // Catch:{ all -> 0x0674 }
            r1.append(r7)     // Catch:{ all -> 0x0674 }
        L_0x0579:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r7 = r1
            java.lang.String r1 = r13.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r9.c(r1)     // Catch:{ all -> 0x0674 }
            r1 = r9
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            boolean r1 = r11.d     // Catch:{ all -> 0x05a6 }
            if (r1 != 0) goto L_0x0593
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x05a6 }
            r1.a()     // Catch:{ all -> 0x05a6 }
        L_0x0593:
            boolean r1 = r13.exists()     // Catch:{ all -> 0x05a6 }
            if (r1 == 0) goto L_0x059c
            r13.delete()     // Catch:{ all -> 0x05a6 }
        L_0x059c:
            boolean r1 = r11.d     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x05fb
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x0674 }
            r1.b()     // Catch:{ all -> 0x0674 }
            goto L_0x05fb
        L_0x05a6:
            r0 = move-exception
            r1 = r0
            boolean r2 = r11.d     // Catch:{ all -> 0x0674 }
            if (r2 != 0) goto L_0x05b1
            com.alibaba.wireless.security.framework.utils.c r2 = r9.b     // Catch:{ all -> 0x0674 }
            r2.b()     // Catch:{ all -> 0x0674 }
        L_0x05b1:
            throw r1     // Catch:{ all -> 0x0674 }
        L_0x05b2:
            r1 = r11
            r11 = r39
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 199(0xc7, float:2.79E-43)
            java.lang.String r4 = "native exception occurred"
            java.lang.String r5 = r10.toString()     // Catch:{ all -> 0x0674 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = "soName="
            r6.<init>(r7)     // Catch:{ all -> 0x0674 }
            r6.append(r1)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = ", authCode="
            r6.append(r1)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r9.h     // Catch:{ all -> 0x0674 }
            r6.append(r1)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = ", errorCode="
            r6.append(r1)     // Catch:{ all -> 0x0674 }
            int r1 = r10.getErrorCode()     // Catch:{ all -> 0x0674 }
            r6.append(r1)     // Catch:{ all -> 0x0674 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0674 }
            java.io.File r1 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r7 = r9.c(r1)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r13.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r8 = r9.c(r1)     // Catch:{ all -> 0x0674 }
            r1 = r9
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
        L_0x05fb:
            throw r10     // Catch:{ all -> 0x0674 }
        L_0x05fc:
            r0 = move-exception
            r1 = r0
            r25 = r19
        L_0x0600:
            java.lang.String r2 = ""
        L_0x0602:
            com.alibaba.wireless.security.framework.utils.a.a(r2, r1)     // Catch:{ all -> 0x0674 }
            goto L_0x060d
        L_0x0606:
            r0 = move-exception
            r1 = r0
            r25 = r19
        L_0x060a:
            java.lang.String r2 = ""
            goto L_0x0602
        L_0x060d:
            com.alibaba.wireless.security.framework.SGPluginExtras.slot = r16
            return r25
        L_0x0610:
            r0 = move-exception
            r23 = r6
            r6 = r7
            r2 = 100043(0x186cb, float:1.4019E-40)
            r3 = 199(0xc7, float:2.79E-43)
            java.lang.String r4 = "isMeetReverseDependencies failed"
            int r1 = r41.length()     // Catch:{ all -> 0x0674 }
            if (r1 != 0) goto L_0x0622
            goto L_0x0637
        L_0x0622:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            r1.<init>()     // Catch:{ all -> 0x0674 }
            r1.append(r12)     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "->"
            r1.append(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r6)     // Catch:{ all -> 0x0674 }
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r6 = r1
        L_0x0637:
            java.io.File r1 = r11.c     // Catch:{ all -> 0x0674 }
            if (r1 == 0) goto L_0x0651
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "src:"
            r1.<init>(r5)     // Catch:{ all -> 0x0674 }
            java.io.File r5 = r11.c     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r9.c(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r5)     // Catch:{ all -> 0x0674 }
            goto L_0x0666
        L_0x0651:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = "zipfile:"
            r1.<init>(r5)     // Catch:{ all -> 0x0674 }
            java.io.File r5 = r11.a     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r5.getAbsolutePath()     // Catch:{ all -> 0x0674 }
            java.lang.String r5 = r9.c(r5)     // Catch:{ all -> 0x0674 }
            r1.append(r5)     // Catch:{ all -> 0x0674 }
        L_0x0666:
            java.lang.String r1 = r1.toString()     // Catch:{ all -> 0x0674 }
            r7 = r1
            java.lang.String r8 = ""
            r1 = r9
            r5 = r23
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0674 }
            throw r0     // Catch:{ all -> 0x0674 }
        L_0x0674:
            r0 = move-exception
            r1 = r0
            com.alibaba.wireless.security.framework.SGPluginExtras.slot = r16
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.d.a(java.lang.String, com.alibaba.wireless.security.framework.d$a, android.content.Context, java.lang.String):com.alibaba.wireless.security.framework.c");
    }

    private File a(Context context) throws SecException {
        if (context == null) {
            a(100038, 116, "", "", "", "", "");
            throw new SecException(116);
        }
        String str = null;
        if (context != null) {
            try {
                String str2 = context.getApplicationInfo().sourceDir;
                if (str2 != null) {
                    File file = new File(str2);
                    if (file.exists()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(file.lastModified() / 1000);
                        str = sb.toString();
                    }
                }
            } catch (Exception e2) {
                a(100038, 115, "", String.valueOf(e2), "", "", "");
                throw new SecException((Throwable) e2, 115);
            }
        }
        if (str == null) {
            throw new SecException(115);
        }
        this.l = context.getDir("SGLib", 0);
        if (this.l == null || !this.l.exists()) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(this.l);
            a(100038, 109, "", sb2.toString(), "", "", "");
            throw new SecException(109);
        }
        File file2 = new File(this.l.getAbsolutePath(), "app_".concat(String.valueOf(str)));
        if (!file2.exists()) {
            file2.mkdirs();
            if (!file2.exists()) {
                file2.mkdirs();
            }
        }
        if (p && file2.exists()) {
            p = false;
            a(this.l, "app_".concat(String.valueOf(str)));
        }
        if (file2.exists()) {
            return file2;
        }
        a(100038, 114, "", "", "", "", "");
        throw new SecException(114);
    }

    private File a(Context context, b bVar) {
        File file = null;
        if (!(f.a(context) || bVar == null || bVar.b() == 0 || bVar.c() == null || bVar.c().length() <= 0)) {
            StringBuilder sb = new StringBuilder();
            sb.append(this.k.getAbsolutePath());
            sb.append(File.separator);
            sb.append("libs");
            sb.append(File.separator);
            sb.append(bVar.b());
            sb.append(File.separator);
            sb.append(bVar.c());
            File file2 = new File(sb.toString());
            if (!file2.exists()) {
                return null;
            }
            file = file2;
        }
        return file;
    }

    private File a(String str) {
        File file = null;
        if (this.g != null) {
            return null;
        }
        String a2 = f.a(d.class.getClassLoader(), "sg".concat(String.valueOf(str)));
        if (a2 != null && a2.length() > 0) {
            file = new File(a2);
        }
        return file;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x0086 A[SYNTHETIC, Splitter:B:31:0x0086] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x008d A[SYNTHETIC, Splitter:B:37:0x008d] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.io.File a(java.lang.String r13, java.io.File r14) {
        /*
            r12 = this;
            r0 = 0
            android.content.Context r1 = r12.c     // Catch:{ Exception -> 0x000a }
            android.content.pm.ApplicationInfo r1 = r1.getApplicationInfo()     // Catch:{ Exception -> 0x000a }
            java.lang.String r1 = r1.sourceDir     // Catch:{ Exception -> 0x000a }
            goto L_0x000b
        L_0x000a:
            r1 = r0
        L_0x000b:
            if (r1 != 0) goto L_0x000e
            return r0
        L_0x000e:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "libsg"
            r2.<init>(r3)
            r2.append(r13)
            java.lang.String r3 = ".so"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            java.lang.String r3 = "Plugin not existed in the application library path, maybe installed in x86 phone, or the armeabi-v7a existed"
            com.alibaba.wireless.security.framework.utils.a.b(r3)     // Catch:{ IOException -> 0x0068, all -> 0x0065 }
            java.util.zip.ZipFile r3 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x0068, all -> 0x0065 }
            r3.<init>(r1)     // Catch:{ IOException -> 0x0068, all -> 0x0065 }
            java.lang.String[] r4 = n     // Catch:{ IOException -> 0x0063 }
            int r5 = r4.length     // Catch:{ IOException -> 0x0063 }
            r6 = 0
        L_0x002f:
            if (r6 >= r5) goto L_0x005e
            r7 = r4[r6]     // Catch:{ IOException -> 0x0063 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x0063 }
            java.lang.String r9 = "lib"
            r8.<init>(r9)     // Catch:{ IOException -> 0x0063 }
            java.lang.String r9 = java.io.File.separator     // Catch:{ IOException -> 0x0063 }
            r8.append(r9)     // Catch:{ IOException -> 0x0063 }
            r8.append(r7)     // Catch:{ IOException -> 0x0063 }
            java.lang.String r9 = java.io.File.separator     // Catch:{ IOException -> 0x0063 }
            r8.append(r9)     // Catch:{ IOException -> 0x0063 }
            r8.append(r2)     // Catch:{ IOException -> 0x0063 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException -> 0x0063 }
            java.util.zip.ZipEntry r9 = r3.getEntry(r8)     // Catch:{ IOException -> 0x0063 }
            if (r9 == 0) goto L_0x005b
            o = r7     // Catch:{ IOException -> 0x0063 }
            java.io.File r14 = r12.a(r13, r14, r3, r8)     // Catch:{ IOException -> 0x0063 }
            goto L_0x005f
        L_0x005b:
            int r6 = r6 + 1
            goto L_0x002f
        L_0x005e:
            r14 = r0
        L_0x005f:
            r3.close()     // Catch:{ Exception -> 0x0062 }
        L_0x0062:
            return r14
        L_0x0063:
            r14 = move-exception
            goto L_0x006a
        L_0x0065:
            r13 = move-exception
            r3 = r0
            goto L_0x008b
        L_0x0068:
            r14 = move-exception
            r3 = r0
        L_0x006a:
            java.lang.String r2 = "getPluginFile throws exception"
            com.alibaba.wireless.security.framework.utils.a.a(r2, r14)     // Catch:{ all -> 0x008a }
            r5 = 100047(0x186cf, float:1.40196E-40)
            r6 = 3
            java.lang.String r7 = r14.toString()     // Catch:{ all -> 0x008a }
            java.lang.String r9 = r12.c(r1)     // Catch:{ all -> 0x008a }
            java.lang.String r10 = ""
            java.lang.String r11 = ""
            r4 = r12
            r8 = r13
            r4.a(r5, r6, r7, r8, r9, r10, r11)     // Catch:{ all -> 0x008a }
            if (r3 == 0) goto L_0x0089
            r3.close()     // Catch:{ Exception -> 0x0089 }
        L_0x0089:
            return r0
        L_0x008a:
            r13 = move-exception
        L_0x008b:
            if (r3 == 0) goto L_0x0090
            r3.close()     // Catch:{ Exception -> 0x0090 }
        L_0x0090:
            throw r13
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.d.a(java.lang.String, java.io.File):java.io.File");
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x003b A[SYNTHETIC, Splitter:B:21:0x003b] */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0043 A[SYNTHETIC, Splitter:B:29:0x0043] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.io.File a(java.lang.String r11, java.io.File r12, java.lang.String r13, java.lang.String r14) {
        /*
            r10 = this;
            r0 = 0
            if (r13 == 0) goto L_0x0047
            if (r14 != 0) goto L_0x0006
            return r0
        L_0x0006:
            java.lang.String r1 = "Plugin not existed in the application library path, maybe installed in x86 phone, or the armeabi-v7a existed"
            com.alibaba.wireless.security.framework.utils.a.b(r1)     // Catch:{ IOException -> 0x001d, all -> 0x001a }
            java.util.zip.ZipFile r1 = new java.util.zip.ZipFile     // Catch:{ IOException -> 0x001d, all -> 0x001a }
            r1.<init>(r13)     // Catch:{ IOException -> 0x001d, all -> 0x001a }
            java.io.File r12 = r10.a(r11, r12, r1, r14)     // Catch:{ IOException -> 0x0018 }
            r1.close()     // Catch:{ Exception -> 0x003f }
            return r12
        L_0x0018:
            r12 = move-exception
            goto L_0x001f
        L_0x001a:
            r11 = move-exception
            r1 = r0
            goto L_0x0041
        L_0x001d:
            r12 = move-exception
            r1 = r0
        L_0x001f:
            java.lang.String r14 = "getPluginFile throws exception"
            com.alibaba.wireless.security.framework.utils.a.a(r14, r12)     // Catch:{ all -> 0x0040 }
            r3 = 100047(0x186cf, float:1.40196E-40)
            r4 = 3
            java.lang.String r5 = r12.toString()     // Catch:{ all -> 0x0040 }
            java.lang.String r7 = r10.c(r13)     // Catch:{ all -> 0x0040 }
            java.lang.String r8 = ""
            java.lang.String r9 = ""
            r2 = r10
            r6 = r11
            r2.a(r3, r4, r5, r6, r7, r8, r9)     // Catch:{ all -> 0x0040 }
            if (r1 == 0) goto L_0x003e
            r1.close()     // Catch:{ Exception -> 0x003e }
        L_0x003e:
            r12 = r0
        L_0x003f:
            return r12
        L_0x0040:
            r11 = move-exception
        L_0x0041:
            if (r1 == 0) goto L_0x0046
            r1.close()     // Catch:{ Exception -> 0x0046 }
        L_0x0046:
            throw r11
        L_0x0047:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.d.a(java.lang.String, java.io.File, java.lang.String, java.lang.String):java.io.File");
    }

    private File a(String str, File file, ZipFile zipFile, String str2) throws IOException {
        if (!(zipFile == null || str2 == null)) {
            ZipEntry entry = zipFile.getEntry(str2);
            if (entry != null && b.a(entry.getName())) {
                StringBuilder sb = new StringBuilder("Plugin existed  in ");
                sb.append(entry.getName());
                com.alibaba.wireless.security.framework.utils.a.b(sb.toString());
                StringBuilder sb2 = new StringBuilder("libsg");
                sb2.append(str);
                sb2.append("_inner");
                sb2.append(entry.getTime());
                sb2.append(FilePathHelper.SUFFIX_DOT_ZIP);
                File file2 = new File(file, sb2.toString());
                if ((!file2.exists() || file2.length() != entry.getSize()) && !f.a(zipFile, entry, file2)) {
                    com.alibaba.wireless.security.framework.utils.a.b("Extract failed!!");
                } else {
                    com.alibaba.wireless.security.framework.utils.a.b("Extract success");
                    return file2;
                }
            }
        }
        return null;
    }

    private Class<?> a(ClassLoader classLoader, String str) {
        Class<?> cls;
        long currentTimeMillis = System.currentTimeMillis();
        try {
            cls = Class.forName(str, true, classLoader);
        } catch (Throwable th) {
            a(100042, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "Class.forName failed", String.valueOf(th), str, classLoader.toString(), "");
            cls = null;
        }
        StringBuilder sb = new StringBuilder("    loadClassFromClassLoader( ");
        sb.append(str);
        sb.append(" ) used time: ");
        sb.append(System.currentTimeMillis() - currentTimeMillis);
        sb.append(" ms");
        com.alibaba.wireless.security.framework.utils.a.b(sb.toString());
        return cls;
    }

    private String a(Class<?> cls) {
        InterfacePluginInfo interfacePluginInfo = (InterfacePluginInfo) cls.getAnnotation(InterfacePluginInfo.class);
        if (interfacePluginInfo == null) {
            return null;
        }
        return interfacePluginInfo.pluginName();
    }

    private void a() throws SecException {
        this.k = a(this.c);
        if (this.k != null) {
            Context context = this.c;
            StringBuilder sb = new StringBuilder();
            sb.append(this.k);
            sb.append(File.separator);
            sb.append("lock.lock");
            this.b = new c(context, sb.toString());
            this.i = b();
            this.m = a(this.c, this.i);
        }
    }

    private void a(int i2, int i3, String str, String str2, String str3, String str4, String str5) {
        UserTrackMethodJniBridge.addUtRecord(String.valueOf(i2), i3, 0, com.alibaba.wireless.security.open.initialize.c.a(), 0, str, str2, str3, str4, str5);
    }

    /* access modifiers changed from: private */
    public void a(File file) {
        if (file.isDirectory()) {
            String[] list = file.list();
            if (list != null) {
                for (String file2 : list) {
                    a(new File(file, file2));
                }
            }
        }
        file.delete();
    }

    private void a(final File file, final String str) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (file != null && file.isDirectory()) {
                        File[] listFiles = file.listFiles();
                        if (listFiles != null && listFiles.length > 0) {
                            for (File file : listFiles) {
                                if (file.isDirectory() && file.getName().startsWith("app_") && !file.getName().equals(str)) {
                                    d.this.a(file);
                                } else if (file.getName().startsWith("libsg")) {
                                    file.delete();
                                }
                            }
                        }
                    }
                    if (d.this.c != null) {
                        File filesDir = d.this.c.getFilesDir();
                        if (filesDir != null && filesDir.isDirectory()) {
                            File[] listFiles2 = filesDir.listFiles();
                            if (listFiles2 != null && listFiles2.length > 0) {
                                for (File file2 : listFiles2) {
                                    if (file2.getName().startsWith("libsecuritysdk")) {
                                        file2.delete();
                                    }
                                }
                            }
                        }
                    }
                } catch (Throwable unused) {
                }
            }
        }).start();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:43:0x009f, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x00a0, code lost:
        r1 = r0;
        r13 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:45:0x00a3, code lost:
        r0 = e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:46:0x00a4, code lost:
        r4 = r19;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x009f A[ExcHandler: all (r0v8 'th' java.lang.Throwable A[CUSTOM_DECLARE]), Splitter:B:1:0x0027] */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00dd A[SYNTHETIC, Splitter:B:53:0x00dd] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00e9 A[SYNTHETIC, Splitter:B:58:0x00e9] */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x00fa A[SYNTHETIC, Splitter:B:65:0x00fa] */
    /* JADX WARNING: Removed duplicated region for block: B:70:0x0106 A[SYNTHETIC, Splitter:B:70:0x0106] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean a(java.io.File r19, java.io.File r20) {
        /*
            r18 = this;
            r9 = r18
            r1 = r20
            java.io.File r10 = new java.io.File
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            java.lang.String r3 = r20.getAbsolutePath()
            r2.append(r3)
            java.lang.String r3 = ".tmp."
            r2.append(r3)
            int r3 = android.os.Process.myPid()
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            r10.<init>(r2)
            r11 = 0
            r2 = 0
            boolean r3 = r10.exists()     // Catch:{ Exception -> 0x00a3, all -> 0x009f }
            if (r3 == 0) goto L_0x0030
            r10.delete()     // Catch:{ Exception -> 0x00a3, all -> 0x009f }
        L_0x0030:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00a3, all -> 0x009f }
            r4 = r19
            r3.<init>(r4)     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            java.nio.channels.FileChannel r3 = r3.getChannel()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch:{ Exception -> 0x0099, all -> 0x0093 }
            r5.<init>(r10)     // Catch:{ Exception -> 0x0099, all -> 0x0093 }
            java.nio.channels.FileChannel r5 = r5.getChannel()     // Catch:{ Exception -> 0x0099, all -> 0x0093 }
            r14 = 0
            long r16 = r3.size()     // Catch:{ Exception -> 0x008e, all -> 0x0088 }
            r12 = r5
            r13 = r3
            r12.transferFrom(r13, r14, r16)     // Catch:{ Exception -> 0x008e, all -> 0x0088 }
            r3.close()     // Catch:{ Exception -> 0x008e, all -> 0x0088 }
            r5.close()     // Catch:{ Exception -> 0x0084, all -> 0x0081 }
            long r5 = r10.length()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            long r7 = r19.length()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 != 0) goto L_0x007d
            boolean r3 = r20.exists()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            if (r3 == 0) goto L_0x0078
            long r5 = r20.length()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            long r7 = r19.length()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            int r3 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r3 != 0) goto L_0x0075
            r11 = 1
            goto L_0x007d
        L_0x0075:
            r20.delete()     // Catch:{ Exception -> 0x009d, all -> 0x009f }
        L_0x0078:
            boolean r3 = r10.renameTo(r1)     // Catch:{ Exception -> 0x009d, all -> 0x009f }
            r11 = r3
        L_0x007d:
            r10.delete()
            return r11
        L_0x0081:
            r0 = move-exception
            r1 = r0
            goto L_0x008b
        L_0x0084:
            r0 = move-exception
            r12 = r2
            r13 = r5
            goto L_0x00a8
        L_0x0088:
            r0 = move-exception
            r1 = r0
            r2 = r3
        L_0x008b:
            r13 = r5
            goto L_0x00f8
        L_0x008e:
            r0 = move-exception
            r2 = r0
            r12 = r3
            r13 = r5
            goto L_0x00a9
        L_0x0093:
            r0 = move-exception
            r1 = r0
            r13 = r2
            r2 = r3
            goto L_0x00f8
        L_0x0099:
            r0 = move-exception
            r13 = r2
            r12 = r3
            goto L_0x00a8
        L_0x009d:
            r0 = move-exception
            goto L_0x00a6
        L_0x009f:
            r0 = move-exception
            r1 = r0
            r13 = r2
            goto L_0x00f8
        L_0x00a3:
            r0 = move-exception
            r4 = r19
        L_0x00a6:
            r12 = r2
            r13 = r12
        L_0x00a8:
            r2 = r0
        L_0x00a9:
            java.lang.String r3 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r3, r2)     // Catch:{ all -> 0x00f5 }
            r3 = 100047(0x186cf, float:1.40196E-40)
            r5 = 2
            java.lang.String r6 = r2.toString()     // Catch:{ all -> 0x00f5 }
            java.lang.String r2 = r19.getAbsolutePath()     // Catch:{ all -> 0x00f5 }
            java.lang.String r7 = r9.c(r2)     // Catch:{ all -> 0x00f5 }
            java.lang.String r1 = r20.getAbsolutePath()     // Catch:{ all -> 0x00f5 }
            java.lang.String r8 = r9.c(r1)     // Catch:{ all -> 0x00f5 }
            java.lang.String r1 = r10.getAbsolutePath()     // Catch:{ all -> 0x00f5 }
            java.lang.String r14 = r9.c(r1)     // Catch:{ all -> 0x00f5 }
            java.lang.String r15 = ""
            r1 = r9
            r2 = r3
            r3 = r5
            r4 = r6
            r5 = r7
            r6 = r8
            r7 = r14
            r8 = r15
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x00f5 }
            if (r12 == 0) goto L_0x00e7
            r12.close()     // Catch:{ Exception -> 0x00e1 }
            goto L_0x00e7
        L_0x00e1:
            r0 = move-exception
            java.lang.String r1 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r1, r0)
        L_0x00e7:
            if (r13 == 0) goto L_0x007d
            r13.close()     // Catch:{ Exception -> 0x00ed }
            goto L_0x007d
        L_0x00ed:
            r0 = move-exception
            java.lang.String r1 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r1, r0)
            goto L_0x007d
            return r11
        L_0x00f5:
            r0 = move-exception
            r1 = r0
            r2 = r12
        L_0x00f8:
            if (r2 == 0) goto L_0x0104
            r2.close()     // Catch:{ Exception -> 0x00fe }
            goto L_0x0104
        L_0x00fe:
            r0 = move-exception
            java.lang.String r2 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r2, r0)
        L_0x0104:
            if (r13 == 0) goto L_0x0110
            r13.close()     // Catch:{ Exception -> 0x010a }
            goto L_0x0110
        L_0x010a:
            r0 = move-exception
            java.lang.String r2 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r2, r0)
        L_0x0110:
            r10.delete()
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.d.a(java.io.File, java.io.File):boolean");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0050, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:44:0x005f, code lost:
        return true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x0076, code lost:
        return true;
     */
    /* JADX WARNING: Incorrect type for immutable var: ssa=java.lang.String, code=java.lang.Throwable, for r14v0, types: [java.lang.String] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x009b A[Catch:{ all -> 0x0082, all -> 0x00d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x00a4 A[Catch:{ all -> 0x0082, all -> 0x00d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:71:0x00a9 A[Catch:{ all -> 0x0082, all -> 0x00d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00b2 A[Catch:{ all -> 0x0082, all -> 0x00d4 }] */
    /* JADX WARNING: Removed duplicated region for block: B:75:0x00c6 A[SYNTHETIC, Splitter:B:75:0x00c6] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized boolean a(java.lang.Throwable r14, java.lang.String r15, java.io.File r16, java.lang.String r17, boolean r18) {
        /*
            r13 = this;
            r9 = r13
            r2 = r17
            monitor-enter(r13)
            android.content.Context r3 = r9.c     // Catch:{ all -> 0x00d4 }
            boolean r3 = com.alibaba.wireless.security.framework.utils.f.a(r3)     // Catch:{ all -> 0x00d4 }
            r4 = 1
            if (r3 == 0) goto L_0x0015
            int r3 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x00d4 }
            r5 = 19
            if (r3 <= r5) goto L_0x0015
            monitor-exit(r13)
            return r4
        L_0x0015:
            java.lang.String r3 = r9.g     // Catch:{ all -> 0x00d4 }
            if (r3 == 0) goto L_0x001b
            monitor-exit(r13)
            return r4
        L_0x001b:
            if (r16 == 0) goto L_0x0033
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x00d4 }
            java.lang.String r5 = r16.getParent()     // Catch:{ all -> 0x00d4 }
            r3.<init>(r5, r2)     // Catch:{ all -> 0x00d4 }
            boolean r3 = r3.exists()     // Catch:{ all -> 0x00d4 }
            if (r3 == 0) goto L_0x002e
            monitor-exit(r13)
            return r4
        L_0x002e:
            java.lang.String r3 = r16.getAbsolutePath()     // Catch:{ all -> 0x00d4 }
            goto L_0x0034
        L_0x0033:
            r3 = r14
        L_0x0034:
            if (r18 != 0) goto L_0x003b
            com.alibaba.wireless.security.framework.utils.c r5 = r9.b     // Catch:{ all -> 0x00d4 }
            r5.a()     // Catch:{ all -> 0x00d4 }
        L_0x003b:
            r5 = 0
            java.io.File r6 = new java.io.File     // Catch:{ Exception -> 0x0085 }
            r7 = r15
            r6.<init>(r7, r2)     // Catch:{ Exception -> 0x0085 }
            boolean r5 = r6.exists()     // Catch:{ Exception -> 0x007f }
            if (r5 == 0) goto L_0x0051
            if (r18 != 0) goto L_0x004f
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x00d4 }
            r1.b()     // Catch:{ all -> 0x00d4 }
        L_0x004f:
            monitor-exit(r13)
            return r4
        L_0x0051:
            boolean r5 = com.alibaba.wireless.security.framework.utils.f.a(r3, r2, r6)     // Catch:{ Exception -> 0x007f }
            if (r5 == 0) goto L_0x0060
            if (r18 != 0) goto L_0x005e
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x00d4 }
            r1.b()     // Catch:{ all -> 0x00d4 }
        L_0x005e:
            monitor-exit(r13)
            return r4
        L_0x0060:
            android.content.Context r5 = r9.c     // Catch:{ Exception -> 0x007f }
            android.content.pm.ApplicationInfo r5 = r5.getApplicationInfo()     // Catch:{ Exception -> 0x007f }
            java.lang.String r5 = r5.sourceDir     // Catch:{ Exception -> 0x007f }
            boolean r2 = com.alibaba.wireless.security.framework.utils.f.a(r5, r2, r6)     // Catch:{ Exception -> 0x007f }
            if (r2 == 0) goto L_0x0077
            if (r18 != 0) goto L_0x0075
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x00d4 }
            r1.b()     // Catch:{ all -> 0x00d4 }
        L_0x0075:
            monitor-exit(r13)
            return r4
        L_0x0077:
            if (r18 != 0) goto L_0x00c9
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x00d4 }
        L_0x007b:
            r1.b()     // Catch:{ all -> 0x00d4 }
            goto L_0x00c9
        L_0x007f:
            r0 = move-exception
            r10 = r0
            goto L_0x0088
        L_0x0082:
            r0 = move-exception
            r1 = r0
            goto L_0x00cc
        L_0x0085:
            r0 = move-exception
            r10 = r0
            r6 = r5
        L_0x0088:
            r2 = 100039(0x186c7, float:1.40184E-40)
            r4 = 107(0x6b, float:1.5E-43)
            java.lang.String r5 = r10.toString()     // Catch:{ all -> 0x0082 }
            java.lang.String r7 = r9.c()     // Catch:{ all -> 0x0082 }
            java.lang.String r8 = r9.c(r3)     // Catch:{ all -> 0x0082 }
            if (r6 == 0) goto L_0x00a4
            java.lang.String r3 = r6.getAbsolutePath()     // Catch:{ all -> 0x0082 }
            java.lang.String r3 = r9.c(r3)     // Catch:{ all -> 0x0082 }
            goto L_0x00a6
        L_0x00a4:
            java.lang.String r3 = ""
        L_0x00a6:
            r11 = r3
            if (r16 == 0) goto L_0x00b2
            java.lang.String r1 = r16.getAbsolutePath()     // Catch:{ all -> 0x0082 }
            java.lang.String r1 = r9.c(r1)     // Catch:{ all -> 0x0082 }
            goto L_0x00b4
        L_0x00b2:
            java.lang.String r1 = ""
        L_0x00b4:
            r12 = r1
            r1 = r9
            r3 = r4
            r4 = r5
            r5 = r7
            r6 = r8
            r7 = r11
            r8 = r12
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x0082 }
            java.lang.String r1 = ""
            com.alibaba.wireless.security.framework.utils.a.a(r1, r10)     // Catch:{ all -> 0x0082 }
            if (r18 != 0) goto L_0x00c9
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b     // Catch:{ all -> 0x00d4 }
            goto L_0x007b
        L_0x00c9:
            r1 = 0
            monitor-exit(r13)
            return r1
        L_0x00cc:
            if (r18 != 0) goto L_0x00d3
            com.alibaba.wireless.security.framework.utils.c r2 = r9.b     // Catch:{ all -> 0x00d4 }
            r2.b()     // Catch:{ all -> 0x00d4 }
        L_0x00d3:
            throw r1     // Catch:{ all -> 0x00d4 }
        L_0x00d4:
            r0 = move-exception
            r1 = r0
            monitor-exit(r13)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.d.a(java.lang.String, java.lang.String, java.io.File, java.lang.String, boolean):boolean");
    }

    /* access modifiers changed from: private */
    public boolean a(String str, String str2, boolean z) throws SecException {
        String str3 = str2;
        if (str.trim().length() == 0) {
            return true;
        }
        String str4 = str;
        String[] split = str4.split(";");
        for (int i2 = 0; i2 < split.length; i2++) {
            String trim = split[i2].trim();
            if (trim.length() != 0) {
                String[] split2 = trim.split(":");
                if (split2.length != 2) {
                    a(100040, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "nameVersionPair.length != 2", trim, str3, String.valueOf(z), String.valueOf(i2));
                    throw new SecException(SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR);
                }
                int indexOf = str3.indexOf(split2[0]);
                if (indexOf >= 0) {
                    int indexOf2 = str3.indexOf("(", indexOf);
                    int indexOf3 = str3.indexOf(")", indexOf);
                    if (indexOf2 < 0 || indexOf3 < 0 || indexOf2 > indexOf3) {
                        a(100040, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "indexLeftP < 0 || indexRightP < 0 || indexLeftP > indexRightP", String.valueOf(indexOf2), String.valueOf(indexOf3), "", String.valueOf(i2));
                        throw new SecException(SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR);
                    }
                    String substring = str3.substring(indexOf2 + 1, indexOf3);
                    if (a(substring, split2[1]) < 0) {
                        StringBuilder sb = new StringBuilder("version ");
                        sb.append(substring);
                        sb.append(" of ");
                        sb.append(split2[0]);
                        sb.append(" is not meet the requirement: ");
                        sb.append(split2[1]);
                        String sb2 = sb.toString();
                        if (str2.length() != 0) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(sb2);
                            sb3.append(", depended by: ");
                            sb3.append(str3);
                            sb2 = sb3.toString();
                        }
                        String str5 = sb2;
                        if (!z) {
                            a(100040, 113, "versionCompare(parentPluginVersion, nameVersionPair[1]) < 0", substring, split2[0], split2[1], String.valueOf(i2));
                        }
                        throw new SecException(str5, 113);
                    }
                } else {
                    ISGPluginInfo iSGPluginInfo = this.d.get(split2[0]);
                    if (iSGPluginInfo == null) {
                        Object obj = null;
                        try {
                            iSGPluginInfo = d(split2[0], str3, !z);
                        } catch (Throwable th) {
                            obj = th;
                        }
                        if (iSGPluginInfo == null) {
                            if (!z) {
                                if (obj instanceof SecException) {
                                    throw ((SecException) obj);
                                }
                                a(100040, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "throwable in loadPluginInner", String.valueOf(obj), str4, str3, String.valueOf(i2));
                                throw new SecException(str3, (int) SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR);
                            }
                        }
                    }
                    if (a(iSGPluginInfo.getVersion(), split2[1]) < 0) {
                        StringBuilder sb4 = new StringBuilder("version ");
                        sb4.append(iSGPluginInfo.getVersion());
                        sb4.append(" of ");
                        sb4.append(split2[0]);
                        sb4.append(" is not meet the requirement: ");
                        sb4.append(split2[1]);
                        String sb5 = sb4.toString();
                        if (str2.length() != 0) {
                            StringBuilder sb6 = new StringBuilder();
                            sb6.append(sb5);
                            sb6.append(", depended by: ");
                            sb6.append(str3);
                            sb5 = sb6.toString();
                        }
                        String str6 = sb5;
                        if (!z) {
                            a(100040, 113, "versionCompare(dependPlugin.getVersion(), nameVersionPair[1]) < 0", iSGPluginInfo.getVersion(), split2[0], split2[1], String.valueOf(i2));
                        }
                        throw new SecException(str6, 113);
                    }
                }
            }
        }
        return true;
    }

    private b b() {
        b bVar;
        File file = new File(this.k, "update.config");
        File file2 = new File(this.k, "init.config");
        if (this.j) {
            bVar = b.a(file);
            if (bVar == null) {
                return b.a(file2);
            }
            try {
                this.b.a();
                file2.delete();
                file.renameTo(file2);
            } catch (Throwable th) {
                this.b.b();
                throw th;
            }
        } else {
            try {
                this.b.a();
                bVar = b.a(file2);
            } catch (Throwable th2) {
                this.b.b();
                throw th2;
            }
        }
        this.b.b();
        return bVar;
    }

    private File b(String str) {
        String str2 = this.g;
        if (str2 == null) {
            try {
                str2 = this.c.getApplicationInfo().nativeLibraryDir;
            } catch (Exception unused) {
            }
        }
        if (str2 != null && str2.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(File.separator);
            sb.append("libsg");
            sb.append(str);
            sb.append(".so");
            File file = new File(sb.toString());
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }

    private ClassLoader b(String str, String str2, boolean z) {
        if (!z) {
            try {
                this.b.a();
            } catch (Throwable th) {
                if (!z) {
                    this.b.b();
                }
                throw th;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(this.c.getApplicationInfo().nativeLibraryDir);
        sb.append(File.pathSeparator);
        sb.append(str2);
        String sb2 = sb.toString();
        if (this.g != null) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(sb2);
            sb3.append(File.pathSeparator);
            sb3.append(this.g);
            sb2 = sb3.toString();
            com.alibaba.wireless.security.framework.utils.a.b("add path to native classloader ".concat(String.valueOf(sb2)));
        }
        if (o != null) {
            StringBuilder sb4 = new StringBuilder();
            sb4.append(sb2);
            sb4.append(File.pathSeparator);
            sb4.append(this.c.getApplicationInfo().sourceDir);
            sb4.append("!/lib/");
            sb4.append(o);
            sb2 = sb4.toString();
        }
        ClassLoader dexClassLoader = (VERSION.SDK_INT < 21 || "6.0.1".equalsIgnoreCase(VERSION.RELEASE)) ? new DexClassLoader(str, str2, sb2, d.class.getClassLoader()) : new PathClassLoader(str, sb2, d.class.getClassLoader());
        if (!z) {
            this.b.b();
        }
        return dexClassLoader;
    }

    private boolean b(File file) {
        try {
            if (!file.getParentFile().getCanonicalPath().equals(file.getCanonicalFile().getParentFile().getCanonicalPath())) {
                return true;
            }
            return true ^ file.getName().equals(file.getCanonicalFile().getName());
        } catch (Exception e2) {
            com.alibaba.wireless.security.framework.utils.a.a("", e2);
            a(100047, 0, e2.toString(), file.getAbsolutePath(), "", "", "");
            return false;
        }
    }

    private boolean b(File file, File file2) {
        Method method;
        try {
            Object obj = null;
            if (VERSION.SDK_INT >= 21) {
                method = Class.forName("android.system.Os").getDeclaredMethod("symlink", new Class[]{String.class, String.class});
            } else {
                Field declaredField = Class.forName("libcore.io.Libcore").getDeclaredField("os");
                declaredField.setAccessible(true);
                obj = declaredField.get(null);
                method = obj.getClass().getMethod("symlink", new Class[]{String.class, String.class});
            }
            method.invoke(obj, new Object[]{file.getAbsolutePath(), file2.getAbsolutePath()});
            return true;
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("create symbolic link( ");
            sb.append(file2.getAbsolutePath());
            sb.append(" -> ");
            sb.append(file.getAbsolutePath());
            sb.append(" ) failed");
            com.alibaba.wireless.security.framework.utils.a.a(sb.toString(), e2);
            String exc = e2.toString();
            String absolutePath = file.getAbsolutePath();
            String absolutePath2 = file2.getAbsolutePath();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(VERSION.SDK_INT);
            a(100047, 1, exc, absolutePath, absolutePath2, sb2.toString(), "");
            return false;
        }
    }

    private boolean b(String str, String str2) throws SecException {
        for (Entry next : this.d.entrySet()) {
            String str3 = (String) next.getKey();
            c cVar = (c) next.getValue();
            String a2 = cVar.a("reversedependencies");
            if (a2 != null) {
                String[] split = a2.split(";");
                for (int i2 = 0; i2 < split.length; i2++) {
                    String trim = split[i2].trim();
                    if (trim.length() != 0) {
                        String[] split2 = trim.split(":");
                        if (split2.length != 2) {
                            StringBuilder sb = new StringBuilder();
                            sb.append(str);
                            sb.append("(");
                            sb.append(str2);
                            sb.append(")");
                            String sb2 = sb.toString();
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(str3);
                            sb3.append("(");
                            sb3.append(cVar.getVersion());
                            sb3.append(")");
                            a(100041, SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR, "nameVersionPair.length != 2", sb2, sb3.toString(), c(cVar.getPluginPath()), a2);
                            throw new SecException(SecExceptionCode.SEC_ERROR_INIT_UNKNOWN_ERROR);
                        } else if (split2[0].equalsIgnoreCase(str) && a(str2, split2[1]) < 0) {
                            StringBuilder sb4 = new StringBuilder("plugin ");
                            sb4.append(str);
                            sb4.append("(");
                            sb4.append(str2);
                            sb4.append(") is not meet the reverse dependency of ");
                            sb4.append(str3);
                            sb4.append("(");
                            sb4.append(cVar.getVersion());
                            sb4.append("): ");
                            sb4.append(split2[0]);
                            sb4.append("(");
                            sb4.append(split2[1]);
                            sb4.append(")");
                            String sb5 = sb4.toString();
                            a(100041, 117, sb5, d.class.getClassLoader().toString(), c(cVar.getPluginPath()), a2, String.valueOf(i2));
                            throw new SecException(sb5, 117);
                        }
                    }
                }
                continue;
            }
        }
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0039  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0051 A[SYNTHETIC, Splitter:B:18:0x0051] */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0063 A[Catch:{ all -> 0x005d }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x013d A[Catch:{ all -> 0x005d }] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x014b  */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x0154  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.alibaba.wireless.security.framework.d.a c(java.lang.String r18, java.lang.String r19, boolean r20) throws com.alibaba.wireless.security.open.SecException {
        /*
            r17 = this;
            r9 = r17
            r10 = r18
            java.io.File r1 = r9.m
            if (r1 == 0) goto L_0x0034
            java.io.File r1 = new java.io.File
            java.io.File r2 = r9.m
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "libsg"
            r3.<init>(r4)
            r3.append(r10)
            java.lang.String r4 = ".so"
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            r1.<init>(r2, r3)
            boolean r2 = r1.exists()
            if (r2 == 0) goto L_0x0034
            java.io.File r2 = r9.m
            java.io.File r2 = r9.d(r2)
            java.io.File r3 = r9.m
            if (r2 == r3) goto L_0x0036
            r3 = 1
            goto L_0x0037
        L_0x0034:
            r1 = 0
            r2 = 0
        L_0x0036:
            r3 = 0
        L_0x0037:
            if (r2 != 0) goto L_0x0046
            java.io.File r2 = r9.k
            java.io.File r2 = r9.d(r2)
            java.io.File r3 = r9.k
            if (r2 == r3) goto L_0x0045
            r3 = 1
            goto L_0x0046
        L_0x0045:
            r3 = 0
        L_0x0046:
            r15 = r2
            r14 = r3
            if (r14 != 0) goto L_0x004f
            com.alibaba.wireless.security.framework.utils.c r2 = r9.b
            r2.a()
        L_0x004f:
            if (r1 != 0) goto L_0x0061
            java.io.File r1 = r17.a(r18)     // Catch:{ all -> 0x005d }
            boolean r2 = r9.c(r1)     // Catch:{ all -> 0x005d }
            if (r2 != 0) goto L_0x0061
            r1 = 0
            goto L_0x0061
        L_0x005d:
            r0 = move-exception
            r1 = r0
            goto L_0x020b
        L_0x0061:
            if (r1 == 0) goto L_0x013b
            java.lang.String r8 = r1.getAbsolutePath()     // Catch:{ all -> 0x005d }
            android.content.Context r2 = r9.c     // Catch:{ all -> 0x005d }
            java.lang.String r2 = r2.getPackageName()     // Catch:{ all -> 0x005d }
            java.lang.String r3 = "com.eg.android.AlipayGphone"
            boolean r2 = r3.equals(r2)     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x00e5
            if (r8 == 0) goto L_0x00e5
            java.lang.String r2 = "app_plugins_lib"
            boolean r2 = r8.contains(r2)     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x00e5
            r2 = 100088(0x186f8, float:1.40253E-40)
            r3 = 1
            java.lang.String r4 = android.os.Build.FINGERPRINT     // Catch:{ all -> 0x005d }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            r5.<init>()     // Catch:{ all -> 0x005d }
            r5.append(r8)     // Catch:{ all -> 0x005d }
            java.lang.String r6 = "("
            r5.append(r6)     // Catch:{ all -> 0x005d }
            long r6 = r1.getTotalSpace()     // Catch:{ all -> 0x005d }
            r5.append(r6)     // Catch:{ all -> 0x005d }
            java.lang.String r1 = ")"
            r5.append(r1)     // Catch:{ all -> 0x005d }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x005d }
            java.lang.Class<com.alibaba.wireless.security.framework.d> r1 = com.alibaba.wireless.security.framework.d.class
            java.lang.ClassLoader r1 = r1.getClassLoader()     // Catch:{ all -> 0x005d }
            java.lang.String r6 = r1.toString()     // Catch:{ all -> 0x005d }
            java.lang.String r7 = r15.getAbsolutePath()     // Catch:{ all -> 0x005d }
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            r1.<init>()     // Catch:{ all -> 0x005d }
            android.content.Context r13 = r9.c     // Catch:{ all -> 0x005d }
            android.content.pm.ApplicationInfo r13 = r13.getApplicationInfo()     // Catch:{ all -> 0x005d }
            java.lang.String r13 = r13.sourceDir     // Catch:{ all -> 0x005d }
            r1.append(r13)     // Catch:{ all -> 0x005d }
            java.lang.String r13 = ","
            r1.append(r13)     // Catch:{ all -> 0x005d }
            java.io.File r13 = new java.io.File     // Catch:{ all -> 0x005d }
            android.content.Context r11 = r9.c     // Catch:{ all -> 0x005d }
            android.content.pm.ApplicationInfo r11 = r11.getApplicationInfo()     // Catch:{ all -> 0x005d }
            java.lang.String r11 = r11.sourceDir     // Catch:{ all -> 0x005d }
            r13.<init>(r11)     // Catch:{ all -> 0x005d }
            long r12 = r13.lastModified()     // Catch:{ all -> 0x005d }
            r1.append(r12)     // Catch:{ all -> 0x005d }
            java.lang.String r11 = r1.toString()     // Catch:{ all -> 0x005d }
            r1 = r9
            r12 = r8
            r8 = r11
            r1.a(r2, r3, r4, r5, r6, r7, r8)     // Catch:{ all -> 0x005d }
            r1 = 0
            goto L_0x00e6
        L_0x00e5:
            r12 = r8
        L_0x00e6:
            if (r1 == 0) goto L_0x013b
            if (r12 == 0) goto L_0x013b
            java.lang.String r2 = "!/"
            boolean r2 = r12.contains(r2)     // Catch:{ all -> 0x005d }
            if (r2 == 0) goto L_0x013b
            java.lang.String r2 = "!/"
            r3 = 2
            java.lang.String[] r2 = r12.split(r2, r3)     // Catch:{ all -> 0x005d }
            r3 = 0
            r4 = r2[r3]     // Catch:{ all -> 0x005d }
            r5 = 1
            r2 = r2[r5]     // Catch:{ all -> 0x005d }
            java.lang.String[] r5 = n     // Catch:{ all -> 0x005d }
            int r6 = r5.length     // Catch:{ all -> 0x005d }
        L_0x0102:
            if (r3 >= r6) goto L_0x013b
            r7 = r5[r3]     // Catch:{ all -> 0x005d }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            java.lang.String r11 = "lib"
            r8.<init>(r11)     // Catch:{ all -> 0x005d }
            java.lang.String r11 = java.io.File.separator     // Catch:{ all -> 0x005d }
            r8.append(r11)     // Catch:{ all -> 0x005d }
            r8.append(r7)     // Catch:{ all -> 0x005d }
            java.lang.String r11 = java.io.File.separator     // Catch:{ all -> 0x005d }
            r8.append(r11)     // Catch:{ all -> 0x005d }
            java.lang.String r11 = "libsg"
            r8.append(r11)     // Catch:{ all -> 0x005d }
            r8.append(r10)     // Catch:{ all -> 0x005d }
            java.lang.String r11 = ".so"
            r8.append(r11)     // Catch:{ all -> 0x005d }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x005d }
            boolean r8 = r8.equals(r2)     // Catch:{ all -> 0x005d }
            if (r8 == 0) goto L_0x0138
            o = r7     // Catch:{ all -> 0x005d }
            java.io.File r1 = r9.a(r10, r15, r4, r2)     // Catch:{ all -> 0x005d }
            goto L_0x013b
        L_0x0138:
            int r3 = r3 + 1
            goto L_0x0102
        L_0x013b:
            if (r1 != 0) goto L_0x0141
            java.io.File r1 = r17.b(r18)     // Catch:{ all -> 0x005d }
        L_0x0141:
            if (r1 != 0) goto L_0x0149
            if (r20 == 0) goto L_0x0149
            java.io.File r1 = r9.a(r10, r15)     // Catch:{ all -> 0x005d }
        L_0x0149:
            if (r1 != 0) goto L_0x0154
            if (r14 != 0) goto L_0x0152
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b
            r1.b()
        L_0x0152:
            r2 = 0
            return r2
        L_0x0154:
            r2 = 0
            java.lang.String r3 = r1.getAbsolutePath()     // Catch:{ all -> 0x005d }
            java.lang.String r4 = ".zip"
            boolean r3 = r3.endsWith(r4)     // Catch:{ all -> 0x005d }
            if (r3 == 0) goto L_0x0168
            com.alibaba.wireless.security.framework.d$a r13 = new com.alibaba.wireless.security.framework.d$a     // Catch:{ all -> 0x005d }
            r13.<init>(r1, r15, r2, r14)     // Catch:{ all -> 0x005d }
            goto L_0x0203
        L_0x0168:
            boolean r3 = com.alibaba.wireless.security.framework.utils.f.a()     // Catch:{ all -> 0x005d }
            if (r3 == 0) goto L_0x0175
            com.alibaba.wireless.security.framework.d$a r13 = new com.alibaba.wireless.security.framework.d$a     // Catch:{ all -> 0x005d }
            r13.<init>(r1, r15, r1, r14)     // Catch:{ all -> 0x005d }
            goto L_0x0203
        L_0x0175:
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x005d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            java.lang.String r5 = "libsg"
            r4.<init>(r5)     // Catch:{ all -> 0x005d }
            r4.append(r10)     // Catch:{ all -> 0x005d }
            java.lang.String r5 = "_"
            r4.append(r5)     // Catch:{ all -> 0x005d }
            long r5 = r1.lastModified()     // Catch:{ all -> 0x005d }
            r4.append(r5)     // Catch:{ all -> 0x005d }
            java.lang.String r5 = ".zip"
            r4.append(r5)     // Catch:{ all -> 0x005d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x005d }
            r3.<init>(r15, r4)     // Catch:{ all -> 0x005d }
            boolean r4 = r3.exists()     // Catch:{ all -> 0x005d }
            if (r4 == 0) goto L_0x01b1
            boolean r4 = r9.b(r3)     // Catch:{ all -> 0x005d }
            if (r4 == 0) goto L_0x01b1
            boolean r4 = r9.c(r3, r1)     // Catch:{ all -> 0x005d }
            if (r4 != 0) goto L_0x01b1
            com.alibaba.wireless.security.framework.d$a r13 = new com.alibaba.wireless.security.framework.d$a     // Catch:{ all -> 0x005d }
            r13.<init>(r3, r15, r1, r14)     // Catch:{ all -> 0x005d }
            goto L_0x0203
        L_0x01b1:
            r3.delete()     // Catch:{ all -> 0x005d }
            boolean r4 = r9.b(r1, r3)     // Catch:{ all -> 0x005d }
            if (r4 == 0) goto L_0x01c0
            com.alibaba.wireless.security.framework.d$a r13 = new com.alibaba.wireless.security.framework.d$a     // Catch:{ all -> 0x005d }
            r13.<init>(r3, r15, r1, r14)     // Catch:{ all -> 0x005d }
            goto L_0x0203
        L_0x01c0:
            java.io.File r3 = new java.io.File     // Catch:{ all -> 0x005d }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x005d }
            java.lang.String r5 = "libsg"
            r4.<init>(r5)     // Catch:{ all -> 0x005d }
            r4.append(r10)     // Catch:{ all -> 0x005d }
            java.lang.String r5 = "_cp"
            r4.append(r5)     // Catch:{ all -> 0x005d }
            long r5 = r1.lastModified()     // Catch:{ all -> 0x005d }
            r4.append(r5)     // Catch:{ all -> 0x005d }
            java.lang.String r5 = ".zip"
            r4.append(r5)     // Catch:{ all -> 0x005d }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x005d }
            r3.<init>(r15, r4)     // Catch:{ all -> 0x005d }
            boolean r4 = r3.exists()     // Catch:{ all -> 0x005d }
            if (r4 == 0) goto L_0x01f6
            long r4 = r3.length()     // Catch:{ all -> 0x005d }
            long r6 = r1.length()     // Catch:{ all -> 0x005d }
            int r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r4 == 0) goto L_0x01fc
        L_0x01f6:
            boolean r4 = r9.a(r1, r3)     // Catch:{ all -> 0x005d }
            if (r4 == 0) goto L_0x0202
        L_0x01fc:
            com.alibaba.wireless.security.framework.d$a r13 = new com.alibaba.wireless.security.framework.d$a     // Catch:{ all -> 0x005d }
            r13.<init>(r3, r15, r1, r14)     // Catch:{ all -> 0x005d }
            goto L_0x0203
        L_0x0202:
            r13 = r2
        L_0x0203:
            if (r14 != 0) goto L_0x020a
            com.alibaba.wireless.security.framework.utils.c r1 = r9.b
            r1.b()
        L_0x020a:
            return r13
        L_0x020b:
            if (r14 != 0) goto L_0x0212
            com.alibaba.wireless.security.framework.utils.c r2 = r9.b
            r2.b()
        L_0x0212:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.framework.d.c(java.lang.String, java.lang.String, boolean):com.alibaba.wireless.security.framework.d$a");
    }

    private String c() {
        StringBuilder sb = new StringBuilder();
        File file = this.k;
        if (file == null || !file.exists() || !file.isDirectory()) {
            sb.append("not exists!");
        } else {
            try {
                sb.append("[");
                File[] listFiles = file.listFiles();
                int i2 = 0;
                while (listFiles != null && i2 < listFiles.length) {
                    File file2 = listFiles[i2];
                    if (file2.getName().startsWith("libsg") && (file2.getName().endsWith("zip") || file2.getName().endsWith(".so"))) {
                        sb.append(file2.getName());
                        sb.append("(");
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(b(file2));
                        sb2.append(" , ");
                        sb.append(sb2.toString());
                        sb.append(file2.length());
                        sb.append(") , ");
                    }
                    i2++;
                }
                sb.append("]");
            } catch (Throwable unused) {
            }
        }
        return sb.toString();
    }

    private String c(String str) {
        if (str == null || str.length() <= 0) {
            return "";
        }
        File file = new File(str);
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        if (b(file)) {
            sb.append("->");
            try {
                sb.append(file.getCanonicalPath());
            } catch (Exception unused) {
            }
        }
        sb.append('[');
        StringBuilder sb2 = new StringBuilder("exists:");
        sb2.append(file.exists());
        sb2.append(",");
        sb.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("size:");
        sb3.append(file.length());
        sb3.append(",");
        sb.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("canRead:");
        sb4.append(file.canRead());
        sb4.append(",");
        sb.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("canWrite:");
        sb5.append(file.canWrite());
        sb5.append(",");
        sb.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder("totalSpace:");
        sb6.append(file.getTotalSpace());
        sb6.append(",");
        sb.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("freeSpace:");
        sb7.append(file.getFreeSpace());
        sb7.append(",");
        sb.append(sb7.toString());
        sb.append(']');
        return sb.toString();
    }

    private boolean c(File file) {
        if (file != null) {
            try {
                String absolutePath = file.getAbsolutePath();
                if (absolutePath != null) {
                    if (absolutePath.length() > 0) {
                        if (f.a(this.c) || !absolutePath.startsWith("/system/")) {
                            return true;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
        return false;
    }

    private boolean c(File file, File file2) {
        try {
            return file.getCanonicalPath().equals(file2.getCanonicalPath());
        } catch (Exception e2) {
            com.alibaba.wireless.security.framework.utils.a.a("", e2);
            a(100046, 0, e2.toString(), file.getAbsolutePath(), file2.getAbsolutePath(), "", "");
            return false;
        }
    }

    private synchronized ISGPluginInfo d(String str, String str2, boolean z) throws SecException {
        StringBuilder sb;
        c cVar = this.d.get(str);
        if (cVar != null) {
            return cVar;
        }
        if (this.k == null || !this.k.exists()) {
            a();
        }
        a c2 = c(str, str2, z);
        if (!(c2 == null || c2.a == null)) {
            if (c2.a.exists()) {
                c a2 = a(str, c2, this.c, str2);
                if (a2 == null) {
                    if (c2.c != null) {
                        sb = new StringBuilder("src:");
                        sb.append(c(c2.c.getAbsolutePath()));
                    } else {
                        sb = new StringBuilder("zipfile:");
                        sb.append(c(c2.a.getAbsolutePath()));
                    }
                    a(100044, 110, "", str, str2, sb.toString(), "");
                    throw new SecException(str, 111);
                }
                this.d.put(str, a2);
                StringBuilder sb2 = new StringBuilder();
                sb2.append(str);
                sb2.append("(");
                sb2.append(a2.getVersion());
                sb2.append(")");
                final String sb3 = sb2.toString();
                final String a3 = a2.a("weakdependencies");
                if (str2.length() != 0) {
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append("->");
                    sb4.append(sb3);
                    sb3 = sb4.toString();
                }
                Looper myLooper = Looper.myLooper();
                if (myLooper == null || myLooper == Looper.getMainLooper()) {
                    com.alibaba.wireless.security.framework.utils.a.a("looper of current thread is null, try to create a new thread with a looper");
                    HandlerThread handlerThread = new HandlerThread("SGBackgroud");
                    handlerThread.start();
                    myLooper = handlerThread.getLooper();
                }
                new Handler(myLooper).postDelayed(new Runnable() {
                    public void run() {
                        try {
                            d.this.a(a3, sb3, true);
                        } catch (SecException e) {
                            com.alibaba.wireless.security.framework.utils.a.a("load weak dependency", e);
                        }
                    }
                }, 60000);
                return a2;
            }
        }
        if (!z) {
            return null;
        }
        StringBuilder sb5 = new StringBuilder("plugin ");
        sb5.append(str);
        sb5.append(" not existed");
        String sb6 = sb5.toString();
        if (str2.length() != 0) {
            StringBuilder sb7 = new StringBuilder();
            sb7.append(sb6);
            sb7.append(", depended by ");
            sb7.append(str2);
            sb6 = sb7.toString();
        }
        a(100044, 110, "", str, str2, "", "");
        throw new SecException(sb6, 110);
    }

    private File d(File file) {
        if (!file.exists() || !file.isDirectory() || !this.j) {
            return file;
        }
        File file2 = new File(file, "main");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        return file2.exists() ? file2 : file;
    }

    public void a(Context context, String str, String str2, boolean z, Object... objArr) {
        if (context.getApplicationContext() != null) {
            context = context.getApplicationContext();
        }
        this.c = context;
        this.h = str;
        this.j = f.c(this.c);
        this.f = z;
        UserTrackMethodJniBridge.init(this.c);
        if (str2 != null && !str2.isEmpty()) {
            this.g = str2;
        }
        try {
            a();
        } catch (SecException unused) {
        }
    }

    public synchronized <T> T getInterface(Class<T> cls) throws SecException {
        Object obj = this.a.get(cls);
        if (obj != null) {
            return cls.cast(obj);
        }
        String str = null;
        if (this.i != null) {
            str = this.i.d();
            if (str == null || str.length() == 0) {
                str = this.i.a(cls.getName());
            }
        }
        if (str == null || str.length() == 0) {
            str = a(cls);
        }
        if (str != null) {
            if (str.length() != 0) {
                ISGPluginInfo pluginInfo = getPluginInfo(str);
                if (pluginInfo == null) {
                    throw new SecException(110);
                }
                T t = pluginInfo.getSGPluginEntry().getInterface(cls);
                if (t == null) {
                    String name = cls.getName();
                    StringBuilder sb = new StringBuilder();
                    sb.append(str);
                    sb.append("(");
                    sb.append(pluginInfo.getVersion());
                    sb.append(")");
                    a(LogConstant.PAGE_ID_SAVE_MAIN, 112, "", name, sb.toString(), c(pluginInfo.getPluginPath()), "");
                    throw new SecException(112);
                }
                this.a.put(cls, t);
                return cls.cast(t);
            }
        }
        throw new SecException(150);
    }

    public String getMainPluginName() {
        return "main";
    }

    public ISGPluginInfo getPluginInfo(String str) throws SecException {
        return d(str, "", true);
    }

    public IRouterComponent getRouter() {
        return this.e;
    }
}
