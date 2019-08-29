package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.bundle.openlayer.entity.LayerItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* renamed from: dqy reason: default package */
/* compiled from: SketchScenicLayerHelper */
final class dqy {
    @NonNull
    private final bty a;

    dqy(@NonNull bty bty) {
        this.a = bty;
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        this.a.r(true);
    }

    /* access modifiers changed from: 0000 */
    public final void b() {
        this.a.r(false);
    }

    private static boolean a(LayerItem layerItem) {
        return 610000 == layerItem.getLayer_id();
    }

    static void a(Map<Integer, String> map) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            ArrayList<LayerItem> i = awo.i();
            if (!i.isEmpty()) {
                Iterator<LayerItem> it = i.iterator();
                while (it.hasNext()) {
                    LayerItem next = it.next();
                    if (next != null && a(next)) {
                        map.put(Integer.valueOf(next.getLayer_id()), next.getData());
                        awo.a(next.getData());
                    }
                }
            }
        }
    }

    static void b(Map<Integer, String> map) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            for (Integer intValue : map.keySet()) {
                awo.a(intValue.intValue());
            }
        }
    }

    static void c(Map<Integer, String> map) {
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            for (Integer intValue : map.keySet()) {
                int intValue2 = intValue.intValue();
                if (intValue2 == 610000) {
                    awo.b(intValue2);
                } else {
                    awo.c(intValue2);
                }
            }
        }
    }
}
