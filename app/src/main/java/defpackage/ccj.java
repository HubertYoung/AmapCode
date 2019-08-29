package defpackage;

import android.text.TextUtils;
import android.view.View;
import com.amap.bundle.statistics.LogManager;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import com.autonavi.minimap.map.DPoint;
import com.autonavi.minimap.offline.auto.protocol.utils.AutoJsonUtils;
import com.autonavi.sdk.log.util.LogConstant;
import java.io.Serializable;
import java.util.HashMap;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: ccj reason: default package */
/* compiled from: MainPoiTipItemEvent */
public final class ccj implements a {
    private boolean a;
    private boolean b;

    public ccj(boolean z) {
        this.a = z;
    }

    public ccj(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
    }

    public final boolean a(View view) {
        return (view == null || view.getTag() == null) ? false : false;
    }

    public final boolean a(POI poi) {
        if (this.a) {
            a(LogConstant.MAIN_YANSHU_LONG_MAP_TIP_POI_DETAIL, poi, this.a);
        } else {
            a(LogConstant.MAIN_YANSHU_MAP_TIP_POI_DETAIL, poi, this.a);
        }
        return false;
    }

    public final boolean b(POI poi) {
        if (this.a) {
            a(LogConstant.MAIN_YANSHU_LONG_MAP_TIP_POI_DETAIL, poi, this.a);
        } else {
            a(LogConstant.MAIN_YANSHU_MAP_TIP_POI_DETAIL, poi, this.a);
        }
        return false;
    }

    public final boolean c(POI poi) {
        if (this.a) {
            a(LogConstant.MAIN_YANSHU_LONG_XUANDIAN_SOU_ZHOU_BIAN, poi, this.a);
        } else {
            a(LogConstant.MAIN_YANSHU_XUANDIAN_SOU_ZHOU_BIAN, poi, this.a);
        }
        return false;
    }

    public final boolean d(POI poi) {
        if (this.a) {
            a(LogConstant.MAIN_YANSHU_LONG_MAP_TIP_ROUTE, poi, this.a);
        } else {
            a(LogConstant.MAIN_YANSHU_MAP_TIP_ROUTE, poi, this.a);
        }
        return false;
    }

    public final boolean e(POI poi) {
        if (this.a) {
            a(LogConstant.MAIN_YANSHU_LONG_MAP_TIP_NAVI, poi, this.a);
        } else {
            a(LogConstant.MAIN_YANSHU_MAP_TIP_NAVI, poi, this.a);
        }
        return false;
    }

    private static void a(String str, POI poi, boolean z) {
        String str2;
        if (poi != null) {
            MapManager mapManager = DoNotUseTool.getMapManager();
            if (mapManager != null) {
                bty mapView = mapManager.getMapView();
                if (mapView != null) {
                    GeoPoint glGeoPoint2GeoPoint = GeoPoint.glGeoPoint2GeoPoint(mapView.n());
                    if (glGeoPoint2GeoPoint != null) {
                        DPoint a2 = cfg.a((long) glGeoPoint2GeoPoint.x, (long) glGeoPoint2GeoPoint.y);
                        if (!TextUtils.isEmpty(poi.getAdCode())) {
                            str2 = poi.getAdCode();
                        } else {
                            str2 = poi.getCityCode();
                        }
                        JSONObject jSONObject = new JSONObject();
                        try {
                            StringBuilder sb = new StringBuilder();
                            sb.append(mapView.w());
                            jSONObject.put("from", sb.toString());
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append(a2.y);
                            jSONObject.put("lat", sb2.toString());
                            StringBuilder sb3 = new StringBuilder();
                            sb3.append(a2.x);
                            jSONObject.put(LocationParams.PARA_FLP_AUTONAVI_LON, sb3.toString());
                            if (!z) {
                                StringBuilder sb4 = new StringBuilder();
                                sb4.append(poi.getId());
                                jSONObject.put(TrafficUtil.POIID, sb4.toString());
                            }
                            apr apr = (apr) a.a.a(apr.class);
                            jSONObject.put("itemId", apr != null ? apr.a(AMapPageUtil.getPageContext()) : false ? "1" : "2");
                            jSONObject.put("status", Integer.toString(mapView.s() ? 1 : 2));
                            jSONObject.put(AutoJsonUtils.JSON_ADCODE, str2);
                            if (!z) {
                                jSONObject.put("type", f(poi));
                            }
                            LogManager.actionLogV2("P00001", str, jSONObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static final int f(POI poi) {
        HashMap<String, Serializable> poiExtra = poi.getPoiExtra();
        if (poiExtra != null) {
            Serializable serializable = poiExtra.get("pointType");
            if (serializable != null) {
                return ((Integer) serializable).intValue();
            }
        }
        return 0;
    }
}
