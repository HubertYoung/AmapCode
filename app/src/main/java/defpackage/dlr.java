package defpackage;

import android.content.Intent;
import com.autonavi.common.Callback;
import com.autonavi.minimap.ajx3.scheme.Ajx3RouterManager;

/* renamed from: dlr reason: default package */
/* compiled from: AjxIntentFinalInterceptor */
public final class dlr implements dlh {
    Callback<Boolean> a;

    public final boolean a(Intent intent) {
        if (intent != null && Ajx3RouterManager.getInstance().isAmapUriIntent(intent.getData())) {
            return Ajx3RouterManager.getInstance().handleFinal(intent, this.a);
        }
        return false;
    }
}
