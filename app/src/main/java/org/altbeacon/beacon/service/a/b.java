package org.altbeacon.beacon.service.a;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Process;
import android.os.SystemClock;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import java.util.Date;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.g;
import org.altbeacon.beacon.startup.StartupBroadcastReceiver;
import org.altbeacon.bluetooth.BluetoothCrashResolver;

@TargetApi(18)
/* compiled from: CycledLeScanner */
public abstract class b {
    protected long a = 0;
    protected boolean b;
    protected final Context c;
    protected long d = 0;
    @NonNull
    protected final Handler e = new Handler(Looper.getMainLooper());
    @NonNull
    protected final Handler f;
    protected final BluetoothCrashResolver g;
    protected final a h;
    protected boolean i = false;
    protected boolean j = false;
    private BluetoothAdapter k;
    private long l = 0;
    private long m = 0;
    private long n = 0;
    private long o = 0;
    private boolean p = false;
    private boolean q;
    private boolean r = false;
    private boolean s = false;
    private long t = 1100;
    private boolean u = false;
    /* access modifiers changed from: private */
    @NonNull
    public final HandlerThread v;
    private volatile boolean w = false;
    private PendingIntent x = null;

    /* access modifiers changed from: protected */
    public abstract void f();

    /* access modifiers changed from: protected */
    public abstract boolean g();

    /* access modifiers changed from: protected */
    public abstract void h();

    /* access modifiers changed from: protected */
    public abstract void j();

    protected b(Context context, boolean backgroundFlag, a cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        this.c = context;
        this.h = cycledLeScanCallback;
        this.g = crashResolver;
        this.i = backgroundFlag;
        this.v = new HandlerThread("CycledLeScannerThread");
        this.v.start();
        this.f = new Handler(this.v.getLooper());
    }

    public static b a(Context context, boolean backgroundFlag, a cycledLeScanCallback, BluetoothCrashResolver crashResolver) {
        boolean useAndroidLScanner = false;
        boolean useAndroidOScanner = false;
        if (VERSION.SDK_INT < 18) {
            d.c("CycledLeScanner", "Not supported prior to API 18.", new Object[0]);
            return null;
        }
        if (VERSION.SDK_INT < 21) {
            d.b("CycledLeScanner", "This is pre Android 5.0.  We are using old scanning APIs", new Object[0]);
            useAndroidLScanner = false;
        } else if (VERSION.SDK_INT >= 26) {
            d.b("CycledLeScanner", "Using Android O scanner", new Object[0]);
            useAndroidOScanner = true;
        } else if (g.y()) {
            d.b("CycledLeScanner", "This is Android 5.0, but L scanning is disabled. We are using old scanning APIs", new Object[0]);
            useAndroidLScanner = false;
        } else {
            d.b("CycledLeScanner", "This is Android 5.0.  We are using new scanning APIs", new Object[0]);
            useAndroidLScanner = true;
        }
        if (useAndroidOScanner) {
            return new e(context, backgroundFlag, cycledLeScanCallback, crashResolver);
        }
        if (useAndroidLScanner) {
            return new k(context, backgroundFlag, cycledLeScanCallback, crashResolver);
        }
        return new f(context, backgroundFlag, cycledLeScanCallback, crashResolver);
    }

    @MainThread
    public final void a(long scanPeriod, long betweenScanPeriod, boolean backgroundFlag) {
        d.a("CycledLeScanner", "Set scan periods called with %s, %s Background mode must have changed.", Long.valueOf(scanPeriod), Long.valueOf(betweenScanPeriod));
        if (this.i != backgroundFlag) {
            this.j = true;
        }
        this.i = backgroundFlag;
        this.t = scanPeriod;
        this.d = betweenScanPeriod;
        if (this.i) {
            d.a("CycledLeScanner", "We are in the background.  Setting wakeup alarm", new Object[0]);
            l();
        } else {
            d.a("CycledLeScanner", "We are not in the background.  Cancelling wakeup alarm", new Object[0]);
            o();
        }
        long now = SystemClock.elapsedRealtime();
        if (this.a > now) {
            long proposedNextScanStartTime = this.m + betweenScanPeriod;
            if (proposedNextScanStartTime < this.a) {
                this.a = proposedNextScanStartTime;
                d.b("CycledLeScanner", "Adjusted nextScanStartTime to be %s", new Date((this.a - SystemClock.elapsedRealtime()) + System.currentTimeMillis()));
            }
        }
        if (this.n > now) {
            long proposedScanStopTime = this.l + scanPeriod;
            if (proposedScanStopTime < this.n) {
                this.n = proposedScanStopTime;
                d.b("CycledLeScanner", "Adjusted scanStopTime to be %s", Long.valueOf(this.n));
            }
        }
    }

