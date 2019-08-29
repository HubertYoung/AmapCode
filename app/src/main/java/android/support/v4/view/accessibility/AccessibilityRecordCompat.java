package android.support.v4.view.accessibility;

import android.os.Build.VERSION;
import android.os.Parcelable;
import android.view.View;
import java.util.Collections;
import java.util.List;

public class AccessibilityRecordCompat {
    private static final AccessibilityRecordImpl IMPL;
    private final Object mRecord;

    static class AccessibilityRecordIcsImpl extends AccessibilityRecordStubImpl {
        AccessibilityRecordIcsImpl() {
        }

        public final Object a() {
            return AccessibilityRecordCompatIcs.a();
        }

        public final Object a(Object obj) {
            return AccessibilityRecordCompatIcs.a(obj);
        }

        public final int b(Object obj) {
            return AccessibilityRecordCompatIcs.b(obj);
        }

        public final CharSequence c(Object obj) {
            return AccessibilityRecordCompatIcs.c(obj);
        }

        public final CharSequence d(Object obj) {
            return AccessibilityRecordCompatIcs.d(obj);
        }

        public final CharSequence e(Object obj) {
            return AccessibilityRecordCompatIcs.e(obj);
        }

        public final int f(Object obj) {
            return AccessibilityRecordCompatIcs.f(obj);
        }

        public final int g(Object obj) {
            return AccessibilityRecordCompatIcs.g(obj);
        }

        public final int h(Object obj) {
            return AccessibilityRecordCompatIcs.h(obj);
        }

        public final Parcelable i(Object obj) {
            return AccessibilityRecordCompatIcs.i(obj);
        }

        public final int j(Object obj) {
            return AccessibilityRecordCompatIcs.j(obj);
        }

        public final int k(Object obj) {
            return AccessibilityRecordCompatIcs.k(obj);
        }

        public final int l(Object obj) {
            return AccessibilityRecordCompatIcs.l(obj);
        }

        public final AccessibilityNodeInfoCompat m(Object obj) {
            return AccessibilityNodeInfoCompat.wrapNonNullInstance(AccessibilityRecordCompatIcs.m(obj));
        }

        public final List<CharSequence> n(Object obj) {
            return AccessibilityRecordCompatIcs.n(obj);
        }

        public final int o(Object obj) {
            return AccessibilityRecordCompatIcs.o(obj);
        }

        public final int p(Object obj) {
            return AccessibilityRecordCompatIcs.p(obj);
        }

        public final boolean q(Object obj) {
            return AccessibilityRecordCompatIcs.q(obj);
        }

        public final boolean r(Object obj) {
            return AccessibilityRecordCompatIcs.r(obj);
        }

        public final boolean s(Object obj) {
            return AccessibilityRecordCompatIcs.s(obj);
        }

        public final boolean t(Object obj) {
            return AccessibilityRecordCompatIcs.t(obj);
        }

        public final boolean u(Object obj) {
            return AccessibilityRecordCompatIcs.u(obj);
        }

        public final void v(Object obj) {
            AccessibilityRecordCompatIcs.v(obj);
        }

        public final void a(Object obj, int i) {
            AccessibilityRecordCompatIcs.a(obj, i);
        }

        public final void a(Object obj, CharSequence charSequence) {
            AccessibilityRecordCompatIcs.a(obj, charSequence);
        }

        public final void a(Object obj, boolean z) {
            AccessibilityRecordCompatIcs.a(obj, z);
        }

        public final void b(Object obj, CharSequence charSequence) {
            AccessibilityRecordCompatIcs.b(obj, charSequence);
        }

        public final void c(Object obj, CharSequence charSequence) {
            AccessibilityRecordCompatIcs.c(obj, charSequence);
        }

        public final void b(Object obj, int i) {
            AccessibilityRecordCompatIcs.b(obj, i);
        }

