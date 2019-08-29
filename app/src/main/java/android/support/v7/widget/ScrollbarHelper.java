package android.support.v7.widget;

import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.State;
import android.view.View;

class ScrollbarHelper {
    ScrollbarHelper() {
    }

    static int a(State state, OrientationHelper orientationHelper, View view, View view2, LayoutManager layoutManager, boolean z, boolean z2) {
        if (layoutManager.getChildCount() == 0 || state.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        int max = z2 ? Math.max(0, (state.a() - Math.max(layoutManager.getPosition(view), layoutManager.getPosition(view2))) - 1) : Math.max(0, Math.min(layoutManager.getPosition(view), layoutManager.getPosition(view2)));
        if (!z) {
            return max;
        }
        return Math.round((((float) max) * (((float) Math.abs(orientationHelper.b(view2) - orientationHelper.a(view))) / ((float) (Math.abs(layoutManager.getPosition(view) - layoutManager.getPosition(view2)) + 1)))) + ((float) (orientationHelper.b() - orientationHelper.a(view))));
    }

    static int a(State state, OrientationHelper orientationHelper, View view, View view2, LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(layoutManager.getPosition(view) - layoutManager.getPosition(view2)) + 1;
        }
        return Math.min(orientationHelper.e(), orientationHelper.b(view2) - orientationHelper.a(view));
    }

    static int b(State state, OrientationHelper orientationHelper, View view, View view2, LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.a() == 0 || view == null || view2 == null) {
            return 0;
        }
        if (!z) {
            return state.a();
        }
        return (int) ((((float) (orientationHelper.b(view2) - orientationHelper.a(view))) / ((float) (Math.abs(layoutManager.getPosition(view) - layoutManager.getPosition(view2)) + 1))) * ((float) state.a()));
    }
}
