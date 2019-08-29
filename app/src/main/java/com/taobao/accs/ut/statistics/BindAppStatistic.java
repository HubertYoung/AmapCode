package com.taobao.accs.ut.statistics;

import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.autonavi.common.SuperId;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

public class BindAppStatistic implements UTInterface {
    private static final String TAG = "BindAppStatistic";
    private final String PAGE_NAME = "BindApp";
    public String deviceId;
    public String failReason;
    private boolean isCommitted = false;
    public boolean ret;
    public String time;

    public void setFailReason(String str) {
        this.failReason = str;
    }

    public void setFailReason(int i) {
        if (i == 200) {
            return;
        }
        if (i != 300) {
            switch (i) {
                case -4:
                    setFailReason((String) "msg too large");
                    return;
                case -3:
                    setFailReason((String) "service not available");
                    return;
                case -2:
                    setFailReason((String) "param error");
                    return;
                case -1:
                    setFailReason((String) "network fail");
                    return;
                default:
                    setFailReason(String.valueOf(i));
                    return;
            }
        } else {
            setFailReason((String) "app not bind");
        }
    }

    public void commitUT() {
        commit("BindApp");
    }

    private void commit(String str) {
        String str2;
        String str3;
        if (!this.isCommitted) {
            this.isCommitted = true;
            HashMap hashMap = new HashMap();
            try {
                str3 = this.deviceId;
                str2 = "221";
                try {
                    hashMap.put("device_id", this.deviceId);
                    hashMap.put("bind_date", this.time);
                    hashMap.put("ret", this.ret ? DictionaryKeys.CTRLXY_Y : SuperId.BIT_1_MAIN_BUSSTATION);
                    hashMap.put("fail_reasons", this.failReason);
                    hashMap.put("push_token", "");
                    UTMini.getInstance().commitEvent(66001, str, (Object) str3, (Object) null, (Object) str2, (Map<String, String>) hashMap);
                } catch (Throwable th) {
                    th = th;
                    StringBuilder sb = new StringBuilder();
                    sb.append(UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap));
                    sb.append(Token.SEPARATOR);
                    sb.append(th.toString());
                    ALog.d(TAG, sb.toString(), new Object[0]);
                }
            } catch (Throwable th2) {
                th = th2;
                str3 = null;
                str2 = null;
                StringBuilder sb2 = new StringBuilder();
                sb2.append(UTMini.getCommitInfo(66001, str3, (String) null, str2, (Map<String, String>) hashMap));
                sb2.append(Token.SEPARATOR);
                sb2.append(th.toString());
                ALog.d(TAG, sb2.toString(), new Object[0]);
            }
        }
    }
}
