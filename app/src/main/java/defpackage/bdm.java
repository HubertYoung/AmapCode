package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.AbstractBaseMapPage;
import com.autonavi.map.fragmentcontainer.page.utils.AMapPageUtil;
import com.autonavi.minimap.basemap.traffic.page.TrafficMainMapPage;

@BundleInterface(bdl.class)
/* renamed from: bdm reason: default package */
/* compiled from: TrafficEventExporter */
public class bdm implements bdl {
    static final String a = "bdm";
    private PageBundle b;

    public bdm() {
        if (this.b == null) {
            this.b = new PageBundle();
            this.b.putBoolean(AbstractBaseMapPage.PAGE_EXTENDS_LAYER, true);
        }
    }

    public final void a() {
        csn.a().b();
    }

    public final boolean a(bid bid) {
        return bid instanceof TrafficMainMapPage;
    }

    public final void a(bid bid, a aVar) {
        this.b.putObject("key_traffic_args", aVar);
        apr apr = (apr) a.a.a(apr.class);
        boolean z = false;
        boolean z2 = apr != null && apr.a(bid);
        bci bci = (bci) a.a.a(bci.class);
        boolean z3 = bci != null && bci.a(bid);
        bid bid2 = null;
        if (AMapPageUtil.getPageContextStacks().size() > 1) {
            bid2 = AMapPageUtil.getPageContextStacks().get(AMapPageUtil.getPageContextStacks().size() - 2);
            if (bid2 != null && apr != null && apr.a(bid2) && z3) {
                z = true;
            }
        }
        if (z2) {
            bid.startPageForResult(TrafficMainMapPage.class, this.b, 1000);
        } else if (z) {
            bid2.startPageForResult(TrafficMainMapPage.class, this.b, 1000);
        } else {
            bid.startPage(TrafficMainMapPage.class, this.b);
        }
    }
}
