package defpackage;

/* renamed from: ekq reason: default package */
/* compiled from: PoiDetailNameParser */
public final class ekq {
    /* JADX WARNING: Code restructure failed: missing block: B:195:0x04db, code lost:
        if (r5 > 0) goto L_0x04e9;
     */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x04f9 A[Catch:{ Exception -> 0x027d, Throwable -> 0x0577 }] */
    /* JADX WARNING: Removed duplicated region for block: B:214:0x053a A[Catch:{ Exception -> 0x027d, Throwable -> 0x0577 }] */
    /* JADX WARNING: Removed duplicated region for block: B:216:0x0546 A[Catch:{ Exception -> 0x027d, Throwable -> 0x0577 }] */
    /* JADX WARNING: Removed duplicated region for block: B:217:0x0558 A[Catch:{ Exception -> 0x027d, Throwable -> 0x0577 }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static defpackage.ekr a(org.json.JSONObject r23) {
        /*
            ekr r1 = new ekr
            r1.<init>()
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Throwable -> 0x0577 }
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r3 = "pois"
            r4 = r23
            org.json.JSONArray r3 = r4.optJSONArray(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0576
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0577 }
            if (r4 <= 0) goto L_0x0576
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0577 }
            java.lang.Class<com> r5 = defpackage.com.class
            java.lang.Object r5 = defpackage.ank.a(r5)     // Catch:{ Throwable -> 0x0577 }
            com r5 = (defpackage.com) r5     // Catch:{ Throwable -> 0x0577 }
            if (r5 != 0) goto L_0x002d
            r1 = 0
            return r1
        L_0x002d:
            java.lang.String r6 = r5.a()     // Catch:{ Throwable -> 0x0577 }
            cop r5 = r5.b(r6)     // Catch:{ Throwable -> 0x0577 }
            int r6 = r5.a()     // Catch:{ Throwable -> 0x0577 }
            r7 = 0
            r8 = 0
        L_0x003b:
            if (r8 >= r4) goto L_0x0576
            org.json.JSONObject r9 = r3.getJSONObject(r8)     // Catch:{ Throwable -> 0x0577 }
            java.lang.Class<com.autonavi.minimap.search.model.searchpoi.ISearchPoiData> r10 = com.autonavi.minimap.search.model.searchpoi.ISearchPoiData.class
            com.autonavi.common.model.POI r10 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r10)     // Catch:{ Throwable -> 0x0577 }
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r10 = (com.autonavi.minimap.search.model.searchpoi.ISearchPoiData) r10     // Catch:{ Throwable -> 0x0577 }
            if (r10 == 0) goto L_0x0560
            esb r11 = defpackage.esb.a.a     // Catch:{ Throwable -> 0x0577 }
            java.lang.Class<bck> r12 = defpackage.bck.class
            esc r11 = r11.a(r12)     // Catch:{ Throwable -> 0x0577 }
            bck r11 = (defpackage.bck) r11     // Catch:{ Throwable -> 0x0577 }
            if (r11 == 0) goto L_0x005c
            r11.a(r9, r10)     // Catch:{ Throwable -> 0x0577 }
        L_0x005c:
            java.lang.String r11 = "id"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x0577 }
            r10.setId(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "industry"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x0577 }
            r10.setIndustry(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "f_nona"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setFnona(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "name"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x0577 }
            if (r11 == 0) goto L_0x008a
            java.lang.String r11 = "name"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x0577 }
            r10.setName(r11)     // Catch:{ Throwable -> 0x0577 }
        L_0x008a:
            java.lang.String r11 = "address"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            boolean r12 = android.text.TextUtils.isEmpty(r11)     // Catch:{ Throwable -> 0x0577 }
            if (r12 != 0) goto L_0x009b
            r10.setAddr(r11)     // Catch:{ Throwable -> 0x0577 }
        L_0x009b:
            java.lang.String r11 = "typecode"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x0577 }
            if (r11 == 0) goto L_0x00ac
            java.lang.String r11 = "typecode"
            java.lang.String r11 = r9.optString(r11)     // Catch:{ Throwable -> 0x0577 }
            r10.setType(r11)     // Catch:{ Throwable -> 0x0577 }
        L_0x00ac:
            java.lang.String r11 = "childType"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x0577 }
            if (r11 == 0) goto L_0x00c0
            java.lang.String r11 = "childType"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setChildType(r11)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x00d3
        L_0x00c0:
            java.lang.String r11 = "childtype"
            boolean r11 = r9.has(r11)     // Catch:{ Throwable -> 0x0577 }
            if (r11 == 0) goto L_0x00d3
            java.lang.String r11 = "childtype"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setChildType(r11)     // Catch:{ Throwable -> 0x0577 }
        L_0x00d3:
            java.lang.String r11 = "parent"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setParent(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "towards_angle"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setTowardsAngle(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "end_poi_extension"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setEndPoiExtension(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "transparent"
            java.lang.String r12 = ""
            java.lang.String r11 = r9.optString(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            r10.setTransparent(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "recommend_flag"
            int r11 = r9.optInt(r11, r7)     // Catch:{ Throwable -> 0x0577 }
            r10.setRecommendFlag(r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "entrances"
            org.json.JSONArray r11 = r9.optJSONArray(r11)     // Catch:{ Throwable -> 0x0577 }
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            if (r11 == 0) goto L_0x016d
            int r14 = r11.length()     // Catch:{ Throwable -> 0x0577 }
            if (r14 <= 0) goto L_0x016d
            java.util.ArrayList r14 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0577 }
            int r15 = r11.length()     // Catch:{ Throwable -> 0x0577 }
            r14.<init>(r15)     // Catch:{ Throwable -> 0x0577 }
            r15 = 0
        L_0x0122:
            int r7 = r11.length()     // Catch:{ Throwable -> 0x0577 }
            if (r15 >= r7) goto L_0x0163
            org.json.JSONObject r7 = r11.getJSONObject(r15)     // Catch:{ Throwable -> 0x0577 }
            r16 = r3
            java.lang.String r3 = "longitude"
            r17 = r4
            double r3 = r7.optDouble(r3, r12)     // Catch:{ Throwable -> 0x0577 }
            r18 = r11
            java.lang.String r11 = "latitude"
            r19 = r8
            double r7 = r7.optDouble(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            int r11 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r11 == 0) goto L_0x0158
            int r11 = (r7 > r12 ? 1 : (r7 == r12 ? 0 : -1))
            if (r11 == 0) goto L_0x0158
            android.graphics.Point r3 = defpackage.cfg.a(r7, r3)     // Catch:{ Throwable -> 0x0577 }
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ Throwable -> 0x0577 }
            int r7 = r3.x     // Catch:{ Throwable -> 0x0577 }
            int r3 = r3.y     // Catch:{ Throwable -> 0x0577 }
            r4.<init>(r7, r3)     // Catch:{ Throwable -> 0x0577 }
            r14.add(r4)     // Catch:{ Throwable -> 0x0577 }
        L_0x0158:
            int r15 = r15 + 1
            r3 = r16
            r4 = r17
            r11 = r18
            r8 = r19
            goto L_0x0122
        L_0x0163:
            r16 = r3
            r17 = r4
            r19 = r8
            r10.setEntranceList(r14)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x0173
        L_0x016d:
            r16 = r3
            r17 = r4
            r19 = r8
        L_0x0173:
            java.lang.String r3 = "exits"
            org.json.JSONArray r3 = r9.optJSONArray(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x01cc
            int r4 = r3.length()     // Catch:{ Throwable -> 0x0577 }
            if (r4 <= 0) goto L_0x01cc
            java.util.ArrayList r4 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0577 }
            int r7 = r3.length()     // Catch:{ Throwable -> 0x0577 }
            r4.<init>(r7)     // Catch:{ Throwable -> 0x0577 }
            r7 = 0
        L_0x018b:
            int r8 = r3.length()     // Catch:{ Throwable -> 0x0577 }
            if (r7 >= r8) goto L_0x01c4
            org.json.JSONObject r8 = r3.getJSONObject(r7)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "longitude"
            double r14 = r8.optDouble(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r11 = "latitude"
            r20 = r5
            r21 = r6
            double r5 = r8.optDouble(r11, r12)     // Catch:{ Throwable -> 0x0577 }
            int r8 = (r14 > r12 ? 1 : (r14 == r12 ? 0 : -1))
            if (r8 == 0) goto L_0x01bd
            int r8 = (r5 > r12 ? 1 : (r5 == r12 ? 0 : -1))
            if (r8 == 0) goto L_0x01bd
            android.graphics.Point r5 = defpackage.cfg.a(r5, r14)     // Catch:{ Throwable -> 0x0577 }
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint     // Catch:{ Throwable -> 0x0577 }
            int r8 = r5.x     // Catch:{ Throwable -> 0x0577 }
            int r5 = r5.y     // Catch:{ Throwable -> 0x0577 }
            r6.<init>(r8, r5)     // Catch:{ Throwable -> 0x0577 }
            r4.add(r6)     // Catch:{ Throwable -> 0x0577 }
        L_0x01bd:
            int r7 = r7 + 1
            r5 = r20
            r6 = r21
            goto L_0x018b
        L_0x01c4:
            r20 = r5
            r21 = r6
            r10.setExitList(r4)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x01d0
        L_0x01cc:
            r20 = r5
            r21 = r6
        L_0x01d0:
            java.lang.String r3 = "parkinfo"
            boolean r4 = r9.has(r3)     // Catch:{ Exception -> 0x027d }
            if (r4 == 0) goto L_0x0282
            org.json.JSONObject r4 = r9.getJSONObject(r3)     // Catch:{ Exception -> 0x027d }
            java.util.Iterator r5 = r4.keys()     // Catch:{ Exception -> 0x027d }
            if (r5 == 0) goto L_0x0282
        L_0x01e2:
            boolean r6 = r5.hasNext()     // Catch:{ Exception -> 0x027d }
            if (r6 == 0) goto L_0x0282
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x027d }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x027d }
            java.lang.String r7 = r4.getString(r6)     // Catch:{ Exception -> 0x027d }
            java.util.HashMap r8 = r10.getPoiExtra()     // Catch:{ Exception -> 0x027d }
            java.lang.StringBuilder r11 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x027d }
            r11.<init>()     // Catch:{ Exception -> 0x027d }
            r11.append(r3)     // Catch:{ Exception -> 0x027d }
            java.lang.String r14 = "_"
            r11.append(r14)     // Catch:{ Exception -> 0x027d }
            r11.append(r6)     // Catch:{ Exception -> 0x027d }
            java.lang.String r11 = r11.toString()     // Catch:{ Exception -> 0x027d }
            r8.put(r11, r7)     // Catch:{ Exception -> 0x027d }
            java.lang.String r7 = "inout_info"
            boolean r6 = r7.equals(r6)     // Catch:{ Exception -> 0x027d }
            if (r6 == 0) goto L_0x0279
            java.lang.String r6 = "inout_info"
            org.json.JSONArray r6 = r4.getJSONArray(r6)     // Catch:{ Exception -> 0x027d }
            if (r6 == 0) goto L_0x0279
            r7 = 0
        L_0x021e:
            int r8 = r6.length()     // Catch:{ Exception -> 0x027d }
            if (r7 >= r8) goto L_0x0279
            org.json.JSONObject r8 = r6.getJSONObject(r7)     // Catch:{ Exception -> 0x027d }
            if (r8 == 0) goto L_0x0274
            java.lang.String r11 = "keytype"
            java.lang.String r11 = defpackage.agd.e(r8, r11)     // Catch:{ Exception -> 0x027d }
            java.lang.String r14 = "2"
            boolean r14 = r14.equals(r11)     // Catch:{ Exception -> 0x027d }
            if (r14 != 0) goto L_0x0240
            java.lang.String r14 = "0"
            boolean r11 = r14.equals(r11)     // Catch:{ Exception -> 0x027d }
            if (r11 == 0) goto L_0x0274
        L_0x0240:
            java.lang.String r11 = "x"
            double r14 = r8.getDouble(r11)     // Catch:{ Exception -> 0x026f }
            java.lang.String r11 = "y"
            double r12 = r8.getDouble(r11)     // Catch:{ Exception -> 0x026f }
            android.graphics.Point r8 = defpackage.cfg.a(r12, r14)     // Catch:{ Exception -> 0x026f }
            java.util.ArrayList r11 = r10.getEntranceList()     // Catch:{ Exception -> 0x026f }
            if (r11 != 0) goto L_0x025e
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ Exception -> 0x026f }
            r11.<init>()     // Catch:{ Exception -> 0x026f }
            r10.setEntranceList(r11)     // Catch:{ Exception -> 0x026f }
        L_0x025e:
            java.util.ArrayList r11 = r10.getEntranceList()     // Catch:{ Exception -> 0x026f }
            com.autonavi.common.model.GeoPoint r12 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x026f }
            int r13 = r8.x     // Catch:{ Exception -> 0x026f }
            int r8 = r8.y     // Catch:{ Exception -> 0x026f }
            r12.<init>(r13, r8)     // Catch:{ Exception -> 0x026f }
            r11.add(r12)     // Catch:{ Exception -> 0x026f }
            goto L_0x0274
        L_0x026f:
            r0 = move-exception
            r8 = r0
            defpackage.kf.a(r8)     // Catch:{ Exception -> 0x027d }
        L_0x0274:
            int r7 = r7 + 1
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x021e
        L_0x0279:
            r12 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x01e2
        L_0x027d:
            r0 = move-exception
            r3 = r0
            defpackage.kf.a(r3)     // Catch:{ Throwable -> 0x0577 }
        L_0x0282:
            java.lang.String r3 = "stations"
            boolean r3 = r9.has(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0315
            java.lang.String r3 = "stations"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r3 != 0) goto L_0x0315
            java.lang.String r3 = "stations"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r4 = "null"
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x0577 }
            if (r3 != 0) goto L_0x0315
            java.lang.String r3 = "stations"
            org.json.JSONObject r3 = r9.getJSONObject(r3)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r4 = "businfo_line_keys"
            boolean r4 = r3.has(r4)     // Catch:{ Throwable -> 0x0577 }
            if (r4 == 0) goto L_0x02fc
            java.lang.String r4 = "businfo_line_keys"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r5 = ";|\\|"
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Throwable -> 0x0577 }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x0577 }
            r5.<init>()     // Catch:{ Throwable -> 0x0577 }
            if (r4 == 0) goto L_0x02fc
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Throwable -> 0x0577 }
            r6.<init>()     // Catch:{ Throwable -> 0x0577 }
            r7 = 0
        L_0x02cb:
            int r8 = r4.length     // Catch:{ Throwable -> 0x0577 }
            if (r7 >= r8) goto L_0x02ec
            r8 = r4[r7]     // Catch:{ Throwable -> 0x0577 }
            boolean r8 = r6.containsKey(r8)     // Catch:{ Throwable -> 0x0577 }
            if (r8 != 0) goto L_0x02e9
            r8 = r4[r7]     // Catch:{ Throwable -> 0x0577 }
            r11 = r4[r7]     // Catch:{ Throwable -> 0x0577 }
            r6.put(r8, r11)     // Catch:{ Throwable -> 0x0577 }
            if (r7 == 0) goto L_0x02e4
            java.lang.String r8 = "/"
            r5.append(r8)     // Catch:{ Throwable -> 0x0577 }
        L_0x02e4:
            r8 = r4[r7]     // Catch:{ Throwable -> 0x0577 }
            r5.append(r8)     // Catch:{ Throwable -> 0x0577 }
        L_0x02e9:
            int r7 = r7 + 1
            goto L_0x02cb
        L_0x02ec:
            r6.clear()     // Catch:{ Throwable -> 0x0577 }
            java.util.HashMap r4 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r6 = "station_lines"
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0577 }
            r4.put(r6, r5)     // Catch:{ Throwable -> 0x0577 }
        L_0x02fc:
            java.lang.String r4 = "businfo_lineids"
            boolean r4 = r3.has(r4)     // Catch:{ Throwable -> 0x0577 }
            if (r4 == 0) goto L_0x0315
            java.util.HashMap r4 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r5 = "businfo_lineids"
            java.lang.String r6 = "businfo_lineids"
            java.lang.Object r3 = r3.get(r6)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x0577 }
            r4.put(r5, r3)     // Catch:{ Throwable -> 0x0577 }
        L_0x0315:
            java.lang.String r3 = "child_stations"
            boolean r3 = r9.has(r3)     // Catch:{ Throwable -> 0x0577 }
            r4 = 1
            if (r3 == 0) goto L_0x0476
            java.lang.String r3 = "child_stations"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r3 != 0) goto L_0x0476
            java.lang.String r3 = "child_stations"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r5 = "null"
            boolean r3 = r3.equals(r5)     // Catch:{ Throwable -> 0x0577 }
            if (r3 != 0) goto L_0x0476
            java.lang.String r3 = "child_stations"
            org.json.JSONArray r3 = r9.getJSONArray(r3)     // Catch:{ Throwable -> 0x0577 }
            org.json.JSONArray r5 = new org.json.JSONArray     // Catch:{ Throwable -> 0x0577 }
            r5.<init>()     // Catch:{ Throwable -> 0x0577 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x0577 }
            r6.<init>()     // Catch:{ Throwable -> 0x0577 }
            r7 = 0
        L_0x0349:
            int r8 = r3.length()     // Catch:{ Throwable -> 0x0577 }
            if (r7 >= r8) goto L_0x03f7
            org.json.JSONObject r8 = r3.getJSONObject(r7)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r11 = "businfo_lineids"
            java.lang.String r11 = r8.optString(r11)     // Catch:{ Exception -> 0x03ee }
            r6.append(r11)     // Catch:{ Exception -> 0x03ee }
            int r11 = r3.length()     // Catch:{ Exception -> 0x03ee }
            int r11 = r11 - r4
            if (r7 >= r11) goto L_0x0368
            java.lang.String r11 = ";"
            r6.append(r11)     // Catch:{ Exception -> 0x03ee }
        L_0x0368:
            if (r7 != 0) goto L_0x0372
            java.lang.String r11 = "bus_alias"
            java.lang.String r12 = "A"
            r8.put(r11, r12)     // Catch:{ Exception -> 0x03ee }
            goto L_0x037b
        L_0x0372:
            if (r7 != r4) goto L_0x037b
            java.lang.String r11 = "bus_alias"
            java.lang.String r12 = "B"
            r8.put(r11, r12)     // Catch:{ Exception -> 0x03ee }
        L_0x037b:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r11 = r10.getPoiChildrenInfo()     // Catch:{ Exception -> 0x03ee }
            java.util.List r11 = (java.util.List) r11     // Catch:{ Exception -> 0x03ee }
            if (r11 == 0) goto L_0x03ea
            int r12 = r11.size()     // Catch:{ Exception -> 0x03ee }
            int r13 = r7 + -1
            if (r12 < r13) goto L_0x03ea
            java.lang.Object r11 = r11.get(r7)     // Catch:{ Exception -> 0x03ee }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r11 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r11     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "businfo_line_key"
            java.lang.String r13 = r11.getAddr()     // Catch:{ Exception -> 0x03ee }
            r8.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "x"
            com.autonavi.common.model.GeoPoint r13 = r11.getPoint()     // Catch:{ Exception -> 0x03ee }
            int r13 = r13.x     // Catch:{ Exception -> 0x03ee }
            r8.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "y"
            com.autonavi.common.model.GeoPoint r13 = r11.getPoint()     // Catch:{ Exception -> 0x03ee }
            int r13 = r13.y     // Catch:{ Exception -> 0x03ee }
            r8.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "name"
            java.lang.String r13 = r10.getName()     // Catch:{ Exception -> 0x03ee }
            r8.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "poiid2"
            java.lang.String r13 = r10.getId()     // Catch:{ Exception -> 0x03ee }
            r8.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "poiid"
            java.lang.String r13 = r11.getPoiId()     // Catch:{ Exception -> 0x03ee }
            r8.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "bus_alias"
            java.lang.String r12 = r8.optString(r12)     // Catch:{ Exception -> 0x03ee }
            r11.setBusinfoAlias(r12)     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = r10.getId()     // Catch:{ Exception -> 0x03ee }
            r11.setPoiId2(r12)     // Catch:{ Exception -> 0x03ee }
            java.util.HashMap r11 = r11.getPoiExtra()     // Catch:{ Exception -> 0x03ee }
            java.lang.String r12 = "businfo_lineids"
            java.lang.String r13 = "businfo_lineids"
            java.lang.String r13 = r8.optString(r13)     // Catch:{ Exception -> 0x03ee }
            r11.put(r12, r13)     // Catch:{ Exception -> 0x03ee }
        L_0x03ea:
            r5.put(r7, r8)     // Catch:{ Exception -> 0x03ee }
            goto L_0x03f3
        L_0x03ee:
            r0 = move-exception
            r8 = r0
            r8.printStackTrace()     // Catch:{ Throwable -> 0x0577 }
        L_0x03f3:
            int r7 = r7 + 1
            goto L_0x0349
        L_0x03f7:
            java.lang.String r3 = r6.toString()     // Catch:{ Throwable -> 0x0577 }
            boolean r6 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r6 != 0) goto L_0x040a
            java.util.HashMap r6 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r7 = "businfo_lineids"
            r6.put(r7, r3)     // Catch:{ Throwable -> 0x0577 }
        L_0x040a:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r10.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0476
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r10.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x0577 }
            int r3 = r3.childType     // Catch:{ Throwable -> 0x0577 }
            if (r3 != r4) goto L_0x0476
            java.util.HashMap r3 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r6 = "child_stations"
            java.lang.String r7 = r5.toString()     // Catch:{ Throwable -> 0x0577 }
            r3.put(r6, r7)     // Catch:{ Throwable -> 0x0577 }
            boolean r3 = r10 instanceof java.util.List     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0462
            r3 = r10
            java.util.List r3 = (java.util.List) r3     // Catch:{ Throwable -> 0x0577 }
            r6 = 0
            java.lang.Object r3 = r3.get(r6)     // Catch:{ Throwable -> 0x0577 }
            boolean r3 = r3 instanceof com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0462
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r10.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x0577 }
            java.util.Collection r3 = (java.util.Collection) r3     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0476
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x0577 }
        L_0x0441:
            boolean r6 = r3.hasNext()     // Catch:{ Throwable -> 0x0577 }
            if (r6 == 0) goto L_0x0476
            java.lang.Object r6 = r3.next()     // Catch:{ Throwable -> 0x0577 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r6 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r6     // Catch:{ Throwable -> 0x0577 }
            java.util.HashMap r7 = r6.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r8 = "child_stations"
            java.lang.String r11 = r5.toString()     // Catch:{ Throwable -> 0x0577 }
            r7.put(r8, r11)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r7 = r10.getName()     // Catch:{ Throwable -> 0x0577 }
            r6.setName(r7)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x0441
        L_0x0462:
            boolean r3 = r10 instanceof com.amap.bundle.datamodel.poi.POIBase     // Catch:{ Throwable -> 0x0577 }
            if (r3 == 0) goto L_0x0476
            r3 = r10
            com.amap.bundle.datamodel.poi.POIBase r3 = (com.amap.bundle.datamodel.poi.POIBase) r3     // Catch:{ Throwable -> 0x0577 }
            java.util.HashMap r3 = r3.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r6 = "child_stations"
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x0577 }
            r3.put(r6, r5)     // Catch:{ Throwable -> 0x0577 }
        L_0x0476:
            java.lang.String r3 = "longitude"
            r5 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r7 = r9.optDouble(r3, r5)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r3 = "latitude"
            double r11 = r9.optDouble(r3, r5)     // Catch:{ Throwable -> 0x0577 }
            int r3 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0493
            int r3 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0493
            com.autonavi.common.model.GeoPoint r3 = r10.getPoint()     // Catch:{ Throwable -> 0x0577 }
            r3.setLonLat(r7, r11)     // Catch:{ Throwable -> 0x0577 }
        L_0x0493:
            java.lang.String r3 = "citycode"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            r10.setCityCode(r3)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r3 = "adcode"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            r10.setAdCode(r3)     // Catch:{ Throwable -> 0x0577 }
            r3 = -100
            java.lang.String r5 = "distance"
            java.lang.String r5 = r9.optString(r5)     // Catch:{ Exception -> 0x04e2 }
            if (r5 == 0) goto L_0x04c4
            java.lang.String r6 = ""
            boolean r6 = r5.equals(r6)     // Catch:{ Exception -> 0x04e2 }
            if (r6 == 0) goto L_0x04b8
            goto L_0x04c4
        L_0x04b8:
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ Exception -> 0x04e2 }
            int r5 = r5.intValue()     // Catch:{ Exception -> 0x04e2 }
            if (r5 != 0) goto L_0x04c3
            goto L_0x04c4
        L_0x04c3:
            r3 = r5
        L_0x04c4:
            com.autonavi.common.model.GeoPoint r5 = r10.getPoint()     // Catch:{ Exception -> 0x04de }
            if (r3 > 0) goto L_0x04ea
            if (r5 == 0) goto L_0x04ea
            int r6 = r2.getAdCode()     // Catch:{ Exception -> 0x04de }
            int r7 = r5.getAdCode()     // Catch:{ Exception -> 0x04de }
            if (r6 == r7) goto L_0x04ea
            float r5 = defpackage.cfe.a(r2, r5)     // Catch:{ Exception -> 0x04de }
            int r5 = (int) r5
            if (r5 <= 0) goto L_0x04ea
            goto L_0x04e9
        L_0x04de:
            r0 = move-exception
            r5 = r3
            r3 = r0
            goto L_0x04e6
        L_0x04e2:
            r0 = move-exception
            r3 = r0
            r5 = -100
        L_0x04e6:
            defpackage.kf.a(r3)     // Catch:{ Throwable -> 0x0577 }
        L_0x04e9:
            r3 = r5
        L_0x04ea:
            r10.setDistance(r3)     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r3 = "cpdata"
            java.lang.String r3 = r9.optString(r3)     // Catch:{ Throwable -> 0x0577 }
            boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x0577 }
            if (r5 != 0) goto L_0x053a
            java.lang.String r5 = "\\|"
            java.lang.String[] r3 = r3.split(r5)     // Catch:{ Throwable -> 0x0577 }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Throwable -> 0x0577 }
            r5.<init>()     // Catch:{ Throwable -> 0x0577 }
            r6 = 0
        L_0x0505:
            int r7 = r3.length     // Catch:{ Throwable -> 0x0577 }
            if (r6 >= r7) goto L_0x052b
            r7 = r3[r6]     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r8 = ";"
            java.lang.String[] r7 = r7.split(r8)     // Catch:{ Throwable -> 0x0577 }
            int r8 = r7.length     // Catch:{ Throwable -> 0x0577 }
            if (r8 <= r4) goto L_0x0527
            com.amap.bundle.datamodel.poi.CpData r8 = new com.amap.bundle.datamodel.poi.CpData     // Catch:{ Throwable -> 0x0577 }
            r8.<init>()     // Catch:{ Throwable -> 0x0577 }
            r9 = 0
            r11 = r7[r9]     // Catch:{ Throwable -> 0x0577 }
            r8.setCpid(r11)     // Catch:{ Throwable -> 0x0577 }
            r7 = r7[r4]     // Catch:{ Throwable -> 0x0577 }
            r8.setSource(r7)     // Catch:{ Throwable -> 0x0577 }
            r5.add(r8)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x0528
        L_0x0527:
            r9 = 0
        L_0x0528:
            int r6 = r6 + 1
            goto L_0x0505
        L_0x052b:
            r9 = 0
            java.util.HashMap r3 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r4 = "Cpdata"
            java.lang.String r5 = org.xidea.el.json.JSONEncoder.encode(r5)     // Catch:{ Throwable -> 0x0577 }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x0544
        L_0x053a:
            r9 = 0
            java.util.HashMap r3 = r10.getPoiExtra()     // Catch:{ Throwable -> 0x0577 }
            java.lang.String r4 = "Cpdata"
            r3.remove(r4)     // Catch:{ Throwable -> 0x0577 }
        L_0x0544:
            if (r21 <= 0) goto L_0x0558
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r3 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r3 = r10.as(r3)     // Catch:{ Throwable -> 0x0577 }
            com.amap.bundle.datamodel.FavoritePOI r3 = (com.amap.bundle.datamodel.FavoritePOI) r3     // Catch:{ Throwable -> 0x0577 }
            r4 = r20
            boolean r5 = r4.c(r10)     // Catch:{ Throwable -> 0x0577 }
            r3.setSaved(r5)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x055a
        L_0x0558:
            r4 = r20
        L_0x055a:
            java.util.List<com.autonavi.common.model.POI> r3 = r1.a     // Catch:{ Throwable -> 0x0577 }
            r3.add(r10)     // Catch:{ Throwable -> 0x0577 }
            goto L_0x056a
        L_0x0560:
            r16 = r3
            r17 = r4
            r4 = r5
            r21 = r6
            r19 = r8
            r9 = 0
        L_0x056a:
            int r8 = r19 + 1
            r5 = r4
            r3 = r16
            r4 = r17
            r6 = r21
            r7 = 0
            goto L_0x003b
        L_0x0576:
            return r1
        L_0x0577:
            r0 = move-exception
            r1 = r0
            r1.printStackTrace()
            java.lang.RuntimeException r1 = new java.lang.RuntimeException
            java.lang.String r2 = "PoiDetailNameParser exception.."
            r1.<init>(r2)
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ekq.a(org.json.JSONObject):ekr");
    }
}
