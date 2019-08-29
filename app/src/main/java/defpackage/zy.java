package defpackage;

/* renamed from: zy reason: default package */
/* compiled from: StartMonitorConfig */
public final class zy {
    private static zy a;
    private boolean b = bno.a;
    private g c;

    private zy() {
    }

    public static zy a() {
        if (a == null) {
            a = new zy();
        }
        return a;
    }

    public final boolean b() {
        if (this.c == null) {
            this.c = aaf.i();
        }
        boolean a2 = this.c == null ? false : this.c.a();
        if (this.b || a2) {
            return true;
        }
        return false;
    }
}
