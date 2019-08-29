package com.taobao.accs.ut.statistics;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

public class SendAckStatistic implements UTInterface {
    private static final String TAG = "accs.SendAckStatistic";
    private final String PAGE_NAME = "sendAck";
    public String dataId;
    public String deviceId;
    public String failReason;
    private boolean isCommitted = false;
    public String sendTime;
    public String serviceId;
    public String sessionId;

    public void commitUT() {
        String str;
        String str2;
        if (!this.isCommitted) {
            this.isCommitted = true;
            HashMap hashMap = new HashMap();
            try {
                str2 = this.deviceId;
                str = "221";
                try {
                    hashMap.put("device_id", this.deviceId);
                    hashMap.put("session_id", this.sessionId);
                    hashMap.put("data_id", this.dataId);
                    hashMap.put("ack_date", this.sendTime);
                    hashMap.put("service_id", this.serviceId);
                    hashMap.put("fail_reasons", this.failReason);
                    UTMini.getInstance().commitEvent(66001, (String) "sendAck", (Object) str2, (Object) null, (Object) str, (Map<String, String>) hashMap);
                } catch (Throwable th) {
                    th = th;
                    StringBuilder sb = new StringBuilder();
                    sb.append(UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap));
                    sb.append(Token.SEPARATOR);
                    sb.append(th.toString());
                    ALog.d(TAG, sb.toString(), new Object[0]);
                }
            } catch (Throwable th2) {
                th = th2;
                str2 = null;
                str = null;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(UTMini.getCommitInfo(66001, str2, (String) null, str, (Map<String, String>) hashMap));
                sb2.append(Token.SEPARATOR);
                sb2.append(th.toString());
                ALog.d(TAG, sb2.toString(), new Object[0]);
            }
        }
    }
}
