package defpackage;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.bundle.blutils.PathManager;

/* renamed from: cld reason: default package */
/* compiled from: MapDataPath */
public final class cld extends cky {
    /* access modifiers changed from: 0000 */
    @NonNull
    public final String a() {
        return "MapDataPath";
    }

    /* access modifiers changed from: 0000 */
    public final void a(Application application) {
        PathManager.a().a((Context) application);
    }
}
