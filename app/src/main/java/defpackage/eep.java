package defpackage;

import android.view.View;
import com.autonavi.minimap.R;

/* renamed from: eep reason: default package */
/* compiled from: RideHeaderUpPresenter */
public final class eep extends bsb {
    public boolean b = false;
    public a c;

    /* renamed from: eep$a */
    /* compiled from: RideHeaderUpPresenter */
    public interface a {
        void a(boolean z);
    }

    public final void a() {
        super.a();
        this.b = edr.a((String) "ridenavimodewithangle", true);
        if (this.b) {
            this.a.setImageResource(R.drawable.route_navi_north_selector);
        } else {
            this.a.setImageResource(R.drawable.route_navi_up_selector);
        }
        this.a.setBackgroundResource(R.drawable.route_suspend_bg_selector);
        if (this.c != null) {
            this.c.a(this.b);
        }
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
        edr.b((String) "ridenavimodewithangle", this.b);
    }
}
