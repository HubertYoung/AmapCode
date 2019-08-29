package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.widget.PopupWindow;

public class PopupWindowCompat {
    static final PopupWindowImpl IMPL;

    static class Api21PopupWindowImpl extends KitKatPopupWindowImpl {
        Api21PopupWindowImpl() {
        }

        public void a(PopupWindow popupWindow, boolean z) {
            PopupWindowCompatApi21.a(popupWindow, z);
        }

        public boolean a(PopupWindow popupWindow) {
            return PopupWindowCompatApi21.a(popupWindow);
        }
    }

    static class Api23PopupWindowImpl extends Api21PopupWindowImpl {
        Api23PopupWindowImpl() {
        }

        public final void a(PopupWindow popupWindow, boolean z) {
            PopupWindowCompatApi23.a(popupWindow, z);
        }

        public final boolean a(PopupWindow popupWindow) {
            return PopupWindowCompatApi23.a(popupWindow);
        }

        public final void a(PopupWindow popupWindow, int i) {
            PopupWindowCompatApi23.a(popupWindow, i);
        }

        public final int b(PopupWindow popupWindow) {
            return PopupWindowCompatApi23.b(popupWindow);
        }
    }

    static class BasePopupWindowImpl implements PopupWindowImpl {
        public void a(PopupWindow popupWindow, int i) {
        }

        public void a(PopupWindow popupWindow, boolean z) {
        }

        public boolean a(PopupWindow popupWindow) {
            return false;
        }

        public int b(PopupWindow popupWindow) {
            return 0;
        }

        BasePopupWindowImpl() {
        }

        public void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            popupWindow.showAsDropDown(view, i, i2);
        }
    }

    static class GingerbreadPopupWindowImpl extends BasePopupWindowImpl {
        GingerbreadPopupWindowImpl() {
        }

        public void a(PopupWindow popupWindow, int i) {
            PopupWindowCompatGingerbread.a(popupWindow, i);
        }

        public int b(PopupWindow popupWindow) {
            return PopupWindowCompatGingerbread.a(popupWindow);
        }
    }

    static class KitKatPopupWindowImpl extends GingerbreadPopupWindowImpl {
        KitKatPopupWindowImpl() {
        }

        public final void a(PopupWindow popupWindow, View view, int i, int i2, int i3) {
            PopupWindowCompatKitKat.a(popupWindow, view, i, i2, i3);
        }
    }

    interface PopupWindowImpl {
        void a(PopupWindow popupWindow, int i);

        void a(PopupWindow popupWindow, View view, int i, int i2, int i3);

        void a(PopupWindow popupWindow, boolean z);

        boolean a(PopupWindow popupWindow);

        int b(PopupWindow popupWindow);
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            IMPL = new Api23PopupWindowImpl();
        } else if (i >= 21) {
            IMPL = new Api21PopupWindowImpl();
        } else if (i >= 19) {
            IMPL = new KitKatPopupWindowImpl();
        } else if (i >= 9) {
            IMPL = new GingerbreadPopupWindowImpl();
        } else {
            IMPL = new BasePopupWindowImpl();
        }
    }

    private PopupWindowCompat() {
    }

    public static void showAsDropDown(PopupWindow popupWindow, View view, int i, int i2, int i3) {
        IMPL.a(popupWindow, view, i, i2, i3);
    }

    public static void setOverlapAnchor(PopupWindow popupWindow, boolean z) {
        IMPL.a(popupWindow, z);
    }

    public static boolean getOverlapAnchor(PopupWindow popupWindow) {
        return IMPL.a(popupWindow);
    }

    public static void setWindowLayoutType(PopupWindow popupWindow, int i) {
        IMPL.a(popupWindow, i);
    }

    public static int getWindowLayoutType(PopupWindow popupWindow) {
        return IMPL.b(popupWindow);
    }
}
