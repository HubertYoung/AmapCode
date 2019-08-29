package defpackage;

/* renamed from: cmn reason: default package */
/* compiled from: EaseInBounce */
public final class cmn extends cml {
    public final float a(float f) {
        float f2;
        float f3 = (1.0f - f) / 1.0f;
        if (f3 < 0.36363637f) {
            f2 = (7.5625f * f3 * f3 * 1.0f) + 0.0f;
        } else if (f3 < 0.72727275f) {
            float f4 = f3 - 0.54545456f;
            f2 = (((7.5625f * f4 * f4) + 0.75f) * 1.0f) + 0.0f;
        } else if (((double) f3) < 0.9090909090909091d) {
            float f5 = f3 - 0.8181818f;
            f2 = (((7.5625f * f5 * f5) + 0.9375f) * 1.0f) + 0.0f;
        } else {
            float f6 = f3 - 0.95454544f;
            f2 = (((7.5625f * f6 * f6) + 0.984375f) * 1.0f) + 0.0f;
        }
        return (1.0f - f2) + 0.0f;
    }
}
