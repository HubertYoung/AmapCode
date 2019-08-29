package defpackage;

import android.view.View.OnClickListener;
import com.autonavi.widget.ui.LoadingViewBL;

/* renamed from: adt reason: default package */
/* compiled from: LoadingUtil */
public final class adt {
    public LoadingViewBL a;
    public bid b;
    public OnClickListener c;

    public adt(bid bid, OnClickListener onClickListener) {
        this.b = bid;
        this.c = onClickListener;
    }

    public final void a() {
        if (this.a != null && this.b != null) {
            this.b.dismissViewLayer(this.a);
        }
    }
}
