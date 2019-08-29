package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.planhome.page.ETripRoutePage;
import com.amap.bundle.planhome.page.PlanHomePage;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@BundleInterface(acg.class)
/* renamed from: acf reason: default package */
/* compiled from: PlanHomeService */
public class acf extends esi implements acg {
    private static final String a = "acf";
    private static final ArrayList<String> b;

    public final String e() {
        return "8.3.0.0";
    }

    static {
        ArrayList<String> arrayList = new ArrayList<>();
        b = arrayList;
        arrayList.add("original_route");
        b.add("bundle_key_poi_start");
        b.add("bundle_key_poi_end");
    }

    public final RouteType a() {
        return acr.a();
    }

    public final void a(RouteType routeType) {
        StringBuilder sb = new StringBuilder("saveLastRouteType() called with: routeType = [");
        sb.append(routeType);
        sb.append("]");
        acr.a(routeType);
    }

    public final void a(final IRouteResultData iRouteResultData, final RouteType routeType) {
        if (iRouteResultData != null && iRouteResultData.getFromPOI() != null && !TextUtils.isEmpty(iRouteResultData.getFromPOI().getName()) && iRouteResultData.getToPOI() != null && !TextUtils.isEmpty(iRouteResultData.getToPOI().getName())) {
            a((Runnable) new Runnable() {
                public final void run() {
                    IRouteResultData iRouteResultData = iRouteResultData;
                    RouteType routeType = routeType;
                    POI clone = iRouteResultData.getFromPOI().clone();
                    POI clone2 = iRouteResultData.getToPOI().clone();
                    chk chk = new chk();
                    chk.f = clone.getPoint().x;
                    chk.g = clone.getPoint().y;
                    chk.h = clone2.getPoint().x;
                    chk.i = clone2.getPoint().y;
                    chk.j = chk.a(clone);
                    chk.k = chk.a(clone2);
                    if (iRouteResultData.getMidPOIs() != null && iRouteResultData.getMidPOIs().size() > 0) {
                        chk.l = chk.a((List<POI>) iRouteResultData.getMidPOIs());
                    }
                    chk.n = chk.a(chk.j);
                    chk.p = chk.a(chk.k);
                    chk.o = chk.b(chk.l);
                    chk.c = chk.b(routeType);
                    chk.d = chk.d(chk);
                    chk.e = iRouteResultData.getMethod();
                    if (routeType == RouteType.TRAIN) {
                        chk.b = chk.c(chk);
                    } else if (routeType == RouteType.ETRIP) {
                        chk.b = chk.b(chk);
                    } else {
                        chk.b = chk.a(chk);
                    }
                    chk.m = System.currentTimeMillis() / 1000;
                    if (routeType == RouteType.ETRIP) {
                        bin.a.c(chk.c, chk.b, chk.b(), 1);
                    } else {
                        bin.a.c(chk.c, chk.b, chk.c(), 1);
                    }
                    if ((routeType == RouteType.CAR || routeType == RouteType.BUS || routeType == RouteType.ONFOOT || routeType == RouteType.RIDE || routeType == RouteType.TRUCK || routeType == RouteType.ETRIP) && !"地图选定位置".equals(clone2.getName()) && chk.a != null) {
                        chk.a.a(clone2, routeType);
                    }
                }
            });
        }
    }

    public final void a(final POI poi, final POI poi2, final RouteType routeType) {
        if (poi != null && poi2 != null) {
            a((Runnable) new Runnable() {
                public final void run() {
                    chk.a(poi, null, poi2, routeType);
                }
            });
        }
    }

