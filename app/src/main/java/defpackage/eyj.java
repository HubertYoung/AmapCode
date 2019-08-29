package defpackage;

/* renamed from: eyj reason: default package */
/* compiled from: UnbindAppSendCommandTask */
public final class eyj extends fbe {
    public eyj(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        exd exd = (exd) fbh;
        ezt a = faw.a(this.b);
        if (a == null) {
            ezv.a().a(exd.c, 1005, new Object[0]);
            return;
        }
        String str = a.a;
        if (a.e) {
            ezv.a().a(exd.c, 1004, new Object[0]);
            fbh = new exf();
        } else {
            int a2 = fav.a(exd);
            if (a2 != 0) {
                ezv.a().a(exd.c, a2, new Object[0]);
                return;
            }
        }
        ewy.a(this.b, str, fbh);
    }
}
