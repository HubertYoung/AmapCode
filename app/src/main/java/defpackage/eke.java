package defpackage;

import android.content.Context;
import com.autonavi.minimap.comment.CommentRequestHolder;
import com.autonavi.minimap.route.ugc.net.callback.BusNaviReviewRequestCallback;
import com.autonavi.minimap.route.ugc.net.param.BusNaviReviewParam;

/* renamed from: eke reason: default package */
/* compiled from: UGCManager */
public final class eke {
    private static eke b;
    public Context a;

    private eke(Context context) {
        this.a = context;
    }

    public static synchronized eke a(Context context) {
        eke eke;
        synchronized (eke.class) {
            try {
                if (b == null) {
                    b = new eke(context);
                }
                eke = b;
            }
        }
        return eke;
    }

    public final void a(ejz ejz) {
        CommentRequestHolder.getInstance().sendBusBsCreate(BusNaviReviewParam.buildParam(ejz), new BusNaviReviewRequestCallback(this.a, ejz));
    }

    public final void a(String str) {
        new ekd(this.a);
        ekd.a(str, (String) "");
    }
}