    @MainThread
    public final void a() {
        d.a("CycledLeScanner", "start called", new Object[0]);
        this.s = true;
        if (!this.r) {
            a(Boolean.valueOf(true));
        } else {
            d.a("CycledLeScanner", "scanning already started", new Object[0]);
        }
    }

    @MainThread
    public final void b() {
        d.a("CycledLeScanner", "stop called", new Object[0]);
        this.s = false;
        if (this.r) {
            a(Boolean.valueOf(false));
            if (this.u) {
                d.a("CycledLeScanner", "Stopping scanning previously left on.", new Object[0]);
                this.u = false;
                try {
                    d.a("CycledLeScanner", "stopping bluetooth le scan", new Object[0]);
                    j();
                } catch (Exception e2) {
                    d.a(e2, "CycledLeScanner", "Internal Android exception scanning for beacons", new Object[0]);
                }
            }
        } else {
            d.a("CycledLeScanner", "scanning already stopped", new Object[0]);
        }
    }

    public final boolean c() {
        return this.w;
    }

    public final void d() {
        this.w = true;
    }

    @MainThread
    public final void e() {
        d.a("CycledLeScanner", "Destroying", new Object[0]);
        this.e.removeCallbacksAndMessages(null);
        this.f.post(new c(this));
    }

    /* access modifiers changed from: protected */
    @MainThread
    public final void a(Boolean enable) {
        try {
            this.r = true;
            if (k() == null) {
                d.d("CycledLeScanner", "No Bluetooth adapter.  beaconService cannot scan.", new Object[0]);
            }
            if (!this.s || !enable.booleanValue()) {
                d.a("CycledLeScanner", "disabling scan", new Object[0]);
                this.q = false;
                this.r = false;
                f();
                this.o = 0;
                this.m = SystemClock.elapsedRealtime();
                this.e.removeCallbacksAndMessages(null);
                m();
            } else if (!g()) {
                d.a("CycledLeScanner", "starting a new scan cycle", new Object[0]);
                if (!this.q || this.b || this.j) {
                    this.q = true;
                    this.b = false;
                    try {
                        if (k() != null) {
                            if (k().isEnabled()) {
                                if (this.g != null && this.g.d()) {
                                    d.c("CycledLeScanner", "Skipping scan because crash recovery is in progress.", new Object[0]);
                                } else if (this.s) {
                                    if (this.j) {
                                        this.j = false;
                                        d.a("CycledLeScanner", "restarting a bluetooth le scan", new Object[0]);
                                    } else {
                                        d.a("CycledLeScanner", "starting a new bluetooth le scan", new Object[0]);
                                    }
                                    try {
                                        if (VERSION.SDK_INT < 23 || q()) {
                                            this.o = SystemClock.elapsedRealtime();
                                            h();
                                        }
                                    } catch (Exception e2) {
                                        d.b(e2, "CycledLeScanner", "Internal Android exception scanning for beacons", new Object[0]);
                                    }
                                } else {
                                    d.a("CycledLeScanner", "Scanning unnecessary - no monitoring or ranging active.", new Object[0]);
                                }
                                this.l = SystemClock.elapsedRealtime();
                            } else {
                                d.a("CycledLeScanner", "Bluetooth is disabled.  Cannot scan for beacons.", new Object[0]);
                            }
                        }
                    } catch (Exception e3) {
                        d.b(e3, "CycledLeScanner", "Exception starting Bluetooth scan.  Perhaps Bluetooth is disabled or unavailable?", new Object[0]);
                    }
                } else {
                    d.a("CycledLeScanner", "We are already scanning and have been for " + (SystemClock.elapsedRealtime() - this.o) + " millis", new Object[0]);
                }
                this.n = SystemClock.elapsedRealtime() + this.t;
                i();
                d.a("CycledLeScanner", "Scan started", new Object[0]);
            }
        } catch (SecurityException e4) {
            d.c("CycledLeScanner", "SecurityException working accessing bluetooth.", new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    @MainThread
    public final void i() {
        long millisecondsUntilStop = this.n - SystemClock.elapsedRealtime();
        if (!this.s || millisecondsUntilStop <= 0) {
            m();
            return;
        }
        d.a("CycledLeScanner", "Waiting to stop scan cycle for another %s milliseconds", Long.valueOf(millisecondsUntilStop));
        if (this.i) {
            l();
        }
        Handler handler = this.e;
        d dVar = new d(this);
        if (millisecondsUntilStop > 1000) {
            millisecondsUntilStop = 1000;
        }
        handler.postDelayed(dVar, millisecondsUntilStop);
    }

    @MainThread
    private void m() {
        d.a("CycledLeScanner", "Done with scan cycle", new Object[0]);
        try {
            this.h.a();
            if (this.q) {
                if (k() != null) {
                    if (k().isEnabled()) {
                        if (!this.w || this.d != 0 || r()) {
                            long now = SystemClock.elapsedRealtime();
                            if (VERSION.SDK_INT < 24 || this.d + this.t >= 6000 || now - this.l >= 6000) {
                                try {
                                    d.a("CycledLeScanner", "stopping bluetooth le scan", new Object[0]);
                                    j();
                                    this.u = false;
                                } catch (Exception e2) {
                                    d.a(e2, "CycledLeScanner", "Internal Android exception scanning for beacons", new Object[0]);
                                }
                            } else {
                                d.a("CycledLeScanner", "Not stopping scan because this is Android N and we keep scanning for a minimum of 6 seconds at a time. We will stop in " + (6000 - (now - this.l)) + " millisconds.", new Object[0]);
                                this.u = true;
                            }
                        } else {
                            d.a("CycledLeScanner", "Not stopping scanning.  Device capable of multiple indistinct detections per scan.", new Object[0]);
                            this.u = true;
                        }
                        this.m = SystemClock.elapsedRealtime();
                    } else {
                        d.a("CycledLeScanner", "Bluetooth is disabled.  Cannot scan for beacons.", new Object[0]);
                        this.j = true;
                    }
                }
                this.a = p();
                if (this.s) {
                    a(Boolean.valueOf(true));
                }
            }
            if (!this.s) {
                d.a("CycledLeScanner", "Scanning disabled. ", new Object[0]);
                this.r = false;
                o();
            }
        } catch (SecurityException e3) {
            d.c("CycledLeScanner", "SecurityException working accessing bluetooth.", new Object[0]);
        }
    }

    /* access modifiers changed from: protected */
    public final BluetoothAdapter k() {
        try {
            if (this.k == null) {
                this.k = ((BluetoothManager) this.c.getApplicationContext().getSystemService("bluetooth")).getAdapter();
                if (this.k == null) {
                    d.c("CycledLeScanner", "Failed to construct a BluetoothAdapter", new Object[0]);
                }
            }
        } catch (SecurityException e2) {
            d.d("CycledLeScanner", "Cannot consruct bluetooth adapter.  Security Exception", new Object[0]);
        }
        return this.k;
    }

    /* access modifiers changed from: protected */
    public final void l() {
        long milliseconds = 300000;
        if (300000 < this.d) {
            milliseconds = this.d;
        }
        if (milliseconds < this.t) {
            milliseconds = this.t;
        }
        ((AlarmManager) this.c.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(2, SystemClock.elapsedRealtime() + milliseconds, n());
        d.a("CycledLeScanner", "Set a wakeup alarm to go off in %s ms: %s", Long.valueOf(milliseconds), n());
    }

    private PendingIntent n() {
        if (this.x == null) {
            Intent wakeupIntent = new Intent(this.c, StartupBroadcastReceiver.class);
            wakeupIntent.putExtra("wakeup", true);
            this.x = PendingIntent.getBroadcast(this.c, 0, wakeupIntent, 134217728);
        }
        return this.x;
    }

    private void o() {
        d.a("CycledLeScanner", "cancel wakeup alarm: %s", this.x);
        ((AlarmManager) this.c.getSystemService(NotificationCompat.CATEGORY_ALARM)).set(2, Long.MAX_VALUE, n());
        d.a("CycledLeScanner", "Set a wakeup alarm to go off in %s ms: %s", Long.valueOf(Long.MAX_VALUE - SystemClock.elapsedRealtime()), n());
    }

    private long p() {
        if (this.d == 0) {
            return SystemClock.elapsedRealtime();
        }
        long normalizedBetweenScanPeriod = this.d - (SystemClock.elapsedRealtime() % (this.t + this.d));
        d.a("CycledLeScanner", "Normalizing between scan period from %s to %s", Long.valueOf(this.d), Long.valueOf(normalizedBetweenScanPeriod));
        return SystemClock.elapsedRealtime() + normalizedBetweenScanPeriod;
    }

    private boolean q() {
        return a((String) "android.permission.ACCESS_COARSE_LOCATION") || a((String) "android.permission.ACCESS_FINE_LOCATION");
    }

    private boolean a(String permission) {
        return this.c.checkPermission(permission, Process.myPid(), Process.myUid()) == 0;
    }

    private boolean r() {
        if (VERSION.SDK_INT >= 24 && this.o > 0 && ((SystemClock.elapsedRealtime() + this.d) + this.t) - this.o > TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL) {
            d.a("CycledLeScanner", "The next scan cycle would go over the Android N max duration.", new Object[0]);
            if (this.p) {
                d.a("CycledLeScanner", "Stopping scan to prevent Android N scan timeout.", new Object[0]);
                return true;
            }
            d.c("CycledLeScanner", "Allowing a long running scan to be stopped by the OS.  To prevent this, set longScanForcingEnabled in the AndroidBeaconLibrary.", new Object[0]);
        }
        return false;
    }
}
