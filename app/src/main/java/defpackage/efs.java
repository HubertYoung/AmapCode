package defpackage;

import android.view.View;
import com.autonavi.minimap.R;

/* renamed from: efs reason: default package */
/* compiled from: PreviewPresenter */
public final class efs extends bsb {
    boolean b;
    public a c;

    /* renamed from: efs$a */
    /* compiled from: PreviewPresenter */
    public interface a {
        void a(boolean z);
    }

    public final void onClick(View view) {
        this.b = !this.b;
        c();
        if (this.c != null) {
            this.c.a(this.b);
        }
    }

    private void c() {
        if (this.b) {
            this.a.setImageResource(R.drawable.route_run_exit_preview_selector);
        } else {
            this.a.setImageResource(R.drawable.route_run_preview_selector);
        }
    }

    public final void a() {
        super.a();
        this.a.setImageResource(R.drawable.route_run_preview_selector);
    }

    public final void b() {
        this.b = false;
        c();
    }
}
