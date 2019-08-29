package com.autonavi.map.search.overlay;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.map.widget.StrokeTextView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@SuppressFBWarnings({"SE_NO_SERIALVERSIONID"})
public class SearchChildOverlay extends PointOverlay<bbr> {
    private static final int CHILD_ICON_PARK = 41;
    public int mFocusedPoiIndex = -1;
    private int mItemIndex = -1;
    private ArrayList<String> poiFilters = new ArrayList<>();

    public SearchChildOverlay(bty bty) {
        super(bty);
        setClearWhenLoseFocus(true);
        setMoveToFocus(false);
    }

    public void addChildPoi(POI poi, int i, int i2) {
        if (poi != null) {
            this.mFocusedPoiIndex = i;
            SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
            Collection<? extends POI> collection = null;
            if (searchPoi.getPoiChildrenInfo() != null) {
                collection = searchPoi.getPoiChildrenInfo().poiList;
            }
            if (collection != null && collection.size() > 0) {
                for (POI as : collection) {
                    bbr createChildPoiItem = createChildPoiItem((SearchPoi) as.as(SearchPoi.class));
                    addItem(createChildPoiItem);
                    setPointItemVisble((PointOverlayItem) createChildPoiItem, false, false);
                }
            }
        }
    }

    public void addChildrenPOI(List<POI> list) {
        if (list != null) {
            for (POI as : list) {
                bbr createChildPoiItem = createChildPoiItem((SearchPoi) as.as(SearchPoi.class));
                addItem(createChildPoiItem);
                setPointItemVisble((PointOverlayItem) createChildPoiItem, false, false);
            }
        }
    }

    private bbr createChildPoiItem(SearchPoi searchPoi) {
        SearchPoi searchPoi2 = searchPoi;
        if (searchPoi2 == null) {
            return null;
        }
        bbr bbr = new bbr(searchPoi2);
        bbr.mBubbleMarker = createMarker(R.drawable.b_poi_hl, 9, 0.5f, 0.87f);
        if (bbr.mBubbleMarker != null) {
            setBubbleAnimator(2);
        }
        if (searchPoi.getIDynamicRenderInfo() != null && searchPoi.getIDynamicRenderInfo().bFlag) {
            return bbr;
        }
        bbr.mDefaultMarker = createMarker(searchPoi.getIconId(), 4);
        if (searchPoi.getPoiChildrenInfo() == null || searchPoi.getPoiChildrenInfo().childType == 41) {
            bty bty = this.mMapView;
            int i = searchPoi.getPoint().x;
            bty.a(i, searchPoi.getPoint().y, 2, (float) agn.a((Context) AMapAppGlobal.getApplication(), 8.0f), (float) agn.a((Context) AMapAppGlobal.getApplication(), 8.0f), searchPoi.getId());
        } else {
            MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, bbr.getGeoPoint(), 3);
            mapViewLayoutParams.mode = 0;
            TextView createPoiNameView = createPoiNameView(this.mContext, searchPoi.getShortName());
            createPoiNameView.setTextSize(1, 11.0f);
            createPoiNameView.setTextColor(this.mContext.getResources().getColor(R.color.map_poi_name));
            this.mMapView.a((View) createPoiNameView, (LayoutParams) mapViewLayoutParams);
            TextView createPoiNameView2 = createPoiNameView(this.mContext, searchPoi.getShortName());
            createPoiNameView2.setTextSize(1, 12.0f);
            createPoiNameView2.setTextColor(this.mContext.getResources().getColor(R.color.map_poi_name_hl));
            this.mMapView.a((View) createPoiNameView2, (LayoutParams) mapViewLayoutParams);
            int i2 = this.mItemIndex + 1;
            this.mItemIndex = i2;
            bbr.mBgFocusMarker = createMarker(i2, (View) createPoiNameView2, 7, 0.0f, 0.0f, true);
            int i3 = this.mItemIndex + 1;
            this.mItemIndex = i3;
            bbr.mBgMarker = createMarker(i3, (View) createPoiNameView, 7, 0.0f, 0.0f, true);
            this.mMapView.a((View) createPoiNameView);
            this.mMapView.a((View) createPoiNameView2);
            this.mMapView.a(searchPoi.getPoint().x, searchPoi.getPoint().y, 0, (float) createPoiNameView.getMeasuredWidth(), (float) createPoiNameView.getMeasuredHeight(), searchPoi.getId());
        }
        this.poiFilters.add(searchPoi.getId());
        return bbr;
    }

    private TextView createPoiNameView(Context context, String str) {
        StrokeTextView strokeTextView = new StrokeTextView(context);
        strokeTextView.setTextSize(1, 11.0f);
        strokeTextView.setTextColor(context.getResources().getColor(R.color.map_poi_name));
        strokeTextView.setPadding(agn.a(context, 8.0f), agn.a(context, 3.0f), agn.a(context, 2.0f), agn.a(context, 3.0f));
        strokeTextView.setText(str);
        strokeTextView.setSingleLine();
        strokeTextView.measure(0, 0);
        strokeTextView.layout(0, 0, strokeTextView.getMeasuredWidth(), strokeTextView.getMeasuredHeight());
        return strokeTextView;
    }

    public void clearFocus() {
        super.clearFocus();
    }

    public void setVisible(boolean z) {
        super.setVisible(z);
    }

    public boolean clear() {
        this.mItemIndex = -1;
        Iterator<String> it = this.poiFilters.iterator();
        while (it.hasNext()) {
            this.mMapView.a(it.next());
        }
        this.poiFilters.clear();
        return super.clear();
    }

    public void setFocus(int i, boolean z) {
        if (getLastFocusedIndex() != i) {
            setVisible(true);
            super.setFocus(i, z);
        }
    }

    public void setFocus(PointOverlayItem pointOverlayItem, boolean z) {
        if (getLastFocusedIndex() != getItemIndex(pointOverlayItem)) {
            super.setFocus(pointOverlayItem, z);
        }
    }
}
