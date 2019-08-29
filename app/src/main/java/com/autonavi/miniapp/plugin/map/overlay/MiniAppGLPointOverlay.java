package com.autonavi.miniapp.plugin.map.overlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLPointOverlay;

public class MiniAppGLPointOverlay extends GLPointOverlay {
    public MiniAppGLPointOverlay(int i, akq akq, int i2) {
        super(i, akq, i2);
    }

    public void updatePointParam(long j, long j2, long j3) {
        nativeUpdatePointParam(j, j2, j3);
    }
}
