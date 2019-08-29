package com.autonavi.minimap.route.foot.overlay;

import com.autonavi.jni.ae.gmap.gloverlay.GLNaviOverlay;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.AbstractNaviOverlay;

public class FootWheelOverlay extends AbstractNaviOverlay {
    private final int dist = ebg.a();

    public FootWheelOverlay(bty bty) {
        super(bty);
    }

    public void resumeMarker() {
        createMarker(R.drawable.compass_circle, 4);
        createMarker(R.drawable.compass_east, 4);
        createMarker(R.drawable.compass_south, 4);
        createMarker(R.drawable.compass_north, 4);
        createMarker(R.drawable.compass_west, 4);
        showWheel();
    }

    public void updateWheel(int i, int i2, int i3) {
        ((GLNaviOverlay) this.mGLOverlay).setCarPosition(i, i2, i3);
    }

    public void hideWheel() {
        ((GLNaviOverlay) this.mGLOverlay).SetNaviTexture(-1, -1, -1, -1, -1);
        ((GLNaviOverlay) this.mGLOverlay).SetCompassMarkerTextures(-1, -1, -1, -1, this.dist);
    }

    public void showWheel() {
        ((GLNaviOverlay) this.mGLOverlay).SetNaviTexture(-1, -1, -1, -1, R.drawable.compass_circle);
        ((GLNaviOverlay) this.mGLOverlay).SetCompassMarkerTextures(R.drawable.compass_east, R.drawable.compass_south, R.drawable.compass_west, R.drawable.compass_north, this.dist);
    }
}
