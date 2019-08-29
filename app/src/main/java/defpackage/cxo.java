package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.basemap.favorites.page.FavoritesPage;

@Router({"favorites"})
/* renamed from: cxo reason: default package */
/* compiled from: FavoritesRouter */
public class cxo extends esk {
    public boolean start(ese ese) {
        if (ese == null || ese.a == null) {
            return false;
        }
        String b = ese.b();
        if (TextUtils.isEmpty(b)) {
            return false;
        }
        char c = 65535;
        if (b.hashCode() == 46613902 && b.equals("/home")) {
            c = 0;
        }
        if (c != 0) {
            return false;
        }
        startPage(FavoritesPage.class, new PageBundle());
        return true;
    }
}
