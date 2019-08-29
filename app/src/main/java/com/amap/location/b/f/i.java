package com.amap.location.b.f;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.amap.location.b.c.l;
import com.amap.location.common.d.a;
import com.amap.location.common.f.h;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/* compiled from: WifiUtil */
public class i {
    public static void a(@NonNull List<l> list, @Nullable List<ScanResult> list2) {
        list.clear();
        if (list2 != null) {
            List<ScanResult> a = a(b(list2));
            int size = a.size();
            if (size > 40) {
                size = 40;
            }
            for (int i = 0; i < size; i++) {
                ScanResult scanResult = a.get(i);
                if (scanResult != null) {
                    l lVar = new l();
                    lVar.a = h.a(scanResult.BSSID);
                    lVar.b = (short) scanResult.level;
                    lVar.c = scanResult.SSID != null ? scanResult.SSID.substring(0, Math.min(32, scanResult.SSID.length())) : "";
                    lVar.f = (short) scanResult.frequency;
                    if (VERSION.SDK_INT >= 17) {
                        lVar.e = scanResult.timestamp / 1000;
                        lVar.d = (short) ((int) ((SystemClock.elapsedRealtime() - lVar.e) / 1000));
                        if (lVar.d < 0) {
                            lVar.d = 0;
                        }
                    }
                    list.add(lVar);
                }
            }
        }
    }

    private static List<ScanResult> b(@NonNull List<ScanResult> list) {
        ArrayList arrayList = new ArrayList();
        HashMap hashMap = new HashMap();
        for (int i = 0; i < list.size(); i++) {
            ScanResult scanResult = list.get(i);
            hashMap.put(Integer.valueOf(scanResult.level), scanResult);
        }
        arrayList.addAll(hashMap.values());
        return arrayList;
    }

    public static boolean a(@Nullable List<ScanResult> list, @Nullable List<ScanResult> list2, double d) {
        if (list == null || list2 == null) {
            return false;
        }
        int size = list.size();
        int size2 = list2.size();
        int i = size + size2;
        if (size <= size2) {
            List<ScanResult> list3 = list2;
            list2 = list;
            list = list3;
        }
        HashMap hashMap = new HashMap(list.size());
        for (ScanResult next : list) {
            if (next.BSSID != null) {
                hashMap.put(next.BSSID, Integer.valueOf(1));
            }
        }
        int i2 = 0;
        for (ScanResult next2 : list2) {
            if (!(next2.BSSID == null || ((Integer) hashMap.get(next2.BSSID)) == null)) {
                i2++;
            }
        }
        if (((double) i2) * 2.0d >= ((double) i) * d) {
            return true;
        }
        return false;
    }

    public static List<ScanResult> a(@NonNull List<ScanResult> list) {
        Collections.sort(list, new Comparator<ScanResult>() {
            /* renamed from: a */
            public final int compare(ScanResult scanResult, ScanResult scanResult2) {
                return scanResult2.level - scanResult.level;
            }
        });
        return list;
    }

    public static boolean a(@NonNull Context context) {
        return h.a(context) == 1;
    }

    public static boolean a(@Nullable WifiManager wifiManager) {
        if (wifiManager != null) {
            try {
                if (wifiManager.isWifiEnabled()) {
                    return true;
                }
                if (VERSION.SDK_INT >= 18 && wifiManager.isScanAlwaysAvailable()) {
                    return true;
                }
            } catch (SecurityException e) {
                a.a((Throwable) e);
            } catch (Exception e2) {
                a.a((Throwable) e2);
            }
        }
        return false;
    }
}
