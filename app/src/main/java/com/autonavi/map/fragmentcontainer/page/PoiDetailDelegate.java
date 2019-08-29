package com.autonavi.map.fragmentcontainer.page;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.amap.bundle.datamodel.FavoritePOI;
import com.autonavi.ae.gmap.glinterface.GLGeoPoint;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.GeocodePOI;
import com.autonavi.map.fragmentcontainer.GpsPOI;
import com.autonavi.map.fragmentcontainer.MapPointPOI;
import com.autonavi.map.fragmentcontainer.page.MapBasePage.POI_DETAIL_TYPE;
import com.autonavi.map.fragmentcontainer.page.dialog.TipContainer.OnTipChangedListener;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.map.widget.ITrafficViewForFeed;
import com.autonavi.minimap.R;
import com.autonavi.minimap.basemap.errorback.inter.ITrafficReportController;
import com.autonavi.minimap.map.mapinterface.AbstractGpsTipView;
import com.autonavi.minimap.map.mapinterface.AbstractPoiDetailView;
import com.autonavi.minimap.search.model.searchpoi.ISearchPoiData;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;

public class PoiDetailDelegate implements IPoiDetailDelegate {
    /* access modifiers changed from: private */
    public AbstractGpsTipView gpsTipView;
    private final MapBasePage mPage;
    private d mReleatedTrafficEventOwner;
    private int mTokenOfPoiDetailPage;
    private boolean mTrafficDlgShowOnPause = false;
    /* access modifiers changed from: private */
    public AbstractPoiDetailView poiDetailView;
    private int poiDetailViewHeight;
    /* access modifiers changed from: private */
    public ely poiTipView;
    private Dialog trafficItemDialog;
    private ITrafficViewForFeed trafficItemView;

    static class myOnDismissListener implements OnDismissListener {
        private WeakReference<MapBasePage> mapBasePageWeakReference = null;
        private WeakReference<PoiDetailDelegate> poiDetailDelegateWeakReference = null;

        myOnDismissListener(MapBasePage mapBasePage, PoiDetailDelegate poiDetailDelegate) {
            if (mapBasePage != null) {
                this.mapBasePageWeakReference = new WeakReference<>(mapBasePage);
            }
            if (poiDetailDelegate != null) {
                this.poiDetailDelegateWeakReference = new WeakReference<>(poiDetailDelegate);
            }
        }

        public void onDismiss(DialogInterface dialogInterface) {
            bty bty;
            PoiDetailDelegate poiDetailDelegate = null;
            MapBasePage mapBasePage = this.mapBasePageWeakReference != null ? (MapBasePage) this.mapBasePageWeakReference.get() : null;
            if (mapBasePage != null) {
                MapManager mapManager = mapBasePage.getMapManager();
                if (mapManager != null) {
                    mapManager.getOverlayManager().getTrafficPointOverlay().clear();
                    bty = mapManager.getMapView();
                } else {
                    bty = null;
                }
                if (bty != null) {
                    bty.P();
                }
            }
            if (this.poiDetailDelegateWeakReference != null) {
                poiDetailDelegate = (PoiDetailDelegate) this.poiDetailDelegateWeakReference.get();
            }
            if (poiDetailDelegate != null && !poiDetailDelegate.getTrafficDlgShowOnPause()) {
                poiDetailDelegate.uninittrafficItemDialog();
            }
        }
    }

    public PoiDetailDelegate(MapBasePage mapBasePage) {
        this.mPage = mapBasePage;
        if (mapBasePage instanceof d) {
            this.mReleatedTrafficEventOwner = (d) mapBasePage;
        }
    }

    public boolean isPoiDetailPageEnabled() {
        return this.mPage.getPoiDetailType() == POI_DETAIL_TYPE.PAGE;
    }

    public boolean isFooterMapPointRequestOutter() {
        return this.mPage.isFooterMapPointRequestOutter();
    }

    public void onMapPointRequestReturnNull() {
        this.mPage.onMapPointRequestReturnNull();
    }

    public AbstractPoiDetailView getpoiDetailView() {
        return this.poiDetailView;
    }

    public ely getIPoiTipView() {
        return this.poiTipView;
    }

    public AbstractGpsTipView getgpsTipView() {
        return this.gpsTipView;
    }

    public void onResume() {
        resumeTrafficItemDialog();
    }

    public void onPause() {
        pauseTrafficItemDialog();
    }

    public void onDestroy() {
        destroyTrafficItemDialog();
    }

