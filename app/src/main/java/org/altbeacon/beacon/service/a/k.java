package org.altbeacon.beacon.service.a;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanSettings;
import android.bluetooth.le.ScanSettings.Builder;
import android.content.Context;
import android.os.Handler;
import android.os.SystemClock;
import java.util.ArrayList;
import java.util.List;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.service.c;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

@TargetApi(21)
/* compiled from: CycledLeScannerForLollipop */
public class k extends b {
    private BluetoothLeScanner k;
    private ScanCallback l;
    /* access modifiers changed from: private */
    public long m = 0;
    private long n = 0;
    private boolean o = false;
    private final g p = g.a(this.c);

    public k(Context context, boolean backgroundFlag, a cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        super(context, backgroundFlag, cycledLeScanCallback, crashResolver);
    }

    /* access modifiers changed from: protected */
    public final void f() {
        m();
    }

    /* access modifiers changed from: protected */
    public final boolean g() {
        long millisecondsUntilStart = this.a - SystemClock.elapsedRealtime();
        boolean deferScan = millisecondsUntilStart > 0;
        boolean scanActiveBefore = this.o;
        this.o = !deferScan;
        if (deferScan) {
            long secsSinceLastDetection = SystemClock.elapsedRealtime() - c.a().b();
            if (scanActiveBefore) {
                if (secsSinceLastDetection > 10000) {
                    this.m = SystemClock.elapsedRealtime();
                    this.n = 0;
                    d.a("CycledLeScannerForLollipop", "This is Android L. Preparing to do a filtered scan for the background.", new Object[0]);
                    if (this.d > 6000) {
                        h();
                    } else {
                        d.a("CycledLeScannerForLollipop", "Suppressing scan between cycles because the between scan cycle is too short.", new Object[0]);
                    }
                } else {
                    d.a("CycledLeScannerForLollipop", "This is Android L, but we last saw a beacon only %s ago, so we will not keep scanning in background.", Long.valueOf(secsSinceLastDetection));
                }
            }
            if (this.m > 0 && c.a().b() > this.m) {
                if (this.n == 0) {
                    this.n = c.a().b();
                }
                if (SystemClock.elapsedRealtime() - this.n >= 10000) {
                    d.a("CycledLeScannerForLollipop", "We've been detecting for a bit.  Stopping Android L background scanning", new Object[0]);
                    f();
                    this.m = 0;
                } else {
                    d.a("CycledLeScannerForLollipop", "Delivering Android L background scanning results", new Object[0]);
                    this.h.a();
                }
            }
            d.a("CycledLeScannerForLollipop", "Waiting to start full Bluetooth scan for another %s milliseconds", Long.valueOf(millisecondsUntilStart));
            if (scanActiveBefore && this.i) {
                l();
            }
            Handler handler = this.e;
            l lVar = new l(this);
            if (millisecondsUntilStart > 1000) {
                millisecondsUntilStart = 1000;
            }
            handler.postDelayed(lVar, millisecondsUntilStart);
        } else if (this.m > 0) {
            f();
            this.m = 0;
        }
        return deferScan;
    }

    /* access modifiers changed from: protected */
    public final void h() {
        ScanSettings settings;
        if (!n()) {
            d.a("CycledLeScannerForLollipop", "Not starting scan because bluetooth is off", new Object[0]);
            return;
        }
        List filters = new ArrayList();
        if (!this.o) {
            d.a("CycledLeScannerForLollipop", "starting filtered scan in SCAN_MODE_LOW_POWER", new Object[0]);
            settings = new Builder().setScanMode(0).build();
            filters = new r().a(this.p.d());
        } else {
            d.a("CycledLeScannerForLollipop", "starting non-filtered scan in SCAN_MODE_LOW_LATENCY", new Object[0]);
            settings = new Builder().setScanMode(2).build();
        }
        if (settings != null) {
            a(filters, settings);
        }
    }

    /* access modifiers changed from: protected */
    public final void j() {
        d.a("CycledLeScannerForLollipop", "Stopping scan", new Object[0]);
        f();
        this.b = true;
    }

    private void a(List<ScanFilter> filters, ScanSettings settings) {
        BluetoothLeScanner scanner = o();
        if (scanner != null) {
            ScanCallback scanCallback = p();
            this.f.removeCallbacksAndMessages(null);
            this.f.post(new m(this, scanner, filters, settings, scanCallback));
        }
    }

    private void m() {
        if (!n()) {
            d.a("CycledLeScannerForLollipop", "Not stopping scan because bluetooth is off", new Object[0]);
            return;
        }
        BluetoothLeScanner scanner = o();
        if (scanner != null) {
            ScanCallback scanCallback = p();
            this.f.removeCallbacksAndMessages(null);
            this.f.post(new n(this, scanner, scanCallback));
        }
    }

    private boolean n() {
        try {
            BluetoothAdapter bluetoothAdapter = k();
            if (bluetoothAdapter == null) {
                d.c("CycledLeScannerForLollipop", "Cannot get bluetooth adapter", new Object[0]);
                return false;
            } else if (bluetoothAdapter.getState() == 12) {
                return true;
            } else {
                return false;
            }
        } catch (SecurityException e) {
            d.c("CycledLeScannerForLollipop", "SecurityException checking if bluetooth is on", new Object[0]);
            return false;
        }
    }

    private BluetoothLeScanner o() {
        try {
            if (this.k == null) {
                d.a("CycledLeScannerForLollipop", "Making new Android L scanner", new Object[0]);
                if (k() != null) {
                    this.k = k().getBluetoothLeScanner();
                }
                if (this.k == null) {
                    d.c("CycledLeScannerForLollipop", "Failed to make new Android L scanner", new Object[0]);
                }
            }
        } catch (SecurityException e) {
            d.c("CycledLeScannerForLollipop", "SecurityException making new Android L scanner", new Object[0]);
        }
        return this.k;
    }

    private ScanCallback p() {
        if (this.l == null) {
            this.l = new o(this);
        }
        return this.l;
    }
}
