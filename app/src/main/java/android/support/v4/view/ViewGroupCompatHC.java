package android.support.v4.view;

import android.view.ViewGroup;

class ViewGroupCompatHC {
    private ViewGroupCompatHC() {
    }

    public static void a(ViewGroup viewGroup, boolean z) {
        viewGroup.setMotionEventSplittingEnabled(z);
    }
}
