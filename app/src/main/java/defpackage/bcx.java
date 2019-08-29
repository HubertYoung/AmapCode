package defpackage;

import android.text.TextUtils;
import com.alipay.mobile.antui.screenadpt.AUScreenAdaptTool;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.bundle.entity.infolite.internal.template.PoiButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiDynButtonTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiHtmlTemplate;
import com.autonavi.bundle.entity.infolite.internal.template.PoiSupperAddressTemplate;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.ArrayList;

/* renamed from: bcx reason: default package */
/* compiled from: InternalParserUtils */
public final class bcx {
    public static Double[] a(String str) {
        if (!TextUtils.isEmpty(str) && str.indexOf(44) > 0) {
            String[] split = str.split(",");
            if (split.length == 4) {
                return new Double[]{Double.valueOf(bcv.a(split[0], 0.0d)), Double.valueOf(bcv.a(split[1], 0.0d)), Double.valueOf(bcv.a(split[2], 0.0d)), Double.valueOf(bcv.a(split[3], 0.0d))};
            }
        }
        return null;
    }

    public static ArrayList<ArrayList<GeoPoint>> b(String str) {
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

    public static String c(String str) {
        try {
            int indexOf = str.indexOf(40);
            int indexOf2 = str.indexOf(41);
            if (indexOf > 0 && indexOf2 > 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("");
                sb.append(str.substring(0, indexOf));
                String sb2 = sb.toString();
                StringBuilder sb3 = new StringBuilder();
                sb3.append(sb2);
                sb3.append(str.substring(indexOf2 + 1, str.length()));
                str = sb3.toString();
            }
            int indexOf3 = str.indexOf(65288);
            int indexOf4 = str.indexOf(65289);
            if (indexOf3 > 0 && indexOf4 > 0 && indexOf3 < indexOf4) {
                String substring = str.substring(0, indexOf3);
                StringBuilder sb4 = new StringBuilder();
                sb4.append(substring);
                sb4.append(str.substring(indexOf4 + 1, str.length()));
                str = sb4.toString();
            }
            return str;
        } catch (Exception unused) {
            return "";
        }
    }

    public static void a(SearchPoi searchPoi) {
        if (searchPoi.getTemplateDataMap() != null) {
            if (searchPoi.getTemplateDataMap().containsKey(Integer.valueOf(2004))) {
                PoiLayoutTemplate poiLayoutTemplate = (PoiLayoutTemplate) searchPoi.getTemplateDataMap().get(Integer.valueOf(2004));
                if (poiLayoutTemplate instanceof PoiDynButtonTemplate) {
                    if ("tel".equals(((PoiDynButtonTemplate) poiLayoutTemplate).getAction())) {
                        searchPoi.setPhone(poiLayoutTemplate.getValue());
                    }
                } else if ((poiLayoutTemplate instanceof PoiButtonTemplate) && "tel".equals(((PoiButtonTemplate) poiLayoutTemplate).getAction())) {
                    searchPoi.setPhone(poiLayoutTemplate.getValue());
                }
            }
            if (searchPoi.getTemplateDataMap().containsKey(Integer.valueOf(5001))) {
                PoiLayoutTemplate poiLayoutTemplate2 = (PoiLayoutTemplate) searchPoi.getTemplateDataMap().get(Integer.valueOf(5001));
                if (poiLayoutTemplate2 instanceof PoiButtonTemplate) {
                    if ("tel".equals(((PoiButtonTemplate) poiLayoutTemplate2).getAction())) {
                        searchPoi.setPhone(poiLayoutTemplate2.getValue());
                    }
                } else if ((poiLayoutTemplate2 instanceof PoiDynButtonTemplate) && "tel".equals(((PoiDynButtonTemplate) poiLayoutTemplate2).getAction())) {
                    searchPoi.setPhone(poiLayoutTemplate2.getValue());
                }
            }
            if (searchPoi.getTemplateDataMap().containsKey(Integer.valueOf(2031))) {
                PoiLayoutTemplate poiLayoutTemplate3 = (PoiLayoutTemplate) searchPoi.getTemplateDataMap().get(Integer.valueOf(2031));
                if (poiLayoutTemplate3 instanceof PoiSupperAddressTemplate) {
                    searchPoi.setSuperAddress(((PoiSupperAddressTemplate) poiLayoutTemplate3).getValue());
                }
            }
            if (searchPoi.getTemplateDataMap().containsKey(Integer.valueOf(2009))) {
                PoiLayoutTemplate poiLayoutTemplate4 = (PoiLayoutTemplate) searchPoi.getTemplateDataMap().get(Integer.valueOf(2009));
                if (poiLayoutTemplate4 instanceof PoiHtmlTemplate) {
                    searchPoi.setAddr(((PoiHtmlTemplate) poiLayoutTemplate4).getSpanned().toString());
                }
            }
        }
    }

    public static ArrayList<GeoPoint> d(String str) {
        String[] split;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList<GeoPoint> arrayList = new ArrayList<>(r0);
        for (String str2 : str.split("_")) {
            if (!TextUtils.isEmpty(str2)) {
                String[] split2 = str2.split(",");
                if (split2.length == 2) {
                    try {
                        double d = 0.0d;
                        double parseDouble = !TextUtils.isEmpty(split2[0]) ? Double.parseDouble(split2[0]) : 0.0d;
                        if (!TextUtils.isEmpty(split2[1])) {
                            d = Double.parseDouble(split2[1]);
                        }
                        GeoPoint geoPoint = new GeoPoint();
                        geoPoint.setLonLat(parseDouble, d);
                        arrayList.add(geoPoint);
                    } catch (NumberFormatException e) {
                        kf.a((Throwable) e);
                    }
                }
            }
        }
        return arrayList;
    }

    public static int a(SearchPoi searchPoi, String str, GeoPoint geoPoint) {
        int intValue;
        GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition();
        if (geoPoint == null) {
            geoPoint = latestPosition;
        }
        int i = -100;
        if (str != null) {
            try {
                if (!str.equals("")) {
                    if ("citycard".equals(searchPoi.getIndustry())) {
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
        GeoPoint point = searchPoi.getPoint();
        if (i <= 0 && point != null && (geoPoint.getAdCode() != point.getAdCode() || bcy.a((POI) searchPoi))) {
            int a = (int) bcz.a(geoPoint, point);
            if (a > 0) {
                return a;
            }
        }
        return i;
    }
}
