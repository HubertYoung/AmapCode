package com.amap.location.g.b.a.d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiresApi(api = 24)
/* compiled from: GnssStatusManager */
public class a extends Callback {
    /* access modifiers changed from: private */
    public final List<C0024a> a = new CopyOnWriteArrayList();
    /* access modifiers changed from: private */
    public com.amap.location.g.b.a.c.a b;
    private Context c;
    private b d = new b(this);

    /* renamed from: com.amap.location.g.b.a.d.a$a reason: collision with other inner class name */
    /* compiled from: GnssStatusManager */
    static class C0024a {
        Callback a;
        private Handler b;

        /* renamed from: com.amap.location.g.b.a.d.a$a$a reason: collision with other inner class name */
        /* compiled from: GnssStatusManager */
        static class C0025a extends Handler {
            private Callback a;

            C0025a(Callback callback, Looper looper) {
                super(looper);
                this.a = callback;
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        this.a.onStarted();
                        return;
                    case 2:
                        this.a.onStopped();
                        return;
                    case 3:
                        this.a.onFirstFix(((Integer) message.obj).intValue());
                        return;
                    case 4:
                        this.a.onSatelliteStatusChanged((GnssStatus) message.obj);
                        break;
                }
            }
        }

        C0024a(Callback callback, Looper looper) {
            this.a = callback;
            this.b = new C0025a(this.a, looper == null ? Looper.getMainLooper() : looper);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Callback callback, Looper looper) {
            if (looper == null) {
                looper = Looper.getMainLooper();
            }
            return this.a == callback && this.b.getLooper() == looper;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Object obj) {
            Message obtainMessage = this.b.obtainMessage();
            obtainMessage.what = i;
            obtainMessage.obj = obj;
            obtainMessage.sendToTarget();
        }
    }

    /* compiled from: GnssStatusManager */
    class b extends BroadcastReceiver {
        private Callback b;

        public b(Callback callback) {
            this.b = callback;
        }

        public void onReceive(Context context, Intent intent) {
            if (com.amap.location.g.b.a.a(context).a((String) WidgetType.GPS)) {
                synchronized (a.this.a) {
                    if (a.this.a.size() > 0) {
                        try {
                            a.this.b.b(this.b);
                            a.this.b.a(this.b);
                        } catch (SecurityException e) {
                            com.amap.location.common.d.a.a("gnstmgr", "", e);
                        }
                    }
                }
            }
        }
    }

    public a(com.amap.location.g.b.a.c.a aVar, Context context) {
        this.b = aVar;
        this.c = context;
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public boolean a(Callback callback, Looper looper) {
        boolean a2;
        if (callback == null) {
            return false;
        }
        synchronized (this.a) {
            C0024a b2 = b(callback);
            if (b2 != null) {
                boolean a3 = b2.a(callback, looper);
                return a3;
            }
            C0024a aVar = new C0024a(callback, looper);
            this.a.add(aVar);
            if (this.a.size() != 1) {
                return true;
            }
            a2 = this.b.a((Callback) this);
            if (!a2) {
                this.a.remove(aVar);
            } else {
                try {
                    this.c.registerReceiver(this.d, new IntentFilter("android.location.PROVIDERS_CHANGED"));
                } catch (Exception e) {
                    com.amap.location.common.d.a.a("gnstmgr", "", e);
                }
            }
        }
        return a2;
    }

    public void a(Callback callback) {
        if (callback != null) {
            synchronized (this.a) {
                C0024a b2 = b(callback);
                if (b2 != null) {
                    boolean remove = this.a.remove(b2);
                    if (this.a.size() == 0 && remove) {
                        this.b.b((Callback) this);
                        try {
                            this.c.unregisterReceiver(this.d);
                        } catch (Exception e) {
                            com.amap.location.common.d.a.a("gnstmgr", "", e);
                        }
                    }
                }
            }
        }
    }

    public void onStarted() {
        synchronized (this.a) {
            for (C0024a a2 : this.a) {
                a2.a(1, (Object) null);
            }
        }
    }

    public void onStopped() {
        synchronized (this.a) {
            for (C0024a a2 : this.a) {
                a2.a(2, (Object) null);
            }
        }
    }

    public void onFirstFix(int i) {
        synchronized (this.a) {
            for (C0024a a2 : this.a) {
                a2.a(3, (Object) Integer.valueOf(i));
            }
        }
    }

    public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
        synchronized (this.a) {
            for (C0024a a2 : this.a) {
                a2.a(4, (Object) gnssStatus);
            }
        }
    }

    private C0024a b(Callback callback) {
        for (C0024a next : this.a) {
            if (next.a == callback) {
                return next;
            }
        }
        return null;
    }
}
