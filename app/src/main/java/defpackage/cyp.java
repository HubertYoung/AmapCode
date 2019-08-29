package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.bundle.locationselect.overlay.SelectPoiPointOverlay;

@BundleInterface(eq.class)
/* renamed from: cyp reason: default package */
/* compiled from: LocationSelectService */
public class cyp extends esi implements eq {
    public final PointOverlay a(bty bty) {
        return new SelectPoiPointOverlay(bty);
    }
}
