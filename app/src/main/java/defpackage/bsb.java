package defpackage;

import android.view.View.OnClickListener;
import android.widget.ImageView;

/* renamed from: bsb reason: default package */
/* compiled from: BaseImageViewPresenter */
public abstract class bsb implements OnClickListener {
    protected ImageView a;

    public final void a(brh brh) {
        if (brh instanceof ImageView) {
            this.a = (ImageView) brh;
        }
        a();
    }

    /* access modifiers changed from: protected */
    public void a() {
        this.a.setOnClickListener(this);
    }
}
