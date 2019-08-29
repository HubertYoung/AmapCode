package defpackage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.drive.ajx.module.ModuleRouteCar;
import com.amap.bundle.drive.ajx.module.ModuleRouteCar.IRouteCarModuleListener;
import com.amap.bundle.drive.result.driveresult.result.AjxRouteCarResultPage;
import com.amap.bundle.drive.setting.quicknaviwidget.main.QuickAutonNaviSettingFragment;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.statistics.util.LogUtil;
import com.amap.bundle.utils.ui.ToastHelper;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.bundle.vui.ajx.ModuleVUI;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.AjxContext;

/* renamed from: pt reason: default package */
/* compiled from: DriveRoutePageManager */
public final class pt implements axe, IRouteCarModuleListener, dic {
    public ModuleVUI a;
    /* access modifiers changed from: private */
    public qb b;
    private PageBundle c;
    private int d;
    private ModuleRouteCar e;
    private Handler f = new Handler();
    private String g;
    private AbstractBasePage h;
    /* access modifiers changed from: private */
    public boolean i = false;
    private boolean j = false;
    private boolean k;
    private boolean l;
    private bfa m = new bfa() {
        public final void a(boolean z) {
            if (z && pt.this.b != null) {
                IRouteUI b = pt.this.b.b();
                if (b != null) {
                    b.p();
                }
            }
        }
    };

    public pt(@NonNull AbstractBasePage abstractBasePage, @NonNull AjxContext ajxContext) {
        tj.a("DriveRoutePageManager", "");
        this.h = abstractBasePage;
        this.e = (ModuleRouteCar) Ajx.getInstance().getModuleIns(ajxContext, ModuleRouteCar.MODULE_NAME);
        this.a = (ModuleVUI) Ajx.getInstance().getModuleIns(ajxContext, ModuleVUI.MODULE_NAME);
        if (this.e != null) {
            this.e.setManagerListener(this);
        }
        if (this.a != null) {
            this.a.setMitVuiDialogEventListener(this.m);
        }
        this.b = new qb(abstractBasePage);
        this.b.a();
    }

