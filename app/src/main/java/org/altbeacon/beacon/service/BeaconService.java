package org.altbeacon.beacon.service;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Messenger;
import android.support.annotation.MainThread;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.Region;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.distance.ModelSpecificDistanceCalculator;
import org.altbeacon.beacon.distance.c;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.j;
import org.altbeacon.beacon.utils.ProcessUtils;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

public class BeaconService {
    Context a;
    final Messenger b = new Messenger(new a(this));
    private final Handler c = new Handler();
    private BluetoothCrashResolver d;
    private m e;

    public BeaconService(Context context) {
        this.a = context;
        a(context);
    }

    public final Context a() {
        return this.a;
    }

    public final Messenger b() {
        return this.b;
    }

    @MainThread
    private void a(Context context) {
        this.d = new BluetoothCrashResolver(context);
        this.d.a();
        this.e = new m(context);
        if (this.e.a() == null) {
            this.e.a(false, this.d);
        }
        this.e.a(MonitoringStatus.a(context));
        this.e.a((Map<Region, f>) new HashMap<Region,f>());
        this.e.a((Set<j>) new HashSet<j>());
        this.e.a(new d());
        g beaconManager = g.a(context);
        beaconManager.c();
        if (beaconManager.b()) {
            d.b("BeaconService", "beaconService version %s is starting up on the main process", "1.0");
        } else {
            d.b("BeaconService", "beaconService version %s is starting up on a separate process", "1.0");
            d.b("BeaconService", "beaconService PID is " + ProcessUtils.c() + " with process name " + new ProcessUtils(context).a(), new Object[0]);
        }
        this.e.d();
        g.t();
        Beacon.a((c) new ModelSpecificDistanceCalculator(context));
        try {
            this.e.a((List) Class.forName("org.altbeacon.beacon.SimulatedScanData").getField("beacons").get(null));
        } catch (ClassNotFoundException e2) {
            d.a("BeaconService", "No org.altbeacon.beacon.SimulatedScanData class exists.", new Object[0]);
        } catch (Exception e3) {
            d.b(e3, "BeaconService", "Cannot get simulated Scan data.  Make sure your org.altbeacon.beacon.SimulatedScanData class defines a field with the signature 'public static List<Beacon> beacons'", new Object[0]);
        }
    }

    @MainThread
    public final void c() {
        d.d("BeaconService", "destroy()", new Object[0]);
        if (VERSION.SDK_INT < 18) {
            d.c("BeaconService", "Not supported prior to API 18.", new Object[0]);
            return;
        }
        this.d.b();
        d.b("BeaconService", "destroy called.  stopping scanning", new Object[0]);
        this.c.removeCallbacksAndMessages(null);
        this.e.a().b();
        this.e.a().e();
        this.e.b().e();
    }

    @MainThread
    public final void a(Region region, b callback) {
        synchronized (this.e.c()) {
            if (this.e.c().containsKey(region)) {
                d.b("BeaconService", "Already ranging that region -- will replace existing region.", new Object[0]);
                this.e.c().remove(region);
            }
            this.e.c().put(region, new f(callback));
            d.a("BeaconService", "Currently ranging %s regions.", Integer.valueOf(this.e.c().size()));
        }
        this.e.a().a();
    }

    @MainThread
    public final void a(Region region) {
        int rangedRegionCount;
        synchronized (this.e.c()) {
            this.e.c().remove(region);
            rangedRegionCount = this.e.c().size();
            d.a("BeaconService", "Currently ranging %s regions.", Integer.valueOf(this.e.c().size()));
        }
        if (rangedRegionCount == 0 && this.e.b().b() == 0) {
            this.e.a().b();
        }
    }

    @MainThread
    public final void b(Region region, b callback) {
        d.a("BeaconService", "startMonitoring called", new Object[0]);
        this.e.b().a(region, callback);
        d.a("BeaconService", "Currently monitoring %s regions.", Integer.valueOf(this.e.b().b()));
        this.e.a().a();
    }

    @MainThread
    public final void b(Region region) {
        d.a("BeaconService", "stopMonitoring called", new Object[0]);
        this.e.b().a(region);
        d.a("BeaconService", "Currently monitoring %s regions.", Integer.valueOf(this.e.b().b()));
        if (this.e.b().b() == 0 && this.e.c().size() == 0) {
            this.e.a().b();
        }
    }

    @MainThread
    public final void a(long scanPeriod, long betweenScanPeriod, boolean backgroundFlag) {
        this.e.a().a(scanPeriod, betweenScanPeriod, backgroundFlag);
    }

    public final void d() {
        this.e.d();
    }
}
