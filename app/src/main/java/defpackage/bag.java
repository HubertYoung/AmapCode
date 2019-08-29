package defpackage;

import android.text.TextUtils;
import com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils.LogUnAvailbleItem;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.bundle.routecommute.common.bean.CommuteControlBean;
import com.autonavi.bundle.routecommute.common.bean.NaviAddress;
import com.autonavi.bundle.routecommute.common.bean.TipsMatrix.DisType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: bag reason: default package */
/* compiled from: DriveCommuteTipsHelpUtil */
public class bag {
    private static final String a = "bag";

    public static String a(int i) {
        Date date = new Date(System.currentTimeMillis() + ((long) (i * 1000)));
        Calendar instance = Calendar.getInstance();
        instance.setTime(date);
        int i2 = instance.get(11);
        String str = (i2 <= 0 || i2 > 9) ? i2 == 0 ? "00" : String.valueOf(i2) : "0".concat(String.valueOf(i2));
        int i3 = instance.get(12);
        String str2 = (i3 <= 0 || i3 > 9) ? i3 == 0 ? "00" : String.valueOf(i3) : "0".concat(String.valueOf(i3));
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(":");
        sb.append(str2);
        return sb.toString();
    }

    public static String b(int i) {
        int i2 = i / 60;
        StringBuilder sb = new StringBuilder();
        if (i2 >= 60) {
            int i3 = i2 / 60;
            int i4 = i2 % 60;
            sb.append(i3);
            sb.append("小时");
            if (i4 > 0) {
                sb.append(i4);
                sb.append("分钟");
            }
        } else if (i2 == 0) {
            sb.append("1");
            sb.append("分钟");
        } else {
            sb.append(i2);
            sb.append("分钟");
        }
        return sb.toString();
    }

    public static boolean a(AbstractBaseMapPage abstractBaseMapPage) {
        if (abstractBaseMapPage instanceof MapBasePage) {
            ICQLayerController cQLayerController = ((MapBasePage) abstractBaseMapPage).getCQLayerController();
            if (cQLayerController != null) {
                return cQLayerController.isShowing();
            }
        }
        return false;
    }

    public static void d(int i) {
        azl.a((String) "drive", i);
    }

    public static POI a(PageBundle pageBundle) {
        if (pageBundle.containsKey("result_poi")) {
            return (POI) pageBundle.getObject("result_poi");
        }
        return null;
    }

