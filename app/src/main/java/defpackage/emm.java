package defpackage;

import android.content.Context;

/* renamed from: emm reason: default package */
/* compiled from: PerformanceCore */
public final class emm {
    public eml a;
    public boolean b;
    private emn c;
    private emr d;
    private Context e;

    public emm(Context context) {
        this.e = context;
    }

    public final void a() {
        this.b = true;
        if (this.d == null) {
            this.d = new ems(this.e);
        }
        this.d.a();
        if (this.d instanceof emq) {
            this.a = new eml((emq) this.d, this.e);
        }
        this.c = new emn(this.a);
        this.c.start();
    }

    public final void b() {
        if (this.b) {
            this.b = false;
            this.c.a = false;
            this.d.b();
        }
    }
}
