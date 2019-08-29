package defpackage;

/* renamed from: cmq reason: default package */
/* compiled from: EaseInElastic */
public final class cmq extends cml {
    public final float a(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = f / 1.0f;
        if (f2 == 1.0f) {
            return 1.0f;
        }
        float f3 = f2 - 1.0f;
        return (-(((float) Math.pow(2.0d, (double) (10.0f * f3))) * 1.0f * ((float) Math.sin((double) ((((f3 * 1.0f) - 0.075f) * 6.2831855f) / 0.3f))))) + 0.0f;
    }
}
