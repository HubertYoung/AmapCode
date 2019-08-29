package defpackage;

import android.content.ClipboardManager;
import android.content.Context;

/* renamed from: ddg reason: default package */
/* compiled from: ClipboardCheckHelper */
public final class ddg {
    public ClipboardManager a;

    public ddg(Context context) {
        if (context != null) {
            this.a = (ClipboardManager) context.getSystemService("clipboard");
        }
    }

    public final boolean a() {
        boolean z;
        if (this.a == null) {
            return false;
        }
        try {
            z = this.a.hasPrimaryClip();
        } catch (Exception unused) {
            z = false;
        }
        return z;
    }

    public final CharSequence b() {
        if (this.a == null) {
            return "";
        }
        try {
            return this.a.getPrimaryClip().getItemAt(0).getText();
        } catch (Exception unused) {
            return "";
        }
    }
}
