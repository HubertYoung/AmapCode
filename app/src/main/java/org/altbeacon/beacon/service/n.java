package org.altbeacon.beacon.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.content.pm.ApplicationInfo;
import android.support.annotation.MainThread;
import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.service.a.a;

/* compiled from: ScanHelper */
final class n implements a {
    final /* synthetic */ m a;

    n(m this$0) {
        this.a = this$0;
    }

    @TargetApi(11)
    @MainThread
    public final void a(BluetoothDevice device, int rssi, byte[] scanRecord) {
        this.a.a(device, rssi, scanRecord);
    }

    @SuppressLint({"WrongThread"})
    @MainThread
    public final void a() {
        this.a.g.a();
        this.a.e.c();
        this.a.h();
        if (this.a.j != null) {
            d.c(m.a, "Simulated scan data is deprecated and will be removed in a future release. Please use the new BeaconSimulator interface instead.", new Object[0]);
            ApplicationInfo applicationInfo = this.a.k.getApplicationInfo();
            int i = applicationInfo.flags & 2;
            applicationInfo.flags = i;
            if (i != 0) {
                for (Beacon beacon : this.a.j) {
                    this.a.a(beacon);
                }
            } else {
                d.c(m.a, "Simulated scan data provided, but ignored because we are not running in debug mode.  Please remove simulated scan data for production.", new Object[0]);
            }
        }
        if (g.v() == null) {
            return;
        }
        if (g.v().a() != null) {
            ApplicationInfo applicationInfo2 = this.a.k.getApplicationInfo();
            int i2 = applicationInfo2.flags & 2;
            applicationInfo2.flags = i2;
            if (i2 != 0) {
                for (Beacon beacon2 : g.v().a()) {
                    this.a.a(beacon2);
                }
                return;
            }
            d.c(m.a, "Beacon simulations provided, but ignored because we are not running in debug mode.  Please remove beacon simulations for production.", new Object[0]);
            return;
        }
        d.c(m.a, "getBeacons is returning null. No simulated beacons to report.", new Object[0]);
    }
}
