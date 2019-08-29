package com.autonavi.map.search.overlay;

import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@SuppressFBWarnings({"SE_NO_SERIALVERSIONID"})
public class SearchCenterOverlay extends PointOverlay<PointOverlayItem> {
    public SearchCenterOverlay(bty bty) {
        super(bty);
        setClickable(false);
    }

    public void addCenterPoint(PointOverlayItem pointOverlayItem) {
        pointOverlayItem.mDefaultMarker = createMarker(R.drawable.b_poi_real, 9, 0.5f, 0.87f);
        addItem(pointOverlayItem);
    }
}
