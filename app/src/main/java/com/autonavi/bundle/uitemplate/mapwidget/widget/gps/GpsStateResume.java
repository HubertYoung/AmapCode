package com.autonavi.bundle.uitemplate.mapwidget.widget.gps;

import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;

public class GpsStateResume implements IGpsStateResume {
    private boolean isNeedResumeGpsState;
    private int mGpsState = -1;

    public void resumeGpsState() {
        cdz gpsManager = getGpsManager();
        if (gpsManager != null) {
            int b = gpsManager.b();
            if (this.isNeedResumeGpsState && this.mGpsState != b) {
                this.isNeedResumeGpsState = false;
                if (6 == this.mGpsState) {
                    gpsManager.d();
                } else {
                    gpsManager.b(this.mGpsState);
                    gpsManager.h().a();
                }
            }
        }
    }

    public void restoreGpsState() {
        cdz gpsManager = getGpsManager();
        if (gpsManager != null) {
            this.mGpsState = gpsManager.b();
            this.isNeedResumeGpsState = true;
        }
    }

    public void cancelResumeGpsState() {
        this.isNeedResumeGpsState = false;
    }

    private cdz getGpsManager() {
        if (DoNotUseTool.getSuspendManager() != null) {
            return DoNotUseTool.getSuspendManager().d();
        }
        return null;
    }
}
