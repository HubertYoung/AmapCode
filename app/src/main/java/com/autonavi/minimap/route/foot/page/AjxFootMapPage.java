package com.autonavi.minimap.route.foot.page;

import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.amap.bundle.statistics.LogManager;
import com.amap.bundle.utils.ui.ToastHelper;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.amap.bundle.voiceservice.dispatch.IVoiceFootDispatcher;
import com.autonavi.bundle.footresult.ajx.ModuleFoot;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.impl.Locator.LOCATION_SCENE;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.jni.ae.model.Coord2D;
import com.autonavi.jni.eyrie.amap.tbt.NaviManager;
import com.autonavi.jni.eyrie.amap.tbt.model.RoutePOIInfo;
import com.autonavi.jni.eyrie.amap.tbt.model.RouteWayPoint;
import com.autonavi.map.core.view.MvpImageView;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBasePresenter;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.suspend.refactor.gps.GPSButton;
import com.autonavi.minimap.R;
import com.autonavi.minimap.ajx3.Ajx3Page;
import com.autonavi.minimap.ajx3.Ajx3PagePresenter;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModuleHistory;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AttributeListener;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView;
import com.autonavi.minimap.bundle.agroup.widget.AGroupSuspendView.b;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.route.ajx.inter.OnAjxFootMapInterface;
import com.autonavi.minimap.route.ajx.inter.OnEndPoiChangeInterface;
import com.autonavi.minimap.route.ajx.inter.OnErrorReportClickInterface;
import com.autonavi.minimap.route.ajx.inter.OnNotifyCalcRouteListener;
import com.autonavi.minimap.route.ajx.inter.OnRouteSaveEventListener;
import com.autonavi.minimap.route.ajx.inter.RouteResultSuccessInterface;
import com.autonavi.minimap.route.ajx.inter.UnLockGpsButtonInterface;
import com.autonavi.sdk.location.LocationInstrument;
import com.autonavi.sdk.log.util.LogConstant;
import com.autonavi.widget.ui.BalloonText;
import com.uc.webview.export.internal.SDKFactory;
import org.json.JSONException;
import org.json.JSONObject;
import tv.danmaku.ijk.media.player.IjkMediaMeta;

@OverlayPageProperty(overlays = {@OvProperty(overlay = UvOverlay.GpsOverlay, visible = false)})
public class AjxFootMapPage extends Ajx3Page implements OnClickListener, bgm, cdp, IVoiceCmdResponder, launchModeSingleTask, AjxLifeCircleListener, OnAjxFootMapInterface, OnEndPoiChangeInterface, OnErrorReportClickInterface, OnNotifyCalcRouteListener, OnRouteSaveEventListener, RouteResultSuccessInterface, UnLockGpsButtonInterface {
    /* access modifiers changed from: private */
    public ModuleFoot a;
    /* access modifiers changed from: private */
    public View b;
    private View c;
    /* access modifiers changed from: private */
    public BalloonText d;
    /* access modifiers changed from: private */
    public Runnable e;
    private MvpImageView f;
    private AGroupSuspendView g;
    private int h;
    private boolean i = false;
    private boolean j;
    private ebu k;
    private View l;
    /* access modifiers changed from: private */
    public aia m;
    private boolean n;
    private View o;
    private long p = 0;
    private aie q = new aie() {
        public final void a(int i) {
            if (AjxFootMapPage.this.m == null || !AjxFootMapPage.this.m.a(AjxFootMapPage.d(AjxFootMapPage.this), (String) "startNavi", i)) {
                AjxFootMapPage.a(i);
            }
        }
    };
    private ImageView r;
    private coi s = new coi() {
        public final void doReportError(String str) {
            AjxFootMapPage.this.a(str, true);
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("name", "步行路线图面");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            LogManager.actionLogV2(LogConstant.PAGE_ID_FEEDBACK_ENTRANCE, "B001", jSONObject);
        }
    };

    public void finishSelf() {
    }

    public String getAjx3Url() {
        return ModuleFoot.URL_FOOT_ROUTE;
    }

    public long getScene() {
        return IjkMediaMeta.AV_CH_TOP_BACK_RIGHT;
    }

