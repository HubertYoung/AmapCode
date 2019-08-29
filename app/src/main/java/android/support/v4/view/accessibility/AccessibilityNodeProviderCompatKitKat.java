package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

class AccessibilityNodeProviderCompatKitKat {

    interface AccessibilityNodeInfoBridge {
        Object a(int i);

        List<Object> a(String str, int i);

        boolean a(int i, int i2, Bundle bundle);

        Object b(int i);
    }

    AccessibilityNodeProviderCompatKitKat() {
    }

    public static Object a(final AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
        return new AccessibilityNodeProvider() {
            public final AccessibilityNodeInfo createAccessibilityNodeInfo(int i) {
                return (AccessibilityNodeInfo) accessibilityNodeInfoBridge.a(i);
            }

            public final List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String str, int i) {
                return accessibilityNodeInfoBridge.a(str, i);
            }

            public final boolean performAction(int i, int i2, Bundle bundle) {
                return accessibilityNodeInfoBridge.a(i, i2, bundle);
            }

            public final AccessibilityNodeInfo findFocus(int i) {
                return (AccessibilityNodeInfo) accessibilityNodeInfoBridge.b(i);
            }
        };
    }
}
