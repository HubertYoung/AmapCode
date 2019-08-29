package defpackage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.autonavi.map.suspend.refactor.gps.GPSButton;

/* renamed from: uy reason: default package */
/* compiled from: EnvSuspendViewHelper */
public final class uy {
    public GPSButton a;
    public View b;
    public View c;
    @NonNull
    public ccv d;
    @NonNull
    public ccy e;
    @NonNull
    public Context f;

    public final void a(boolean z) {
        if (z) {
            if (this.a != null) {
                this.a.setVisibility(0);
            }
            if (this.b != null) {
                this.b.setVisibility(0);
            }
        } else {
            if (this.a != null) {
                this.a.setVisibility(4);
            }
            if (this.b != null) {
                this.b.setVisibility(4);
            }
        }
    }

    public uy(@NonNull Context context, @NonNull ccv ccv, @NonNull ccy ccy) {
        this.f = context;
        this.d = ccv;
        this.e = ccy;
    }
}
