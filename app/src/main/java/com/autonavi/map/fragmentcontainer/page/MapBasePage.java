package com.autonavi.map.fragmentcontainer.page;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import com.autonavi.common.Callback;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager.c;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.ICQLayerController.DetailLayerState;
import com.autonavi.map.fragmentcontainer.page.IMapPagePresenter;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.fragmentcontainer.page.utils.IPageStateListener;
import com.autonavi.map.suspend.refactor.scale.ScaleView;
import com.autonavi.map.widget.RecyclableViewPager;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.basemap.errorback.inter.IErrorReportStarter;
import com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.VisiblePolicy;
import com.autonavi.minimap.map.overlayholder.OverlayUtil;
import com.autonavi.minimap.widget.SyncPopupWindow;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.List;

public abstract class MapBasePage<Presenter extends IMapPagePresenter> extends AbstractBaseMapPage<Presenter> implements cep, IPoiDetailPage {
    private coi callback = new coi() {
        public void doReportError(String str) {
            MapBasePage.this.doFastReportError(str);
        }
    };
    public ICQLayerController mCQLayerController;
    private BaseCQLayerOwner mCQLayerOwner = null;
    protected boolean mIsHideAllOpenLayerCustomize = false;
    private OverlayPageProperty mOverlayProperties;
    protected IPoiDetailDelegate mPoiDelegate = new PoiDetailDelegate(this);
    private int mSaveOverlayFocusKey = 0;
    public bra mSlidePanelManager = new bra();
    private SyncPopupWindow mSyncPopupWindow;

    public enum POI_DETAIL_TYPE {
        DEFAULT,
        CQ_VIEW,
        PAGE
    }

    public static void LogCQ(String str) {
    }

    public void OnTrafficLabelClick() {
    }

    public void OnTrafficLabelClickCancel() {
    }

    /* access modifiers changed from: protected */
    public void bindGpsWidget() {
    }

    public int getTrafficEventSource() {
        return -1;
    }

    public boolean isGpsTipDisable() {
        return false;
    }

    public boolean isUsePoiDelegate() {
        return false;
    }

    public boolean onMapLayerSetDefaultMode() {
        return false;
    }

    public void onPageActivityResult(int i, int i2, Intent intent) {
    }

    public boolean onPageBlankClick() {
        return false;
    }

    /* access modifiers changed from: protected */
    public void onPageCreated() {
    }

    public void onPageDestroyView() {
    }

    public void onPageGpsBtnClicked() {
    }

    public void onPageHiddenChanged(boolean z) {
    }

    public boolean onPageKeyDown(int i, KeyEvent keyEvent) {
        return false;
    }

    public boolean onPageLineOverlayClick(long j) {
        return false;
    }

    public void onPageMapAnimationFinished(int i) {
    }

    public boolean onPageMapLevelChange(boolean z) {
        return false;
    }

    public boolean onPageMapLongPress(MotionEvent motionEvent, GeoPoint geoPoint) {
        return false;
    }

    public boolean onPageMapMotionStop() {
        return false;
    }

    public boolean onPageMapSingleClick(MotionEvent motionEvent, GeoPoint geoPoint) {
        return true;
    }

    public void onPageMapSurfaceChanged(int i, int i2) {
    }

    public void onPageMapSurfaceCreated() {
    }

    public void onPageMapSurfaceDestroy() {
    }

    public boolean onPageMapTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onPageNoBlankClick() {
        return false;
    }

    public boolean onPageNonFeatureClick() {
        return false;
    }

    public void onPagePausePost() {
    }

    public boolean onPagePointOverlayClick(long j, int i) {
        return false;
    }

    public void onPageResumePost() {
    }

    public void onPageStart() {
    }

    public void onPageStop() {
    }

    public void onPageWindowFocusChanged(boolean z) {
    }

    public void onTipDimiss() {
    }

    /* access modifiers changed from: protected */
    public void onTrunPoiDetialPage() {
    }

    public void showDefaultMapTip() {
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        setPageStateListener();
    }

    public void onPageResume() {
        registerCloudSyncListener();
        resumeMapUI();
    }

