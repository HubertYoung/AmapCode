package com.amap.location.protocol.a;

import android.location.Location;
import android.support.annotation.NonNull;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.protocol.e.e;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RecentLocCache */
public class a extends ArrayList<AmapLoc> {
    private static a b;
    private int a = 5;

    public static synchronized a a() {
        a aVar;
        synchronized (a.class) {
            try {
                if (b == null) {
                    b = new a();
                }
                aVar = b;
            }
        }
        return aVar;
    }

    private a() {
        super(5);
    }

    /* renamed from: a */
    public synchronized boolean add(@NonNull AmapLoc amapLoc) {
        return b(amapLoc);
    }

    public synchronized boolean a(@NonNull Location location) {
        AmapLoc amapLoc;
        amapLoc = new AmapLoc();
        amapLoc.setProvider(location.getProvider());
        amapLoc.setRetype("0");
        amapLoc.setTime(System.currentTimeMillis());
        amapLoc.setLon(location.getLongitude());
        amapLoc.setLat(location.getLatitude());
        amapLoc.setAccuracy(location.getAccuracy());
        return b(amapLoc);
    }

    private boolean b(AmapLoc amapLoc) {
        if (!c(amapLoc)) {
            return false;
        }
        super.add(0, amapLoc);
        b(this.a);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:14:0x001a, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(int r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            if (r3 >= 0) goto L_0x0005
            monitor-exit(r2)
            return
        L_0x0005:
            int r0 = r2.size()     // Catch:{ all -> 0x001b }
            if (r0 <= r3) goto L_0x0019
            int r0 = r0 + -1
        L_0x000d:
            if (r0 < 0) goto L_0x0019
            int r1 = r3 + -1
            if (r0 <= r1) goto L_0x0019
            r2.remove(r0)     // Catch:{ all -> 0x001b }
            int r0 = r0 + -1
            goto L_0x000d
        L_0x0019:
            monitor-exit(r2)
            return
        L_0x001b:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.protocol.a.a.b(int):void");
    }

    public synchronized List<AmapLoc> a(int i) {
        ArrayList arrayList;
        arrayList = new ArrayList();
        int i2 = 0;
        int i3 = 0;
        while (i2 < size()) {
            AmapLoc amapLoc = (AmapLoc) get(i2);
            if (c(amapLoc)) {
                if (i3 >= i) {
                    break;
                }
                arrayList.add(amapLoc);
                i3++;
            } else {
                remove(amapLoc);
                i2--;
            }
            i2++;
        }
        return arrayList;
    }

    private boolean c(AmapLoc amapLoc) {
        if (amapLoc.getAccuracy() <= 100.0f && e.a() - amapLoc.getTime() <= StatisticConfig.MIN_UPLOAD_INTERVAL) {
            return true;
        }
        return false;
    }
}
