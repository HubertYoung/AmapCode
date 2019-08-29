package com.autonavi.link.adapter.server.d;

import android.content.Context;
import android.util.DisplayMetrics;
import com.autonavi.link.adapter.model.SocketConnectState;
import com.autonavi.link.adapter.server.b.a.C0046a;
import com.autonavi.link.adapter.server.b.b;
import com.autonavi.link.adapter.server.listener.OnWifiConnectedListener;
import com.autonavi.link.adapter.server.listener.OnWifiDataReceivedListener;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.ref.WeakReference;

/* compiled from: WifiEngineImpl */
public class a {
    /* access modifiers changed from: private */
    public WeakReference<Context> a;
    /* access modifiers changed from: private */
    public C0048a b;
    /* access modifiers changed from: private */
    public b c;
    /* access modifiers changed from: private */
    public OnWifiConnectedListener d;
    /* access modifiers changed from: private */
    public OnWifiDataReceivedListener e;
    private final C0046a f = new C0046a() {
        public void a(int i) {
        }

        public void a(int i, int i2) {
        }

        public void a(SocketConnectState socketConnectState) {
            if (socketConnectState == SocketConnectState.CONNECTED) {
                a.this.b.a(a.this.c.a());
                return;
            }
            if (socketConnectState == SocketConnectState.DISCONNECT && a.this.d != null) {
                a.this.d.onWifiState(socketConnectState.ordinal());
            }
        }

        public void a(byte[] bArr) {
            if (a.this.e != null) {
                a.this.e.onDataRequestReponse(bArr);
            }
        }

        public void b(boolean z) {
            a.this.b.a((String) "Cadillac", (String) "测试Cadillac车型信息");
        }

        public void a() {
            if (a.this.a != null) {
                Context context = (Context) a.this.a.get();
                if (context != null) {
                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    a.this.b.a(displayMetrics.widthPixels, displayMetrics.heightPixels);
                    if (a.this.d != null) {
                        a.this.d.onWifiState(SocketConnectState.CONNECTED.ordinal());
                    }
                }
            }
        }

        public void a(boolean z, int i, boolean z2) {
            a.this.b.b(32, 1);
        }

        public void a(boolean z) {
            if (a.this.d != null) {
                a.this.d.onIsTvDataOutput(z);
            }
            a.this.b.b(42, 1);
        }

        public void a(int i, boolean z) {
            a.this.b.b(38, i);
        }
    };

    /* renamed from: com.autonavi.link.adapter.server.d.a$a reason: collision with other inner class name */
    /* compiled from: WifiEngineImpl */
    class C0048a extends com.autonavi.link.adapter.server.a.a {
        private OutputStream b;

        private C0048a() {
        }

        public void a(OutputStream outputStream) {
            this.b = outputStream;
        }

        public void a(byte[] bArr) {
            try {
                if (this.b != null) {
                    this.b.write(bArr);
                    this.b.flush();
                }
            } catch (IOException unused) {
            }
        }
    }

    public a(Context context) {
        this.a = new WeakReference<>(context);
        this.c = new b();
        this.c.a(this.f);
        this.c.b();
        this.b = new C0048a();
    }

    public void a() {
        if (this.a != null) {
            this.a.clear();
            this.a = null;
        }
        this.c.c();
        if (this.b != null) {
            this.b.a();
        }
    }

    public void a(byte[] bArr) {
        this.b.b(bArr);
    }

    public void a(OnWifiConnectedListener onWifiConnectedListener) {
        this.d = onWifiConnectedListener;
    }

    public void a(OnWifiDataReceivedListener onWifiDataReceivedListener) {
        this.e = onWifiDataReceivedListener;
    }
}
