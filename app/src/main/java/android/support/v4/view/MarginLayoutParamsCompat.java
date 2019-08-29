package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewGroup.MarginLayoutParams;

public class MarginLayoutParamsCompat {
    static final MarginLayoutParamsCompatImpl IMPL;

    interface MarginLayoutParamsCompatImpl {
        int a(MarginLayoutParams marginLayoutParams);

        void a(MarginLayoutParams marginLayoutParams, int i);

        int b(MarginLayoutParams marginLayoutParams);

        void b(MarginLayoutParams marginLayoutParams, int i);

        void c(MarginLayoutParams marginLayoutParams, int i);

        boolean c(MarginLayoutParams marginLayoutParams);

        int d(MarginLayoutParams marginLayoutParams);

        void d(MarginLayoutParams marginLayoutParams, int i);
    }

    static class MarginLayoutParamsCompatImplBase implements MarginLayoutParamsCompatImpl {
        public final void c(MarginLayoutParams marginLayoutParams, int i) {
        }

        public final boolean c(MarginLayoutParams marginLayoutParams) {
            return false;
        }

        public final int d(MarginLayoutParams marginLayoutParams) {
            return 0;
        }

        public final void d(MarginLayoutParams marginLayoutParams, int i) {
        }

        MarginLayoutParamsCompatImplBase() {
        }

        public final int a(MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.leftMargin;
        }

        public final int b(MarginLayoutParams marginLayoutParams) {
            return marginLayoutParams.rightMargin;
        }

        public final void a(MarginLayoutParams marginLayoutParams, int i) {
            marginLayoutParams.leftMargin = i;
        }

        public final void b(MarginLayoutParams marginLayoutParams, int i) {
            marginLayoutParams.rightMargin = i;
        }
    }

    static class MarginLayoutParamsCompatImplJbMr1 implements MarginLayoutParamsCompatImpl {
        MarginLayoutParamsCompatImplJbMr1() {
        }

        public final int a(MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.a(marginLayoutParams);
        }

        public final int b(MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.b(marginLayoutParams);
        }

        public final void a(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.a(marginLayoutParams, i);
        }

        public final void b(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.b(marginLayoutParams, i);
        }

        public final boolean c(MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.c(marginLayoutParams);
        }

        public final int d(MarginLayoutParams marginLayoutParams) {
            return MarginLayoutParamsCompatJellybeanMr1.d(marginLayoutParams);
        }

        public final void c(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.c(marginLayoutParams, i);
        }

        public final void d(MarginLayoutParams marginLayoutParams, int i) {
            MarginLayoutParamsCompatJellybeanMr1.d(marginLayoutParams, i);
        }
    }

    static {
        if (VERSION.SDK_INT >= 17) {
            IMPL = new MarginLayoutParamsCompatImplJbMr1();
        } else {
            IMPL = new MarginLayoutParamsCompatImplBase();
        }
    }

    public static int getMarginStart(MarginLayoutParams marginLayoutParams) {
        return IMPL.a(marginLayoutParams);
    }

    public static int getMarginEnd(MarginLayoutParams marginLayoutParams) {
        return IMPL.b(marginLayoutParams);
    }

    public static void setMarginStart(MarginLayoutParams marginLayoutParams, int i) {
        IMPL.a(marginLayoutParams, i);
    }

    public static void setMarginEnd(MarginLayoutParams marginLayoutParams, int i) {
        IMPL.b(marginLayoutParams, i);
    }

    public static boolean isMarginRelative(MarginLayoutParams marginLayoutParams) {
        return IMPL.c(marginLayoutParams);
    }

    public static int getLayoutDirection(MarginLayoutParams marginLayoutParams) {
        return IMPL.d(marginLayoutParams);
    }

    public static void setLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
        IMPL.c(marginLayoutParams, i);
    }

    public static void resolveLayoutDirection(MarginLayoutParams marginLayoutParams, int i) {
        IMPL.d(marginLayoutParams, i);
    }
}
