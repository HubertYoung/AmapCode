package org.android.spdy;

public class SuperviseConnectInfo {
    public int connectTime;
    public int doHandshakeTime;
    public int handshakeTime;
    public int keepalive_period_second;
    public double lossRate;
    long quicConnectionID = 0;
    public double retransmissionRate;
    public int retryTimes;
    public int reused_counter;
    public int rtoCount;
    public int sessionTicketReused;
    public int timeout;
    public int tlpCount;

    SuperviseConnectInfo() {
    }
}
