package com.autonavi.jni.route.health;

import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.LineOverlayItem;

public class HelLineOverlay extends LineOverlay {
    public HelLineOverlay(bty bty) {
        super(bty);
    }

    public void drawPathSegments(PathLineParser pathLineParser) {
        PathLineSegment[] segments = pathLineParser.getSegments();
        for (int i = 0; i < segments.length; i++) {
            if (segments[i] != null) {
                LineOverlayItem lineOverlayItem = new LineOverlayItem(1, segments[i].getGeoPointArray(), agn.a(this.mContext, 3.0f));
                lineOverlayItem.setIsColorGradient(true);
                lineOverlayItem.setFillLineId(R.drawable.map_frontlr);
                lineOverlayItem.setMatchColors(segments[i].getColorArray());
                lineOverlayItem.setBorderLineWidth(agn.a(this.mContext, 4.0f));
                lineOverlayItem.setBackgrondId(R.drawable.map_lr);
                lineOverlayItem.setBackgroundColor(-1);
                addItem(lineOverlayItem);
            }
        }
    }
}
