package defpackage;

import android.text.TextUtils;

/* renamed from: eym reason: default package */
/* compiled from: OnBindAppReceiveTask */
public final class eym extends eya {
    public eym(fbh fbh) {
        super(fbh);
    }

    public final void a(fbh fbh) {
        exj exj = (exj) fbh;
        String str = exj.a;
        ezv.a().a(exj.d, exj.e, str);
        if (TextUtils.isEmpty(exj.d) && !TextUtils.isEmpty(str)) {
            ezv.a().a(str);
        }
        fbf.b(new eyn(this, str, exj));
    }
}
