package defpackage;

import android.graphics.Point;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.rdssecuritysdk.constant.DictionaryKeys;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.bundle.routecommon.entity.BusPath;
import com.autonavi.bundle.routecommon.entity.BusPath.BusDisplayObj;
import com.autonavi.bundle.routecommon.entity.BusPath.TaxiBusPath;
import com.autonavi.bundle.routecommon.entity.BusPath.WalkPath;
import com.autonavi.bundle.routecommon.entity.BusPathSection;
import com.autonavi.bundle.routecommon.entity.BusPathSection.IrregularTime;
import com.autonavi.bundle.routecommon.entity.BusPathSection.SubwayPort;
import com.autonavi.bundle.routecommon.entity.ExTrainPath;
import com.autonavi.bundle.routecommon.entity.ExTrainPath.AlterTrain;
import com.autonavi.bundle.routecommon.entity.ExTrainPath.Station;
import com.autonavi.bundle.routecommon.entity.ExtBusPath;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.entity.OnFootNaviSection;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.miniapp.plugin.map.action.ShowRouteActionProcessor;
import com.autonavi.minimap.route.bus.localbus.RouteBusResultData;
import com.autonavi.minimap.route.bus.model.Bus;
import com.autonavi.minimap.route.bus.model.ExTaxiPath;
import com.autonavi.minimap.route.export.model.IRouteResultData;
import com.autonavi.sync.beans.GirfFavoriteRoute;
import com.sina.weibo.sdk.statistic.LogBuilder;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: dvg reason: default package */
/* compiled from: IBusSaveUtilImpl */
public final class dvg implements atd {

    /* renamed from: dvg$a */
    /* compiled from: IBusSaveUtilImpl */
    static class a {
        static dvg a = new dvg();
    }

