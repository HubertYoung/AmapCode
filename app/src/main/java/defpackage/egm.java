package defpackage;

/* renamed from: egm reason: default package */
/* compiled from: ShareTickManager */
public class egm {
    private static egm a;
    private a b = new a();

    /* renamed from: egm$a */
    /* compiled from: ShareTickManager */
    static class a extends Thread {
        long a = -1;
        Object b = new Object();
        boolean c = true;
        b d;

        public a() {
            super("ShareRiding Tick Thread");
        }

        public final void run() {
            super.run();
            while (this.c) {
                try {
                    synchronized (this.b) {
                        if (this.d != null) {
                            this.d.a(this.a);
                            String name = getName();
                            StringBuilder sb = new StringBuilder("Thread ---> ");
                            sb.append(getId());
                            sb.append(" mListener.onTick(currentSecond) + ");
                            sb.append(this.a);
                            eao.e(name, sb.toString());
                        }
                        if (this.a >= 0) {
                            this.a++;
                        }
                        this.b.wait(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /* renamed from: egm$b */
    /* compiled from: ShareTickManager */
    public interface b {
        void a(long j);
    }

    private egm() {
        this.b.start();
    }

    public static synchronized egm a() {
        egm egm;
        synchronized (egm.class) {
            try {
                if (a == null) {
                    a = new egm();
                }
                egm = a;
            }
        }
        return egm;
    }

    public final synchronized void a(long j) {
        if (this.b != null) {
            a aVar = this.b;
            eao.f(egm.class.getName(), "updateCurrentSecond time ".concat(String.valueOf(j)));
            aVar.a = j;
            try {
                synchronized (aVar.b) {
                    aVar.b.notifyAll();
                }
            } catch (Exception unused) {
            }
        }
    }

    public final synchronized void b() {
        a aVar = this.b;
        aVar.a = -1;
        aVar.c = false;
        aVar.d = null;
        this.b = null;
        a = null;
    }

    public final synchronized void a(b bVar) {
        this.b.d = bVar;
    }
}
