package defpackage;

import java.io.File;

/* renamed from: eum reason: default package */
/* compiled from: SpeexPlayer */
public final class eum {
    public euo a = null;
    private String b = null;

    /* renamed from: eum$a */
    /* compiled from: SpeexPlayer */
    public interface a {
        void onFinish();

        void onStart();
    }

    /* renamed from: eum$b */
    /* compiled from: SpeexPlayer */
    class b extends Thread {
        b() {
        }

        public final void run() {
            try {
                if (eum.this.a != null) {
                    eum.this.a.b();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public eum(String str) {
        this.b = str;
        try {
            this.a = new euo(new File(this.b));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void a() {
        new Thread(new b()).start();
    }

    public final void b() {
        if (this.a != null) {
            this.a.b = null;
            this.a.a();
            this.a = null;
        }
    }

    public final void a(a aVar) {
        if (this.a != null) {
            this.a.b = aVar;
        }
    }
}
