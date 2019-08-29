package defpackage;

import android.text.TextUtils;

/* renamed from: eyn reason: default package */
/* compiled from: OnBindAppReceiveTask */
final class eyn implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ exj b;
    final /* synthetic */ eym c;

    eyn(eym eym, String str, exj exj) {
        this.c = eym;
        this.a = str;
        this.b = exj;
    }

    public final void run() {
        if (!TextUtils.isEmpty(this.a)) {
            this.c.a.a(this.c.b, this.a);
        }
    }
}