    public final void a(PageBundle pageBundle) {
        tj.a("DriveRoutePageManager", "");
        this.c = pageBundle;
        if (this.c != null) {
            boolean z = pageBundle.getBoolean("bundle_key_from_scheme", false);
            if (pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE)) {
                this.d = pageBundle.getInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, 1002);
            }
            this.g = this.c.getString("bundle_key_end_poi_name_passed_in", "");
            if (pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE) || pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE) || pageBundle.containsKey("bundle_key_keyword")) {
                this.j = true;
            }
            if (z) {
                if (pageBundle.containsKey("bundle_key_poi_end") || pageBundle.containsKey("bundle_key_end_poi_name_passed_in") || this.d == 1002) {
                    POI poi = (POI) pageBundle.getObject("bundle_key_poi_start");
                    POI poi2 = (POI) pageBundle.getObject("bundle_key_poi_end");
                    if (poi2 != null) {
                        GeoPoint point = poi2.getPoint();
                        if (point != null && point.getLatitude() == 0.0d && point.getLongitude() == 0.0d) {
                            ToastHelper.showLongToast(this.h.getString(R.string.drive_route_end_invalid));
                            return;
                        }
                    }
                    if (bnx.a(poi, poi2)) {
                        ToastHelper.showLongToast(this.h.getString(R.string.route_same_from_to));
                        return;
                    }
                    this.l = a(false);
                } else {
                    ToastHelper.showLongToast(this.h.getString(R.string.drive_route_end_empty));
                    return;
                }
            }
            this.k = TextUtils.isEmpty(DriveUtil.getCarPlateNumber());
        }
    }

    public final void a() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void b() {
        tj.a("DriveRoutePageManager", "");
        this.b.a();
        boolean isEmpty = TextUtils.isEmpty(DriveUtil.getCarPlateNumber());
        if (!(isEmpty == this.k || this.e == null)) {
            this.k = isEmpty;
            this.e.updateCarOwner();
        }
        if (!this.j && !this.l) {
            this.f.post(new Runnable() {
                public final void run() {
                    if (pt.this.b.a(false) && !pt.this.i) {
                        pt.this.b((String) null);
                    }
                }
            });
        }
    }

    public final void c() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void d() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void e() {
        tj.a("DriveRoutePageManager", "");
        if (this.e != null) {
            this.e.setManagerListener(null);
            this.e.release();
        }
        if (this.a != null) {
            this.a.setMitVuiDialogEventListener(null);
            ModuleVUI.mMitVuiDialogEventCallback = null;
        }
        if (this.f != null) {
            this.f.removeCallbacksAndMessages(null);
        }
    }

    public final void a(int i2, ResultType resultType, PageBundle pageBundle) {
        StringBuilder sb = new StringBuilder("requestCode=");
        sb.append(i2);
        sb.append(", resultType=");
        sb.append(resultType);
        sb.append(", data=");
        sb.append(pageBundle);
        tj.a("DriveRoutePageManager", sb.toString());
        if (i2 == 1004) {
            DriveUtil.getPOIHome();
            POI a2 = a(resultType, pageBundle);
            if (a2 != null) {
                auz auz = (auz) a.a.a(auz.class);
                if (auz != null) {
                    auz.a(a2);
                }
                if (this.e != null) {
                    this.e.updateHome();
                }
            }
        } else if (i2 == 1005) {
            DriveUtil.getPOICompany();
            POI a3 = a(resultType, pageBundle);
            if (a3 != null) {
                auz auz2 = (auz) a.a.a(auz.class);
                if (auz2 != null) {
                    auz2.b(a3);
                }
                if (this.e != null) {
                    this.e.updateCompany();
                }
            }
        } else {
            if (i2 == 1000) {
                if (this.e != null) {
                    this.e.updateCarOwner();
                }
            } else if (i2 == 1001 && this.d == 1001) {
                POI a4 = qb.a(resultType, pageBundle);
                if (a4 != null) {
                    this.b.a(a4);
                }
                if (TextUtils.isEmpty(this.g)) {
                    a(true);
                } else {
                    this.b.a(this.g);
                }
            } else if (i2 == 1001 || i2 == 1002 || i2 == 1003) {
                SelectFor selectFor = SelectFor.DEFAULT_POI;
                if (i2 == 1001) {
                    selectFor = SelectFor.FROM_POI;
                } else if (i2 == 1002) {
                    selectFor = SelectFor.TO_POI;
                } else if (pageBundle != null) {
                    selectFor = (SelectFor) pageBundle.getObject("selectedfor");
                }
                final String str = null;
                if (pageBundle != null) {
                    str = pageBundle.getString("bundle_key_from_page");
                }
                if (this.b.a(selectFor, resultType, pageBundle) && this.b.a(false)) {
                    this.b.h();
                    this.f.post(new Runnable() {
                        public final void run() {
                            pt.this.a(str);
                        }
                    });
                }
            } else if (i2 == 1100 && this.e != null) {
                this.e.updateCarOwner();
            }
        }
    }

    public final ResultType f() {
        tj.a("DriveRoutePageManager", "");
        if (this.h.hasViewLayer()) {
            return ResultType.CANCEL;
        }
        if (!this.b.m()) {
            return ResultType.NONE;
        }
        g();
        return ResultType.CANCEL;
    }

    public final void b(PageBundle pageBundle) {
        tj.a("DriveRoutePageManager", "newExtraData=".concat(String.valueOf(pageBundle)));
        this.c = pageBundle;
    }

    /* access modifiers changed from: private */
    public void a(String str) {
        tj.a("DriveRoutePageManager", "frompage=".concat(String.valueOf(str)));
        b(str);
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        IRouteUI b2 = this.b.b();
        if (b2 != null && b2.i().equals(RouteType.CAR)) {
            PageBundle pageBundle = this.c;
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
            tj.a((String) "\n------------------------------------");
            tj.a((String) "car-search-click_1.1");
            b2.a(AjxRouteCarResultPage.class, RouteType.CAR, pageBundle);
            this.f.removeCallbacksAndMessages(null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:56:0x00ed A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startRouteCarResultPage(java.lang.String r13) {
        /*
            r12 = this;
            java.lang.String r0 = "DriveRoutePageManager"
            java.lang.String r1 = "param="
            java.lang.String r2 = java.lang.String.valueOf(r13)
            java.lang.String r1 = r1.concat(r2)
            defpackage.tj.a(r0, r1)
            qb r0 = r12.b
            com.autonavi.bundle.routecommon.inter.IRouteUI r0 = r0.b()
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0037
            int r3 = r0.r()
            if (r3 == r1) goto L_0x0037
            java.lang.String r13 = "DriveRoutePageManager"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r3 = "getPageLevel="
            r1.<init>(r3)
            int r0 = r0.r()
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            defpackage.tj.a(r13, r0)
            return r2
        L_0x0037:
            boolean r3 = android.text.TextUtils.isEmpty(r13)
            if (r3 == 0) goto L_0x003e
            return r2
        L_0x003e:
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            java.lang.String r4 = ""
            r5 = 0
            org.json.JSONObject r6 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            r6.<init>(r13)     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            java.lang.String r13 = "start_poi"
            org.json.JSONObject r13 = r6.optJSONObject(r13)     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            java.lang.String r7 = "end_poi"
            org.json.JSONObject r7 = r6.optJSONObject(r7)     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            java.lang.String r8 = "middle_pois"
            org.json.JSONArray r8 = r6.optJSONArray(r8)     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            if (r13 == 0) goto L_0x0068
            java.lang.String r13 = r13.toString()     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            com.autonavi.common.model.POI r13 = defpackage.bnx.a(r13)     // Catch:{ JSONException -> 0x00e5, Exception -> 0x00de }
            goto L_0x0069
        L_0x0068:
            r13 = r5
        L_0x0069:
            if (r7 == 0) goto L_0x007b
            java.lang.String r7 = r7.toString()     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            com.autonavi.common.model.POI r7 = defpackage.bnx.a(r7)     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            r5 = r7
            goto L_0x007b
        L_0x0075:
            r6 = move-exception
            goto L_0x00e1
        L_0x0078:
            r6 = move-exception
            goto L_0x00e8
        L_0x007b:
            if (r8 == 0) goto L_0x00a8
            int r7 = r8.length()     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            if (r7 <= 0) goto L_0x00a8
            r7 = 0
        L_0x0084:
            int r9 = r8.length()     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            if (r7 >= r9) goto L_0x00a8
            org.json.JSONObject r9 = r8.optJSONObject(r7)     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            if (r9 == 0) goto L_0x00a5
            java.lang.String r10 = r9.toString()     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            boolean r10 = android.text.TextUtils.isEmpty(r10)     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            if (r10 != 0) goto L_0x00a5
            java.lang.String r9 = r9.toString()     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            com.autonavi.common.model.POI r9 = defpackage.bnx.a(r9)     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            r3.add(r9)     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
        L_0x00a5:
            int r7 = r7 + 1
            goto L_0x0084
        L_0x00a8:
            if (r13 == 0) goto L_0x00dd
            if (r5 != 0) goto L_0x00ad
            goto L_0x00dd
        L_0x00ad:
            java.lang.String r7 = "source"
            java.lang.String r6 = r6.optString(r7)     // Catch:{ JSONException -> 0x0078, Exception -> 0x0075 }
            java.lang.String r4 = "DriveRoutePageManager"
            java.lang.String r7 = "startRouteCarResultPage fromPage:"
            java.lang.String r8 = java.lang.String.valueOf(r6)     // Catch:{ JSONException -> 0x00d8, Exception -> 0x00d3 }
            java.lang.String r7 = r7.concat(r8)     // Catch:{ JSONException -> 0x00d8, Exception -> 0x00d3 }
            com.amap.bundle.logs.AMapLog.i(r4, r7)     // Catch:{ JSONException -> 0x00d8, Exception -> 0x00d3 }
            java.lang.String r4 = "planend_record"
            boolean r4 = r4.equals(r6)     // Catch:{ JSONException -> 0x00d8, Exception -> 0x00d3 }
            if (r4 == 0) goto L_0x00d1
            td r4 = defpackage.td.a()     // Catch:{ JSONException -> 0x00d8, Exception -> 0x00d3 }
            r4.a(r1)     // Catch:{ JSONException -> 0x00d8, Exception -> 0x00d3 }
        L_0x00d1:
            r4 = r6
            goto L_0x00eb
        L_0x00d3:
            r4 = move-exception
            r11 = r6
            r6 = r4
            r4 = r11
            goto L_0x00e1
        L_0x00d8:
            r4 = move-exception
            r11 = r6
            r6 = r4
            r4 = r11
            goto L_0x00e8
        L_0x00dd:
            return r2
        L_0x00de:
            r13 = move-exception
            r6 = r13
            r13 = r5
        L_0x00e1:
            r6.printStackTrace()
            goto L_0x00eb
        L_0x00e5:
            r13 = move-exception
            r6 = r13
            r13 = r5
        L_0x00e8:
            r6.printStackTrace()
        L_0x00eb:
            if (r0 != 0) goto L_0x00ee
            return r2
        L_0x00ee:
            r0.a(r13, r3, r5)
            r12.a(r4)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pt.startRouteCarResultPage(java.lang.String):boolean");
    }

    private static POI a(ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK != resultType || pageBundle == null || !pageBundle.containsKey("result_poi")) {
            return null;
        }
        return (POI) pageBundle.getObject("result_poi");
    }

    private boolean a(boolean z) {
        if (!this.b.a(z)) {
            return false;
        }
        a((String) null);
        return true;
    }

    private boolean g() {
        if (this.b.d()) {
            boolean l2 = this.b.l();
            this.b.h();
            if (!l2) {
                a(true);
            } else {
                this.b.r();
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
                LogManager.actionLogV2("P00014", "B061", LogUtil.createPairJSONObj("type", H5PageData.KEY_UC_START));
                break;
            case EXCHANGE_CLICK:
                if (!this.b.m()) {
                    a(false);
                    break;
                }
                break;
            case END_CLICK:
                LogManager.actionLogV2("P00014", "B061", LogUtil.createPairJSONObj("type", "end"));
                break;
            case ADD_CLICK:
                this.b.i();
                LogManager.actionLogV2("P00014", "B062");
                break;
            case SUMMARY_CLICK:
                this.b.i();
                break;
            case COMPLETE_CLICK:
                z = g();
                break;
        }
        return z;
    }
}
