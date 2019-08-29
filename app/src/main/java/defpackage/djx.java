package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.drive.trafficboard.page.TrafficBoardPage;

@Router({"jamrank"})
/* renamed from: djx reason: default package */
/* compiled from: JamRankRouter */
public class djx extends esk {
    public boolean start(ese ese) {
        if (TextUtils.equals("home", ese.a.getPathSegments().get(0))) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage(TrafficBoardPage.class, (PageBundle) null);
                return true;
            }
        }
        return false;
    }
}
