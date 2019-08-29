package android.support.v4.view;

import android.view.KeyEvent;

class KeyEventCompatHoneycomb {
    KeyEventCompatHoneycomb() {
    }

    public static int a(int i) {
        return KeyEvent.normalizeMetaState(i);
    }

    public static boolean a(int i, int i2) {
        return KeyEvent.metaStateHasModifiers(i, i2);
    }

    public static boolean b(int i) {
        return KeyEvent.metaStateHasNoModifiers(i);
    }
}
