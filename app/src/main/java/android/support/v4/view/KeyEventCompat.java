package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.KeyEvent;
import android.view.KeyEvent.Callback;
import android.view.View;

public class KeyEventCompat {
    static final KeyEventVersionImpl IMPL;

    static class BaseKeyEventVersionImpl implements KeyEventVersionImpl {
        public int a(int i) {
            if ((i & 192) != 0) {
                i |= 1;
            }
            if ((i & 48) != 0) {
                i |= 2;
            }
            return i & 247;
        }

        public Object a(View view) {
            return null;
        }

        public void a(KeyEvent keyEvent) {
        }

        public boolean b(KeyEvent keyEvent) {
            return false;
        }

        BaseKeyEventVersionImpl() {
        }

        private static int a(int i, int i2, int i3, int i4, int i5) {
            boolean z = false;
            boolean z2 = (i2 & i3) != 0;
            int i6 = i4 | i5;
            if ((i2 & i6) != 0) {
                z = true;
            }
            if (!z2) {
                return z ? i & (~i3) : i;
            }
            if (!z) {
                return i & (~i6);
            }
            throw new IllegalArgumentException("bad arguments");
        }

        public boolean a(int i, int i2) {
            if (a(a(a(i) & 247, i2, 1, 64, 128), i2, 2, 16, 32) == i2) {
                return true;
            }
            return false;
        }

        public boolean b(int i) {
            return (a(i) & 247) == 0;
        }

        public boolean a(KeyEvent keyEvent, Callback callback, Object obj, Object obj2) {
            return keyEvent.dispatch(callback);
        }
    }

    static class EclairKeyEventVersionImpl extends BaseKeyEventVersionImpl {
        EclairKeyEventVersionImpl() {
        }

        public final void a(KeyEvent keyEvent) {
            KeyEventCompatEclair.a(keyEvent);
        }

        public final boolean b(KeyEvent keyEvent) {
            return KeyEventCompatEclair.b(keyEvent);
        }

        public final Object a(View view) {
            return KeyEventCompatEclair.a(view);
        }

        public final boolean a(KeyEvent keyEvent, Callback callback, Object obj, Object obj2) {
            return KeyEventCompatEclair.a(keyEvent, callback, obj, obj2);
        }
    }

    static class HoneycombKeyEventVersionImpl extends EclairKeyEventVersionImpl {
        HoneycombKeyEventVersionImpl() {
        }

        public final int a(int i) {
            return KeyEventCompatHoneycomb.a(i);
        }

        public final boolean a(int i, int i2) {
            return KeyEventCompatHoneycomb.a(i, i2);
        }

        public final boolean b(int i) {
            return KeyEventCompatHoneycomb.b(i);
        }
    }

    interface KeyEventVersionImpl {
        int a(int i);

        Object a(View view);

        void a(KeyEvent keyEvent);

        boolean a(int i, int i2);

        boolean a(KeyEvent keyEvent, Callback callback, Object obj, Object obj2);

        boolean b(int i);

        boolean b(KeyEvent keyEvent);
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombKeyEventVersionImpl();
        } else {
            IMPL = new BaseKeyEventVersionImpl();
        }
    }

    public static int normalizeMetaState(int i) {
        return IMPL.a(i);
    }

    public static boolean metaStateHasModifiers(int i, int i2) {
        return IMPL.a(i, i2);
    }

    public static boolean metaStateHasNoModifiers(int i) {
        return IMPL.b(i);
    }

    public static boolean hasModifiers(KeyEvent keyEvent, int i) {
        return IMPL.a(keyEvent.getMetaState(), i);
    }

    public static boolean hasNoModifiers(KeyEvent keyEvent) {
        return IMPL.b(keyEvent.getMetaState());
    }

    public static void startTracking(KeyEvent keyEvent) {
        IMPL.a(keyEvent);
    }

    public static boolean isTracking(KeyEvent keyEvent) {
        return IMPL.b(keyEvent);
    }

    public static Object getKeyDispatcherState(View view) {
        return IMPL.a(view);
    }

    public static boolean dispatch(KeyEvent keyEvent, Callback callback, Object obj, Object obj2) {
        return IMPL.a(keyEvent, callback, obj, obj2);
    }
}
