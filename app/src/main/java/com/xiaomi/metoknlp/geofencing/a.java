package com.xiaomi.metoknlp.geofencing;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class a {
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public c b;
    /* access modifiers changed from: private */
    public boolean c = false;
    /* access modifiers changed from: private */
    public int d = 0;
    private List<b> e = new ArrayList();
    private List<b> f = new ArrayList();
    /* access modifiers changed from: private */
    public Handler g;
    private final ServiceConnection h = new b(this);

    /* renamed from: com.xiaomi.metoknlp.geofencing.a$a reason: collision with other inner class name */
    class C0077a extends Handler {
        public C0077a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    a.this.d = a.this.d + 1;
                    a.this.a(a.this.a);
                    StringBuilder sb = new StringBuilder("Try bindService count=");
                    sb.append(a.this.d);
                    sb.append(",mBinded=");
                    sb.append(a.this.c);
                    if (!a.this.c && a.this.g != null && a.this.d < 10) {
                        a.this.g.sendEmptyMessageDelayed(1, 10000);
                    }
                    return;
                case 2:
                    a.this.a();
                    return;
                case 3:
                    a.this.b();
                    return;
                default:
                    return;
            }
        }
    }

    class b {
        public double a;
        public double b;
        public float c;
        public long d;
        public String e;
        public String f;
        public String g;

        public b(double d2, double d3, float f2, long j, String str, String str2, String str3) {
            this.a = d2;
            this.b = d3;
            this.c = f2;
            this.d = j;
            this.e = str;
            this.f = str2;
            this.g = str3;
        }
    }

    public a(Context context) {
        this.a = context;
        this.c = false;
        a(context);
        HandlerThread handlerThread = new HandlerThread("GeoFencingServiceWrapper");
        handlerThread.start();
        this.g = new C0077a(handlerThread.getLooper());
        if (!this.c) {
            this.g.sendEmptyMessageDelayed(1, 10000);
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        if (this.e != null) {
            this.e.size();
        }
        for (b next : this.e) {
            if (!(next == null || this.b == null)) {
                try {
                    this.b.a(next.a, next.b, next.c, next.d, next.e, next.f, next.g);
                } catch (RemoteException e2) {
                    new StringBuilder("registerPendingFence:").append(e2);
                }
            }
        }
        if (this.e != null) {
            this.e.clear();
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        if (this.f != null) {
            this.f.size();
        }
        for (b next : this.f) {
            if (!(next == null || this.b == null)) {
                try {
                    this.b.a(next.e, next.f);
                } catch (RemoteException e2) {
                    new StringBuilder("unregisterPendingFence:").append(e2);
                }
            }
        }
        if (this.f != null) {
            this.f.clear();
        }
    }

    public void a(Context context) {
        if (!this.c && context != null && this.b == null) {
            Intent intent = new Intent("com.xiaomi.metoknlp.GeoFencingService");
            intent.setPackage("com.xiaomi.metoknlp");
            try {
                if (!context.bindService(intent, this.h, 1)) {
                    this.c = false;
                } else {
                    this.c = true;
                }
            } catch (SecurityException e2) {
                new StringBuilder("SecurityException:").append(e2);
            }
        }
    }

    public void a(Context context, double d2, double d3, float f2, long j, String str, String str2, String str3) {
        a(context);
        if (this.b != null) {
            try {
                this.b.a(d2, d3, f2, j, str, str2, str3);
            } catch (RemoteException e2) {
                throw new RuntimeException("GeoFencingService has died", e2);
            }
        } else {
            b bVar = new b(d2, d3, f2, j, str, str2, str3);
            this.e.add(bVar);
        }
    }

    public void a(Context context, String str, String str2) {
        a(context);
        if (this.b != null) {
            try {
                this.b.a(str, str2);
            } catch (RemoteException e2) {
                throw new RuntimeException("GeoFencingService has died", e2);
            }
        } else {
            b bVar = new b(0.0d, 0.0d, 0.0f, -1, str, str2, "");
            this.f.add(bVar);
        }
    }
}
