package com.autonavi.bundle.amaphome.page;

import android.content.Context;
import android.graphics.Point;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.media.TransportMediator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.amap.bundle.maplayer.api.VMapPageConfig;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.amaphome.components.centralcard.ModuleCentralCard;
import com.autonavi.bundle.amaphome.widget.MapHomePageWidgetManager;
import com.autonavi.bundle.uitemplate.ajx.ModuleSlideContainer;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.container.SlideContainer.b;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.c;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.gps.GpsWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.zoom.IZoomWidgetEventDelegate;
import com.autonavi.bundle.uitemplate.page.AbstractSlidablePage;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.IViewLayer;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.modules.ModuleCloudSync;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.views.AmapAjxView;
import com.autonavi.minimap.ajx3.views.AmapAjxView.AjxLifeCircleListener;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.statusbar.StatusBarManager;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.SaveOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.LocalReportOverlay, visible = true)})
@VMapPageConfig(name = "Main")
public class MapHomePage extends AbstractSlidablePage<aro> implements arn, bek, beo, bgm, IVoiceCmdResponder, c, IAMapHomePage, emc {
    public long a = 0;
    public AmapAjxView b;
    public aqn c;
    public int d;
    public boolean e = false;
    public aqq f;
    public aqo g;
    public boolean h = false;
    public boolean i = false;
    public PanelState j = PanelState.ANCHORED;
    public boolean k = false;
    public IMapWidgetManager l;
    public MapHomePageWidgetManager m = new MapHomePageWidgetManager(this);
    public b n = new b() {
        public final void a() {
            MapHomePage.this.n();
        }
    };
    public int o = 262;
    public boolean p = false;
    public IZoomWidgetEventDelegate q = new IZoomWidgetEventDelegate() {
        public final void onClickToZoomIn() {
            MapHomePage.this.i();
        }

        public final void onClickToZoomOut() {
            MapHomePage.this.i();
        }
    };
    private ben u;
    private PanelState v;

    public void finishSelf() {
    }

    public View getMapSuspendView() {
        return null;
    }

    public long getScene() {
        return 1;
    }

