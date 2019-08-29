package com.autonavi.minimap.route.sharebike.overlay;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import com.autonavi.minimap.base.overlay.PointOverlayItem;

public class ShareBikeIndicatorOverlay extends PointOverlay {
    public ShareBikeIndicatorOverlay(bty bty) {
        super(bty);
    }

    public void drawIndicatorOverlay(GeoPoint geoPoint) {
        if (geoPoint == null) {
            eao.a((String) "Amap#", (String) "Bike# Draw Indicator Error point is null !!");
        }
        clear();
        Context appContext = AMapPageUtil.getAppContext();
        if (appContext != null) {
            ImageView imageView = new ImageView(appContext);
            imageView.setImageDrawable(appContext.getResources().getDrawable(R.drawable.sharebike_location_point));
            imageView.setMaxWidth(agn.a(appContext, 42.0f));
            imageView.setMaxHeight(agn.a(appContext, 65.0f));
            imageView.setScaleType(ScaleType.FIT_CENTER);
            PointOverlayItem pointOverlayItem = new PointOverlayItem(geoPoint);
            pointOverlayItem.mDefaultMarker = createMarker(0, (View) imageView, 5, 0.0f, 0.0f, false);
            addItem(pointOverlayItem);
        }
    }
}
