package com.autonavi.map.fragmentcontainer.page;

import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.amap.bundle.datamodel.poi.POIFactory;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.core.Real3DManager;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.page.IMapPage;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer;
import com.autonavi.map.fragmentcontainer.page.mappage.MapMethodDispatchRecord;
import com.autonavi.map.fragmentcontainer.page.mappage.TipsView.PoiDetailView;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.List;

public abstract class AbstractBaseMapPagePresenter<Page extends IMapPage> extends AbstractBasePresenter<Page> implements IMapPagePresenter {
    public static final boolean SWITCH_RENDER_PAUSE_DELAY = true;
    final MapMethodDispatchRecord mDispatchRecord = new MapMethodDispatchRecord();
    private IGPSTipView mGpsTipView;
    private ceq mLogoStatusTracker;
    private PoiDetailView mPoiDetailView;
    private ely mPoiTipView;

    public interface IGPSTipView {
        View getView();

        void reset();

        void setFromPageID(int i);
    }

    public void onDoubleTap() {
    }

    public void onEngineVisible(int i, boolean z) {
    }

    public void onHoveBegin() {
    }

    public void onMoveBegin() {
    }

    public void onScaleRotateBegin() {
    }

    public void onSelectSubWayActive(List<Long> list) {
    }

    public void onZoomOutTap() {
    }

