package com.autonavi.minimap.basemap.save.page;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.Page.ON_BACK_TYPE;
import com.autonavi.common.Page.ResultType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.IOverlayManager;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.map.fragmentcontainer.page.MapBasePage.POI_DETAIL_TYPE;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.MapPointOverlay;
import com.autonavi.minimap.base.overlay.MapPointOverlayItem;
import com.autonavi.minimap.map.FavoriteLayer;
import com.autonavi.minimap.map.FavoriteLayer.FavoriteItemFocusChangeListener;
import com.autonavi.minimap.map.FavoriteLayer.FavoriteItemsRefreshListener;
import com.autonavi.minimap.map.FavoriteOverlayItem;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OvProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.OverlayPageProperty;
import com.autonavi.minimap.map.overlayholder.OverlayPage.UvOverlay;
import com.autonavi.minimap.map.overlayholder.OverlayPage.VisiblePolicy;
import com.autonavi.widget.ui.TitleBar;
import java.lang.ref.WeakReference;

@OverlayPageProperty(overlays = {@OvProperty(clickable = true, moveToFocus = true, overlay = UvOverlay.SaveOverlay, visible = true, visiblePolicy = VisiblePolicy.IgnoreConfig)})
public class SavePointToMapPage extends MapBasePage<crj> {
    b a = new b(this);
    a b = new a(this);
    private TitleBar c;
    /* access modifiers changed from: private */
    public bth d;
    /* access modifiers changed from: private */
    public POI e;
    /* access modifiers changed from: private */
    public FavoriteLayer f;
    private boolean g = true;
    private int h = 16;
    private crr i;
    private OnClickListener j = new OnClickListener() {
        public final void onClick(View view) {
            if (((crj) SavePointToMapPage.this.mPresenter).onBackPressed() == ON_BACK_TYPE.TYPE_NORMAL) {
                SavePointToMapPage.this.finish();
            }
        }
    };

    static class a implements FavoriteItemsRefreshListener {
        private WeakReference<SavePointToMapPage> a;

        public a(SavePointToMapPage savePointToMapPage) {
            this.a = new WeakReference<>(savePointToMapPage);
        }

        public final void onFavoriteItemsRefresh() {
            if (this.a != null) {
                SavePointToMapPage savePointToMapPage = (SavePointToMapPage) this.a.get();
                if (savePointToMapPage != null) {
                    savePointToMapPage.f.setFocus(new FavoriteOverlayItem(savePointToMapPage.d));
                }
            }
        }
    }

    static class b implements FavoriteItemFocusChangeListener {
        private WeakReference<SavePointToMapPage> a;

        public b(SavePointToMapPage savePointToMapPage) {
            this.a = new WeakReference<>(savePointToMapPage);
        }

