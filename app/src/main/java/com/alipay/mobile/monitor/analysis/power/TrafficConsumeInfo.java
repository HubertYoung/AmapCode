package com.alipay.mobile.monitor.analysis.power;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.concurrent.TimeUnit;

public class TrafficConsumeInfo {
    public static final long LOAD_TIME_SCALE = TimeUnit.SECONDS.toMillis(1);
    private static final String STATS_FILE_PATH = "/proc/net/xt_qtaguid/stats";
    private static final String TAG = "TrafficConsumeInfo";
    private static final String WIFILINE = "wlan0";
    private long gprsRxBytes;
    private long gprsTxBytes;
    private long loadSecondTime;
    private long totalRxBytes;
    private long totalTxBytes;
    private int version = 1;
    private long wifiRxBytes;
    private long wifiTxBytes;

    public long getLoadSecondTime() {
        return this.loadSecondTime;
    }

    public void setLoadSecondTime(long j) {
        this.loadSecondTime = j;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public TrafficConsumeInfo(Context context) {
    }

    public void setTotalTxBytes(long j) {
        this.totalTxBytes = j;
    }

    public void setTotalRxBytes(long j) {
        this.totalRxBytes = j;
    }

    public void setWifiTxBytes(long j) {
        this.wifiTxBytes = j;
    }

    public void setWifiRxBytes(long j) {
        this.wifiRxBytes = j;
    }

    public void setGprsTxBytes(long j) {
        this.gprsTxBytes = j;
    }

    public void setGprsRxBytes(long j) {
        this.gprsRxBytes = j;
    }

    public long getTotalTxBytes() {
        return this.totalTxBytes;
    }

    public long getTotalRxBytes() {
        return this.totalRxBytes;
    }

    public long getWifiTxBytes() {
        return this.wifiTxBytes;
    }

    public long getWifiRxBytes() {
        return this.wifiRxBytes;
    }

    public long getGprsTxBytes() {
        return this.gprsTxBytes;
    }

    public long getGprsRxBytes() {
        return this.gprsRxBytes;
    }

    public long calcTotalBytes() {
        return this.totalTxBytes + this.totalRxBytes;
    }

    public long calcWifiBytes() {
        return this.wifiTxBytes + this.wifiRxBytes;
    }

    public long calcGprsBytes() {
        return this.gprsTxBytes + this.gprsRxBytes;
    }

    public long calcRxBytes() {
        return this.wifiRxBytes + this.gprsRxBytes > 0 ? this.wifiRxBytes + this.gprsRxBytes : this.totalRxBytes;
    }

    public long calcTxBytes() {
        return this.wifiTxBytes + this.gprsTxBytes > 0 ? this.wifiTxBytes + this.gprsTxBytes : this.totalTxBytes;
    }

    public void subtraction(TrafficConsumeInfo trafficConsumeInfo) {
        if (trafficConsumeInfo != null) {
            this.loadSecondTime -= trafficConsumeInfo.getLoadSecondTime();
            this.totalRxBytes -= trafficConsumeInfo.getTotalRxBytes();
            this.totalTxBytes -= trafficConsumeInfo.getTotalTxBytes();
            this.wifiRxBytes -= trafficConsumeInfo.getWifiRxBytes();
            this.wifiTxBytes -= trafficConsumeInfo.getWifiTxBytes();
            this.gprsRxBytes -= trafficConsumeInfo.getGprsRxBytes();
            this.gprsTxBytes -= trafficConsumeInfo.getGprsTxBytes();
        }
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(18:56|57|58|59|(2:60|(3:62|(1:148)(2:64|(2:69|(2:73|152))(2:68|149))|146)(1:147))|74|(1:76)|77|(1:79)|80|(1:82)|83|(1:85)|86|87|88|89|90) */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:88:0x0189 */
    /* JADX WARNING: Removed duplicated region for block: B:101:0x01b0  */
    /* JADX WARNING: Removed duplicated region for block: B:104:0x01b8  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x01c0  */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x01c8  */
    /* JADX WARNING: Removed duplicated region for block: B:112:0x01cc A[SYNTHETIC, Splitter:B:112:0x01cc] */
    /* JADX WARNING: Removed duplicated region for block: B:116:0x01d1 A[SYNTHETIC, Splitter:B:116:0x01d1] */
    /* JADX WARNING: Removed duplicated region for block: B:125:0x01ef  */
    /* JADX WARNING: Removed duplicated region for block: B:128:0x01f7  */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x01ff  */
    /* JADX WARNING: Removed duplicated region for block: B:134:0x0207  */
    /* JADX WARNING: Removed duplicated region for block: B:136:0x020b A[SYNTHETIC, Splitter:B:136:0x020b] */
    /* JADX WARNING: Removed duplicated region for block: B:140:0x0210 A[SYNTHETIC, Splitter:B:140:0x0210] */
    /* JADX WARNING: Removed duplicated region for block: B:144:0x0214 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void load(boolean r13) {
        /*
            r12 = this;
            long r0 = java.lang.System.currentTimeMillis()
            long r2 = LOAD_TIME_SCALE
            long r0 = r0 / r2
            r12.loadSecondTime = r0
            com.alipay.mobile.common.logging.api.ProcessInfo r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getProcessInfo()
            int r0 = r0.getUserId()
            long r1 = android.net.TrafficStats.getUidRxBytes(r0)
            r3 = 0
            int r5 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r5 >= 0) goto L_0x001c
            r1 = r3
        L_0x001c:
            r12.setTotalRxBytes(r1)
            long r5 = android.net.TrafficStats.getUidTxBytes(r0)
            int r7 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r7 >= 0) goto L_0x0028
            r5 = r3
        L_0x0028:
            r12.setTotalTxBytes(r5)
            if (r13 == 0) goto L_0x0036
            int r13 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r13 <= 0) goto L_0x0036
            int r13 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r13 <= 0) goto L_0x0036
            return
        L_0x0036:
            r13 = 0
            java.lang.String r1 = "netstats"
            android.os.IBinder r1 = android.os.ServiceManager.getService(r1)     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            android.net.INetworkStatsService r1 = android.net.INetworkStatsService.Stub.asInterface(r1)     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            if (r1 != 0) goto L_0x0077
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r0 = "TrafficConsumeInfo"
            java.lang.String r1 = "load: "
            java.lang.String r2 = java.lang.String.valueOf(r12)
            java.lang.String r1 = r1.concat(r2)
            r13.verbose(r0, r1)
            long r0 = r12.gprsTxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x005e
            r12.gprsTxBytes = r3
        L_0x005e:
            long r0 = r12.gprsRxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x0066
            r12.gprsRxBytes = r3
        L_0x0066:
            long r0 = r12.wifiRxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x006e
            r12.wifiRxBytes = r3
        L_0x006e:
            long r0 = r12.wifiTxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x0076
            r12.wifiTxBytes = r3
        L_0x0076:
            return
        L_0x0077:
            java.lang.Class<android.net.INetworkStatsService> r2 = android.net.INetworkStatsService.class
            java.lang.String r5 = "getMobileIfaces"
            r6 = 0
            java.lang.Class[] r7 = new java.lang.Class[r6]     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            java.lang.reflect.Method r2 = r2.getMethod(r5, r7)     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            if (r2 != 0) goto L_0x00b8
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r0 = "TrafficConsumeInfo"
            java.lang.String r1 = "load: "
            java.lang.String r2 = java.lang.String.valueOf(r12)
            java.lang.String r1 = r1.concat(r2)
            r13.verbose(r0, r1)
            long r0 = r12.gprsTxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x009f
            r12.gprsTxBytes = r3
        L_0x009f:
            long r0 = r12.gprsRxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x00a7
            r12.gprsRxBytes = r3
        L_0x00a7:
            long r0 = r12.wifiRxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x00af
            r12.wifiRxBytes = r3
        L_0x00af:
            long r0 = r12.wifiTxBytes
            int r13 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x00b7
            r12.wifiTxBytes = r3
        L_0x00b7:
            return
        L_0x00b8:
            java.lang.String[] r1 = r1.getMobileIfaces()     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            r2.<init>()     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            if (r1 == 0) goto L_0x00d1
            int r5 = r1.length     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            if (r5 == 0) goto L_0x00d1
            int r5 = r1.length     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
        L_0x00c7:
            if (r6 >= r5) goto L_0x00d1
            r7 = r1[r6]     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            r2.add(r7)     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            int r6 = r6 + 1
            goto L_0x00c7
        L_0x00d1:
            java.io.FileReader r1 = new java.io.FileReader     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            java.lang.String r5 = "/proc/net/xt_qtaguid/stats"
            r1.<init>(r5)     // Catch:{ Throwable -> 0x01d5, all -> 0x0194 }
            java.io.BufferedReader r5 = new java.io.BufferedReader     // Catch:{ Throwable -> 0x01d6, all -> 0x0191 }
            r5.<init>(r1)     // Catch:{ Throwable -> 0x01d6, all -> 0x0191 }
            java.lang.String r13 = java.lang.String.valueOf(r0)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
        L_0x00e1:
            java.lang.String r0 = r5.readLine()     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            if (r0 == 0) goto L_0x0153
            java.lang.String r6 = " "
            java.lang.String[] r0 = r0.split(r6)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r6 = 3
            r6 = r0[r6]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            boolean r6 = r13.equals(r6)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            if (r6 == 0) goto L_0x00e1
            r6 = 1
            r7 = r0[r6]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            boolean r7 = r2.contains(r7)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r8 = 5
            r9 = 7
            r10 = 2
            if (r7 == 0) goto L_0x0125
            java.lang.String r7 = "0x0"
            r11 = r0[r10]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            boolean r7 = r7.equalsIgnoreCase(r11)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            if (r7 == 0) goto L_0x0125
            long r6 = r12.gprsTxBytes     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r9 = r0[r9]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            long r9 = java.lang.Long.parseLong(r9)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r11 = 0
            long r6 = r6 + r9
            r12.gprsTxBytes = r6     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            long r6 = r12.gprsRxBytes     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r0 = r0[r8]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r0 = 0
            long r6 = r6 + r8
            r12.gprsRxBytes = r6     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            goto L_0x00e1
        L_0x0125:
            java.lang.String r7 = "wlan0"
            r6 = r0[r6]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            boolean r6 = r7.equalsIgnoreCase(r6)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            if (r6 == 0) goto L_0x00e1
            java.lang.String r6 = "0x0"
            r7 = r0[r10]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            boolean r6 = r6.equalsIgnoreCase(r7)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            if (r6 == 0) goto L_0x00e1
            long r6 = r12.wifiRxBytes     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r8 = r0[r8]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            long r10 = java.lang.Long.parseLong(r8)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r8 = 0
            long r6 = r6 + r10
            r12.wifiRxBytes = r6     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            long r6 = r12.wifiTxBytes     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r0 = r0[r9]     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            long r8 = java.lang.Long.parseLong(r0)     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            r0 = 0
            long r6 = r6 + r8
            r12.wifiTxBytes = r6     // Catch:{ Throwable -> 0x018f, all -> 0x018d }
            goto L_0x00e1
        L_0x0153:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r0 = "TrafficConsumeInfo"
            java.lang.String r2 = "load: "
            java.lang.String r6 = java.lang.String.valueOf(r12)
            java.lang.String r2 = r2.concat(r6)
            r13.verbose(r0, r2)
            long r6 = r12.gprsTxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x016e
            r12.gprsTxBytes = r3
        L_0x016e:
            long r6 = r12.gprsRxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x0176
            r12.gprsRxBytes = r3
        L_0x0176:
            long r6 = r12.wifiRxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x017e
            r12.wifiRxBytes = r3
        L_0x017e:
            long r6 = r12.wifiTxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x0186
            r12.wifiTxBytes = r3
        L_0x0186:
            r5.close()     // Catch:{ IOException -> 0x0189 }
        L_0x0189:
            r1.close()     // Catch:{ IOException -> 0x018c }
        L_0x018c:
            return
        L_0x018d:
            r0 = move-exception
            goto L_0x0197
        L_0x018f:
            r13 = r5
            goto L_0x01d6
        L_0x0191:
            r0 = move-exception
            r5 = r13
            goto L_0x0197
        L_0x0194:
            r0 = move-exception
            r1 = r13
            r5 = r1
        L_0x0197:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r13 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r2 = "TrafficConsumeInfo"
            java.lang.String r6 = "load: "
            java.lang.String r7 = java.lang.String.valueOf(r12)
            java.lang.String r6 = r6.concat(r7)
            r13.verbose(r2, r6)
            long r6 = r12.gprsTxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x01b2
            r12.gprsTxBytes = r3
        L_0x01b2:
            long r6 = r12.gprsRxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x01ba
            r12.gprsRxBytes = r3
        L_0x01ba:
            long r6 = r12.wifiRxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x01c2
            r12.wifiRxBytes = r3
        L_0x01c2:
            long r6 = r12.wifiTxBytes
            int r13 = (r6 > r3 ? 1 : (r6 == r3 ? 0 : -1))
            if (r13 >= 0) goto L_0x01ca
            r12.wifiTxBytes = r3
        L_0x01ca:
            if (r5 == 0) goto L_0x01cf
            r5.close()     // Catch:{ IOException -> 0x01cf }
        L_0x01cf:
            if (r1 == 0) goto L_0x01d4
            r1.close()     // Catch:{ IOException -> 0x01d4 }
        L_0x01d4:
            throw r0
        L_0x01d5:
            r1 = r13
        L_0x01d6:
            com.alipay.mobile.common.logging.api.trace.TraceLogger r0 = com.alipay.mobile.common.logging.api.LoggerFactory.getTraceLogger()
            java.lang.String r2 = "TrafficConsumeInfo"
            java.lang.String r5 = "load: "
            java.lang.String r6 = java.lang.String.valueOf(r12)
            java.lang.String r5 = r5.concat(r6)
            r0.verbose(r2, r5)
            long r5 = r12.gprsTxBytes
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x01f1
            r12.gprsTxBytes = r3
        L_0x01f1:
            long r5 = r12.gprsRxBytes
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x01f9
            r12.gprsRxBytes = r3
        L_0x01f9:
            long r5 = r12.wifiRxBytes
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x0201
            r12.wifiRxBytes = r3
        L_0x0201:
            long r5 = r12.wifiTxBytes
            int r0 = (r5 > r3 ? 1 : (r5 == r3 ? 0 : -1))
            if (r0 >= 0) goto L_0x0209
            r12.wifiTxBytes = r3
        L_0x0209:
            if (r13 == 0) goto L_0x020e
            r13.close()     // Catch:{ IOException -> 0x020e }
        L_0x020e:
            if (r1 == 0) goto L_0x0214
            r1.close()     // Catch:{ IOException -> 0x0213 }
        L_0x0213:
            return
        L_0x0214:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.monitor.analysis.power.TrafficConsumeInfo.load(boolean):void");
    }

    public void readFromSP(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            this.loadSecondTime = sharedPreferences.getLong("loadSecondTime", 0);
            this.version = sharedPreferences.getInt("version", 0);
            this.totalTxBytes = sharedPreferences.getLong("totalTxBytes", 0);
            this.totalRxBytes = sharedPreferences.getLong("totalRxBytes", 0);
            this.wifiTxBytes = sharedPreferences.getLong("wifiTxBytes", 0);
            this.wifiRxBytes = sharedPreferences.getLong("wifiRxBytes", 0);
            this.gprsTxBytes = sharedPreferences.getLong("gprsTxBytes", 0);
            this.gprsRxBytes = sharedPreferences.getLong("gprsRxBytes", 0);
        }
    }

    public void writeToSP(Editor editor) {
        if (editor != null) {
            editor.putLong("loadSecondTime", this.loadSecondTime);
            editor.putInt("version", this.version);
            editor.putLong("totalTxBytes", this.totalTxBytes);
            editor.putLong("totalRxBytes", this.totalRxBytes);
            editor.putLong("wifiTxBytes", this.wifiTxBytes);
            editor.putLong("wifiRxBytes", this.wifiRxBytes);
            editor.putLong("gprsTxBytes", this.gprsTxBytes);
            editor.putLong("gprsRxBytes", this.gprsRxBytes);
            editor.commit();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version = ");
        sb.append(this.version);
        sb.append(", loadSecondTime = ");
        sb.append(this.loadSecondTime);
        sb.append(", totalTxBytes = ");
        sb.append(this.totalTxBytes);
        sb.append(", totalRxBytes = ");
        sb.append(this.totalRxBytes);
        sb.append(", wifiTxBytes = ");
        sb.append(this.wifiTxBytes);
        sb.append(", wifiRxBytes = ");
        sb.append(this.wifiRxBytes);
        sb.append(", gprsTxBytes = ");
        sb.append(this.gprsTxBytes);
        sb.append(", gprsRxBytes = ");
        sb.append(this.gprsRxBytes);
        return sb.toString();
    }
}
