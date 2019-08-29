package defpackage;

import android.content.Context;
import android.view.ViewConfiguration;

/* renamed from: ceq reason: default package */
/* compiled from: LogoStatusTracker */
public final class ceq {
    public float a;
    public float b;
    public float c;
    public float d;
    public boolean e;
    public int f;

    public ceq(Context context) {
        this.f = (int) (((float) ViewConfiguration.get(context).getScaledTouchSlop()) / 4.0f);
        this.f = this.f > 0 ? this.f : 1;
    }
}
