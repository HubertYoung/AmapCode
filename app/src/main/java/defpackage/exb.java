package defpackage;

/* renamed from: exb reason: default package */
/* compiled from: StopServiceCommand */
public final class exb extends fbh {
    private String a;

    public final String toString() {
        return "StopServiceCommand";
    }

    public exb(String str) {
        super(2008);
        this.a = str;
    }

    public exb() {
        super(2008);
    }

    public final void a(ewx ewx) {
        ewx.a((String) "package_name", this.a);
    }

    public final void b(ewx ewx) {
        this.a = ewx.a((String) "package_name");
    }
}
