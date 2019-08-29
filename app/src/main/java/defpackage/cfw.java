package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import java.util.List;

@Router({"wallet"})
/* renamed from: cfw reason: default package */
/* compiled from: WalletRouter */
public class cfw extends esk {
    private static boolean a() {
        cpq cpq = (cpq) ank.a(cpq.class);
        if (cpq == null) {
            return false;
        }
        cpq.a();
        return true;
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() <= 0 || !TextUtils.equals("home", pathSegments.get(0)) || !a()) {
            return "Mine".equalsIgnoreCase(uri.getQueryParameter("featureName")) && "Fortune".equals(uri.getQueryParameter("page")) && "Wallet".equalsIgnoreCase(uri.getQueryParameter(RouteItem.ITEM_TAG)) && a();
        }
        return true;
    }
}
