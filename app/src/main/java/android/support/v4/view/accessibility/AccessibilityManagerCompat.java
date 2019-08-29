package android.support.v4.view.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityManager;
import java.util.Collections;
import java.util.List;

public class AccessibilityManagerCompat {
    /* access modifiers changed from: private */
    public static final AccessibilityManagerVersionImpl IMPL;

    static class AccessibilityManagerIcsImpl extends AccessibilityManagerStubImpl {
        AccessibilityManagerIcsImpl() {
        }

        public final Object a(final AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
            return AccessibilityManagerCompatIcs.a((AccessibilityStateChangeListenerBridge) new AccessibilityStateChangeListenerBridge() {
                public final void a(boolean z) {
                    accessibilityStateChangeListenerCompat.onAccessibilityStateChanged(z);
                }
            });
        }

        public final boolean a(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
            return AccessibilityManagerCompatIcs.a(accessibilityManager, accessibilityStateChangeListenerCompat.mListener);
        }

        public final boolean b(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
            return AccessibilityManagerCompatIcs.b(accessibilityManager, accessibilityStateChangeListenerCompat.mListener);
        }

        public final List<AccessibilityServiceInfo> a(AccessibilityManager accessibilityManager, int i) {
            return AccessibilityManagerCompatIcs.a(accessibilityManager, i);
        }

        public final List<AccessibilityServiceInfo> a(AccessibilityManager accessibilityManager) {
            return AccessibilityManagerCompatIcs.a(accessibilityManager);
        }

        public final boolean b(AccessibilityManager accessibilityManager) {
            return AccessibilityManagerCompatIcs.b(accessibilityManager);
        }
    }

    static class AccessibilityManagerStubImpl implements AccessibilityManagerVersionImpl {
        public Object a(AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
            return null;
        }

        public boolean a(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
            return false;
        }

        public boolean b(AccessibilityManager accessibilityManager) {
            return false;
        }

        public boolean b(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
            return false;
        }

        AccessibilityManagerStubImpl() {
        }

        public List<AccessibilityServiceInfo> a(AccessibilityManager accessibilityManager, int i) {
            return Collections.emptyList();
        }

        public List<AccessibilityServiceInfo> a(AccessibilityManager accessibilityManager) {
            return Collections.emptyList();
        }
    }

    interface AccessibilityManagerVersionImpl {
        Object a(AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat);

        List<AccessibilityServiceInfo> a(AccessibilityManager accessibilityManager);

        List<AccessibilityServiceInfo> a(AccessibilityManager accessibilityManager, int i);

        boolean a(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat);

        boolean b(AccessibilityManager accessibilityManager);

        boolean b(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat);
    }

    public static abstract class AccessibilityStateChangeListenerCompat {
        final Object mListener = AccessibilityManagerCompat.IMPL.a(this);

        public abstract void onAccessibilityStateChanged(boolean z);
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityManagerIcsImpl();
        } else {
            IMPL = new AccessibilityManagerStubImpl();
        }
    }

    public static boolean addAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
        return IMPL.a(accessibilityManager, accessibilityStateChangeListenerCompat);
    }

    public static boolean removeAccessibilityStateChangeListener(AccessibilityManager accessibilityManager, AccessibilityStateChangeListenerCompat accessibilityStateChangeListenerCompat) {
        return IMPL.b(accessibilityManager, accessibilityStateChangeListenerCompat);
    }

    public static List<AccessibilityServiceInfo> getInstalledAccessibilityServiceList(AccessibilityManager accessibilityManager) {
        return IMPL.a(accessibilityManager);
    }

    public static List<AccessibilityServiceInfo> getEnabledAccessibilityServiceList(AccessibilityManager accessibilityManager, int i) {
        return IMPL.a(accessibilityManager, i);
    }

    public static boolean isTouchExplorationEnabled(AccessibilityManager accessibilityManager) {
        return IMPL.b(accessibilityManager);
    }
}
