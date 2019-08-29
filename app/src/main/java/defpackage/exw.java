package defpackage;

/* renamed from: exw reason: default package */
/* compiled from: PushModeCommand */
public final class exw extends fbh {
    public int a = 0;

    public final boolean a() {
        return true;
    }

    public final String toString() {
        return "PushModeCommand";
    }

    public exw() {
        super(2011);
    }

    public final void a(ewx ewx) {
        ewx.a((String) "com.bbk.push.ikey.MODE_TYPE", this.a);
    }

    public final void b(ewx ewx) {
        this.a = ewx.b((String) "com.bbk.push.ikey.MODE_TYPE", 0);
    }
}
