package defpackage;

/* renamed from: au reason: default package */
/* compiled from: QualityChangeFilter */
public final class au {
    boolean a = false;
    protected long b;
    private final double c = 40.0d;
    private boolean d = true;

    /* access modifiers changed from: protected */
    public final boolean a() {
        if (!this.d) {
            return false;
        }
        if (System.currentTimeMillis() - this.b <= 0) {
            return true;
        }
        this.d = false;
        return false;
    }
}
