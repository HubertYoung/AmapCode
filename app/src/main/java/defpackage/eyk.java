package defpackage;

/* renamed from: eyk reason: default package */
/* compiled from: ChangeNetPermissionTask */
public final class eyk extends fbe {
    public eyk(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        boolean z;
        ezt a = faw.a(this.b);
        if (((exe) fbh).a) {
            try {
                z = eyo.a(this.b);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        } else {
            z = eyo.b(this.b);
        }
        if (z) {
            ezt a2 = faw.a(this.b);
            if (a == null || a2 == null || a2.a == null || !a2.a.equals(a.a)) {
                if (!(a == null || a.a == null)) {
                    ewy.a(this.b, a.a, (fbh) new exb(a.a));
                }
                if (!(a2 == null || a2.a == null)) {
                    ewy.a(this.b, a2.a, (fbh) new exg());
                }
            }
        }
    }
}
