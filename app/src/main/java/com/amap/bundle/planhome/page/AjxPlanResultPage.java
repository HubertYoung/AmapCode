package com.amap.bundle.planhome.page;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.alibaba.wireless.security.SecExceptionCode;
import com.alipay.mobile.h5container.api.H5Param;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.amap.bundle.planhome.ajx.ModuleHome;
import com.amap.bundle.planhome.view.RoutePopupView;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.bundle.routecommon.entity.BusPaths;
import com.autonavi.bundle.routecommon.entity.IBusRouteResult;
import com.autonavi.bundle.routecommon.inter.IRouteUI;
import com.autonavi.bundle.routecommon.model.IRouteHeaderEvent;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxViewInterface;
import com.autonavi.minimap.route.bus.busline.presenter.BusLineStationListPresenter;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil;
import com.autonavi.minimap.route.logs.operation.UpLoadOperationDataUtil.OperationType;
import com.uc.webview.export.internal.SDKFactory;
import java.lang.ref.WeakReference;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class AjxPlanResultPage extends Ajx3Page implements ada, axc, axe, axh<IBusRouteResult>, bgm, bgo, IVoiceCmdResponder {
    private static final String a = "AjxPlanResultPage";
    private int b;
    private IRouteUI c;
    private ModuleHome d;
    private IBusRouteResult e;
    private adi f;
    private RoutePopupView g;
    private a h;
    private boolean i = false;
    private boolean j = false;
    private bdd k;
    /* access modifiers changed from: private */
    public acg l;
    /* access modifiers changed from: private */
    public POI m;
    /* access modifiers changed from: private */
    public POI n;
    private int o = -1;
    private boolean p;

    static class a extends Handler {
        private WeakReference<AjxPlanResultPage> a;

        public a(AjxPlanResultPage ajxPlanResultPage) {
            this.a = new WeakReference<>(ajxPlanResultPage);
        }

        public final void handleMessage(Message message) {
            super.handleMessage(message);
            int i = message.what;
            AjxPlanResultPage ajxPlanResultPage = (AjxPlanResultPage) this.a.get();
            if (ajxPlanResultPage != null && ajxPlanResultPage.isAlive()) {
                switch (i) {
                    case 1001:
                        AjxPlanResultPage.a(ajxPlanResultPage);
                        return;
                    case 1002:
                        AjxPlanResultPage.b(ajxPlanResultPage);
                        break;
                }
            }
        }
    }

    public void finishSelf() {
    }

    public bgo getPresenter() {
        return this;
    }

    public long getScene() {
        return 16384;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return 16384;
    }

    public boolean handleVUICmd(bgb bgb, bfb bfb) {
        return false;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onTypeChange(RouteType routeType, RouteType routeType2) {
    }

    public final /* synthetic */ void a(Object obj) {
        IBusRouteResult iBusRouteResult = (IBusRouteResult) obj;
        if (iBusRouteResult == null) {
            a(false);
            return;
        }
        this.p = true;
        a(true);
        this.e = iBusRouteResult;
        this.h.sendEmptyMessage(1002);
    }

    public void onJsBack(Object obj, String str) {
        StringBuilder sb = new StringBuilder("jsBack: object ");
        sb.append(obj);
        sb.append(" pageID ");
        sb.append(str);
        PageBundle pageBundle = new PageBundle();
        pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
        setResult(ResultType.OK, pageBundle);
        if (this.c instanceof AbstractBasePresenter) {
            ((AbstractBasePresenter) this.c).onBackPressed();
        } else {
            finish();
        }
    }

    public void pageCreated() {
        this.k = new bdd(getActivity());
        if (b() == RouteType.BUS) {
            bdp bdp = (bdp) defpackage.esb.a.a.a(bdp.class);
            if (bdp != null) {
                bdp.b();
            }
            this.l = (acg) defpackage.esb.a.a.a(acg.class);
            if (this.l != null) {
                this.m = this.l.f();
                this.n = this.l.h();
                this.l.a(this.m);
                this.l.b(this.n);
                this.l.a(new String[]{"输入起点", "输入终点"});
                this.l.a((ada) this);
            }
            if (b() == RouteType.BUS) {
                PageBundle arguments = getArguments();
                if (arguments != null) {
                    this.o = arguments.getInt("bundle_key_token", -1);
                }
            }
        }
        this.h = new a(this);
    }

    public final boolean a(IRouteHeaderEvent iRouteHeaderEvent, PageBundle pageBundle) {
        if (iRouteHeaderEvent == null) {
            return false;
        }
        if (!(iRouteHeaderEvent == IRouteHeaderEvent.HEAD_ANIMATION_DOING || iRouteHeaderEvent == IRouteHeaderEvent.PAGE_ANIMATION_DONE || iRouteHeaderEvent == IRouteHeaderEvent.PAGE_ON_RESULT || this.g == null)) {
            this.g.hidePopupLineView();
        }
        switch (iRouteHeaderEvent) {
            case START_CLICK:
                ebm.a(1);
                break;
            case END_CLICK:
                ebm.a(1);
                break;
            case PREPARE_SWITCH_TAB:
                RouteType routeType = (RouteType) pageBundle.getObject(IRouteHeaderEvent.PREPARE_SWITCH_TAB.name());
                if (this.d != null) {
                    this.d.notifyRouteTypeChange(routeType);
                    break;
                }
                break;
        }
        return false;
    }

    /* access modifiers changed from: private */
    public boolean a(POI poi, POI poi2) {
        if (b() == RouteType.BUS) {
            atb atb = (atb) defpackage.esb.a.a.a(atb.class);
            if (!(this.c == null || atb == null)) {
                atb.b().a(this.mAjxView, poi, poi2);
            }
            b((String) "busRoute");
            a((String) "B016", (JSONObject) null);
        }
        return false;
    }

    private void a(boolean z) {
        int i2 = -1;
        if (b() == RouteType.BUS) {
            PageBundle arguments = getArguments();
            if (arguments != null) {
                i2 = arguments.getInt("bundle_key_token", -1);
            }
        }
        if (b() == RouteType.BUS && this.p && defpackage.eqc.a.a.b) {
            if (z) {
                d.a.a(i2, 10000, (String) null);
            } else {
                d.a.a(i2, (int) SDKFactory.getCoreType, (String) null);
            }
            defpackage.eqc.a.a.b = false;
        }
    }

    public final void a() {
        setSoftInputMode(16);
        if (this.f == null) {
            this.f = new adi(this);
            this.f.c = this.e;
            this.f.f = this.mAjxView;
        }
        this.h.sendEmptyMessage(1001);
    }

    public final void a(String str) {
        if (FunctionSupportConfiger.TAXI_TAG.equals(str)) {
            ((axd) getContentView().getParent()).getRouteInputUI().a(RouteType.TAXI);
            return;
        }
        if ("freeride".equals(str)) {
            ((axd) getContentView().getParent()).getRouteInputUI().a(RouteType.FREERIDE);
        }
    }

    public final void a(int i2, String str) {
        if (this.e != null) {
            this.e.setFocusBusPathIndex(i2);
            bty mapView = getMapManager().getMapView();
            if (mapView != null) {
                ebf.a(mapView, mapView.p(false), mapView.ae(), 2);
                mapView.a(true);
            }
            PageBundle pageBundle = new PageBundle();
            atb atb = (atb) defpackage.esb.a.a.a(atb.class);
            if (this.e.isExtBusResult()) {
                pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, this.e);
                pageBundle.putLong("bundle_key_start_time", System.currentTimeMillis());
                if (atb != null) {
                    atb.a().a(1, pageBundle);
                }
            } else {
                pageBundle.putObject(BusLineStationListPresenter.BUNDLE_KEY_RESULT_OBJ, this.e);
                pageBundle.putString("original_busroute_data", atb != null ? atb.b().a(this.mAjxView) : "");
                pageBundle.putBoolean("key_favorites", false);
                pageBundle.putLong("bundle_key_start_time", System.currentTimeMillis());
                pageBundle.putString("bundle_key_options", str);
                if (atb != null) {
                    startPageForResult(atb.a().a(2), pageBundle, (int) SecExceptionCode.SEC_ERROR_UMID_UNKNOWN_ERR);
                }
                UpLoadOperationDataUtil.a(OperationType.TYPE_BUS_ROUTE_SELECT, 0, 0, 0);
            }
        }
    }

    public void loadJs() {
        this.b = getActivity().getWindow().getAttributes().softInputMode;
        pageCreated();
        this.mAjxView.onAjxContextCreated(new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                AjxPlanResultPage.c(AjxPlanResultPage.this);
            }
        });
        this.mAjxView.setOnUiLoadCallback(new Callback<AmapAjxView>() {
            public void error(Throwable th, boolean z) {
            }

            public void callback(AmapAjxView amapAjxView) {
                AjxPlanResultPage.this.c();
            }
        });
        super.loadJs(0, 0);
    }

    public void resume() {
        super.resume();
        if (this.c == null) {
            this.c = ((axd) getContentView().getParent()).getRouteInputUI();
        }
        boolean z = false;
        if (this.ajxPageStateInvoker.getAppSwitch()) {
            setSoftInputMode(16);
        } else {
            PageBundle arguments = getArguments();
            if (this.i) {
                this.i = false;
                b((String) "busRoute");
            } else if (this.j) {
                this.j = false;
            } else if (arguments != null) {
                b(arguments.getString("bundle_key_source", "others"));
            }
        }
        boolean a2 = bnx.a(this.m, this.c.d());
        boolean a3 = bnx.a(this.n, this.c.f());
        this.m = this.c.d();
        this.n = this.c.f();
        this.c.a((axe) this);
        getActivity().getWindow().getDecorView().addOnLayoutChangeListener(this.k);
        if (this.c.o()) {
            c();
        }
        if (b() == RouteType.BUS) {
            if (this.l != null) {
                this.m = this.c.d();
                this.n = this.c.f();
                this.l.a((acy) new acy() {
                    public final void onDataChange(POI poi, List list, POI poi2) {
                        boolean z = true;
                        boolean z2 = !AjxPlanResultPage.this.l.a(poi, AjxPlanResultPage.this.m);
                        boolean z3 = !AjxPlanResultPage.this.l.a(poi2, AjxPlanResultPage.this.n);
                        if (!bnx.a(poi, AjxPlanResultPage.this.n) || !bnx.a(poi2, AjxPlanResultPage.this.m)) {
                            z = false;
                        }
                        StringBuilder sb = new StringBuilder(" (onDataChange) isStartChange: ");
                        sb.append(z2);
                        sb.append(" isEndChange: ");
                        sb.append(z3);
                        sb.append(" isExchange: ");
                        sb.append(z);
                        eao.a((String) AjxPlanResultPage.a, sb.toString());
                        AjxPlanResultPage.this.m = poi;
                        AjxPlanResultPage.this.n = poi2;
                        if (z2 || z3) {
                            if (z2) {
                                AjxPlanResultPage.this.l.a(AjxPlanResultPage.this.m);
                            }
                            if (z3) {
                                AjxPlanResultPage.this.l.b(AjxPlanResultPage.this.n);
                            }
                            AjxPlanResultPage.this.c();
                        }
                        if (z) {
                            AjxPlanResultPage.this.a(poi, poi2);
                        }
                    }
                });
                this.l.a(this.m);
                this.l.b(this.n);
            }
            if (this.c.o()) {
                PageBundle arguments2 = getArguments();
                if (arguments2 != null) {
                    int a4 = eqg.a(arguments2.getString("bundleKeyVoiceCmd"));
                    if (a4 != -1) {
                        d.a.a(a4, 10000, (String) null);
                    }
                }
                if (a3 && a2) {
                    if (this.e != null) {
                        z = true;
                    }
                    a(z);
                }
            }
        }
    }

    public void pause() {
        super.pause();
        if (this.b != 0) {
            setSoftInputMode(this.b);
        }
        if (this.k != null) {
            getActivity().getWindow().getDecorView().removeOnLayoutChangeListener(this.k);
        }
    }

    public void result(int i2, ResultType resultType, PageBundle pageBundle) {
        super.result(i2, resultType, pageBundle);
        IRouteUI routeInputUI = ((axd) getContentView().getParent()).getRouteInputUI();
        if (i2 == 1001) {
            POI a2 = a(resultType, pageBundle);
            if (a2 != null) {
                routeInputUI.a(a2);
            }
            this.i = true;
        } else if (i2 == 1002) {
            POI a3 = a(resultType, pageBundle);
            if (a3 != null) {
                routeInputUI.b(a3);
            }
            this.i = true;
        } else {
            if (i2 == 999) {
                if (pageBundle != null) {
                    String string = pageBundle.getString("refresh_json", "");
                    atb atb = (atb) defpackage.esb.a.a.a(atb.class);
                    if (!TextUtils.isEmpty(string) && atb != null) {
                        atb.b().a((AmapAjxViewInterface) this.mAjxView, string);
                    }
                    String string2 = pageBundle.getString("bus_route_data", "");
                    if (!TextUtils.isEmpty(string2) && atb != null) {
                        atb.b().b(this.mAjxView, string2);
                    }
                }
                b((String) H5Param.DEFAULT_LONG_BACK_BEHAVIOR);
                this.j = true;
            }
        }
    }

    private static POI a(ResultType resultType, PageBundle pageBundle) {
        if (ResultType.OK != resultType || pageBundle == null || !pageBundle.containsKey("result_poi")) {
            return null;
        }
        return (POI) pageBundle.getObject("result_poi");
    }

    private RouteType b() {
        axd axd = (axd) getContentView().getParent();
        if (axd != null) {
            IRouteUI routeInputUI = axd.getRouteInputUI();
            if (routeInputUI != null) {
                return routeInputUI.g();
            }
        }
        return RouteType.DEFAULT;
    }

    private static void b(String str) {
        eao.a((String) "zq", "fromValue:".concat(String.valueOf(str)));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("from", str);
            a((String) "B035", jSONObject);
        } catch (JSONException unused) {
        }
    }

    private static void a(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            LogManager.actionLogV2("P00018", str);
        } else {
            LogManager.actionLogV2("P00018", str, jSONObject);
        }
    }

    public void destroy() {
        atb atb = (atb) defpackage.esb.a.a.a(atb.class);
        if (atb != null) {
            atb.b().a((AmapAjxViewInterface) this.mAjxView, (axh<IBusRouteResult>) null);
        }
        super.destroy();
        if (this.h != null) {
            this.h.removeCallbacksAndMessages(null);
            this.h = null;
        }
        if (this.f != null) {
            adi adi = this.f;
            adi.a = null;
            adi.c = null;
            adi.b = null;
            adi.e = null;
            this.f = null;
        }
        if (this.g != null) {
            this.g.onDestroy();
        }
        if (this.c != null) {
            this.c.a((axe) null);
        }
        if (this.l != null) {
            this.l.b((ada) this);
        }
    }

    public boolean backPressed() {
        if (!(this.f != null && this.f.d)) {
            return super.backPressed();
        }
        if (this.f != null) {
            this.f.a(false);
            axd axd = (axd) getContentView().getParent();
            adi adi = this.f;
            if (adi.e.isAlive()) {
                adi.a.setVisibility(8);
                IRouteUI routeInputUI = axd.getRouteInputUI();
                if (routeInputUI != null) {
                    routeInputUI.b((View) adi.a);
                }
            }
        }
        return true;
    }

    /* access modifiers changed from: private */
    public void c() {
        atb atb = (atb) defpackage.esb.a.a.a(atb.class);
        if (atb != null) {
            atb.b().b(this.mAjxView, this.m, this.n);
        }
    }

    static /* synthetic */ void a(AjxPlanResultPage ajxPlanResultPage) {
        if (ajxPlanResultPage.f != null) {
            axd axd = (axd) ajxPlanResultPage.getContentView().getParent();
            adi adi = ajxPlanResultPage.f;
            if (adi.e.isAlive()) {
                IRouteUI routeInputUI = axd.getRouteInputUI();
                if (routeInputUI != null) {
                    routeInputUI.a((View) adi.a);
                    adi.a.setVisibility(8);
                }
            }
            adi adi2 = ajxPlanResultPage.f;
            if (adi2.c != null) {
                BusPaths busPathsResult = adi2.c.getBusPathsResult();
                if (!TextUtils.isEmpty(busPathsResult.mShowInput_Content)) {
                    adi2.a.setEtHindText(busPathsResult.mShowInput_Content);
                }
            }
            adi2.a(true, R.anim.fade_in_from_bottom, adi2);
            adi2.a.setEtFocusable();
            ((InputMethodManager) adi2.b.getSystemService("input_method")).showSoftInput(adi2.a.getEditText(), 1);
        }
    }

    static /* synthetic */ void b(AjxPlanResultPage ajxPlanResultPage) {
        int alternative = ajxPlanResultPage.e.getAlternative();
        switch (alternative) {
            case 1:
            case 2:
                if (ajxPlanResultPage.g == null) {
                    ajxPlanResultPage.g = new RoutePopupView(ajxPlanResultPage.getContext(), (axd) ajxPlanResultPage.getContentView().getParent());
                }
                ajxPlanResultPage.g.showAlternativePopup(alternative);
                return;
            default:
                return;
        }
    }

    static /* synthetic */ void c(AjxPlanResultPage ajxPlanResultPage) {
        ajxPlanResultPage.d = (ModuleHome) ajxPlanResultPage.mAjxView.getJsModule(ModuleHome.MODULE_NAME);
        atb atb = (atb) defpackage.esb.a.a.a(atb.class);
        if (atb != null) {
            atb.b().a((AmapAjxViewInterface) ajxPlanResultPage.mAjxView, (axh<IBusRouteResult>) ajxPlanResultPage);
        }
        aso aso = (aso) defpackage.esb.a.a.a(aso.class);
        if (aso != null) {
            aso.a().a(ajxPlanResultPage.mAjxView, ajxPlanResultPage);
        }
    }
}
