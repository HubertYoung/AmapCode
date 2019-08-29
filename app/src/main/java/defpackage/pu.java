package defpackage;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.alipay.mobile.h5container.api.H5PageData;
import com.amap.bundle.drive.ajx.module.ModuleRouteMotor;
import com.amap.bundle.drive.ajx.module.ModuleRouteMotor.IRouteMotorModuleListener;
import com.amap.bundle.drive.result.motorresult.result.AjxRouteMotorResultPage;
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
import com.autonavi.common.utils.Constant.SelectPoiFromMapFragment.SelectFor;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.AjxContext;

/* renamed from: pu reason: default package */
/* compiled from: RouteMotorPageManager */
public final class pu implements axe, IRouteMotorModuleListener, dic {
    /* access modifiers changed from: private */
    public qb a;
    private PageBundle b;
    private int c;
    private ModuleRouteMotor d;
    private Handler e = new Handler();
    private String f;
    private AbstractBasePage g;
    /* access modifiers changed from: private */
    public boolean h = false;
    private boolean i = false;
    private boolean j;
    private boolean k;
    private boolean l;

    public pu(@NonNull AbstractBasePage abstractBasePage, @NonNull AjxContext ajxContext) {
        tj.a("DriveRoutePageManager", "");
        this.g = abstractBasePage;
        this.d = (ModuleRouteMotor) Ajx.getInstance().getModuleIns(ajxContext, ModuleRouteMotor.MODULE_NAME);
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
            this.l = pageBundle.getBoolean("bundle_key_from_scheme", false);
            if (pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE)) {
                this.c = pageBundle.getInt(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE, 1002);
            }
            this.f = this.b.getString("bundle_key_end_poi_name_passed_in", "");
            if (pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE) || pageBundle.containsKey(QuickAutonNaviSettingFragment.BUNDLE_KEY_REQUEST_CODE) || pageBundle.containsKey("bundle_key_keyword")) {
                this.i = true;
            }
            if (this.l) {
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
                    this.k = a(false);
                } else {
                    ToastHelper.showLongToast(this.g.getString(R.string.drive_route_end_empty));
                    return;
                }
            }
            this.j = TextUtils.isEmpty(DriveUtil.getCarPlateNumber());
        }
    }

    public final void a() {
        tj.a("DriveRoutePageManager", "");
    }

    public final void b() {
        tj.a("DriveRoutePageManager", "");
        this.a.a();
        boolean isEmpty = TextUtils.isEmpty(DriveUtil.getCarPlateNumber());
        if (!(isEmpty == this.j || this.d == null)) {
            this.j = isEmpty;
            this.d.updateCarOwner();
        }
        if (!this.i && !this.k) {
            this.e.post(new Runnable() {
                public final void run() {
                    if (pu.this.a.a(false) && !pu.this.h) {
                        pu.this.b((String) null);
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
        if (this.d != null) {
            this.d.setManagerListener(null);
            this.d.release();
        }
        if (this.e != null) {
            this.e.removeCallbacksAndMessages(null);
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
        if (i2 == 1000) {
            if (this.d != null) {
                this.d.updatePreference();
                this.d.updateCarOwner();
            }
        } else if (i2 == 1001 && this.c == 1001) {
            POI a2 = qb.a(resultType, pageBundle);
            if (a2 != null) {
                this.a.a(a2);
            }
            if (TextUtils.isEmpty(this.f)) {
                a(true);
            } else {
                this.a.a(this.f);
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
            if (this.a.a(selectFor, resultType, pageBundle) && this.a.a(false)) {
                this.a.h();
                this.e.post(new Runnable() {
                    public final void run() {
                        pu.this.a(str);
                    }
                });
            }
        } else if (i2 == 1100 && this.d != null) {
            this.d.updateCarOwner();
            this.d.updatePreference();
        }
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
        tj.a("DriveRoutePageManager", "frompage=".concat(String.valueOf(str)));
        b(str);
    }

    /* access modifiers changed from: private */
    public void b(String str) {
        IRouteUI b2 = this.a.b();
        if (b2 != null && b2.i().equals(RouteType.MOTOR)) {
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
            if (this.l) {
                pageBundle.putString("bundle_key_source", "scheme");
            }
            tj.a((String) "\n------------------------------------");
            tj.a((String) "car-search-click_1.1");
            b2.a(AjxRouteMotorResultPage.class, RouteType.MOTOR, pageBundle);
            this.e.removeCallbacksAndMessages(null);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00cd A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ce  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean startRouteMotorResultPage(java.lang.String r12) {
        /*
            r11 = this;
            java.lang.String r0 = "DriveRoutePageManager"
            java.lang.String r1 = "param="
            java.lang.String r2 = java.lang.String.valueOf(r12)
            java.lang.String r1 = r1.concat(r2)
            defpackage.tj.a(r0, r1)
            boolean r0 = android.text.TextUtils.isEmpty(r12)
            r1 = 0
            if (r0 == 0) goto L_0x0017
            return r1
        L_0x0017:
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.lang.String r2 = ""
            r3 = 0
            r4 = 1
            org.json.JSONObject r5 = new org.json.JSONObject     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            r5.<init>(r12)     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            java.lang.String r12 = "start_poi"
            org.json.JSONObject r12 = r5.optJSONObject(r12)     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            java.lang.String r6 = "end_poi"
            org.json.JSONObject r6 = r5.optJSONObject(r6)     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            java.lang.String r7 = "middle_pois"
            org.json.JSONArray r7 = r5.optJSONArray(r7)     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            if (r12 == 0) goto L_0x0042
            java.lang.String r12 = r12.toString()     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            com.autonavi.common.model.POI r12 = defpackage.bnx.a(r12)     // Catch:{ JSONException -> 0x00bf, Exception -> 0x00b8 }
            goto L_0x0043
        L_0x0042:
            r12 = r3
        L_0x0043:
            if (r6 == 0) goto L_0x0055
            java.lang.String r6 = r6.toString()     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            com.autonavi.common.model.POI r6 = defpackage.bnx.a(r6)     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            r3 = r6
            goto L_0x0055
        L_0x004f:
            r5 = move-exception
            goto L_0x00bb
        L_0x0052:
            r5 = move-exception
            goto L_0x00c2
        L_0x0055:
            if (r7 == 0) goto L_0x0082
            int r6 = r7.length()     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            if (r6 <= 0) goto L_0x0082
            r6 = 0
        L_0x005e:
            int r8 = r7.length()     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            if (r6 >= r8) goto L_0x0082
            org.json.JSONObject r8 = r7.optJSONObject(r6)     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            if (r8 == 0) goto L_0x007f
            java.lang.String r9 = r8.toString()     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            boolean r9 = android.text.TextUtils.isEmpty(r9)     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            if (r9 != 0) goto L_0x007f
            java.lang.String r8 = r8.toString()     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            com.autonavi.common.model.POI r8 = defpackage.bnx.a(r8)     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            r0.add(r8)     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
        L_0x007f:
            int r6 = r6 + 1
            goto L_0x005e
        L_0x0082:
            if (r12 == 0) goto L_0x00b7
            if (r3 != 0) goto L_0x0087
            goto L_0x00b7
        L_0x0087:
            java.lang.String r6 = "source"
            java.lang.String r5 = r5.optString(r6)     // Catch:{ JSONException -> 0x0052, Exception -> 0x004f }
            java.lang.String r2 = "DriveRoutePageManager"
            java.lang.String r6 = "startRouteCarResultPage fromPage:"
            java.lang.String r7 = java.lang.String.valueOf(r5)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            java.lang.String r6 = r6.concat(r7)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            com.amap.bundle.logs.AMapLog.i(r2, r6)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            java.lang.String r2 = "planend_record"
            boolean r2 = r2.equals(r5)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            if (r2 == 0) goto L_0x00ab
            td r2 = defpackage.td.a()     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
            r2.a(r4)     // Catch:{ JSONException -> 0x00b2, Exception -> 0x00ad }
        L_0x00ab:
            r2 = r5
            goto L_0x00c5
        L_0x00ad:
            r2 = move-exception
            r10 = r5
            r5 = r2
            r2 = r10
            goto L_0x00bb
        L_0x00b2:
            r2 = move-exception
            r10 = r5
            r5 = r2
            r2 = r10
            goto L_0x00c2
        L_0x00b7:
            return r1
        L_0x00b8:
            r12 = move-exception
            r5 = r12
            r12 = r3
        L_0x00bb:
            r5.printStackTrace()
            goto L_0x00c5
        L_0x00bf:
            r12 = move-exception
            r5 = r12
            r12 = r3
        L_0x00c2:
            r5.printStackTrace()
        L_0x00c5:
            qb r5 = r11.a
            com.autonavi.bundle.routecommon.inter.IRouteUI r5 = r5.b()
            if (r5 != 0) goto L_0x00ce
            return r1
        L_0x00ce:
            r5.a(r12, r0, r3)
            r11.a(r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.pu.startRouteMotorResultPage(java.lang.String):boolean");
    }

    private boolean a(boolean z) {
        if (!this.a.a(z)) {
            return false;
        }
        a((String) null);
        return true;
    }

    private boolean g() {
        if (this.a.d()) {
            boolean l2 = this.a.l();
            this.a.h();
            if (!l2) {
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
                LogManager.actionLogV2("P00014", "B061", LogUtil.createPairJSONObj("type", H5PageData.KEY_UC_START));
                break;
            case EXCHANGE_CLICK:
                if (!this.a.m()) {
                    a(false);
                    break;
                }
                break;
            case END_CLICK:
                LogManager.actionLogV2("P00014", "B061", LogUtil.createPairJSONObj("type", "end"));
                break;
            case ADD_CLICK:
                this.a.i();
                LogManager.actionLogV2("P00014", "B062");
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
