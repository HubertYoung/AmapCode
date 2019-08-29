package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.basemap.favorites.data.RouteItem;
import java.util.List;

@Router({"measure"})
/* renamed from: cgx reason: default package */
/* compiled from: MeasureRouter */
public class cgx extends esk {
    private static boolean a() {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext == null) {
            return false;
        }
        pageContext.startPage((String) "amap.basemap.action.measure_page", (PageBundle) null);
        return true;
    }

    public boolean start(ese ese) {
        Uri uri = ese.a;
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments.size() > 0 && TextUtils.equals("home", pathSegments.get(0)) && a()) {
            return true;
        }
        String queryParameter = uri.getQueryParameter("featureName");
        if (!"measure".equalsIgnoreCase(queryParameter)) {
            return "Mine".equalsIgnoreCase(queryParameter) && "ToolBox".equals(uri.getQueryParameter("page")) && "Measure".equalsIgnoreCase(uri.getQueryParameter(RouteItem.ITEM_TAG)) && a();
        }
        if (a()) {
            return true;
        }
    }
}
