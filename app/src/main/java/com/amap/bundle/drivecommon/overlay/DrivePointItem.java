package com.amap.bundle.drivecommon.overlay;

import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointOverlay;
import com.autonavi.common.model.GeoPoint;

public abstract class DrivePointItem<E extends DriveBaseBoardPointOverlay> extends DriveBaseBoardPointItem<E> {
    protected static int g;

    public enum Style {
        NONE,
        DAY,
        NIGHT
    }

    public DrivePointItem(GeoPoint geoPoint) {
        super(geoPoint);
    }
}
