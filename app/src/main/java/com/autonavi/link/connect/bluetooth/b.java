package com.autonavi.link.connect.bluetooth;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.transmit.impl.BluetoothConnection;
import com.autonavi.link.transmit.inter.LinkProxyError;
import com.autonavi.link.transmit.proxy.LinkProxy;
import java.io.IOException;
import java.util.UUID;

@SuppressLint({"NewApi"})
/* compiled from: BluetoothSppService */
public class b {
    /* access modifiers changed from: private */
    public static final UUID a = UUID.fromString("fa87c0d0-2199-1724-12cc-0800200c9a66");
    /* access modifiers changed from: private */
    public static final UUID b = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    /* access modifiers changed from: private */
    public final BluetoothAdapter c = BluetoothAdapter.getDefaultAdapter();
    /* access modifiers changed from: private */
    public final Handler d;
    private a e;
    private C0049b f;
    /* access modifiers changed from: private */
    public int g = 0;
    private boolean h;
    private boolean i = true;
    /* access modifiers changed from: private */
    public DiscoverInfo j;

    /* compiled from: BluetoothSppService */
    class a extends Thread {
        private boolean b = true;
        private boolean c = false;
        private BluetoothServerSocket d;
        private BluetoothSocket e;
        private a f;
        private BluetoothConnection g;
        private boolean h;
        private LinkProxyError i = new LinkProxyError() {
            public boolean onError(int i) {
                b.this.d.obtainMessage(2, i, -1).sendToTarget();
                return true;
            }
        };

        public a(boolean z) {
            this.h = z;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:52:0x005d, code lost:
            continue;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r6 = this;
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.bluetooth.BluetoothAdapter r0 = r0.c
                r1 = -3
                r2 = -1
                r3 = 2
                if (r0 != 0) goto L_0x0019
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            L_0x0019:
                boolean r0 = r6.h     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                if (r0 == 0) goto L_0x0030
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                android.bluetooth.BluetoothAdapter r0 = r0.c     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                java.lang.String r4 = "Bluetooth Secure"
                java.util.UUID r5 = com.autonavi.link.connect.bluetooth.b.a     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                android.bluetooth.BluetoothServerSocket r0 = r0.listenUsingRfcommWithServiceRecord(r4, r5)     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                r6.d = r0     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                goto L_0x0042
            L_0x0030:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                android.bluetooth.BluetoothAdapter r0 = r0.c     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                java.lang.String r4 = "Bluetooth Secure"
                java.util.UUID r5 = com.autonavi.link.connect.bluetooth.b.b     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                android.bluetooth.BluetoothServerSocket r0 = r0.listenUsingRfcommWithServiceRecord(r4, r5)     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                r6.d = r0     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
            L_0x0042:
                com.autonavi.link.connect.bluetooth.a r0 = new com.autonavi.link.connect.bluetooth.a     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                com.autonavi.link.connect.bluetooth.b r4 = com.autonavi.link.connect.bluetooth.b.this     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                android.os.Handler r4 = r4.d     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                r0.<init>(r4)     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                r6.f = r0     // Catch:{ IOException -> 0x0102, Exception -> 0x00f4 }
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                r1 = 3
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
            L_0x005d:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                int r0 = r0.g
                if (r0 == r1) goto L_0x00f3
                boolean r0 = r6.b
                if (r0 == 0) goto L_0x00f3
                android.bluetooth.BluetoothServerSocket r0 = r6.d     // Catch:{ IOException -> 0x00e4, NullPointerException -> 0x00e3 }
                android.bluetooth.BluetoothSocket r0 = r0.accept()     // Catch:{ IOException -> 0x00e4, NullPointerException -> 0x00e3 }
                r6.e = r0     // Catch:{ IOException -> 0x00e4, NullPointerException -> 0x00e3 }
                android.bluetooth.BluetoothSocket r0 = r6.e
                if (r0 == 0) goto L_0x005d
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                int r0 = r0.g
                switch(r0) {
                    case 0: goto L_0x00cc;
                    case 1: goto L_0x007f;
                    case 2: goto L_0x007f;
                    case 3: goto L_0x00cc;
                    default: goto L_0x007e;
                }
            L_0x007e:
                goto L_0x005d
            L_0x007f:
                com.autonavi.link.connect.bluetooth.a r0 = r6.f
                if (r0 == 0) goto L_0x00ac
                com.autonavi.link.connect.bluetooth.a r0 = r6.f
                android.bluetooth.BluetoothSocket r4 = r6.e
                boolean r0 = r0.b(r4)
                r6.c = r0
                boolean r0 = r6.c
                if (r0 == 0) goto L_0x009d
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                com.autonavi.link.connect.bluetooth.a r4 = r6.f
                com.autonavi.link.connect.model.DiscoverInfo r4 = r4.a()
                r0.j = r4
                goto L_0x00ac
            L_0x009d:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                r1 = -2
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            L_0x00ac:
                com.autonavi.link.transmit.impl.BluetoothConnection r0 = new com.autonavi.link.transmit.impl.BluetoothConnection
                android.bluetooth.BluetoothSocket r4 = r6.e
                r0.<init>(r4)
                r6.g = r0
                com.autonavi.link.transmit.proxy.LinkProxy r0 = com.autonavi.link.transmit.proxy.LinkProxy.getInstance()
                com.autonavi.link.transmit.impl.BluetoothConnection r4 = r6.g
                com.autonavi.link.transmit.inter.LinkProxyError r5 = r6.i
                r0.addConnection(r4, r5)
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.bluetooth.BluetoothSocket r4 = r6.e
                android.bluetooth.BluetoothDevice r4 = r4.getRemoteDevice()
                r0.b(r4)
                goto L_0x005d
            L_0x00cc:
                android.bluetooth.BluetoothSocket r0 = r6.e     // Catch:{ IOException -> 0x00d3, NullPointerException -> 0x00d2 }
                r0.close()     // Catch:{ IOException -> 0x00d3, NullPointerException -> 0x00d2 }
                goto L_0x005d
            L_0x00d2:
                return
            L_0x00d3:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                r4 = -6
                android.os.Message r0 = r0.obtainMessage(r3, r4, r2)
                r0.sendToTarget()
                goto L_0x005d
            L_0x00e3:
                return
            L_0x00e4:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                r1 = -4
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            L_0x00f3:
                return
            L_0x00f4:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            L_0x0102:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.connect.bluetooth.b.a.run():void");
        }

