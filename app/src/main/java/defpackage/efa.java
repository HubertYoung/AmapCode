package defpackage;

import com.amap.bundle.logs.AMapLog;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.run.page.RunRecommendPage;
import com.autonavi.minimap.widget.ConfirmDlgLifeCircle;

@Router({"runRecommend"})
/* renamed from: efa reason: default package */
/* compiled from: RunRecommendRouter */
public class efa extends esk {
    public boolean start(ese ese) {
        try {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage((String) "amap.basemap.action.default_page", (PageBundle) null);
            }
            ConfirmDlgLifeCircle.removeAll();
        } catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            AMapLog.e("HealthyRideRouter", sb.toString());
        }
        startPage(RunRecommendPage.class, (PageBundle) null);
        return true;
    }
}
