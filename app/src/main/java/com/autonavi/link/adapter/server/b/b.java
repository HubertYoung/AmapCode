package com.autonavi.link.adapter.server.b;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.adapter.model.SocketConnectState;
import com.autonavi.link.adapter.server.b.a.C0046a;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;

@SuppressLint({"HandlerLeak"})
/* compiled from: ConnectionManager */
public class b {
    /* access modifiers changed from: private */
    public C0047b a;
    /* access modifiers changed from: private */
    public C0046a b = null;
    /* access modifiers changed from: private */
    public com.autonavi.link.adapter.server.c.a c;
    /* access modifiers changed from: private */
    public com.autonavi.link.adapter.b.a d;
    /* access modifiers changed from: private */
    public Object e = new Object();
    /* access modifiers changed from: private */
    public ServerSocket f;
    /* access modifiers changed from: private */
    public Handler g;
    /* access modifiers changed from: private */
    public com.autonavi.link.adapter.a.b h = new com.autonavi.link.adapter.a.b() {
        public void a(String str, String str2, boolean z) {
        }

        public void b(byte[] bArr) {
        }

        public void a(int i, int i2) {
            if (b.this.b != null) {
                b.this.b.a(i, i2);
            }
        }

        public void f() {
            if (b.this.b != null) {
                b.this.b.a();
            }
        }

        public void a(int i) {
            if (b.this.b != null) {
                b.this.b.a(i);
            }
        }

        public void a(byte[] bArr) {
            if (b.this.b != null) {
                b.this.b.a(bArr);
            }
        }

        public void a(boolean z) {
            if (b.this.b != null) {
                b.this.b.a(z);
            }
        }

        public void a(boolean z, int i, boolean z2) {
            if (b.this.b != null) {
                b.this.b.a(z, i, z2);
            }
        }

        public void b(boolean z) {
            if (b.this.b != null) {
                b.this.b.b(z);
            }
        }

        public void a(int i, boolean z) {
            if (b.this.b != null) {
                b.this.b.a(i, z);
            }
        }
    };

    /* compiled from: ConnectionManager */
    class a extends Thread {
        private a() {
        }

        /* JADX WARNING: Missing exception handler attribute for start block: B:16:0x0076 */
        /* JADX WARNING: Missing exception handler attribute for start block: B:18:0x0084 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r4 = this;
                r0 = 50
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ IOException -> 0x00b8 }
                java.net.ServerSocket r2 = new java.net.ServerSocket     // Catch:{ IOException -> 0x00b8 }
                r3 = 4545(0x11c1, float:6.369E-42)
                r2.<init>(r3)     // Catch:{ IOException -> 0x00b8 }
                r1.f = r2     // Catch:{ IOException -> 0x00b8 }
            L_0x000e:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                android.os.Handler r1 = r1.g     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r2 = 20
                android.os.Message r1 = r1.obtainMessage(r2)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r1.sendToTarget()     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                java.net.ServerSocket r1 = r1.f     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                java.net.Socket r1 = r1.accept()     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r2 = 1
                r1.setOOBInline(r2)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r1.setKeepAlive(r2)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.c.a r3 = new com.autonavi.link.adapter.server.c.a     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r3.<init>(r1)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r2.c = r3     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.c.a r1 = r1.c     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.b.a r2 = r2.d     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r1.a(r2)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.b.a r1 = r1.d     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.a.b r2 = r2.h     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r1.a(r2)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                android.os.Handler r1 = r1.g     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r2 = 70
                r1.sendEmptyMessage(r2)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ InterruptedException -> 0x0076 }
                java.lang.Object r1 = r1.e     // Catch:{ InterruptedException -> 0x0076 }
                monitor-enter(r1)     // Catch:{ InterruptedException -> 0x0076 }
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ all -> 0x0073 }
                java.lang.Object r2 = r2.e     // Catch:{ all -> 0x0073 }
                r2.wait()     // Catch:{ all -> 0x0073 }
                monitor-exit(r1)     // Catch:{ all -> 0x0073 }
                goto L_0x000e
            L_0x0073:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0073 }
                throw r2     // Catch:{ InterruptedException -> 0x0076 }
            L_0x0076:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                android.os.Handler r1 = r1.g     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                android.os.Message r1 = r1.obtainMessage(r0)     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                r1.sendToTarget()     // Catch:{ SocketException -> 0x00a0, IOException -> 0x0092, Exception -> 0x0084 }
                goto L_0x00ad
            L_0x0084:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ IOException -> 0x00b8 }
                android.os.Handler r1 = r1.g     // Catch:{ IOException -> 0x00b8 }
                android.os.Message r1 = r1.obtainMessage(r0)     // Catch:{ IOException -> 0x00b8 }
                r1.sendToTarget()     // Catch:{ IOException -> 0x00b8 }
                goto L_0x00ad
            L_0x0092:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ IOException -> 0x00b8 }
                android.os.Handler r1 = r1.g     // Catch:{ IOException -> 0x00b8 }
                android.os.Message r1 = r1.obtainMessage(r0)     // Catch:{ IOException -> 0x00b8 }
                r1.sendToTarget()     // Catch:{ IOException -> 0x00b8 }
                goto L_0x00ad
            L_0x00a0:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ IOException -> 0x00b8 }
                android.os.Handler r1 = r1.g     // Catch:{ IOException -> 0x00b8 }
                android.os.Message r1 = r1.obtainMessage(r0)     // Catch:{ IOException -> 0x00b8 }
                r1.sendToTarget()     // Catch:{ IOException -> 0x00b8 }
            L_0x00ad:
                com.autonavi.link.adapter.server.b.b r0 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ IOException -> 0x00b7 }
                java.net.ServerSocket r0 = r0.f     // Catch:{ IOException -> 0x00b7 }
                r0.close()     // Catch:{ IOException -> 0x00b7 }
                return
            L_0x00b7:
                return
            L_0x00b8:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                android.os.Handler r1 = r1.g
                android.os.Message r0 = r1.obtainMessage(r0)
                r0.sendToTarget()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.adapter.server.b.b.a.run():void");
        }
    }

    /* renamed from: com.autonavi.link.adapter.server.b.b$b reason: collision with other inner class name */
    /* compiled from: ConnectionManager */
    class C0047b extends Thread {
        private C0047b() {
        }

