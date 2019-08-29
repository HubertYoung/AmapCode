package defpackage;

import android.content.Context;
import android.graphics.Point;
import android.location.Location;
import android.view.MotionEvent;
import com.amap.bundle.blutils.log.DebugLog;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPagePresenter;
import com.autonavi.mine.measure.page.MeasurePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.POIOverlayItem;
import java.util.Iterator;
import java.util.Vector;

/* renamed from: cgo reason: default package */
/* compiled from: MeasurePresenter */
public final class cgo extends AbstractBaseMapPagePresenter<MeasurePage> {
    public Vector<POI> a = new Vector<>();
    public Vector<LineOverlayItem> b = new Vector<>();
    public int c = 0;
    public String d = null;

    public cgo(MeasurePage measurePage) {
        super(measurePage);
    }

    public final void onStart() {
        int i;
        super.onStart();
        MeasurePage measurePage = (MeasurePage) this.mPage;
        Vector<POI> vector = ((cgo) measurePage.mPresenter).a;
        Vector<LineOverlayItem> vector2 = ((cgo) measurePage.mPresenter).b;
        if (vector != null && vector.size() > 0) {
            for (int i2 = 0; i2 < vector.size(); i2++) {
                POI poi = vector.get(i2);
                if (i2 < vector.size() - 1) {
                    i = cgm.a("measure_point");
                } else {
                    i = cgm.a("measure_point_red");
                }
                POIOverlayItem pOIOverlayItem = new POIOverlayItem(poi);
                pOIOverlayItem.mDefaultMarker = measurePage.b.createMarker(i, 4);
                measurePage.b.addItem(pOIOverlayItem);
            }
            if (vector2 != null && vector2.size() > 0) {
                Iterator<LineOverlayItem> it = vector2.iterator();
                while (it.hasNext()) {
                    measurePage.c.addItem(it.next());
                }
            }
            measurePage.a();
        }
        if (measurePage.a != null) {
            bty mapView = measurePage.a.getMapView();
            int l = bim.aa().l((String) "101");
            if (l == 0) {
                if (mapView != null) {
                    mapView.a(l, mapView.ae(), 0);
                }
            } else if (l == 1) {
                if (mapView != null) {
                    mapView.a(1, 0, mapView.o(false));
                }
            } else if (l == 2 && mapView != null) {
                mapView.a(2, 0, mapView.o(false));
            }
        }
    }

    public final void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    public final void onDestroy() {
        super.onDestroy();
    }

    public final boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        super.onMapSingleClick(motionEvent, geoPoint);
        if (geoPoint == null) {
            return true;
        }
        MeasurePage measurePage = (MeasurePage) this.mPage;
        Point point = new Point();
        measurePage.a.getMapView().a((GLGeoPoint) geoPoint, point);
        if (point.x >= 0 || point.y >= 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(geoPoint.hashCode());
            POI createPOI = POIFactory.createPOI(sb.toString(), geoPoint);
            POIOverlayItem pOIOverlayItem = new POIOverlayItem(geoPoint);
            pOIOverlayItem.mDefaultMarker = measurePage.b.createMarker(cgm.a("measure_point_red"), 4);
            try {
                Vector<POI> vector = ((cgo) measurePage.mPresenter).a;
                Vector<LineOverlayItem> vector2 = ((cgo) measurePage.mPresenter).b;
                if (vector.size() > 0) {
                    LineOverlayItem lineOverlayItem = new LineOverlayItem(1, new GeoPoint[]{vector.lastElement().getPoint(), geoPoint}, agn.a((Context) AMapAppGlobal.getApplication(), 2.0f));
                    lineOverlayItem.setFillLineColor(-268346393);
                    lineOverlayItem.setFillLineId(cgm.a("map_lr"));
                    measurePage.c.addItem(lineOverlayItem);
                    vector2.add(lineOverlayItem);
                    measurePage.b.removeItem(vector.size() - 1);
                    POIOverlayItem pOIOverlayItem2 = new POIOverlayItem(vector.lastElement());
                    pOIOverlayItem2.mDefaultMarker = measurePage.b.createMarker(cgm.a("measure_point"), 4);
                    measurePage.b.addItem(pOIOverlayItem2);
                    cgo cgo = (cgo) measurePage.mPresenter;
                    if (cgo.a.size() > 0) {
                        cgo.c += a(cgo.a.lastElement().getPoint(), createPOI.getPoint());
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append(cgo.c);
                        sb2.append("m");
                        cgo.d = sb2.toString();
                        if (cgo.c > 1000) {
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(cgo.c / 1000);
                            sb3.append(".");
                            sb3.append((cgo.c % 1000) / 100);
                            sb3.append("km");
                            cgo.d = sb3.toString();
                        }
                    }
                } else {
                    measurePage.e.setEnabled(true);
                    measurePage.d.setText(R.string.keep_click_map);
                }
                measurePage.b.addItem(pOIOverlayItem);
                vector.add(createPOI);
                measurePage.a();
            } catch (Exception e) {
                DebugLog.error(e);
            }
        } else {
            ToastHelper.showToast(measurePage.getString(R.string.click_point_invalid));
        }
        return true;
    }

    public static int a(GeoPoint geoPoint, GeoPoint geoPoint2) {
        float[] fArr = new float[1];
        Location.distanceBetween(geoPoint.getLatitude(), geoPoint.getLongitude(), geoPoint2.getLatitude(), geoPoint2.getLongitude(), fArr);
        return Math.max((int) fArr[0], 0);
    }
}
