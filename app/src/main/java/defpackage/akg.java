package defpackage;

import android.view.View;
import com.alipay.sdk.util.h;
import java.lang.ref.WeakReference;

/* renamed from: akg reason: default package */
/* compiled from: PageId */
public final class akg {
    private WeakReference<akc> a;
    private WeakReference<View> b;

    public akg(akc akc) {
        this.a = new WeakReference<>(akc);
    }

    public final akc a() {
        if (this.a != null) {
            return (akc) this.a.get();
        }
        return null;
    }

    public final View b() {
        if (this.b != null) {
            return (View) this.b.get();
        }
        return null;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("PageId {c=");
        View view = null;
        sb.append(this.a != null ? (akc) this.a.get() : null);
        sb.append(" view=");
        if (this.b != null) {
            view = (View) this.b.get();
        }
        sb.append(view);
        sb.append(h.d);
        return sb.toString();
    }
}
