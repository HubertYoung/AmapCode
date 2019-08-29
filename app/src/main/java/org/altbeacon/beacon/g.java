package org.altbeacon.beacon;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import org.altbeacon.beacon.b.d;
import org.altbeacon.beacon.c.a;
import org.altbeacon.beacon.service.BeaconService;
import org.altbeacon.beacon.service.MonitoringStatus;
import org.altbeacon.beacon.service.StartRMData;
import org.altbeacon.beacon.service.a.q;
import org.altbeacon.beacon.service.k;
import org.altbeacon.beacon.service.r;
import org.altbeacon.beacon.service.s;
import org.altbeacon.beacon.utils.ProcessUtils;

/* compiled from: BeaconManager */
public final class g {
    @Nullable
    protected static volatile g a = null;
    @Nullable
    protected static a e;
    protected static String f = "";
    protected static Class g = k.class;
    private static boolean t = false;
    private static boolean u = false;
    private static final Object w = new Object();
    private static long x = 10000;
    private long A = 10000;
    private long B = 300000;
    @NonNull
    protected final Set<o> b = new CopyOnWriteArraySet();
    @Nullable
    protected o c = null;
    @NonNull
    protected final Set<n> d = new CopyOnWriteArraySet();
    @NonNull
    private final Context h;
    /* access modifiers changed from: private */
    @NonNull
    public final ConcurrentMap<d, i> i = new ConcurrentHashMap();
    /* access modifiers changed from: private */
    @Nullable
    public Messenger j = null;
    @NonNull
    private final ArrayList<Region> k = new ArrayList<>();
    @NonNull
    private final List<j> l = new CopyOnWriteArrayList();
    @Nullable
    private q m;
    private boolean n = true;
    private boolean o = false;
    private boolean p = true;
    private boolean q = false;
    /* access modifiers changed from: private */
    @Nullable
    public Boolean r = null;
    private boolean s = false;
    private BeaconService v;
    private long y = 1100;
    private long z = 0;

    public static void a(long regionExitPeriod) {
        x = regionExitPeriod;
        g instance = a;
        if (instance != null) {
            instance.o();
        }
    }

    public static long a() {
        return x;
    }

