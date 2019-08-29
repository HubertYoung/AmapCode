package defpackage;

import com.autonavi.ae.gmap.gloverlay.BaseMapOverlay;
import com.autonavi.jni.ae.gmap.gloverlay.GLOverlayBundle;

/* renamed from: btm reason: default package */
/* compiled from: GLOverlayBundle */
public final class btm {
    private GLOverlayBundle a;
    private int b;

    public btm(int i, GLOverlayBundle gLOverlayBundle) {
        this.b = i;
        this.a = gLOverlayBundle;
    }

    public final int a() {
        if (this.a != null) {
            return this.a.getOverlayCount();
        }
        return 0;
    }

    public final boolean a(BaseMapOverlay baseMapOverlay) {
        if (this.a != null) {
            return this.a.cotainsOverlay(baseMapOverlay);
        }
        return false;
    }

    public final BaseMapOverlay a(int i) {
        if (this.a != null) {
            return this.a.getOverlay(i);
        }
        return null;
    }

    public final void b(BaseMapOverlay baseMapOverlay) {
        if (this.a != null) {
            this.a.addOverlay(baseMapOverlay);
        }
    }

    public final void c(BaseMapOverlay baseMapOverlay) {
        if (this.a != null) {
            this.a.removeOverlay(baseMapOverlay);
        }
    }

    public final amc b(int i) {
        if (this.a != null) {
            return this.a.getOverlayTextureItem(i);
        }
        return null;
    }
}
