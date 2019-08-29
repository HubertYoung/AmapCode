package com.autonavi.common.model;

import android.text.TextUtils;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.location.sdk.fusion.LocationParams;
import com.autonavi.bundle.routecommon.inter.IRouteEditView.State;
import com.autonavi.minimap.basemap.traffic.TrafficUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import org.json.JSONObject;

public class RouteHeaderModel {
    public static final int CHANGED_VALUE_END = 2;
    public static final int CHANGED_VALUE_MID = 4;
    public static final int CHANGED_VALUE_START = 1;
    public static final String ROUTE_HEADER_MODEL_KEY = "route_header_model_key";
    public static final String ROUTE_HEADER_MODEL_KEY_HAS_CHANGED = "route_header_model_key_has_changed";
    public boolean mCanExchange = true;
    public Class mChildPageClass;
    public State mEditStatus;
    public POI mEndPoi;
    public List<POI> mMidPois;
    public POI mStartPoi;
    public int mWidgetId;

    public interface IDealVoiceAddMidPois {
        POI getEndPOI();

        List<POI> getExistingMidPois();

        POI getPOICompany();

        POI getPOIHome();

        POI getStartPOI();

        boolean isAddMidPoisEnable();

        void onSetMidPoisToPage(List<POI> list);
    }

    private static String getMidSummaryDes(int i) {
        switch (i) {
            case 1:
                return "经1地";
            case 2:
                return "经2地";
            case 3:
                return "经3地";
            default:
                return "";
        }
    }

    private static boolean isDefaultValue(double d) {
        return d == -1000.0d;
    }

    private static boolean isDefaultValue(double d, double d2) {
        return d == -1000.0d && d2 == -1000.0d;
    }

    private static boolean isLongLatiValid(double d, double d2) {
        return -180.0d <= d && d <= 180.0d && -90.0d <= d2 && d2 <= 90.0d;
    }

    public static String getMidSummaryDes(List<POI> list) {
        return (list == null || list.size() == 0) ? "" : getMidSummaryDes(list.size());
    }

