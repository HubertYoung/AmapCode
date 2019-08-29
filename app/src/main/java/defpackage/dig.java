package defpackage;

import android.net.Uri;
import android.text.TextUtils;
import com.autonavi.annotation.Router;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.List;

@Router({"searchSpecPoi"})
/* renamed from: dig reason: default package */
/* compiled from: SearchCallbackRouter */
public class dig extends esk {
    public boolean start(ese ese) {
        if (ese != null) {
            String a = ese.a();
            Uri uri = ese.a;
            if (TextUtils.equals("searchSpecPoi", a) && uri != null) {
                List<String> pathSegments = uri.getPathSegments();
                if (pathSegments != null && pathSegments.size() > 0) {
                    String str = pathSegments.get(0);
                    if (TextUtils.isEmpty(str) || !TextUtils.equals("callbackDest", str)) {
                        return false;
                    }
                    PageBundle pageBundle = new PageBundle();
                    pageBundle.putBoolean("key_is_set_result", true);
                    pageBundle.putInt("search_for", 2);
                    pageBundle.putString("hint", "请输入终点");
                    bid pageContext = AMapPageUtil.getPageContext();
                    if (pageContext != null) {
                        pageContext.startPageForResult((String) "search.fragment.SearchCallbackFragment", pageBundle, 11);
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
