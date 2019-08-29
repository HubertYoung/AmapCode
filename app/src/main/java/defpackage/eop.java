package defpackage;

import com.autonavi.common.PageBundle;
import com.autonavi.minimap.bundle.share.page.ShareViewLayer;
import com.autonavi.minimap.bundle.share.page.ShareViewLayer.a;

/* renamed from: eop reason: default package */
/* compiled from: ShareViewPageControlStrategy */
public final class eop implements eon {
    ShareViewLayer a;
    ddj b;
    private bid c;
    private a d = new a() {
        public final void a() {
            eop.this.a();
        }

        public final void a(dcr dcr) {
            eop.this.b.a(eop.this.a, dcr);
            eop.this.a();
        }
    };

    public eop(bid bid) {
        this.c = bid;
    }

    public final void a(PageBundle pageBundle) {
        if (this.b == null) {
            this.b = new ddj(pageBundle);
        }
        this.a = new ShareViewLayer(this.c.getContext(), this.b.a(), this.d);
        this.c.showViewLayer(this.a);
        this.b.c();
    }

    public final void a() {
        this.c.dismissViewLayer(this.a);
        this.b.b();
    }
}