        public void a() {
            if (this.f != null) {
                this.f.b();
                this.f = null;
            }
            this.b = false;
            try {
                if (this.d != null) {
                    this.d.close();
                    this.d = null;
                }
                if (this.e != null) {
                    this.e.close();
                }
            } catch (IOException unused) {
                b.this.d.obtainMessage(2, -6, -1).sendToTarget();
            }
            if (this.g != null) {
                LinkProxy.getInstance().removeConnection(this.g);
                this.g = null;
            }
        }
    }

    /* renamed from: com.autonavi.link.connect.bluetooth.b$b reason: collision with other inner class name */
    /* compiled from: BluetoothSppService */
    class C0049b extends Thread {
        private BluetoothSocket b;
        private final BluetoothDevice c;
        private a d;
        private boolean e = false;
        private BluetoothConnection f;
        private LinkProxyError g = new LinkProxyError() {
            public boolean onError(int i) {
                b.this.d.obtainMessage(2, i, -1).sendToTarget();
                return true;
            }
        };

        public C0049b(BluetoothDevice bluetoothDevice, boolean z) {
            this.c = bluetoothDevice;
            if (z) {
                try {
                    this.b = bluetoothDevice.createRfcommSocketToServiceRecord(b.a);
                } catch (IOException unused) {
                    b.this.d.obtainMessage(2, -2, -1).sendToTarget();
                }
            } else {
                this.b = bluetoothDevice.createRfcommSocketToServiceRecord(b.b);
            }
            this.d = new a(b.this.d);
        }

