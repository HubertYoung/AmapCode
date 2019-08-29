package com.amap.bundle.drive.result.driveresult.browser;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxRouteCarResultBrowserPage extends Ajx3Page implements mr {
    ModuleRouteDriveResult a;
    public boolean b = false;
    mq c = new mq() {
        /* JADX WARNING: Removed duplicated region for block: B:12:0x0028  */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x002e  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void a(java.lang.String r3, java.lang.String r4) {
            /*
                r2 = this;
                int r0 = r3.hashCode()
                r1 = -1203606180(0xffffffffb8426d5c, float:-4.6355053E-5)
                if (r0 == r1) goto L_0x0019
                r1 = 553280922(0x20fa659a, float:4.2418882E-19)
                if (r0 == r1) goto L_0x000f
                goto L_0x0023
            L_0x000f:
                java.lang.String r0 = "carNavi"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0023
                r3 = 0
                goto L_0x0024
            L_0x0019:
                java.lang.String r0 = "errorReport"
                boolean r3 = r3.equals(r0)
                if (r3 == 0) goto L_0x0023
                r3 = 1
                goto L_0x0024
            L_0x0023:
                r3 = -1
            L_0x0024:
                switch(r3) {
                    case 0: goto L_0x002e;
                    case 1: goto L_0x0028;
                    default: goto L_0x0027;
                }
            L_0x0027:
                goto L_0x0034
            L_0x0028:
                com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage r3 = com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage.this
                com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage.b(r3, r4)
                goto L_0x0034
            L_0x002e:
                com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage r3 = com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage.this
                com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage.a(r3, r4)
                return
            L_0x0034:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage.AnonymousClass2.a(java.lang.String, java.lang.String):void");
        }
    };
    private qc d;
    private int e = 0;
    private boolean f = false;
    private ph g;
    private OnClickListener h = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.btn_error_report) {
                AjxRouteCarResultBrowserPage ajxRouteCarResultBrowserPage = AjxRouteCarResultBrowserPage.this;
                String string = AMapAppGlobal.getApplication().getString(R.string.action_log_type_car);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B022", jSONObject);
                if (ajxRouteCarResultBrowserPage.a != null) {
                    ajxRouteCarResultBrowserPage.a.callbackErrorReportClick(true);
                }
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        PageBundle arguments = getArguments();
        this.f = arguments.getBoolean("is_from_etrip", false);
        this.g = (ph) arguments.getObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        this.e = arguments.getInt("route_car_type_key");
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.ajx_route_car_result_browser_page, null);
        viewGroup.addView(amapAjxView, new LayoutParams(-1, -1));
        return viewGroup;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.a = (ModuleRouteDriveResult) this.mAjxView.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
        if (this.a != null) {
            this.a.addSwitchSceneListener(this.c);
        }
    }

    public void onJsBack(Object obj, String str) {
        super.onJsBack(obj, str);
        finish();
    }

    public boolean backPressed() {
        return super.backPressed();
    }

    public Ajx3PagePresenter createPresenter() {
        return new or(this);
    }

    public void pause() {
        super.pause();
    }

    public void resume() {
        super.resume();
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.a(true);
            mapView.a(mapView.p(false), 0, 1);
        }
        if (bno.a) {
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("[");
            sb.append(Ajx3Page.TAG);
            sb.append("]onResume#setMapModeAndStyle#getMapView().getMapMode(false):");
            sb.append(getMapManager().getMapView().p(false));
            a2.c("NaviMonitor", sb.toString());
        }
        if (this.f) {
            this.d.b();
        }
        this.d.d();
    }

    public void destroy() {
        super.destroy();
        if (this.a != null) {
            this.a.removeSwitchSceneListener(this.c);
        }
    }

    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        super.result(i, resultType, pageBundle);
    }

    public View getMapSuspendView() {
        this.d = new qc(this);
        this.d.a = this.h;
        this.d.c();
        View suspendView = this.d.getSuspendView();
        if (euk.a()) {
            suspendView.setPadding(suspendView.getPaddingLeft(), suspendView.getPaddingTop() + euk.a(getContext()), suspendView.getPaddingRight(), suspendView.getPaddingBottom());
        }
        return suspendView;
    }

    static /* synthetic */ void a(AjxRouteCarResultBrowserPage ajxRouteCarResultBrowserPage, String str) {
        if (!TextUtils.isEmpty(str)) {
            boolean z = true;
            if (ajxRouteCarResultBrowserPage.e == 1) {
                z = false;
            }
            nm.a(ajxRouteCarResultBrowserPage.getActivity(), str, (b) null, z ? "car" : DriveUtil.NAVI_TYPE_TRUCK);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x00c7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void b(com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage r8, java.lang.String r9) {
        /*
            r0 = 0
            r1 = 15
            org.json.JSONObject r2 = new org.json.JSONObject     // Catch:{ JSONException -> 0x003c }
            r2.<init>(r9)     // Catch:{ JSONException -> 0x003c }
            java.lang.String r3 = "focusIndex"
            int r3 = r2.optInt(r3)     // Catch:{ JSONException -> 0x003c }
            java.lang.String r4 = "fromType"
            boolean r4 = r2.has(r4)     // Catch:{ JSONException -> 0x003a }
            if (r4 == 0) goto L_0x001d
            java.lang.String r4 = "fromType"
            int r4 = r2.optInt(r4)     // Catch:{ JSONException -> 0x003a }
            r1 = r4
        L_0x001d:
            java.lang.String r4 = "routeID"
            org.json.JSONArray r2 = r2.optJSONArray(r4)     // Catch:{ JSONException -> 0x003a }
            int r4 = r2.length()     // Catch:{ JSONException -> 0x003a }
            int[] r5 = new int[r4]     // Catch:{ JSONException -> 0x003a }
        L_0x002a:
            if (r0 >= r4) goto L_0x0035
            int r6 = r2.getInt(r0)     // Catch:{ JSONException -> 0x003a }
            r5[r0] = r6     // Catch:{ JSONException -> 0x003a }
            int r0 = r0 + 1
            goto L_0x002a
        L_0x0035:
            long r4 = com.autonavi.jni.eyrie.amap.tbt.NaviManager.createPathResult(r5)     // Catch:{ JSONException -> 0x003a }
            goto L_0x0044
        L_0x003a:
            r0 = move-exception
            goto L_0x003f
        L_0x003c:
            r2 = move-exception
            r0 = r2
            r3 = 0
        L_0x003f:
            r0.printStackTrace()
            r4 = 0
        L_0x0044:
            java.lang.String r0 = TAG
            java.lang.String r2 = "startErrorReport--params="
            java.lang.String r9 = java.lang.String.valueOf(r9)
            java.lang.String r9 = r2.concat(r9)
            com.amap.bundle.logs.AMapLog.d(r0, r9)
            com.autonavi.common.PageBundle r9 = new com.autonavi.common.PageBundle
            r9.<init>()
            com.amap.bundle.drivecommon.model.RouteCarResultData r0 = new com.amap.bundle.drivecommon.model.RouteCarResultData
            com.autonavi.minimap.drive.route.CalcRouteScene r2 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN
            r0.<init>(r2)
            r0.setFocusRouteIndex(r3)
            com.autonavi.jni.ae.route.route.CalcRouteResult r2 = new com.autonavi.jni.ae.route.route.CalcRouteResult
            r2.<init>()
            r2.setPtr(r4)
            java.util.Map<java.lang.Object, java.lang.Object> r3 = r2.mResultInfo
            java.lang.String r6 = "valid"
            java.lang.Boolean r7 = java.lang.Boolean.TRUE
            r3.put(r6, r7)
            r0.setCalcRouteResult(r2)
            com.autonavi.minimap.drive.route.CalcRouteScene r3 = com.autonavi.minimap.drive.route.CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN
            ph r6 = r8.g
            com.autonavi.common.model.POI r6 = r6.g
            ph r7 = r8.g
            com.autonavi.common.model.POI r7 = r7.h
            com.amap.bundle.drivecommon.model.NavigationResult r2 = defpackage.rn.a(r3, r6, r7, r2)
            r8.getContext()
            java.lang.String r3 = "0"
            java.lang.String r3 = defpackage.ru.a(r3)
            ph r6 = r8.g
            com.autonavi.common.model.POI r6 = r6.g
            ph r7 = r8.g
            com.autonavi.common.model.POI r7 = r7.h
            r0.setNaviResultData(r6, r7, r2, r3)
            ph r2 = r8.g
            com.autonavi.common.model.POI r2 = r2.g
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r0.setShareStartPos(r2)
            ph r2 = r8.g
            com.autonavi.common.model.POI r2 = r2.h
            com.autonavi.common.model.GeoPoint r2 = r2.getPoint()
            r0.setShareEndPos(r2)
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            esb r3 = defpackage.esb.a.a
            java.lang.Class<acg> r6 = defpackage.acg.class
            esc r3 = r3.a(r6)
            acg r3 = (defpackage.acg) r3
            java.util.List r6 = r3.g()
            if (r6 == 0) goto L_0x00e3
            java.util.List r3 = r3.g()
            java.util.Iterator r3 = r3.iterator()
        L_0x00cf:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x00e3
            java.lang.Object r6 = r3.next()
            com.autonavi.common.model.POI r6 = (com.autonavi.common.model.POI) r6
            com.autonavi.common.model.GeoPoint r6 = r6.getPoint()
            r2.add(r6)
            goto L_0x00cf
        L_0x00e3:
            r0.setShareMidPos(r2)
            java.lang.String r2 = "RouteCarResultErrorReportFragment.bundle_key_result"
            r9.putObject(r2, r0)
            java.lang.String r0 = "RouteCarResultErrorReportFragment.from_page_code"
            java.lang.String r1 = java.lang.String.valueOf(r1)
            r9.putString(r0, r1)
            java.lang.String r0 = "pathResult"
            r9.putLong(r0, r4)
            java.lang.String r0 = "amap.basemap.action.route_car_error_report"
            r8.startPage(r0, r9)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage.b(com.amap.bundle.drive.result.driveresult.browser.AjxRouteCarResultBrowserPage, java.lang.String):void");
    }
}
