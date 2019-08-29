package defpackage;

import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.amaphome.page.MapHomePage;
import com.autonavi.bundle.amaphome.page.MapHomeTabPage;
import com.autonavi.bundle.amaphome.page.MineAjx3Page;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager.Stub;
import com.autonavi.common.PageBundle;
import com.autonavi.map.fragmentcontainer.page.IAMapHomePage;
import com.autonavi.minimap.ajx3.Ajx3Path;
import com.autonavi.minimap.bundle.amaphome.ab.page.OldMapHomePage;
import com.autonavi.minimap.bundle.feed.page.FeedAjx3Page;
import com.autonavi.minimap.statusbar.StatusBarManager;
import java.lang.ref.WeakReference;

@BundleInterface(apr.class)
/* renamed from: arg reason: default package */
/* compiled from: MapHomeService */
public class arg extends esi implements apr {
    public final StatusBarManager a() {
        return arh.a();
    }

    public final void b(bid bid, PageBundle pageBundle) {
        if (bid != null) {
            pageBundle.putString("url", Ajx3Path.BASEMAP_MINE);
            new bnv();
            pageBundle.putString("jsData", bnv.b());
            if (a.a.a) {
                pageBundle.putString(MapHomeTabPage.a, bep.a((String) "我的", MineAjx3Page.class));
                bid.startPage(MapHomeTabPage.class, pageBundle);
                return;
            }
            bid.startPage(MineAjx3Page.class, pageBundle);
        }
    }

    public final boolean a(bid bid) {
        if (!a.a.a) {
            return bid instanceof OldMapHomePage;
        }
        if (bid instanceof IAMapHomePage) {
            return ((IAMapHomePage) bid).isMapHomePage();
        }
        return false;
    }

    public final void b(bid bid) {
        a.a.a(bid, null);
    }

    public final void a(bid bid, PageBundle pageBundle) {
        a.a.a(bid, pageBundle);
    }

    public final void a(bui bui) {
        cum cum = a.a;
        Stub.getMapWidgetManager().setABHomePage(cum.a);
        if (bui != null) {
            if (cum.a) {
                new PageBundle().putString(MapHomeTabPage.a, bep.a((String) "首页", MapHomePage.class));
                bui.a(MapHomeTabPage.class, null);
                return;
            }
            bui.a(OldMapHomePage.class, null);
        }
    }

    public final void a(aps aps) {
        aqu aqu = a.a;
        boolean z = true;
        int size = aqu.c.size() - 1;
        while (true) {
            if (size < 0) {
                z = false;
                break;
            }
            aps aps2 = (aps) aqu.c.get(size).get();
            if (aps2 == null) {
                aqu.c.remove(aps2);
            } else if (aps2 == aps) {
                break;
            }
            size--;
        }
        if (!z) {
            aqu.c.add(new WeakReference(aps));
        }
        aqu.c();
    }

    public final void c(bid bid, PageBundle pageBundle) {
        if (bid != null) {
            if (a.a.a) {
                pageBundle.putString(MapHomeTabPage.a, bep.a((String) "发现", FeedAjx3Page.class));
                bid.startPage(MapHomeTabPage.class, pageBundle);
                return;
            }
            bid.startPage(FeedAjx3Page.class, pageBundle);
        }
    }

    public final boolean c(bid bid) {
        if (a.a.a) {
            return bid instanceof MapHomeTabPage;
        }
        return bid instanceof OldMapHomePage;
    }
}
