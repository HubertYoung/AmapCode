package android.support.v4.app;

import android.os.Build.VERSION;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransitionCompat21.EpicenterView;
import android.support.v4.app.FragmentTransitionCompat21.ViewRetriever;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LogWriter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import com.alipay.mobile.beehive.util.MiscUtil;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.alipay.sdk.util.h;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

final class BackStackRecord extends FragmentTransaction implements BackStackEntry, Runnable {
    static final boolean a = (VERSION.SDK_INT >= 21);
    final FragmentManagerImpl b;
    Op c;
    Op d;
    int e;
    int f;
    int g;
    int h;
    int i;
    int j;
    int k;
    boolean l;
    boolean m = true;
    String n;
    boolean o;
    int p = -1;
    int q;
    CharSequence r;
    int s;
    CharSequence t;
    ArrayList<String> u;
    ArrayList<String> v;

    static final class Op {
        Op a;
        Op b;
        int c;
        Fragment d;
        int e;
        int f;
        int g;
        int h;
        ArrayList<Fragment> i;

        Op() {
        }
    }

    public class TransitionState {
        public EpicenterView enteringEpicenterView = new EpicenterView();
        public ArrayList<View> hiddenFragmentViews = new ArrayList<>();
        public ArrayMap<String, String> nameOverrides = new ArrayMap<>();
        public View nonExistentView;

        public TransitionState() {
        }
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append("BackStackEntry{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.p >= 0) {
            sb.append(" #");
            sb.append(this.p);
        }
        if (this.n != null) {
            sb.append(Token.SEPARATOR);
            sb.append(this.n);
        }
        sb.append(h.d);
        return sb.toString();
    }

    public final void a(String str, PrintWriter printWriter) {
        a(str, printWriter, true);
    }

