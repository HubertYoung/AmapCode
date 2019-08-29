package android.support.v4.view;

import android.view.View;
import android.view.ViewParent;

class ViewParentCompatLollipop {
    ViewParentCompatLollipop() {
    }

    public static boolean a(ViewParent viewParent, View view, View view2, int i) {
        try {
            return viewParent.onStartNestedScroll(view, view2, i);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onStartNestedScroll");
            return false;
        }
    }

    public static void b(ViewParent viewParent, View view, View view2, int i) {
        try {
            viewParent.onNestedScrollAccepted(view, view2, i);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onNestedScrollAccepted");
        }
    }

    public static void a(ViewParent viewParent, View view) {
        try {
            viewParent.onStopNestedScroll(view);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onStopNestedScroll");
        }
    }

    public static void a(ViewParent viewParent, View view, int i, int i2, int i3, int i4) {
        try {
            viewParent.onNestedScroll(view, i, i2, i3, i4);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onNestedScroll");
        }
    }

    public static void a(ViewParent viewParent, View view, int i, int i2, int[] iArr) {
        try {
            viewParent.onNestedPreScroll(view, i, i2, iArr);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onNestedPreScroll");
        }
    }

    public static boolean a(ViewParent viewParent, View view, float f, float f2, boolean z) {
        try {
            return viewParent.onNestedFling(view, f, f2, z);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onNestedFling");
            return false;
        }
    }

    public static boolean a(ViewParent viewParent, View view, float f, float f2) {
        try {
            return viewParent.onNestedPreFling(view, f, f2);
        } catch (AbstractMethodError unused) {
            StringBuilder sb = new StringBuilder("ViewParent ");
            sb.append(viewParent);
            sb.append(" does not implement interface method onNestedPreFling");
            return false;
        }
    }
}
