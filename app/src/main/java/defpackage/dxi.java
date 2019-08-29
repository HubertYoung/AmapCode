package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.BusPaths;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusLineOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusPointOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusStationNameOverlay;
import com.autonavi.minimap.route.bus.localbus.overlay.RouteBusTipOverlay;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dxi reason: default package */
/* compiled from: BusNaviErrorReportUtil */
public final class dxi {

    /* renamed from: dxi$a */
    /* compiled from: BusNaviErrorReportUtil */
    public static class a extends dta {
        private IBusRouteResult b;
        private float c = -1.0f;
        private dxj d;

        public final boolean a() {
            return false;
        }

        public final boolean b() {
            return false;
        }

        public a(IBusRouteResult iBusRouteResult) {
            this.b = iBusRouteResult;
        }

        public final List<BaseOverlayDelegate> a(bid bid, bty bty) {
            int i;
            int i2;
            int i3;
            int i4;
            this.d = new dxj(bid.getContext(), this.b);
            dxj dxj = this.d;
            dxj.k = bty;
            dxj.j = LayoutInflater.from(dxj.i);
            ArrayList arrayList = new ArrayList();
            dxj.a = new RouteBusLineOverlay(dxj.k);
            RouteBusStationNameOverlay routeBusStationNameOverlay = new RouteBusStationNameOverlay(dxj.k);
            routeBusStationNameOverlay.setMinDisplayLevel(15);
            dxj.b = routeBusStationNameOverlay;
            dxj.c = new RouteBusPointOverlay(dxj.k);
            dxj.d = new RouteBusPointOverlay(dxj.k);
            dxj.e = new RouteBusPointOverlay(dxj.k);
            RouteBusTipOverlay routeBusTipOverlay = new RouteBusTipOverlay(dxj.k);
            routeBusTipOverlay.setClickable(false);
            routeBusTipOverlay.setMinDisplayLevel(10);
            routeBusTipOverlay.setAutoSetFocus(false);
            dxj.g = routeBusTipOverlay;
            dxj.f = new RouteBusPointOverlay(dxj.k);
            arrayList.add(dxj.a);
            arrayList.add(dxj.b);
            arrayList.add(dxj.c);
            arrayList.add(dxj.d);
            arrayList.add(dxj.e);
            arrayList.add(dxj.g);
            arrayList.add(dxj.f);
            if (!(dxj.h == null || !dxj.h.hasData() || dxj.a == null || dxj.c == null || dxj.e == null || dxj.d == null || dxj.f == null)) {
                if (dxj.a != null) {
                    dxj.a.clear();
                }
                if (dxj.b != null) {
                    dxj.b.clear();
                }
                if (dxj.c != null) {
                    dxj.c.clear();
                }
                if (dxj.e != null) {
                    dxj.e.clear();
                }
                if (dxj.d != null) {
                    dxj.d.clear();
                }
                if (dxj.f != null) {
                    dxj.f.clear();
                }
                if (dxj.g != null) {
                    dxj.g.clear();
                }
                BusPaths busPathsResult = dxj.h.getBusPathsResult();
                BusPath focusBusPath = dxj.h.getFocusBusPath();
                if (!(focusBusPath == null || busPathsResult == null || focusBusPath.mSectionNum == 0)) {
                    ArrayList arrayList2 = new ArrayList();
                    if (focusBusPath.mStartObj != null) {
                        i2 = focusBusPath.mStartObj.mDisX;
                        i = focusBusPath.mStartObj.mDisY;
                    } else {
                        i2 = 0;
                        i = 0;
                    }
                    if (i2 == 0 && i == 0) {
                        i2 = busPathsResult.mstartX;
                        i = busPathsResult.mstartY;
                    }
                    PointOverlayItem a = dxj.a(dxj.f, i2, i, R.drawable.bubble_start, 5);
                    dvw dvw = new dvw();
                    dvw.e = a;
                    dvw.a = 0;
                    dvw.d = 0;
                    dvw.c = 2;
                    arrayList2.add(dvw);
                    int a2 = dxj.a(focusBusPath, dxj.a, arrayList2, 1);
                    if (focusBusPath.mEndObj != null) {
                        i3 = focusBusPath.mEndObj.mDisX;
                        i4 = focusBusPath.mEndObj.mDisY;
                    } else {
                        i4 = 0;
                        i3 = 0;
                    }
                    if (i4 == 0 && i3 == 0) {
                        i3 = busPathsResult.mendX;
                        i4 = busPathsResult.mendY;
                    }
                    PointOverlayItem a3 = dxj.a(dxj.f, i3, i4, R.drawable.bubble_end, 5);
                    dvw dvw2 = new dvw();
                    dvw2.e = a3;
                    dvw2.a = a2;
                    dvw2.d = 0;
                    dvw2.c = 3;
                    arrayList2.add(dvw2);
                }
            }
            this.d.a(15.0f, false);
            this.d.a();
            return arrayList;
        }

