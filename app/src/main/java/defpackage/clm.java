package defpackage;

import android.app.Application;
import android.support.annotation.NonNull;
import com.amap.bundle.mapstorage.MPSharedPreferences;

/* renamed from: clm reason: default package */
/* compiled from: SpEncpytTransfer */
public final class clm extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "SpEncpytTransfer";
    }

    public final void a(Application application) {
        MPSharedPreferences.a((String) "com.autonavi.minimap.mpsharedpreferences");
        km.a(application.getApplicationContext());
        new xz();
        xz.a();
    }
}
