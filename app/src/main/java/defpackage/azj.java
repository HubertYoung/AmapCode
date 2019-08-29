package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.bundle.routecommute.common.CommuteHelper;

@Router({"routecommute"})
/* renamed from: azj reason: default package */
/* compiled from: RouteCommuteRouter */
public class azj extends esk {
    public boolean start(ese ese) {
        Uri uri = ese.a;
        azb.a(CommuteHelper.a, "scheme enter uri = ".concat(String.valueOf(uri)));
        if (uri == null || uri.getHost() == null || !TextUtils.equals(uri.getHost(), "routecommute")) {
            return false;
        }
        String queryParameter = uri.getQueryParameter("type");
        String queryParameter2 = uri.getQueryParameter("dest");
        String queryParameter3 = uri.getQueryParameter("from");
        if (TextUtils.isEmpty(queryParameter3)) {
            queryParameter3 = "1";
        }
        axv axv = (axv) a.a.a(axv.class);
        if (axv != null) {
            axv.a(queryParameter, queryParameter2, queryParameter3);
        }
        return true;
    }
}
