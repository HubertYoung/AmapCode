package defpackage;

import android.content.Intent;

/* renamed from: dme reason: default package */
/* compiled from: TaxiShortcutIntentInterceptor */
public final class dme implements dlh {
    private dfk a;

    public dme() {
        if (((djk) ank.a(djk.class)) != null) {
            this.a = null;
        }
    }

    public final boolean a(Intent intent) {
        if ((intent != null && "INTENT_ACTION_TAXISHORT".equalsIgnoreCase(intent.getAction())) && this.a != null) {
            return this.a.dispatch(intent);
        }
        return false;
    }
}
