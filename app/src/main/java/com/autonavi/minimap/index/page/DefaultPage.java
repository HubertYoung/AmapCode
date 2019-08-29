package com.autonavi.minimap.index.page;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController.DetailLayerState;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage.POI_DETAIL_TYPE;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer.OnTipChangedListener;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.agroup.api.IAgroupOverlayService.AgroupScenes;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.map.BasePoiOverlay;
import com.autonavi.minimap.map.BasePoiOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.widget.PoiDetailView;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.GpsOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.MapPointOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.SaveOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.TrafficOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.LocalReportOverlay, visible = true), @OvProperty(clickable = true, overlay = UvOverlay.GeoCodeOverlay, visible = true)})
public class DefaultPage extends MapBasePage<dld> implements awd, bgm, IVoiceCmdResponder, launchModeSingleTask, OnTipChangedListener, d {
    public BasePoiOverlay a;
    public dlc b;
    public boolean c;
    int d = 0;
    int e = 0;
    int f = 0;
    int g = 0;
    View h = null;
    boolean i = false;
    private PoiDetailView j;
    private FrameLayout k;
    private FrameLayout l;
    private dle m;
    private boolean n = false;
    private MapSharePreference o = new MapSharePreference(SharePreferenceName.SharedPreferences);
    /* access modifiers changed from: private */
    public defpackage.csb.a p;
    /* access modifiers changed from: private */
    public Runnable q = new Runnable() {
        public final void run() {
            if (DefaultPage.j(DefaultPage.this)) {
                DefaultPage.this.getSuspendManager();
            }
        }
    };

    class a extends BaseCQLayerOwner {
        private boolean b = false;

        public a(MapBasePage mapBasePage) {
            super(mapBasePage);
        }

        public final boolean isFullScreen() {
            return DefaultPage.this.a();
        }

        public final void onShowCQLayer() {
            super.onShowCQLayer();
            if (DefaultPage.this.p != null) {
                DefaultPage.this.p.j();
            }
            DefaultPage.this.h();
            DefaultPage.d(DefaultPage.this);
        }

        public final void onDismissCQLayer(int i) {
            super.onDismissCQLayer(i);
            DefaultPage.this.a(true);
            DefaultPage.this.c(true);
        }

        public final bra getSlidePanelManager() {
            return DefaultPage.this.mSlidePanelManager;
        }

        public final void onSlideEnd(boolean z) {
            if (DefaultPage.this.mCQLayerController != null && !DefaultPage.this.mCQLayerController.isShowing() && DefaultPage.j(DefaultPage.this)) {
                aho.b(DefaultPage.this.q);
                aho.a(DefaultPage.this.q, 500);
            }
            if (z && !this.b) {
                this.b = true;
                dld.f = true;
            }
        }
    }

    class b extends defpackage.csb.a {
        public b(MapBasePage mapBasePage) {
            super(mapBasePage);
        }

        public final void a() {
            super.a();
            DefaultPage.b();
            DefaultPage.e();
            if (DefaultPage.this.mCQLayerController != null && DefaultPage.this.mCQLayerController.isShowing()) {
                DefaultPage.this.mCQLayerController.hideCQLayer();
            }
            DefaultPage.a(DefaultPage.this, 8);
            DefaultPage.this.mPresenter;
            dld.a((String) "2");
            dld dld = (dld) DefaultPage.this.mPresenter;
            if (dld.b != null) {
                dkx dkx = dld.b;
                if (dkx.a != null) {
                    dkx.a.g();
                }
            }
            DefaultPage.this.getSuspendManager();
        }

        public final void b() {
            super.b();
            if (DefaultPage.this.mCQLayerController != null && !DefaultPage.this.mCQLayerController.isShowing()) {
                dld dld = (dld) DefaultPage.this.mPresenter;
                if (dld.a != null) {
                    dlb dlb = dld.a;
                    if (dlb.b != null) {
                        dlb.b.b().m();
                    }
                }
                if (dld.b != null) {
                    dkx dkx = dld.b;
                    if (dkx.a != null) {
                        dkx.a.h();
                    }
                }
                DefaultPage.c();
                DefaultPage.d();
            }
            DefaultPage.a(DefaultPage.this, 0);
        }
    }

