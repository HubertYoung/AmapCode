package defpackage;

import android.content.Context;
import com.autonavi.common.PageBundle;

/* renamed from: eoo reason: default package */
/* compiled from: ShareViewActivityControlStrategy */
public final class eoo implements eon {
    private Context a;
    private eoq b;

    public eoo(Context context) {
        this.a = context;
    }

    public final void a(PageBundle pageBundle) {
        if (this.b == null) {
            this.b = new eoq(this.a, pageBundle);
        }
        this.b.show();
    }
}
