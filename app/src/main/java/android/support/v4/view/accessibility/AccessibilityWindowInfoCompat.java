package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;

public class AccessibilityWindowInfoCompat {
    private static final AccessibilityWindowInfoImpl IMPL;
    public static final int TYPE_ACCESSIBILITY_OVERLAY = 4;
    public static final int TYPE_APPLICATION = 1;
    public static final int TYPE_INPUT_METHOD = 2;
    public static final int TYPE_SYSTEM = 3;
    private static final int UNDEFINED = -1;
    private Object mInfo;

    static class AccessibilityWindowInfoApi21Impl extends AccessibilityWindowInfoStubImpl {
        private AccessibilityWindowInfoApi21Impl() {
            super(0);
        }

        /* synthetic */ AccessibilityWindowInfoApi21Impl(byte b) {
            this();
        }

        public final Object a() {
            return AccessibilityWindowInfoCompatApi21.a();
        }

        public final Object a(Object obj) {
            return AccessibilityWindowInfoCompatApi21.a(obj);
        }

        public final int b(Object obj) {
            return AccessibilityWindowInfoCompatApi21.b(obj);
        }

        public final int c(Object obj) {
            return AccessibilityWindowInfoCompatApi21.c(obj);
        }

        public final Object d(Object obj) {
            return AccessibilityWindowInfoCompatApi21.d(obj);
        }

        public final Object e(Object obj) {
            return AccessibilityWindowInfoCompatApi21.e(obj);
        }

        public final int f(Object obj) {
            return AccessibilityWindowInfoCompatApi21.f(obj);
        }

        public final void a(Object obj, Rect rect) {
            AccessibilityWindowInfoCompatApi21.a(obj, rect);
        }

        public final boolean g(Object obj) {
            return AccessibilityWindowInfoCompatApi21.g(obj);
        }

        public final boolean h(Object obj) {
            return AccessibilityWindowInfoCompatApi21.h(obj);
        }

        public final boolean i(Object obj) {
            return AccessibilityWindowInfoCompatApi21.i(obj);
        }

        public final int j(Object obj) {
            return AccessibilityWindowInfoCompatApi21.j(obj);
        }

        public final Object a(Object obj, int i) {
            return AccessibilityWindowInfoCompatApi21.a(obj, i);
        }

        public final void k(Object obj) {
            AccessibilityWindowInfoCompatApi21.k(obj);
        }
    }

    interface AccessibilityWindowInfoImpl {
        Object a();

        Object a(Object obj);

        Object a(Object obj, int i);

        void a(Object obj, Rect rect);

        int b(Object obj);

        int c(Object obj);

        Object d(Object obj);

        Object e(Object obj);

        int f(Object obj);

        boolean g(Object obj);

        boolean h(Object obj);

        boolean i(Object obj);

        int j(Object obj);

        void k(Object obj);
    }

    static class AccessibilityWindowInfoStubImpl implements AccessibilityWindowInfoImpl {
        public Object a() {
            return null;
        }

        public Object a(Object obj) {
            return null;
        }

        public Object a(Object obj, int i) {
            return null;
        }

        public void a(Object obj, Rect rect) {
        }

        public int b(Object obj) {
            return -1;
        }

        public int c(Object obj) {
            return -1;
        }

        public Object d(Object obj) {
            return null;
        }

        public Object e(Object obj) {
            return null;
        }

        public int f(Object obj) {
            return -1;
        }

        public boolean g(Object obj) {
            return true;
        }

        public boolean h(Object obj) {
            return true;
        }

        public boolean i(Object obj) {
            return true;
        }

        public int j(Object obj) {
            return 0;
        }

        public void k(Object obj) {
        }

        private AccessibilityWindowInfoStubImpl() {
        }

        /* synthetic */ AccessibilityWindowInfoStubImpl(byte b) {
            this();
        }
    }

    private static String typeToString(int i) {
        switch (i) {
            case 1:
                return "TYPE_APPLICATION";
            case 2:
                return "TYPE_INPUT_METHOD";
            case 3:
                return "TYPE_SYSTEM";
            case 4:
                return "TYPE_ACCESSIBILITY_OVERLAY";
            default:
                return "<UNKNOWN>";
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityWindowInfoApi21Impl(0);
        } else {
            IMPL = new AccessibilityWindowInfoStubImpl(0);
        }
    }

    static AccessibilityWindowInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return new AccessibilityWindowInfoCompat(obj);
        }
        return null;
    }

    private AccessibilityWindowInfoCompat(Object obj) {
        this.mInfo = obj;
    }

    public int getType() {
        return IMPL.b(this.mInfo);
    }

    public int getLayer() {
        return IMPL.c(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getRoot() {
        return AccessibilityNodeInfoCompat.wrapNonNullInstance(IMPL.d(this.mInfo));
    }

    public AccessibilityWindowInfoCompat getParent() {
        return wrapNonNullInstance(IMPL.e(this.mInfo));
    }

    public int getId() {
        return IMPL.f(this.mInfo);
    }

    public void getBoundsInScreen(Rect rect) {
        IMPL.a(this.mInfo, rect);
    }

    public boolean isActive() {
        return IMPL.g(this.mInfo);
    }

    public boolean isFocused() {
        return IMPL.h(this.mInfo);
    }

    public boolean isAccessibilityFocused() {
        return IMPL.i(this.mInfo);
    }

    public int getChildCount() {
        return IMPL.j(this.mInfo);
    }

    public AccessibilityWindowInfoCompat getChild(int i) {
        return wrapNonNullInstance(IMPL.a(this.mInfo, i));
    }

    public static AccessibilityWindowInfoCompat obtain() {
        return wrapNonNullInstance(IMPL.a());
    }

    public static AccessibilityWindowInfoCompat obtain(AccessibilityWindowInfoCompat accessibilityWindowInfoCompat) {
        return wrapNonNullInstance(IMPL.a(accessibilityWindowInfoCompat.mInfo));
    }

    public void recycle() {
        IMPL.k(this.mInfo);
    }

    public int hashCode() {
        if (this.mInfo == null) {
            return 0;
        }
        return this.mInfo.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityWindowInfoCompat accessibilityWindowInfoCompat = (AccessibilityWindowInfoCompat) obj;
        if (this.mInfo == null) {
            if (accessibilityWindowInfoCompat.mInfo != null) {
                return false;
            }
        } else if (!this.mInfo.equals(accessibilityWindowInfoCompat.mInfo)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Rect rect = new Rect();
        getBoundsInScreen(rect);
        sb.append("AccessibilityWindowInfo[");
        sb.append("id=");
        sb.append(getId());
        sb.append(", type=");
        sb.append(typeToString(getType()));
        sb.append(", layer=");
        sb.append(getLayer());
        sb.append(", bounds=");
        sb.append(rect);
        sb.append(", focused=");
        sb.append(isFocused());
        sb.append(", active=");
        sb.append(isActive());
        sb.append(", hasParent=");
        boolean z = false;
        sb.append(getParent() != null);
        sb.append(", hasChildren=");
        if (getChildCount() > 0) {
            z = true;
        }
        sb.append(z);
        sb.append(']');
        return sb.toString();
    }
}
