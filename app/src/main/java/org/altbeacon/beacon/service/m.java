package org.altbeacon.beacon.service;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.j;
import org.altbeacon.beacon.service.a.a;
import org.altbeacon.beacon.service.a.b;
import org.altbeacon.beacon.service.a.p;
import org.altbeacon.beacon.service.a.r;
import org.altbeacon.beacon.startup.StartupBroadcastReceiver;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

@TargetApi(21)
/* compiled from: ScanHelper */
class m {
    /* access modifiers changed from: private */
    public static final String a = m.class.getSimpleName();
    private ExecutorService b;
    private g c;
    /* access modifiers changed from: private */
    public b d;
    /* access modifiers changed from: private */
    public MonitoringStatus e;
    private final Map<Region, f> f = new HashMap();
    /* access modifiers changed from: private */
    public p g = new p();
    private d h;
    /* access modifiers changed from: private */
    public Set<j> i = new HashSet();
    /* access modifiers changed from: private */
    public List<Beacon> j = null;
    /* access modifiers changed from: private */
    public Context k;
    private final a l = new n(this);

    m(Context context) {
        this.k = context;
        this.c = g.a(context);
        this.b = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    }

    /* access modifiers changed from: 0000 */
    public final b a() {
        return this.d;
    }

    /* access modifiers changed from: 0000 */
    public final MonitoringStatus b() {
        return this.e;
    }

    /* access modifiers changed from: 0000 */
    public final void a(MonitoringStatus monitoringStatus) {
        this.e = monitoringStatus;
    }

    /* access modifiers changed from: 0000 */
    public final Map<Region, f> c() {
        return this.f;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Map<Region, f> rangedRegionState) {
        synchronized (this.f) {
            this.f.clear();
            this.f.putAll(rangedRegionState);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a(d extraDataBeaconTracker) {
        this.h = extraDataBeaconTracker;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Set<j> beaconParsers) {
        this.i = beaconParsers;
    }

    /* access modifiers changed from: 0000 */
    public final void a(List<Beacon> simulatedScanData) {
        this.j = simulatedScanData;
    }

    /* access modifiers changed from: 0000 */
    public final void a(boolean backgroundMode, BluetoothCrashResolver crashResolver) {
        this.d = b.a(this.k, backgroundMode, this.l, crashResolver);
    }

    /* access modifiers changed from: 0000 */
    @TargetApi(11)
    public final void a(BluetoothDevice device, int rssi, byte[] scanRecord) {
        try {
            new p(this, this.c.x()).executeOnExecutor(this.b, new o[]{new o(this, device, rssi, scanRecord)});
        } catch (RejectedExecutionException e2) {
            d.c(a, "Ignoring scan result because we cannot keep up.", new Object[0]);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void d() {
        HashSet newBeaconParsers = new HashSet();
        boolean matchBeaconsByServiceUUID = true;
        newBeaconParsers.addAll(this.c.d());
        for (j beaconParser : this.c.d()) {
            if (beaconParser.a().size() > 0) {
                matchBeaconsByServiceUUID = false;
                newBeaconParsers.addAll(beaconParser.a());
            }
        }
        this.i = newBeaconParsers;
        this.h = new d(matchBeaconsByServiceUUID);
    }

    /* access modifiers changed from: 0000 */
    public final void b(Set<j> beaconParsers) {
        ScanSettings settings = new Builder().setScanMode(0).build();
        List filters = new r().a((List<j>) new ArrayList<j>(beaconParsers));
        try {
            BluetoothAdapter bluetoothAdapter = ((BluetoothManager) this.k.getApplicationContext().getSystemService("bluetooth")).getAdapter();
            if (bluetoothAdapter == null) {
                d.c(a, "Failed to construct a BluetoothAdapter", new Object[0]);
            } else if (!bluetoothAdapter.isEnabled()) {
                d.c(a, "Failed to start background scan on Android O: BluetoothAdapter is not enabled", new Object[0]);
            } else {
                int result = bluetoothAdapter.getBluetoothLeScanner().startScan(filters, settings, g());
                if (result != 0) {
                    d.d(a, "Failed to start background scan on Android O.  Code: " + result, new Object[0]);
                } else {
                    d.a(a, "Started passive beacon scan", new Object[0]);
                }
            }
        } catch (SecurityException e2) {
            d.d(a, "SecurityException making Android O background scanner", new Object[0]);
        }
    }

    /* access modifiers changed from: 0000 */
    public final void e() {
        try {
            BluetoothAdapter bluetoothAdapter = ((BluetoothManager) this.k.getApplicationContext().getSystemService("bluetooth")).getAdapter();
            if (bluetoothAdapter == null) {
                d.c(a, "Failed to construct a BluetoothAdapter", new Object[0]);
            } else if (!bluetoothAdapter.isEnabled()) {
                d.c(a, "BluetoothAdapter is not enabled", new Object[0]);
            } else {
                bluetoothAdapter.getBluetoothLeScanner().stopScan(g());
            }
        } catch (SecurityException e2) {
            d.d(a, "SecurityException stopping Android O background scanner", new Object[0]);
        }
    }

    private PendingIntent g() {
        Intent intent = new Intent(this.k, StartupBroadcastReceiver.class);
        intent.putExtra("o-scan", true);
        return PendingIntent.getBroadcast(this.k, 0, intent, 134217728);
    }

    /* access modifiers changed from: private */
    public void h() {
        synchronized (this.f) {
            for (Region region : this.f.keySet()) {
                d.a(a, "Calling ranging callback", new Object[0]);
                b.a(this.k, "rangingData", new h(this.f.get(region).a(), region).c());
            }
        }
    }

    /* access modifiers changed from: private */
    @WorkerThread
    public void a(@NonNull Beacon beacon) {
        if (u.a().b()) {
            u.a().c();
        }
        if (d.a()) {
            d.a(a, "beacon detected : %s", beacon.toString());
        }
        Beacon beacon2 = this.h.a(beacon);
        if (beacon2 != null) {
            this.e.a(beacon2);
            d.a(a, "looking for ranging region matches for this beacon", new Object[0]);
            synchronized (this.f) {
                for (Region region : a(beacon2, (Collection<Region>) this.f.keySet())) {
                    d.a(a, "matches ranging region: %s", region);
                    f rangeState = this.f.get(region);
                    if (rangeState != null) {
                        rangeState.a(beacon2);
                    }
                }
            }
        } else if (d.a()) {
            d.a(a, "not processing detections for GATT extra data beacon", new Object[0]);
        }
    }

    private static List<Region> a(Beacon beacon, Collection<Region> regions) {
        List matched = new ArrayList();
        for (Region region : regions) {
            if (region.a(beacon)) {
                matched.add(region);
            } else {
                d.a(a, "This region (%s) does not match beacon: %s", region, beacon);
            }
        }
        return matched;
    }
}