    public JSONObject getScenesData() {
        return null;
    }

    public long getScenesID() {
        return IjkMediaMeta.AV_CH_TOP_BACK_RIGHT;
    }

    public boolean handleSetContentView() {
        return true;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public void onFloorChanged(int i2, int i3) {
    }

    public Ajx3PagePresenter createPresenter() {
        return new ecq(this);
    }

    public void onContentViewCreated(View view) {
        this.l = view;
        this.l.setLayoutParams(new LayoutParams(-1, -1));
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        eav.a("performance-", "AjxFootMapPage onCreate");
        requestScreenOrientation(1);
        setContentView(R.layout.ajx_foot_result_map_fragment);
        ebn.a(getActivity().getWindow().getDecorView());
        bty mapView = getMapView();
        if (mapView != null) {
            this.i = mapView.ai();
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.FootResult, getArguments(), false);
        }
        this.k = new ebu();
        this.d = (BalloonText) findViewById(R.id.route_foot_too_long_warning);
        IVoiceFootDispatcher iVoiceFootDispatcher = (IVoiceFootDispatcher) ank.a(IVoiceFootDispatcher.class);
        if (iVoiceFootDispatcher != null) {
            iVoiceFootDispatcher.setFootVoiceListener(this.q);
        }
    }

    public void onAjxViewCreated(AmapAjxView amapAjxView) {
        amapAjxView.setAttributeListener(new AttributeListener() {
            public final boolean handleAttr(String str, Object obj) {
                if ("RIDE_FADE_FROM_BOTTOM".equalsIgnoreCase(str)) {
                    Float f = (Float) obj;
                    AjxFootMapPage.this.b.setAlpha(f.floatValue());
                    if (AjxFootMapPage.b(AjxFootMapPage.this).a() == 102) {
                        AjxFootMapPage.a(AjxFootMapPage.this, f.floatValue());
                    }
                    return true;
                } else if (!"RIDE_HEIGHT_TO_TOP".equalsIgnoreCase(str)) {
                    return false;
                } else {
                    if (AjxFootMapPage.b(AjxFootMapPage.this).a() != 102) {
                        AjxFootMapPage.b(AjxFootMapPage.this, ((Float) obj).floatValue());
                    }
                    return true;
                }
            }
        });
    }

    public void resume() {
        super.resume();
        this.k.a(getContentView());
        bty mapView = getMapManager() != null ? getMapManager().getMapView() : null;
        if (mapView != null) {
            if (3 != mapView.o(false)) {
                ebf.a(mapView, mapView.p(false), mapView.ae(), 3);
            }
            mapView.b(false);
        }
        LocationInstrument.getInstance().subscribe(getContext(), LOCATION_SCENE.TYPE_FOOT_PATH_PLAN);
        if (!this.n) {
            ((ecq) this.mPresenter).a.a(this.l);
            this.n = true;
        }
        this.l.setVisibility(0);
    }

    public void pause() {
        super.pause();
        this.l.setVisibility(8);
        LocationInstrument.getInstance().unsubscribe(getContext());
    }