    public void onConfigurationChanged() {
        boolean z = false;
        if (this.poiDetailView != null) {
            this.poiDetailView.refreshByScreenState(getResources().getConfiguration().orientation == 2);
        }
        if (this.poiTipView != null) {
            this.poiTipView.refreshByScreenState(getResources().getConfiguration().orientation == 2);
        }
        if (this.gpsTipView != null) {
            AbstractGpsTipView abstractGpsTipView = this.gpsTipView;
            if (getResources().getConfiguration().orientation == 2) {
                z = true;
            }
            abstractGpsTipView.refreshByScreenState(z);
        }
        trafficItemDialogRequestLayout();
    }

    public void drawOverlay(POI poi) {
        MapManager mapManager = this.mPage.getMapManager();
        if (mapManager != null) {
            if (this.mPage == AMapPageUtil.getPageContext()) {
                mapManager.getOverlayManager().getDeepInfoOverlayManager().a(poi);
                return;
            }
            int saveOverlayFocusKey = this.mPage.getSaveOverlayFocusKey();
            if (saveOverlayFocusKey > 0) {
                mapManager.getOverlayManager().getDeepInfoOverlayManager().a(saveOverlayFocusKey, poi);
            }
        }
    }

    public boolean isPoiTipsShowing() {
        return (this.poiTipView != null && this.poiTipView.getView().getVisibility() == 0) || (this.poiDetailView != null && this.poiDetailView.getVisibility() == 0) || (this.gpsTipView != null && this.gpsTipView.getVisibility() == 0);
    }

    public boolean isTrafficItemDialogShowing() {
        return this.trafficItemDialog != null && this.trafficItemDialog.isShowing();
    }

    private Resources getResources() {
        return this.mPage.getResources();
    }

    private void trafficItemDialogRequestLayout() {
        if (this.trafficItemDialog != null && this.trafficItemDialog.isShowing()) {
            this.trafficItemDialog.getWindow().getDecorView().requestLayout();
        }
    }

    private void resumeTrafficItemDialog() {
        if (this.trafficItemDialog != null && this.mTrafficDlgShowOnPause) {
            this.trafficItemDialog.show();
        }
    }

    private void pauseTrafficItemDialog() {
        this.mTrafficDlgShowOnPause = this.trafficItemDialog != null && this.trafficItemDialog.isShowing();
    }

    private void destroyTrafficItemDialog() {
        if (this.trafficItemDialog != null && this.trafficItemDialog.isShowing()) {
            this.trafficItemDialog.dismiss();
        }
    }

    public void uninittrafficItemDialog() {
        this.trafficItemDialog = null;
    }

    public boolean getTrafficDlgShowOnPause() {
        return this.mTrafficDlgShowOnPause;
    }

    public boolean isGpsTipDisable() {
        if (this.mPage instanceof IPoiDetailPage) {
            return this.mPage.isGpsTipDisable();
        }
        return false;
    }

    public boolean isTokenAvailable(int i) {
        return isTokenPageAvailable(i) || (this.mPage.getBottomTipsContainer() != null && this.mPage.getBottomTipsContainer().isTokenAvailable(i));
    }

    private boolean isTokenPageAvailable(int i) {
        return isTokenPageType() && this.mTokenOfPoiDetailPage != 0 && this.mTokenOfPoiDetailPage == i;
    }

    public void resetTokenPage() {
        this.mTokenOfPoiDetailPage = 0;
    }

    public void addOnTipChangedListener(OnTipChangedListener onTipChangedListener) {
        if (this.mPage.getBottomTipsContainer() != null) {
            this.mPage.getBottomTipsContainer().addOnTipChangedListener(onTipChangedListener);
        }
    }

    public void removeOnTipChangedListener(OnTipChangedListener onTipChangedListener) {
        if (this.mPage.getBottomTipsContainer() != null) {
            this.mPage.getBottomTipsContainer().removeOnTipChangedListener(onTipChangedListener);
        }
    }

    public void dimissFooter() {
        if (this.mPage.getBottomTipsContainer() != null) {
            this.mPage.getBottomTipsContainer().dimissTips();
        }
        this.mPage.getCQLayerController().dismissCQLayer(true);
    }

