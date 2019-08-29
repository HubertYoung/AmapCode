package com.taobao.accs.ut.statistics;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

public class ReceiveMsgStat implements UTInterface {
    private static final String TAG = "ReceiveMessage";
    private final String PAGE_NAME = "receiveMessage";
    public String dataId;
    public String dataLen;
    public String deviceId;
    private boolean isCommitted = false;
    public String messageType;
    public String receiveDate;
    public boolean repeat = false;
    public String serviceId;
    public String toBzDate;
    public String userId;

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
                    hashMap.put("data_id", this.dataId);
                    hashMap.put("receive_date", this.receiveDate);
                    hashMap.put("to_bz_date", this.toBzDate);
                    hashMap.put("service_id", this.serviceId);
                    hashMap.put("data_length", this.dataLen);
                    hashMap.put("msg_type", this.messageType);
                    hashMap.put("repeat", this.repeat ? DictionaryKeys.CTRLXY_Y : SuperId.BIT_1_MAIN_BUSSTATION);
                    hashMap.put("user_id", this.userId);
                    UTMini.getInstance().commitEvent(66001, (String) "receiveMessage", (Object) str2, (Object) null, (Object) str, (Map<String, String>) hashMap);
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
