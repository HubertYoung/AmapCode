package defpackage;

import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;
import com.autonavi.minimap.life.sketchscenic.layer.ScenicGuidePoi;

/* renamed from: dre reason: default package */
/* compiled from: ScenicPlayPointOverlayItem */
public final class dre extends PointOverlayItem {
    final ScenicGuidePoi a;

    dre(ScenicGuidePoi scenicGuidePoi) {
        this.a = scenicGuidePoi;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((dre) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
