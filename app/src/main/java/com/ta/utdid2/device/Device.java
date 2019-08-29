package com.ta.utdid2.device;

public class Device {
    private String deviceId = "";
    private String imei = "";
    private String imsi = "";
    private long mCheckSum = 0;
    private long mCreateTimestamp = 0;
    private String utdid = "";

    /* access modifiers changed from: 0000 */
    public long getCheckSum() {
        return this.mCheckSum;
    }

    /* access modifiers changed from: 0000 */
    public void setCheckSum(long j) {
        this.mCheckSum = j;
    }

    /* access modifiers changed from: 0000 */
    public long getCreateTimestamp() {
        return this.mCreateTimestamp;
    }

    /* access modifiers changed from: 0000 */
    public void setCreateTimestamp(long j) {
        this.mCreateTimestamp = j;
    }

    public String getImei() {
        return this.imei;
    }

    /* access modifiers changed from: 0000 */
    public void setImei(String str) {
        this.imei = str;
    }

    public String getImsi() {
        return this.imsi;
    }

    /* access modifiers changed from: 0000 */
    public void setImsi(String str) {
        this.imsi = str;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    /* access modifiers changed from: 0000 */
    public void setDeviceId(String str) {
        this.deviceId = str;
    }

    public String getUtdid() {
        return this.utdid;
    }

    /* access modifiers changed from: 0000 */
    public void setUtdid(String str) {
        this.utdid = str;
    }
}
