package defpackage;

import android.content.Context;

/* renamed from: eda reason: default package */
/* compiled from: CompassSensorWrapper */
public final class eda implements defpackage.ecc.a {
    public ecc a = null;
    public boolean b = false;
    private float c = 0.0f;
    private a d;

    /* renamed from: eda$a */
    /* compiled from: CompassSensorWrapper */
    public interface a {
        void a(boolean z);
    }

    public eda(Context context, a aVar) {
        this.a = new ecc(context);
        this.d = aVar;
    }

    public final void a(int i, int i2) {
        boolean z = true;
        if (ahr.a()) {
            if (!(i == 2 || i == 1)) {
                return;
            }
        } else if (i != 3) {
            return;
        }
        if (i2 == 1 || i2 == 0) {
            z = false;
        }
        if (this.d != null) {
            this.d.a(z);
        }
    }
}
