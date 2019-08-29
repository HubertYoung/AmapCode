package defpackage;

import android.content.Intent;

/* renamed from: dmb reason: default package */
/* compiled from: RouterIntentInterceptor */
public final class dmb implements dlh {
    public final boolean a(Intent intent) {
        if (intent.getData() == null) {
            return false;
        }
        return esf.a().a(new ese(intent));
    }
}
