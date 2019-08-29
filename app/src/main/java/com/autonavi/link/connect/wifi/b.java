package com.autonavi.link.connect.wifi;

import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/* compiled from: SendMultDiscover */
public class b {
    /* access modifiers changed from: private */
    public static final String a = "b";
    /* access modifiers changed from: private */
    public MulticastSocket b;
    /* access modifiers changed from: private */
    public DatagramSocket c = null;
    /* access modifiers changed from: private */
    public DatagramPacket d;
    /* access modifiers changed from: private */
    public InetAddress e;
    private c f;
    private C0051b g;
    /* access modifiers changed from: private */
    public List<DiscoverInfo> h = null;
    /* access modifiers changed from: private */
    public a i;

    /* compiled from: SendMultDiscover */
    public interface a {
        void a(List<DiscoverInfo> list);
    }

    /* renamed from: com.autonavi.link.connect.wifi.b$b reason: collision with other inner class name */
    /* compiled from: SendMultDiscover */
    class C0051b extends Thread {
        private boolean b;

        private C0051b() {
            this.b = true;
        }

        public void interrupt() {
            this.b = false;
            super.interrupt();
        }

        public void run() {
            while (this.b) {
                try {
                    Logger.d(b.a, (String) " receive broadcast --> begin--> ", new Object[0]);
                    b.this.c.receive(b.this.d);
                    Logger.d(b.a, (String) " receive broadcast --> end--> ", new Object[0]);
                    byte[] data = b.this.d.getData();
                    Logger.d(b.a, " receive broadcast --> end--> ".concat(String.valueOf(data)), new Object[0]);
                    if (data != null && data.length > 0) {
                        String c = b.a;
                        StringBuilder sb = new StringBuilder(" receive broadcast --> end--> recData bu wei null---> ");
                        sb.append(data.length);
                        Logger.d(c, sb.toString(), new Object[0]);
                        try {
                            DiscoverInfo b2 = com.autonavi.link.connect.b.a.b(new ByteArrayInputStream(data));
                            b2.IP = b.this.d.getAddress().getHostAddress();
                            for (int i = 0; i < b.this.h.size(); i++) {
                                if (b2.IP.equals(((DiscoverInfo) b.this.h.get(i)).IP)) {
                                    b.this.h.remove(i);
                                }
                            }
                            b.this.h.add(b2);
                            if (b.this.i != null) {
                                b.this.i.a(b.this.h);
                            }
                        } catch (IOException unused) {
                        }
                    }
                } catch (IOException | NullPointerException unused2) {
                }
                try {
                    sleep(500);
                } catch (InterruptedException unused3) {
                    return;
                }
            }
        }
    }

    /* compiled from: SendMultDiscover */
    class c extends Thread {
        private boolean b;

        private c() {
            this.b = true;
        }

        public void interrupt() {
            this.b = false;
            if (!(b.this.b == null || b.this.b.isClosed() || b.this.e == null)) {
                b.this.b.close();
            }
            super.interrupt();
        }

        public void run() {
            while (this.b) {
                try {
                    b.this.e = ShareNetManager.getInstance().getIpAddress(true);
                    String c = b.a;
                    StringBuilder sb = new StringBuilder(" send broadcast --> ");
                    sb.append(b.this.e);
                    Logger.d(c, sb.toString(), new Object[0]);
                    if (b.this.e != null) {
                        String c2 = b.a;
                        StringBuilder sb2 = new StringBuilder(" send broadcast != null send --> ");
                        sb2.append(b.this.e);
                        Logger.d(c2, sb2.toString(), new Object[0]);
                        byte[] a2 = com.autonavi.link.connect.b.a.a(null, ShareNetManager.getInstance().getAppPackageName(), null);
                        b.this.b.send(new DatagramPacket(a2, a2.length, b.this.e, 9911));
                    }
                } catch (IOException | NullPointerException | SocketException | JSONException unused) {
                }
                try {
                    sleep(1000);
                } catch (InterruptedException unused2) {
                    return;
                }
            }
        }
    }

    public b() throws IOException {
        d();
    }

    private void d() throws IOException {
        this.d = new DatagramPacket(new byte[2048], 2048);
        this.b = new MulticastSocket();
        this.b.setTimeToLive(4);
        this.b.setLoopbackMode(true);
    }

    public void a() throws IOException {
        if (this.b == null || this.b.isClosed()) {
            d();
        }
        if (this.c == null) {
            this.h = new ArrayList();
            this.c = new DatagramSocket(9912);
            this.f = new c();
            this.f.start();
            this.g = new C0051b();
            this.g.start();
        }
    }

    public void b() {
        if (this.h != null && !this.h.isEmpty()) {
            this.h.clear();
        }
        if (this.f != null) {
            this.f.interrupt();
        }
        if (this.g != null) {
            this.g.interrupt();
        }
        if (this.c != null && !this.c.isClosed()) {
            this.c.close();
            this.c = null;
        }
    }

    public void a(a aVar) {
        this.i = aVar;
    }
}
