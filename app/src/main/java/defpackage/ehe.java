package defpackage;

import android.app.Activity;

/* renamed from: ehe reason: default package */
/* compiled from: ShareRidingFaqWebViewPresenter */
public final class ehe extends ajf {
    private bdd b;
    private Activity c;
    private int d;

    public final boolean g() {
        return true;
    }

    public final boolean h() {
        return true;
    }

    public ehe(Activity activity) {
        this.c = activity;
    }

    public final void onPageCreated() {
        super.onPageCreated();
    }

    public final void onStart() {
        super.onStart();
        if (this.c != null) {
            this.d = this.c.getWindow().getAttributes().softInputMode;
            this.c.getWindow().setSoftInputMode(18);
            this.b = new bdd(this.c);
            this.c.getWindow().getDecorView().addOnLayoutChangeListener(this.b);
        }
    }

    public final void onStop() {
        super.onStop();
        if (this.c != null) {
            this.c.getWindow().setSoftInputMode(this.d);
            if (this.b != null) {
                this.c.getWindow().getDecorView().removeOnLayoutChangeListener(this.b);
            }
        }
    }

    public final void onDestroy() {
        this.c = null;
        super.onDestroy();
    }
}
