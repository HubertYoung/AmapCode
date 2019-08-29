package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;

public class DrawableCompat {
    static final DrawableImpl IMPL;

    static class BaseDrawableImpl implements DrawableImpl {
        public void a(Drawable drawable) {
        }

        public void a(Drawable drawable, float f, float f2) {
        }

        public void a(Drawable drawable, int i, int i2, int i3, int i4) {
        }

        public void a(Drawable drawable, boolean z) {
        }

        public void b(Drawable drawable, int i) {
        }

        public boolean b(Drawable drawable) {
            return false;
        }

        public int d(Drawable drawable) {
            return 0;
        }

        BaseDrawableImpl() {
        }

        public void a(Drawable drawable, int i) {
            DrawableCompatBase.a(drawable, i);
        }

        public void a(Drawable drawable, ColorStateList colorStateList) {
            DrawableCompatBase.a(drawable, colorStateList);
        }

        public void a(Drawable drawable, Mode mode) {
            DrawableCompatBase.a(drawable, mode);
        }

        public Drawable c(Drawable drawable) {
            return DrawableCompatBase.a(drawable);
        }
    }

    interface DrawableImpl {
        void a(Drawable drawable);

        void a(Drawable drawable, float f, float f2);

        void a(Drawable drawable, int i);

        void a(Drawable drawable, int i, int i2, int i3, int i4);

        void a(Drawable drawable, ColorStateList colorStateList);

        void a(Drawable drawable, Mode mode);

        void a(Drawable drawable, boolean z);

        void b(Drawable drawable, int i);

        boolean b(Drawable drawable);

        Drawable c(Drawable drawable);

        int d(Drawable drawable);
    }

    static class HoneycombDrawableImpl extends BaseDrawableImpl {
        HoneycombDrawableImpl() {
        }

        public final void a(Drawable drawable) {
            DrawableCompatHoneycomb.a(drawable);
        }

        public Drawable c(Drawable drawable) {
            return DrawableCompatHoneycomb.b(drawable);
        }
    }

    static class JellybeanMr1DrawableImpl extends HoneycombDrawableImpl {
        JellybeanMr1DrawableImpl() {
        }

        public void b(Drawable drawable, int i) {
            DrawableCompatJellybeanMr1.a(drawable, i);
        }

        public int d(Drawable drawable) {
            int a = DrawableCompatJellybeanMr1.a(drawable);
            if (a >= 0) {
                return a;
            }
            return 0;
        }
    }

    static class KitKatDrawableImpl extends JellybeanMr1DrawableImpl {
        KitKatDrawableImpl() {
        }

        public final void a(Drawable drawable, boolean z) {
            DrawableCompatKitKat.a(drawable, z);
        }

        public final boolean b(Drawable drawable) {
            return DrawableCompatKitKat.a(drawable);
        }

        public Drawable c(Drawable drawable) {
            return DrawableCompatKitKat.b(drawable);
        }
    }

    static class LollipopDrawableImpl extends KitKatDrawableImpl {
        LollipopDrawableImpl() {
        }

        public final void a(Drawable drawable, float f, float f2) {
            DrawableCompatLollipop.a(drawable, f, f2);
        }

        public final void a(Drawable drawable, int i, int i2, int i3, int i4) {
            DrawableCompatLollipop.a(drawable, i, i2, i3, i4);
        }

        public final void a(Drawable drawable, int i) {
            DrawableCompatLollipop.a(drawable, i);
        }

        public final void a(Drawable drawable, ColorStateList colorStateList) {
            DrawableCompatLollipop.a(drawable, colorStateList);
        }

        public final void a(Drawable drawable, Mode mode) {
            DrawableCompatLollipop.a(drawable, mode);
        }

        public Drawable c(Drawable drawable) {
            return DrawableCompatLollipop.a(drawable);
        }
    }

    static class LollipopMr1DrawableImpl extends LollipopDrawableImpl {
        LollipopMr1DrawableImpl() {
        }

        public final Drawable c(Drawable drawable) {
            return DrawableCompatApi22.a(drawable);
        }
    }

    static class MDrawableImpl extends LollipopMr1DrawableImpl {
        MDrawableImpl() {
        }

        public final void b(Drawable drawable, int i) {
            DrawableCompatApi23.a(drawable, i);
        }

        public final int d(Drawable drawable) {
            return DrawableCompatApi23.a(drawable);
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            IMPL = new MDrawableImpl();
        } else if (i >= 22) {
            IMPL = new LollipopMr1DrawableImpl();
        } else if (i >= 21) {
            IMPL = new LollipopDrawableImpl();
        } else if (i >= 19) {
            IMPL = new KitKatDrawableImpl();
        } else if (i >= 17) {
            IMPL = new JellybeanMr1DrawableImpl();
        } else if (i >= 11) {
            IMPL = new HoneycombDrawableImpl();
        } else {
            IMPL = new BaseDrawableImpl();
        }
    }

    public static void jumpToCurrentState(Drawable drawable) {
        IMPL.a(drawable);
    }

    public static void setAutoMirrored(Drawable drawable, boolean z) {
        IMPL.a(drawable, z);
    }

    public static boolean isAutoMirrored(Drawable drawable) {
        return IMPL.b(drawable);
    }

    public static void setHotspot(Drawable drawable, float f, float f2) {
        IMPL.a(drawable, f, f2);
    }

    public static void setHotspotBounds(Drawable drawable, int i, int i2, int i3, int i4) {
        IMPL.a(drawable, i, i2, i3, i4);
    }

    public static void setTint(Drawable drawable, int i) {
        IMPL.a(drawable, i);
    }

    public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
        IMPL.a(drawable, colorStateList);
    }

    public static void setTintMode(Drawable drawable, Mode mode) {
        IMPL.a(drawable, mode);
    }

    public static Drawable wrap(Drawable drawable) {
        return IMPL.c(drawable);
    }

    public static <T extends Drawable> T unwrap(Drawable drawable) {
        return drawable instanceof DrawableWrapper ? ((DrawableWrapper) drawable).getWrappedDrawable() : drawable;
    }

    public static void setLayoutDirection(Drawable drawable, int i) {
        IMPL.b(drawable, i);
    }

    public static int getLayoutDirection(Drawable drawable) {
        return IMPL.d(drawable);
    }
}
