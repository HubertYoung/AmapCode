package defpackage;

import android.content.Context;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.annotation.BundleInterface;
import com.autonavi.bundle.ugc.api.IUGCPage;
import com.autonavi.minimap.comment.CommentRequestHolder;
import com.autonavi.minimap.route.ugc.net.callback.BusNaviReviewRequestCallback;
import com.autonavi.minimap.route.ugc.net.callback.FootNaviReviewRequestCallback;
import com.autonavi.minimap.route.ugc.net.param.BusNaviReviewParam;
import com.autonavi.minimap.route.ugc.net.param.FootNaviReviewParam;
import com.autonavi.minimap.route.ugc.page.FootNaviReviewPage;

@BundleInterface(bdp.class)
/* renamed from: ekc reason: default package */
/* compiled from: UGCService */
public class ekc extends esi implements bdp {
    public final void b() {
        eke a = eke.a((Context) AMapAppGlobal.getApplication());
        new ekd(a.a);
        String a2 = ekd.a((String) "ugc_cache_bus");
        if (a2 != null && !a2.isEmpty()) {
            CommentRequestHolder.getInstance().sendBusBsCreate(BusNaviReviewParam.buildParam(a2), new BusNaviReviewRequestCallback(a.a, null));
        }
        String a3 = ekd.a((String) "ugc_cache_foot");
        if (a3 != null && !a3.isEmpty()) {
            CommentRequestHolder.getInstance().sendWalkCreate(FootNaviReviewParam.buildParam(a3), new FootNaviReviewRequestCallback(a.a, null));
        }
    }

    public final void a(ejz ejz) {
        eke.a((Context) AMapAppGlobal.getApplication()).a(ejz);
    }

    public final Class<? extends bid> c() {
        return FootNaviReviewPage.class;
    }

    public final IUGCPage a() {
        return a.a;
    }
}
