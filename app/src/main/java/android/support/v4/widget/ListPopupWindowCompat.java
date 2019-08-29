package android.support.v4.widget;

import android.os.Build.VERSION;
import android.view.View;
import android.view.View.OnTouchListener;

public class ListPopupWindowCompat {
    static final ListPopupWindowImpl IMPL;

    static class BaseListPopupWindowImpl implements ListPopupWindowImpl {
        public OnTouchListener a(Object obj, View view) {
            return null;
        }

        BaseListPopupWindowImpl() {
        }
    }

    static class KitKatListPopupWindowImpl extends BaseListPopupWindowImpl {
        KitKatListPopupWindowImpl() {
        }

        public final OnTouchListener a(Object obj, View view) {
            return ListPopupWindowCompatKitKat.a(obj, view);
        }
    }

    interface ListPopupWindowImpl {
        OnTouchListener a(Object obj, View view);
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new KitKatListPopupWindowImpl();
        } else {
            IMPL = new BaseListPopupWindowImpl();
        }
    }

    private ListPopupWindowCompat() {
    }

    public static OnTouchListener createDragToOpenListener(Object obj, View view) {
        return IMPL.a(obj, view);
    }
}
