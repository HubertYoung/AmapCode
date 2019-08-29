package defpackage;

import com.autonavi.annotation.Router;

@Router({"rideNavi"})
/* renamed from: edl reason: default package */
/* compiled from: RideNaviRouter */
public class edl extends esk {
    /* JADX WARNING: Removed duplicated region for block: B:32:0x006b  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x0074  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0092  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b7  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:55:0x00f3  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f1 A[EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f1 A[EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f1 A[EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  , SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x00f1 A[EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  
    EDGE_INSN: B:67:0x00f1->B:54:0x00f1 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(defpackage.ese r13) {
        /*
            r12 = this;
            android.net.Uri r13 = r13.a
            java.lang.String r0 = "featureName"
            java.lang.String r0 = r13.getQueryParameter(r0)
            java.lang.String r1 = "OnRideNavi"
            boolean r0 = r0.equalsIgnoreCase(r1)
            r1 = 0
            if (r0 == 0) goto L_0x013e
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r2 = ""
            java.lang.String r3 = "endName"
            r4 = 0
            java.lang.String r6 = "lat"
            java.lang.String r6 = r13.getQueryParameter(r6)     // Catch:{ Exception -> 0x004f }
            double r6 = java.lang.Double.parseDouble(r6)     // Catch:{ Exception -> 0x004f }
            java.lang.String r8 = "lon"
            java.lang.String r8 = r13.getQueryParameter(r8)     // Catch:{ Exception -> 0x0049 }
            double r8 = java.lang.Double.parseDouble(r8)     // Catch:{ Exception -> 0x0049 }
            java.lang.String r4 = "endName"
            java.lang.String r4 = r13.getQueryParameter(r4)     // Catch:{ Exception -> 0x0045 }
            java.lang.String r3 = "poiId"
            java.lang.String r3 = r13.getQueryParameter(r3)     // Catch:{ Exception -> 0x0040 }
            r2 = r3
            r3 = r4
            r4 = r6
            goto L_0x0054
        L_0x0040:
            r3 = move-exception
            r10 = r6
            r6 = r3
            r3 = r4
            goto L_0x004d
        L_0x0045:
            r4 = move-exception
            r10 = r6
            r6 = r4
            goto L_0x004d
        L_0x0049:
            r8 = move-exception
            r10 = r6
            r6 = r8
            r8 = r4
        L_0x004d:
            r4 = r10
            goto L_0x0051
        L_0x004f:
            r6 = move-exception
            r8 = r4
        L_0x0051:
            r6.printStackTrace()
        L_0x0054:
            android.graphics.Point r4 = defpackage.cfg.a(r4, r8)
            java.lang.String r5 = "dev"
            java.lang.String r5 = r13.getQueryParameter(r5)     // Catch:{ Exception -> 0x0063 }
            int r5 = java.lang.Integer.parseInt(r5)     // Catch:{ Exception -> 0x0063 }
            goto L_0x0068
        L_0x0063:
            r5 = move-exception
            r5.printStackTrace()
            r5 = 0
        L_0x0068:
            r6 = 1
            if (r5 != r6) goto L_0x0074
            int r5 = r4.x
            int r4 = r4.y
            com.autonavi.common.model.GeoPoint r4 = defpackage.cff.a(r5, r4)
            goto L_0x007e
        L_0x0074:
            com.autonavi.common.model.GeoPoint r5 = new com.autonavi.common.model.GeoPoint
            int r7 = r4.x
            int r4 = r4.y
            r5.<init>(r7, r4)
            r4 = r5
        L_0x007e:
            java.lang.String r5 = "endPoint"
            r0.putObject(r5, r4)
            java.lang.String r5 = "rideType"
            java.lang.String r13 = r13.getQueryParameter(r5)
            java.lang.String r5 = "zscheme"
            boolean r7 = android.text.TextUtils.isEmpty(r13)
            if (r7 != 0) goto L_0x00b7
            java.lang.String r7 = "elebike"
            boolean r13 = android.text.TextUtils.equals(r7, r13)
            if (r13 == 0) goto L_0x00a9
            java.lang.String r13 = "open_ride_navi_type_key"
            r0.putInt(r13, r6)
            java.lang.String r13 = "pagefrom"
            java.lang.String r5 = "dscheme"
            r0.putString(r13, r5)
            java.lang.String r5 = "dscheme"
            goto L_0x00c4
        L_0x00a9:
            java.lang.String r13 = "open_ride_navi_type_key"
            r0.putInt(r13, r1)
            java.lang.String r13 = "pagefrom"
            java.lang.String r7 = "zscheme"
            r0.putString(r13, r7)
            goto L_0x00c4
        L_0x00b7:
            java.lang.String r13 = "open_ride_navi_type_key"
            r0.putInt(r13, r1)
            java.lang.String r13 = "pagefrom"
            java.lang.String r7 = "zscheme"
            r0.putString(r13, r7)
        L_0x00c4:
            java.util.ArrayList r13 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPagesStacks()
            if (r13 == 0) goto L_0x0107
            boolean r7 = r13.isEmpty()
            if (r7 != 0) goto L_0x0107
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            int r13 = r13.size()
            int r13 = r13 - r6
        L_0x00da:
            if (r13 < 0) goto L_0x00f1
            bid r8 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getStackFragment(r13)
            if (r8 == 0) goto L_0x00ee
            boolean r9 = r8 instanceof com.autonavi.minimap.route.ride.dest.page.AjxRideNaviPageNew
            if (r9 == 0) goto L_0x00eb
            r7.add(r8)
            r1 = 1
            goto L_0x00f1
        L_0x00eb:
            r7.add(r8)
        L_0x00ee:
            int r13 = r13 + -1
            goto L_0x00da
        L_0x00f1:
            if (r1 == 0) goto L_0x0107
            java.util.Iterator r13 = r7.iterator()
        L_0x00f7:
            boolean r1 = r13.hasNext()
            if (r1 == 0) goto L_0x0107
            java.lang.Object r1 = r13.next()
            bid r1 = (defpackage.bid) r1
            r1.finish()
            goto L_0x00f7
        L_0x0107:
            org.json.JSONObject r13 = new org.json.JSONObject
            r13.<init>()
            com.autonavi.common.model.POI r1 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r3, r4)     // Catch:{ JSONException -> 0x0139 }
            r1.setId(r2)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r2 = "endPoi"
            org.json.JSONObject r1 = defpackage.bnx.b(r1)     // Catch:{ JSONException -> 0x0139 }
            r13.put(r2, r1)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r1 = "fromWhere"
            r13.put(r1, r5)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r1 = "bundle_key_obj_result"
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x0139 }
            r0.putString(r1, r13)     // Catch:{ JSONException -> 0x0139 }
            edl$1 r13 = new edl$1     // Catch:{ JSONException -> 0x0139 }
            r13.<init>(r0)     // Catch:{ JSONException -> 0x0139 }
            eam r13 = defpackage.eam.a(r13)     // Catch:{ JSONException -> 0x0139 }
            java.lang.String r0 = "agree_ondest_declare"
            r13.a(r0, r6)     // Catch:{ JSONException -> 0x0139 }
            goto L_0x013d
        L_0x0139:
            r13 = move-exception
            r13.printStackTrace()
        L_0x013d:
            return r6
        L_0x013e:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.edl.start(ese):boolean");
    }
}
