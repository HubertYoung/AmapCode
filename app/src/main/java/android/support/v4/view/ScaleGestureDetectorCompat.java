package android.support.v4.view;

import android.os.Build.VERSION;

public class ScaleGestureDetectorCompat {
    static final ScaleGestureDetectorImpl IMPL;

    static class BaseScaleGestureDetectorImpl implements ScaleGestureDetectorImpl {
        public final void a(Object obj, boolean z) {
        }

        public final boolean a(Object obj) {
            return false;
        }

        private BaseScaleGestureDetectorImpl() {
        }

        /* synthetic */ BaseScaleGestureDetectorImpl(byte b) {
            this();
        }
    }

    static class ScaleGestureDetectorCompatKitKatImpl implements ScaleGestureDetectorImpl {
        private ScaleGestureDetectorCompatKitKatImpl() {
        }

        /* synthetic */ ScaleGestureDetectorCompatKitKatImpl(byte b) {
            this();
        }

        public final void a(Object obj, boolean z) {
            ScaleGestureDetectorCompatKitKat.a(obj, z);
        }

        public final boolean a(Object obj) {
            return ScaleGestureDetectorCompatKitKat.a(obj);
        }
    }

    interface ScaleGestureDetectorImpl {
        void a(Object obj, boolean z);

        boolean a(Object obj);
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new ScaleGestureDetectorCompatKitKatImpl(0);
        } else {
            IMPL = new BaseScaleGestureDetectorImpl(0);
        }
    }

    private ScaleGestureDetectorCompat() {
    }

    public static void setQuickScaleEnabled(Object obj, boolean z) {
        IMPL.a(obj, z);
    }

    public static boolean isQuickScaleEnabled(Object obj) {
        return IMPL.a(obj);
    }
}
