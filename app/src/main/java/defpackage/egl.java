package defpackage;

import android.app.Activity;
import java.lang.ref.WeakReference;

/* renamed from: egl reason: default package */
/* compiled from: ShareBikePayManager */
public final class egl {
    WeakReference<Activity> a;

    /* renamed from: egl$a */
    /* compiled from: ShareBikePayManager */
    public interface a {
        void a(ehq ehq);
    }

    public egl(Activity activity) {
        this.a = new WeakReference<>(activity);
    }
}
