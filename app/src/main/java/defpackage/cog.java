package defpackage;

/* renamed from: cog reason: default package */
/* compiled from: PoiDetailNameParser */
public final class cog {
    /* JADX WARNING: Code restructure failed: missing block: B:196:0x04d8, code lost:
        if (r4 > 0) goto L_0x04e6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:206:0x04f6 A[Catch:{ Exception -> 0x0278, Throwable -> 0x056e }] */
    /* JADX WARNING: Removed duplicated region for block: B:215:0x0537 A[Catch:{ Exception -> 0x0278, Throwable -> 0x056e }] */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x0543 A[Catch:{ Exception -> 0x0278, Throwable -> 0x056e }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.coh a(org.json.JSONObject r21) {
        /*
            r1 = r21
            coh r2 = new coh
            r2.<init>()
            com.autonavi.sdk.location.LocationInstrument r3 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Throwable -> 0x056e }
            com.autonavi.common.model.GeoPoint r3 = r3.getLatestPosition()     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x056c
            java.lang.String r4 = "pois"
            org.json.JSONArray r1 = r1.optJSONArray(r4)     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x056c
            int r4 = r1.length()     // Catch:{ Throwable -> 0x056e }
            if (r4 <= 0) goto L_0x056c
            int r4 = r1.length()     // Catch:{ Throwable -> 0x056e }
            java.lang.Class<com> r5 = defpackage.com.class
            java.lang.Object r5 = defpackage.ank.a(r5)     // Catch:{ Throwable -> 0x056e }
            com r5 = (defpackage.com) r5     // Catch:{ Throwable -> 0x056e }
            if (r5 != 0) goto L_0x002f
            r1 = 0
            return r1
        L_0x002f:
            java.lang.String r6 = r5.a()     // Catch:{ Throwable -> 0x056e }
            cop r5 = r5.b(r6)     // Catch:{ Throwable -> 0x056e }
            int r6 = r5.a()     // Catch:{ Throwable -> 0x056e }
            r7 = 0
            r8 = 0
        L_0x003d:
            if (r8 >= r4) goto L_0x056c
            org.json.JSONObject r9 = r1.getJSONObject(r8)     // Catch:{ Throwable -> 0x056e }
            java.lang.Class<com.autonavi.minimap.search.model.searchpoi.ISearchPoiData> r10 = com.autonavi.minimap.search.model.searchpoi.ISearchPoiData.class
            com.autonavi.common.model.POI r10 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r10)     // Catch:{ Throwable -> 0x056e }
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r10 = (com.autonavi.minimap.search.model.searchpoi.ISearchPoiData) r10     // Catch:{ Throwable -> 0x056e }
            if (r10 == 0) goto L_0x055a
            esb r11 = defpackage.esb.a.a     // Catch:{ Throwable -> 0x056e }
            java.lang.Class<bck> r12 = defpackage.bck.class
            esc r11 = r11.a(r12)     // Catch:{ Throwable -> 0x056e }
            bck r11 = (defpackage.bck) r11     // Catch:{ Throwable -> 0x056e }
            if (r11 == 0) goto L_0x005e
            r11.a(r9, r10)     // Catch:{ Throwable -> 0x056e }
        L_0x005e:
            java.lang.String r11 = "id"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x056e }
            r10.setId(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "industry"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x056e }
            r10.setIndustry(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "f_nona"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setFnona(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "name"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x056e }
            if (r11 == 0) goto L_0x008c
            java.lang.String r11 = "name"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x056e }
            r10.setName(r11)     // Catch:{ Throwable -> 0x056e }
        L_0x008c:
            java.lang.String r11 = "address"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x056e }
            if (r12 != 0) goto L_0x009d
            r10.setAddr(r11)     // Catch:{ Throwable -> 0x056e }
        L_0x009d:
            java.lang.String r11 = "typecode"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x056e }
            if (r11 == 0) goto L_0x00b0
            java.lang.String r11 = "typecode"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x056e }
            r10.setType(r11)     // Catch:{ Throwable -> 0x056e }
        L_0x00b0:
            java.lang.String r11 = "childType"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x056e }
            if (r11 == 0) goto L_0x00c4
            java.lang.String r11 = "childType"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setChildType(r11)     // Catch:{ Throwable -> 0x056e }
            goto L_0x00d7
        L_0x00c4:
            java.lang.String r11 = "childtype"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x056e }
            if (r11 == 0) goto L_0x00d7
            java.lang.String r11 = "childtype"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setChildType(r11)     // Catch:{ Throwable -> 0x056e }
        L_0x00d7:
            java.lang.String r11 = "parent"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setParent(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "towards_angle"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setTowardsAngle(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "end_poi_extension"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setEndPoiExtension(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "transparent"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x056e }
            r10.setTransparent(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "recommend_flag"
            int r11 = r9.optInt(r11, r7)     // Catch:{ Throwable -> 0x056e }
            r10.setRecommendFlag(r11)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "entrances"
            org.json.JSONArray r11 = r9.optJSONArray(r11)     // Catch:{ Throwable -> 0x056e }
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            if (r11 == 0) goto L_0x0175
            int r14 = r11.length()     // Catch:{ Throwable -> 0x056e }
            if (r14 <= 0) goto L_0x0175
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ Throwable -> 0x056e }
            int r15 = r11.length()     // Catch:{ Throwable -> 0x056e }
            r14.<init>(r15)     // Catch:{ Throwable -> 0x056e }
            r15 = 0
        L_0x0128:
            int r7 = r11.length()     // Catch:{ Throwable -> 0x056e }
            if (r15 >= r7) goto L_0x0169
            org.json.JSONObject r7 = r11.getJSONObject(r15)     // Catch:{ Throwable -> 0x056e }
            r16 = r1
            java.lang.String r1 = "longitude"
            r17 = r2
            double r1 = r7.optDouble(r1, r12)     // Catch:{ Throwable -> 0x056e }
            r18 = r4
            java.lang.String r4 = "latitude"
            r19 = r8
            double r7 = r7.optDouble(r4, r12)     // Catch:{ Throwable -> 0x056e }
            int r4 = (r1 > r12 ? 1 : (r1 == r12 ? 0 : -1))
            if (r4 == 0) goto L_0x015e
            int r4 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r4 == 0) goto L_0x015e
            android.graphics.Point r1 = defpackage.cfg.a(r7, r1)     // Catch:{ Throwable -> 0x056e }
            com.autonavi.common.model.GeoPoint r2 = new com.autonavi.common.model.GeoPoint     // Catch:{ Throwable -> 0x056e }
            int r4 = r1.x     // Catch:{ Throwable -> 0x056e }
            int r1 = r1.y     // Catch:{ Throwable -> 0x056e }
            r2.<init>(r4, r1)     // Catch:{ Throwable -> 0x056e }
            r14.add(r2)     // Catch:{ Throwable -> 0x056e }
        L_0x015e:
            int r15 = r15 + 1
            r1 = r16
            r2 = r17
            r4 = r18
            r8 = r19
            goto L_0x0128
        L_0x0169:
            r16 = r1
            r17 = r2
            r18 = r4
            r19 = r8
            r10.setEntranceList(r14)     // Catch:{ Throwable -> 0x056e }
            goto L_0x017d
        L_0x0175:
            r16 = r1
            r17 = r2
            r18 = r4
            r19 = r8
        L_0x017d:
            java.lang.String r1 = "exits"
            org.json.JSONArray r1 = r9.optJSONArray(r1)     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x01c9
            int r2 = r1.length()     // Catch:{ Throwable -> 0x056e }
            if (r2 <= 0) goto L_0x01c9
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ Throwable -> 0x056e }
            int r4 = r1.length()     // Catch:{ Throwable -> 0x056e }
            r2.<init>(r4)     // Catch:{ Throwable -> 0x056e }
            r4 = 0
        L_0x0195:
            int r7 = r1.length()     // Catch:{ Throwable -> 0x056e }
            if (r4 >= r7) goto L_0x01c6
            org.json.JSONObject r7 = r1.getJSONObject(r4)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r8 = "longitude"
            double r14 = r7.optDouble(r8, r12)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r8 = "latitude"
            double r7 = r7.optDouble(r8, r12)     // Catch:{ Throwable -> 0x056e }
            int r11 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r11 == 0) goto L_0x01c3
            int r11 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r11 == 0) goto L_0x01c3
            android.graphics.Point r7 = defpackage.cfg.a(r7, r14)     // Catch:{ Throwable -> 0x056e }
            com.autonavi.common.model.GeoPoint r8 = new com.autonavi.common.model.GeoPoint     // Catch:{ Throwable -> 0x056e }
            int r11 = r7.x     // Catch:{ Throwable -> 0x056e }
            int r7 = r7.y     // Catch:{ Throwable -> 0x056e }
            r8.<init>(r11, r7)     // Catch:{ Throwable -> 0x056e }
            r2.add(r8)     // Catch:{ Throwable -> 0x056e }
        L_0x01c3:
            int r4 = r4 + 1
            goto L_0x0195
        L_0x01c6:
            r10.setExitList(r2)     // Catch:{ Throwable -> 0x056e }
        L_0x01c9:
            java.lang.String r1 = "parkinfo"
            boolean r2 = r9.has(r1)     // Catch:{ Exception -> 0x0278 }
            if (r2 == 0) goto L_0x027d
            org.json.JSONObject r2 = r9.getJSONObject(r1)     // Catch:{ Exception -> 0x0278 }
            java.util.Iterator r4 = r2.keys()     // Catch:{ Exception -> 0x0278 }
            if (r4 == 0) goto L_0x027d
        L_0x01db:
            boolean r7 = r4.hasNext()     // Catch:{ Exception -> 0x0278 }
            if (r7 == 0) goto L_0x027d
            java.lang.Object r7 = r4.next()     // Catch:{ Exception -> 0x0278 }
            java.lang.String r7 = (java.lang.String) r7     // Catch:{ Exception -> 0x0278 }
            java.lang.String r8 = r2.getString(r7)     // Catch:{ Exception -> 0x0278 }
            java.util.HashMap r11 = r10.getPoiExtra()     // Catch:{ Exception -> 0x0278 }
            java.lang.StringBuilder r14 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0278 }
            r14.<init>()     // Catch:{ Exception -> 0x0278 }
            r14.append(r1)     // Catch:{ Exception -> 0x0278 }
            java.lang.String r15 = "_"
            r14.append(r15)     // Catch:{ Exception -> 0x0278 }
            r14.append(r7)     // Catch:{ Exception -> 0x0278 }
            java.lang.String r14 = r14.toString()     // Catch:{ Exception -> 0x0278 }
            r11.put(r14, r8)     // Catch:{ Exception -> 0x0278 }
            java.lang.String r8 = "inout_info"
            boolean r7 = r8.equals(r7)     // Catch:{ Exception -> 0x0278 }
            if (r7 == 0) goto L_0x0274
            java.lang.String r7 = "inout_info"
            org.json.JSONArray r7 = r2.getJSONArray(r7)     // Catch:{ Exception -> 0x0278 }
            if (r7 == 0) goto L_0x0274
            r8 = 0
        L_0x0217:
            int r11 = r7.length()     // Catch:{ Exception -> 0x0278 }
            if (r8 >= r11) goto L_0x0274
            org.json.JSONObject r11 = r7.getJSONObject(r8)     // Catch:{ Exception -> 0x0278 }
            if (r11 == 0) goto L_0x026f
            java.lang.String r14 = "keytype"
            java.lang.String r14 = defpackage.agd.e(r11, r14)     // Catch:{ Exception -> 0x0278 }
            java.lang.String r15 = "2"
            boolean r15 = r15.equals(r14)     // Catch:{ Exception -> 0x0278 }
            if (r15 != 0) goto L_0x0239
            java.lang.String r15 = "0"
            boolean r14 = r15.equals(r14)     // Catch:{ Exception -> 0x0278 }
            if (r14 == 0) goto L_0x026f
        L_0x0239:
            java.lang.String r14 = "x"
            double r14 = r11.getDouble(r14)     // Catch:{ Exception -> 0x026a }
            java.lang.String r12 = "y"
            double r11 = r11.getDouble(r12)     // Catch:{ Exception -> 0x026a }
            android.graphics.Point r11 = defpackage.cfg.a(r11, r14)     // Catch:{ Exception -> 0x026a }
            java.util.ArrayList r12 = r10.getEntranceList()     // Catch:{ Exception -> 0x026a }
            if (r12 != 0) goto L_0x0259
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ Exception -> 0x026a }
            r12.<init>()     // Catch:{ Exception -> 0x026a }
            r10.setEntranceList(r12)     // Catch:{ Exception -> 0x026a }
        L_0x0259:
            java.util.ArrayList r12 = r10.getEntranceList()     // Catch:{ Exception -> 0x026a }
            com.autonavi.common.model.GeoPoint r13 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x026a }
            int r14 = r11.x     // Catch:{ Exception -> 0x026a }
            int r11 = r11.y     // Catch:{ Exception -> 0x026a }
            r13.<init>(r14, r11)     // Catch:{ Exception -> 0x026a }
            r12.add(r13)     // Catch:{ Exception -> 0x026a }
            goto L_0x026f
        L_0x026a:
            r0 = move-exception
            r11 = r0
            defpackage.kf.a(r11)     // Catch:{ Exception -> 0x0278 }
        L_0x026f:
            int r8 = r8 + 1
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x0217
        L_0x0274:
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x01db
        L_0x0278:
            r0 = move-exception
            r1 = r0
            defpackage.kf.a(r1)     // Catch:{ Throwable -> 0x056e }
        L_0x027d:
            java.lang.String r1 = "stations"
            boolean r1 = r9.has(r1)     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x0310
            java.lang.String r1 = "stations"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x056e }
            if (r1 != 0) goto L_0x0310
            java.lang.String r1 = "stations"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r2 = "null"
            boolean r1 = r1.equals(r2)     // Catch:{ Throwable -> 0x056e }
            if (r1 != 0) goto L_0x0310
            java.lang.String r1 = "stations"
            org.json.JSONObject r1 = r9.getJSONObject(r1)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r2 = "businfo_line_keys"
            boolean r2 = r1.has(r2)     // Catch:{ Throwable -> 0x056e }
            if (r2 == 0) goto L_0x02f7
            java.lang.String r2 = "businfo_line_keys"
            java.lang.String r2 = r1.getString(r2)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r4 = ";|\\|"
            java.lang.String[] r2 = r2.split(r4)     // Catch:{ Throwable -> 0x056e }
            java.lang.StringBuffer r4 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x056e }
            r4.<init>()     // Catch:{ Throwable -> 0x056e }
            if (r2 == 0) goto L_0x02f7
            java.util.HashMap r7 = new java.util.HashMap     // Catch:{ Throwable -> 0x056e }
            r7.<init>()     // Catch:{ Throwable -> 0x056e }
            r8 = 0
        L_0x02c6:
            int r11 = r2.length     // Catch:{ Throwable -> 0x056e }
            if (r8 >= r11) goto L_0x02e7
            r11 = r2[r8]     // Catch:{ Throwable -> 0x056e }
            boolean r11 = r7.containsKey(r11)     // Catch:{ Throwable -> 0x056e }
            if (r11 != 0) goto L_0x02e4
            r11 = r2[r8]     // Catch:{ Throwable -> 0x056e }
            r12 = r2[r8]     // Catch:{ Throwable -> 0x056e }
            r7.put(r11, r12)     // Catch:{ Throwable -> 0x056e }
            if (r8 == 0) goto L_0x02df
            java.lang.String r11 = "/"
            r4.append(r11)     // Catch:{ Throwable -> 0x056e }
        L_0x02df:
            r11 = r2[r8]     // Catch:{ Throwable -> 0x056e }
            r4.append(r11)     // Catch:{ Throwable -> 0x056e }
        L_0x02e4:
            int r8 = r8 + 1
            goto L_0x02c6
        L_0x02e7:
            r7.clear()     // Catch:{ Throwable -> 0x056e }
            java.util.HashMap r2 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r7 = "station_lines"
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x056e }
            r2.put(r7, r4)     // Catch:{ Throwable -> 0x056e }
        L_0x02f7:
            java.lang.String r2 = "businfo_lineids"
            boolean r2 = r1.has(r2)     // Catch:{ Throwable -> 0x056e }
            if (r2 == 0) goto L_0x0310
            java.util.HashMap r2 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r4 = "businfo_lineids"
            java.lang.String r7 = "businfo_lineids"
            java.lang.Object r1 = r1.get(r7)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r1 = (java.lang.String) r1     // Catch:{ Throwable -> 0x056e }
            r2.put(r4, r1)     // Catch:{ Throwable -> 0x056e }
        L_0x0310:
            java.lang.String r1 = "child_stations"
            boolean r1 = r9.has(r1)     // Catch:{ Throwable -> 0x056e }
            r2 = 1
            if (r1 == 0) goto L_0x0473
            java.lang.String r1 = "child_stations"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            boolean r1 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x056e }
            if (r1 != 0) goto L_0x0473
            java.lang.String r1 = "child_stations"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r4 = "null"
            boolean r1 = r1.equals(r4)     // Catch:{ Throwable -> 0x056e }
            if (r1 != 0) goto L_0x0473
            java.lang.String r1 = "child_stations"
            org.json.JSONArray r1 = r9.getJSONArray(r1)     // Catch:{ Throwable -> 0x056e }
            org.json.JSONArray r4 = new org.json.JSONArray     // Catch:{ Throwable -> 0x056e }
            r4.<init>()     // Catch:{ Throwable -> 0x056e }
            java.lang.StringBuilder r7 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x056e }
            r7.<init>()     // Catch:{ Throwable -> 0x056e }
            r8 = 0
        L_0x0344:
            int r11 = r1.length()     // Catch:{ Throwable -> 0x056e }
            if (r8 >= r11) goto L_0x03f4
            org.json.JSONObject r11 = r1.getJSONObject(r8)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r12 = "businfo_lineids"
            java.lang.String r12 = r11.optString(r12)     // Catch:{ Exception -> 0x03eb }
            r7.append(r12)     // Catch:{ Exception -> 0x03eb }
            int r12 = r1.length()     // Catch:{ Exception -> 0x03eb }
            int r12 = r12 - r2
            if (r8 >= r12) goto L_0x0363
            java.lang.String r12 = ";"
            r7.append(r12)     // Catch:{ Exception -> 0x03eb }
        L_0x0363:
            if (r8 != 0) goto L_0x036d
            java.lang.String r12 = "bus_alias"
            java.lang.String r13 = "A"
            r11.put(r12, r13)     // Catch:{ Exception -> 0x03eb }
            goto L_0x0376
        L_0x036d:
            if (r8 != r2) goto L_0x0376
            java.lang.String r12 = "bus_alias"
            java.lang.String r13 = "B"
            r11.put(r12, r13)     // Catch:{ Exception -> 0x03eb }
        L_0x0376:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r12 = r10.getPoiChildrenInfo()     // Catch:{ Exception -> 0x03eb }
            java.util.List r12 = (java.util.List) r12     // Catch:{ Exception -> 0x03eb }
            if (r12 == 0) goto L_0x03e7
            int r13 = r12.size()     // Catch:{ Exception -> 0x03eb }
            int r14 = r8 + -1
            if (r13 < r14) goto L_0x03e7
            java.lang.Object r12 = r12.get(r8)     // Catch:{ Exception -> 0x03eb }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r12 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r12     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "businfo_line_key"
            java.lang.String r14 = r12.getAddr()     // Catch:{ Exception -> 0x03eb }
            r11.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "x"
            com.autonavi.common.model.GeoPoint r14 = r12.getPoint()     // Catch:{ Exception -> 0x03eb }
            int r14 = r14.x     // Catch:{ Exception -> 0x03eb }
            r11.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "y"
            com.autonavi.common.model.GeoPoint r14 = r12.getPoint()     // Catch:{ Exception -> 0x03eb }
            int r14 = r14.y     // Catch:{ Exception -> 0x03eb }
            r11.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "name"
            java.lang.String r14 = r10.getName()     // Catch:{ Exception -> 0x03eb }
            r11.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "poiid2"
            java.lang.String r14 = r10.getId()     // Catch:{ Exception -> 0x03eb }
            r11.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "poiid"
            java.lang.String r14 = r12.getPoiId()     // Catch:{ Exception -> 0x03eb }
            r11.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "bus_alias"
            java.lang.String r13 = r11.optString(r13)     // Catch:{ Exception -> 0x03eb }
            r12.setBusinfoAlias(r13)     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = r10.getId()     // Catch:{ Exception -> 0x03eb }
            r12.setPoiId2(r13)     // Catch:{ Exception -> 0x03eb }
            java.util.HashMap r12 = r12.getPoiExtra()     // Catch:{ Exception -> 0x03eb }
            java.lang.String r13 = "businfo_lineids"
            java.lang.String r14 = "businfo_lineids"
            java.lang.String r14 = r11.optString(r14)     // Catch:{ Exception -> 0x03eb }
            r12.put(r13, r14)     // Catch:{ Exception -> 0x03eb }
        L_0x03e7:
            r4.put(r8, r11)     // Catch:{ Exception -> 0x03eb }
            goto L_0x03f0
        L_0x03eb:
            r0 = move-exception
            r11 = r0
            r11.printStackTrace()     // Catch:{ Throwable -> 0x056e }
        L_0x03f0:
            int r8 = r8 + 1
            goto L_0x0344
        L_0x03f4:
            java.lang.String r1 = r7.toString()     // Catch:{ Throwable -> 0x056e }
            boolean r7 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x056e }
            if (r7 != 0) goto L_0x0407
            java.util.HashMap r7 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r8 = "businfo_lineids"
            r7.put(r8, r1)     // Catch:{ Throwable -> 0x056e }
        L_0x0407:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r1 = r10.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x0473
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r1 = r10.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x056e }
            int r1 = r1.childType     // Catch:{ Throwable -> 0x056e }
            if (r1 != r2) goto L_0x0473
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r7 = "child_stations"
            java.lang.String r8 = r4.toString()     // Catch:{ Throwable -> 0x056e }
            r1.put(r7, r8)     // Catch:{ Throwable -> 0x056e }
            boolean r1 = r10 instanceof java.util.List     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x045f
            r1 = r10
            java.util.List r1 = (java.util.List) r1     // Catch:{ Throwable -> 0x056e }
            r7 = 0
            java.lang.Object r1 = r1.get(r7)     // Catch:{ Throwable -> 0x056e }
            boolean r1 = r1 instanceof com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x045f
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r1 = r10.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x056e }
            java.util.Collection r1 = (java.util.Collection) r1     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x0473
            java.util.Iterator r1 = r1.iterator()     // Catch:{ Throwable -> 0x056e }
        L_0x043e:
            boolean r7 = r1.hasNext()     // Catch:{ Throwable -> 0x056e }
            if (r7 == 0) goto L_0x0473
            java.lang.Object r7 = r1.next()     // Catch:{ Throwable -> 0x056e }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r7 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r7     // Catch:{ Throwable -> 0x056e }
            java.util.HashMap r8 = r7.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r11 = "child_stations"
            java.lang.String r12 = r4.toString()     // Catch:{ Throwable -> 0x056e }
            r8.put(r11, r12)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r8 = r10.getName()     // Catch:{ Throwable -> 0x056e }
            r7.setName(r8)     // Catch:{ Throwable -> 0x056e }
            goto L_0x043e
        L_0x045f:
            boolean r1 = r10 instanceof com.amap.bundle.datamodel.poi.POIBase     // Catch:{ Throwable -> 0x056e }
            if (r1 == 0) goto L_0x0473
            r1 = r10
            com.amap.bundle.datamodel.poi.POIBase r1 = (com.amap.bundle.datamodel.poi.POIBase) r1     // Catch:{ Throwable -> 0x056e }
            java.util.HashMap r1 = r1.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r7 = "child_stations"
            java.lang.String r4 = r4.toString()     // Catch:{ Throwable -> 0x056e }
            r1.put(r7, r4)     // Catch:{ Throwable -> 0x056e }
        L_0x0473:
            java.lang.String r1 = "longitude"
            r7 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r11 = r9.optDouble(r1, r7)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r1 = "latitude"
            double r13 = r9.optDouble(r1, r7)     // Catch:{ Throwable -> 0x056e }
            int r1 = (r11 > r7 ? 1 : (r11 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x0490
            int r1 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r1 == 0) goto L_0x0490
            com.autonavi.common.model.GeoPoint r1 = r10.getPoint()     // Catch:{ Throwable -> 0x056e }
            r1.setLonLat(r11, r13)     // Catch:{ Throwable -> 0x056e }
        L_0x0490:
            java.lang.String r1 = "citycode"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            r10.setCityCode(r1)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r1 = "adcode"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            r10.setAdCode(r1)     // Catch:{ Throwable -> 0x056e }
            r1 = -100
            java.lang.String r4 = "distance"
            java.lang.String r4 = r9.optString(r4)     // Catch:{ Exception -> 0x04df }
            if (r4 == 0) goto L_0x04c1
            java.lang.String r7 = ""
            boolean r7 = r4.equals(r7)     // Catch:{ Exception -> 0x04df }
            if (r7 == 0) goto L_0x04b5
            goto L_0x04c1
        L_0x04b5:
            java.lang.Double r4 = java.lang.Double.valueOf(r4)     // Catch:{ Exception -> 0x04df }
            int r4 = r4.intValue()     // Catch:{ Exception -> 0x04df }
            if (r4 != 0) goto L_0x04c0
            goto L_0x04c1
        L_0x04c0:
            r1 = r4
        L_0x04c1:
            com.autonavi.common.model.GeoPoint r4 = r10.getPoint()     // Catch:{ Exception -> 0x04db }
            if (r1 > 0) goto L_0x04e7
            if (r4 == 0) goto L_0x04e7
            int r7 = r3.getAdCode()     // Catch:{ Exception -> 0x04db }
            int r8 = r4.getAdCode()     // Catch:{ Exception -> 0x04db }
            if (r7 == r8) goto L_0x04e7
            float r4 = defpackage.cfe.a(r3, r4)     // Catch:{ Exception -> 0x04db }
            int r4 = (int) r4
            if (r4 <= 0) goto L_0x04e7
            goto L_0x04e6
        L_0x04db:
            r0 = move-exception
            r4 = r1
            r1 = r0
            goto L_0x04e3
        L_0x04df:
            r0 = move-exception
            r1 = r0
            r4 = -100
        L_0x04e3:
            defpackage.kf.a(r1)     // Catch:{ Throwable -> 0x056e }
        L_0x04e6:
            r1 = r4
        L_0x04e7:
            r10.setDistance(r1)     // Catch:{ Throwable -> 0x056e }
            java.lang.String r1 = "cpdata"
            java.lang.String r1 = r9.optString(r1)     // Catch:{ Throwable -> 0x056e }
            boolean r4 = android.text.TextUtils.isEmpty(r1)     // Catch:{ Throwable -> 0x056e }
            if (r4 != 0) goto L_0x0537
            java.lang.String r4 = "\\|"
            java.lang.String[] r1 = r1.split(r4)     // Catch:{ Throwable -> 0x056e }
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Throwable -> 0x056e }
            r4.<init>()     // Catch:{ Throwable -> 0x056e }
            r7 = 0
        L_0x0502:
            int r8 = r1.length     // Catch:{ Throwable -> 0x056e }
            if (r7 >= r8) goto L_0x0528
            r8 = r1[r7]     // Catch:{ Throwable -> 0x056e }
            java.lang.String r9 = ";"
            java.lang.String[] r8 = r8.split(r9)     // Catch:{ Throwable -> 0x056e }
            int r9 = r8.length     // Catch:{ Throwable -> 0x056e }
            if (r9 <= r2) goto L_0x0524
            com.amap.bundle.datamodel.poi.CpData r9 = new com.amap.bundle.datamodel.poi.CpData     // Catch:{ Throwable -> 0x056e }
            r9.<init>()     // Catch:{ Throwable -> 0x056e }
            r11 = 0
            r12 = r8[r11]     // Catch:{ Throwable -> 0x056e }
            r9.setCpid(r12)     // Catch:{ Throwable -> 0x056e }
            r8 = r8[r2]     // Catch:{ Throwable -> 0x056e }
            r9.setSource(r8)     // Catch:{ Throwable -> 0x056e }
            r4.add(r9)     // Catch:{ Throwable -> 0x056e }
            goto L_0x0525
        L_0x0524:
            r11 = 0
        L_0x0525:
            int r7 = r7 + 1
            goto L_0x0502
        L_0x0528:
            r11 = 0
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r2 = "Cpdata"
            java.lang.String r4 = org.xidea.el.json.JSONEncoder.encode(r4)     // Catch:{ Throwable -> 0x056e }
            r1.put(r2, r4)     // Catch:{ Throwable -> 0x056e }
            goto L_0x0541
        L_0x0537:
            r11 = 0
            java.util.HashMap r1 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x056e }
            java.lang.String r2 = "Cpdata"
            r1.remove(r2)     // Catch:{ Throwable -> 0x056e }
        L_0x0541:
            if (r6 <= 0) goto L_0x0552
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r1 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r1 = r10.as(r1)     // Catch:{ Throwable -> 0x056e }
            com.amap.bundle.datamodel.FavoritePOI r1 = (com.amap.bundle.datamodel.FavoritePOI) r1     // Catch:{ Throwable -> 0x056e }
            boolean r2 = r5.c(r10)     // Catch:{ Throwable -> 0x056e }
            r1.setSaved(r2)     // Catch:{ Throwable -> 0x056e }
        L_0x0552:
            r1 = r17
            java.util.List<com.autonavi.common.model.POI> r2 = r1.a     // Catch:{ Throwable -> 0x056e }
            r2.add(r10)     // Catch:{ Throwable -> 0x056e }
            goto L_0x0562
        L_0x055a:
            r16 = r1
            r1 = r2
            r18 = r4
            r19 = r8
            r11 = 0
        L_0x0562:
            int r8 = r19 + 1
            r2 = r1
            r1 = r16
            r4 = r18
            r7 = 0
            goto L_0x003d
        L_0x056c:
            r1 = r2
            return r1
        L_0x056e:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "PoiDetailNameParser exception.."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cog.a(org.json.JSONObject):coh");
    }
}
