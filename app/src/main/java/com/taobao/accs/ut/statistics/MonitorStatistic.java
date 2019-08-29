package com.taobao.accs.ut.statistics;

import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.CommonUtils;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

public class MonitorStatistic implements UTInterface {
    private static final long COMMIT_INTERVAL = 1200000;
    private static final String PAGE = "MONITOR";
    private static final String TAG = "MonitorStatistic";
    public int connType;
    private long lastCommitTime = 0;
    public int messageNum = 0;
    public boolean networkAvailable;
    public String proxy;
    public long startServiceTime;
    public int status;
    public boolean tcpConnected = false;
    public boolean threadIsalive;
    public int unHandleMessageNum = 0;
    public String url;

    public void commitUT() {
        String str;
        String str2;
        String str3;
        long currentTimeMillis = System.currentTimeMillis();
        if (ALog.isPrintLog(Level.D)) {
            StringBuilder sb = new StringBuilder("commitUT interval:");
            sb.append(currentTimeMillis - this.lastCommitTime);
            sb.append(" interval1:");
            sb.append(currentTimeMillis - this.startServiceTime);
            ALog.d(TAG, sb.toString(), new Object[0]);
        }
        if (currentTimeMillis - this.lastCommitTime > COMMIT_INTERVAL && currentTimeMillis - this.startServiceTime > 60000) {
            HashMap hashMap = new HashMap();
            try {
                str3 = String.valueOf(this.messageNum);
                try {
                    str2 = String.valueOf(this.unHandleMessageNum);
                    str = "221";
                } catch (Throwable th) {
                    th = th;
                    str2 = null;
                    str = str2;
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(UTMini.getCommitInfo(66001, str3, str2, str, (Map<String, String>) hashMap));
                    sb2.append(Token.SEPARATOR);
                    sb2.append(th.toString());
                    ALog.d(TAG, sb2.toString(), new Object[0]);
                }
                try {
                    hashMap.put("connStatus", String.valueOf(this.status));
                    hashMap.put("connType", String.valueOf(this.connType));
                    hashMap.put("tcpConnected", String.valueOf(this.tcpConnected));
                    hashMap.put(CommonUtils.APN_PROP_PROXY, String.valueOf(this.proxy));
                    hashMap.put("startServiceTime", String.valueOf(this.startServiceTime));
                    hashMap.put("commitTime", String.valueOf(currentTimeMillis));
                    hashMap.put("networkAvailable", String.valueOf(this.networkAvailable));
                    hashMap.put("threadIsalive", String.valueOf(this.threadIsalive));
                    hashMap.put("url", this.url);
                    if (ALog.isPrintLog(Level.D)) {
                        ALog.d(TAG, UTMini.getCommitInfo(66001, str3, str2, str, (Map<String, String>) hashMap), new Object[0]);
                    }
                    UTMini.getInstance().commitEvent(66001, (String) PAGE, (Object) str3, (Object) str2, (Object) str, (Map<String, String>) hashMap);
                    this.lastCommitTime = currentTimeMillis;
                } catch (Throwable th2) {
                    th = th2;
                    StringBuilder sb22 = new StringBuilder();
                    sb22.append(UTMini.getCommitInfo(66001, str3, str2, str, (Map<String, String>) hashMap));
                    sb22.append(Token.SEPARATOR);
                    sb22.append(th.toString());
                    ALog.d(TAG, sb22.toString(), new Object[0]);
                }
            } catch (Throwable th3) {
                th = th3;
                str3 = null;
                str2 = null;
                str = str2;
                StringBuilder sb222 = new StringBuilder();
                sb222.append(UTMini.getCommitInfo(66001, str3, str2, str, (Map<String, String>) hashMap));
                sb222.append(Token.SEPARATOR);
                sb222.append(th.toString());
                ALog.d(TAG, sb222.toString(), new Object[0]);
            }
        }
    }
}
