package defpackage;

import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeResponser;
import com.autonavi.common.Callback;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.route.navi.ModuleWrapper.ReverseGeocodeWrapper$1;

/* renamed from: edc reason: default package */
/* compiled from: ReverseGeocodeWrapper */
public final class edc {
    public a a = null;
    private GeoPoint b = null;
    private com.autonavi.common.Callback.a c = null;
    private Callback<ReverseGeocodeResponser> d = new ReverseGeocodeWrapper$1(this);

    /* renamed from: edc$a */
    /* compiled from: ReverseGeocodeWrapper */
    public interface a {
        void a(String str);
    }

    public final void a(GeoPoint geoPoint, a aVar) {
        if (geoPoint != null) {
            if (this.c != null) {
                this.c.cancel();
                this.c = null;
            }
            this.b = geoPoint;
            this.a = aVar;
            this.c = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, this.d);
        }
    }
}
