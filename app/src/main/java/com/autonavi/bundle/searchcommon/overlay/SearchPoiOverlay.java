package com.autonavi.bundle.searchcommon.overlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.imageloader.ImageLoader;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public abstract class SearchPoiOverlay extends PointOverlay<bbr> {
    private static final int FOCUSEDPOIINDEX = -2;
    private int mBrandCurIndex = 0;
    private Bitmap mDefaultIcon;
    private bbo mFocusChangeCallback;
    private int mFocusedPoiIndex = -2;
    private HashMap<bkf, Object> mMap;
    private HashMap<String, Marker> mMarkerCache;
    private int mNormalCurIndex = 0;
    protected SearchPoi mSearchPoi;
    private ArrayList<String> poiFilters;

    /* access modifiers changed from: protected */
    public abstract Marker getDefaultMarker(int i, DynamicRenderData dynamicRenderData);

    /* access modifiers changed from: protected */
    public abstract Marker getFocusMarker(int i, DynamicRenderData dynamicRenderData);

    public SearchPoiOverlay(bty bty) {
        super(bty);
        if (bty != null) {
            this.mMarkerCache = new HashMap<>();
            this.mMap = new HashMap<>();
            this.poiFilters = new ArrayList<>();
            setMoveToFocus(false);
            try {
                InputStream openRawResource = AMapAppGlobal.getApplication().getResources().openRawResource(R.raw.search_brand_default);
                if (openRawResource != null) {
                    this.mDefaultIcon = BitmapFactory.decodeStream(openRawResource);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void iniGLOverlay() {
        super.iniGLOverlay();
    }

    public void addPoiItem(POI poi, int i, boolean z) {
        if (poi == null) {
            this.mSearchPoi = null;
            return;
        }
        this.mSearchPoi = (SearchPoi) poi.as(SearchPoi.class);
        bbr bbr = new bbr(this.mSearchPoi, this.mSearchPoi.getPoint());
        if (z) {
            int iconId = poi.getIconId();
            if (iconId <= 0) {
                DynamicRenderData iDynamicRenderInfo = this.mSearchPoi.getIDynamicRenderInfo();
                bbr.mDefaultMarker = getDefaultMarker(i, iDynamicRenderInfo);
                bbr.mFocusMarker = getFocusMarker(i, iDynamicRenderInfo);
            } else {
                bbr.mDefaultMarker = createMarker(iconId, 5);
                bbr.mFocusMarker = createMarker(R.drawable.b_poi_hl, 5);
                bbr.mBgMarker = getIconBgMarker(this.mSearchPoi);
            }
            if (isOfflinePOI(poi)) {
                GeoPoint point = poi.getPoint();
                this.mMapView.a(point.x, point.y, 2, (float) bbr.mDefaultMarker.mWidth, (float) bbr.mDefaultMarker.mHeight, poi.getId());
                this.poiFilters.add(poi.getId());
            } else {
                GeoPoint point2 = poi.getPoint();
                if (((SearchPoi) poi.as(SearchPoi.class)).getPoiChildrenInfo() != null && ((SearchPoi) poi.as(SearchPoi.class)).getPoiChildrenInfo().childType == 1) {
                    point2 = ((SearchPoi) poi.as(SearchPoi.class)).getPoint();
                }
                this.mMapView.a(point2.x, point2.y, 2, (float) (bbr.mDefaultMarker.mWidth - 8), (float) (bbr.mDefaultMarker.mHeight - 8), poi.getId(), 8);
                this.poiFilters.add(poi.getId());
            }
            addItem(bbr);
        }
    }

    public void setFocus(int i, boolean z) {
        super.setFocus(i, z);
    }

    public void setFocus(PointOverlayItem pointOverlayItem, boolean z) {
        super.setFocus(pointOverlayItem, z);
    }

    public void setFocusChangeCallback(bbo bbo) {
        this.mFocusChangeCallback = bbo;
    }

    public boolean clear() {
        if (this.mMap != null) {
            for (bkf a : this.mMap.keySet()) {
                ImageLoader.a((Context) AMapAppGlobal.getApplication()).a((Object) a);
            }
            this.mMap.clear();
        }
        if (this.mMarkerCache != null) {
            this.mMarkerCache.clear();
        }
        this.mBrandCurIndex = 0;
        this.mNormalCurIndex = 0;
        Iterator<String> it = this.poiFilters.iterator();
        while (it.hasNext()) {
            this.mMapView.a(it.next());
        }
        this.poiFilters.clear();
        this.mFocusedPoiIndex = -2;
        return super.clear();
    }

    public void clearFocus() {
        super.clearFocus();
        this.mFocusedPoiIndex = -2;
        if (this.mFocusChangeCallback != null) {
            this.mFocusChangeCallback.a(this);
        }
    }

    public void setFocusIndex(int i) {
        this.mFocusedPoiIndex = i;
    }

    public int getFocusedIndex() {
        return this.mFocusedPoiIndex;
    }

    /* access modifiers changed from: protected */
    public boolean showIconBgMarker(SearchPoi searchPoi) {
        if (searchPoi.getDisplayIconNameState() != 1 || TextUtils.isEmpty(searchPoi.getIconSrcName())) {
            return false;
        }
        return true;
    }

    private boolean showTxtBgMarker(SearchPoi searchPoi) {
        if ((searchPoi.getIDynamicRenderInfo() == null || !searchPoi.getIDynamicRenderInfo().bFlag) && searchPoi.getIsShowName() && searchPoi.getDisplayIconNameState() == 2 && !TextUtils.isEmpty(searchPoi.getName()) && searchPoi.getName().length() <= 8) {
            return true;
        }
        return false;
    }

    private Marker getIconBgMarker(SearchPoi searchPoi) {
        String iconSrcName = searchPoi.getIconSrcName();
        Marker marker = null;
        if (PoiLayoutTemplate.BUSDADZHAN_BG.equals(iconSrcName)) {
            return null;
        }
        int a = bby.a(iconSrcName, this.mContext);
        if (a > 0) {
            marker = createMarker(a, 4);
        }
        return marker;
    }

    public boolean isOfflinePOI(POI poi) {
        if (poi != null && poi.getPoiExtra().containsKey("SrcType")) {
            String str = (String) poi.getPoiExtra().get("SrcType");
            if (!TextUtils.isEmpty(str) && "nativepoi".equals(str)) {
                return true;
            }
        }
        return false;
    }
}
