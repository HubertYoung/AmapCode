package com.uc.webview.export.internal.utility;

import android.content.Context;
import com.uc.webview.export.CDParamKeys;
import com.uc.webview.export.cyclone.UCCyclone;
import com.uc.webview.export.cyclone.UCCyclone.DecFileOrign;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IGlobalSettings;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;
import com.uc.webview.export.internal.setup.UCMPackageInfo;
import com.uc.webview.export.internal.utility.i.b;
import java.io.File;
import java.util.Stack;

/* compiled from: ProGuard */
public final class g {
    public static long a = 2;
    private static final String b = "g";
    private static long c = 1;
    private static long d = 4;
    private static long e = 8;
    private static long f = 16;
    private static String g = "com.eg.android.AlipayGphone";
    private static long h;
    private static long i;
    private static long j;

    /* compiled from: ProGuard */
    public static class a {
        public static long a = 1;
        public static long b = 2;
        public static long c = 4;
        public static long d = 8;
        public static long e = 16;
        public static long f = 32;
        public static long g = 64;
        public static long h = 128;
        public static long i = 256;
        public static long j = 512;
        public static long k = 1024;
        public static long l = 2048;
        public static long m = 4096;
        public static long n = 8192;
        public static long o = 16384;
        public static long p = 32768;
        public static long q = 65536;
        public static long r = 131072;
        public long s = 0;

        public final void a(long j2) {
            this.s = j2 | this.s;
        }
    }

    public static void a(String str) {
        if (!j.a(str)) {
            try {
                IGlobalSettings iGlobalSettings = (IGlobalSettings) SDKFactory.invoke(10022, new Object[0]);
                if (iGlobalSettings != null) {
                    iGlobalSettings.setStringValue("LoadShareCoreHost", str);
                }
            } catch (Throwable unused) {
            }
        }
    }

    public static boolean a(String str, String str2, a aVar) {
        String[] split;
        if (j.a(str) || j.a(str2)) {
            if (aVar != null) {
                aVar.a(a.c);
            }
            return false;
        }
        for (String str3 : str.split(CDParamKeys.CD_VALUE_STRING_SPLITER)) {
            if (str2.equals(str3) || str2.matches(str3)) {
                return true;
            }
        }
        if (aVar != null) {
            WaStat.stat((String) IWaStat.SHARE_CORE_SDK_VERSION_CHECK_FAILURE);
        }
        if (aVar != null) {
            aVar.a(a.d);
        }
        return false;
    }

    private static long d(String str) {
        long j2 = 0;
        if (j.a(str)) {
            return 0;
        }
        String[] split = str.split("\\.");
        for (int i2 = 0; i2 < split.length; i2++) {
            j2 += (long) Integer.parseInt(split[i2]);
            if (i2 < split.length - 1) {
                j2 *= 100;
            }
        }
        return j2;
    }

    private static int a(String str, String str2) {
        int i2 = (d(str) > d(str2) ? 1 : (d(str) == d(str2) ? 0 : -1));
        if (i2 > 0) {
            return 1;
        }
        return i2 == 0 ? 0 : -1;
    }

    public static String b(String str) {
        if (j.a(str)) {
            return str;
        }
        try {
            String replaceAll = str.replaceAll("Exception", "E");
            try {
                int indexOf = replaceAll.indexOf(":");
                int i2 = -1;
                if (indexOf >= 0) {
                    i2 = replaceAll.lastIndexOf(".", indexOf);
                }
                if (i2 >= 0) {
                    int min = Math.min(i2 + 30, replaceAll.length());
                    if (min > i2) {
                        str = replaceAll.substring(i2, min);
                        return str;
                    }
                }
            } catch (Throwable unused) {
            }
            str = replaceAll;
        } catch (Throwable unused2) {
        }
        return str;
    }

