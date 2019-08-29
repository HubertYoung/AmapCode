package com.uc.webview.export.internal.setup;

import android.util.Pair;
import com.uc.webview.export.cyclone.UCHashMap;

/* compiled from: ProGuard */
public abstract class q extends UCSubSetupTask<q, q> {
    protected boolean a = false;
    protected boolean b = false;
    final int c = -1;

    protected static void a(String str, ClassLoader classLoader, String str2, String str3, String str4) {
        if (str != null) {
            try {
                if (str.length() > 0) {
                    String[] split = str.split(str4);
                    if (split.length > 0) {
                        String str5 = (String) Class.forName(str2, false, classLoader).getField(str3).get(null);
                        if (str5 != null && str5.length() > 0) {
                            int length = split.length;
                            int i = 0;
                            while (i < length) {
                                String trim = split[i].trim();
                                if (trim.length() <= 0 || (!str5.startsWith(trim) && !str5.matches(trim))) {
                                    i++;
                                } else {
                                    throw new UCSetupException(4013, String.format("UCM version [%s] is excluded by rules [%s].", new Object[]{str5, str}));
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new UCSetupException(4012, (Throwable) e);
            }
        }
    }

    protected static void b(String str, ClassLoader classLoader, String str2, String str3, String str4) {
        String str5 = "";
        if (str != null) {
            try {
                if (str.length() > 0) {
                    String[] split = str.split(str4);
                    if (split.length > 0) {
                        str5 = (String) Class.forName(str2, false, classLoader).getField(str3).get(null);
                        if (str5 != null && str5.length() > 0) {
                            int length = split.length;
                            int i = 0;
                            while (i < length) {
                                String trim = split[i].trim();
                                if (trim.length() <= 0 || (!str5.equals(trim) && !str5.matches(trim))) {
                                    i++;
                                } else {
                                    return;
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                throw new UCSetupException(4012, (Throwable) e);
            }
        }
        throw new UCSetupException(4029, String.format("UCM version [%s] not included by rules [%s].", new Object[]{str5, str}));
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0053, code lost:
        if (((java.lang.Integer) r2.get(null)).intValue() >= 19) goto L_0x0034;
     */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0059  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0095  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0097  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x009e  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x0116  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(com.uc.webview.export.internal.setup.UCMPackageInfo r20, android.content.Context r21, java.lang.ClassLoader r22, int r23) {
        /*
            r19 = this;
            r0 = r19
            r1 = r20
            r2 = r22
            r4 = 2
            r5 = 0
            r6 = 0
            r7 = 1
            java.lang.String r8 = "com.uc.webview.browser.shell.NativeLibraries"
            java.lang.Class r8 = java.lang.Class.forName(r8, r7, r2)     // Catch:{ Throwable -> 0x0023 }
            if (r8 == 0) goto L_0x0082
            java.lang.String r9 = "LIBRARIES"
            java.lang.reflect.Field r9 = r8.getDeclaredField(r9)     // Catch:{ Throwable -> 0x0024 }
            r9.setAccessible(r7)     // Catch:{ Throwable -> 0x0024 }
            java.lang.Object r9 = r9.get(r5)     // Catch:{ Throwable -> 0x0024 }
            java.lang.String[][] r9 = (java.lang.String[][]) r9     // Catch:{ Throwable -> 0x0024 }
            r5 = r9
            goto L_0x0082
        L_0x0023:
            r8 = r5
        L_0x0024:
            r9 = 10011(0x271b, float:1.4028E-41)
            java.lang.Object[] r10 = new java.lang.Object[r6]
            java.lang.Object r9 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r9, r10)
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto L_0x0036
        L_0x0034:
            r2 = 1
            goto L_0x0057
        L_0x0036:
            java.lang.String r8 = "com.uc.webview.browser.shell.Build$Version"
            java.lang.Class r8 = java.lang.Class.forName(r8, r7, r2)     // Catch:{ Throwable -> 0x0133 }
            if (r8 == 0) goto L_0x0056
            java.lang.String r2 = "BUILD_SERIAL"
            java.lang.reflect.Field r2 = r8.getDeclaredField(r2)     // Catch:{ Throwable -> 0x0133 }
            r2.setAccessible(r7)     // Catch:{ Throwable -> 0x0133 }
            java.lang.Object r2 = r2.get(r5)     // Catch:{ Throwable -> 0x0133 }
            java.lang.Integer r2 = (java.lang.Integer) r2     // Catch:{ Throwable -> 0x0133 }
            int r2 = r2.intValue()     // Catch:{ Throwable -> 0x0133 }
            r9 = 19
            if (r2 < r9) goto L_0x0056
            goto L_0x0034
        L_0x0056:
            r2 = 0
        L_0x0057:
            if (r2 == 0) goto L_0x0082
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            if (r8 != 0) goto L_0x0060
            r2 = 3018(0xbca, float:4.229E-42)
            goto L_0x0062
        L_0x0060:
            r2 = 3019(0xbcb, float:4.23E-42)
        L_0x0062:
            java.util.Locale r3 = java.util.Locale.CHINA
            java.lang.String r5 = "%s [%s] missing."
            java.lang.Object[] r4 = new java.lang.Object[r4]
            if (r8 != 0) goto L_0x006d
            java.lang.String r9 = "Class"
            goto L_0x006f
        L_0x006d:
            java.lang.String r9 = "Field"
        L_0x006f:
            r4[r6] = r9
            if (r8 != 0) goto L_0x0076
            java.lang.String r6 = "com.uc.webview.browser.shell.NativeLibraries"
            goto L_0x0078
        L_0x0076:
            java.lang.String r6 = "com.uc.webview.browser.shell.NativeLibraries.LIBRARIES"
        L_0x0078:
            r4[r7] = r6
            java.lang.String r3 = java.lang.String.format(r3, r5, r4)
            r1.<init>(r2, r3)
            throw r1
        L_0x0082:
            if (r5 == 0) goto L_0x0132
            int r2 = r5.length
            if (r2 <= 0) goto L_0x0132
            java.lang.String r2 = r1.soDirPath
            if (r2 != 0) goto L_0x0091
            android.content.pm.ApplicationInfo r2 = r21.getApplicationInfo()
            java.lang.String r2 = r2.nativeLibraryDir
        L_0x0091:
            r8 = r23 & 16
            if (r8 == 0) goto L_0x0097
            r8 = 1
            goto L_0x0098
        L_0x0097:
            r8 = 0
        L_0x0098:
            r9 = -2147483648(0xffffffff80000000, float:-0.0)
            r9 = r23 & r9
            if (r9 == 0) goto L_0x00a0
            r9 = 1
            goto L_0x00a1
        L_0x00a0:
            r9 = 0
        L_0x00a1:
            int r10 = r5.length
            r11 = 0
        L_0x00a3:
            if (r11 >= r10) goto L_0x0114
            r12 = r5[r11]
            r13 = r12[r6]
            r12 = r12[r7]
            long r14 = com.uc.webview.export.internal.utility.j.d(r12)
            java.io.File r12 = new java.io.File
            r12.<init>(r2, r13)
            long r16 = r12.length()
            int r13 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            if (r13 == 0) goto L_0x00fd
            java.lang.String r1 = "EnvTask"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            java.lang.String r3 = "组件校验 So Size Failed ["
            r2.<init>(r3)
            java.lang.String r3 = r12.getAbsolutePath()
            r2.append(r3)
            java.lang.String r3 = "]"
            r2.append(r3)
            java.lang.String r2 = r2.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r2)
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r2 = 1008(0x3f0, float:1.413E-42)
            java.util.Locale r3 = java.util.Locale.CHINA
            java.lang.String r5 = "So file [%s] with length [%d] mismatch to predefined [%d]."
            r8 = 3
            java.lang.Object[] r8 = new java.lang.Object[r8]
            r8[r6] = r12
            long r9 = r12.length()
            java.lang.Long r6 = java.lang.Long.valueOf(r9)
            r8[r7] = r6
            java.lang.Long r6 = java.lang.Long.valueOf(r14)
            r8[r4] = r6
            java.lang.String r3 = java.lang.String.format(r3, r5, r8)
            r1.<init>(r2, r3)
            throw r1
        L_0x00fd:
            int r13 = r0.c
            java.util.Locale r14 = java.util.Locale.CHINA
            java.lang.String r15 = "Check file size ok [%s]."
            java.lang.Object[] r4 = new java.lang.Object[r7]
            r4[r6] = r12
            java.lang.String r4 = java.lang.String.format(r14, r15, r4)
            java.lang.Throwable[] r12 = new java.lang.Throwable[r6]
            com.uc.webview.export.cyclone.UCLogger.print(r13, r4, r12)
            int r11 = r11 + 1
            r4 = 2
            goto L_0x00a3
        L_0x0114:
            if (r8 == 0) goto L_0x0132
            java.lang.String r2 = "verify_task"
            java.lang.Object r2 = r0.getOption(r2)
            com.uc.webview.export.internal.setup.da r2 = (com.uc.webview.export.internal.setup.da) r2
            if (r2 == 0) goto L_0x0132
            r2.setUCM(r1)
            r2.a = r5
            if (r9 == 0) goto L_0x012b
            r2.start()
            return
        L_0x012b:
            java.lang.Integer r1 = java.lang.Integer.valueOf(r23)
            r2.a(r1)
        L_0x0132:
            return
        L_0x0133:
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r2 = 3020(0xbcc, float:4.232E-42)
            java.lang.String r3 = "Version.BUILD_SERIAL not found."
            r1.<init>(r2, r3)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.q.a(com.uc.webview.export.internal.setup.UCMPackageInfo, android.content.Context, java.lang.ClassLoader, int):void");
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0030  */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x005b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(com.uc.webview.export.internal.setup.UCMPackageInfo r16, android.content.Context r17, java.lang.ClassLoader r18, int r19) {
        /*
            r15 = this;
            r0 = 2
            r1 = 0
            r2 = 0
            r3 = 1
            java.lang.String r4 = "com.uc.webview.browser.shell.PakAssets"
            r5 = r18
            java.lang.Class r4 = java.lang.Class.forName(r4, r3, r5)     // Catch:{ Throwable -> 0x001f }
            if (r4 == 0) goto L_0x0059
            java.lang.String r5 = "ASSETS"
            java.lang.reflect.Field r5 = r4.getDeclaredField(r5)     // Catch:{ Throwable -> 0x0020 }
            r5.setAccessible(r3)     // Catch:{ Throwable -> 0x0020 }
            java.lang.Object r5 = r5.get(r1)     // Catch:{ Throwable -> 0x0020 }
            java.lang.String[][] r5 = (java.lang.String[][]) r5     // Catch:{ Throwable -> 0x0020 }
            r1 = r5
            goto L_0x0059
        L_0x001f:
            r4 = r1
        L_0x0020:
            r5 = 10011(0x271b, float:1.4028E-41)
            java.lang.Object[] r6 = new java.lang.Object[r2]
            java.lang.Object r5 = com.uc.webview.export.internal.setup.UCMPackageInfo.invoke(r5, r6)
            java.lang.Boolean r5 = (java.lang.Boolean) r5
            boolean r5 = r5.booleanValue()
            if (r5 == 0) goto L_0x0059
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            if (r4 != 0) goto L_0x0037
            r5 = 3028(0xbd4, float:4.243E-42)
            goto L_0x0039
        L_0x0037:
            r5 = 3029(0xbd5, float:4.245E-42)
        L_0x0039:
            java.util.Locale r6 = java.util.Locale.CHINA
            java.lang.String r7 = "%s [%s] missing."
            java.lang.Object[] r0 = new java.lang.Object[r0]
            if (r4 != 0) goto L_0x0044
            java.lang.String r8 = "Class"
            goto L_0x0046
        L_0x0044:
            java.lang.String r8 = "Field"
        L_0x0046:
            r0[r2] = r8
            if (r4 != 0) goto L_0x004d
            java.lang.String r2 = "com.uc.webview.browser.shell.PakAssets"
            goto L_0x004f
        L_0x004d:
            java.lang.String r2 = "com.uc.webview.browser.shell.PakAssets.ASSETS"
        L_0x004f:
            r0[r3] = r2
            java.lang.String r0 = java.lang.String.format(r6, r7, r0)
            r1.<init>(r5, r0)
            throw r1
        L_0x0059:
            if (r1 == 0) goto L_0x00f7
            int r4 = r1.length
            if (r4 <= 0) goto L_0x00f7
            r4 = r16
            java.lang.String r4 = r4.resDirPath
            if (r4 != 0) goto L_0x0065
            return
        L_0x0065:
            java.io.File r5 = new java.io.File
            java.lang.String r6 = "paks"
            r5.<init>(r4, r6)
            java.lang.String r4 = r5.getAbsolutePath()
            r6 = r19 & 64
            if (r6 == 0) goto L_0x0076
            r6 = 1
            goto L_0x0077
        L_0x0076:
            r6 = 0
        L_0x0077:
            int r7 = r1.length
            r8 = 0
        L_0x0079:
            if (r8 >= r7) goto L_0x00ea
            r9 = r1[r8]
            r10 = r9[r2]
            r9 = r9[r3]
            long r11 = com.uc.webview.export.internal.utility.j.d(r9)
            java.io.File r9 = new java.io.File
            r9.<init>(r4, r10)
            long r13 = r9.length()
            int r10 = (r13 > r11 ? 1 : (r13 == r11 ? 0 : -1))
            if (r10 == 0) goto L_0x00d3
            java.lang.String r1 = "EnvTask"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            java.lang.String r5 = "组件校验 Pak Size Failed ["
            r4.<init>(r5)
            java.lang.String r5 = r9.getAbsolutePath()
            r4.append(r5)
            java.lang.String r5 = "]"
            r4.append(r5)
            java.lang.String r4 = r4.toString()
            com.uc.webview.export.internal.utility.Log.d(r1, r4)
            com.uc.webview.export.internal.setup.UCSetupException r1 = new com.uc.webview.export.internal.setup.UCSetupException
            r4 = 1014(0x3f6, float:1.421E-42)
            java.util.Locale r5 = java.util.Locale.CHINA
            java.lang.String r6 = "So file [%s] with length [%d] mismatch to predefined [%d]."
            r7 = 3
            java.lang.Object[] r7 = new java.lang.Object[r7]
            r7[r2] = r9
            long r8 = r9.length()
            java.lang.Long r2 = java.lang.Long.valueOf(r8)
            r7[r3] = r2
            java.lang.Long r2 = java.lang.Long.valueOf(r11)
            r7[r0] = r2
            java.lang.String r0 = java.lang.String.format(r5, r6, r7)
            r1.<init>(r4, r0)
            throw r1
        L_0x00d3:
            r10 = r15
            int r11 = r10.c
            java.util.Locale r12 = java.util.Locale.CHINA
            java.lang.String r13 = "Check file size ok [%s]."
            java.lang.Object[] r14 = new java.lang.Object[r3]
            r14[r2] = r9
            java.lang.String r9 = java.lang.String.format(r12, r13, r14)
            java.lang.Throwable[] r12 = new java.lang.Throwable[r2]
            com.uc.webview.export.cyclone.UCLogger.print(r11, r9, r12)
            int r8 = r8 + 1
            goto L_0x0079
        L_0x00ea:
            r10 = r15
            if (r6 == 0) goto L_0x00f8
            java.lang.Integer r0 = java.lang.Integer.valueOf(r19)
            r2 = r17
            com.uc.webview.export.internal.setup.da.a(r2, r4, r1, r0)
            goto L_0x00f8
        L_0x00f7:
            r10 = r15
        L_0x00f8:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.uc.webview.export.internal.setup.q.b(com.uc.webview.export.internal.setup.UCMPackageInfo, android.content.Context, java.lang.ClassLoader, int):void");
    }

    /* access modifiers changed from: protected */
    public final void a(String str, boolean z, Integer num, String str2, long j, long j2) {
        try {
            callbackStat(new Pair(str, new UCHashMap().set("cnt", "1").set("code", String.valueOf(num)).set("frun", z ? "T" : "F").set("data", str2).set("cost", String.valueOf(j)).set("cost_cpu", String.valueOf(j2))));
        } catch (Throwable unused) {
        }
    }
}
