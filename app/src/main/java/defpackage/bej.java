package defpackage;

import android.content.Context;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;

/* renamed from: bej reason: default package */
/* compiled from: RedesignFloorWidgetService */
public class bej implements bec {
    private bea a = new beh(new beb() {
        public final Context a() {
            return AMapPageUtil.getAppContext();
        }
    });

    public bej() {
        if (this.a instanceof cdf) {
            cdd.n().a((cdf) this.a);
        }
    }

    public final bea a() {
        return this.a;
    }
}
