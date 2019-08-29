package defpackage;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.text.TextUtils;
import com.autonavi.widget.ui.BalloonLayout;
import com.vivo.vms.IPCInvoke;
import com.vivo.vms.IPCInvoke.Stub;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: exa reason: default package */
/* compiled from: IPCManager */
public final class exa implements ServiceConnection {
    private static final Object d = new Object();
    private static Map<String, exa> e = new HashMap();
    public boolean a;
    public String b = null;
    public Context c;
    /* access modifiers changed from: private */
    public AtomicInteger f;
    private volatile IPCInvoke g;
    private Object h = new Object();
    private String i;
    private Handler j = null;

    private exa(Context context, String str) {
        this.c = context;
        this.i = str;
        this.b = faw.b(context);
        boolean z = false;
        if (TextUtils.isEmpty(this.b) || TextUtils.isEmpty(this.i)) {
            Context context2 = this.c;
            StringBuilder sb = new StringBuilder("init error : push pkgname is ");
            sb.append(this.b);
            sb.append(" ; action is ");
            sb.append(this.i);
            fat.c(context2, sb.toString());
            this.a = false;
            return;
        }
        this.a = fbd.a(context, this.b) >= 1260 ? true : z;
        this.f = new AtomicInteger(1);
        this.j = new Handler(Looper.getMainLooper(), new exy(this));
        a();
    }

    public static exa a(Context context, String str) {
        exa exa = e.get(str);
        if (exa == null) {
            synchronized (d) {
                try {
                    exa = e.get(str);
                    if (exa == null) {
                        exa = new exa(context, str);
                        e.put(str, exa);
                    }
                }
            }
        }
        return exa;
    }

    private void a() {
        int i2 = this.f.get();
        fat.d("AidlManager", "Enter connect, Connection Status: ".concat(String.valueOf(i2)));
        if (!(i2 == 4 || i2 == 2 || i2 == 3 || i2 == 5 || !this.a)) {
            a(2);
            if (!b()) {
                a(1);
                fat.a((String) "AidlManager", (String) "bind core service fail");
                return;
            }
            this.j.removeMessages(1);
            this.j.sendEmptyMessageDelayed(1, BalloonLayout.DEFAULT_DISPLAY_DURATION);
        }
    }

    private boolean b() {
        Intent intent = new Intent(this.i);
        intent.setPackage(this.b);
        try {
            return this.c.bindService(intent, this, 1);
        } catch (Exception e2) {
            fat.a("AidlManager", "bind core error", e2);
            return false;
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        this.f.set(i2);
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        c();
        this.g = Stub.asInterface(iBinder);
        if (this.g == null) {
            fat.d("AidlManager", "onServiceConnected error : aidl must not be null.");
            d();
            this.f.set(1);
            return;
        }
        if (this.f.get() == 2) {
            a(4);
        } else if (this.f.get() != 4) {
            d();
        }
        synchronized (this.h) {
            this.h.notifyAll();
        }
    }

    private void c() {
        this.j.removeMessages(1);
    }

    /* access modifiers changed from: private */
    public void d() {
        try {
            this.c.unbindService(this);
        } catch (Exception e2) {
            StringBuilder sb = new StringBuilder("On unBindServiceException:");
            sb.append(e2.getMessage());
            fat.a((String) "AidlManager", sb.toString());
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.g = null;
        a(1);
    }

    public final void onBindingDied(ComponentName componentName) {
        fat.b((String) "AidlManager", "onBindingDied : ".concat(String.valueOf(componentName)));
    }

    public final boolean a(Bundle bundle) {
        a();
        if (this.f.get() == 2) {
            synchronized (this.h) {
                try {
                    this.h.wait(2000);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
        try {
            int i2 = this.f.get();
            if (i2 == 4) {
                this.j.removeMessages(2);
                this.j.sendEmptyMessageDelayed(2, StatisticConfig.MIN_UPLOAD_INTERVAL);
                this.g.asyncCall(bundle, null);
                return true;
            }
            fat.d("AidlManager", "invoke error : connect status = ".concat(String.valueOf(i2)));
            return false;
        } catch (RemoteException e3) {
            e3.printStackTrace();
            fat.a("AidlManager", "invoke error ", e3);
            int i3 = this.f.get();
            fat.d("AidlManager", "Enter disconnect, Connection Status: ".concat(String.valueOf(i3)));
            switch (i3) {
                case 2:
                    c();
                    a(1);
                    break;
                case 3:
                    a(1);
                    break;
                case 4:
                    a(1);
                    d();
                    break;
            }
        }
    }
}
