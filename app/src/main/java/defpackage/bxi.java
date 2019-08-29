package defpackage;

import android.content.Context;

/* renamed from: bxi reason: default package */
/* compiled from: SearchResultDataSource */
public final class bxi {
    public Context a;
    public bbq b;

    public bxi(Context context) {
        this.a = context;
    }

    public static boolean a(bxh bxh) {
        if (bxh.c() == null) {
            return false;
        }
        new ekv().a(bxh.c().clone(), 3, new bvv(bxh));
        return true;
    }
}
