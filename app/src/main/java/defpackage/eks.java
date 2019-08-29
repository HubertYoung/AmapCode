package defpackage;

import android.view.View;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;

/* renamed from: eks reason: default package */
/* compiled from: PoiDetailUtil */
public final class eks {
    public static PageBundle a(POI poi, ely<?> ely) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("poi_detail_page_type", 0);
        pageBundle.putObject("tip_view", ely);
        pageBundle.putObject("POI", poi);
        return pageBundle;
    }

    public static PageBundle a(POI poi, View view) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("poi_detail_page_type", 0);
        pageBundle.putObject("tip_view", view);
        pageBundle.putObject("POI", poi);
        return pageBundle;
    }
}
