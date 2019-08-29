package android.support.v4.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.Gravity;

public class GravityCompat {
    public static final int END = 8388613;
    static final GravityCompatImpl IMPL;
    public static final int RELATIVE_HORIZONTAL_GRAVITY_MASK = 8388615;
    public static final int RELATIVE_LAYOUT_DIRECTION = 8388608;
    public static final int START = 8388611;

    interface GravityCompatImpl {
        int a(int i, int i2);

        void a(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6);

        void a(int i, int i2, int i3, Rect rect, Rect rect2, int i4);

        void a(int i, Rect rect, Rect rect2, int i2);
    }

    static class GravityCompatImplBase implements GravityCompatImpl {
        public final int a(int i, int i2) {
            return i & -8388609;
        }

        GravityCompatImplBase() {
        }

        public final void a(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
            Gravity.apply(i, i2, i3, rect, rect2);
        }

        public final void a(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
            Gravity.apply(i, i2, i3, rect, i4, i5, rect2);
        }

        public final void a(int i, Rect rect, Rect rect2, int i2) {
            Gravity.applyDisplay(i, rect, rect2);
        }
    }

    static class GravityCompatImplJellybeanMr1 implements GravityCompatImpl {
        GravityCompatImplJellybeanMr1() {
        }

        public final int a(int i, int i2) {
            return GravityCompatJellybeanMr1.a(i, i2);
        }

        public final void a(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
            GravityCompatJellybeanMr1.a(i, i2, i3, rect, rect2, i4);
        }

        public final void a(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
            GravityCompatJellybeanMr1.a(i, i2, i3, rect, i4, i5, rect2, i6);
        }

        public final void a(int i, Rect rect, Rect rect2, int i2) {
            GravityCompatJellybeanMr1.a(i, rect, rect2, i2);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new GravityCompatImplJellybeanMr1();
        } else {
            IMPL = new GravityCompatImplBase();
        }
    }

    public static void apply(int i, int i2, int i3, Rect rect, Rect rect2, int i4) {
        IMPL.a(i, i2, i3, rect, rect2, i4);
    }

    public static void apply(int i, int i2, int i3, Rect rect, int i4, int i5, Rect rect2, int i6) {
        IMPL.a(i, i2, i3, rect, i4, i5, rect2, i6);
    }

    public static void applyDisplay(int i, Rect rect, Rect rect2, int i2) {
        IMPL.a(i, rect, rect2, i2);
    }

    public static int getAbsoluteGravity(int i, int i2) {
        return IMPL.a(i, i2);
    }
}