        public final void onFavoriteFocusChange(int i, FavoriteOverlayItem favoriteOverlayItem) {
            if (this.a != null) {
                SavePointToMapPage savePointToMapPage = (SavePointToMapPage) this.a.get();
                if (!(savePointToMapPage == null || favoriteOverlayItem == null)) {
                    savePointToMapPage.e = favoriteOverlayItem.getPOI();
                    savePointToMapPage.d = favoriteOverlayItem.getSavePoint();
                    SavePointToMapPage.b(savePointToMapPage);
                    savePointToMapPage.getMapView().a(300, 16.0f, -9999, -9999, favoriteOverlayItem.getGeoPoint().x, favoriteOverlayItem.getGeoPoint().y);
                }
            }
        }
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.d = (bth) getArguments().get("currentSelectedPoi");
        if (this.d != null) {
            this.e = this.d.a();
        }
        brn brn = (brn) ank.a(brn.class);
        if (brn != null) {
            this.f = brn.g();
        } else {
            AMapLog.d("SavePointToMapFragment", "saveOverlay init failed.");
        }
        setContentView(R.layout.save_point_map_fragment);
        View contentView = getContentView();
        this.i = new crr(this);
        this.c = (TitleBar) contentView.findViewById(R.id.title);
        this.c.setOnBackClickListener(this.j);
        String name = this.e.getName();
        if (name == null) {
            this.c.setTitle(getString(R.string.fav_saved_poi));
        } else {
            this.c.setTitle(name);
        }
        cde suspendManager = getSuspendManager();
        if (suspendManager != null) {
            suspendManager.d().f();
        } else {
            AMapLog.d("SavePointToMapFragment", "initView: unlockGPSButton failed.");
        }
        if (getArguments() == null) {
            finish();
        } else if (this.e == null) {
            finish();
        } else {
            cde suspendManager2 = getSuspendManager();
            if (suspendManager2 != null) {
                suspendManager2.d().a(false);
            } else {
                AMapLog.d("SavePointToMapFragment", "initData: setAnimateToGPSLocation failed.");
            }
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public crj createPresenter() {
        return new crj(this);
    }

    public View getMapSuspendView() {
        return this.i.a();
    }

    public void onPagePause() {
        super.onPagePause();
        getMapCustomizeManager().getMapLayerDialogCustomActions().a = 1;
        MapManager mapManager = getMapManager();
        bty mapView = mapManager != null ? mapManager.getMapView() : null;
        if (this.f != null) {
            this.f.setFavoriteItemFocusChangeListener(null);
            if (this.f.getFocusItem() != null) {
                this.f.clearFocus();
            }
            this.f.setFavoriteItemVisible(false);
        }
        if (mapView != null) {
            mapView.g(true);
        } else {
            AMapLog.d("SavePointToMapFragment", "onPause: mapView.endableFocusClear(true) failed.");
        }
    }

    public void onPageResume() {
        super.onPageResume();
        MapManager mapManager = getMapManager();
        bty mapView = mapManager != null ? mapManager.getMapView() : null;
        getMapCustomizeManager().getMapLayerDialogCustomActions().a = 6;
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        if (getSuspendWidgetHelper() != null) {
            getSuspendWidgetHelper().d(booleanValue);
        }
        if (this.f != null) {
            this.f.setFavoriteItemFocusChangeListener(this.a);
            this.f.setFavoriteItemsRefreshListener(this.b);
        }
        MapPointOverlay mapPointOverlay = getMapManager().getOverlayManager().getMapPointOverlay();
        mapPointOverlay.setItem(new MapPointOverlayItem(this.e.getPoint(), R.drawable.b_poi_hl));
        mapPointOverlay.setVisible(true);
        if (this.g || bim.aa().g()) {
            bim.aa().d(false);
            this.g = false;
        }
        this.d.a().getPoiExtra().put(IOverlayManager.POI_EXTRA_FROM_FAV, Boolean.TRUE);
        if (this.f != null) {
            this.f.refreshAll();
            this.f.onFavoriteLayerClick(new FavoriteOverlayItem(this.d));
        }
        MapManager mapManager2 = getMapManager();
        if (mapManager2 != null) {
            bty mapView2 = mapManager2.getMapView();
            if (mapView2 != null) {
                mapView2.f((float) this.h);
                mapView2.a(this.e.getPoint().x, this.e.getPoint().y);
            } else {
                AMapLog.d("SavePointToMapFragment", "mapView is NULL");
            }
        }
        if (mapView != null) {
            mapView.g(false);
        } else {
            AMapLog.d("SavePointToMapFragment", "onResume: mapView.endableFocusClear(false) failed.");
        }
    }

    public POI_DETAIL_TYPE getPoiDetailType() {
        return POI_DETAIL_TYPE.DEFAULT;
    }

    public void onPageResumePost() {
        super.onPageResumePost();
    }

    public void onPageStop() {
        super.onPageStop();
        if (getMapView() != null) {
            this.h = getMapView().w();
        }
    }

    public void onPageDestroy() {
        super.onPageDestroy();
        boolean k = bim.aa().k((String) "104");
        if (this.f != null) {
            if (!k) {
                this.f.setVisible(false);
            }
            this.f.clearFocus();
        }
        bim.aa().f(true);
        bim.aa().d(true);
    }

    public void onPageResult(int i2, ResultType resultType, PageBundle pageBundle) {
        super.onPageResult(i2, resultType, pageBundle);
        if (1111 == i2 && resultType == ResultType.CANCEL) {
            finish();
        }
    }

    static /* synthetic */ void b(SavePointToMapPage savePointToMapPage) {
        if (savePointToMapPage.e != null && savePointToMapPage.c != null) {
            savePointToMapPage.c.setTitle(savePointToMapPage.e.getName());
        }
    }
}
