package defpackage;

/* renamed from: ccb reason: default package */
/* compiled from: PoiDetailMpsPoiParser */
public final class ccb {
    /* JADX WARNING: Code restructure failed: missing block: B:191:0x04b0, code lost:
        if (r5 > 0) goto L_0x04be;
     */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x04ce A[Catch:{ Exception -> 0x0252, Throwable -> 0x052d }] */
    /* JADX WARNING: Removed duplicated region for block: B:210:0x050f A[Catch:{ Exception -> 0x0252, Throwable -> 0x052d }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.List<com.autonavi.common.model.POI> a(org.json.JSONObject r21) {
        /*
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            com.autonavi.sdk.location.LocationInstrument r2 = com.autonavi.sdk.location.LocationInstrument.getInstance()     // Catch:{ Throwable -> 0x052d }
            com.autonavi.common.model.GeoPoint r2 = r2.getLatestPosition()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r3 = "pois"
            r4 = r21
            org.json.JSONArray r3 = r4.optJSONArray(r3)     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x0532
            int r4 = r3.length()     // Catch:{ Throwable -> 0x052d }
            if (r4 <= 0) goto L_0x0532
            int r4 = r3.length()     // Catch:{ Throwable -> 0x052d }
            r5 = 0
            r6 = 0
        L_0x0023:
            if (r6 >= r4) goto L_0x0532
            org.json.JSONObject r7 = r3.getJSONObject(r6)     // Catch:{ Throwable -> 0x052d }
            java.lang.Class<com.autonavi.minimap.search.model.searchpoi.ISearchPoiData> r8 = com.autonavi.minimap.search.model.searchpoi.ISearchPoiData.class
            com.autonavi.common.model.POI r8 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r8)     // Catch:{ Throwable -> 0x052d }
            com.autonavi.minimap.search.model.searchpoi.ISearchPoiData r8 = (com.autonavi.minimap.search.model.searchpoi.ISearchPoiData) r8     // Catch:{ Throwable -> 0x052d }
            if (r8 == 0) goto L_0x051d
            esb r9 = defpackage.esb.a.a     // Catch:{ Throwable -> 0x052d }
            java.lang.Class<bck> r10 = defpackage.bck.class
            esc r9 = r9.a(r10)     // Catch:{ Throwable -> 0x052d }
            bck r9 = (defpackage.bck) r9     // Catch:{ Throwable -> 0x052d }
            if (r9 == 0) goto L_0x0044
            r9.a(r7, r8)     // Catch:{ Throwable -> 0x052d }
        L_0x0044:
            java.lang.String r9 = "id"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Throwable -> 0x052d }
            r8.setId(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "industry"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Throwable -> 0x052d }
            r8.setIndustry(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "f_nona"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setFnona(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "name"
            boolean r9 = r7.has(r9)     // Catch:{ Throwable -> 0x052d }
            if (r9 == 0) goto L_0x0072
            java.lang.String r9 = "name"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Throwable -> 0x052d }
            r8.setName(r9)     // Catch:{ Throwable -> 0x052d }
        L_0x0072:
            java.lang.String r9 = "address"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            boolean r10 = android.text.TextUtils.isEmpty(r9)     // Catch:{ Throwable -> 0x052d }
            if (r10 != 0) goto L_0x0083
            r8.setAddr(r9)     // Catch:{ Throwable -> 0x052d }
        L_0x0083:
            java.lang.String r9 = "typecode"
            boolean r9 = r7.has(r9)     // Catch:{ Throwable -> 0x052d }
            if (r9 == 0) goto L_0x0094
            java.lang.String r9 = "typecode"
            java.lang.String r9 = r7.optString(r9)     // Catch:{ Throwable -> 0x052d }
            r8.setType(r9)     // Catch:{ Throwable -> 0x052d }
        L_0x0094:
            java.lang.String r9 = "childType"
            boolean r9 = r7.has(r9)     // Catch:{ Throwable -> 0x052d }
            if (r9 == 0) goto L_0x00a8
            java.lang.String r9 = "childType"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setChildType(r9)     // Catch:{ Throwable -> 0x052d }
            goto L_0x00bb
        L_0x00a8:
            java.lang.String r9 = "childtype"
            boolean r9 = r7.has(r9)     // Catch:{ Throwable -> 0x052d }
            if (r9 == 0) goto L_0x00bb
            java.lang.String r9 = "childtype"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setChildType(r9)     // Catch:{ Throwable -> 0x052d }
        L_0x00bb:
            java.lang.String r9 = "parent"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setParent(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "towards_angle"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setTowardsAngle(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "end_poi_extension"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setEndPoiExtension(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "transparent"
            java.lang.String r10 = ""
            java.lang.String r9 = r7.optString(r9, r10)     // Catch:{ Throwable -> 0x052d }
            r8.setTransparent(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "recommend_flag"
            int r9 = r7.optInt(r9, r5)     // Catch:{ Throwable -> 0x052d }
            r8.setRecommendFlag(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "entrances"
            org.json.JSONArray r9 = r7.optJSONArray(r9)     // Catch:{ Throwable -> 0x052d }
            r10 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            if (r9 == 0) goto L_0x0146
            int r12 = r9.length()     // Catch:{ Throwable -> 0x052d }
            if (r12 <= 0) goto L_0x0146
            java.util.ArrayList r12 = new java.util.ArrayList     // Catch:{ Throwable -> 0x052d }
            int r13 = r9.length()     // Catch:{ Throwable -> 0x052d }
            r12.<init>(r13)     // Catch:{ Throwable -> 0x052d }
            r13 = 0
        L_0x010a:
            int r14 = r9.length()     // Catch:{ Throwable -> 0x052d }
            if (r13 >= r14) goto L_0x0140
            org.json.JSONObject r14 = r9.getJSONObject(r13)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r15 = "longitude"
            r16 = r6
            double r5 = r14.optDouble(r15, r10)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r15 = "latitude"
            double r14 = r14.optDouble(r15, r10)     // Catch:{ Throwable -> 0x052d }
            int r17 = (r5 > r10 ? 1 : (r5 == r10 ? 0 : -1))
            if (r17 == 0) goto L_0x013a
            int r17 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r17 == 0) goto L_0x013a
            android.graphics.Point r5 = defpackage.cfg.a(r14, r5)     // Catch:{ Throwable -> 0x052d }
            com.autonavi.common.model.GeoPoint r6 = new com.autonavi.common.model.GeoPoint     // Catch:{ Throwable -> 0x052d }
            int r14 = r5.x     // Catch:{ Throwable -> 0x052d }
            int r5 = r5.y     // Catch:{ Throwable -> 0x052d }
            r6.<init>(r14, r5)     // Catch:{ Throwable -> 0x052d }
            r12.add(r6)     // Catch:{ Throwable -> 0x052d }
        L_0x013a:
            int r13 = r13 + 1
            r6 = r16
            r5 = 0
            goto L_0x010a
        L_0x0140:
            r16 = r6
            r8.setEntranceList(r12)     // Catch:{ Throwable -> 0x052d }
            goto L_0x0148
        L_0x0146:
            r16 = r6
        L_0x0148:
            java.lang.String r5 = "exits"
            org.json.JSONArray r5 = r7.optJSONArray(r5)     // Catch:{ Throwable -> 0x052d }
            if (r5 == 0) goto L_0x01a1
            int r6 = r5.length()     // Catch:{ Throwable -> 0x052d }
            if (r6 <= 0) goto L_0x01a1
            java.util.ArrayList r6 = new java.util.ArrayList     // Catch:{ Throwable -> 0x052d }
            int r9 = r5.length()     // Catch:{ Throwable -> 0x052d }
            r6.<init>(r9)     // Catch:{ Throwable -> 0x052d }
            r9 = 0
        L_0x0160:
            int r12 = r5.length()     // Catch:{ Throwable -> 0x052d }
            if (r9 >= r12) goto L_0x0199
            org.json.JSONObject r12 = r5.getJSONObject(r9)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r13 = "longitude"
            double r13 = r12.optDouble(r13, r10)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r15 = "latitude"
            r18 = r3
            r19 = r4
            double r3 = r12.optDouble(r15, r10)     // Catch:{ Throwable -> 0x052d }
            int r12 = (r13 > r10 ? 1 : (r13 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0192
            int r12 = (r3 > r10 ? 1 : (r3 == r10 ? 0 : -1))
            if (r12 == 0) goto L_0x0192
            android.graphics.Point r3 = defpackage.cfg.a(r3, r13)     // Catch:{ Throwable -> 0x052d }
            com.autonavi.common.model.GeoPoint r4 = new com.autonavi.common.model.GeoPoint     // Catch:{ Throwable -> 0x052d }
            int r12 = r3.x     // Catch:{ Throwable -> 0x052d }
            int r3 = r3.y     // Catch:{ Throwable -> 0x052d }
            r4.<init>(r12, r3)     // Catch:{ Throwable -> 0x052d }
            r6.add(r4)     // Catch:{ Throwable -> 0x052d }
        L_0x0192:
            int r9 = r9 + 1
            r3 = r18
            r4 = r19
            goto L_0x0160
        L_0x0199:
            r18 = r3
            r19 = r4
            r8.setExitList(r6)     // Catch:{ Throwable -> 0x052d }
            goto L_0x01a5
        L_0x01a1:
            r18 = r3
            r19 = r4
        L_0x01a5:
            java.lang.String r3 = "parkinfo"
            boolean r4 = r7.has(r3)     // Catch:{ Exception -> 0x0252 }
            if (r4 == 0) goto L_0x0257
            org.json.JSONObject r4 = r7.getJSONObject(r3)     // Catch:{ Exception -> 0x0252 }
            java.util.Iterator r5 = r4.keys()     // Catch:{ Exception -> 0x0252 }
            if (r5 == 0) goto L_0x0257
        L_0x01b7:
            boolean r6 = r5.hasNext()     // Catch:{ Exception -> 0x0252 }
            if (r6 == 0) goto L_0x0257
            java.lang.Object r6 = r5.next()     // Catch:{ Exception -> 0x0252 }
            java.lang.String r6 = (java.lang.String) r6     // Catch:{ Exception -> 0x0252 }
            java.lang.String r9 = r4.getString(r6)     // Catch:{ Exception -> 0x0252 }
            java.util.HashMap r12 = r8.getPoiExtra()     // Catch:{ Exception -> 0x0252 }
            java.lang.StringBuilder r13 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x0252 }
            r13.<init>()     // Catch:{ Exception -> 0x0252 }
            r13.append(r3)     // Catch:{ Exception -> 0x0252 }
            java.lang.String r14 = "_"
            r13.append(r14)     // Catch:{ Exception -> 0x0252 }
            r13.append(r6)     // Catch:{ Exception -> 0x0252 }
            java.lang.String r13 = r13.toString()     // Catch:{ Exception -> 0x0252 }
            r12.put(r13, r9)     // Catch:{ Exception -> 0x0252 }
            java.lang.String r9 = "inout_info"
            boolean r6 = r9.equals(r6)     // Catch:{ Exception -> 0x0252 }
            if (r6 == 0) goto L_0x024e
            java.lang.String r6 = "inout_info"
            org.json.JSONArray r6 = r4.getJSONArray(r6)     // Catch:{ Exception -> 0x0252 }
            if (r6 == 0) goto L_0x024e
            r9 = 0
        L_0x01f3:
            int r12 = r6.length()     // Catch:{ Exception -> 0x0252 }
            if (r9 >= r12) goto L_0x024e
            org.json.JSONObject r12 = r6.getJSONObject(r9)     // Catch:{ Exception -> 0x0252 }
            if (r12 == 0) goto L_0x0249
            java.lang.String r13 = "keytype"
            java.lang.String r13 = defpackage.agd.e(r12, r13)     // Catch:{ Exception -> 0x0252 }
            java.lang.String r14 = "2"
            boolean r14 = r14.equals(r13)     // Catch:{ Exception -> 0x0252 }
            if (r14 != 0) goto L_0x0215
            java.lang.String r14 = "0"
            boolean r13 = r14.equals(r13)     // Catch:{ Exception -> 0x0252 }
            if (r13 == 0) goto L_0x0249
        L_0x0215:
            java.lang.String r13 = "x"
            double r13 = r12.getDouble(r13)     // Catch:{ Exception -> 0x0244 }
            java.lang.String r15 = "y"
            double r10 = r12.getDouble(r15)     // Catch:{ Exception -> 0x0244 }
            android.graphics.Point r10 = defpackage.cfg.a(r10, r13)     // Catch:{ Exception -> 0x0244 }
            java.util.ArrayList r11 = r8.getEntranceList()     // Catch:{ Exception -> 0x0244 }
            if (r11 != 0) goto L_0x0233
            java.util.ArrayList r11 = new java.util.ArrayList     // Catch:{ Exception -> 0x0244 }
            r11.<init>()     // Catch:{ Exception -> 0x0244 }
            r8.setEntranceList(r11)     // Catch:{ Exception -> 0x0244 }
        L_0x0233:
            java.util.ArrayList r11 = r8.getEntranceList()     // Catch:{ Exception -> 0x0244 }
            com.autonavi.common.model.GeoPoint r12 = new com.autonavi.common.model.GeoPoint     // Catch:{ Exception -> 0x0244 }
            int r13 = r10.x     // Catch:{ Exception -> 0x0244 }
            int r10 = r10.y     // Catch:{ Exception -> 0x0244 }
            r12.<init>(r13, r10)     // Catch:{ Exception -> 0x0244 }
            r11.add(r12)     // Catch:{ Exception -> 0x0244 }
            goto L_0x0249
        L_0x0244:
            r0 = move-exception
            r10 = r0
            defpackage.kf.a(r10)     // Catch:{ Exception -> 0x0252 }
        L_0x0249:
            int r9 = r9 + 1
            r10 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x01f3
        L_0x024e:
            r10 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            goto L_0x01b7
        L_0x0252:
            r0 = move-exception
            r3 = r0
            defpackage.kf.a(r3)     // Catch:{ Throwable -> 0x052d }
        L_0x0257:
            java.lang.String r3 = "stations"
            boolean r3 = r7.has(r3)     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x02ea
            java.lang.String r3 = "stations"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x052d }
            if (r3 != 0) goto L_0x02ea
            java.lang.String r3 = "stations"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r4 = "null"
            boolean r3 = r3.equals(r4)     // Catch:{ Throwable -> 0x052d }
            if (r3 != 0) goto L_0x02ea
            java.lang.String r3 = "stations"
            org.json.JSONObject r3 = r7.getJSONObject(r3)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r4 = "businfo_line_keys"
            boolean r4 = r3.has(r4)     // Catch:{ Throwable -> 0x052d }
            if (r4 == 0) goto L_0x02d1
            java.lang.String r4 = "businfo_line_keys"
            java.lang.String r4 = r3.getString(r4)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r5 = ";|\\|"
            java.lang.String[] r4 = r4.split(r5)     // Catch:{ Throwable -> 0x052d }
            java.lang.StringBuffer r5 = new java.lang.StringBuffer     // Catch:{ Throwable -> 0x052d }
            r5.<init>()     // Catch:{ Throwable -> 0x052d }
            if (r4 == 0) goto L_0x02d1
            java.util.HashMap r6 = new java.util.HashMap     // Catch:{ Throwable -> 0x052d }
            r6.<init>()     // Catch:{ Throwable -> 0x052d }
            r9 = 0
        L_0x02a0:
            int r10 = r4.length     // Catch:{ Throwable -> 0x052d }
            if (r9 >= r10) goto L_0x02c1
            r10 = r4[r9]     // Catch:{ Throwable -> 0x052d }
            boolean r10 = r6.containsKey(r10)     // Catch:{ Throwable -> 0x052d }
            if (r10 != 0) goto L_0x02be
            r10 = r4[r9]     // Catch:{ Throwable -> 0x052d }
            r11 = r4[r9]     // Catch:{ Throwable -> 0x052d }
            r6.put(r10, r11)     // Catch:{ Throwable -> 0x052d }
            if (r9 == 0) goto L_0x02b9
            java.lang.String r10 = "/"
            r5.append(r10)     // Catch:{ Throwable -> 0x052d }
        L_0x02b9:
            r10 = r4[r9]     // Catch:{ Throwable -> 0x052d }
            r5.append(r10)     // Catch:{ Throwable -> 0x052d }
        L_0x02be:
            int r9 = r9 + 1
            goto L_0x02a0
        L_0x02c1:
            r6.clear()     // Catch:{ Throwable -> 0x052d }
            java.util.HashMap r4 = r8.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r6 = "station_lines"
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x052d }
            r4.put(r6, r5)     // Catch:{ Throwable -> 0x052d }
        L_0x02d1:
            java.lang.String r4 = "businfo_lineids"
            boolean r4 = r3.has(r4)     // Catch:{ Throwable -> 0x052d }
            if (r4 == 0) goto L_0x02ea
            java.util.HashMap r4 = r8.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r5 = "businfo_lineids"
            java.lang.String r6 = "businfo_lineids"
            java.lang.Object r3 = r3.get(r6)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r3 = (java.lang.String) r3     // Catch:{ Throwable -> 0x052d }
            r4.put(r5, r3)     // Catch:{ Throwable -> 0x052d }
        L_0x02ea:
            java.lang.String r3 = "child_stations"
            boolean r3 = r7.has(r3)     // Catch:{ Throwable -> 0x052d }
            r4 = 1
            if (r3 == 0) goto L_0x044b
            java.lang.String r3 = "child_stations"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            boolean r3 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x052d }
            if (r3 != 0) goto L_0x044b
            java.lang.String r3 = "child_stations"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r5 = "null"
            boolean r3 = r3.equals(r5)     // Catch:{ Throwable -> 0x052d }
            if (r3 != 0) goto L_0x044b
            java.lang.String r3 = "child_stations"
            org.json.JSONArray r3 = r7.getJSONArray(r3)     // Catch:{ Throwable -> 0x052d }
            org.json.JSONArray r5 = new org.json.JSONArray     // Catch:{ Throwable -> 0x052d }
            r5.<init>()     // Catch:{ Throwable -> 0x052d }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Throwable -> 0x052d }
            r6.<init>()     // Catch:{ Throwable -> 0x052d }
            r9 = 0
        L_0x031e:
            int r10 = r3.length()     // Catch:{ Throwable -> 0x052d }
            if (r9 >= r10) goto L_0x03cc
            org.json.JSONObject r10 = r3.getJSONObject(r9)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r11 = "businfo_lineids"
            java.lang.String r11 = r10.optString(r11)     // Catch:{ Exception -> 0x03c3 }
            r6.append(r11)     // Catch:{ Exception -> 0x03c3 }
            int r11 = r3.length()     // Catch:{ Exception -> 0x03c3 }
            int r11 = r11 - r4
            if (r9 >= r11) goto L_0x033d
            java.lang.String r11 = ";"
            r6.append(r11)     // Catch:{ Exception -> 0x03c3 }
        L_0x033d:
            if (r9 != 0) goto L_0x0347
            java.lang.String r11 = "bus_alias"
            java.lang.String r12 = "A"
            r10.put(r11, r12)     // Catch:{ Exception -> 0x03c3 }
            goto L_0x0350
        L_0x0347:
            if (r9 != r4) goto L_0x0350
            java.lang.String r11 = "bus_alias"
            java.lang.String r12 = "B"
            r10.put(r11, r12)     // Catch:{ Exception -> 0x03c3 }
        L_0x0350:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r11 = r8.getPoiChildrenInfo()     // Catch:{ Exception -> 0x03c3 }
            java.util.List r11 = (java.util.List) r11     // Catch:{ Exception -> 0x03c3 }
            if (r11 == 0) goto L_0x03bf
            int r12 = r11.size()     // Catch:{ Exception -> 0x03c3 }
            int r13 = r9 + -1
            if (r12 < r13) goto L_0x03bf
            java.lang.Object r11 = r11.get(r9)     // Catch:{ Exception -> 0x03c3 }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r11 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r11     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "businfo_line_key"
            java.lang.String r13 = r11.getAddr()     // Catch:{ Exception -> 0x03c3 }
            r10.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "x"
            com.autonavi.common.model.GeoPoint r13 = r11.getPoint()     // Catch:{ Exception -> 0x03c3 }
            int r13 = r13.x     // Catch:{ Exception -> 0x03c3 }
            r10.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "y"
            com.autonavi.common.model.GeoPoint r13 = r11.getPoint()     // Catch:{ Exception -> 0x03c3 }
            int r13 = r13.y     // Catch:{ Exception -> 0x03c3 }
            r10.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "name"
            java.lang.String r13 = r8.getName()     // Catch:{ Exception -> 0x03c3 }
            r10.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "poiid2"
            java.lang.String r13 = r8.getId()     // Catch:{ Exception -> 0x03c3 }
            r10.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "poiid"
            java.lang.String r13 = r11.getPoiId()     // Catch:{ Exception -> 0x03c3 }
            r10.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "bus_alias"
            java.lang.String r12 = r10.optString(r12)     // Catch:{ Exception -> 0x03c3 }
            r11.setBusinfoAlias(r12)     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = r8.getId()     // Catch:{ Exception -> 0x03c3 }
            r11.setPoiId2(r12)     // Catch:{ Exception -> 0x03c3 }
            java.util.HashMap r11 = r11.getPoiExtra()     // Catch:{ Exception -> 0x03c3 }
            java.lang.String r12 = "businfo_lineids"
            java.lang.String r13 = "businfo_lineids"
            java.lang.String r13 = r10.optString(r13)     // Catch:{ Exception -> 0x03c3 }
            r11.put(r12, r13)     // Catch:{ Exception -> 0x03c3 }
        L_0x03bf:
            r5.put(r9, r10)     // Catch:{ Exception -> 0x03c3 }
            goto L_0x03c8
        L_0x03c3:
            r0 = move-exception
            r10 = r0
            r10.printStackTrace()     // Catch:{ Throwable -> 0x052d }
        L_0x03c8:
            int r9 = r9 + 1
            goto L_0x031e
        L_0x03cc:
            java.lang.String r3 = r6.toString()     // Catch:{ Throwable -> 0x052d }
            boolean r6 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x052d }
            if (r6 != 0) goto L_0x03df
            java.util.HashMap r6 = r8.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = "businfo_lineids"
            r6.put(r9, r3)     // Catch:{ Throwable -> 0x052d }
        L_0x03df:
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r8.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x044b
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r8.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x052d }
            int r3 = r3.childType     // Catch:{ Throwable -> 0x052d }
            if (r3 != r4) goto L_0x044b
            java.util.HashMap r3 = r8.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r6 = "child_stations"
            java.lang.String r9 = r5.toString()     // Catch:{ Throwable -> 0x052d }
            r3.put(r6, r9)     // Catch:{ Throwable -> 0x052d }
            boolean r3 = r8 instanceof java.util.List     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x0437
            r3 = r8
            java.util.List r3 = (java.util.List) r3     // Catch:{ Throwable -> 0x052d }
            r6 = 0
            java.lang.Object r3 = r3.get(r6)     // Catch:{ Throwable -> 0x052d }
            boolean r3 = r3 instanceof com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x0437
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildrenPoiData r3 = r8.getPoiChildrenInfo()     // Catch:{ Throwable -> 0x052d }
            java.util.Collection r3 = (java.util.Collection) r3     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x044b
            java.util.Iterator r3 = r3.iterator()     // Catch:{ Throwable -> 0x052d }
        L_0x0416:
            boolean r6 = r3.hasNext()     // Catch:{ Throwable -> 0x052d }
            if (r6 == 0) goto L_0x044b
            java.lang.Object r6 = r3.next()     // Catch:{ Throwable -> 0x052d }
            com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData r6 = (com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData) r6     // Catch:{ Throwable -> 0x052d }
            java.util.HashMap r9 = r6.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r10 = "child_stations"
            java.lang.String r11 = r5.toString()     // Catch:{ Throwable -> 0x052d }
            r9.put(r10, r11)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = r8.getName()     // Catch:{ Throwable -> 0x052d }
            r6.setName(r9)     // Catch:{ Throwable -> 0x052d }
            goto L_0x0416
        L_0x0437:
            boolean r3 = r8 instanceof com.amap.bundle.datamodel.poi.POIBase     // Catch:{ Throwable -> 0x052d }
            if (r3 == 0) goto L_0x044b
            r3 = r8
            com.amap.bundle.datamodel.poi.POIBase r3 = (com.amap.bundle.datamodel.poi.POIBase) r3     // Catch:{ Throwable -> 0x052d }
            java.util.HashMap r3 = r3.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r6 = "child_stations"
            java.lang.String r5 = r5.toString()     // Catch:{ Throwable -> 0x052d }
            r3.put(r6, r5)     // Catch:{ Throwable -> 0x052d }
        L_0x044b:
            java.lang.String r3 = "longitude"
            r5 = -4616189618054758400(0xbff0000000000000, double:-1.0)
            double r9 = r7.optDouble(r3, r5)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r3 = "latitude"
            double r11 = r7.optDouble(r3, r5)     // Catch:{ Throwable -> 0x052d }
            int r3 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0468
            int r3 = (r11 > r5 ? 1 : (r11 == r5 ? 0 : -1))
            if (r3 == 0) goto L_0x0468
            com.autonavi.common.model.GeoPoint r3 = r8.getPoint()     // Catch:{ Throwable -> 0x052d }
            r3.setLonLat(r9, r11)     // Catch:{ Throwable -> 0x052d }
        L_0x0468:
            java.lang.String r3 = "citycode"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            r8.setCityCode(r3)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r3 = "adcode"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            r8.setAdCode(r3)     // Catch:{ Throwable -> 0x052d }
            r3 = -100
            java.lang.String r5 = "distance"
            java.lang.String r5 = r7.optString(r5)     // Catch:{ Exception -> 0x04b7 }
            if (r5 == 0) goto L_0x0499
            java.lang.String r6 = ""
            boolean r6 = r5.equals(r6)     // Catch:{ Exception -> 0x04b7 }
            if (r6 == 0) goto L_0x048d
            goto L_0x0499
        L_0x048d:
            java.lang.Double r5 = java.lang.Double.valueOf(r5)     // Catch:{ Exception -> 0x04b7 }
            int r5 = r5.intValue()     // Catch:{ Exception -> 0x04b7 }
            if (r5 != 0) goto L_0x0498
            goto L_0x0499
        L_0x0498:
            r3 = r5
        L_0x0499:
            com.autonavi.common.model.GeoPoint r5 = r8.getPoint()     // Catch:{ Exception -> 0x04b3 }
            if (r3 > 0) goto L_0x04bf
            if (r5 == 0) goto L_0x04bf
            int r6 = r2.getAdCode()     // Catch:{ Exception -> 0x04b3 }
            int r9 = r5.getAdCode()     // Catch:{ Exception -> 0x04b3 }
            if (r6 == r9) goto L_0x04bf
            float r5 = defpackage.cfe.a(r2, r5)     // Catch:{ Exception -> 0x04b3 }
            int r5 = (int) r5
            if (r5 <= 0) goto L_0x04bf
            goto L_0x04be
        L_0x04b3:
            r0 = move-exception
            r5 = r3
            r3 = r0
            goto L_0x04bb
        L_0x04b7:
            r0 = move-exception
            r3 = r0
            r5 = -100
        L_0x04bb:
            defpackage.kf.a(r3)     // Catch:{ Throwable -> 0x052d }
        L_0x04be:
            r3 = r5
        L_0x04bf:
            r8.setDistance(r3)     // Catch:{ Throwable -> 0x052d }
            java.lang.String r3 = "cpdata"
            java.lang.String r3 = r7.optString(r3)     // Catch:{ Throwable -> 0x052d }
            boolean r5 = android.text.TextUtils.isEmpty(r3)     // Catch:{ Throwable -> 0x052d }
            if (r5 != 0) goto L_0x050f
            java.lang.String r5 = "\\|"
            java.lang.String[] r3 = r3.split(r5)     // Catch:{ Throwable -> 0x052d }
            java.util.ArrayList r5 = new java.util.ArrayList     // Catch:{ Throwable -> 0x052d }
            r5.<init>()     // Catch:{ Throwable -> 0x052d }
            r6 = 0
        L_0x04da:
            int r7 = r3.length     // Catch:{ Throwable -> 0x052d }
            if (r6 >= r7) goto L_0x0500
            r7 = r3[r6]     // Catch:{ Throwable -> 0x052d }
            java.lang.String r9 = ";"
            java.lang.String[] r7 = r7.split(r9)     // Catch:{ Throwable -> 0x052d }
            int r9 = r7.length     // Catch:{ Throwable -> 0x052d }
            if (r9 <= r4) goto L_0x04fc
            com.amap.bundle.datamodel.poi.CpData r9 = new com.amap.bundle.datamodel.poi.CpData     // Catch:{ Throwable -> 0x052d }
            r9.<init>()     // Catch:{ Throwable -> 0x052d }
            r10 = 0
            r11 = r7[r10]     // Catch:{ Throwable -> 0x052d }
            r9.setCpid(r11)     // Catch:{ Throwable -> 0x052d }
            r7 = r7[r4]     // Catch:{ Throwable -> 0x052d }
            r9.setSource(r7)     // Catch:{ Throwable -> 0x052d }
            r5.add(r9)     // Catch:{ Throwable -> 0x052d }
            goto L_0x04fd
        L_0x04fc:
            r10 = 0
        L_0x04fd:
            int r6 = r6 + 1
            goto L_0x04da
        L_0x0500:
            r10 = 0
            java.util.HashMap r3 = r8.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r4 = "Cpdata"
            java.lang.String r5 = org.xidea.el.json.JSONEncoder.encode(r5)     // Catch:{ Throwable -> 0x052d }
            r3.put(r4, r5)     // Catch:{ Throwable -> 0x052d }
            goto L_0x0519
        L_0x050f:
            r10 = 0
            java.util.HashMap r3 = r8.getPoiExtra()     // Catch:{ Throwable -> 0x052d }
            java.lang.String r4 = "Cpdata"
            r3.remove(r4)     // Catch:{ Throwable -> 0x052d }
        L_0x0519:
            r1.add(r8)     // Catch:{ Throwable -> 0x052d }
            goto L_0x0524
        L_0x051d:
            r18 = r3
            r19 = r4
            r16 = r6
            r10 = 0
        L_0x0524:
            int r6 = r16 + 1
            r3 = r18
            r4 = r19
            r5 = 0
            goto L_0x0023
        L_0x052d:
            r0 = move-exception
            r2 = r0
            r2.printStackTrace()
        L_0x0532:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ccb.a(org.json.JSONObject):java.util.List");
    }
}
