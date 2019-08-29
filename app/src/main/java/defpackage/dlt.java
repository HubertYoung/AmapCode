package defpackage;

import android.content.Intent;
import android.net.Uri;

@Deprecated
/* renamed from: dlt reason: default package */
/* compiled from: AlipayZhimaIntentInterceptor */
public final class dlt implements dlh {
    private cpn a = ((cpn) ank.a(cpn.class));

    public final boolean a(Intent intent) {
        boolean z;
        if (intent != null) {
            Uri data = intent.getData();
            if (data != null && data.isHierarchical()) {
                String scheme = data.getScheme();
                if ("scme2017122201358023c49cf2".equals(scheme) || "scme20171218009637581187f3".equals(scheme)) {
                    z = true;
                    if (!z && this.a != null) {
                        return this.a.a(intent);
                    }
                    return false;
                }
            }
        }
        z = false;
        if (!z) {
            return false;
        }
        return this.a.a(intent);
    }
}
