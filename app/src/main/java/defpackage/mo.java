package defpackage;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import com.amap.bundle.aosservice.request.AosRequest;
import com.amap.bundle.drive.navi.drivenavi.normal.page.AjxRouteCarNaviPage;
import com.amap.bundle.drive.navi.naviwrapper.NaviManager;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.Callback;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import java.util.ArrayList;

@BundleInterface(ms.class)
/* renamed from: mo reason: default package */
/* compiled from: DriveNaviService */
public class mo extends esi implements ms {
    public final void a(Activity activity, String str, b bVar) {
        nm.a(activity, str, bVar);
    }

    public final void a() {
        AbstractBaseMapPage abstractBaseMapPage;
        if (NaviManager.a().f()) {
            bid pageContext = AMapPageUtil.getPageContext();
            if (pageContext != null) {
                if (!(pageContext instanceof AbstractBaseMapPage)) {
                    ArrayList<akc> pagesStacks = AMapPageUtil.getPagesStacks();
                    if (pagesStacks != null) {
                        int size = pagesStacks.size() - 2;
                        if (size >= 0) {
                            pageContext = AMapPageUtil.getStackFragment(size);
                        }
                    }
                }
                if (pageContext != null && (pageContext instanceof AbstractBaseMapPage)) {
                    abstractBaseMapPage = (AbstractBaseMapPage) pageContext;
                    if (abstractBaseMapPage != null && (abstractBaseMapPage instanceof AjxRouteCarNaviPage)) {
                        abstractBaseMapPage.finish();
                        return;
                    }
                }
            }
            abstractBaseMapPage = null;
            if (abstractBaseMapPage != null) {
            }
        }
    }

    public final AosRequest a(Callback<tc> callback, ta... taVarArr) {
        return om.a(0, callback, taVarArr);
    }

    public final CharSequence a(@NonNull Context context, int i, int i2) {
        return pw.a(context, i, i2);
    }

    public final void b() {
        ro.b();
    }

    public final void a(boolean z) {
        NaviManager.a().j = z;
    }

    public final void a(Activity activity, POI poi, ArrayList<POI> arrayList, POI poi2, int i, int i2, int i3, String str) {
        PageBundle pageBundle = new PageBundle();
        nm.a(pageBundle, i, i2, poi, arrayList, poi2, false);
        pageBundle.putString("voiceRequestRouteMethod", str);
        nm.a(activity, pageBundle, arrayList, poi2, null, i3);
    }
}
