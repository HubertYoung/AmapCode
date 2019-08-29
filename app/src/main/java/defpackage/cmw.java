package defpackage;

/* renamed from: cmw reason: default package */
/* compiled from: EaseInOutElastic */
public final class cmw extends cml {
    public final float a(float f) {
        if (f == 0.0f) {
            return 0.0f;
        }
        float f2 = f / 0.5f;
        if (f2 == 2.0f) {
            return 1.0f;
        }
        if (f2 < 1.0f) {
            float f3 = f2 - 1.0f;
            return (((float) Math.pow(2.0d, (double) (10.0f * f3))) * 1.0f * ((float) Math.sin((double) ((((f3 * 1.0f) - 0.112500004f) * 6.2831855f) / 0.45000002f))) * -0.5f) + 0.0f;
        }
        float f4 = f2 - 1.0f;
        return (((float) Math.pow(2.0d, (double) (-10.0f * f4))) * 1.0f * ((float) Math.sin((double) ((((f4 * 1.0f) - 0.112500004f) * 6.2831855f) / 0.45000002f))) * 0.5f) + 1.0f + 0.0f;
    }
}
