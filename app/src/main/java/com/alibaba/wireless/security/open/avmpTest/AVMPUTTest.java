package com.alibaba.wireless.security.open.avmpTest;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Build.VERSION;
import com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent;
import com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent.IAVMPGenericInstance;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;

public class AVMPUTTest {
    public static final String TAG = "AVMPUTTest_T";
    private static IAVMPGenericComponent a;
    private static IAVMPGenericInstance b;

    /* JADX WARNING: Removed duplicated region for block: B:18:0x0053 A[Catch:{ SecException -> 0x00c7, Exception -> 0x0088, all -> 0x00c4, all -> 0x0141, all -> 0x0083 }] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0054 A[Catch:{ SecException -> 0x00c7, Exception -> 0x0088, all -> 0x00c4, all -> 0x0141, all -> 0x0083 }] */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0115 A[Catch:{ SecException -> 0x00c7, Exception -> 0x0088, all -> 0x00c4, all -> 0x0141, all -> 0x0083 }] */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x0116 A[Catch:{ SecException -> 0x00c7, Exception -> 0x0088, all -> 0x00c4, all -> 0x0141, all -> 0x0083 }] */
    /* JADX WARNING: Unknown top exception splitter block from list: {B:15:0x0045=Splitter:B:15:0x0045, B:51:0x0107=Splitter:B:51:0x0107} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static synchronized void a(android.content.Context r21) {
        /*
            java.lang.Class<com.alibaba.wireless.security.open.avmpTest.AVMPUTTest> r1 = com.alibaba.wireless.security.open.avmpTest.AVMPUTTest.class
            monitor-enter(r1)
            java.lang.String r8 = ""
            int r2 = isForeground(r21)     // Catch:{ all -> 0x0141 }
            long r3 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0141 }
            r5 = 1
            r6 = 0
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r7 = b     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            if (r7 == 0) goto L_0x0019
            java.lang.String r7 = "AVMP instance has been initialized"
            a(r7)     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            goto L_0x0044
        L_0x0019:
            com.alibaba.wireless.security.open.SecurityGuardManager r7 = com.alibaba.wireless.security.open.SecurityGuardManager.getInstance(r21)     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            java.lang.Class<com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent> r9 = com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent.class
            java.lang.Object r7 = r7.getInterface(r9)     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent r7 = (com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent) r7     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            a = r7     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            if (r7 == 0) goto L_0x0044
            com.alibaba.wireless.security.open.SecurityGuardManager r7 = com.alibaba.wireless.security.open.SecurityGuardManager.getInstance(r21)     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            java.lang.Class<com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent> r9 = com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent.class
            java.lang.Object r7 = r7.getInterface(r9)     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent r7 = (com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent) r7     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            a = r7     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            java.lang.String r9 = "mwua"
            java.lang.String r10 = "sgcipher"
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r7 = r7.createAVMPInstance(r9, r10)     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            b = r7     // Catch:{ SecException -> 0x00c7, Exception -> 0x0088 }
            r7 = 1
            goto L_0x0045
        L_0x0044:
            r7 = 0
        L_0x0045:
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0141 }
            r11 = 0
            long r9 = r9 - r3
            int r3 = isForeground(r21)     // Catch:{ all -> 0x0141 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r4 = b     // Catch:{ all -> 0x0141 }
            if (r4 == 0) goto L_0x0054
            goto L_0x0055
        L_0x0054:
            r5 = 0
        L_0x0055:
            java.lang.String r4 = "100086"
            r6 = 0
            r11 = 1
            java.lang.String r12 = ""
            java.lang.String r13 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x0141 }
            java.lang.String r14 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0141 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0141 }
            r5.<init>()     // Catch:{ all -> 0x0141 }
            r5.append(r2)     // Catch:{ all -> 0x0141 }
            r5.append(r3)     // Catch:{ all -> 0x0141 }
            java.lang.String r15 = r5.toString()     // Catch:{ all -> 0x0141 }
            java.lang.String r16 = ""
            r2 = r4
            r3 = r6
            r4 = r11
            r5 = r12
            r6 = r9
            r9 = r13
            r10 = r14
            r11 = r15
            r12 = r16
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r2, r3, r4, r5, r6, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0141 }
            monitor-exit(r1)
            return
        L_0x0083:
            r0 = move-exception
            r13 = r0
            r9 = 0
            goto L_0x0107
        L_0x0088:
            r0 = move-exception
            r7 = r0
            r9 = 1999(0x7cf, float:2.801E-42)
            java.lang.String r16 = r7.getMessage()     // Catch:{ all -> 0x00c4 }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0141 }
            r9 = 0
            long r14 = r7 - r3
            int r3 = isForeground(r21)     // Catch:{ all -> 0x0141 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r4 = b     // Catch:{ all -> 0x0141 }
            if (r4 == 0) goto L_0x00a0
            goto L_0x00a1
        L_0x00a0:
            r5 = 0
        L_0x00a1:
            java.lang.String r10 = "100086"
            r11 = 1999(0x7cf, float:2.801E-42)
            r12 = 1
            java.lang.String r13 = ""
            java.lang.String r17 = "false"
            java.lang.String r18 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0141 }
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x0141 }
            r4.<init>()     // Catch:{ all -> 0x0141 }
            r4.append(r2)     // Catch:{ all -> 0x0141 }
            r4.append(r3)     // Catch:{ all -> 0x0141 }
            java.lang.String r19 = r4.toString()     // Catch:{ all -> 0x0141 }
            java.lang.String r20 = ""
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r10, r11, r12, r13, r14, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x0141 }
            monitor-exit(r1)
            return
        L_0x00c4:
            r0 = move-exception
            r13 = r0
            goto L_0x0107
        L_0x00c7:
            r0 = move-exception
            r7 = r0
            int r7 = r7.getErrorCode()     // Catch:{ all -> 0x0083 }
            long r9 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0141 }
            r11 = 0
            long r9 = r9 - r3
            int r3 = isForeground(r21)     // Catch:{ all -> 0x0141 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r4 = b     // Catch:{ all -> 0x0141 }
            if (r4 == 0) goto L_0x00dc
            goto L_0x00dd
        L_0x00dc:
            r5 = 0
        L_0x00dd:
            java.lang.String r4 = "100086"
            r6 = 1
            java.lang.String r11 = ""
            java.lang.String r12 = "false"
            java.lang.String r13 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0141 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0141 }
            r5.<init>()     // Catch:{ all -> 0x0141 }
            r5.append(r2)     // Catch:{ all -> 0x0141 }
            r5.append(r3)     // Catch:{ all -> 0x0141 }
            java.lang.String r14 = r5.toString()     // Catch:{ all -> 0x0141 }
            java.lang.String r15 = ""
            r2 = r4
            r3 = r7
            r4 = r6
            r5 = r11
            r6 = r9
            r9 = r12
            r10 = r13
            r11 = r14
            r12 = r15
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r2, r3, r4, r5, r6, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0141 }
            monitor-exit(r1)
            return
        L_0x0107:
            long r10 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0141 }
            r7 = 0
            long r10 = r10 - r3
            int r3 = isForeground(r21)     // Catch:{ all -> 0x0141 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r4 = b     // Catch:{ all -> 0x0141 }
            if (r4 == 0) goto L_0x0116
            goto L_0x0117
        L_0x0116:
            r5 = 0
        L_0x0117:
            java.lang.String r4 = "100086"
            r6 = 1
            java.lang.String r7 = ""
            java.lang.String r12 = "false"
            java.lang.String r14 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x0141 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0141 }
            r5.<init>()     // Catch:{ all -> 0x0141 }
            r5.append(r2)     // Catch:{ all -> 0x0141 }
            r5.append(r3)     // Catch:{ all -> 0x0141 }
            java.lang.String r15 = r5.toString()     // Catch:{ all -> 0x0141 }
            java.lang.String r16 = ""
            r2 = r4
            r3 = r9
            r4 = r6
            r5 = r7
            r6 = r10
            r9 = r12
            r10 = r14
            r11 = r15
            r12 = r16
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r2, r3, r4, r5, r6, r8, r9, r10, r11, r12)     // Catch:{ all -> 0x0141 }
            throw r13     // Catch:{ all -> 0x0141 }
        L_0x0141:
            r0 = move-exception
            r2 = r0
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.open.avmpTest.AVMPUTTest.a(android.content.Context):void");
    }

    private static void a(String str) {
    }

    public static byte[] avmpSign(IAVMPGenericInstance iAVMPGenericInstance, byte[] bArr, String str, int i) throws Exception {
        if (iAVMPGenericInstance == null) {
            return null;
        }
        return (byte[]) iAVMPGenericInstance.invokeAVMP("sign", new byte[0].getClass(), Integer.valueOf(i), str.getBytes(), Integer.valueOf(str.getBytes().length), null, bArr, Integer.valueOf(0));
    }

    public static byte[] avmpSign2(IAVMPGenericInstance iAVMPGenericInstance, String str, String str2) throws Exception {
        if (iAVMPGenericInstance == null) {
            return null;
        }
        return (byte[]) iAVMPGenericInstance.invokeAVMP2("sign_v2", new byte[0].getClass(), str2, str, Integer.valueOf(0));
    }

    public static void avmpTest(Context context, String str) {
        a((String) "enter avmpTest");
        Context applicationContext = context.getApplicationContext();
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("|startLVMTrack");
        runAVMPSignSchedule(applicationContext, sb.toString());
    }

    public static int isForeground(Context context) {
        int i;
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(WidgetType.ACTIVITY);
            if (VERSION.SDK_INT < 21) {
                return activityManager.getRunningTasks(1).get(0).topActivity.getPackageName().equals(context.getPackageName()) ? 1 : 0;
            }
            i = 0;
            for (RunningAppProcessInfo next : activityManager.getRunningAppProcesses()) {
                try {
                    if (next.importance == 100) {
                        String[] strArr = next.pkgList;
                        int length = strArr.length;
                        int i2 = i;
                        int i3 = 0;
                        while (i3 < length) {
                            try {
                                if (strArr[i3].equals(context.getPackageName())) {
                                    i2 = 1;
                                }
                                i3++;
                            } catch (Exception unused) {
                                return i2;
                            }
                        }
                        i = i2;
                    }
                } catch (Exception unused2) {
                }
            }
            return i;
        } catch (Exception unused3) {
            i = 0;
        }
    }

    public static void mySleep(long j) {
        try {
            Thread.sleep(j);
        } catch (Exception unused) {
        }
    }

    public static void runAVMPSignSchedule(Context context, String str) {
        for (int i = 0; i < 15; i++) {
            a((String) "enter runAVMPSignSchedule");
            runAVMPSignSchedule1(context, str, "ib00000010b4732dc6645e87a20900b653a94ef27d72696f99", "ib0001002026f1091f2042df0cae1af323ac6e80a661d4a169");
        }
    }

    /* JADX WARNING: Unknown top exception splitter block from list: {B:112:0x0460=Splitter:B:112:0x0460, B:44:0x0171=Splitter:B:44:0x0171, B:101:0x03d2=Splitter:B:101:0x03d2, B:56:0x0214=Splitter:B:56:0x0214, B:29:0x00d5=Splitter:B:29:0x00d5, B:90:0x0346=Splitter:B:90:0x0346, B:94:0x03b9=Splitter:B:94:0x03b9, B:14:0x003b=Splitter:B:14:0x003b, B:73:0x02ab=Splitter:B:73:0x02ab} */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized void runAVMPSignSchedule1(android.content.Context r28, java.lang.String r29, java.lang.String r30, java.lang.String r31) {
        /*
            java.lang.Class<com.alibaba.wireless.security.open.avmpTest.AVMPUTTest> r1 = com.alibaba.wireless.security.open.avmpTest.AVMPUTTest.class
            monitor-enter(r1)
            a(r28)     // Catch:{ all -> 0x04d2 }
            r2 = 3000(0xbb8, double:1.482E-320)
            mySleep(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r4 = ""
            int r5 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r8 = 4
            r10 = 0
            r12 = 0
            byte[] r8 = new byte[r8]     // Catch:{ SecException -> 0x0152, Exception -> 0x00b6, all -> 0x00b2 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r13 = b     // Catch:{ SecException -> 0x0152, Exception -> 0x00b6, all -> 0x00b2 }
            java.lang.String r14 = "ib00000010b4732dc6645e87a20900b653a94ef27d72696f99"
            byte[] r8 = avmpSign(r13, r8, r14, r12)     // Catch:{ SecException -> 0x0152, Exception -> 0x00b6, all -> 0x00b2 }
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r15 = 0
            long r6 = r13 - r6
            int r13 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r14 = new java.lang.String     // Catch:{ Exception -> 0x0039 }
            r14.<init>(r8)     // Catch:{ Exception -> 0x0039 }
            int r8 = r14.length()     // Catch:{ Exception -> 0x0039 }
            r14 = r8
            r8 = 1
            goto L_0x003b
        L_0x0039:
            r8 = 0
            r14 = 0
        L_0x003b:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = "enter test1: isForeground1="
            r15.<init>(r9)     // Catch:{ all -> 0x04d2 }
            r15.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " isForeground11="
            r15.append(r9)     // Catch:{ all -> 0x04d2 }
            r15.append(r13)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " msg"
            r15.append(r9)     // Catch:{ all -> 0x04d2 }
            r15.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " callRes1"
            r15.append(r9)     // Catch:{ all -> 0x04d2 }
            r15.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " signLength="
            r15.append(r9)     // Catch:{ all -> 0x04d2 }
            r15.append(r14)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " duration="
            r15.append(r9)     // Catch:{ all -> 0x04d2 }
            r15.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = r15.toString()     // Catch:{ all -> 0x04d2 }
            a(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r16 = "100087"
            r17 = 0
            r18 = 1
            java.lang.String r19 = ""
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r9.<init>()     // Catch:{ all -> 0x04d2 }
            r9.append(r5)     // Catch:{ all -> 0x04d2 }
            r9.append(r13)     // Catch:{ all -> 0x04d2 }
            java.lang.String r5 = "&"
            r9.append(r5)     // Catch:{ all -> 0x04d2 }
            r9.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r22 = r9.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r23 = "1"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r4.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r5 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x04d2 }
            r4.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r24 = r4.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r25 = java.lang.String.valueOf(r14)     // Catch:{ all -> 0x04d2 }
            java.lang.String r26 = ""
            r20 = r6
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r16, r17, r18, r19, r20, r22, r23, r24, r25, r26)     // Catch:{ all -> 0x04d2 }
            goto L_0x01e4
        L_0x00b2:
            r0 = move-exception
            r2 = r0
            goto L_0x0447
        L_0x00b6:
            r0 = move-exception
            r8 = r0
            java.lang.String r8 = r8.getMessage()     // Catch:{ all -> 0x014c }
            long r13 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r4 = 0
            long r6 = r13 - r6
            int r4 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = new java.lang.String     // Catch:{ Exception -> 0x00d3 }
            r9.<init>(r10)     // Catch:{ Exception -> 0x00d3 }
            int r9 = r9.length()     // Catch:{ Exception -> 0x00d3 }
            r13 = r9
            r9 = 1
            goto L_0x00d5
        L_0x00d3:
            r9 = 0
            r13 = 0
        L_0x00d5:
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = "enter test1: isForeground1="
            r14.<init>(r15)     // Catch:{ all -> 0x04d2 }
            r14.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = " isForeground11="
            r14.append(r15)     // Catch:{ all -> 0x04d2 }
            r14.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = " msg"
            r14.append(r15)     // Catch:{ all -> 0x04d2 }
            r14.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = " callRes1"
            r14.append(r15)     // Catch:{ all -> 0x04d2 }
            r14.append(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = " signLength="
            r14.append(r15)     // Catch:{ all -> 0x04d2 }
            r14.append(r13)     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = " duration="
            r14.append(r15)     // Catch:{ all -> 0x04d2 }
            r14.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r14 = r14.toString()     // Catch:{ all -> 0x04d2 }
            a(r14)     // Catch:{ all -> 0x04d2 }
            java.lang.String r15 = "100087"
            r16 = 1999(0x7cf, float:2.801E-42)
            r17 = 1
            java.lang.String r18 = ""
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r14.<init>()     // Catch:{ all -> 0x04d2 }
            r14.append(r5)     // Catch:{ all -> 0x04d2 }
            r14.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r4 = "&"
            r14.append(r4)     // Catch:{ all -> 0x04d2 }
            r14.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r21 = r14.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r22 = "1"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r4.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r5 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x04d2 }
            r4.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r23 = r4.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r24 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x04d2 }
            java.lang.String r25 = ""
            r19 = r6
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r15, r16, r17, r18, r19, r21, r22, r23, r24, r25)     // Catch:{ all -> 0x04d2 }
            goto L_0x01e4
        L_0x014c:
            r0 = move-exception
            r2 = r0
            r12 = 1999(0x7cf, float:2.801E-42)
            goto L_0x0447
        L_0x0152:
            r0 = move-exception
            r8 = r0
            int r14 = r8.getErrorCode()     // Catch:{ all -> 0x0444 }
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r13 = 0
            long r6 = r8 - r6
            int r8 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = new java.lang.String     // Catch:{ Exception -> 0x016f }
            r9.<init>(r10)     // Catch:{ Exception -> 0x016f }
            int r9 = r9.length()     // Catch:{ Exception -> 0x016f }
            r13 = r9
            r9 = 1
            goto L_0x0171
        L_0x016f:
            r9 = 0
            r13 = 0
        L_0x0171:
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "enter test1: isForeground1="
            r15.<init>(r11)     // Catch:{ all -> 0x04d2 }
            r15.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " isForeground11="
            r15.append(r11)     // Catch:{ all -> 0x04d2 }
            r15.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " msg"
            r15.append(r11)     // Catch:{ all -> 0x04d2 }
            r15.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " callRes1"
            r15.append(r11)     // Catch:{ all -> 0x04d2 }
            r15.append(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " signLength="
            r15.append(r11)     // Catch:{ all -> 0x04d2 }
            r15.append(r13)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " duration="
            r15.append(r11)     // Catch:{ all -> 0x04d2 }
            r15.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = r15.toString()     // Catch:{ all -> 0x04d2 }
            a(r11)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "100087"
            r15 = 1
            java.lang.String r16 = ""
            java.lang.StringBuilder r12 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r12.<init>()     // Catch:{ all -> 0x04d2 }
            r12.append(r5)     // Catch:{ all -> 0x04d2 }
            r12.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r5 = "&"
            r12.append(r5)     // Catch:{ all -> 0x04d2 }
            r12.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r19 = r12.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r20 = "1"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r4.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r5 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x04d2 }
            r4.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r21 = r4.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r22 = java.lang.String.valueOf(r13)     // Catch:{ all -> 0x04d2 }
            java.lang.String r23 = ""
            r13 = r11
            r17 = r6
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r13, r14, r15, r16, r17, r19, r20, r21, r22, r23)     // Catch:{ all -> 0x04d2 }
        L_0x01e4:
            mySleep(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r2 = ""
            int r3 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            long r4 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            com.alibaba.wireless.security.open.avmp.IAVMPGenericComponent$IAVMPGenericInstance r6 = b     // Catch:{ SecException -> 0x0327, Exception -> 0x028d }
            java.lang.String r7 = "ib00000010b4732dc6645e87a20900b653a94ef27d72696f99"
            java.lang.String r8 = "vs_config_0"
            byte[] r6 = avmpSign2(r6, r7, r8)     // Catch:{ SecException -> 0x0327, Exception -> 0x028d }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r9 = 0
            long r14 = r7 - r4
            int r4 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r5 = new java.lang.String     // Catch:{ Exception -> 0x0212 }
            r5.<init>(r6)     // Catch:{ Exception -> 0x0212 }
            int r12 = r5.length()     // Catch:{ Exception -> 0x0212 }
            r5 = 1
            goto L_0x0214
        L_0x0212:
            r5 = 0
            r12 = 0
        L_0x0214:
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = "enter test2: isForeground1="
            r6.<init>(r7)     // Catch:{ all -> 0x04d2 }
            r6.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = " isForeground11="
            r6.append(r7)     // Catch:{ all -> 0x04d2 }
            r6.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = " msg"
            r6.append(r7)     // Catch:{ all -> 0x04d2 }
            r6.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = " callRes1"
            r6.append(r7)     // Catch:{ all -> 0x04d2 }
            r6.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = " signLength="
            r6.append(r7)     // Catch:{ all -> 0x04d2 }
            r6.append(r12)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = " dration="
            r6.append(r7)     // Catch:{ all -> 0x04d2 }
            r6.append(r14)     // Catch:{ all -> 0x04d2 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x04d2 }
            a(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = "100087"
            r11 = 0
            r6 = 1
            java.lang.String r13 = ""
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r7.<init>()     // Catch:{ all -> 0x04d2 }
            r7.append(r3)     // Catch:{ all -> 0x04d2 }
            r7.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = "&"
            r7.append(r3)     // Catch:{ all -> 0x04d2 }
            r7.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r16 = r7.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r17 = "3"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r2.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = java.lang.String.valueOf(r5)     // Catch:{ all -> 0x04d2 }
            r2.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r18 = r2.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r19 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x04d2 }
            java.lang.String r20 = ""
            r12 = r6
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r10, r11, r12, r13, r14, r16, r17, r18, r19, r20)     // Catch:{ all -> 0x04d2 }
            monitor-exit(r1)
            return
        L_0x0288:
            r0 = move-exception
            r6 = r0
            r12 = 0
            goto L_0x03b9
        L_0x028d:
            r0 = move-exception
            r6 = r0
            java.lang.String r6 = r6.getMessage()     // Catch:{ all -> 0x0321 }
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r2 = 0
            long r4 = r7 - r4
            int r2 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = new java.lang.String     // Catch:{ Exception -> 0x02a9 }
            r7.<init>(r10)     // Catch:{ Exception -> 0x02a9 }
            int r12 = r7.length()     // Catch:{ Exception -> 0x02a9 }
            r7 = 1
            goto L_0x02ab
        L_0x02a9:
            r7 = 0
            r12 = 0
        L_0x02ab:
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = "enter test2: isForeground1="
            r8.<init>(r9)     // Catch:{ all -> 0x04d2 }
            r8.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " isForeground11="
            r8.append(r9)     // Catch:{ all -> 0x04d2 }
            r8.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " msg"
            r8.append(r9)     // Catch:{ all -> 0x04d2 }
            r8.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " callRes1"
            r8.append(r9)     // Catch:{ all -> 0x04d2 }
            r8.append(r7)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " signLength="
            r8.append(r9)     // Catch:{ all -> 0x04d2 }
            r8.append(r12)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = " dration="
            r8.append(r9)     // Catch:{ all -> 0x04d2 }
            r8.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r8 = r8.toString()     // Catch:{ all -> 0x04d2 }
            a(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "100087"
            r8 = 1999(0x7cf, float:2.801E-42)
            r13 = 1
            java.lang.String r14 = ""
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r9.<init>()     // Catch:{ all -> 0x04d2 }
            r9.append(r3)     // Catch:{ all -> 0x04d2 }
            r9.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r2 = "&"
            r9.append(r2)     // Catch:{ all -> 0x04d2 }
            r9.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r17 = r9.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r18 = "3"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r2.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x04d2 }
            r2.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r19 = r2.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r20 = java.lang.String.valueOf(r12)     // Catch:{ all -> 0x04d2 }
            java.lang.String r21 = ""
            r12 = r8
            r15 = r4
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r11, r12, r13, r14, r15, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x04d2 }
            monitor-exit(r1)
            return
        L_0x0321:
            r0 = move-exception
            r6 = r0
            r12 = 1999(0x7cf, float:2.801E-42)
            goto L_0x03b9
        L_0x0327:
            r0 = move-exception
            r6 = r0
            int r12 = r6.getErrorCode()     // Catch:{ all -> 0x0288 }
            long r6 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r8 = 0
            long r4 = r6 - r4
            int r6 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r7 = new java.lang.String     // Catch:{ Exception -> 0x0344 }
            r7.<init>(r10)     // Catch:{ Exception -> 0x0344 }
            int r7 = r7.length()     // Catch:{ Exception -> 0x0344 }
            r8 = r7
            r7 = 1
            goto L_0x0346
        L_0x0344:
            r7 = 0
            r8 = 0
        L_0x0346:
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = "enter test2: isForeground1="
            r9.<init>(r10)     // Catch:{ all -> 0x04d2 }
            r9.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = " isForeground11="
            r9.append(r10)     // Catch:{ all -> 0x04d2 }
            r9.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = " msg"
            r9.append(r10)     // Catch:{ all -> 0x04d2 }
            r9.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = " callRes1"
            r9.append(r10)     // Catch:{ all -> 0x04d2 }
            r9.append(r7)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = " signLength="
            r9.append(r10)     // Catch:{ all -> 0x04d2 }
            r9.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = " dration="
            r9.append(r10)     // Catch:{ all -> 0x04d2 }
            r9.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r9 = r9.toString()     // Catch:{ all -> 0x04d2 }
            a(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "100087"
            r13 = 1
            java.lang.String r14 = ""
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r9.<init>()     // Catch:{ all -> 0x04d2 }
            r9.append(r3)     // Catch:{ all -> 0x04d2 }
            r9.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = "&"
            r9.append(r3)     // Catch:{ all -> 0x04d2 }
            r9.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r17 = r9.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r18 = "3"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r2.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = java.lang.String.valueOf(r7)     // Catch:{ all -> 0x04d2 }
            r2.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r19 = r2.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r20 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r21 = ""
            r15 = r4
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r11, r12, r13, r14, r15, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x04d2 }
            monitor-exit(r1)
            return
        L_0x03b9:
            long r7 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r9 = 0
            long r4 = r7 - r4
            int r7 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x03d0 }
            r8.<init>(r10)     // Catch:{ Exception -> 0x03d0 }
            int r8 = r8.length()     // Catch:{ Exception -> 0x03d0 }
            r9 = r8
            r8 = 1
            goto L_0x03d2
        L_0x03d0:
            r8 = 0
            r9 = 0
        L_0x03d2:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "enter test2: isForeground1="
            r10.<init>(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " isForeground11="
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r7)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " msg"
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " callRes1"
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " signLength="
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " dration="
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x04d2 }
            a(r10)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "100087"
            r13 = 1
            java.lang.String r14 = ""
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r10.<init>()     // Catch:{ all -> 0x04d2 }
            r10.append(r3)     // Catch:{ all -> 0x04d2 }
            r10.append(r7)     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = "&"
            r10.append(r3)     // Catch:{ all -> 0x04d2 }
            r10.append(r2)     // Catch:{ all -> 0x04d2 }
            java.lang.String r17 = r10.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r18 = "3"
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r2.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x04d2 }
            r2.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r19 = r2.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r20 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r21 = ""
            r15 = r4
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r11, r12, r13, r14, r15, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x04d2 }
            throw r6     // Catch:{ all -> 0x04d2 }
        L_0x0444:
            r0 = move-exception
            r2 = r0
            r12 = 0
        L_0x0447:
            long r8 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x04d2 }
            r3 = 0
            long r6 = r8 - r6
            int r3 = isForeground(r28)     // Catch:{ all -> 0x04d2 }
            java.lang.String r8 = new java.lang.String     // Catch:{ Exception -> 0x045e }
            r8.<init>(r10)     // Catch:{ Exception -> 0x045e }
            int r8 = r8.length()     // Catch:{ Exception -> 0x045e }
            r9 = r8
            r8 = 1
            goto L_0x0460
        L_0x045e:
            r8 = 0
            r9 = 0
        L_0x0460:
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "enter test1: isForeground1="
            r10.<init>(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r5)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " isForeground11="
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " msg"
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " callRes1"
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r8)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " signLength="
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = " duration="
            r10.append(r11)     // Catch:{ all -> 0x04d2 }
            r10.append(r6)     // Catch:{ all -> 0x04d2 }
            java.lang.String r10 = r10.toString()     // Catch:{ all -> 0x04d2 }
            a(r10)     // Catch:{ all -> 0x04d2 }
            java.lang.String r11 = "100087"
            r13 = 1
            java.lang.String r14 = ""
            java.lang.StringBuilder r10 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r10.<init>()     // Catch:{ all -> 0x04d2 }
            r10.append(r5)     // Catch:{ all -> 0x04d2 }
            r10.append(r3)     // Catch:{ all -> 0x04d2 }
            java.lang.String r3 = "&"
            r10.append(r3)     // Catch:{ all -> 0x04d2 }
            r10.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r17 = r10.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r18 = "1"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x04d2 }
            r3.<init>()     // Catch:{ all -> 0x04d2 }
            java.lang.String r4 = java.lang.String.valueOf(r8)     // Catch:{ all -> 0x04d2 }
            r3.append(r4)     // Catch:{ all -> 0x04d2 }
            java.lang.String r19 = r3.toString()     // Catch:{ all -> 0x04d2 }
            java.lang.String r20 = java.lang.String.valueOf(r9)     // Catch:{ all -> 0x04d2 }
            java.lang.String r21 = ""
            r15 = r6
            com.alibaba.wireless.security.framework.utils.UserTrackMethodJniBridge.addUtRecord(r11, r12, r13, r14, r15, r17, r18, r19, r20, r21)     // Catch:{ all -> 0x04d2 }
            throw r2     // Catch:{ all -> 0x04d2 }
        L_0x04d2:
            r0 = move-exception
            r2 = r0
            monitor-exit(r1)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.wireless.security.open.avmpTest.AVMPUTTest.runAVMPSignSchedule1(android.content.Context, java.lang.String, java.lang.String, java.lang.String):void");
    }
}