    public long getScenesID() {
        return 1;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean isMapHomePage() {
        return true;
    }

    public boolean isShowMapWidgets() {
        return true;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public final boolean u() {
        return true;
    }

    /* access modifiers changed from: private */
    /* renamed from: v */
    public aro createPresenter() {
        return new aro(this);
    }

    public final void k() {
        aqw e2 = e();
        if (e2 != null) {
            e2.b();
        }
    }

    @Nullable
    public final View a(Context context) {
        this.c = new aqn(context, this, this.r);
        this.c.d.c = new b() {
            public final void a() {
                aqv.this.m = true;
                aqv.this.a();
            }
        };
        return this.c.b;
    }

    @Nullable
    public final View b(Context context) {
        return aqp.a(context);
    }

    public final void a(boolean z) {
        float f2;
        int i2 = this.o;
        if (!z) {
            int l2 = l();
            if (this.k) {
                l2 = 0;
            }
            if (!(getMapManager() == null || getMapManager().getOverlayManager() == null || getMapManager().getOverlayManager().getGpsLayer() == null)) {
                cdx gpsLayer = getMapManager().getOverlayManager().getGpsLayer();
                Point a2 = aru.a(getContext(), l2);
                if (!(gpsLayer.a == null || a2 == null)) {
                    int i3 = a2.x;
                    int i4 = a2.y;
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("projectCenterX", i3);
                        jSONObject.put("projectCenterY", i4);
                    } catch (JSONException unused) {
                    }
                    gpsLayer.a.sendCommand(102209, jSONObject.toString());
                }
            }
        } else {
            if (!(getMapManager() == null || getMapManager().getOverlayManager() == null || getMapManager().getOverlayManager().getGpsLayer() == null)) {
                cdx gpsLayer2 = getMapManager().getOverlayManager().getGpsLayer();
                JSONObject jSONObject2 = new JSONObject();
                try {
                    jSONObject2.put("enable", 1);
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
                gpsLayer2.a.sendCommand(102202, jSONObject2.toString());
            }
            Context context = getContext();
            bty mapView = getMapView();
            if (!(context == null || mapView == null)) {
                if (context != null) {
                    int height = ags.a(context).height();
                    f2 = (float) ((((double) (((height - bet.a(context, i2)) / 2) + (euk.a(context) / 2))) * 1.0d) / (((double) height) * 1.0d));
                } else {
                    f2 = 0.5f;
                }
                mapView.a(0.5f, f2);
            }
        }
    }

    public final int l() {
        int i2 = this.o;
        PanelState panelState = this.r.getPanelState();
        switch (panelState) {
            case ANCHORED:
                i2 = this.o;
                break;
            case COLLAPSED:
                i2 = TransportMediator.KEYCODE_MEDIA_PLAY;
                break;
        }
        bez.b(getClass().getSimpleName(), "getQuickServiceOffset", new bew("panelState", panelState), new bew("offSetHeight", Integer.valueOf(i2)));
        return i2;
    }

    public static boolean m() {
        IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
        if (iMapWidgetManagerService != null) {
            GpsWidgetPresenter gpsWidgetPresenter = (GpsWidgetPresenter) iMapWidgetManagerService.getPresenter(WidgetType.GPS);
            if (gpsWidgetPresenter != null) {
                return gpsWidgetPresenter.isGpsBtnInFollowingState();
            }
        }
        return false;
    }

    @Nullable
    public final View c(Context context) {
        this.g = new aqo(this);
        bfo bfo = (bfo) a.a.a(bfo.class);
        if (bfo != null) {
            bfo.a((Object) this.g);
        }
        this.b = new AmapAjxView(context);
        this.b.setLoadingCallback(new Callback<AmapAjxView>() {
            public void callback(AmapAjxView amapAjxView) {
                ckb.a((String) "MapHomePage#LoadingCallback#callback");
                ckb.b();
                View preloadView = MapHomePage.this.r.getPreloadView();
                if (preloadView != null) {
                    preloadView.setVisibility(8);
                    MapHomePage.this.r.requestDisallowInterceptTouchEvent(true);
                }
            }

            public void error(Throwable th, boolean z) {
                ckb.a((String) "MapHomePage#LoadingCallback#error");
            }
        });
        this.b.setAjxLifeCircleListener(new AjxLifeCircleListener() {
            public final void onJsBack(Object obj, String str) {
            }

            public final void onAjxContxtCreated(IAjxContext iAjxContext) {
                ckb.a((String) "MapHomePage#onAjxContxtCreated");
                ModuleSlideContainer moduleSlideContainer = (ModuleSlideContainer) MapHomePage.this.b.getJsModule(ModuleSlideContainer.MODULE_NAME);
                if (moduleSlideContainer != null) {
                    moduleSlideContainer.attachContainer(MapHomePage.this.r);
                    moduleSlideContainer.attachRelativeAnimationAjxView(MapHomePage.this.b);
                    aqo e = MapHomePage.this.g;
                    e.a = moduleSlideContainer;
                    e.a(e.c);
                    moduleSlideContainer.setTipStateChangeListener(new ModuleSlideContainer.b() {
                        public final void a(boolean z) {
                            aqv.this.k = z;
                            if (aqv.this.k && aqv.this.l == 2) {
                                aqv.this.a();
                            }
                        }
                    });
                }
            }
        });
        this.r.setPanelState(this.j);
        String string = AMapAppGlobal.getApplication().getSharedPreferences("toolbox", 0).getString("recommendTools", "");
        String b2 = bim.aa().b((String) "1001", (String) "1001");
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("recommendTools", string);
            jSONObject.put(ModuleCloudSync.MODULE_NAME, b2);
            Ajx.getInstance().getMemoryStorage("toolbox").setItem("loadingCache", jSONObject.toString());
        } catch (JSONException e2) {
            e2.printStackTrace();
        }
        this.f = new aqq(context, this.m, this.r);
        this.f.f = new a() {
            public final void a(AmapAjxView amapAjxView) {
                ModuleSlideContainer moduleSlideContainer = (ModuleSlideContainer) amapAjxView.getJsModule(ModuleSlideContainer.MODULE_NAME);
                if (moduleSlideContainer != null) {
                    moduleSlideContainer.attachContainer(MapHomePage.this.r);
                }
            }
        };
        aqq aqq = this.f;
        aqq.b(aqq.a);
        aqq.b.setAjxLifeCircleListener(new AjxLifeCircleListener() {
            public final void onJsBack(Object obj, String str) {
            }

            public final void onAjxContxtCreated(IAjxContext iAjxContext) {
                ModuleCentralCard moduleCentralCard = (ModuleCentralCard) aqq.this.b.getJsModule("centralCard");
                if (moduleCentralCard != null) {
                    moduleCentralCard.setCentralCardListener(aqq.this);
                    moduleCentralCard.setMaxWidth(DimensionUtils.pixelToStandardUnit((float) aqq.this.d.getCCardContainerWidth()));
                }
                if (aqq.this.f != null) {
                    aqq.this.f.a(aqq.this.b);
                }
                ModuleSlideContainer moduleSlideContainer = (ModuleSlideContainer) aqq.this.b.getJsModule(ModuleSlideContainer.MODULE_NAME);
                if (moduleSlideContainer != null) {
                    moduleSlideContainer.attachRelativeAnimationAjxView(aqq.this.b);
                }
                if (aqq.this.b.getChildAt(0) != null) {
                    aqq.this.b.setClipChildren(false);
                }
            }
        });
        return this.b;
    }

    public final void n() {
        if (this.r != null) {
            this.r.removeTouchEventListener(this.n);
        }
        this.g.b();
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        ckb.a((String) "MapHomePage#onCreate");
        this.r.hideCloseButton();
        if (getResources().getDisplayMetrics().widthPixels <= bet.a(context, 320)) {
            this.r.setAnchorHeight(bet.a(context, this.o));
        }
        this.l = Stub.getMapWidgetManager();
        this.r.addPanelSlideListener(this);
        this.r.post(new Runnable() {
            public final void run() {
                MapHomePage.this.a(0.0f);
            }
        });
        a(true);
        ckb.a((String) "MapHomePage#QuickService#loadJs");
        this.r.computeSlideRange();
        this.b.load("path://amap_bundle_quickservice/src/pages/QuickService.page.js", "", "", getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels - bet.a(getContext(), 100));
        aqq aqq = this.f;
        ckb.a((String) "MapHomePage#CentralCard#loadJs");
        aqq.b.load("path://amap_bundle_quickservice/src/pages/CentralCardPage.page.js", null, null, aqq.d.getCCardContainerWidth(), DimensionUtils.standardUnitToPixel(aqq.a));
        aqq.b.post(new Runnable() {
            public final void run() {
                aqq.d(aqq.this);
            }
        });
    }

    public final void d(Context context) {
        int a2 = bet.a(context, TransportMediator.KEYCODE_MEDIA_PLAY);
        int a3 = bet.a(context, this.o);
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            int i2 = iMainMapService.i();
            if (-1 != i2) {
                a3 = bet.a(context, i2);
                this.o = i2;
            }
        }
        a(a2, a3);
    }

