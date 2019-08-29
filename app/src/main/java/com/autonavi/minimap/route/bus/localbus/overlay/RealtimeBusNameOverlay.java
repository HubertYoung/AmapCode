package com.autonavi.minimap.route.bus.localbus.overlay;

import android.graphics.Rect;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.Map;
import java.util.Vector;

public class RealtimeBusNameOverlay extends PointOverlay<dwd> implements amg {
    private RealtimeStationNameOverlay mStationNamePointOverlay;
    private MapSharePreference msp;

    public synchronized void addCheckCoverOverlay(RealtimeStationNameOverlay realtimeStationNameOverlay) {
        this.mStationNamePointOverlay = realtimeStationNameOverlay;
    }

    public RealtimeBusNameOverlay(bty bty) {
        super(bty);
        initRealTimeBusSp();
    }

    public void reculateOverlay(akq akq) {
        if (isOpenedRealTimeBus()) {
            GLMapState a = ((bty) ((Map) akq.K).get(Integer.valueOf(brx.d))).a(brx.d);
            if (a != null) {
                recalueItemBounds(a);
            }
        }
    }

    private boolean isOpenedRealTimeBus() {
        return getRealTimeBusStatus() && AMapPageUtil.isHomePage();
    }

    private void initRealTimeBusSp() {
        if (this.msp == null) {
            this.msp = new MapSharePreference(SharePreferenceName.SharedPreferences);
        }
    }

    private boolean getRealTimeBusStatus() {
        return bmn.b();
    }

    private void recalueItemBounds(GLMapState gLMapState) {
        if (this.mStationNamePointOverlay != null) {
            Vector vector = (Vector) getItems();
            Vector vector2 = (Vector) this.mStationNamePointOverlay.getItems();
            if (vector != null && !vector.isEmpty() && vector2 != null && !vector2.isEmpty()) {
                Vector vector3 = (Vector) vector.clone();
                Vector vector4 = (Vector) vector2.clone();
                for (int i = 0; i < vector4.size(); i++) {
                    dwd dwd = (dwd) vector4.get(i);
                    dwd.setIconVisible(true);
                    dwd.a(gLMapState);
                }
                for (int i2 = 0; i2 < vector3.size(); i2++) {
                    ((dwd) vector3.get(i2)).a(gLMapState);
                }
                checkOverlayCovered(vector3, vector4);
            }
        }
    }

    private void checkOverlayCovered(Vector<dwd> vector, Vector<dwd> vector2) {
        for (int i = 0; i < vector.size(); i++) {
            dwd dwd = vector.get(i);
            for (int i2 = 0; i2 < vector2.size(); i2++) {
                dwd dwd2 = vector2.get(i2);
                if (dwd2.isIconVisible()) {
                    synchronized (this) {
                        if (Rect.intersects(dwd.b, dwd2.b)) {
                            this.mStationNamePointOverlay.setPointItemVisble((PointOverlayItem) dwd2, false, false);
                        } else {
                            this.mStationNamePointOverlay.setPointItemVisble((PointOverlayItem) dwd2, true, true);
                        }
                    }
                }
            }
        }
    }
}
