package defpackage;

import android.graphics.Rect;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.route.health.PathLineParser;
import com.autonavi.jni.route.health.PathLineSegment;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.common.view.HelPointOverlay;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.RideType;
import com.autonavi.minimap.route.ride.beans.RideTraceHistory.a;
import com.autonavi.minimap.route.ride.dest.overlay.RouteDestLineOverlay;
import com.autonavi.minimap.route.ride.overlay.RideTraceOverlay;
import java.util.ArrayList;

/* renamed from: eej reason: default package */
/* compiled from: RideFinishOverlay */
public final class eej {
    private static int a = 19;
    private static int b = 3;
    private RideTraceOverlay c = new RideTraceOverlay(this.f);
    private HelPointOverlay d = new HelPointOverlay(this.f);
    private RouteDestLineOverlay e;
    private bty f;
    private int g;
    private int h;
    private int i;
    private int j;
    private int k;
    private int l;
    private a[] m;
    private POI n;
    private POI o;
    private POI p;
    private boolean q;

    public eej(AbstractBaseMapPage abstractBaseMapPage) {
        this.f = abstractBaseMapPage.getMapManager().getMapView();
        this.d.setClickable(false);
        this.e = new RouteDestLineOverlay(this.f);
        this.f.e(0.0f);
        this.f.g(0.0f);
        abstractBaseMapPage.getSuspendManager().d().f();
        a();
        this.f.g(1728053247);
        this.f.m(true);
        abstractBaseMapPage.addOverlay(this.c);
        abstractBaseMapPage.addOverlay(this.d);
        abstractBaseMapPage.addOverlay(this.e);
    }

    public final void a(int i2, int i3, int i4, int i5) {
        this.g = i2 + 50;
        this.h = i3 + 50;
        this.i = i4 + 50;
        this.j = i5 + 50;
        this.k = this.h + this.j;
        this.l = this.g + this.i;
    }

    public final void a(a[] aVarArr, POI poi, POI poi2, POI poi3, boolean z) {
        this.m = aVarArr;
        this.n = poi;
        this.o = poi2;
        this.p = poi3;
        this.q = z;
    }

