package defpackage;

import android.content.Context;
import com.autonavi.map.core.MapManager;

/* renamed from: aqf reason: default package */
/* compiled from: MainMapDelegate */
public final class aqf {
    public Context a;
    public MapManager b;
    cde c;
    public bty d;
    public bty e;
    final arn f;

    public aqf(arn arn) {
        this.f = arn;
        this.a = arn.getContext();
        this.b = arn.getMapManager();
        this.c = arn.getSuspendManager();
        this.d = arn.getMapView();
        this.e = arn.getMapView();
    }

    public final void a(btw btw) {
        if (this.b != null && btw != null) {
            this.b.popMapEventListener(btw);
        }
    }
}
