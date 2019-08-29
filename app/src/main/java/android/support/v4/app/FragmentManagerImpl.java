package android.support.v4.app;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.CallSuper;
import android.support.v4.app.BackStackRecord.TransitionState;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.util.DebugUtils;
import android.support.v4.util.LogWriter;
import android.support.v4.view.LayoutInflaterFactory;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: FragmentManager */
final class FragmentManagerImpl extends FragmentManager implements LayoutInflaterFactory {
    static final Interpolator A = new DecelerateInterpolator(2.5f);
    static final Interpolator B = new DecelerateInterpolator(1.5f);
    static final Interpolator C = new AccelerateInterpolator(2.5f);
    static final Interpolator D = new AccelerateInterpolator(1.5f);
    static boolean a = false;
    static final boolean b = (VERSION.SDK_INT >= 11);
    static Field r;
    ArrayList<Runnable> c;
    Runnable[] d;
    boolean e;
    ArrayList<Fragment> f;
    ArrayList<Fragment> g;
    ArrayList<Integer> h;
    ArrayList<BackStackRecord> i;
    ArrayList<Fragment> j;
    ArrayList<BackStackRecord> k;
    ArrayList<Integer> l;
    ArrayList<OnBackStackChangedListener> m;
    int n = 0;
    FragmentHostCallback o;
    FragmentContainer p;
    Fragment q;
    boolean s;
    boolean t;
    boolean u;
    String v;
    boolean w;
    Bundle x = null;
    SparseArray<Parcelable> y = null;
    Runnable z = new Runnable() {
        public void run() {
            FragmentManagerImpl.this.b();
        }
    };

    /* compiled from: FragmentManager */
    static class AnimateOnHWLayerIfNeededListener implements AnimationListener {
        private AnimationListener a = null;
        private boolean b = false;
        /* access modifiers changed from: private */
        public View c = null;

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation) {
            if (view != null && animation != null) {
                this.c = view;
            }
        }

        public AnimateOnHWLayerIfNeededListener(View view, Animation animation, AnimationListener animationListener) {
            if (view != null && animation != null) {
                this.a = animationListener;
                this.c = view;
            }
        }

