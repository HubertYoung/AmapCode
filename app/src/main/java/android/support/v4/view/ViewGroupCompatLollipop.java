package android.support.v4.view;

import android.view.ViewGroup;

class ViewGroupCompatLollipop {
    ViewGroupCompatLollipop() {
    }

    public static void a(ViewGroup viewGroup, boolean z) {
        viewGroup.setTransitionGroup(z);
    }

    public static boolean a(ViewGroup viewGroup) {
        return viewGroup.isTransitionGroup();
    }

    public static int b(ViewGroup viewGroup) {
        return viewGroup.getNestedScrollAxes();
    }
}
