package com.autonavi.minimap.route.bus.busline.overlay;

import android.graphics.PointF;
import android.graphics.Rect;
import com.autonavi.jni.ae.gmap.GLMapState;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class BusLinePointOverlay extends PointOverlay implements amg {
    private boolean isInitFinish = false;
    private RouteBusStationNameOverlay mStationNameOverlay;
    private bty mapView;

    public BusLinePointOverlay(bty bty) {
        super(bty);
    }

    public void addCheckCoverOverlay(RouteBusStationNameOverlay routeBusStationNameOverlay, bty bty) {
        this.mStationNameOverlay = routeBusStationNameOverlay;
        this.mapView = bty;
    }

    public void setDataInit(boolean z) {
        this.isInitFinish = z;
    }

    public void reculateOverlay(akq akq) {
        GLMapState a = ((bty) ((Map) akq.K).get(Integer.valueOf(brx.d))).a(brx.d);
        if (!(!this.isInitFinish || a == null || this.mStationNameOverlay == null)) {
            RouteBusStationNameOverlay routeBusStationNameOverlay = this.mStationNameOverlay;
            bty bty = this.mapView;
            Vector vector = (Vector) getItems();
            Vector vector2 = (Vector) routeBusStationNameOverlay.getItems();
            if (vector != null && !vector.isEmpty() && vector2 != null && !vector2.isEmpty()) {
                Vector vector3 = (Vector) vector.clone();
                Vector vector4 = (Vector) vector2.clone();
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                for (int i = 0; i < vector3.size(); i++) {
                    PointOverlayItem pointOverlayItem = (PointOverlayItem) vector3.get(i);
                    pointOverlayItem.setIconVisible(true);
                    if (pointOverlayItem.mBgMarker != null) {
                        amc b = bty.F().b(pointOverlayItem.mBgMarker.mID);
                        PointF pointF = new PointF();
                        a.p20ToScreenPoint(pointOverlayItem.getGeoPoint().x, pointOverlayItem.getGeoPoint().y, pointF);
                        Rect a2 = dwh.a(new Rect(), (int) pointF.x, (int) pointF.y, b, b.h);
                        a aVar = new a(0);
                        aVar.b = pointOverlayItem;
                        aVar.a = a2;
                        arrayList.add(aVar);
                    }
                }
                for (int i2 = 0; i2 < vector4.size(); i2++) {
                    PointOverlayItem pointOverlayItem2 = (PointOverlayItem) vector4.get(i2);
                    pointOverlayItem2.setIconVisible(true);
                    routeBusStationNameOverlay.setPointItemVisble(pointOverlayItem2, true, true);
                    if (pointOverlayItem2.mBgMarker != null) {
                        amc b2 = bty.F().b(pointOverlayItem2.mBgMarker.mID);
                        PointF pointF2 = new PointF();
                        a.p20ToScreenPoint(pointOverlayItem2.getGeoPoint().x, pointOverlayItem2.getGeoPoint().y, pointF2);
                        Rect a3 = dwh.a(new Rect(), (int) pointF2.x, (int) pointF2.y, b2, b2.h);
                        a aVar2 = new a(0);
                        aVar2.b = pointOverlayItem2;
                        aVar2.a = a3;
                        arrayList2.add(aVar2);
                    }
                }
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    a aVar3 = (a) arrayList.get(i3);
                    for (int i4 = 0; i4 < arrayList2.size(); i4++) {
                        a aVar4 = (a) arrayList2.get(i4);
                        if (aVar4.b.isBgVisible()) {
                            if (Rect.intersects(aVar3.a, aVar4.a)) {
                                routeBusStationNameOverlay.setPointItemVisble(aVar4.b, false, false);
                            } else {
                                routeBusStationNameOverlay.setPointItemVisble(aVar4.b, true, true);
                            }
                        }
                    }
                }
            }
        }
    }
}
