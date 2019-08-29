package defpackage;

import android.content.Context;
import com.autonavi.common.model.GeoPoint;
import com.autonavi.map.delegate.BaseOverlayDelegate;
import com.autonavi.minimap.R;
import com.autonavi.minimap.base.overlay.LineOverlayItem;
import java.util.ArrayList;
import java.util.List;

/* renamed from: dta reason: default package */
/* compiled from: OverLayStyle */
public abstract class dta {
    public static final dta a = new dta() {
        public final List<LineOverlayItem> a(Context context, GeoPoint[] geoPointArr) {
            ArrayList arrayList = new ArrayList();
            LineOverlayItem lineOverlayItem = new LineOverlayItem(3, geoPointArr, agn.a(context, 3.0f));
            lineOverlayItem.setFillLineColor(-4276546);
            lineOverlayItem.setFillLineId(R.drawable.map_alr_night);
            arrayList.add(lineOverlayItem);
            return arrayList;
        }
    };

    public List<LineOverlayItem> a(Context context, GeoPoint[] geoPointArr) {
        return null;
    }

    public List<BaseOverlayDelegate> a(bid bid, bty bty) {
        return null;
    }

    public boolean a() {
        return true;
    }

    public boolean b() {
        return true;
    }

    public boolean b(bid bid, bty bty) {
        return false;
    }
}
