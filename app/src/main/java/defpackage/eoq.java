package defpackage;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.WindowManager.LayoutParams;
import com.autonavi.common.PageBundle;
import com.autonavi.minimap.R;
import com.autonavi.minimap.bundle.share.page.ShareViewLayer;
import com.autonavi.minimap.bundle.share.page.ShareViewLayer.a;

/* renamed from: eoq reason: default package */
/* compiled from: ShareFullScreenDialog */
public final class eoq extends Dialog implements a {
    private Context a;
    /* access modifiers changed from: private */
    public ShareViewLayer b;
    /* access modifiers changed from: private */
    public ddj c;
    private a d;

    public final void show() {
        super.show();
        a();
        euj.a((a) this);
        this.c.c();
    }

    public final void dismiss() {
        super.dismiss();
        euj.b((a) this);
        this.c.b();
    }

    private eoq(@NonNull Context context, @StyleRes int i) {
        super(context, i);
        this.d = new a() {
            public final void a() {
                eoq.this.dismiss();
            }

            public final void a(dcr dcr) {
                eoq.this.c.a(eoq.this.b, dcr);
                eoq.this.dismiss();
            }
        };
    }

    public eoq(@NonNull Context context, PageBundle pageBundle) {
        this(context, R.style.MiniAppShareDialog);
        this.a = context;
        setOwnerActivity((Activity) context);
        if (this.c == null) {
            this.c = new ddj(pageBundle);
        }
        this.b = new ShareViewLayer(this.a, this.c.a(), this.d);
        setContentView(this.b.getView());
    }

    public final void a(boolean z) {
        a();
    }

    private void a() {
        LayoutParams attributes = getWindow().getAttributes();
        attributes.gravity = 119;
        attributes.width = -1;
        attributes.height = -2;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(attributes);
    }
}
