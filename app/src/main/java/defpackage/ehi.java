package defpackage;

import android.view.View;
import com.autonavi.minimap.R;

/* renamed from: ehi reason: default package */
/* compiled from: RidingUpSimPresenter */
public final class ehi extends bsb {
    public boolean b = false;
    public a c;

    /* renamed from: ehi$a */
    /* compiled from: RidingUpSimPresenter */
    public interface a {
        void a(boolean z);
    }

    public final void a() {
        super.a();
        this.b = edr.a((String) "sharebikenavimodewithangle", true);
        if (this.b) {
            this.a.setImageResource(R.drawable.share_bike_north_head_selector);
        } else {
            this.a.setImageResource(R.drawable.share_bike_up_head_selector);
        }
        if (this.c != null) {
            this.c.a(this.b);
        }
    }

    public final void onClick(View view) {
        if (this.b) {
            this.a.setImageResource(R.drawable.share_bike_up_head_selector);
        } else {
            this.a.setImageResource(R.drawable.share_bike_north_head_selector);
        }
        this.b = !this.b;
        if (this.c != null) {
            this.c.a(this.b);
        }
        edr.b((String) "sharebikenavimodewithangle", this.b);
    }
}
