package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;

public class ViewGroupCompat {
    static final ViewGroupCompatImpl IMPL;
    public static final int LAYOUT_MODE_CLIP_BOUNDS = 0;
    public static final int LAYOUT_MODE_OPTICAL_BOUNDS = 1;

    static class ViewGroupCompatHCImpl extends ViewGroupCompatStubImpl {
        ViewGroupCompatHCImpl() {
        }

        public final void a(ViewGroup viewGroup, boolean z) {
            ViewGroupCompatHC.a(viewGroup, z);
        }
    }

    static class ViewGroupCompatIcsImpl extends ViewGroupCompatHCImpl {
        ViewGroupCompatIcsImpl() {
        }

        public final boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return ViewGroupCompatIcs.a(viewGroup, view, accessibilityEvent);
        }
    }

    interface ViewGroupCompatImpl {
        int a(ViewGroup viewGroup);

        void a(ViewGroup viewGroup, int i);

        void a(ViewGroup viewGroup, boolean z);

        boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent);

        void b(ViewGroup viewGroup, boolean z);

        boolean b(ViewGroup viewGroup);

        int c(ViewGroup viewGroup);
    }

    static class ViewGroupCompatJellybeanMR2Impl extends ViewGroupCompatIcsImpl {
        ViewGroupCompatJellybeanMR2Impl() {
        }

        public final int a(ViewGroup viewGroup) {
            return ViewGroupCompatJellybeanMR2.a(viewGroup);
        }

        public final void a(ViewGroup viewGroup, int i) {
            ViewGroupCompatJellybeanMR2.a(viewGroup, i);
        }
    }

    static class ViewGroupCompatLollipopImpl extends ViewGroupCompatJellybeanMR2Impl {
        ViewGroupCompatLollipopImpl() {
        }

        public final void b(ViewGroup viewGroup, boolean z) {
            ViewGroupCompatLollipop.a(viewGroup, z);
        }

        public final boolean b(ViewGroup viewGroup) {
            return ViewGroupCompatLollipop.a(viewGroup);
        }

        public final int c(ViewGroup viewGroup) {
            return ViewGroupCompatLollipop.b(viewGroup);
        }
    }

    static class ViewGroupCompatStubImpl implements ViewGroupCompatImpl {
        public int a(ViewGroup viewGroup) {
            return 0;
        }

        public void a(ViewGroup viewGroup, int i) {
        }

        public void a(ViewGroup viewGroup, boolean z) {
        }

        public boolean a(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return true;
        }

        public void b(ViewGroup viewGroup, boolean z) {
        }

        public boolean b(ViewGroup viewGroup) {
            return false;
        }

        ViewGroupCompatStubImpl() {
        }

        public int c(ViewGroup viewGroup) {
            if (viewGroup instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) viewGroup).getNestedScrollAxes();
            }
            return 0;
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            IMPL = new ViewGroupCompatLollipopImpl();
        } else if (i >= 18) {
            IMPL = new ViewGroupCompatJellybeanMR2Impl();
        } else if (i >= 14) {
            IMPL = new ViewGroupCompatIcsImpl();
        } else if (i >= 11) {
            IMPL = new ViewGroupCompatHCImpl();
        } else {
            IMPL = new ViewGroupCompatStubImpl();
        }
    }

    private ViewGroupCompat() {
    }

    public static boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.a(viewGroup, view, accessibilityEvent);
    }

    public static void setMotionEventSplittingEnabled(ViewGroup viewGroup, boolean z) {
        IMPL.a(viewGroup, z);
    }

    public static int getLayoutMode(ViewGroup viewGroup) {
        return IMPL.a(viewGroup);
    }

    public static void setLayoutMode(ViewGroup viewGroup, int i) {
        IMPL.a(viewGroup, i);
    }

    public static void setTransitionGroup(ViewGroup viewGroup, boolean z) {
        IMPL.b(viewGroup, z);
    }

    public static boolean isTransitionGroup(ViewGroup viewGroup) {
        return IMPL.b(viewGroup);
    }

    public static int getNestedScrollAxes(ViewGroup viewGroup) {
        return IMPL.c(viewGroup);
    }
}
