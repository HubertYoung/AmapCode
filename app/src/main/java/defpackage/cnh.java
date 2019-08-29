package defpackage;

/* renamed from: cnh reason: default package */
/* compiled from: EaseOutBounce */
public final class cnh extends cml {
    public final float a(float f) {
        float f2 = f / 1.0f;
        if (f2 < 0.36363637f) {
            return (7.5625f * f2 * f2 * 1.0f) + 0.0f;
        }
        if (f2 < 0.72727275f) {
            float f3 = f2 - 0.54545456f;
            return (((7.5625f * f3 * f3) + 0.75f) * 1.0f) + 0.0f;
        } else if (((double) f2) < 0.9090909090909091d) {
            float f4 = f2 - 0.8181818f;
            return (((7.5625f * f4 * f4) + 0.9375f) * 1.0f) + 0.0f;
        } else {
            float f5 = f2 - 0.95454544f;
            return (((7.5625f * f5 * f5) + 0.984375f) * 1.0f) + 0.0f;
        }
    }
}
