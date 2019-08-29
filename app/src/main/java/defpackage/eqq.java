package defpackage;

import android.os.Build.VERSION;
import android.view.View;

/* renamed from: eqq reason: default package */
/* compiled from: Compat */
public final class eqq {
    public static void a(View view, Runnable runnable) {
        if (VERSION.SDK_INT >= 16) {
            view.postOnAnimation(runnable);
        } else {
            view.postDelayed(runnable, 16);
        }
    }
}
