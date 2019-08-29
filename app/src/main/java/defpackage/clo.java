package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.amap.bundle.utils.ui.ToastHelper;

/* renamed from: clo reason: default package */
/* compiled from: ToastInit */
public final class clo extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "ToastInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        ToastHelper.init(application);
    }
}
