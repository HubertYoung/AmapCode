package defpackage;

import android.view.animation.Interpolator;
import com.autonavi.minimap.auidebugger.boommenu.Eases.EaseType;

/* renamed from: cns reason: default package */
/* compiled from: InterpolatorFactory */
public final class cns {

    /* renamed from: cns$a */
    /* compiled from: InterpolatorFactory */
    public static class a implements Interpolator {
        private EaseType a;

        public a(EaseType easeType) {
            this.a = easeType;
        }

        public final float getInterpolation(float f) {
            return this.a.getOffset(f);
        }
    }

    public static a a(EaseType easeType) {
        return new a(easeType);
    }
}
