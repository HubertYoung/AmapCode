package com.amap.location.b.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Looper;
import com.amap.location.b.c.l;
import com.amap.location.b.f.i;
import com.amap.location.common.d.a;
import com.amap.location.common.f.h;
import java.util.ArrayList;
import java.util.List;

/* compiled from: WifiCollector */
public class d {
    private Context a;
    private Handler b;
    private WifiManager c;
    private BroadcastReceiver d;
    private long e = -1;
    private List<ScanResult> f = new ArrayList();
    private Location g;
    private ArrayList<l> h = new ArrayList<>();

    public d(Context context, Looper looper) {
        this.a = context;
        this.c = (WifiManager) context.getSystemService("wifi");
        this.b = new Handler(looper);
    }

    public void a() {
        this.d = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                try {
                    d.this.a(intent);
                } catch (Throwable unused) {
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.STATE_CHANGE");
        try {
            this.a.registerReceiver(this.d, intentFilter, null, this.b);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        d();
    }

    public void b() {
        if (this.d != null) {
            try {
                this.a.unregisterReceiver(this.d);
                this.d = null;
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        this.b.removeCallbacksAndMessages(null);
        this.b = null;
    }

    public long c() {
        return this.e;
    }

    public List<l> a(Location location, List<ScanResult> list, long j, long j2) {
        if (!b(location, list, j, j2)) {
            return null;
        }
        i.a(this.h, list);
        this.f.clear();
        this.f.addAll(list);
        this.g = location;
        return this.h;
    }

    private boolean b(Location location, List<ScanResult> list, long j, long j2) {
        boolean z = false;
        if (!i.a(this.c)) {
            return false;
        }
        if (a(location, j, j2) && list != null && list.size() > 0) {
            if (this.g != null) {
                z = a(location);
                if (!z) {
                    z = !i.a(list, this.f, 0.5d);
                }
            } else {
                z = true;
            }
        }
        return z;
    }

    private boolean a(Location location, long j, long j2) {
        return j > 0 && j2 - j < ((long) ((location.getSpeed() > 10.0f ? 1 : (location.getSpeed() == 10.0f ? 0 : -1)) >= 0 ? 2000 : 3500));
    }

    private boolean a(Location location) {
        float f2 = 10.0f;
        if (location.getSpeed() > 10.0f) {
            f2 = 200.0f;
        } else if (location.getSpeed() > 2.0f) {
            f2 = 50.0f;
        }
        return location.distanceTo(this.g) > f2;
    }

    /* access modifiers changed from: private */
    public void a(Intent intent) {
        String action = intent.getAction();
        if (action != null) {
            char c2 = 65535;
            if (action.hashCode() == -343630553 && action.equals("android.net.wifi.STATE_CHANGE")) {
                c2 = 0;
            }
            if (c2 == 0) {
                d();
            }
        }
    }

    private void d() {
        this.e = -1;
        try {
            WifiInfo connectionInfo = this.c == null ? null : this.c.getConnectionInfo();
            if (connectionInfo != null) {
                this.e = h.a(connectionInfo.getBSSID());
            }
        } catch (Throwable th) {
            a.a(th);
        }
    }
}
