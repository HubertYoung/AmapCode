package com.autonavi.miniapp.monitor.api.analysis.power;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.concurrent.TimeUnit;

public class TrafficConsumeInfo {
    public static final long LOAD_TIME_SCALE = TimeUnit.SECONDS.toMillis(1);
    private static final String STATS_FILE_PATH = "/proc/net/xt_qtaguid/stats";
    private static final String TAG = "TrafficConsumeInfo";
    private static final String WIFILINE = "wlan0";
    private long gprsRxBytes;
    private long gprsTxBytes;
    private long loadSecondTime;
    private long totalRxBytes;
    private long totalTxBytes;
    private int version = 1;
    private long wifiRxBytes;
    private long wifiTxBytes;

    public void load(boolean z) {
    }

    public long getLoadSecondTime() {
        return this.loadSecondTime;
    }

    public void setLoadSecondTime(long j) {
        this.loadSecondTime = j;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public TrafficConsumeInfo(Context context) {
    }

    public void setTotalTxBytes(long j) {
        this.totalTxBytes = j;
    }

    public void setTotalRxBytes(long j) {
        this.totalRxBytes = j;
    }

    public void setWifiTxBytes(long j) {
        this.wifiTxBytes = j;
    }

    public void setWifiRxBytes(long j) {
        this.wifiRxBytes = j;
    }

    public void setGprsTxBytes(long j) {
        this.gprsTxBytes = j;
    }

    public void setGprsRxBytes(long j) {
        this.gprsRxBytes = j;
    }

    public long getTotalTxBytes() {
        return this.totalTxBytes;
    }

    public long getTotalRxBytes() {
        return this.totalRxBytes;
    }

    public long getWifiTxBytes() {
        return this.wifiTxBytes;
    }

    public long getWifiRxBytes() {
        return this.wifiRxBytes;
    }

    public long getGprsTxBytes() {
        return this.gprsTxBytes;
    }

    public long getGprsRxBytes() {
        return this.gprsRxBytes;
    }

    public long calcTotalBytes() {
        return this.totalTxBytes + this.totalRxBytes;
    }

    public long calcWifiBytes() {
        return this.wifiTxBytes + this.wifiRxBytes;
    }

    public long calcGprsBytes() {
        return this.gprsTxBytes + this.gprsRxBytes;
    }

    public long calcRxBytes() {
        return this.wifiRxBytes + this.gprsRxBytes > 0 ? this.wifiRxBytes + this.gprsRxBytes : this.totalRxBytes;
    }

    public long calcTxBytes() {
        return this.wifiTxBytes + this.gprsTxBytes > 0 ? this.wifiTxBytes + this.gprsTxBytes : this.totalTxBytes;
    }

    public void subtraction(TrafficConsumeInfo trafficConsumeInfo) {
        if (trafficConsumeInfo != null) {
            this.loadSecondTime -= trafficConsumeInfo.getLoadSecondTime();
            this.totalRxBytes -= trafficConsumeInfo.getTotalRxBytes();
            this.totalTxBytes -= trafficConsumeInfo.getTotalTxBytes();
            this.wifiRxBytes -= trafficConsumeInfo.getWifiRxBytes();
            this.wifiTxBytes -= trafficConsumeInfo.getWifiTxBytes();
            this.gprsRxBytes -= trafficConsumeInfo.getGprsRxBytes();
            this.gprsTxBytes -= trafficConsumeInfo.getGprsTxBytes();
        }
    }

    public void readFromSP(SharedPreferences sharedPreferences) {
        if (sharedPreferences != null) {
            this.loadSecondTime = sharedPreferences.getLong("loadSecondTime", 0);
            this.version = sharedPreferences.getInt("version", 0);
            this.totalTxBytes = sharedPreferences.getLong("totalTxBytes", 0);
            this.totalRxBytes = sharedPreferences.getLong("totalRxBytes", 0);
            this.wifiTxBytes = sharedPreferences.getLong("wifiTxBytes", 0);
            this.wifiRxBytes = sharedPreferences.getLong("wifiRxBytes", 0);
            this.gprsTxBytes = sharedPreferences.getLong("gprsTxBytes", 0);
            this.gprsRxBytes = sharedPreferences.getLong("gprsRxBytes", 0);
        }
    }

    public void writeToSP(Editor editor) {
        if (editor != null) {
            editor.putLong("loadSecondTime", this.loadSecondTime);
            editor.putInt("version", this.version);
            editor.putLong("totalTxBytes", this.totalTxBytes);
            editor.putLong("totalRxBytes", this.totalRxBytes);
            editor.putLong("wifiTxBytes", this.wifiTxBytes);
            editor.putLong("wifiRxBytes", this.wifiRxBytes);
            editor.putLong("gprsTxBytes", this.gprsTxBytes);
            editor.putLong("gprsRxBytes", this.gprsRxBytes);
            editor.commit();
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("version = ");
        sb.append(this.version);
        sb.append(", loadSecondTime = ");
        sb.append(this.loadSecondTime);
        sb.append(", totalTxBytes = ");
        sb.append(this.totalTxBytes);
        sb.append(", totalRxBytes = ");
        sb.append(this.totalRxBytes);
        sb.append(", wifiTxBytes = ");
        sb.append(this.wifiTxBytes);
        sb.append(", wifiRxBytes = ");
        sb.append(this.wifiRxBytes);
        sb.append(", gprsTxBytes = ");
        sb.append(this.gprsTxBytes);
        sb.append(", gprsRxBytes = ");
        sb.append(this.gprsRxBytes);
        return sb.toString();
    }
}
