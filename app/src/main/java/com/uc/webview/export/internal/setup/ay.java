package com.uc.webview.export.internal.setup;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.system.Os;
import android.system.OsConstants;
import android.util.Pair;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCKnownException;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.j;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

/* compiled from: ProGuard */
public class ay extends UCSubSetupTask<ay, ay> {
    /* access modifiers changed from: private */
    public static final String a = "ay";
    private static final HashMap<String, String> b = new az();
    private static long c = 1;
    private static long d = 2;
    private static long e = (c << 2);
    private static long f = (c << 3);
    private static long g = (c << 4);
    private static long h = (c << 5);
    private static long i = 0;
    private static long j = 1;
    private static long k = 2;
    private static long l = (j << 2);
    private static long m = (j << 3);
    private static long n = (j << 4);
    private static long o = (j << 5);
    private static long p = (j << 6);
    private static long q = (j << 7);
    private static long r = (j << 8);
    private static long s = (j << 9);
    private static long t = (j << 10);
    private static long u = (j << 11);

    /* compiled from: ProGuard */
    static class a {
        @TargetApi(21)
        protected static boolean a(File file) {
            if (VERSION.SDK_INT < 21) {
                return b(file);
            }
            try {
                if ((Os.stat(file.getAbsolutePath()).st_mode & OsConstants.S_IXOTH) == 0) {
                    return false;
                }
                String a = ay.a;
                StringBuilder sb = new StringBuilder();
                sb.append(file.getAbsolutePath());
                sb.append(" other can exe.");
                Log.d(a, sb.toString());
                return true;
            } catch (Throwable th) {
                Log.d(ay.a, ".run", th);
                return false;
            }
        }

        private static boolean b(File file) {
            try {
                Field field = Class.forName("libcore.io.Libcore").getField("os");
                field.setAccessible(true);
                Object obj = field.get(null);
                Method declaredMethod = Class.forName("libcore.io.Os").getDeclaredMethod("stat", new Class[]{String.class});
                declaredMethod.setAccessible(true);
                if ((((Integer) Class.forName("libcore.io.StructStat").getField("st_mode").get(declaredMethod.invoke(obj, new Object[]{file.getAbsolutePath()}))).intValue() & ((Integer) Class.forName("libcore.io.OsConstants").getField("S_IXOTH").get(null)).intValue()) == 0) {
                    return false;
                }
                String a = ay.a;
                StringBuilder sb = new StringBuilder();
                sb.append(file.getAbsolutePath());
                sb.append(" other can exe.");
                Log.d(a, sb.toString());
                return true;
            } catch (IllegalAccessException e) {
                String a2 = ay.a;
                StringBuilder sb2 = new StringBuilder("==checkFileOtherPermissionsReflect IllegalAccessException.");
                sb2.append(e.getMessage());
                Log.e(a2, sb2.toString());
                return false;
            } catch (InvocationTargetException e2) {
                String a3 = ay.a;
                StringBuilder sb3 = new StringBuilder("==checkFileOtherPermissionsReflect InvocationTargetException.");
                sb3.append(e2.getMessage());
                Log.e(a3, sb3.toString());
                return false;
            } catch (NoSuchMethodException e3) {
                String a4 = ay.a;
                StringBuilder sb4 = new StringBuilder("==checkFileOtherPermissionsReflect NoSuchMethodException.");
                sb4.append(e3.getMessage());
                Log.e(a4, sb4.toString());
                return false;
            } catch (NoSuchFieldException e4) {
                String a5 = ay.a;
                StringBuilder sb5 = new StringBuilder("==checkFileOtherPermissionsReflect NoSuchFieldException.");
                sb5.append(e4.getMessage());
                Log.e(a5, sb5.toString());
                return false;
            } catch (ClassNotFoundException e5) {
                String a6 = ay.a;
                StringBuilder sb6 = new StringBuilder("==checkFileOtherPermissionsReflect ClassNotFoundException.");
                sb6.append(e5.getMessage());
                Log.e(a6, sb6.toString());
                return false;
            } catch (Throwable th) {
                String a7 = ay.a;
                StringBuilder sb7 = new StringBuilder("==checkFileOtherPermissionsReflect Throwable.");
                sb7.append(th.getMessage());
                Log.e(a7, sb7.toString());
                return false;
            }
        }
    }

