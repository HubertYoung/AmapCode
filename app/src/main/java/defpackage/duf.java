package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.bus.busline.page.BusLineSearchPage;

@Router({"busline"})
/* renamed from: duf reason: default package */
/* compiled from: BusLineRouter */
public class duf extends esk {
    public boolean start(ese ese) {
        Uri uri = ese.a;
        if (uri != null) {
            String queryParameter = uri.getQueryParameter("busname");
            String queryParameter2 = uri.getQueryParameter("city");
            PageBundle pageBundle = new PageBundle();
            if (!TextUtils.isEmpty(queryParameter)) {
                pageBundle.putString("busname", queryParameter);
            }
            if (!TextUtils.isEmpty(queryParameter2)) {
                pageBundle.putString("city", queryParameter2);
            }
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage(BusLineSearchPage.class, pageBundle);
            }
        }
        return true;
    }
}
