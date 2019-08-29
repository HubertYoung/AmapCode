package com.amap.location.uptunnel.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import com.amap.location.uptunnel.ConfigContainer;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: UpTunnelManager */
public class c {
    /* access modifiers changed from: private */
    public HandlerThread a;
    /* access modifiers changed from: private */
    public Looper b;
    /* access modifiers changed from: private */
    public b c;
    /* access modifiers changed from: private */
    public a d;
    /* access modifiers changed from: private */
    public b e;
    /* access modifiers changed from: private */
    public a f;
    /* access modifiers changed from: private */
    public final Object g = new byte[0];
    /* access modifiers changed from: private */
    public ArrayList<Message> h = new ArrayList<>();

    /* compiled from: UpTunnelManager */
    final class a extends BroadcastReceiver {
        private a() {
        }

        public final void onReceive(Context context, Intent intent) {
            if (context != null && intent != null) {
                String action = intent.getAction();
                char c = 65535;
                if (action.hashCode() == -1172645946 && action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                    c = 0;
                }
                if (c == 0 && c.this.c != null) {
                    c.this.c.sendEmptyMessage(11);
                }
            }
        }
    }

    /* compiled from: UpTunnelManager */
    class b extends Handler {
        private boolean b;

        b(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (!this.b) {
                if (message.what == 10) {
                    c.this.d.a(message);
                } else if (message.what == 11) {
                    c.this.d.a();
                } else if (message.what == 12) {
                    c.this.d.a((com.amap.location.uptunnel.a.a) message.obj);
                } else {
                    if (message.what == 13) {
                        this.b = true;
                        removeCallbacksAndMessages(null);
                        c.this.d.b();
                        try {
                            c.this.e.a().unregisterReceiver(c.this.f);
                        } catch (Exception e) {
                            com.amap.location.common.d.a.a((Throwable) e);
                        }
                        post(new Runnable() {
                            public void run() {
                                try {
                                    b.this.getLooper().quit();
                                } catch (Throwable th) {
                                    com.amap.location.common.d.a.a(th);
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public c(@NonNull final Context context, @NonNull final ConfigContainer configContainer) {
        this.a = new HandlerThread("UpTunnelWorkThread") {
            /* access modifiers changed from: protected */
            public void onLooperPrepared() {
                synchronized (c.this.g) {
                    c.this.e = new b(context);
                    c.this.b = c.this.a.getLooper();
                    c.this.c = new b(c.this.b);
                    c.this.d = new a();
                    c.this.d.a(c.this.e, configContainer, c.this.b);
                    c.this.b();
                    Iterator it = c.this.h.iterator();
                    while (it.hasNext()) {
                        c.this.c.sendMessage((Message) it.next());
                    }
                    c.this.h.clear();
                }
            }
        };
        this.a.start();
    }

    /* access modifiers changed from: private */
    public void b() {
        this.f = new a();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        try {
            this.e.a().registerReceiver(this.f, intentFilter);
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    public void a(int i) {
        a(10, 1, i, null);
    }

    public void a(int i, byte[] bArr) {
        a(10, 2, i, bArr);
    }

    public void b(int i, byte[] bArr) {
        a(10, 5, i, bArr);
    }

    public void c(int i, byte[] bArr) {
        a(10, 3, i, bArr);
    }

    public void a(int i, String str) {
        a(10, 4, i, str);
    }

    public void a(@NonNull com.amap.location.uptunnel.a.a aVar) {
        a(12, -1, -1, aVar);
    }

    public void a() {
        a(13, 0, 0, null);
    }

    public long b(int i) {
        b bVar = this.e;
        if (bVar != null) {
            return bVar.d(i);
        }
        return -1;
    }

    private void a(int i, int i2, int i3, Object obj) {
        if (this.c != null) {
            this.c.obtainMessage(i, i2, i3, obj).sendToTarget();
            return;
        }
        synchronized (this.g) {
            if (this.c != null) {
                this.c.obtainMessage(i, i2, i3, obj).sendToTarget();
            } else {
                this.h.add(Message.obtain(null, i, i2, i3, obj));
            }
        }
    }
}
