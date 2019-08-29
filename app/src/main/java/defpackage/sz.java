package defpackage;

import com.amap.bundle.drivecommon.overlay.DriveBasePointOverlay;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

/* renamed from: sz reason: default package */
/* compiled from: DriveBasePointItem */
public abstract class sz<E extends DriveBasePointOverlay> extends PointOverlayItem<E> {
    public boolean f = true;

    public sz(GeoPoint geoPoint) {
        super(geoPoint);
    }
}
