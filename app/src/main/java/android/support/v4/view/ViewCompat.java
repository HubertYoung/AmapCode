package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class ViewCompat {
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    private static final long FAKE_FRAME_TIME = 10;
    static final ViewCompatImpl IMPL;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    private static final String TAG = "ViewCompat";

    @Retention(RetentionPolicy.SOURCE)
    @interface AccessibilityLiveRegion {
    }

    static class BaseViewCompatImpl implements ViewCompatImpl {
        WeakHashMap<View, ViewPropertyAnimatorCompat> a = null;
        private Method b;
        private Method c;
        private boolean d;

        public float A(View view) {
            return 0.0f;
        }

        public float B(View view) {
            return 0.0f;
        }

        public float C(View view) {
            return 0.0f;
        }

        public float D(View view) {
            return 0.0f;
        }

        public float H(View view) {
            return 0.0f;
        }

        public float I(View view) {
            return 0.0f;
        }

        public String J(View view) {
            return null;
        }

        public int K(View view) {
            return 0;
        }

        public void L(View view) {
        }

        public float M(View view) {
            return 0.0f;
        }

        public float N(View view) {
            return 0.0f;
        }

        public Rect O(View view) {
            return null;
        }

        public boolean P(View view) {
            return false;
        }

        public void Q(View view) {
        }

        public boolean R(View view) {
            return false;
        }

        public int a(int i, int i2) {
            return i | i2;
        }

        public int a(View view) {
            return 2;
        }

        /* access modifiers changed from: 0000 */
        public long a() {
            return ViewCompat.FAKE_FRAME_TIME;
        }

        public WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void a(View view, float f) {
        }

        public void a(View view, int i, int i2) {
        }

        public void a(View view, int i, Paint paint) {
        }

        public void a(View view, Paint paint) {
        }

        public void a(View view, Rect rect) {
        }

        public void a(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        }

        public void a(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        }

        public void a(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        }

        public void a(View view, AccessibilityEvent accessibilityEvent) {
        }

        public void a(View view, String str) {
        }

        public void a(View view, boolean z) {
        }

        public void a(ViewGroup viewGroup, boolean z) {
        }

        public boolean a(View view, int i, Bundle bundle) {
            return false;
        }

        public boolean aa(View view) {
            return false;
        }

        public int ab(View view) {
            return 0;
        }

        public WindowInsetsCompat b(View view, WindowInsetsCompat windowInsetsCompat) {
            return windowInsetsCompat;
        }

        public void b(View view, float f) {
        }

        public void b(View view, AccessibilityEvent accessibilityEvent) {
        }

        public void b(View view, boolean z) {
        }

        public boolean b(View view) {
            return false;
        }

        public void c(View view, float f) {
        }

        public void c(View view, int i) {
        }

        public void c(View view, boolean z) {
        }

        public boolean c(View view) {
            return false;
        }

        public void d(View view, float f) {
        }

        public void d(View view, int i) {
        }

        public void d(View view, boolean z) {
        }

        public int e(View view) {
            return 0;
        }

        public void e(View view, float f) {
        }

        public void e(View view, int i) {
        }

        public AccessibilityNodeProviderCompat f(View view) {
            return null;
        }

        public void f(View view, float f) {
        }

        public void f(View view, int i) {
        }

        public float g(View view) {
            return 1.0f;
        }

        public void g(View view, float f) {
        }

        public void g(View view, int i) {
        }

        public int h(View view) {
            return 0;
        }

        public void h(View view, float f) {
        }

        public int i(View view) {
            return 0;
        }

        public void i(View view, float f) {
        }

        public void i(View view, int i) {
        }

        public int j(View view) {
            return 0;
        }

        public void j(View view, float f) {
        }

        public void k(View view, float f) {
        }

        public void l(View view, float f) {
        }

        public void m(View view, float f) {
        }

        public void n(View view, float f) {
        }

        public int o(View view) {
            return 0;
        }

        public int p(View view) {
            return 0;
        }

        public boolean u(View view) {
            return true;
        }

        public float v(View view) {
            return 0.0f;
        }

        public float w(View view) {
            return 0.0f;
        }

        public float x(View view) {
            return 0.0f;
        }

        public float y(View view) {
            return 0.0f;
        }

        public float z(View view) {
            return 0.0f;
        }

        BaseViewCompatImpl() {
        }

        public boolean a(View view, int i) {
            if (view instanceof ScrollingView) {
                ScrollingView scrollingView = (ScrollingView) view;
                int computeHorizontalScrollOffset = scrollingView.computeHorizontalScrollOffset();
                int computeHorizontalScrollRange = scrollingView.computeHorizontalScrollRange() - scrollingView.computeHorizontalScrollExtent();
                if (computeHorizontalScrollRange != 0 && (i >= 0 ? computeHorizontalScrollOffset < computeHorizontalScrollRange - 1 : computeHorizontalScrollOffset > 0)) {
                    return true;
                }
            }
            return false;
        }

        public boolean b(View view, int i) {
            if (view instanceof ScrollingView) {
                ScrollingView scrollingView = (ScrollingView) view;
                int computeVerticalScrollOffset = scrollingView.computeVerticalScrollOffset();
                int computeVerticalScrollRange = scrollingView.computeVerticalScrollRange() - scrollingView.computeVerticalScrollExtent();
                if (computeVerticalScrollRange != 0 && (i >= 0 ? computeVerticalScrollOffset < computeVerticalScrollRange - 1 : computeVerticalScrollOffset > 0)) {
                    return true;
                }
            }
            return false;
        }

        public void d(View view) {
            view.invalidate();
        }

        public void a(View view, int i, int i2, int i3, int i4) {
            view.invalidate(i, i2, i3, i4);
        }

        public void a(View view, Runnable runnable) {
            view.postDelayed(runnable, a());
        }

        public void a(View view, Runnable runnable, long j) {
            view.postDelayed(runnable, a() + j);
        }

        public ViewParent k(View view) {
            return view.getParent();
        }

        public boolean l(View view) {
            Drawable background = view.getBackground();
            if (background == null || background.getOpacity() != -1) {
                return false;
            }
            return true;
        }

        public int a(int i, int i2, int i3) {
            return View.resolveSize(i, i2);
        }

        public int m(View view) {
            return view.getMeasuredWidth();
        }

        public int n(View view) {
            return view.getMeasuredHeight();
        }

        public int q(View view) {
            return view.getPaddingLeft();
        }

        public int r(View view) {
            return view.getPaddingRight();
        }

        public void b(View view, int i, int i2, int i3, int i4) {
            view.setPadding(i, i2, i3, i4);
        }

        public final void s(View view) {
            if (!this.d) {
                b();
            }
            if (this.b != null) {
                try {
                    this.b.invoke(view, new Object[0]);
                } catch (Exception unused) {
                }
            } else {
                view.onStartTemporaryDetach();
            }
        }

        public final void t(View view) {
            if (!this.d) {
                b();
            }
            if (this.c != null) {
                try {
                    this.c.invoke(view, new Object[0]);
                } catch (Exception unused) {
                }
            } else {
                view.onFinishTemporaryDetach();
            }
        }

        private void b() {
            try {
                this.b = View.class.getDeclaredMethod("dispatchStartTemporaryDetach", new Class[0]);
                this.c = View.class.getDeclaredMethod("dispatchFinishTemporaryDetach", new Class[0]);
            } catch (NoSuchMethodException unused) {
            }
            this.d = true;
        }

        public int E(View view) {
            return ViewCompatBase.d(view);
        }

        public int F(View view) {
            return ViewCompatBase.e(view);
        }

        public ViewPropertyAnimatorCompat G(View view) {
            return new ViewPropertyAnimatorCompat(view);
        }

        public void e(View view, boolean z) {
            if (view instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view).setNestedScrollingEnabled(z);
            }
        }

        public boolean S(View view) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).isNestedScrollingEnabled();
            }
            return false;
        }

        public ColorStateList T(View view) {
            return ViewCompatBase.a(view);
        }

        public void a(View view, ColorStateList colorStateList) {
            ViewCompatBase.a(view, colorStateList);
        }

        public void a(View view, Mode mode) {
            ViewCompatBase.a(view, mode);
        }

        public Mode U(View view) {
            return ViewCompatBase.b(view);
        }

        public boolean h(View view, int i) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).startNestedScroll(i);
            }
            return false;
        }

        public void V(View view) {
            if (view instanceof NestedScrollingChild) {
                ((NestedScrollingChild) view).stopNestedScroll();
            }
        }

        public boolean W(View view) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).hasNestedScrollingParent();
            }
            return false;
        }

        public boolean a(View view, int i, int i2, int i3, int i4, int[] iArr) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedScroll(i, i2, i3, i4, iArr);
            }
            return false;
        }

        public boolean a(View view, int i, int i2, int[] iArr, int[] iArr2) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedPreScroll(i, i2, iArr, iArr2);
            }
            return false;
        }

        public boolean a(View view, float f, float f2, boolean z) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedFling(f, f2, z);
            }
            return false;
        }

        public boolean a(View view, float f, float f2) {
            if (view instanceof NestedScrollingChild) {
                return ((NestedScrollingChild) view).dispatchNestedPreFling(f, f2);
            }
            return false;
        }

        public boolean X(View view) {
            return ViewCompatBase.c(view);
        }

        public float Y(View view) {
            return N(view) + M(view);
        }

        public boolean Z(View view) {
            return ViewCompatBase.f(view);
        }
    }

    static class EclairMr1ViewCompatImpl extends BaseViewCompatImpl {
        EclairMr1ViewCompatImpl() {
        }

        public final boolean l(View view) {
            return ViewCompatEclairMr1.a(view);
        }

        public final void a(ViewGroup viewGroup, boolean z) {
            ViewCompatEclairMr1.a(viewGroup, z);
        }
    }

    static class GBViewCompatImpl extends EclairMr1ViewCompatImpl {
        GBViewCompatImpl() {
        }

        public final int a(View view) {
            return ViewCompatGingerbread.a(view);
        }

        public final void c(View view, int i) {
            ViewCompatGingerbread.a(view, i);
        }
    }

    static class HCViewCompatImpl extends GBViewCompatImpl {
        HCViewCompatImpl() {
        }

        /* access modifiers changed from: 0000 */
        public final long a() {
            return ViewCompatHC.a();
        }

        public final float g(View view) {
            return ViewCompatHC.a(view);
        }

        public final void a(View view, int i, Paint paint) {
            ViewCompatHC.a(view, i, paint);
        }

        public final int h(View view) {
            return ViewCompatHC.b(view);
        }

        public final int a(int i, int i2, int i3) {
            return ViewCompatHC.a(i, i2, i3);
        }

        public final int m(View view) {
            return ViewCompatHC.c(view);
        }

        public final int n(View view) {
            return ViewCompatHC.d(view);
        }

        public final int o(View view) {
            return ViewCompatHC.e(view);
        }

        public final float v(View view) {
            return ViewCompatHC.f(view);
        }

        public final float w(View view) {
            return ViewCompatHC.g(view);
        }

        public final void b(View view, float f) {
            ViewCompatHC.a(view, f);
        }

        public final void c(View view, float f) {
            ViewCompatHC.b(view, f);
        }

        public final void d(View view, float f) {
            ViewCompatHC.c(view, f);
        }

        public final void i(View view, float f) {
            ViewCompatHC.d(view, f);
        }

        public final void j(View view, float f) {
            ViewCompatHC.e(view, f);
        }

        public final void a(View view, float f) {
            ViewCompatHC.f(view, f);
        }

        public final void e(View view, float f) {
            ViewCompatHC.g(view, f);
        }

        public final void f(View view, float f) {
            ViewCompatHC.h(view, f);
        }

        public final void g(View view, float f) {
            ViewCompatHC.i(view, f);
        }

        public final void h(View view, float f) {
            ViewCompatHC.j(view, f);
        }

        public final void k(View view, float f) {
            ViewCompatHC.k(view, f);
        }

        public final void l(View view, float f) {
            ViewCompatHC.l(view, f);
        }

        public final float x(View view) {
            return ViewCompatHC.h(view);
        }

        public final float y(View view) {
            return ViewCompatHC.i(view);
        }

        public final float z(View view) {
            return ViewCompatHC.j(view);
        }

        public final float A(View view) {
            return ViewCompatHC.k(view);
        }

        public final float B(View view) {
            return ViewCompatHC.l(view);
        }

        public final float C(View view) {
            return ViewCompatHC.m(view);
        }

        public final float D(View view) {
            return ViewCompatHC.n(view);
        }

        public final float H(View view) {
            return ViewCompatHC.o(view);
        }

        public final float I(View view) {
            return ViewCompatHC.p(view);
        }

        public final void Q(View view) {
            ViewCompatHC.q(view);
        }

        public final void c(View view, boolean z) {
            ViewCompatHC.a(view, z);
        }

        public final void d(View view, boolean z) {
            ViewCompatHC.b(view, z);
        }

        public final int a(int i, int i2) {
            return ViewCompatHC.a(i, i2);
        }

        public void a(View view, Paint paint) {
            ViewCompatHC.a(view, ViewCompatHC.b(view), paint);
            view.invalidate();
        }
    }

    static class ICSMr1ViewCompatImpl extends ICSViewCompatImpl {
        ICSMr1ViewCompatImpl() {
        }

        public final boolean aa(View view) {
            return ViewCompatICSMr1.a(view);
        }
    }

    static class ICSViewCompatImpl extends HCViewCompatImpl {
        static Field b = null;
        static boolean c = false;

        ICSViewCompatImpl() {
        }

        public final boolean a(View view, int i) {
            return ViewCompatICS.a(view, i);
        }

        public final boolean b(View view, int i) {
            return ViewCompatICS.b(view, i);
        }

        public final void a(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.a(view, accessibilityEvent);
        }

        public final void b(View view, AccessibilityEvent accessibilityEvent) {
            ViewCompatICS.b(view, accessibilityEvent);
        }

        public final void a(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewCompatICS.b(view, accessibilityNodeInfoCompat.getInfo());
        }

        public final void a(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat) {
            ViewCompatICS.a(view, accessibilityDelegateCompat == null ? null : accessibilityDelegateCompat.getBridge());
        }

        public final boolean b(View view) {
            if (c) {
                return false;
            }
            if (b == null) {
                try {
                    Field declaredField = View.class.getDeclaredField("mAccessibilityDelegate");
                    b = declaredField;
                    declaredField.setAccessible(true);
                } catch (Throwable unused) {
                    c = true;
                    return false;
                }
            }
            try {
                if (b.get(view) != null) {
                    return true;
                }
                return false;
            } catch (Throwable unused2) {
                c = true;
                return false;
            }
        }

        public final ViewPropertyAnimatorCompat G(View view) {
            if (this.a == null) {
                this.a = new WeakHashMap();
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = (ViewPropertyAnimatorCompat) this.a.get(view);
            if (viewPropertyAnimatorCompat != null) {
                return viewPropertyAnimatorCompat;
            }
            ViewPropertyAnimatorCompat viewPropertyAnimatorCompat2 = new ViewPropertyAnimatorCompat(view);
            this.a.put(view, viewPropertyAnimatorCompat2);
            return viewPropertyAnimatorCompat2;
        }

        public final void b(View view, boolean z) {
            ViewCompatICS.a(view, z);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ImportantForAccessibility {
    }

    static class JBViewCompatImpl extends ICSMr1ViewCompatImpl {
        JBViewCompatImpl() {
        }

        public final boolean c(View view) {
            return ViewCompatJB.a(view);
        }

        public final void a(View view, boolean z) {
            ViewCompatJB.a(view, z);
        }

        public final void d(View view) {
            ViewCompatJB.b(view);
        }

        public final void a(View view, int i, int i2, int i3, int i4) {
            ViewCompatJB.a(view, i, i2, i3, i4);
        }

        public final void a(View view, Runnable runnable) {
            ViewCompatJB.a(view, runnable);
        }

        public final void a(View view, Runnable runnable, long j) {
            ViewCompatJB.a(view, runnable, j);
        }

        public final int e(View view) {
            return ViewCompatJB.c(view);
        }

        public void d(View view, int i) {
            if (i == 4) {
                i = 2;
            }
            ViewCompatJB.a(view, i);
        }

        public final boolean a(View view, int i, Bundle bundle) {
            return ViewCompatJB.a(view, i, bundle);
        }

        public final AccessibilityNodeProviderCompat f(View view) {
            Object d = ViewCompatJB.d(view);
            if (d != null) {
                return new AccessibilityNodeProviderCompat(d);
            }
            return null;
        }

        public final ViewParent k(View view) {
            return ViewCompatJB.e(view);
        }

        public final int E(View view) {
            return ViewCompatJB.f(view);
        }

        public final int F(View view) {
            return ViewCompatJB.g(view);
        }

        public void L(View view) {
            ViewCompatJB.h(view);
        }

        public final boolean P(View view) {
            return ViewCompatJB.i(view);
        }

        public final boolean u(View view) {
            return ViewCompatJB.j(view);
        }
    }

    static class JbMr1ViewCompatImpl extends JBViewCompatImpl {
        JbMr1ViewCompatImpl() {
        }

        public final int i(View view) {
            return ViewCompatJellybeanMr1.a(view);
        }

        public final void e(View view, int i) {
            ViewCompatJellybeanMr1.a(view, i);
        }

        public final void a(View view, Paint paint) {
            ViewCompatJellybeanMr1.a(view, paint);
        }

        public final int j(View view) {
            return ViewCompatJellybeanMr1.b(view);
        }

        public final void f(View view, int i) {
            ViewCompatJellybeanMr1.b(view, i);
        }

        public final int q(View view) {
            return ViewCompatJellybeanMr1.c(view);
        }

        public final int r(View view) {
            return ViewCompatJellybeanMr1.d(view);
        }

        public final void b(View view, int i, int i2, int i3, int i4) {
            ViewCompatJellybeanMr1.a(view, i, i2, i3, i4);
        }

        public final int K(View view) {
            return ViewCompatJellybeanMr1.e(view);
        }

        public final boolean R(View view) {
            return ViewCompatJellybeanMr1.f(view);
        }
    }

    static class JbMr2ViewCompatImpl extends JbMr1ViewCompatImpl {
        JbMr2ViewCompatImpl() {
        }

        public final void a(View view, Rect rect) {
            ViewCompatJellybeanMr2.a(view, rect);
        }

        public final Rect O(View view) {
            return ViewCompatJellybeanMr2.a(view);
        }
    }

    static class KitKatViewCompatImpl extends JbMr2ViewCompatImpl {
        KitKatViewCompatImpl() {
        }

        public final int p(View view) {
            return ViewCompatKitKat.a(view);
        }

        public final void g(View view, int i) {
            ViewCompatKitKat.a(view, i);
        }

        public final void d(View view, int i) {
            ViewCompatJB.a(view, i);
        }

        public final boolean X(View view) {
            return ViewCompatKitKat.b(view);
        }

        public final boolean Z(View view) {
            return ViewCompatKitKat.c(view);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface LayerType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface LayoutDirectionMode {
    }

    static class LollipopViewCompatImpl extends KitKatViewCompatImpl {
        LollipopViewCompatImpl() {
        }

        public final void a(View view, String str) {
            ViewCompatLollipop.a(view, str);
        }

        public final String J(View view) {
            return ViewCompatLollipop.a(view);
        }

        public final void L(View view) {
            ViewCompatLollipop.b(view);
        }

        public final void m(View view, float f) {
            ViewCompatLollipop.a(view, f);
        }

        public final float M(View view) {
            return ViewCompatLollipop.c(view);
        }

        public final void n(View view, float f) {
            ViewCompatLollipop.b(view, f);
        }

        public final float N(View view) {
            return ViewCompatLollipop.d(view);
        }

        public final void a(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
            ViewCompatLollipop.a(view, onApplyWindowInsetsListener);
        }

        public final void e(View view, boolean z) {
            ViewCompatLollipop.a(view, z);
        }

        public final boolean S(View view) {
            return ViewCompatLollipop.g(view);
        }

        public final boolean h(View view, int i) {
            return ViewCompatLollipop.a(view, i);
        }

        public final void V(View view) {
            ViewCompatLollipop.h(view);
        }

        public final boolean W(View view) {
            return ViewCompatLollipop.i(view);
        }

        public final boolean a(View view, int i, int i2, int i3, int i4, int[] iArr) {
            return ViewCompatLollipop.a(view, i, i2, i3, i4, iArr);
        }

        public final boolean a(View view, int i, int i2, int[] iArr, int[] iArr2) {
            return ViewCompatLollipop.a(view, i, i2, iArr, iArr2);
        }

        public final boolean a(View view, float f, float f2, boolean z) {
            return ViewCompatLollipop.a(view, f, f2, z);
        }

        public final boolean a(View view, float f, float f2) {
            return ViewCompatLollipop.a(view, f, f2);
        }

        public final ColorStateList T(View view) {
            return ViewCompatLollipop.e(view);
        }

        public final void a(View view, ColorStateList colorStateList) {
            ViewCompatLollipop.a(view, colorStateList);
        }

        public final void a(View view, Mode mode) {
            ViewCompatLollipop.a(view, mode);
        }

        public final Mode U(View view) {
            return ViewCompatLollipop.f(view);
        }

        public final WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat) {
            return ViewCompatLollipop.a(view, windowInsetsCompat);
        }

        public final WindowInsetsCompat b(View view, WindowInsetsCompat windowInsetsCompat) {
            return ViewCompatLollipop.b(view, windowInsetsCompat);
        }

        public final float Y(View view) {
            return ViewCompatLollipop.j(view);
        }
    }

    static class MarshmallowViewCompatImpl extends LollipopViewCompatImpl {
        MarshmallowViewCompatImpl() {
        }

        public final void i(View view, int i) {
            ViewCompatMarshmallow.a(view, i);
        }

        public final void a(View view, int i, int i2) {
            ViewCompatMarshmallow.a(view, i, i2);
        }

        public final int ab(View view) {
            return ViewCompatMarshmallow.a(view);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface OverScroll {
    }

    @Retention(RetentionPolicy.SOURCE)
    @interface ResolvedLayoutDirectionMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ScrollIndicators {
    }

    interface ViewCompatImpl {
        float A(View view);

        float B(View view);

        float C(View view);

        float D(View view);

        int E(View view);

        int F(View view);

        ViewPropertyAnimatorCompat G(View view);

        float H(View view);

        float I(View view);

        String J(View view);

        int K(View view);

        void L(View view);

        float M(View view);

        float N(View view);

        Rect O(View view);

        boolean P(View view);

        void Q(View view);

        boolean R(View view);

        boolean S(View view);

        ColorStateList T(View view);

        Mode U(View view);

        void V(View view);

        boolean W(View view);

        boolean X(View view);

        float Y(View view);

        boolean Z(View view);

        int a(int i, int i2);

        int a(int i, int i2, int i3);

        int a(View view);

        WindowInsetsCompat a(View view, WindowInsetsCompat windowInsetsCompat);

        void a(View view, float f);

        void a(View view, int i, int i2);

        void a(View view, int i, int i2, int i3, int i4);

        void a(View view, int i, Paint paint);

        void a(View view, ColorStateList colorStateList);

        void a(View view, Paint paint);

        void a(View view, Mode mode);

        void a(View view, Rect rect);

        void a(View view, @Nullable AccessibilityDelegateCompat accessibilityDelegateCompat);

        void a(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener);

        void a(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat);

        void a(View view, AccessibilityEvent accessibilityEvent);

        void a(View view, Runnable runnable);

        void a(View view, Runnable runnable, long j);

        void a(View view, String str);

        void a(View view, boolean z);

        void a(ViewGroup viewGroup, boolean z);

        boolean a(View view, float f, float f2);

        boolean a(View view, float f, float f2, boolean z);

        boolean a(View view, int i);

        boolean a(View view, int i, int i2, int i3, int i4, int[] iArr);

        boolean a(View view, int i, int i2, int[] iArr, int[] iArr2);

        boolean a(View view, int i, Bundle bundle);

        boolean aa(View view);

        int ab(View view);

        WindowInsetsCompat b(View view, WindowInsetsCompat windowInsetsCompat);

        void b(View view, float f);

        void b(View view, int i, int i2, int i3, int i4);

        void b(View view, AccessibilityEvent accessibilityEvent);

        void b(View view, boolean z);

        boolean b(View view);

        boolean b(View view, int i);

        void c(View view, float f);

        void c(View view, int i);

        void c(View view, boolean z);

        boolean c(View view);

        void d(View view);

        void d(View view, float f);

        void d(View view, int i);

        void d(View view, boolean z);

        int e(View view);

        void e(View view, float f);

        void e(View view, int i);

        void e(View view, boolean z);

        AccessibilityNodeProviderCompat f(View view);

        void f(View view, float f);

        void f(View view, int i);

        float g(View view);

        void g(View view, float f);

        void g(View view, int i);

        int h(View view);

        void h(View view, float f);

        boolean h(View view, int i);

        int i(View view);

        void i(View view, float f);

        void i(View view, int i);

        int j(View view);

        void j(View view, float f);

        ViewParent k(View view);

        void k(View view, float f);

        void l(View view, float f);

        boolean l(View view);

        int m(View view);

        void m(View view, float f);

        int n(View view);

        void n(View view, float f);

        int o(View view);

        int p(View view);

        int q(View view);

        int r(View view);

        void s(View view);

        void t(View view);

        boolean u(View view);

        float v(View view);

        float w(View view);

        float x(View view);

        float y(View view);

        float z(View view);
    }

    static {
        int i = VERSION.SDK_INT;
        if (i >= 23) {
            IMPL = new MarshmallowViewCompatImpl();
        } else if (i >= 21) {
            IMPL = new LollipopViewCompatImpl();
        } else if (i >= 19) {
            IMPL = new KitKatViewCompatImpl();
        } else if (i >= 17) {
            IMPL = new JbMr1ViewCompatImpl();
        } else if (i >= 16) {
            IMPL = new JBViewCompatImpl();
        } else if (i >= 15) {
            IMPL = new ICSMr1ViewCompatImpl();
        } else if (i >= 14) {
            IMPL = new ICSViewCompatImpl();
        } else if (i >= 11) {
            IMPL = new HCViewCompatImpl();
        } else if (i >= 9) {
            IMPL = new GBViewCompatImpl();
        } else if (i >= 7) {
            IMPL = new EclairMr1ViewCompatImpl();
        } else {
            IMPL = new BaseViewCompatImpl();
        }
    }

    public static boolean canScrollHorizontally(View view, int i) {
        return IMPL.a(view, i);
    }

    public static boolean canScrollVertically(View view, int i) {
        return IMPL.b(view, i);
    }

    public static int getOverScrollMode(View view) {
        return IMPL.a(view);
    }

    public static void setOverScrollMode(View view, int i) {
        IMPL.c(view, i);
    }

    public static void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.a(view, accessibilityEvent);
    }

    public static void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
        IMPL.b(view, accessibilityEvent);
    }

    public static void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        IMPL.a(view, accessibilityNodeInfoCompat);
    }

    public static void setAccessibilityDelegate(View view, AccessibilityDelegateCompat accessibilityDelegateCompat) {
        IMPL.a(view, accessibilityDelegateCompat);
    }

    public static boolean hasAccessibilityDelegate(View view) {
        return IMPL.b(view);
    }

    public static boolean hasTransientState(View view) {
        return IMPL.c(view);
    }

    public static void setHasTransientState(View view, boolean z) {
        IMPL.a(view, z);
    }

    public static void postInvalidateOnAnimation(View view) {
        IMPL.d(view);
    }

    public static void postInvalidateOnAnimation(View view, int i, int i2, int i3, int i4) {
        IMPL.a(view, i, i2, i3, i4);
    }

    public static void postOnAnimation(View view, Runnable runnable) {
        IMPL.a(view, runnable);
    }

    public static void postOnAnimationDelayed(View view, Runnable runnable, long j) {
        IMPL.a(view, runnable, j);
    }

    public static int getImportantForAccessibility(View view) {
        return IMPL.e(view);
    }

    public static void setImportantForAccessibility(View view, int i) {
        IMPL.d(view, i);
    }

    public static boolean performAccessibilityAction(View view, int i, Bundle bundle) {
        return IMPL.a(view, i, bundle);
    }

    public static AccessibilityNodeProviderCompat getAccessibilityNodeProvider(View view) {
        return IMPL.f(view);
    }

    public static float getAlpha(View view) {
        return IMPL.g(view);
    }

    public static void setLayerType(View view, int i, Paint paint) {
        IMPL.a(view, i, paint);
    }

    public static int getLayerType(View view) {
        return IMPL.h(view);
    }

    public static int getLabelFor(View view) {
        return IMPL.i(view);
    }

    public static void setLabelFor(View view, @IdRes int i) {
        IMPL.e(view, i);
    }

    public static void setLayerPaint(View view, Paint paint) {
        IMPL.a(view, paint);
    }

    public static int getLayoutDirection(View view) {
        return IMPL.j(view);
    }

    public static void setLayoutDirection(View view, int i) {
        IMPL.f(view, i);
    }

    public static ViewParent getParentForAccessibility(View view) {
        return IMPL.k(view);
    }

    public static boolean isOpaque(View view) {
        return IMPL.l(view);
    }

    public static int resolveSizeAndState(int i, int i2, int i3) {
        return IMPL.a(i, i2, i3);
    }

    public static int getMeasuredWidthAndState(View view) {
        return IMPL.m(view);
    }

    public static int getMeasuredHeightAndState(View view) {
        return IMPL.n(view);
    }

    public static int getMeasuredState(View view) {
        return IMPL.o(view);
    }

    public static int combineMeasuredStates(int i, int i2) {
        return IMPL.a(i, i2);
    }

    public static int getAccessibilityLiveRegion(View view) {
        return IMPL.p(view);
    }

    public static void setAccessibilityLiveRegion(View view, int i) {
        IMPL.g(view, i);
    }

    public static int getPaddingStart(View view) {
        return IMPL.q(view);
    }

    public static int getPaddingEnd(View view) {
        return IMPL.r(view);
    }

    public static void setPaddingRelative(View view, int i, int i2, int i3, int i4) {
        IMPL.b(view, i, i2, i3, i4);
    }

    public static void dispatchStartTemporaryDetach(View view) {
        IMPL.s(view);
    }

    public static void dispatchFinishTemporaryDetach(View view) {
        IMPL.t(view);
    }

    public static float getTranslationX(View view) {
        return IMPL.v(view);
    }

    public static float getTranslationY(View view) {
        return IMPL.w(view);
    }

    public static int getMinimumWidth(View view) {
        return IMPL.E(view);
    }

    public static int getMinimumHeight(View view) {
        return IMPL.F(view);
    }

    public static ViewPropertyAnimatorCompat animate(View view) {
        return IMPL.G(view);
    }

    public static void setTranslationX(View view, float f) {
        IMPL.b(view, f);
    }

    public static void setTranslationY(View view, float f) {
        IMPL.c(view, f);
    }

    public static void setAlpha(View view, @FloatRange(from = 0.0d, to = 1.0d) float f) {
        IMPL.d(view, f);
    }

    public static void setX(View view, float f) {
        IMPL.i(view, f);
    }

    public static void setY(View view, float f) {
        IMPL.j(view, f);
    }

    public static void setRotation(View view, float f) {
        IMPL.a(view, f);
    }

    public static void setRotationX(View view, float f) {
        IMPL.e(view, f);
    }

    public static void setRotationY(View view, float f) {
        IMPL.f(view, f);
    }

    public static void setScaleX(View view, float f) {
        IMPL.g(view, f);
    }

    public static void setScaleY(View view, float f) {
        IMPL.h(view, f);
    }

    public static float getPivotX(View view) {
        return IMPL.H(view);
    }

    public static void setPivotX(View view, float f) {
        IMPL.k(view, f);
    }

    public static float getPivotY(View view) {
        return IMPL.I(view);
    }

    public static void setPivotY(View view, float f) {
        IMPL.l(view, f);
    }

    public static float getRotation(View view) {
        return IMPL.z(view);
    }

    public static float getRotationX(View view) {
        return IMPL.A(view);
    }

    public static float getRotationY(View view) {
        return IMPL.B(view);
    }

    public static float getScaleX(View view) {
        return IMPL.C(view);
    }

    public static float getScaleY(View view) {
        return IMPL.D(view);
    }

    public static float getX(View view) {
        return IMPL.x(view);
    }

    public static float getY(View view) {
        return IMPL.y(view);
    }

    public static void setElevation(View view, float f) {
        IMPL.m(view, f);
    }

    public static float getElevation(View view) {
        return IMPL.M(view);
    }

    public static void setTranslationZ(View view, float f) {
        IMPL.n(view, f);
    }

    public static float getTranslationZ(View view) {
        return IMPL.N(view);
    }

    public static void setTransitionName(View view, String str) {
        IMPL.a(view, str);
    }

    public static String getTransitionName(View view) {
        return IMPL.J(view);
    }

    public static int getWindowSystemUiVisibility(View view) {
        return IMPL.K(view);
    }

    public static void requestApplyInsets(View view) {
        IMPL.L(view);
    }

    public static void setChildrenDrawingOrderEnabled(ViewGroup viewGroup, boolean z) {
        IMPL.a(viewGroup, z);
    }

    public static boolean getFitsSystemWindows(View view) {
        return IMPL.P(view);
    }

    public static void setFitsSystemWindows(View view, boolean z) {
        IMPL.b(view, z);
    }

    public static void jumpDrawablesToCurrentState(View view) {
        IMPL.Q(view);
    }

    public static void setOnApplyWindowInsetsListener(View view, OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        IMPL.a(view, onApplyWindowInsetsListener);
    }

    public static WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return IMPL.a(view, windowInsetsCompat);
    }

    public static WindowInsetsCompat dispatchApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
        return IMPL.b(view, windowInsetsCompat);
    }

    public static void setSaveFromParentEnabled(View view, boolean z) {
        IMPL.c(view, z);
    }

    public static void setActivated(View view, boolean z) {
        IMPL.d(view, z);
    }

    public static boolean hasOverlappingRendering(View view) {
        return IMPL.u(view);
    }

    public static boolean isPaddingRelative(View view) {
        return IMPL.R(view);
    }

    public static ColorStateList getBackgroundTintList(View view) {
        return IMPL.T(view);
    }

    public static void setBackgroundTintList(View view, ColorStateList colorStateList) {
        IMPL.a(view, colorStateList);
    }

    public static Mode getBackgroundTintMode(View view) {
        return IMPL.U(view);
    }

    public static void setBackgroundTintMode(View view, Mode mode) {
        IMPL.a(view, mode);
    }

    public static void setNestedScrollingEnabled(View view, boolean z) {
        IMPL.e(view, z);
    }

    public static boolean isNestedScrollingEnabled(View view) {
        return IMPL.S(view);
    }

    public static boolean startNestedScroll(View view, int i) {
        return IMPL.h(view, i);
    }

    public static void stopNestedScroll(View view) {
        IMPL.V(view);
    }

    public static boolean hasNestedScrollingParent(View view) {
        return IMPL.W(view);
    }

    public static boolean dispatchNestedScroll(View view, int i, int i2, int i3, int i4, int[] iArr) {
        return IMPL.a(view, i, i2, i3, i4, iArr);
    }

    public static boolean dispatchNestedPreScroll(View view, int i, int i2, int[] iArr, int[] iArr2) {
        return IMPL.a(view, i, i2, iArr, iArr2);
    }

    public static boolean dispatchNestedFling(View view, float f, float f2, boolean z) {
        return IMPL.a(view, f, f2, z);
    }

    public static boolean dispatchNestedPreFling(View view, float f, float f2) {
        return IMPL.a(view, f, f2);
    }

    public static boolean isLaidOut(View view) {
        return IMPL.X(view);
    }

    public static float getZ(View view) {
        return IMPL.Y(view);
    }

    public static void offsetTopAndBottom(View view, int i) {
        view.offsetTopAndBottom(i);
        if (i != 0 && VERSION.SDK_INT < 11) {
            view.invalidate();
        }
    }

    public static void offsetLeftAndRight(View view, int i) {
        view.offsetLeftAndRight(i);
        if (i != 0 && VERSION.SDK_INT < 11) {
            view.invalidate();
        }
    }

    public static void setClipBounds(View view, Rect rect) {
        IMPL.a(view, rect);
    }

    public static Rect getClipBounds(View view) {
        return IMPL.O(view);
    }

    public static boolean isAttachedToWindow(View view) {
        return IMPL.Z(view);
    }

    public static boolean hasOnClickListeners(View view) {
        return IMPL.aa(view);
    }

    public static void setScrollIndicators(@NonNull View view, int i) {
        IMPL.i(view, i);
    }

    public static void setScrollIndicators(@NonNull View view, int i, int i2) {
        IMPL.a(view, i, i2);
    }

    public static int getScrollIndicators(@NonNull View view) {
        return IMPL.ab(view);
    }
}
