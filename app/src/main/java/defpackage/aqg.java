package defpackage;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.network.response.AosParserResponse;
import com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder;
import com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder.MethodType;
import com.autonavi.bundle.amaphome.compat.service.MainMapCallbackHolder.a;
import com.autonavi.bundle.amaphome.msg.IMapHomeMsgDispatchService.Sub;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.bundle.life.api.api.IScenicGuideItemClickCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayCloseCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayRouteCloseCallback;
import com.autonavi.bundle.life.api.api.IScenicPlayRouteItemClickCallback;
import com.autonavi.bundle.life.api.api.IScenicPlaySelectCallback;
import com.autonavi.bundle.life.api.entity.ScenicGuideListItemEntity;
import com.autonavi.bundle.life.api.entity.ScenicPlayEntity;
import com.autonavi.bundle.scenicarea.scenicguide.ScenicGuideView;
import com.autonavi.bundle.scenicarea.scenicguide.ScenicGuideWidget;
import com.autonavi.bundle.scenicarea.scenicplay.ScenicPlayView;
import com.autonavi.bundle.scenicarea.scenicplay.ScenicPlayWidget;
import com.autonavi.bundle.scenicarea.scenicplayroute.ScenicPlayRouteView;
import com.autonavi.bundle.scenicarea.scenicplayroute.ScenicPlayRouteWidget;
import com.autonavi.bundle.scenicarea.scenicspeak.ScenicSpeakView;
import com.autonavi.bundle.scenicarea.scenicspeak.ScenicSpeakWidget;
import com.autonavi.bundle.uitemplate.container.internal.SlidableLayout.PanelState;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetProperty;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.LayerWidgetPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.layer.MapLayerMapWidget;
import com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea.ScenicAreaPresenter;
import com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea.ScenicAreaView;
import com.autonavi.bundle.uitemplate.mapwidget.widget.scenicarea.ScenicAreaWidget;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.search.fragment.SearchCQDetailPage;
import com.autonavi.minimap.ajx3.modules.ModuleLongLinkService;
import com.autonavi.minimap.bundle.amaphome.ab.page.OldMapHomePage;
import com.autonavi.minimap.bundle.maphome.service.IMainMapService;
import com.autonavi.minimap.bundle.msgbox.dispatcher.AbsMsgBoxDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* renamed from: aqg reason: default package */
/* compiled from: MainMapService */
public class aqg implements IMainMapService {
    public aqf a;
    public MainMapCallbackHolder b = new MainMapCallbackHolder();
    public List<btw> c = new ArrayList();
    public HashMap<Class, Object> d = new HashMap<>();
    private arm e = new arm().a();
    private ScenicGuideWidget f = null;
    private ScenicSpeakWidget g = null;
    /* access modifiers changed from: private */
    public ScenicPlayWidget h = null;
    private ScenicPlayRouteWidget i = null;

    public final boolean a() {
        return this.a != null;
    }

    public final <T extends czi> T a(Class<T> cls) {
        if (!j()) {
            return null;
        }
        if (this.d.containsKey(cls)) {
            return (czi) this.d.get(cls);
        }
        T a2 = aqh.a(cls, this.a);
        if (a2 != null) {
            this.d.put(cls, a2);
        }
        return a2;
    }

