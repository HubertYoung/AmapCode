package defpackage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.drive.ajx.module.ModuleRouteTruck;
import com.amap.bundle.drive.ajx.module.ModuleRouteTruck.IRouteTruckModuleListener;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteTruckResultPage;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.AjxContext;

/* renamed from: pv reason: default package */
/* compiled from: RouteTruckPageManager */
public final class pv implements axe, IRouteTruckModuleListener, dic {
    /* access modifiers changed from: private */
    public qb a;
    private PageBundle b;
    private int c;
    private ModuleRouteTruck d;
    private Handler e = new Handler();
    private String f;
    private AbstractBasePage g;
    private boolean h;

    public pv(@NonNull AbstractBasePage abstractBasePage, @NonNull AjxContext ajxContext) {
        tj.a("DriveRoutePageManager", "");
        this.g = abstractBasePage;
        this.d = (ModuleRouteTruck) Ajx.getInstance().getModuleIns(ajxContext, ModuleRouteTruck.MODULE_NAME);
        if (this.d != null) {
            this.d.setManagerListener(this);
        }
        this.a = new qb(abstractBasePage);
        this.a.a();
    }

    public final void a(PageBundle pageBundle) {
        tj.a("DriveRoutePageManager", "");
        this.b = pageBundle;
        if (this.b != null) {
            boolean z = pageBundle.getBoolean("bundle_key_from_scheme", false);
            if (pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE)) {
                this.c = pageBundle.getInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, 1002);
            }
            this.f = this.b.getString("bundle_key_end_poi_name_passed_in", "");
            if (z) {
                if (pageBundle.containsKey("bundle_key_poi_end") || pageBundle.containsKey("bundle_key_end_poi_name_passed_in") || this.c == 1002) {
                    POI poi = (POI) pageBundle.getObject("bundle_key_poi_start");
                    POI poi2 = (POI) pageBundle.getObject("bundle_key_poi_end");
                    if (poi2 != null) {
                        GeoPoint point = poi2.getPoint();
                        if (point != null && point.getLatitude() == 0.0d && point.getLongitude() == 0.0d) {
                            ToastHelper.showLongToast(this.g.getString(R.string.drive_route_end_invalid));
                            return;
                        }
                    }
                    if (bnx.a(poi, poi2)) {
                        ToastHelper.showLongToast(this.g.getString(R.string.route_same_from_to));
                        return;
                    }
                    a(false);
                } else {
                    ToastHelper.showLongToast(this.g.getString(R.string.drive_route_end_empty));
                    return;
                }
            }
            this.h = TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber());
        }
    }

    public final void a() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void b() {
        tj.a("DriveRoutePageManager", "");
        this.a.a();
        if (this.d != null) {
            boolean isEmpty = TextUtils.isEmpty(DriveUtil.getTruckCarPlateNumber());
            if (isEmpty != this.h) {
                this.h = isEmpty;
                this.d.updateCarInfo();
            }
        }
        this.e.post(new Runnable() {
            public final void run() {
                if (pv.this.a.a(false)) {
                    pv.this.a((String) null);
                }
            }
        });
    }

    public final void c() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void d() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void e() {
        tj.a("DriveRoutePageManager", "");
        if (this.d != null) {
            this.d.setManagerListener(null);
            this.d.release();
        }
        if (this.e != null) {
            this.e.removeCallbacksAndMessages(null);
        }
    }

    /* JADX WARNING: type inference failed for: r2v3 */
    /* JADX WARNING: type inference failed for: r2v4, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v5, types: [java.lang.String] */
    /* JADX WARNING: type inference failed for: r2v6, types: [cop] */
    /* JADX WARNING: type inference failed for: r2v7, types: [cop] */
    /* JADX WARNING: type inference failed for: r2v8 */
    /* JADX WARNING: type inference failed for: r2v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r2v3
      assigns: [?[int, float, boolean, short, byte, char, OBJECT, ARRAY], cop, java.lang.String]
      uses: [java.lang.String, ?[int, boolean, OBJECT, ARRAY, byte, short, char], cop]
      mth insns count: 92
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:49)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:49)
    	at jadx.core.ProcessClass.process(ProcessClass.java:35)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(int r5, com.autonavi.common.Page.ResultType r6, com.autonavi.common.PageBundle r7) {
        /*
            r4 = this;
            java.lang.String r0 = "DriveRoutePageManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "requestCode="
            r1.<init>(r2)
            r1.append(r5)
            java.lang.String r2 = ", resultType="
            r1.append(r2)
            r1.append(r6)
            java.lang.String r2 = ", data="
            r1.append(r2)
            r1.append(r7)
            java.lang.String r1 = r1.toString()
            defpackage.tj.a(r0, r1)
            r0 = 1001(0x3e9, float:1.403E-42)
            if (r5 != r0) goto L_0x004b
            int r1 = r4.c
            if (r1 != r0) goto L_0x004b
            com.autonavi.common.model.POI r5 = defpackage.qb.a(r6, r7)
            if (r5 == 0) goto L_0x0036
            qb r6 = r4.a
            r6.a(r5)
        L_0x0036:
            java.lang.String r5 = r4.f
            boolean r5 = android.text.TextUtils.isEmpty(r5)
            if (r5 == 0) goto L_0x0043
            r5 = 1
            r4.a(r5)
            return
        L_0x0043:
            qb r5 = r4.a
            java.lang.String r6 = r4.f
            r5.a(r6)
            return
        L_0x004b:
            r1 = 1002(0x3ea, float:1.404E-42)
            r2 = 0
            if (r5 == r0) goto L_0x00c2
            if (r5 == r1) goto L_0x00c2
            r3 = 1003(0x3eb, float:1.406E-42)
            if (r5 != r3) goto L_0x0057
            goto L_0x00c2
        L_0x0057:
            r0 = 240(0xf0, float:3.36E-43)
            if (r5 != r0) goto L_0x00a6
            com.autonavi.common.Page$ResultType r5 = com.autonavi.common.Page.ResultType.OK
            if (r5 != r6) goto L_0x0072
            if (r7 == 0) goto L_0x0072
            java.lang.String r5 = "result_poi"
            boolean r5 = r7.containsKey(r5)
            if (r5 == 0) goto L_0x0072
            java.lang.String r5 = "result_poi"
            java.lang.Object r5 = r7.getObject(r5)
            com.autonavi.common.model.POI r5 = (com.autonavi.common.model.POI) r5
            goto L_0x0073
        L_0x0072:
            r5 = r2
        L_0x0073:
            if (r5 == 0) goto L_0x00a5
            java.lang.Class<com.amap.bundle.datamodel.FavoritePOI> r6 = com.amap.bundle.datamodel.FavoritePOI.class
            com.autonavi.common.model.POI r5 = r5.as(r6)
            com.amap.bundle.datamodel.FavoritePOI r5 = (com.amap.bundle.datamodel.FavoritePOI) r5
            java.lang.String r6 = ""
            r5.setCommonName(r6)
            java.lang.Class<com> r6 = defpackage.com.class
            java.lang.Object r6 = defpackage.ank.a(r6)
            com r6 = (defpackage.com) r6
            if (r6 != 0) goto L_0x008d
            goto L_0x0095
        L_0x008d:
            java.lang.String r7 = r6.a()
            cop r2 = r6.b(r7)
        L_0x0095:
            if (r2 == 0) goto L_0x00a5
            r2.b(r5)
            com.autonavi.map.fragmentcontainer.page.AbstractBasePage r5 = r4.g
            int r6 = com.autonavi.minimap.R.string.route_add_success
            java.lang.String r5 = r5.getString(r6)
            com.amap.bundle.utils.ui.ToastHelper.showLongToast(r5)
        L_0x00a5:
            return
        L_0x00a6:
            r6 = 1000(0x3e8, float:1.401E-42)
            if (r5 != r6) goto L_0x00b4
            com.amap.bundle.drive.ajx.module.ModuleRouteTruck r5 = r4.d
            if (r5 == 0) goto L_0x00c1
            com.amap.bundle.drive.ajx.module.ModuleRouteTruck r5 = r4.d
            r5.updatePreference()
            return
        L_0x00b4:
            r6 = 1100(0x44c, float:1.541E-42)
            if (r5 != r6) goto L_0x00c1
            com.amap.bundle.drive.ajx.module.ModuleRouteTruck r5 = r4.d
            if (r5 == 0) goto L_0x00c1
            com.amap.bundle.drive.ajx.module.ModuleRouteTruck r5 = r4.d
            r5.updateCarInfo()
        L_0x00c1:
            return
        L_0x00c2:
            com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r3 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.DEFAULT_POI
            if (r5 != r0) goto L_0x00c9
            com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r3 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.FROM_POI
            goto L_0x00d9
        L_0x00c9:
            if (r5 != r1) goto L_0x00ce
            com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r3 = com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor.TO_POI
            goto L_0x00d9
        L_0x00ce:
            if (r7 == 0) goto L_0x00d9
            java.lang.String r5 = "selectedfor"
            java.lang.Object r5 = r7.getObject(r5)
            r3 = r5
            com.autonavi.common.utils.Constant$SelectPoiFromMapFragment$SelectFor r3 = (com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor) r3
        L_0x00d9:
            if (r7 == 0) goto L_0x00e1
            java.lang.String r5 = "bundle_key_from_page"
            java.lang.String r2 = r7.getString(r5)
        L_0x00e1:
            qb r5 = r4.a
            boolean r5 = r5.a(r3, r6, r7)
            if (r5 == 0) goto L_0x0101
            qb r5 = r4.a
            r6 = 0
            boolean r5 = r5.a(r6)
            if (r5 == 0) goto L_0x0101
            qb r5 = r4.a
            r5.h()
            android.os.Handler r5 = r4.e
            pv$2 r6 = new pv$2
            r6.<init>(r2)
            r5.post(r6)
        L_0x0101:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pv.a(int, com.autonavi.common.Page$ResultType, com.autonavi.common.PageBundle):void");
    }

    public final ResultType f() {
        tj.a("DriveRoutePageManager", "");
        if (this.g.hasViewLayer()) {
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

    /* access modifiers changed from: private */
    public void a(String str) {
        IRouteUI b2 = this.a.b();
        if (b2 != null) {
            PageBundle pageBundle = this.b;
            if (pageBundle == null) {
                pageBundle = new PageBundle();
            } else {
                POI f2 = b2.f();
                if (f2 == null) {
                    f2 = (POI) pageBundle.getObject("bundle_key_poi_end");
                }
                if (f2 != null) {
                    GeoPoint point = f2.getPoint();
                    if (point != null && point.getLatitude() == 0.0d && point.getLongitude() == 0.0d) {
                        return;
                    }
                }
            }
            pageBundle.putBoolean("from_drive_route_page", true);
            if (!TextUtils.isEmpty(str)) {
                pageBundle.putString("bundle_key_from_page", str);
            }
            if (DriveUtil.isCarTruckInfoEmpty()) {
                b2.n();
            }
            b2.a(AjxRouteTruckResultPage.class, RouteType.TRUCK, pageBundle);
            this.e.removeCallbacksAndMessages(null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00a2 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00a3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startRouteTruckResultPage(java.lang.String r10) {
        /*
            r9 = this;
            java.lang.String r0 = "DriveRoutePageManager"
            java.lang.String r1 = "param="
            java.lang.String r2 = java.lang.String.valueOf(r10)
            java.lang.String r1 = r1.concat(r2)
            defpackage.tj.a(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r10)
            r1 = 0
            if (r0 == 0) goto L_0x0017
            return r1
        L_0x0017:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r2 = ""
            r3 = 0
            org.json.JSONObject r4 = new org.json.JSONObject     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            r4.<init>(r10)     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            java.lang.String r10 = "start_poi"
            org.json.JSONObject r10 = r4.optJSONObject(r10)     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            java.lang.String r5 = "end_poi"
            org.json.JSONObject r5 = r4.optJSONObject(r5)     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            java.lang.String r6 = "middle_pois"
            org.json.JSONArray r6 = r4.optJSONArray(r6)     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            if (r10 == 0) goto L_0x0041
            java.lang.String r10 = r10.toString()     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            com.autonavi.common.model.POI r10 = defpackage.bnx.a(r10)     // Catch:{ JSONException -> 0x0094, Exception -> 0x008d }
            goto L_0x0042
        L_0x0041:
            r10 = r3
        L_0x0042:
            if (r5 == 0) goto L_0x0052
            java.lang.String r5 = r5.toString()     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            com.autonavi.common.model.POI r5 = defpackage.bnx.a(r5)     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            r3 = r5
            goto L_0x0052
        L_0x004e:
            r4 = move-exception
            goto L_0x0090
        L_0x0050:
            r4 = move-exception
            goto L_0x0097
        L_0x0052:
            if (r6 == 0) goto L_0x007f
            int r5 = r6.length()     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            if (r5 <= 0) goto L_0x007f
            r5 = 0
        L_0x005b:
            int r7 = r6.length()     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            if (r5 >= r7) goto L_0x007f
            org.json.JSONObject r7 = r6.optJSONObject(r5)     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            if (r7 == 0) goto L_0x007c
            java.lang.String r8 = r7.toString()     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            boolean r8 = android.text.TextUtils.isEmpty(r8)     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            if (r8 != 0) goto L_0x007c
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            com.autonavi.common.model.POI r7 = defpackage.bnx.a(r7)     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            r0.add(r7)     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
        L_0x007c:
            int r5 = r5 + 1
            goto L_0x005b
        L_0x007f:
            if (r10 == 0) goto L_0x008c
            if (r3 != 0) goto L_0x0084
            goto L_0x008c
        L_0x0084:
            java.lang.String r5 = "source"
            java.lang.String r4 = r4.optString(r5)     // Catch:{ JSONException -> 0x0050, Exception -> 0x004e }
            r2 = r4
            goto L_0x009a
        L_0x008c:
            return r1
        L_0x008d:
            r10 = move-exception
            r4 = r10
            r10 = r3
        L_0x0090:
            r4.printStackTrace()
            goto L_0x009a
        L_0x0094:
            r10 = move-exception
            r4 = r10
            r10 = r3
        L_0x0097:
            r4.printStackTrace()
        L_0x009a:
            qb r4 = r9.a
            com.autonavi.bundle.routecommon.inter.IRouteUI r4 = r4.b()
            if (r4 != 0) goto L_0x00a3
            return r1
        L_0x00a3:
            r4.a(r10, r0, r3)
            r9.a(r2)
            r10 = 1
            return r10
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pv.startRouteTruckResultPage(java.lang.String):boolean");
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
            case START_CLICK:
                LogManager.actionLogV2("P00329", "B003", LogUtil.createPairJSONObj("type", H5PageData.KEY_UC_START));
                break;
            case EXCHANGE_CLICK:
                if (!this.a.m()) {
                    a(false);
                    break;
                }
                break;
            case END_CLICK:
                LogManager.actionLogV2("P00329", "B003", LogUtil.createPairJSONObj("type", "end"));
                break;
            case ADD_CLICK:
                this.a.i();
                LogManager.actionLogV2("P00329", "B004");
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
}
