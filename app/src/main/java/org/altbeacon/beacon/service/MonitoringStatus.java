package org.altbeacon.beacon.service;

import android.content.Context;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;

public class MonitoringStatus {
    private static volatile MonitoringStatus a;
    private static final String b = MonitoringStatus.class.getSimpleName();
    private static final Object f = new Object();
    private Map<Region, i> c;
    private Context d;
    private boolean e = true;

    public static MonitoringStatus a(Context context) {
        MonitoringStatus instance = a;
        if (instance == null) {
            synchronized (f) {
                try {
                    instance = a;
                    if (instance == null) {
                        MonitoringStatus instance2 = new MonitoringStatus(context.getApplicationContext());
                        try {
                            a = instance2;
                            instance = instance2;
                        } catch (Throwable th) {
                            th = th;
                            MonitoringStatus monitoringStatus = instance2;
                            throw th;
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    throw th;
                }
            }
        }
        return instance;
    }

    public MonitoringStatus(Context context) {
        this.d = context;
    }

    public final synchronized void a(Region region, b callback) {
        b(region, callback);
        d();
    }

    public final synchronized void a(Region region) {
        c(region);
        d();
    }

    public final synchronized Set<Region> a() {
        try {
        }
        return h().keySet();
    }

    public final synchronized int b() {
        try {
        }
        return a().size();
    }

    public final synchronized i b(Region region) {
        return h().get(region);
    }

    public final synchronized void c() {
        boolean needsMonitoringStateSaving = false;
        for (Region region : a()) {
            i state = b(region);
            if (state.d()) {
                needsMonitoringStateSaving = true;
                d.a(b, "found a monitor that expired: %s", region);
                state.a();
                b.a(this.d, "monitoringData", new e(state.e(), region).c());
            }
        }
        if (needsMonitoringStateSaving) {
            d();
        } else {
            a(System.currentTimeMillis());
        }
    }

    public final synchronized void a(Beacon beacon) {
        boolean needsMonitoringStateSaving = false;
        for (Region region : b(beacon)) {
            i state = h().get(region);
            if (state != null && state.b()) {
                needsMonitoringStateSaving = true;
                state.a();
                b.a(this.d, "monitoringData", new e(state.e(), region).c());
            }
        }
        if (needsMonitoringStateSaving) {
            d();
        } else {
            a(System.currentTimeMillis());
        }
    }

    private Map<Region, i> h() {
        if (this.c == null) {
            i();
        }
        return this.c;
    }

    private void i() {
        long millisSinceLastMonitor = System.currentTimeMillis() - j();
        this.c = new HashMap();
        if (!this.e) {
            d.a(b, "Not restoring monitoring state because persistence is disabled", new Object[0]);
        } else if (millisSinceLastMonitor > 900000) {
            d.a(b, "Not restoring monitoring state because it was recorded too many milliseconds ago: " + millisSinceLastMonitor, new Object[0]);
        } else {
            k();
            d.a(b, "Done restoring monitoring status", new Object[0]);
        }
    }

    private List<Region> b(Beacon beacon) {
        List matched = new ArrayList();
        for (Region region : a()) {
            if (region.a(beacon)) {
                matched.add(region);
            } else {
                d.a(b, "This region (%s) does not match beacon: %s", region, beacon);
            }
        }
        return matched;
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0076 A[SYNTHETIC, Splitter:B:23:0x0076] */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x007b A[SYNTHETIC, Splitter:B:26:0x007b] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x0099 A[SYNTHETIC, Splitter:B:34:0x0099] */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x009e A[SYNTHETIC, Splitter:B:37:0x009e] */
    /* JADX WARNING: Removed duplicated region for block: B:51:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void d() {
        /*
            r10 = this;
            r9 = 0
            boolean r4 = r10.e
            if (r4 != 0) goto L_0x0006
        L_0x0005:
            return
        L_0x0006:
            java.lang.String r4 = b
            java.lang.String r5 = "saveMonitoringStatusIfOn()"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.a(r4, r5, r6)
            java.util.Map r4 = r10.h()
            int r4 = r4.size()
            r5 = 50
            if (r4 <= r5) goto L_0x002c
            java.lang.String r4 = b
            java.lang.String r5 = "Too many regions being monitored.  Will not persist region state"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.c(r4, r5, r6)
            android.content.Context r4 = r10.d
            java.lang.String r5 = "org.altbeacon.beacon.service.monitoring_status_state"
            r4.deleteFile(r5)
            goto L_0x0005
        L_0x002c:
            r3 = 0
            r1 = 0
            android.content.Context r4 = r10.d     // Catch:{ IOException -> 0x0062 }
            java.lang.String r5 = "org.altbeacon.beacon.service.monitoring_status_state"
            r6 = 0
            java.io.FileOutputStream r3 = r4.openFileOutput(r5, r6)     // Catch:{ IOException -> 0x0062 }
            java.io.ObjectOutputStream r2 = new java.io.ObjectOutputStream     // Catch:{ IOException -> 0x0062 }
            r2.<init>(r3)     // Catch:{ IOException -> 0x0062 }
            java.util.Map r4 = r10.h()     // Catch:{ IOException -> 0x00bb, all -> 0x00b8 }
            r2.writeObject(r4)     // Catch:{ IOException -> 0x00bb, all -> 0x00b8 }
            if (r3 == 0) goto L_0x0048
            r3.close()     // Catch:{ IOException -> 0x0057 }
        L_0x0048:
            r2.close()     // Catch:{ IOException -> 0x004c }
            goto L_0x0005
        L_0x004c:
            r4 = move-exception
            java.lang.String r4 = b
            java.lang.String r5 = "close objectOutputStream exception"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r4, r5, r6)
            goto L_0x0005
        L_0x0057:
            r4 = move-exception
            java.lang.String r4 = b
            java.lang.String r5 = "close outputStream exception"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r4, r5, r6)
            goto L_0x0048
        L_0x0062:
            r0 = move-exception
        L_0x0063:
            java.lang.String r4 = b     // Catch:{ all -> 0x0096 }
            java.lang.String r5 = "Error while saving monitored region states to file. %s "
            r6 = 1
            java.lang.Object[] r6 = new java.lang.Object[r6]     // Catch:{ all -> 0x0096 }
            r7 = 0
            java.lang.String r8 = r0.getMessage()     // Catch:{ all -> 0x0096 }
            r6[r7] = r8     // Catch:{ all -> 0x0096 }
            org.altbeacon.beacon.b.d.d(r4, r5, r6)     // Catch:{ all -> 0x0096 }
            if (r3 == 0) goto L_0x0079
            r3.close()     // Catch:{ IOException -> 0x008b }
        L_0x0079:
            if (r1 == 0) goto L_0x0005
            r1.close()     // Catch:{ IOException -> 0x007f }
            goto L_0x0005
        L_0x007f:
            r4 = move-exception
            java.lang.String r4 = b
            java.lang.String r5 = "close objectOutputStream exception"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r4, r5, r6)
            goto L_0x0005
        L_0x008b:
            r4 = move-exception
            java.lang.String r4 = b
            java.lang.String r5 = "close outputStream exception"
            java.lang.Object[] r6 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r4, r5, r6)
            goto L_0x0079
        L_0x0096:
            r4 = move-exception
        L_0x0097:
            if (r3 == 0) goto L_0x009c
            r3.close()     // Catch:{ IOException -> 0x00a2 }
        L_0x009c:
            if (r1 == 0) goto L_0x00a1
            r1.close()     // Catch:{ IOException -> 0x00ad }
        L_0x00a1:
            throw r4
        L_0x00a2:
            r5 = move-exception
            java.lang.String r5 = b
            java.lang.String r6 = "close outputStream exception"
            java.lang.Object[] r7 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r5, r6, r7)
            goto L_0x009c
        L_0x00ad:
            r5 = move-exception
            java.lang.String r5 = b
            java.lang.String r6 = "close objectOutputStream exception"
            java.lang.Object[] r7 = new java.lang.Object[r9]
            org.altbeacon.beacon.b.d.d(r5, r6, r7)
            goto L_0x00a1
        L_0x00b8:
            r4 = move-exception
            r1 = r2
            goto L_0x0097
        L_0x00bb:
            r0 = move-exception
            r1 = r2
            goto L_0x0063
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.beacon.service.MonitoringStatus.d():void");
    }

    private void a(long time) {
        this.d.getFileStreamPath("org.altbeacon.beacon.service.monitoring_status_state").setLastModified(time);
    }

    private long j() {
        return this.d.getFileStreamPath("org.altbeacon.beacon.service.monitoring_status_state").lastModified();
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0085 A[Catch:{ all -> 0x00fd }] */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0091 A[SYNTHETIC, Splitter:B:15:0x0091] */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0096 A[SYNTHETIC, Splitter:B:18:0x0096] */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00eb A[SYNTHETIC, Splitter:B:48:0x00eb] */
    /* JADX WARNING: Removed duplicated region for block: B:64:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void k() {
        /*
            r13 = this;
            r12 = 0
            r1 = 0
            r3 = 0
            android.content.Context r7 = r13.d     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x012b }
            java.lang.String r8 = "org.altbeacon.beacon.service.monitoring_status_state"
            java.io.FileInputStream r1 = r7.openFileInput(r8)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x012b }
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x012b }
            r4.<init>(r1)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x012b }
            java.lang.Object r2 = r4.readObject()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.util.Map r2 = (java.util.Map) r2     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r7 = b     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r9 = "Restored region monitoring state for "
            r8.<init>(r9)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            int r9 = r2.size()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r9 = " regions."
            java.lang.StringBuilder r8 = r8.append(r9)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r8 = r8.toString()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            org.altbeacon.beacon.b.d.a(r7, r8, r9)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.util.Set r7 = r2.keySet()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
        L_0x003f:
            boolean r8 = r7.hasNext()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            if (r8 == 0) goto L_0x009a
            java.lang.Object r5 = r7.next()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            org.altbeacon.beacon.Region r5 = (org.altbeacon.beacon.Region) r5     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r8 = b     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.StringBuilder r9 = new java.lang.StringBuilder     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r10 = "Region  "
            r9.<init>(r10)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.StringBuilder r9 = r9.append(r5)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r10 = " uniqueId: "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r10 = r5.a()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r10 = " state: "
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.Object r10 = r2.get(r5)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.StringBuilder r9 = r9.append(r10)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.lang.String r9 = r9.toString()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            r10 = 0
            java.lang.Object[] r10 = new java.lang.Object[r10]     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            org.altbeacon.beacon.b.d.a(r8, r9, r10)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            goto L_0x003f
        L_0x007f:
            r0 = move-exception
            r3 = r4
        L_0x0081:
            boolean r7 = r0 instanceof java.io.InvalidClassException     // Catch:{ all -> 0x00fd }
            if (r7 == 0) goto L_0x00eb
            java.lang.String r7 = b     // Catch:{ all -> 0x00fd }
            java.lang.String r8 = "Serialized Monitoring State has wrong class. Just ignoring saved state..."
            r9 = 0
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00fd }
            org.altbeacon.beacon.b.d.a(r7, r8, r9)     // Catch:{ all -> 0x00fd }
        L_0x008f:
            if (r1 == 0) goto L_0x0094
            r1.close()     // Catch:{ IOException -> 0x00ff }
        L_0x0094:
            if (r3 == 0) goto L_0x0099
            r3.close()     // Catch:{ IOException -> 0x010a }
        L_0x0099:
            return
        L_0x009a:
            java.util.Collection r7 = r2.values()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            java.util.Iterator r7 = r7.iterator()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
        L_0x00a2:
            boolean r8 = r7.hasNext()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            if (r8 == 0) goto L_0x00c5
            java.lang.Object r6 = r7.next()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            org.altbeacon.beacon.service.i r6 = (org.altbeacon.beacon.service.i) r6     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            boolean r8 = r6.e()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            if (r8 == 0) goto L_0x00a2
            r6.b()     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            goto L_0x00a2
        L_0x00b8:
            r7 = move-exception
            r3 = r4
        L_0x00ba:
            if (r1 == 0) goto L_0x00bf
            r1.close()     // Catch:{ IOException -> 0x0115 }
        L_0x00bf:
            if (r3 == 0) goto L_0x00c4
            r3.close()     // Catch:{ IOException -> 0x0120 }
        L_0x00c4:
            throw r7
        L_0x00c5:
            java.util.Map<org.altbeacon.beacon.Region, org.altbeacon.beacon.service.i> r7 = r13.c     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            r7.putAll(r2)     // Catch:{ IOException | ClassCastException | ClassNotFoundException -> 0x007f, all -> 0x00b8 }
            if (r1 == 0) goto L_0x00cf
            r1.close()     // Catch:{ IOException -> 0x00d4 }
        L_0x00cf:
            r4.close()     // Catch:{ IOException -> 0x00df }
            r3 = r4
            goto L_0x0099
        L_0x00d4:
            r7 = move-exception
            java.lang.String r7 = b
            java.lang.String r8 = "close inputStream exception"
            java.lang.Object[] r9 = new java.lang.Object[r12]
            org.altbeacon.beacon.b.d.d(r7, r8, r9)
            goto L_0x00cf
        L_0x00df:
            r7 = move-exception
            java.lang.String r7 = b
            java.lang.String r8 = "close objectInputStream exception"
            java.lang.Object[] r9 = new java.lang.Object[r12]
            org.altbeacon.beacon.b.d.d(r7, r8, r9)
            r3 = r4
            goto L_0x0099
        L_0x00eb:
            java.lang.String r7 = b     // Catch:{ all -> 0x00fd }
            java.lang.String r8 = "Deserialization exception, message: %s"
            r9 = 1
            java.lang.Object[] r9 = new java.lang.Object[r9]     // Catch:{ all -> 0x00fd }
            r10 = 0
            java.lang.String r11 = r0.getMessage()     // Catch:{ all -> 0x00fd }
            r9[r10] = r11     // Catch:{ all -> 0x00fd }
            org.altbeacon.beacon.b.d.d(r7, r8, r9)     // Catch:{ all -> 0x00fd }
            goto L_0x008f
        L_0x00fd:
            r7 = move-exception
            goto L_0x00ba
        L_0x00ff:
            r7 = move-exception
            java.lang.String r7 = b
            java.lang.String r8 = "close inputStream exception"
            java.lang.Object[] r9 = new java.lang.Object[r12]
            org.altbeacon.beacon.b.d.d(r7, r8, r9)
            goto L_0x0094
        L_0x010a:
            r7 = move-exception
            java.lang.String r7 = b
            java.lang.String r8 = "close objectInputStream exception"
            java.lang.Object[] r9 = new java.lang.Object[r12]
            org.altbeacon.beacon.b.d.d(r7, r8, r9)
            goto L_0x0099
        L_0x0115:
            r8 = move-exception
            java.lang.String r8 = b
            java.lang.String r9 = "close inputStream exception"
            java.lang.Object[] r10 = new java.lang.Object[r12]
            org.altbeacon.beacon.b.d.d(r8, r9, r10)
            goto L_0x00bf
        L_0x0120:
            r8 = move-exception
            java.lang.String r8 = b
            java.lang.String r9 = "close objectInputStream exception"
            java.lang.Object[] r10 = new java.lang.Object[r12]
            org.altbeacon.beacon.b.d.d(r8, r9, r10)
            goto L_0x00c4
        L_0x012b:
            r0 = move-exception
            goto L_0x0081
        */
        throw new UnsupportedOperationException("Method not decompiled: org.altbeacon.beacon.service.MonitoringStatus.k():void");
    }

    public final synchronized void e() {
        this.d.deleteFile("org.altbeacon.beacon.service.monitoring_status_state");
        this.e = false;
    }

    public final synchronized void f() {
        if (!this.e) {
            this.e = true;
            d();
        }
    }

    public final boolean g() {
        return this.e;
    }

    public final void a(Region region, Integer state) {
        i internalState = h().get(region);
        if (internalState == null) {
            internalState = d(region);
        }
        if (state != null) {
            if (state.intValue() == 0) {
                internalState.c();
            }
            if (state.intValue() == 1) {
                internalState.b();
            }
        }
    }

    private void c(Region region) {
        h().remove(region);
    }

    private i d(Region region) {
        return b(region, new b());
    }

    private i b(Region region, b callback) {
        if (h().containsKey(region)) {
            Iterator<Region> it = h().keySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Region existingRegion = it.next();
                if (existingRegion.equals(region)) {
                    if (existingRegion.a(region)) {
                        return h().get(existingRegion);
                    }
                    d.a(b, "Replacing region with unique identifier " + region.a(), new Object[0]);
                    d.a(b, "Old definition: " + existingRegion, new Object[0]);
                    d.a(b, "New definition: " + region, new Object[0]);
                    d.a(b, "clearing state", new Object[0]);
                    h().remove(region);
                }
            }
        }
        i monitoringState = new i(callback);
        h().put(region, monitoringState);
        return monitoringState;
    }
}