    public final void b(final PageBundle pageBundle) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            public final void run() {
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null) {
                    pageContext.startPage(PlanHomePage.class, pageBundle);
                }
            }
        });
    }

    public final void c(PageBundle pageBundle) {
        if (pageBundle == null) {
            eao.e(a, "startETripRoutePage, bundle is null! Return.");
        } else if (!pageBundle.containsKey("key_type")) {
            eao.e(a, "startETripRoutePage, bundle doesn't has key : key_type! Return.");
        } else {
            RouteType type = RouteType.getType(pageBundle.getInt("key_type", RouteType.CAR.getValue()));
            if (type != RouteType.TAXI) {
                Iterator<String> it = b.iterator();
                while (it.hasNext()) {
                    String next = it.next();
                    if (!pageBundle.containsKey(next)) {
                        String str = a;
                        StringBuilder sb = new StringBuilder("startETripRoutePage, bundle doesn't has key : ");
                        sb.append(next);
                        sb.append("! Return.");
                        eao.e(str, sb.toString());
                        return;
                    }
                }
                String string = pageBundle.getString("original_route");
                POI poi = (POI) pageBundle.getObject("bundle_key_poi_start");
                POI poi2 = (POI) pageBundle.getObject("bundle_key_poi_end");
                if (type == RouteType.ONFOOT || type == RouteType.CAR || type == RouteType.RIDE) {
                    pageBundle.putByteArray("original_route", aci.a(string));
                } else {
                    byte[] a2 = aci.a(string);
                    IRouteResultData iRouteResultData = null;
                    if (a2 != null) {
                        switch (defpackage.aci.AnonymousClass1.a[type.ordinal()]) {
                            case 1:
                                atb atb = (atb) a.a.a(atb.class);
                                if (atb != null) {
                                    iRouteResultData = atb.e().a(a2, poi, poi2);
                                    break;
                                }
                                break;
                            case 2:
                                avi avi = (avi) a.a.a(avi.class);
                                if (avi != null) {
                                    iRouteResultData = avi.b().a(a2, poi, poi2);
                                    break;
                                }
                                break;
                        }
                    } else {
                        eao.e("ETripDataUtil", "parse original data failed, routeType :".concat(String.valueOf(type)));
                    }
                    if (iRouteResultData == null) {
                        eao.e(a, "startETripRoutePage, parse original data fail! Return.");
                    }
                    pageBundle.putObject("key_result", iRouteResultData);
                }
            }
            pageBundle.putInt("key_source", 102);
            if (type == RouteType.BUS) {
                atb atb2 = (atb) a.a.a(atb.class);
                if (atb2 != null) {
                    atb2.a().a(2, pageBundle);
                }
                return;
            }
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage(ETripRoutePage.class, pageBundle);
            }
        }
    }

    public final int c() {
        Class<?> topPageClass = AMapPageUtil.getTopPageClass();
        if (topPageClass != null) {
            String simpleName = topPageClass.getSimpleName();
            if (simpleName == null) {
                return 0;
            }
            eao.a((String) "", "pageInNavigating clz:".concat(String.valueOf(simpleName)));
            aww aww = (aww) a.a.a(aww.class);
            String str = null;
            Object simpleName2 = aww != null ? aww.a().a(1).getSimpleName() : null;
            avi avi = (avi) a.a.a(avi.class);
            Object simpleName3 = avi != null ? avi.c().a(1).getSimpleName() : null;
            avn avn = (avn) a.a.a(avn.class);
            Object simpleName4 = avn != null ? avn.a().a(1).getSimpleName() : null;
            avo avo = (avo) a.a.a(avo.class);
            Object simpleName5 = avo != null ? avo.a().a(1).getSimpleName() : null;
            bdf bdf = (bdf) a.a.a(bdf.class);
            if (bdf != null) {
                str = bdf.b().a(1).getSimpleName();
            }
            if (simpleName.equals(simpleName2)) {
                return 1;
            }
            if (simpleName.equals(simpleName3)) {
                return 2;
            }
            if (simpleName.equals(simpleName4)) {
                return 3;
            }
            if (simpleName.equals(simpleName5)) {
                return 4;
            }
            if (simpleName.equals(str) || (bdf != null && bdf.a().c())) {
                eao.a((String) "", (String) "pageInNavigating has sharebike order!");
                return 5;
            }
        }
        ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
        if (pagesStacks != null && !pagesStacks.isEmpty()) {
            int i = 0;
            while (i < pagesStacks.size()) {
                bid stackFragment = AMapPageUtil.getStackFragment(i);
                if (stackFragment == null || !(stackFragment instanceof edd)) {
                    i++;
                } else {
                    eao.a((String) "", (String) "pageInNavigating in bg navi!");
                    return 6;
                }
            }
        }
        return 0;
    }

    public final boolean d() {
        return axq.a().b();
    }

    private static void a(Runnable runnable) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            runnable.run();
        } else {
            ahm.c(runnable);
        }
    }

    public final void a(@NonNull RouteType routeType, POI poi) {
        if (b(routeType)) {
            ade.a().a(poi);
        }
    }

    public final void a(@NonNull RouteType routeType, List<POI> list) {
        if (b(routeType)) {
            ade.a().a(list);
        }
    }

    public final void b(@NonNull RouteType routeType, POI poi) {
        if (b(routeType)) {
            ade.a().b(poi);
        }
    }

    @Nullable
    public final POI f() {
        return ade.a().b(true);
    }

    @Nullable
    public final List<POI> g() {
        return ade.a().c();
    }

    @Nullable
    public final POI h() {
        return ade.a().d(true);
    }

    public final RouteType i() {
        return adf.a().b();
    }

    public final void a(POI poi) {
        acq.a().a(poi);
    }

    public final void a(String str) {
        acq.a().a(str);
    }

    public final void b(POI poi) {
        acq.a().b(poi);
    }

    public final void b(String str) {
        acq.a().b(str);
    }

    public final void a(List<POI> list) {
        acq.a().a(list);
    }

    public final void a(String[] strArr) {
        acq.a().a(strArr);
    }

    public final void a(ada ada) {
        adf.a().a(ada);
    }

    public final boolean b(ada ada) {
        return adf.a().b(ada);
    }

    public final void a(acy acy) {
        ade.a().c = acy;
    }

    private static boolean b(RouteType routeType) {
        if (routeType == null) {
            return false;
        }
        return routeType == RouteType.CAR || routeType == RouteType.TRUCK || routeType == RouteType.MOTOR || routeType == RouteType.RIDE || routeType == RouteType.ONFOOT;
    }

    public final RouteType j() {
        return ade.a().d;
    }

    public final RouteType k() {
        return ade.a().e;
    }

    public final boolean a(POI poi, POI poi2) {
        return acj.a(poi, poi2, true);
    }

    public final boolean a(List<POI> list, List<POI> list2) {
        return acj.a(list, list2);
    }

    public final String b() {
        String stringValue = new MapSharePreference(SharePreferenceName.user_route_method_info).getStringValue("car_method", "1");
        return TextUtils.isEmpty(stringValue) ? "1" : stringValue;
    }

    public final void a(PageBundle pageBundle) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPage(PlanHomePage.class, pageBundle);
        }
    }
}
