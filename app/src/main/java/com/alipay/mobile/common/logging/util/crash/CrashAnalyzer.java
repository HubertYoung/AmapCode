package com.alipay.mobile.common.logging.util.crash;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.alipay.mobile.common.logging.api.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CrashAnalyzer {
    private static List<CrashInfoDO> a;

    public static synchronized List<CrashInfoDO> getHistoryCrashTypes(Context context) {
        List rList;
        synchronized (CrashAnalyzer.class) {
            rList = new ArrayList();
            if (context != null) {
                try {
                    if (a != null) {
                        rList.addAll(a);
                    } else {
                        a = new ArrayList();
                        SharedPreferences sp = a(context);
                        if (sp == null) {
                            throw new RuntimeException("SP is null");
                        }
                        String crashTypes = sp.getString("crashTypes", "");
                        if (!TextUtils.isEmpty(crashTypes)) {
                            JSONArray crashDOArray = new JSONArray(crashTypes);
                            for (int i = 0; i < crashDOArray.length(); i++) {
                                JSONObject object = crashDOArray.getJSONObject(i);
                                CrashInfoDO crashInfoDO = new CrashInfoDO();
                                crashInfoDO.parse(object);
                                a.add(crashInfoDO);
                            }
                        }
                        rList.addAll(a);
                    }
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().warn((String) "CrashAnalyzer", tr);
                }
            }
        }
        return rList;
    }

    private static synchronized void a(Context context, List<CrashInfoDO> crashList) {
        synchronized (CrashAnalyzer.class) {
            if (!(context == null || crashList == null)) {
                try {
                    if (a == null) {
                        a = new ArrayList();
                    }
                    a.clear();
                    a.addAll(crashList);
                    SharedPreferences sp = a(context);
                    if (sp == null) {
                        throw new RuntimeException("SP is null");
                    }
                    JSONArray crashDOArray = new JSONArray();
                    for (CrashInfoDO crashInfoDO : a) {
                        crashDOArray.put(crashInfoDO.toJsonObject());
                    }
                    sp.edit().putString("crashTypes", crashDOArray.toString()).commit();
                } catch (Throwable tr) {
                    LoggerFactory.getTraceLogger().warn((String) "CrashAnalyzer", tr);
                }
            }
        }
        return;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:106:0x02b6, code lost:
        r29 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:?, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn((java.lang.String) "CrashAnalyzer", r29);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x02c8, code lost:
        r30 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x02c9, code lost:
        r4 = r5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:113:?, code lost:
        r4.close();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x02fa, code lost:
        r29 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x02fb, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn((java.lang.String) "CrashAnalyzer", r29);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0233, code lost:
        r29 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x0234, code lost:
        com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger().warn((java.lang.String) "CrashAnalyzer", r29);
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0286  */
    /* JADX WARNING: Removed duplicated region for block: B:109:0x02c8 A[ExcHandler: all (th java.lang.Throwable), Splitter:B:6:0x0029] */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x02cc A[SYNTHETIC, Splitter:B:112:0x02cc] */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x0317  */
    /* JADX WARNING: Removed duplicated region for block: B:151:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void analyzeNativeCrash(android.content.Context r34, java.lang.String r35) {
        /*
            if (r34 == 0) goto L_0x0008
            boolean r30 = android.text.TextUtils.isEmpty(r35)
            if (r30 == 0) goto L_0x0009
        L_0x0008:
            return
        L_0x0009:
            com.alipay.mobile.common.logging.util.crash.CrashInfoDO r12 = new com.alipay.mobile.common.logging.util.crash.CrashInfoDO
            r12.<init>()
            r18 = 0
            r17 = 0
            java.lang.String r9 = ""
            r4 = 0
            java.io.StringReader r24 = new java.io.StringReader     // Catch:{ Throwable -> 0x036c }
            r0 = r24
            r1 = r35
            r0.<init>(r1)     // Catch:{ Throwable -> 0x036c }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x036c }
            r30 = 8192(0x2000, float:1.14794E-41)
            r0 = r24
            r1 = r30
            r5.<init>(r0, r1)     // Catch:{ Throwable -> 0x036c }
        L_0x0029:
            java.lang.String r19 = r5.readLine()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r19 == 0) goto L_0x02d0
            java.lang.String r30 = "Mobile Information: "
            r0 = r19
            r1 = r30
            boolean r30 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x007c
            r30 = 20
            r0 = r19
            r1 = r30
            java.lang.String r20 = r0.substring(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            boolean r30 = android.text.TextUtils.isEmpty(r20)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 != 0) goto L_0x007c
            java.lang.String r30 = "/sdk: "
            r0 = r20
            r1 = r30
            int r25 = r0.indexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r30 = "'"
            r0 = r20
            r1 = r30
            int r15 = r0.lastIndexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r25
            if (r15 <= r0) goto L_0x007c
            if (r25 <= 0) goto L_0x007c
            int r30 = r25 + 6
            r0 = r20
            r1 = r30
            java.lang.String r22 = r0.substring(r1, r15)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.Integer r30 = java.lang.Integer.valueOf(r22)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            int r30 = r30.intValue()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r30
            r12.setSdkVersion(r0)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
        L_0x007c:
            java.lang.String r30 = "Process Name: "
            r0 = r19
            r1 = r30
            boolean r30 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0097
            r30 = 14
            r0 = r19
            r1 = r30
            java.lang.String r21 = r0.substring(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r21
            r12.setCrashProcessName(r0)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
        L_0x0097:
            java.lang.String r30 = "Thread Name: "
            r0 = r19
            r1 = r30
            boolean r30 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x00b2
            r30 = 13
            r0 = r19
            r1 = r30
            java.lang.String r28 = r0.substring(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r28
            r12.setCrashThreadName(r0)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
        L_0x00b2:
            java.lang.String r30 = "mPaaSProductVersion: "
            r0 = r19
            r1 = r30
            boolean r30 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x00cd
            r30 = 21
            r0 = r19
            r1 = r30
            java.lang.String r30 = r0.substring(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r30
            r12.setCrashProductVersion(r0)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
        L_0x00cd:
            java.lang.String r30 = "mPaaSProductVersion: "
            r0 = r19
            r1 = r30
            boolean r30 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x00ec
            r30 = 14
            r0 = r19
            r1 = r30
            java.lang.String r27 = r0.substring(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            boolean r30 = java.lang.Boolean.getBoolean(r27)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r30
            r12.setStartupCrash(r0)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
        L_0x00ec:
            java.lang.String r30 = "signal "
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0186
            java.lang.String r30 = ", code "
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0186
            java.lang.String r30 = "code "
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0186
            java.lang.String r30 = ", fault addr "
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0186
            java.lang.String r30 = "signal "
            r0 = r19
            r1 = r30
            int r25 = r0.indexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r30 = ", code "
            r0 = r19
            r1 = r30
            int r15 = r0.indexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r25 < 0) goto L_0x0151
            r0 = r25
            if (r15 <= r0) goto L_0x0151
            int r30 = r25 + 7
            r0 = r19
            r1 = r30
            java.lang.String r30 = r0.substring(r1, r15)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = " "
            java.lang.String[] r23 = r30.split(r31)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r30 = 0
            r30 = r23[r30]     // Catch:{ Throwable -> 0x0233, all -> 0x02c8 }
            int r14 = java.lang.Integer.parseInt(r30)     // Catch:{ Throwable -> 0x0233, all -> 0x02c8 }
            r12.setSignal(r14)     // Catch:{ Throwable -> 0x0233, all -> 0x02c8 }
        L_0x0151:
            java.lang.String r30 = "code "
            r0 = r19
            r1 = r30
            int r25 = r0.indexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r30 = ", fault addr "
            r0 = r19
            r1 = r30
            int r15 = r0.indexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r25 < 0) goto L_0x0186
            r0 = r25
            if (r15 <= r0) goto L_0x0186
            int r30 = r25 + 5
            r0 = r19
            r1 = r30
            java.lang.String r30 = r0.substring(r1, r15)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = " "
            java.lang.String[] r8 = r30.split(r31)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r30 = 0
            r30 = r8[r30]     // Catch:{ Throwable -> 0x02b6, all -> 0x02c8 }
            int r10 = java.lang.Integer.parseInt(r30)     // Catch:{ Throwable -> 0x02b6, all -> 0x02c8 }
            r12.setCode(r10)     // Catch:{ Throwable -> 0x02b6, all -> 0x02c8 }
        L_0x0186:
            if (r18 != 0) goto L_0x01c6
            java.lang.String r30 = "    #"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x01c6
            java.lang.String r30 = "pc"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x01c6
            java.lang.String r30 = "egl"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x01b8
            java.lang.String r30 = "libGLES"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 != 0) goto L_0x01c4
        L_0x01b8:
            java.lang.String r30 = "libhwui.so"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x01c6
        L_0x01c4:
            r18 = 1
        L_0x01c6:
            java.lang.String r30 = "key_m_l_l_t"
            r0 = r19
            r1 = r30
            boolean r30 = r0.startsWith(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x01f8
            r30 = 58
            r0 = r19
            r1 = r30
            int r26 = r0.lastIndexOf(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r26 < 0) goto L_0x01f8
            int r30 = r19.length()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            int r30 = r30 + -1
            r0 = r26
            r1 = r30
            if (r0 >= r1) goto L_0x01f8
            int r30 = r26 + 1
            r0 = r19
            r1 = r30
            java.lang.String r30 = r0.substring(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r9 = r30.trim()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
        L_0x01f8:
            java.lang.String r30 = ">>>"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0029
            java.lang.String r30 = "<<<"
            r0 = r19
            r1 = r30
            boolean r30 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            if (r30 == 0) goto L_0x0029
            java.lang.StringBuilder r30 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = " "
            r30.<init>(r31)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = r34.getPackageName()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = " "
            java.lang.StringBuilder r30 = r30.append(r31)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r30 = r30.toString()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            r0 = r19
            r1 = r30
            boolean r17 = r0.contains(r1)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            goto L_0x0029
        L_0x0233:
            r29 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r30 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = "CrashAnalyzer"
            r0 = r30
            r1 = r31
            r2 = r29
            r0.warn(r1, r2)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            goto L_0x0151
        L_0x0245:
            r29 = move-exception
            r4 = r5
        L_0x0247:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r30 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ all -> 0x0369 }
            java.lang.String r31 = "CrashAnalyzer"
            r0 = r30
            r1 = r31
            r2 = r29
            r0.warn(r1, r2)     // Catch:{ all -> 0x0369 }
            if (r4 == 0) goto L_0x025b
            r4.close()     // Catch:{ Throwable -> 0x02e8 }
        L_0x025b:
            int r30 = r12.getSignal()
            if (r30 <= 0) goto L_0x0315
            java.lang.String r30 = r12.getCrashThreadName()
            boolean r30 = android.text.TextUtils.isEmpty(r30)
            if (r30 != 0) goto L_0x0315
            java.lang.String r30 = r12.getCrashThreadName()
            java.lang.String r31 = "RenderThread"
            boolean r30 = r30.contains(r31)
            if (r30 != 0) goto L_0x0279
            if (r18 == 0) goto L_0x0280
        L_0x0279:
            r30 = 100
            r0 = r30
            r12.setCrashType(r0)
        L_0x0280:
            int r30 = r12.getCrashType()
            if (r30 == 0) goto L_0x0315
            java.util.List r13 = getHistoryCrashTypes(r34)
            r16 = 0
            java.util.Iterator r30 = r13.iterator()
        L_0x0290:
            boolean r31 = r30.hasNext()
            if (r31 == 0) goto L_0x030b
            java.lang.Object r11 = r30.next()
            com.alipay.mobile.common.logging.util.crash.CrashInfoDO r11 = (com.alipay.mobile.common.logging.util.crash.CrashInfoDO) r11
            int r31 = r11.getCrashType()
            int r32 = r12.getCrashType()
            r0 = r31
            r1 = r32
            if (r0 != r1) goto L_0x0290
            org.json.JSONObject r31 = r12.toJsonObject()
            r0 = r31
            r11.merge(r0)
            r16 = 1
            goto L_0x0290
        L_0x02b6:
            r29 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r30 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            java.lang.String r31 = "CrashAnalyzer"
            r0 = r30
            r1 = r31
            r2 = r29
            r0.warn(r1, r2)     // Catch:{ Throwable -> 0x0245, all -> 0x02c8 }
            goto L_0x0186
        L_0x02c8:
            r30 = move-exception
            r4 = r5
        L_0x02ca:
            if (r4 == 0) goto L_0x02cf
            r4.close()     // Catch:{ Throwable -> 0x02fa }
        L_0x02cf:
            throw r30
        L_0x02d0:
            r5.close()     // Catch:{ Throwable -> 0x02d5 }
            r4 = r5
            goto L_0x025b
        L_0x02d5:
            r29 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r30 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r31 = "CrashAnalyzer"
            r0 = r30
            r1 = r31
            r2 = r29
            r0.warn(r1, r2)
            r4 = r5
            goto L_0x025b
        L_0x02e8:
            r29 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r30 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r31 = "CrashAnalyzer"
            r0 = r30
            r1 = r31
            r2 = r29
            r0.warn(r1, r2)
            goto L_0x025b
        L_0x02fa:
            r29 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r31 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r32 = "CrashAnalyzer"
            r0 = r31
            r1 = r32
            r2 = r29
            r0.warn(r1, r2)
            goto L_0x02cf
        L_0x030b:
            if (r16 != 0) goto L_0x0310
            r13.add(r12)
        L_0x0310:
            r0 = r34
            a(r0, r13)
        L_0x0315:
            if (r17 == 0) goto L_0x0008
            java.lang.String r30 = "foreground"
            com.alipay.mobile.common.logging.api.LogContext r31 = com.alipay.mobile.common.logging.api.LoggerFactory.getLogContext()     // Catch:{ Throwable -> 0x034c }
            r32 = 0
            r33 = 1
            r0 = r31
            r1 = r32
            r2 = r33
            r3 = r35
            java.lang.String r31 = r0.getClientStatus(r1, r2, r3)     // Catch:{ Throwable -> 0x034c }
            boolean r30 = r30.equals(r31)     // Catch:{ Throwable -> 0x034c }
            if (r30 == 0) goto L_0x0008
            long r6 = java.lang.Long.parseLong(r9)     // Catch:{ Throwable -> 0x034c }
            r30 = 0
            int r30 = (r6 > r30 ? 1 : (r6 == r30 ? 0 : -1))
            if (r30 <= 0) goto L_0x035e
            com.alipay.mobile.common.logging.util.avail.ExceptionCollector r30 = com.alipay.mobile.common.logging.util.avail.ExceptionCollector.getInstance(r34)     // Catch:{ Throwable -> 0x034c }
            java.lang.String r31 = "CRASH"
            r0 = r30
            r1 = r31
            r0.recordException(r1, r6)     // Catch:{ Throwable -> 0x034c }
            goto L_0x0008
        L_0x034c:
            r29 = move-exception
            com.alipay.mobile.common.logging.api.trace.TraceLogger r30 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r31 = "CrashAnalyzer"
            r0 = r30
            r1 = r31
            r2 = r29
            r0.warn(r1, r2)
            goto L_0x0008
        L_0x035e:
            com.alipay.mobile.common.logging.util.avail.ExceptionCollector r30 = com.alipay.mobile.common.logging.util.avail.ExceptionCollector.getInstance(r34)     // Catch:{ Throwable -> 0x034c }
            java.lang.String r31 = "CRASH"
            r30.recordException(r31)     // Catch:{ Throwable -> 0x034c }
            goto L_0x0008
        L_0x0369:
            r30 = move-exception
            goto L_0x02ca
        L_0x036c:
            r29 = move-exception
            goto L_0x0247
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.common.logging.util.crash.CrashAnalyzer.analyzeNativeCrash(android.content.Context, java.lang.String):void");
    }

    public static void analyzeJavaCrash(Context context, String crashInfo) {
    }

    private static SharedPreferences a(Context context) {
        try {
            return context.getSharedPreferences("logging_crash_analysis", 4);
        } catch (Throwable e) {
            LoggerFactory.getTraceLogger().error("CrashAnalyzer", "readAndParseStrategy", e);
            return null;
        }
    }
}
