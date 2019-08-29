package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.alipay.android.nebulaapp.MiniAppInitHelper;

/* renamed from: cle reason: default package */
/* compiled from: MiniAppInit */
public final class cle extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "MiniAppInit";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        MiniAppInitHelper.getInstance().setupMiniAppEnvironment();
    }
}