    /* JADX WARNING: No exception handlers in catch block: Catch:{  } */
    /* JADX WARNING: Removed duplicated region for block: B:147:0x03f5 A[SYNTHETIC, Splitter:B:147:0x03f5] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
            r20 = this;
            r7 = r20
            long r1 = c
            long r8 = i
            java.lang.String r3 = "sc_loc"
            java.lang.String r3 = com.uc.webview.export.extension.UCCore.getParam(r3)     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            boolean r3 = java.lang.Boolean.parseBoolean(r3)     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            r4 = 0
            if (r3 != 0) goto L_0x0019
            r3 = 0
            goto L_0x001d
        L_0x0019:
            boolean r3 = r3.booleanValue()     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
        L_0x001d:
            java.lang.String r5 = "CONTEXT"
            java.lang.Object r5 = r7.getOption(r5)     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            android.content.Context r5 = (android.content.Context) r5     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            r6 = 10011(0x271b, float:1.4028E-41)
            java.lang.Object[] r10 = new java.lang.Object[r4]     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            java.lang.Object r6 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r6, r10)     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            boolean r6 = r6.booleanValue()     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            if (r6 == 0) goto L_0x0069
            long r10 = d     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            android.util.Pair r12 = new android.util.Pair
            java.lang.String r13 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r10
            r5 = r8
            r1.<init>(r2, r3, r5)
            r12.<init>(r13, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r12)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r8)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x0069:
            com.uc.webview.export.internal.setup.UCMRunningInfo r6 = com.uc.webview.export.utility.SetupTask.getTotalLoadedUCM()     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            if (r6 != 0) goto L_0x00a3
            long r10 = e     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            android.util.Pair r12 = new android.util.Pair
            java.lang.String r13 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r10
            r5 = r8
            r1.<init>(r2, r3, r5)
            r12.<init>(r13, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r12)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r8)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x00a3:
            int r10 = r6.coreType     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            r11 = 2
            if (r10 != r11) goto L_0x00dc
            long r10 = f     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            android.util.Pair r12 = new android.util.Pair
            java.lang.String r13 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r10
            r5 = r8
            r1.<init>(r2, r3, r5)
            r12.<init>(r13, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r12)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r8)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x00dc:
            boolean r10 = r6.isShareCore     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            if (r10 == 0) goto L_0x0114
            long r10 = h     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            android.util.Pair r12 = new android.util.Pair
            java.lang.String r13 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r10
            r5 = r8
            r1.<init>(r2, r3, r5)
            r12.<init>(r13, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r12)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r8)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x0114:
            long r12 = g     // Catch:{ Throwable -> 0x044d, all -> 0x0448 }
            long r1 = j     // Catch:{ Throwable -> 0x0446 }
            com.uc.webview.export.internal.setup.UCMPackageInfo r6 = r6.ucmPackageInfo     // Catch:{ Throwable -> 0x043f, all -> 0x0438 }
            r8 = 0
            java.io.File r5 = com.uc.webview.export.utility.download.UpdateTask.getUpdateRoot(r5)     // Catch:{ Exception -> 0x03e5 }
            java.io.File r9 = new java.io.File     // Catch:{ Exception -> 0x03e5 }
            java.lang.String r10 = r5.getParent()     // Catch:{ Exception -> 0x03e5 }
            java.lang.String r14 = "config.json"
            r9.<init>(r10, r14)     // Catch:{ Exception -> 0x03e5 }
            java.lang.String r10 = a     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r14 = "处理共享内核:"
            java.lang.String r15 = java.lang.String.valueOf(r3)     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r14 = r14.concat(r15)     // Catch:{ Exception -> 0x03e0 }
            com.uc.webview.export.internal.utility.Log.d(r10, r14)     // Catch:{ Exception -> 0x03e0 }
            if (r3 != 0) goto L_0x0186
            long r10 = k     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            boolean r1 = r9.exists()     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
            if (r1 == 0) goto L_0x0146
            r9.delete()     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
        L_0x0146:
            android.util.Pair r8 = new android.util.Pair
            java.lang.String r9 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r10
            r1.<init>(r2, r3, r5)
            r8.<init>(r9, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r8)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x0178:
            r0 = move-exception
            goto L_0x044b
        L_0x017b:
            r0 = move-exception
            r1 = r0
            r8 = r10
            goto L_0x0450
        L_0x0180:
            r0 = move-exception
            r1 = r0
            r17 = r10
            goto L_0x03ea
        L_0x0186:
            java.io.File r3 = r5.getParentFile()     // Catch:{ Exception -> 0x03e0 }
            java.io.File r3 = r3.getParentFile()     // Catch:{ Exception -> 0x03e0 }
            boolean r3 = com.uc.webview.export.internal.setup.ay.a.a(r3)     // Catch:{ Exception -> 0x03e0 }
            if (r3 != 0) goto L_0x01c8
            long r10 = u     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            android.util.Pair r8 = new android.util.Pair
            java.lang.String r9 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r10
            r1.<init>(r2, r3, r5)
            r8.<init>(r9, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r8)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x01c8:
            java.lang.String r3 = "sc_hucmv"
            java.lang.String r3 = com.uc.webview.export.extension.UCCore.getParam(r3)     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r10 = com.uc.webview.export.Build.UCM_VERSION     // Catch:{ Exception -> 0x03e0 }
            boolean r3 = com.uc.webview.export.internal.utility.g.a(r3, r10, r8)     // Catch:{ Exception -> 0x03e0 }
            if (r3 != 0) goto L_0x0236
            java.lang.String r3 = a     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r5 = "共享内核版本校验失败 UCM_VERSION: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r5 = com.uc.webview.export.Build.UCM_VERSION     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            r4.append(r5)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r5 = " Host Versions: "
            r4.append(r5)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r5 = "sc_hucmv"
            java.lang.String r5 = com.uc.webview.export.extension.UCCore.getParam(r5)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            r4.append(r5)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            com.uc.webview.export.internal.utility.Log.d(r3, r4)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            long r10 = t     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            boolean r1 = r9.exists()     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
            if (r1 == 0) goto L_0x0204
            r9.delete()     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
        L_0x0204:
            android.util.Pair r8 = new android.util.Pair
            java.lang.String r9 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r10
            r1.<init>(r2, r3, r5)
            r8.<init>(r9, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r8)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x0236:
            java.util.concurrent.ConcurrentHashMap r3 = r7.mOptions     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r10 = "ucmLibDir"
            boolean r3 = r3.containsKey(r10)     // Catch:{ Exception -> 0x03e0 }
            java.util.ArrayList r10 = new java.util.ArrayList     // Catch:{ Exception -> 0x03e0 }
            r10.<init>()     // Catch:{ Exception -> 0x03e0 }
            if (r3 == 0) goto L_0x02c9
            if (r6 != 0) goto L_0x024b
            r17 = r1
            goto L_0x02f4
        L_0x024b:
            r8 = 10012(0x271c, float:1.403E-41)
            r14 = 1
            java.lang.Object[] r15 = new java.lang.Object[r14]     // Catch:{ Exception -> 0x03e0 }
            java.util.concurrent.ConcurrentHashMap r14 = r7.mOptions     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r11 = "ucmLibDir"
            java.lang.Object r11 = r14.get(r11)     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r11 = (java.lang.String) r11     // Catch:{ Exception -> 0x03e0 }
            r15[r4] = r11     // Catch:{ Exception -> 0x03e0 }
            java.lang.Object r8 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r8, r15)     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r8 = (java.lang.String) r8     // Catch:{ Exception -> 0x03e0 }
            java.util.concurrent.ConcurrentHashMap r11 = r7.mOptions     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r14 = "CONTEXT"
            java.lang.Object r11 = r11.get(r14)     // Catch:{ Exception -> 0x03e0 }
            android.content.Context r11 = (android.content.Context) r11     // Catch:{ Exception -> 0x03e0 }
            com.uc.webview.export.internal.setup.UCMPackageInfo$a r14 = new com.uc.webview.export.internal.setup.UCMPackageInfo$a     // Catch:{ Exception -> 0x03e0 }
            r14.<init>()     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r15 = com.uc.webview.export.Build.UCM_VERSION     // Catch:{ Exception -> 0x03e0 }
            r14.a = r15     // Catch:{ Exception -> 0x03e0 }
            java.lang.String r15 = com.uc.webview.export.Build.UCM_SUPPORT_SDK_MIN     // Catch:{ Exception -> 0x03e0 }
            r14.b = r15     // Catch:{ Exception -> 0x03e0 }
            r15 = 2
            java.lang.Object[] r4 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x03e0 }
            r17 = r1
            r15 = 1
            java.lang.Object[] r1 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x03de }
            r2 = 0
            r1[r2] = r11     // Catch:{ Exception -> 0x03de }
            r15 = 10046(0x273e, float:1.4077E-41)
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r15, r1)     // Catch:{ Exception -> 0x03de }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Exception -> 0x03de }
            r4[r2] = r1     // Catch:{ Exception -> 0x03de }
            r1 = 1
            r4[r1] = r8     // Catch:{ Exception -> 0x03de }
            r1 = 10035(0x2733, float:1.4062E-41)
            java.lang.Object r2 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r4)     // Catch:{ Exception -> 0x03de }
            java.io.File r2 = (java.io.File) r2     // Catch:{ Exception -> 0x03de }
            java.lang.String r2 = r2.getAbsolutePath()     // Catch:{ Exception -> 0x03de }
            r14.c = r2     // Catch:{ Exception -> 0x03de }
            java.lang.String r2 = r6.soDirPath     // Catch:{ Exception -> 0x03de }
            r14.d = r2     // Catch:{ Exception -> 0x03de }
            r2 = 2
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x03de }
            r4 = 10008(0x2718, float:1.4024E-41)
            r15 = 1
            java.lang.Object[] r1 = new java.lang.Object[r15]     // Catch:{ Exception -> 0x03de }
            r16 = 0
            r1[r16] = r11     // Catch:{ Exception -> 0x03de }
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r4, r1)     // Catch:{ Exception -> 0x03de }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Exception -> 0x03de }
            r2[r16] = r1     // Catch:{ Exception -> 0x03de }
            r2[r15] = r8     // Catch:{ Exception -> 0x03de }
            r1 = 10035(0x2733, float:1.4062E-41)
            java.lang.Object r1 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r1, r2)     // Catch:{ Exception -> 0x03de }
            java.io.File r1 = (java.io.File) r1     // Catch:{ Exception -> 0x03de }
            java.lang.String r1 = r1.getAbsolutePath()     // Catch:{ Exception -> 0x03de }
            r14.e = r1     // Catch:{ Exception -> 0x03de }
            r8 = r14
            goto L_0x02f4
        L_0x02c9:
            r17 = r1
            if (r6 != 0) goto L_0x02ce
            goto L_0x02f4
        L_0x02ce:
            com.uc.webview.export.internal.setup.UCMPackageInfo$a r8 = new com.uc.webview.export.internal.setup.UCMPackageInfo$a     // Catch:{ Exception -> 0x03de }
            r8.<init>()     // Catch:{ Exception -> 0x03de }
            java.lang.String r1 = com.uc.webview.export.Build.UCM_VERSION     // Catch:{ Exception -> 0x03de }
            r8.a = r1     // Catch:{ Exception -> 0x03de }
            java.lang.String r1 = com.uc.webview.export.Build.UCM_SUPPORT_SDK_MIN     // Catch:{ Exception -> 0x03de }
            r8.b = r1     // Catch:{ Exception -> 0x03de }
            java.io.File r1 = new java.io.File     // Catch:{ Exception -> 0x03de }
            android.util.Pair<java.lang.String, java.lang.String> r2 = r6.coreImplModule     // Catch:{ Exception -> 0x03de }
            java.lang.Object r2 = r2.first     // Catch:{ Exception -> 0x03de }
            java.lang.String r2 = (java.lang.String) r2     // Catch:{ Exception -> 0x03de }
            r1.<init>(r2)     // Catch:{ Exception -> 0x03de }
            java.lang.String r1 = r1.getParent()     // Catch:{ Exception -> 0x03de }
            r8.c = r1     // Catch:{ Exception -> 0x03de }
            java.lang.String r1 = r6.soDirPath     // Catch:{ Exception -> 0x03de }
            r8.d = r1     // Catch:{ Exception -> 0x03de }
            java.lang.String r1 = r6.resDirPath     // Catch:{ Exception -> 0x03de }
            r8.e = r1     // Catch:{ Exception -> 0x03de }
        L_0x02f4:
            if (r8 == 0) goto L_0x02fc
            long r1 = l     // Catch:{ Exception -> 0x03de }
            r10.add(r8)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            goto L_0x02fe
        L_0x02fc:
            r1 = r17
        L_0x02fe:
            boolean r4 = a(r10, r9)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            if (r4 != 0) goto L_0x033f
            long r10 = m     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r1 = a     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
            java.lang.String r2 = "处理共享内核:内核信息无变化"
            com.uc.webview.export.internal.utility.Log.d(r1, r2)     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
            android.util.Pair r8 = new android.util.Pair
            java.lang.String r9 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r10
            r1.<init>(r2, r3, r5)
            r8.<init>(r9, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r8)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x033f:
            boolean r4 = com.uc.webview.export.internal.utility.j.b(r5)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            if (r4 != 0) goto L_0x0380
            long r10 = n     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r1 = a     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
            java.lang.String r2 = "setExecutable(ucmRoot) fail."
            com.uc.webview.export.internal.utility.Log.e(r1, r2)     // Catch:{ Exception -> 0x0180, Throwable -> 0x017b, all -> 0x0178 }
            android.util.Pair r8 = new android.util.Pair
            java.lang.String r9 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r10
            r1.<init>(r2, r3, r5)
            r8.<init>(r9, r14)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r8)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x0380:
            if (r3 == 0) goto L_0x03a3
            boolean r3 = r20.b()     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            if (r3 != 0) goto L_0x03a3
            long r3 = o     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r1 = a     // Catch:{ Exception -> 0x039e, Throwable -> 0x0399, all -> 0x0394 }
            java.lang.String r2 = "createShareJarFromSo() fail."
            com.uc.webview.export.internal.utility.Log.e(r1, r2)     // Catch:{ Exception -> 0x039e, Throwable -> 0x0399, all -> 0x0394 }
        L_0x0391:
            r8 = r3
            goto L_0x0402
        L_0x0394:
            r0 = move-exception
            r8 = r0
            r10 = r3
            goto L_0x046a
        L_0x0399:
            r0 = move-exception
            r1 = r0
            r8 = r3
            goto L_0x0450
        L_0x039e:
            r0 = move-exception
            r1 = r0
            r17 = r3
            goto L_0x03ea
        L_0x03a3:
            if (r8 == 0) goto L_0x03b5
            boolean r3 = r7.a(r6, r8)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            if (r3 != 0) goto L_0x03b5
            long r3 = p     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r1 = a     // Catch:{ Exception -> 0x039e, Throwable -> 0x0399, all -> 0x0394 }
            java.lang.String r2 = "modifyFilePermissions(config) fail."
            com.uc.webview.export.internal.utility.Log.e(r1, r2)     // Catch:{ Exception -> 0x039e, Throwable -> 0x0399, all -> 0x0394 }
            goto L_0x0391
        L_0x03b5:
            boolean r3 = com.uc.webview.export.internal.setup.UCMPackageInfo.a.a(r10, r9)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            if (r3 == 0) goto L_0x03cb
            boolean r3 = com.uc.webview.export.internal.utility.j.a(r9)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            if (r3 == 0) goto L_0x03cb
            java.lang.String r3 = a     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r4 = "success modify share core config file."
            com.uc.webview.export.internal.utility.Log.i(r3, r4)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            long r3 = q     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            goto L_0x0391
        L_0x03cb:
            java.lang.String r3 = a     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            java.lang.String r4 = "failure modify share core config file."
            com.uc.webview.export.internal.utility.Log.i(r3, r4)     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            long r3 = r     // Catch:{ Throwable -> 0x03fe, all -> 0x03f9 }
            boolean r1 = r9.exists()     // Catch:{ Exception -> 0x039e, Throwable -> 0x0399, all -> 0x0394 }
            if (r1 == 0) goto L_0x0391
            r9.delete()     // Catch:{ Exception -> 0x039e, Throwable -> 0x0399, all -> 0x0394 }
            goto L_0x0391
        L_0x03de:
            r0 = move-exception
            goto L_0x03e3
        L_0x03e0:
            r0 = move-exception
            r17 = r1
        L_0x03e3:
            r1 = r0
            goto L_0x03ea
        L_0x03e5:
            r0 = move-exception
            r17 = r1
            r1 = r0
            r9 = r8
        L_0x03ea:
            java.lang.String r2 = a     // Catch:{ Throwable -> 0x0436, all -> 0x0434 }
            java.lang.String r3 = "FilePermissions.run"
            com.uc.webview.export.internal.utility.Log.e(r2, r3, r1)     // Catch:{ Throwable -> 0x0436, all -> 0x0434 }
            long r1 = s     // Catch:{ Throwable -> 0x0436, all -> 0x0434 }
            if (r9 == 0) goto L_0x0401
            r9.delete()     // Catch:{  }
            goto L_0x0401
        L_0x03f9:
            r0 = move-exception
            r8 = r0
            r10 = r1
            goto L_0x046a
        L_0x03fe:
            r0 = move-exception
            r8 = r1
            goto L_0x044f
        L_0x0401:
            r8 = r1
        L_0x0402:
            android.util.Pair r10 = new android.util.Pair
            java.lang.String r11 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r8
            r1.<init>(r2, r3, r5)
            r10.<init>(r11, r14)
        L_0x0412:
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r10)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r8)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            return
        L_0x0434:
            r0 = move-exception
            goto L_0x043b
        L_0x0436:
            r0 = move-exception
            goto L_0x0442
        L_0x0438:
            r0 = move-exception
            r17 = r1
        L_0x043b:
            r8 = r0
            r10 = r17
            goto L_0x046a
        L_0x043f:
            r0 = move-exception
            r17 = r1
        L_0x0442:
            r1 = r0
            r8 = r17
            goto L_0x0450
        L_0x0446:
            r0 = move-exception
            goto L_0x044f
        L_0x0448:
            r0 = move-exception
            r12 = r1
        L_0x044a:
            r10 = r8
        L_0x044b:
            r8 = r0
            goto L_0x046a
        L_0x044d:
            r0 = move-exception
            r12 = r1
        L_0x044f:
            r1 = r0
        L_0x0450:
            java.lang.String r2 = a     // Catch:{ all -> 0x0468 }
            java.lang.String r3 = ".run"
            com.uc.webview.export.internal.utility.Log.d(r2, r3, r1)     // Catch:{ all -> 0x0468 }
            android.util.Pair r10 = new android.util.Pair
            java.lang.String r11 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r14 = new com.uc.webview.export.internal.setup.ba
            r1 = r14
            r2 = r7
            r3 = r12
            r5 = r8
            r1.<init>(r2, r3, r5)
            r10.<init>(r11, r14)
            goto L_0x0412
        L_0x0468:
            r0 = move-exception
            goto L_0x044a
        L_0x046a:
            android.util.Pair r9 = new android.util.Pair
            java.lang.String r14 = "csc_tpk"
            com.uc.webview.export.internal.setup.ba r15 = new com.uc.webview.export.internal.setup.ba
            r1 = r15
            r2 = r7
            r3 = r12
            r5 = r10
            r1.<init>(r2, r3, r5)
            r9.<init>(r14, r15)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.statAKV(r9)
            java.lang.String r1 = a
            java.lang.String r2 = ".run processStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            java.lang.String r1 = a
            java.lang.String r2 = ".run doShareStatFinal: "
            java.lang.String r3 = java.lang.String.valueOf(r10)
            java.lang.String r2 = r2.concat(r3)
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.ay.run():void");
    }

    private static String a(String str) {
        String[] strArr;
        loop0:
        while (true) {
            File file = (File) UCMPackageInfo.invoke(UCMPackageInfo.expectDirFile1S, str);
            try {
                return new File(UCCyclone.expectFile(file, UCMPackageInfo.CORE_IMPL_DEX_FILE_USING_SO_SUFFIX).getAbsolutePath()).getParent();
            } catch (UCKnownException e2) {
                File[] listFiles = file.listFiles();
                if (listFiles == null) {
                    break;
                }
                for (String str2 : UCMPackageInfo.ARCHS) {
                    int length = listFiles.length;
                    int i2 = 0;
                    while (i2 < length) {
                        File file2 = listFiles[i2];
                        if (!str2.equals(file2.getName()) || !file2.isDirectory()) {
                            i2++;
                        } else {
                            str = file2.getAbsolutePath();
                        }
                    }
                }
                break loop0;
                throw e2;
            }
        }
        throw e2;
    }

    private static String a(Context context, String str, String str2) {
        if (j.a(str2)) {
            return null;
        }
        File file = new File(str2);
        String name = file.getName();
        String str3 = b.get(name);
        if (str3 == null && name.equals(UCMPackageInfo.BROWSER_IF_DEX_FILE_USING_SO_SUFFIX)) {
            str3 = b.get(UCMPackageInfo.BROWSER_IF_FOR_EXPORT_FILE_USING_SO_SUFFIX);
        }
        String str4 = (String) UCMPackageInfo.invoke(10012, str);
        return j.a(file, new File((File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, (File) UCMPackageInfo.invoke(UCMPackageInfo.getKernalShareJarLnkRoot, context), str4), str3), new File((File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, (File) UCMPackageInfo.invoke(UCMPackageInfo.getKernalShareJarCpyRoot, context), str4), str3), false).getAbsolutePath();
    }

    private boolean b() {
        String absolutePath;
        String absolutePath2;
        String str;
        String str2 = (String) this.mOptions.get(UCCore.OPTION_UCM_LIB_DIR);
        File file = (File) UCMPackageInfo.invoke(UCMPackageInfo.expectDirFile1S, a(str2));
        Context context = (Context) this.mOptions.get(UCCore.OPTION_CONTEXT);
        try {
            absolutePath = UCCyclone.expectFile(file, UCMPackageInfo.CORE_IMPL_DEX_FILE_USING_SO_SUFFIX).getAbsolutePath();
            absolutePath2 = UCCyclone.expectFile(file, UCMPackageInfo.SDK_SHELL_DEX_FILE_USING_SO_SUFFIX).getAbsolutePath();
            str = UCCyclone.expectFile(file, UCMPackageInfo.BROWSER_IF_FOR_EXPORT_FILE_USING_SO_SUFFIX).getAbsolutePath();
        } catch (Throwable th) {
            String str3 = a;
            StringBuilder sb = new StringBuilder(".createShareJarFromSo fail! ");
            sb.append(th.toString());
            Log.e(str3, sb.toString());
            return false;
        }
        a(context, str2, absolutePath);
        a(context, str2, absolutePath2);
        a(context, str2, str);
        return true;
    }

    private boolean a(UCMPackageInfo uCMPackageInfo, com.uc.webview.export.internal.setup.UCMPackageInfo.a aVar) {
        File[] listFiles;
        File file = (File) UCMPackageInfo.invoke(10001, (Context) this.mOptions.get(UCCore.OPTION_CONTEXT));
        if (!j.b(file)) {
            Log.e(a, "setExecutable ucmsdkDir fail.".concat(String.valueOf(file)));
            return false;
        }
        File file2 = new File(aVar.c);
        if (!j.b(file2, file)) {
            Log.e(a, "Utils.modifyFilePermissionsDirFromTo dexDir and parent's dir fail.".concat(String.valueOf(file2)));
            return false;
        }
        for (String file3 : b.values()) {
            File file4 = new File(file2, file3);
            if (!j.a(file4)) {
                Log.e(a, "setReadable(new File(dexDir, dex)) fail.".concat(String.valueOf(file4)));
                return false;
            }
        }
        File file5 = new File(aVar.e);
        if (!j.b(file5, file)) {
            Log.e(a, "Utils.modifyFilePermissionsDirFromTo resDir and parent's dir fail.".concat(String.valueOf(file5)));
            return false;
        }
        File file6 = (File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, file5, UCMPackageInfo.RES_PAKS_DIR_NAME);
        if (!j.b(file6)) {
            Log.e(a, "setReadable or setExecutable pakDir fail.".concat(String.valueOf(file6)));
            return false;
        }
        for (File file7 : file6.listFiles()) {
            if (!j.a(file7)) {
                Log.e(a, "setReadable pakDir fail.".concat(String.valueOf(file7)));
                return false;
            }
        }
        File file8 = new File(aVar.d);
        if (!j.b(file8, file)) {
            Log.e(a, "Utils.modifyFilePermissionsDirFromTo soDir and parent's dir fail.".concat(String.valueOf(file8)));
            return false;
        } else if (!j.a(file8)) {
            Log.e(a, "setReadable soDir fail.".concat(String.valueOf(file8)));
            return false;
        } else {
            for (String file9 : j.a(uCMPackageInfo.mSdkShellClassLoader)) {
                File file10 = new File(file8, file9);
                if (file10.exists()) {
                    if (!j.b(file10)) {
                        Log.e(a, "setExecutable(soFile) fail.".concat(String.valueOf(file10)));
                        return false;
                    } else if (!j.a(file10)) {
                        Log.e(a, "setReadable(soFile) fail.".concat(String.valueOf(file10)));
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static boolean a(File file, File file2, boolean z) {
        try {
            if (!file.getCanonicalPath().startsWith(file2.getCanonicalPath())) {
                return false;
            }
            while (file.getCanonicalPath().contains(file2.getCanonicalPath()) && !file.getCanonicalPath().equals(file2.getCanonicalPath())) {
                if (!a(file, z, true)) {
                    return false;
                }
                file = file.getParentFile();
            }
            return true;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static boolean a(File file, boolean z, boolean z2) {
        if (z && !file.canRead()) {
            return false;
        }
        if (!z2 || file.canExecute()) {
            return true;
        }
        return false;
    }

    public static boolean a(File file, File file2) {
        if (!a(file, file2, false)) {
            return false;
        }
        for (String file3 : b.values()) {
            if (!a(new File(file, file3), true, false)) {
                return false;
            }
        }
        return true;
    }

    public static boolean b(File file, File file2) {
        if (!a(file, file2, true)) {
            return false;
        }
        String[] strArr = {"libwebviewuc.so"};
        for (int i2 = 0; i2 <= 0; i2++) {
            if (!a(new File(file, strArr[0]), true, true)) {
                return false;
            }
        }
        return true;
    }

    public static boolean c(File file, File file2) {
        if (!a(file, file2, false)) {
            return false;
        }
        File file3 = (File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, file, UCMPackageInfo.RES_PAKS_DIR_NAME);
        if (!a(file3, false, true)) {
            return false;
        }
        String[] strArr = {"webviewuc.pak"};
        for (int i2 = 0; i2 <= 0; i2++) {
            if (!a(new File(file3, strArr[0]), true, false)) {
                return false;
            }
        }
        return true;
    }

    private static boolean a(List<com.uc.webview.export.internal.setup.UCMPackageInfo.a> list, File file) {
        if (!file.exists()) {
            return true;
        }
        List<com.uc.webview.export.internal.setup.UCMPackageInfo.a> a2 = com.uc.webview.export.internal.setup.UCMPackageInfo.a.a(file);
        if (a2.size() != list.size()) {
            return false;
        }
        for (int i2 = 0; i2 < a2.size(); i2++) {
            com.uc.webview.export.internal.setup.UCMPackageInfo.a aVar = a2.get(i2);
            com.uc.webview.export.internal.setup.UCMPackageInfo.a aVar2 = list.get(i2);
            if (!(a(aVar.a, aVar2.a) && a(aVar.b, aVar2.b) && a(aVar.c, aVar2.c) && a(aVar.d, aVar2.d) && a(aVar.f, aVar2.f) && a(aVar.g, aVar2.g) && a(aVar.e, aVar2.e))) {
                return true;
            }
        }
        return false;
    }

    private static boolean a(String str, String str2) {
        return (str == null && str2 == null) || (str != null && str.equals(str2));
    }

    public static void a(Context context) {
        int i2 = 1;
        File file = new File((File) UCMPackageInfo.invoke(10001, context), "config.json");
        if (file.exists()) {
            file.delete();
            Log.d(a, "Config File : config.json is deleted!");
        } else {
            i2 = 0;
        }
        WaStat.statAKV(new Pair("csc_tpk", new bb(i2)));
    }
}