    public final void a(RideType rideType) {
        a[] aVarArr;
        if (rideType == RideType.RIDE_TYPE || rideType == RideType.SHARE_RIDE_TYPE) {
            if (this.m != null && this.m.length > 0) {
                PathLineParser pathLineParser = new PathLineParser(1);
                for (a aVar : this.m) {
                    pathLineParser.addPoint(aVar.a.getPoint(), aVar.b != 0, aVar.c);
                }
                PathLineSegment[] segments = pathLineParser.getSegments();
                for (PathLineSegment pathLineSegment : segments) {
                    LineOverlayItem lineOverlayItem = new LineOverlayItem(1, pathLineSegment.getGeoPointArray(), agn.a(AMapPageUtil.getAppContext(), 4.0f));
                    lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
                    lineOverlayItem.setIsColorGradient(true);
                    lineOverlayItem.setMatchColors(pathLineSegment.getColorArray());
                    lineOverlayItem.setBorderLineWidth(agn.a(AMapPageUtil.getAppContext(), 5.0f));
                    lineOverlayItem.setBackgrondId(R.drawable.map_lr);
                    lineOverlayItem.setBackgroundColor(-1);
                    this.c.addItem(lineOverlayItem);
                }
                PointOverlayItem pointOverlayItem = new PointOverlayItem(this.m[0].a.getPoint());
                PointOverlayItem pointOverlayItem2 = new PointOverlayItem(this.m[this.m.length - 1].a.getPoint());
                pointOverlayItem.mDefaultMarker = this.d.createMarker(R.drawable.run_point_start, 4);
                pointOverlayItem2.mDefaultMarker = this.d.createMarker(R.drawable.run_point_end, 4);
                this.d.addItem(pointOverlayItem);
                this.d.addItem(pointOverlayItem2);
            }
        } else if (rideType == RideType.DEST_TYPE && this.m.length > 0) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (a aVar2 : this.m) {
                if (aVar2.b == 0) {
                    arrayList2.add(aVar2.a);
                } else {
                    arrayList.add((ArrayList) arrayList2.clone());
                    arrayList2.clear();
                }
            }
            arrayList.add(arrayList2);
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                ArrayList arrayList3 = (ArrayList) arrayList.get(i2);
                GeoPoint[] geoPointArr = new GeoPoint[arrayList3.size()];
                for (int i3 = 0; i3 < arrayList3.size(); i3++) {
                    geoPointArr[i3] = ((POI) arrayList3.get(i3)).getPoint();
                }
                this.e.createAndAddBackgroundLineItem(geoPointArr);
                this.e.createAndAddArrowLineItem(geoPointArr);
            }
            if (!(this.n == null || this.n.getPoint() == null)) {
                GeoPoint point = this.n.getPoint();
                PointOverlayItem pointOverlayItem3 = new PointOverlayItem(point);
                pointOverlayItem3.mDefaultMarker = this.d.createMarker(R.drawable.bubble_start, 4);
                this.d.addItem(pointOverlayItem3);
                this.e.addItem(new LineOverlayItem(1, new GeoPoint[]{point}, 0));
            }
            if (!(this.o == null || this.o.getPoint() == null)) {
                GeoPoint point2 = this.o.getPoint();
                PointOverlayItem pointOverlayItem4 = new PointOverlayItem(point2);
                pointOverlayItem4.mDefaultMarker = this.d.createMarker(R.drawable.bubble_end, 4);
                this.d.addItem(pointOverlayItem4);
                this.e.addItem(new LineOverlayItem(1, new GeoPoint[]{point2}, 0));
            }
            if (!(!this.q || this.p == null || this.p.getPoint() == null)) {
                PointOverlayItem pointOverlayItem5 = new PointOverlayItem(this.p.getPoint());
                pointOverlayItem5.mDefaultMarker = this.d.createMarker(R.drawable.riding_navi_end_icon, 4);
                this.d.addItem(pointOverlayItem5);
            }
        }
    }

    public final void b(RideType rideType) {
        if (rideType == RideType.RIDE_TYPE || rideType == RideType.SHARE_RIDE_TYPE) {
            a(this.c.getBound());
            return;
        }
        if (rideType == RideType.DEST_TYPE) {
            a(this.e.getBound());
        }
    }

    public final void a() {
        this.d.clear();
        this.c.clear();
        this.e.clear();
        this.f.m(false);
    }

    private void a(Rect rect) {
        if (rect != null) {
            float b2 = b(rect);
            GeoPoint a2 = a(rect, b2);
            this.f.b(400, b2 == -1.0f ? -9999.0f : b2, 0, 0, a2.x, a2.y);
        }
    }

    private float b(Rect rect) {
        if (this.k == 0) {
            int height = (((ags.a(AMapPageUtil.getAppContext()).height() - 738) - 132) - ((int) (((float) ags.a(AMapPageUtil.getAppContext()).width()) * 0.75f))) / 2;
            this.k = height + 132 + agn.a(AMapPageUtil.getAppContext(), 50.0f) + height + 738 + agn.a(AMapPageUtil.getAppContext(), 10.0f) + 100;
        }
        if (this.l == 0) {
            this.l = (agn.a(AMapPageUtil.getAppContext(), 20.0f) + 50) << 1;
        }
        float U = this.f.U();
        return Math.min((float) a, Math.max((float) b, Math.min((float) a((double) ((((float) this.f.al()) - ((float) this.l)) * U), (double) rect.width()), (float) a((double) ((((float) this.f.am()) - ((float) this.k)) * U), (double) rect.height()))));
    }

    private static double a(double d2, double d3) {
        return 20.0d - (Math.log(d3 / d2) / Math.log(2.0d));
    }

    private GeoPoint a(Rect rect, float f2) {
        float U = this.f.U();
        float f3 = ((float) (this.j - this.h)) * U;
        double d2 = (double) (19.0f - f2);
        return new GeoPoint(rect.centerX() + ((int) (((double) (((float) (this.i - this.g)) * U)) * Math.pow(2.0d, d2))), rect.centerY() + ((int) (((double) f3) * Math.pow(2.0d, d2))));
    }
}
