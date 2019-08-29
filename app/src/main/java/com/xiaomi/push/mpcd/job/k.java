package com.xiaomi.push.mpcd.job;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.alipay.mobile.monitor.track.spm.merge.MergeUtil;
import com.xiaomi.channel.commonutils.misc.c;
import com.xiaomi.xmpush.thrift.d;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class k extends f {
    private Comparator<ScanResult> a = new l(this);

    public k(Context context, int i) {
        super(context, i);
    }

    public int a() {
        return 8;
    }

    public String b() {
        StringBuilder sb = new StringBuilder();
        try {
            WifiManager wifiManager = (WifiManager) this.d.getSystemService("wifi");
            if (wifiManager.isWifiEnabled()) {
                WifiInfo connectionInfo = wifiManager.getConnectionInfo();
                if (connectionInfo != null) {
                    sb.append(connectionInfo.getSSID());
                    sb.append(",");
                    sb.append(connectionInfo.getBSSID());
                    sb.append(MergeUtil.SEPARATOR_KV);
                }
            }
            List<ScanResult> scanResults = wifiManager.getScanResults();
            if (!c.a(scanResults)) {
                Collections.sort(scanResults, this.a);
                for (int i = 0; i < Math.min(5, scanResults.size()); i++) {
                    ScanResult scanResult = scanResults.get(i);
                    if (i > 0) {
                        sb.append(";");
                    }
                    if (scanResult != null) {
                        sb.append(scanResult.SSID);
                        sb.append(",");
                        sb.append(scanResult.BSSID);
                        sb.append(",");
                        sb.append(scanResult.level);
                    }
                }
            }
        } catch (Throwable unused) {
        }
        return sb.toString();
    }

    public d d() {
        return d.WIFI;
    }
}