    public void finishSelf() {
    }

    public long getScene() {
        return 1;
    }

    public long getScenesID() {
        return 1;
    }

    public int getTrafficEventSource() {
        return 0;
    }

    public boolean isInnerPage() {
        return false;
    }

    public boolean isUsePoiDelegate() {
        return true;
    }

    public boolean needKeepSessionAlive() {
        return false;
    }

    public boolean onPageMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        return true;
    }

    public defpackage.csb.a getReleatedTrafficEventHandler() {
        if (this.p == null) {
            this.p = new b(this);
        }
        return this.p;
    }

    /* access modifiers changed from: private */
    /* renamed from: g */
    public dld createPresenter() {
        return new dld(this);
    }

    public void onCreate(Context context) {
        la.e("EnterMainPage");
        super.onCreate(context);
        setContentView(R.layout.default_fragment);
        View contentView = getContentView();
        this.m = new dlf(this, contentView);
        this.b = new dlc(this);
        this.m.a(this.b, getSuspendManager(), getMapManager());
        this.k = (FrameLayout) contentView.findViewById(R.id.mapTopInteractiveView);
        this.l = (FrameLayout) contentView.findViewById(R.id.mapBottomInteractiveView);
        dismissViewFooter();
        if (getBottomTipsContainer() != null) {
            getBottomTipsContainer().addOnTipChangedListener(this);
        }
        this.a = new BasePoiOverlay(getGLMapView());
        this.a.setClearWhenLoseFocus(true);
        addOverlay(this.a);
        MapCustomizeManager mapCustomizeManager = getMapCustomizeManager();
        if (mapCustomizeManager != null) {
            mapCustomizeManager.enableView(-111349921);
        }
        cuh cuh = (cuh) defpackage.esb.a.a.a(cuh.class);
        if (cuh != null) {
            cuh.b().a(getClass(), AgroupScenes.MapHome, getArguments(), false);
        }
        la.e("DefaultPageOnCreate");
    }

    public View getMapSuspendView() {
        return this.m.e();
    }

    public void showDefaultMapTip() {
        super.showDefaultMapTip();
        if (this.c) {
            f();
        }
        dld dld = (dld) this.mPresenter;
        if (dld.a != null) {
            dlb dlb = dld.a;
            if (dlb.b != null) {
                dlb.b.b().i();
            }
        }
        if (a()) {
            b(true);
        }
    }

    public final boolean a() {
        if ((this.mCQLayerController == null || !this.mCQLayerController.isLayerShowing() || this.mCQLayerController.getDetailLayerState() != DetailLayerState.EXPAND) && this.k != null && this.k.getVisibility() == 8) {
            return true;
        }
        return false;
    }

    public void onTipShow() {
        if (this.c) {
            f();
            ((dld) this.mPresenter).e();
        }
        super.onTipShow();
        if (this.p != null) {
            this.p.j();
        }
        h();
    }

    public void onTipDimiss() {
        super.onTipDimiss();
        a(false);
    }

    public static void b() {
        ((IMainMapService) ank.a(IMainMapService.class)).a(czg.class);
    }

    public static void c() {
        ((IMainMapService) ank.a(IMainMapService.class)).a(czg.class);
    }

    public static void d() {
        bhz bhz = (bhz) ((IMainMapService) ank.a(IMainMapService.class)).a(bhz.class);
        if (bhz != null) {
            bhz.a();
        }
    }

    public static void e() {
        bhz bhz = (bhz) ((IMainMapService) ank.a(IMainMapService.class)).a(bhz.class);
        if (bhz != null) {
            bhz.b();
        }
    }

    /* access modifiers changed from: private */
    public void a(boolean z) {
        if ((z || getCQLayerController() == null || !getCQLayerController().isShowing()) && this.b != null) {
            this.b.a(0);
        }
        c();
        d();
        ((dld) this.mPresenter).b();
    }

    /* access modifiers changed from: private */
    public void h() {
        if (this.b != null) {
            this.b.a(8);
        }
        b();
        e();
        ((dld) this.mPresenter).a();
    }

    public void onPageConfigurationChanged(Configuration configuration) {
        super.onPageConfigurationChanged(configuration);
        int i2 = getResources().getConfiguration().orientation;
        if (this.j != null) {
            this.j.refreshByScreenState(i2 == 2);
        }
    }

    public void onPageDestroyView() {
        if (getBottomTipsContainer() != null) {
            getBottomTipsContainer().removeOnTipChangedListener(this);
        }
        if (this.m != null) {
            this.m.b();
        }
        super.onPageDestroyView();
    }

    public void onPageResume() {
        requestScreenOrientation(1);
        super.onPageResume();
        if (this.a != null && this.a.getSize() > 0) {
            ahm.a(new Runnable() {
                public final void run() {
                    if (DefaultPage.this.a != null && DefaultPage.this.a.getSize() > 0) {
                        ArrayList<BasePoiOverlayItem> arrayList = new ArrayList<>();
                        arrayList.addAll(DefaultPage.this.a.getItems());
                        DefaultPage.this.a.clear();
                        for (BasePoiOverlayItem basePoiOverlayItem : arrayList) {
                            if (basePoiOverlayItem != null) {
                                DefaultPage.this.a.addItem(basePoiOverlayItem);
                            }
                        }
                    }
                }
            });
        }
        bty gLMapView = getGLMapView();
        if (gLMapView != null) {
            bty e2 = gLMapView.e();
            if (e2 != null) {
                int l2 = bim.aa().l((String) "101");
                if (l2 == 0) {
                    if (gLMapView.p(false) == 0) {
                        e2.q(true);
                    }
                } else if (l2 == 1) {
                    e2.q(false);
                } else if (l2 == 2) {
                    e2.q(false);
                }
            }
        }
        bty mapView = getMapView();
        if (mapView != null) {
            mapView.a(false);
            mapView.t(this.o.getBooleanValue("blind_mode_status", false));
        }
        k();
        if (this.m != null) {
            this.m.a();
        }
        if (!(getActivity() == null || getActivity().getWindow() == null)) {
            View decorView = getActivity().getWindow().getDecorView();
            if (decorView != null) {
                decorView.setFocusable(true);
                decorView.setFocusableInTouchMode(true);
                decorView.requestFocus();
            }
            setSoftInputMode(32);
        }
        if (this.c) {
            IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
            if (iMainMapService != null) {
                iMainMapService.a(czm.class);
            }
        }
        aia aia = (aia) defpackage.esb.a.a.a(aia.class);
        if (aia != null) {
            aia.a();
        }
    }

    public void bindGpsWidget() {
        if (this.m != null) {
            this.m.a(getContext());
        }
    }

    public void onPageCover() {
        super.onPageCover();
        dld dld = (dld) this.mPresenter;
        dld.c = true;
        Real3DManager.a().d(dld.c());
        AMapLog.i("zyl", "onLeaveMainMap");
        if (((DefaultPage) dld.mPage).b != null) {
            bse bse = ((DefaultPage) dld.mPage).b.e;
            if (bse != null) {
                bse.b();
            }
        }
        dld.a((String) "2");
        ((DefaultPage) dld.mPage).b(true);
        dld.a(false);
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x004f  */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x008a  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean onPageNonFeatureClick() {
        /*
            r3 = this;
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r3.mPresenter
            dld r0 = (defpackage.dld) r0
            r0.e()
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r3.mPresenter
            dld r0 = (defpackage.dld) r0
            dlb r1 = r0.a
            r2 = 0
            if (r1 == 0) goto L_0x0021
            dlb r0 = r0.a
            awv r1 = r0.b
            if (r1 == 0) goto L_0x0021
            awv r0 = r0.b
            awu r0 = r0.b()
            boolean r0 = r0.j()
            goto L_0x0022
        L_0x0021:
            r0 = 0
        L_0x0022:
            csb$a r1 = r3.p
            if (r1 == 0) goto L_0x0036
            csb$a r1 = r3.p
            boolean r1 = r1.h()
            if (r1 == 0) goto L_0x0036
            defpackage.csb.a.k()
            boolean r0 = super.onPageNonFeatureClick()
            return r0
        L_0x0036:
            com.autonavi.minimap.map.BasePoiOverlay r1 = r3.a
            if (r1 == 0) goto L_0x0047
            com.autonavi.minimap.map.BasePoiOverlay r1 = r3.a
            int r1 = r1.getSize()
            if (r1 <= 0) goto L_0x0047
            com.autonavi.minimap.map.BasePoiOverlay r1 = r3.a
            r1.clear()
        L_0x0047:
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r3.mPresenter
            dld r1 = (defpackage.dld) r1
            boolean r1 = r1.d
            if (r1 != 0) goto L_0x0069
            boolean r1 = r3.j()
            if (r1 != 0) goto L_0x0065
            r3.dismissViewFooter()
            boolean r1 = r3.c
            if (r1 == 0) goto L_0x0061
            r1 = 1
            r3.b(r1)
            goto L_0x0072
        L_0x0061:
            r3.b(r2)
            goto L_0x0072
        L_0x0065:
            r3.dismissViewFooter()
            goto L_0x0072
        L_0x0069:
            r3.dismissViewFooter()
            com.autonavi.map.fragmentcontainer.page.IPresenter r1 = r3.mPresenter
            dld r1 = (defpackage.dld) r1
            r1.d = r2
        L_0x0072:
            dle r1 = r3.m
            if (r1 == 0) goto L_0x007b
            dle r1 = r3.m
            r1.d()
        L_0x007b:
            boolean r1 = r3.c
            if (r1 == 0) goto L_0x0084
            if (r0 != 0) goto L_0x0084
            r3.f()
        L_0x0084:
            boolean r1 = super.onPageNonFeatureClick()
            if (r0 == 0) goto L_0x0091
            com.autonavi.map.fragmentcontainer.page.IPresenter r0 = r3.mPresenter
            dld r0 = (defpackage.dld) r0
            r0.f()
        L_0x0091:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.index.page.DefaultPage.onPageNonFeatureClick():boolean");
    }

    private void b(boolean z) {
        if (!j()) {
            boolean a2 = a();
            int i2 = 0;
            boolean z2 = !z && !a();
            if (a2 != z2) {
                if (z2) {
                    i2 = 8;
                }
                this.k.setVisibility(i2);
                this.l.setVisibility(i2);
                if (this.m != null) {
                    this.m.a(z2);
                    dismissTip();
                    if (z2) {
                        e();
                        b();
                        return;
                    }
                    d();
                    c();
                }
            }
        }
    }

    private boolean i() {
        return getPoiDetailType() == POI_DETAIL_TYPE.CQ_VIEW ? this.mCQLayerController != null && this.mCQLayerController.isLayerShowing() : this.mPoiDelegate != null && this.mPoiDelegate.isPoiTipsShowing();
    }

    private boolean j() {
        return this.n || i() || getMapManager() == null || this.k == null || this.m == null;
    }

    public void onPageMapSurfaceCreated() {
        super.onPageMapSurfaceCreated();
        cdd.n().d(a());
    }

    public boolean isInVisibleRangeWhenRecoverCenter(GeoPoint geoPoint) {
        bty gLMapView = getGLMapView();
        if (gLMapView == null) {
            return false;
        }
        Point point = new Point();
        gLMapView.a((GLGeoPoint) geoPoint, point);
        Rect rect = null;
        if (getMapSuspendBtnView2() != null) {
            rect = new Rect();
            getMapSuspendBtnView2().getGlobalVisibleRect(rect);
        }
        if (rect == null || !rect.contains(point.x, point.y)) {
            return false;
        }
        return true;
    }

    public POI_DETAIL_TYPE getPoiDetailType() {
        return POI_DETAIL_TYPE.CQ_VIEW;
    }

    public BaseCQLayerOwner createCQLayerOwner() {
        return new a(this);
    }

    private void k() {
        if (this.h != null && this.m != null) {
            View f2 = this.m.f();
            if (!(f2 == null || f2 == this.h)) {
                c(true);
                this.d = f2.getPaddingLeft();
                this.e = f2.getPaddingTop();
                this.f = f2.getPaddingRight();
                this.g = f2.getPaddingBottom();
                this.h = f2;
            }
            if (this.mCQLayerController != null) {
                this.h.setPadding(this.d, this.e, this.f, this.g + agn.a(getContext(), (float) this.mCQLayerController.getCQTopOffset()));
            }
        }
    }

    /* access modifiers changed from: private */
    public void c(boolean z) {
        if (this.h != null) {
            this.h.setPadding(this.d, this.e, this.f, this.g);
            if (z) {
                this.d = 0;
                this.e = 0;
                this.f = 0;
                this.g = 0;
                this.h = null;
            }
        }
    }

    public void onTrunPoiDetialPage() {
        this.i = true;
    }

    public void onPageGpsBtnClicked() {
        dld.a((String) "2");
    }

    public final void f() {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (!(iMainMapService == null || ((czm) iMainMapService.a(czm.class)) == null)) {
            ((dld) this.mPresenter).f();
        }
        l();
    }

    private void l() {
        if (this.m != null) {
            this.m.g();
        }
    }

    public bgo getPresenter() {
        return (bgo) this.mPresenter;
    }

    public JSONObject getScenesData() {
        try {
            return new JSONObject("");
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public void onPagePause() {
        if (!this.i) {
            bty gLMapView = getGLMapView();
            if (gLMapView != null) {
                gLMapView.E();
            }
        } else {
            this.i = false;
        }
        if (!(getMapCustomizeManager() == null || getMapCustomizeManager().getMapLayerDialogCustomActions() == null)) {
            getMapCustomizeManager().getMapLayerDialogCustomActions().a = 1;
        }
        MapCustomizeManager mapCustomizeManager = getMapCustomizeManager();
        if (mapCustomizeManager != null) {
            mapCustomizeManager.disableView(33554432);
        }
        c(false);
        aia aia = (aia) defpackage.esb.a.a.a(aia.class);
        if (aia != null) {
            aia.b();
        }
        super.onPagePause();
    }

    public void onPageNewNodeFragmentBundle(PageBundle pageBundle) {
        if (pageBundle != null) {
            int i2 = pageBundle.getInt("bundle_key_token", -1);
            if (i2 > 0) {
                d.a.a(i2, pageBundle.getInt("bundle_key_code", 10000), (String) null);
            }
        }
    }

    static /* synthetic */ void d(DefaultPage defaultPage) {
        View f2 = defaultPage.m == null ? null : defaultPage.m.f();
        if (!(f2 == null || defaultPage.h == null || f2 == defaultPage.h)) {
            defaultPage.c(true);
        }
        if (defaultPage.h == null && f2 != null) {
            defaultPage.d = f2.getPaddingLeft();
            defaultPage.e = f2.getPaddingTop();
            defaultPage.f = f2.getPaddingRight();
            defaultPage.g = f2.getPaddingBottom();
            defaultPage.h = f2;
            defaultPage.k();
        }
    }

    static /* synthetic */ boolean j(DefaultPage defaultPage) {
        return (defaultPage.getSuspendManager() == null || defaultPage.getSuspendManager().h() == null || !defaultPage.getSuspendManager().h().a()) ? false : true;
    }

    static /* synthetic */ void a(DefaultPage defaultPage, int i2) {
        if (defaultPage.b != null) {
            defaultPage.b.a(i2);
        }
    }
}
