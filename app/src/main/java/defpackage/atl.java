package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.basemap.route.page.CarLicenseScanPage;

@BundleInterface(ato.class)
/* renamed from: atl reason: default package */
/* compiled from: CarownerService */
public class atl extends esi implements ato {
    public final void a(PageBundle pageBundle) {
        bid pageContext = AMapPageUtil.getPageContext();
        if (pageContext != null) {
            pageContext.startPageForResult(CarLicenseScanPage.class, pageBundle, 1002);
        }
    }

    public final atm a() {
        return bsl.a();
    }

    public final atn b() {
        return bsm.e();
    }
}
