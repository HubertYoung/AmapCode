package android.support.v4.graphics.drawable;

import android.graphics.drawable.Drawable;

class DrawableCompatApi23 {
    DrawableCompatApi23() {
    }

    public static void a(Drawable drawable, int i) {
        drawable.setLayoutDirection(i);
    }

    public static int a(Drawable drawable) {
        return drawable.getLayoutDirection();
    }
}
