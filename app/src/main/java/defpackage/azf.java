package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.nebula.filecache.FileCache;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.logs.AMapLog;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommute.common.NaviAddressManager$1;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany;
import com.autonavi.bundle.routecommute.common.bean.NaviAddressHome;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.navigation.NavigationRequestHolder;
import com.autonavi.minimap.navigation.param.AddressRequest;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: azf reason: default package */
/* compiled from: NaviAddressManager */
public final class azf {
    public static NaviAddress a;

    /* renamed from: azf$a */
    /* compiled from: NaviAddressManager */
    public interface a {
        void a(NaviAddress naviAddress);
    }

    public static JSONObject a() {
        POI e = azi.e();
        POI f = azi.f();
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject jSONObject2 = new JSONObject();
            if (e != null) {
                String id = e.getId();
                if (id == null) {
                    id = "";
                }
                jSONObject2.putOpt(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, id);
                jSONObject2.putOpt("name", e.getName());
                jSONObject2.putOpt("address", e.getAddr());
                jSONObject2.putOpt("phoneNumbers", e.getPhone());
                jSONObject2.putOpt("new_type", e.getType());
                GeoPoint point = e.getPoint();
                jSONObject2.putOpt(DictionaryKeys.CTRLXY_X, Integer.valueOf(point.x));
                jSONObject2.putOpt(DictionaryKeys.CTRLXY_Y, Integer.valueOf(point.y));
                jSONObject2.putOpt(LocationParams.PARA_FLP_AUTONAVI_LON, Double.valueOf(point.getLongitude()));
                jSONObject2.putOpt("lat", Double.valueOf(point.getLatitude()));
                jSONObject2.putOpt("cityCode", e.getCityCode());
                jSONObject2.putOpt(AutoJsonUtils.JSON_ADCODE, e.getAdCode());
                jSONObject2.putOpt("end_poi_extension", e.getEndPoiExtension());
                jSONObject.putOpt("home", jSONObject2);
            }
            if (f != null) {
                JSONObject jSONObject3 = new JSONObject();
                String id2 = f.getId();
                if (id2 == null) {
                    id2 = "";
                }
                jSONObject3.putOpt(LocationInstrument.LOCATION_EXTRAS_KEY_POIID, id2);
                jSONObject3.putOpt("name", f.getName());
                jSONObject3.putOpt("address", f.getAddr());
                jSONObject3.putOpt("phoneNumbers", f.getPhone());
                jSONObject3.putOpt("new_type", f.getType());
                GeoPoint point2 = f.getPoint();
                jSONObject3.putOpt(DictionaryKeys.CTRLXY_X, Integer.valueOf(point2.x));
                jSONObject3.putOpt(DictionaryKeys.CTRLXY_Y, Integer.valueOf(point2.y));
                jSONObject3.putOpt(LocationParams.PARA_FLP_AUTONAVI_LON, Double.valueOf(point2.getLongitude()));
                jSONObject3.putOpt("lat", Double.valueOf(point2.getLatitude()));
                jSONObject3.putOpt("cityCode", f.getCityCode());
                jSONObject3.putOpt(AutoJsonUtils.JSON_ADCODE, f.getAdCode());
                jSONObject3.putOpt("end_poi_extension", f.getEndPoiExtension());
                jSONObject.putOpt("company", jSONObject3);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return jSONObject;
    }

    public static void a(a aVar) {
        NaviAddress b = b();
        a = b;
        if (b == null) {
            a(aVar, 3);
        } else if (a.company != null && a.home == null) {
            a(aVar, 1);
        } else if (a.home != null && a.company == null) {
            a(aVar, 2);
        } else if (a.home.source == 0 && a.company.source == 0) {
            if (a(azi.a()) && !a(azi.c())) {
                a(aVar, 1);
            } else if (a(azi.c()) && !a(azi.a())) {
                a(aVar, 2);
            } else if (!a(azi.a()) || !a(azi.c())) {
                aVar.a(a);
            } else {
                a(aVar, 3);
            }
        } else if (a.home.source == 0 && a.company.source == 1) {
            if (a(azi.a())) {
                a(aVar, 1);
            } else {
                aVar.a(a);
            }
        } else if (a.company.source == 0 && a.home.source == 1) {
            if (a(azi.c())) {
                a(aVar, 2);
            } else {
                aVar.a(a);
            }
        } else if (!e()) {
            a(aVar, 0);
        } else {
            aVar.a(a);
        }
    }

    public static NaviAddress b() {
        NaviAddress naviAddress = new NaviAddress();
        NaviAddressHome naviAddressHome = new NaviAddressHome();
        NaviAddressCompany naviAddressCompany = new NaviAddressCompany();
        POI a2 = azl.a();
        if (a2 != null) {
            naviAddressHome.setHome(a2);
            naviAddressHome.source = 1;
            naviAddress.home = naviAddressHome;
        } else {
            POI e = azi.e();
            if (e != null) {
                naviAddressHome.setHome(e);
                naviAddressHome.source = 0;
                naviAddress.home = naviAddressHome;
                a2 = e;
            }
        }
        POI b = azl.b();
        if (b != null) {
            naviAddressCompany.setCompany(b);
            naviAddressCompany.source = 1;
            naviAddress.company = naviAddressCompany;
        } else {
            POI f = azi.f();
            if (f != null) {
                naviAddressCompany.setCompany(f);
                naviAddressCompany.source = 0;
                naviAddress.company = naviAddressCompany;
                b = f;
            }
        }
        if (a2 == null && b == null) {
            return null;
        }
        naviAddress.busCarPref = azi.g();
        return naviAddress;
    }

    public static POI c() {
        POI a2 = azl.a();
        if (a2 == null) {
            return azi.e();
        }
        StringBuilder sb = new StringBuilder("getHomePoi = ");
        sb.append(a2.getName());
        azb.a("NaviAddressManager: ", sb.toString());
        return a2;
    }

    public static POI d() {
        POI b = azl.b();
        if (b == null) {
            return azi.f();
        }
        StringBuilder sb = new StringBuilder("getCompanyPOI = ");
        sb.append(b.getName());
        azb.a("NaviAddressManager: ", sb.toString());
        return b;
    }

    private static boolean e() {
        return !TextUtils.isEmpty(azi.g());
    }

    private static boolean a(long j) {
        if (j == 0) {
            return true;
        }
        long currentTimeMillis = System.currentTimeMillis() - j;
        AMapLog.d("Daniel-27", "checkDateDeprecated dValue: ".concat(String.valueOf(currentTimeMillis)));
        if (currentTimeMillis >= FileCache.EXPIRE_TIME) {
            return true;
        }
        return false;
    }

    private static void a(a aVar, int i) {
        if (a(azi.a()) || a(azi.c())) {
            NavigationRequestHolder.getInstance().sendAddress(new AddressRequest(), new NaviAddressManager$1(i, aVar));
            return;
        }
        if (aVar != null) {
            aVar.a(a);
        }
    }
}
