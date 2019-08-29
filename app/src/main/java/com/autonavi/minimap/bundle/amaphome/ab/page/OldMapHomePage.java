package com.autonavi.minimap.bundle.amaphome.ab.page;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.amap.bundle.voiceservice.dispatch.IVoiceCmdResponder;
import com.autonavi.bundle.amaphome.widget.OldMapHomePageWidgetManager;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.bundle.uitemplate.container.SlideContainer.b;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.diy.DIYMainMapWidgetManager;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.common.utils.Constant;
import com.autonavi.map.fragmentcontainer.LaunchMode.launchModeSingleTask;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import org.json.JSONException;
import org.json.JSONObject;

@OverlayPageProperty(overlays = {@OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.SaveOverlay, visible = true), @OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.LocalReportOverlay, visible = true)})
public class OldMapHomePage extends AbstractBaseMapPage<cun> implements arn, bek, bgm, IVoiceCmdResponder, launchModeSingleTask, IAMapHomePage, emc {
    public OldMapHomePageWidgetManager a = new OldMapHomePageWidgetManager(this);
    public boolean b = false;
    public bdu c;
    private DIYMainMapWidgetManager d;
    private cya e;

    public final void a() {
    }

    public final boolean a(b bVar) {
        return false;
    }

    public final void b() {
    }

    public final boolean b(b bVar) {
        return false;
    }

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

    public final boolean i() {
        return false;
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

    public void onCreate(Context context) {
        super.onCreate(context);
        setContentView(R.layout.old_main_home_page_layout);
        if (this.a != null) {
            this.a.addNearBySearchToPage();
            this.a.addRouteToPage();
        }
        this.d = new DIYMainMapWidgetManager();
        this.e = new cya();
        this.c = new bdu(this.d, this.e);
        this.c.e();
    }

    public final void k() {
        aqw e2 = e();
        if (e2 != null) {
            e2.b();
        }
    }

    public JSONObject getScenesData() {
        try {
            return new JSONObject("");
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public final aqw e() {
        return ((cun) this.mPresenter).a;
    }

    /* access modifiers changed from: private */
    /* renamed from: o */
    public cun createPresenter() {
        return new cun(this);
    }

    public final FrameLayout l() {
        View contentView = getContentView();
        if (contentView != null) {
            return (FrameLayout) contentView.findViewById(R.id.footer);
        }
        return null;
    }

    public final FrameLayout m() {
        View contentView = getContentView();
        if (contentView != null) {
            return (FrameLayout) contentView.findViewById(R.id.route_line_container);
        }
        return null;
    }

    public IWidgetProperty[] customPageWidgets() {
        if (this.a == null || this.b) {
            return null;
        }
        return this.a.getPageMapWidgets();
    }

    public void setPageHeader() {
        if (this.a != null && !this.b) {
            this.a.addHomePageHeader(this);
        }
    }

    public void onInitMapWidget() {
        if (this.a != null) {
            this.a.initMapHomePageWidget();
            this.a.setDIYManager(this.d);
            this.a.initFrequentLocationController(this.e, this);
        }
    }

    public final boolean f() {
        return this.b;
    }

    public void resetMapSkinState() {
        bdv.a((AbstractBaseMapPage) this);
    }

    public final void g() {
        a(false);
        this.b = false;
        if (this.a != null) {
            this.a.existImmersiveMode();
        }
        Stub.getMapWidgetManager().existImmersiveMode(Stub.getCombineWidgetsTag(WidgetType.GPS, WidgetType.SCALE), WidgetType.INDOOR_GUIDE, "floor", WidgetType.COMPASS);
        ((cun) this.mPresenter).b.i();
    }

    public final void h() {
        a(true);
        this.b = true;
        if (this.a != null) {
            this.a.enterImmersiveMode();
        }
        Stub.getMapWidgetManager().enterImmersiveMode(this.a.getImmersiveModeMargins(getContext()), Stub.getCombineWidgetsTag(WidgetType.GPS, WidgetType.SCALE), WidgetType.INDOOR_GUIDE, "floor", WidgetType.COMPASS);
        ((cun) this.mPresenter).b.h();
    }

    private static void a(boolean z) {
        IMainMapService iMainMapService = (IMainMapService) ank.a(IMainMapService.class);
        if (iMainMapService != null) {
            AbsMsgBoxDispatcher c2 = iMainMapService.c();
            if (c2 != null) {
                c2.setEnterImmersiveMapFlag(z);
            }
        }
    }

    public final ViewGroup j() {
        return (ViewGroup) getContentView().findViewById(R.id.home_page_drawer_content);
    }

    public boolean isShowPageHeader() {
        return !this.b;
    }

    public boolean onShowPoiTipView(PageBundle pageBundle, int i) {
        bci bci = (bci) a.a.a(bci.class);
        if (bci == null || pageBundle == null || !(pageBundle.getObject("POI") instanceof POI)) {
            return super.onShowPoiTipView(pageBundle, i);
        }
        bci.a(this, (POI) pageBundle.getObject("POI"), null, pageBundle.getBoolean(Constant.KEY_IS_FAVORITE, false));
        return true;
    }

    public final void c() {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.a();
        }
    }

    public final void d() {
        aia aia = (aia) a.a.a(aia.class);
        if (aia != null) {
            aia.b();
        }
    }

    public static void n() {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.c(9001);
            awo.f();
        }
    }

    public void onAnimationStarted(boolean z) {
        if (z) {
            if (this.b) {
                g();
            }
            IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
            if (iMapWidgetManagerService != null) {
                iMapWidgetManagerService.setContainerVisible(0);
            }
            Stub.getMapWidgetManager().setContainerBottomMargin(0, false);
            setPageHeader();
            onBindMapWidgets();
        }
    }

    public /* bridge */ /* synthetic */ bgo getPresenter() {
        return (cun) this.mPresenter;
    }
}
