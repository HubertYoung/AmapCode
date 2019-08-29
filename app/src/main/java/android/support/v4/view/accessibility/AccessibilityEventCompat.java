package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.view.accessibility.AccessibilityEvent;

public class AccessibilityEventCompat {
    public static final int CONTENT_CHANGE_TYPE_CONTENT_DESCRIPTION = 4;
    public static final int CONTENT_CHANGE_TYPE_SUBTREE = 1;
    public static final int CONTENT_CHANGE_TYPE_TEXT = 2;
    public static final int CONTENT_CHANGE_TYPE_UNDEFINED = 0;
    private static final AccessibilityEventVersionImpl IMPL;
    public static final int TYPES_ALL_MASK = -1;
    public static final int TYPE_ANNOUNCEMENT = 16384;
    public static final int TYPE_GESTURE_DETECTION_END = 524288;
    public static final int TYPE_GESTURE_DETECTION_START = 262144;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_END = 1024;
    public static final int TYPE_TOUCH_EXPLORATION_GESTURE_START = 512;
    public static final int TYPE_TOUCH_INTERACTION_END = 2097152;
    public static final int TYPE_TOUCH_INTERACTION_START = 1048576;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUSED = 32768;
    public static final int TYPE_VIEW_ACCESSIBILITY_FOCUS_CLEARED = 65536;
    public static final int TYPE_VIEW_HOVER_ENTER = 128;
    public static final int TYPE_VIEW_HOVER_EXIT = 256;
    public static final int TYPE_VIEW_SCROLLED = 4096;
    public static final int TYPE_VIEW_TEXT_SELECTION_CHANGED = 8192;
    public static final int TYPE_VIEW_TEXT_TRAVERSED_AT_MOVEMENT_GRANULARITY = 131072;
    public static final int TYPE_WINDOW_CONTENT_CHANGED = 2048;

    static class AccessibilityEventIcsImpl extends AccessibilityEventStubImpl {
        AccessibilityEventIcsImpl() {
        }

        public final void a(AccessibilityEvent accessibilityEvent, Object obj) {
            AccessibilityEventCompatIcs.a(accessibilityEvent, obj);
        }

        public final Object a(AccessibilityEvent accessibilityEvent, int i) {
            return AccessibilityEventCompatIcs.a(accessibilityEvent, i);
        }

        public final int a(AccessibilityEvent accessibilityEvent) {
            return AccessibilityEventCompatIcs.a(accessibilityEvent);
        }
    }

    static class AccessibilityEventKitKatImpl extends AccessibilityEventIcsImpl {
        AccessibilityEventKitKatImpl() {
        }

        public final void b(AccessibilityEvent accessibilityEvent, int i) {
            AccessibilityEventCompatKitKat.a(accessibilityEvent, i);
        }

        public final int b(AccessibilityEvent accessibilityEvent) {
            return AccessibilityEventCompatKitKat.a(accessibilityEvent);
        }
    }

    static class AccessibilityEventStubImpl implements AccessibilityEventVersionImpl {
        public int a(AccessibilityEvent accessibilityEvent) {
            return 0;
        }

        public Object a(AccessibilityEvent accessibilityEvent, int i) {
            return null;
        }

        public void a(AccessibilityEvent accessibilityEvent, Object obj) {
        }

        public int b(AccessibilityEvent accessibilityEvent) {
            return 0;
        }

        public void b(AccessibilityEvent accessibilityEvent, int i) {
        }

        AccessibilityEventStubImpl() {
        }
    }

    interface AccessibilityEventVersionImpl {
        int a(AccessibilityEvent accessibilityEvent);

        Object a(AccessibilityEvent accessibilityEvent, int i);

        void a(AccessibilityEvent accessibilityEvent, Object obj);

        int b(AccessibilityEvent accessibilityEvent);

        void b(AccessibilityEvent accessibilityEvent, int i);
    }

    static {
        if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityEventKitKatImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityEventIcsImpl();
        } else {
            IMPL = new AccessibilityEventStubImpl();
        }
    }

    private AccessibilityEventCompat() {
    }

    public static int getRecordCount(AccessibilityEvent accessibilityEvent) {
        return IMPL.a(accessibilityEvent);
    }

    public static void appendRecord(AccessibilityEvent accessibilityEvent, AccessibilityRecordCompat accessibilityRecordCompat) {
        IMPL.a(accessibilityEvent, accessibilityRecordCompat.getImpl());
    }

    public static AccessibilityRecordCompat getRecord(AccessibilityEvent accessibilityEvent, int i) {
        return new AccessibilityRecordCompat(IMPL.a(accessibilityEvent, i));
    }

    public static AccessibilityRecordCompat asRecord(AccessibilityEvent accessibilityEvent) {
        return new AccessibilityRecordCompat(accessibilityEvent);
    }

    public static void setContentChangeTypes(AccessibilityEvent accessibilityEvent, int i) {
        IMPL.b(accessibilityEvent, i);
    }

    public static int getContentChangeTypes(AccessibilityEvent accessibilityEvent) {
        return IMPL.b(accessibilityEvent);
    }
}
