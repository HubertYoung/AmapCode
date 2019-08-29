package com.alipay.mobile.mrtc.api.report;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatisticsData {
    public long callDuration;
    public Map<String, String> extras;
    public boolean isP2P;
    public String localIp;
    public long recvAudioBytes;
    public long recvAudioPackgs;
    public long recvVideoBytes;
    public long recvVideoPackgs;
    public String remoteIp;
    public int result;
    public long sentAudioBytes;
    public long sentAudioPackgs;
    public int sentMaxRtt;
    public int sentMinRtt;
    public long sentVideoBytes;
    public long sentVideoPackgs;
    public List<String> stunServers;
    private int tempsentMaxRtt1;
    private int tempsentMaxRtt2;
    public List<String> turnServers;

    public void setSentRtt(int rtt) {
        if (this.sentMinRtt == 0 || this.sentMaxRtt == 0) {
            this.sentMinRtt = rtt;
            this.sentMaxRtt = rtt;
            return;
        }
        this.sentMinRtt = Math.min(this.sentMinRtt, rtt);
        if (this.tempsentMaxRtt1 < rtt) {
            this.sentMaxRtt = this.tempsentMaxRtt2;
            this.tempsentMaxRtt2 = this.tempsentMaxRtt1;
            this.tempsentMaxRtt1 = rtt;
        } else if (this.tempsentMaxRtt2 < rtt) {
            this.sentMaxRtt = this.tempsentMaxRtt2;
            this.tempsentMaxRtt2 = rtt;
        } else if (this.sentMaxRtt < rtt) {
            this.sentMaxRtt = rtt;
        }
    }

    public void addTurnServer(String server) {
        if (!TextUtils.isEmpty(server)) {
            if (this.turnServers == null) {
                this.turnServers = new ArrayList();
            }
            List<String> list = this.turnServers;
            if (server.startsWith("turn:")) {
                server = server.substring(5);
            }
            list.add(server);
        }
    }

    public void addStunServer(String server) {
        if (!TextUtils.isEmpty(server)) {
            if (this.stunServers == null) {
                this.stunServers = new ArrayList();
            }
            List<String> list = this.stunServers;
            if (server.startsWith("stun:")) {
                server = server.substring(5);
            }
            list.add(server);
        }
    }

    public String toString() {
        return "StatisticsData{result=" + this.result + ", localIp='" + this.localIp + '\'' + ", remoteIp='" + this.remoteIp + '\'' + ", turnServers=" + this.turnServers + ", stunServers=" + this.stunServers + ", isP2P='" + this.isP2P + '\'' + ", sentAudioPackgs=" + this.sentAudioPackgs + ", sentVideoPackgs=" + this.sentVideoPackgs + ", sentAudioBytes=" + this.sentAudioBytes + ", sentVideoBytes=" + this.sentVideoBytes + ", recvAudioPackgs=" + this.recvAudioPackgs + ", recvVideoPackgs=" + this.recvVideoPackgs + ", recvAudioBytes=" + this.recvAudioBytes + ", recvVideoBytes=" + this.recvVideoBytes + ", callDuration=" + this.callDuration + ", sentMinRtt=" + this.sentMinRtt + ", tempsentMaxRtt1=" + this.tempsentMaxRtt1 + ", tempsentMaxRtt2=" + this.tempsentMaxRtt2 + ", sentMaxRtt=" + this.sentMaxRtt + ", extras=" + this.extras + '}';
    }
}
