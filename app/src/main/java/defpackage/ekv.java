package defpackage;

import android.text.TextUtils;
import com.autonavi.bl.search.InfoliteParam;
import com.autonavi.bundle.entity.infolite.internal.InfoliteResult;
import com.autonavi.common.Callback.c;

/* renamed from: ekv reason: default package */
/* compiled from: SearchRequester */
public final class ekv {
    public final bbq a(InfoliteParam infoliteParam, int i, final aea aea) {
        final adx a = b.a.a(infoliteParam, i, new elq<InfoliteResult>() {
            public final /* synthetic */ void a(Object obj) {
                InfoliteResult infoliteResult = (InfoliteResult) obj;
                if (aea != null) {
                    aea.callback(infoliteResult);
                }
                bct.a();
            }

            public final void a(int i) {
                bct.a();
                if (aea != null) {
                    aea.error(i);
                }
            }
        });
        AnonymousClass2 r4 = new bbq() {
            public final void a() {
                a.a();
            }

            public final boolean b() {
                return a.b();
            }
        };
        if (aea instanceof c) {
            String loadingMessage = ((c) aea).getLoadingMessage();
            if (!TextUtils.isEmpty(loadingMessage)) {
                bct.a(loadingMessage, r4);
            }
        }
        return r4;
    }
}