    public final void a(String str, PrintWriter printWriter, boolean z) {
        String str2;
        if (z) {
            printWriter.print(str);
            printWriter.print("mName=");
            printWriter.print(this.n);
            printWriter.print(" mIndex=");
            printWriter.print(this.p);
            printWriter.print(" mCommitted=");
            printWriter.println(this.o);
            if (this.j != 0) {
                printWriter.print(str);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.j));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.k));
            }
            if (!(this.f == 0 && this.g == 0)) {
                printWriter.print(str);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.f));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.g));
            }
            if (!(this.h == 0 && this.i == 0)) {
                printWriter.print(str);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.h));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.i));
            }
            if (!(this.q == 0 && this.r == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.q));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.r);
            }
            if (!(this.s == 0 && this.t == null)) {
                printWriter.print(str);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.s));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.t);
            }
        }
        if (this.c != null) {
            printWriter.print(str);
            printWriter.println("Operations:");
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            sb.append("    ");
            String sb2 = sb.toString();
            Op op = this.c;
            int i2 = 0;
            while (op != null) {
                switch (op.c) {
                    case 0:
                        str2 = MiscUtil.NULL_STR;
                        break;
                    case 1:
                        str2 = "ADD";
                        break;
                    case 2:
                        str2 = "REPLACE";
                        break;
                    case 3:
                        str2 = "REMOVE";
                        break;
                    case 4:
                        str2 = "HIDE";
                        break;
                    case 5:
                        str2 = "SHOW";
                        break;
                    case 6:
                        str2 = "DETACH";
                        break;
                    case 7:
                        str2 = "ATTACH";
                        break;
                    default:
                        StringBuilder sb3 = new StringBuilder("cmd=");
                        sb3.append(op.c);
                        str2 = sb3.toString();
                        break;
                }
                printWriter.print(str);
                printWriter.print("  Op #");
                printWriter.print(i2);
                printWriter.print(": ");
                printWriter.print(str2);
                printWriter.print(Token.SEPARATOR);
                printWriter.println(op.d);
                if (z) {
                    if (!(op.e == 0 && op.f == 0)) {
                        printWriter.print(str);
                        printWriter.print("enterAnim=#");
                        printWriter.print(Integer.toHexString(op.e));
                        printWriter.print(" exitAnim=#");
                        printWriter.println(Integer.toHexString(op.f));
                    }
                    if (!(op.g == 0 && op.h == 0)) {
                        printWriter.print(str);
                        printWriter.print("popEnterAnim=#");
                        printWriter.print(Integer.toHexString(op.g));
                        printWriter.print(" popExitAnim=#");
                        printWriter.println(Integer.toHexString(op.h));
                    }
                }
                if (op.i != null && op.i.size() > 0) {
                    for (int i3 = 0; i3 < op.i.size(); i3++) {
                        printWriter.print(sb2);
                        if (op.i.size() == 1) {
                            printWriter.print("Removed: ");
                        } else {
                            if (i3 == 0) {
                                printWriter.println("Removed:");
                            }
                            printWriter.print(sb2);
                            printWriter.print("  #");
                            printWriter.print(i3);
                            printWriter.print(": ");
                        }
                        printWriter.println(op.i.get(i3));
                    }
                }
                op = op.a;
                i2++;
            }
        }
    }

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        this.b = fragmentManagerImpl;
    }

    public final int getId() {
        return this.p;
    }

    public final int getBreadCrumbTitleRes() {
        return this.q;
    }

    public final int getBreadCrumbShortTitleRes() {
        return this.s;
    }

    public final CharSequence getBreadCrumbTitle() {
        if (this.q != 0) {
            return this.b.o.getContext().getText(this.q);
        }
        return this.r;
    }

    public final CharSequence getBreadCrumbShortTitle() {
        if (this.s != 0) {
            return this.b.o.getContext().getText(this.s);
        }
        return this.t;
    }

    /* access modifiers changed from: 0000 */
    public final void a(Op op) {
        if (this.c == null) {
            this.d = op;
            this.c = op;
        } else {
            op.b = this.d;
            this.d.a = op;
            this.d = op;
        }
        op.e = this.f;
        op.f = this.g;
        op.g = this.h;
        op.h = this.i;
        this.e++;
    }

    public final FragmentTransaction add(Fragment fragment, String str) {
        a(0, fragment, str, 1);
        return this;
    }

    public final FragmentTransaction add(int i2, Fragment fragment) {
        a(i2, fragment, (String) null, 1);
        return this;
    }

    public final FragmentTransaction add(int i2, Fragment fragment, String str) {
        a(i2, fragment, str, 1);
        return this;
    }

    private void a(int i2, Fragment fragment, String str, int i3) {
        fragment.mFragmentManager = this.b;
        if (str != null) {
            if (fragment.mTag == null || str.equals(fragment.mTag)) {
                fragment.mTag = str;
            } else {
                StringBuilder sb = new StringBuilder("Can't change tag of fragment ");
                sb.append(fragment);
                sb.append(": was ");
                sb.append(fragment.mTag);
                sb.append(" now ");
                sb.append(str);
                throw new IllegalStateException(sb.toString());
            }
        }
        if (i2 != 0) {
            if (fragment.mFragmentId == 0 || fragment.mFragmentId == i2) {
                fragment.mFragmentId = i2;
                fragment.mContainerId = i2;
            } else {
                StringBuilder sb2 = new StringBuilder("Can't change container ID of fragment ");
                sb2.append(fragment);
                sb2.append(": was ");
                sb2.append(fragment.mFragmentId);
                sb2.append(" now ");
                sb2.append(i2);
                throw new IllegalStateException(sb2.toString());
            }
        }
        Op op = new Op();
        op.c = i3;
        op.d = fragment;
        a(op);
    }

    public final FragmentTransaction replace(int i2, Fragment fragment) {
        return replace(i2, fragment, null);
    }

    public final FragmentTransaction replace(int i2, Fragment fragment, String str) {
        if (i2 == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        a(i2, fragment, str, 2);
        return this;
    }

    public final FragmentTransaction remove(Fragment fragment) {
        Op op = new Op();
        op.c = 3;
        op.d = fragment;
        a(op);
        return this;
    }

    public final FragmentTransaction hide(Fragment fragment) {
        Op op = new Op();
        op.c = 4;
        op.d = fragment;
        a(op);
        return this;
    }

    public final FragmentTransaction show(Fragment fragment) {
        Op op = new Op();
        op.c = 5;
        op.d = fragment;
        a(op);
        return this;
    }

    public final FragmentTransaction detach(Fragment fragment) {
        Op op = new Op();
        op.c = 6;
        op.d = fragment;
        a(op);
        return this;
    }

    public final FragmentTransaction attach(Fragment fragment) {
        Op op = new Op();
        op.c = 7;
        op.d = fragment;
        a(op);
        return this;
    }

    public final FragmentTransaction setCustomAnimations(int i2, int i3) {
        return setCustomAnimations(i2, i3, 0, 0);
    }

    public final FragmentTransaction setCustomAnimations(int i2, int i3, int i4, int i5) {
        this.f = i2;
        this.g = i3;
        this.h = i4;
        this.i = i5;
        return this;
    }

    public final FragmentTransaction setTransition(int i2) {
        this.j = i2;
        return this;
    }

    public final FragmentTransaction addSharedElement(View view, String str) {
        if (a) {
            String a2 = FragmentTransitionCompat21.a(view);
            if (a2 == null) {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
            if (this.u == null) {
                this.u = new ArrayList<>();
                this.v = new ArrayList<>();
            }
            this.u.add(a2);
            this.v.add(str);
        }
        return this;
    }

    public final FragmentTransaction setTransitionStyle(int i2) {
        this.k = i2;
        return this;
    }

    public final FragmentTransaction addToBackStack(String str) {
        if (!this.m) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.l = true;
        this.n = str;
        return this;
    }

    public final boolean isAddToBackStackAllowed() {
        return this.m;
    }

    public final FragmentTransaction disallowAddToBackStack() {
        if (this.l) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.m = false;
        return this;
    }

    public final FragmentTransaction setBreadCrumbTitle(int i2) {
        this.q = i2;
        this.r = null;
        return this;
    }

    public final FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        this.q = 0;
        this.r = charSequence;
        return this;
    }

    public final FragmentTransaction setBreadCrumbShortTitle(int i2) {
        this.s = i2;
        this.t = null;
        return this;
    }

    public final FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        this.s = 0;
        this.t = charSequence;
        return this;
    }

    /* access modifiers changed from: 0000 */
    public final void a(int i2) {
        if (this.l) {
            if (FragmentManagerImpl.a) {
                StringBuilder sb = new StringBuilder("Bump nesting in ");
                sb.append(this);
                sb.append(" by ");
                sb.append(i2);
            }
            Op op = this.c;
            while (op != null) {
                if (op.d != null) {
                    op.d.mBackStackNesting += i2;
                    if (FragmentManagerImpl.a) {
                        StringBuilder sb2 = new StringBuilder("Bump nesting of ");
                        sb2.append(op.d);
                        sb2.append(" to ");
                        sb2.append(op.d.mBackStackNesting);
                    }
                }
                if (op.i != null) {
                    for (int size = op.i.size() - 1; size >= 0; size--) {
                        Fragment fragment = op.i.get(size);
                        fragment.mBackStackNesting += i2;
                        if (FragmentManagerImpl.a) {
                            StringBuilder sb3 = new StringBuilder("Bump nesting of ");
                            sb3.append(fragment);
                            sb3.append(" to ");
                            sb3.append(fragment.mBackStackNesting);
                        }
                    }
                }
                op = op.a;
            }
        }
    }

    public final int commit() {
        return a(false);
    }

    public final int commitAllowingStateLoss() {
        return a(true);
    }

    private int a(boolean z) {
        if (this.o) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.a) {
            new StringBuilder("Commit: ").append(this);
            a((String) "  ", new PrintWriter(new LogWriter("FragmentManager")));
        }
        this.o = true;
        if (this.l) {
            this.p = this.b.a(this);
        } else {
            this.p = -1;
        }
        this.b.a((Runnable) this, z);
        return this.p;
    }

    public final void run() {
        TransitionState transitionState;
        int i2;
        int i3;
        int i4;
        int i5;
        if (FragmentManagerImpl.a) {
            new StringBuilder("Run: ").append(this);
        }
        if (!this.l || this.p >= 0) {
            a(1);
            if (a) {
                SparseArray sparseArray = new SparseArray();
                SparseArray sparseArray2 = new SparseArray();
                b(sparseArray, sparseArray2);
                transitionState = a(sparseArray, sparseArray2, false);
            } else {
                transitionState = null;
            }
            if (transitionState != null) {
                i2 = 0;
            } else {
                i2 = this.k;
            }
            if (transitionState != null) {
                i3 = 0;
            } else {
                i3 = this.j;
            }
            for (Op op = this.c; op != null; op = op.a) {
                if (transitionState != null) {
                    i4 = 0;
                } else {
                    i4 = op.e;
                }
                if (transitionState != null) {
                    i5 = 0;
                } else {
                    i5 = op.f;
                }
                switch (op.c) {
                    case 1:
                        Fragment fragment = op.d;
                        fragment.mNextAnim = i4;
                        this.b.a(fragment, false);
                        break;
                    case 2:
                        Fragment fragment2 = op.d;
                        int i6 = fragment2.mContainerId;
                        if (this.b.g != null) {
                            Fragment fragment3 = fragment2;
                            for (int i7 = 0; i7 < this.b.g.size(); i7++) {
                                Fragment fragment4 = this.b.g.get(i7);
                                if (FragmentManagerImpl.a) {
                                    StringBuilder sb = new StringBuilder("OP_REPLACE: adding=");
                                    sb.append(fragment3);
                                    sb.append(" old=");
                                    sb.append(fragment4);
                                }
                                if (fragment4.mContainerId == i6) {
                                    if (fragment4 == fragment3) {
                                        op.d = null;
                                        fragment3 = null;
                                    } else {
                                        if (op.i == null) {
                                            op.i = new ArrayList<>();
                                        }
                                        op.i.add(fragment4);
                                        fragment4.mNextAnim = i5;
                                        if (this.l) {
                                            fragment4.mBackStackNesting++;
                                            if (FragmentManagerImpl.a) {
                                                StringBuilder sb2 = new StringBuilder("Bump nesting of ");
                                                sb2.append(fragment4);
                                                sb2.append(" to ");
                                                sb2.append(fragment4.mBackStackNesting);
                                            }
                                        }
                                        this.b.a(fragment4, i3, i2);
                                    }
                                }
                            }
                            fragment2 = fragment3;
                        }
                        if (fragment2 == null) {
                            break;
                        } else {
                            fragment2.mNextAnim = i4;
                            this.b.a(fragment2, false);
                            break;
                        }
                    case 3:
                        Fragment fragment5 = op.d;
                        fragment5.mNextAnim = i5;
                        this.b.a(fragment5, i3, i2);
                        break;
                    case 4:
                        Fragment fragment6 = op.d;
                        fragment6.mNextAnim = i5;
                        this.b.b(fragment6, i3, i2);
                        break;
                    case 5:
                        Fragment fragment7 = op.d;
                        fragment7.mNextAnim = i4;
                        this.b.c(fragment7, i3, i2);
                        break;
                    case 6:
                        Fragment fragment8 = op.d;
                        fragment8.mNextAnim = i5;
                        this.b.d(fragment8, i3, i2);
                        break;
                    case 7:
                        Fragment fragment9 = op.d;
                        fragment9.mNextAnim = i4;
                        this.b.e(fragment9, i3, i2);
                        break;
                    default:
                        StringBuilder sb3 = new StringBuilder("Unknown cmd: ");
                        sb3.append(op.c);
                        throw new IllegalArgumentException(sb3.toString());
                }
            }
            this.b.a(this.b.n, i3, i2, true);
            if (this.l) {
                FragmentManagerImpl fragmentManagerImpl = this.b;
                if (fragmentManagerImpl.i == null) {
                    fragmentManagerImpl.i = new ArrayList<>();
                }
                fragmentManagerImpl.i.add(this);
                fragmentManagerImpl.c();
                return;
            }
            return;
        }
        throw new IllegalStateException("addToBackStack() called after commit()");
    }

    private static void a(SparseArray<Fragment> sparseArray, Fragment fragment) {
        if (fragment != null) {
            int i2 = fragment.mContainerId;
            if (i2 != 0 && !fragment.isHidden() && fragment.isAdded() && fragment.getView() != null && sparseArray.get(i2) == null) {
                sparseArray.put(i2, fragment);
            }
        }
    }

    private static void b(SparseArray<Fragment> sparseArray, Fragment fragment) {
        if (fragment != null) {
            int i2 = fragment.mContainerId;
            if (i2 != 0) {
                sparseArray.put(i2, fragment);
            }
        }
    }

    private void b(SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2) {
        if (this.b.p.onHasView()) {
            for (Op op = this.c; op != null; op = op.a) {
                switch (op.c) {
                    case 1:
                        b(sparseArray2, op.d);
                        break;
                    case 2:
                        Fragment fragment = op.d;
                        if (this.b.g != null) {
                            for (int i2 = 0; i2 < this.b.g.size(); i2++) {
                                Fragment fragment2 = this.b.g.get(i2);
                                if (fragment == null || fragment2.mContainerId == fragment.mContainerId) {
                                    if (fragment2 == fragment) {
                                        fragment = null;
                                    } else {
                                        a(sparseArray, fragment2);
                                    }
                                }
                            }
                        }
                        b(sparseArray2, fragment);
                        break;
                    case 3:
                        a(sparseArray, op.d);
                        break;
                    case 4:
                        a(sparseArray, op.d);
                        break;
                    case 5:
                        b(sparseArray2, op.d);
                        break;
                    case 6:
                        a(sparseArray, op.d);
                        break;
                    case 7:
                        b(sparseArray2, op.d);
                        break;
                }
            }
        }
    }

    public final void a(SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2) {
        if (this.b.p.onHasView()) {
            for (Op op = this.c; op != null; op = op.a) {
                switch (op.c) {
                    case 1:
                        a(sparseArray, op.d);
                        break;
                    case 2:
                        if (op.i != null) {
                            for (int size = op.i.size() - 1; size >= 0; size--) {
                                b(sparseArray2, op.i.get(size));
                            }
                        }
                        a(sparseArray, op.d);
                        break;
                    case 3:
                        b(sparseArray2, op.d);
                        break;
                    case 4:
                        b(sparseArray2, op.d);
                        break;
                    case 5:
                        a(sparseArray, op.d);
                        break;
                    case 6:
                        b(sparseArray2, op.d);
                        break;
                    case 7:
                        a(sparseArray, op.d);
                        break;
                }
            }
        }
    }

    public final TransitionState a(boolean z, TransitionState transitionState, SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2) {
        int i2;
        int i3;
        int i4;
        int i5;
        if (FragmentManagerImpl.a) {
            new StringBuilder("popFromBackStack: ").append(this);
            a((String) "  ", new PrintWriter(new LogWriter("FragmentManager")));
        }
        if (a) {
            if (transitionState == null) {
                if (!(sparseArray.size() == 0 && sparseArray2.size() == 0)) {
                    transitionState = a(sparseArray, sparseArray2, true);
                }
            } else if (!z) {
                a(transitionState, this.v, this.u);
            }
        }
        a(-1);
        if (transitionState != null) {
            i2 = 0;
        } else {
            i2 = this.k;
        }
        if (transitionState != null) {
            i3 = 0;
        } else {
            i3 = this.j;
        }
        for (Op op = this.d; op != null; op = op.b) {
            if (transitionState != null) {
                i4 = 0;
            } else {
                i4 = op.g;
            }
            if (transitionState != null) {
                i5 = 0;
            } else {
                i5 = op.h;
            }
            switch (op.c) {
                case 1:
                    Fragment fragment = op.d;
                    fragment.mNextAnim = i5;
                    this.b.a(fragment, FragmentManagerImpl.b(i3), i2);
                    break;
                case 2:
                    Fragment fragment2 = op.d;
                    if (fragment2 != null) {
                        fragment2.mNextAnim = i5;
                        this.b.a(fragment2, FragmentManagerImpl.b(i3), i2);
                    }
                    if (op.i == null) {
                        break;
                    } else {
                        for (int i6 = 0; i6 < op.i.size(); i6++) {
                            Fragment fragment3 = op.i.get(i6);
                            fragment3.mNextAnim = i4;
                            this.b.a(fragment3, false);
                        }
                        break;
                    }
                case 3:
                    Fragment fragment4 = op.d;
                    fragment4.mNextAnim = i4;
                    this.b.a(fragment4, false);
                    break;
                case 4:
                    Fragment fragment5 = op.d;
                    fragment5.mNextAnim = i4;
                    this.b.c(fragment5, FragmentManagerImpl.b(i3), i2);
                    break;
                case 5:
                    Fragment fragment6 = op.d;
                    fragment6.mNextAnim = i5;
                    this.b.b(fragment6, FragmentManagerImpl.b(i3), i2);
                    break;
                case 6:
                    Fragment fragment7 = op.d;
                    fragment7.mNextAnim = i4;
                    this.b.e(fragment7, FragmentManagerImpl.b(i3), i2);
                    break;
                case 7:
                    Fragment fragment8 = op.d;
                    fragment8.mNextAnim = i4;
                    this.b.d(fragment8, FragmentManagerImpl.b(i3), i2);
                    break;
                default:
                    StringBuilder sb = new StringBuilder("Unknown cmd: ");
                    sb.append(op.c);
                    throw new IllegalArgumentException(sb.toString());
            }
        }
        if (z) {
            this.b.a(this.b.n, FragmentManagerImpl.b(i3), i2, true);
            transitionState = null;
        }
        if (this.p >= 0) {
            FragmentManagerImpl fragmentManagerImpl = this.b;
            int i7 = this.p;
            synchronized (fragmentManagerImpl) {
                fragmentManagerImpl.k.set(i7, null);
                if (fragmentManagerImpl.l == null) {
                    fragmentManagerImpl.l = new ArrayList<>();
                }
                boolean z2 = FragmentManagerImpl.a;
                fragmentManagerImpl.l.add(Integer.valueOf(i7));
            }
            this.p = -1;
        }
        return transitionState;
    }

    public final String getName() {
        return this.n;
    }

    public final boolean isEmpty() {
        return this.e == 0;
    }

    private TransitionState a(SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2, boolean z) {
        TransitionState transitionState = new TransitionState();
        transitionState.nonExistentView = new View(this.b.o.getContext());
        boolean z2 = false;
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            if (a(sparseArray.keyAt(i2), transitionState, z, sparseArray, sparseArray2)) {
                z2 = true;
            }
        }
        for (int i3 = 0; i3 < sparseArray2.size(); i3++) {
            int keyAt = sparseArray2.keyAt(i3);
            if (sparseArray.get(keyAt) == null && a(keyAt, transitionState, z, sparseArray, sparseArray2)) {
                z2 = true;
            }
        }
        if (!z2) {
            return null;
        }
        return transitionState;
    }

    private static Object a(Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.a(z ? fragment.getReenterTransition() : fragment.getEnterTransition());
    }

    private static Object b(Fragment fragment, boolean z) {
        if (fragment == null) {
            return null;
        }
        return FragmentTransitionCompat21.a(z ? fragment.getReturnTransition() : fragment.getExitTransition());
    }

    private static Object a(Fragment fragment, Fragment fragment2, boolean z) {
        if (fragment == null || fragment2 == null) {
            return null;
        }
        return FragmentTransitionCompat21.b(z ? fragment2.getSharedElementReturnTransition() : fragment.getSharedElementEnterTransition());
    }

    private static Object a(Object obj, Fragment fragment, ArrayList<View> arrayList, ArrayMap<String, View> arrayMap, View view) {
        return obj != null ? FragmentTransitionCompat21.a(obj, fragment.getView(), arrayList, arrayMap, view) : obj;
    }

    private ArrayMap<String, View> a(TransitionState transitionState, Fragment fragment, boolean z) {
        ArrayMap<String, View> arrayMap = new ArrayMap<>();
        if (this.u != null) {
            FragmentTransitionCompat21.a((Map<String, View>) arrayMap, fragment.getView());
            if (z) {
                arrayMap.retainAll(this.v);
            } else {
                arrayMap = a(this.u, this.v, arrayMap);
            }
        }
        if (z) {
            if (fragment.mEnterTransitionCallback != null) {
                fragment.mEnterTransitionCallback.onMapSharedElements(this.v, arrayMap);
            }
            a(transitionState, arrayMap, false);
        } else {
            if (fragment.mExitTransitionCallback != null) {
                fragment.mExitTransitionCallback.onMapSharedElements(this.v, arrayMap);
            }
            b(transitionState, arrayMap, false);
        }
        return arrayMap;
    }

    private boolean a(int i2, TransitionState transitionState, boolean z, SparseArray<Fragment> sparseArray, SparseArray<Fragment> sparseArray2) {
        ArrayList arrayList;
        Object obj;
        Object obj2;
        ArrayMap arrayMap;
        boolean z2;
        Object obj3;
        int i3 = i2;
        TransitionState transitionState2 = transitionState;
        boolean z3 = z;
        ViewGroup viewGroup = (ViewGroup) this.b.p.onFindViewById(i3);
        if (viewGroup == null) {
            return false;
        }
        final Fragment fragment = sparseArray2.get(i3);
        Fragment fragment2 = sparseArray.get(i3);
        Object a2 = a(fragment, z3);
        Object a3 = a(fragment, fragment2, z3);
        Object b2 = b(fragment2, z3);
        ArrayList arrayList2 = new ArrayList();
        Object obj4 = null;
        if (a3 != null) {
            ArrayMap<String, View> a4 = a(transitionState2, fragment2, z3);
            if (a4.isEmpty()) {
                arrayMap = null;
                arrayList = arrayList2;
                obj = b2;
                obj2 = a2;
            } else {
                SharedElementCallback sharedElementCallback = z3 ? fragment2.mEnterTransitionCallback : fragment.mEnterTransitionCallback;
                if (sharedElementCallback != null) {
                    sharedElementCallback.onSharedElementStart(new ArrayList(a4.keySet()), new ArrayList(a4.values()), null);
                }
                arrayList = arrayList2;
                obj = b2;
                obj2 = a2;
                a(transitionState2, viewGroup, a3, fragment, fragment2, z3, arrayList);
                obj4 = a3;
                arrayMap = a4;
            }
        } else {
            arrayList = arrayList2;
            obj = b2;
            obj2 = a2;
            arrayMap = null;
            obj4 = a3;
        }
        if (obj2 == null && obj4 == null && obj == null) {
            return false;
        }
        ArrayList arrayList3 = new ArrayList();
        Object a5 = a(obj, fragment2, arrayList3, arrayMap, transitionState2.nonExistentView);
        if (this.v != null && arrayMap != null) {
            View view = (View) arrayMap.get(this.v.get(0));
            if (view != null) {
                if (a5 != null) {
                    FragmentTransitionCompat21.a(a5, view);
                }
                if (obj4 != null) {
                    FragmentTransitionCompat21.a(obj4, view);
                }
            }
        }
        AnonymousClass1 r14 = new ViewRetriever() {
            public View getView() {
                return fragment.getView();
            }
        };
        ArrayList arrayList4 = new ArrayList();
        ArrayMap arrayMap2 = new ArrayMap();
        boolean z4 = fragment != null ? z3 ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap() : true;
        Object a6 = FragmentTransitionCompat21.a(obj2, a5, obj4, z4);
        if (a6 != null) {
            obj3 = a6;
            z2 = false;
            Object obj5 = obj2;
            ViewGroup viewGroup2 = viewGroup;
            FragmentTransitionCompat21.a(obj2, obj4, (View) viewGroup, (ViewRetriever) r14, transitionState2.nonExistentView, transitionState2.enteringEpicenterView, (Map<String, String>) transitionState2.nameOverrides, arrayList4, (Map<String, View>) arrayMap, (Map<String, View>) arrayMap2, arrayList);
            int i4 = i2;
            a((View) viewGroup2, transitionState2, i4, obj3);
            FragmentTransitionCompat21.a(obj3, transitionState2.nonExistentView, true);
            a(transitionState2, i4, obj3);
            FragmentTransitionCompat21.a(viewGroup2, obj3);
            FragmentTransitionCompat21.a((View) viewGroup2, transitionState2.nonExistentView, obj5, arrayList4, a5, arrayList3, obj4, arrayList, obj3, transitionState2.hiddenFragmentViews, (Map<String, View>) arrayMap2);
        } else {
            obj3 = a6;
            z2 = false;
        }
        if (obj3 != null) {
            return true;
        }
        return z2;
    }

    private void a(TransitionState transitionState, View view, Object obj, Fragment fragment, Fragment fragment2, boolean z, ArrayList<View> arrayList) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        final View view2 = view;
        final Object obj2 = obj;
        final ArrayList<View> arrayList2 = arrayList;
        final TransitionState transitionState2 = transitionState;
        final boolean z2 = z;
        final Fragment fragment3 = fragment;
        final Fragment fragment4 = fragment2;
        AnonymousClass2 r1 = new OnPreDrawListener() {
            public boolean onPreDraw() {
                view2.getViewTreeObserver().removeOnPreDrawListener(this);
                if (obj2 != null) {
                    FragmentTransitionCompat21.a(obj2, arrayList2);
                    arrayList2.clear();
                    ArrayMap a2 = BackStackRecord.a(BackStackRecord.this, transitionState2, z2, fragment3);
                    FragmentTransitionCompat21.a(obj2, transitionState2.nonExistentView, (Map<String, View>) a2, arrayList2);
                    BackStackRecord.a(BackStackRecord.this, a2, transitionState2);
                    BackStackRecord.a(fragment3, fragment4, z2, a2);
                }
                return true;
            }
        };
        viewTreeObserver.addOnPreDrawListener(r1);
    }

    private static ArrayMap<String, View> a(ArrayList<String> arrayList, ArrayList<String> arrayList2, ArrayMap<String, View> arrayMap) {
        if (arrayMap.isEmpty()) {
            return arrayMap;
        }
        ArrayMap<String, View> arrayMap2 = new ArrayMap<>();
        int size = arrayList.size();
        for (int i2 = 0; i2 < size; i2++) {
            View view = (View) arrayMap.get(arrayList.get(i2));
            if (view != null) {
                arrayMap2.put(arrayList2.get(i2), view);
            }
        }
        return arrayMap2;
    }

    private void a(View view, TransitionState transitionState, int i2, Object obj) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        final View view2 = view;
        final TransitionState transitionState2 = transitionState;
        final int i3 = i2;
        final Object obj2 = obj;
        AnonymousClass3 r1 = new OnPreDrawListener() {
            public boolean onPreDraw() {
                view2.getViewTreeObserver().removeOnPreDrawListener(this);
                BackStackRecord.this.a(transitionState2, i3, obj2);
                return true;
            }
        };
        viewTreeObserver.addOnPreDrawListener(r1);
    }

    /* access modifiers changed from: private */
    public void a(TransitionState transitionState, int i2, Object obj) {
        if (this.b.g != null) {
            for (int i3 = 0; i3 < this.b.g.size(); i3++) {
                Fragment fragment = this.b.g.get(i3);
                if (!(fragment.mView == null || fragment.mContainer == null || fragment.mContainerId != i2)) {
                    if (!fragment.mHidden) {
                        FragmentTransitionCompat21.a(obj, fragment.mView, false);
                        transitionState.hiddenFragmentViews.remove(fragment.mView);
                    } else if (!transitionState.hiddenFragmentViews.contains(fragment.mView)) {
                        FragmentTransitionCompat21.a(obj, fragment.mView, true);
                        transitionState.hiddenFragmentViews.add(fragment.mView);
                    }
                }
            }
        }
    }

    private static void a(ArrayMap<String, String> arrayMap, String str, String str2) {
        if (!(str == null || str2 == null)) {
            for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                if (str.equals(arrayMap.valueAt(i2))) {
                    arrayMap.setValueAt(i2, str2);
                    return;
                }
            }
            arrayMap.put(str, str2);
        }
    }

    private static void a(TransitionState transitionState, ArrayList<String> arrayList, ArrayList<String> arrayList2) {
        if (arrayList != null) {
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                a(transitionState.nameOverrides, arrayList.get(i2), arrayList2.get(i2));
            }
        }
    }

    private void a(TransitionState transitionState, ArrayMap<String, View> arrayMap, boolean z) {
        int size = this.v == null ? 0 : this.v.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str = this.u.get(i2);
            View view = (View) arrayMap.get(this.v.get(i2));
            if (view != null) {
                String a2 = FragmentTransitionCompat21.a(view);
                if (z) {
                    a(transitionState.nameOverrides, str, a2);
                } else {
                    a(transitionState.nameOverrides, a2, str);
                }
            }
        }
    }

    private static void b(TransitionState transitionState, ArrayMap<String, View> arrayMap, boolean z) {
        int size = arrayMap.size();
        for (int i2 = 0; i2 < size; i2++) {
            String str = (String) arrayMap.keyAt(i2);
            String a2 = FragmentTransitionCompat21.a((View) arrayMap.valueAt(i2));
            if (z) {
                a(transitionState.nameOverrides, str, a2);
            } else {
                a(transitionState.nameOverrides, a2, str);
            }
        }
    }

    static /* synthetic */ ArrayMap a(BackStackRecord backStackRecord, TransitionState transitionState, boolean z, Fragment fragment) {
        ArrayMap<String, View> arrayMap = new ArrayMap<>();
        View view = fragment.getView();
        if (!(view == null || backStackRecord.u == null)) {
            FragmentTransitionCompat21.a((Map<String, View>) arrayMap, view);
            if (z) {
                arrayMap = a(backStackRecord.u, backStackRecord.v, arrayMap);
            } else {
                arrayMap.retainAll(backStackRecord.v);
            }
        }
        if (z) {
            if (fragment.mExitTransitionCallback != null) {
                fragment.mExitTransitionCallback.onMapSharedElements(backStackRecord.v, arrayMap);
            }
            backStackRecord.a(transitionState, arrayMap, true);
        } else {
            if (fragment.mEnterTransitionCallback != null) {
                fragment.mEnterTransitionCallback.onMapSharedElements(backStackRecord.v, arrayMap);
            }
            b(transitionState, arrayMap, true);
        }
        return arrayMap;
    }

    static /* synthetic */ void a(BackStackRecord backStackRecord, ArrayMap arrayMap, TransitionState transitionState) {
        if (backStackRecord.v != null && !arrayMap.isEmpty()) {
            View view = (View) arrayMap.get(backStackRecord.v.get(0));
            if (view != null) {
                transitionState.enteringEpicenterView.epicenter = view;
            }
        }
    }

    static /* synthetic */ void a(Fragment fragment, Fragment fragment2, boolean z, ArrayMap arrayMap) {
        SharedElementCallback sharedElementCallback = z ? fragment2.mEnterTransitionCallback : fragment.mEnterTransitionCallback;
        if (sharedElementCallback != null) {
            sharedElementCallback.onSharedElementEnd(new ArrayList(arrayMap.keySet()), new ArrayList(arrayMap.values()), null);
        }
    }
}
