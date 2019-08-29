package android.support.v4.view;

import android.view.MotionEvent;

class MotionEventCompatGingerbread {
    MotionEventCompatGingerbread() {
    }

    public static int a(MotionEvent motionEvent) {
        return motionEvent.getSource();
    }
}
