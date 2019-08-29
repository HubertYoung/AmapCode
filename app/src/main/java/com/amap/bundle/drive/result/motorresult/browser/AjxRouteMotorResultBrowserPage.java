package com.amap.bundle.drive.result.motorresult.browser;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.amap.bundle.drive.ajx.module.ModuleRouteDriveResult;
import com.amap.bundle.drivecommon.model.NavigationResult;
import com.amap.bundle.drivecommon.model.RouteCarResultData;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.route.route.CalcRouteResult;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.drive.route.CalcRouteScene;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxRouteMotorResultBrowserPage extends Ajx3Page implements mr {
    mq a = new mq() {
        public final void a(String str, String str2) {
            if (((str.hashCode() == -2136002916 && str.equals("motorbikeNavi")) ? (char) 0 : 65535) == 0) {
                AjxRouteMotorResultBrowserPage.a(AjxRouteMotorResultBrowserPage.this, str2);
            }
        }
    };
    private ModuleRouteDriveResult b;
    private qc c;
    private int d = 0;
    private boolean e = false;
    private ph f;
    private OnClickListener g = new OnClickListener() {
        public final void onClick(View view) {
            if (view.getId() == R.id.btn_error_report) {
                AjxRouteMotorResultBrowserPage ajxRouteMotorResultBrowserPage = AjxRouteMotorResultBrowserPage.this;
                String string = AMapAppGlobal.getApplication().getString(R.string.action_log_type_motor);
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("type", string);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00001", "B022", jSONObject);
                ajxRouteMotorResultBrowserPage.a();
            }
        }
    };

    public void onCreate(Context context) {
        super.onCreate(context);
        PageBundle arguments = getArguments();
        this.e = arguments.getBoolean("is_from_etrip", false);
        this.f = (ph) arguments.getObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ);
        this.d = arguments.getInt("route_car_type_key");
    }

    public View onCreateView(AmapAjxView amapAjxView) {
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.ajx_route_car_result_browser_page, null);
        viewGroup.addView(amapAjxView, new LayoutParams(-1, -1));
        return viewGroup;
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        super.onAjxContxtCreated(iAjxContext);
        this.b = (ModuleRouteDriveResult) this.mAjxView.getJsModule(ModuleRouteDriveResult.MODULE_NAME);
        if (this.b != null) {
            this.b.setGpsButtonActionInterface(this);
            this.b.addSwitchSceneListener(this.a);
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
        return new pq(this);
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
        if (this.e) {
            this.c.b();
        }
        this.c.d();
    }

    public void destroy() {
        super.destroy();
        if (this.b != null) {
            this.b.removeSwitchSceneListener(this.a);
        }
    }

    public void result(int i, ResultType resultType, PageBundle pageBundle) {
        super.result(i, resultType, pageBundle);
    }

    public View getMapSuspendView() {
        this.c = new qc(this);
        this.c.a = this.g;
        this.c.c();
        View suspendView = this.c.getSuspendView();
        if (euk.a()) {
            suspendView.setPadding(suspendView.getPaddingLeft(), suspendView.getPaddingTop() + euk.a(getContext()), suspendView.getPaddingRight(), suspendView.getPaddingBottom());
        }
        return suspendView;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        int i;
        long j = 0;
        if (this.f != null) {
            i = this.f.e;
            if (this.f.a != null) {
                int length = this.f.a.length;
                int[] iArr = new int[length];
                for (int i2 = 0; i2 < length; i2++) {
                    iArr[i2] = (int) this.f.a[i2];
                }
                j = NaviManager.createPathResult(iArr);
            }
        } else {
            i = 0;
        }
        PageBundle pageBundle = new PageBundle();
        RouteCarResultData routeCarResultData = new RouteCarResultData(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN);
        routeCarResultData.setFocusRouteIndex(i);
        CalcRouteResult calcRouteResult = new CalcRouteResult();
        calcRouteResult.setPtr(j);
        calcRouteResult.mResultInfo.put("valid", Boolean.TRUE);
        routeCarResultData.setCalcRouteResult(calcRouteResult);
        NavigationResult navigationResult = null;
        if (this.f != null) {
            navigationResult = rn.a(CalcRouteScene.SCENE_DRIVE_ROUTE_PLAN, this.f.g, this.f.h, calcRouteResult);
        }
        getContext();
        routeCarResultData.setNaviResultData(this.f.g, this.f.h, navigationResult, ru.a("0"));
        routeCarResultData.setShareStartPos(this.f.g.getPoint());
        routeCarResultData.setShareEndPos(this.f.h.getPoint());
        ArrayList arrayList = new ArrayList();
        for (POI point : this.f.i) {
            arrayList.add(point.getPoint());
        }
        routeCarResultData.setShareMidPos(arrayList);
        pageBundle.putObject("RouteCarResultErrorReportFragment.bundle_key_result", routeCarResultData);
        pageBundle.putString("RouteCarResultErrorReportFragment.from_page_code", String.valueOf("51"));
        pageBundle.putLong("pathResult", j);
        startPage((String) "amap.basemap.action.route_car_error_report", pageBundle);
    }

    static /* synthetic */ void a(AjxRouteMotorResultBrowserPage ajxRouteMotorResultBrowserPage, String str) {
        if (!TextUtils.isEmpty(str)) {
            no.a(ajxRouteMotorResultBrowserPage.getActivity(), str, (a) null);
        }
    }
}
