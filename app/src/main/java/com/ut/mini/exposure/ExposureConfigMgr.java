package com.ut.mini.exposure;

import com.alibaba.analytics.AnalyticsMgr;
import com.alibaba.analytics.core.ClientVariables;
import com.alibaba.analytics.utils.SpSetting;

public class ExposureConfigMgr {
    private static final String EXP_CONFIG_TAG = "autoExposure";
    public static double dimThreshold = 0.5d;
    private static String mConfig = null;
    public static int maxTimeThreshold = 3600000;
    public static boolean notClearTagAfterDisAppear = false;
    public static int timeThreshold = 500;
    public static boolean trackerExposureOpen = true;

    public static void init() {
        updateExposureConfig(SpSetting.get(ClientVariables.getInstance().getContext(), EXP_CONFIG_TAG));
        TrackerManager.getInstance().getThreadHandle().postDelayed(new Runnable() {
            public final void run() {
                ExposureConfigMgr.updateExposureConfig();
            }
        }, 15000);
    }

    public static void updateExposureConfig() {
        try {
            updateExposureConfig(AnalyticsMgr.getValue(EXP_CONFIG_TAG));
        } catch (Throwable unused) {
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000a, code lost:
        if (r7.equalsIgnoreCase(mConfig) == false) goto L_0x000c;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void updateExposureConfig(java.lang.String r7) {
        /*
            r0 = 0
            r1 = 1
            if (r7 == 0) goto L_0x000c
            java.lang.String r2 = mConfig     // Catch:{ Throwable -> 0x00c7 }
            boolean r2 = r7.equalsIgnoreCase(r2)     // Catch:{ Throwable -> 0x00c7 }
            if (r2 != 0) goto L_0x00c7
        L_0x000c:
            if (r7 != 0) goto L_0x0012
            java.lang.String r2 = mConfig     // Catch:{ Throwable -> 0x00c7 }
            if (r2 == 0) goto L_0x00c7
        L_0x0012:
            mConfig = r7     // Catch:{ Throwable -> 0x00c7 }
            com.alibaba.analytics.core.ClientVariables r7 = com.alibaba.analytics.core.ClientVariables.getInstance()     // Catch:{ Throwable -> 0x00c7 }
            android.content.Context r7 = r7.getContext()     // Catch:{ Throwable -> 0x00c7 }
            java.lang.String r2 = "autoExposure"
            java.lang.String r3 = mConfig     // Catch:{ Throwable -> 0x00c7 }
            com.alibaba.analytics.utils.SpSetting.put(r7, r2, r3)     // Catch:{ Throwable -> 0x00c7 }
            java.lang.String r7 = mConfig     // Catch:{ Throwable -> 0x00c7 }
            if (r7 == 0) goto L_0x00bd
            java.lang.String r7 = mConfig     // Catch:{ Exception -> 0x00b8 }
            java.lang.Class<java.util.Map> r2 = java.util.Map.class
            java.lang.Object r7 = com.alibaba.fastjson.JSONObject.parseObject(r7, r2)     // Catch:{ Exception -> 0x00b8 }
            java.util.Map r7 = (java.util.Map) r7     // Catch:{ Exception -> 0x00b8 }
            if (r7 == 0) goto L_0x00c7
            int r2 = r7.size()     // Catch:{ Exception -> 0x00b8 }
            if (r2 <= 0) goto L_0x00c7
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8 }
            r2.<init>()     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r3 = "turnOn"
            java.lang.Object r3 = r7.get(r3)     // Catch:{ Exception -> 0x00b8 }
            r2.append(r3)     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r3 = "1"
            boolean r2 = r2.equals(r3)     // Catch:{ Exception -> 0x00b8 }
            if (r2 == 0) goto L_0x0057
            trackerExposureOpen = r1     // Catch:{ Exception -> 0x00b8 }
            goto L_0x0059
        L_0x0057:
            trackerExposureOpen = r0     // Catch:{ Exception -> 0x00b8 }
        L_0x0059:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8 }
            r2.<init>()     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r3 = "timeThreshold"
            java.lang.Object r3 = r7.get(r3)     // Catch:{ Exception -> 0x00b8 }
            r2.append(r3)     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00b8 }
            r3 = -1
            int r2 = java.lang.Integer.parseInt(r2)     // Catch:{ Exception -> 0x0072 }
            goto L_0x0073
        L_0x0072:
            r2 = -1
        L_0x0073:
            if (r2 < 0) goto L_0x0077
            timeThreshold = r2     // Catch:{ Exception -> 0x00b8 }
        L_0x0077:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8 }
            r2.<init>()     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r3 = "areaThreshold"
            java.lang.Object r3 = r7.get(r3)     // Catch:{ Exception -> 0x00b8 }
            r2.append(r3)     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r2 = r2.toString()     // Catch:{ Exception -> 0x00b8 }
            r3 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r5 = java.lang.Double.parseDouble(r2)     // Catch:{ Exception -> 0x0090 }
            r3 = r5
        L_0x0090:
            r5 = 0
            int r2 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r2 < 0) goto L_0x0098
            dimThreshold = r3     // Catch:{ Exception -> 0x00b8 }
        L_0x0098:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00b8 }
            r2.<init>()     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r3 = "notClearTag"
            java.lang.Object r7 = r7.get(r3)     // Catch:{ Exception -> 0x00b8 }
            r2.append(r7)     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r7 = r2.toString()     // Catch:{ Exception -> 0x00b8 }
            java.lang.String r2 = "1"
            boolean r7 = r7.equals(r2)     // Catch:{ Exception -> 0x00b8 }
            if (r7 == 0) goto L_0x00b5
            notClearTagAfterDisAppear = r1     // Catch:{ Exception -> 0x00b8 }
            goto L_0x00c7
        L_0x00b5:
            notClearTagAfterDisAppear = r0     // Catch:{ Exception -> 0x00b8 }
            goto L_0x00c7
        L_0x00b8:
            r7 = move-exception
            r7.printStackTrace()     // Catch:{ Throwable -> 0x00c7 }
            goto L_0x00c7
        L_0x00bd:
            trackerExposureOpen = r1     // Catch:{ Throwable -> 0x00c7 }
            r7 = 500(0x1f4, float:7.0E-43)
            timeThreshold = r7     // Catch:{ Throwable -> 0x00c7 }
            r2 = 4602678819172646912(0x3fe0000000000000, double:0.5)
            dimThreshold = r2     // Catch:{ Throwable -> 0x00c7 }
        L_0x00c7:
            java.lang.String r7 = "ExposureConfigMgr"
            r2 = 6
            java.lang.Object[] r2 = new java.lang.Object[r2]
            java.lang.String r3 = "trackerExposureOpen"
            r2[r0] = r3
            boolean r0 = trackerExposureOpen
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
            r2[r1] = r0
            r0 = 2
            java.lang.String r1 = "timeThreshold"
            r2[r0] = r1
            r0 = 3
            int r1 = timeThreshold
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)
            r2[r0] = r1
            r0 = 4
            java.lang.String r1 = " dimThreshold"
            r2[r0] = r1
            r0 = 5
            double r3 = dimThreshold
            java.lang.Double r1 = java.lang.Double.valueOf(r3)
            r2[r0] = r1
            com.ut.mini.exposure.ExpLogger.d(r7, r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ut.mini.exposure.ExposureConfigMgr.updateExposureConfig(java.lang.String):void");
    }
}
