package com.autonavi.minimap.route.bus.extbus.overlay;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlay;
import com.autonavi.minimap.base.overlay.LineOverlayItem;

public class ExtBusLineOverlay extends LineOverlay {
    public void onPause() {
    }

    public void onResume() {
    }

    public ExtBusLineOverlay(bty bty) {
        super(bty);
    }

    public int createAndAddArrowLineItem(int[] iArr, int[] iArr2, int i) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            geoPointArr[i2] = new GeoPoint(iArr[i2], iArr2[i2]);
        }
        LineOverlayItem lineOverlayItem = new LineOverlayItem(4, geoPointArr, i);
        lineOverlayItem.setFillLineId(R.drawable.map_aolr);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createAndAddLinkPathItem(int[] iArr, int[] iArr2) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            geoPointArr[i] = new GeoPoint(iArr[i], iArr2[i]);
        }
        LineOverlayItem lineOverlayItem = new LineOverlayItem(4, geoPointArr, agn.a(this.mContext, 4.0f));
        lineOverlayItem.setFillLineId(R.drawable.map_link_dott);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createColorLineItem(int[] iArr, int[] iArr2, int i) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i2 = 0; i2 < iArr.length; i2++) {
            geoPointArr[i2] = new GeoPoint(iArr[i2], iArr2[i2]);
        }
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, 4.0f));
        int i3 = -4276546;
        switch (i) {
            case -3:
            case -2:
            case -1:
                i3 = -1;
                break;
            case 1:
                i3 = -16711936;
                break;
            case 2:
                i3 = InputDeviceCompat.SOURCE_ANY;
                break;
            case 3:
                i3 = SupportMenu.CATEGORY_MASK;
                break;
            case 4:
                i3 = -2354116;
                break;
        }
        lineOverlayItem.setFillLineColor(i3);
        lineOverlayItem.setFillLineId(getLineStateMarkerId(i));
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    public int createLineItem(int[] iArr, int[] iArr2) {
        GeoPoint[] geoPointArr = new GeoPoint[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            geoPointArr[i] = new GeoPoint(iArr[i], iArr2[i]);
        }
        LineOverlayItem lineOverlayItem = new LineOverlayItem(1, geoPointArr, agn.a(this.mContext, 3.0f));
        lineOverlayItem.setFillLineId(R.drawable.map_lr_bus);
        lineOverlayItem.setBackgroundColor(-14449188);
        lineOverlayItem.setBackgrondId(R.drawable.map_lr);
        addItem(lineOverlayItem);
        return getSize() - 1;
    }

    private int getLineStateMarkerId(int i) {
        switch (i) {
            case 0:
                return R.drawable.map_lr_bus;
            case 1:
                return R.drawable.map_lr_green;
            case 2:
                return R.drawable.map_lr_slow;
            case 3:
                return R.drawable.map_lr_bad;
            case 4:
                return R.drawable.map_lr_darkred;
            case 5:
                return R.drawable.map_traffic_platenum_restrict_hl;
            default:
                return R.drawable.map_lr_nodata;
        }
    }
}
