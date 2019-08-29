package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View.OnTouchListener;

public class PopupMenuCompat {
    static final PopupMenuImpl IMPL;

    static class BasePopupMenuImpl implements PopupMenuImpl {
        public OnTouchListener a(Object obj) {
            return null;
        }

        BasePopupMenuImpl() {
        }
    }

    static class KitKatPopupMenuImpl extends BasePopupMenuImpl {
        KitKatPopupMenuImpl() {
        }

        public final OnTouchListener a(Object obj) {
            return PopupMenuCompatKitKat.a(obj);
        }
    }

    interface PopupMenuImpl {
        OnTouchListener a(Object obj);
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new KitKatPopupMenuImpl();
        } else {
            IMPL = new BasePopupMenuImpl();
        }
    }

    private PopupMenuCompat() {
    }

    public static OnTouchListener getDragToOpenListener(Object obj) {
        return IMPL.a(obj);
    }
}
