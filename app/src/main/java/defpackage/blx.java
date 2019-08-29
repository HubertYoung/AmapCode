package defpackage;

import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;

/* renamed from: blx reason: default package */
/* compiled from: ToggleFavoritePointAction */
public class blx extends vz {
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00f0  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00f7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(org.json.JSONObject r8, defpackage.wa r9) {
        /*
            r7 = this;
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0138
            com.amap.bundle.jsadapter.JsAdapter r1 = r7.a()
            if (r1 != 0) goto L_0x0011
            return
        L_0x0011:
            java.lang.String r2 = "poiInfo"
            java.lang.String r8 = r8.optString(r2)
            com.autonavi.common.model.POI r8 = defpackage.kv.a(r8)
            java.lang.String r2 = r0.a()
            cop r0 = r0.b(r2)
            r2 = 0
            if (r0 == 0) goto L_0x002b
            boolean r0 = r0.c(r8)
            goto L_0x002c
        L_0x002b:
            r0 = 0
        L_0x002c:
            r3 = 1
            if (r0 != 0) goto L_0x0031
            r0 = 1
            goto L_0x0032
        L_0x0031:
            r0 = 0
        L_0x0032:
            r4 = -1
            if (r8 == 0) goto L_0x00e3
            bid r5 = r1.mPageContext
            if (r5 != 0) goto L_0x003b
            goto L_0x00e3
        L_0x003b:
            r5 = 0
            if (r0 == 0) goto L_0x008a
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r0 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r8 = r8.as(r0)
            com.amap.bundle.datamodel.FavoritePOI r8 = (com.amap.bundle.datamodel.FavoritePOI) r8
            r8.setSaved(r3)
            a(r8)
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x0063
            java.lang.String r6 = r0.a()
            cop r0 = r0.b(r6)
            if (r0 == 0) goto L_0x0063
            r0.b(r8)
        L_0x0063:
            android.app.Application r8 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r8 = r8.getApplicationContext()
            int r0 = com.autonavi.minimap.R.string.poi_add_favourite_success
            java.lang.String r8 = r8.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r8)
            java.lang.Class<brn> r8 = defpackage.brn.class
            java.lang.Object r8 = defpackage.ank.a(r8)
            brn r8 = (defpackage.brn) r8
            if (r8 == 0) goto L_0x0081
            r8.b()
        L_0x0081:
            bid r8 = r1.mPageContext
            com.autonavi.common.Page$ResultType r0 = com.autonavi.common.Page.ResultType.CANCEL
            r8.setResult(r0, r5)
        L_0x0088:
            r8 = 1
            goto L_0x00e4
        L_0x008a:
            com.autonavi.common.PageBundle r0 = r1.getBundle()
            if (r0 != 0) goto L_0x0091
            goto L_0x00e3
        L_0x0091:
            a(r8)
            java.lang.Class<com> r0 = defpackage.com.class
            java.lang.Object r0 = defpackage.ank.a(r0)
            com r0 = (defpackage.com) r0
            if (r0 == 0) goto L_0x00ab
            java.lang.String r6 = r0.a()
            cop r0 = r0.b(r6)
            if (r0 == 0) goto L_0x00ab
            r0.a(r8)
        L_0x00ab:
            android.app.Application r0 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.Context r0 = r0.getApplicationContext()
            int r6 = com.autonavi.minimap.R.string.poi_cancel_favourite
            java.lang.String r0 = r0.getString(r6)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r0)
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r0 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r8 = r8.as(r0)
            com.amap.bundle.datamodel.FavoritePOI r8 = (com.amap.bundle.datamodel.FavoritePOI) r8
            r8.setSaved(r2)
            java.lang.String r0 = r8.getName()
            r8.setCustomName(r0)
            java.lang.Class<brn> r8 = defpackage.brn.class
            java.lang.Object r8 = defpackage.ank.a(r8)
            brn r8 = (defpackage.brn) r8
            if (r8 == 0) goto L_0x00db
            r8.b()
        L_0x00db:
            bid r8 = r1.mPageContext
            com.autonavi.common.Page$ResultType r0 = com.autonavi.common.Page.ResultType.CANCEL
            r8.setResult(r0, r5)
            goto L_0x0088
        L_0x00e3:
            r8 = -1
        L_0x00e4:
            java.lang.String r0 = ""
            android.app.Application r5 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            android.content.res.Resources r5 = r5.getResources()
            if (r8 != r3) goto L_0x00f7
            int r0 = com.autonavi.minimap.R.string.success
            java.lang.String r0 = r5.getString(r0)
            goto L_0x0114
        L_0x00f7:
            if (r8 != r4) goto L_0x0100
            int r8 = com.autonavi.minimap.R.string.error_incorrect_passed_in_poi
            java.lang.String r0 = r5.getString(r8)
            goto L_0x0113
        L_0x0100:
            r3 = -2
            if (r8 != r3) goto L_0x010a
            int r8 = com.autonavi.minimap.R.string.error_fail_to_open_database
            java.lang.String r0 = r5.getString(r8)
            goto L_0x0113
        L_0x010a:
            r3 = -3
            if (r8 != r3) goto L_0x0113
            int r8 = com.autonavi.minimap.R.string.error_excceed_maximum_favorite
            java.lang.String r0 = r5.getString(r8)
        L_0x0113:
            r8 = 0
        L_0x0114:
            org.json.JSONObject r2 = new org.json.JSONObject
            r2.<init>()
            java.lang.String r3 = "_action"
            java.lang.String r4 = r9.b     // Catch:{ Exception -> 0x012b }
            r2.put(r3, r4)     // Catch:{ Exception -> 0x012b }
            java.lang.String r3 = "result"
            r2.put(r3, r8)     // Catch:{ Exception -> 0x012b }
            java.lang.String r8 = "message"
            r2.put(r8, r0)     // Catch:{ Exception -> 0x012b }
            goto L_0x012f
        L_0x012b:
            r8 = move-exception
            r8.printStackTrace()
        L_0x012f:
            java.lang.String r8 = r9.a
            java.lang.String r9 = r2.toString()
            r1.callJs(r8, r9)
        L_0x0138:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.blx.a(org.json.JSONObject, wa):void");
    }

    private static void a(POI poi) {
        if (poi != null && TextUtils.isEmpty(poi.getName()) && !TextUtils.isEmpty(poi.getAddr())) {
            poi.setName(poi.getAddr());
        }
        if (poi != null && AMapAppGlobal.getApplication().getString(R.string.my_location).equals(poi.getName())) {
            String addr = poi.getAddr();
            if (TextUtils.isEmpty(addr)) {
                addr = AMapAppGlobal.getApplication().getString(R.string.map_point);
            }
            poi.setName(addr);
        }
    }
}
