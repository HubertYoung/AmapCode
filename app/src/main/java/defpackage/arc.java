package defpackage;

import android.view.View;
import android.view.ViewGroup;
import com.amap.bundle.mapstorage.MapSharePreference;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.core.MemoryStorageRef;
import java.util.ArrayList;
import java.util.List;

/* renamed from: arc reason: default package */
/* compiled from: MapHomeGuideManager */
public final class arc {
    public aro a;
    public View b;
    public ViewGroup c;
    public MapSharePreference d;
    asg e;
    List<asg> f = new ArrayList();
    boolean g = true;
    ard h;
    MemoryStorageRef i;

    public arc(aro aro) {
        this.a = aro;
        this.d = new MapSharePreference((String) "basemap");
        this.i = Ajx.getInstance().getMemoryStorage("main_map");
    }

    public final void a() {
        for (asg next : this.f) {
            if (next.b != null) {
                ViewGroup viewGroup = (ViewGroup) next.b.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(next.b);
                    next.a();
                }
            }
        }
        this.f.clear();
    }
}
