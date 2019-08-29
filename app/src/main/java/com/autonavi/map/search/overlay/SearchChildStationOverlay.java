package com.autonavi.map.search.overlay;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.common.model.POI;
import com.autonavi.map.core.MapViewLayoutParams;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import com.autonavi.minimap.search.model.searchpoi.searchpoitype.ChildStationPoiData;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuppressFBWarnings({"SE_NO_SERIALVERSIONID"})
public class SearchChildStationOverlay extends PointOverlay<bbr> {
    public int mFocusedPoiIndex = -1;
    private int mItemIndex = -1;
    private List<String> mPoiFilters = new ArrayList();

    public SearchChildStationOverlay(bty bty) {
        super(bty);
        setClearWhenLoseFocus(true);
        setAnimatorType(2);
        setMoveToFocus(false);
    }

    public void addStation(POI poi, int i, int i2) {
        if (poi != null) {
            this.mFocusedPoiIndex = i;
            SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
            Collection<? extends POI> collection = null;
            if (searchPoi.getPoiChildrenInfo() != null) {
                collection = searchPoi.getPoiChildrenInfo().stationList;
            }
            if (collection != null && !collection.isEmpty()) {
                for (POI createChildOverlayItem : collection) {
                    bbr createChildOverlayItem2 = createChildOverlayItem(createChildOverlayItem);
                    addItem(createChildOverlayItem2);
                    setPointItemVisble((PointOverlayItem) createChildOverlayItem2, false, false);
                }
            }
            setFocus(i2, false);
        }
    }

    public void addStation(List<POI> list) {
        if (list != null) {
            for (POI createChildOverlayItem : list) {
                bbr createChildOverlayItem2 = createChildOverlayItem(createChildOverlayItem);
                addItem(createChildOverlayItem2);
                setPointItemVisble((PointOverlayItem) createChildOverlayItem2, false, false);
            }
        }
    }

    private bbr createChildOverlayItem(POI poi) {
        if (poi == null) {
            return null;
        }
        bbr bbr = new bbr(poi);
        SearchPoi searchPoi = (SearchPoi) poi.as(SearchPoi.class);
        if (searchPoi != null && searchPoi.getIDynamicRenderInfo() != null && searchPoi.getIDynamicRenderInfo().bFlag) {
            return bbr;
        }
        bbr.mDefaultMarker = createMarker(R.drawable.station_icon, 4);
        Bitmap decodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.b_poi_hl);
        int i = this.mItemIndex + 1;
        this.mItemIndex = i;
        bbr.mBubbleMarker = createMarker(i, decodeResource, 9, 0.5f, 0.89f, false);
        if (bbr.mBubbleMarker != null) {
            setBubbleAnimator(2);
        }
        MapViewLayoutParams mapViewLayoutParams = new MapViewLayoutParams(-2, -2, bbr.getGeoPoint(), 3);
        mapViewLayoutParams.mode = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(((ChildStationPoiData) poi.as(ChildStationPoiData.class)).getBusinfoAlias());
        sb.append(this.mContext.getString(R.string.station));
        TextView createStationNameView = createStationNameView(sb.toString());
        this.mMapView.a((View) createStationNameView, (LayoutParams) mapViewLayoutParams);
        int i2 = this.mItemIndex + 1;
        this.mItemIndex = i2;
        bbr.mBgMarker = createMarker(i2, (View) createStationNameView, 7, 0.0f, 0.0f, true);
        this.mMapView.a((View) createStationNameView);
        this.mMapView.a(poi.getPoint().x, poi.getPoint().y, 0, (float) createStationNameView.getMeasuredWidth(), (float) createStationNameView.getMeasuredHeight(), poi.getId());
        this.mPoiFilters.add(poi.getId());
        return bbr;
    }

    private TextView createStationNameView(String str) {
        TextView textView = new TextView(this.mContext);
        textView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        textView.setBackgroundResource(R.drawable.station_text);
        textView.setGravity(16);
        textView.setTextColor(this.mContext.getResources().getColor(R.color.f_c_6));
        textView.setTextSize(1, 9.0f);
        textView.setText(str);
        textView.setSingleLine();
        textView.setPadding(agn.a(this.mContext, 8.0f), 0, agn.a(this.mContext, 5.0f), 0);
        textView.measure(0, 0);
        textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
        return textView;
    }

    public boolean clear() {
        for (String a : this.mPoiFilters) {
            this.mMapView.a(a);
        }
        this.mPoiFilters.clear();
        this.mItemIndex = -1;
        return super.clear();
    }

    public void setFocus(int i, boolean z) {
        super.setFocus(i, z);
    }

    public void setVisible(boolean z) {
        super.setVisible(z);
    }
}
