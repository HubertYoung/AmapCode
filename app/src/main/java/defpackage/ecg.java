package defpackage;

import com.amap.bundle.drivecommon.tools.DriveUtil;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.footresult.api.IFootResultPage;
import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.PageBundle;
import com.autonavi.common.model.POI;

@BundleInterface(avl.class)
/* renamed from: ecg reason: default package */
/* compiled from: FootResultService */
public class ecg extends esi implements avl {
    public final void a(cor cor) {
        acg acg = (acg) a.a.a(acg.class);
        if (acg != null) {
            POI a = cor.a();
            POI b = cor.b();
            PageBundle pageBundle = new PageBundle();
            pageBundle.putObject("bundle_key_route_type", RouteType.ONFOOT);
            pageBundle.putObject("bundle_key_poi_start", a);
            pageBundle.putObject("bundle_key_poi_end", b);
            pageBundle.putBoolean(DriveUtil.BUNDLE_KEY_BOOL_SAVECOOKIE, false);
            pageBundle.putObject("bundle_key_auto_route", Boolean.TRUE);
            pageBundle.putBoolean("key_favorites", true);
            acg.a(pageBundle);
        }
    }

    public final IFootResultPage a() {
        return a.a;
    }

    public final avm b() {
        return a.a;
    }
}
