package android.support.v4.view;

import android.view.View;

class ViewCompatGingerbread {
    ViewCompatGingerbread() {
    }

    public static int a(View view) {
        return view.getOverScrollMode();
    }

    public static void a(View view, int i) {
        view.setOverScrollMode(i);
    }
}
