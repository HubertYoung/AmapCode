package defpackage;

import com.autonavi.bundle.entity.common.searchpoi.SearchPoi;
import com.autonavi.jni.eyrie.amap.redesign.maps.overlay.item.PointOverlayItem;

/* renamed from: drh reason: default package */
/* compiled from: SketchScenicPointOverlayItem */
public final class drh extends PointOverlayItem {
    final SearchPoi a;

    drh(SearchPoi searchPoi) {
        this.a = searchPoi;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return this.a.equals(((drh) obj).a);
    }

    public final int hashCode() {
        return this.a.hashCode();
    }
}
