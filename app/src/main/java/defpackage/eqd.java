package defpackage;

import android.app.Dialog;
import java.lang.ref.WeakReference;

/* renamed from: eqd reason: default package */
/* compiled from: VUIDialogListener */
public final class eqd implements btq {
    public final void a(WeakReference<? extends Dialog> weakReference) {
        Dialog dialog = (Dialog) weakReference.get();
        if (dialog != null) {
            d.a.a(dialog.hashCode(), true);
        }
    }

    public final void b(WeakReference<? extends Dialog> weakReference) {
        Dialog dialog = (Dialog) weakReference.get();
        if (dialog != null) {
            d.a.a(dialog.hashCode(), false);
        }
    }
}