    public final void a(Object obj, JSONObject jSONObject) {
        if (obj != null && BusPath.class.isInstance(obj)) {
            BusPath busPath = (BusPath) obj;
            agd.a(jSONObject, (String) "method_time", String.valueOf(busPath.reqStartTime / 1000));
            JSONArray jSONArray = new JSONArray();
            int i = 0;
            while (busPath.mPathSections != null && i < busPath.mPathSections.length) {
                BusPathSection busPathSection = busPath.mPathSections[i];
                JSONObject jSONObject2 = new JSONObject();
                if (busPathSection != null) {
                    agd.a(jSONObject2, (String) "busid", busPathSection.bus_id);
                    agd.a(jSONObject2, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_SECTION_NAME, busPathSection.mSectionName);
                }
                jSONArray.put(jSONObject2);
                i++;
            }
            try {
                jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_BUS_PATH_SECTION, jSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public final String a(Object obj) {
        if (obj == null) {
            return null;
        }
        BusPath busPath = (BusPath) obj;
        JSONObject jSONObject = new JSONObject();
        try {
            agd.a(jSONObject, (String) "reqStartTime", String.valueOf(busPath.reqStartTime));
            agd.a(jSONObject, (String) "mstartX", busPath.mstartX);
            agd.a(jSONObject, (String) "mstartY", busPath.mstartY);
            agd.a(jSONObject, (String) "mTotalLength", busPath.mTotalLength);
            agd.a(jSONObject, (String) "mStartWalkLength", busPath.mStartWalkLength);
            agd.a(jSONObject, (String) "mEndWalkLength", busPath.mEndWalkLength);
            agd.a(jSONObject, (String) "endfoottime", busPath.endfoottime);
            agd.a(jSONObject, (String) "allfootlength", busPath.mAllFootLength);
            agd.a(jSONObject, (String) "taxi_price", busPath.taxi_price);
            agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME, busPath.expenseTime);
            StringBuilder sb = new StringBuilder();
            sb.append(busPath.expense);
            agd.a(jSONObject, (String) "expense", sb.toString());
            agd.a(jSONObject, (String) "totaldriverlength", busPath.totaldriverlength);
            jSONObject.put("endwalk", a(busPath.endwalk));
            agd.a(jSONObject, (String) "mDataLength", busPath.mDataLength);
            if (busPath.mPathSections != null) {
                agd.a(jSONObject, (String) "mSectionNum", busPath.mPathSections.length);
            }
            JSONArray jSONArray = new JSONArray();
            int i = 0;
            while (busPath.mPathSections != null && i < busPath.mPathSections.length) {
                BusPathSection busPathSection = busPath.mPathSections[i];
                JSONObject b = b(busPathSection);
                JSONArray jSONArray2 = new JSONArray();
                if (busPathSection.alter_list != null) {
                    for (BusPathSection a2 : busPathSection.alter_list) {
                        jSONArray2.put(a(a2));
                    }
                }
                b.put("alterlist", jSONArray2);
                jSONArray.put(b);
                i++;
            }
            jSONObject.put(GirfFavoriteRoute.JSON_FIELD_ROUTE_BUS_PATH_SECTION, jSONArray);
            if (busPath.taxiBusPath != null) {
                jSONObject.put(FunctionSupportConfiger.TAXI_TAG, a(busPath.taxiBusPath));
            }
            if (busPath.mEndObj != null) {
                BusDisplayObj busDisplayObj = busPath.mEndObj;
                JSONObject jSONObject2 = new JSONObject();
                agd.a(jSONObject2, (String) "disx", busDisplayObj.mDisX);
                agd.a(jSONObject2, (String) "disy", busDisplayObj.mDisY);
                agd.a(jSONObject2, (String) "distype", busDisplayObj.mDisType);
                agd.a(jSONObject2, (String) "disname", busDisplayObj.mDisName);
                jSONObject.put("endobj", jSONObject2.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jSONObject.toString();
    }

    public final boolean b(Object obj) {
        if (obj == null) {
            return false;
        }
        BusPath busPath = (BusPath) obj;
        if (busPath.mPathSections == null || busPath.mPathSections.length == 0) {
            return false;
        }
        return true;
    }

    public final boolean c(Object obj) {
        Bus bus = (Bus) obj;
        return (bus.coordX == null || bus.coordX.length == 0) ? false : true;
    }

    public final IRouteResultData a(byte[] bArr, POI poi, POI poi2) {
        RouteBusResultData routeBusResultData = new RouteBusResultData();
        routeBusResultData.setFromPOI(poi);
        routeBusResultData.setToPOI(poi2);
        dvz dvz = new dvz(routeBusResultData);
        try {
            dvz.parser(bArr);
        } catch (UnsupportedEncodingException e) {
            kf.a((Throwable) e);
        } catch (JSONException e2) {
            kf.a((Throwable) e2);
        }
        try {
            if (dvz.mDataObject != null) {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("start_poi", bnx.b(poi));
                jSONObject.put("end_poi", bnx.b(poi2));
                dvz.mDataObject.put("res_info", jSONObject);
            }
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        IBusRouteResult iBusRouteResult = dvz.a;
        if (!(iBusRouteResult == null || dvz.mDataObject == null)) {
            iBusRouteResult.setBaseData(dvz.mDataObject.toString().getBytes());
        }
        return iBusRouteResult;
    }

    public final Object a(JSONObject jSONObject) {
        int i;
        JSONObject jSONObject2;
        if (jSONObject == null) {
            return null;
        }
        BusPath busPath = new BusPath();
        busPath.reqStartTime = (long) (Math.max(0.0d, agd.b(jSONObject, "method_time")) * 1000.0d);
        try {
            JSONArray jSONArray = jSONObject.getJSONArray(GirfFavoriteRoute.JSON_FIELD_ROUTE_BUS_PATH_SECTION);
            if (jSONArray != null) {
                busPath.mSectionNum = jSONArray.length();
                i = busPath.mSectionNum;
                busPath.mPathSections = new BusPathSection[i];
            } else {
                i = 0;
            }
            for (int i2 = 0; i2 < i; i2++) {
                try {
                    jSONObject2 = jSONArray.getJSONObject(i2);
                } catch (JSONException e) {
                    e.printStackTrace();
                    jSONObject2 = null;
                }
                if (jSONObject2 != null) {
                    BusPathSection busPathSection = new BusPathSection();
                    busPathSection.bus_id = agd.e(jSONObject2, "busid");
                    busPathSection.mSectionName = agd.e(jSONObject2, GirfFavoriteRoute.JSON_FIELD_ROUTE_SECTION_NAME);
                    busPath.mPathSections[i2] = busPathSection;
                }
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        return busPath;
    }

    public final Object a(JSONObject jSONObject, POI poi, POI poi2) {
        if (jSONObject == null) {
            return null;
        }
        try {
            ExtBusPath extBusPath = new ExtBusPath();
            extBusPath.mFromPoi = poi;
            extBusPath.mToPoi = poi2;
            extBusPath.cost = (double) axr.b(jSONObject, "cost");
            extBusPath.time = axr.b(jSONObject, "time");
            extBusPath.tag = axr.d(jSONObject, "tag");
            extBusPath.distance = axr.b(jSONObject, "distance");
            JSONArray jSONArray = jSONObject.getJSONArray("dataArray");
            if (jSONArray != null) {
                int length = jSONArray.length();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    String e = agd.e(jSONObject2, "pathtype");
                    if (e.equals(ShowRouteActionProcessor.SEARCH_TYPE_BUS)) {
                        extBusPath.getBusPathList().add(c(jSONObject2.getJSONObject("data")));
                    } else if (e.equals("railway")) {
                        extBusPath.getBusPathList().add(d(jSONObject2.getJSONObject("data")));
                    } else if (e.equals(FunctionSupportConfiger.TAXI_TAG)) {
                        JSONObject jSONObject3 = jSONObject2.getJSONObject("data");
                        ArrayList<axb> busPathList = extBusPath.getBusPathList();
                        ExTaxiPath exTaxiPath = new ExTaxiPath();
                        exTaxiPath.length = axr.b(jSONObject3, "length");
                        exTaxiPath.cost = axr.b(jSONObject3, "cost");
                        exTaxiPath.time = axr.b(jSONObject3, "drivetime");
                        exTaxiPath.startpoint = axr.e(jSONObject3, "startpoint");
                        exTaxiPath.endpoint = axr.e(jSONObject3, "endpoint");
                        String[] split = exTaxiPath.startpoint != null ? exTaxiPath.startpoint.split(",") : null;
                        if (split != null && split.length > 0) {
                            Point a2 = cfg.a(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
                            exTaxiPath.startX = a2.x;
                            exTaxiPath.startY = a2.y;
                        }
                        String[] split2 = exTaxiPath.endpoint != null ? exTaxiPath.endpoint.split(",") : null;
                        if (split2 != null && split2.length > 0) {
                            Point a3 = cfg.a(Double.parseDouble(split2[1]), Double.parseDouble(split2[0]));
                            exTaxiPath.endX = a3.x;
                            exTaxiPath.endY = a3.y;
                        }
                        exTaxiPath.mStartName = axr.e(jSONObject3, "startName");
                        exTaxiPath.mEndName = axr.e(jSONObject3, "endName");
                        busPathList.add(exTaxiPath);
                    }
                }
            }
            return extBusPath;
        } catch (Exception e2) {
            kf.a((Throwable) e2);
            return null;
        }
    }

    public final Object b(JSONObject jSONObject) {
        int i;
        if (jSONObject == null) {
            return null;
        }
        try {
            BusPath busPath = new BusPath();
            busPath.reqStartTime = agd.c(jSONObject, "reqStartTime");
            busPath.mstartX = agd.a(jSONObject, "mstartX");
            busPath.mstartY = agd.a(jSONObject, "mstartY");
            busPath.mTotalLength = agd.a(jSONObject, "mTotalLength");
            busPath.mStartWalkLength = agd.a(jSONObject, "mStartWalkLength");
            busPath.mEndWalkLength = agd.a(jSONObject, "mEndWalkLength");
            busPath.endfoottime = agd.a(jSONObject, "endfoottime");
            busPath.mAllFootLength = agd.a(jSONObject, "allfootlength");
            busPath.taxi_price = agd.a(jSONObject, "taxi_price");
            busPath.expenseTime = agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME);
            busPath.expense = (double) agd.a(jSONObject, "expense");
            busPath.mDataLength = agd.a(jSONObject, "mDataLength");
            busPath.totaldriverlength = agd.a(jSONObject, "totaldriverlength");
            busPath.endwalk = b(agd.e(jSONObject, "endwalk"));
            busPath.mSectionNum = agd.a(jSONObject, "mSectionNum");
            busPath.taxiBusPath = a(agd.e(jSONObject, FunctionSupportConfiger.TAXI_TAG));
            busPath.mEndObj = c(agd.e(jSONObject, "endobj"));
            JSONArray jSONArray = jSONObject.getJSONArray(GirfFavoriteRoute.JSON_FIELD_ROUTE_BUS_PATH_SECTION);
            if (jSONArray != null) {
                busPath.mSectionNum = jSONArray.length();
                i = busPath.mSectionNum;
                busPath.mPathSections = new BusPathSection[i];
            } else {
                i = 0;
            }
            for (int i2 = 0; i2 < i; i2++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                BusPathSection e = e(jSONObject2);
                busPath.mPathSections[i2] = e;
                if (jSONObject2.has("alterlist")) {
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("alterlist");
                    if (jSONArray2 != null && jSONArray2.length() > 0) {
                        int length = jSONArray2.length();
                        e.alter_list = new BusPathSection[length];
                        for (int i3 = 0; i3 < length; i3++) {
                            e.alter_list[i3] = a(jSONArray2.getJSONObject(i3), e);
                        }
                    }
                }
            }
            return busPath;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x0136  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(defpackage.bid r10, defpackage.cor r11, int r12) {
        /*
            r9 = this;
            r0 = 2
            r1 = -1
            r2 = 0
            r3 = 0
            r4 = 1
            if (r12 == r0) goto L_0x006b
            r10 = 4
            if (r12 == r10) goto L_0x000c
            goto L_0x0180
        L_0x000c:
            boolean r10 = r11.e()
            if (r10 == 0) goto L_0x0180
            esb r10 = defpackage.esb.a.a
            java.lang.Class<bax> r12 = defpackage.bax.class
            esc r10 = r10.a(r12)
            bax r10 = (defpackage.bax) r10
            if (r10 == 0) goto L_0x0180
            com.autonavi.minimap.route.bus.localbus.RouteBusResultData r12 = new com.autonavi.minimap.route.bus.localbus.RouteBusResultData
            r12.<init>()
            r12.setExtBusResultFlag(r4)
            java.util.ArrayList r0 = r12.getExtBusPathList()
            r0.clear()
            java.util.ArrayList r0 = r12.getExtBusPathList()
            java.lang.Object r4 = r11.d()
            com.autonavi.bundle.routecommon.entity.ExtBusPath r4 = (com.autonavi.bundle.routecommon.entity.ExtBusPath) r4
            r0.add(r4)
            r12.setFocusBusPathIndex(r3)
            r12.setFocusStationIndex(r1)
            com.autonavi.common.model.POI r0 = r11.a()
            com.autonavi.common.model.POI r1 = r11.b()
            java.lang.String r11 = r11.h()
            r12.setBusPathsData(r0, r1, r2, r11)
            com.autonavi.common.PageBundle r11 = new com.autonavi.common.PageBundle
            r11.<init>()
            java.lang.String r0 = "key_type"
            com.autonavi.bundle.routecommon.model.RouteType r1 = com.autonavi.bundle.routecommon.model.RouteType.BUS
            int r1 = r1.getValue()
            r11.putInt(r0, r1)
            java.lang.String r0 = "key_result"
            r11.putObject(r0, r12)
            r10.a(r11)
            goto L_0x0180
        L_0x006b:
            if (r10 == 0) goto L_0x0181
            if (r11 != 0) goto L_0x0071
            goto L_0x0181
        L_0x0071:
            boolean r12 = r11.e()
            if (r12 == 0) goto L_0x00a4
            esb r12 = defpackage.esb.a.a
            java.lang.Class<asy> r0 = defpackage.asy.class
            esc r12 = r12.a(r0)
            asy r12 = (defpackage.asy) r12
            if (r12 == 0) goto L_0x0092
            android.content.Context r0 = r10.getContext()
            java.lang.String r5 = r11.f()
            java.lang.String r12 = r12.a(r0, r5)
            goto L_0x0093
        L_0x0092:
            r12 = r2
        L_0x0093:
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            if (r0 != 0) goto L_0x00a4
            org.json.JSONObject r0 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00a0 }
            r0.<init>(r12)     // Catch:{ JSONException -> 0x00a0 }
            r12 = 0
            goto L_0x00a6
        L_0x00a0:
            r12 = move-exception
            r12.printStackTrace()
        L_0x00a4:
            r0 = r2
            r12 = 1
        L_0x00a6:
            if (r12 == 0) goto L_0x0136
            if (r10 == 0) goto L_0x0135
            if (r11 != 0) goto L_0x00ae
            goto L_0x0135
        L_0x00ae:
            com.autonavi.common.model.POI r10 = r11.a()
            com.autonavi.common.model.POI r11 = r11.b()
            if (r10 == 0) goto L_0x0134
            if (r11 != 0) goto L_0x00bb
            goto L_0x0134
        L_0x00bb:
            java.lang.String r12 = r10.getName()
            java.lang.String r0 = r11.getName()
            com.autonavi.common.model.GeoPoint r1 = r10.getPoint()
            double r1 = r1.getLongitude()
            com.autonavi.common.model.GeoPoint r10 = r10.getPoint()
            double r3 = r10.getLatitude()
            com.autonavi.common.model.GeoPoint r10 = r11.getPoint()
            double r5 = r10.getLongitude()
            com.autonavi.common.model.GeoPoint r10 = r11.getPoint()
            double r10 = r10.getLatitude()
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "amapuri://routePlan/plan?sid=BGVIS1&t=1&dlat="
            r7.<init>(r8)
            r7.append(r10)
            java.lang.String r10 = "&dev=0&dname="
            r7.append(r10)
            r7.append(r0)
            java.lang.String r10 = "&slat="
            r7.append(r10)
            r7.append(r3)
            java.lang.String r10 = "&dlon="
            r7.append(r10)
            r7.append(r5)
            java.lang.String r10 = "&did=BGVIS2&slon="
            r7.append(r10)
            r7.append(r1)
            java.lang.String r10 = "&m=0&sname="
            r7.append(r10)
            r7.append(r12)
            java.lang.String r10 = r7.toString()
            android.content.Intent r11 = new android.content.Intent
            java.lang.String r12 = "android.intent.action.VIEW"
            r11.<init>(r12)
            android.net.Uri r10 = android.net.Uri.parse(r10)
            r11.setData(r10)
            ese r10 = new ese
            r10.<init>(r11)
            esf r11 = defpackage.esf.a()
            r11.a(r10)
            return
        L_0x0134:
            return
        L_0x0135:
            return
        L_0x0136:
            com.autonavi.minimap.route.bus.localbus.RouteBusResultData r12 = new com.autonavi.minimap.route.bus.localbus.RouteBusResultData
            r12.<init>()
            com.autonavi.common.model.POI r2 = r11.a()
            r12.setFromPOI(r2)
            com.autonavi.common.model.POI r2 = r11.b()
            r12.setToPOI(r2)
            java.lang.String r2 = r11.h()
            r12.setMethod(r2)
            r12.parse(r0, r4)     // Catch:{ Exception -> 0x0154 }
            goto L_0x0158
        L_0x0154:
            r0 = move-exception
            r0.printStackTrace()
        L_0x0158:
            r12.setFocusBusPathIndex(r3)
            r12.setFocusStationIndex(r1)
            if (r10 == 0) goto L_0x0180
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r1 = "bundle_key_result"
            r0.putObject(r1, r12)
            java.lang.String r12 = "bundle_key_favorite"
            r0.putBoolean(r12, r4)
            if (r11 == 0) goto L_0x017a
            java.lang.String r12 = "item_key_from_favorites"
            java.lang.String r11 = r11.f()
            r0.putString(r12, r11)
        L_0x017a:
            java.lang.Class<com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailPage> r11 = com.autonavi.minimap.route.bus.navidetail.view.BusNaviDetailPage.class
            r10.startPage(r11, r0)
            return
        L_0x0180:
            return
        L_0x0181:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dvg.a(bid, cor, int):void");
    }

    private static JSONObject a(BusPathSection busPathSection) {
        JSONObject jSONObject = new JSONObject();
        if (busPathSection != null) {
            agd.a(jSONObject, (String) "busid", busPathSection.bus_id);
            agd.a(jSONObject, (String) "busname", busPathSection.mSectionName);
            agd.a(jSONObject, (String) "bustype", busPathSection.mBusType);
            agd.a(jSONObject, (String) LogBuilder.KEY_START_TIME, busPathSection.start_time);
            agd.a(jSONObject, (String) LogBuilder.KEY_END_TIME, busPathSection.end_time);
            agd.a(jSONObject, (String) "stationStartTime", busPathSection.stationStartTime);
            agd.a(jSONObject, (String) "stationEndTime", busPathSection.stationEndTime);
            if (busPathSection.irregulartime != null) {
                try {
                    JSONObject jSONObject2 = new JSONObject();
                    agd.a(jSONObject2, (String) "normalday", busPathSection.irregulartime.normalday);
                    agd.a(jSONObject2, (String) "workday", busPathSection.irregulartime.workday);
                    agd.a(jSONObject2, (String) "holiday", busPathSection.irregulartime.holiday);
                    jSONObject.put("irregulartime", jSONObject2);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            agd.a(jSONObject, (String) "footlength", busPathSection.mFootLength);
            agd.a(jSONObject, (String) "foottime", busPathSection.foot_time);
            agd.a(jSONObject, (String) "endid", busPathSection.end_id);
            agd.a(jSONObject, (String) "startid", busPathSection.start_id);
            agd.a(jSONObject, (String) "stationNum", busPathSection.mStationNum);
            agd.a(jSONObject, (String) "foottime", busPathSection.foot_time);
            agd.a(jSONObject, (String) "driverlength", busPathSection.mDriverLength);
            agd.a(jSONObject, (String) "drivertime", busPathSection.driver_time);
            agd.a(jSONObject, (String) "mRouteColor", busPathSection.mRouteColor);
            agd.a(jSONObject, (String) "mExactSectionName", busPathSection.mExactSectionName);
        }
        return jSONObject;
    }

    private static JSONObject a(SubwayPort subwayPort) {
        if (subwayPort == null || subwayPort.coord == null) {
            return null;
        }
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "name", subwayPort.name);
        StringBuilder sb = new StringBuilder();
        sb.append(subwayPort.coord.x);
        sb.append(",");
        sb.append(subwayPort.coord.y);
        agd.a(jSONObject, (String) "coord", sb.toString());
        return jSONObject;
    }

    private static JSONObject b(BusPathSection busPathSection) {
        JSONObject jSONObject = new JSONObject();
        if (busPathSection != null) {
            try {
                agd.a(jSONObject, (String) "busid", busPathSection.bus_id);
                agd.a(jSONObject, (String) GirfFavoriteRoute.JSON_FIELD_ROUTE_SECTION_NAME, busPathSection.mSectionName);
                agd.a(jSONObject, (String) "busType", busPathSection.mBusType);
                if (busPathSection.subway_inport != null) {
                    jSONObject.put("inport", a(busPathSection.subway_inport));
                }
                if (busPathSection.subway_outport != null) {
                    jSONObject.put("outport", a(busPathSection.subway_outport));
                }
                agd.a(jSONObject, (String) "mStartName", busPathSection.mStartName);
                agd.a(jSONObject, (String) "startid", busPathSection.start_id);
                agd.a(jSONObject, (String) LogBuilder.KEY_START_TIME, busPathSection.start_time);
                agd.a(jSONObject, (String) "mEndName", busPathSection.mEndName);
                agd.a(jSONObject, (String) "stationStartTime", busPathSection.stationStartTime);
                agd.a(jSONObject, (String) "stationEndTime", busPathSection.stationEndTime);
                agd.a(jSONObject, (String) "endid", busPathSection.end_id);
                agd.a(jSONObject, (String) LogBuilder.KEY_END_TIME, busPathSection.end_time);
                if (busPathSection.irregulartime != null) {
                    JSONObject jSONObject2 = new JSONObject();
                    agd.a(jSONObject2, (String) "normalday", busPathSection.irregulartime.normalday);
                    agd.a(jSONObject2, (String) "workday", busPathSection.irregulartime.workday);
                    agd.a(jSONObject2, (String) "holiday", busPathSection.irregulartime.holiday);
                    jSONObject.put("irregulartime", jSONObject2);
                }
                int i = 0;
                int i2 = 1;
                if (!busPathSection.is_night) {
                    i2 = 0;
                }
                agd.a(jSONObject, (String) "night", i2);
                agd.a(jSONObject, (String) "footlength", busPathSection.mFootLength);
                agd.a(jSONObject, (String) "foottime", busPathSection.foot_time);
                agd.a(jSONObject, (String) "driverlength", busPathSection.mDriverLength);
                agd.a(jSONObject, (String) "drivertime", busPathSection.driver_time);
                agd.a(jSONObject, (String) "mStationNum", busPathSection.mStationNum);
                agd.a(jSONObject, (String) "mRouteColor", busPathSection.mRouteColor);
                agd.a(jSONObject, (String) "mExactSectionName", busPathSection.mExactSectionName);
                JSONArray jSONArray = new JSONArray();
                int i3 = 0;
                while (busPathSection.mStations != null && i3 < busPathSection.mStations.length) {
                    JSONObject jSONObject3 = new JSONObject();
                    agd.a(jSONObject3, (String) "name", busPathSection.mStations[i3].mName);
                    agd.a(jSONObject3, (String) DictionaryKeys.CTRLXY_X, busPathSection.mStations[i3].mX);
                    agd.a(jSONObject3, (String) DictionaryKeys.CTRLXY_Y, busPathSection.mStations[i3].mY);
                    agd.a(jSONObject3, (String) "id", busPathSection.mStations[i3].id);
                    jSONArray.put(jSONObject3);
                    i3++;
                }
                jSONObject.put("mStations", jSONArray);
                agd.a(jSONObject, (String) "mPointNum", busPathSection.mPointNum);
                JSONArray jSONArray2 = new JSONArray();
                while (busPathSection.mXs != null && i < busPathSection.mXs.length) {
                    JSONObject jSONObject4 = new JSONObject();
                    agd.a(jSONObject4, (String) DictionaryKeys.CTRLXY_X, busPathSection.mXs[i]);
                    agd.a(jSONObject4, (String) DictionaryKeys.CTRLXY_Y, busPathSection.mYs[i]);
                    jSONArray2.put(jSONObject4);
                    i++;
                }
                jSONObject.put("points", jSONArray2);
                jSONObject.put(ShowRouteActionProcessor.SEARCH_TYPE_WALK, a(busPathSection.walk_path));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    private static JSONObject a(WalkPath walkPath) {
        JSONObject jSONObject = new JSONObject();
        if (walkPath != null) {
            agd.a(jSONObject, (String) "dir", walkPath.dir);
            JSONArray jSONArray = new JSONArray();
            if (walkPath.infolist != null) {
                for (int i = 0; i < walkPath.infolist.size(); i++) {
                    JSONObject jSONObject2 = new JSONObject();
                    OnFootNaviSection onFootNaviSection = walkPath.infolist.get(i);
                    if (onFootNaviSection != null) {
                        agd.a(jSONObject2, (String) "main", onFootNaviSection.mNaviActionStr);
                        agd.a(jSONObject2, (String) "assist", onFootNaviSection.mNaviAssiActionStr);
                        agd.a(jSONObject2, (String) "distance", onFootNaviSection.mPathlength);
                        if (onFootNaviSection.mXs != null && onFootNaviSection.mXs.length > 0) {
                            StringBuilder sb = new StringBuilder();
                            for (int i2 = 0; i2 < onFootNaviSection.mXs.length; i2++) {
                                sb.append(",");
                                sb.append(onFootNaviSection.mXs[i2]);
                                sb.append(",");
                                sb.append(onFootNaviSection.mYs[i2]);
                            }
                            agd.a(jSONObject2, (String) "coord", sb.toString().substring(1));
                        }
                        agd.a(jSONObject2, (String) "road", onFootNaviSection.mStreetName);
                    }
                    jSONArray.put(jSONObject2);
                }
            }
            try {
                jSONObject.put("infolist", jSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jSONObject;
    }

    private static JSONObject a(TaxiBusPath taxiBusPath) {
        JSONObject jSONObject = new JSONObject();
        agd.a(jSONObject, (String) "isstart", taxiBusPath.isStart);
        agd.a(jSONObject, (String) "length", taxiBusPath.mDriveLength);
        agd.a(jSONObject, (String) "cost", taxiBusPath.mCost);
        agd.a(jSONObject, (String) "drivetime", taxiBusPath.mDriveTime);
        agd.a(jSONObject, (String) "startname", taxiBusPath.mStartName);
        agd.a(jSONObject, (String) "endname", taxiBusPath.mEndName);
        StringBuilder sb = new StringBuilder();
        sb.append(taxiBusPath.mstartX);
        sb.append(",");
        sb.append(taxiBusPath.mstartY);
        agd.a(jSONObject, (String) "startpoint", sb.toString());
        StringBuilder sb2 = new StringBuilder();
        sb2.append(taxiBusPath.mendX);
        sb2.append(",");
        sb2.append(taxiBusPath.mendY);
        agd.a(jSONObject, (String) "endpoint", sb2.toString());
        if (taxiBusPath.mXs != null && taxiBusPath.mXs.length > 0) {
            StringBuilder sb3 = new StringBuilder();
            for (int i = 0; i < taxiBusPath.mXs.length; i++) {
                sb3.append(",");
                sb3.append(taxiBusPath.mXs[i]);
                sb3.append(",");
                sb3.append(taxiBusPath.mYs[i]);
            }
            agd.a(jSONObject, (String) "coord", sb3.toString().substring(1));
        }
        return jSONObject;
    }

    private static BusPath c(JSONObject jSONObject) {
        int i;
        if (jSONObject == null) {
            return null;
        }
        try {
            BusPath busPath = new BusPath();
            busPath.mstartX = agd.a(jSONObject, "mstartX");
            busPath.mstartY = agd.a(jSONObject, "mstartY");
            busPath.mTotalLength = agd.a(jSONObject, "mTotalLength");
            busPath.mStartWalkLength = agd.a(jSONObject, "mStartWalkLength");
            busPath.mEndWalkLength = agd.a(jSONObject, "mEndWalkLength");
            busPath.endfoottime = agd.a(jSONObject, "endfoottime");
            busPath.mAllFootLength = agd.a(jSONObject, "allfootlength");
            busPath.taxi_price = agd.a(jSONObject, "taxi_price");
            busPath.expenseTime = agd.a(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_COST_TIME);
            busPath.expense = (double) agd.a(jSONObject, "expense");
            busPath.mDataLength = agd.a(jSONObject, "mDataLength");
            busPath.totaldriverlength = agd.a(jSONObject, "totaldriverlength");
            busPath.endwalk = b(agd.e(jSONObject, "endwalk"));
            busPath.mSectionNum = agd.a(jSONObject, "mSectionNum");
            busPath.taxiBusPath = a(agd.e(jSONObject, FunctionSupportConfiger.TAXI_TAG));
            busPath.mEndObj = c(agd.e(jSONObject, "endobj"));
            JSONArray jSONArray = jSONObject.getJSONArray(GirfFavoriteRoute.JSON_FIELD_ROUTE_BUS_PATH_SECTION);
            if (jSONArray != null) {
                busPath.mSectionNum = jSONArray.length();
                i = busPath.mSectionNum;
                busPath.mPathSections = new BusPathSection[i];
            } else {
                i = 0;
            }
            for (int i2 = 0; i2 < i; i2++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i2);
                BusPathSection e = e(jSONObject2);
                busPath.mPathSections[i2] = e;
                if (jSONObject2.has("alterlist")) {
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("alterlist");
                    if (jSONArray2 != null && jSONArray2.length() > 0) {
                        int length = jSONArray2.length();
                        e.alter_list = new BusPathSection[length];
                        for (int i3 = 0; i3 < length; i3++) {
                            e.alter_list[i3] = a(jSONArray2.getJSONObject(i3), e);
                        }
                    }
                }
            }
            return busPath;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    private static ExTrainPath d(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject;
        ExTrainPath exTrainPath = new ExTrainPath();
        exTrainPath.sstid = axr.e(jSONObject2, "sstid");
        exTrainPath.sad = axr.e(jSONObject2, "sad");
        exTrainPath.tad = axr.e(jSONObject2, "tad");
        exTrainPath.id = axr.e(jSONObject2, "id");
        exTrainPath.trip = axr.e(jSONObject2, "trip");
        exTrainPath.tou = axr.b(jSONObject2, "tou");
        exTrainPath.tst = axr.e(jSONObject2, "tst");
        exTrainPath.type = axr.b(jSONObject2, "type");
        exTrainPath.sin = axr.b(jSONObject2, "sin");
        exTrainPath.sst = axr.e(jSONObject2, "sst");
        exTrainPath.sint = axr.e(jSONObject2, "sint");
        exTrainPath.time = axr.b(jSONObject2, "time");
        exTrainPath.name = axr.e(jSONObject2, "name");
        exTrainPath.tstid = axr.e(jSONObject2, "tstid");
        exTrainPath.tout = axr.e(jSONObject2, "tout");
        exTrainPath.viastid = axr.e(jSONObject2, "viastid");
        exTrainPath.viast = axr.e(jSONObject2, "viast");
        exTrainPath.viawait = axr.e(jSONObject2, "viawait");
        exTrainPath.viaint = axr.e(jSONObject2, "viaint");
        exTrainPath.scord = axr.e(jSONObject2, "scord");
        exTrainPath.tcord = axr.e(jSONObject2, "tcord");
        exTrainPath.viastcord = axr.e(jSONObject2, "viastcord");
        String[] split = exTrainPath.scord.split(Token.SEPARATOR);
        if (split.length > 0) {
            Point a2 = cfg.a(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
            exTrainPath.startX = a2.x;
            exTrainPath.startY = a2.y;
        }
        String[] split2 = exTrainPath.tcord.split(Token.SEPARATOR);
        if (split2.length > 0) {
            Point a3 = cfg.a(Double.parseDouble(split2[1]), Double.parseDouble(split2[0]));
            exTrainPath.endX = a3.x;
            exTrainPath.endY = a3.y;
        }
        String[] split3 = exTrainPath.viastcord.split(Token.SEPARATOR);
        if (split3.length > 0) {
            int length = split3.length / 2;
            String[] split4 = exTrainPath.viastid.split(Token.SEPARATOR);
            String[] split5 = exTrainPath.viast.split(Token.SEPARATOR);
            String[] split6 = exTrainPath.viaint.split(Token.SEPARATOR);
            String[] split7 = exTrainPath.viawait.split(Token.SEPARATOR);
            exTrainPath.mXs = new int[length];
            exTrainPath.mYs = new int[length];
            ArrayList<Station> stationList = exTrainPath.getStationList();
            int i = 0;
            while (i < length) {
                int i2 = i * 2;
                double parseDouble = Double.parseDouble(split3[i2 + 1]);
                double parseDouble2 = Double.parseDouble(split3[i2]);
                Point a4 = cfg.a(parseDouble, parseDouble2);
                String[] strArr = split3;
                exTrainPath.mXs[i] = a4.x;
                exTrainPath.mYs[i] = a4.y;
                try {
                    Station station = new Station();
                    station.id = split4[i];
                    station.name = split5[i];
                    station.time = split6[i];
                    station.wait = Integer.parseInt(split7[i]);
                    station.x = a4.x;
                    station.y = a4.y;
                    station.lat = parseDouble;
                    station.lon = parseDouble2;
                    stationList.add(station);
                } catch (Exception e) {
                    kf.a((Throwable) e);
                }
                i++;
                split3 = strArr;
                JSONObject jSONObject3 = jSONObject;
            }
            int i3 = length + 2;
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            for (int i4 = 0; i4 < iArr.length; i4++) {
                if (i4 == 0) {
                    iArr[i4] = exTrainPath.startX;
                    iArr2[i4] = exTrainPath.startY;
                } else if (i4 == iArr.length - 1) {
                    iArr[i4] = exTrainPath.endX;
                    iArr2[i4] = exTrainPath.endY;
                } else {
                    int i5 = i4 - 1;
                    iArr[i4] = exTrainPath.mXs[i5];
                    iArr2[i4] = exTrainPath.mYs[i5];
                }
            }
            exTrainPath.mXs = iArr;
            exTrainPath.mYs = iArr2;
        }
        JSONObject jSONObject4 = jSONObject;
        if (jSONObject4.has("alter")) {
            try {
                JSONObject jSONObject5 = jSONObject4.getJSONObject("alter");
                String[] split8 = axr.e(jSONObject5, "id").split(Token.SEPARATOR);
                String[] split9 = axr.e(jSONObject5, "name").split(Token.SEPARATOR);
                if (split8.length == split9.length) {
                    for (int i6 = 0; i6 < split8.length; i6++) {
                        AlterTrain alterTrain = new AlterTrain();
                        alterTrain.id = split8[i6];
                        alterTrain.name = split9[i6];
                        exTrainPath.getAlterList().add(alterTrain);
                    }
                }
            } catch (Exception e2) {
                kf.a((Throwable) e2);
            }
        }
        return exTrainPath;
    }

    private static TaxiBusPath a(String str) {
        TaxiBusPath taxiBusPath;
        JSONException e;
        Exception e2;
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            taxiBusPath = new TaxiBusPath();
            try {
                taxiBusPath.isStart = agd.d(jSONObject, "isstart");
                taxiBusPath.mCost = agd.a(jSONObject, "cost");
                taxiBusPath.mDriveLength = agd.a(jSONObject, "length");
                taxiBusPath.mDriveTime = agd.a(jSONObject, "drivetime");
                taxiBusPath.mStartName = agd.e(jSONObject, "startname");
                taxiBusPath.mEndName = agd.e(jSONObject, "endname");
                String e3 = agd.e(jSONObject, "coord");
                if (e3 != null && e3.length() > 0) {
                    String[] split = e3.split(",");
                    if (split != null) {
                        int length = split.length / 2;
                        taxiBusPath.mXs = new int[length];
                        taxiBusPath.mYs = new int[length];
                        for (int i = 0; i < length; i++) {
                            try {
                                int i2 = i * 2;
                                taxiBusPath.mXs[i] = Integer.parseInt(split[i2]);
                                taxiBusPath.mYs[i] = Integer.parseInt(split[i2 + 1]);
                            } catch (NumberFormatException e4) {
                                e4.printStackTrace();
                            }
                        }
                    }
                }
                String[] split2 = agd.e(jSONObject, "startpoint").split(",");
                try {
                    taxiBusPath.mstartX = Integer.parseInt(split2[0]);
                    taxiBusPath.mstartY = Integer.parseInt(split2[1]);
                    String[] split3 = agd.e(jSONObject, "endpoint").split(",");
                    taxiBusPath.mendX = Integer.parseInt(split3[0]);
                    taxiBusPath.mendY = Integer.parseInt(split3[1]);
                } catch (NumberFormatException e5) {
                    e5.printStackTrace();
                }
            } catch (JSONException e6) {
                e = e6;
            } catch (Exception e7) {
                e2 = e7;
                e2.printStackTrace();
                return taxiBusPath;
            }
        } catch (JSONException e8) {
            e = e8;
            taxiBusPath = null;
            e.printStackTrace();
            return taxiBusPath;
        } catch (Exception e9) {
            e2 = e9;
            taxiBusPath = null;
            e2.printStackTrace();
            return taxiBusPath;
        }
        return taxiBusPath;
    }

    private static BusPathSection e(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            BusPathSection busPathSection = new BusPathSection();
            busPathSection.bus_id = agd.e(jSONObject, "busid");
            busPathSection.mBusType = agd.a(jSONObject, "busType");
            busPathSection.mDataLength = agd.a(jSONObject, "mDataLength");
            busPathSection.mSectionName = agd.e(jSONObject, GirfFavoriteRoute.JSON_FIELD_ROUTE_SECTION_NAME);
            busPathSection.mStartName = agd.e(jSONObject, "mStartName");
            busPathSection.mEndName = agd.e(jSONObject, "mEndName");
            busPathSection.start_id = agd.e(jSONObject, "startid");
            busPathSection.end_id = agd.e(jSONObject, "endid");
            busPathSection.start_time = agd.e(jSONObject, LogBuilder.KEY_START_TIME);
            busPathSection.end_time = agd.e(jSONObject, LogBuilder.KEY_END_TIME);
            busPathSection.stationStartTime = agd.e(jSONObject, "stationStartTime");
            busPathSection.stationEndTime = agd.e(jSONObject, "stationEndTime");
            busPathSection.irregulartime = d(axr.e(jSONObject, "irregulartime"));
            busPathSection.is_night = agd.a(jSONObject, "night") != 0;
            busPathSection.mFootLength = agd.a(jSONObject, "footlength");
            busPathSection.foot_time = agd.a(jSONObject, "foottime");
            busPathSection.mDriverLength = agd.a(jSONObject, "mPathLength");
            if (jSONObject.has("driverlength")) {
                busPathSection.mDriverLength = agd.a(jSONObject, "driverlength");
            }
            busPathSection.driver_time = agd.a(jSONObject, "drivertime");
            busPathSection.subway_inport = e(agd.e(jSONObject, "inport"));
            busPathSection.subway_outport = e(agd.e(jSONObject, "outport"));
            busPathSection.mStationNum = agd.a(jSONObject, "mStationNum");
            busPathSection.isNeedRequest = false;
            if (jSONObject.has("mStations")) {
                JSONArray jSONArray = jSONObject.getJSONArray("mStations");
                int length = jSONArray.length();
                busPathSection.mStations = new com.autonavi.bundle.routecommon.entity.Station[length];
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    busPathSection.mStations[i] = new com.autonavi.bundle.routecommon.entity.Station();
                    busPathSection.mStations[i].mName = agd.e(jSONObject2, "name");
                    busPathSection.mStations[i].mX = agd.a(jSONObject2, DictionaryKeys.CTRLXY_X);
                    busPathSection.mStations[i].mY = agd.a(jSONObject2, DictionaryKeys.CTRLXY_Y);
                    busPathSection.mStations[i].id = agd.e(jSONObject2, "id");
                }
            }
            if (jSONObject.has("points")) {
                JSONArray jSONArray2 = jSONObject.getJSONArray("points");
                int length2 = jSONArray2.length();
                busPathSection.mPointNum = jSONArray2.length();
                busPathSection.mXs = new int[length2];
                busPathSection.mYs = new int[length2];
                for (int i2 = 0; i2 < length2; i2++) {
                    JSONObject jSONObject3 = jSONArray2.getJSONObject(i2);
                    busPathSection.mXs[i2] = agd.a(jSONObject3, DictionaryKeys.CTRLXY_X);
                    busPathSection.mYs[i2] = agd.a(jSONObject3, DictionaryKeys.CTRLXY_Y);
                }
            }
            busPathSection.walk_path = b(agd.e(jSONObject, ShowRouteActionProcessor.SEARCH_TYPE_WALK));
            busPathSection.mRouteColor = agd.e(jSONObject, "mRouteColor");
            busPathSection.mExactSectionName = agd.e(jSONObject, "mExactSectionName");
            busPathSection.mTransferType = agd.a(jSONObject, "mTransferType");
            return busPathSection;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static WalkPath b(String str) {
        WalkPath walkPath;
        JSONException e;
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            walkPath = new WalkPath();
            try {
                walkPath.dir = agd.a(jSONObject, "dir");
                JSONArray f = agd.f(jSONObject, "infolist");
                if (f != null && f.length() > 0) {
                    walkPath.infolist = new ArrayList<>();
                    for (int i = 0; i < f.length(); i++) {
                        JSONObject jSONObject2 = f.getJSONObject(i);
                        if (jSONObject2 != null) {
                            OnFootNaviSection onFootNaviSection = new OnFootNaviSection();
                            onFootNaviSection.mNaviActionStr = axr.e(jSONObject2, "main");
                            onFootNaviSection.mNavigtionAction = axr.f(jSONObject2, "main");
                            onFootNaviSection.mNaviAssiActionStr = axr.e(jSONObject2, "assist");
                            onFootNaviSection.mNaviAssiAction = axr.f(jSONObject2, "assist");
                            onFootNaviSection.mPathlength = axr.b(jSONObject2, "distance");
                            onFootNaviSection.mStreetName = axr.e(jSONObject2, "road");
                            String e2 = axr.e(jSONObject2, "coord");
                            if (e2 != null && e2.length() > 0) {
                                String[] split = e2.split(",");
                                int length = split.length / 2;
                                onFootNaviSection.mXs = new int[length];
                                onFootNaviSection.mYs = new int[length];
                                for (int i2 = 0; i2 < length; i2++) {
                                    try {
                                        int i3 = i2 * 2;
                                        onFootNaviSection.mXs[i2] = Integer.parseInt(split[i3]);
                                        onFootNaviSection.mYs[i2] = Integer.parseInt(split[i3 + 1]);
                                    } catch (NumberFormatException e3) {
                                        e3.printStackTrace();
                                    }
                                }
                            }
                            walkPath.infolist.add(onFootNaviSection);
                        }
                    }
                }
            } catch (JSONException e4) {
                e = e4;
                e.printStackTrace();
                return walkPath;
            }
        } catch (JSONException e5) {
            e = e5;
            walkPath = null;
            e.printStackTrace();
            return walkPath;
        }
        return walkPath;
    }

    private static BusDisplayObj c(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        BusDisplayObj busDisplayObj = new BusDisplayObj();
        try {
            JSONObject jSONObject = new JSONObject(str);
            busDisplayObj.mDisX = agd.a(jSONObject, "disx");
            busDisplayObj.mDisY = agd.a(jSONObject, "disy");
            busDisplayObj.mDisType = agd.a(jSONObject, "distype");
            busDisplayObj.mDisName = agd.e(jSONObject, "disname");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return busDisplayObj;
    }

    private static BusPathSection a(JSONObject jSONObject, BusPathSection busPathSection) {
        if (jSONObject == null || busPathSection == null) {
            return null;
        }
        BusPathSection busPathSection2 = new BusPathSection();
        busPathSection2.bus_id = agd.e(jSONObject, "busid");
        busPathSection2.mSectionName = agd.e(jSONObject, "busname");
        busPathSection2.mBusType = agd.a(jSONObject, "bustype");
        if (jSONObject.has("startid")) {
            busPathSection2.start_id = agd.e(jSONObject, "startid");
        } else {
            busPathSection2.start_id = busPathSection.start_id;
        }
        if (jSONObject.has("endid")) {
            busPathSection2.end_id = agd.e(jSONObject, "endid");
        } else {
            busPathSection2.end_id = busPathSection.end_id;
        }
        busPathSection2.mStartName = busPathSection.mStartName;
        busPathSection2.mEndName = busPathSection.mEndName;
        busPathSection2.is_night = busPathSection.is_night;
        busPathSection2.start_time = agd.e(jSONObject, LogBuilder.KEY_START_TIME);
        busPathSection2.end_time = agd.e(jSONObject, LogBuilder.KEY_END_TIME);
        busPathSection2.stationStartTime = agd.e(jSONObject, "stationStartTime");
        busPathSection2.stationEndTime = agd.e(jSONObject, "stationEndTime");
        busPathSection2.irregulartime = d(axr.e(jSONObject, "irregulartime"));
        busPathSection2.mFootLength = agd.a(jSONObject, "footlength");
        busPathSection2.foot_time = agd.a(jSONObject, "foottime");
        busPathSection2.mDriverLength = agd.a(jSONObject, "driverlength");
        busPathSection2.driver_time = agd.a(jSONObject, "drivertime");
        busPathSection2.mRouteColor = agd.e(jSONObject, "mRouteColor");
        busPathSection2.mExactSectionName = agd.e(jSONObject, "mExactSectionName");
        busPathSection2.mTransferType = agd.a(jSONObject, "mTransferType");
        if (jSONObject.has("stationNum")) {
            busPathSection2.mStationNum = agd.a(jSONObject, "stationNum");
        }
        busPathSection2.mStations = new com.autonavi.bundle.routecommon.entity.Station[busPathSection2.mStationNum];
        for (int i = 0; i < busPathSection2.mStationNum; i++) {
            busPathSection2.mStations[i] = new com.autonavi.bundle.routecommon.entity.Station();
        }
        busPathSection2.isNeedRequest = true;
        return busPathSection2;
    }

    private static IrregularTime d(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            IrregularTime irregularTime = new IrregularTime();
            irregularTime.normalday = axr.e(jSONObject, "normalday");
            irregularTime.workday = axr.e(jSONObject, "workday");
            irregularTime.holiday = axr.e(jSONObject, "holiday");
            return irregularTime;
        } catch (JSONException unused) {
            return null;
        }
    }

    private static SubwayPort e(String str) {
        JSONObject jSONObject;
        if (str == null || str.length() == 0) {
            return null;
        }
        try {
            jSONObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
            jSONObject = null;
        }
        if (jSONObject == null) {
            return null;
        }
        SubwayPort subwayPort = new SubwayPort();
        subwayPort.subwayName = agd.e(jSONObject, "buskeyname");
        subwayPort.name = agd.e(jSONObject, "name");
        String e2 = agd.e(jSONObject, "coord");
        if (e2 != null && e2.length() > 0) {
            String[] split = e2.split(",");
            if (split.length > 0) {
                try {
                    subwayPort.coord = new GeoPoint(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                } catch (NumberFormatException e3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(e3.getMessage());
                    AMapLog.e("RouteItemJsonUtils", sb.toString());
                }
            }
        }
        return subwayPort;
    }
}
