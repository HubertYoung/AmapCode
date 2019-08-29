package defpackage;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;

/* renamed from: fbv reason: default package */
/* compiled from: CommandSession */
public final class fbv implements Runnable {
    private static final UUID l = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    fby a;
    private Handler b;
    private Socket c;
    private String d;
    private int e;
    private boolean f = false;
    private fci g;
    private fch h;
    private boolean i = true;
    private BluetoothDevice j;
    private BluetoothSocket k;

    fbv(String str, Handler handler, fby fby) {
        "create command session to ".concat(String.valueOf(str));
        this.a = fby;
        this.d = str;
        this.e = 50001;
        this.b = handler;
    }

    private void b() {
        this.g = new fci(this.h);
        new Thread(this.g).start();
    }

    private boolean c() {
        new StringBuilder("connectToBluetoothServer ").append(this.j.getName());
        try {
            this.k = this.j.createInsecureRfcommSocketToServiceRecord(l);
            if (this.k != null) {
                this.k.connect();
                this.h = new fch(this.k.getOutputStream(), this.k.getInputStream());
                b();
                if (this.i) {
                    this.b.obtainMessage(4098).sendToTarget();
                }
                return true;
            }
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private boolean d() {
        this.c = new Socket();
        try {
            this.c.setReceiveBufferSize(262144);
            this.c.setSoTimeout(0);
            InetAddress byName = InetAddress.getByName(this.d);
            this.c.bind(null);
            new StringBuilder("connectting to socket of data service ").append(this.d);
            this.c.connect(new InetSocketAddress(byName.getHostAddress(), this.e), 5000);
            new StringBuilder("connectted to socket of data service ").append(this.d);
            this.h = new fch(this.c);
            b();
            if (this.i) {
                this.b.obtainMessage(4098).sendToTarget();
            }
            return true;
        } catch (UnknownHostException e2) {
            e2.printStackTrace();
            return false;
        } catch (IOException e3) {
            e3.printStackTrace();
            return false;
        }
    }

    public final void a() {
        this.i = false;
        this.f = true;
        e();
    }

    public final void a(List<Long> list) {
        a((fcu) new fcj(list));
    }

    public final void a(boolean z, String str) {
        a((fcu) new fbq(z, str));
    }

    public final void a(int i2, boolean z, int i3) {
        a((fcu) new fbt(i2, z, i3));
    }

    public final void a(fcu fcu) {
        if (this.g != null) {
            this.g.a(fcu);
        }
    }

    private void e() {
        if (this.c != null && !this.c.isClosed()) {
            try {
                this.c.close();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void f() {
        if (this.g != null) {
            this.g.a();
        }
        if (this.b != null) {
            this.b = null;
        }
    }

    public final void run() {
        boolean z;
        int i2 = 5;
        while (true) {
            if (this.j != null) {
                z = c();
            } else {
                z = d();
            }
            if (z) {
                while (true) {
                    try {
                    } catch (IOException e2) {
                        e2.printStackTrace();
                        try {
                            if (this.c != null && !this.c.isClosed()) {
                                this.c.close();
                            }
                            if (this.i) {
                                this.b.obtainMessage(4099, -1, -1, this).sendToTarget();
                            }
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                        f();
                        return;
                    }
                }
            } else if (this.f) {
                f();
                return;
            } else {
                int i3 = i2 - 1;
                if (i2 > 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e4) {
                        e4.printStackTrace();
                    }
                    i2 = i3;
                } else {
                    if (this.i) {
                        this.b.obtainMessage(4115, -1, -1, this).sendToTarget();
                    }
                    f();
                    return;
                }
            }
        }
        while (true) {
            fch fch = this.h;
            byte[] bArr = new byte[10];
            int i4 = 0;
            int i5 = 0;
            while (i5 < 10) {
                int i6 = 10 - i5;
                int read = fch.b.read(bArr, i5, i6);
                if (read == -1) {
                    StringBuilder sb = new StringBuilder("read stream error ");
                    sb.append(read);
                    sb.append("readCount=");
                    sb.append(i5);
                    sb.append("toRead=");
                    sb.append(i6);
                    throw new IOException();
                }
                i5 += read;
            }
            "readCount=".concat(String.valueOf(i5));
            fch.a(bArr);
            int b2 = fch.b(bArr);
            "bodylen ".concat(String.valueOf(b2));
            if (b2 <= 0) {
                throw new IOException();
            }
            byte[] bArr2 = new byte[b2];
            while (i4 < b2) {
                int read2 = fch.b.read(bArr2, i4, b2 - i4);
                if (read2 == -1) {
                    throw new IOException();
                }
                i4 += read2;
                "read count=".concat(String.valueOf(read2));
                "read count total=".concat(String.valueOf(i4));
            }
            String str = new String(bArr2);
            "receive message: ".concat(String.valueOf(str));
            fcu a2 = fch.a(str);
            if (a2 != null && this.i) {
                this.b.obtainMessage(4097, -1, -1, a2).sendToTarget();
            }
        }
    }
}