        /* JADX WARNING: Failed to process nested try/catch */
        /* JADX WARNING: Missing exception handler attribute for start block: B:6:0x001e */
        /* JADX WARNING: Removed duplicated region for block: B:12:0x002b  */
        /* JADX WARNING: Removed duplicated region for block: B:9:0x0022 A[Catch:{ IOException -> 0x0070, NullPointerException -> 0x006f }] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
                r5 = this;
                android.bluetooth.BluetoothSocket r0 = r5.b
                r1 = -2
                r2 = -1
                r3 = 2
                if (r0 != 0) goto L_0x0015
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            L_0x0015:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this     // Catch:{ Exception -> 0x001e }
                android.bluetooth.BluetoothAdapter r0 = r0.c     // Catch:{ Exception -> 0x001e }
                r0.cancelDiscovery()     // Catch:{ Exception -> 0x001e }
            L_0x001e:
                android.bluetooth.BluetoothSocket r0 = r5.b     // Catch:{ IOException -> 0x0070, NullPointerException -> 0x006f }
                if (r0 == 0) goto L_0x0027
                android.bluetooth.BluetoothSocket r0 = r5.b     // Catch:{ IOException -> 0x0070, NullPointerException -> 0x006f }
                r0.connect()     // Catch:{ IOException -> 0x0070, NullPointerException -> 0x006f }
            L_0x0027:
                com.autonavi.link.connect.bluetooth.a r0 = r5.d
                if (r0 == 0) goto L_0x0053
                com.autonavi.link.connect.bluetooth.a r0 = r5.d
                android.bluetooth.BluetoothSocket r4 = r5.b
                boolean r0 = r0.a(r4)
                r5.e = r0
                boolean r0 = r5.e
                if (r0 == 0) goto L_0x0045
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                com.autonavi.link.connect.bluetooth.a r1 = r5.d
                com.autonavi.link.connect.model.DiscoverInfo r1 = r1.a()
                r0.j = r1
                goto L_0x0053
            L_0x0045:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            L_0x0053:
                com.autonavi.link.transmit.impl.BluetoothConnection r0 = new com.autonavi.link.transmit.impl.BluetoothConnection
                android.bluetooth.BluetoothSocket r1 = r5.b
                r0.<init>(r1)
                r5.f = r0
                com.autonavi.link.transmit.proxy.LinkProxy r0 = com.autonavi.link.transmit.proxy.LinkProxy.getInstance()
                com.autonavi.link.transmit.impl.BluetoothConnection r1 = r5.f
                com.autonavi.link.transmit.inter.LinkProxyError r2 = r5.g
                r0.addConnection(r1, r2)
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.bluetooth.BluetoothDevice r1 = r5.c
                r0.b(r1)
                return
            L_0x006f:
                return
            L_0x0070:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                android.bluetooth.BluetoothSocket r0 = r5.b     // Catch:{ IOException -> 0x0087 }
                if (r0 == 0) goto L_0x0086
                android.bluetooth.BluetoothSocket r0 = r5.b     // Catch:{ IOException -> 0x0087 }
                r0.close()     // Catch:{ IOException -> 0x0087 }
            L_0x0086:
                return
            L_0x0087:
                com.autonavi.link.connect.bluetooth.b r0 = com.autonavi.link.connect.bluetooth.b.this
                android.os.Handler r0 = r0.d
                r1 = -6
                android.os.Message r0 = r0.obtainMessage(r3, r1, r2)
                r0.sendToTarget()
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.autonavi.link.connect.bluetooth.b.C0049b.run():void");
        }

        public void a() {
            if (this.d != null) {
                this.d.b();
                this.d = null;
            }
            try {
                if (this.b != null) {
                    this.b.close();
                }
            } catch (IOException unused) {
                b.this.d.obtainMessage(2, -6, -1).sendToTarget();
            }
            if (this.f != null) {
                LinkProxy.getInstance().removeConnection(this.f);
                this.f = null;
            }
        }
    }

    public b(Handler handler) {
        this.d = handler;
    }

    public int a() {
        return this.g;
    }

    private void a(int i2) {
        this.g = i2;
        a(i2 == 3);
    }

    public boolean b() {
        return this.h;
    }

    public void a(boolean z) {
        this.h = z;
    }

    public void b(boolean z) {
        a(1);
        if (this.e == null) {
            this.e = new a(z);
            this.e.start();
            this.i = z;
        }
    }

    public void a(BluetoothDevice bluetoothDevice) {
        if (this.g == 2 && this.f != null) {
            this.f.a();
            this.f = null;
        }
        this.f = new C0049b(bluetoothDevice, this.i);
        this.f.start();
        a(2);
    }

    public void b(BluetoothDevice bluetoothDevice) {
        Message obtainMessage = this.d.obtainMessage(1);
        Bundle bundle = new Bundle();
        bundle.putString("device_name", bluetoothDevice.getName());
        bundle.putString("device_address", bluetoothDevice.getAddress());
        bundle.putSerializable("device_info", this.j);
        obtainMessage.setData(bundle);
        this.d.sendMessage(obtainMessage);
        a(3);
    }

    public synchronized void c() {
        if (this.f != null) {
            this.f.a();
            this.f = null;
        }
        if (this.e != null) {
            this.e.a();
            this.e = null;
        }
        a(0);
    }
}
