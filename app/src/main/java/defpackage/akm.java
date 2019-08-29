package defpackage;

import android.view.View;

/* renamed from: akm reason: default package */
/* compiled from: ParamsForStart */
public final class akm extends akk {
    final akd e;
    final Class<? extends akc> f;
    final int g;
    final int h;
    public final Class<?> i;
    final akh j;
    final aki k;
    final String l;
    View m;

    public akm(akd akd, Class<? extends akc> cls, int i2, akh akh, aki aki, ake ake) {
        super(ake);
        this.e = akd;
        this.f = cls;
        this.g = i2;
        String str = null;
        this.i = akh != null ? akh.d : null;
        this.j = akh;
        this.k = aki;
        this.l = akh != null ? akh.e : str;
        int i3 = 0;
        if (akh == null || akh.c == null) {
            this.h = 0;
            return;
        }
        Integer num = (Integer) akh.c.get("PAGE_COUNT");
        this.h = num != null ? num.intValue() : i3;
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(int i2) {
        return (this.g & i2) == i2;
    }
}
