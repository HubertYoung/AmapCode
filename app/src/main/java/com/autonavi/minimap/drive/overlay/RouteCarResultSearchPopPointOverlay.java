package com.autonavi.minimap.drive.overlay;

import com.amap.bundle.drivecommon.overlay.DriveBaseBoardPointOverlay;

public class RouteCarResultSearchPopPointOverlay extends DriveBaseBoardPointOverlay {
    public int getCollideType() {
        return 6;
    }

    public boolean isCalcingOverlay() {
        return false;
    }

    public RouteCarResultSearchPopPointOverlay(bty bty) {
        super(bty);
    }

    public void onUpdateDirection(int i, int i2) {
        boolean z = false;
        boolean z2 = i2 != -1;
        if (i2 != -1) {
            z = true;
        }
        setPointItemVisble(i, z2, z);
    }
}
