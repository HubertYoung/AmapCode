package android.support.v4.view;

import android.graphics.Rect;
import android.view.WindowInsets;

class WindowInsetsCompatApi21 extends WindowInsetsCompat {
    final WindowInsets a;

    WindowInsetsCompatApi21(WindowInsets windowInsets) {
        this.a = windowInsets;
    }

    public int getSystemWindowInsetLeft() {
        return this.a.getSystemWindowInsetLeft();
    }

    public int getSystemWindowInsetTop() {
        return this.a.getSystemWindowInsetTop();
    }

    public int getSystemWindowInsetRight() {
        return this.a.getSystemWindowInsetRight();
    }

    public int getSystemWindowInsetBottom() {
        return this.a.getSystemWindowInsetBottom();
    }

    public boolean hasSystemWindowInsets() {
        return this.a.hasSystemWindowInsets();
    }

    public boolean hasInsets() {
        return this.a.hasInsets();
    }

    public boolean isConsumed() {
        return this.a.isConsumed();
    }

    public boolean isRound() {
        return this.a.isRound();
    }

    public WindowInsetsCompat consumeSystemWindowInsets() {
        return new WindowInsetsCompatApi21(this.a.consumeSystemWindowInsets());
    }

    public WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        return new WindowInsetsCompatApi21(this.a.replaceSystemWindowInsets(i, i2, i3, i4));
    }

    public WindowInsetsCompat replaceSystemWindowInsets(Rect rect) {
        return new WindowInsetsCompatApi21(this.a.replaceSystemWindowInsets(rect));
    }

    public int getStableInsetTop() {
        return this.a.getStableInsetTop();
    }

    public int getStableInsetLeft() {
        return this.a.getStableInsetLeft();
    }

    public int getStableInsetRight() {
        return this.a.getStableInsetRight();
    }

    public int getStableInsetBottom() {
        return this.a.getStableInsetBottom();
    }

    public boolean hasStableInsets() {
        return this.a.hasStableInsets();
    }

    public WindowInsetsCompat consumeStableInsets() {
        return new WindowInsetsCompatApi21(this.a.consumeStableInsets());
    }
}
