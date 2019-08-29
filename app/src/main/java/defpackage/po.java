package defpackage;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;
import org.json.JSONObject;

/* renamed from: po reason: default package */
/* compiled from: MitVuiRequestModel */
public final class po extends bgd {
    public final String a() {
        return "requestRoute";
    }

    public final boolean a(bgb bgb, bfb bfb) {
        POI poi;
        bgb bgb2 = bgb;
        StringBuilder sb = new StringBuilder("handleVUICmd ");
        sb.append(bgb2.b);
        tq.b("NaviMonitor", "MitVuiRequestModel", sb.toString());
        boolean z = false;
        if (TextUtils.isEmpty(bgb2.b)) {
            return false;
        }
        JSONObject a = rg.a(bgb2.b);
        if (a == null) {
            bfp.a(a.a, 1, "获取串失败");
            return false;
        }
        int a2 = agd.a(a, "param_type");
        int a3 = agd.a(a, "param_type2");
        if (a3 == -1 || eqg.a(a3)) {
            POI a4 = rg.a(a, "start_pois");
            POI a5 = rg.a(a, "end_pois");
            ArrayList<POI> b = rg.b(a, "via_pois");
            int a6 = agd.a(a, "param_type1");
            if (a2 == 1) {
                z = true;
            }
            ArrayList<POI> arrayList = b;
            int a7 = rh.a(a4, a5, b, a6, 1003, z, a(a3));
            if (a7 > 0) {
                d.a.a(bgb2.a, a7, (String) null);
                return true;
            }
            if (z) {
                int i = bgb2.a;
                if (a4 == null) {
                    POI createPOI = POIFactory.createPOI();
                    if (LocationInstrument.getInstance().getLatestPosition(5) == null) {
                        poi = null;
                    } else {
                        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
                        createPOI.setName("我的位置");
                        createPOI.setPoint(latestPosition);
                        poi = createPOI;
                    }
                } else {
                    poi = a4;
                }
                PageBundle pageBundle = new PageBundle();
                nm.a(pageBundle, 0, 0, poi, arrayList, a5, true);
                pageBundle.putString("navi_type", "");
                pageBundle.putInt("mit_voice_tokenid", i);
                nm.a(AMapAppGlobal.getTopActivity(), pageBundle, arrayList, a5, new b(i) {
                    final /* synthetic */ int a;

                    {
                        this.a = r1;
                    }

                    public final void a(boolean z) {
                        tq.b("NaviMonitor", "MIT startMitVuiAutonaviInternal", "免责 isInNavi： ".concat(String.valueOf(z)));
                        if (!z) {
                            d.a.a(this.a, 10000, (String) "");
                        }
                    }
                }, i);
            } else {
                POI poi2 = a5;
                bax bax = (bax) a.a.a(bax.class);
                if (bax != null) {
                    if (a3 == RouteType.ETRIP.getValue()) {
                        d.a.d();
                        d.a.a(bgb2.a, 10000, (String) "");
                    }
                    PageBundle pageBundle2 = new PageBundle();
                    pageBundle2.putInt("key_type", a(a3));
                    pageBundle2.putInt("bundle_key_token", bgb2.a);
                    a.a.b = true;
                    if (poi2 != null) {
                        pageBundle2.putObject("bundle_key_poi_end", poi2);
                    }
                    pageBundle2.putObject("bundle_key_poi_mids", arrayList);
                    if (a4 != null) {
                        pageBundle2.putObject("bundle_key_poi_start", a4);
                    }
                    pageBundle2.putBoolean("bundle_key_auto_route", true);
                    bax.a(pageBundle2);
                }
            }
            return true;
        }
        d.a.a(bgb2.a, 10128, (String) null);
        return true;
    }

    private static int a(int i) {
        bax bax = (bax) a.a.a(bax.class);
        int value = RouteType.CAR.getValue();
        if (bax == null) {
            return value;
        }
        return i != -1 ? i : bax.a().getValue();
    }
}