    public void onPageCreated() {
        super.onPageCreated();
        this.mDispatchRecord.baseOnPageCreatedCalled = true;
        ((IMapPage) this.mPage).bindMapSuspendView();
        ((IMapPage) this.mPage).onBindMapWidgets();
        ((IMapPage) this.mPage).onInitMapWidget();
        ((IMapPage) this.mPage).setPageHeader();
        if (((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().onPageCreated();
        }
    }

    public void onResume() {
        StringBuilder sb = new StringBuilder("~onResume ~");
        sb.append(getClass().getSimpleName());
        AMapLog.d("AmapPage", sb.toString());
        if (this.mPage instanceof AbstractBaseMapPage) {
            ((IMapPage) this.mPage).getPageMapWidgetService().restoreContainerConfig();
            ((AbstractBaseMapPage) this.mPage).reBindMapWidgets();
        }
        super.onResume();
        this.mDispatchRecord.baseOnResumeCalled = true;
        if (!(!(this.mPage instanceof AbstractBaseMapPage) || ((IMapPage) this.mPage).getMapManager() == null || ((IMapPage) this.mPage).getMapManager().getOverlayManager().getGpsLayer() == null)) {
            cdx gpsLayer = ((IMapPage) this.mPage).getMapManager().getOverlayManager().getGpsLayer();
            gpsLayer.e = ((AbstractBaseMapPage) this.mPage).getGpsLayerTextureProvider();
            gpsLayer.a();
        }
        cdd.n().j();
        cdd.n().i();
        if (((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().onPageResume();
        }
        if (this.mPage instanceof AbstractBaseMapPage) {
            ((AbstractBaseMapPage) this.mPage).isForeground();
        }
    }

    public void onStart() {
        super.onStart();
        this.mDispatchRecord.baseOnStartCalled = true;
        ((IMapPage) this.mPage).getMapManager().setMapEventListener(((IMapPage) this.mPage).getMapView().j(), (btv) this.mPage);
        ((IMapPage) this.mPage).restoreOverlays();
        ((IMapPage) this.mPage).getMapView().N();
        ((IMapPage) this.mPage).getMapView().f(true);
        setMapCommonOverlayListener(false);
        ccy suspendWidgetHelper = ((IMapPage) this.mPage).getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            suspendWidgetHelper.r();
        }
        setInterPolatorFlag(true);
        boolean isHomePage = AMapPageUtil.isHomePage();
        ku.a().c("AbstractBaseMapPagePresenter", "onstart--isHomePage=".concat(String.valueOf(isHomePage)));
        if (!isHomePage) {
            Real3DManager a = Real3DManager.a();
            MapManager mapManager = ((IMapPage) this.mPage).getMapManager();
            ku a2 = ku.a();
            StringBuilder sb = new StringBuilder("disableReal3DShowByBase--isIgnoreCloseOnMainMap=");
            sb.append(a.f);
            a2.c("AbstractBaseMapPagePresenter", sb.toString());
            if (!a.f && !a.g) {
                ku a3 = ku.a();
                StringBuilder sb2 = new StringBuilder("disableReal3DShowByBase--isReal3DEnable=");
                sb2.append(a.h);
                a3.c("AbstractBaseMapPagePresenter", sb2.toString());
                if (!a.h && mapManager != null) {
                    bty mapView = mapManager.getMapView();
                    if (mapView != null) {
                        int j = mapView.e().j();
                        boolean j2 = mapView.j(j);
                        ku.a().c("AbstractBaseMapPagePresenter", "disableReal3DShowByBase--isOpen=".concat(String.valueOf(j2)));
                        if (j2) {
                            Real3DManager.a(String.format("Disable Real3D show.", new Object[0]));
                            mapView.a(j, false);
                            cdd.n().g();
                        }
                    }
                }
            }
        }
        if (((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().onPageStart();
        }
    }

    private void setMapCommonOverlayListener(boolean z) {
        if (!(this.mPage instanceof IPoiDetailPage) || !((IPoiDetailPage) this.mPage).isUsePoiDelegate()) {
            IOverlayManager overlayManager = ((IMapPage) this.mPage).getMapManager().getOverlayManager();
            if (overlayManager != null) {
                overlayManager.setMapCommonOverlayListener(!z ? (IMapPage) this.mPage : null);
            }
        }
    }

    private void setInterPolatorFlag(boolean z) {
        cde suspendManager = ((IMapPage) this.mPage).getSuspendManager();
        if (suspendManager != null) {
            suspendManager.d().b(z);
        }
    }

    public void onPause() {
        super.onPause();
        this.mDispatchRecord.baseOnPauseCalled = true;
        if (((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().onPagePause();
        }
        if ((this.mPage instanceof AbstractBaseMapPage) && ((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().saveContainerConfig();
        }
    }

    public void onStop() {
        super.onStop();
        this.mDispatchRecord.baseOnStopCalled = true;
        if (((IMapPage) this.mPage).getMapManager() != null) {
            ((IMapPage) this.mPage).getMapManager().renderPauseDelay();
        } else {
            ((IMapPage) this.mPage).getMapView().M();
        }
        ((IMapPage) this.mPage).getMapManager().setMapEventListener(((IMapPage) this.mPage).getMapView().j(), null);
        ((IMapPage) this.mPage).saveOverlays();
        setMapCommonOverlayListener(true);
        ccy suspendWidgetHelper = ((IMapPage) this.mPage).getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            suspendWidgetHelper.s();
        }
        setInterPolatorFlag(false);
        ((IMapPage) this.mPage).unBindMapWidgets();
        if (((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().onPageStop();
        }
        if (this.mPage instanceof AbstractBaseMapPage) {
            ((AbstractBaseMapPage) this.mPage).isBackground();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Real3DManager.a().h = false;
        this.mDispatchRecord.baseOnDestroyCalled = true;
        ((IMapPage) this.mPage).destroyOverlays();
        ((IMapPage) this.mPage).unbindMapSuspendView();
        if (((IMapPage) this.mPage).getPageMapWidgetService() != null) {
            ((IMapPage) this.mPage).getPageMapWidgetService().onPageDestroy();
        }
        ccy suspendWidgetHelper = ((IMapPage) this.mPage).getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            suspendWidgetHelper.t();
        }
    }

    public AbstractBaseMapPagePresenter(Page page) {
        super(page);
    }

    public boolean onMapTouchEvent(MotionEvent motionEvent) {
        this.mDispatchRecord.baseOnMapTouchEventCalled = true;
        if (this.mLogoStatusTracker == null) {
            this.mLogoStatusTracker = new ceq(((IMapPage) this.mPage).getContext());
        }
        ceq ceq = this.mLogoStatusTracker;
        int action = motionEvent.getAction();
        if (action == 0) {
            cdd.n().f();
            ceq.a = motionEvent.getX();
            ceq.b = motionEvent.getY();
            ceq.c = ceq.a;
            ceq.d = ceq.b;
            ceq.e = false;
        } else if (action == 2) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            if (Math.abs(x - ceq.a) > ((float) ceq.f) || Math.abs(y - ceq.b) > ((float) ceq.f) || Math.abs(x - ceq.c) > ((float) ceq.f) || Math.abs(y - ceq.d) > ((float) ceq.f)) {
                ceq.e = true;
            }
            ceq.c = x;
            ceq.d = y;
            if (ceq.e) {
                cdd.n().b(false);
            }
        } else if (action == 1 && ceq.e) {
            cdd.n().b(true);
        }
        return false;
    }

    public boolean onMapLevelChange(boolean z) {
        this.mDispatchRecord.baseOnMapLevelChangeCalled = true;
        return false;
    }

    public boolean onMapMotionStop() {
        this.mDispatchRecord.baseOnMapMotionStopCalled = true;
        return false;
    }

    public boolean onMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        this.mDispatchRecord.baseOnMapLongPressCalled = true;
        return false;
    }

    public boolean onMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        this.mDispatchRecord.baseOnMapSingleClickCalled = true;
        return false;
    }

    public boolean onEngineActionGesture(alg alg) {
        this.mDispatchRecord.baseOnEngineActionGestureCalled = true;
        return false;
    }

    public boolean onBlankClick() {
        this.mDispatchRecord.baseOnBlankClickCalled = true;
        return false;
    }

    public boolean onNoBlankClick() {
        this.mDispatchRecord.baseOnNoBlankClickCalled = true;
        return false;
    }

    public boolean onLabelClick(List<als> list) {
        this.mDispatchRecord.baseOnLabelClickCalled = true;
        return false;
    }

    public boolean onPointOverlayClick(long j, int i) {
        this.mDispatchRecord.baseOnPointOverlayClickCalled = true;
        return false;
    }

    public boolean onLineOverlayClick(long j) {
        this.mDispatchRecord.baseOnLineOverlayClickCalled = true;
        return false;
    }

    public boolean onFocusClear() {
        this.mDispatchRecord.baseOnFocusClearCalled = true;
        return false;
    }

    public void onMapAnimationFinished(int i) {
        this.mDispatchRecord.baseOnMapAnimationFinishedCalled = true;
    }

    public void onMapAnimationFinished(aln aln) {
        this.mDispatchRecord.baseOnMapAnimationFinishedCalled2 = true;
    }

    public void onMapSurfaceCreated() {
        this.mDispatchRecord.baseOnMapSurfaceCreatedCalled = true;
    }

    public void onMapSurfaceDestroy() {
        this.mDispatchRecord.baseOnMapSurfaceDestroyCalled = true;
    }

    public void onMapSurfaceChanged(int i, int i2) {
        this.mDispatchRecord.baseOnMapSurfaceChangedCalled = true;
    }

    public boolean onShowGpsTipView(int i, btt btt) {
        if (!(btt instanceof cdx) || ((IMapPage) this.mPage).getBottomTipsContainer() == null) {
            return false;
        }
        if (this.mGpsTipView == null) {
            IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
            if (iPoiTipViewService != null) {
                this.mGpsTipView = iPoiTipViewService.createGpsTipView((bid) this.mPage, (cdx) btt);
            }
        }
        this.mGpsTipView.setFromPageID(10001);
        if (this.mPage instanceof IPoiDetailPage) {
            ((IPoiDetailPage) this.mPage).onStartDetail(POIFactory.createPOI().as(GpsPOI.class), this.mGpsTipView.getView());
        }
        this.mGpsTipView.reset();
        return true;
    }

    public boolean onShowPoiTipView(PageBundle pageBundle, int i) {
        if (((IMapPage) this.mPage).getBottomTipsContainer() == null) {
            return false;
        }
        POI poi = (POI) pageBundle.getObject("POI");
        if (this.mPoiTipView == null) {
            IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
            if (iPoiTipViewService != null) {
                this.mPoiTipView = iPoiTipViewService.createPoiTipView(null, (bid) this.mPage, poi);
            }
        }
        if (this.mPoiTipView == null) {
            return false;
        }
        this.mPoiTipView.setSingle(true);
        this.mPoiTipView.setFromSource("mainmap");
        this.mPoiTipView.adjustMargin();
        this.mPoiTipView.initData(null, poi, 2);
        if (this.mPage instanceof IPoiDetailPage) {
            ((IPoiDetailPage) this.mPage).onStartDetail(poi, this.mPoiTipView);
        }
        return true;
    }

    public boolean onShowGeoPoiDetailView(PageBundle pageBundle, int i) {
        if (this.mPage == null) {
            return false;
        }
        POI poi = (POI) pageBundle.getObject("POI");
        if (this.mPoiDetailView == null) {
            this.mPoiDetailView = new PoiDetailView((bid) this.mPage);
        }
        if (this.mPage instanceof IPoiDetailPage) {
            ((IPoiDetailPage) this.mPage).onStartDetail(poi, (View) this.mPoiDetailView);
        }
        this.mPoiDetailView.refreshByScreenState(ags.b(((bid) this.mPage).getActivity()));
        this.mPoiDetailView.reset();
        this.mPoiDetailView.setPoiFooterDetail(pageBundle);
        this.mPoiDetailView.requestPoiDetail(poi);
        return true;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ccy suspendWidgetHelper = ((IMapPage) this.mPage).getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            suspendWidgetHelper.a(configuration);
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        super.onKeyDown(i, keyEvent);
        return false;
    }

    public ON_BACK_TYPE onBackPressed() {
        TipContainer bottomTipsContainer = ((IMapPage) this.mPage).getBottomTipsContainer();
        if (bottomTipsContainer == null || !bottomTipsContainer.onBackKeyPressed()) {
            return super.onBackPressed();
        }
        return ON_BACK_TYPE.TYPE_IGNORE;
    }

    public void onMapRenderCompleted() {
        this.mDispatchRecord.baseOnMapRenderCompletedCalled = true;
    }
}
