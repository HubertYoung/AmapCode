package android.support.v4.view;

import android.graphics.Rect;
import android.view.View;

class ViewCompatJellybeanMr2 {
    ViewCompatJellybeanMr2() {
    }

    public static Rect a(View view) {
        return view.getClipBounds();
    }

    public static void a(View view, Rect rect) {
        view.setClipBounds(rect);
    }
}
