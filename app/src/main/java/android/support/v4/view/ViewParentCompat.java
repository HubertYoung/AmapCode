package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

public class ViewParentCompat {
    static final ViewParentCompatImpl IMPL;

    static class ViewParentCompatICSImpl extends ViewParentCompatStubImpl {
        ViewParentCompatICSImpl() {
        }

        public final boolean a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            return ViewParentCompatICS.requestSendAccessibilityEvent(viewParent, view, accessibilityEvent);
        }
    }

    interface ViewParentCompatImpl {
        void a(ViewParent viewParent, View view);

        void a(ViewParent viewParent, View view, int i, int i2, int i3, int i4);

        void a(ViewParent viewParent, View view, int i, int i2, int[] iArr);

        boolean a(ViewParent viewParent, View view, float f, float f2);

        boolean a(ViewParent viewParent, View view, float f, float f2, boolean z);

        boolean a(ViewParent viewParent, View view, View view2, int i);

        boolean a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent);

        void b(ViewParent viewParent, View view, View view2, int i);

        void c(ViewParent viewParent, View view, View view2, int i);
    }

    static class ViewParentCompatKitKatImpl extends ViewParentCompatICSImpl {
        ViewParentCompatKitKatImpl() {
        }

        public final void c(ViewParent viewParent, View view, View view2, int i) {
            ViewParentCompatKitKat.a(viewParent, view, view2, i);
        }
    }

    static class ViewParentCompatLollipopImpl extends ViewParentCompatKitKatImpl {
        ViewParentCompatLollipopImpl() {
        }

        public final boolean a(ViewParent viewParent, View view, View view2, int i) {
            return ViewParentCompatLollipop.a(viewParent, view, view2, i);
        }

        public final void b(ViewParent viewParent, View view, View view2, int i) {
            ViewParentCompatLollipop.b(viewParent, view, view2, i);
        }

        public final void a(ViewParent viewParent, View view) {
            ViewParentCompatLollipop.a(viewParent, view);
        }

        public final void a(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
            ViewParentCompatLollipop.a(viewParent, view, i, i2, i3, i4);
        }

        public final void a(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
            ViewParentCompatLollipop.a(viewParent, view, i, i2, iArr);
        }

        public final boolean a(ViewParent viewParent, View view, float f, float f2, boolean z) {
            return ViewParentCompatLollipop.a(viewParent, view, f, f2, z);
        }

        public final boolean a(ViewParent viewParent, View view, float f, float f2) {
            return ViewParentCompatLollipop.a(viewParent, view, f, f2);
        }
    }

    static class ViewParentCompatStubImpl implements ViewParentCompatImpl {
        public void c(ViewParent viewParent, View view, View view2, int i) {
        }

        ViewParentCompatStubImpl() {
        }

        public boolean a(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
            if (view == null) {
                return false;
            }
            ((AccessibilityManager) view.getContext().getSystemService("accessibility")).sendAccessibilityEvent(accessibilityEvent);
            return true;
        }

        public boolean a(ViewParent viewParent, View view, View view2, int i) {
            if (viewParent instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) viewParent).onStartNestedScroll(view, view2, i);
            }
            return false;
        }

        public void b(ViewParent viewParent, View view, View view2, int i) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) viewParent).onNestedScrollAccepted(view, view2, i);
            }
        }

        public void a(ViewParent viewParent, View view) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) viewParent).onStopNestedScroll(view);
            }
        }

        public void a(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) viewParent).onNestedScroll(view, i, i2, i3, i4);
            }
        }

        public void a(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
            if (viewParent instanceof NestedScrollingParent) {
                ((NestedScrollingParent) viewParent).onNestedPreScroll(view, i, i2, iArr);
            }
        }

        public boolean a(ViewParent viewParent, View view, float f, float f2, boolean z) {
            if (viewParent instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) viewParent).onNestedFling(view, f, f2, z);
            }
            return false;
        }

        public boolean a(ViewParent viewParent, View view, float f, float f2) {
            if (viewParent instanceof NestedScrollingParent) {
                return ((NestedScrollingParent) viewParent).onNestedPreFling(view, f, f2);
            }
            return false;
        }
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 21) {
            IMPL = new ViewParentCompatLollipopImpl();
        } else if (i >= 19) {
            IMPL = new ViewParentCompatKitKatImpl();
        } else if (i >= 14) {
            IMPL = new ViewParentCompatICSImpl();
        } else {
            IMPL = new ViewParentCompatStubImpl();
        }
    }

    private ViewParentCompat() {
    }

    public static boolean requestSendAccessibilityEvent(ViewParent viewParent, View view, AccessibilityEvent accessibilityEvent) {
        return IMPL.a(viewParent, view, accessibilityEvent);
    }

    public static boolean onStartNestedScroll(ViewParent viewParent, View view, View view2, int i) {
        return IMPL.a(viewParent, view, view2, i);
    }

    public static void onNestedScrollAccepted(ViewParent viewParent, View view, View view2, int i) {
        IMPL.b(viewParent, view, view2, i);
    }

    public static void onStopNestedScroll(ViewParent viewParent, View view) {
        IMPL.a(viewParent, view);
    }

    public static void onNestedScroll(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
        IMPL.a(viewParent, view, i, i2, i3, i4);
    }

    public static void onNestedPreScroll(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
        IMPL.a(viewParent, view, i, i2, iArr);
    }

    public static boolean onNestedFling(ViewParent viewParent, View view, float f, float f2, boolean z) {
        return IMPL.a(viewParent, view, f, f2, z);
    }

    public static boolean onNestedPreFling(ViewParent viewParent, View view, float f, float f2) {
        return IMPL.a(viewParent, view, f, f2);
    }

    public static void notifySubtreeAccessibilityStateChanged(ViewParent viewParent, View view, View view2, int i) {
        IMPL.c(viewParent, view, view2, i);
    }
}
