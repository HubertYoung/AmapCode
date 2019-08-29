package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.map.search.tip.indicator.indicator.PoiBottomView;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;

/* renamed from: cbx reason: default package */
/* compiled from: PoiBottomViewProperty */
public final class cbx extends BaseProperty<PoiBottomView> {
    public cbx(@NonNull PoiBottomView poiBottomView, @NonNull IAjxContext iAjxContext) {
        super(poiBottomView, iAjxContext);
    }

    public final void updateAttribute(String str, Object obj) {
        if (!"updateUI".equals(str) || !(obj instanceof String)) {
            super.updateAttribute(str, obj);
        } else {
            ((PoiBottomView) this.mView).updateUI((String) obj);
        }
    }
}
