package com.alipay.mobile.common.transport.utils;

public class TcpInfo {
    public long advmss;
    public int backoff;
    public int caState;
    public long fackets;
    public long lost;
    public long msAto;
    public long msLastAckRecv;
    public long msLastAckSent;
    public long msLastDataRecv;
    public long msLastDataSent;
    public long msRcvRtt;
    public long msRto;
    public long msRtt;
    public long msRttVar;
    public int options;
    public long pmtu;
    public int probes;
    public long rcvMss;
    public long rcvSpace;
    public long rcvSsthresh;
    public int rcvWscale;
    public long reordering;
    public long retrans;
    public int retransmits;
    public long sacked;
    public long siocoutq;
    public long sndCwnd;
    public long sndMss;
    public long sndSsthresh;
    public int sndWscale;
    public int state;
    public long totalRetrans;
    public long unacked;

    public String toString() {
        return "TcpInfo{siocoutq=" + this.siocoutq + ", state=" + this.state + ", caState=" + this.caState + ", retransmits=" + this.retransmits + ", probes=" + this.probes + ", backoff=" + this.backoff + ", options=" + this.options + ", sndWscale=" + this.sndWscale + ", rcvWscale=" + this.rcvWscale + ", msRto=" + this.msRto + ", msAto=" + this.msAto + ", sndMss=" + this.sndMss + ", rcvMss=" + this.rcvMss + ", unacked=" + this.unacked + ", sacked=" + this.sacked + ", lost=" + this.lost + ", retrans=" + this.retrans + ", fackets=" + this.fackets + ", msLastDataSent=" + this.msLastDataSent + ", msLastAckSent=" + this.msLastAckSent + ", msLastDataRecv=" + this.msLastDataRecv + ", msLastAckRecv=" + this.msLastAckRecv + ", pmtu=" + this.pmtu + ", rcvSsthresh=" + this.rcvSsthresh + ", msRtt=" + this.msRtt + ", msRttVar=" + this.msRttVar + ", sndSsthresh=" + this.sndSsthresh + ", sndCwnd=" + this.sndCwnd + ", advmss=" + this.advmss + ", reordering=" + this.reordering + ", msRcvRtt=" + this.msRcvRtt + ", rcvSpace=" + this.rcvSpace + ", totalRetrans=" + this.totalRetrans + '}';
    }

    public static TcpInfo convert(String tcpInfoStr) {
        if (tcpInfoStr == null) {
            return null;
        }
        String[] strs = tcpInfoStr.split(";");
        if (strs == null || strs.length != 33) {
            return null;
        }
        try {
            TcpInfo tcpInfo = new TcpInfo();
            try {
                tcpInfo.siocoutq = Long.parseLong(strs[0]);
                tcpInfo.state = Integer.parseInt(strs[1]);
                tcpInfo.caState = Integer.parseInt(strs[2]);
                tcpInfo.retransmits = Integer.parseInt(strs[3]);
                tcpInfo.probes = Integer.parseInt(strs[4]);
                tcpInfo.backoff = Integer.parseInt(strs[5]);
                tcpInfo.options = Integer.parseInt(strs[6]);
                tcpInfo.sndWscale = Integer.parseInt(strs[7]);
                tcpInfo.rcvWscale = Integer.parseInt(strs[8]);
                tcpInfo.msRto = Long.parseLong(strs[9]);
                tcpInfo.msAto = Long.parseLong(strs[10]);
                tcpInfo.sndMss = Long.parseLong(strs[11]);
                tcpInfo.rcvMss = Long.parseLong(strs[12]);
                tcpInfo.unacked = Long.parseLong(strs[13]);
                tcpInfo.sacked = Long.parseLong(strs[14]);
                tcpInfo.lost = Long.parseLong(strs[15]);
                tcpInfo.retrans = Long.parseLong(strs[16]);
                tcpInfo.fackets = Long.parseLong(strs[17]);
                tcpInfo.msLastDataSent = Long.parseLong(strs[18]);
                tcpInfo.msLastAckSent = Long.parseLong(strs[19]);
                tcpInfo.msLastDataRecv = Long.parseLong(strs[20]);
                tcpInfo.msLastAckRecv = Long.parseLong(strs[21]);
                tcpInfo.pmtu = Long.parseLong(strs[22]);
                tcpInfo.rcvSsthresh = Long.parseLong(strs[23]);
                tcpInfo.msRtt = Long.parseLong(strs[24]);
                tcpInfo.msRttVar = Long.parseLong(strs[25]);
                tcpInfo.sndSsthresh = Long.parseLong(strs[26]);
                tcpInfo.sndCwnd = Long.parseLong(strs[27]);
                tcpInfo.advmss = Long.parseLong(strs[28]);
                tcpInfo.reordering = Long.parseLong(strs[29]);
                tcpInfo.msRcvRtt = Long.parseLong(strs[30]);
                tcpInfo.rcvSpace = Long.parseLong(strs[31]);
                tcpInfo.totalRetrans = Long.parseLong(strs[32]);
                LogCatUtil.debug("TcpInfo", "convert success. " + tcpInfo.toString());
                return tcpInfo;
            } catch (Throwable th) {
                e = th;
                TcpInfo tcpInfo2 = tcpInfo;
                LogCatUtil.warn((String) "TcpInfo", "convert error. " + e.toString());
                return null;
            }
        } catch (Throwable th2) {
            e = th2;
            LogCatUtil.warn((String) "TcpInfo", "convert error. " + e.toString());
            return null;
        }
    }
}