    public void showDomainPoiFooter(final PageBundle pageBundle, int i, final Callback<Integer> callback) {
        if (this.mPage.getBottomTipsContainer() != null) {
            POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                if (this.poiTipView == null) {
                    IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
                    if (iPoiTipViewService != null) {
                        this.poiTipView = iPoiTipViewService.createPoiTipView(this.mPage.getBottomTipsContainer().getContainer(), this.mPage, poi);
                    }
                    if (this.poiTipView != null) {
                        this.poiTipView.setSingle(true);
                        this.poiTipView.setFromSource("mainmap");
                        this.poiTipView.adjustMargin();
                        if (this.poiTipView instanceof ely) {
                            this.poiTipView.setTipItemEvent(iPoiTipViewService.createPoiTipEvent(false));
                        }
                    } else {
                        return;
                    }
                }
                this.poiTipView.refreshByScreenState(ags.b(this.mPage.getActivity()));
                AnonymousClass1 r1 = new Callback<Integer>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(Integer num) {
                        if (PoiDetailDelegate.this.poiTipView != null) {
                            PoiDetailDelegate.this.poiTipView.initData(null, (POI) pageBundle.getObject("POI"), 2);
                            if (callback != null) {
                                callback.callback(num);
                            }
                        }
                    }
                };
                switch (this.mPage.getPoiDetailType()) {
                    case PAGE:
                    case CQ_VIEW:
                        this.mTokenOfPoiDetailPage = (int) System.currentTimeMillis();
                        r1.callback(Integer.valueOf(this.mTokenOfPoiDetailPage));
                        this.mPage.onStartDetail(poi, this.poiTipView);
                        return;
                    case DEFAULT:
                        this.mPage.getBottomTipsContainer().showTip(this.poiTipView.getView(), i, r1);
                        break;
                }
            }
        }
    }

    private boolean isTokenPageType() {
        return this.mPage.getPoiDetailType() == POI_DETAIL_TYPE.PAGE || this.mPage.getPoiDetailType() == POI_DETAIL_TYPE.CQ_VIEW;
    }

    private void refreshDomainPoiFooter(PageBundle pageBundle, int i) {
        if (isTokenPageAvailable(i) || (this.mPage.getBottomTipsContainer() != null && this.poiTipView != null && this.poiTipView.getView().getVisibility() == 0 && this.mPage.getBottomTipsContainer().isTokenAvailable(i))) {
            POI poi = (POI) pageBundle.getObject("POI");
            POI poi2 = this.poiTipView.getPoi();
            if (poi != null && poi2 != null) {
                poi.setPoint(poi2.getPoint());
                String str = (String) poi2.getPoiExtra().get("titleName");
                if (!TextUtils.isEmpty(str)) {
                    poi.getPoiExtra().put("titleName", str);
                }
                HashMap<String, Serializable> poiExtra = poi2.getPoiExtra();
                if (poiExtra != null) {
                    Serializable serializable = poiExtra.get("pointType");
                    if (serializable != null) {
                        poi.getPoiExtra().put("pointType", serializable);
                    }
                }
                if (!this.mPage.onTipRefreshCallbackForCQView(poi)) {
                    this.poiTipView.initData(null, poi, 2);
                }
                ((ISearchPoiData) poi.as(ISearchPoiData.class)).getTemplateDataMap();
            }
        }
    }

    public void refreshPoiFooter(PageBundle pageBundle, int i) {
        if (isTokenAvailable(i)) {
            POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                if (GpsPOI.class.isInstance(poi)) {
                    if (this.gpsTipView != null) {
                        this.gpsTipView.reset();
                        this.gpsTipView.setFromPageID(10001);
                    }
                } else if (!MapPointPOI.class.isInstance(poi)) {
                    setPoiFooterDetail(this.poiDetailView, pageBundle);
                } else if (this.poiTipView != null) {
                    refreshDomainPoiFooter(pageBundle, i);
                }
            }
        }
    }

    private void showDefaultMapTip() {
        if (this.mPage != null) {
            this.mPage.showDefaultMapTip();
        }
    }

    public MapBasePage getPage() {
        return this.mPage;
    }

    public void showPoiFooter(final PageBundle pageBundle, int i, final Callback<Integer> callback) {
        this.mPage.showDefaultMapTip();
        if (pageBundle != null && pageBundle.containsKey(IOverlayManager.EVENT_ID_KEY)) {
            if (pageBundle.getInt(IOverlayManager.EVENT_ID_KEY) > 0) {
                boolean z = pageBundle.getBoolean(IOverlayManager.TRAFFIC_GROUP_FLAG_KEY, false);
                a aVar = null;
                if (!(this.trafficItemView == null || ((FrameLayout) this.trafficItemView).getParent() == null || z)) {
                    ((ViewGroup) ((FrameLayout) this.trafficItemView).getParent()).removeAllViews();
                    this.trafficItemView = null;
                }
                bid pageContext = AMapPageUtil.getPageContext();
                if (pageContext != null && (pageContext instanceof d)) {
                    this.mReleatedTrafficEventOwner = (d) pageContext;
                    aVar = this.mReleatedTrafficEventOwner.getReleatedTrafficEventHandler();
                }
                ITrafficReportController iTrafficReportController = (ITrafficReportController) ank.a(ITrafficReportController.class);
                if (this.mReleatedTrafficEventOwner == null || aVar == null || iTrafficReportController == null) {
                    showTrafficDialog(pageBundle);
                } else {
                    if (!z) {
                        this.trafficItemView = iTrafficReportController.a(this.mPage.getActivity(), pageBundle, this.mPage.getMapManager(), aVar);
                        if (this.trafficItemView != null) {
                            this.trafficItemView.refreshByScreenState();
                        }
                        aVar.a((View) this.trafficItemView);
                    } else if (this.trafficItemView != null) {
                        boolean updateTrafficEvent = this.trafficItemView.updateTrafficEvent(pageBundle);
                        this.trafficItemView.refreshByScreenState();
                        getClass().getName();
                        new StringBuilder("The traffic event group has been updated ").append(updateTrafficEvent ? "Success" : "Failed");
                    }
                    aVar.i();
                }
            }
        } else if (this.mPage.getBottomTipsContainer() != null) {
            final POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                if (MapPointPOI.class.isInstance(poi)) {
                    showDomainPoiFooter(pageBundle, i, callback);
                } else if (GpsPOI.class.isInstance(poi)) {
                    showGpsFooter(pageBundle, i, callback);
                } else {
                    IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
                    if (this.poiDetailView == null && iPoiTipViewService != null) {
                        this.poiDetailView = iPoiTipViewService.createPoiDetailViewForCQ();
                        this.poiDetailView.adjustMargin();
                    }
                    this.poiDetailView.refreshByScreenState(ags.b(this.mPage.getActivity()));
                    AnonymousClass2 r1 = new Callback<Integer>() {
                        public void error(Throwable th, boolean z) {
                        }

                        public void callback(Integer num) {
                            PoiDetailDelegate.this.poiDetailView.reset();
                            PoiDetailDelegate.this.setPoiFooterDetail(PoiDetailDelegate.this.poiDetailView, pageBundle);
                            if (GeocodePOI.class.isInstance(poi)) {
                                PoiDetailDelegate.this.adjustMarker(poi.getPoint());
                            }
                            if (callback != null) {
                                callback.callback(num);
                            }
                        }
                    };
                    switch (this.mPage.getPoiDetailType()) {
                        case PAGE:
                        case CQ_VIEW:
                            this.mTokenOfPoiDetailPage = (int) System.currentTimeMillis();
                            r1.callback(Integer.valueOf(this.mTokenOfPoiDetailPage));
                            this.mPage.onStartDetail(poi, (View) this.poiDetailView);
                            return;
                        case DEFAULT:
                            this.mPage.getBottomTipsContainer().showTip(this.poiDetailView, i, r1);
                            break;
                    }
                }
            }
        }
    }

    private void showTrafficDialog(PageBundle pageBundle) {
        myOnDismissListener myondismisslistener = new myOnDismissListener(this.mPage, this);
        if (this.trafficItemDialog != null && this.trafficItemDialog.isShowing()) {
            this.trafficItemDialog.dismiss();
            this.trafficItemDialog = null;
        }
        ITrafficReportController iTrafficReportController = (ITrafficReportController) ank.a(ITrafficReportController.class);
        if (iTrafficReportController != null) {
            this.trafficItemDialog = iTrafficReportController.a(this.mPage.getActivity(), pageBundle, this.mPage.getMapManager());
            if (this.trafficItemDialog != null) {
                this.trafficItemDialog.setOnDismissListener(myondismisslistener);
                this.trafficItemDialog.show();
            }
        }
    }

    private void showGpsFooter(PageBundle pageBundle, int i, final Callback<Integer> callback) {
        cdx cdx;
        if (this.mPage.getBottomTipsContainer() != null) {
            POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                MapManager mapManager = this.mPage.getMapManager();
                if (this.gpsTipView == null) {
                    IPoiTipViewService iPoiTipViewService = (IPoiTipViewService) ank.a(IPoiTipViewService.class);
                    if (iPoiTipViewService != null) {
                        MapBasePage mapBasePage = this.mPage;
                        if (mapManager == null) {
                            cdx = null;
                        } else {
                            cdx = mapManager.getOverlayManager().getGpsLayer();
                        }
                        this.gpsTipView = iPoiTipViewService.createGpsTipViewForPoiDetaiilDelegate(mapBasePage, cdx);
                        this.gpsTipView.adjustMargin();
                    }
                } else if (this.mPage.getPoiDetailType() == POI_DETAIL_TYPE.DEFAULT && this.gpsTipView.getVisibility() == 0) {
                    return;
                }
                this.gpsTipView.setFromPageID(10001);
                this.gpsTipView.refreshByScreenState(ags.b(this.mPage.getActivity()));
                AnonymousClass3 r0 = new Callback<Integer>() {
                    public void error(Throwable th, boolean z) {
                    }

                    public void callback(Integer num) {
                        PoiDetailDelegate.this.gpsTipView.reset();
                        PoiDetailDelegate.this.gpsTipView.setFromPageID(10001);
                        if (callback != null) {
                            callback.callback(num);
                        }
                    }
                };
                switch (this.mPage.getPoiDetailType()) {
                    case PAGE:
                    case CQ_VIEW:
                        this.mTokenOfPoiDetailPage = (int) System.currentTimeMillis();
                        r0.callback(Integer.valueOf(this.mTokenOfPoiDetailPage));
                        this.mPage.onStartDetail(poi, (View) this.gpsTipView);
                        return;
                    case DEFAULT:
                        this.mPage.getBottomTipsContainer().showTip(this.gpsTipView, i, r0);
                        break;
                }
            }
        }
    }

    public void adjustMarker(GeoPoint geoPoint) {
        if (this.poiDetailViewHeight <= 0) {
            this.poiDetailViewHeight = getPoiDetailViewMeasureHeight();
        }
        if (this.poiDetailViewHeight > 0 && geoPoint != null) {
            Point point = new Point();
            bty mapView = this.mPage.getMapManager().getMapView();
            if (mapView != null) {
                mapView.a((GLGeoPoint) geoPoint, point);
                int i = this.poiDetailViewHeight;
                if (new Rect(0, mapView.am() - i, mapView.al(), mapView.am()).contains(point.x, point.y)) {
                    mapView.a(mapView.c(mapView.al() / 2, (mapView.am() / 2) + (i - (mapView.am() - point.y)) + 150));
                }
            }
        }
    }

    private int getPoiDetailViewMeasureHeight() {
        if (this.poiDetailView == null) {
            return 0;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(0, 0);
        this.poiDetailView.measure(MeasureSpec.makeMeasureSpec(0, 0), makeMeasureSpec);
        return this.poiDetailView.getMeasuredHeight();
    }

    /* access modifiers changed from: private */
    public void setPoiFooterDetail(AbstractPoiDetailView abstractPoiDetailView, PageBundle pageBundle) {
        if (pageBundle != null && abstractPoiDetailView != null) {
            String string = pageBundle.getString("mainTitle");
            String string2 = pageBundle.getString("viceTitle");
            POI poi = (POI) pageBundle.getObject("POI");
            if (poi != null) {
                if (string == null && string2 == null) {
                    if (!((FavoritePOI) poi.as(FavoritePOI.class)).isSaved()) {
                        string = poi.getName();
                        string2 = poi.getAddr();
                    } else {
                        string = ((FavoritePOI) poi.as(FavoritePOI.class)).getCustomName();
                        if (TextUtils.isEmpty(string)) {
                            string = ((FavoritePOI) poi.as(FavoritePOI.class)).getName();
                        }
                        string2 = ((FavoritePOI) poi.as(FavoritePOI.class)).getAddr();
                    }
                }
                boolean isInstance = GpsPOI.class.isInstance(poi);
                View findViewById = abstractPoiDetailView.findViewById(R.id.child_station_ll);
                TextView textView = (TextView) abstractPoiDetailView.findViewById(R.id.station_num);
                if (pageBundle.getBoolean("isChildStation")) {
                    findViewById.setVisibility(0);
                    textView.setText(pageBundle.getString("childAlia"));
                } else {
                    findViewById.setVisibility(8);
                }
                abstractPoiDetailView.setMainTitle(string);
                abstractPoiDetailView.setViceTitle(string2);
                ISearchPoiData iSearchPoiData = (ISearchPoiData) poi.as(ISearchPoiData.class);
                if (iSearchPoiData.getPoiChildrenInfo() != null) {
                    Collection<? extends POI> collection = iSearchPoiData.getPoiChildrenInfo().stationList;
                    if (collection != null && collection.size() > 0) {
                        abstractPoiDetailView.setPoi(((ChildStationPoiData[]) collection.toArray(new ChildStationPoiData[collection.size()]))[0]);
                        return;
                    }
                }
                if (isInstance && TextUtils.isEmpty(poi.getName())) {
                    poi.setName(AMapAppGlobal.getApplication().getString(R.string.my_location));
                }
                abstractPoiDetailView.setPoi(poi);
            }
        }
    }
}
