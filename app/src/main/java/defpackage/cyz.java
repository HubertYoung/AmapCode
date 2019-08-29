package defpackage;

import com.autonavi.bundle.openlayer.entity.LayerItem;
import com.autonavi.map.core.MapManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* renamed from: cyz reason: default package */
/* compiled from: MapLayerUpdateManager */
public final class cyz {
    public MapManager a;
    public boolean b = false;
    private a c = new a() {
        public final void a() {
            cyz.this.a();
        }
    };
    private b d = new b() {
    };

    public cyz(MapManager mapManager) {
        this.a = mapManager;
        awo awo = (awo) a.a.a(awo.class);
        if (awo != null) {
            awo.a(this.c);
            awo.a(this.d);
        }
    }

    public final void a() {
        cde suspendManager = DoNotUseTool.getSuspendManager();
        if (!(suspendManager == null || suspendManager.b() == null || this.a == null || suspendManager.b().getMapLayerDialogCustomActions() == null || suspendManager.b().getMapLayerDialogCustomActions().a != 1 || this.a.getMapView() == null)) {
            awo awo = (awo) a.a.a(awo.class);
            if (awo != null) {
                ArrayList<Integer> j = awo.j();
                Iterator<Integer> it = j.iterator();
                while (it.hasNext()) {
                    int intValue = it.next().intValue();
                    Iterator<LayerItem> it2 = awo.i().iterator();
                    while (it2.hasNext()) {
                        LayerItem next = it2.next();
                        if (next.getLayer_id() == intValue) {
                            awo.a(next.getData());
                        }
                    }
                }
                awo.a((List<Integer>) j);
            }
        }
    }
}
