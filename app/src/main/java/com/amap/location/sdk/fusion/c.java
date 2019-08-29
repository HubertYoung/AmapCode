package com.amap.location.sdk.fusion;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import com.amap.api.service.AMapService;
import com.amap.location.sdk.fusion.ILocationCallback.Stub;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONObject;

/* compiled from: LocationServiceProxy */
public class c implements a {
    private List<Long> a = new LinkedList();
    /* access modifiers changed from: private */
    public AtomicBoolean b = new AtomicBoolean(true);
    /* access modifiers changed from: private */
    public IBinder c;
    /* access modifiers changed from: private */
    public ILocationService d;
    /* access modifiers changed from: private */
    public a e;
    /* access modifiers changed from: private */
    public LocationStatusListener f;
    /* access modifiers changed from: private */
    public Context g;
    /* access modifiers changed from: private */
    public Handler h;
    /* access modifiers changed from: private */
    public Runnable i = new Runnable() {
        public void run() {
            if (c.this.a()) {
                if (c.this.c == null || !c.this.c.isBinderAlive()) {
                    com.amap.location.common.d.a.c("mainserviceproxy", "pingBinderRunnable fail");
                    c.this.a((Exception) null, 4);
                } else {
                    try {
                        if (c.this.c.pingBinder()) {
                            c.this.h.postDelayed(c.this.i, 10000);
                        } else {
                            c.this.a((Exception) null, 4);
                        }
                    } catch (Exception e) {
                        c.this.a(e, 3);
                    }
                }
            }
        }
    };
    private ILocationCallback j = new Stub() {
        public void a(final Location location) throws RemoteException {
            c.this.h.post(new Runnable() {
                public void run() {
                    c.this.e.a(location);
                }
            });
            c.this.h.removeCallbacks(c.this.i);
            c.this.h.postDelayed(c.this.i, 10000);
        }

        public void a(final String str, final int i, final Bundle bundle) throws RemoteException {
            c.this.h.post(new Runnable() {
                public void run() {
                    c.this.e.a(str, i, bundle);
                }
            });
            c.this.h.removeCallbacks(c.this.i);
            c.this.h.postDelayed(c.this.i, 10000);
        }

        public void a(final String str) throws RemoteException {
            c.this.h.post(new Runnable() {
                public void run() {
                    c.this.e.a(str);
                }
            });
            c.this.h.removeCallbacks(c.this.i);
            c.this.h.postDelayed(c.this.i, 10000);
        }

        public void b(final String str) throws RemoteException {
            c.this.h.post(new Runnable() {
                public void run() {
                    c.this.e.b(str);
                }
            });
            c.this.h.removeCallbacks(c.this.i);
            c.this.h.postDelayed(c.this.i, 10000);
        }
    };
    private IStatusCallback k = new IStatusCallback.Stub() {
        public void a(String str, long j, long j2, Bundle bundle) throws RemoteException {
            Handler c = c.this.h;
            final String str2 = str;
            final long j3 = j;
            final long j4 = j2;
            final Bundle bundle2 = bundle;
            AnonymousClass1 r0 = new Runnable() {
                public void run() {
                    try {
                        LocationStatusListener e2 = c.this.f;
                        if (e2 != null) {
                            e2.onStatusChanged(str2, j3, j4, bundle2);
                        }
                    } catch (Throwable unused) {
                    }
                }
            };
            c.post(r0);
        }
    };
    private ServiceConnection l = new ServiceConnection() {
        public void onServiceConnected(ComponentName componentName, final IBinder iBinder) {
            if (!c.this.b.get()) {
                try {
                    c.this.g.unbindService(this);
                } catch (Exception e) {
                    StringBuilder sb = new StringBuilder("unbind error:");
                    sb.append(e.toString());
                    com.amap.location.common.d.a.c("mainserviceproxy", sb.toString());
                }
            } else {
                c.this.h.post(new Runnable() {
                    public void run() {
                        c.this.c = iBinder;
                        c.this.d = ILocationService.Stub.a(iBinder);
                        try {
                            iBinder.linkToDeath(c.this.m, 0);
                        } catch (RemoteException unused) {
                        }
                        c.this.e.a();
                    }
                });
                c.this.h.removeCallbacks(c.this.i);
                c.this.h.postDelayed(c.this.i, 10000);
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
            c.this.h.post(new Runnable() {
                public void run() {
                    c.this.c = null;
                    c.this.a((Exception) null, 1);
                }
            });
        }
    };
    /* access modifiers changed from: private */
    public DeathRecipient m = new DeathRecipient() {
        public void binderDied() {
            c.this.h.post(new Runnable() {
                public void run() {
                    c.this.a((Exception) null, 2);
                }
            });
        }
    };

    /* compiled from: LocationServiceProxy */
    public interface a {
        void a();

        void a(int i);

        void a(Location location);

        void a(String str);

        void a(String str, int i, Bundle bundle);

        void b(String str);
    }

    public c(Context context, a aVar, Looper looper) {
        this.g = context;
        this.e = aVar;
        this.h = new Handler(looper);
        a(context);
        this.h.postDelayed(this.i, 10000);
    }

    public void a(int i2, long j2, float f2, boolean z) {
        try {
            this.d.a(i2, j2, f2, z, this.j);
        } catch (Exception e2) {
            a(e2, 3);
        }
    }

    public void b() {
        try {
            this.d.a(this.j);
        } catch (Exception e2) {
            a(e2, 3);
        }
    }

    public void a(@NonNull JSONObject jSONObject) {
        try {
            this.d.a(jSONObject.toString());
        } catch (Exception e2) {
            a(e2, 3);
        }
    }

    public void a(@NonNull String str, int i2, int i3, String str2) {
        try {
            this.d.a(str, i2, i3, str2);
        } catch (Exception e2) {
            a(e2, 3);
        }
    }

    public void a(LocationStatusListener locationStatusListener) {
        this.f = locationStatusListener;
        try {
            this.d.a(this.k);
        } catch (Exception e2) {
            a(e2, 3);
        }
    }

    public String a(String str) {
        try {
            return this.d.b(str);
        } catch (Exception e2) {
            a(e2, 3);
            return null;
        }
    }

    public void c() {
        if (a()) {
            b();
            try {
                this.d.a();
            } catch (Exception e2) {
                a(e2, 3);
            }
        }
    }

    public boolean a() {
        return this.d != null;
    }

    private void a(Context context) {
        StringBuilder sb = new StringBuilder("bind times:");
        sb.append(this.a.size());
        com.amap.location.common.d.a.b("mainserviceproxy", sb.toString());
        Intent intent = new Intent(context, AMapService.class);
        intent.putExtra("foreground", true);
        intent.setPackage(context.getPackageName());
        try {
            if (!context.bindService(intent, this.l, 1)) {
                com.amap.location.common.d.a.c("mainserviceproxy", "bind error");
            }
        } catch (Exception e2) {
            StringBuilder sb2 = new StringBuilder("bind error:");
            sb2.append(e2.toString());
            com.amap.location.common.d.a.c("mainserviceproxy", sb2.toString());
        }
    }

    /* access modifiers changed from: private */
    public void a(Exception exc, int i2) {
        if (this.d != null) {
            this.d = null;
            this.e.a(i2);
            if (d()) {
                a(this.g);
            } else {
                com.amap.location.common.d.a.c("mainserviceproxy", "error too much, stop connect");
            }
            this.h.removeCallbacks(this.i);
        }
        if (exc != null) {
            com.amap.location.common.d.a.a((Throwable) exc);
        }
    }

    private boolean d() {
        int size = this.a.size();
        long currentTimeMillis = System.currentTimeMillis();
        boolean z = false;
        if (size != 2) {
            if (size < 2) {
                this.a.add(Long.valueOf(currentTimeMillis));
            }
            this.b.set(z);
            return z;
        } else if (currentTimeMillis - this.a.get(0).longValue() < StatisticConfig.MIN_UPLOAD_INTERVAL) {
            this.a.add(Long.valueOf(currentTimeMillis));
            this.b.set(z);
            return z;
        } else {
            this.a.add(Long.valueOf(currentTimeMillis));
            this.a.remove(0);
        }
        z = true;
        this.b.set(z);
        return z;
    }
}
