package android.support.v4.view.accessibility;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AccessibilityNodeInfoCompat {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    /* access modifiers changed from: private */
    public static final AccessibilityNodeInfoImpl IMPL;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    private final Object mInfo;

    public static class AccessibilityActionCompat {
        public static final AccessibilityActionCompat ACTION_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(64, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityActionCompat(128, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_FOCUS = new AccessibilityActionCompat(2, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLEAR_SELECTION = new AccessibilityActionCompat(8, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CLICK = new AccessibilityActionCompat(16, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_COLLAPSE = new AccessibilityActionCompat(524288, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_COPY = new AccessibilityActionCompat(16384, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_CUT = new AccessibilityActionCompat(65536, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_DISMISS = new AccessibilityActionCompat(1048576, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_EXPAND = new AccessibilityActionCompat(262144, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_FOCUS = new AccessibilityActionCompat(1, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_LONG_CLICK = new AccessibilityActionCompat(32, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(256, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_NEXT_HTML_ELEMENT = new AccessibilityActionCompat(1024, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PASTE = new AccessibilityActionCompat(32768, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityActionCompat(512, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityActionCompat(2048, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_BACKWARD = new AccessibilityActionCompat(8192, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SCROLL_FORWARD = new AccessibilityActionCompat(4096, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SELECT = new AccessibilityActionCompat(4, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SET_SELECTION = new AccessibilityActionCompat(131072, (CharSequence) null);
        public static final AccessibilityActionCompat ACTION_SET_TEXT = new AccessibilityActionCompat(2097152, (CharSequence) null);
        /* access modifiers changed from: private */
        public final Object mAction;

        public AccessibilityActionCompat(int i, CharSequence charSequence) {
            this(AccessibilityNodeInfoCompat.IMPL.a(i, charSequence));
        }

        private AccessibilityActionCompat(Object obj) {
            this.mAction = obj;
        }

        public int getId() {
            return AccessibilityNodeInfoCompat.IMPL.b(this.mAction);
        }

        public CharSequence getLabel() {
            return AccessibilityNodeInfoCompat.IMPL.c(this.mAction);
        }
    }

    static class AccessibilityNodeInfoApi21Impl extends AccessibilityNodeInfoKitKatImpl {
        AccessibilityNodeInfoApi21Impl() {
        }

        public final Object a(int i, CharSequence charSequence) {
            return AccessibilityNodeInfoCompatApi21.a(i, charSequence);
        }

        public final List<Object> a(Object obj) {
            return AccessibilityNodeInfoCompatApi21.a(obj);
        }

        public final Object a(int i, int i2, boolean z, int i3) {
            return AccessibilityNodeInfoCompatApi21.a(i, i2, z, i3);
        }

        public final void a(Object obj, Object obj2) {
            AccessibilityNodeInfoCompatApi21.a(obj, obj2);
        }

        public final boolean b(Object obj, Object obj2) {
            return AccessibilityNodeInfoCompatApi21.b(obj, obj2);
        }

        public final int b(Object obj) {
            return AccessibilityNodeInfoCompatApi21.e(obj);
        }

        public final CharSequence c(Object obj) {
            return AccessibilityNodeInfoCompatApi21.f(obj);
        }

        public final Object a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return AccessibilityNodeInfoCompatApi21.a(i, i2, i3, i4, z, z2);
        }

        public final boolean d(Object obj) {
            return CollectionItemInfo.a(obj);
        }

        public final CharSequence e(Object obj) {
            return AccessibilityNodeInfoCompatApi21.b(obj);
        }

        public final void a(Object obj, CharSequence charSequence) {
            AccessibilityNodeInfoCompatApi21.a(obj, charSequence);
        }

        public final void a(Object obj, int i) {
            AccessibilityNodeInfoCompatApi21.a(obj, i);
        }

        public final int f(Object obj) {
            return AccessibilityNodeInfoCompatApi21.c(obj);
        }

        public final Object g(Object obj) {
            return AccessibilityNodeInfoCompatApi21.d(obj);
        }

        public final boolean a(Object obj, View view) {
            return AccessibilityNodeInfoCompatApi21.a(obj, view);
        }

        public final boolean a(Object obj, View view, int i) {
            return AccessibilityNodeInfoCompatApi21.a(obj, view, i);
        }
    }

    static class AccessibilityNodeInfoApi22Impl extends AccessibilityNodeInfoApi21Impl {
        AccessibilityNodeInfoApi22Impl() {
        }

        public final Object h(Object obj) {
            return AccessibilityNodeInfoCompatApi22.a(obj);
        }

        public final void b(Object obj, View view) {
            AccessibilityNodeInfoCompatApi22.a(obj, view);
        }

        public final void b(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatApi22.a(obj, view, i);
        }

        public final Object i(Object obj) {
            return AccessibilityNodeInfoCompatApi22.b(obj);
        }

        public final void c(Object obj, View view) {
            AccessibilityNodeInfoCompatApi22.b(obj, view);
        }

        public final void c(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatApi22.b(obj, view, i);
        }
    }

    static class AccessibilityNodeInfoIcsImpl extends AccessibilityNodeInfoStubImpl {
        AccessibilityNodeInfoIcsImpl() {
        }

        public final Object a() {
            return AccessibilityNodeInfoCompatIcs.a();
        }

        public final Object a(View view) {
            return AccessibilityNodeInfoCompatIcs.a(view);
        }

        public final Object j(Object obj) {
            return AccessibilityNodeInfoCompatIcs.a(obj);
        }

        public final void b(Object obj, int i) {
            AccessibilityNodeInfoCompatIcs.a(obj, i);
        }

        public final void d(Object obj, View view) {
            AccessibilityNodeInfoCompatIcs.a(obj, view);
        }

        public final List<Object> a(Object obj, String str) {
            return AccessibilityNodeInfoCompatIcs.a(obj, str);
        }

        public final int k(Object obj) {
            return AccessibilityNodeInfoCompatIcs.b(obj);
        }

        public final void a(Object obj, Rect rect) {
            AccessibilityNodeInfoCompatIcs.a(obj, rect);
        }

        public final void b(Object obj, Rect rect) {
            AccessibilityNodeInfoCompatIcs.b(obj, rect);
        }

        public final Object c(Object obj, int i) {
            return AccessibilityNodeInfoCompatIcs.b(obj, i);
        }

        public final int l(Object obj) {
            return AccessibilityNodeInfoCompatIcs.c(obj);
        }

        public final CharSequence m(Object obj) {
            return AccessibilityNodeInfoCompatIcs.d(obj);
        }

        public final CharSequence n(Object obj) {
            return AccessibilityNodeInfoCompatIcs.e(obj);
        }

        public final CharSequence o(Object obj) {
            return AccessibilityNodeInfoCompatIcs.f(obj);
        }

        public final Object p(Object obj) {
            return AccessibilityNodeInfoCompatIcs.g(obj);
        }

        public final CharSequence q(Object obj) {
            return AccessibilityNodeInfoCompatIcs.h(obj);
        }

        public final int r(Object obj) {
            return AccessibilityNodeInfoCompatIcs.i(obj);
        }

        public final boolean s(Object obj) {
            return AccessibilityNodeInfoCompatIcs.j(obj);
        }

        public final boolean t(Object obj) {
            return AccessibilityNodeInfoCompatIcs.k(obj);
        }

        public final boolean u(Object obj) {
            return AccessibilityNodeInfoCompatIcs.l(obj);
        }

        public final boolean v(Object obj) {
            return AccessibilityNodeInfoCompatIcs.m(obj);
        }

        public final boolean w(Object obj) {
            return AccessibilityNodeInfoCompatIcs.n(obj);
        }

        public final boolean x(Object obj) {
            return AccessibilityNodeInfoCompatIcs.o(obj);
        }

        public final boolean y(Object obj) {
            return AccessibilityNodeInfoCompatIcs.p(obj);
        }

        public final boolean z(Object obj) {
            return AccessibilityNodeInfoCompatIcs.q(obj);
        }

        public final boolean A(Object obj) {
            return AccessibilityNodeInfoCompatIcs.r(obj);
        }

        public final boolean B(Object obj) {
            return AccessibilityNodeInfoCompatIcs.s(obj);
        }

        public final boolean d(Object obj, int i) {
            return AccessibilityNodeInfoCompatIcs.c(obj, i);
        }

        public final void c(Object obj, Rect rect) {
            AccessibilityNodeInfoCompatIcs.c(obj, rect);
        }

        public final void d(Object obj, Rect rect) {
            AccessibilityNodeInfoCompatIcs.d(obj, rect);
        }

        public final void a(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.a(obj, z);
        }

        public final void b(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.b(obj, z);
        }

        public final void b(Object obj, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.a(obj, charSequence);
        }

        public final void c(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.c(obj, z);
        }

        public final void c(Object obj, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.b(obj, charSequence);
        }

        public final void d(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.d(obj, z);
        }

        public final void e(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.e(obj, z);
        }

        public final void f(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.f(obj, z);
        }

        public final void g(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.g(obj, z);
        }

        public final void d(Object obj, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.c(obj, charSequence);
        }

        public final void e(Object obj, View view) {
            AccessibilityNodeInfoCompatIcs.b(obj, view);
        }

        public final void h(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.h(obj, z);
        }

        public final void i(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.i(obj, z);
        }

        public final void j(Object obj, boolean z) {
            AccessibilityNodeInfoCompatIcs.j(obj, z);
        }

        public final void f(Object obj, View view) {
            AccessibilityNodeInfoCompatIcs.c(obj, view);
        }

        public final void e(Object obj, CharSequence charSequence) {
            AccessibilityNodeInfoCompatIcs.d(obj, charSequence);
        }

        public final void C(Object obj) {
            AccessibilityNodeInfoCompatIcs.t(obj);
        }
    }

    interface AccessibilityNodeInfoImpl {
        boolean A(Object obj);

        boolean B(Object obj);

        void C(Object obj);

        int D(Object obj);

        boolean E(Object obj);

        boolean F(Object obj);

        String G(Object obj);

        int H(Object obj);

        Object I(Object obj);

        Object J(Object obj);

        Object K(Object obj);

        int L(Object obj);

        int M(Object obj);

        boolean N(Object obj);

        int O(Object obj);

        int P(Object obj);

        int Q(Object obj);

        int R(Object obj);

        boolean S(Object obj);

        boolean T(Object obj);

        Object U(Object obj);

        Object V(Object obj);

        boolean W(Object obj);

        Bundle X(Object obj);

        int Y(Object obj);

        int Z(Object obj);

        Object a();

        Object a(int i, int i2, int i3, int i4, boolean z, boolean z2);

        Object a(int i, int i2, boolean z, int i3);

        Object a(int i, CharSequence charSequence);

        Object a(View view);

        Object a(View view, int i);

        List<Object> a(Object obj);

        List<Object> a(Object obj, String str);

        void a(Object obj, int i);

        void a(Object obj, int i, int i2);

        void a(Object obj, Rect rect);

        void a(Object obj, CharSequence charSequence);

        void a(Object obj, Object obj2);

        void a(Object obj, boolean z);

        boolean a(Object obj, int i, Bundle bundle);

        boolean a(Object obj, View view);

        boolean a(Object obj, View view, int i);

        int aa(Object obj);

        boolean ab(Object obj);

        boolean ac(Object obj);

        boolean ad(Object obj);

        boolean ae(Object obj);

        int b(Object obj);

        void b(Object obj, int i);

        void b(Object obj, Rect rect);

        void b(Object obj, View view);

        void b(Object obj, View view, int i);

        void b(Object obj, CharSequence charSequence);

        void b(Object obj, String str);

        void b(Object obj, boolean z);

        boolean b(Object obj, Object obj2);

        CharSequence c(Object obj);

        Object c(Object obj, int i);

        List<Object> c(Object obj, String str);

        void c(Object obj, Rect rect);

        void c(Object obj, View view);

        void c(Object obj, View view, int i);

        void c(Object obj, CharSequence charSequence);

        void c(Object obj, Object obj2);

        void c(Object obj, boolean z);

        void d(Object obj, Rect rect);

        void d(Object obj, View view);

        void d(Object obj, View view, int i);

        void d(Object obj, CharSequence charSequence);

        void d(Object obj, Object obj2);

        void d(Object obj, boolean z);

        boolean d(Object obj);

        boolean d(Object obj, int i);

        CharSequence e(Object obj);

        Object e(Object obj, int i);

        void e(Object obj, View view);

        void e(Object obj, View view, int i);

        void e(Object obj, CharSequence charSequence);

        void e(Object obj, Object obj2);

        void e(Object obj, boolean z);

        int f(Object obj);

        Object f(Object obj, int i);

        void f(Object obj, View view);

        void f(Object obj, View view, int i);

        void f(Object obj, boolean z);

        Object g(Object obj);

        void g(Object obj, int i);

        void g(Object obj, View view);

        void g(Object obj, View view, int i);

        void g(Object obj, boolean z);

        Object h(Object obj);

        void h(Object obj, int i);

        void h(Object obj, View view);

        void h(Object obj, View view, int i);

        void h(Object obj, boolean z);

        Object i(Object obj);

        void i(Object obj, int i);

        void i(Object obj, boolean z);

        Object j(Object obj);

        void j(Object obj, boolean z);

        int k(Object obj);

        void k(Object obj, boolean z);

        int l(Object obj);

        void l(Object obj, boolean z);

        CharSequence m(Object obj);

        void m(Object obj, boolean z);

        CharSequence n(Object obj);

        void n(Object obj, boolean z);

        CharSequence o(Object obj);

        void o(Object obj, boolean z);

        Object p(Object obj);

        void p(Object obj, boolean z);

        CharSequence q(Object obj);

        void q(Object obj, boolean z);

        int r(Object obj);

        boolean s(Object obj);

        boolean t(Object obj);

        boolean u(Object obj);

        boolean v(Object obj);

        boolean w(Object obj);

        boolean x(Object obj);

        boolean y(Object obj);

        boolean z(Object obj);
    }

    static class AccessibilityNodeInfoJellybeanImpl extends AccessibilityNodeInfoIcsImpl {
        AccessibilityNodeInfoJellybeanImpl() {
        }

        public final Object a(View view, int i) {
            return AccessibilityNodeInfoCompatJellyBean.a(view, i);
        }

        public final Object e(Object obj, int i) {
            return AccessibilityNodeInfoCompatJellyBean.b(obj, i);
        }

        public final Object f(Object obj, int i) {
            return AccessibilityNodeInfoCompatJellyBean.c(obj, i);
        }

        public final void e(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatJellyBean.a(obj, view, i);
        }

        public final void d(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatJellyBean.b(obj, view, i);
        }

        public final boolean E(Object obj) {
            return AccessibilityNodeInfoCompatJellyBean.a(obj);
        }

        public final void k(Object obj, boolean z) {
            AccessibilityNodeInfoCompatJellyBean.a(obj, z);
        }

        public final boolean F(Object obj) {
            return AccessibilityNodeInfoCompatJellyBean.c(obj);
        }

        public final void l(Object obj, boolean z) {
            AccessibilityNodeInfoCompatJellyBean.b(obj, z);
        }

        public final boolean a(Object obj, int i, Bundle bundle) {
            return AccessibilityNodeInfoCompatJellyBean.a(obj, i, bundle);
        }

        public final void g(Object obj, int i) {
            AccessibilityNodeInfoCompatJellyBean.a(obj, i);
        }

        public final int D(Object obj) {
            return AccessibilityNodeInfoCompatJellyBean.b(obj);
        }

        public final void f(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatJellyBean.c(obj, view, i);
        }
    }

    static class AccessibilityNodeInfoJellybeanMr1Impl extends AccessibilityNodeInfoJellybeanImpl {
        AccessibilityNodeInfoJellybeanMr1Impl() {
        }

        public final void g(Object obj, View view) {
            AccessibilityNodeInfoCompatJellybeanMr1.a(obj, view);
        }

        public final void g(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatJellybeanMr1.a(obj, view, i);
        }

        public final Object U(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr1.a(obj);
        }

        public final void h(Object obj, View view) {
            AccessibilityNodeInfoCompatJellybeanMr1.b(obj, view);
        }

        public final void h(Object obj, View view, int i) {
            AccessibilityNodeInfoCompatJellybeanMr1.b(obj, view, i);
        }

        public final Object V(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr1.b(obj);
        }
    }

    static class AccessibilityNodeInfoJellybeanMr2Impl extends AccessibilityNodeInfoJellybeanMr1Impl {
        AccessibilityNodeInfoJellybeanMr2Impl() {
        }

        public final String G(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr2.a(obj);
        }

        public final void b(Object obj, String str) {
            AccessibilityNodeInfoCompatJellybeanMr2.a(obj, str);
        }

        public final List<Object> c(Object obj, String str) {
            return AccessibilityNodeInfoCompatJellybeanMr2.b(obj, str);
        }

        public final void a(Object obj, int i, int i2) {
            AccessibilityNodeInfoCompatJellybeanMr2.a(obj, i, i2);
        }

        public final int Z(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr2.b(obj);
        }

        public final int aa(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr2.c(obj);
        }

        public final boolean ac(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr2.d(obj);
        }

        public final void p(Object obj, boolean z) {
            AccessibilityNodeInfoCompatJellybeanMr2.a(obj, z);
        }

        public final boolean ae(Object obj) {
            return AccessibilityNodeInfoCompatJellybeanMr2.e(obj);
        }
    }

    static class AccessibilityNodeInfoKitKatImpl extends AccessibilityNodeInfoJellybeanMr2Impl {
        AccessibilityNodeInfoKitKatImpl() {
        }

        public final int H(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.a(obj);
        }

        public final void h(Object obj, int i) {
            AccessibilityNodeInfoCompatKitKat.a(obj, i);
        }

        public final Object I(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.b(obj);
        }

        public final void c(Object obj, Object obj2) {
            AccessibilityNodeInfoCompatKitKat.a(obj, obj2);
        }

        public Object a(int i, int i2, boolean z, int i3) {
            return AccessibilityNodeInfoCompatKitKat.a(i, i2, z);
        }

        public Object a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return AccessibilityNodeInfoCompatKitKat.a(i, i2, i3, i4, z);
        }

        public final int L(Object obj) {
            return CollectionInfo.a(obj);
        }

        public final int M(Object obj) {
            return CollectionInfo.b(obj);
        }

        public final boolean N(Object obj) {
            return CollectionInfo.c(obj);
        }

        public final Object J(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.c(obj);
        }

        public final Object K(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.d(obj);
        }

        public final void e(Object obj, Object obj2) {
            AccessibilityNodeInfoCompatKitKat.c(obj, obj2);
        }

        public final int O(Object obj) {
            return CollectionItemInfo.a(obj);
        }

        public final int P(Object obj) {
            return CollectionItemInfo.b(obj);
        }

        public final int Q(Object obj) {
            return CollectionItemInfo.c(obj);
        }

        public final int R(Object obj) {
            return CollectionItemInfo.d(obj);
        }

        public final boolean S(Object obj) {
            return CollectionItemInfo.e(obj);
        }

        public final void d(Object obj, Object obj2) {
            AccessibilityNodeInfoCompatKitKat.b(obj, obj2);
        }

        public final void m(Object obj, boolean z) {
            AccessibilityNodeInfoCompatKitKat.a(obj, z);
        }

        public final boolean T(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.e(obj);
        }

        public final boolean W(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.f(obj);
        }

        public final void n(Object obj, boolean z) {
            AccessibilityNodeInfoCompatKitKat.b(obj, z);
        }

        public final Bundle X(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.g(obj);
        }

        public final int Y(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.h(obj);
        }

        public final void i(Object obj, int i) {
            AccessibilityNodeInfoCompatKitKat.b(obj, i);
        }

        public final boolean ab(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.i(obj);
        }

        public final void o(Object obj, boolean z) {
            AccessibilityNodeInfoCompatKitKat.c(obj, z);
        }

        public final boolean ad(Object obj) {
            return AccessibilityNodeInfoCompatKitKat.j(obj);
        }

        public final void q(Object obj, boolean z) {
            AccessibilityNodeInfoCompatKitKat.d(obj, z);
        }
    }

    static class AccessibilityNodeInfoStubImpl implements AccessibilityNodeInfoImpl {
        public boolean A(Object obj) {
            return false;
        }

        public boolean B(Object obj) {
            return false;
        }

        public void C(Object obj) {
        }

        public int D(Object obj) {
            return 0;
        }

        public boolean E(Object obj) {
            return false;
        }

        public boolean F(Object obj) {
            return false;
        }

        public String G(Object obj) {
            return null;
        }

        public int H(Object obj) {
            return 0;
        }

        public Object I(Object obj) {
            return null;
        }

        public Object J(Object obj) {
            return null;
        }

        public Object K(Object obj) {
            return null;
        }

        public int L(Object obj) {
            return 0;
        }

        public int M(Object obj) {
            return 0;
        }

        public boolean N(Object obj) {
            return false;
        }

        public int O(Object obj) {
            return 0;
        }

        public int P(Object obj) {
            return 0;
        }

        public int Q(Object obj) {
            return 0;
        }

        public int R(Object obj) {
            return 0;
        }

        public boolean S(Object obj) {
            return false;
        }

        public boolean T(Object obj) {
            return false;
        }

        public Object U(Object obj) {
            return null;
        }

        public Object V(Object obj) {
            return null;
        }

        public boolean W(Object obj) {
            return false;
        }

        public int Y(Object obj) {
            return 0;
        }

        public int Z(Object obj) {
            return -1;
        }

        public Object a() {
            return null;
        }

        public Object a(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return null;
        }

        public Object a(int i, int i2, boolean z, int i3) {
            return null;
        }

        public Object a(int i, CharSequence charSequence) {
            return null;
        }

        public Object a(View view) {
            return null;
        }

        public Object a(View view, int i) {
            return null;
        }

        public List<Object> a(Object obj) {
            return null;
        }

        public void a(Object obj, int i) {
        }

        public void a(Object obj, int i, int i2) {
        }

        public void a(Object obj, Rect rect) {
        }

        public void a(Object obj, CharSequence charSequence) {
        }

        public void a(Object obj, Object obj2) {
        }

        public void a(Object obj, boolean z) {
        }

        public boolean a(Object obj, int i, Bundle bundle) {
            return false;
        }

        public boolean a(Object obj, View view) {
            return false;
        }

        public boolean a(Object obj, View view, int i) {
            return false;
        }

        public int aa(Object obj) {
            return -1;
        }

        public boolean ab(Object obj) {
            return false;
        }

        public boolean ac(Object obj) {
            return false;
        }

        public boolean ad(Object obj) {
            return false;
        }

        public boolean ae(Object obj) {
            return false;
        }

        public int b(Object obj) {
            return 0;
        }

        public void b(Object obj, int i) {
        }

        public void b(Object obj, Rect rect) {
        }

        public void b(Object obj, View view) {
        }

        public void b(Object obj, View view, int i) {
        }

        public void b(Object obj, CharSequence charSequence) {
        }

        public void b(Object obj, String str) {
        }

        public void b(Object obj, boolean z) {
        }

        public boolean b(Object obj, Object obj2) {
            return false;
        }

        public CharSequence c(Object obj) {
            return null;
        }

        public Object c(Object obj, int i) {
            return null;
        }

        public void c(Object obj, Rect rect) {
        }

        public void c(Object obj, View view) {
        }

        public void c(Object obj, View view, int i) {
        }

        public void c(Object obj, CharSequence charSequence) {
        }

        public void c(Object obj, Object obj2) {
        }

        public void c(Object obj, boolean z) {
        }

        public void d(Object obj, Rect rect) {
        }

        public void d(Object obj, View view) {
        }

        public void d(Object obj, View view, int i) {
        }

        public void d(Object obj, CharSequence charSequence) {
        }

        public void d(Object obj, Object obj2) {
        }

        public void d(Object obj, boolean z) {
        }

        public boolean d(Object obj) {
            return false;
        }

        public boolean d(Object obj, int i) {
            return false;
        }

        public CharSequence e(Object obj) {
            return null;
        }

        public Object e(Object obj, int i) {
            return null;
        }

        public void e(Object obj, View view) {
        }

        public void e(Object obj, View view, int i) {
        }

        public void e(Object obj, CharSequence charSequence) {
        }

        public void e(Object obj, Object obj2) {
        }

        public void e(Object obj, boolean z) {
        }

        public int f(Object obj) {
            return -1;
        }

        public Object f(Object obj, int i) {
            return null;
        }

        public void f(Object obj, View view) {
        }

        public void f(Object obj, View view, int i) {
        }

        public void f(Object obj, boolean z) {
        }

        public Object g(Object obj) {
            return null;
        }

        public void g(Object obj, int i) {
        }

        public void g(Object obj, View view) {
        }

        public void g(Object obj, View view, int i) {
        }

        public void g(Object obj, boolean z) {
        }

        public Object h(Object obj) {
            return null;
        }

        public void h(Object obj, int i) {
        }

        public void h(Object obj, View view) {
        }

        public void h(Object obj, View view, int i) {
        }

        public void h(Object obj, boolean z) {
        }

        public Object i(Object obj) {
            return null;
        }

        public void i(Object obj, int i) {
        }

        public void i(Object obj, boolean z) {
        }

        public Object j(Object obj) {
            return null;
        }

        public void j(Object obj, boolean z) {
        }

        public int k(Object obj) {
            return 0;
        }

        public void k(Object obj, boolean z) {
        }

        public int l(Object obj) {
            return 0;
        }

        public void l(Object obj, boolean z) {
        }

        public CharSequence m(Object obj) {
            return null;
        }

        public void m(Object obj, boolean z) {
        }

        public CharSequence n(Object obj) {
            return null;
        }

        public void n(Object obj, boolean z) {
        }

        public CharSequence o(Object obj) {
            return null;
        }

        public void o(Object obj, boolean z) {
        }

        public Object p(Object obj) {
            return null;
        }

        public void p(Object obj, boolean z) {
        }

        public CharSequence q(Object obj) {
            return null;
        }

        public void q(Object obj, boolean z) {
        }

        public int r(Object obj) {
            return 0;
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

        public boolean v(Object obj) {
            return false;
        }

        public boolean w(Object obj) {
            return false;
        }

        public boolean x(Object obj) {
            return false;
        }

        public boolean y(Object obj) {
            return false;
        }

        public boolean z(Object obj) {
            return false;
        }

        AccessibilityNodeInfoStubImpl() {
        }

        public List<Object> a(Object obj, String str) {
            return Collections.emptyList();
        }

        public List<Object> c(Object obj, String str) {
            return Collections.emptyList();
        }

        public Bundle X(Object obj) {
            return new Bundle();
        }
    }

    public static class CollectionInfoCompat {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        final Object mInfo;

        public static CollectionInfoCompat obtain(int i, int i2, boolean z, int i3) {
            return new CollectionInfoCompat(AccessibilityNodeInfoCompat.IMPL.a(i, i2, z, i3));
        }

        private CollectionInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public int getColumnCount() {
            return AccessibilityNodeInfoCompat.IMPL.L(this.mInfo);
        }

        public int getRowCount() {
            return AccessibilityNodeInfoCompat.IMPL.M(this.mInfo);
        }

        public boolean isHierarchical() {
            return AccessibilityNodeInfoCompat.IMPL.N(this.mInfo);
        }
    }

    public static class CollectionItemInfoCompat {
        /* access modifiers changed from: private */
        public final Object mInfo;

        public static CollectionItemInfoCompat obtain(int i, int i2, int i3, int i4, boolean z, boolean z2) {
            return new CollectionItemInfoCompat(AccessibilityNodeInfoCompat.IMPL.a(i, i2, i3, i4, z, z2));
        }

        private CollectionItemInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public int getColumnIndex() {
            return AccessibilityNodeInfoCompat.IMPL.O(this.mInfo);
        }

        public int getColumnSpan() {
            return AccessibilityNodeInfoCompat.IMPL.P(this.mInfo);
        }

        public int getRowIndex() {
            return AccessibilityNodeInfoCompat.IMPL.Q(this.mInfo);
        }

        public int getRowSpan() {
            return AccessibilityNodeInfoCompat.IMPL.R(this.mInfo);
        }

        public boolean isHeading() {
            return AccessibilityNodeInfoCompat.IMPL.S(this.mInfo);
        }

        public boolean isSelected() {
            return AccessibilityNodeInfoCompat.IMPL.d(this.mInfo);
        }
    }

    public static class RangeInfoCompat {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        /* access modifiers changed from: private */
        public final Object mInfo;

        private RangeInfoCompat(Object obj) {
            this.mInfo = obj;
        }

        public float getCurrent() {
            return RangeInfo.a(this.mInfo);
        }

        public float getMax() {
            return RangeInfo.b(this.mInfo);
        }

        public float getMin() {
            return RangeInfo.c(this.mInfo);
        }

        public int getType() {
            return RangeInfo.d(this.mInfo);
        }
    }

    private static String getActionSymbolicName(int i) {
        switch (i) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            default:
                return "ACTION_UNKNOWN";
        }
    }

    static {
        if (VERSION.SDK_INT >= 22) {
            IMPL = new AccessibilityNodeInfoApi22Impl();
        } else if (VERSION.SDK_INT >= 21) {
            IMPL = new AccessibilityNodeInfoApi21Impl();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new AccessibilityNodeInfoKitKatImpl();
        } else if (VERSION.SDK_INT >= 18) {
            IMPL = new AccessibilityNodeInfoJellybeanMr2Impl();
        } else if (VERSION.SDK_INT >= 17) {
            IMPL = new AccessibilityNodeInfoJellybeanMr1Impl();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new AccessibilityNodeInfoJellybeanImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new AccessibilityNodeInfoIcsImpl();
        } else {
            IMPL = new AccessibilityNodeInfoStubImpl();
        }
    }

    static AccessibilityNodeInfoCompat wrapNonNullInstance(Object obj) {
        if (obj != null) {
            return new AccessibilityNodeInfoCompat(obj);
        }
        return null;
    }

    public AccessibilityNodeInfoCompat(Object obj) {
        this.mInfo = obj;
    }

    public Object getInfo() {
        return this.mInfo;
    }

    public static AccessibilityNodeInfoCompat obtain(View view) {
        return wrapNonNullInstance(IMPL.a(view));
    }

    public static AccessibilityNodeInfoCompat obtain(View view, int i) {
        return wrapNonNullInstance(IMPL.a(view, i));
    }

    public static AccessibilityNodeInfoCompat obtain() {
        return wrapNonNullInstance(IMPL.a());
    }

    public static AccessibilityNodeInfoCompat obtain(AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        return wrapNonNullInstance(IMPL.j(accessibilityNodeInfoCompat.mInfo));
    }

    public void setSource(View view) {
        IMPL.f(this.mInfo, view);
    }

    public void setSource(View view, int i) {
        IMPL.d(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat findFocus(int i) {
        return wrapNonNullInstance(IMPL.e(this.mInfo, i));
    }

    public AccessibilityNodeInfoCompat focusSearch(int i) {
        return wrapNonNullInstance(IMPL.f(this.mInfo, i));
    }

    public int getWindowId() {
        return IMPL.r(this.mInfo);
    }

    public int getChildCount() {
        return IMPL.l(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getChild(int i) {
        return wrapNonNullInstance(IMPL.c(this.mInfo, i));
    }

    public void addChild(View view) {
        IMPL.d(this.mInfo, view);
    }

    public void addChild(View view, int i) {
        IMPL.e(this.mInfo, view, i);
    }

    public boolean removeChild(View view) {
        return IMPL.a(this.mInfo, view);
    }

    public boolean removeChild(View view, int i) {
        return IMPL.a(this.mInfo, view, i);
    }

    public int getActions() {
        return IMPL.k(this.mInfo);
    }

    public void addAction(int i) {
        IMPL.b(this.mInfo, i);
    }

    public void addAction(AccessibilityActionCompat accessibilityActionCompat) {
        IMPL.a(this.mInfo, accessibilityActionCompat.mAction);
    }

    public boolean removeAction(AccessibilityActionCompat accessibilityActionCompat) {
        return IMPL.b(this.mInfo, accessibilityActionCompat.mAction);
    }

    public boolean performAction(int i) {
        return IMPL.d(this.mInfo, i);
    }

    public boolean performAction(int i, Bundle bundle) {
        return IMPL.a(this.mInfo, i, bundle);
    }

    public void setMovementGranularities(int i) {
        IMPL.g(this.mInfo, i);
    }

    public int getMovementGranularities() {
        return IMPL.D(this.mInfo);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String str) {
        ArrayList arrayList = new ArrayList();
        List<Object> a = IMPL.a(this.mInfo, str);
        int size = a.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new AccessibilityNodeInfoCompat(a.get(i)));
        }
        return arrayList;
    }

    public AccessibilityNodeInfoCompat getParent() {
        return wrapNonNullInstance(IMPL.p(this.mInfo));
    }

    public void setParent(View view) {
        IMPL.e(this.mInfo, view);
    }

    public void setParent(View view, int i) {
        IMPL.f(this.mInfo, view, i);
    }

    public void getBoundsInParent(Rect rect) {
        IMPL.a(this.mInfo, rect);
    }

    public void setBoundsInParent(Rect rect) {
        IMPL.c(this.mInfo, rect);
    }

    public void getBoundsInScreen(Rect rect) {
        IMPL.b(this.mInfo, rect);
    }

    public void setBoundsInScreen(Rect rect) {
        IMPL.d(this.mInfo, rect);
    }

    public boolean isCheckable() {
        return IMPL.s(this.mInfo);
    }

    public void setCheckable(boolean z) {
        IMPL.a(this.mInfo, z);
    }

    public boolean isChecked() {
        return IMPL.t(this.mInfo);
    }

    public void setChecked(boolean z) {
        IMPL.b(this.mInfo, z);
    }

    public boolean isFocusable() {
        return IMPL.w(this.mInfo);
    }

    public void setFocusable(boolean z) {
        IMPL.e(this.mInfo, z);
    }

    public boolean isFocused() {
        return IMPL.x(this.mInfo);
    }

    public void setFocused(boolean z) {
        IMPL.f(this.mInfo, z);
    }

    public boolean isVisibleToUser() {
        return IMPL.E(this.mInfo);
    }

    public void setVisibleToUser(boolean z) {
        IMPL.k(this.mInfo, z);
    }

    public boolean isAccessibilityFocused() {
        return IMPL.F(this.mInfo);
    }

    public void setAccessibilityFocused(boolean z) {
        IMPL.l(this.mInfo, z);
    }

    public boolean isSelected() {
        return IMPL.B(this.mInfo);
    }

    public void setSelected(boolean z) {
        IMPL.j(this.mInfo, z);
    }

    public boolean isClickable() {
        return IMPL.u(this.mInfo);
    }

    public void setClickable(boolean z) {
        IMPL.c(this.mInfo, z);
    }

    public boolean isLongClickable() {
        return IMPL.y(this.mInfo);
    }

    public void setLongClickable(boolean z) {
        IMPL.g(this.mInfo, z);
    }

    public boolean isEnabled() {
        return IMPL.v(this.mInfo);
    }

    public void setEnabled(boolean z) {
        IMPL.d(this.mInfo, z);
    }

    public boolean isPassword() {
        return IMPL.z(this.mInfo);
    }

    public void setPassword(boolean z) {
        IMPL.h(this.mInfo, z);
    }

    public boolean isScrollable() {
        return IMPL.A(this.mInfo);
    }

    public void setScrollable(boolean z) {
        IMPL.i(this.mInfo, z);
    }

    public CharSequence getPackageName() {
        return IMPL.o(this.mInfo);
    }

    public void setPackageName(CharSequence charSequence) {
        IMPL.d(this.mInfo, charSequence);
    }

    public CharSequence getClassName() {
        return IMPL.m(this.mInfo);
    }

    public void setClassName(CharSequence charSequence) {
        IMPL.b(this.mInfo, charSequence);
    }

    public CharSequence getText() {
        return IMPL.q(this.mInfo);
    }

    public void setText(CharSequence charSequence) {
        IMPL.e(this.mInfo, charSequence);
    }

    public CharSequence getContentDescription() {
        return IMPL.n(this.mInfo);
    }

    public void setContentDescription(CharSequence charSequence) {
        IMPL.c(this.mInfo, charSequence);
    }

    public void recycle() {
        IMPL.C(this.mInfo);
    }

    public void setViewIdResourceName(String str) {
        IMPL.b(this.mInfo, str);
    }

    public String getViewIdResourceName() {
        return IMPL.G(this.mInfo);
    }

    public int getLiveRegion() {
        return IMPL.H(this.mInfo);
    }

    public void setLiveRegion(int i) {
        IMPL.h(this.mInfo, i);
    }

    public CollectionInfoCompat getCollectionInfo() {
        Object I = IMPL.I(this.mInfo);
        if (I == null) {
            return null;
        }
        return new CollectionInfoCompat(I);
    }

    public void setCollectionInfo(Object obj) {
        IMPL.c(this.mInfo, ((CollectionInfoCompat) obj).mInfo);
    }

    public void setCollectionItemInfo(Object obj) {
        IMPL.d(this.mInfo, ((CollectionItemInfoCompat) obj).mInfo);
    }

    public CollectionItemInfoCompat getCollectionItemInfo() {
        Object J = IMPL.J(this.mInfo);
        if (J == null) {
            return null;
        }
        return new CollectionItemInfoCompat(J);
    }

    public RangeInfoCompat getRangeInfo() {
        Object K = IMPL.K(this.mInfo);
        if (K == null) {
            return null;
        }
        return new RangeInfoCompat(K);
    }

    public void setRangeInfo(RangeInfoCompat rangeInfoCompat) {
        IMPL.e(this.mInfo, rangeInfoCompat.mInfo);
    }

    public List<AccessibilityActionCompat> getActionList() {
        List<Object> a = IMPL.a(this.mInfo);
        if (a == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        int size = a.size();
        for (int i = 0; i < size; i++) {
            arrayList.add(new AccessibilityActionCompat(a.get(i)));
        }
        return arrayList;
    }

    public void setContentInvalid(boolean z) {
        IMPL.m(this.mInfo, z);
    }

    public boolean isContentInvalid() {
        return IMPL.T(this.mInfo);
    }

    public void setError(CharSequence charSequence) {
        IMPL.a(this.mInfo, charSequence);
    }

    public CharSequence getError() {
        return IMPL.e(this.mInfo);
    }

    public void setLabelFor(View view) {
        IMPL.g(this.mInfo, view);
    }

    public void setLabelFor(View view, int i) {
        IMPL.g(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat getLabelFor() {
        return wrapNonNullInstance(IMPL.U(this.mInfo));
    }

    public void setLabeledBy(View view) {
        IMPL.h(this.mInfo, view);
    }

    public void setLabeledBy(View view, int i) {
        IMPL.h(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat getLabeledBy() {
        return wrapNonNullInstance(IMPL.V(this.mInfo));
    }

    public boolean canOpenPopup() {
        return IMPL.W(this.mInfo);
    }

    public void setCanOpenPopup(boolean z) {
        IMPL.n(this.mInfo, z);
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByViewId(String str) {
        List<Object> c = IMPL.c(this.mInfo, str);
        if (c == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Object accessibilityNodeInfoCompat : c) {
            arrayList.add(new AccessibilityNodeInfoCompat(accessibilityNodeInfoCompat));
        }
        return arrayList;
    }

    public Bundle getExtras() {
        return IMPL.X(this.mInfo);
    }

    public int getInputType() {
        return IMPL.Y(this.mInfo);
    }

    public void setInputType(int i) {
        IMPL.i(this.mInfo, i);
    }

    public void setMaxTextLength(int i) {
        IMPL.a(this.mInfo, i);
    }

    public int getMaxTextLength() {
        return IMPL.f(this.mInfo);
    }

    public void setTextSelection(int i, int i2) {
        IMPL.a(this.mInfo, i, i2);
    }

    public int getTextSelectionStart() {
        return IMPL.Z(this.mInfo);
    }

    public int getTextSelectionEnd() {
        return IMPL.aa(this.mInfo);
    }

    public AccessibilityNodeInfoCompat getTraversalBefore() {
        return wrapNonNullInstance(IMPL.h(this.mInfo));
    }

    public void setTraversalBefore(View view) {
        IMPL.b(this.mInfo, view);
    }

    public void setTraversalBefore(View view, int i) {
        IMPL.b(this.mInfo, view, i);
    }

    public AccessibilityNodeInfoCompat getTraversalAfter() {
        return wrapNonNullInstance(IMPL.i(this.mInfo));
    }

    public void setTraversalAfter(View view) {
        IMPL.c(this.mInfo, view);
    }

    public void setTraversalAfter(View view, int i) {
        IMPL.c(this.mInfo, view, i);
    }

    public AccessibilityWindowInfoCompat getWindow() {
        return AccessibilityWindowInfoCompat.wrapNonNullInstance(IMPL.g(this.mInfo));
    }

    public boolean isDismissable() {
        return IMPL.ab(this.mInfo);
    }

    public void setDismissable(boolean z) {
        IMPL.o(this.mInfo, z);
    }

    public boolean isEditable() {
        return IMPL.ac(this.mInfo);
    }

    public void setEditable(boolean z) {
        IMPL.p(this.mInfo, z);
    }

    public boolean isMultiLine() {
        return IMPL.ad(this.mInfo);
    }

    public void setMultiLine(boolean z) {
        IMPL.q(this.mInfo, z);
    }

    public boolean refresh() {
        return IMPL.ae(this.mInfo);
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
        AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = (AccessibilityNodeInfoCompat) obj;
        if (this.mInfo == null) {
            if (accessibilityNodeInfoCompat.mInfo != null) {
                return false;
            }
        } else if (!this.mInfo.equals(accessibilityNodeInfoCompat.mInfo)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        Rect rect = new Rect();
        getBoundsInParent(rect);
        sb.append("; boundsInParent: ".concat(String.valueOf(rect)));
        getBoundsInScreen(rect);
        sb.append("; boundsInScreen: ".concat(String.valueOf(rect)));
        sb.append("; packageName: ");
        sb.append(getPackageName());
        sb.append("; className: ");
        sb.append(getClassName());
        sb.append("; text: ");
        sb.append(getText());
        sb.append("; contentDescription: ");
        sb.append(getContentDescription());
        sb.append("; viewId: ");
        sb.append(getViewIdResourceName());
        sb.append("; checkable: ");
        sb.append(isCheckable());
        sb.append("; checked: ");
        sb.append(isChecked());
        sb.append("; focusable: ");
        sb.append(isFocusable());
        sb.append("; focused: ");
        sb.append(isFocused());
        sb.append("; selected: ");
        sb.append(isSelected());
        sb.append("; clickable: ");
        sb.append(isClickable());
        sb.append("; longClickable: ");
        sb.append(isLongClickable());
        sb.append("; enabled: ");
        sb.append(isEnabled());
        sb.append("; password: ");
        sb.append(isPassword());
        StringBuilder sb2 = new StringBuilder("; scrollable: ");
        sb2.append(isScrollable());
        sb.append(sb2.toString());
        sb.append("; [");
        int actions = getActions();
        while (actions != 0) {
            int numberOfTrailingZeros = 1 << Integer.numberOfTrailingZeros(actions);
            actions &= ~numberOfTrailingZeros;
            sb.append(getActionSymbolicName(numberOfTrailingZeros));
            if (actions != 0) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
