package defpackage;

/* renamed from: emn reason: default package */
/* compiled from: SamplerThread */
public final class emn extends Thread {
    volatile boolean a = true;
    private a b;
    private float c;

    /* renamed from: emn$a */
    /* compiled from: SamplerThread */
    public interface a {
        void a();
    }

    public emn(a aVar) {
        this.b = aVar;
        Float.compare(0.0f, 1000.0f);
        this.c = 1000.0f;
    }

    public final void run() {
        while (this.a) {
            try {
                Thread.sleep((long) this.c);
                if (this.b != null) {
                    this.b.a();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