        @CallSuper
        public void onAnimationStart(Animation animation) {
            if (this.c != null) {
                this.b = FragmentManagerImpl.a(this.c, animation);
                if (this.b) {
                    this.c.post(new Runnable() {
                        public void run() {
                            ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.c, 2, null);
                        }
                    });
                }
            }
            if (this.a != null) {
                this.a.onAnimationStart(animation);
            }
        }

        @CallSuper
        public void onAnimationEnd(Animation animation) {
            if (this.c != null && this.b) {
                this.c.post(new Runnable() {
                    public void run() {
                        ViewCompat.setLayerType(AnimateOnHWLayerIfNeededListener.this.c, 0, null);
                    }
                });
            }
            if (this.a != null) {
                this.a.onAnimationEnd(animation);
            }
        }

        public void onAnimationRepeat(Animation animation) {
            if (this.a != null) {
                this.a.onAnimationRepeat(animation);
            }
        }
    }

    /* compiled from: FragmentManager */
    static class FragmentTag {
        public static final int[] a = {16842755, 16842960, 16842961};

        FragmentTag() {
        }
    }

    public static int b(int i2) {
        if (i2 == 4097) {
            return 8194;
        }
        if (i2 != 4099) {
            return i2 != 8194 ? 0 : 4097;
        }
        return 4099;
    }

    FragmentManagerImpl() {
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x003a A[RETURN] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static boolean a(android.view.View r3, android.view.animation.Animation r4) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 0
            r2 = 19
            if (r0 < r2) goto L_0x003b
            int r0 = android.support.v4.view.ViewCompat.getLayerType(r3)
            if (r0 != 0) goto L_0x003b
            boolean r3 = android.support.v4.view.ViewCompat.hasOverlappingRendering(r3)
            if (r3 == 0) goto L_0x003b
            boolean r3 = r4 instanceof android.view.animation.AlphaAnimation
            r0 = 1
            if (r3 == 0) goto L_0x001a
        L_0x0018:
            r3 = 1
            goto L_0x0038
        L_0x001a:
            boolean r3 = r4 instanceof android.view.animation.AnimationSet
            if (r3 == 0) goto L_0x0037
            android.view.animation.AnimationSet r4 = (android.view.animation.AnimationSet) r4
            java.util.List r3 = r4.getAnimations()
            r4 = 0
        L_0x0025:
            int r2 = r3.size()
            if (r4 >= r2) goto L_0x0037
            java.lang.Object r2 = r3.get(r4)
            boolean r2 = r2 instanceof android.view.animation.AlphaAnimation
            if (r2 == 0) goto L_0x0034
            goto L_0x0018
        L_0x0034:
            int r4 = r4 + 1
            goto L_0x0025
        L_0x0037:
            r3 = 0
        L_0x0038:
            if (r3 == 0) goto L_0x003b
            return r0
        L_0x003b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.a(android.view.View, android.view.animation.Animation):boolean");
    }

    private void a(RuntimeException runtimeException) {
        runtimeException.getMessage();
        PrintWriter printWriter = new PrintWriter(new LogWriter("FragmentManager"));
        if (this.o != null) {
            try {
                this.o.onDump("  ", null, printWriter, new String[0]);
            } catch (Exception unused) {
            }
        } else {
            dump("  ", null, printWriter, new String[0]);
        }
        throw runtimeException;
    }

    public final FragmentTransaction beginTransaction() {
        return new BackStackRecord(this);
    }

    public final boolean executePendingTransactions() {
        return b();
    }

    public final void popBackStack() {
        a((Runnable) new Runnable() {
            public void run() {
                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                FragmentManagerImpl.this.o.getHandler();
                fragmentManagerImpl.a((String) null, -1, 0);
            }
        }, false);
    }

    public final boolean popBackStackImmediate() {
        l();
        executePendingTransactions();
        this.o.getHandler();
        return a((String) null, -1, 0);
    }

    public final void popBackStack(final String str, final int i2) {
        a((Runnable) new Runnable() {
            public void run() {
                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                FragmentManagerImpl.this.o.getHandler();
                fragmentManagerImpl.a(str, -1, i2);
            }
        }, false);
    }

    public final boolean popBackStackImmediate(String str, int i2) {
        l();
        executePendingTransactions();
        this.o.getHandler();
        return a(str, -1, i2);
    }

    public final void popBackStack(final int i2, final int i3) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Bad id: ".concat(String.valueOf(i2)));
        }
        a((Runnable) new Runnable() {
            public void run() {
                FragmentManagerImpl fragmentManagerImpl = FragmentManagerImpl.this;
                FragmentManagerImpl.this.o.getHandler();
                fragmentManagerImpl.a((String) null, i2, i3);
            }
        }, false);
    }

    public final boolean popBackStackImmediate(int i2, int i3) {
        l();
        executePendingTransactions();
        if (i2 < 0) {
            throw new IllegalArgumentException("Bad id: ".concat(String.valueOf(i2)));
        }
        this.o.getHandler();
        return a((String) null, i2, i3);
    }

    public final int getBackStackEntryCount() {
        if (this.i != null) {
            return this.i.size();
        }
        return 0;
    }

    public final BackStackEntry getBackStackEntryAt(int i2) {
        return this.i.get(i2);
    }

    public final void addOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        if (this.m == null) {
            this.m = new ArrayList<>();
        }
        this.m.add(onBackStackChangedListener);
    }

    public final void removeOnBackStackChangedListener(OnBackStackChangedListener onBackStackChangedListener) {
        if (this.m != null) {
            this.m.remove(onBackStackChangedListener);
        }
    }

    public final void putFragment(Bundle bundle, String str, Fragment fragment) {
        if (fragment.mIndex < 0) {
            StringBuilder sb = new StringBuilder("Fragment ");
            sb.append(fragment);
            sb.append(" is not currently in the FragmentManager");
            a((RuntimeException) new IllegalStateException(sb.toString()));
        }
        bundle.putInt(str, fragment.mIndex);
    }

    public final Fragment getFragment(Bundle bundle, String str) {
        int i2 = bundle.getInt(str, -1);
        if (i2 == -1) {
            return null;
        }
        if (i2 >= this.f.size()) {
            StringBuilder sb = new StringBuilder("Fragment no longer exists for key ");
            sb.append(str);
            sb.append(": index ");
            sb.append(i2);
            a((RuntimeException) new IllegalStateException(sb.toString()));
        }
        Fragment fragment = this.f.get(i2);
        if (fragment == null) {
            StringBuilder sb2 = new StringBuilder("Fragment no longer exists for key ");
            sb2.append(str);
            sb2.append(": index ");
            sb2.append(i2);
            a((RuntimeException) new IllegalStateException(sb2.toString()));
        }
        return fragment;
    }

    public final List<Fragment> getFragments() {
        return this.f;
    }

    public final SavedState saveFragmentInstanceState(Fragment fragment) {
        if (fragment.mIndex < 0) {
            StringBuilder sb = new StringBuilder("Fragment ");
            sb.append(fragment);
            sb.append(" is not currently in the FragmentManager");
            a((RuntimeException) new IllegalStateException(sb.toString()));
        }
        if (fragment.mState <= 0) {
            return null;
        }
        Bundle f2 = f(fragment);
        if (f2 != null) {
            return new SavedState(f2);
        }
        return null;
    }

    public final boolean isDestroyed() {
        return this.u;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("FragmentManager{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append(" in ");
        if (this.q != null) {
            DebugUtils.buildShortClassTag(this.q, sb);
        } else {
            DebugUtils.buildShortClassTag(this.o, sb);
        }
        sb.append("}}");
        return sb.toString();
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("    ");
        String sb2 = sb.toString();
        if (this.f != null) {
            int size = this.f.size();
            if (size > 0) {
                printWriter.print(str);
                printWriter.print("Active Fragments in ");
                printWriter.print(Integer.toHexString(System.identityHashCode(this)));
                printWriter.println(":");
                for (int i2 = 0; i2 < size; i2++) {
                    Fragment fragment = this.f.get(i2);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i2);
                    printWriter.print(": ");
                    printWriter.println(fragment);
                    if (fragment != null) {
                        fragment.dump(sb2, fileDescriptor, printWriter, strArr);
                    }
                }
            }
        }
        if (this.g != null) {
            int size2 = this.g.size();
            if (size2 > 0) {
                printWriter.print(str);
                printWriter.println("Added Fragments:");
                for (int i3 = 0; i3 < size2; i3++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i3);
                    printWriter.print(": ");
                    printWriter.println(this.g.get(i3).toString());
                }
            }
        }
        if (this.j != null) {
            int size3 = this.j.size();
            if (size3 > 0) {
                printWriter.print(str);
                printWriter.println("Fragments Created Menus:");
                for (int i4 = 0; i4 < size3; i4++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i4);
                    printWriter.print(": ");
                    printWriter.println(this.j.get(i4).toString());
                }
            }
        }
        if (this.i != null) {
            int size4 = this.i.size();
            if (size4 > 0) {
                printWriter.print(str);
                printWriter.println("Back Stack:");
                for (int i5 = 0; i5 < size4; i5++) {
                    BackStackRecord backStackRecord = this.i.get(i5);
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i5);
                    printWriter.print(": ");
                    printWriter.println(backStackRecord.toString());
                    backStackRecord.a(sb2, printWriter);
                }
            }
        }
        synchronized (this) {
            if (this.k != null) {
                int size5 = this.k.size();
                if (size5 > 0) {
                    printWriter.print(str);
                    printWriter.println("Back Stack Indices:");
                    for (int i6 = 0; i6 < size5; i6++) {
                        printWriter.print(str);
                        printWriter.print("  #");
                        printWriter.print(i6);
                        printWriter.print(": ");
                        printWriter.println(this.k.get(i6));
                    }
                }
            }
            if (this.l != null && this.l.size() > 0) {
                printWriter.print(str);
                printWriter.print("mAvailBackStackIndices: ");
                printWriter.println(Arrays.toString(this.l.toArray()));
            }
        }
        if (this.c != null) {
            int size6 = this.c.size();
            if (size6 > 0) {
                printWriter.print(str);
                printWriter.println("Pending Actions:");
                for (int i7 = 0; i7 < size6; i7++) {
                    printWriter.print(str);
                    printWriter.print("  #");
                    printWriter.print(i7);
                    printWriter.print(": ");
                    printWriter.println(this.c.get(i7));
                }
            }
        }
        printWriter.print(str);
        printWriter.println("FragmentManager misc state:");
        printWriter.print(str);
        printWriter.print("  mHost=");
        printWriter.println(this.o);
        printWriter.print(str);
        printWriter.print("  mContainer=");
        printWriter.println(this.p);
        if (this.q != null) {
            printWriter.print(str);
            printWriter.print("  mParent=");
            printWriter.println(this.q);
        }
        printWriter.print(str);
        printWriter.print("  mCurState=");
        printWriter.print(this.n);
        printWriter.print(" mStateSaved=");
        printWriter.print(this.t);
        printWriter.print(" mDestroyed=");
        printWriter.println(this.u);
        if (this.s) {
            printWriter.print(str);
            printWriter.print("  mNeedMenuInvalidate=");
            printWriter.println(this.s);
        }
        if (this.v != null) {
            printWriter.print(str);
            printWriter.print("  mNoTransactionsBecause=");
            printWriter.println(this.v);
        }
        if (this.h != null && this.h.size() > 0) {
            printWriter.print(str);
            printWriter.print("  mAvailIndices: ");
            printWriter.println(Arrays.toString(this.h.toArray()));
        }
    }

    private static Animation a(float f2, float f3, float f4, float f5) {
        AnimationSet animationSet = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f3, f2, f3, 1, 0.5f, 1, 0.5f);
        scaleAnimation.setInterpolator(A);
        scaleAnimation.setDuration(220);
        animationSet.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(f4, f5);
        alphaAnimation.setInterpolator(B);
        alphaAnimation.setDuration(220);
        animationSet.addAnimation(alphaAnimation);
        return animationSet;
    }

    private static Animation a(float f2, float f3) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(f2, f3);
        alphaAnimation.setInterpolator(B);
        alphaAnimation.setDuration(220);
        return alphaAnimation;
    }

    private Animation a(Fragment fragment, int i2, boolean z2, int i3) {
        Animation onCreateAnimation = fragment.onCreateAnimation(i2, z2, fragment.mNextAnim);
        if (onCreateAnimation != null) {
            return onCreateAnimation;
        }
        if (fragment.mNextAnim != 0) {
            Animation loadAnimation = AnimationUtils.loadAnimation(this.o.getContext(), fragment.mNextAnim);
            if (loadAnimation != null) {
                return loadAnimation;
            }
        }
        if (i2 == 0) {
            return null;
        }
        char c2 = 65535;
        if (i2 == 4097) {
            c2 = z2 ? (char) 1 : 2;
        } else if (i2 == 4099) {
            c2 = z2 ? (char) 5 : 6;
        } else if (i2 == 8194) {
            c2 = z2 ? (char) 3 : 4;
        }
        if (c2 < 0) {
            return null;
        }
        switch (c2) {
            case 1:
                this.o.getContext();
                return a(1.125f, 1.0f, 0.0f, 1.0f);
            case 2:
                this.o.getContext();
                return a(1.0f, 0.975f, 1.0f, 0.0f);
            case 3:
                this.o.getContext();
                return a(0.975f, 1.0f, 0.0f, 1.0f);
            case 4:
                this.o.getContext();
                return a(1.0f, 1.075f, 1.0f, 0.0f);
            case 5:
                this.o.getContext();
                return a(0.0f, 1.0f);
            case 6:
                this.o.getContext();
                return a(1.0f, 0.0f);
            default:
                if (i3 == 0 && this.o.onHasWindowAnimations()) {
                    i3 = this.o.onGetWindowAnimations();
                }
                return i3 == 0 ? null : null;
        }
    }

    public final void a(Fragment fragment) {
        if (fragment.mDeferStart) {
            if (this.e) {
                this.w = true;
                return;
            }
            fragment.mDeferStart = false;
            a(fragment, this.n, 0, 0, false);
        }
    }

    private static void b(View view, Animation animation) {
        if (!(view == null || animation == null || !a(view, animation))) {
            AnimationListener animationListener = null;
            try {
                if (r == null) {
                    Field declaredField = Animation.class.getDeclaredField("mListener");
                    r = declaredField;
                    declaredField.setAccessible(true);
                }
                animationListener = (AnimationListener) r.get(animation);
            } catch (IllegalAccessException | NoSuchFieldException unused) {
            }
            animation.setAnimationListener(new AnimateOnHWLayerIfNeededListener(view, animation, animationListener));
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:101:0x01f9, code lost:
        if (a == false) goto L_0x0205;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:102:0x01fb, code lost:
        new java.lang.StringBuilder("moveto STARTED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:103:0x0205, code lost:
        r17.performStart();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:104:0x0208, code lost:
        if (r13 <= 4) goto L_0x032c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:106:0x020c, code lost:
        if (a == false) goto L_0x0218;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x020e, code lost:
        new java.lang.StringBuilder("moveto RESUMED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x0218, code lost:
        r7.mResumed = true;
        r17.performResume();
        r7.mSavedFragmentState = null;
        r7.mSavedViewState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:119:0x0244, code lost:
        if (r13 >= 4) goto L_0x0257;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:121:0x0248, code lost:
        if (a == false) goto L_0x0254;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:122:0x024a, code lost:
        new java.lang.StringBuilder("movefrom STARTED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:123:0x0254, code lost:
        r17.performStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:124:0x0257, code lost:
        if (r13 >= 3) goto L_0x026a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:126:0x025b, code lost:
        if (a == false) goto L_0x0267;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:127:0x025d, code lost:
        new java.lang.StringBuilder("movefrom STOPPED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:128:0x0267, code lost:
        r17.performReallyStop();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:130:0x026b, code lost:
        if (r13 >= 2) goto L_0x02cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:132:0x026f, code lost:
        if (a == false) goto L_0x027b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0271, code lost:
        new java.lang.StringBuilder("movefrom ACTIVITY_CREATED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:135:0x027d, code lost:
        if (r7.mView == null) goto L_0x028e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:137:0x0285, code lost:
        if (r6.o.onShouldSaveFragmentState(r7) == false) goto L_0x028e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:139:0x0289, code lost:
        if (r7.mSavedViewState != null) goto L_0x028e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:140:0x028b, code lost:
        e(r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:141:0x028e, code lost:
        r17.performDestroyView();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0293, code lost:
        if (r7.mView == null) goto L_0x02c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:144:0x0297, code lost:
        if (r7.mContainer == null) goto L_0x02c5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:146:0x029b, code lost:
        if (r6.n <= 0) goto L_0x02a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:148:0x029f, code lost:
        if (r6.u != false) goto L_0x02a6;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:149:0x02a1, code lost:
        r0 = a(r7, r8, false, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:150:0x02a6, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:151:0x02a7, code lost:
        if (r0 == null) goto L_0x02be;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:152:0x02a9, code lost:
        r7.mAnimatingAway = r7.mView;
        r7.mStateAfterAnimating = r13;
        r0.setAnimationListener(new android.support.v4.app.FragmentManagerImpl.AnonymousClass5(r6, r7.mView, r0));
        r7.mView.startAnimation(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:153:0x02be, code lost:
        r7.mContainer.removeView(r7.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:154:0x02c5, code lost:
        r7.mContainer = null;
        r7.mView = null;
        r7.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:155:0x02cb, code lost:
        if (r13 > 0) goto L_0x032c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:157:0x02cf, code lost:
        if (r6.u == false) goto L_0x02dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:159:0x02d3, code lost:
        if (r7.mAnimatingAway == null) goto L_0x02dc;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:160:0x02d5, code lost:
        r0 = r7.mAnimatingAway;
        r7.mAnimatingAway = null;
        r0.clearAnimation();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:162:0x02de, code lost:
        if (r7.mAnimatingAway == null) goto L_0x02e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x02e0, code lost:
        r7.mStateAfterAnimating = r13;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:165:0x02e5, code lost:
        if (a == false) goto L_0x02f1;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:166:0x02e7, code lost:
        new java.lang.StringBuilder("movefrom CREATED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:168:0x02f3, code lost:
        if (r7.mRetaining != false) goto L_0x02f8;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:169:0x02f5, code lost:
        r17.performDestroy();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:170:0x02f8, code lost:
        r7.mCalled = false;
        r17.onDetach();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:171:0x02ff, code lost:
        if (r7.mCalled != false) goto L_0x031a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:172:0x0301, code lost:
        r1 = new java.lang.StringBuilder("Fragment ");
        r1.append(r7);
        r1.append(" did not call through to super.onDetach()");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:173:0x0319, code lost:
        throw new android.support.v4.app.SuperNotCalledException(r1.toString());
     */
    /* JADX WARNING: Code restructure failed: missing block: B:174:0x031a, code lost:
        if (r21 != false) goto L_0x032c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:176:0x031e, code lost:
        if (r7.mRetaining != false) goto L_0x0324;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:177:0x0320, code lost:
        d(r17);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:178:0x0324, code lost:
        r7.mHost = null;
        r7.mParentFragment = null;
        r7.mFragmentManager = null;
        r7.mChildFragmentManager = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x013b, code lost:
        if (r13 <= 1) goto L_0x01f5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x013f, code lost:
        if (a == false) goto L_0x014b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x0141, code lost:
        new java.lang.StringBuilder("moveto ACTIVITY_CREATED: ").append(r7);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x014d, code lost:
        if (r7.mFromLayout != false) goto L_0x01e5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x0151, code lost:
        if (r7.mContainerId == 0) goto L_0x019a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x0153, code lost:
        r0 = (android.view.ViewGroup) r6.p.onFindViewById(r7.mContainerId);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x015d, code lost:
        if (r0 != null) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0161, code lost:
        if (r7.mRestored != false) goto L_0x019b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:0x0163, code lost:
        r3 = new java.lang.StringBuilder("No view found for id 0x");
        r3.append(java.lang.Integer.toHexString(r7.mContainerId));
        r3.append(" (");
        r3.append(r17.getResources().getResourceName(r7.mContainerId));
        r3.append(") for fragment ");
        r3.append(r7);
        a((java.lang.RuntimeException) new java.lang.IllegalArgumentException(r3.toString()));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x019a, code lost:
        r0 = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:82:0x019b, code lost:
        r7.mContainer = r0;
        r7.mView = r7.performCreateView(r7.getLayoutInflater(r7.mSavedFragmentState), r0, r7.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:0x01ad, code lost:
        if (r7.mView == null) goto L_0x01e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x01af, code lost:
        r7.mInnerView = r7.mView;
        r7.mView = android.support.v4.app.NoSaveStateFrameLayout.wrap(r7.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:85:0x01bb, code lost:
        if (r0 == null) goto L_0x01d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x01bd, code lost:
        r2 = a(r7, r8, true, r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x01c1, code lost:
        if (r2 == null) goto L_0x01cd;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x01c3, code lost:
        b(r7.mView, r2);
        r7.mView.startAnimation(r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x01cd, code lost:
        r0.addView(r7.mView);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x01d4, code lost:
        if (r7.mHidden == false) goto L_0x01db;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:92:0x01d6, code lost:
        r7.mView.setVisibility(8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:93:0x01db, code lost:
        r7.onViewCreated(r7.mView, r7.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:94:0x01e3, code lost:
        r7.mInnerView = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:95:0x01e5, code lost:
        r7.performActivityCreated(r7.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:96:0x01ec, code lost:
        if (r7.mView == null) goto L_0x01f3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:97:0x01ee, code lost:
        r7.restoreViewState(r7.mSavedFragmentState);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:98:0x01f3, code lost:
        r7.mSavedFragmentState = null;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:99:0x01f5, code lost:
        if (r13 <= 3) goto L_0x0208;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(android.support.v4.app.Fragment r17, int r18, int r19, int r20, boolean r21) {
        /*
            r16 = this;
            r6 = r16
            r7 = r17
            r8 = r19
            r9 = r20
            boolean r0 = r7.mAdded
            r10 = 1
            if (r0 == 0) goto L_0x0015
            boolean r0 = r7.mDetached
            if (r0 == 0) goto L_0x0012
            goto L_0x0015
        L_0x0012:
            r0 = r18
            goto L_0x001a
        L_0x0015:
            r0 = r18
            if (r0 <= r10) goto L_0x001a
            r0 = 1
        L_0x001a:
            boolean r1 = r7.mRemoving
            if (r1 == 0) goto L_0x0024
            int r1 = r7.mState
            if (r0 <= r1) goto L_0x0024
            int r0 = r7.mState
        L_0x0024:
            boolean r1 = r7.mDeferStart
            r11 = 4
            r12 = 3
            if (r1 == 0) goto L_0x0032
            int r1 = r7.mState
            if (r1 >= r11) goto L_0x0032
            if (r0 <= r12) goto L_0x0032
            r13 = 3
            goto L_0x0033
        L_0x0032:
            r13 = r0
        L_0x0033:
            int r0 = r7.mState
            r14 = 0
            r15 = 0
            if (r0 >= r13) goto L_0x0223
            boolean r0 = r7.mFromLayout
            if (r0 == 0) goto L_0x0042
            boolean r0 = r7.mInLayout
            if (r0 != 0) goto L_0x0042
            return
        L_0x0042:
            android.view.View r0 = r7.mAnimatingAway
            if (r0 == 0) goto L_0x0052
            r7.mAnimatingAway = r15
            int r2 = r7.mStateAfterAnimating
            r3 = 0
            r4 = 0
            r5 = 1
            r0 = r6
            r1 = r7
            r0.a(r1, r2, r3, r4, r5)
        L_0x0052:
            int r0 = r7.mState
            r1 = 8
            switch(r0) {
                case 0: goto L_0x005b;
                case 1: goto L_0x013b;
                case 2: goto L_0x01f5;
                case 3: goto L_0x01f5;
                case 4: goto L_0x0208;
                default: goto L_0x0059;
            }
        L_0x0059:
            goto L_0x032c
        L_0x005b:
            boolean r0 = a
            if (r0 == 0) goto L_0x0069
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "moveto CREATED: "
            r0.<init>(r2)
            r0.append(r7)
        L_0x0069:
            android.os.Bundle r0 = r7.mSavedFragmentState
            if (r0 == 0) goto L_0x00b1
            android.os.Bundle r0 = r7.mSavedFragmentState
            android.support.v4.app.FragmentHostCallback r2 = r6.o
            android.content.Context r2 = r2.getContext()
            java.lang.ClassLoader r2 = r2.getClassLoader()
            r0.setClassLoader(r2)
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r2 = "android:view_state"
            android.util.SparseArray r0 = r0.getSparseParcelableArray(r2)
            r7.mSavedViewState = r0
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r2 = "android:target_state"
            android.support.v4.app.Fragment r0 = r6.getFragment(r0, r2)
            r7.mTarget = r0
            android.support.v4.app.Fragment r0 = r7.mTarget
            if (r0 == 0) goto L_0x009e
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r2 = "android:target_req_state"
            int r0 = r0.getInt(r2, r14)
            r7.mTargetRequestCode = r0
        L_0x009e:
            android.os.Bundle r0 = r7.mSavedFragmentState
            java.lang.String r2 = "android:user_visible_hint"
            boolean r0 = r0.getBoolean(r2, r10)
            r7.mUserVisibleHint = r0
            boolean r0 = r7.mUserVisibleHint
            if (r0 != 0) goto L_0x00b1
            r7.mDeferStart = r10
            if (r13 <= r12) goto L_0x00b1
            r13 = 3
        L_0x00b1:
            android.support.v4.app.FragmentHostCallback r0 = r6.o
            r7.mHost = r0
            android.support.v4.app.Fragment r0 = r6.q
            r7.mParentFragment = r0
            android.support.v4.app.Fragment r0 = r6.q
            if (r0 == 0) goto L_0x00c2
            android.support.v4.app.Fragment r0 = r6.q
            android.support.v4.app.FragmentManagerImpl r0 = r0.mChildFragmentManager
            goto L_0x00c8
        L_0x00c2:
            android.support.v4.app.FragmentHostCallback r0 = r6.o
            android.support.v4.app.FragmentManagerImpl r0 = r0.getFragmentManagerImpl()
        L_0x00c8:
            r7.mFragmentManager = r0
            r7.mCalled = r14
            android.support.v4.app.FragmentHostCallback r0 = r6.o
            android.content.Context r0 = r0.getContext()
            r7.onAttach(r0)
            boolean r0 = r7.mCalled
            if (r0 != 0) goto L_0x00f2
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Fragment "
            r1.<init>(r2)
            r1.append(r7)
            java.lang.String r2 = " did not call through to super.onAttach()"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x00f2:
            android.support.v4.app.Fragment r0 = r7.mParentFragment
            if (r0 != 0) goto L_0x00fb
            android.support.v4.app.FragmentHostCallback r0 = r6.o
            r0.onAttachFragment(r7)
        L_0x00fb:
            boolean r0 = r7.mRetaining
            if (r0 != 0) goto L_0x0104
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.performCreate(r0)
        L_0x0104:
            r7.mRetaining = r14
            boolean r0 = r7.mFromLayout
            if (r0 == 0) goto L_0x013b
            android.os.Bundle r0 = r7.mSavedFragmentState
            android.view.LayoutInflater r0 = r7.getLayoutInflater(r0)
            android.os.Bundle r2 = r7.mSavedFragmentState
            android.view.View r0 = r7.performCreateView(r0, r15, r2)
            r7.mView = r0
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x0139
            android.view.View r0 = r7.mView
            r7.mInnerView = r0
            android.view.View r0 = r7.mView
            android.view.ViewGroup r0 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r0)
            r7.mView = r0
            boolean r0 = r7.mHidden
            if (r0 == 0) goto L_0x0131
            android.view.View r0 = r7.mView
            r0.setVisibility(r1)
        L_0x0131:
            android.view.View r0 = r7.mView
            android.os.Bundle r2 = r7.mSavedFragmentState
            r7.onViewCreated(r0, r2)
            goto L_0x013b
        L_0x0139:
            r7.mInnerView = r15
        L_0x013b:
            if (r13 <= r10) goto L_0x01f5
            boolean r0 = a
            if (r0 == 0) goto L_0x014b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r2 = "moveto ACTIVITY_CREATED: "
            r0.<init>(r2)
            r0.append(r7)
        L_0x014b:
            boolean r0 = r7.mFromLayout
            if (r0 != 0) goto L_0x01e5
            int r0 = r7.mContainerId
            if (r0 == 0) goto L_0x019a
            android.support.v4.app.FragmentContainer r0 = r6.p
            int r2 = r7.mContainerId
            android.view.View r0 = r0.onFindViewById(r2)
            android.view.ViewGroup r0 = (android.view.ViewGroup) r0
            if (r0 != 0) goto L_0x019b
            boolean r2 = r7.mRestored
            if (r2 != 0) goto L_0x019b
            java.lang.IllegalArgumentException r2 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "No view found for id 0x"
            r3.<init>(r4)
            int r4 = r7.mContainerId
            java.lang.String r4 = java.lang.Integer.toHexString(r4)
            r3.append(r4)
            java.lang.String r4 = " ("
            r3.append(r4)
            android.content.res.Resources r4 = r17.getResources()
            int r5 = r7.mContainerId
            java.lang.String r4 = r4.getResourceName(r5)
            r3.append(r4)
            java.lang.String r4 = ") for fragment "
            r3.append(r4)
            r3.append(r7)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            r6.a(r2)
            goto L_0x019b
        L_0x019a:
            r0 = r15
        L_0x019b:
            r7.mContainer = r0
            android.os.Bundle r2 = r7.mSavedFragmentState
            android.view.LayoutInflater r2 = r7.getLayoutInflater(r2)
            android.os.Bundle r3 = r7.mSavedFragmentState
            android.view.View r2 = r7.performCreateView(r2, r0, r3)
            r7.mView = r2
            android.view.View r2 = r7.mView
            if (r2 == 0) goto L_0x01e3
            android.view.View r2 = r7.mView
            r7.mInnerView = r2
            android.view.View r2 = r7.mView
            android.view.ViewGroup r2 = android.support.v4.app.NoSaveStateFrameLayout.wrap(r2)
            r7.mView = r2
            if (r0 == 0) goto L_0x01d2
            android.view.animation.Animation r2 = r6.a(r7, r8, r10, r9)
            if (r2 == 0) goto L_0x01cd
            android.view.View r3 = r7.mView
            b(r3, r2)
            android.view.View r3 = r7.mView
            r3.startAnimation(r2)
        L_0x01cd:
            android.view.View r2 = r7.mView
            r0.addView(r2)
        L_0x01d2:
            boolean r0 = r7.mHidden
            if (r0 == 0) goto L_0x01db
            android.view.View r0 = r7.mView
            r0.setVisibility(r1)
        L_0x01db:
            android.view.View r0 = r7.mView
            android.os.Bundle r1 = r7.mSavedFragmentState
            r7.onViewCreated(r0, r1)
            goto L_0x01e5
        L_0x01e3:
            r7.mInnerView = r15
        L_0x01e5:
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.performActivityCreated(r0)
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x01f3
            android.os.Bundle r0 = r7.mSavedFragmentState
            r7.restoreViewState(r0)
        L_0x01f3:
            r7.mSavedFragmentState = r15
        L_0x01f5:
            if (r13 <= r12) goto L_0x0208
            boolean r0 = a
            if (r0 == 0) goto L_0x0205
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "moveto STARTED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x0205:
            r17.performStart()
        L_0x0208:
            if (r13 <= r11) goto L_0x032c
            boolean r0 = a
            if (r0 == 0) goto L_0x0218
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "moveto RESUMED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x0218:
            r7.mResumed = r10
            r17.performResume()
            r7.mSavedFragmentState = r15
            r7.mSavedViewState = r15
            goto L_0x032c
        L_0x0223:
            int r0 = r7.mState
            if (r0 <= r13) goto L_0x032c
            int r0 = r7.mState
            switch(r0) {
                case 1: goto L_0x02cb;
                case 2: goto L_0x026a;
                case 3: goto L_0x0257;
                case 4: goto L_0x0244;
                case 5: goto L_0x022e;
                default: goto L_0x022c;
            }
        L_0x022c:
            goto L_0x032c
        L_0x022e:
            r0 = 5
            if (r13 >= r0) goto L_0x0244
            boolean r0 = a
            if (r0 == 0) goto L_0x023f
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom RESUMED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x023f:
            r17.performPause()
            r7.mResumed = r14
        L_0x0244:
            if (r13 >= r11) goto L_0x0257
            boolean r0 = a
            if (r0 == 0) goto L_0x0254
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom STARTED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x0254:
            r17.performStop()
        L_0x0257:
            if (r13 >= r12) goto L_0x026a
            boolean r0 = a
            if (r0 == 0) goto L_0x0267
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom STOPPED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x0267:
            r17.performReallyStop()
        L_0x026a:
            r0 = 2
            if (r13 >= r0) goto L_0x02cb
            boolean r0 = a
            if (r0 == 0) goto L_0x027b
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom ACTIVITY_CREATED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x027b:
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x028e
            android.support.v4.app.FragmentHostCallback r0 = r6.o
            boolean r0 = r0.onShouldSaveFragmentState(r7)
            if (r0 == 0) goto L_0x028e
            android.util.SparseArray<android.os.Parcelable> r0 = r7.mSavedViewState
            if (r0 != 0) goto L_0x028e
            r16.e(r17)
        L_0x028e:
            r17.performDestroyView()
            android.view.View r0 = r7.mView
            if (r0 == 0) goto L_0x02c5
            android.view.ViewGroup r0 = r7.mContainer
            if (r0 == 0) goto L_0x02c5
            int r0 = r6.n
            if (r0 <= 0) goto L_0x02a6
            boolean r0 = r6.u
            if (r0 != 0) goto L_0x02a6
            android.view.animation.Animation r0 = r6.a(r7, r8, r14, r9)
            goto L_0x02a7
        L_0x02a6:
            r0 = r15
        L_0x02a7:
            if (r0 == 0) goto L_0x02be
            android.view.View r1 = r7.mView
            r7.mAnimatingAway = r1
            r7.mStateAfterAnimating = r13
            android.view.View r1 = r7.mView
            android.support.v4.app.FragmentManagerImpl$5 r2 = new android.support.v4.app.FragmentManagerImpl$5
            r2.<init>(r1, r0, r7)
            r0.setAnimationListener(r2)
            android.view.View r1 = r7.mView
            r1.startAnimation(r0)
        L_0x02be:
            android.view.ViewGroup r0 = r7.mContainer
            android.view.View r1 = r7.mView
            r0.removeView(r1)
        L_0x02c5:
            r7.mContainer = r15
            r7.mView = r15
            r7.mInnerView = r15
        L_0x02cb:
            if (r13 > 0) goto L_0x032c
            boolean r0 = r6.u
            if (r0 == 0) goto L_0x02dc
            android.view.View r0 = r7.mAnimatingAway
            if (r0 == 0) goto L_0x02dc
            android.view.View r0 = r7.mAnimatingAway
            r7.mAnimatingAway = r15
            r0.clearAnimation()
        L_0x02dc:
            android.view.View r0 = r7.mAnimatingAway
            if (r0 == 0) goto L_0x02e3
            r7.mStateAfterAnimating = r13
            goto L_0x032d
        L_0x02e3:
            boolean r0 = a
            if (r0 == 0) goto L_0x02f1
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "movefrom CREATED: "
            r0.<init>(r1)
            r0.append(r7)
        L_0x02f1:
            boolean r0 = r7.mRetaining
            if (r0 != 0) goto L_0x02f8
            r17.performDestroy()
        L_0x02f8:
            r7.mCalled = r14
            r17.onDetach()
            boolean r0 = r7.mCalled
            if (r0 != 0) goto L_0x031a
            android.support.v4.app.SuperNotCalledException r0 = new android.support.v4.app.SuperNotCalledException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Fragment "
            r1.<init>(r2)
            r1.append(r7)
            java.lang.String r2 = " did not call through to super.onDetach()"
            r1.append(r2)
            java.lang.String r1 = r1.toString()
            r0.<init>(r1)
            throw r0
        L_0x031a:
            if (r21 != 0) goto L_0x032c
            boolean r0 = r7.mRetaining
            if (r0 != 0) goto L_0x0324
            r16.d(r17)
            goto L_0x032c
        L_0x0324:
            r7.mHost = r15
            r7.mParentFragment = r15
            r7.mFragmentManager = r15
            r7.mChildFragmentManager = r15
        L_0x032c:
            r10 = r13
        L_0x032d:
            r7.mState = r10
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.a(android.support.v4.app.Fragment, int, int, int, boolean):void");
    }

    private void b(Fragment fragment) {
        a(fragment, this.n, 0, 0, false);
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2) {
        a(i2, 0, 0, false);
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2, int i3, int i4, boolean z2) {
        if (this.o == null && i2 != 0) {
            throw new IllegalStateException("No host");
        } else if (z2 || this.n != i2) {
            this.n = i2;
            if (this.f != null) {
                boolean z3 = false;
                for (int i5 = 0; i5 < this.f.size(); i5++) {
                    Fragment fragment = this.f.get(i5);
                    if (fragment != null) {
                        a(fragment, i2, i3, i4, false);
                        if (fragment.mLoaderManager != null) {
                            z3 |= fragment.mLoaderManager.hasRunningLoaders();
                        }
                    }
                }
                if (!z3) {
                    a();
                }
                if (this.s && this.o != null && this.n == 5) {
                    this.o.onSupportInvalidateOptionsMenu();
                    this.s = false;
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final void a() {
        if (this.f != null) {
            for (int i2 = 0; i2 < this.f.size(); i2++) {
                Fragment fragment = this.f.get(i2);
                if (fragment != null) {
                    a(fragment);
                }
            }
        }
    }

    private void c(Fragment fragment) {
        if (fragment.mIndex < 0) {
            if (this.h == null || this.h.size() <= 0) {
                if (this.f == null) {
                    this.f = new ArrayList<>();
                }
                fragment.setIndex(this.f.size(), this.q);
                this.f.add(fragment);
            } else {
                fragment.setIndex(this.h.remove(this.h.size() - 1).intValue(), this.q);
                this.f.set(fragment.mIndex, fragment);
            }
            if (a) {
                new StringBuilder("Allocated fragment index ").append(fragment);
            }
        }
    }

    private void d(Fragment fragment) {
        if (fragment.mIndex >= 0) {
            if (a) {
                new StringBuilder("Freeing fragment index ").append(fragment);
            }
            this.f.set(fragment.mIndex, null);
            if (this.h == null) {
                this.h = new ArrayList<>();
            }
            this.h.add(Integer.valueOf(fragment.mIndex));
            this.o.inactivateFragment(fragment.mWho);
            fragment.initState();
        }
    }

    public final void a(Fragment fragment, boolean z2) {
        if (this.g == null) {
            this.g = new ArrayList<>();
        }
        if (a) {
            new StringBuilder("add: ").append(fragment);
        }
        c(fragment);
        if (fragment.mDetached) {
            return;
        }
        if (this.g.contains(fragment)) {
            throw new IllegalStateException("Fragment already added: ".concat(String.valueOf(fragment)));
        }
        this.g.add(fragment);
        fragment.mAdded = true;
        fragment.mRemoving = false;
        if (fragment.mHasMenu && fragment.mMenuVisible) {
            this.s = true;
        }
        if (z2) {
            b(fragment);
        }
    }

    public final void a(Fragment fragment, int i2, int i3) {
        if (a) {
            StringBuilder sb = new StringBuilder("remove: ");
            sb.append(fragment);
            sb.append(" nesting=");
            sb.append(fragment.mBackStackNesting);
        }
        boolean z2 = !fragment.isInBackStack();
        if (!fragment.mDetached || z2) {
            if (this.g != null) {
                this.g.remove(fragment);
            }
            if (fragment.mHasMenu && fragment.mMenuVisible) {
                this.s = true;
            }
            fragment.mAdded = false;
            fragment.mRemoving = true;
            a(fragment, z2 ? 0 : 1, i2, i3, false);
        }
    }

    public final void b(Fragment fragment, int i2, int i3) {
        if (a) {
            new StringBuilder("hide: ").append(fragment);
        }
        if (!fragment.mHidden) {
            fragment.mHidden = true;
            if (fragment.mView != null) {
                Animation a2 = a(fragment, i2, false, i3);
                if (a2 != null) {
                    b(fragment.mView, a2);
                    fragment.mView.startAnimation(a2);
                }
                fragment.mView.setVisibility(8);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.s = true;
            }
            fragment.onHiddenChanged(true);
        }
    }

    public final void c(Fragment fragment, int i2, int i3) {
        if (a) {
            new StringBuilder("show: ").append(fragment);
        }
        if (fragment.mHidden) {
            fragment.mHidden = false;
            if (fragment.mView != null) {
                Animation a2 = a(fragment, i2, true, i3);
                if (a2 != null) {
                    b(fragment.mView, a2);
                    fragment.mView.startAnimation(a2);
                }
                fragment.mView.setVisibility(0);
            }
            if (fragment.mAdded && fragment.mHasMenu && fragment.mMenuVisible) {
                this.s = true;
            }
            fragment.onHiddenChanged(false);
        }
    }

    public final void d(Fragment fragment, int i2, int i3) {
        if (a) {
            new StringBuilder("detach: ").append(fragment);
        }
        if (!fragment.mDetached) {
            fragment.mDetached = true;
            if (fragment.mAdded) {
                if (this.g != null) {
                    if (a) {
                        new StringBuilder("remove from detach: ").append(fragment);
                    }
                    this.g.remove(fragment);
                }
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.s = true;
                }
                fragment.mAdded = false;
                a(fragment, 1, i2, i3, false);
            }
        }
    }

    public final void e(Fragment fragment, int i2, int i3) {
        if (a) {
            new StringBuilder("attach: ").append(fragment);
        }
        if (fragment.mDetached) {
            fragment.mDetached = false;
            if (!fragment.mAdded) {
                if (this.g == null) {
                    this.g = new ArrayList<>();
                }
                if (this.g.contains(fragment)) {
                    throw new IllegalStateException("Fragment already added: ".concat(String.valueOf(fragment)));
                }
                if (a) {
                    new StringBuilder("add from attach: ").append(fragment);
                }
                this.g.add(fragment);
                fragment.mAdded = true;
                if (fragment.mHasMenu && fragment.mMenuVisible) {
                    this.s = true;
                }
                a(fragment, this.n, i2, i3, false);
            }
        }
    }

    public final Fragment findFragmentById(int i2) {
        if (this.g != null) {
            for (int size = this.g.size() - 1; size >= 0; size--) {
                Fragment fragment = this.g.get(size);
                if (fragment != null && fragment.mFragmentId == i2) {
                    return fragment;
                }
            }
        }
        if (this.f != null) {
            for (int size2 = this.f.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = this.f.get(size2);
                if (fragment2 != null && fragment2.mFragmentId == i2) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    public final Fragment findFragmentByTag(String str) {
        if (!(this.g == null || str == null)) {
            for (int size = this.g.size() - 1; size >= 0; size--) {
                Fragment fragment = this.g.get(size);
                if (fragment != null && str.equals(fragment.mTag)) {
                    return fragment;
                }
            }
        }
        if (!(this.f == null || str == null)) {
            for (int size2 = this.f.size() - 1; size2 >= 0; size2--) {
                Fragment fragment2 = this.f.get(size2);
                if (fragment2 != null && str.equals(fragment2.mTag)) {
                    return fragment2;
                }
            }
        }
        return null;
    }

    private void l() {
        if (this.t) {
            throw new IllegalStateException("Can not perform this action after onSaveInstanceState");
        } else if (this.v != null) {
            StringBuilder sb = new StringBuilder("Can not perform this action inside of ");
            sb.append(this.v);
            throw new IllegalStateException(sb.toString());
        }
    }

    public final void a(Runnable runnable, boolean z2) {
        if (!z2) {
            l();
        }
        synchronized (this) {
            if (!this.u) {
                if (this.o != null) {
                    if (this.c == null) {
                        this.c = new ArrayList<>();
                    }
                    this.c.add(runnable);
                    if (this.c.size() == 1) {
                        this.o.getHandler().removeCallbacks(this.z);
                        this.o.getHandler().post(this.z);
                    }
                }
            }
            throw new IllegalStateException("Activity has been destroyed");
        }
    }

    public final int a(BackStackRecord backStackRecord) {
        synchronized (this) {
            try {
                if (this.l != null) {
                    if (this.l.size() > 0) {
                        int intValue = this.l.remove(this.l.size() - 1).intValue();
                        if (a) {
                            StringBuilder sb = new StringBuilder("Adding back stack index ");
                            sb.append(intValue);
                            sb.append(" with ");
                            sb.append(backStackRecord);
                        }
                        this.k.set(intValue, backStackRecord);
                        return intValue;
                    }
                }
                if (this.k == null) {
                    this.k = new ArrayList<>();
                }
                int size = this.k.size();
                if (a) {
                    StringBuilder sb2 = new StringBuilder("Setting back stack index ");
                    sb2.append(size);
                    sb2.append(" to ");
                    sb2.append(backStackRecord);
                }
                this.k.add(backStackRecord);
                return size;
            }
        }
    }

    private void a(int i2, BackStackRecord backStackRecord) {
        synchronized (this) {
            if (this.k == null) {
                this.k = new ArrayList<>();
            }
            int size = this.k.size();
            if (i2 < size) {
                if (a) {
                    StringBuilder sb = new StringBuilder("Setting back stack index ");
                    sb.append(i2);
                    sb.append(" to ");
                    sb.append(backStackRecord);
                }
                this.k.set(i2, backStackRecord);
            } else {
                while (size < i2) {
                    this.k.add(null);
                    if (this.l == null) {
                        this.l = new ArrayList<>();
                    }
                    this.l.add(Integer.valueOf(size));
                    size++;
                }
                if (a) {
                    StringBuilder sb2 = new StringBuilder("Adding back stack index ");
                    sb2.append(i2);
                    sb2.append(" with ");
                    sb2.append(backStackRecord);
                }
                this.k.add(backStackRecord);
            }
        }
    }

    public final boolean isExecutingActions() {
        return this.e;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0060, code lost:
        r6.e = true;
        r3 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:24:0x0063, code lost:
        if (r3 >= r2) goto L_0x0074;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0065, code lost:
        r6.d[r3].run();
        r6.d[r3] = null;
        r3 = r3 + 1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean b() {
        /*
            r6 = this;
            boolean r0 = r6.e
            if (r0 == 0) goto L_0x000c
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Recursive entry to executePendingTransactions"
            r0.<init>(r1)
            throw r0
        L_0x000c:
            android.os.Looper r0 = android.os.Looper.myLooper()
            android.support.v4.app.FragmentHostCallback r1 = r6.o
            android.os.Handler r1 = r1.getHandler()
            android.os.Looper r1 = r1.getLooper()
            if (r0 == r1) goto L_0x0024
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "Must be called from main thread of process"
            r0.<init>(r1)
            throw r0
        L_0x0024:
            r0 = 1
            r1 = 0
            r2 = 0
        L_0x0027:
            monitor-enter(r6)
            java.util.ArrayList<java.lang.Runnable> r3 = r6.c     // Catch:{ all -> 0x00a7 }
            if (r3 == 0) goto L_0x0078
            java.util.ArrayList<java.lang.Runnable> r3 = r6.c     // Catch:{ all -> 0x00a7 }
            int r3 = r3.size()     // Catch:{ all -> 0x00a7 }
            if (r3 != 0) goto L_0x0035
            goto L_0x0078
        L_0x0035:
            java.util.ArrayList<java.lang.Runnable> r2 = r6.c     // Catch:{ all -> 0x00a7 }
            int r2 = r2.size()     // Catch:{ all -> 0x00a7 }
            java.lang.Runnable[] r3 = r6.d     // Catch:{ all -> 0x00a7 }
            if (r3 == 0) goto L_0x0044
            java.lang.Runnable[] r3 = r6.d     // Catch:{ all -> 0x00a7 }
            int r3 = r3.length     // Catch:{ all -> 0x00a7 }
            if (r3 >= r2) goto L_0x0048
        L_0x0044:
            java.lang.Runnable[] r3 = new java.lang.Runnable[r2]     // Catch:{ all -> 0x00a7 }
            r6.d = r3     // Catch:{ all -> 0x00a7 }
        L_0x0048:
            java.util.ArrayList<java.lang.Runnable> r3 = r6.c     // Catch:{ all -> 0x00a7 }
            java.lang.Runnable[] r4 = r6.d     // Catch:{ all -> 0x00a7 }
            r3.toArray(r4)     // Catch:{ all -> 0x00a7 }
            java.util.ArrayList<java.lang.Runnable> r3 = r6.c     // Catch:{ all -> 0x00a7 }
            r3.clear()     // Catch:{ all -> 0x00a7 }
            android.support.v4.app.FragmentHostCallback r3 = r6.o     // Catch:{ all -> 0x00a7 }
            android.os.Handler r3 = r3.getHandler()     // Catch:{ all -> 0x00a7 }
            java.lang.Runnable r4 = r6.z     // Catch:{ all -> 0x00a7 }
            r3.removeCallbacks(r4)     // Catch:{ all -> 0x00a7 }
            monitor-exit(r6)     // Catch:{ all -> 0x00a7 }
            r6.e = r0
            r3 = 0
        L_0x0063:
            if (r3 >= r2) goto L_0x0074
            java.lang.Runnable[] r4 = r6.d
            r4 = r4[r3]
            r4.run()
            java.lang.Runnable[] r4 = r6.d
            r5 = 0
            r4[r3] = r5
            int r3 = r3 + 1
            goto L_0x0063
        L_0x0074:
            r6.e = r1
            r2 = 1
            goto L_0x0027
        L_0x0078:
            monitor-exit(r6)     // Catch:{ all -> 0x00a7 }
            boolean r0 = r6.w
            if (r0 == 0) goto L_0x00a6
            r0 = 0
            r3 = 0
        L_0x007f:
            java.util.ArrayList<android.support.v4.app.Fragment> r4 = r6.f
            int r4 = r4.size()
            if (r0 >= r4) goto L_0x009f
            java.util.ArrayList<android.support.v4.app.Fragment> r4 = r6.f
            java.lang.Object r4 = r4.get(r0)
            android.support.v4.app.Fragment r4 = (android.support.v4.app.Fragment) r4
            if (r4 == 0) goto L_0x009c
            android.support.v4.app.LoaderManagerImpl r5 = r4.mLoaderManager
            if (r5 == 0) goto L_0x009c
            android.support.v4.app.LoaderManagerImpl r4 = r4.mLoaderManager
            boolean r4 = r4.hasRunningLoaders()
            r3 = r3 | r4
        L_0x009c:
            int r0 = r0 + 1
            goto L_0x007f
        L_0x009f:
            if (r3 != 0) goto L_0x00a6
            r6.w = r1
            r6.a()
        L_0x00a6:
            return r2
        L_0x00a7:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x00a7 }
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.b():boolean");
    }

    /* access modifiers changed from: 0000 */
    public final void c() {
        if (this.m != null) {
            for (int i2 = 0; i2 < this.m.size(); i2++) {
                this.m.get(i2).onBackStackChanged();
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public final boolean a(String str, int i2, int i3) {
        int i4;
        if (this.i == null) {
            return false;
        }
        if (str == null && i2 < 0 && (i3 & 1) == 0) {
            int size = this.i.size() - 1;
            if (size < 0) {
                return false;
            }
            BackStackRecord remove = this.i.remove(size);
            SparseArray sparseArray = new SparseArray();
            SparseArray sparseArray2 = new SparseArray();
            remove.a(sparseArray, sparseArray2);
            remove.a(true, (TransitionState) null, sparseArray, sparseArray2);
        } else {
            if (str != null || i2 >= 0) {
                int size2 = this.i.size() - 1;
                while (i4 >= 0) {
                    BackStackRecord backStackRecord = this.i.get(i4);
                    if ((str != null && str.equals(backStackRecord.getName())) || (i2 >= 0 && i2 == backStackRecord.p)) {
                        break;
                    }
                    size2 = i4 - 1;
                }
                if (i4 < 0) {
                    return false;
                }
                if ((i3 & 1) != 0) {
                    i4--;
                    while (i4 >= 0) {
                        BackStackRecord backStackRecord2 = this.i.get(i4);
                        if ((str == null || !str.equals(backStackRecord2.getName())) && (i2 < 0 || i2 != backStackRecord2.p)) {
                            break;
                        }
                        i4--;
                    }
                }
            } else {
                i4 = -1;
            }
            if (i4 == this.i.size() - 1) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (int size3 = this.i.size() - 1; size3 > i4; size3--) {
                arrayList.add(this.i.remove(size3));
            }
            int size4 = arrayList.size() - 1;
            SparseArray sparseArray3 = new SparseArray();
            SparseArray sparseArray4 = new SparseArray();
            for (int i5 = 0; i5 <= size4; i5++) {
                ((BackStackRecord) arrayList.get(i5)).a(sparseArray3, sparseArray4);
            }
            TransitionState transitionState = null;
            int i6 = 0;
            while (i6 <= size4) {
                if (a) {
                    new StringBuilder("Popping back stack state: ").append(arrayList.get(i6));
                }
                transitionState = ((BackStackRecord) arrayList.get(i6)).a(i6 == size4, transitionState, sparseArray3, sparseArray4);
                i6++;
            }
        }
        c();
        return true;
    }

    private void e(Fragment fragment) {
        if (fragment.mInnerView != null) {
            if (this.y == null) {
                this.y = new SparseArray<>();
            } else {
                this.y.clear();
            }
            fragment.mInnerView.saveHierarchyState(this.y);
            if (this.y.size() > 0) {
                fragment.mSavedViewState = this.y;
                this.y = null;
            }
        }
    }

    private Bundle f(Fragment fragment) {
        Bundle bundle;
        if (this.x == null) {
            this.x = new Bundle();
        }
        fragment.performSaveInstanceState(this.x);
        if (!this.x.isEmpty()) {
            bundle = this.x;
            this.x = null;
        } else {
            bundle = null;
        }
        if (fragment.mView != null) {
            e(fragment);
        }
        if (fragment.mSavedViewState != null) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putSparseParcelableArray("android:view_state", fragment.mSavedViewState);
        }
        if (!fragment.mUserVisibleHint) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putBoolean("android:user_visible_hint", fragment.mUserVisibleHint);
        }
        return bundle;
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Removed duplicated region for block: B:53:0x0145  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.os.Parcelable d() {
        /*
            r11 = this;
            r11.b()
            boolean r0 = b
            r1 = 1
            if (r0 == 0) goto L_0x000a
            r11.t = r1
        L_0x000a:
            java.util.ArrayList<android.support.v4.app.Fragment> r0 = r11.f
            r2 = 0
            if (r0 == 0) goto L_0x018c
            java.util.ArrayList<android.support.v4.app.Fragment> r0 = r11.f
            int r0 = r0.size()
            if (r0 > 0) goto L_0x0019
            goto L_0x018c
        L_0x0019:
            java.util.ArrayList<android.support.v4.app.Fragment> r0 = r11.f
            int r0 = r0.size()
            android.support.v4.app.FragmentState[] r3 = new android.support.v4.app.FragmentState[r0]
            r4 = 0
            r5 = 0
            r6 = 0
        L_0x0024:
            if (r5 >= r0) goto L_0x00d6
            java.util.ArrayList<android.support.v4.app.Fragment> r7 = r11.f
            java.lang.Object r7 = r7.get(r5)
            android.support.v4.app.Fragment r7 = (android.support.v4.app.Fragment) r7
            if (r7 == 0) goto L_0x00d2
            int r6 = r7.mIndex
            if (r6 >= 0) goto L_0x0054
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Failure saving state: active "
            r8.<init>(r9)
            r8.append(r7)
            java.lang.String r9 = " has cleared index: "
            r8.append(r9)
            int r9 = r7.mIndex
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            r6.<init>(r8)
            r11.a(r6)
        L_0x0054:
            android.support.v4.app.FragmentState r6 = new android.support.v4.app.FragmentState
            r6.<init>(r7)
            r3[r5] = r6
            int r8 = r7.mState
            if (r8 <= 0) goto L_0x00b5
            android.os.Bundle r8 = r6.j
            if (r8 != 0) goto L_0x00b5
            android.os.Bundle r8 = r11.f(r7)
            r6.j = r8
            android.support.v4.app.Fragment r8 = r7.mTarget
            if (r8 == 0) goto L_0x00b9
            android.support.v4.app.Fragment r8 = r7.mTarget
            int r8 = r8.mIndex
            if (r8 >= 0) goto L_0x0093
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r10 = "Failure saving state: "
            r9.<init>(r10)
            r9.append(r7)
            java.lang.String r10 = " has target not in fragment manager: "
            r9.append(r10)
            android.support.v4.app.Fragment r10 = r7.mTarget
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            r8.<init>(r9)
            r11.a(r8)
        L_0x0093:
            android.os.Bundle r8 = r6.j
            if (r8 != 0) goto L_0x009e
            android.os.Bundle r8 = new android.os.Bundle
            r8.<init>()
            r6.j = r8
        L_0x009e:
            android.os.Bundle r8 = r6.j
            java.lang.String r9 = "android:target_state"
            android.support.v4.app.Fragment r10 = r7.mTarget
            r11.putFragment(r8, r9, r10)
            int r8 = r7.mTargetRequestCode
            if (r8 == 0) goto L_0x00b9
            android.os.Bundle r8 = r6.j
            java.lang.String r9 = "android:target_req_state"
            int r10 = r7.mTargetRequestCode
            r8.putInt(r9, r10)
            goto L_0x00b9
        L_0x00b5:
            android.os.Bundle r8 = r7.mSavedFragmentState
            r6.j = r8
        L_0x00b9:
            boolean r8 = a
            if (r8 == 0) goto L_0x00d1
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "Saved state of "
            r8.<init>(r9)
            r8.append(r7)
            java.lang.String r7 = ": "
            r8.append(r7)
            android.os.Bundle r6 = r6.j
            r8.append(r6)
        L_0x00d1:
            r6 = 1
        L_0x00d2:
            int r5 = r5 + 1
            goto L_0x0024
        L_0x00d6:
            if (r6 != 0) goto L_0x00d9
            return r2
        L_0x00d9:
            java.util.ArrayList<android.support.v4.app.Fragment> r0 = r11.g
            if (r0 == 0) goto L_0x0140
            java.util.ArrayList<android.support.v4.app.Fragment> r0 = r11.g
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0140
            int[] r1 = new int[r0]
            r5 = 0
        L_0x00e8:
            if (r5 >= r0) goto L_0x0141
            java.util.ArrayList<android.support.v4.app.Fragment> r6 = r11.g
            java.lang.Object r6 = r6.get(r5)
            android.support.v4.app.Fragment r6 = (android.support.v4.app.Fragment) r6
            int r6 = r6.mIndex
            r1[r5] = r6
            r6 = r1[r5]
            if (r6 >= 0) goto L_0x0120
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.StringBuilder r7 = new java.lang.StringBuilder
            java.lang.String r8 = "Failure saving state: active "
            r7.<init>(r8)
            java.util.ArrayList<android.support.v4.app.Fragment> r8 = r11.g
            java.lang.Object r8 = r8.get(r5)
            r7.append(r8)
            java.lang.String r8 = " has cleared index: "
            r7.append(r8)
            r8 = r1[r5]
            r7.append(r8)
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            r11.a(r6)
        L_0x0120:
            boolean r6 = a
            if (r6 == 0) goto L_0x013d
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            java.lang.String r7 = "saveAllState: adding fragment #"
            r6.<init>(r7)
            r6.append(r5)
            java.lang.String r7 = ": "
            r6.append(r7)
            java.util.ArrayList<android.support.v4.app.Fragment> r7 = r11.g
            java.lang.Object r7 = r7.get(r5)
            r6.append(r7)
        L_0x013d:
            int r5 = r5 + 1
            goto L_0x00e8
        L_0x0140:
            r1 = r2
        L_0x0141:
            java.util.ArrayList<android.support.v4.app.BackStackRecord> r0 = r11.i
            if (r0 == 0) goto L_0x0180
            java.util.ArrayList<android.support.v4.app.BackStackRecord> r0 = r11.i
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0180
            android.support.v4.app.BackStackState[] r2 = new android.support.v4.app.BackStackState[r0]
        L_0x014f:
            if (r4 >= r0) goto L_0x0180
            android.support.v4.app.BackStackState r5 = new android.support.v4.app.BackStackState
            java.util.ArrayList<android.support.v4.app.BackStackRecord> r6 = r11.i
            java.lang.Object r6 = r6.get(r4)
            android.support.v4.app.BackStackRecord r6 = (android.support.v4.app.BackStackRecord) r6
            r5.<init>(r6)
            r2[r4] = r5
            boolean r5 = a
            if (r5 == 0) goto L_0x017d
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "saveAllState: adding back stack #"
            r5.<init>(r6)
            r5.append(r4)
            java.lang.String r6 = ": "
            r5.append(r6)
            java.util.ArrayList<android.support.v4.app.BackStackRecord> r6 = r11.i
            java.lang.Object r6 = r6.get(r4)
            r5.append(r6)
        L_0x017d:
            int r4 = r4 + 1
            goto L_0x014f
        L_0x0180:
            android.support.v4.app.FragmentManagerState r0 = new android.support.v4.app.FragmentManagerState
            r0.<init>()
            r0.a = r3
            r0.b = r1
            r0.c = r2
            return r0
        L_0x018c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.FragmentManagerImpl.d():android.os.Parcelable");
    }

    /* access modifiers changed from: 0000 */
    public final void a(Parcelable parcelable, List<Fragment> list) {
        if (parcelable != null) {
            FragmentManagerState fragmentManagerState = (FragmentManagerState) parcelable;
            if (fragmentManagerState.a != null) {
                if (list != null) {
                    for (int i2 = 0; i2 < list.size(); i2++) {
                        Fragment fragment = list.get(i2);
                        if (a) {
                            new StringBuilder("restoreAllState: re-attaching retained ").append(fragment);
                        }
                        FragmentState fragmentState = fragmentManagerState.a[fragment.mIndex];
                        fragmentState.k = fragment;
                        fragment.mSavedViewState = null;
                        fragment.mBackStackNesting = 0;
                        fragment.mInLayout = false;
                        fragment.mAdded = false;
                        fragment.mTarget = null;
                        if (fragmentState.j != null) {
                            fragmentState.j.setClassLoader(this.o.getContext().getClassLoader());
                            fragment.mSavedViewState = fragmentState.j.getSparseParcelableArray("android:view_state");
                            fragment.mSavedFragmentState = fragmentState.j;
                        }
                    }
                }
                this.f = new ArrayList<>(fragmentManagerState.a.length);
                if (this.h != null) {
                    this.h.clear();
                }
                for (int i3 = 0; i3 < fragmentManagerState.a.length; i3++) {
                    FragmentState fragmentState2 = fragmentManagerState.a[i3];
                    if (fragmentState2 != null) {
                        Fragment a2 = fragmentState2.a(this.o, this.q);
                        if (a) {
                            StringBuilder sb = new StringBuilder("restoreAllState: active #");
                            sb.append(i3);
                            sb.append(": ");
                            sb.append(a2);
                        }
                        this.f.add(a2);
                        fragmentState2.k = null;
                    } else {
                        this.f.add(null);
                        if (this.h == null) {
                            this.h = new ArrayList<>();
                        }
                        this.h.add(Integer.valueOf(i3));
                    }
                }
                if (list != null) {
                    for (int i4 = 0; i4 < list.size(); i4++) {
                        Fragment fragment2 = list.get(i4);
                        if (fragment2.mTargetIndex >= 0) {
                            if (fragment2.mTargetIndex < this.f.size()) {
                                fragment2.mTarget = this.f.get(fragment2.mTargetIndex);
                            } else {
                                StringBuilder sb2 = new StringBuilder("Re-attaching retained fragment ");
                                sb2.append(fragment2);
                                sb2.append(" target no longer exists: ");
                                sb2.append(fragment2.mTargetIndex);
                                fragment2.mTarget = null;
                            }
                        }
                    }
                }
                if (fragmentManagerState.b != null) {
                    this.g = new ArrayList<>(fragmentManagerState.b.length);
                    for (int i5 = 0; i5 < fragmentManagerState.b.length; i5++) {
                        Fragment fragment3 = this.f.get(fragmentManagerState.b[i5]);
                        if (fragment3 == null) {
                            StringBuilder sb3 = new StringBuilder("No instantiated fragment for index #");
                            sb3.append(fragmentManagerState.b[i5]);
                            a((RuntimeException) new IllegalStateException(sb3.toString()));
                        }
                        fragment3.mAdded = true;
                        if (a) {
                            StringBuilder sb4 = new StringBuilder("restoreAllState: added #");
                            sb4.append(i5);
                            sb4.append(": ");
                            sb4.append(fragment3);
                        }
                        if (this.g.contains(fragment3)) {
                            throw new IllegalStateException("Already added!");
                        }
                        this.g.add(fragment3);
                    }
                } else {
                    this.g = null;
                }
                if (fragmentManagerState.c != null) {
                    this.i = new ArrayList<>(fragmentManagerState.c.length);
                    for (int i6 = 0; i6 < fragmentManagerState.c.length; i6++) {
                        BackStackRecord a3 = fragmentManagerState.c[i6].a(this);
                        if (a) {
                            StringBuilder sb5 = new StringBuilder("restoreAllState: back stack #");
                            sb5.append(i6);
                            sb5.append(" (index ");
                            sb5.append(a3.p);
                            sb5.append("): ");
                            sb5.append(a3);
                            a3.a((String) "  ", new PrintWriter(new LogWriter("FragmentManager")), false);
                        }
                        this.i.add(a3);
                        if (a3.p >= 0) {
                            a(a3.p, a3);
                        }
                    }
                    return;
                }
                this.i = null;
            }
        }
    }

    public final void a(FragmentHostCallback fragmentHostCallback, FragmentContainer fragmentContainer, Fragment fragment) {
        if (this.o != null) {
            throw new IllegalStateException("Already attached");
        }
        this.o = fragmentHostCallback;
        this.p = fragmentContainer;
        this.q = fragment;
    }

    public final void e() {
        this.t = false;
        a(1);
    }

    public final void f() {
        this.t = false;
        a(2);
    }

    public final void g() {
        this.t = false;
        a(4);
    }

    public final void h() {
        this.t = false;
        a(5);
    }

    public final void i() {
        this.t = true;
        a(3);
    }

    public final void j() {
        this.u = true;
        b();
        a(0);
        this.o = null;
        this.p = null;
        this.q = null;
    }

    public final void a(Configuration configuration) {
        if (this.g != null) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                Fragment fragment = this.g.get(i2);
                if (fragment != null) {
                    fragment.performConfigurationChanged(configuration);
                }
            }
        }
    }

    public final void k() {
        if (this.g != null) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                Fragment fragment = this.g.get(i2);
                if (fragment != null) {
                    fragment.performLowMemory();
                }
            }
        }
    }

    public final boolean a(Menu menu, MenuInflater menuInflater) {
        boolean z2;
        ArrayList<Fragment> arrayList = null;
        if (this.g != null) {
            z2 = false;
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                Fragment fragment = this.g.get(i2);
                if (fragment != null && fragment.performCreateOptionsMenu(menu, menuInflater)) {
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    arrayList.add(fragment);
                    z2 = true;
                }
            }
        } else {
            z2 = false;
        }
        if (this.j != null) {
            for (int i3 = 0; i3 < this.j.size(); i3++) {
                Fragment fragment2 = this.j.get(i3);
                if (arrayList == null || !arrayList.contains(fragment2)) {
                    fragment2.onDestroyOptionsMenu();
                }
            }
        }
        this.j = arrayList;
        return z2;
    }

    public final boolean a(Menu menu) {
        if (this.g == null) {
            return false;
        }
        boolean z2 = false;
        for (int i2 = 0; i2 < this.g.size(); i2++) {
            Fragment fragment = this.g.get(i2);
            if (fragment != null && fragment.performPrepareOptionsMenu(menu)) {
                z2 = true;
            }
        }
        return z2;
    }

    public final boolean a(MenuItem menuItem) {
        if (this.g != null) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                Fragment fragment = this.g.get(i2);
                if (fragment != null && fragment.performOptionsItemSelected(menuItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final boolean b(MenuItem menuItem) {
        if (this.g != null) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                Fragment fragment = this.g.get(i2);
                if (fragment != null && fragment.performContextItemSelected(menuItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void b(Menu menu) {
        if (this.g != null) {
            for (int i2 = 0; i2 < this.g.size(); i2++) {
                Fragment fragment = this.g.get(i2);
                if (fragment != null) {
                    fragment.performOptionsMenuClosed(menu);
                }
            }
        }
    }

    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Fragment fragment;
        Fragment fragment2 = null;
        if (!"fragment".equals(str)) {
            return null;
        }
        String attributeValue = attributeSet.getAttributeValue(null, "class");
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, FragmentTag.a);
        int i2 = 0;
        if (attributeValue == null) {
            attributeValue = obtainStyledAttributes.getString(0);
        }
        String str2 = attributeValue;
        int resourceId = obtainStyledAttributes.getResourceId(1, -1);
        String string = obtainStyledAttributes.getString(2);
        obtainStyledAttributes.recycle();
        if (!Fragment.isSupportFragmentClass(this.o.getContext(), str2)) {
            return null;
        }
        if (view != null) {
            i2 = view.getId();
        }
        if (i2 == -1 && resourceId == -1 && string == null) {
            StringBuilder sb = new StringBuilder();
            sb.append(attributeSet.getPositionDescription());
            sb.append(": Must specify unique android:id, android:tag, or have a parent with an id for ");
            sb.append(str2);
            throw new IllegalArgumentException(sb.toString());
        }
        if (resourceId != -1) {
            fragment2 = findFragmentById(resourceId);
        }
        if (fragment2 == null && string != null) {
            fragment2 = findFragmentByTag(string);
        }
        if (fragment2 == null && i2 != -1) {
            fragment2 = findFragmentById(i2);
        }
        if (a) {
            StringBuilder sb2 = new StringBuilder("onCreateView: id=0x");
            sb2.append(Integer.toHexString(resourceId));
            sb2.append(" fname=");
            sb2.append(str2);
            sb2.append(" existing=");
            sb2.append(fragment2);
        }
        if (fragment2 == null) {
            Fragment instantiate = Fragment.instantiate(context, str2);
            instantiate.mFromLayout = true;
            instantiate.mFragmentId = resourceId != 0 ? resourceId : i2;
            instantiate.mContainerId = i2;
            instantiate.mTag = string;
            instantiate.mInLayout = true;
            instantiate.mFragmentManager = this;
            instantiate.mHost = this.o;
            instantiate.onInflate(this.o.getContext(), attributeSet, instantiate.mSavedFragmentState);
            a(instantiate, true);
            fragment = instantiate;
        } else if (fragment2.mInLayout) {
            StringBuilder sb3 = new StringBuilder();
            sb3.append(attributeSet.getPositionDescription());
            sb3.append(": Duplicate id 0x");
            sb3.append(Integer.toHexString(resourceId));
            sb3.append(", tag ");
            sb3.append(string);
            sb3.append(", or parent id 0x");
            sb3.append(Integer.toHexString(i2));
            sb3.append(" with another fragment for ");
            sb3.append(str2);
            throw new IllegalArgumentException(sb3.toString());
        } else {
            fragment2.mInLayout = true;
            if (!fragment2.mRetaining) {
                fragment2.onInflate(this.o.getContext(), attributeSet, fragment2.mSavedFragmentState);
            }
            fragment = fragment2;
        }
        if (this.n > 0 || !fragment.mFromLayout) {
            b(fragment);
        } else {
            a(fragment, 1, 0, 0, false);
        }
        if (fragment.mView == null) {
            StringBuilder sb4 = new StringBuilder("Fragment ");
            sb4.append(str2);
            sb4.append(" did not create a view.");
            throw new IllegalStateException(sb4.toString());
        }
        if (resourceId != 0) {
            fragment.mView.setId(resourceId);
        }
        if (fragment.mView.getTag() == null) {
            fragment.mView.setTag(string);
        }
        return fragment.mView;
    }
}
