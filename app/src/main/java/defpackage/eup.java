package defpackage;

import android.os.Process;
import com.gauss.speex.encode.Speex;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* renamed from: eup reason: default package */
/* compiled from: SpeexEncoder */
public final class eup implements Runnable {
    public final Object a = new Object();
    public List<a> b = null;
    private volatile boolean c;
    private String d;
    private int e = 8000;
    private Speex f = new Speex();
    private byte[] g = new byte[1024];

    /* renamed from: eup$a */
    /* compiled from: SpeexEncoder */
    public class a {
        public int a;
        public short[] b = new short[1024];

        public a() {
        }
    }

    public eup(String str, int i) {
        this.f.a();
        this.b = Collections.synchronizedList(new LinkedList());
        this.d = str;
        this.e = i;
    }

    public final void run() {
        int encode;
        euu euu = new euu(this.d, this.e);
        Thread thread = new Thread(euu);
        euu.a(true);
        thread.start();
        Process.setThreadPriority(-19);
        while (a()) {
            if (this.b.size() == 0) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            } else if (this.b.size() > 0) {
                synchronized (this.a) {
                    a remove = this.b.remove(0);
                    Speex speex = this.f;
                    encode = speex.encode(remove.b, 0, this.g, remove.a, speex.a);
                }
                if (encode > 0) {
                    byte[] bArr = this.g;
                    defpackage.euu.a aVar = new defpackage.euu.a();
                    aVar.a = encode;
                    System.arraycopy(bArr, 0, aVar.b, 0, encode);
                    euu.a.add(aVar);
                    this.g = new byte[1024];
                }
            } else {
                continue;
            }
        }
        euu.a(false);
        if (this.f != null) {
            this.f.b();
        }
    }

    public final void a(boolean z) {
        synchronized (this.a) {
            this.c = z;
        }
    }

    private boolean a() {
        boolean z;
        synchronized (this.a) {
            z = this.c;
        }
        return z;
    }
}
