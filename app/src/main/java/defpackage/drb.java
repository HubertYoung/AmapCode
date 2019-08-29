package defpackage;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi;

/* renamed from: drb reason: default package */
/* compiled from: ScenicGuidePointOverlayItem */
public final class drb extends PointOverlayItem {
    final ScenicGuidePoi a;

    drb(ScenicGuidePoi scenicGuidePoi) {
        this.a = scenicGuidePoi;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((drb) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
