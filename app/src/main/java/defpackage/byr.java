package defpackage;

import com.alipay.mobile.beehive.rpc.RpcConstant;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: byr reason: default package */
/* compiled from: SearchRouteAction */
public class byr extends wb {
    public final boolean b() {
        return true;
    }

    private static SearchPoi a(POI poi, String str) {
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(RpcConstant.BASE)) {
                jSONObject = jSONObject.optJSONObject(RpcConstant.BASE);
            }
            if (jSONObject.has("towards_angle")) {
                searchPoi.setTowardsAngle(jSONObject.optString("towards_angle"));
            }
            if (jSONObject.has("parent")) {
                searchPoi.setParent(jSONObject.optString("parent"));
            }
            if (jSONObject.has("f_nona")) {
                searchPoi.setFnona(jSONObject.optString("f_nona"));
            }
            if (jSONObject.has("childType")) {
                searchPoi.setChildType(jSONObject.optString("childType"));
            } else if (jSONObject.has("childtype")) {
                searchPoi.setChildType(jSONObject.optString("childtype"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchPoi;
    }

    /* JADX WARNING: Removed duplicated region for block: B:53:0x00cf  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void b(org.json.JSONObject r6, defpackage.wa r7) {
        /*
            r5 = this;
            com.amap.bundle.jsadapter.JsAdapter r7 = r5.a()
            if (r7 == 0) goto L_0x0102
            com.autonavi.common.PageBundle r0 = r7.getBundle()
            if (r0 != 0) goto L_0x000e
            goto L_0x0102
        L_0x000e:
            org.json.JSONObject r0 = new org.json.JSONObject
            r0.<init>()
            r1 = 0
            com.autonavi.common.PageBundle r2 = r7.getBundle()     // Catch:{ JSONException -> 0x00b4 }
            if (r2 == 0) goto L_0x0040
            com.autonavi.common.PageBundle r7 = r7.getBundle()     // Catch:{ JSONException -> 0x00b4 }
            java.lang.String r2 = "POI"
            java.lang.Object r7 = r7.getObject(r2)     // Catch:{ JSONException -> 0x00b4 }
            com.autonavi.common.model.POI r7 = (com.autonavi.common.model.POI) r7     // Catch:{ JSONException -> 0x00b4 }
            if (r7 == 0) goto L_0x0040
            java.lang.String r2 = "page_type"
            java.lang.String r3 = r7.getType()     // Catch:{ JSONException -> 0x00b4 }
            r0.put(r2, r3)     // Catch:{ JSONException -> 0x00b4 }
            ccg r2 = defpackage.ccg.a()     // Catch:{ JSONException -> 0x00b4 }
            r3 = 1
            r2.a(r1, r7, r3)     // Catch:{ JSONException -> 0x00b4 }
            r7 = 11100(0x2b5c, float:1.5554E-41)
            r2 = 9
            com.amap.bundle.statistics.LogManager.actionLog(r7, r2, r0)     // Catch:{ JSONException -> 0x00b4 }
        L_0x0040:
            java.lang.String r7 = "type"
            java.lang.String r0 = ""
            java.lang.String r7 = r6.optString(r7, r0)     // Catch:{ JSONException -> 0x00b4 }
            java.lang.String r0 = "car"
            boolean r0 = r7.equalsIgnoreCase(r0)     // Catch:{ JSONException -> 0x00b4 }
            if (r0 == 0) goto L_0x0053
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.CAR     // Catch:{ JSONException -> 0x00b4 }
            goto L_0x006a
        L_0x0053:
            java.lang.String r0 = "bus"
            boolean r0 = r7.equalsIgnoreCase(r0)     // Catch:{ JSONException -> 0x00b4 }
            if (r0 == 0) goto L_0x005e
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.BUS     // Catch:{ JSONException -> 0x00b4 }
            goto L_0x006a
        L_0x005e:
            java.lang.String r0 = "walk"
            boolean r7 = r7.equalsIgnoreCase(r0)     // Catch:{ JSONException -> 0x00b4 }
            if (r7 == 0) goto L_0x0069
            com.autonavi.bundle.routecommon.model.RouteType r7 = com.autonavi.bundle.routecommon.model.RouteType.ONFOOT     // Catch:{ JSONException -> 0x00b4 }
            goto L_0x006a
        L_0x0069:
            r7 = r1
        L_0x006a:
            java.lang.String r0 = "startPoi"
            org.json.JSONObject r0 = r6.optJSONObject(r0)     // Catch:{ JSONException -> 0x00b1 }
            java.lang.String r2 = "endPoi"
            org.json.JSONObject r2 = r6.optJSONObject(r2)     // Catch:{ JSONException -> 0x00b1 }
            if (r0 == 0) goto L_0x008d
            java.lang.String r3 = r0.toString()     // Catch:{ JSONException -> 0x00b1 }
            com.autonavi.common.model.POI r3 = defpackage.kv.a(r3)     // Catch:{ JSONException -> 0x00b1 }
            java.lang.String r0 = r0.toString()     // Catch:{ JSONException -> 0x0089 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r0 = a(r3, r0)     // Catch:{ JSONException -> 0x0089 }
            goto L_0x008e
        L_0x0089:
            r6 = move-exception
            r2 = r1
            r0 = r3
            goto L_0x00b8
        L_0x008d:
            r0 = r1
        L_0x008e:
            if (r2 == 0) goto L_0x00a7
            java.lang.String r3 = r2.toString()     // Catch:{ JSONException -> 0x00a4 }
            com.autonavi.common.model.POI r3 = defpackage.kv.a(r3)     // Catch:{ JSONException -> 0x00a4 }
            java.lang.String r2 = r2.toString()     // Catch:{ JSONException -> 0x00a1 }
            com.autonavi.bundle.entity.common.searchpoi.SearchPoi r2 = a(r3, r2)     // Catch:{ JSONException -> 0x00a1 }
            goto L_0x00a8
        L_0x00a1:
            r6 = move-exception
            r2 = r3
            goto L_0x00b8
        L_0x00a4:
            r6 = move-exception
            r2 = r1
            goto L_0x00b8
        L_0x00a7:
            r2 = r1
        L_0x00a8:
            java.lang.String r3 = "superid"
            java.lang.String r6 = r6.optString(r3)     // Catch:{ JSONException -> 0x00af }
            goto L_0x00bc
        L_0x00af:
            r6 = move-exception
            goto L_0x00b8
        L_0x00b1:
            r6 = move-exception
            r0 = r1
            goto L_0x00b7
        L_0x00b4:
            r6 = move-exception
            r7 = r1
            r0 = r7
        L_0x00b7:
            r2 = r0
        L_0x00b8:
            r6.printStackTrace()
            r6 = r1
        L_0x00bc:
            com.autonavi.common.PageBundle r1 = new com.autonavi.common.PageBundle
            r1.<init>()
            esb r3 = defpackage.esb.a.a
            java.lang.Class<bax> r4 = defpackage.bax.class
            esc r3 = r3.a(r4)
            bax r3 = (defpackage.bax) r3
            if (r3 == 0) goto L_0x0101
            if (r7 == 0) goto L_0x00d6
            java.lang.String r4 = "bundle_key_route_type"
            r1.putObject(r4, r7)
        L_0x00d6:
            java.lang.String r7 = "bundle_key_poi_start"
            r1.putObject(r7, r0)
            java.lang.String r7 = "bundle_key_poi_end"
            r1.putObject(r7, r2)
            boolean r7 = android.text.TextUtils.isEmpty(r6)
            if (r7 != 0) goto L_0x00eb
            java.lang.String r7 = "bundle_key_superid"
            r1.putString(r7, r6)
        L_0x00eb:
            java.lang.String r6 = "bundle_key_auto_route"
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            r1.putObject(r6, r7)
            de.greenrobot.event.EventBus r6 = de.greenrobot.event.EventBus.getDefault()
            cbm r7 = new cbm
            r7.<init>()
            r6.post(r7)
            r3.a(r1)
        L_0x0101:
            return
        L_0x0102:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.byr.b(org.json.JSONObject, wa):void");
    }
}
