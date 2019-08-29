package defpackage;

import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.route.run.page.RouteFootRunMapPage;

@Router({"healthyRun"})
/* renamed from: eey reason: default package */
/* compiled from: HealthyRunRouter */
public class eey extends esk {
    public boolean start(ese ese) {
        startPage(RouteFootRunMapPage.class, (PageBundle) null);
        return true;
    }
}
