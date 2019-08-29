package defpackage;

import android.content.Intent;
import com.autonavi.minimap.ajx3.scheme.Ajx3RouterManager;

/* renamed from: dls reason: default package */
/* compiled from: AjxIntentPrepareInterceptor */
public final class dls implements dlh {
    public final boolean a(Intent intent) {
        if (intent != null && Ajx3RouterManager.getInstance().isAmapUriIntent(intent.getData())) {
            return Ajx3RouterManager.getInstance().handlePrepare(intent);
        }
        return false;
    }
}
