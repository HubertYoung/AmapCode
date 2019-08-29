package defpackage;

import android.support.annotation.NonNull;
import com.autonavi.common.PageBundle;

/* renamed from: arr reason: default package */
/* compiled from: MainMapHelper */
public final class arr {
    public static void a(@NonNull bid bid) {
        PageBundle pageBundle = new PageBundle();
        pageBundle.putInt("SWITCH_CITY_FOR", 1);
        bid.startPage((String) "amap.basemap.action.switch_city_node_page", pageBundle);
    }
}
