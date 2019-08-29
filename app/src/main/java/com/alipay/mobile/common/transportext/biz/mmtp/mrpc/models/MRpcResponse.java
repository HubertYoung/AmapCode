package com.alipay.mobile.common.transportext.biz.mmtp.mrpc.models;

import java.util.HashMap;
import java.util.Map;

public class MRpcResponse {
    public int airTime;
    public int amnetAllTime;
    public int amnetEncodeTime;
    public int amnetHungTime;
    public int amnetStalledTime;
    public int amnetWaitTiming;
    public byte[] body;
    public long cid;
    public String clientIp = "";
    public int ctjOutTime;
    public int dnsTiming;
    public Map<String, String> extMap;
    public Map<String, String> headers;
    public int ipcP2m;
    public boolean isFlexible = false;
    public boolean isOnShort;
    public boolean isUseBifrost = false;
    public boolean isUseHttp2 = false;
    public int jtcTiming;
    public String mtag;
    public int ntIOTime;
    public int qoeCur = -1;
    public String queneStorage;
    public int queueOutTime;
    public int readTiming;
    public int reqSize;
    public int reqZipType = -1;
    public int respSize;
    public int resultCode;
    public String resultMsg;
    public boolean retried;
    public int rspZipType = -1;
    public int saTime;
    public int sslNtv;
    public int sslTiming;
    public int streamId;
    public String targetHost = "";
    public String targetHostShort;
    public int tcpNtv;
    public int tcpTiming;
    public boolean useShort;

    public String toString() {
        return "MRpcResponse [headers=" + (this.headers != null ? this.headers : "is null") + ", body=" + (this.body != null ? new String(this.body) : "is null") + ", resultCode=" + this.resultCode + ", resultMsg=" + (this.resultMsg != null ? this.resultMsg : "is null") + ", extMap=" + (this.extMap != null ? this.extMap : "is null") + "]";
    }

    public void addExtInfo(String name, String value) {
        getExtMap().put(name, value);
    }

    public Map<String, String> getExtMap() {
        if (this.extMap == null) {
            this.extMap = new HashMap(3);
        }
        return this.extMap;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }
}