    public final void a(Object obj) {
        if (obj != null && j()) {
            MainMapCallbackHolder mainMapCallbackHolder = this.b;
            if (obj instanceof czy) {
                mainMapCallbackHolder.a(obj, MethodType.RESUME);
                mainMapCallbackHolder.a(obj, MethodType.PAUSE);
            }
            if (obj instanceof czz) {
                mainMapCallbackHolder.a(obj, MethodType.START);
                mainMapCallbackHolder.a(obj, MethodType.STOP);
            }
            if (obj instanceof czu) {
                mainMapCallbackHolder.a(obj, MethodType.CREATE);
                mainMapCallbackHolder.a(obj, MethodType.DESTROY);
            }
            if (obj instanceof IPageStateListener) {
                mainMapCallbackHolder.a(obj, MethodType.COVER);
                mainMapCallbackHolder.a(obj, MethodType.APPEAR);
            }
            if (obj instanceof czw) {
                mainMapCallbackHolder.a(obj, MethodType.NEW_INTENT);
            }
            if (obj instanceof czs) {
                mainMapCallbackHolder.a(obj, MethodType.BACK_PRESSED);
            }
            if (obj instanceof btx) {
                mainMapCallbackHolder.a(obj, MethodType.SURFACE_CREATED);
                mainMapCallbackHolder.a(obj, MethodType.SURFACE_DESTROY);
                mainMapCallbackHolder.a(obj, MethodType.SURFACE_CHANGED);
            }
            if (obj instanceof czx) {
                mainMapCallbackHolder.a(obj, MethodType.RESULT);
            }
            if (obj instanceof daa) {
                mainMapCallbackHolder.a(obj, MethodType.WINDOW_FOCUS_CHANGED);
            }
            if (obj instanceof czv) {
                mainMapCallbackHolder.a(obj, MethodType.FULL_SCREEN_STATE_CHANGED);
            }
            if (obj instanceof czt) {
                mainMapCallbackHolder.a(obj, MethodType.CONFIGURATION_CHANGED);
            }
            if (obj instanceof czr) {
                mainMapCallbackHolder.a(obj, MethodType.INDOOR);
                mainMapCallbackHolder.a(obj, MethodType.SCENIC);
            }
            if (obj instanceof czo) {
                mainMapCallbackHolder.a(obj, MethodType.ACTIVITY_RESULT);
            }
            if (obj instanceof czq) {
                mainMapCallbackHolder.a(obj, MethodType.ACTIVITY_START);
                mainMapCallbackHolder.a(obj, MethodType.ACTIVITY_STOP);
            }
            if (obj instanceof czp) {
                mainMapCallbackHolder.a(obj, MethodType.ACTIVITY_RESUME);
                mainMapCallbackHolder.a(obj, MethodType.ACTIVITY_PAUSE);
            }
            if (obj instanceof dab) {
                mainMapCallbackHolder.a(obj, MethodType.REAL_TIME_BUS_STATE_CHANGED);
            }
            if ((obj instanceof btw) && j()) {
                aqf aqf = this.a;
                btw btw = (btw) obj;
                if (!(aqf.b == null || btw == null)) {
                    aqf.b.pushMapEventListener(btw);
                }
                this.c.add(btw);
            }
        }
    }