        public final void b(Object obj, boolean z) {
            AccessibilityRecordCompatIcs.b(obj, z);
        }

        public final void c(Object obj, int i) {
            AccessibilityRecordCompatIcs.c(obj, i);
        }

        public final void c(Object obj, boolean z) {
            AccessibilityRecordCompatIcs.c(obj, z);
        }

        public final void d(Object obj, int i) {
            AccessibilityRecordCompatIcs.d(obj, i);
        }

        public final void a(Object obj, Parcelable parcelable) {
            AccessibilityRecordCompatIcs.a(obj, parcelable);
        }

        public final void d(Object obj, boolean z) {
            AccessibilityRecordCompatIcs.d(obj, z);
        }

        public final void e(Object obj, int i) {
            AccessibilityRecordCompatIcs.e(obj, i);
        }

        public final void f(Object obj, int i) {
            AccessibilityRecordCompatIcs.f(obj, i);
        }

        public final void g(Object obj, int i) {
            AccessibilityRecordCompatIcs.g(obj, i);
        }

        public final void e(Object obj, boolean z) {
            AccessibilityRecordCompatIcs.e(obj, z);
        }

        public final void a(Object obj, View view) {
            AccessibilityRecordCompatIcs.a(obj, view);
        }

        public final void h(Object obj, int i) {
            AccessibilityRecordCompatIcs.h(obj, i);
        }
    }

    static class AccessibilityRecordIcsMr1Impl extends AccessibilityRecordIcsImpl {
        AccessibilityRecordIcsMr1Impl() {
        }

        public final int w(Object obj) {
            return AccessibilityRecordCompatIcsMr1.a(obj);
        }

        public final int x(Object obj) {
            return AccessibilityRecordCompatIcsMr1.b(obj);
        }

        public final void i(Object obj, int i) {
            AccessibilityRecordCompatIcsMr1.a(obj, i);
        }

        public final void j(Object obj, int i) {
            AccessibilityRecordCompatIcsMr1.b(obj, i);
        }
    }

    interface AccessibilityRecordImpl {
        Object a();

        Object a(Object obj);

        void a(Object obj, int i);

        void a(Object obj, Parcelable parcelable);

        void a(Object obj, View view);

        void a(Object obj, View view, int i);

        void a(Object obj, CharSequence charSequence);

        void a(Object obj, boolean z);

        int b(Object obj);

        void b(Object obj, int i);

        void b(Object obj, CharSequence charSequence);

        void b(Object obj, boolean z);

        CharSequence c(Object obj);

        void c(Object obj, int i);

        void c(Object obj, CharSequence charSequence);

        void c(Object obj, boolean z);

        CharSequence d(Object obj);

        void d(Object obj, int i);

        void d(Object obj, boolean z);

        CharSequence e(Object obj);

        void e(Object obj, int i);

        void e(Object obj, boolean z);

        int f(Object obj);

        void f(Object obj, int i);

        int g(Object obj);

        void g(Object obj, int i);

        int h(Object obj);

        void h(Object obj, int i);

        Parcelable i(Object obj);

        void i(Object obj, int i);

        int j(Object obj);

        void j(Object obj, int i);

        int k(Object obj);

        int l(Object obj);

        AccessibilityNodeInfoCompat m(Object obj);

        List<CharSequence> n(Object obj);

        int o(Object obj);

        int p(Object obj);

        boolean q(Object obj);

        boolean r(Object obj);

        boolean s(Object obj);

        boolean t(Object obj);

        boolean u(Object obj);

        void v(Object obj);

        int w(Object obj);

        int x(Object obj);
    }

    static class AccessibilityRecordJellyBeanImpl extends AccessibilityRecordIcsMr1Impl {
        AccessibilityRecordJellyBeanImpl() {
        }

        public final void a(Object obj, View view, int i) {
            AccessibilityRecordCompatJellyBean.a(obj, view, i);
        }
    }

