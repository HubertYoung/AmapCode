package defpackage;

import com.tencent.tauth.IUiListener;
import java.lang.ref.WeakReference;

/* renamed from: ddd reason: default package */
/* compiled from: QQShareHandler */
public final class ddd {
    public WeakReference<IUiListener> a;

    /* renamed from: ddd$a */
    /* compiled from: QQShareHandler */
    public static class a {
        public static final ddd a = new ddd(0);
    }

    /* synthetic */ ddd(byte b) {
        this();
    }

    private ddd() {
    }

    public final void a(IUiListener iUiListener) {
        this.a = new WeakReference<>(iUiListener);
    }
}
