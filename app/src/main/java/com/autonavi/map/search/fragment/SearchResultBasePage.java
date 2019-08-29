package com.autonavi.map.search.fragment;

import android.content.Context;
import android.view.View;

import com.amap.bundle.mapstorage.MapSharePreference;
import com.amap.bundle.mapstorage.MapSharePreference.SharePreferenceName;
import com.autonavi.common.PageBundle;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.BaseCQLayerOwner;
import com.autonavi.map.fragmentcontainer.page.MapBasePage;
import com.autonavi.minimap.base.overlay.PointOverlay;

import java.util.List;

import defpackage.*;

public abstract class SearchResultBasePage extends MapBasePage< cbe > implements bgm, btz {
    protected bxk a;
    private boolean b = false;

    /* access modifiers changed from: protected */
    public abstract bxk a(SearchResultBasePage searchResultBasePage);

    public int getTrafficEventSource() {
        return 1;
    }

    public boolean isGpsTipDisable() {
        return true;
    }

    public boolean isUsePoiDelegate() {
        return true;
    }

    public void onCreate(Context context) {
        super.onCreate(context);
        this.a.a(context);
        requestScreenOrientation(1);
        getMapCustomizeManager().enableView(268468228);
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            this.b = mapView.s();
        }
    }

    public void onPageResume() {
        super.onPageResume();
        PointOverlay b2 = b();
        if (b2 != null) {
            b2.setAnimatorType(0);
        }
        PointOverlay b3 = b();
        if (b3 != null) {
            b3.setAnimatorType(1);
        }
        bty mapView = getMapManager().getMapView();
        boolean booleanValue = new MapSharePreference(SharePreferenceName.SharedPreferences).getBooleanValue("traffic", false);
        if (mapView != null) {
            mapView.b(booleanValue);
        }
    }

    public void onPagePause() {
        super.onPagePause();
        bty mapView = getMapManager().getMapView();
        if (mapView != null) {
            mapView.b(this.b);
        }
    }

    public void onPageNewNodeFragmentBundle(PageBundle pageBundle) {
        super.onPageNewNodeFragmentBundle(pageBundle);
    }

    public void onPageDestroy() {
        super.onPageDestroy();
    }

    public ON_BACK_TYPE onPageBackPressed() {
        return super.onPageBackPressed();
    }

    public final void a(int i) {
        super.setContentView(i);
    }

    private PointOverlay b() {
        MapManager mapManager = getMapManager();
        if (mapManager == null) {
            return null;
        }
        return mapManager.getOverlayManager().getGeoCodeOverlay();
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public cbe createPresenter() {
        this.a = a(this);
        cbe cbe = new cbe(this);
        cbe.a = this.a;
        return cbe;
    }

    public View getMapSuspendView() {
        return this.a.b(getContext());
    }

    public POI_DETAIL_TYPE getPoiDetailType() {
        return POI_DETAIL_TYPE.CQ_VIEW;
    }

    public BaseCQLayerOwner createCQLayerOwner() {
        return ((cbe) this.mPresenter).a.t();
    }

    public final long a() {
        if (this.a == null) {
            return -1;
        }
        return this.a.u();
    }

    public boolean onMapLayerSetDefaultMode() {
        cbe cbe = (cbe) this.mPresenter;
        if (cbe.a instanceof bxo ) {
            return ((bxo) cbe.a).ae();
        }
        return false;
    }

    public void onPageWindowFocusChanged(boolean z) {
        super.onPageWindowFocusChanged(z);
        this.a.b(z);
    }

    public void onSelectSubWayActive(List<Long> list) {
        super.onSelectSubWayActive(list);
        this.a.onSelectSubWayActive(list);
    }

    public /* bridge */ /* synthetic */ bgo getPresenter() {
        return (cbe) this.mPresenter;
    }
}