    static class AccessibilityRecordStubImpl implements AccessibilityRecordImpl {
        public Object a() {
            return null;
        }

        public Object a(Object obj) {
            return null;
        }

        public void a(Object obj, int i) {
        }

        public void a(Object obj, Parcelable parcelable) {
        }

        public void a(Object obj, View view) {
        }

        public void a(Object obj, View view, int i) {
        }

        public void a(Object obj, CharSequence charSequence) {
        }

        public void a(Object obj, boolean z) {
        }

        public int b(Object obj) {
            return 0;
        }

        public void b(Object obj, int i) {
        }

        public void b(Object obj, CharSequence charSequence) {
        }

        public void b(Object obj, boolean z) {
        }

        public CharSequence c(Object obj) {
            return null;
        }

        public void c(Object obj, int i) {
        }

        public void c(Object obj, CharSequence charSequence) {
        }

        public void c(Object obj, boolean z) {
        }

        public CharSequence d(Object obj) {
            return null;
        }

        public void d(Object obj, int i) {
        }

        public void d(Object obj, boolean z) {
        }

        public CharSequence e(Object obj) {
            return null;
        }

        public void e(Object obj, int i) {
        }

        public void e(Object obj, boolean z) {
        }

        public int f(Object obj) {
            return 0;
        }

        public void f(Object obj, int i) {
        }

        public int g(Object obj) {
            return 0;
        }

        public void g(Object obj, int i) {
        }

        public int h(Object obj) {
            return 0;
        }

        public void h(Object obj, int i) {
        }

        public Parcelable i(Object obj) {
            return null;
        }

        public void i(Object obj, int i) {
        }

        public int j(Object obj) {
            return 0;
        }

        public void j(Object obj, int i) {
        }

        public int k(Object obj) {
            return 0;
        }

        public int l(Object obj) {
            return 0;
        }

        public AccessibilityNodeInfoCompat m(Object obj) {
            return null;
        }

        public int o(Object obj) {
            return 0;
        }

        public int p(Object obj) {
            return 0;
        }

        public boolean q(Object obj) {
            return false;
        }

        public boolean r(Object obj) {
            return false;
        }

        public boolean s(Object obj) {
            return false;
        }

        public boolean t(Object obj) {
            return false;
        }

        public boolean u(Object obj) {
            return false;
        }

        public void v(Object obj) {
        }

        public int w(Object obj) {
            return 0;
        }

        public int x(Object obj) {
            return 0;
        }

        AccessibilityRecordStubImpl() {
        }

        public List<CharSequence> n(Object obj) {
            return Collections.emptyList();
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityRecordJellyBeanImpl();
        } else if (VERSION.SDK_INT >= 15) {
            IMPL = new AccessibilityRecordIcsMr1Impl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityRecordIcsImpl();
        } else {
            IMPL = new AccessibilityRecordStubImpl();
        }
    }

    public AccessibilityRecordCompat(Object obj) {
        this.mRecord = obj;
    }

    public Object getImpl() {
        return this.mRecord;
    }

    public static AccessibilityRecordCompat obtain(AccessibilityRecordCompat accessibilityRecordCompat) {
        return new AccessibilityRecordCompat(IMPL.a(accessibilityRecordCompat.mRecord));
    }

    public static AccessibilityRecordCompat obtain() {
        return new AccessibilityRecordCompat(IMPL.a());
    }

    public void setSource(View view) {
        IMPL.a(this.mRecord, view);
    }

    public void setSource(View view, int i) {
        IMPL.a(this.mRecord, view, i);
    }

    public AccessibilityNodeInfoCompat getSource() {
        return IMPL.m(this.mRecord);
    }

    public int getWindowId() {
        return IMPL.p(this.mRecord);
    }

    public boolean isChecked() {
        return IMPL.q(this.mRecord);
    }

    public void setChecked(boolean z) {
        IMPL.a(this.mRecord, z);
    }

