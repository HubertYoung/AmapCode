package com.alipay.android.phone.mobilesdk.socketcraft.monitor;

public class SimpleStatistical {
    public long connectedTime = 0;
    public long disconnectedTime = 0;
    public long dnsTime = -1;
    public long endConnAllTime = 0;
    public long recvMsgCount = 0;
    public String recvMsgLenArray = "";
    public long sendMsgCount = 0;
    public String sendMsgLenArray = "";
    public long sslTime = -1;
    public long startConnAllTime = 0;
    public String targetHost = "";
    public long tcpTime = -1;
    public long wsHsTime = -1;

    public long getConnAllTime() {
        if (this.startConnAllTime <= 0 || this.endConnAllTime <= this.startConnAllTime) {
            return -1;
        }
        return this.endConnAllTime - this.startConnAllTime;
    }

    public long getLinkLiveTime() {
        if (this.connectedTime <= 0 || this.disconnectedTime <= this.connectedTime) {
            return -1;
        }
        return this.disconnectedTime - this.connectedTime;
    }
}
