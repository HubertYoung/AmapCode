package defpackage;

/* renamed from: bkv reason: default package */
/* compiled from: GetFavoriteMarkAction */
public class bkv extends vz {
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0061 A[Catch:{ JSONException -> 0x0081 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r6, defpackage.wa r7) {
        /*
            r5 = this;
            com.amap.bundle.jsadapter.JsAdapter r0 = r5.a()
            if (r0 == 0) goto L_0x0085
            java.lang.String r1 = "poiInfo"
            org.json.JSONObject r6 = r6.getJSONObject(r1)     // Catch:{ JSONException -> 0x0081 }
            com.amap.bundle.jsadapter.JsAdapter r1 = r5.a()     // Catch:{ JSONException -> 0x0081 }
            com.autonavi.common.PageBundle r1 = r1.getBundle()     // Catch:{ JSONException -> 0x0081 }
            if (r6 == 0) goto L_0x001f
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0081 }
            com.autonavi.common.model.POI r6 = defpackage.kv.a(r6)     // Catch:{ JSONException -> 0x0081 }
            goto L_0x003c
        L_0x001f:
            if (r1 == 0) goto L_0x003b
            java.lang.String r6 = "favorite_poi"
            boolean r6 = r1.containsKey(r6)     // Catch:{ JSONException -> 0x0081 }
            if (r6 == 0) goto L_0x0032
            java.lang.String r6 = "favorite_poi"
            java.lang.Object r6 = r1.getObject(r6)     // Catch:{ JSONException -> 0x0081 }
            com.autonavi.common.model.POI r6 = (com.autonavi.common.model.POI) r6     // Catch:{ JSONException -> 0x0081 }
            goto L_0x003c
        L_0x0032:
            java.lang.String r6 = "POI"
            java.lang.Object r6 = r1.getObject(r6)     // Catch:{ JSONException -> 0x0081 }
            com.autonavi.common.model.POI r6 = (com.autonavi.common.model.POI) r6     // Catch:{ JSONException -> 0x0081 }
            goto L_0x003c
        L_0x003b:
            r6 = 0
        L_0x003c:
            if (r6 == 0) goto L_0x004d
            java.lang.Class<coy> r1 = defpackage.coy.class
            java.lang.Object r1 = defpackage.ank.a(r1)     // Catch:{ JSONException -> 0x0081 }
            coy r1 = (defpackage.coy) r1     // Catch:{ JSONException -> 0x0081 }
            if (r1 == 0) goto L_0x004d
            boolean r1 = r1.a(r6)     // Catch:{ JSONException -> 0x0081 }
            goto L_0x004e
        L_0x004d:
            r1 = 0
        L_0x004e:
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0081 }
            r2.<init>()     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r3 = "_action"
            java.lang.String r4 = r7.b     // Catch:{ JSONException -> 0x0081 }
            r2.put(r3, r4)     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r3 = "status"
            r2.put(r3, r1)     // Catch:{ JSONException -> 0x0081 }
            if (r1 == 0) goto L_0x0077
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r6 = r6.as(r1)     // Catch:{ JSONException -> 0x0081 }
            com.amap.bundle.datamodel.FavoritePOI r6 = (com.amap.bundle.datamodel.FavoritePOI) r6     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r6 = defpackage.bnx.a(r6)     // Catch:{ JSONException -> 0x0081 }
            org.json.JSONObject r1 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0081 }
            r1.<init>(r6)     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r6 = "favInfo"
            r2.put(r6, r1)     // Catch:{ JSONException -> 0x0081 }
        L_0x0077:
            java.lang.String r6 = r7.a     // Catch:{ JSONException -> 0x0081 }
            java.lang.String r7 = r2.toString()     // Catch:{ JSONException -> 0x0081 }
            r0.callJs(r6, r7)     // Catch:{ JSONException -> 0x0081 }
            return
        L_0x0081:
            r6 = move-exception
            defpackage.kf.a(r6)
        L_0x0085:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.bkv.a(org.json.JSONObject, wa):void");
    }
}
