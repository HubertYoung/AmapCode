package defpackage;

import android.net.Uri;
import android.os.Environment;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.route.foot.page.RouteFootVoiceDebugPage;
import java.io.File;
import java.util.List;

@Router({"footResult"})
/* renamed from: eby reason: default package */
/* compiled from: FootResultRouter */
public class eby extends esk {
    public boolean start(ese ese) {
        Uri uri = ese.a;
        if (uri == null || !"footResult".equalsIgnoreCase(uri.getHost())) {
            return false;
        }
        List<String> pathSegments = uri.getPathSegments();
        if (pathSegments == null || pathSegments.size() == 0 || !"voicedebug".equalsIgnoreCase(pathSegments.get(0))) {
            return false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory());
        sb.append("/testvoicesimtrue");
        if (new File(sb.toString()).exists()) {
            AMapPageUtil.getPageContext().startPage(RouteFootVoiceDebugPage.class, (PageBundle) null);
        }
        return true;
    }
}
