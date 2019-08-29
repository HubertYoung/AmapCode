package defpackage;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* renamed from: euu reason: default package */
/* compiled from: SpeexWriter */
public final class euu implements Runnable {
    public List<a> a = Collections.synchronizedList(new LinkedList());
    private volatile boolean b;
    private final Object c = new Object();
    private eut d = new eut();
    private a e;

    /* renamed from: euu$a */
    /* compiled from: SpeexWriter */
    public class a {
        public int a;
        public byte[] b = new byte[1024];

        public a() {
        }
    }

    public euu(String str, int i) {
        this.d.a(str, i);
    }

    public final void run() {
        eut eut;
        while (true) {
            if (!a() && this.a.size() <= 0) {
                break;
            } else if (this.a.size() > 0) {
                this.e = this.a.remove(0);
                eut eut2 = this.d;
                byte[] bArr = this.e.b;
                int i = this.e.a;
                try {
                    eus eus = eut2.e;
                    if (i > 0) {
                        if (eus.m > 250) {
                            eus.a(false);
                        }
                        System.arraycopy(bArr, 0, eus.h, eus.i, i);
                        eus.i += i;
                        byte[] bArr2 = eus.j;
                        int i2 = eus.k;
                        eus.k = i2 + 1;
                        bArr2[i2] = (byte) i;
                        eus.m++;
                        long j = eus.n;
                        int i3 = eus.e;
                        int i4 = eus.b == 2 ? 640 : eus.b == 1 ? 320 : 160;
                        eus.n = j + ((long) (i3 * i4));
                    }
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }
        if (this.d != null) {
            try {
                eut = this.d;
                if (eut.e != null) {
                    try {
                        eus eus2 = eut.e;
                        eus2.a(true);
                        eus2.a.close();
                        eut.e = null;
                    } catch (IOException e4) {
                        e4.printStackTrace();
                        eut.e = null;
                    }
                }
            } catch (Exception e5) {
                e5.printStackTrace();
            } catch (Throwable th) {
                eut.e = null;
                throw th;
            }
        }
    }

    public final void a(boolean z) {
        synchronized (this.c) {
            this.b = z;
            if (this.b) {
                this.c.notify();
            }
        }
    }

    private boolean a() {
        boolean z;
        synchronized (this.c) {
            z = this.b;
        }
        return z;
    }
}
