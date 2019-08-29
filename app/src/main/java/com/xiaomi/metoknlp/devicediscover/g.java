package com.xiaomi.metoknlp.devicediscover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.HandlerThread;
import android.os.Message;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.client.api.impl.TokenApiImpl;
import com.xiaomi.metoknlp.a;
import com.xiaomi.metoknlp.b;

public class g {
    private static final long a = (b.b() ? StatisticConfig.MIN_UPLOAD_INTERVAL : TokenApiImpl.TOKEN_EXPIRE_PROTECT_INTERVAL);
    private static final Object e = new Object();
    private Context b;
    private ConnectivityManager c;
    private p d;
    private c f;
    private HandlerThread g;
    /* access modifiers changed from: private */
    public o h;
    private BroadcastReceiver i = new l(this);

    static {
        b.a();
    }

    public g(Context context) {
        this.b = context;
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        NetworkInfo networkInfo = null;
        try {
            if (!(this.b == null || this.b.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.b.getPackageName()) != 0 || this.c == null)) {
                networkInfo = this.c.getActiveNetworkInfo();
            }
        } catch (Exception unused) {
        }
        if (this.f != null) {
            if (networkInfo == null || networkInfo.getType() != 1 || !networkInfo.isConnected()) {
                this.f.h();
                return;
            }
            String a2 = j.a(this.b, 1);
            if (this.f.b() == null || !this.f.b().equals(a2)) {
                this.f.a(a2);
            }
            if (this.h.hasMessages(2)) {
                this.h.removeMessages(2);
            }
            Message obtainMessage = this.h.obtainMessage(2);
            long j = a;
            obtainMessage.obj = Boolean.valueOf(z);
            if (z) {
                this.h.sendMessage(obtainMessage);
            } else {
                this.h.sendMessageDelayed(obtainMessage, j);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b(boolean z) {
        if (!b.a().f()) {
            return;
        }
        if (z || (e() && g() && f())) {
            h();
            this.f.g();
            this.f.i();
        }
    }

    private boolean e() {
        long currentTimeMillis = System.currentTimeMillis();
        long c2 = this.f.c();
        long j = b.a().j();
        if (j == Long.MAX_VALUE) {
            j = a;
        }
        String b2 = this.f.b();
        return b2 != null && b2.equals(j.a(this.b, 1)) && currentTimeMillis - c2 >= j;
    }

    private boolean f() {
        boolean z = true;
        if (b.a().h()) {
            long i2 = b.a().i();
            if (i2 == Long.MAX_VALUE) {
                i2 = 172800000;
            }
            this.f.f();
            if (this.f.d() > i2) {
                return true;
            }
            z = false;
        }
        return z;
    }

    private boolean g() {
        long e2 = this.f.e();
        long g2 = b.a().g();
        if (g2 == Long.MAX_VALUE) {
            g2 = 172800000;
        }
        return System.currentTimeMillis() - e2 > g2;
    }

    private void h() {
        this.d.a(this.f.b(), this.f.c(), this.f.d());
    }

    private int i() {
        try {
            return ((a) this.b).b();
        } catch (Exception unused) {
            return 0;
        }
    }

    private void j() {
        this.b.registerReceiver(this.i, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    private void k() {
        if (this.h.hasMessages(1)) {
            this.h.removeMessages(1);
        }
        if (this.h.hasMessages(2)) {
            this.h.removeMessages(2);
        }
        this.b.unregisterReceiver(this.i);
    }

    public void a() {
        a(true);
    }

    public void a(p pVar) {
        synchronized (e) {
            this.d = pVar;
        }
    }

    public void b() {
        this.f = new c(this.b);
        this.c = (ConnectivityManager) this.b.getSystemService("connectivity");
        this.g = new HandlerThread("WifiCampStatics");
        this.g.start();
        this.h = new o(this, this.g.getLooper());
        if (i() == 0) {
            j();
        }
    }

    public void c() {
        if (i() == 0) {
            k();
        }
        this.c = null;
        this.f.a();
        if (this.g != null) {
            this.g.quitSafely();
            this.g = null;
        }
    }

    public void d() {
        synchronized (e) {
            this.d = null;
        }
    }
}