    public final void b(Object obj) {
        if (obj != null) {
            MainMapCallbackHolder mainMapCallbackHolder = this.b;
            if (mainMapCallbackHolder.a.size() > 0) {
                for (List next : mainMapCallbackHolder.a.values()) {
                    if (next != null && next.contains(obj)) {
                        next.remove(obj);
                    }
                }
            }
            if (mainMapCallbackHolder.b.size() > 0) {
                for (List next2 : mainMapCallbackHolder.b.values()) {
                    if (next2 != null && next2.size() > 0) {
                        Iterator it = next2.iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            a aVar = (a) it.next();
                            if (aVar != null && aVar.b == obj) {
                                next2.remove(aVar);
                                break;
                            }
                        }
                    }
                }
            }
            if ((obj instanceof btw) && j()) {
                this.a.a((btw) obj);
                this.c.remove(obj);
            }
        }
    }

    @Nullable
    public final cde b() {
        if (j()) {
            return this.a.c;
        }
        return null;
    }

    public final AbsMsgBoxDispatcher c() {
        return Sub.getInstance().getMsgBoxDispatcher();
    }

    @Nullable
    public final MapManager d() {
        if (j()) {
            return this.a.b;
        }
        return null;
    }

    @Nullable
    public final bid e() {
        if (j()) {
            return this.a.f;
        }
        return null;
    }

    @Nullable
    public final bty f() {
        if (j()) {
            return this.a.d;
        }
        return null;
    }

    @Nullable
    public final bty g() {
        if (j()) {
            return this.a.e;
        }
        return null;
    }

    public final void h() {
        LayerWidgetPresenter layerWidgetPresenter = (LayerWidgetPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.LAYER);
        if (layerWidgetPresenter != null && layerWidgetPresenter.isWidgetNotNull()) {
            ((MapLayerMapWidget) layerWidgetPresenter.getWidget()).showTip();
        }
    }

    public final void a(boolean z, String str, String str2) {
        ScenicAreaPresenter scenicAreaPresenter = (ScenicAreaPresenter) ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).getPresenter(WidgetType.SCENIC_AREA);
        if (scenicAreaPresenter != null && scenicAreaPresenter.isWidgetNotNull()) {
            View contentView = ((ScenicAreaWidget) scenicAreaPresenter.getWidget()).getContentView();
            if (contentView != null && (contentView instanceof ScenicAreaView)) {
                if (z) {
                    ((ScenicAreaView) contentView).initRootView(str, str2);
                }
                int i2 = 0;
                if (z != (contentView.getVisibility() == 0)) {
                    IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
                    if (!z) {
                        i2 = 8;
                    }
                    iMapWidgetManagerService.setWidgetVisibleForType(WidgetType.SCENIC_AREA, i2);
                }
            }
        }
    }

    public final void a(AosParserResponse aosParserResponse) {
        arn arn = null;
        String responseBodyString = aosParserResponse == null ? null : aosParserResponse.getResponseBodyString();
        bez.b(getClass().getSimpleName(), "responseNearBySearchData", new bew(ModuleLongLinkService.CALLBACK_KEY_RESPONSE, responseBodyString));
        if (this.a != null) {
            arn = this.a.f;
        }
        if (arn != null) {
            if (arn instanceof MapHomePage) {
                MapHomePage mapHomePage = (MapHomePage) arn;
                if (mapHomePage.m != null) {
                    mapHomePage.m.responseNearbyData(responseBodyString);
                }
            } else if (arn instanceof OldMapHomePage) {
                OldMapHomePage oldMapHomePage = (OldMapHomePage) arn;
                if (oldMapHomePage.a != null) {
                    oldMapHomePage.a.responseNearbyData(responseBodyString);
                }
            }
        }
    }

    public boolean isScenicGuideShown() {
        if (this.f == null || this.f.getContentView().getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public boolean isScenicSpeakShown() {
        if (this.g == null || this.g.getContentView().getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public boolean isScenicPlayShown() {
        if (this.h == null || this.h.getContentView().getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public boolean isScenicPlayRouteShown() {
        if (this.i == null || this.i.getContentView().getVisibility() != 0) {
            return false;
        }
        return true;
    }

    public void setScenicPlayRoute(boolean z, ScenicPlayEntity scenicPlayEntity, IScenicPlayRouteItemClickCallback iScenicPlayRouteItemClickCallback, IScenicPlayCloseCallback iScenicPlayCloseCallback, amv amv, int i2) {
        if (z) {
            final IScenicPlayCloseCallback iScenicPlayCloseCallback2 = iScenicPlayCloseCallback;
            AnonymousClass1 r4 = new IScenicPlayRouteCloseCallback() {
                public final void a() {
                    ((ScenicPlayView) aqg.this.h.getContentView()).closeScenicPlayWidget(true, iScenicPlayCloseCallback2);
                    aqg.this.k();
                }
            };
            Context appContext = AMapPageUtil.getAppContext();
            if (this.i == null) {
                this.i = new ScenicPlayRouteWidget(appContext);
                WidgetProperty widgetProperty = new WidgetProperty(10, 48, "scenic_play_route", 0, a(0, 0, -3));
                WidgetProperty inImmersiveModeVisible = widgetProperty.setWillBindPages(new String[]{"MapHomePage", SearchCQDetailPage.a}).setInImmersiveModeVisible(true);
                ((ScenicPlayRouteView) this.i.getContentView()).initRootView(scenicPlayEntity, iScenicPlayRouteItemClickCallback, r4, amv, i2);
                this.i.setWidgetProperty(inImmersiveModeVisible);
                ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).addWidget(this.i);
            }
            return;
        }
        k();
    }

    public void initScenicGuideView(ArrayList<ScenicGuideListItemEntity> arrayList) {
        if (this.f != null) {
            ((ScenicGuideView) this.f.getContentView()).setScenicGuideUnselected(arrayList);
        }
    }

    public void closeScenicSpeakOperateView() {
        if (this.g != null) {
            ((ScenicSpeakView) this.g.getContentView()).closeOperateView();
        }
    }

    public void closeScenicPlayWidget(IScenicPlayCloseCallback iScenicPlayCloseCallback, boolean z) {
        if (this.h != null) {
            ((ScenicPlayView) this.h.getContentView()).closeScenicPlayWidget(z, iScenicPlayCloseCallback);
        }
    }

    /* access modifiers changed from: private */
    public void k() {
        if (this.i != null) {
            ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).removeWidget(this.i);
            this.i = null;
        }
    }

    public final void a(boolean z) {
        Object obj;
        boolean z2;
        IMapWidgetManagerService iMapWidgetManagerService = (IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class);
        String str = null;
        if (iMapWidgetManagerService == null || !iMapWidgetManagerService.isNewHomePage()) {
            obj = null;
        } else {
            obj = this.a == null ? null : this.a.f;
            if (obj != null && (obj instanceof MapHomePage)) {
                ((MapHomePage) obj).i = z;
            }
        }
        String simpleName = getClass().getSimpleName();
        bew[] bewArr = new bew[4];
        bew bew = new bew("isScreenCenter", Boolean.valueOf(z));
        boolean z3 = false;
        bewArr[0] = bew;
        if (iMapWidgetManagerService == null) {
            z2 = false;
        } else {
            z2 = iMapWidgetManagerService.isNewHomePage();
        }
        bewArr[1] = new bew("\nisNewHomePage", Boolean.valueOf(z2));
        if (obj != null) {
            str = obj.getClass().getSimpleName();
        }
        bewArr[2] = new bew("\ncurPage", str);
        if (obj != null) {
            z3 = obj instanceof MapHomePage;
        }
        bewArr[3] = new bew("\ncurPage instanceof MapHomePage?", Boolean.valueOf(z3));
        bez.a(simpleName, "setGpsOverlayRegionCenter", bewArr);
    }

    public final int i() {
        int i2;
        if (this.e != null) {
            i2 = this.e.a;
        } else {
            i2 = -1;
        }
        bez.a(getClass().getSimpleName(), "getQCCloudConfigHeight", new bew("QCCloudConfigHeight", Integer.valueOf(i2)));
        return i2;
    }

    public final boolean j() {
        if (a()) {
            return true;
        }
        AMapLog.e("MainMapService", "service is not running!");
        return false;
    }

    private static MarginLayoutParams a(int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(-2, -2);
        Context appContext = AMapPageUtil.getAppContext();
        marginLayoutParams.leftMargin = bet.a(appContext, i2);
        marginLayoutParams.topMargin = bet.a(appContext, i3);
        marginLayoutParams.rightMargin = bet.a(appContext, 0);
        marginLayoutParams.bottomMargin = bet.a(appContext, i4);
        return marginLayoutParams;
    }

    public void setMapHomePageStateCollapsed() {
        arn arn = this.a == null ? null : this.a.f;
        if (arn != null && (arn instanceof MapHomePage)) {
            MapHomePage mapHomePage = (MapHomePage) arn;
            if (mapHomePage.k) {
                mapHomePage.a(PanelState.COLLAPSED);
                return;
            }
            PanelState panelState = PanelState.COLLAPSED;
            if (mapHomePage.r != null) {
                mapHomePage.r.setPanelState(panelState, true);
            }
        }
    }

    public int getScenicPlayWidgetLocationY() {
        if (this.h == null) {
            return 200;
        }
        int[] iArr = new int[2];
        this.h.getContentView().getLocationOnScreen(iArr);
        return iArr[1];
    }

    public void setScenicGuide(boolean z, ArrayList<ScenicGuideListItemEntity> arrayList, IScenicGuideItemClickCallback iScenicGuideItemClickCallback) {
        if (z) {
            Context appContext = AMapPageUtil.getAppContext();
            if (this.f == null) {
                this.f = new ScenicGuideWidget(appContext);
                WidgetProperty widgetProperty = new WidgetProperty(3, 48, "scenic_guide", 6, a(3, 0, 0));
                WidgetProperty inImmersiveModeVisible = widgetProperty.setWillBindPages(new String[]{"MapHomePage", SearchCQDetailPage.a}).setInImmersiveModeVisible(true);
                ((ScenicGuideView) this.f.getContentView()).initRootView(arrayList, iScenicGuideItemClickCallback);
                this.f.setWidgetProperty(inImmersiveModeVisible);
                ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).addWidget(this.f);
            }
            return;
        }
        if (this.f != null) {
            ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).removeWidget(this.f);
            this.f = null;
        }
    }

    public void setScenicSpeak(boolean z, String str, String str2, String str3, int i2, boolean z2, amv amv) {
        if (z) {
            Context appContext = AMapPageUtil.getAppContext();
            if (this.g == null) {
                this.g = new ScenicSpeakWidget(appContext);
                WidgetProperty widgetProperty = new WidgetProperty(3, 48, "scenic_speak", 5, a(0, 1, 0));
                WidgetProperty inImmersiveModeVisible = widgetProperty.setWillBindPages(new String[]{"MapHomePage", SearchCQDetailPage.a}).setInImmersiveModeVisible(true);
                ((ScenicSpeakView) this.g.getContentView()).initRootView(str, str2, str3, i2, z2, amv);
                this.g.setWidgetProperty(inImmersiveModeVisible);
                ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).addWidget(this.g);
            }
            return;
        }
        if (this.g != null) {
            ((ScenicSpeakView) this.g.getContentView()).removeView();
            ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).removeWidget(this.g);
            this.g = null;
        }
    }

    public void setScenicPlay(boolean z, IScenicPlaySelectCallback iScenicPlaySelectCallback) {
        if (z) {
            Context appContext = AMapPageUtil.getAppContext();
            if (this.h == null) {
                this.h = new ScenicPlayWidget(appContext);
                WidgetProperty widgetProperty = new WidgetProperty(3, 48, "scenic_play", 4, a(0, isScenicSpeakShown() ^ true ? 1 : 0, 0));
                WidgetProperty inImmersiveModeVisible = widgetProperty.setWillBindPages(new String[]{"MapHomePage", SearchCQDetailPage.a}).setInImmersiveModeVisible(true);
                ((ScenicPlayView) this.h.getContentView()).initRootView(iScenicPlaySelectCallback);
                this.h.setWidgetProperty(inImmersiveModeVisible);
                ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).addWidget(this.h);
            }
            return;
        }
        if (this.h != null) {
            ((IMapWidgetManagerService) ank.a(IMapWidgetManagerService.class)).removeWidget(this.h);
            this.h = null;
        }
    }
}
