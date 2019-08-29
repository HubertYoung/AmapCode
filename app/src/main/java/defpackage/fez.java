package defpackage;

/* renamed from: fez reason: default package */
/* compiled from: MtopCacheListenerProxy */
public final class fez extends fey implements a {
    public fez(few few) {
        super(few);
    }

    public final void onCached(fer fer, Object obj) {
        if (this.a instanceof a) {
            ((a) this.a).onCached(fer, obj);
            this.d = true;
        }
    }
}
