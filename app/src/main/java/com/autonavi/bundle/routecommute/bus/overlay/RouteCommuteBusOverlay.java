package com.autonavi.bundle.routecommute.bus.overlay;

import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusInfo;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

public class RouteCommuteBusOverlay extends PointOverlay {
    int MAX_BUS_COUNT = 5;

    public RouteCommuteBusOverlay(bty bty) {
        super(bty);
    }

    public void drawRealTimeBus(ArrayList<RealTimeBusLineInfo> arrayList) {
        clear();
        if (arrayList != null && arrayList.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            int i = 0;
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                RealTimeBusLineInfo realTimeBusLineInfo = arrayList.get(i2);
                if (realTimeBusLineInfo != null) {
                    ArrayList<RealTimeBusInfo> arrayList3 = realTimeBusLineInfo.trip;
                    if (arrayList3 != null && arrayList3.size() > 0) {
                        if (arrayList3.size() > i) {
                            i = arrayList3.size();
                        }
                        arrayList2.add(arrayList3);
                    }
                }
            }
            int size = arrayList2.size();
            int i3 = 0;
            int i4 = 0;
            while (i3 < i) {
                int i5 = i4;
                for (int i6 = 0; i6 < size; i6++) {
                    ArrayList arrayList4 = (ArrayList) arrayList2.get(i6);
                    if (i3 < arrayList4.size()) {
                        RealTimeBusInfo realTimeBusInfo = (RealTimeBusInfo) arrayList4.get(i3);
                        if (realTimeBusInfo != null) {
                            drawOneBus(15 - ((i3 * size) + i6), realTimeBusInfo);
                            i5++;
                            if (i5 >= this.MAX_BUS_COUNT) {
                                return;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                i3++;
                i4 = i5;
            }
        }
    }

    private void drawOneBus(int i, RealTimeBusInfo realTimeBusInfo) {
        GeoPoint geoPoint;
        if (realTimeBusInfo != null) {
            try {
                geoPoint = new GeoPoint(Double.parseDouble(realTimeBusInfo.x), Double.parseDouble(realTimeBusInfo.y));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                geoPoint = null;
            }
            if (geoPoint != null) {
                PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
                pointOverlayItem.mDefaultMarker = createMarker(R.drawable.drive_real_time_yellow_bus, 4);
                pointOverlayItem.mAngle = realTimeBusInfo.direction;
                StringBuilder sb = new StringBuilder("deng---实时公交方向:");
                sb.append(realTimeBusInfo.direction);
                azb.a(null, sb.toString());
                pointOverlayItem.mAngleMode = 3;
                getGLOverlay().setOverlayItemPriority(i);
                addItemWithAngle(pointOverlayItem);
            }
        }
    }
}
