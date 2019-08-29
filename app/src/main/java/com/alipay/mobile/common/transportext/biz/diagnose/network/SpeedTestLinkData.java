package com.alipay.mobile.common.transportext.biz.diagnose.network;

import com.autonavi.common.SuperId;

public class SpeedTestLinkData {
    public String channel = null;
    public float connTime = 0.0f;
    public String data = null;
    public String describe = null;
    public int errCode = -2;
    public String ip = null;
    public int port = -1;
    public String result = SuperId.BIT_1_MAIN_BUSSTATION;
    public float rtt = 0.0f;
    public float sslTime = 0.0f;

    public String toString() {
        return ((((((((this.ip == null ? "" : this.ip) + ";" + this.port) + ";" + (this.result == null ? SuperId.BIT_1_MAIN_BUSSTATION : this.result)) + ";" + (this.describe == null ? "-" : this.describe)) + ";" + (this.channel == null ? "" : this.channel)) + ";" + this.connTime) + ";" + this.sslTime) + ";" + this.rtt) + ";" + this.errCode;
    }
}