    @NonNull
    public static g a(@NonNull Context context) {
        g instance = a;
        if (instance == null) {
            synchronized (w) {
                try {
                    instance = a;
                    if (instance == null) {
                        g instance2 = new g(context);
                        try {
                            a = instance2;
                            instance = instance2;
                        } catch (Throwable th) {
                            th = th;
                            g gVar = instance2;
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

    private g(@NonNull Context context) {
        this.h = context.getApplicationContext();
        A();
        this.l.add(new b());
    }

    public final boolean b() {
        return this.q;
    }

    private boolean z() {
        return this.r != null && !this.r.booleanValue();
    }

    public final void c() {
        this.r = Boolean.valueOf(false);
    }

    private void A() {
        ProcessUtils processUtils = new ProcessUtils(this.h);
        String processName = processUtils.a();
        String packageName = processUtils.b();
        int pid = ProcessUtils.c();
        this.q = processUtils.d();
        d.b("BeaconManager", "BeaconManager started up on pid " + pid + " named '" + processName + "' for application package '" + packageName + "'.  isMainProcess=" + this.q, new Object[0]);
    }

    @NonNull
    public final List<j> d() {
        return this.l;
    }

    public final void a(@NonNull d consumer) {
        if (!E()) {
            d.c("BeaconManager", "Method invocation will be ignored.", new Object[0]);
        } else if (!this.h.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            d.c("BeaconManager", "This device does not support bluetooth LE.  Will not start beacon scanning.", new Object[0]);
        } else {
            synchronized (this.i) {
                i newConsumerInfo = new i(this);
                if (this.i.putIfAbsent(consumer, newConsumerInfo) != null) {
                    d.a("BeaconManager", "This consumer is already bound", new Object[0]);
                } else {
                    d.a("BeaconManager", "This consumer is not bound.  Binding now: %s", consumer);
                    if (this.s) {
                        d.a("BeaconManager", "Not starting beacon scanning service. Using scheduled jobs", new Object[0]);
                        consumer.onBeaconServiceConnect();
                    } else {
                        d.a("BeaconManager", "Binding to service", new Object[0]);
                        this.v = new BeaconService(this.h);
                        newConsumerInfo.b.a(this.v);
                    }
                    d.a("BeaconManager", "consumer count is now: %s", Integer.valueOf(this.i.size()));
                }
            }
        }
    }

    public final void b(@NonNull d consumer) {
        if (!E()) {
            d.c("BeaconManager", "Method invocation will be ignored.", new Object[0]);
            return;
        }
        synchronized (this.i) {
            if (this.i.containsKey(consumer)) {
                d.a("BeaconManager", "Unbinding", new Object[0]);
                if (this.s) {
                    d.a("BeaconManager", "Not unbinding from scanning service as we are using scan jobs.", new Object[0]);
                } else {
                    this.v.c();
                    this.v = null;
                    ((i) this.i.get(consumer)).b.a();
                }
                this.i.remove(consumer);
                if (this.i.size() == 0) {
                    this.j = null;
                    this.o = false;
                    if (this.s) {
                    }
                }
            } else {
                d.a("BeaconManager", "This consumer is not bound to: %s", consumer);
                d.a("BeaconManager", "Bound consumers: ", new Object[0]);
                for (Entry consumerEntry : this.i.entrySet()) {
                    d.a("BeaconManager", String.valueOf(consumerEntry.getValue()), new Object[0]);
                }
            }
        }
    }

    public final boolean e() {
        boolean z2;
        synchronized (this.i) {
            z2 = this.i.isEmpty() && this.j != null;
        }
        return z2;
    }

    public final void a(boolean backgroundMode) {
        if (!E()) {
            d.c("BeaconManager", "Method invocation will be ignored.", new Object[0]);
            return;
        }
        this.p = false;
        if (backgroundMode != this.o) {
            this.o = backgroundMode;
            try {
                C();
            } catch (RemoteException e2) {
                d.d("BeaconManager", "Cannot contact service to set scan periods", new Object[0]);
            }
        }
    }

    public final boolean f() {
        return this.s;
    }

    public final boolean g() {
        return this.o;
    }

    public final long h() {
        return this.A;
    }

    public final long i() {
        return this.B;
    }

    public final long j() {
        return this.y;
    }

    public final long k() {
        return this.z;
    }

    public final void a(@NonNull o notifier) {
        this.b.add(notifier);
    }

    public final void l() {
        this.b.clear();
    }

    public final void m() {
        H();
        this.d.clear();
    }

    public final boolean n() {
        return this.n;
    }

    @TargetApi(18)
    public final void a(@NonNull Region region) {
        if (!E()) {
            d.c("BeaconManager", "Method invocation will be ignored.", new Object[0]);
            return;
        }
        H();
        synchronized (this.k) {
            this.k.add(region);
        }
        a(2, region);
    }

    public final void o() {
        H();
        if (!e()) {
            d.a("BeaconManager", "Not synchronizing settings to service, as it has not started up yet", new Object[0]);
        } else if (z()) {
            d.a("BeaconManager", "Synchronizing settings to service", new Object[0]);
            B();
        } else {
            d.a("BeaconManager", "Not synchronizing settings to service, as it is in the same process", new Object[0]);
        }
    }

    private void B() {
        if (this.s) {
            r.a().a(this.h, this);
            return;
        }
        try {
            a(7, (Region) null);
        } catch (RemoteException e2) {
            d.d("BeaconManager", "Failed to sync settings to service", e2);
        }
    }

    @TargetApi(18)
    private void C() {
        if (!E()) {
            d.c("BeaconManager", "Method invocation will be ignored.", new Object[0]);
            return;
        }
        H();
        d.a("BeaconManager", "updating background flag to %s", Boolean.valueOf(this.o));
        d.a("BeaconManager", "updating scan period to %s, %s", Long.valueOf(F()), Long.valueOf(G()));
        a(6, (Region) null);
    }

    @TargetApi(18)
    private void a(int type, Region region) {
        if (this.s) {
            r.a().a(this.h, this);
        } else if (this.j == null) {
            throw new RemoteException("The BeaconManager is not bound to the service.  Call beaconManager.bind(BeaconConsumer consumer) and wait for a callback to onBeaconServiceConnect()");
        } else {
            Message msg = Message.obtain(null, type, 0, 0);
            if (type == 6) {
                msg.setData(new StartRMData(F(), G(), this.o).f());
            } else if (type == 7) {
                msg.setData(new s().a(this.h).a());
            } else {
                msg.setData(new StartRMData(region, D(), F(), G(), this.o).f());
            }
            this.j.send(msg);
        }
    }

    private String D() {
        String packageName = this.h.getPackageName();
        d.a("BeaconManager", "callback packageName: %s", packageName);
        return packageName;
    }

    @NonNull
    public final Set<n> p() {
        return Collections.unmodifiableSet(this.d);
    }

    @NonNull
    public final Set<o> q() {
        return Collections.unmodifiableSet(this.b);
    }

    @NonNull
    public final Collection<Region> r() {
        return MonitoringStatus.a(this.h).a();
    }

    @NonNull
    public final Collection<Region> s() {
        ArrayList arrayList;
        synchronized (this.k) {
            arrayList = new ArrayList(this.k);
        }
        return arrayList;
    }

    public static String t() {
        return f;
    }

    public static Class u() {
        return g;
    }

    @Nullable
    public static a v() {
        return e;
    }

    /* access modifiers changed from: protected */
    @Nullable
    public final o w() {
        return this.c;
    }

    @Nullable
    public final q x() {
        return this.m;
    }

    private boolean E() {
        if (VERSION.SDK_INT < 18) {
            d.c("BeaconManager", "Bluetooth LE not supported prior to API 18.", new Object[0]);
            return false;
        } else if (this.h.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le")) {
            return true;
        } else {
            d.c("BeaconManager", "This device does not support bluetooth LE.", new Object[0]);
            return false;
        }
    }

    private long F() {
        if (this.o) {
            return this.A;
        }
        return this.y;
    }

    private long G() {
        if (this.o) {
            return this.B;
        }
        return this.z;
    }

    public static boolean y() {
        return t;
    }

    public static void b(boolean disabled) {
        t = disabled;
        g instance = a;
        if (instance != null) {
            instance.o();
        }
    }

    private boolean H() {
        if (z() && !b()) {
            d.c("BeaconManager", "Ranging/Monitoring may not be controlled from a separate BeaconScanner process.  To remove this warning, please wrap this call in: if (beaconManager.isMainProcess())", new Object[0]);
        }
        return false;
    }
}
