package defpackage;

import android.app.Activity;
import com.autonavi.minimap.intent.BaseIntentDispatcher;

/* renamed from: dpd reason: default package */
/* compiled from: LifeIntentDispatcherImpl */
public final class dpd extends BaseIntentDispatcher implements dpc {
    private Activity a;

    public dpd(Activity activity) {
        this.a = activity;
    }

    /* JADX WARNING: Removed duplicated region for block: B:107:0x0286 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x0287 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0105 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0106  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x0200 A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean a(@android.support.annotation.NonNull android.content.Intent r14) {
        /*
            r13 = this;
            android.net.Uri r0 = r14.getData()
            r1 = 0
            if (r0 == 0) goto L_0x0289
            java.lang.String r2 = r0.getHost()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L_0x0289
            java.lang.String r3 = r0.getHost()
            java.util.List r4 = r0.getPathSegments()
            java.lang.String r5 = "search"
            boolean r5 = android.text.TextUtils.equals(r3, r5)
            r6 = 0
            r7 = 1
            if (r5 == 0) goto L_0x00d0
            if (r4 == 0) goto L_0x00ce
            int r3 = r4.size()
            if (r3 > 0) goto L_0x002d
            goto L_0x00ce
        L_0x002d:
            java.lang.Object r3 = r4.get(r1)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "nearby"
            boolean r3 = android.text.TextUtils.equals(r3, r4)
            if (r3 == 0) goto L_0x00ce
            java.lang.String r3 = "keyword"
            java.lang.String r3 = r0.getQueryParameter(r3)
            boolean r4 = android.text.TextUtils.isEmpty(r3)
            if (r4 != 0) goto L_0x00ee
            bid r4 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r4 == 0) goto L_0x00ee
            boolean r5 = r4 instanceof com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage
            if (r5 == 0) goto L_0x0060
            com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage r4 = (com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage) r4
            bty r4 = r4.getMapView()
            com.autonavi.ae.gmap.glinterface.GLGeoPoint r4 = r4.n()
            com.autonavi.common.model.GeoPoint r4 = com.autonavi.common.model.GeoPoint.glGeoPoint2GeoPoint(r4)
            goto L_0x0061
        L_0x0060:
            r4 = r6
        L_0x0061:
            if (r4 != 0) goto L_0x006c
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            r5 = 5
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition(r5)
        L_0x006c:
            if (r4 != 0) goto L_0x0076
            com.autonavi.sdk.location.LocationInstrument r4 = com.autonavi.sdk.location.LocationInstrument.getInstance()
            com.autonavi.common.model.GeoPoint r4 = r4.getLatestPosition()
        L_0x0076:
            java.lang.String r5 = "transparent"
            java.lang.String r5 = r0.getQueryParameter(r5)
            com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            java.lang.String r8 = ""
            java.lang.String r9 = ""
            java.lang.String r10 = ""
            aeo r11 = new aeo
            r11.<init>()
            boolean r12 = android.text.TextUtils.isEmpty(r5)
            if (r12 != 0) goto L_0x0093
            r11.f = r5
        L_0x0093:
            r11.e = r10
            r11.d = r9
            r11.c = r8
            ael r5 = new ael
            r5.<init>(r3, r4)
            r5.j = r11
            if (r4 == 0) goto L_0x00b8
            android.graphics.Rect r3 = new android.graphics.Rect
            int r8 = r4.x
            int r8 = r8 + -100
            int r9 = r4.y
            int r9 = r9 + -100
            int r10 = r4.x
            int r10 = r10 + 100
            int r4 = r4.y
            int r4 = r4 + 100
            r3.<init>(r8, r9, r10, r4)
            goto L_0x00b9
        L_0x00b8:
            r3 = r6
        L_0x00b9:
            r5.c = r3
            esb r3 = defpackage.esb.a.a
            java.lang.Class<bck> r4 = defpackage.bck.class
            esc r3 = r3.a(r4)
            bck r3 = (defpackage.bck) r3
            if (r3 == 0) goto L_0x00ee
            r4 = 2
            r3.a(r5, r4)
            goto L_0x00ee
        L_0x00ce:
            r3 = 0
            goto L_0x0103
        L_0x00d0:
            java.lang.String r4 = "nearby"
            boolean r3 = android.text.TextUtils.equals(r3, r4)
            if (r3 == 0) goto L_0x00ce
            java.lang.String r3 = r0.getPath()
            java.lang.String r4 = "/weather"
            boolean r4 = r4.equalsIgnoreCase(r3)
            if (r4 == 0) goto L_0x00f0
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            boolean r3 = com.autonavi.minimap.life.common.NearbyUtils.a(r3)
            if (r3 == 0) goto L_0x00ce
        L_0x00ee:
            r3 = 1
            goto L_0x0103
        L_0x00f0:
            java.lang.String r4 = "/main"
            boolean r3 = r4.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x00ce
            bid r3 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            boolean r3 = com.autonavi.minimap.life.common.NearbyUtils.a(r3)
            if (r3 == 0) goto L_0x00ce
            goto L_0x00ee
        L_0x0103:
            if (r3 == 0) goto L_0x0106
            return r7
        L_0x0106:
            java.lang.String r3 = "nearby"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0123
            java.lang.String r14 = r0.getPath()
            java.lang.String r0 = "/weather"
            boolean r14 = r0.equalsIgnoreCase(r14)
            if (r14 == 0) goto L_0x0287
            bid r14 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            com.autonavi.minimap.life.common.NearbyUtils.a(r14)
            goto L_0x0287
        L_0x0123:
            java.lang.String r3 = "openFeature"
            boolean r3 = r2.equalsIgnoreCase(r3)
            if (r3 == 0) goto L_0x0201
            android.net.Uri r14 = r14.getData()
            if (r14 != 0) goto L_0x0134
            java.lang.String r0 = ""
            goto L_0x013a
        L_0x0134:
            java.lang.String r0 = "featureName"
            java.lang.String r0 = r14.getQueryParameter(r0)
        L_0x013a:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L_0x014f
            android.app.Application r14 = com.autonavi.amap.app.AMapAppGlobal.getApplication()
            int r0 = com.autonavi.minimap.R.string.intent_open_fail_param_error
            java.lang.String r14 = r14.getString(r0)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r14)
            goto L_0x01fb
        L_0x014f:
            java.lang.String r2 = "AroundSearch"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0175
            com.autonavi.map.widget.ProgressDlg r14 = new com.autonavi.map.widget.ProgressDlg
            android.app.Activity r0 = r13.a
            android.app.Activity r2 = r13.a
            int r3 = com.autonavi.minimap.R.string.locating
            java.lang.String r2 = r2.getString(r3)
            r14.<init>(r0, r2)
            r14.show()
            com.autonavi.minimap.life.intent.inner.LifeIntentDispatcherImpl$2 r0 = new com.autonavi.minimap.life.intent.inner.LifeIntentDispatcherImpl$2
            r0.<init>(r13, r14)
            r14 = 30000(0x7530, float:4.2039E-41)
            defpackage.bib.a(r0, r14)
            goto L_0x01fb
        L_0x0175:
            java.lang.String r2 = "NearKeyWordSearch"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x0189
            bid r14 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r14 == 0) goto L_0x01fb
            java.lang.String r0 = "amap.search.action.arround"
            r14.startPage(r0, r6)
            goto L_0x01fb
        L_0x0189:
            java.lang.String r2 = "nearSearchResult"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x01b8
            java.lang.String r0 = "keyWord"
            java.lang.String r14 = r14.getQueryParameter(r0)
            com.autonavi.common.PageBundle r0 = new com.autonavi.common.PageBundle
            r0.<init>()
            java.lang.String r2 = "keyword"
            r0.putString(r2, r14)
            java.lang.String r14 = "search_type"
            r0.putInt(r14, r7)
            java.lang.String r14 = "start_search"
            r0.putBoolean(r14, r7)
            bid r14 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            if (r14 == 0) goto L_0x01fb
            java.lang.String r2 = "amap.search.action.arround"
            r14.startPage(r2, r0)
            goto L_0x01fb
        L_0x01b8:
            java.lang.String r2 = "OrderForm"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x01cf
            com.autonavi.minimap.life.inter.impl.OpenLifeFragmentImpl r14 = new com.autonavi.minimap.life.inter.impl.OpenLifeFragmentImpl
            r14.<init>()
            bid r0 = r13.getPageContext()
            r2 = 24
            r14.a(r0, r2, r6)
            goto L_0x01fb
        L_0x01cf:
            java.lang.String r2 = "MallGuide"
            boolean r2 = r0.equalsIgnoreCase(r2)
            if (r2 == 0) goto L_0x01e2
            android.app.Activity r0 = r13.a
            dpd$1 r2 = new dpd$1
            r2.<init>(r14)
            r0.runOnUiThread(r2)
            goto L_0x01fb
        L_0x01e2:
            java.lang.String r2 = "Nearby"
            boolean r0 = r0.equalsIgnoreCase(r2)
            if (r0 == 0) goto L_0x01fd
            java.lang.String r0 = "lon"
            r14.getQueryParameter(r0)
            java.lang.String r0 = "lat"
            r14.getQueryParameter(r0)
            bid r14 = com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil.getPageContext()
            com.autonavi.minimap.life.common.NearbyUtils.a(r14)
        L_0x01fb:
            r14 = 1
            goto L_0x01fe
        L_0x01fd:
            r14 = 0
        L_0x01fe:
            if (r14 != 0) goto L_0x0287
            return r1
        L_0x0201:
            java.lang.String r14 = "poi"
            boolean r14 = r2.equals(r14)
            if (r14 == 0) goto L_0x0288
            java.util.List r14 = r0.getPathSegments()
            if (r14 == 0) goto L_0x0283
            int r2 = r14.size()
            if (r2 <= 0) goto L_0x0283
            java.lang.Object r14 = r14.get(r1)
            java.lang.String r14 = (java.lang.String) r14
            java.lang.String r2 = "comment"
            boolean r14 = android.text.TextUtils.equals(r2, r14)
            if (r14 == 0) goto L_0x0283
            java.lang.String r14 = "business"
            java.lang.String r14 = r0.getQueryParameter(r14)
            java.lang.String r2 = "poiname"
            java.lang.String r2 = r0.getQueryParameter(r2)
            java.lang.String r3 = "poiid"
            java.lang.String r3 = r0.getQueryParameter(r3)
            com.autonavi.common.PageBundle r4 = new com.autonavi.common.PageBundle
            r4.<init>()
            java.lang.String r5 = "EDIT_COMMENT_POI"
            r4.putString(r5, r2)
            java.lang.String r2 = "EDIT_COMMENT_POI_ID"
            r4.putString(r2, r3)
            java.lang.String r2 = "EDIT_COMMENT_POI_BUSINESS"
            r4.putString(r2, r14)
            java.lang.String r14 = "COMMENT_FROM"
            java.lang.String r2 = "PUSH"
            r4.putString(r14, r2)
            if (r0 == 0) goto L_0x026d
            java.lang.String r14 = "from"
            java.lang.String r14 = r0.getQueryParameter(r14)
            boolean r0 = android.text.TextUtils.isEmpty(r14)
            if (r0 != 0) goto L_0x026d
            java.lang.String r0 = "activity"
            boolean r14 = r14.equalsIgnoreCase(r0)
            if (r14 == 0) goto L_0x026d
            java.lang.String r14 = "COMMENT_FROM"
            java.lang.String r0 = "activity"
            r4.putString(r14, r0)
        L_0x026d:
            java.lang.Class<com.autonavi.inter.IPageManifest> r14 = com.autonavi.inter.IPageManifest.class
            java.lang.Object r14 = defpackage.ank.a(r14)
            com.autonavi.inter.IPageManifest r14 = (com.autonavi.inter.IPageManifest) r14
            if (r14 == 0) goto L_0x0283
            java.lang.String r0 = "amap.search.action.comment"
            java.lang.Class r14 = r14.getPage(r0)
            r0 = -1
            r13.startPageForResult(r14, r4, r0)
            r14 = 1
            goto L_0x0284
        L_0x0283:
            r14 = 0
        L_0x0284:
            if (r14 != 0) goto L_0x0287
            return r1
        L_0x0287:
            return r7
        L_0x0288:
            return r1
        L_0x0289:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.dpd.a(android.content.Intent):boolean");
    }
}
