package defpackage;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.minimap.base.overlay.PointOverlay.OnItemClickListener;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.busline.overlay.BusLineStationPointOverlay;

/* renamed from: dul reason: default package */
/* compiled from: BusLineStationMapOverlayManage */
public final class dul {
    public BusLineStationPointOverlay a;
    public a b;

    /* renamed from: dul$a */
    /* compiled from: BusLineStationMapOverlayManage */
    public interface a {
        void a(int i);
    }

    public dul(final AbstractBaseMapPage abstractBaseMapPage) {
        if (this.a == null && abstractBaseMapPage.getMapManager() != null && abstractBaseMapPage.getMapManager().getMapView() != null) {
            this.a = new BusLineStationPointOverlay(abstractBaseMapPage.getMapManager().getMapView());
            abstractBaseMapPage.addOverlay(this.a);
            this.a.setOnItemClickListener(new OnItemClickListener() {
                public final void onItemClick(bty bty, BaseMapOverlay baseMapOverlay, Object obj) {
                    if (abstractBaseMapPage.getMapManager() != null) {
                        abstractBaseMapPage.getMapManager().getOverlayManager().clearAllFocus();
                    }
                    int itemIndex = ((BusLineStationPointOverlay) baseMapOverlay).getItemIndex((PointOverlayItem) obj);
                    if (dul.this.b != null) {
                        dul.this.b.a(itemIndex);
                    }
                }
            });
        }
    }
}
