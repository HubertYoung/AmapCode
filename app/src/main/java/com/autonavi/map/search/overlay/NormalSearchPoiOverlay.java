package com.autonavi.map.search.overlay;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.autonavi.bundle.entity.common.searchpoi.DynamicRenderData;
import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.map.search.view.PoiFocusScenicView;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.Marker;
import com.autonavi.minimap.search.templete.type.PoiLayoutTemplate;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"SE_NO_SERIALVERSIONID"})
public class NormalSearchPoiOverlay extends SearchPoiOverlay {
    private int mLastFocusIndex = -1;

    public NormalSearchPoiOverlay(bty bty) {
        super(bty);
    }

    public void setLastFocusIndex(int i) {
        this.mLastFocusIndex = i;
    }

    public void clearLastFocusIndex() {
        this.mLastFocusIndex = -1;
    }

    public void clearFocus() {
        super.clearFocus();
        clearLastFocusIndex();
    }

    public int getLastFocusIndex() {
        return this.mLastFocusIndex;
    }

    /* access modifiers changed from: protected */
    public Marker getDefaultMarker(int i, DynamicRenderData dynamicRenderData) {
        if (dynamicRenderData != null && dynamicRenderData.subStyle > 0 && dynamicRenderData.mainStyle > 0) {
            StringBuilder sb = new StringBuilder("b_poi_");
            sb.append(dynamicRenderData.mainStyle);
            sb.append("_");
            sb.append(dynamicRenderData.subStyle);
            int a = bby.a(sb.toString(), this.mContext);
            Bitmap a2 = a > 0 ? bby.a(this.mContext.getResources().getDrawable(a)) : null;
            if (a2 == null) {
                a2 = bby.a(this.mContext.getResources().getDrawable(bby.a("b_poi_geo_hl", this.mContext)));
            }
            if (a2 != null) {
                return createMarker(i, a2, 4, false);
            }
            return null;
        } else if (!showIconBgMarker(this.mSearchPoi) || !this.mSearchPoi.getIconSrcName().equals(PoiLayoutTemplate.BUSDADZHAN_BG)) {
            return createMarker(R.drawable.b_poi_geo_hl, 4);
        } else {
            return createMarker(R.drawable.poi_4busdadzhan, 4);
        }
    }

    /* access modifiers changed from: protected */
    public Marker getFocusMarkerByIndexAndPoi(int i, SearchPoi searchPoi) {
        View view;
        int i2 = i + 1000;
        DynamicRenderData iDynamicRenderInfo = searchPoi.getIDynamicRenderInfo();
        if (iDynamicRenderInfo != null && iDynamicRenderInfo.subStyle > 0 && iDynamicRenderInfo.mainStyle > 0) {
            StringBuilder sb = new StringBuilder("b_poi_");
            sb.append(iDynamicRenderInfo.mainStyle);
            sb.append("_");
            sb.append(iDynamicRenderInfo.subStyle);
            sb.append("_hl");
            int a = bby.a(sb.toString(), this.mContext);
            if (a > 0) {
                if (cch.a(searchPoi)) {
                    PoiFocusScenicView poiFocusScenicView = new PoiFocusScenicView(this.mContext);
                    poiFocusScenicView.setTitle(searchPoi.getName());
                    poiFocusScenicView.setDesc(searchPoi.getSketchDuration());
                    view = poiFocusScenicView;
                } else {
                    View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.poi_focus_view, null);
                    ((ImageView) inflate.findViewById(R.id.poi_icon)).setImageResource(a);
                    view = inflate;
                }
                return createMarker(i2, view, 9, 0.5f, 0.87f, false);
            }
            return createMarker(i2, bby.a(this.mContext.getResources().getDrawable(R.drawable.b_poi_hl)), 9, 0.5f, 0.87f, false);
        } else if (!showIconBgMarker(this.mSearchPoi) || !this.mSearchPoi.getIconSrcName().equals(PoiLayoutTemplate.BUSDADZHAN_BG)) {
            return createMarker(i2, bby.a(this.mContext.getResources().getDrawable(R.drawable.b_poi_hl)), 9, 0.5f, 0.87f, false);
        } else {
            return createMarker(i2, bby.a(this.mContext.getResources().getDrawable(R.drawable.poi_4busdadzhan_selected)), 9, 0.5f, 0.87f, false);
        }
    }
}
