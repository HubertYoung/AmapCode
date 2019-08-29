package com.autonavi.link.adapter.client;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.adapter.client.a.a.C0042a;
import com.autonavi.link.adapter.client.b.c.a;
import com.autonavi.link.adapter.client.listener.OnALinkConnectedListener;
import com.autonavi.link.adapter.client.listener.OnAlinkDataRequestListener;
import com.autonavi.link.adapter.model.SocketConnectState;
import com.autonavi.link.utils.ConnectState;
import java.io.IOException;
import java.util.Iterator;

public class SocketClientProvider {
    private static volatile SocketClientProvider c;
    /* access modifiers changed from: private */
    public Context a = null;
    private boolean b = false;
    /* access modifiers changed from: private */
    public a d = null;
    private com.autonavi.link.adapter.client.b.a.a e;
    private final int f = 1;
    private final C0042a g = new C0042a() {
        public void a() {
        }

        public void b(byte[] bArr) {
        }

        public void a(int i, SocketConnectState socketConnectState) {
            if (socketConnectState == SocketConnectState.CONNECTED) {
                SocketClientProvider.this.i = true;
                SocketClientProvider.this.d = a.a();
                if (SocketClientProvider.this.a != null) {
                    try {
                        SocketClientProvider.this.d.b();
                        SocketClientProvider.this.d.a(1);
                        SocketClientProvider.this.d.c();
                    } catch (IOException unused) {
                    }
                }
            } else if (socketConnectState == SocketConnectState.DISCONNECT && !SocketClientProvider.this.h) {
                SocketClientProvider.this.i = false;
                try {
                    SocketClientProvider.this.stopConnect();
                } catch (IOException unused2) {
                }
                SocketClientProvider.this.a(SocketConnectState.DISCONNECT);
            }
        }

        public void a(int i, int i2) {
            SocketClientProvider.this.a(SocketConnectState.CONNECTED);
        }

        public void a(byte[] bArr) {
            if (SocketClientProvider.this.mOnAlinkDataRequestListener != null) {
                SocketClientProvider.this.mOnAlinkDataRequestListener.alinkDataRequest(bArr);
            }
        }
    };
    /* access modifiers changed from: private */
    public boolean h = false;
    /* access modifiers changed from: private */
    public boolean i = false;
    private final Handler j = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 1) {
                Iterator<OnALinkConnectedListener> it = com.autonavi.link.adapter.client.listener.a.a().c().iterator();
                while (it.hasNext()) {
                    it.next().onIsALinkConnected(SocketClientProvider.this.i);
                }
            }
        }
    };
    public OnAlinkDataRequestListener mOnAlinkDataRequestListener;

    private SocketClientProvider() {
    }

    public static SocketClientProvider getInstance() {
        if (c == null) {
            synchronized (SocketClientProvider.class) {
                if (c == null) {
                    c = new SocketClientProvider();
                }
            }
        }
        return c;
    }

    public boolean getIsConnected() {
        return this.i;
    }

    public void init(Context context) {
        if (!this.b) {
            this.b = true;
            this.h = false;
            if (this.a == null) {
                this.a = context;
            }
        }
    }

    public void unInit() {
        this.h = true;
        if (this.b) {
            this.b = false;
            try {
                stopConnect();
            } catch (IOException unused) {
            }
            com.autonavi.link.adapter.client.listener.a.a().b();
            this.a = null;
        }
    }

    public boolean isInit() {
        return this.b;
    }

    public void sendAlinkDataReponse(byte[] bArr) throws Exception {
        if (!com.autonavi.link.adapter.client.b.a.a.a().e()) {
            throw new Exception("fail to Connect Server!!!");
        } else if (this.d != null) {
            this.d.a(bArr);
        }
    }

    public void doConnect() {
        this.e = com.autonavi.link.adapter.client.b.a.a.a();
        this.e.a(this.g);
        this.e.b();
    }

    public void stopConnect() throws IOException {
        if (this.e != null) {
            this.e.c();
        }
        this.i = false;
    }

    public void notifyAppIsRun() {
        if (this.d != null) {
            try {
                this.d.a(41, 3);
            } catch (IOException unused) {
            }
        }
    }

    /* access modifiers changed from: private */
    public synchronized void a(SocketConnectState socketConnectState) {
        this.i = false;
        if (ConnectState.CONNECTED.ordinal() == socketConnectState.ordinal()) {
            this.i = true;
        }
        Message message = new Message();
        message.obj = Boolean.valueOf(this.i);
        message.what = 1;
        this.j.sendMessage(message);
    }

    public boolean addOnALinkConnectedListener(OnALinkConnectedListener onALinkConnectedListener) {
        return com.autonavi.link.adapter.client.listener.a.a().a(onALinkConnectedListener);
    }

    public boolean removeOnALinkConnectedListener(OnALinkConnectedListener onALinkConnectedListener) {
        return com.autonavi.link.adapter.client.listener.a.a().b(onALinkConnectedListener);
    }

    public void setOnAlinkDataRequestListener(OnAlinkDataRequestListener onAlinkDataRequestListener) {
        this.mOnAlinkDataRequestListener = onAlinkDataRequestListener;
    }
}