    public final void b() {
        if (isPageSwitch()) {
            aro aro = (aro) this.mPresenter;
            if (aro.e != null) {
                aro.e.a();
            }
        }
        aro aro2 = (aro) this.mPresenter;
        this.r.onPageHide(this.p && aro2 != null && aro2.f);
    }

    public final void d() {
        if (this.u.e() == this) {
            this.p = true;
        }
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.b();
        }
    }

    public void onAnimationStarted(boolean z) {
        if (z) {
            StatusBarManager.d().c();
            StatusBarManager.d().a((bid) this);
            this.mMapWidgetService.restoreContainerConfig();
            this.mMapWidgetService.releaseContainerConfig();
            if (this.k) {
                g();
            }
            IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
            if (iMapWidgetManagerService != null) {
                iMapWidgetManagerService.setContainerVisible(0);
            }
            this.l.setContainerBottomMargin(this.d, true);
            this.e = true;
            onBindMapWidgets();
        }
    }

    public final void a(View view, float f2) {
        a(f2);
    }

    /* access modifiers changed from: private */
    public void a(float f2) {
        float anchorPoint = this.r.getAnchorPoint();
        if (f2 <= anchorPoint) {
            int slideOffsetHeight = this.r.getSlideOffsetHeight();
            this.d = slideOffsetHeight;
            this.l.setContainerBottomMargin(slideOffsetHeight, false);
            this.l.setContainerAlpha(1.0f);
        } else {
            this.l.setContainerAlpha((((1.0f + anchorPoint) + anchorPoint) + anchorPoint) - (f2 * 4.0f));
        }
        this.g.a(this.r.getAnchorHeight());
    }

    public final void a(View view, PanelState panelState, PanelState panelState2) {
        if (!(this.c == null || this.c.d == null)) {
            this.c.d.a();
        }
        StatusBarManager.d().a((panelState2 == PanelState.EXPANDED || panelState2 == PanelState.HIDDEN) ? false : true);
    }

    public JSONObject getScenesData() {
        try {
            return new JSONObject("");
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final ViewGroup j() {
        return this.s;
    }

    public final void h() {
        if (w()) {
            this.u.f();
            b(true);
            if (this.r.getPanelState() == PanelState.EXPANDED) {
                this.r.setPanelState(PanelState.ANCHORED, true);
                return;
            }
            this.k = true;
            this.v = this.r.getPanelState();
            this.r.setPanelState(PanelState.HIDDEN, true);
            Stub.getMapWidgetManager().enterImmersiveMode(WidgetType.GPS, WidgetType.SCALE, WidgetType.INDOOR_GUIDE, "floor", WidgetType.COMPASS, WidgetType.SCENIC_AREA);
            if (this.m != null) {
                this.m.updateWidgetContainerPadding(10);
            }
            ((aro) this.mPresenter).d.h();
        }
    }

    private static void b(boolean z) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            AbsMsgBoxDispatcher c2 = iMainMapService.c();
            if (c2 != null) {
                c2.setEnterImmersiveMapFlag(z);
            }
        }
    }

    public final void g() {
        if (w()) {
            if (this.v == PanelState.HIDDEN) {
                this.v = PanelState.COLLAPSED;
            }
            a(this.v);
        }
    }

    public final void a(PanelState panelState) {
        this.u.g();
        this.r.setPanelState(panelState, true);
        this.k = false;
        Stub.getMapWidgetManager().existImmersiveMode(WidgetType.GPS, WidgetType.WEATHER_RESTRICT, WidgetType.SCALE, WidgetType.INDOOR_GUIDE, "floor", WidgetType.COMPASS, WidgetType.SCENIC_AREA);
        if (this.m != null) {
            this.m.updateWidgetContainerPadding(-5);
        }
        ((aro) this.mPresenter).d.i();
        b(false);
        this.f.b();
    }

    public final boolean i() {
        if (!w() || this.k || PanelState.COLLAPSED == this.r.getPanelState() || SystemClock.currentThreadTimeMillis() - this.a <= 200) {
            return false;
        }
        this.r.setPanelState(PanelState.COLLAPSED, true);
        return true;
    }

    public final boolean f() {
        return this.k;
    }

    public void resetMapSkinState() {
        bdv.a((AbstractBaseMapPage) this);
    }

    public final boolean a(b bVar) {
        if (this.r == null) {
            return false;
        }
        this.r.addTouchEventListener(bVar);
        return true;
    }

    public final boolean b(b bVar) {
        if (this.r == null) {
            return false;
        }
        this.r.removeTouchEventListener(bVar);
        return true;
    }

    public IWidgetProperty[] customPageWidgets() {
        if (this.m == null || this.k) {
            return null;
        }
        return this.m.getPageMapWidgets();
    }

    public void onInitMapWidget() {
        if (this.m != null && !this.k) {
            this.m.initMapHomePageWidget();
        }
    }

    public final void q() {
        if (this.j == this.r.getPanelState()) {
            int i2 = ((MarginLayoutParams) this.b.getLayoutParams()).topMargin;
            DimensionUtils.standardUnitToPixel((float) ((ModuleSlideContainer) this.b.getJsModule(ModuleSlideContainer.MODULE_NAME)).getSafePaddingTop());
            aqp.a();
        }
    }

    public final void a(ben ben) {
        this.u = ben;
    }

    public final void r() {
        if (this.u != null && (this.u instanceof MapHomeTabPage)) {
            ViewGroup viewGroup = (ViewGroup) ((MapHomeTabPage) this.u).getContentView();
            if (viewGroup != null) {
                viewGroup.addView(this.s);
                this.s.bringToFront();
            }
        }
    }

    public void showViewLayer(IViewLayer iViewLayer) {
        this.u.showViewLayer(iViewLayer);
    }

    public void dismissViewLayer(IViewLayer iViewLayer) {
        this.u.dismissViewLayer(iViewLayer);
    }

    public final void s() {
        onAnimationStarted(true);
        this.r.requestLayout();
    }

    public final void t() {
        this.c.c.setShowInit(false);
        this.h = true;
        onAnimationStarted(false);
    }

    private static boolean w() {
        return "1".equals(new MapSharePreference((String) "basemap").getStringValue("new_user_guide_is_shown", ""));
    }

    public final aqw e() {
        return ((aro) this.mPresenter).b;
    }

    public final void a() {
        aro aro = (aro) this.mPresenter;
        this.r.onPageShow(this.p && aro != null && aro.g);
        if (aro != null) {
            aro.g = false;
        }
    }

    public final void c() {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a();
        }
    }

    public final boolean o() {
        boolean z;
        aro aro = (aro) this.mPresenter;
        if (!aro.b.b() && !aro.d.g() && !aro.c.a()) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            return true;
        }
        if (this.k || PanelState.EXPANDED != this.r.getPanelState()) {
            return false;
        }
        this.r.setPanelState(PanelState.ANCHORED, true);
        return true;
    }

    public static void p() {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
            awo.f();
        }
    }

    public boolean onShowPoiTipView(PageBundle pageBundle, int i2) {
        bci bci = (bci) a.a.a(bci.class);
        if (bci == null || pageBundle == null || !(pageBundle.getObject("POI") instanceof POI)) {
            return super.onShowPoiTipView(pageBundle, i2);
        }
        bci.a(this, (POI) pageBundle.getObject("POI"), null, pageBundle.getBoolean(Constant.KEY_IS_FAVORITE, false));
        return true;
    }

    public void onBindMapWidgets() {
        if (!this.k) {
            super.onBindMapWidgets();
        }
    }

    public /* bridge */ /* synthetic */ bgo getPresenter() {
        return (aro) this.mPresenter;
    }
}