    public boolean isEnabled() {
        return IMPL.r(this.mRecord);
    }

    public void setEnabled(boolean z) {
        IMPL.b(this.mRecord, z);
    }

    public boolean isPassword() {
        return IMPL.t(this.mRecord);
    }

    public void setPassword(boolean z) {
        IMPL.d(this.mRecord, z);
    }

    public boolean isFullScreen() {
        return IMPL.s(this.mRecord);
    }

    public void setFullScreen(boolean z) {
        IMPL.c(this.mRecord, z);
    }

    public boolean isScrollable() {
        return IMPL.u(this.mRecord);
    }

    public void setScrollable(boolean z) {
        IMPL.e(this.mRecord, z);
    }

    public int getItemCount() {
        return IMPL.h(this.mRecord);
    }

    public void setItemCount(int i) {
        IMPL.d(this.mRecord, i);
    }

    public int getCurrentItemIndex() {
        return IMPL.f(this.mRecord);
    }

    public void setCurrentItemIndex(int i) {
        IMPL.b(this.mRecord, i);
    }

    public int getFromIndex() {
        return IMPL.g(this.mRecord);
    }

    public void setFromIndex(int i) {
        IMPL.c(this.mRecord, i);
    }

    public int getToIndex() {
        return IMPL.o(this.mRecord);
    }

    public void setToIndex(int i) {
        IMPL.h(this.mRecord, i);
    }

    public int getScrollX() {
        return IMPL.k(this.mRecord);
    }

    public void setScrollX(int i) {
        IMPL.f(this.mRecord, i);
    }

    public int getScrollY() {
        return IMPL.l(this.mRecord);
    }

    public void setScrollY(int i) {
        IMPL.g(this.mRecord, i);
    }

    public int getMaxScrollX() {
        return IMPL.w(this.mRecord);
    }

    public void setMaxScrollX(int i) {
        IMPL.i(this.mRecord, i);
    }

    public int getMaxScrollY() {
        return IMPL.x(this.mRecord);
    }

    public void setMaxScrollY(int i) {
        IMPL.j(this.mRecord, i);
    }

    public int getAddedCount() {
        return IMPL.b(this.mRecord);
    }

    public void setAddedCount(int i) {
        IMPL.a(this.mRecord, i);
    }

    public int getRemovedCount() {
        return IMPL.j(this.mRecord);
    }

    public void setRemovedCount(int i) {
        IMPL.e(this.mRecord, i);
    }

    public CharSequence getClassName() {
        return IMPL.d(this.mRecord);
    }

    public void setClassName(CharSequence charSequence) {
        IMPL.b(this.mRecord, charSequence);
    }

    public List<CharSequence> getText() {
        return IMPL.n(this.mRecord);
    }

    public CharSequence getBeforeText() {
        return IMPL.c(this.mRecord);
    }

    public void setBeforeText(CharSequence charSequence) {
        IMPL.a(this.mRecord, charSequence);
    }

    public CharSequence getContentDescription() {
        return IMPL.e(this.mRecord);
    }

    public void setContentDescription(CharSequence charSequence) {
        IMPL.c(this.mRecord, charSequence);
    }

    public Parcelable getParcelableData() {
        return IMPL.i(this.mRecord);
    }

    public void setParcelableData(Parcelable parcelable) {
        IMPL.a(this.mRecord, parcelable);
    }

    public void recycle() {
        IMPL.v(this.mRecord);
    }

    public int hashCode() {
        if (this.mRecord == null) {
            return 0;
        }
        return this.mRecord.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AccessibilityRecordCompat accessibilityRecordCompat = (AccessibilityRecordCompat) obj;
        if (this.mRecord == null) {
            if (accessibilityRecordCompat.mRecord != null) {
                return false;
            }
        } else if (!this.mRecord.equals(accessibilityRecordCompat.mRecord)) {
            return false;
        }
        return true;
    }
}
