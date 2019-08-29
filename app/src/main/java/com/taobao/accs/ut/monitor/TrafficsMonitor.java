package com.taobao.accs.ut.monitor;

import android.content.Context;
import android.text.TextUtils;
import anet.channel.statist.Dimension;
import anet.channel.statist.Measure;
import anet.channel.statist.Monitor;
import anet.channel.statist.StatObject;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.statistics.DBHelper;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrafficsMonitor {
    private static final int CACHE_COUNT = 10;
    private static final String DIMENSION_BIZID = "bizId";
    private static final String DIMENSION_DATE = "date";
    private static final String DIMENSION_HOST = "host";
    private static final String DIMENSION_ISBACKGROUND = "isBackground";
    private static final String MEASURE_SIZE = "size";
    private static final String MODULE = "NetworkSDK";
    private static final String MONITOR_POINT = "TrafficStats";
    private static final String TAG = "TrafficsMonitor";
    private Map<String, String> bidMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1;

        {
            put("im", "512");
            put("motu", "513");
            put("acds", "514");
            put(GlobalClientInfo.AGOO_SERVICE_ID, "515");
            put("agooAck", "515");
            put("agooTokenReport", "515");
            put("accsSelf", "1000");
        }
    };
    private int count = 0;
    private String lastSaveDay = "";
    private Context mContext;
    private Map<String, List<TrafficInfo>> trafficMap = new HashMap();

    @Monitor(module = "NetworkSDK", monitorPoint = "TrafficStats")
    public static class StatTrafficMonitor extends BaseMonitor {
        @Dimension
        public String bizId;
        @Dimension
        public String date;
        @Dimension
        public String host;
        @Dimension
        public boolean isBackground;
        @Dimension
        public String serviceId;
        @Measure
        public long size;
    }

    public static class TrafficInfo {
        String bid;
        String date;
        String host;
        boolean isBackground;
        String serviceId;
        long trafficSize;

        public TrafficInfo(String str, boolean z, String str2, long j) {
            this.serviceId = str;
            this.isBackground = z;
            this.host = str2;
            this.trafficSize = j;
        }

        public TrafficInfo(String str, String str2, String str3, boolean z, String str4, long j) {
            this.date = str;
            this.bid = str2;
            this.serviceId = str3;
            this.isBackground = z;
            this.host = str4;
            this.trafficSize = j;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            StringBuilder sb2 = new StringBuilder("date:");
            sb2.append(this.date);
            sb.append(sb2.toString());
            sb.append(Token.SEPARATOR);
            StringBuilder sb3 = new StringBuilder("bizId:");
            sb3.append(this.bid);
            sb.append(sb3.toString());
            sb.append(Token.SEPARATOR);
            StringBuilder sb4 = new StringBuilder("serviceId:");
            sb4.append(this.serviceId);
            sb.append(sb4.toString());
            sb.append(Token.SEPARATOR);
            StringBuilder sb5 = new StringBuilder("host:");
            sb5.append(this.host);
            sb.append(sb5.toString());
            sb.append(Token.SEPARATOR);
            StringBuilder sb6 = new StringBuilder("isBackground:");
            sb6.append(this.isBackground);
            sb.append(sb6.toString());
            sb.append(Token.SEPARATOR);
            StringBuilder sb7 = new StringBuilder("size:");
            sb7.append(this.trafficSize);
            sb.append(sb7.toString());
            return sb.toString();
        }
    }

    public TrafficsMonitor(Context context) {
        this.mContext = context;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0090, code lost:
        if (r10.count < 10) goto L_0x0099;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0092, code lost:
        saveTraffics();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addTrafficInfo(com.taobao.accs.ut.monitor.TrafficsMonitor.TrafficInfo r11) {
        /*
            r10 = this;
            if (r11 == 0) goto L_0x0099
            java.lang.String r0 = r11.host
            if (r0 == 0) goto L_0x0099
            long r0 = r11.trafficSize
            r2 = 0
            int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r0 <= 0) goto L_0x0099
            java.lang.String r0 = r11.serviceId
            boolean r0 = android.text.TextUtils.isEmpty(r0)
            if (r0 == 0) goto L_0x0019
            java.lang.String r0 = "accsSelf"
            goto L_0x001b
        L_0x0019:
            java.lang.String r0 = r11.serviceId
        L_0x001b:
            r11.serviceId = r0
            java.util.Map<java.lang.String, java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo>> r0 = r10.trafficMap
            monitor-enter(r0)
            java.util.Map<java.lang.String, java.lang.String> r1 = r10.bidMap     // Catch:{ all -> 0x0096 }
            java.lang.String r2 = r11.serviceId     // Catch:{ all -> 0x0096 }
            java.lang.Object r1 = r1.get(r2)     // Catch:{ all -> 0x0096 }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ all -> 0x0096 }
            boolean r2 = android.text.TextUtils.isEmpty(r1)     // Catch:{ all -> 0x0096 }
            if (r2 == 0) goto L_0x0032
            monitor-exit(r0)     // Catch:{ all -> 0x0096 }
            return
        L_0x0032:
            r11.bid = r1     // Catch:{ all -> 0x0096 }
            com.taobao.accs.utl.ALog$Level r2 = com.taobao.accs.utl.ALog.Level.D     // Catch:{ all -> 0x0096 }
            com.taobao.accs.utl.ALog.isPrintLog(r2)     // Catch:{ all -> 0x0096 }
            java.util.Map<java.lang.String, java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo>> r2 = r10.trafficMap     // Catch:{ all -> 0x0096 }
            java.lang.Object r2 = r2.get(r1)     // Catch:{ all -> 0x0096 }
            java.util.List r2 = (java.util.List) r2     // Catch:{ all -> 0x0096 }
            r3 = 1
            if (r2 == 0) goto L_0x0079
            java.util.Iterator r4 = r2.iterator()     // Catch:{ all -> 0x0096 }
        L_0x0048:
            boolean r5 = r4.hasNext()     // Catch:{ all -> 0x0096 }
            if (r5 == 0) goto L_0x0072
            java.lang.Object r5 = r4.next()     // Catch:{ all -> 0x0096 }
            com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo r5 = (com.taobao.accs.ut.monitor.TrafficsMonitor.TrafficInfo) r5     // Catch:{ all -> 0x0096 }
            boolean r6 = r5.isBackground     // Catch:{ all -> 0x0096 }
            boolean r7 = r11.isBackground     // Catch:{ all -> 0x0096 }
            if (r6 != r7) goto L_0x0048
            java.lang.String r6 = r5.host     // Catch:{ all -> 0x0096 }
            if (r6 == 0) goto L_0x0048
            java.lang.String r6 = r5.host     // Catch:{ all -> 0x0096 }
            java.lang.String r7 = r11.host     // Catch:{ all -> 0x0096 }
            boolean r6 = r6.equals(r7)     // Catch:{ all -> 0x0096 }
            if (r6 == 0) goto L_0x0048
            long r6 = r5.trafficSize     // Catch:{ all -> 0x0096 }
            long r8 = r11.trafficSize     // Catch:{ all -> 0x0096 }
            r4 = 0
            long r6 = r6 + r8
            r5.trafficSize = r6     // Catch:{ all -> 0x0096 }
            r4 = 0
            goto L_0x0073
        L_0x0072:
            r4 = 1
        L_0x0073:
            if (r4 == 0) goto L_0x0081
            r2.add(r11)     // Catch:{ all -> 0x0096 }
            goto L_0x0081
        L_0x0079:
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0096 }
            r2.<init>()     // Catch:{ all -> 0x0096 }
            r2.add(r11)     // Catch:{ all -> 0x0096 }
        L_0x0081:
            java.util.Map<java.lang.String, java.util.List<com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo>> r11 = r10.trafficMap     // Catch:{ all -> 0x0096 }
            r11.put(r1, r2)     // Catch:{ all -> 0x0096 }
            int r11 = r10.count     // Catch:{ all -> 0x0096 }
            int r11 = r11 + r3
            r10.count = r11     // Catch:{ all -> 0x0096 }
            monitor-exit(r0)     // Catch:{ all -> 0x0096 }
            int r11 = r10.count
            r0 = 10
            if (r11 < r0) goto L_0x0099
            r10.saveTraffics()
            goto L_0x0099
        L_0x0096:
            r11 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0096 }
            throw r11
        L_0x0099:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.ut.monitor.TrafficsMonitor.addTrafficInfo(com.taobao.accs.ut.monitor.TrafficsMonitor$TrafficInfo):void");
    }

    private void saveTraffics() {
        boolean z;
        String str;
        synchronized (this.trafficMap) {
            String formatDay = UtilityImpl.formatDay(System.currentTimeMillis());
            if (TextUtils.isEmpty(this.lastSaveDay) || this.lastSaveDay.equals(formatDay)) {
                str = formatDay;
                z = false;
            } else {
                str = this.lastSaveDay;
                z = true;
            }
            for (String str2 : this.trafficMap.keySet()) {
                for (TrafficInfo trafficInfo : this.trafficMap.get(str2)) {
                    if (trafficInfo != null) {
                        DBHelper.getInstance(this.mContext).onTraffics(trafficInfo.host, trafficInfo.serviceId, this.bidMap.get(trafficInfo.serviceId), trafficInfo.isBackground, trafficInfo.trafficSize, str);
                    }
                }
            }
            if (ALog.isPrintLog(Level.D)) {
                StringBuilder sb = new StringBuilder("savetoDay:");
                sb.append(str);
                sb.append(" saveTraffics");
                sb.append(this.trafficMap.toString());
                ALog.d(TAG, sb.toString(), new Object[0]);
            }
            if (z) {
                this.trafficMap.clear();
                commit();
            } else if (ALog.isPrintLog(Level.D)) {
                StringBuilder sb2 = new StringBuilder("no need commit lastsaveDay:");
                sb2.append(this.lastSaveDay);
                sb2.append(" currday:");
                sb2.append(formatDay);
                ALog.d(TAG, sb2.toString(), new Object[0]);
            }
            this.lastSaveDay = formatDay;
            this.count = 0;
        }
    }

    public void restoreTraffics() {
        try {
            synchronized (this.trafficMap) {
                this.trafficMap.clear();
            }
            List<TrafficInfo> traffics = DBHelper.getInstance(this.mContext).getTraffics(true);
            if (traffics != null) {
                for (TrafficInfo addTrafficInfo : traffics) {
                    addTrafficInfo(addTrafficInfo);
                }
            }
        } catch (Exception e) {
            ALog.w(TAG, e.toString(), new Object[0]);
        }
    }

    private void commit() {
        List<TrafficInfo> traffics = DBHelper.getInstance(this.mContext).getTraffics(false);
        if (traffics != null) {
            try {
                for (TrafficInfo next : traffics) {
                    if (next != null) {
                        StatTrafficMonitor statTrafficMonitor = new StatTrafficMonitor();
                        statTrafficMonitor.bizId = next.bid;
                        statTrafficMonitor.date = next.date;
                        statTrafficMonitor.host = next.host;
                        statTrafficMonitor.isBackground = next.isBackground;
                        statTrafficMonitor.size = next.trafficSize;
                        x.a().a((StatObject) statTrafficMonitor);
                    }
                }
                DBHelper.getInstance(this.mContext).clearTraffics();
            } catch (Throwable th) {
                ALog.e("", th.toString(), new Object[0]);
                th.printStackTrace();
            }
        }
    }
}
