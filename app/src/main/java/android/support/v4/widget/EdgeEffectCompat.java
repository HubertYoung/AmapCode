package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;

public class EdgeEffectCompat {
    private static final EdgeEffectImpl IMPL;
    private Object mEdgeEffect;

    static class BaseEdgeEffectImpl implements EdgeEffectImpl {
        public final Object a(Context context) {
            return null;
        }

        public final void a(Object obj, int i, int i2) {
        }

        public final boolean a(Object obj) {
            return true;
        }

        public final boolean a(Object obj, float f) {
            return false;
        }

        public final boolean a(Object obj, float f, float f2) {
            return false;
        }

        public final boolean a(Object obj, int i) {
            return false;
        }

        public final boolean a(Object obj, Canvas canvas) {
            return false;
        }

        public final void b(Object obj) {
        }

        public final boolean c(Object obj) {
            return false;
        }

        BaseEdgeEffectImpl() {
        }
    }

    static class EdgeEffectIcsImpl implements EdgeEffectImpl {
        EdgeEffectIcsImpl() {
        }

        public final Object a(Context context) {
            return EdgeEffectCompatIcs.a(context);
        }

        public final void a(Object obj, int i, int i2) {
            EdgeEffectCompatIcs.a(obj, i, i2);
        }

        public final boolean a(Object obj) {
            return EdgeEffectCompatIcs.a(obj);
        }

        public final void b(Object obj) {
            EdgeEffectCompatIcs.b(obj);
        }

        public final boolean a(Object obj, float f) {
            return EdgeEffectCompatIcs.a(obj, f);
        }

        public final boolean c(Object obj) {
            return EdgeEffectCompatIcs.c(obj);
        }

        public final boolean a(Object obj, int i) {
            return EdgeEffectCompatIcs.a(obj, i);
        }

        public final boolean a(Object obj, Canvas canvas) {
            return EdgeEffectCompatIcs.a(obj, canvas);
        }

        public boolean a(Object obj, float f, float f2) {
            return EdgeEffectCompatIcs.a(obj, f);
        }
    }

    interface EdgeEffectImpl {
        Object a(Context context);

        void a(Object obj, int i, int i2);

        boolean a(Object obj);

        boolean a(Object obj, float f);

        boolean a(Object obj, float f, float f2);

        boolean a(Object obj, int i);

        boolean a(Object obj, Canvas canvas);

        void b(Object obj);

        boolean c(Object obj);
    }

    static class EdgeEffectLollipopImpl extends EdgeEffectIcsImpl {
        EdgeEffectLollipopImpl() {
        }

        public final boolean a(Object obj, float f, float f2) {
            return EdgeEffectCompatLollipop.a(obj, f, f2);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new EdgeEffectLollipopImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new EdgeEffectIcsImpl();
        } else {
            IMPL = new BaseEdgeEffectImpl();
        }
    }

    public EdgeEffectCompat(Context context) {
        this.mEdgeEffect = IMPL.a(context);
    }

    public void setSize(int i, int i2) {
        IMPL.a(this.mEdgeEffect, i, i2);
    }

    public boolean isFinished() {
        return IMPL.a(this.mEdgeEffect);
    }

    public void finish() {
        IMPL.b(this.mEdgeEffect);
    }

    public boolean onPull(float f) {
        return IMPL.a(this.mEdgeEffect, f);
    }

    public boolean onPull(float f, float f2) {
        return IMPL.a(this.mEdgeEffect, f, f2);
    }

    public boolean onRelease() {
        return IMPL.c(this.mEdgeEffect);
    }

    public boolean onAbsorb(int i) {
        return IMPL.a(this.mEdgeEffect, i);
    }

    public boolean draw(Canvas canvas) {
        return IMPL.a(this.mEdgeEffect, canvas);
    }
}
