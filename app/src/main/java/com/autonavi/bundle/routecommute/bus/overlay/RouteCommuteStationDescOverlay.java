package com.autonavi.bundle.routecommute.bus.overlay;

import android.content.Context;
import android.view.View;
import com.autonavi.bundle.routecommute.bus.overlay.view.StationDescOverlayView;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusPath;
import com.autonavi.jni.eyrie.amap.tbt.bus.response.BusRealTimeResponse.RealTimeBusLineInfo;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import java.util.ArrayList;

public class RouteCommuteStationDescOverlay extends RouteCommuteStationBaseOverlay {
    private Context mContext;

    public RouteCommuteStationDescOverlay(bty bty) {
        super(bty);
        this.mContext = bty.d();
    }

    public void addBusStationDescOverlay(ArrayList<BusPath> arrayList, ArrayList<RealTimeBusLineInfo> arrayList2) {
        if (arrayList != null && arrayList.size() > 0) {
            clear();
            clearShowedPoints();
            setCheckCover(true);
            int i = 0;
            while (i < arrayList.size() && i < 3) {
                BusPath busPath = arrayList.get(i);
                if (busPath != null) {
                    drawOverlay(i, ayt.c(busPath), busPath, arrayList2);
                }
                i++;
            }
        }
        setFocus(this.mChooseIndex, false);
    }

    private RealTimeBusLineInfo getBusRealTimeInfo(BusPath busPath, ArrayList<RealTimeBusLineInfo> arrayList) {
        if (arrayList != null) {
            return ayt.a(ayt.d(busPath), arrayList);
        }
        return null;
    }

    private void drawOverlay(int i, GeoPoint geoPoint, BusPath busPath, ArrayList<RealTimeBusLineInfo> arrayList) {
        if (geoPoint != null) {
            addShowedPoints(geoPoint);
            StationDescOverlayView stationDescOverlayView = new StationDescOverlayView(this.mContext);
            stationDescOverlayView.initData(busPath);
            stationDescOverlayView.initRealTimeInfo(getBusRealTimeInfo(busPath, arrayList));
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.Tag = Integer.valueOf(i);
            try {
                pointOverlayItem.mDefaultMarker = createMarker(i, (View) stationDescOverlayView, 2, 0.0f, 0.0f, false);
            } catch (Exception unused) {
            }
            if (pointOverlayItem.mDefaultMarker != null) {
                addItem(pointOverlayItem);
            }
        }
    }
}
