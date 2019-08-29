package defpackage;

import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.List;

@Router({"activities"})
/* renamed from: cti reason: default package */
/* compiled from: ActivitiesRouter */
public class cti extends esk {
    public boolean start(ese ese) {
        boolean z;
        List<String> pathSegments = ese.a.getPathSegments();
        if (pathSegments != null && pathSegments.size() > 0 && TextUtils.equals(pathSegments.get(0), "activityzone")) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                pageContext.startPage((String) "amap.basemap.action.acticities", new PageBundle());
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }
}
