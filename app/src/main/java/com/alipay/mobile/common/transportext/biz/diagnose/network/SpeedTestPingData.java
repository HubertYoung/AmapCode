package com.alipay.mobile.common.transportext.biz.diagnose.network;

public class SpeedTestPingData {
    public String data = null;
    public String ip = null;
    public int seq = -1;
    public float time = 0.0f;
    public int ttl = 0;

    public String toString() {
        return (((this.seq) + ";" + (this.ip == null ? "" : this.ip)) + ";" + this.ttl) + ";" + this.time;
    }
}