        /* JADX WARNING: Removed duplicated region for block: B:19:0x0033  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean b(defpackage.bid r3, defpackage.bty r4) {
            /*
                r2 = this;
                dxj r0 = r2.d
                r1 = 0
                if (r0 != 0) goto L_0x0006
                return r1
            L_0x0006:
                boolean r3 = r3.isAlive()
                if (r3 == 0) goto L_0x003b
                float r3 = r4.v()
                float r4 = r2.c
                r0 = -1082130432(0xffffffffbf800000, float:-1.0)
                int r4 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r4 == 0) goto L_0x002d
                float r4 = r2.c
                r0 = 1097859072(0x41700000, float:15.0)
                int r4 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1))
                if (r4 < 0) goto L_0x0028
                int r4 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
                if (r4 < 0) goto L_0x0025
                return r1
            L_0x0025:
                r2.c = r3
                goto L_0x002f
            L_0x0028:
                int r4 = (r3 > r0 ? 1 : (r3 == r0 ? 0 : -1))
                if (r4 >= 0) goto L_0x002d
                return r1
            L_0x002d:
                r2.c = r3
            L_0x002f:
                dxj r3 = r2.d
                if (r3 == 0) goto L_0x003b
                dxj r3 = r2.d
                float r4 = r2.c
                r0 = 1
                r3.a(r4, r0)
            L_0x003b:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.dxi.a.b(bid, bty):boolean");
        }
    }

    public static PageBundle a(Context context, IBusRouteResult iBusRouteResult, String str, boolean z, String str2) {
        if (iBusRouteResult == null) {
            return null;
        }
        PageBundle pageBundle = new PageBundle();
        pageBundle.putString("page_action", "com.basemap.action.feedback_entry_list");
        pageBundle.putInt("page_id", 5);
        pageBundle.putBoolean("has_ride_for_bus_route", z);
        pageBundle.putInt("sourcepage", 14);
        pageBundle.putString("error_pic_path", str);
        POI fromPOI = iBusRouteResult.getFromPOI();
        POI toPOI = iBusRouteResult.getToPOI();
        if (fromPOI != null) {
            fromPOI = fromPOI.clone();
            if (TextUtils.equals(AMapPageUtil.getAppContext().getString(R.string.bus_my_location), fromPOI.getName()) && iBusRouteResult.getBusPathsResult() != null) {
                fromPOI.setName(iBusRouteResult.getBusPathsResult().mStartDes);
            }
        }
        if (toPOI != null) {
            toPOI = toPOI.clone();
            if (TextUtils.equals(AMapPageUtil.getAppContext().getString(R.string.bus_my_location), toPOI.getName()) && iBusRouteResult.getBusPathsResult() != null) {
                toPOI.setName(iBusRouteResult.getBusPathsResult().mEndDes);
            }
        }
        pageBundle.putObject("startpoint", fromPOI);
        pageBundle.putObject("endpoint", toPOI);
        pageBundle.putString("location_log", LocationInstrument.getInstance().getLocationLog(context));
        pageBundle.putString("category", iBusRouteResult.getMethod());
        pageBundle.putBoolean("bundle_key_boolean_default", false);
        pageBundle.putString("bsid", iBusRouteResult.getBsid());
        if (iBusRouteResult.getFromPOI() != null) {
            pageBundle.putString("Ad1", String.valueOf(li.a().a(iBusRouteResult.getFromPOI().getPoint().getLongitude(), iBusRouteResult.getFromPOI().getPoint().getLatitude())));
        }
        if (iBusRouteResult.getToPOI() != null) {
            pageBundle.putString("Ad2", String.valueOf(li.a().a(iBusRouteResult.getToPOI().getPoint().getLongitude(), iBusRouteResult.getToPOI().getPoint().getLatitude())));
        }
        if (iBusRouteResult.getBusPathsResult() != null && iBusRouteResult.getBusPathsResult().mBusPaths != null && !iBusRouteResult.isExtBusResult()) {
            BusPath[] busPathArr = iBusRouteResult.getBusPathsResult().mBusPaths;
            if (busPathArr.length > iBusRouteResult.getFocusBusPathIndex()) {
                BusPath busPath = busPathArr[iBusRouteResult.getFocusBusPathIndex()];
                if (!(fromPOI == null || busPath.mStartObj == null)) {
                    fromPOI.setPoint(new GeoPoint(busPath.mStartObj.mDisX, busPath.mStartObj.mDisY));
                }
                if (!(toPOI == null || busPath.mEndObj == null)) {
                    toPOI.setPoint(new GeoPoint(busPath.mEndObj.mDisX, busPath.mEndObj.mDisY));
                }
                StringBuilder sb = new StringBuilder();
                for (BusPathSection busPathSection : busPath.mPathSections) {
                    if (sb.length() > 0) {
                        sb.append(" -> ");
                    }
                    String str3 = busPathSection.mSectionName;
                    if (TextUtils.isEmpty(str3) || !str3.contains("(")) {
                        sb.append(str3);
                    } else {
                        sb.append(str3.substring(0, str3.indexOf("(")));
                    }
                }
                pageBundle.putString("name", sb.toString());
                pageBundle.putSerializable("bus_path", busPath);
            }
        } else if (iBusRouteResult.getExtBusPathList() != null && iBusRouteResult.getExtBusPathList().size() > 0 && iBusRouteResult.isExtBusResult()) {
            ExtBusPath focusExtBusPath = iBusRouteResult.getFocusExtBusPath();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(focusExtBusPath.mFromPoi.getName());
            sb2.append("->");
            sb2.append(focusExtBusPath.mToPoi.getName());
            pageBundle.putString("name", sb2.toString());
            pageBundle.putSerializable("bus_path", focusExtBusPath);
        }
        pageBundle.putObject("foot_path", iBusRouteResult.getBusResultFootErrorData());
        pageBundle.putInt("zoom_level", 15);
        pageBundle.putObject("overlay_style", new a(iBusRouteResult));
        pageBundle.putString("realtime_bus_param", str2);
        return pageBundle;
    }
}
