package com.autonavi.link.connect.wifi;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.autonavi.link.connect.model.DiscoverInfo;
import com.autonavi.link.utils.Logger;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

/* compiled from: ReceiveMultDevice */
public class a {
    /* access modifiers changed from: private */
    public static final String a = "a";
    /* access modifiers changed from: private */
    public DatagramPacket b;
    /* access modifiers changed from: private */
    public DatagramSocket c = null;
    /* access modifiers changed from: private */
    public b d;
    /* access modifiers changed from: private */
    public C0050a e;
    /* access modifiers changed from: private */
    public List<DiscoverInfo> f = null;
    /* access modifiers changed from: private */
    public final Handler g = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == 257) {
                removeMessages(257);
                a.this.f.add((DiscoverInfo) message.obj);
                if (a.this.e != null) {
                    a.this.e.a(a.this.f);
                }
            }
        }
    };

    /* renamed from: com.autonavi.link.connect.wifi.a$a reason: collision with other inner class name */
    /* compiled from: ReceiveMultDevice */
    public interface C0050a {
        void a(List<DiscoverInfo> list);
    }

    /* compiled from: ReceiveMultDevice */
    class b extends Thread {
        private boolean b;

        private b() {
            this.b = false;
        }

        public synchronized void start() {
            this.b = true;
            super.start();
        }

        public void interrupt() {
            this.b = false;
            super.interrupt();
        }

        public void run() {
            boolean z;
            Logger.d(a.a, (String) " ReceiveHostThread --> run ", new Object[0]);
            while (this.b) {
                try {
                    Logger.d(a.a, (String) " ReceiveHostThread --> run --> su she le ", new Object[0]);
                    a.this.c.receive(a.this.b);
                    Logger.d(a.a, (String) " ReceiveHostThread --> run --> su she le --> shou dao shu ju 111", new Object[0]);
                    byte[] data = a.this.b.getData();
                    Logger.d(a.a, " ReceiveHostThread --> run --> su she le --> shou dao shu ju 222 recData--> ".concat(String.valueOf(data)), new Object[0]);
                    if (data != null && data.length > 0) {
                        String c = a.a;
                        StringBuilder sb = new StringBuilder(" ReceiveHostThread --> run --> shu ju recData you zhi --> ");
                        sb.append(data.length);
                        Logger.d(c, sb.toString(), new Object[0]);
                        try {
                            DiscoverInfo b2 = com.autonavi.link.connect.b.a.b(new ByteArrayInputStream(data));
                            b2.IP = a.this.b.getAddress().getHostAddress();
                            String c2 = a.a;
                            StringBuilder sb2 = new StringBuilder(" ReceiveHostThread --> run --> huo de she bei ip --> ");
                            sb2.append(b2.IP);
                            Logger.d(c2, sb2.toString(), new Object[0]);
                            DiscoverInfo[] discoverInfoArr = (DiscoverInfo[]) a.this.f.toArray(new DiscoverInfo[0]);
                            int i = 0;
                            while (true) {
                                if (i >= discoverInfoArr.length) {
                                    z = false;
                                    break;
                                } else if (b2.toString().equals(discoverInfoArr[i].toString())) {
                                    String c3 = a.a;
                                    StringBuilder sb3 = new StringBuilder(" ReceiveHostThread --> run --> shu ju  --> ");
                                    sb3.append(b2.toString());
                                    sb3.append(" yi cun zai");
                                    Logger.d(c3, sb3.toString(), new Object[0]);
                                    sleep(300);
                                    z = true;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                            Logger.d(a.a, " ReceiveHostThread --> run --> isRepeat --> ".concat(String.valueOf(z)), new Object[0]);
                            if (!z) {
                                byte[] a2 = com.autonavi.link.connect.b.a.a(null, ShareNetManager.getInstance().getAppPackageName(), null);
                                a.this.c.send(new DatagramPacket(a2, a2.length, a.this.b.getAddress(), 9912));
                                a.this.g.obtainMessage(257, b2).sendToTarget();
                            }
                        } catch (JSONException e) {
                            Logger.d(a.a, " ReceiveHostThread --> run --> shu ju jie xi yi chang --> ".concat(String.valueOf(e)), new Object[0]);
                        }
                    }
                } catch (IOException e2) {
                    Logger.d(a.a, " ReceiveHostThread --> IOException --> ".concat(String.valueOf(e2)), new Object[0]);
                    if (!this.b) {
                        break;
                    }
                    try {
                        sleep(800);
                    } catch (Exception unused) {
                    }
                } catch (InterruptedException e3) {
                    Logger.d(a.a, " ReceiveHostThread --> InterruptedException --> ".concat(String.valueOf(e3)), new Object[0]);
                } catch (NullPointerException e4) {
                    Logger.d(a.a, " ReceiveHostThread --> NullPointerException --> ".concat(String.valueOf(e4)), new Object[0]);
                }
            }
            a.this.d();
            a.this.d = null;
        }
    }

    public void a() {
        this.g.removeMessages(257);
        this.f.clear();
        d();
        try {
            if (this.d != null) {
                this.d.interrupt();
                this.d = null;
            }
        } catch (NullPointerException unused) {
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        try {
            if (this.c != null && !this.c.isClosed()) {
                this.c.close();
                this.c = null;
            }
        } catch (NullPointerException unused) {
        }
    }

    public void b() throws SocketException {
        if (this.c == null) {
            this.b = new DatagramPacket(new byte[2048], 2048);
            this.f = new ArrayList();
            this.c = new DatagramSocket(9911);
        }
        if (this.d == null) {
            this.d = new b();
            this.d.start();
        }
    }

    public void a(C0050a aVar) {
        this.e = aVar;
    }
}
