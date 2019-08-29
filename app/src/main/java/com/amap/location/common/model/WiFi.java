package com.amap.location.common.model;

import com.alipay.mobile.mrtc.api.constants.APCallCode;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.amap.location.common.f.h;

public class WiFi {
    public boolean connected;
    public int frequency;
    public long lastUpdateUtcMills = 0;
    public long mac;
    public int rssi = APCallCode.CALL_ERROR_INNER;
    public String ssid;
    public long timestamp;
    public int type;

    public WiFi(long j, String str, int i, int i2, long j2) {
        this.mac = j;
        this.ssid = str == null ? "" : str;
        this.rssi = i;
        this.frequency = i2;
        this.timestamp = j2;
    }

    public WiFi(long j, String str, int i, int i2, long j2, boolean z) {
        this.mac = j;
        this.ssid = str == null ? "" : str;
        this.rssi = i;
        this.frequency = i2;
        this.timestamp = j2;
        this.connected = z;
    }

    public WiFi(long j, String str, int i, int i2, long j2, long j3, boolean z, int i3) {
        this.mac = j;
        this.ssid = str == null ? "" : str;
        this.rssi = i;
        this.frequency = i2;
        this.timestamp = j2;
        this.lastUpdateUtcMills = j3;
        this.connected = z;
        this.type = i3;
    }

    public String getKey() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.connected);
        sb.append(MetaRecord.LOG_SEPARATOR);
        sb.append(this.mac);
        return sb.toString();
    }

    public WiFi clone() {
        WiFi wiFi = new WiFi(this.mac, this.ssid, this.rssi, this.frequency, this.timestamp, this.lastUpdateUtcMills, this.connected, this.type);
        return wiFi;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("WiFi:[");
        StringBuilder sb = new StringBuilder("mac:");
        sb.append(h.a(this.mac));
        sb.append(",");
        stringBuffer.append(sb.toString());
        StringBuilder sb2 = new StringBuilder("ssid:");
        sb2.append(this.ssid);
        sb2.append(",");
        stringBuffer.append(sb2.toString());
        StringBuilder sb3 = new StringBuilder("rssi:");
        sb3.append(this.rssi);
        sb3.append(",");
        stringBuffer.append(sb3.toString());
        StringBuilder sb4 = new StringBuilder("freq:");
        sb4.append(this.frequency);
        sb4.append(",");
        stringBuffer.append(sb4.toString());
        StringBuilder sb5 = new StringBuilder("time:");
        sb5.append(this.timestamp);
        sb5.append(",");
        stringBuffer.append(sb5.toString());
        StringBuilder sb6 = new StringBuilder("utc:");
        sb6.append(this.lastUpdateUtcMills);
        sb6.append(",");
        stringBuffer.append(sb6.toString());
        StringBuilder sb7 = new StringBuilder("conn:");
        sb7.append(this.connected);
        sb7.append(",");
        stringBuffer.append(sb7.toString());
        StringBuilder sb8 = new StringBuilder("type:");
        sb8.append(this.type);
        sb8.append(",");
        stringBuffer.append(sb8.toString());
        stringBuffer.append("]");
        return stringBuffer.toString();
    }
}
