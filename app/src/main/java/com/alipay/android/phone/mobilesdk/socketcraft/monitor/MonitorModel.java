package com.alipay.android.phone.mobilesdk.socketcraft.monitor;

import com.alipay.android.phone.mobilesdk.socketcraft.util.StringUtils;
import java.util.HashMap;
import java.util.Map;

public class MonitorModel {
    public Map<String, String> itemMap = new HashMap();
    public String logTitle;

    public MonitorModel() {
        this.itemMap.put(MonitorItemConstants.KEY_VER, "17");
    }

    public MonitorModel appendAppId(String appId) {
        this.itemMap.put(MonitorItemConstants.KEY_APP_ID, appId);
        return this;
    }

    public MonitorModel appendUrl(String url) {
        this.itemMap.put(MonitorItemConstants.KEY_URL, url);
        return this;
    }

    public MonitorModel appendResult(boolean suc) {
        this.itemMap.put("RESULT", suc ? "T" : "F");
        return this;
    }

    public MonitorModel appendAllTime(String allTime) {
        this.itemMap.put("ALL_TIME", allTime);
        return this;
    }

    public MonitorModel appendCode(String code) {
        this.itemMap.put(MonitorItemConstants.KEY_CODE, code);
        return this;
    }

    public MonitorModel appendErrMsg(String errMsg) {
        this.itemMap.put(MonitorItemConstants.KEY_ERR_MSG, errMsg);
        return this;
    }

    public MonitorModel appendLiveTime(String liveTime) {
        this.itemMap.put(MonitorItemConstants.KEY_LIVE_TIME, liveTime);
        return this;
    }

    public MonitorModel appendUpMsgCount(String upMsgCount) {
        if (!StringUtils.isEmpty(upMsgCount)) {
            this.itemMap.put(MonitorItemConstants.KEY_UPC, upMsgCount);
        }
        return this;
    }

    public MonitorModel appendDownMsgCount(String downMsgCount) {
        if (!StringUtils.isEmpty(downMsgCount)) {
            this.itemMap.put(MonitorItemConstants.KEY_DOWNC, downMsgCount);
        }
        return this;
    }

    public MonitorModel appendUpMsgLens(String upMsgLens) {
        if (!StringUtils.isEmpty(upMsgLens)) {
            this.itemMap.put(MonitorItemConstants.KEY_UPMSG, upMsgLens);
        }
        return this;
    }

    public MonitorModel appendDownMsgLens(String downMsgLens) {
        if (!StringUtils.isEmpty(downMsgLens)) {
            this.itemMap.put(MonitorItemConstants.KEY_DOWNMSG, downMsgLens);
        }
        return this;
    }

    public MonitorModel appendDnsTime(String dnsTime) {
        if (!StringUtils.isEmpty(dnsTime) && !"-1".equals(dnsTime)) {
            this.itemMap.put("DNS_TIME", dnsTime);
        }
        return this;
    }

    public MonitorModel appendTcpTime(String tcpTime) {
        if (!StringUtils.isEmpty(tcpTime) && !"-1".equals(tcpTime)) {
            this.itemMap.put("TCP_TIME", tcpTime);
        }
        return this;
    }

    public MonitorModel appendSSLTime(String sslTime) {
        if (!StringUtils.isEmpty(sslTime) && !"-1".equals(sslTime)) {
            this.itemMap.put("SSL_TIME", sslTime);
        }
        return this;
    }

    public MonitorModel appendWsHsTime(String hsTime) {
        if (!StringUtils.isEmpty(hsTime) && !"-1".equals(hsTime)) {
            this.itemMap.put(MonitorItemConstants.KEY_WS_HS_TIME, hsTime);
        }
        return this;
    }

    public MonitorModel appendTargetHost(String targetHost) {
        if (!StringUtils.isEmpty(targetHost) && !"null".equals(targetHost)) {
            this.itemMap.put("TARGET_HOST", targetHost);
        }
        return this;
    }

    public String toString() {
        return "MonitorModel{logTitle='" + this.logTitle + '\'' + ", itemMap=" + this.itemMap + '}';
    }
}