    public static String getMidSummaryDes(Object[] objArr) {
        return (objArr == null || objArr.length == 0) ? "" : getMidSummaryDes(objArr.length);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002b, code lost:
        r9 = 10001;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int dealVoiceAddMidPois(java.lang.String r9, com.autonavi.common.model.RouteHeaderModel.IDealVoiceAddMidPois r10) {
        /*
            boolean r0 = r10.isAddMidPoisEnable()
            r1 = 10001(0x2711, float:1.4014E-41)
            if (r0 == 0) goto L_0x010b
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ Exception -> 0x0106 }
            r0.<init>()     // Catch:{ Exception -> 0x0106 }
            java.util.List r2 = r10.getExistingMidPois()     // Catch:{ Exception -> 0x0106 }
            if (r2 == 0) goto L_0x0016
            r0.addAll(r2)     // Catch:{ Exception -> 0x0106 }
        L_0x0016:
            org.json.JSONArray r2 = new org.json.JSONArray     // Catch:{ Exception -> 0x0106 }
            r2.<init>(r9)     // Catch:{ Exception -> 0x0106 }
            r9 = 0
            r3 = 0
        L_0x001d:
            int r4 = r2.length()     // Catch:{ Exception -> 0x0106 }
            r5 = 1
            if (r3 >= r4) goto L_0x00ef
            int r4 = r0.size()     // Catch:{ Exception -> 0x0106 }
            r6 = 3
            if (r4 < r6) goto L_0x002f
        L_0x002b:
            r9 = 10001(0x2711, float:1.4014E-41)
            goto L_0x00f4
        L_0x002f:
            org.json.JSONObject r4 = r2.getJSONObject(r3)     // Catch:{ Exception -> 0x0106 }
            java.lang.String r6 = "poiType"
            java.lang.String r6 = r4.optString(r6)     // Catch:{ Exception -> 0x0106 }
            r7 = 0
            java.lang.String r8 = "1"
            boolean r8 = r8.equals(r6)     // Catch:{ Exception -> 0x0106 }
            if (r8 == 0) goto L_0x004c
            com.autonavi.common.model.POI r7 = r10.getPOIHome()     // Catch:{ Exception -> 0x0106 }
            if (r7 != 0) goto L_0x00ac
            r9 = 10012(0x271c, float:1.403E-41)
            goto L_0x00f4
        L_0x004c:
            java.lang.String r8 = "2"
            boolean r8 = r8.equals(r6)     // Catch:{ Exception -> 0x0106 }
            if (r8 == 0) goto L_0x005e
            com.autonavi.common.model.POI r7 = r10.getPOICompany()     // Catch:{ Exception -> 0x0106 }
            if (r7 != 0) goto L_0x00ac
            r9 = 10013(0x271d, float:1.4031E-41)
            goto L_0x00f4
        L_0x005e:
            java.lang.String r8 = "3"
            boolean r8 = r8.equals(r6)     // Catch:{ Exception -> 0x0106 }
            if (r8 == 0) goto L_0x008a
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Exception -> 0x0106 }
            r6 = 5
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition(r6)     // Catch:{ Exception -> 0x0106 }
            if (r4 != 0) goto L_0x0075
            r9 = 10003(0x2713, float:1.4017E-41)
            goto L_0x00f4
        L_0x0075:
            com.autonavi.common.model.POI r7 = com.amap.bundle.datamodel.poi.POIFactory.createPOI()     // Catch:{ Exception -> 0x0106 }
            r7.setPoint(r4)     // Catch:{ Exception -> 0x0106 }
            int r4 = com.autonavi.minimap.R.string.my_location     // Catch:{ Exception -> 0x0106 }
            android.app.Application r6 = com.autonavi.amap.app.AMapAppGlobal.getApplication()     // Catch:{ Exception -> 0x0106 }
            java.lang.String r4 = r6.getString(r4)     // Catch:{ Exception -> 0x0106 }
            r7.setName(r4)     // Catch:{ Exception -> 0x0106 }
            goto L_0x00ac
        L_0x008a:
            java.lang.String r8 = "0"
            boolean r6 = r8.equals(r6)     // Catch:{ Exception -> 0x0106 }
            if (r6 == 0) goto L_0x00ac
            java.util.concurrent.atomic.AtomicReference r6 = new java.util.concurrent.atomic.AtomicReference     // Catch:{ Exception -> 0x0106 }
            java.lang.Integer r7 = java.lang.Integer.valueOf(r1)     // Catch:{ Exception -> 0x0106 }
            r6.<init>(r7)     // Catch:{ Exception -> 0x0106 }
            com.autonavi.common.model.POI r7 = parsePOIModel(r4, r6)     // Catch:{ Exception -> 0x0106 }
            if (r7 != 0) goto L_0x00ac
            java.lang.Object r9 = r6.get()     // Catch:{ Exception -> 0x0106 }
            java.lang.Integer r9 = (java.lang.Integer) r9     // Catch:{ Exception -> 0x0106 }
            int r9 = r9.intValue()     // Catch:{ Exception -> 0x0106 }
            goto L_0x00f4
        L_0x00ac:
            if (r7 != 0) goto L_0x00b0
            goto L_0x002b
        L_0x00b0:
            com.autonavi.common.model.POI r4 = r10.getStartPOI()     // Catch:{ Exception -> 0x0106 }
            com.autonavi.common.model.POI r6 = r10.getEndPOI()     // Catch:{ Exception -> 0x0106 }
            if (r4 == 0) goto L_0x00c0
            boolean r4 = defpackage.bnx.a(r4, r7)     // Catch:{ Exception -> 0x0106 }
            if (r4 != 0) goto L_0x00c8
        L_0x00c0:
            if (r6 == 0) goto L_0x00cb
            boolean r4 = defpackage.bnx.a(r6, r7)     // Catch:{ Exception -> 0x0106 }
            if (r4 == 0) goto L_0x00cb
        L_0x00c8:
            r9 = 10010(0x271a, float:1.4027E-41)
            goto L_0x00f4
        L_0x00cb:
            java.util.Iterator r4 = r0.iterator()     // Catch:{ Exception -> 0x0106 }
        L_0x00cf:
            boolean r6 = r4.hasNext()     // Catch:{ Exception -> 0x0106 }
            if (r6 == 0) goto L_0x00e3
            java.lang.Object r6 = r4.next()     // Catch:{ Exception -> 0x0106 }
            com.autonavi.common.model.POI r6 = (com.autonavi.common.model.POI) r6     // Catch:{ Exception -> 0x0106 }
            boolean r6 = defpackage.bnx.a(r6, r7)     // Catch:{ Exception -> 0x0106 }
            if (r6 == 0) goto L_0x00cf
            r4 = 1
            goto L_0x00e4
        L_0x00e3:
            r4 = 0
        L_0x00e4:
            if (r4 == 0) goto L_0x00e8
            goto L_0x002b
        L_0x00e8:
            r0.add(r7)     // Catch:{ Exception -> 0x0106 }
            int r3 = r3 + 1
            goto L_0x001d
        L_0x00ef:
            r2 = 10020(0x2724, float:1.4041E-41)
            r9 = 10020(0x2724, float:1.4041E-41)
            r5 = 0
        L_0x00f4:
            if (r5 != 0) goto L_0x0104
            int r2 = r0.size()     // Catch:{ Exception -> 0x0106 }
            if (r2 <= 0) goto L_0x0104
            r10.onSetMidPoisToPage(r0)     // Catch:{ Exception -> 0x0106 }
            r9 = 10000(0x2710, float:1.4013E-41)
            r1 = 10000(0x2710, float:1.4013E-41)
            goto L_0x010d
        L_0x0104:
            r1 = r9
            goto L_0x010d
        L_0x0106:
            r9 = move-exception
            r9.printStackTrace()
            goto L_0x010d
        L_0x010b:
            r1 = 9004(0x232c, float:1.2617E-41)
        L_0x010d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.common.model.RouteHeaderModel.dealVoiceAddMidPois(java.lang.String, com.autonavi.common.model.RouteHeaderModel$IDealVoiceAddMidPois):int");
    }

    private static POI parsePOIModel(JSONObject jSONObject, AtomicReference<Integer> atomicReference) {
        if (atomicReference == null) {
            return null;
        }
        atomicReference.set(Integer.valueOf(10001));
        try {
            POI createPOI = POIFactory.createPOI();
            String optString = jSONObject.optString(TrafficUtil.POIID);
            String optString2 = jSONObject.optString("poiName");
            String optString3 = jSONObject.optString(LocationParams.PARA_FLP_AUTONAVI_LON);
            String optString4 = jSONObject.optString("lat");
            String optString5 = jSONObject.optString("entry_lon");
            String optString6 = jSONObject.optString("entry_lat");
            if (!TextUtils.isEmpty(optString2) && !TextUtils.isEmpty(optString3) && !TextUtils.isEmpty(optString4) && !isDefaultValue(Double.parseDouble(optString3))) {
                if (!isDefaultValue(Double.parseDouble(optString4))) {
                    if (!isLongLatiValid(Double.parseDouble(optString3), Double.parseDouble(optString4))) {
                        atomicReference.set(Integer.valueOf(10006));
                        return null;
                    }
                    createPOI.setId(optString);
                    createPOI.setName(optString2);
                    createPOI.getPoint().setLonLat(Double.parseDouble(optString3), Double.parseDouble(optString4));
                    if (!TextUtils.isEmpty(optString5) && !TextUtils.isEmpty(optString6) && !isDefaultValue(Double.parseDouble(optString5), Double.parseDouble(optString6))) {
                        if (!isLongLatiValid(Double.parseDouble(optString5), Double.parseDouble(optString6))) {
                            atomicReference.set(Integer.valueOf(10006));
                            return null;
                        }
                        ArrayList arrayList = new ArrayList();
                        arrayList.add(new GeoPoint(Double.parseDouble(optString5), Double.parseDouble(optString6)));
                        createPOI.setEntranceList(arrayList);
                    }
                    atomicReference.set(Integer.valueOf(10000));
                    return createPOI;
                }
            }
            atomicReference.set(Integer.valueOf(10001));
            return null;
        } catch (Exception unused) {
            atomicReference.set(Integer.valueOf(10001));
            return null;
        }
    }
}
