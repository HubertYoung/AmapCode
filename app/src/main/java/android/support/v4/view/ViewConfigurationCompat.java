package android.support.v4.view;

import android.os.Build.VERSION;
import android.view.ViewConfiguration;

public class ViewConfigurationCompat {
    static final ViewConfigurationVersionImpl IMPL;

    static class BaseViewConfigurationVersionImpl implements ViewConfigurationVersionImpl {
        public boolean b(ViewConfiguration viewConfiguration) {
            return true;
        }

        BaseViewConfigurationVersionImpl() {
        }

        public int a(ViewConfiguration viewConfiguration) {
            return viewConfiguration.getScaledTouchSlop();
        }
    }

    static class FroyoViewConfigurationVersionImpl extends BaseViewConfigurationVersionImpl {
        FroyoViewConfigurationVersionImpl() {
        }

        public final int a(ViewConfiguration viewConfiguration) {
            return ViewConfigurationCompatFroyo.a(viewConfiguration);
        }
    }

    static class HoneycombViewConfigurationVersionImpl extends FroyoViewConfigurationVersionImpl {
        public boolean b(ViewConfiguration viewConfiguration) {
            return false;
        }

        HoneycombViewConfigurationVersionImpl() {
        }
    }

    static class IcsViewConfigurationVersionImpl extends HoneycombViewConfigurationVersionImpl {
        IcsViewConfigurationVersionImpl() {
        }

        public final boolean b(ViewConfiguration viewConfiguration) {
            return ViewConfigurationCompatICS.a(viewConfiguration);
        }
    }

    interface ViewConfigurationVersionImpl {
        int a(ViewConfiguration viewConfiguration);

        boolean b(ViewConfiguration viewConfiguration);
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new IcsViewConfigurationVersionImpl();
        } else if (VERSION.SDK_INT >= 11) {
            IMPL = new HoneycombViewConfigurationVersionImpl();
        } else if (VERSION.SDK_INT >= 8) {
            IMPL = new FroyoViewConfigurationVersionImpl();
        } else {
            IMPL = new BaseViewConfigurationVersionImpl();
        }
    }

    public static int getScaledPagingTouchSlop(ViewConfiguration viewConfiguration) {
        return IMPL.a(viewConfiguration);
    }

    public static boolean hasPermanentMenuKey(ViewConfiguration viewConfiguration) {
        return IMPL.b(viewConfiguration);
    }
}
