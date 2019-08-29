package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatHoneycomb {
    DrawableCompatHoneycomb() {
    }

    public static void a(Drawable drawable) {
        drawable.jumpToCurrentState();
    }

    public static Drawable b(Drawable drawable) {
        return !(drawable instanceof DrawableWrapperHoneycomb) ? new DrawableWrapperHoneycomb(drawable) : drawable;
    }
}