    public static void e(int i) {
        String str = "";
        if (i == 4 || i == 8) {
            str = i == 4 ? "1" : "3";
        } else if (i == 2 || i == 6) {
            str = i == 2 ? "0" : "2";
        }
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B252", jSONObject);
        }
    }

    public static void f(int i) {
        String str = "";
        if (i == 4) {
            str = "1";
        } else if (i == 2) {
            str = "0";
        }
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B271", jSONObject);
        }
    }

    public static void a(int i, String str) {
        String str2 = "";
        if (i == 11) {
            str2 = "1";
        } else if (i == 12) {
            str2 = "0";
        }
        if (!TextUtils.isEmpty(str2) && !TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str2);
                jSONObject.put(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B268", jSONObject);
        }
    }

    public static void a(String str, int i) {
        String str2 = "";
        if (i == 4 || i == 8) {
            str2 = i == 4 ? "1" : "3";
        } else if (i == 2 || i == 6) {
            str2 = i == 2 ? "0" : "2";
        }
        if (!TextUtils.isEmpty(str2)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str2);
                jSONObject.put("action", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B250", jSONObject);
        }
    }

    public static void g(int i) {
        String str = "";
        if (i == 12) {
            str = "0";
        } else if (i == 11) {
            str = "1";
        }
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B267", jSONObject);
        }
    }

    public static void a(String str, int i, String str2, GeoPoint geoPoint) {
        String str3 = "";
        if (i == 2) {
            str3 = "0";
        } else if (i == 4) {
            str3 = "1";
        } else if (i == 6) {
            str3 = "2";
        } else if (i == 8) {
            str3 = "3";
        } else if (i == 10) {
            str3 = "4";
        }
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(str3)) {
            boolean z = b(str2, geoPoint).ordinal() == DisType.CPOINT_DIS.ordinal();
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str3);
                jSONObject.put("action", str);
                jSONObject.put(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, z ? "1" : "0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B253", jSONObject);
        }
    }

    public static void a(int i, String str, GeoPoint geoPoint, String str2) {
        String str3 = "";
        boolean z = true;
        if (i == 1) {
            str3 = "0";
        } else if (i == 3) {
            str3 = "2";
        } else if (i == 5) {
            str3 = "1";
        } else if (i == 7) {
            str3 = "3";
        } else if (i == 9) {
            str3 = "4";
        }
        if (!TextUtils.isEmpty(str3)) {
            if (b(str, geoPoint).ordinal() != DisType.CPOINT_DIS.ordinal()) {
                z = false;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str3);
                jSONObject.put(LogUnAvailbleItem.EXTRA_KEY_SUBTYPE, z ? "1" : "0");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", str2, jSONObject);
        }
    }

    private static DisType b(String str, GeoPoint geoPoint) {
        NaviAddress b = azf.b();
        if (geoPoint == null) {
            geoPoint = LocationInstrument.getInstance().getLatestPosition(5);
        }
        if (geoPoint == null) {
            azb.a(a, "getCommuteTipsType currentPoint null!");
            return DisType.INVALID_DIS;
        }
        if (str == null) {
            String a2 = lo.a().a((String) "commute_config");
            if (TextUtils.isEmpty(a2)) {
                a2 = azi.m();
            }
            azb.a(a, "handlerCommute updataConfig from init result : ".concat(String.valueOf(a2)));
            CommuteControlBean create = CommuteControlBean.create(a2);
            if (create == null) {
                azb.a(a, "getCommuteTipsType mConfigBean null!");
                return DisType.INVALID_DIS;
            }
            str = create.carBubbleRule;
        }
        return azk.b(b, str, geoPoint);
    }

    public static int a(String str, GeoPoint geoPoint) {
        NaviAddress b = azf.b();
        if (geoPoint == null) {
            geoPoint = LocationInstrument.getInstance().getLatestPosition(5);
        }
        if (geoPoint == null) {
            azb.a(a, "getCommuteTipsType currentPoint null!");
            return 0;
        }
        if (str == null) {
            String a2 = lo.a().a((String) "commute_config");
            if (TextUtils.isEmpty(a2)) {
                a2 = azi.m();
            }
            azb.a(a, "handlerCommute updataConfig from init result : ".concat(String.valueOf(a2)));
            CommuteControlBean create = CommuteControlBean.create(a2);
            if (create == null) {
                azb.a(a, "getCommuteTipsType mConfigBean null!");
                return 0;
            }
            str = create.carBubbleRule;
        }
        return azk.a(b, str, geoPoint);
    }

    public static void h(int i) {
        String str = "";
        if (i == 2) {
            str = "0";
        } else if (i == 4) {
            str = "1";
        } else if (i == 6) {
            str = "2";
        } else if (i == 8) {
            str = "3";
        }
        if (!TextUtils.isEmpty(str)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B276", jSONObject);
        }
    }

    public static void b(String str, int i) {
        String str2 = "";
        if (i == 2) {
            str2 = "0";
        } else if (i == 4) {
            str2 = "1";
        } else if (i == 6) {
            str2 = "2";
        } else if (i == 8) {
            str2 = "3";
        }
        if (!TextUtils.isEmpty(str2)) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("type", str2);
                jSONObject.put("action", str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2("P00001", "B277", jSONObject);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x00a0 A[Catch:{ JSONException -> 0x0183 }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a1 A[Catch:{ JSONException -> 0x0183 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void c(int r12) {
        /*
            com.autonavi.sdk.location.LocationInstrument r0 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            r1 = 5
            com.autonavi.common.model.GeoPoint r0 = r0.getLatestPosition(r1)
            if (r0 != 0) goto L_0x000c
            return
        L_0x000c:
            esb r1 = defpackage.esb.a.a
            java.lang.Class<axw> r2 = defpackage.axw.class
            esc r1 = r1.a(r2)
            axw r1 = (defpackage.axw) r1
            if (r1 != 0) goto L_0x001b
            return
        L_0x001b:
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            org.json.JSONObject r3 = new org.json.JSONObject
            r3.<init>()
            org.json.JSONObject r4 = new org.json.JSONObject
            r4.<init>()
            java.lang.String r5 = "x"
            int r6 = r0.x     // Catch:{ JSONException -> 0x0183 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ JSONException -> 0x0183 }
            r2.putOpt(r5, r6)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "y"
            int r6 = r0.y     // Catch:{ JSONException -> 0x0183 }
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ JSONException -> 0x0183 }
            r2.putOpt(r5, r6)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "lon"
            double r6 = r0.getLongitude()     // Catch:{ JSONException -> 0x0183 }
            java.lang.Double r6 = java.lang.Double.valueOf(r6)     // Catch:{ JSONException -> 0x0183 }
            r2.putOpt(r5, r6)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "lat"
            double r6 = r0.getLatitude()     // Catch:{ JSONException -> 0x0183 }
            java.lang.Double r6 = java.lang.Double.valueOf(r6)     // Catch:{ JSONException -> 0x0183 }
            r2.putOpt(r5, r6)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "cityCode"
            java.lang.String r6 = r0.getCity()     // Catch:{ JSONException -> 0x0183 }
            r2.putOpt(r5, r6)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "adcode"
            int r0 = r0.getAdCode()     // Catch:{ JSONException -> 0x0183 }
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ JSONException -> 0x0183 }
            r2.putOpt(r5, r0)     // Catch:{ JSONException -> 0x0183 }
            r0 = 0
            com.autonavi.bundle.routecommute.common.bean.NaviAddress r5 = defpackage.azf.b()     // Catch:{ JSONException -> 0x0183 }
            r6 = 4
            r7 = 8
            r8 = 6
            r9 = 2
            if (r12 == r9) goto L_0x0092
            if (r12 != r8) goto L_0x0081
            goto L_0x0092
        L_0x0081:
            if (r12 == r6) goto L_0x0085
            if (r12 != r7) goto L_0x009e
        L_0x0085:
            if (r5 == 0) goto L_0x009e
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r10 = r5.company     // Catch:{ JSONException -> 0x0183 }
            if (r10 == 0) goto L_0x009e
            com.autonavi.bundle.routecommute.common.bean.NaviAddressCompany r0 = r5.company     // Catch:{ JSONException -> 0x0183 }
            com.autonavi.common.model.POI r0 = r0.getCompany()     // Catch:{ JSONException -> 0x0183 }
            goto L_0x009e
        L_0x0092:
            if (r5 == 0) goto L_0x009e
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r10 = r5.home     // Catch:{ JSONException -> 0x0183 }
            if (r10 == 0) goto L_0x009e
            com.autonavi.bundle.routecommute.common.bean.NaviAddressHome r0 = r5.home     // Catch:{ JSONException -> 0x0183 }
            com.autonavi.common.model.POI r0 = r0.getHome()     // Catch:{ JSONException -> 0x0183 }
        L_0x009e:
            if (r0 != 0) goto L_0x00a1
            return
        L_0x00a1:
            java.lang.String r5 = "poiid"
            java.lang.String r10 = r0.getId()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "name"
            java.lang.String r10 = r0.getName()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "address"
            java.lang.String r10 = r0.getAddr()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "phoneNumbers"
            java.lang.String r10 = r0.getPhone()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "x"
            com.autonavi.common.model.GeoPoint r10 = r0.getPoint()     // Catch:{ JSONException -> 0x0183 }
            int r10 = r10.x     // Catch:{ JSONException -> 0x0183 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "y"
            com.autonavi.common.model.GeoPoint r10 = r0.getPoint()     // Catch:{ JSONException -> 0x0183 }
            int r10 = r10.y     // Catch:{ JSONException -> 0x0183 }
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "lon"
            com.autonavi.common.model.GeoPoint r10 = r0.getPoint()     // Catch:{ JSONException -> 0x0183 }
            double r10 = r10.getLongitude()     // Catch:{ JSONException -> 0x0183 }
            java.lang.Double r10 = java.lang.Double.valueOf(r10)     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "lat"
            com.autonavi.common.model.GeoPoint r10 = r0.getPoint()     // Catch:{ JSONException -> 0x0183 }
            double r10 = r10.getLatitude()     // Catch:{ JSONException -> 0x0183 }
            java.lang.Double r10 = java.lang.Double.valueOf(r10)     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "cityCode"
            java.lang.String r10 = r0.getCityCode()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "adcode"
            java.lang.String r10 = r0.getAdCode()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "end_poi_extension"
            java.lang.String r10 = r0.getEndPoiExtension()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r10)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r5 = "transparent"
            java.lang.String r0 = r0.getTransparent()     // Catch:{ JSONException -> 0x0183 }
            r3.putOpt(r5, r0)     // Catch:{ JSONException -> 0x0183 }
            r0 = 0
            if (r12 == r9) goto L_0x014e
            if (r12 != r8) goto L_0x0132
            goto L_0x014e
        L_0x0132:
            if (r12 == r6) goto L_0x0136
            if (r12 != r7) goto L_0x0163
        L_0x0136:
            if (r12 != r7) goto L_0x0143
            java.lang.String r12 = "endPointType"
            r5 = 3
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ JSONException -> 0x0183 }
            r4.putOpt(r12, r5)     // Catch:{ JSONException -> 0x0183 }
            goto L_0x0163
        L_0x0143:
            java.lang.String r12 = "endPointType"
            r5 = 1
            java.lang.Integer r5 = java.lang.Integer.valueOf(r5)     // Catch:{ JSONException -> 0x0183 }
            r4.putOpt(r12, r5)     // Catch:{ JSONException -> 0x0183 }
            goto L_0x0163
        L_0x014e:
            if (r12 != r8) goto L_0x015a
            java.lang.String r12 = "endPointType"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r9)     // Catch:{ JSONException -> 0x0183 }
            r4.putOpt(r12, r5)     // Catch:{ JSONException -> 0x0183 }
            goto L_0x0163
        L_0x015a:
            java.lang.String r12 = "endPointType"
            java.lang.Integer r5 = java.lang.Integer.valueOf(r0)     // Catch:{ JSONException -> 0x0183 }
            r4.putOpt(r12, r5)     // Catch:{ JSONException -> 0x0183 }
        L_0x0163:
            java.lang.String r12 = "startPoi"
            r4.putOpt(r12, r2)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r12 = "endPoi"
            r4.putOpt(r12, r3)     // Catch:{ JSONException -> 0x0183 }
            java.lang.String r12 = "from"
            java.lang.Integer r0 = java.lang.Integer.valueOf(r0)     // Catch:{ JSONException -> 0x0183 }
            r4.putOpt(r12, r0)     // Catch:{ JSONException -> 0x0183 }
            bid r12 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r0 = r4.toString()
            r1.a(r12, r0)
            return
        L_0x0183:
            r12 = move-exception
            r12.printStackTrace()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bag.c(int):void");
    }
}
