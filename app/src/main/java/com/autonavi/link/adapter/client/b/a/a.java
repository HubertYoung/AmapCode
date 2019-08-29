package com.autonavi.link.adapter.client.b.a;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.autonavi.link.adapter.a.b;
import com.autonavi.link.adapter.client.a.a.C0042a;
import com.autonavi.link.adapter.model.SocketConnectState;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/* compiled from: UsbNetManager */
public class a implements b {
    private static a a;
    /* access modifiers changed from: private */
    public com.autonavi.link.adapter.client.b.b.a b;
    /* access modifiers changed from: private */
    public String c = null;
    /* access modifiers changed from: private */
    public C0043a d;
    /* access modifiers changed from: private */
    public final com.autonavi.link.adapter.a.a e = new com.autonavi.link.adapter.a.a();
    /* access modifiers changed from: private */
    public C0042a f;
    /* access modifiers changed from: private */
    public boolean g = false;
    /* access modifiers changed from: private */
    public ArrayList<String> h;
    /* access modifiers changed from: private */
    public final Handler i = new Handler() {
        public void dispatchMessage(Message message) {
            if (message.what == 101) {
                SocketConnectState socketConnectState = (SocketConnectState) message.obj;
                if (socketConnectState != SocketConnectState.CONNECTED) {
                    try {
                        a.this.d();
                    } catch (IOException unused) {
                    }
                }
                if (a.this.f != null) {
                    a.this.f.a(-1, socketConnectState);
                }
            } else if (message.what == 102) {
                if (message.arg1 != 0) {
                    try {
                        a.this.c();
                    } catch (IOException unused2) {
                    }
                } else if (a.this.g) {
                    a.this.i.sendEmptyMessageDelayed(103, 4000);
                }
            } else {
                int i = message.what;
            }
        }
    };
    private int j = 0;

    /* renamed from: com.autonavi.link.adapter.client.b.a.a$a reason: collision with other inner class name */
    /* compiled from: UsbNetManager */
    class C0043a extends Thread {
        private boolean b;

        private C0043a() {
            this.b = true;
        }

        public void a() {
            this.b = false;
            interrupt();
        }

        public void run() {
            int i = 0;
            while (true) {
                if (!this.b) {
                    break;
                }
                a.this.c = a.this.h();
                if (a.this.h == null || a.this.h.isEmpty()) {
                    i++;
                    if (i > 3) {
                        a.this.a(SocketConnectState.DISCONNECT);
                        break;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException unused) {
                            a.this.a(SocketConnectState.DISCONNECT);
                        }
                    }
                } else {
                    a.this.g = false;
                    int i2 = 0;
                    while (i2 < a.this.h.size()) {
                        a.this.c = (String) a.this.h.get(i2);
                        if (!TextUtils.isEmpty(a.this.c)) {
                            try {
                                a.this.b.a(a.this.c, 4545);
                                a.this.g = true;
                            } catch (Exception unused2) {
                                a.this.g = false;
                            }
                            if (a.this.g) {
                                break;
                            }
                        }
                        try {
                            Thread.sleep(10);
                            i2++;
                        } catch (InterruptedException unused3) {
                        }
                    }
                    if (a.this.g) {
                        com.autonavi.link.adapter.client.b.c.a.a().a(a.this.b.a());
                        a.this.a(SocketConnectState.CONNECTED);
                        a.this.i.sendEmptyMessageDelayed(103, 4000);
                        try {
                            a.this.b.a(a.this.e);
                        } catch (IOException unused4) {
                        }
                        a.this.a(SocketConnectState.DISCONNECT);
                    } else {
                        a.this.a(SocketConnectState.DISCONNECT);
                    }
                }
            }
            a.this.d = null;
        }
    }

    public void a(int i2) {
    }

    public void a(int i2, boolean z) {
    }

    public void a(String str, String str2, boolean z) {
    }

    public void a(boolean z) {
    }

    public void a(boolean z, int i2, boolean z2) {
    }

    public void b(boolean z) {
    }

    public static a a() {
        if (a == null) {
            a = new a();
        }
        return a;
    }

    private a() {
        this.e.a(this);
        this.b = new com.autonavi.link.adapter.client.b.b.a();
    }

    public void b() {
        if (this.d == null) {
            this.d = new C0043a();
            this.d.start();
        }
    }

    public void c() throws IOException {
        this.g = false;
        if (this.d != null) {
            this.d.a();
        }
        d();
    }

    public void d() throws IOException {
        try {
            if (this.b != null) {
                this.b.b();
            }
            com.autonavi.link.adapter.client.b.c.a.a().d();
        } catch (IOException e2) {
            throw e2;
        }
    }

    public void a(C0042a aVar) {
        this.f = aVar;
    }

    private ArrayList<String> g() throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/proc/net/arp"));
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            String[] split = readLine.split(" +");
            if (split != null && split.length >= 4) {
                String str = split[0];
                if (!str.equals("IP")) {
                    arrayList.add(str);
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public String h() {
        String str;
        try {
            this.h = g();
            if (this.h != null) {
                if (!this.h.isEmpty()) {
                    if (this.j > this.h.size() - 1) {
                        this.j = 0;
                    }
                    str = this.h.get(this.j);
                    try {
                        if (this.j < this.h.size() - 1) {
                            this.j++;
                        } else {
                            this.j = 0;
                        }
                    } catch (IOException unused) {
                    }
                    return str;
                }
            }
            return null;
        } catch (IOException unused2) {
            str = null;
        }
    }

    public boolean e() {
        return this.g;
    }

    /* access modifiers changed from: private */
    public synchronized void a(SocketConnectState socketConnectState) {
        Message message = new Message();
        message.obj = socketConnectState;
        message.what = 101;
        this.i.sendMessage(message);
    }

    public void a(int i2, int i3) {
        if (this.f != null) {
            this.f.a(i2, i3);
        }
    }

    public void f() {
        if (this.f != null) {
            this.f.a();
        }
    }

    public void a(byte[] bArr) {
        if (this.f != null) {
            this.f.b(bArr);
        }
    }

    public void b(byte[] bArr) {
        if (this.f != null) {
            this.f.a(bArr);
        }
    }
}
