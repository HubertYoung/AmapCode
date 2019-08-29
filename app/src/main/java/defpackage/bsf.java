package defpackage;

import android.view.View.OnClickListener;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import java.util.Comparator;

/* renamed from: bsf reason: default package */
/* compiled from: OpenLayerPresenter */
public final class bsf {
    public OnClickListener a;
    private cen b;
    private MapSharePreference c;
    private Comparator<LayerItem> d = new Comparator<LayerItem>() {
        public final /* synthetic */ int compare(Object obj, Object obj2) {
            return a((LayerItem) obj, (LayerItem) obj2);
        }

        private static int a(LayerItem layerItem, LayerItem layerItem2) {
            try {
                int order = layerItem.getOrder();
                int order2 = layerItem2.getOrder();
                if (order < order2) {
                    return -1;
                }
                return order > order2 ? 1 : 0;
            } catch (NumberFormatException unused) {
                return -1;
            }
        }
    };

    public bsf(cen cen) {
        this.b = cen;
        this.c = new MapSharePreference((String) "MapLayer");
    }
}
