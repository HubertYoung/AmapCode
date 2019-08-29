package com.amap.bundle.drive.guide;

import android.view.View;
import com.amap.bundle.drive.api.IMotorGuideManager;
import com.autonavi.bundle.routecommon.inter.IRouteUI;

public class MotorGuideManagerImpl implements IMotorGuideManager {
    private View a;

    public boolean isGuideShowing() {
        return this.a != null && this.a.getVisibility() == 0;
    }

    public synchronized void checkShowGuide(IRouteUI iRouteUI, boolean z) {
    }
}