        /* JADX WARNING: Missing exception handler attribute for start block: B:25:0x004e */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r4 = this;
                r0 = 30
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ SocketException -> 0x004e, Exception -> 0x0034 }
                com.autonavi.link.adapter.server.c.a r1 = r1.c     // Catch:{ SocketException -> 0x004e, Exception -> 0x0034 }
                r1.c()     // Catch:{ SocketException -> 0x004e, Exception -> 0x0034 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                r1.d()
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                java.lang.Object r1 = r1.e
                monitor-enter(r1)
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ all -> 0x002f }
                java.lang.Object r2 = r2.e     // Catch:{ all -> 0x002f }
                r2.notify()     // Catch:{ all -> 0x002f }
                monitor-exit(r1)     // Catch:{ all -> 0x002f }
            L_0x0021:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                android.os.Handler r1 = r1.g
                android.os.Message r0 = r1.obtainMessage(r0)
                r0.sendToTarget()
                return
            L_0x002f:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x002f }
                throw r0
            L_0x0032:
                r1 = move-exception
                goto L_0x0077
            L_0x0034:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                r1.d()
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                java.lang.Object r1 = r1.e
                monitor-enter(r1)
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ all -> 0x004b }
                java.lang.Object r2 = r2.e     // Catch:{ all -> 0x004b }
                r2.notify()     // Catch:{ all -> 0x004b }
                monitor-exit(r1)     // Catch:{ all -> 0x004b }
                goto L_0x0021
            L_0x004b:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x004b }
                throw r0
            L_0x004e:
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ all -> 0x0032 }
                android.os.Handler r1 = r1.g     // Catch:{ all -> 0x0032 }
                r2 = 50
                android.os.Message r1 = r1.obtainMessage(r2)     // Catch:{ all -> 0x0032 }
                r1.sendToTarget()     // Catch:{ all -> 0x0032 }
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                r1.d()
                com.autonavi.link.adapter.server.b.b r1 = com.autonavi.link.adapter.server.b.b.this
                java.lang.Object r1 = r1.e
                monitor-enter(r1)
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ all -> 0x0074 }
                java.lang.Object r2 = r2.e     // Catch:{ all -> 0x0074 }
                r2.notify()     // Catch:{ all -> 0x0074 }
                monitor-exit(r1)     // Catch:{ all -> 0x0074 }
                goto L_0x0021
            L_0x0074:
                r0 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0074 }
                throw r0
            L_0x0077:
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this
                r2.d()
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this
                java.lang.Object r2 = r2.e
                monitor-enter(r2)
                com.autonavi.link.adapter.server.b.b r3 = com.autonavi.link.adapter.server.b.b.this     // Catch:{ all -> 0x009b }
                java.lang.Object r3 = r3.e     // Catch:{ all -> 0x009b }
                r3.notify()     // Catch:{ all -> 0x009b }
                monitor-exit(r2)     // Catch:{ all -> 0x009b }
                com.autonavi.link.adapter.server.b.b r2 = com.autonavi.link.adapter.server.b.b.this
                android.os.Handler r2 = r2.g
                android.os.Message r0 = r2.obtainMessage(r0)
                r0.sendToTarget()
                throw r1
            L_0x009b:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x009b }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.adapter.server.b.b.C0047b.run():void");
        }
    }

    public b() {
        e();
    }

    public void a(C0046a aVar) {
        this.b = aVar;
    }

    public OutputStream a() {
        if (this.c != null) {
            return this.c.a();
        }
        return null;
    }

    public void b() {
        this.d = new com.autonavi.link.adapter.b.a();
        new a().start();
    }

    public void c() {
        d();
        try {
            if (this.a != null && this.a.isAlive() && !this.a.isInterrupted()) {
                this.a.interrupt();
            }
            if (this.f != null) {
                this.f.close();
            }
        } catch (IOException unused) {
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        if (this.c != null) {
            this.c.b();
        }
    }

    private void e() {
        this.g = new Handler(Looper.getMainLooper()) {
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 10) {
                    b.this.a(SocketConnectState.CONNECTED);
                } else if (i == 20) {
                    b.this.a(SocketConnectState.LISTENING);
                } else if (i == 30) {
                    b.this.a(SocketConnectState.DISCONNECT);
                } else if (i == 40) {
                    b.this.a(SocketConnectState.CONNECT_TIMEOUT);
                } else if (i == 50) {
                    b.this.a(SocketConnectState.CONNECT_FAILED);
                } else if (i != 60) {
                    if (i == 70) {
                        b.this.a = new C0047b();
                        b.this.a.start();
                        b.this.g.obtainMessage(10).sendToTarget();
                    }
                } else {
                    b.this.a(SocketConnectState.DISCONNECT);
                }
            }
        };
    }

    /* access modifiers changed from: private */
    public void a(SocketConnectState socketConnectState) {
        if (this.b != null) {
            this.b.a(socketConnectState);
        }
    }
}
