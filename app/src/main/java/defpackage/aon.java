package defpackage;

import android.content.Intent;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import java.lang.ref.WeakReference;

/* renamed from: aon reason: default package */
/* compiled from: QQListenerHolder */
public final class aon {
    public WeakReference<IUiListener> a;

    /* renamed from: aon$a */
    /* compiled from: QQListenerHolder */
    public static class a {
        public static final aon a = new aon(0);
    }

    /* synthetic */ aon(byte b) {
        this();
    }

    private aon() {
    }

    public final void a(IUiListener iUiListener) {
        this.a = new WeakReference<>(iUiListener);
    }

    public final void a(int i, int i2, Intent intent) {
        if (this.a != null) {
            IUiListener iUiListener = (IUiListener) this.a.get();
            if (iUiListener != null) {
                Tencent.onActivityResultData(i, i2, intent, iUiListener);
            }
        }
    }
}