    public void destroy() {
        super.destroy();
        if (this.g != null) {
            this.g.destroy();
        }
        IVoiceFootDispatcher iVoiceFootDispatcher = (IVoiceFootDispatcher) ank.a(IVoiceFootDispatcher.class);
        if (iVoiceFootDispatcher != null) {
            iVoiceFootDispatcher.setFootVoiceListener(null);
        }
        this.r.setOnClickListener(null);
        this.f.setOnClickListener(null);
        this.o.setOnClickListener(null);
        if (this.d != null) {
            this.d.setVisibility(8);
            ebr.a(true).removeCallbacks(this.e);
            this.e = null;
        }
        if (this.n) {
            ((ecq) this.mPresenter).a.b(this.l);
            this.n = false;
        }
        if (this.a != null) {
            this.a.setEndPoiChangeListener(null);
            this.a.setUnLockGpsBtnListener(null);
            this.a.setOnErrorReportClickListener(null);
            this.a.setOnRouteSaveEventListener(null);
            this.a.setOnNotifyCalcRouteListener(null);
            this.a.setOnRouteResultSuccessListener(null);
            this.a = null;
        }
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.q(this.i);
            ebf.a(mapView, mapView.j(false), 0, 0);
        }
    }

    public void onPageCover() {
        super.onPageCover();
        ((ecq) this.mPresenter).b();
    }

    public void stop() {
        super.stop();
        b(false);
    }

    public View getMapSuspendView() {
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        ccv ccv = new ccv(AMapPageUtil.getAppContext());
        if (((ecq) this.mPresenter).a() != 102) {
            ccv.addWidget(suspendWidgetHelper.a(), suspendWidgetHelper.c(), 1);
            LinearLayout.LayoutParams m2 = suspendWidgetHelper.m();
            m2.bottomMargin = -agn.a(getContext(), 1.0f);
            ccv.addWidget(suspendWidgetHelper.l(), m2, 6);
        }
        this.o = LayoutInflater.from(getContext()).inflate(R.layout.foot_result_page_right_bottom_container, null);
        ccv.addWidget(this.o, new RelativeLayout.LayoutParams(-1, -2), 6);
        this.o.setOnClickListener(this);
        this.o.setVisibility(8);
        ccv.addWidget(suspendWidgetHelper.f(), suspendWidgetHelper.g(), 7);
        ccv.addWidget(suspendWidgetHelper.n(), suspendWidgetHelper.q(), 2);
        GPSButton d2 = suspendWidgetHelper.d();
        LinearLayout.LayoutParams a2 = a();
        a2.leftMargin = agn.a(getContext(), 4.0f);
        suspendWidgetHelper.a(suspendWidgetHelper.d());
        suspendWidgetHelper.a(ccv.getSuspendView(), d2, a2, 3);
        this.f = new MvpImageView(getContext());
        this.f.setScaleType(ScaleType.CENTER_INSIDE);
        this.f.setImageResource(R.drawable.icon_c18_selector);
        this.f.setBackgroundResource(R.drawable.rt_bus_around_refresh_bg_selector);
        this.f.setContentDescription("报错");
        LinearLayout.LayoutParams a3 = a();
        a3.rightMargin = agn.a(getContext(), 4.0f);
        ccv.addWidget(this.f, a3, 4);
        boolean z = false;
        if (this.f != null) {
            if (((ecq) this.mPresenter).a() == 102) {
                this.f.setVisibility(8);
            } else {
                this.f.setVisibility(0);
            }
        }
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
        layoutParams.leftMargin = agn.a(getContext(), 4.0f);
        layoutParams.rightMargin = agn.a(getContext(), 4.0f);
        int a4 = agn.a(getContext(), 4.0f);
        this.r = new ImageView(getContext());
        this.r.setPadding(a4, a4, a4, a4);
        this.r.setScaleType(ScaleType.CENTER_INSIDE);
        this.r.setImageResource(R.drawable.back_to_outdoor);
        this.r.setBackgroundResource(R.drawable.icon_c_bg_single);
        this.r.setContentDescription("出门");
        this.r.setVisibility(8);
        ccv.addWidget(this.r, layoutParams, 2);
        if (this.r != null) {
            this.r.setOnClickListener(new OnClickListener() {
                public final void onClick(View view) {
                    LogManager.actionLogV2("P00094", "B004");
                    AjxFootMapPage.this.unLockGpsButtonState();
                    if (AjxFootMapPage.this.a != null) {
                        AjxFootMapPage.this.a.setOnOutDoorLineFocus();
                    }
                }
            });
        }
        cuh cuh = (cuh) a.a.a(cuh.class);
        if (cuh != null) {
            cug c2 = cuh.c();
            if (c2 != null && c2.c()) {
                z = true;
            }
        }
        if (z && ((ecq) this.mPresenter).a() == 100) {
            this.g = new AGroupSuspendView(getContext());
            LinearLayout.LayoutParams a5 = a();
            a5.rightMargin = agn.a(getContext(), 4.0f);
            this.g.setBackgroundResource(R.drawable.icon_c_bg_single);
            ccv.addWidget(this.g, a5, 4);
            this.g.setOnEntryEventListener(new b() {
                public final void a() {
                    cuh cuh = (cuh) a.a.a(cuh.class);
                    if (cuh != null) {
                        cuh.b("P00094", "B022");
                    }
                }
            });
        }
        this.f.setOnClickListener(new OnClickListener() {
            public final void onClick(View view) {
                AjxFootMapPage.e(AjxFootMapPage.this);
            }
        });
        this.b = ccv.getSuspendView();
        getSuspendWidgetHelper().a((cdp) this);
        return this.b;
    }

    public void unLockGpsButtonState() {
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            cdz d2 = suspendManager.d();
            if (d2 != null) {
                d2.f();
            }
        }
    }

    private LinearLayout.LayoutParams a() {
        int dimensionPixelSize = getContext().getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        return new LinearLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize);
    }

    public final void a(String str) {
        if (this.a != null) {
            this.a.requestFootRoute(str);
        }
    }

    public final void b(String str) {
        if (this.a != null) {
            this.a.setWeatherData(str);
        }
    }

    public final void c(String str) {
        if (this.a != null) {
            this.a.setRequestData(str);
        }
    }

    /* access modifiers changed from: private */
    public void a(String str, boolean z) {
        if (this.a != null) {
            PageBundle a2 = ebc.a(getContext(), str, this.a.getErrorReportData());
            if (z) {
                a2.putInt("sourcepage", 16);
            } else {
                a2.putInt("sourcepage", 5);
            }
            a2.putInt("page_id", 7);
            a2.putInt("route_line_type", 1);
            IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
            if (iErrorReportStarter != null) {
                iErrorReportStarter.startFeedback(a2);
            }
        }
    }

    public void onEndPoiChangeListener(POI poi) {
        if (this.mPresenter != null) {
            ecq ecq = (ecq) this.mPresenter;
            if (ecq.b != null && ecq.b.i() == RouteType.ONFOOT) {
                ecq.b.b(RouteType.ONFOOT, poi);
                ecq.c = poi.clone();
            }
        }
    }

    public void onRouteResultSuccess(String str) {
        GeoPoint geoPoint;
        unLockGpsButtonState();
        ecq ecq = (ecq) this.mPresenter;
        if (ecq.b == null || ecq.b.f() == null) {
            geoPoint = LocationInstrument.getInstance().getLatestPosition();
        } else {
            geoPoint = ecq.b.f().getPoint();
        }
        eco.a(geoPoint.getLongitude(), geoPoint.getLatitude(), new a() {
            public final void a(String str) {
                ecq.d(ecq.this).b(str);
            }

            public final void a() {
                ecq.d(ecq.this).b((String) "");
            }
        });
        try {
            if (!(new JSONObject(str).optInt("routeLength") <= 10000 || ((ecq) this.mPresenter).a() == 101 || ((ecq) this.mPresenter).a() == 102)) {
                this.d.setVisibility(0);
                if (this.e != null) {
                    ebr.a(true).removeCallbacks(this.e);
                    this.e = null;
                }
                this.e = new Runnable() {
                    public final void run() {
                        if (AjxFootMapPage.this.d != null) {
                            AjxFootMapPage.this.d.setVisibility(8);
                            AjxFootMapPage.this.e = null;
                        }
                    }
                };
                ebr.a(true).postDelayed(this.e, 5000);
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        c(true);
        a(true);
        this.p = System.currentTimeMillis();
    }

    public void onRouteResultFail() {
        c(false);
        a(false);
    }

    private void a(boolean z) {
        int i2;
        View view;
        if (this.o != null) {
            if (((ecq) this.mPresenter).a() == 101) {
                view = this.o;
            } else {
                view = this.o;
                if (z) {
                    i2 = 0;
                    view.setVisibility(i2);
                }
            }
            i2 = 8;
            view.setVisibility(i2);
        }
    }

    public void onErrorReportClickBtn(String str) {
        a(str, false);
    }

    public void onAjxContxtCreated(IAjxContext iAjxContext) {
        if (isAlive() && iAjxContext != null) {
            this.a = (ModuleFoot) this.mAjxView.getJsModule(ModuleFoot.MODULE_NAME);
            this.a.setEndPoiChangeListener(this);
            this.a.setUnLockGpsBtnListener(this);
            this.a.setOnErrorReportClickListener(this);
            this.a.setOnRouteSaveEventListener(this);
            this.a.setOnNotifyCalcRouteListener(this);
            this.a.setOnRouteResultSuccessListener(this);
            this.a.setOnAjxResultListener(this);
            c(((ecq) this.mPresenter).a(((ecq) this.mPresenter).c()));
            this.m = (aia) a.a.a(aia.class);
        }
    }

    public void onJsBack(Object obj, String str) {
        if (isAlive()) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject(ModuleHistory.AJX_BACK_RETURN_DATA_KEY, obj);
            setResult(ResultType.OK, pageBundle);
            if (((ecq) this.mPresenter).a instanceof AbstractBasePresenter) {
                ((AbstractBasePresenter) ((ecq) this.mPresenter).a).onBackPressed();
            } else {
                finish();
            }
        }
    }

    public void onFloorWidgetVisibilityChanged(ami ami, boolean z, int i2) {
        if (this.r != null) {
            if (!z) {
                this.r.setVisibility(8);
            } else if (this.j) {
                this.r.setVisibility(0);
            }
        }
    }

    public void onExistOutDoorData(boolean z) {
        this.j = z;
    }

    public void onIndoorFloorChanged(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                int optInt = new JSONObject(str).optInt("floor");
                if (getSuspendManager() != null && getSuspendManager().c().c()) {
                    getSuspendManager().c().b(optInt);
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void onSlidingUiStatue(boolean z) {
        if (z) {
            b(true);
        }
    }

    private void b(boolean z) {
        if (!z) {
            this.k.a();
        } else {
            this.k.b();
        }
    }

    public boolean onRouteSaveEvent(String str) {
        try {
            if (new JSONObject(str).optInt("action") == 1) {
                String a2 = ebk.a();
                cos b2 = ecu.b(str);
                if (b2 == null) {
                    return false;
                }
                com com2 = (com) ank.a(com.class);
                if (com2 != null) {
                    coq a3 = com2.a(a2);
                    if (a3 != null) {
                        a3.a(b2);
                    }
                }
                return true;
            }
            String a4 = ecu.a(str);
            if (!TextUtils.isEmpty(a4)) {
                ebk.a(a4);
            }
            return true;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    private void c(boolean z) {
        PageBundle arguments = getArguments();
        int i2 = -1;
        if (arguments != null) {
            i2 = arguments.getInt("bundle_key_token", -1);
        }
        if (a.a.b) {
            if (z) {
                d.a.a(i2, 10000, (String) null);
            } else {
                d.a.a(i2, (int) SDKFactory.getCoreType, (String) null);
            }
            a.a.b = false;
        }
    }

    public void onClick(View view) {
        if (view.equals(this.o)) {
            if (System.currentTimeMillis() - this.p > 10000) {
                a(false);
                a(((ecq) this.mPresenter).a(true));
                return;
            }
            ToastHelper.showLongToast(getResources().getString(R.string.foot_result_toast_refresh_route));
        }
    }

    public void loadJs() {
        this.mAjxView.load(ModuleFoot.URL_FOOT_ROUTE, ((ecq) this.mPresenter).a(true), "FOOT_MAP_RESULT");
    }

    public void onCalcRoute() {
        int i2;
        POI d2 = ((ecq) this.mPresenter).a.d();
        POI f2 = ((ecq) this.mPresenter).a.f();
        if (d2 != null && f2 != null) {
            int i3 = 0;
            try {
                i2 = Integer.valueOf(d2.getIndoorFloorNoName()).intValue();
            } catch (NumberFormatException e2) {
                e2.printStackTrace();
                i2 = 0;
            }
            try {
                i3 = Integer.valueOf(f2.getIndoorFloorNoName()).intValue();
            } catch (NumberFormatException e3) {
                e3.printStackTrace();
            }
            GeoPoint point = ((ecq) this.mPresenter).a.d().getPoint();
            GeoPoint point2 = ((ecq) this.mPresenter).a.f().getPoint();
            String name = ((ecq) this.mPresenter).a.d().getName();
            String name2 = ((ecq) this.mPresenter).a.f().getName();
            RouteWayPoint routeWayPoint = new RouteWayPoint();
            RoutePOIInfo routePOIInfo = new RoutePOIInfo();
            Coord2D coord2D = new Coord2D();
            coord2D.lon = point.getLongitude();
            coord2D.lat = point.getLatitude();
            routePOIInfo.realPos = coord2D;
            routePOIInfo.name = name;
            routePOIInfo.floor = i2;
            routePOIInfo.poiID = d2.getId();
            routePOIInfo.parentID = d2.getPid();
            routePOIInfo.type = ebm.a(d2);
            routeWayPoint.start.add(routePOIInfo);
            RoutePOIInfo routePOIInfo2 = new RoutePOIInfo();
            Coord2D coord2D2 = new Coord2D();
            coord2D2.lon = point2.getLongitude();
            coord2D2.lat = point2.getLatitude();
            routePOIInfo2.realPos = coord2D2;
            routePOIInfo2.name = name2;
            routePOIInfo2.floor = i3;
            routePOIInfo2.poiID = f2.getId();
            routePOIInfo2.parentID = f2.getPid();
            routePOIInfo2.type = ebm.a(f2);
            routeWayPoint.end.add(routePOIInfo2);
            byte[] bArr = null;
            PageBundle arguments = ((AjxFootMapPage) ((ecq) this.mPresenter).mPage).getArguments();
            if (arguments != null) {
                bArr = arguments.getByteArray("original_route");
            }
            NaviManager.calcRouteFromDataNew(4, routeWayPoint, bArr);
        }
    }

    public bgo getPresenter() {
        return (ecq) this.mPresenter;
    }

    static /* synthetic */ ecq b(AjxFootMapPage ajxFootMapPage) {
        return (ecq) ajxFootMapPage.mPresenter;
    }

    static /* synthetic */ void a(AjxFootMapPage ajxFootMapPage, float f2) {
        if (ajxFootMapPage.c == null && ((ecq) ajxFootMapPage.mPresenter).a != null) {
            ajxFootMapPage.c = ((ecq) ajxFootMapPage.mPresenter).a.b();
        }
        if (ajxFootMapPage.c != null) {
            ajxFootMapPage.c.setAlpha(f2);
        }
    }

    static /* synthetic */ void b(AjxFootMapPage ajxFootMapPage, float f2) {
        if (!(((ecq) ajxFootMapPage.mPresenter).a == null || ((ecq) ajxFootMapPage.mPresenter).a == null)) {
            ajxFootMapPage.c = ((ecq) ajxFootMapPage.mPresenter).a.c();
        }
        if (ajxFootMapPage.c != null) {
            if (ajxFootMapPage.h == 0) {
                ajxFootMapPage.h = ajxFootMapPage.c.getHeight();
            }
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ajxFootMapPage.c.getLayoutParams();
            double d2 = (double) f2;
            layoutParams.topMargin = -((int) ((d2 - 0.5d) * 3.3333332538604736d * ((double) ajxFootMapPage.h)));
            if (d2 < 0.5d) {
                layoutParams.topMargin = 0;
            } else if (d2 > 0.8d) {
                layoutParams.topMargin = -ajxFootMapPage.h;
            }
            layoutParams.bottomMargin = -layoutParams.topMargin;
            ajxFootMapPage.c.requestLayout();
        }
    }

    static /* synthetic */ IAjxContext d(AjxFootMapPage ajxFootMapPage) {
        if (ajxFootMapPage.mAjxView == null) {
            return null;
        }
        return ajxFootMapPage.mAjxView.getAjxContext();
    }

    static /* synthetic */ void a(int i2) {
        if (i2 != -1) {
            aia aia = (aia) a.a.a(aia.class);
            if (aia != null) {
                aia.a(i2, (int) SDKFactory.getCoreType, (Pair<String, Object>) null);
            }
        }
    }

    static /* synthetic */ void e(AjxFootMapPage ajxFootMapPage) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("from", "ghjg");
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        LogManager.actionLogV2("P00094", "D002", jSONObject);
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.doReportError(ajxFootMapPage.getMapManager(), ajxFootMapPage.s);
        }
    }
}