    public static long a() {
        long j2;
        long j3 = a;
        try {
            String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_COMMONALITY_TARGET_FPATH);
            if (j.a(param)) {
                j2 = d;
            } else if (SDKFactory.e.getPackageManager().checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", SDKFactory.e.getPackageName()) != 0) {
                j2 = e;
            } else {
                File file = new File(param, "uws");
                UCCyclone.expectCreateDirFile(file);
                if (file.exists()) {
                    return j3;
                }
                j2 = f;
            }
            return j2;
        } catch (Throwable th) {
            Log.d(b, ".sdcardAuthority", th);
            return j3;
        }
    }

    public static boolean b() {
        return a() == a;
    }

    public static String c() {
        return new File(UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_COMMONALITY_TARGET_FPATH), "uws").getAbsolutePath();
    }

    public static File a(File file, String str) {
        return (File) UCMPackageInfo.invoke(UCMPackageInfo.expectCreateDirFile2P, file, (String) UCMPackageInfo.invoke(10012, str));
    }

    public static File c(String str) {
        return a(new File(c()), str);
    }

    public static boolean d() {
        return g.equals(SDKFactory.e.getPackageName());
    }

    public static boolean a(File file) {
        return file.getAbsolutePath().contains("uws") && file.getAbsolutePath().contains((String) UCMPackageInfo.invoke(10012, g));
    }

    public static boolean a(File file, a aVar) {
        String absolutePath = file.getAbsolutePath();
        Context context = SDKFactory.e;
        return i.a(absolutePath, context, context, "com.UCMobile", new b("sc_cvsv"), aVar);
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00af A[SYNTHETIC, Splitter:B:39:0x00af] */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00b6 A[Catch:{ all -> 0x00aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00bd A[Catch:{ all -> 0x00aa }] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00cd A[SYNTHETIC, Splitter:B:46:0x00cd] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00d3 A[Catch:{ Throwable -> 0x00d7 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String b(java.io.File r8, com.uc.webview.export.internal.utility.g.a r9) {
        /*
            r0 = 0
            java.lang.String r1 = "sdk_shell"
            java.lang.String r8 = com.uc.webview.export.internal.utility.j.a(r8, r1)     // Catch:{ Throwable -> 0x00d7 }
            boolean r1 = com.uc.webview.export.internal.utility.j.a(r8)     // Catch:{ Throwable -> 0x00d7 }
            r2 = 0
            if (r1 == 0) goto L_0x0028
            if (r9 == 0) goto L_0x0015
            long r3 = com.uc.webview.export.internal.utility.g.a.e     // Catch:{ Throwable -> 0x00d7 }
            r9.a(r3)     // Catch:{ Throwable -> 0x00d7 }
        L_0x0015:
            com.uc.webview.export.cyclone.UCKnownException r1 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x00d7 }
            r3 = 8004(0x1f44, float:1.1216E-41)
            java.lang.String r4 = "[%s] no found after UCCyclone.decompress."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x00d7 }
            r5[r2] = r8     // Catch:{ Throwable -> 0x00d7 }
            java.lang.String r8 = java.lang.String.format(r4, r5)     // Catch:{ Throwable -> 0x00d7 }
            r1.<init>(r3, r8)     // Catch:{ Throwable -> 0x00d7 }
            throw r1     // Catch:{ Throwable -> 0x00d7 }
        L_0x0028:
            if (r9 == 0) goto L_0x0038
            java.lang.String r1 = "csc_vvfgscl"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)     // Catch:{ Throwable -> 0x0034, all -> 0x0030 }
            goto L_0x0038
        L_0x0030:
            r8 = move-exception
            r1 = r0
            goto L_0x00cb
        L_0x0034:
            r8 = move-exception
            r1 = r0
            goto L_0x00ad
        L_0x0038:
            r1 = r0
        L_0x0039:
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x00ac }
            android.content.Context r4 = com.uc.webview.export.internal.SDKFactory.e     // Catch:{ Throwable -> 0x00ac }
            java.io.File r4 = r4.getCacheDir()     // Catch:{ Throwable -> 0x00ac }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x00ac }
            java.lang.String r6 = "temp_dex_verify_"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x00ac }
            int r6 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x00ac }
            r5.append(r6)     // Catch:{ Throwable -> 0x00ac }
            java.lang.String r6 = "_"
            r5.append(r6)     // Catch:{ Throwable -> 0x00ac }
            int r6 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x00ac }
            r5.append(r6)     // Catch:{ Throwable -> 0x00ac }
            java.lang.String r6 = "_"
            r5.append(r6)     // Catch:{ Throwable -> 0x00ac }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x00ac }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x00ac }
            r5.append(r6)     // Catch:{ Throwable -> 0x00ac }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x00ac }
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x00ac }
            boolean r1 = r3.exists()     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            if (r1 != 0) goto L_0x00a2
            com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r3)     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            dalvik.system.DexClassLoader r1 = new dalvik.system.DexClassLoader     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            java.lang.String r4 = r3.getAbsolutePath()     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            java.lang.String r5 = ""
            java.lang.Class<com.uc.webview.export.internal.utility.g> r6 = com.uc.webview.export.internal.utility.g.class
            java.lang.ClassLoader r6 = r6.getClassLoader()     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            r1.<init>(r8, r4, r5, r6)     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            if (r9 == 0) goto L_0x0093
            java.lang.String r8 = "csc_vvfdscl"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r8)     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
        L_0x0093:
            if (r9 == 0) goto L_0x009a
            long r4 = com.uc.webview.export.internal.utility.g.a.r     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            r9.a(r4)     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
        L_0x009a:
            java.lang.String r8 = com.uc.webview.export.internal.utility.j.b(r1)     // Catch:{ Throwable -> 0x00a7, all -> 0x00a4 }
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r3, r2, r0)     // Catch:{ Throwable -> 0x00d7 }
            return r8
        L_0x00a2:
            r1 = r3
            goto L_0x0039
        L_0x00a4:
            r8 = move-exception
            r1 = r3
            goto L_0x00cb
        L_0x00a7:
            r8 = move-exception
            r1 = r3
            goto L_0x00ad
        L_0x00aa:
            r8 = move-exception
            goto L_0x00cb
        L_0x00ac:
            r8 = move-exception
        L_0x00ad:
            if (r9 == 0) goto L_0x00b4
            long r3 = com.uc.webview.export.internal.utility.g.a.h     // Catch:{ all -> 0x00aa }
            r9.a(r3)     // Catch:{ all -> 0x00aa }
        L_0x00b4:
            if (r9 == 0) goto L_0x00bb
            java.lang.String r3 = "csc_vvfexc"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3)     // Catch:{ all -> 0x00aa }
        L_0x00bb:
            if (r9 == 0) goto L_0x00d1
            java.lang.String r3 = "csc_vvfexc_v"
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x00aa }
            java.lang.String r8 = b(r8)     // Catch:{ all -> 0x00aa }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r3, r8)     // Catch:{ all -> 0x00aa }
            goto L_0x00d1
        L_0x00cb:
            if (r1 == 0) goto L_0x00d0
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r1, r2, r0)     // Catch:{ Throwable -> 0x00d7 }
        L_0x00d0:
            throw r8     // Catch:{ Throwable -> 0x00d7 }
        L_0x00d1:
            if (r1 == 0) goto L_0x00fc
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r1, r2, r0)     // Catch:{ Throwable -> 0x00d7 }
            goto L_0x00fc
        L_0x00d7:
            r8 = move-exception
            if (r9 == 0) goto L_0x00df
            long r1 = com.uc.webview.export.internal.utility.g.a.g
            r9.a(r1)
        L_0x00df:
            if (r9 == 0) goto L_0x00e6
            java.lang.String r1 = "csc_vvferr"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1)
        L_0x00e6:
            if (r9 == 0) goto L_0x00f5
            java.lang.String r9 = "csc_vvferr_v"
            java.lang.String r1 = r8.toString()
            java.lang.String r1 = b(r1)
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r9, r1)
        L_0x00f5:
            java.lang.String r9 = b
            java.lang.String r1 = ".getCoreCompressFileVersion"
            com.uc.webview.export.internal.utility.Log.d(r9, r1, r8)
        L_0x00fc:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.g.b(java.io.File, com.uc.webview.export.internal.utility.g$a):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x0111 A[Catch:{ all -> 0x0104 }] */
    /* JADX WARNING: Removed duplicated region for block: B:41:0x0118 A[Catch:{ all -> 0x0104 }] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x011f A[Catch:{ all -> 0x0104 }] */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x0134  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String a(java.io.File r10, java.lang.String r11, com.uc.webview.export.internal.utility.g.a r12) {
        /*
            java.lang.String r0 = b
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = ".getLegalVersionsFromCoreCompressFile "
            r1.<init>(r2)
            java.lang.String r2 = r10.getAbsolutePath()
            r1.append(r2)
            java.lang.String r2 = ", "
            r1.append(r2)
            r1.append(r11)
            java.lang.String r1 = r1.toString()
            com.uc.webview.export.internal.utility.Log.d(r0, r1)
            r0 = 0
            r1 = r0
        L_0x0021:
            r2 = 0
            java.io.File r3 = new java.io.File     // Catch:{ Throwable -> 0x0107 }
            android.content.Context r4 = com.uc.webview.export.internal.SDKFactory.e     // Catch:{ Throwable -> 0x0107 }
            java.io.File r4 = r4.getCacheDir()     // Catch:{ Throwable -> 0x0107 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0107 }
            java.lang.String r6 = "temp_dec_core_"
            r5.<init>(r6)     // Catch:{ Throwable -> 0x0107 }
            int r6 = android.os.Process.myPid()     // Catch:{ Throwable -> 0x0107 }
            r5.append(r6)     // Catch:{ Throwable -> 0x0107 }
            java.lang.String r6 = "_"
            r5.append(r6)     // Catch:{ Throwable -> 0x0107 }
            int r6 = android.os.Process.myTid()     // Catch:{ Throwable -> 0x0107 }
            r5.append(r6)     // Catch:{ Throwable -> 0x0107 }
            java.lang.String r6 = "_"
            r5.append(r6)     // Catch:{ Throwable -> 0x0107 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ Throwable -> 0x0107 }
            java.lang.String r6 = java.lang.String.valueOf(r6)     // Catch:{ Throwable -> 0x0107 }
            r5.append(r6)     // Catch:{ Throwable -> 0x0107 }
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0107 }
            r3.<init>(r4, r5)     // Catch:{ Throwable -> 0x0107 }
            boolean r1 = r3.exists()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            if (r1 != 0) goto L_0x00fc
            com.uc.webview.export.cyclone.UCCyclone.expectCreateDirFile(r3)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r1 = r10.getAbsolutePath()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            boolean r4 = com.uc.webview.export.cyclone.UCCyclone.detect7zFromFileName(r1)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            android.content.Context r5 = com.uc.webview.export.internal.SDKFactory.e     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r6 = r10.getAbsolutePath()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r7 = r3.getAbsolutePath()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r8 = "sdk_shell"
            com.uc.webview.export.internal.utility.h r9 = new com.uc.webview.export.internal.utility.h     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r9.<init>()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            com.uc.webview.export.cyclone.UCCyclone.decompress(r4, r5, r6, r7, r8, r9)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            if (r12 == 0) goto L_0x0087
            java.lang.String r10 = "csc_vvfdecs"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r10)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
        L_0x0087:
            if (r12 == 0) goto L_0x008e
            long r4 = com.uc.webview.export.internal.utility.g.a.q     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r12.a(r4)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
        L_0x008e:
            java.lang.String r10 = b(r3, r12)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            boolean r1 = com.uc.webview.export.internal.utility.j.a(r10)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            if (r1 == 0) goto L_0x00a1
            if (r12 == 0) goto L_0x009f
            long r10 = com.uc.webview.export.internal.utility.g.a.b     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r12.a(r10)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
        L_0x009f:
            r10 = r0
            goto L_0x00f8
        L_0x00a1:
            if (r12 == 0) goto L_0x00a8
            java.lang.String r1 = "csc_vval"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r1, r10)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
        L_0x00a8:
            java.lang.String r1 = b     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r5 = ".getLegalVersionsFromCoreDir ucmVersion: "
            r4.<init>(r5)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r4.append(r10)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r5 = " of "
            r4.append(r5)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r5 = r3.getAbsolutePath()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r4.append(r5)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            com.uc.webview.export.internal.utility.Log.d(r1, r4)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            boolean r11 = a(r11, r10, r12)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            if (r11 == 0) goto L_0x009f
            java.lang.String r11 = "sdk_shell"
            java.lang.String r11 = com.uc.webview.export.internal.utility.j.a(r3, r11)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.io.File r1 = new java.io.File     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r1.<init>(r11)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            boolean r1 = a(r1, r12)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            if (r1 != 0) goto L_0x00f8
            if (r12 == 0) goto L_0x00e5
            long r4 = com.uc.webview.export.internal.utility.g.a.f     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r12.a(r4)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
        L_0x00e5:
            com.uc.webview.export.cyclone.UCKnownException r10 = new com.uc.webview.export.cyclone.UCKnownException     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r1 = 8005(0x1f45, float:1.1217E-41)
            java.lang.String r4 = "[%s] verify failure."
            r5 = 1
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r5[r2] = r11     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            java.lang.String r11 = java.lang.String.format(r4, r5)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            r10.<init>(r1, r11)     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
            throw r10     // Catch:{ Throwable -> 0x0101, all -> 0x00ff }
        L_0x00f8:
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r3, r2, r0)
            return r10
        L_0x00fc:
            r1 = r3
            goto L_0x0021
        L_0x00ff:
            r10 = move-exception
            goto L_0x0132
        L_0x0101:
            r10 = move-exception
            r1 = r3
            goto L_0x0108
        L_0x0104:
            r10 = move-exception
            r3 = r1
            goto L_0x0132
        L_0x0107:
            r10 = move-exception
        L_0x0108:
            java.lang.String r11 = b     // Catch:{ all -> 0x0104 }
            java.lang.String r3 = ".getLegalVersionsFromCoreCompressFile"
            com.uc.webview.export.internal.utility.Log.d(r11, r3, r10)     // Catch:{ all -> 0x0104 }
            if (r12 == 0) goto L_0x0116
            long r3 = com.uc.webview.export.internal.utility.g.a.a     // Catch:{ all -> 0x0104 }
            r12.a(r3)     // Catch:{ all -> 0x0104 }
        L_0x0116:
            if (r12 == 0) goto L_0x011d
            java.lang.String r11 = "csc_vvfdece"
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11)     // Catch:{ all -> 0x0104 }
        L_0x011d:
            if (r12 == 0) goto L_0x012c
            java.lang.String r11 = "csc_vvfdece_v"
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x0104 }
            java.lang.String r10 = b(r10)     // Catch:{ all -> 0x0104 }
            com.uc.webview.export.internal.interfaces.IWaStat.WaStat.stat(r11, r10)     // Catch:{ all -> 0x0104 }
        L_0x012c:
            if (r1 == 0) goto L_0x0131
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r1, r2, r0)
        L_0x0131:
            return r0
        L_0x0132:
            if (r3 == 0) goto L_0x0137
            com.uc.webview.export.cyclone.UCCyclone.recursiveDelete(r3, r2, r0)
        L_0x0137:
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.utility.g.a(java.io.File, java.lang.String, com.uc.webview.export.internal.utility.g$a):java.lang.String");
    }

    public static String e() {
        String str;
        try {
            int i2 = 0;
            File[] listFiles = ((File) UCMPackageInfo.invoke(10003, SDKFactory.e)).listFiles();
            if (listFiles.length == 0) {
                return null;
            }
            int length = listFiles.length;
            while (true) {
                if (i2 >= length) {
                    str = null;
                    break;
                }
                File file = listFiles[i2];
                if (file.isDirectory()) {
                    str = j.a(file, (String) "sdk_shell");
                    if (!j.a(str)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(DecFileOrign.DecFileOrignFlag);
                        sb.append(DecFileOrign.Sdcard_Share_Core);
                        if (!j.a(j.a(file, sb.toString()))) {
                            break;
                        }
                    } else {
                        continue;
                    }
                }
                i2++;
            }
            if (j.a(str)) {
                return null;
            }
            return new File(str).getParent();
        } catch (Throwable th) {
            Log.d(b, ".getLocationDecDir ", th);
            return null;
        }
    }

    static {
        long j2 = c << 1;
        h = j2;
        long j3 = j2 << 1;
        i = j3;
        j = j3 << 1;
    }

    public static String f() {
        long j2;
        long j3 = h;
        a aVar = null;
        try {
            if (!b()) {
                Log.d(b, ".getSdcardShareCoreDecFilePath Sdcard配置及权限校验失败");
                long j4 = i;
                WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j4));
                Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j4)));
                return null;
            }
            String param = UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_SPECIAL_HOST_PKG_NAME_LIST);
            if (j.a(param)) {
                j2 = j;
                try {
                    Log.d(b, ".getSdcardShareCoreDecFilePath CDKeys.CD_KEY_SHARE_CORE_HOST_PKG_NAME_LIST配置为空");
                    WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j2));
                    Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j2)));
                    return null;
                } catch (Throwable th) {
                    th = th;
                    try {
                        Log.d(b, ".getSdcardShareCoreDecFilePath", th);
                        WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j2));
                        Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j2)));
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        Throwable th3 = th;
                        WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j2));
                        Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j2)));
                        throw th3;
                    }
                }
            } else {
                String[] split = param.split(CDParamKeys.CD_VALUE_STRING_SPLITER);
                Stack stack = new Stack();
                int length = split.length;
                String str = null;
                int i2 = 0;
                while (i2 < length) {
                    String str2 = split[i2];
                    if (!j.a(str2)) {
                        File c2 = c(str2);
                        if (!c2.exists()) {
                            String str3 = b;
                            StringBuilder sb = new StringBuilder(".getSdcardShareCoreDecFilePath ");
                            sb.append(c2.getAbsolutePath());
                            sb.append(" not exists.");
                            Log.d(str3, sb.toString());
                        } else {
                            File[] listFiles = c2.listFiles();
                            if (listFiles != null) {
                                if (listFiles.length != 0) {
                                    int length2 = listFiles.length;
                                    String str4 = str;
                                    int i3 = 0;
                                    while (i3 < length2) {
                                        File file = listFiles[i3];
                                        String str5 = b;
                                        StringBuilder sb2 = new StringBuilder(".getSdcardShareCoreDecFilePath coreFile: ");
                                        sb2.append(file.getAbsolutePath());
                                        Log.d(str5, sb2.toString());
                                        String a2 = a(file, UCCore.getParam(CDParamKeys.CD_KEY_SHARE_CORE_CLIENT_UCM_VERSIONS), aVar);
                                        if (j.a(a2)) {
                                            Log.d(b, ".getSdcardShareCoreDecFilePath version is empty.");
                                        } else {
                                            Log.d(b, ".getSdcardShareCoreDecFilePath version : ".concat(String.valueOf(a2)));
                                            if (a(str4, a2) < 0) {
                                                stack.push(file);
                                                str4 = a2;
                                            }
                                        }
                                        i3++;
                                        aVar = null;
                                    }
                                    str = str4;
                                }
                            }
                            String str6 = b;
                            StringBuilder sb3 = new StringBuilder(".getSdcardShareCoreDecFilePath ");
                            sb3.append(c2.getAbsolutePath());
                            sb3.append(" empty.");
                            Log.d(str6, sb3.toString());
                        }
                    }
                    i2++;
                    aVar = null;
                }
                while (!stack.empty()) {
                    File file2 = (File) stack.pop();
                    if (!UCCyclone.detect7zFromFileName(file2.getName()) && !a(file2)) {
                        String absolutePath = file2.getAbsolutePath();
                        Context context = SDKFactory.e;
                        if (!i.a(absolutePath, context, context, "com.UCMobile", new b("sc_cvsv"))) {
                            Log.d(b, ".getSdcardShareCoreDecFilePath verifySignature failure!");
                        }
                    }
                    String absolutePath2 = file2.getAbsolutePath();
                    WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j3));
                    Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j3)));
                    return absolutePath2;
                }
                WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j3));
                Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j3)));
                return null;
            }
        } catch (Throwable th4) {
            th = th4;
            j2 = j3;
            Throwable th32 = th;
            WaStat.stat(IWaStat.SHARE_CORE_GET_CORE_DEC_FILE_PATH, Long.toString(j2));
            Log.d(b, ".getSdcardShareCoreDecFilePath fStat: ".concat(String.valueOf(j2)));
            throw th32;
        }
    }

    public static void a(Context context) {
        File file = (File) UCMPackageInfo.invoke(10003, context);
        File file2 = new File(e());
        Log.d(b, ".deleteShareCoreDecompressDir decRootDir:".concat(String.valueOf(file)));
        Log.d(b, ".deleteShareCoreDecompressDir scDecDir:".concat(String.valueOf(file2)));
        File file3 = file2;
        int i2 = 5;
        while (true) {
            Log.d(b, ".deleteShareCoreDecompressDir scParentDir:".concat(String.valueOf(file2)));
            if (file2.getAbsolutePath().equals(file.getAbsolutePath())) {
                Log.d(b, ".deleteShareCoreDecompressDir delete share core decompress dir.");
                WaStat.stat((String) IWaStat.SHARE_CORE_DELETE_DEC_DIR);
                UCCyclone.recursiveDelete(file3, false, null);
                return;
            }
            File parentFile = file3.getParentFile();
            i2--;
            if (i2 > 0) {
                File file4 = file2;
                file2 = parentFile;
                file3 = file4;
            } else {
                return;
            }
        }
    }
}
