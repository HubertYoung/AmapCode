package defpackage;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.text.TextUtils;

/* renamed from: eza reason: default package */
/* compiled from: OnNotificationArrivedReceiveTask */
final class eza implements Runnable {
    final /* synthetic */ ezp a;
    final /* synthetic */ exq b;
    final /* synthetic */ eyz c;

    eza(eyz eyz, ezp ezp, exq exq) {
        this.c = eyz;
        this.a = ezp;
        this.b = exq;
    }

    public final void run() {
        char c2;
        fau.a(this.a);
        fap fap = new fap(this.c.b, this.a, this.b.c, this.c.a.a(this.c.b));
        boolean z = this.a.c;
        String str = this.a.k;
        if (TextUtils.isEmpty(str)) {
            str = this.a.m;
        }
        if (!TextUtils.isEmpty(str)) {
            fat.c((String) "OnNotificationArrivedTask", "showCode=".concat(String.valueOf(z)));
            if (!z) {
                fat.a(this.c.b, (String) "mobile net unshow");
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.c.b.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.getState() == State.CONNECTED) {
                    int type = activeNetworkInfo.getType();
                    c2 = type == 1 ? 2 : type == 0 ? (char) 1 : 3;
                } else {
                    c2 = 0;
                }
                if (c2 == 1) {
                    str = null;
                    this.a.l = "";
                    this.a.k = "";
                }
            } else {
                fat.a(this.c.b, (String) "mobile net show");
            }
        }
        fap.execute(new String[]{this.a.l, str});
    }
}
