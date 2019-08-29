package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;

/* renamed from: aeq reason: default package */
/* compiled from: InternalParserUtils */
public final class aeq {
    static ArrayList<ArrayList<GeoPoint>> a(String str) {
        ArrayList<ArrayList<GeoPoint>> arrayList = new ArrayList<>();
        String[] split = str.split(AUScreenAdaptTool.PREFIX_ID);
        for (String split2 : split) {
            String[] split3 = split2.split(";");
            ArrayList arrayList2 = new ArrayList();
            int i = 0;
            while (true) {
                if (i >= split3.length) {
                    break;
                }
                try {
                    double d = 0.0d;
                    double parseDouble = !TextUtils.isEmpty(split3[i]) ? Double.parseDouble(split3[i]) : 0.0d;
                    i++;
                    if (i >= split3.length) {
                        break;
                    }
                    if (!TextUtils.isEmpty(split3[i])) {
                        d = Double.parseDouble(split3[i]);
                    }
                    GeoPoint geoPoint = new GeoPoint();
                    geoPoint.setLonLat(parseDouble, d);
                    arrayList2.add(geoPoint);
                    i++;
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
            }
            arrayList.add(arrayList2);
        }
        return arrayList;
    }

    static int a(ISearchPoiData iSearchPoiData, String str, GeoPoint geoPoint) {
        int intValue;
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (geoPoint == null) {
            geoPoint = latestPosition;
        }
        int i = -100;
        if (str != null) {
            try {
                if (!str.equals("")) {
                    if ("citycard".equals(iSearchPoiData.getIndustry())) {
                        intValue = (int) (Double.valueOf(str).doubleValue() * 1000.0d);
                    } else {
                        intValue = Double.valueOf(str).intValue();
                        if (intValue == 0) {
                        }
                    }
                    i = intValue;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GeoPoint point = iSearchPoiData.getPoint();
        if (i <= 0 && point != null && (geoPoint.getAdCode() != point.getAdCode() || bcy.a((POI) iSearchPoiData))) {
            int a = (int) bcz.a(geoPoint, point);
            if (a > 0) {
                return a;
            }
        }
        return i;
    }
}
