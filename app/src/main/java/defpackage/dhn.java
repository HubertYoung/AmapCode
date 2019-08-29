package defpackage;

import android.graphics.Rect;
import android.support.annotation.Nullable;
import com.autonavi.common.model.POI;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.PointOverlay;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dhn reason: default package */
/* compiled from: RouteCarResultMidPointItem */
public final class dhn extends dhp {
    public int h;
    private int k;

    private dhn(POI poi, int i, int i2) {
        super(poi);
        this.h = i;
        this.k = i2;
    }

    @Nullable
    public static List<dhn> a(List<POI> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = size - 1; i >= 0; i--) {
            POI poi = list.get(i);
            if (poi != null) {
                arrayList.add(new dhn(poi, i, size));
            }
        }
        return arrayList;
    }

    public final void onPrepareAddItem(PointOverlay pointOverlay) {
        int i;
        super.onPrepareAddItem(pointOverlay);
        if (this.k != 1) {
            switch (this.h) {
                case 0:
                    i = R.drawable.bubble_midd1;
                    break;
                case 1:
                    i = R.drawable.bubble_midd2;
                    break;
                case 2:
                    i = R.drawable.bubble_midd3;
                    break;
                case 3:
                    i = R.drawable.bubble_midd4;
                    break;
                case 4:
                    i = R.drawable.bubble_midd5;
                    break;
            }
        }
        i = R.drawable.bubble_midd;
        this.mDefaultMarker = pointOverlay.createMarker(i, 5);
    }

    public final Rect[] a() {
        return new Rect[]{b()};
    }
}
