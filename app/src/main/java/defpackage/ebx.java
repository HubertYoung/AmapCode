package defpackage;

import com.autonavi.annotation.Router;

@Router({"footNavi"})
/* renamed from: ebx reason: default package */
/* compiled from: FootNaviRouter */
public class ebx extends esk {
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x007f  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00a5  */
    /* JADX WARNING: Removed duplicated region for block: B:48:0x00bd  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x00ba A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean start(defpackage.ese r14) {
        /*
            r13 = this;
            android.net.Uri r14 = r14.a
            java.lang.String r0 = "featureName"
            java.lang.String r0 = r14.getQueryParameter(r0)
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            r2 = 1
            if (r1 == 0) goto L_0x0019
            int r14 = com.autonavi.minimap.R.string.intent_open_fail_param_error
            java.lang.String r14 = defpackage.eay.a(r14)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r14)
            return r2
        L_0x0019:
            java.lang.String r1 = "OnFootNavi"
            boolean r0 = r0.equalsIgnoreCase(r1)
            r1 = 0
            if (r0 == 0) goto L_0x010a
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r3 = ""
            java.lang.String r4 = ""
            r5 = 0
            java.lang.String r7 = "lat"
            java.lang.String r7 = r14.getQueryParameter(r7)     // Catch:{ Exception -> 0x0059 }
            double r7 = java.lang.Double.parseDouble(r7)     // Catch:{ Exception -> 0x0059 }
            java.lang.String r9 = "lon"
            java.lang.String r9 = r14.getQueryParameter(r9)     // Catch:{ Exception -> 0x0054 }
            double r9 = java.lang.Double.parseDouble(r9)     // Catch:{ Exception -> 0x0054 }
            java.lang.String r5 = "endName"
            java.lang.String r5 = r14.getQueryParameter(r5)     // Catch:{ Exception -> 0x0052 }
            if (r5 == 0) goto L_0x004a
            r4 = r5
        L_0x004a:
            java.lang.String r5 = "poiId"
            java.lang.String r5 = r14.getQueryParameter(r5)     // Catch:{ Exception -> 0x0052 }
            r3 = r5
            goto L_0x0060
        L_0x0052:
            r5 = move-exception
            goto L_0x005d
        L_0x0054:
            r9 = move-exception
            r11 = r5
            r5 = r9
            r9 = r11
            goto L_0x005d
        L_0x0059:
            r7 = move-exception
            r9 = r5
            r5 = r7
            r7 = r9
        L_0x005d:
            r5.printStackTrace()
        L_0x0060:
            android.graphics.Point r5 = defpackage.cfg.a(r7, r9)
            java.lang.String r6 = "dev"
            java.lang.String r14 = r14.getQueryParameter(r6)     // Catch:{ Exception -> 0x006f }
            int r14 = java.lang.Integer.parseInt(r14)     // Catch:{ Exception -> 0x006f }
            goto L_0x0074
        L_0x006f:
            r14 = move-exception
            r14.printStackTrace()
            r14 = 0
        L_0x0074:
            if (r14 != r2) goto L_0x007f
            int r14 = r5.x
            int r5 = r5.y
            com.autonavi.common.model.GeoPoint r14 = defpackage.cff.a(r14, r5)
            goto L_0x0088
        L_0x007f:
            com.autonavi.common.model.GeoPoint r14 = new com.autonavi.common.model.GeoPoint
            int r6 = r5.x
            int r5 = r5.y
            r14.<init>(r6, r5)
        L_0x0088:
            java.lang.String r5 = "endPoint"
            r0.putObject(r5, r14)
            java.util.ArrayList r5 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPagesStacks()
            if (r5 == 0) goto L_0x00d1
            boolean r6 = r5.isEmpty()
            if (r6 != 0) goto L_0x00d1
            java.util.ArrayList r6 = new java.util.ArrayList
            r6.<init>()
            int r5 = r5.size()
            int r5 = r5 - r2
        L_0x00a3:
            if (r5 < 0) goto L_0x00ba
            bid r7 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getStackFragment(r5)
            if (r7 == 0) goto L_0x00b7
            boolean r8 = r7 instanceof com.autonavi.minimap.route.foot.page.AjxFootNaviPage
            if (r8 == 0) goto L_0x00b4
            r6.add(r7)
            r5 = 1
            goto L_0x00bb
        L_0x00b4:
            r6.add(r7)
        L_0x00b7:
            int r5 = r5 + -1
            goto L_0x00a3
        L_0x00ba:
            r5 = 0
        L_0x00bb:
            if (r5 == 0) goto L_0x00d1
            java.util.Iterator r5 = r6.iterator()
        L_0x00c1:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x00d1
            java.lang.Object r6 = r5.next()
            bid r6 = (defpackage.bid) r6
            r6.finish()
            goto L_0x00c1
        L_0x00d1:
            org.json.JSONObject r5 = new org.json.JSONObject
            r5.<init>()
            com.autonavi.common.model.POI r14 = com.amap.bundle.datamodel.poi.POIFactory.createPOI(r4, r14)     // Catch:{ JSONException -> 0x0105 }
            r14.setId(r3)     // Catch:{ JSONException -> 0x0105 }
            java.lang.String r3 = "endPoi"
            org.json.JSONObject r14 = defpackage.bnx.b(r14)     // Catch:{ JSONException -> 0x0105 }
            r5.put(r3, r14)     // Catch:{ JSONException -> 0x0105 }
            java.lang.String r14 = "fromWhere"
            java.lang.String r3 = "scheme"
            r5.put(r14, r3)     // Catch:{ JSONException -> 0x0105 }
            java.lang.String r14 = "bundle_key_obj_result"
            java.lang.String r3 = r5.toString()     // Catch:{ JSONException -> 0x0105 }
            r0.putString(r14, r3)     // Catch:{ JSONException -> 0x0105 }
            ebx$1 r14 = new ebx$1     // Catch:{ JSONException -> 0x0105 }
            r14.<init>(r0)     // Catch:{ JSONException -> 0x0105 }
            eam r14 = defpackage.eam.a(r14)     // Catch:{ JSONException -> 0x0105 }
            java.lang.String r0 = "agree_onfoot_declare"
            r14.a(r0, r1)     // Catch:{ JSONException -> 0x0105 }
            goto L_0x0109
        L_0x0105:
            r14 = move-exception
            r14.printStackTrace()
        L_0x0109:
            return r2
        L_0x010a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ebx.start(ese):boolean");
    }
}
