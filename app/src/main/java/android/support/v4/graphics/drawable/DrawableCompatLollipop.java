package android.support.v4.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;

class DrawableCompatLollipop {
    DrawableCompatLollipop() {
    }

    public static void a(Drawable drawable, float f, float f2) {
        drawable.setHotspot(f, f2);
    }

    public static void a(Drawable drawable, int i, int i2, int i3, int i4) {
        drawable.setHotspotBounds(i, i2, i3, i4);
    }

    public static void a(Drawable drawable, int i) {
        if (drawable instanceof DrawableWrapperLollipop) {
            DrawableCompatBase.a(drawable, i);
        } else {
            drawable.setTint(i);
        }
    }

    public static void a(Drawable drawable, ColorStateList colorStateList) {
        if (drawable instanceof DrawableWrapperLollipop) {
            DrawableCompatBase.a(drawable, colorStateList);
        } else {
            drawable.setTintList(colorStateList);
        }
    }

    public static void a(Drawable drawable, Mode mode) {
        if (drawable instanceof DrawableWrapperLollipop) {
            DrawableCompatBase.a(drawable, mode);
        } else {
            drawable.setTintMode(mode);
        }
    }

    public static Drawable a(Drawable drawable) {
        if ((drawable instanceof GradientDrawable) || (drawable instanceof DrawableContainer)) {
            return new DrawableWrapperLollipop(drawable);
        }
        return drawable;
    }
}