    /* access modifiers changed from: protected */
    public void resumeMapUI() {
        if (this.mPoiDelegate != null) {
            this.mPoiDelegate.onResume();
        }
        resumeUniversalOverlayCareConfig();
        resumeUniversalOverlayFocus();
        resetMapSkinState();
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            registerTrafficOverlayListener(mapManager);
            resumeSuspendView();
            injectPoiDelegate();
            setMapViewTouchEnable(true);
            setSeamlessIndoor(false);
            resetScaleview();
        }
    }

    private void resetScaleview() {
        ccy suspendWidgetHelper = getSuspendWidgetHelper();
        if (suspendWidgetHelper != null) {
            ScaleView f = suspendWidgetHelper.f();
            if (f != null) {
                f.changeLogoStatus(true);
            }
        }
        ccy suspendWidgetHelper2 = getSuspendWidgetHelper();
        if (suspendWidgetHelper2 != null) {
            ScaleView f2 = suspendWidgetHelper2.f();
            if (f2 != null) {
                f2.changeLogoStatus(true);
            }
        }
    }

    private void setSeamlessIndoor(boolean z) {
        cdd.n().e(z);
    }

    private void setMapViewTouchEnable(boolean z) {
        bty gLMapView = getGLMapView();
        if (gLMapView != null) {
            gLMapView.f(z);
        }
    }

    private void resumeSuspendView() {
        bindMapSuspendView();
        bindGpsWidget();
    }

    private void registerTrafficOverlayListener(MapManager mapManager) {
        mapManager.getOverlayManager().getTrafficPointOverlay().setOpenLayerInteractiveListener(this);
    }

    private void setPageStateListener() {
        AMapPageUtil.setPageStateListener(this, new IPageStateListener() {
            public void onCover() {
                MapBasePage.this.onPageCover();
            }

            public void onAppear() {
                MapBasePage.this.onPageAppear();
            }
        });
    }

    public void onPageCover() {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onPageCover();
        }
        if (this.mPoiDelegate.isTrafficItemDialogShowing()) {
            clearTrafficOverlay();
        }
        dismissTrafficReportDialog();
        bty gLMapView = getGLMapView();
        if (gLMapView != null) {
            gLMapView.E();
        }
    }

    private void clearTrafficOverlay() {
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            mapManager.getOverlayManager().getTrafficPointOverlay().clear();
        }
    }

    public void onPageAppear() {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onPageAppear();
        }
    }

    private void dismissTrafficReportDialog() {
        ITrafficReportController iTrafficReportController = (ITrafficReportController) ank.a(ITrafficReportController.class);
        if (iTrafficReportController != null) {
            iTrafficReportController.a();
        }
    }

    public void onPagePause() {
        cleanUpSyncPopupWindow();
        pauseMapUI();
    }

    /* access modifiers changed from: protected */
    public void pauseMapUI() {
        if (this.mPoiDelegate != null) {
            this.mPoiDelegate.onResume();
        }
        MapManager mapManager = getMapManager();
        pauseUniversalOverlay();
        saveUniversalOverlayFocus();
        if (mapManager != null) {
            unbindMapSuspendView();
            unregisterTrafficOverlayListener(mapManager);
            setMapViewTouchEnable(false);
            setSeamlessIndoor(false);
        }
    }

    private void unregisterTrafficOverlayListener(MapManager mapManager) {
        mapManager.getOverlayManager().getTrafficPointOverlay().setOpenLayerInteractiveListener(null);
    }

    public boolean getIsHideAllOpenLayerCustomize() {
        return this.mIsHideAllOpenLayerCustomize;
    }

    public void setIsHideAllOpenLayerCustomize(boolean z) {
        this.mIsHideAllOpenLayerCustomize = z;
    }

    /* access modifiers changed from: protected */
    public void onPageNewNodeFragmentBundle(PageBundle pageBundle) {
        injectPoiDelegate();
    }

    public void onPageResult(int i, ResultType resultType, PageBundle pageBundle) {
        injectPoiDelegate();
    }

    public void injectPoiDelegate() {
        if (getMapManager() != null && getBottomTipsContainer() != null) {
            getMapManager().getOverlayManager().setPoiDetailDelegate(this.mPoiDelegate);
        }
    }

    private void saveUniversalOverlayFocus() {
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            a aVar = null;
            if (this instanceof d) {
                aVar = ((d) this).getReleatedTrafficEventHandler();
            }
            if (!(getBottomTipsContainer() == null || getBottomTipsContainer().getCurrentTips() == null) || ((this.mPoiDelegate != null && this.mPoiDelegate.isTrafficItemDialogShowing()) || ((aVar != null && aVar.h()) || (this.mCQLayerController != null && this.mCQLayerController.isShowing())))) {
                this.mSaveOverlayFocusKey = mapManager.getOverlayManager().saveFocus();
            }
            mapManager.getOverlayManager().clearAllFocus();
        }
    }

    public void deleteCurrentFocusKey() {
        getMapManager().getOverlayManager().deleteSaveFocusKey(this.mSaveOverlayFocusKey);
    }

    private c getFocusPointItem(List<c> list) {
        c cVar = null;
        if (list != null) {
            for (c next : list) {
                if (!(next == null || next.a == null)) {
                    if (cVar == null || cVar.b.ordinal() > next.b.ordinal()) {
                        cVar = next;
                    }
                }
            }
        }
        return cVar;
    }

    private void resumeUniversalOverlayFocus() {
        GeoPoint geoPoint = null;
        View mapPointTipView = (this.mCQLayerController == null || !this.mCQLayerController.isShowing()) ? null : this.mCQLayerController.getMapPointTipView();
        if (mapPointTipView == null && getBottomTipsContainer() != null) {
            mapPointTipView = getBottomTipsContainer().getCurrentTips();
        }
        a releatedTrafficEventHandler = this instanceof d ? ((d) this).getReleatedTrafficEventHandler() : null;
        boolean z = this.mPoiDelegate != null && this.mPoiDelegate.isTrafficItemDialogShowing();
        MapManager mapManager = getMapManager();
        if (mapPointTipView != null) {
            AbstractPoiDetailView abstractPoiDetailView = this.mPoiDelegate.getpoiDetailView();
            ely iPoiTipView = this.mPoiDelegate.getIPoiTipView();
            AbstractGpsTipView abstractGpsTipView = this.mPoiDelegate.getgpsTipView();
            c focusPointItem = getFocusPointItem(solveSavedFocus(mapManager, true));
            PointOverlayItem pointOverlayItem = focusPointItem != null ? focusPointItem.a : null;
            if ((abstractPoiDetailView == null || !mapPointTipView.equals(abstractPoiDetailView)) && ((iPoiTipView == null || !mapPointTipView.equals(iPoiTipView.getView())) && !(mapPointTipView instanceof RecyclableViewPager) && (pointOverlayItem == null || pointOverlayItem.Tag == null || !pointOverlayItem.Tag.equals(mapPointTipView.getTag())))) {
                if (abstractGpsTipView != null && mapPointTipView.equals(abstractGpsTipView)) {
                    GeoPoint latestPosition = LocationInstrument.getInstance().getLatestPosition(5);
                    if (latestPosition != null) {
                        geoPoint = latestPosition;
                    }
                    if (z) {
                        solveSavedFocus(mapManager, true);
                    }
                }
            } else if (!(pointOverlayItem == null || pointOverlayItem.getGeoPoint() == null)) {
                geoPoint = pointOverlayItem.getGeoPoint();
            }
            if (geoPoint == null || getSuspendManager().d().c()) {
                if (z) {
                    solveSavedFocus(mapManager, true);
                } else if (releatedTrafficEventHandler != null && releatedTrafficEventHandler.h()) {
                    solveSavedFocus(mapManager, true);
                }
            } else if (!(mapPointTipView instanceof RecyclableViewPager)) {
                bty gLMapView = getGLMapView();
                if (gLMapView != null && (isAlwaysMoveToCenterWhenRestoreFocus() || !isInVisibleRangeWhenRecoverCenter(geoPoint))) {
                    gLMapView.a(geoPoint.x, geoPoint.y);
                }
            }
        } else if (z) {
            solveSavedFocus(mapManager, true);
        } else if (releatedTrafficEventHandler == null || !releatedTrafficEventHandler.h()) {
            solveSavedFocus(mapManager, false);
        } else {
            solveSavedFocus(mapManager, true);
        }
    }

    /* access modifiers changed from: protected */
    public List<c> solveSavedFocus(MapManager mapManager, boolean z) {
        if (mapManager == null) {
            return null;
        }
        return mapManager.getOverlayManager().solveSavedFocusWithKey(this.mSaveOverlayFocusKey, z);
    }

    public void removeSavedFocus() {
        solveSavedFocus(getMapManager(), false);
    }

    /* access modifiers changed from: protected */
    public boolean isInVisibleRangeWhenRecoverCenter(GeoPoint geoPoint) {
        bty gLMapView = getGLMapView();
        if (gLMapView != null) {
            return gLMapView.H().contains(geoPoint.x, geoPoint.y);
        }
        return false;
    }

    public void onPageConfigurationChanged(Configuration configuration) {
        if (this.mPoiDelegate != null) {
            this.mPoiDelegate.onConfigurationChanged();
        }
    }

    public ON_BACK_TYPE onPageBackPressed() {
        MapManager mapManager = getMapManager();
        if (mapManager != null) {
            mapManager.getOverlayManager().clearAllFocus();
        }
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public void onPageDestroy() {
        destroyOverlays();
        if (this.mPoiDelegate != null) {
            this.mPoiDelegate.onDestroy();
        }
        bty gLMapView = getGLMapView();
        if (gLMapView != null) {
            gLMapView.E();
        }
    }

    /* access modifiers changed from: protected */
    public void onReportErrorClick() {
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.doReportError(getMapManager(), this.callback);
        }
    }

    /* access modifiers changed from: protected */
    public void doFastReportError(String str) {
        IErrorReportStarter iErrorReportStarter = (IErrorReportStarter) ank.a(IErrorReportStarter.class);
        if (iErrorReportStarter != null) {
            iErrorReportStarter.doFastReportError(str);
        }
    }

    private void resumeUniversalOverlayCareConfig() {
        OvProperty[] overlays;
        if (this.mOverlayProperties == null) {
            OverlayPageProperty overlayPageProperty = (OverlayPageProperty) getClass().getAnnotation(OverlayPageProperty.class);
            if (overlayPageProperty == null || overlayPageProperty.overlays() == null) {
                this.mOverlayProperties = OverlayUtil.getDefaultPageProperty();
            } else {
                this.mOverlayProperties = overlayPageProperty;
            }
        }
        setOverlayProperty(OverlayUtil.getDefaultPageProperty());
        if (!this.mOverlayProperties.equals(OverlayUtil.getDefaultPageProperty())) {
            boolean o = bin.a.o("104");
            for (OvProperty ovProperty : this.mOverlayProperties.overlays()) {
                if (ovProperty != null) {
                    UvOverlay overlay = ovProperty.overlay();
                    xq slectOverlayByEnum = slectOverlayByEnum(overlay);
                    if (slectOverlayByEnum != null) {
                        if (overlay != UvOverlay.SaveOverlay || o || !ovProperty.visible() || ovProperty.visiblePolicy() != VisiblePolicy.CareConfig) {
                            OverlayUtil.setOverlayProperty(slectOverlayByEnum, ovProperty);
                        } else {
                            slectOverlayByEnum.setVisible(o);
                        }
                    }
                }
            }
        }
    }

    private void pauseUniversalOverlay() {
        setOverlayProperty(OverlayUtil.getDefaultPageProperty());
    }

    private void setOverlayProperty(OverlayPageProperty overlayPageProperty) {
        OvProperty[] overlays;
        if (overlayPageProperty != null && overlayPageProperty.overlays() != null) {
            for (OvProperty ovProperty : overlayPageProperty.overlays()) {
                if (ovProperty != null) {
                    xq slectOverlayByEnum = slectOverlayByEnum(ovProperty.overlay());
                    if (slectOverlayByEnum != null) {
                        OverlayUtil.setOverlayProperty(slectOverlayByEnum, ovProperty);
                    }
                }
            }
        }
    }

    private xq slectOverlayByEnum(UvOverlay uvOverlay) {
        MapManager mapManager = getMapManager();
        if (mapManager == null) {
            return null;
        }
        switch (uvOverlay) {
            case GpsOverlay:
                return mapManager.getOverlayManager().getGpsLayer();
            case MapPointOverlay:
                return mapManager.getOverlayManager().getMapPointOverlay();
            case GeoCodeOverlay:
                return mapManager.getOverlayManager().getGeoCodeOverlay();
            case SaveOverlay:
            case LocalReportOverlay:
                return mapManager.getOverlayManager().getUniversalOverlay(uvOverlay);
            default:
                return null;
        }
    }

    public void showViewFooter(View view) {
        showViewFooter(view, 0, null);
    }

    @Deprecated
    public boolean dismissViewFooter() {
        return (getBottomTipsContainer() != null ? getBottomTipsContainer().dimissTips(getMapManager() != null && getMapManager().getOverlayManager() != null && getMapManager().getOverlayManager().isScenicSelected()) : false) || (this.mCQLayerController != null ? this.mCQLayerController.dismissCQLayer(true) : false);
    }

    @Deprecated
    private void showViewFooter(View view, int i, Callback<Integer> callback2) {
        if (getBottomTipsContainer() != null) {
            getBottomTipsContainer().showTip(view, i, callback2);
        }
    }

    public void refreshSaveOtherChildrenState() {
        if (getTipContainer() != null) {
            getTipContainer().refreshSaveOtherChildrenState();
        }
    }

    public void dismissTip() {
        dismissViewFooter();
    }

    @Deprecated
    public TipContainer getTipContainer() {
        return getBottomTipsContainer();
    }

    public boolean onPageLabelClick(List<als> list) {
        getSuspendManager();
        return false;
    }

    public void resetMapSkinState() {
        bty gLMapView = getGLMapView();
        if (!(this instanceof NotMapSkinPage) && gLMapView != null && getMapManager() != null && getMapManager().isMapSurfaceCreated()) {
            int p = bin.a.p("101");
            if (p != 0) {
                gLMapView.a(p, gLMapView.ae(), 0);
            } else {
                gLMapView.a(p, gLMapView.ae(), 0);
            }
        }
    }

    public bty getGLMapView() {
        MapManager mapManager = getMapManager();
        if (mapManager == null) {
            return null;
        }
        return mapManager.getMapView();
    }

    private void cleanUpSyncPopupWindow() {
        unregisterCloudSyncListener();
        hideSyncPopupWindow();
    }

    public void hideSyncPopupWindow() {
        if (this.mSyncPopupWindow != null) {
            this.mSyncPopupWindow.hide();
        }
    }

    public IPoiDetailDelegate getPoiDetailDelegate() {
        return this.mPoiDelegate;
    }

    public void onTipShow() {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.dismissCQLayer(true);
        }
    }

    /* access modifiers changed from: protected */
    public boolean isAlwaysMoveToCenterWhenRestoreFocus() {
        return this.mCQLayerController != null && this.mCQLayerController.isShowing() && this.mCQLayerController.getDetailLayerState() == DetailLayerState.EXPAND;
    }

    public int getSaveOverlayFocusKey() {
        return this.mSaveOverlayFocusKey;
    }

    /* access modifiers changed from: protected */
    public POI_DETAIL_TYPE getPoiDetailType() {
        return POI_DETAIL_TYPE.PAGE;
    }

    private BaseCQLayerOwner onCreateCQLayerOwner() {
        if (this.mCQLayerOwner == null) {
            this.mCQLayerOwner = createCQLayerOwner();
        }
        return this.mCQLayerOwner;
    }

    /* access modifiers changed from: protected */
    public BaseCQLayerOwner createCQLayerOwner() {
        return new BaseCQLayerOwner(this);
    }

    public bra getSlidePanelManager() {
        return this.mSlidePanelManager;
    }

    public boolean onTipRefreshCallbackForCQView(POI poi) {
        if (getPoiDetailType() != POI_DETAIL_TYPE.CQ_VIEW) {
            return false;
        }
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onTipRefreshCallback(poi);
        }
        return true;
    }

    public boolean isFooterMapPointRequestOutter() {
        switch (getPoiDetailType()) {
            case CQ_VIEW:
                if (this.mCQLayerController != null) {
                    return this.mCQLayerController.isMapPointRequestOutter();
                }
                return true;
            case PAGE:
                return false;
            default:
                return true;
        }
    }

    public void onMapPointRequestReturnNull() {
        if (AnonymousClass4.$SwitchMap$com$autonavi$map$fragmentcontainer$page$MapBasePage$POI_DETAIL_TYPE[getPoiDetailType().ordinal()] == 1 && this.mCQLayerController != null) {
            this.mCQLayerController.onMapPointRequestReturnNull();
        }
    }

    public void onStartDetail(POI poi, View view) {
        if (AnonymousClass4.$SwitchMap$com$autonavi$map$fragmentcontainer$page$MapBasePage$POI_DETAIL_TYPE[getPoiDetailType().ordinal()] != 1) {
            onTrunPoiDetialPage();
        } else if (this.mCQLayerController != null) {
            this.mCQLayerController.showCQLayer(poi, view, false);
            getBottomTipsContainer().dimissTips();
        }
    }

    public void onStartDetail(POI poi, ely<?> ely) {
        if (AnonymousClass4.$SwitchMap$com$autonavi$map$fragmentcontainer$page$MapBasePage$POI_DETAIL_TYPE[getPoiDetailType().ordinal()] != 1) {
            onTrunPoiDetialPage();
        } else if (this.mCQLayerController != null) {
            this.mCQLayerController.showCQLayer(poi, ely, false);
            getBottomTipsContainer().dimissTips();
        }
    }

    public void initCQLayerController() {
        if (this.mCQLayerController == null) {
            this.mCQLayerController = (ICQLayerController) ank.a(ICQLayerController.class);
            if (this.mCQLayerController != null) {
                this.mCQLayerController.init(onCreateCQLayerOwner());
            }
        }
    }

    public void initCQLayerViews() {
        if (this.mCQLayerOwner != null) {
            this.mCQLayerOwner.onInitView();
        }
    }

    public void pauseCQLayerController() {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onPause();
        }
    }

    public void resumeCQLayerController() {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onResume();
        }
    }

    public void destroyCQLayerController() {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onDestroy();
        }
    }

    public ON_BACK_TYPE onBackPressCQLayerController() {
        if (this.mCQLayerController != null) {
            ON_BACK_TYPE onBackPressed = this.mCQLayerController.onBackPressed();
            if (!(onBackPressed == null || onBackPressed == ON_BACK_TYPE.TYPE_NORMAL)) {
                return onBackPressed;
            }
        }
        return ON_BACK_TYPE.TYPE_NORMAL;
    }

    public void onFragmentResultCQLayerController(int i, ResultType resultType, PageBundle pageBundle) {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onFragmentResult(i, resultType, pageBundle);
        }
    }

    public void onMapSurfaceChangedCQLayerController(int i, int i2) {
        if (this.mCQLayerController != null) {
            this.mCQLayerController.onMapSurfaceChanged(i, i2);
        }
    }

    public ICQLayerController getCQLayerController() {
        return this.mCQLayerController;
    }

    private void registerCloudSyncListener() {
        bin.a.b(new biv() {
            public void saveSucess() {
                MapBasePage.this.showSyncPopupWindow();
            }
        });
    }

    private void unregisterCloudSyncListener() {
        bin.a.b(null);
    }

    /* access modifiers changed from: private */
    public void showSyncPopupWindow() {
        if (!bin.a.a()) {
            if (this.mSyncPopupWindow == null) {
                this.mSyncPopupWindow = new SyncPopupWindow(getContentView());
            }
            this.mSyncPopupWindow.show();
        }
    }
}
