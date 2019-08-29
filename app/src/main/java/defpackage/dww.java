package defpackage;

import com.autonavi.bundle.maphome.api.reverse.ReverseGeocodeManager;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.route.bus.navidetail.model.ReverseGeoCodeParser$1;

/* renamed from: dww reason: default package */
/* compiled from: ReverseGeoCodeParser */
public final class dww {
    public com.autonavi.common.Callback.a a;

    /* renamed from: dww$a */
    /* compiled from: ReverseGeoCodeParser */
    public interface a {
        void a(int i, String str);
    }

    public final void a(GeoPoint geoPoint, a aVar) {
        if (geoPoint != null) {
            if (this.a != null) {
                this.a.cancel();
            }
            this.a = ReverseGeocodeManager.getReverseGeocodeResult(geoPoint, new ReverseGeoCodeParser$1(this, aVar));
        }
    }
}
