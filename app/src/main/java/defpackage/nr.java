package defpackage;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.amap.bundle.drive.ajx.module.ModuleRouteEtrip;
import com.amap.bundle.drive.ajx.module.ModuleRouteEtrip.IRouteEtripModuleListener;
import com.amap.bundle.drive.etrip.home.AjxEtripResultPage;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.AjxContext;

/* renamed from: nr reason: default package */
/* compiled from: RouteEtripPageManager */
public final class nr implements axe, IRouteEtripModuleListener, dic {
    private qb a;
    private PageBundle b;
    private ModuleRouteEtrip c;
    private AbstractBasePage d;

    public nr(@NonNull AbstractBasePage abstractBasePage, @NonNull AjxContext ajxContext) {
        tj.a("DriveRoutePageManager", "");
        this.d = abstractBasePage;
        this.c = (ModuleRouteEtrip) Ajx.getInstance().getModuleIns(ajxContext, ModuleRouteEtrip.MODULE_NAME);
        if (this.c != null) {
            this.c.setManagerListener(this);
        }
        this.a = new qb(abstractBasePage);
        this.a.a();
    }

    public final void a(PageBundle pageBundle) {
        tj.a("DriveRoutePageManager", "");
        this.b = pageBundle;
        if (this.b != null) {
        }
    }

    public final void a() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void b() {
        tj.a("DriveRoutePageManager", "");
        this.a.a();
    }

    public final void c() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void d() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void e() {
        tj.a("DriveRoutePageManager", "");
        if (this.c != null) {
            this.c.setManagerListener(null);
            this.c.release();
        }
    }

    public final void a(int i, ResultType resultType, PageBundle pageBundle) {
        StringBuilder sb = new StringBuilder("requestCode=");
        sb.append(i);
        sb.append(", resultType=");
        sb.append(resultType);
        sb.append(", data=");
        sb.append(pageBundle);
        tj.a("DriveRoutePageManager", sb.toString());
    }

    public final ResultType f() {
        tj.a("DriveRoutePageManager", "");
        if (this.d.hasViewLayer()) {
            return ResultType.CANCEL;
        }
        if (!this.a.m()) {
            return ResultType.NONE;
        }
        g();
        return ResultType.CANCEL;
    }

    public final void b(PageBundle pageBundle) {
        tj.a("DriveRoutePageManager", "newExtraData=".concat(String.valueOf(pageBundle)));
        this.b = pageBundle;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:0x0077 A[ADDED_TO_REGION, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0078  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startEtripResultPage(java.lang.String r9) {
        /*
            r8 = this;
            java.lang.String r0 = "DriveRoutePageManager"
            java.lang.String r1 = "param="
            java.lang.String r2 = java.lang.String.valueOf(r9)
            java.lang.String r1 = r1.concat(r2)
            defpackage.tj.a(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r9)
            r1 = 0
            if (r0 == 0) goto L_0x0017
            return r1
        L_0x0017:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r2 = ""
            r3 = 0
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x006a, Exception -> 0x0064 }
            r4.<init>(r9)     // Catch:{ JSONException -> 0x006a, Exception -> 0x0064 }
            java.lang.String r9 = "start_poi"
            org.json.JSONObject r9 = r4.optJSONObject(r9)     // Catch:{ JSONException -> 0x006a, Exception -> 0x0064 }
            java.lang.String r5 = "end_poi"
            org.json.JSONObject r5 = r4.optJSONObject(r5)     // Catch:{ JSONException -> 0x006a, Exception -> 0x0064 }
            if (r9 == 0) goto L_0x003b
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x006a, Exception -> 0x0064 }
            com.autonavi.common.model.POI r9 = defpackage.bnx.a(r9)     // Catch:{ JSONException -> 0x006a, Exception -> 0x0064 }
            goto L_0x003c
        L_0x003b:
            r9 = r3
        L_0x003c:
            if (r5 == 0) goto L_0x0054
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x004e, Exception -> 0x0048 }
            com.autonavi.common.model.POI r5 = defpackage.bnx.a(r5)     // Catch:{ JSONException -> 0x004e, Exception -> 0x0048 }
            r3 = r5
            goto L_0x0054
        L_0x0048:
            r4 = move-exception
            r7 = r3
            r3 = r9
            r9 = r4
            r4 = r7
            goto L_0x0066
        L_0x004e:
            r4 = move-exception
            r7 = r3
            r3 = r9
            r9 = r4
            r4 = r7
            goto L_0x006c
        L_0x0054:
            if (r9 == 0) goto L_0x0063
            if (r3 != 0) goto L_0x0059
            goto L_0x0063
        L_0x0059:
            java.lang.String r5 = "source"
            java.lang.String r4 = r4.optString(r5)     // Catch:{ JSONException -> 0x004e, Exception -> 0x0048 }
            r2 = r4
            r4 = r3
            r3 = r9
            goto L_0x006f
        L_0x0063:
            return r1
        L_0x0064:
            r9 = move-exception
            r4 = r3
        L_0x0066:
            r9.printStackTrace()
            goto L_0x006f
        L_0x006a:
            r9 = move-exception
            r4 = r3
        L_0x006c:
            r9.printStackTrace()
        L_0x006f:
            qb r9 = r8.a
            com.autonavi.bundle.routecommon.inter.IRouteUI r9 = r9.b()
            if (r9 != 0) goto L_0x0078
            return r1
        L_0x0078:
            com.autonavi.bundle.routecommon.model.RouteType r5 = r9.i()
            com.autonavi.bundle.routecommon.model.RouteType r6 = com.autonavi.bundle.routecommon.model.RouteType.ETRIP
            if (r5 == r6) goto L_0x0081
            return r1
        L_0x0081:
            r9.a(r3, r0, r4)
            r8.a(r2)
            r9 = 1
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.nr.startEtripResultPage(java.lang.String):boolean");
    }

    private void a(boolean z) {
        if (this.a.a(z)) {
            a((String) null);
        }
    }

    private boolean g() {
        if (this.a.d()) {
            boolean l = this.a.l();
            this.a.h();
            if (!l) {
                a(true);
            } else {
                this.a.r();
            }
        }
        return true;
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        boolean z = false;
        if (iRouteHeaderEvent == null) {
            return false;
        }
        switch (iRouteHeaderEvent) {
            case EXCHANGE_CLICK:
                if (!this.a.m()) {
                    a(false);
                    break;
                }
                break;
            case ADD_CLICK:
                this.a.i();
                break;
            case SUMMARY_CLICK:
                this.a.i();
                break;
            case COMPLETE_CLICK:
                z = g();
                break;
        }
        return z;
    }

    private void a(String str) {
        IRouteUI b2 = this.a.b();
        if (b2 != null) {
            PageBundle pageBundle = this.b;
            if (pageBundle == null) {
                pageBundle = new PageBundle();
            } else {
                POI f = b2.f();
                if (f == null) {
                    f = (POI) pageBundle.getObject("bundle_key_poi_end");
                }
                if (f != null) {
                    GeoPoint point = f.getPoint();
                    if (point != null && point.getLatitude() == 0.0d && point.getLongitude() == 0.0d) {
                        return;
                    }
                }
            }
            pageBundle.putBoolean("from_drive_route_page", true);
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("bundle_key_from_page", str);
            }
            b2.a(AjxEtripResultPage.class, RouteType.ETRIP, pageBundle);
        }
    }
}
