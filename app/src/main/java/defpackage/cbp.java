package defpackage;

import android.text.TextUtils;
import com.autonavi.bundle.searchresult.ajx.ModulePoi;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;

/* renamed from: cbp reason: default package */
/* compiled from: SearchResultCommand */
public final class cbp {
    public static void a(String str, POI poi, String str2) {
        bax bax = (bax) a.a.a(bax.class);
        if (bax != null) {
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject(str, poi);
            pageBundle.putBoolean("bundle_key_auto_route", true);
            if (!TextUtils.isEmpty(str2)) {
                pageBundle.putString("bundle_key_from_page", ModulePoi.TIPS.equals(str2) ? "tips_go" : "searchlist_go");
            }
            bax.a(pageBundle);
        }
    }
}
