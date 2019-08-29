package com.amap.location.common.model;

import android.net.wifi.ScanResult;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.amap.location.common.f.h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class WifiStatus {
    public WiFi mainWifi;
    public long updateTime;
    private List<WiFi> wifiList = Collections.emptyList();

    public WifiStatus() {
    }

    public WifiStatus(long j) {
        this.updateTime = j;
    }

    public WifiStatus(long j, List<ScanResult> list) {
        this.updateTime = j;
        this.wifiList = scanResultList2WifiList(list);
    }

    public WifiStatus(long j, List<WiFi> list, int i) {
        this.updateTime = j;
        this.wifiList = list;
    }

    public final WiFi getWiFi(int i) {
        return this.wifiList.get(i);
    }

    public final int numWiFis() {
        return this.wifiList.size();
    }

    public void setWifiList(List<WiFi> list) {
        this.wifiList = list;
    }

    public List<WiFi> getWifiList() {
        return this.wifiList;
    }

    public List<WiFi> scanResultList2WifiList(List<ScanResult> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<ScanResult> it = list.iterator();
        if (VERSION.SDK_INT >= 17) {
            while (it.hasNext()) {
                ScanResult next = it.next();
                if (next != null) {
                    WiFi wiFi = new WiFi(h.a(next.BSSID), next.SSID, next.level, next.frequency, next.timestamp / 1000);
                    arrayList.add(wiFi);
                }
            }
        } else {
            while (it.hasNext()) {
                ScanResult next2 = it.next();
                if (next2 != null) {
                    WiFi wiFi2 = new WiFi(h.a(next2.BSSID), next2.SSID, next2.level, next2.frequency, SystemClock.elapsedRealtime());
                    arrayList.add(wiFi2);
                }
            }
        }
        return arrayList;
    }

    public WifiStatus clone() {
        WifiStatus wifiStatus = new WifiStatus(this.updateTime);
        if (this.mainWifi != null) {
            wifiStatus.mainWifi = this.mainWifi.clone();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(this.wifiList);
        wifiStatus.wifiList = arrayList;
        return wifiStatus;
    }

    public String toString() {
        return toStr(false);
    }

    public String toStringSimple() {
        return toStr(true);
    }

    private String toStr(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append("wifiStatus:[");
        StringBuilder sb2 = new StringBuilder("updateTime=");
        sb2.append(this.updateTime);
        sb2.append(",");
        sb.append(sb2.toString());
        if (this.mainWifi != null) {
            StringBuilder sb3 = new StringBuilder("mainWifi:[");
            sb3.append(this.mainWifi.toString());
            sb3.append("],");
            sb.append(sb3.toString());
        } else {
            sb.append("mainWifi:[null],");
        }
        if (this.wifiList != null) {
            ArrayList arrayList = new ArrayList();
            if (this.wifiList.size() <= 5) {
                arrayList.addAll(this.wifiList);
                StringBuilder sb4 = new StringBuilder("wifiList=");
                sb4.append(this.wifiList.toString());
                sb.append(sb4.toString());
            } else if (z) {
                arrayList.addAll(this.wifiList.subList(0, 5));
                StringBuilder sb5 = new StringBuilder("wifiList=");
                sb5.append(arrayList.toString());
                sb.append(sb5.toString());
            } else {
                arrayList.addAll(this.wifiList);
                StringBuilder sb6 = new StringBuilder("wifiList=");
                sb6.append(this.wifiList.toString());
                sb.append(sb6.toString());
            }
        } else {
            sb.append("wifiList=0");
        }
        sb.append("]");
        return sb.toString();
    }
}
