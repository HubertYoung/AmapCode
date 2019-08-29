package com.autonavi.map.fragmentcontainer.page;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.maplayer.api.VMapPageConfig;
import com.autonavi.ae.gmap.gloverlay.BaseOverlay;
import com.autonavi.bundle.mapevent.listener.MainMapEventListener;
import com.autonavi.bundle.uitemplate.api.IMapWidgetService;
import com.autonavi.bundle.uitemplate.api.IWidgetProperty;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.jni.eyrie.amap.redesign.maps.vmap.VMapPage;
import com.autonavi.map.core.MapCustomizeManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.MapInteractiveRelativeLayout;
import com.autonavi.map.fragmentcontainer.page.IMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer.OnTipChangedListener;
import com.autonavi.map.fragmentcontainer.page.mappage.MapMethodDispatchRecord;
import com.autonavi.map.fragmentcontainer.page.mappage.UniversalOverlayManager;
import com.autonavi.minimap.R;
import com.autonavi.minimap.map.overlayholder.IOverlayHolder;
import com.autonavi.minimap.map.overlayholder.OverlayHolderImpl;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public abstract class AbstractBaseMapPage<Presenter extends IMapPagePresenter> extends AbstractBasePage<Presenter> implements IMapPage, OnTipChangedListener {
    public static String PAGE_EXTENDS_LAYER = "PAGE_EXTENDS_LAYER";
    private MapMethodDispatchRecord mDispatchRecord;
    private boolean mIsPageInit = false;
    private LinkedHashSet<MainMapEventListener> mMainMapEventListeners = new LinkedHashSet<>();
    private MapCustomizeManager mMapCustomizeManager;
    private awb mMapEventService = ((awb) a.a.a(awb.class));
    private View mMapSuspendBtnView;
    private bua mMapVirtualizationPage;
    protected IMapWidgetService mMapWidgetService = ((IMapWidgetService) ank.a(IMapWidgetService.class));
    private OverlayHolderImpl mOverlayHolder;
    private ccy mSuspendWidgetHelper;
    private TipContainer mTipContainer;
    private UniversalOverlayManager mUniversalOverlayManager;
    private VMapPage mVMapPage;

    /* access modifiers changed from: protected */
    public abstract Presenter createPresenter();

    public abstract View getMapSuspendView();

    public void onInitMapWidget() {
    }

    public void onTipDimiss() {
    }

    public void onTipShow() {
    }

    public void resetMapSkinState() {
    }

    public void setPageHeader() {
    }

    /* access modifiers changed from: protected */
    public boolean useMapVirtualization() {
        return false;
    }

    public final MapCustomizeManager getMapCustomizeManager() {
        return this.mMapCustomizeManager;
    }

    public final IOverlayHolder getOverlayHolder() {
        return this.mOverlayHolder;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        if (this.mMapWidgetService != null) {
            this.mMapWidgetService.onBindPage(this);
        }
        this.mDispatchRecord = ((AbstractBaseMapPagePresenter) this.mPresenter).mDispatchRecord;
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            this.mOverlayHolder = new OverlayHolderImpl(mapManager.getMapView());
            this.mSuspendWidgetHelper = new ccy(this);
            this.mUniversalOverlayManager = new UniversalOverlayManager(mapManager.getOverlayManager());
            this.mMapCustomizeManager = new MapCustomizeManager();
            setMapCustomManager();
        }
        initVMapPage();
        this.mIsPageInit = true;
    }

    private void initVMapPage() {
        VMapPageConfig vMapPageConfig = (VMapPageConfig) getClass().getAnnotation(VMapPageConfig.class);
        String name = (vMapPageConfig == null || TextUtils.isEmpty(vMapPageConfig.name())) ? getClass().getName() : vMapPageConfig.name();
        PageBundle arguments = getArguments();
        if (arguments == null || !arguments.getBoolean(PAGE_EXTENDS_LAYER, false)) {
            this.mVMapPage = getMapView().ao().createVMapPage(getContext(), name);
        } else {
            this.mVMapPage = getMapView().ao().createVMapPage(getContext(), name, 1);
        }
    }

    /* access modifiers changed from: protected */
    public final void reBindMapWidgets() {
        StringBuilder sb = new StringBuilder("----reBindMapWidgets----isPageSwitch():");
        sb.append(isPageSwitch());
        AMapLog.d("AmapPage", sb.toString());
        if (!isJustNowCreated() && isPageSwitch()) {
            onBindMapWidgets();
            setPageHeader();
        }
    }

    /* access modifiers changed from: protected */
    public final void isBackground() {
        boolean z = !isPageSwitch();
        if (this.mMapWidgetService != null && z) {
            this.mMapWidgetService.onBackground();
        }
    }

    /* access modifiers changed from: protected */
    public final void isForeground() {
        boolean z = !isPageSwitch();
        if (this.mMapWidgetService != null && z) {
            this.mMapWidgetService.onForeground();
        }
    }

    private void setMapCustomManager() {
        cde suspendManager = getSuspendManager();
        if (suspendManager != null && this.mMapCustomizeManager != null) {
            suspendManager.a(this.mMapCustomizeManager);
            if (this.mMapCustomizeManager != null) {
                this.mMapCustomizeManager.resume();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPageCreatedEvent() {
        if (useMapVirtualization()) {
            buc virtualizationPageService = DoNotUseTool.getVirtualizationPageService();
            if (virtualizationPageService != null) {
                this.mMapVirtualizationPage = virtualizationPageService.a(getClass().getName());
                if (this.mMapVirtualizationPage != null) {
                    this.mMapVirtualizationPage.a();
                }
            }
        }
        this.mDispatchRecord.baseOnPageCreatedCalled = false;
        super.dispatchPageCreatedEvent();
        if (!this.mDispatchRecord.baseOnPageCreatedCalled) {
            throw new IllegalStateException("Must call super.OnPageCreated()!!");
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchStartEvent() {
        if (useMapVirtualization() && this.mMapVirtualizationPage != null) {
            this.mMapVirtualizationPage.b();
        }
        this.mDispatchRecord.baseOnStartCalled = false;
        setMapCustomManager();
        super.dispatchStartEvent();
        if (!this.mDispatchRecord.baseOnStartCalled) {
            throw new IllegalStateException("Must call super.OnStart()!!");
        }
        setMapCustomManager();
    }

    /* access modifiers changed from: 0000 */
    public void dispatchResumeEvent() {
        this.mDispatchRecord.baseOnResumeCalled = false;
        super.dispatchResumeEvent();
        if (!this.mDispatchRecord.baseOnResumeCalled) {
            throw new IllegalStateException("Must call super.onResume()!!");
        }
        this.mVMapPage.onShow();
    }

    /* access modifiers changed from: 0000 */
    public void dispatchStopEvent() {
        this.mDispatchRecord.baseOnStopCalled = false;
        super.dispatchStopEvent();
        if (!this.mDispatchRecord.baseOnStopCalled) {
            throw new IllegalStateException("Must call super.OnStop()!!");
        } else if (useMapVirtualization() && this.mMapVirtualizationPage != null) {
            this.mMapVirtualizationPage.c();
        }
    }

    /* access modifiers changed from: 0000 */
    public void dispatchPauseEvent() {
        this.mDispatchRecord.baseOnPauseCalled = false;
        super.dispatchPauseEvent();
        if (!this.mDispatchRecord.baseOnPauseCalled) {
            throw new IllegalStateException("Must call super.onPause()!!");
        }
        this.mVMapPage.onHide();
    }

    /* access modifiers changed from: 0000 */
    public void dispatchDestroyEvent() {
        this.mDispatchRecord.baseOnDestroyCalled = false;
        super.dispatchDestroyEvent();
        if (!this.mDispatchRecord.baseOnDestroyCalled) {
            throw new IllegalStateException("Must call super.OnDestroy()!!");
        }
        if (useMapVirtualization() && this.mMapVirtualizationPage != null) {
            this.mMapVirtualizationPage.d();
            buc virtualizationPageService = DoNotUseTool.getVirtualizationPageService();
            if (virtualizationPageService != null) {
                virtualizationPageService.a(this.mMapVirtualizationPage);
            }
            this.mMapVirtualizationPage = null;
        }
        getMapView().ao().destroyVMapPage(this.mVMapPage);
    }

    public final brx getMapViewManager() {
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            return mapManager.getMapViewManager();
        }
        return null;
    }

    public final void addOverlay(BaseOverlay baseOverlay) {
        addOverlay(baseOverlay, true);
    }

    public final void addOverlay(BaseOverlay baseOverlay, boolean z) {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.simpleOverlayHolder.addOverlay(baseOverlay, z);
        }
    }

    public final void removeOverlay(BaseOverlay baseOverlay) {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.simpleOverlayHolder.removeOverlay(baseOverlay);
        }
    }

    public final void restoreOverlays() {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.restore();
        }
        if (this.mUniversalOverlayManager != null) {
            this.mUniversalOverlayManager.resumeUniversalOverlayCareConfig(this);
            this.mUniversalOverlayManager.resumeUniversalOverlayFocus();
        }
    }

    public final void saveOverlays() {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.save();
        }
        if (this.mUniversalOverlayManager != null) {
            this.mUniversalOverlayManager.saveUniversalOverlayFocus();
        }
    }

    public final void destroyOverlays() {
        if (this.mOverlayHolder != null) {
            this.mOverlayHolder.clearAndRemove();
            this.mOverlayHolder = null;
        }
    }

    public void addOverlayV(BaseOverlay baseOverlay) {
        if (useMapVirtualization() && this.mMapVirtualizationPage != null) {
            this.mMapVirtualizationPage.a(getMapView().j(), baseOverlay.getGLOverlay().getNativeInstatnce(), (long) baseOverlay.getGLOverlay().getCode());
        }
    }

    public void removeOverlayV(BaseOverlay baseOverlay) {
        if (useMapVirtualization() && this.mMapVirtualizationPage != null) {
            this.mMapVirtualizationPage.a(getMapView().j(), baseOverlay.getGLOverlay().getNativeInstatnce());
        }
    }

    public void setMapStateVirtualFlags(long j) {
        if (useMapVirtualization() && this.mMapVirtualizationPage != null) {
            this.mMapVirtualizationPage.a(j);
        }
    }

    public long getMapStateVirtualFlags() {
        if (!useMapVirtualization() || this.mMapVirtualizationPage == null) {
            return 0;
        }
        return this.mMapVirtualizationPage.e();
    }

    public View getMapSuspendBtnView2() {
        return this.mMapSuspendBtnView;
    }

    public final void bindMapSuspendView() {
        if (getContentView() != null) {
            MapInteractiveRelativeLayout mapInteractiveRelativeLayout = (MapInteractiveRelativeLayout) getContentView().findViewById(R.id.mapInteractiveRelativeLayout);
            if (mapInteractiveRelativeLayout != null) {
                this.mMapSuspendBtnView = getMapSuspendView();
                if (this.mMapSuspendBtnView != null) {
                    ViewParent parent = this.mMapSuspendBtnView.getParent();
                    if (parent != null && (parent instanceof ViewGroup)) {
                        ((ViewGroup) parent).removeView(this.mMapSuspendBtnView);
                    }
                    mapInteractiveRelativeLayout.addView(this.mMapSuspendBtnView);
                }
            }
        }
    }

    public final void unbindMapSuspendView() {
        if (getContentView() != null) {
            MapInteractiveRelativeLayout mapInteractiveRelativeLayout = (MapInteractiveRelativeLayout) getContentView().findViewById(R.id.mapInteractiveRelativeLayout);
            if (!(mapInteractiveRelativeLayout == null || this.mMapSuspendBtnView == null)) {
                mapInteractiveRelativeLayout.removeView(this.mMapSuspendBtnView);
            }
        }
    }

    public TipContainer getBottomTipsContainer() {
        synchronized (this) {
            try {
                if (this.mTipContainer == null) {
                    View findViewById = getContentView().findViewById(R.id.mapBottomInteractiveView);
                    if (findViewById != null && (findViewById instanceof ViewGroup)) {
                        this.mTipContainer = new TipContainer((ViewGroup) findViewById);
                        this.mTipContainer.addOnTipChangedListener(this);
                    }
                }
            }
        }
        return this.mTipContainer;
    }

    public View getTopMapInteractiveView() {
        if (getContentView() != null) {
            return getContentView().findViewById(R.id.mapTopInteractiveView);
        }
        return null;
    }

    public View getBottomMapInteractiveView() {
        if (getContentView() != null) {
            return getContentView().findViewById(R.id.mapBottomInteractiveView);
        }
        return null;
    }

    public MapInteractiveRelativeLayout getMapInteractiveView() {
        if (getContentView() != null) {
            return (MapInteractiveRelativeLayout) getContentView().findViewById(R.id.mapInteractiveRelativeLayout);
        }
        return null;
    }

    public ccy getSuspendWidgetHelper() {
        return this.mSuspendWidgetHelper;
    }

    public final boolean onMapTouchEvent(MotionEvent motionEvent) {
        this.mDispatchRecord.baseOnMapTouchEventCalled = false;
        boolean onMapTouchEvent = ((IMapPagePresenter) this.mPresenter).onMapTouchEvent(motionEvent);
        if (this.mDispatchRecord.baseOnMapTouchEventCalled) {
            return onMapTouchEvent;
        }
        throw new IllegalStateException("Must call super.onMapTouchEvent()!!");
    }

    public final boolean onMapLevelChange(boolean z) {
        this.mDispatchRecord.baseOnMapLevelChangeCalled = false;
        boolean onMapLevelChange = ((IMapPagePresenter) this.mPresenter).onMapLevelChange(z);
        if (this.mDispatchRecord.baseOnMapLevelChangeCalled) {
            return onMapLevelChange;
        }
        throw new IllegalStateException("Must call super.onMapLevelChange()!!");
    }

    public final boolean onMapMotionStop() {
        this.mDispatchRecord.baseOnMapMotionStopCalled = false;
        boolean onMapMotionStop = ((IMapPagePresenter) this.mPresenter).onMapMotionStop();
        if (this.mDispatchRecord.baseOnMapMotionStopCalled) {
            return onMapMotionStop;
        }
        throw new IllegalStateException("Must call super.onMapMotionStop()!!");
    }

    public final boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        this.mDispatchRecord.baseOnMapLongPressCalled = false;
        boolean onMapLongPress = ((IMapPagePresenter) this.mPresenter).onMapLongPress(motionEvent, geoPoint);
        if (this.mDispatchRecord.baseOnMapLongPressCalled) {
            return onMapLongPress;
        }
        throw new IllegalStateException("Must call super.onMapLongPress()!!");
    }

    public final boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        this.mDispatchRecord.baseOnMapSingleClickCalled = false;
        boolean onMapSingleClick = ((IMapPagePresenter) this.mPresenter).onMapSingleClick(motionEvent, geoPoint);
        if (this.mDispatchRecord.baseOnMapSingleClickCalled) {
            return onMapSingleClick;
        }
        throw new IllegalStateException("Must call super.onMapSingleClick()!!");
    }

    public void onEngineVisible(int i, boolean z) {
        ((IMapPagePresenter) this.mPresenter).onEngineVisible(i, z);
    }

    public final boolean onEngineActionGesture(alg alg) {
        this.mDispatchRecord.baseOnEngineActionGestureCalled = false;
        boolean onEngineActionGesture = ((IMapPagePresenter) this.mPresenter).onEngineActionGesture(alg);
        if (this.mDispatchRecord.baseOnEngineActionGestureCalled) {
            return onEngineActionGesture;
        }
        throw new IllegalStateException("Must call super.onEngineActionGesture()!!");
    }

    public final boolean onBlankClick() {
        this.mDispatchRecord.baseOnBlankClickCalled = false;
        boolean onBlankClick = ((IMapPagePresenter) this.mPresenter).onBlankClick();
        if (this.mDispatchRecord.baseOnBlankClickCalled) {
            return onBlankClick;
        }
        throw new IllegalStateException("Must call super.onBlankClick()!!");
    }

    public final boolean onNoBlankClick() {
        this.mDispatchRecord.baseOnNoBlankClickCalled = false;
        boolean onNoBlankClick = ((IMapPagePresenter) this.mPresenter).onNoBlankClick();
        if (this.mDispatchRecord.baseOnNoBlankClickCalled) {
            return onNoBlankClick;
        }
        throw new IllegalStateException("Must call super.onNoBlankClick()!!");
    }

    public final boolean onLabelClick(List<als> list) {
        this.mDispatchRecord.baseOnLabelClickCalled = false;
        boolean onLabelClick = ((IMapPagePresenter) this.mPresenter).onLabelClick(list);
        if (this.mDispatchRecord.baseOnLabelClickCalled) {
            return onLabelClick;
        }
        throw new IllegalStateException("Must call super.onLabelClick()!!");
    }

    public final boolean onPointOverlayClick(long j, int i) {
        this.mDispatchRecord.baseOnPointOverlayClickCalled = false;
        boolean onPointOverlayClick = ((IMapPagePresenter) this.mPresenter).onPointOverlayClick(j, i);
        if (this.mDispatchRecord.baseOnPointOverlayClickCalled) {
            return onPointOverlayClick;
        }
        throw new IllegalStateException("Must call super.onPointOverlayClick()!!");
    }

    public final boolean onLineOverlayClick(long j) {
        this.mDispatchRecord.baseOnLineOverlayClickCalled = false;
        boolean onLineOverlayClick = ((IMapPagePresenter) this.mPresenter).onLineOverlayClick(j);
        if (this.mDispatchRecord.baseOnLineOverlayClickCalled) {
            return onLineOverlayClick;
        }
        throw new IllegalStateException("Must call super.onLineOverlayClick()!!");
    }

    public final boolean onFocusClear() {
        this.mDispatchRecord.baseOnFocusClearCalled = false;
        boolean onFocusClear = ((IMapPagePresenter) this.mPresenter).onFocusClear();
        if (this.mDispatchRecord.baseOnFocusClearCalled) {
            return onFocusClear;
        }
        throw new IllegalStateException("Must call super.onFocusClear()!!");
    }

    public final void onMapAnimationFinished(int i) {
        this.mDispatchRecord.baseOnMapAnimationFinishedCalled = false;
        ((IMapPagePresenter) this.mPresenter).onMapAnimationFinished(i);
        if (!this.mDispatchRecord.baseOnMapAnimationFinishedCalled) {
            throw new IllegalStateException("Must call super.onMapAnimationFinished(int)!!");
        }
    }

    public final void onMapAnimationFinished(aln aln) {
        this.mDispatchRecord.baseOnMapAnimationFinishedCalled2 = false;
        ((IMapPagePresenter) this.mPresenter).onMapAnimationFinished(aln);
        if (!this.mDispatchRecord.baseOnMapAnimationFinishedCalled2) {
            throw new IllegalStateException("Must call super.onMapAnimationFinished(GLAnimationCallbackParam)!!");
        }
    }

    public final void onMapSurfaceCreated() {
        if (this.mIsPageInit) {
            this.mDispatchRecord.baseOnMapSurfaceCreatedCalled = false;
            ((IMapPagePresenter) this.mPresenter).onMapSurfaceCreated();
            if (!this.mDispatchRecord.baseOnMapSurfaceCreatedCalled) {
                throw new IllegalStateException("Must call super.onMapSurfaceCreated()!!");
            }
        }
    }

    public final void onMapSurfaceChanged(int i, int i2) {
        this.mDispatchRecord.baseOnMapSurfaceChangedCalled = false;
        ((IMapPagePresenter) this.mPresenter).onMapSurfaceChanged(i, i2);
        if (!this.mDispatchRecord.baseOnMapSurfaceChangedCalled) {
            throw new IllegalStateException("Must call super.onMapSurfaceChanged()!!");
        }
    }

    public final void onMapSurfaceDestroy() {
        this.mDispatchRecord.baseOnMapSurfaceDestroyCalled = false;
        ((IMapPagePresenter) this.mPresenter).onMapSurfaceDestroy();
        if (!this.mDispatchRecord.baseOnMapSurfaceDestroyCalled) {
            throw new IllegalStateException("Must call super.onMapSurfaceDestroy()!!");
        }
        Iterator it = this.mMainMapEventListeners.iterator();
        while (it.hasNext()) {
            this.mMapEventService.b((MainMapEventListener) it.next());
        }
    }

    public boolean onShowGpsTipView(int i, btt btt) {
        return ((IMapPagePresenter) this.mPresenter).onShowGpsTipView(i, btt);
    }

    public boolean onShowPoiTipView(PageBundle pageBundle, int i) {
        return ((IMapPagePresenter) this.mPresenter).onShowPoiTipView(pageBundle, i);
    }

    public boolean onShowGeoPoiDetailView(PageBundle pageBundle, int i) {
        return ((IMapPagePresenter) this.mPresenter).onShowGeoPoiDetailView(pageBundle, i);
    }

    public void onDoubleTap() {
        ((IMapPagePresenter) this.mPresenter).onDoubleTap();
    }

    public void onMoveBegin() {
        ((IMapPagePresenter) this.mPresenter).onMoveBegin();
    }

    public void onZoomOutTap() {
        ((IMapPagePresenter) this.mPresenter).onZoomOutTap();
    }

    public void onScaleRotateBegin() {
        ((IMapPagePresenter) this.mPresenter).onScaleRotateBegin();
    }

    public void onHoveBegin() {
        ((IMapPagePresenter) this.mPresenter).onHoveBegin();
    }

    public void onSelectSubWayActive(List<Long> list) {
        ((IMapPagePresenter) this.mPresenter).onSelectSubWayActive(list);
    }

    public final void onMapRenderCompleted() {
        this.mDispatchRecord.baseOnMapRenderCompletedCalled = false;
        ((IMapPagePresenter) this.mPresenter).onMapRenderCompleted();
        if (!this.mDispatchRecord.baseOnMapRenderCompletedCalled) {
            throw new IllegalStateException("Must call super.onMapRenderCompleted()!!");
        }
    }

    /* access modifiers changed from: protected */
    public a getGpsLayerTextureProvider() {
        return cdx.c();
    }

    public final bty getMapView() {
        return DoNotUseTool.getMapView().e();
    }

    public final MapManager getMapManager() {
        return DoNotUseTool.getMapManager();
    }

    public final cde getSuspendManager() {
        return DoNotUseTool.getSuspendManager();
    }

    public void addMainMapEventListener(int i, MainMapEventListener mainMapEventListener) {
        if (this.mMapEventService != null) {
            this.mMapEventService.a(i, mainMapEventListener);
            this.mMainMapEventListeners.add(mainMapEventListener);
        }
    }

    public void addMainMapEventListener(MainMapEventListener mainMapEventListener) {
        if (this.mMapEventService != null) {
            this.mMapEventService.a(mainMapEventListener);
            this.mMainMapEventListeners.add(mainMapEventListener);
        }
    }

    public void removeMainMapEventListener(MainMapEventListener mainMapEventListener) {
        if (this.mMapEventService != null) {
            this.mMapEventService.b(mainMapEventListener);
            this.mMainMapEventListeners.remove(mainMapEventListener);
        }
    }

    public VMapPage getVMapPage() {
        return this.mVMapPage;
    }

    public void onBindMapWidgets() {
        if (this.mMapWidgetService != null) {
            this.mMapWidgetService.onBindMapWidgets(customPageWidgets());
        }
    }

    public void unBindMapWidgets() {
        if (this.mMapWidgetService != null) {
            this.mMapWidgetService.unBindMapWidgets();
        }
    }

    public IWidgetProperty[] customPageWidgets() {
        return new IWidgetProperty[0];
    }

    public final IMapWidgetService getPageMapWidgetService() {
        return this.mMapWidgetService;
    }
}
