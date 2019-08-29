package com.amap.bundle.drive.util;

import android.graphics.Point;
import android.location.Location;
import com.autonavi.annotation.MainMapFeature;
import com.autonavi.common.impl.Locator.Status;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.sdk.location.LocationInstrument;

@MainMapFeature
public class Ae8EggManager implements czy {
    /* access modifiers changed from: private */
    public AE8PositionEgg a;
    /* access modifiers changed from: private */
    public boolean b = false;
    private bid c;
    private ang d = new ang<Status>() {
        public final /* synthetic */ void onOriginalLocationChange(Object obj) {
            Status status = (Status) obj;
            if (Ae8EggManager.this.b && status == Status.ON_LOCATION_OK) {
                Location originalLocation = LocationInstrument.getInstance().getOriginalLocation();
                if (originalLocation != null) {
                    Point a2 = cfg.a(originalLocation.getLatitude(), originalLocation.getLongitude());
                    GeoPoint geoPoint = new GeoPoint(a2.x, a2.y);
                    if (Ae8EggManager.this.a != null) {
                        if (Ae8EggManager.this.a.getSize() >= 5) {
                            Ae8EggManager.this.a.removeItem(0);
                        }
                        Ae8EggManager.this.a.addItem(new PointOverlayItem(geoPoint));
                    }
                }
            }
        }
    };

    static class AE8PositionEgg extends PointOverlay {
        public AE8PositionEgg(bty bty) {
            super(bty);
            resumeMarker();
        }

        public void resumeMarker() {
            super.resumeMarker();
            this.mOverlayDefaultMarker = createMarker(R.drawable.navi_direction_position, 4);
        }
    }

    public Ae8EggManager() {
        bty bty;
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            this.c = iMainMapService.e();
            this.b = re.a((String) "show_my_gps_in_navigation", false);
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager == null) {
                bty = null;
            } else {
                bty = mapManager.getMapView();
            }
            if (bty != null) {
                this.a = new AE8PositionEgg(bty);
                if (((czl) iMainMapService.a(czl.class)) != null) {
                    this.a.setMoveToFocus(false);
                    this.a.setMoveToFocus(false);
                }
            }
        }
    }

    public void onResume() {
        this.b = re.a((String) "show_my_gps_in_navigation", false);
        LocationInstrument.getInstance().addOriginalLocation(this.d);
    }

    public void onPause() {
        LocationInstrument.getInstance().removeOriginalLocation(this.d);
    }
}
