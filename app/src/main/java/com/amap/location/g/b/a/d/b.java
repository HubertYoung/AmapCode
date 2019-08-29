package com.amap.location.g.b.a.d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.GpsStatus.Listener;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: GpsStatusManager */
public class b implements Listener {
    /* access modifiers changed from: private */
    public final List<C0026b> a = new CopyOnWriteArrayList();
    /* access modifiers changed from: private */
    public com.amap.location.g.b.a.c.a b;
    private Context c;
    private a d = new a(this);

    /* compiled from: GpsStatusManager */
    class a extends BroadcastReceiver {
        private Listener b;

        public a(Listener listener) {
            this.b = listener;
        }

        public void onReceive(Context context, Intent intent) {
            if (com.amap.location.g.b.a.a(context).a((String) WidgetType.GPS)) {
                synchronized (b.this.a) {
                    if (b.this.a.size() > 0) {
                        b.this.b.b(this.b);
                        b.this.b.a(this.b);
                    }
                }
            }
        }
    }

    /* renamed from: com.amap.location.g.b.a.d.b$b reason: collision with other inner class name */
    /* compiled from: GpsStatusManager */
    static class C0026b {
        Listener a;
        private Handler b;

        /* renamed from: com.amap.location.g.b.a.d.b$b$a */
        /* compiled from: GpsStatusManager */
        static class a extends Handler {
            private Listener a;

            a(Listener listener, Looper looper) {
                super(looper);
                this.a = listener;
            }

            public void handleMessage(Message message) {
                this.a.onGpsStatusChanged(message.arg1);
            }
        }

        C0026b(Listener listener, Looper looper) {
            this.a = listener;
            this.b = new a(this.a, looper == null ? Looper.getMainLooper() : looper);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(Listener listener, Looper looper) {
            if (looper == null) {
                looper = Looper.getMainLooper();
            }
            return this.a == listener && this.b.getLooper() == looper;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i) {
            Message obtainMessage = this.b.obtainMessage();
            obtainMessage.arg1 = i;
            obtainMessage.sendToTarget();
        }
    }

    public b(com.amap.location.g.b.a.c.a aVar, Context context) {
        this.b = aVar;
        this.c = context;
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @Deprecated
    public boolean a(Listener listener, Looper looper) {
        boolean a2;
        if (listener == null) {
            return false;
        }
        synchronized (this.a) {
            C0026b b2 = b(listener);
            if (b2 != null) {
                boolean a3 = b2.a(listener, looper);
                return a3;
            }
            C0026b bVar = new C0026b(listener, looper);
            this.a.add(bVar);
            if (this.a.size() != 1) {
                return true;
            }
            a2 = this.b.a((Listener) this);
            if (!a2) {
                this.a.remove(bVar);
            } else {
                try {
                    this.c.registerReceiver(this.d, new IntentFilter("android.location.PROVIDERS_CHANGED"));
                } catch (Exception e) {
                    com.amap.location.common.d.a.a("gpsstmgr", "", e);
                }
            }
        }
        return a2;
    }

    @Deprecated
    public void a(Listener listener) {
        if (listener != null) {
            synchronized (this.a) {
                C0026b b2 = b(listener);
                if (b2 != null) {
                    boolean remove = this.a.remove(b2);
                    if (this.a.size() == 0 && remove) {
                        this.b.b((Listener) this);
                        try {
                            this.c.unregisterReceiver(this.d);
                        } catch (Exception e) {
                            com.amap.location.common.d.a.a("gpsstmgr", "", e);
                        }
                    }
                }
            }
        }
    }

    private C0026b b(Listener listener) {
        for (C0026b next : this.a) {
            if (next.a == listener) {
                return next;
            }
        }
        return null;
    }

    public void onGpsStatusChanged(int i) {
        synchronized (this.a) {
            for (C0026b a2 : this.a) {
                a2.a(i);
            }
        }
    }
}
