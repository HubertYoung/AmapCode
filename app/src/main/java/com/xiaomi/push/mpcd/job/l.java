package com.xiaomi.push.mpcd.job;

import android.net.wifi.ScanResult;
import java.util.Comparator;

class l implements Comparator<ScanResult> {
    final /* synthetic */ k a;

    l(k kVar) {
        this.a = kVar;
    }

    /* renamed from: a */
    public int compare(ScanResult scanResult, ScanResult scanResult2) {
        return scanResult2.level - scanResult.level;
    }
}
