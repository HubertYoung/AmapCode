package defpackage;

import android.view.View;
import com.autonavi.minimap.R;

/* renamed from: efr reason: default package */
/* compiled from: HeaderUpPresenter */
public final class efr extends bsb {
    public boolean b = true;
    public a c;

    /* renamed from: efr$a */
    /* compiled from: HeaderUpPresenter */
    public interface a {
        void a(boolean z);
    }

    public final void a() {
        super.a();
        this.b = edr.a((String) "runnavimodewithangle", true);
        if (this.b) {
            this.a.setImageResource(R.drawable.route_navi_north_selector);
        } else {
            this.a.setImageResource(R.drawable.route_navi_up_selector);
        }
        this.a.setBackgroundResource(R.drawable.route_suspend_bg_selector);
    }

    public final void onClick(View view) {
        if (this.b) {
            this.a.setImageResource(R.drawable.route_navi_up_selector);
        } else {
            this.a.setImageResource(R.drawable.route_navi_north_selector);
        }
        this.b = !this.b;
        if (this.c != null) {
            this.c.a(this.b);
        }
        edr.b((String) "runnavimodewithangle", this.b);
    }
}
