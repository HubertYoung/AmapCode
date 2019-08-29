package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.accessibility.AccessibilityEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

public class StaggeredGridLayoutManager extends LayoutManager {
    private static final boolean DEBUG = false;
    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    private static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    public static final String TAG = "StaggeredGridLayoutManager";
    public static final int VERTICAL = 1;
    private final AnchorInfo mAnchorInfo = new AnchorInfo(this, 0);
    private final Runnable mCheckForGapsRunnable;
    private int mFullSizeSpec;
    private int mGapStrategy = 2;
    private boolean mLaidOutInvalidFullSpan = false;
    private boolean mLastLayoutFromEnd;
    private boolean mLastLayoutRTL;
    private LayoutState mLayoutState;
    LazySpanLookup mLazySpanLookup = new LazySpanLookup();
    /* access modifiers changed from: private */
    public int mOrientation;
    private SavedState mPendingSavedState;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    OrientationHelper mPrimaryOrientation;
    private BitSet mRemainingSpans;
    /* access modifiers changed from: private */
    public boolean mReverseLayout = false;
    OrientationHelper mSecondaryOrientation;
    boolean mShouldReverseLayout = false;
    private int mSizePerSpan;
    private boolean mSmoothScrollbarEnabled;
    private int mSpanCount = -1;
    private Span[] mSpans;
    private final Rect mTmpRect = new Rect();

    class AnchorInfo {
        int a;
        int b;
        boolean c;
        boolean d;

        private AnchorInfo() {
        }

        /* synthetic */ AnchorInfo(StaggeredGridLayoutManager staggeredGridLayoutManager, byte b2) {
            this();
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.b = this.c ? StaggeredGridLayoutManager.this.mPrimaryOrientation.c() : StaggeredGridLayoutManager.this.mPrimaryOrientation.b();
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        Span a;
        public boolean b;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public final int a() {
            if (this.a == null) {
                return -1;
            }
            return this.a.e;
        }
    }

    static class LazySpanLookup {
        int[] a;
        List<FullSpanItem> b;

        static class FullSpanItem implements Parcelable {
            public static final Creator<FullSpanItem> CREATOR = new Creator<FullSpanItem>() {
                public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                    return new FullSpanItem[i];
                }

                public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }
            };
            int a;
            int b;
            int[] c;
            boolean d;

            public int describeContents() {
                return 0;
            }

            public FullSpanItem(Parcel parcel) {
                this.a = parcel.readInt();
                this.b = parcel.readInt();
                boolean z = true;
                this.d = parcel.readInt() != 1 ? false : z;
                int readInt = parcel.readInt();
                if (readInt > 0) {
                    this.c = new int[readInt];
                    parcel.readIntArray(this.c);
                }
            }

            public FullSpanItem() {
            }

            /* access modifiers changed from: 0000 */
            public final int a(int i) {
                if (this.c == null) {
                    return 0;
                }
                return this.c[i];
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b);
                parcel.writeInt(this.d ? 1 : 0);
                if (this.c == null || this.c.length <= 0) {
                    parcel.writeInt(0);
                    return;
                }
                parcel.writeInt(this.c.length);
                parcel.writeIntArray(this.c);
            }

            public String toString() {
                StringBuilder sb = new StringBuilder("FullSpanItem{mPosition=");
                sb.append(this.a);
                sb.append(", mGapDir=");
                sb.append(this.b);
                sb.append(", mHasUnwantedGapAfter=");
                sb.append(this.d);
                sb.append(", mGapPerSpan=");
                sb.append(Arrays.toString(this.c));
                sb.append('}');
                return sb.toString();
            }
        }

        LazySpanLookup() {
        }

        /* access modifiers changed from: 0000 */
        public final int a(int i) {
            if (this.b != null) {
                for (int size = this.b.size() - 1; size >= 0; size--) {
                    if (this.b.get(size).a >= i) {
                        this.b.remove(size);
                    }
                }
            }
            return b(i);
        }

        /* access modifiers changed from: 0000 */
        public final int b(int i) {
            if (this.a == null || i >= this.a.length) {
                return -1;
            }
            int f = f(i);
            if (f == -1) {
                int[] iArr = this.a;
                Arrays.fill(iArr, i, iArr.length, -1);
                return this.a.length;
            }
            int i2 = f + 1;
            Arrays.fill(this.a, i, i2, -1);
            return i2;
        }

        private int e(int i) {
            int length = this.a.length;
            while (length <= i) {
                length *= 2;
            }
            return length;
        }

        /* access modifiers changed from: 0000 */
        public final void c(int i) {
            if (this.a == null) {
                this.a = new int[(Math.max(i, 10) + 1)];
                Arrays.fill(this.a, -1);
                return;
            }
            if (i >= this.a.length) {
                int[] iArr = this.a;
                this.a = new int[e(i)];
                System.arraycopy(iArr, 0, this.a, 0, iArr.length);
                Arrays.fill(this.a, iArr.length, this.a.length, -1);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            if (this.a != null) {
                Arrays.fill(this.a, -1);
            }
            this.b = null;
        }

        /* access modifiers changed from: 0000 */
        public final void a(int i, int i2) {
            if (this.a != null && i < this.a.length) {
                int i3 = i + i2;
                c(i3);
                int[] iArr = this.a;
                int[] iArr2 = this.a;
                System.arraycopy(iArr, i3, iArr2, i, (iArr2.length - i) - i2);
                Arrays.fill(this.a, this.a.length - i2, this.a.length, -1);
                c(i, i2);
            }
        }

        private void c(int i, int i2) {
            if (this.b != null) {
                int i3 = i + i2;
                for (int size = this.b.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.b.get(size);
                    if (fullSpanItem.a >= i) {
                        if (fullSpanItem.a < i3) {
                            this.b.remove(size);
                        } else {
                            fullSpanItem.a -= i2;
                        }
                    }
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final void b(int i, int i2) {
            if (this.a != null && i < this.a.length) {
                int i3 = i + i2;
                c(i3);
                int[] iArr = this.a;
                System.arraycopy(iArr, i, iArr, i3, (this.a.length - i) - i2);
                Arrays.fill(this.a, i, i3, -1);
                d(i, i2);
            }
        }

        private void d(int i, int i2) {
            if (this.b != null) {
                for (int size = this.b.size() - 1; size >= 0; size--) {
                    FullSpanItem fullSpanItem = this.b.get(size);
                    if (fullSpanItem.a >= i) {
                        fullSpanItem.a += i2;
                    }
                }
            }
        }

        private int f(int i) {
            if (this.b == null) {
                return -1;
            }
            FullSpanItem d = d(i);
            if (d != null) {
                this.b.remove(d);
            }
            int size = this.b.size();
            int i2 = 0;
            while (true) {
                if (i2 >= size) {
                    i2 = -1;
                    break;
                } else if (this.b.get(i2).a >= i) {
                    break;
                } else {
                    i2++;
                }
            }
            if (i2 == -1) {
                return -1;
            }
            this.b.remove(i2);
            return this.b.get(i2).a;
        }

        public final void a(FullSpanItem fullSpanItem) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem fullSpanItem2 = this.b.get(i);
                if (fullSpanItem2.a == fullSpanItem.a) {
                    this.b.remove(i);
                }
                if (fullSpanItem2.a >= fullSpanItem.a) {
                    this.b.add(i, fullSpanItem);
                    return;
                }
            }
            this.b.add(fullSpanItem);
        }

        public final FullSpanItem d(int i) {
            if (this.b == null) {
                return null;
            }
            for (int size = this.b.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.b.get(size);
                if (fullSpanItem.a == i) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        public final FullSpanItem a(int i, int i2, int i3) {
            if (this.b == null) {
                return null;
            }
            int size = this.b.size();
            for (int i4 = 0; i4 < size; i4++) {
                FullSpanItem fullSpanItem = this.b.get(i4);
                if (fullSpanItem.a >= i2) {
                    return null;
                }
                if (fullSpanItem.a >= i && (i3 == 0 || fullSpanItem.b == i3 || fullSpanItem.d)) {
                    return fullSpanItem;
                }
            }
            return null;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final /* bridge */ /* synthetic */ Object[] newArray(int i) {
                return new SavedState[i];
            }

            public final /* synthetic */ Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }
        };
        int a;
        int b;
        int c;
        int[] d;
        int e;
        int[] f;
        List<FullSpanItem> g;
        boolean h;
        boolean i;
        boolean j;

        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt();
            if (this.c > 0) {
                this.d = new int[this.c];
                parcel.readIntArray(this.d);
            }
            this.e = parcel.readInt();
            if (this.e > 0) {
                this.f = new int[this.e];
                parcel.readIntArray(this.f);
            }
            boolean z = false;
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            this.j = parcel.readInt() == 1 ? true : z;
            this.g = parcel.readArrayList(FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.c = savedState.c;
            this.a = savedState.a;
            this.b = savedState.b;
            this.d = savedState.d;
            this.e = savedState.e;
            this.f = savedState.f;
            this.h = savedState.h;
            this.i = savedState.i;
            this.j = savedState.j;
            this.g = savedState.g;
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.d = null;
            this.c = 0;
            this.a = -1;
            this.b = -1;
        }

        public void writeToParcel(Parcel parcel, int i2) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            if (this.c > 0) {
                parcel.writeIntArray(this.d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }
    }

    class Span {
        /* access modifiers changed from: 0000 */
        public ArrayList<View> a;
        int b;
        int c;
        int d;
        final int e;

        /* synthetic */ Span(StaggeredGridLayoutManager staggeredGridLayoutManager, int i, byte b2) {
            this(i);
        }

        private Span(int i) {
            this.a = new ArrayList<>();
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
            this.d = 0;
            this.e = i;
        }

        /* access modifiers changed from: 0000 */
        public final int a(int i) {
            if (this.b != Integer.MIN_VALUE) {
                return this.b;
            }
            if (this.a.size() == 0) {
                return i;
            }
            f();
            return this.b;
        }

        private void f() {
            View view = this.a.get(0);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            this.b = StaggeredGridLayoutManager.this.mPrimaryOrientation.a(view);
            if (layoutParams.b) {
                FullSpanItem d2 = StaggeredGridLayoutManager.this.mLazySpanLookup.d(layoutParams.c.getLayoutPosition());
                if (d2 != null && d2.b == -1) {
                    this.b -= d2.a(this.e);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final int a() {
            if (this.b != Integer.MIN_VALUE) {
                return this.b;
            }
            f();
            return this.b;
        }

        /* access modifiers changed from: 0000 */
        public final int b(int i) {
            if (this.c != Integer.MIN_VALUE) {
                return this.c;
            }
            if (this.a.size() == 0) {
                return i;
            }
            g();
            return this.c;
        }

        private void g() {
            View view = this.a.get(this.a.size() - 1);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            this.c = StaggeredGridLayoutManager.this.mPrimaryOrientation.b(view);
            if (layoutParams.b) {
                FullSpanItem d2 = StaggeredGridLayoutManager.this.mLazySpanLookup.d(layoutParams.c.getLayoutPosition());
                if (d2 != null && d2.b == 1) {
                    this.c += d2.a(this.e);
                }
            }
        }

        /* access modifiers changed from: 0000 */
        public final int b() {
            if (this.c != Integer.MIN_VALUE) {
                return this.c;
            }
            g();
            return this.c;
        }

        /* access modifiers changed from: 0000 */
        public final void c() {
            this.a.clear();
            h();
            this.d = 0;
        }

        private void h() {
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public final void c(int i) {
            this.b = i;
            this.c = i;
        }

        /* access modifiers changed from: 0000 */
        public final void d() {
            int size = this.a.size();
            View remove = this.a.remove(size - 1);
            LayoutParams layoutParams = (LayoutParams) remove.getLayoutParams();
            layoutParams.a = null;
            if (layoutParams.c.isRemoved() || layoutParams.c.isUpdated()) {
                this.d -= StaggeredGridLayoutManager.this.mPrimaryOrientation.c(remove);
            }
            if (size == 1) {
                this.b = Integer.MIN_VALUE;
            }
            this.c = Integer.MIN_VALUE;
        }

        /* access modifiers changed from: 0000 */
        public final void e() {
            View remove = this.a.remove(0);
            LayoutParams layoutParams = (LayoutParams) remove.getLayoutParams();
            layoutParams.a = null;
            if (this.a.size() == 0) {
                this.c = Integer.MIN_VALUE;
            }
            if (layoutParams.c.isRemoved() || layoutParams.c.isUpdated()) {
                this.d -= StaggeredGridLayoutManager.this.mPrimaryOrientation.c(remove);
            }
            this.b = Integer.MIN_VALUE;
        }

        static LayoutParams c(View view) {
            return (LayoutParams) view.getLayoutParams();
        }

        /* access modifiers changed from: 0000 */
        public final void d(int i) {
            if (this.b != Integer.MIN_VALUE) {
                this.b += i;
            }
            if (this.c != Integer.MIN_VALUE) {
                this.c += i;
            }
        }

        /* access modifiers changed from: 0000 */
        public final int a(int i, int i2, boolean z) {
            int b2 = StaggeredGridLayoutManager.this.mPrimaryOrientation.b();
            int c2 = StaggeredGridLayoutManager.this.mPrimaryOrientation.c();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view = this.a.get(i);
                int a2 = StaggeredGridLayoutManager.this.mPrimaryOrientation.a(view);
                int b3 = StaggeredGridLayoutManager.this.mPrimaryOrientation.b(view);
                if (a2 < c2 && b3 > b2) {
                    if (!z) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                    if (a2 >= b2 && b3 <= c2) {
                        return StaggeredGridLayoutManager.this.getPosition(view);
                    }
                }
                i += i3;
            }
            return -1;
        }

        public final View a(int i, int i2) {
            View view = null;
            if (i2 != -1) {
                int size = this.a.size() - 1;
                while (size >= 0) {
                    View view2 = this.a.get(size);
                    if (view2.isFocusable()) {
                        if ((StaggeredGridLayoutManager.this.getPosition(view2) > i) != (!StaggeredGridLayoutManager.this.mReverseLayout)) {
                            break;
                        }
                        size--;
                        view = view2;
                    } else {
                        break;
                    }
                }
            } else {
                int size2 = this.a.size();
                int i3 = 0;
                while (i3 < size2) {
                    View view3 = this.a.get(i3);
                    if (view3.isFocusable()) {
                        if ((StaggeredGridLayoutManager.this.getPosition(view3) > i) != StaggeredGridLayoutManager.this.mReverseLayout) {
                            break;
                        }
                        i3++;
                        view = view3;
                    } else {
                        break;
                    }
                }
            }
            return view;
        }

        /* access modifiers changed from: 0000 */
        public final void a(View view) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.a = this;
            this.a.add(0, view);
            this.b = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.c = Integer.MIN_VALUE;
            }
            if (layoutParams.c.isRemoved() || layoutParams.c.isUpdated()) {
                this.d += StaggeredGridLayoutManager.this.mPrimaryOrientation.c(view);
            }
        }

        /* access modifiers changed from: 0000 */
        public final void b(View view) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.a = this;
            this.a.add(view);
            this.c = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.b = Integer.MIN_VALUE;
            }
            if (layoutParams.c.isRemoved() || layoutParams.c.isUpdated()) {
                this.d += StaggeredGridLayoutManager.this.mPrimaryOrientation.c(view);
            }
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        boolean z = true;
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new Runnable() {
            public void run() {
                StaggeredGridLayoutManager.this.checkForGaps();
            }
        };
        Properties properties = getProperties(context, attributeSet, i, i2);
        setOrientation(properties.a);
        setSpanCount(properties.b);
        setReverseLayout(properties.c);
        setAutoMeasureEnabled(this.mGapStrategy == 0 ? false : z);
    }

    public StaggeredGridLayoutManager(int i, int i2) {
        boolean z = true;
        this.mSmoothScrollbarEnabled = true;
        this.mCheckForGapsRunnable = new Runnable() {
            public void run() {
                StaggeredGridLayoutManager.this.checkForGaps();
            }
        };
        this.mOrientation = i2;
        setSpanCount(i);
        setAutoMeasureEnabled(this.mGapStrategy == 0 ? false : z);
    }

    /* access modifiers changed from: private */
    public boolean checkForGaps() {
        int i;
        int i2;
        if (getChildCount() == 0 || this.mGapStrategy == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (this.mShouldReverseLayout) {
            i2 = getLastChildPosition();
            i = getFirstChildPosition();
        } else {
            i2 = getFirstChildPosition();
            i = getLastChildPosition();
        }
        if (i2 == 0 && hasGapsToFix() != null) {
            this.mLazySpanLookup.a();
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        } else if (!this.mLaidOutInvalidFullSpan) {
            return false;
        } else {
            int i3 = this.mShouldReverseLayout ? -1 : 1;
            int i4 = i + 1;
            FullSpanItem a = this.mLazySpanLookup.a(i2, i4, i3);
            if (a == null) {
                this.mLaidOutInvalidFullSpan = false;
                this.mLazySpanLookup.a(i4);
                return false;
            }
            FullSpanItem a2 = this.mLazySpanLookup.a(i2, a.a, i3 * -1);
            if (a2 == null) {
                this.mLazySpanLookup.a(a.a);
            } else {
                this.mLazySpanLookup.a(a2.a + 1);
            }
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
    }

    public void onScrollStateChanged(int i) {
        if (i == 0) {
            checkForGaps();
        }
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        removeCallbacks(this.mCheckForGapsRunnable);
        for (int i = 0; i < this.mSpanCount; i++) {
            this.mSpans[i].c();
        }
    }

    /* access modifiers changed from: 0000 */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0073, code lost:
        if (r10 == r11) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0085, code lost:
        if (r10 == r11) goto L_0x0087;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0089, code lost:
        r10 = false;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View hasGapsToFix() {
        /*
            r12 = this;
            int r0 = r12.getChildCount()
            r1 = 1
            int r0 = r0 - r1
            java.util.BitSet r2 = new java.util.BitSet
            int r3 = r12.mSpanCount
            r2.<init>(r3)
            int r3 = r12.mSpanCount
            r4 = 0
            r2.set(r4, r3, r1)
            int r3 = r12.mOrientation
            r5 = -1
            if (r3 != r1) goto L_0x0020
            boolean r3 = r12.isLayoutRTL()
            if (r3 == 0) goto L_0x0020
            r3 = 1
            goto L_0x0021
        L_0x0020:
            r3 = -1
        L_0x0021:
            boolean r6 = r12.mShouldReverseLayout
            if (r6 == 0) goto L_0x0027
            r6 = -1
            goto L_0x002b
        L_0x0027:
            int r0 = r0 + 1
            r6 = r0
            r0 = 0
        L_0x002b:
            if (r0 >= r6) goto L_0x002e
            r5 = 1
        L_0x002e:
            if (r0 == r6) goto L_0x00aa
            android.view.View r7 = r12.getChildAt(r0)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams r8 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r8
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r8.a
            int r9 = r9.e
            boolean r9 = r2.get(r9)
            if (r9 == 0) goto L_0x0054
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r8.a
            boolean r9 = r12.checkSpanForGap(r9)
            if (r9 == 0) goto L_0x004d
            return r7
        L_0x004d:
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r8.a
            int r9 = r9.e
            r2.clear(r9)
        L_0x0054:
            boolean r9 = r8.b
            if (r9 != 0) goto L_0x00a8
            int r9 = r0 + r5
            if (r9 == r6) goto L_0x00a8
            android.view.View r9 = r12.getChildAt(r9)
            boolean r10 = r12.mShouldReverseLayout
            if (r10 == 0) goto L_0x0076
            android.support.v7.widget.OrientationHelper r10 = r12.mPrimaryOrientation
            int r10 = r10.b(r7)
            android.support.v7.widget.OrientationHelper r11 = r12.mPrimaryOrientation
            int r11 = r11.b(r9)
            if (r10 >= r11) goto L_0x0073
            return r7
        L_0x0073:
            if (r10 != r11) goto L_0x0089
            goto L_0x0087
        L_0x0076:
            android.support.v7.widget.OrientationHelper r10 = r12.mPrimaryOrientation
            int r10 = r10.a(r7)
            android.support.v7.widget.OrientationHelper r11 = r12.mPrimaryOrientation
            int r11 = r11.a(r9)
            if (r10 <= r11) goto L_0x0085
            return r7
        L_0x0085:
            if (r10 != r11) goto L_0x0089
        L_0x0087:
            r10 = 1
            goto L_0x008a
        L_0x0089:
            r10 = 0
        L_0x008a:
            if (r10 == 0) goto L_0x00a8
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams r9 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r9
            android.support.v7.widget.StaggeredGridLayoutManager$Span r8 = r8.a
            int r8 = r8.e
            android.support.v7.widget.StaggeredGridLayoutManager$Span r9 = r9.a
            int r9 = r9.e
            int r8 = r8 - r9
            if (r8 >= 0) goto L_0x009f
            r8 = 1
            goto L_0x00a0
        L_0x009f:
            r8 = 0
        L_0x00a0:
            if (r3 >= 0) goto L_0x00a4
            r9 = 1
            goto L_0x00a5
        L_0x00a4:
            r9 = 0
        L_0x00a5:
            if (r8 == r9) goto L_0x00a8
            return r7
        L_0x00a8:
            int r0 = r0 + r5
            goto L_0x002e
        L_0x00aa:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.hasGapsToFix():android.view.View");
    }

    private boolean checkSpanForGap(Span span) {
        return this.mShouldReverseLayout ? span.b() < this.mPrimaryOrientation.c() && !Span.c((View) span.a.get(span.a.size() - 1)).b : span.a() > this.mPrimaryOrientation.b() && !Span.c((View) span.a.get(0)).b;
        return false;
    }

    public void setSpanCount(int i) {
        assertNotInLayoutOrScroll(null);
        if (i != this.mSpanCount) {
            invalidateSpanAssignments();
            this.mSpanCount = i;
            this.mRemainingSpans = new BitSet(this.mSpanCount);
            this.mSpans = new Span[this.mSpanCount];
            for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                this.mSpans[i2] = new Span(this, i2, 0);
            }
            requestLayout();
        }
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll(null);
            if (i != this.mOrientation) {
                this.mOrientation = i;
                if (!(this.mPrimaryOrientation == null || this.mSecondaryOrientation == null)) {
                    OrientationHelper orientationHelper = this.mPrimaryOrientation;
                    this.mPrimaryOrientation = this.mSecondaryOrientation;
                    this.mSecondaryOrientation = orientationHelper;
                }
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation.");
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.h == z)) {
            this.mPendingSavedState.h = z;
        }
        this.mReverseLayout = z;
        requestLayout();
    }

    public int getGapStrategy() {
        return this.mGapStrategy;
    }

    public void setGapStrategy(int i) {
        assertNotInLayoutOrScroll(null);
        if (i != this.mGapStrategy) {
            if (i == 0 || i == 2) {
                this.mGapStrategy = i;
                setAutoMeasureEnabled(this.mGapStrategy != 0);
                requestLayout();
                return;
            }
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
    }

    public void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public int getSpanCount() {
        return this.mSpanCount;
    }

    public void invalidateSpanAssignments() {
        this.mLazySpanLookup.a();
        requestLayout();
    }

    private void ensureOrientationHelper() {
        if (this.mPrimaryOrientation == null) {
            this.mPrimaryOrientation = OrientationHelper.a(this, this.mOrientation);
            this.mSecondaryOrientation = OrientationHelper.a(this, 1 - this.mOrientation);
            this.mLayoutState = new LayoutState();
        }
    }

    private void resolveShouldLayoutReverse() {
        boolean z = true;
        if (this.mOrientation == 1 || !isLayoutRTL()) {
            z = this.mReverseLayout;
        } else if (this.mReverseLayout) {
            z = false;
        }
        this.mShouldReverseLayout = z;
    }

    /* access modifiers changed from: 0000 */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            i4 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            i3 = chooseSize(i, (this.mSizePerSpan * this.mSpanCount) + paddingLeft, getMinimumWidth());
        } else {
            i3 = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            i4 = chooseSize(i2, (this.mSizePerSpan * this.mSpanCount) + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        onLayoutChildren(recycler, state, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:79:0x0153, code lost:
        if (checkForGaps() != false) goto L_0x0157;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void onLayoutChildren(android.support.v7.widget.RecyclerView.Recycler r12, android.support.v7.widget.RecyclerView.State r13, boolean r14) {
        /*
            r11 = this;
            r0 = 0
        L_0x0001:
            r11.ensureOrientationHelper()
            android.support.v7.widget.StaggeredGridLayoutManager$AnchorInfo r1 = r11.mAnchorInfo
            r2 = -1
            r1.a = r2
            r3 = -2147483648(0xffffffff80000000, float:-0.0)
            r1.b = r3
            r1.c = r0
            r1.d = r0
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r4 = r11.mPendingSavedState
            if (r4 != 0) goto L_0x0019
            int r4 = r11.mPendingScrollPosition
            if (r4 == r2) goto L_0x0023
        L_0x0019:
            int r4 = r13.a()
            if (r4 != 0) goto L_0x0023
            r11.removeAndRecycleAllViews(r12)
            return
        L_0x0023:
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r4 = r11.mPendingSavedState
            if (r4 == 0) goto L_0x002b
            r11.applyPendingSavedState(r1)
            goto L_0x0032
        L_0x002b:
            r11.resolveShouldLayoutReverse()
            boolean r4 = r11.mShouldReverseLayout
            r1.c = r4
        L_0x0032:
            r11.updateAnchorInfoForLayout(r13, r1)
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r4 = r11.mPendingSavedState
            r5 = 1
            if (r4 != 0) goto L_0x004f
            boolean r4 = r1.c
            boolean r6 = r11.mLastLayoutFromEnd
            if (r4 != r6) goto L_0x0048
            boolean r4 = r11.isLayoutRTL()
            boolean r6 = r11.mLastLayoutRTL
            if (r4 == r6) goto L_0x004f
        L_0x0048:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r11.mLazySpanLookup
            r4.a()
            r1.d = r5
        L_0x004f:
            int r4 = r11.getChildCount()
            if (r4 <= 0) goto L_0x00bf
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r4 = r11.mPendingSavedState
            if (r4 == 0) goto L_0x005f
            android.support.v7.widget.StaggeredGridLayoutManager$SavedState r4 = r11.mPendingSavedState
            int r4 = r4.c
            if (r4 > 0) goto L_0x00bf
        L_0x005f:
            boolean r4 = r1.d
            if (r4 == 0) goto L_0x007f
            r4 = 0
        L_0x0064:
            int r6 = r11.mSpanCount
            if (r4 >= r6) goto L_0x00bf
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            r6 = r6[r4]
            r6.c()
            int r6 = r1.b
            if (r6 == r3) goto L_0x007c
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            r6 = r6[r4]
            int r7 = r1.b
            r6.c(r7)
        L_0x007c:
            int r4 = r4 + 1
            goto L_0x0064
        L_0x007f:
            r4 = 0
        L_0x0080:
            int r6 = r11.mSpanCount
            if (r4 >= r6) goto L_0x00bf
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r6 = r11.mSpans
            r6 = r6[r4]
            boolean r7 = r11.mShouldReverseLayout
            int r8 = r1.b
            if (r7 == 0) goto L_0x0093
            int r9 = r6.b(r3)
            goto L_0x0097
        L_0x0093:
            int r9 = r6.a(r3)
        L_0x0097:
            r6.c()
            if (r9 == r3) goto L_0x00bc
            if (r7 == 0) goto L_0x00a8
            android.support.v7.widget.StaggeredGridLayoutManager r10 = android.support.v7.widget.StaggeredGridLayoutManager.this
            android.support.v7.widget.OrientationHelper r10 = r10.mPrimaryOrientation
            int r10 = r10.c()
            if (r9 < r10) goto L_0x00bc
        L_0x00a8:
            if (r7 != 0) goto L_0x00b5
            android.support.v7.widget.StaggeredGridLayoutManager r7 = android.support.v7.widget.StaggeredGridLayoutManager.this
            android.support.v7.widget.OrientationHelper r7 = r7.mPrimaryOrientation
            int r7 = r7.b()
            if (r9 <= r7) goto L_0x00b5
            goto L_0x00bc
        L_0x00b5:
            if (r8 == r3) goto L_0x00b8
            int r9 = r9 + r8
        L_0x00b8:
            r6.c = r9
            r6.b = r9
        L_0x00bc:
            int r4 = r4 + 1
            goto L_0x0080
        L_0x00bf:
            r11.detachAndScrapAttachedViews(r12)
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            r4.a = r0
            r11.mLaidOutInvalidFullSpan = r0
            android.support.v7.widget.OrientationHelper r4 = r11.mSecondaryOrientation
            int r4 = r4.e()
            r11.updateMeasureSpecs(r4)
            int r4 = r1.a
            r11.updateLayoutState(r4, r13)
            boolean r4 = r1.c
            if (r4 == 0) goto L_0x00f6
            r11.setLayoutStateDirection(r2)
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            r11.fill(r12, r4, r13)
            r11.setLayoutStateDirection(r5)
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            int r6 = r1.a
            android.support.v7.widget.LayoutState r7 = r11.mLayoutState
            int r7 = r7.d
            int r6 = r6 + r7
            r4.c = r6
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            r11.fill(r12, r4, r13)
            goto L_0x0111
        L_0x00f6:
            r11.setLayoutStateDirection(r5)
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            r11.fill(r12, r4, r13)
            r11.setLayoutStateDirection(r2)
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            int r6 = r1.a
            android.support.v7.widget.LayoutState r7 = r11.mLayoutState
            int r7 = r7.d
            int r6 = r6 + r7
            r4.c = r6
            android.support.v7.widget.LayoutState r4 = r11.mLayoutState
            r11.fill(r12, r4, r13)
        L_0x0111:
            r11.repositionToWrapContentIfNecessary()
            int r4 = r11.getChildCount()
            if (r4 <= 0) goto L_0x012b
            boolean r4 = r11.mShouldReverseLayout
            if (r4 == 0) goto L_0x0125
            r11.fixEndGap(r12, r13, r5)
            r11.fixStartGap(r12, r13, r0)
            goto L_0x012b
        L_0x0125:
            r11.fixStartGap(r12, r13, r5)
            r11.fixEndGap(r12, r13, r0)
        L_0x012b:
            if (r14 == 0) goto L_0x015c
            boolean r14 = r13.c
            if (r14 != 0) goto L_0x015c
            int r14 = r11.mGapStrategy
            if (r14 == 0) goto L_0x0147
            int r14 = r11.getChildCount()
            if (r14 <= 0) goto L_0x0147
            boolean r14 = r11.mLaidOutInvalidFullSpan
            if (r14 != 0) goto L_0x0145
            android.view.View r14 = r11.hasGapsToFix()
            if (r14 == 0) goto L_0x0147
        L_0x0145:
            r14 = 1
            goto L_0x0148
        L_0x0147:
            r14 = 0
        L_0x0148:
            if (r14 == 0) goto L_0x0156
            java.lang.Runnable r14 = r11.mCheckForGapsRunnable
            r11.removeCallbacks(r14)
            boolean r14 = r11.checkForGaps()
            if (r14 == 0) goto L_0x0156
            goto L_0x0157
        L_0x0156:
            r5 = 0
        L_0x0157:
            r11.mPendingScrollPosition = r2
            r11.mPendingScrollPositionOffset = r3
            goto L_0x015d
        L_0x015c:
            r5 = 0
        L_0x015d:
            boolean r14 = r1.c
            r11.mLastLayoutFromEnd = r14
            boolean r14 = r11.isLayoutRTL()
            r11.mLastLayoutRTL = r14
            r14 = 0
            r11.mPendingSavedState = r14
            if (r5 == 0) goto L_0x016f
            r14 = 0
            goto L_0x0001
        L_0x016f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.onLayoutChildren(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, boolean):void");
    }

    private void repositionToWrapContentIfNecessary() {
        if (this.mSecondaryOrientation.g() != 1073741824) {
            int childCount = getChildCount();
            float f = 0.0f;
            for (int i = 0; i < childCount; i++) {
                View childAt = getChildAt(i);
                float c = (float) this.mSecondaryOrientation.c(childAt);
                if (c >= f) {
                    if (((LayoutParams) childAt.getLayoutParams()).b) {
                        c = (c * 1.0f) / ((float) this.mSpanCount);
                    }
                    f = Math.max(f, c);
                }
            }
            int i2 = this.mSizePerSpan;
            int round = Math.round(f * ((float) this.mSpanCount));
            if (this.mSecondaryOrientation.g() == Integer.MIN_VALUE) {
                round = Math.min(round, this.mSecondaryOrientation.e());
            }
            updateMeasureSpecs(round);
            if (this.mSizePerSpan != i2) {
                for (int i3 = 0; i3 < childCount; i3++) {
                    View childAt2 = getChildAt(i3);
                    LayoutParams layoutParams = (LayoutParams) childAt2.getLayoutParams();
                    if (!layoutParams.b) {
                        if (!isLayoutRTL() || this.mOrientation != 1) {
                            int i4 = layoutParams.a.e * this.mSizePerSpan;
                            int i5 = layoutParams.a.e * i2;
                            if (this.mOrientation == 1) {
                                childAt2.offsetLeftAndRight(i4 - i5);
                            } else {
                                childAt2.offsetTopAndBottom(i4 - i5);
                            }
                        } else {
                            childAt2.offsetLeftAndRight(((-((this.mSpanCount - 1) - layoutParams.a.e)) * this.mSizePerSpan) - ((-((this.mSpanCount - 1) - layoutParams.a.e)) * i2));
                        }
                    }
                }
            }
        }
    }

    private void applyPendingSavedState(AnchorInfo anchorInfo) {
        if (this.mPendingSavedState.c > 0) {
            if (this.mPendingSavedState.c == this.mSpanCount) {
                for (int i = 0; i < this.mSpanCount; i++) {
                    this.mSpans[i].c();
                    int i2 = this.mPendingSavedState.d[i];
                    if (i2 != Integer.MIN_VALUE) {
                        if (this.mPendingSavedState.i) {
                            i2 += this.mPrimaryOrientation.c();
                        } else {
                            i2 += this.mPrimaryOrientation.b();
                        }
                    }
                    this.mSpans[i].c(i2);
                }
            } else {
                SavedState savedState = this.mPendingSavedState;
                savedState.d = null;
                savedState.c = 0;
                savedState.e = 0;
                savedState.f = null;
                savedState.g = null;
                this.mPendingSavedState.a = this.mPendingSavedState.b;
            }
        }
        this.mLastLayoutRTL = this.mPendingSavedState.j;
        setReverseLayout(this.mPendingSavedState.h);
        resolveShouldLayoutReverse();
        if (this.mPendingSavedState.a != -1) {
            this.mPendingScrollPosition = this.mPendingSavedState.a;
            anchorInfo.c = this.mPendingSavedState.i;
        } else {
            anchorInfo.c = this.mShouldReverseLayout;
        }
        if (this.mPendingSavedState.e > 1) {
            this.mLazySpanLookup.a = this.mPendingSavedState.f;
            this.mLazySpanLookup.b = this.mPendingSavedState.g;
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateAnchorInfoForLayout(State state, AnchorInfo anchorInfo) {
        if (!updateAnchorFromPendingData(state, anchorInfo) && !updateAnchorFromChildren(state, anchorInfo)) {
            anchorInfo.a();
            anchorInfo.a = 0;
        }
    }

    private boolean updateAnchorFromChildren(State state, AnchorInfo anchorInfo) {
        anchorInfo.a = this.mLastLayoutFromEnd ? findLastReferenceChildPosition(state.a()) : findFirstReferenceChildPosition(state.a());
        anchorInfo.b = Integer.MIN_VALUE;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public void updateMeasureSpecs(int i) {
        this.mSizePerSpan = i / this.mSpanCount;
        this.mFullSizeSpec = MeasureSpec.makeMeasureSpec(i, this.mSecondaryOrientation.g());
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null;
    }

    public int[] findFirstVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            StringBuilder sb = new StringBuilder("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.mSpanCount);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            Span span = this.mSpans[i];
            iArr[i] = StaggeredGridLayoutManager.this.mReverseLayout ? span.a(span.a.size() - 1, -1, false) : span.a(0, span.a.size(), false);
        }
        return iArr;
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            StringBuilder sb = new StringBuilder("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.mSpanCount);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            Span span = this.mSpans[i];
            iArr[i] = StaggeredGridLayoutManager.this.mReverseLayout ? span.a(span.a.size() - 1, -1, true) : span.a(0, span.a.size(), true);
        }
        return iArr;
    }

    public int[] findLastVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            StringBuilder sb = new StringBuilder("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.mSpanCount);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            Span span = this.mSpans[i];
            iArr[i] = StaggeredGridLayoutManager.this.mReverseLayout ? span.a(0, span.a.size(), false) : span.a(span.a.size() - 1, -1, false);
        }
        return iArr;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.mSpanCount];
        } else if (iArr.length < this.mSpanCount) {
            StringBuilder sb = new StringBuilder("Provided int[]'s size must be more than or equal to span count. Expected:");
            sb.append(this.mSpanCount);
            sb.append(", array size:");
            sb.append(iArr.length);
            throw new IllegalArgumentException(sb.toString());
        }
        for (int i = 0; i < this.mSpanCount; i++) {
            Span span = this.mSpans[i];
            iArr[i] = StaggeredGridLayoutManager.this.mReverseLayout ? span.a(0, span.a.size(), true) : span.a(span.a.size() - 1, -1, true);
        }
        return iArr;
    }

    public int computeHorizontalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    private int computeScrollOffset(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureOrientationHelper();
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        View findFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled, true);
        return ScrollbarHelper.a(state, orientationHelper, findFirstVisibleItemClosestToStart, findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    public int computeVerticalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    public int computeHorizontalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    private int computeScrollExtent(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureOrientationHelper();
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        View findFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled, true);
        return ScrollbarHelper.a(state, orientationHelper, findFirstVisibleItemClosestToStart, findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    public int computeHorizontalScrollRange(State state) {
        return computeScrollRange(state);
    }

    private int computeScrollRange(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureOrientationHelper();
        OrientationHelper orientationHelper = this.mPrimaryOrientation;
        View findFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(!this.mSmoothScrollbarEnabled, true);
        return ScrollbarHelper.b(state, orientationHelper, findFirstVisibleItemClosestToStart, findFirstVisibleItemClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    public int computeVerticalScrollRange(State state) {
        return computeScrollRange(state);
    }

    private void measureChildWithDecorationsAndMargin(View view, LayoutParams layoutParams, boolean z) {
        if (layoutParams.b) {
            if (this.mOrientation == 1) {
                measureChildWithDecorationsAndMargin(view, this.mFullSizeSpec, getChildMeasureSpec(getHeight(), getHeightMode(), 0, layoutParams.height, true), z);
            } else {
                measureChildWithDecorationsAndMargin(view, getChildMeasureSpec(getWidth(), getWidthMode(), 0, layoutParams.width, true), this.mFullSizeSpec, z);
            }
        } else if (this.mOrientation == 1) {
            measureChildWithDecorationsAndMargin(view, getChildMeasureSpec(this.mSizePerSpan, getWidthMode(), 0, layoutParams.width, false), getChildMeasureSpec(getHeight(), getHeightMode(), 0, layoutParams.height, true), z);
        } else {
            measureChildWithDecorationsAndMargin(view, getChildMeasureSpec(getWidth(), getWidthMode(), 0, layoutParams.width, true), getChildMeasureSpec(this.mSizePerSpan, getHeightMode(), 0, layoutParams.height, false), z);
        }
    }

    private void measureChildWithDecorationsAndMargin(View view, int i, int i2, boolean z) {
        calculateItemDecorationsForChild(view, this.mTmpRect);
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        int updateSpecWithExtra = updateSpecWithExtra(i, layoutParams.leftMargin + this.mTmpRect.left, layoutParams.rightMargin + this.mTmpRect.right);
        int updateSpecWithExtra2 = updateSpecWithExtra(i2, layoutParams.topMargin + this.mTmpRect.top, layoutParams.bottomMargin + this.mTmpRect.bottom);
        if (z ? shouldReMeasureChild(view, updateSpecWithExtra, updateSpecWithExtra2, layoutParams) : shouldMeasureChild(view, updateSpecWithExtra, updateSpecWithExtra2, layoutParams)) {
            view.measure(updateSpecWithExtra, updateSpecWithExtra2);
        }
    }

    private int updateSpecWithExtra(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize(i) - i2) - i3), mode);
        }
        return i;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState) parcelable;
            requestLayout();
        }
    }

    public Parcelable onSaveInstanceState() {
        int i;
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        savedState.h = this.mReverseLayout;
        savedState.i = this.mLastLayoutFromEnd;
        savedState.j = this.mLastLayoutRTL;
        if (this.mLazySpanLookup == null || this.mLazySpanLookup.a == null) {
            savedState.e = 0;
        } else {
            savedState.f = this.mLazySpanLookup.a;
            savedState.e = savedState.f.length;
            savedState.g = this.mLazySpanLookup.b;
        }
        if (getChildCount() > 0) {
            ensureOrientationHelper();
            savedState.a = this.mLastLayoutFromEnd ? getLastChildPosition() : getFirstChildPosition();
            savedState.b = findFirstVisibleItemPositionInt();
            savedState.c = this.mSpanCount;
            savedState.d = new int[this.mSpanCount];
            for (int i2 = 0; i2 < this.mSpanCount; i2++) {
                if (this.mLastLayoutFromEnd) {
                    i = this.mSpans[i2].b(Integer.MIN_VALUE);
                    if (i != Integer.MIN_VALUE) {
                        i -= this.mPrimaryOrientation.c();
                    }
                } else {
                    i = this.mSpans[i2].a(Integer.MIN_VALUE);
                    if (i != Integer.MIN_VALUE) {
                        i -= this.mPrimaryOrientation.b();
                    }
                }
                savedState.d[i2] = i;
            }
        } else {
            savedState.a = -1;
            savedState.b = -1;
            savedState.c = 0;
        }
        return savedState;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        int i;
        int i2;
        int i3;
        int i4;
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int i5 = 1;
        if (this.mOrientation == 0) {
            int a = layoutParams2.a();
            if (layoutParams2.b) {
                i5 = this.mSpanCount;
            }
            i4 = a;
            i3 = i5;
            i2 = -1;
            i = -1;
        } else {
            int a2 = layoutParams2.a();
            if (layoutParams2.b) {
                i2 = a2;
                i = this.mSpanCount;
                i4 = -1;
                i3 = -1;
            } else {
                i2 = a2;
                i4 = -1;
                i3 = -1;
                i = 1;
            }
        }
        accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(i4, i3, i2, i, layoutParams2.b, false));
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat asRecord = AccessibilityEventCompat.asRecord(accessibilityEvent);
            View findFirstVisibleItemClosestToStart = findFirstVisibleItemClosestToStart(false, true);
            View findFirstVisibleItemClosestToEnd = findFirstVisibleItemClosestToEnd(false, true);
            if (findFirstVisibleItemClosestToStart != null && findFirstVisibleItemClosestToEnd != null) {
                int position = getPosition(findFirstVisibleItemClosestToStart);
                int position2 = getPosition(findFirstVisibleItemClosestToEnd);
                if (position < position2) {
                    asRecord.setFromIndex(position);
                    asRecord.setToIndex(position2);
                    return;
                }
                asRecord.setFromIndex(position2);
                asRecord.setToIndex(position);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int findFirstVisibleItemPositionInt() {
        View findFirstVisibleItemClosestToEnd = this.mShouldReverseLayout ? findFirstVisibleItemClosestToEnd(true, true) : findFirstVisibleItemClosestToStart(true, true);
        if (findFirstVisibleItemClosestToEnd == null) {
            return -1;
        }
        return getPosition(findFirstVisibleItemClosestToEnd);
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        return super.getRowCountForAccessibility(recycler, state);
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        return super.getColumnCountForAccessibility(recycler, state);
    }

    /* access modifiers changed from: 0000 */
    public View findFirstVisibleItemClosestToStart(boolean z, boolean z2) {
        ensureOrientationHelper();
        int b = this.mPrimaryOrientation.b();
        int c = this.mPrimaryOrientation.c();
        int childCount = getChildCount();
        View view = null;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int a = this.mPrimaryOrientation.a(childAt);
            if (this.mPrimaryOrientation.b(childAt) > b && a < c) {
                if (a >= b || !z) {
                    return childAt;
                }
                if (z2 && view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    /* access modifiers changed from: 0000 */
    public View findFirstVisibleItemClosestToEnd(boolean z, boolean z2) {
        ensureOrientationHelper();
        int b = this.mPrimaryOrientation.b();
        int c = this.mPrimaryOrientation.c();
        View view = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int a = this.mPrimaryOrientation.a(childAt);
            int b2 = this.mPrimaryOrientation.b(childAt);
            if (b2 > b && a < c) {
                if (b2 <= c || !z) {
                    return childAt;
                }
                if (z2 && view == null) {
                    view = childAt;
                }
            }
        }
        return view;
    }

    private void fixEndGap(Recycler recycler, State state, boolean z) {
        int maxEnd = getMaxEnd(Integer.MIN_VALUE);
        if (maxEnd != Integer.MIN_VALUE) {
            int c = this.mPrimaryOrientation.c() - maxEnd;
            if (c > 0) {
                int i = c - (-scrollBy(-c, recycler, state));
                if (z && i > 0) {
                    this.mPrimaryOrientation.a(i);
                }
            }
        }
    }

    private void fixStartGap(Recycler recycler, State state, boolean z) {
        int minStart = getMinStart(Integer.MAX_VALUE);
        if (minStart != Integer.MAX_VALUE) {
            int b = minStart - this.mPrimaryOrientation.b();
            if (b > 0) {
                int scrollBy = b - scrollBy(b, recycler, state);
                if (z && scrollBy > 0) {
                    this.mPrimaryOrientation.a(-scrollBy);
                }
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004d  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x006f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void updateLayoutState(int r5, android.support.v7.widget.RecyclerView.State r6) {
        /*
            r4 = this;
            android.support.v7.widget.LayoutState r0 = r4.mLayoutState
            r1 = 0
            r0.b = r1
            android.support.v7.widget.LayoutState r0 = r4.mLayoutState
            r0.c = r5
            boolean r0 = r4.isSmoothScrolling()
            r2 = 1
            if (r0 == 0) goto L_0x002e
            int r6 = r6.a
            r0 = -1
            if (r6 == r0) goto L_0x002e
            boolean r0 = r4.mShouldReverseLayout
            if (r6 >= r5) goto L_0x001b
            r5 = 1
            goto L_0x001c
        L_0x001b:
            r5 = 0
        L_0x001c:
            if (r0 != r5) goto L_0x0027
            android.support.v7.widget.OrientationHelper r5 = r4.mPrimaryOrientation
            int r5 = r5.e()
            r6 = r5
            r5 = 0
            goto L_0x0030
        L_0x0027:
            android.support.v7.widget.OrientationHelper r5 = r4.mPrimaryOrientation
            int r5 = r5.e()
            goto L_0x002f
        L_0x002e:
            r5 = 0
        L_0x002f:
            r6 = 0
        L_0x0030:
            boolean r0 = r4.getClipToPadding()
            if (r0 == 0) goto L_0x004d
            android.support.v7.widget.LayoutState r0 = r4.mLayoutState
            android.support.v7.widget.OrientationHelper r3 = r4.mPrimaryOrientation
            int r3 = r3.b()
            int r3 = r3 - r5
            r0.f = r3
            android.support.v7.widget.LayoutState r5 = r4.mLayoutState
            android.support.v7.widget.OrientationHelper r0 = r4.mPrimaryOrientation
            int r0 = r0.c()
            int r0 = r0 + r6
            r5.g = r0
            goto L_0x005d
        L_0x004d:
            android.support.v7.widget.LayoutState r0 = r4.mLayoutState
            android.support.v7.widget.OrientationHelper r3 = r4.mPrimaryOrientation
            int r3 = r3.d()
            int r3 = r3 + r6
            r0.g = r3
            android.support.v7.widget.LayoutState r6 = r4.mLayoutState
            int r5 = -r5
            r6.f = r5
        L_0x005d:
            android.support.v7.widget.LayoutState r5 = r4.mLayoutState
            r5.h = r1
            android.support.v7.widget.LayoutState r5 = r4.mLayoutState
            r5.a = r2
            android.support.v7.widget.LayoutState r5 = r4.mLayoutState
            android.support.v7.widget.OrientationHelper r6 = r4.mPrimaryOrientation
            int r6 = r6.g()
            if (r6 != 0) goto L_0x0070
            r1 = 1
        L_0x0070:
            r5.i = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.updateLayoutState(int, android.support.v7.widget.RecyclerView$State):void");
    }

    private void setLayoutStateDirection(int i) {
        this.mLayoutState.e = i;
        LayoutState layoutState = this.mLayoutState;
        int i2 = 1;
        if (this.mShouldReverseLayout != (i == -1)) {
            i2 = -1;
        }
        layoutState.d = i2;
    }

    public void offsetChildrenHorizontal(int i) {
        super.offsetChildrenHorizontal(i);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            this.mSpans[i2].d(i);
        }
    }

    public void offsetChildrenVertical(int i) {
        super.offsetChildrenVertical(i);
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            this.mSpans[i2].d(i);
        }
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, 2);
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        handleUpdate(i, i2, 1);
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.mLazySpanLookup.a();
        requestLayout();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        handleUpdate(i, i2, 8);
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        handleUpdate(i, i2, 4);
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0026  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0036  */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0043 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0044  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleUpdate(int r6, int r7, int r8) {
        /*
            r5 = this;
            boolean r0 = r5.mShouldReverseLayout
            if (r0 == 0) goto L_0x0009
            int r0 = r5.getLastChildPosition()
            goto L_0x000d
        L_0x0009:
            int r0 = r5.getFirstChildPosition()
        L_0x000d:
            r1 = 8
            if (r8 != r1) goto L_0x001b
            if (r6 >= r7) goto L_0x0016
            int r2 = r7 + 1
            goto L_0x001d
        L_0x0016:
            int r2 = r6 + 1
            r3 = r2
            r2 = r7
            goto L_0x001f
        L_0x001b:
            int r2 = r6 + r7
        L_0x001d:
            r3 = r2
            r2 = r6
        L_0x001f:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r5.mLazySpanLookup
            r4.b(r2)
            if (r8 == r1) goto L_0x0036
            switch(r8) {
                case 1: goto L_0x0030;
                case 2: goto L_0x002a;
                default: goto L_0x0029;
            }
        L_0x0029:
            goto L_0x0041
        L_0x002a:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r8 = r5.mLazySpanLookup
            r8.a(r6, r7)
            goto L_0x0041
        L_0x0030:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r8 = r5.mLazySpanLookup
            r8.b(r6, r7)
            goto L_0x0041
        L_0x0036:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r8 = r5.mLazySpanLookup
            r1 = 1
            r8.a(r6, r1)
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r6 = r5.mLazySpanLookup
            r6.b(r7, r1)
        L_0x0041:
            if (r3 > r0) goto L_0x0044
            return
        L_0x0044:
            boolean r6 = r5.mShouldReverseLayout
            if (r6 == 0) goto L_0x004d
            int r6 = r5.getFirstChildPosition()
            goto L_0x0051
        L_0x004d:
            int r6 = r5.getLastChildPosition()
        L_0x0051:
            if (r2 > r6) goto L_0x0056
            r5.requestLayout()
        L_0x0056:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.handleUpdate(int, int, int):void");
    }

    /* JADX WARNING: type inference failed for: r9v0 */
    /* JADX WARNING: type inference failed for: r9v1, types: [boolean, int] */
    /* JADX WARNING: type inference failed for: r9v3 */
    /* JADX WARNING: type inference failed for: r9v8 */
    /* JADX WARNING: type inference failed for: r9v9 */
    /* JADX WARNING: Multi-variable type inference failed. Error: jadx.core.utils.exceptions.JadxRuntimeException: No candidate types for var: r9v1, types: [boolean, int]
      assigns: []
      uses: [boolean, int, ?[int, short, byte, char]]
      mth insns count: 242
    	at jadx.core.dex.visitors.typeinference.TypeSearch.fillTypeCandidates(TypeSearch.java:237)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.typeinference.TypeSearch.run(TypeSearch.java:53)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.runMultiVariableSearch(TypeInferenceVisitor.java:104)
    	at jadx.core.dex.visitors.typeinference.TypeInferenceVisitor.visit(TypeInferenceVisitor.java:97)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
    	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
    	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
    	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
    	at jadx.core.ProcessClass.process(ProcessClass.java:30)
    	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:311)
    	at jadx.api.JavaClass.decompile(JavaClass.java:62)
    	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:217)
     */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x01f4  */
    /* JADX WARNING: Unknown variable types count: 3 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int fill(android.support.v7.widget.RecyclerView.Recycler r18, android.support.v7.widget.LayoutState r19, android.support.v7.widget.RecyclerView.State r20) {
        /*
            r17 = this;
            r6 = r17
            r7 = r18
            r8 = r19
            java.util.BitSet r0 = r6.mRemainingSpans
            int r1 = r6.mSpanCount
            r9 = 0
            r10 = 1
            r0.set(r9, r1, r10)
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            boolean r0 = r0.i
            if (r0 == 0) goto L_0x0025
            int r0 = r8.e
            if (r0 != r10) goto L_0x0020
            r0 = 2147483647(0x7fffffff, float:NaN)
            r11 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0036
        L_0x0020:
            r0 = -2147483648(0xffffffff80000000, float:-0.0)
            r11 = -2147483648(0xffffffff80000000, float:-0.0)
            goto L_0x0036
        L_0x0025:
            int r0 = r8.e
            if (r0 != r10) goto L_0x0030
            int r0 = r8.g
            int r1 = r8.b
            int r0 = r0 + r1
        L_0x002e:
            r11 = r0
            goto L_0x0036
        L_0x0030:
            int r0 = r8.f
            int r1 = r8.b
            int r0 = r0 - r1
            goto L_0x002e
        L_0x0036:
            int r0 = r8.e
            r6.updateAllRemainingSpans(r0, r11)
            boolean r0 = r6.mShouldReverseLayout
            if (r0 == 0) goto L_0x0047
            android.support.v7.widget.OrientationHelper r0 = r6.mPrimaryOrientation
            int r0 = r0.c()
        L_0x0045:
            r12 = r0
            goto L_0x004e
        L_0x0047:
            android.support.v7.widget.OrientationHelper r0 = r6.mPrimaryOrientation
            int r0 = r0.b()
            goto L_0x0045
        L_0x004e:
            r0 = 0
        L_0x004f:
            int r1 = r8.c
            if (r1 < 0) goto L_0x005d
            int r1 = r8.c
            int r2 = r20.a()
            if (r1 >= r2) goto L_0x005d
            r1 = 1
            goto L_0x005e
        L_0x005d:
            r1 = 0
        L_0x005e:
            r2 = -1
            if (r1 == 0) goto L_0x01f2
            android.support.v7.widget.LayoutState r1 = r6.mLayoutState
            boolean r1 = r1.i
            if (r1 != 0) goto L_0x006f
            java.util.BitSet r1 = r6.mRemainingSpans
            boolean r1 = r1.isEmpty()
            if (r1 != 0) goto L_0x01f2
        L_0x006f:
            int r0 = r8.c
            android.view.View r13 = r7.b(r0)
            int r0 = r8.c
            int r1 = r8.d
            int r0 = r0 + r1
            r8.c = r0
            android.view.ViewGroup$LayoutParams r0 = r13.getLayoutParams()
            r14 = r0
            android.support.v7.widget.StaggeredGridLayoutManager$LayoutParams r14 = (android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams) r14
            android.support.v7.widget.RecyclerView$ViewHolder r0 = r14.c
            int r0 = r0.getLayoutPosition()
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r1 = r6.mLazySpanLookup
            int[] r3 = r1.a
            if (r3 == 0) goto L_0x009a
            int[] r3 = r1.a
            int r3 = r3.length
            if (r0 < r3) goto L_0x0095
            goto L_0x009a
        L_0x0095:
            int[] r1 = r1.a
            r1 = r1[r0]
            goto L_0x009b
        L_0x009a:
            r1 = -1
        L_0x009b:
            if (r1 != r2) goto L_0x009f
            r3 = 1
            goto L_0x00a0
        L_0x009f:
            r3 = 0
        L_0x00a0:
            if (r3 == 0) goto L_0x00bb
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x00ab
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r1 = r6.mSpans
            r1 = r1[r9]
            goto L_0x00af
        L_0x00ab:
            android.support.v7.widget.StaggeredGridLayoutManager$Span r1 = r6.getNextSpan(r8)
        L_0x00af:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.mLazySpanLookup
            r4.c(r0)
            int[] r4 = r4.a
            int r5 = r1.e
            r4[r0] = r5
            goto L_0x00bf
        L_0x00bb:
            android.support.v7.widget.StaggeredGridLayoutManager$Span[] r4 = r6.mSpans
            r1 = r4[r1]
        L_0x00bf:
            r15 = r1
            r14.a = r15
            int r1 = r8.e
            if (r1 != r10) goto L_0x00ca
            r6.addView(r13)
            goto L_0x00cd
        L_0x00ca:
            r6.addView(r13, r9)
        L_0x00cd:
            r6.measureChildWithDecorationsAndMargin(r13, r14, r9)
            int r1 = r8.e
            if (r1 != r10) goto L_0x00fe
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x00dd
            int r1 = r6.getMaxEnd(r12)
            goto L_0x00e1
        L_0x00dd:
            int r1 = r15.b(r12)
        L_0x00e1:
            android.support.v7.widget.OrientationHelper r4 = r6.mPrimaryOrientation
            int r4 = r4.c(r13)
            int r4 = r4 + r1
            if (r3 == 0) goto L_0x00fb
            boolean r5 = r14.b
            if (r5 == 0) goto L_0x00fb
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r5 = r6.createFullSpanItemFromEnd(r1)
            r5.b = r2
            r5.a = r0
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.mLazySpanLookup
            r9.a(r5)
        L_0x00fb:
            r5 = r4
            r4 = r1
            goto L_0x0127
        L_0x00fe:
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x0107
            int r1 = r6.getMinStart(r12)
            goto L_0x010b
        L_0x0107:
            int r1 = r15.a(r12)
        L_0x010b:
            android.support.v7.widget.OrientationHelper r4 = r6.mPrimaryOrientation
            int r4 = r4.c(r13)
            int r4 = r1 - r4
            if (r3 == 0) goto L_0x0126
            boolean r5 = r14.b
            if (r5 == 0) goto L_0x0126
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r5 = r6.createFullSpanItemFromStart(r1)
            r5.b = r10
            r5.a = r0
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.mLazySpanLookup
            r9.a(r5)
        L_0x0126:
            r5 = r1
        L_0x0127:
            boolean r1 = r14.b
            if (r1 == 0) goto L_0x014e
            int r1 = r8.d
            if (r1 != r2) goto L_0x014e
            if (r3 != 0) goto L_0x014c
            int r1 = r8.e
            if (r1 != r10) goto L_0x013b
            boolean r1 = r17.areAllEndsEqual()
        L_0x0139:
            r1 = r1 ^ r10
            goto L_0x0140
        L_0x013b:
            boolean r1 = r17.areAllStartsEqual()
            goto L_0x0139
        L_0x0140:
            if (r1 == 0) goto L_0x014e
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r1 = r6.mLazySpanLookup
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r0 = r1.d(r0)
            if (r0 == 0) goto L_0x014c
            r0.d = r10
        L_0x014c:
            r6.mLaidOutInvalidFullSpan = r10
        L_0x014e:
            r6.attachViewToSpans(r13, r14, r8)
            boolean r0 = r17.isLayoutRTL()
            if (r0 == 0) goto L_0x0182
            int r0 = r6.mOrientation
            if (r0 != r10) goto L_0x0182
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x0166
            android.support.v7.widget.OrientationHelper r0 = r6.mSecondaryOrientation
            int r0 = r0.c()
            goto L_0x0177
        L_0x0166:
            android.support.v7.widget.OrientationHelper r0 = r6.mSecondaryOrientation
            int r0 = r0.c()
            int r1 = r6.mSpanCount
            int r1 = r1 - r10
            int r2 = r15.e
            int r1 = r1 - r2
            int r2 = r6.mSizePerSpan
            int r1 = r1 * r2
            int r0 = r0 - r1
        L_0x0177:
            android.support.v7.widget.OrientationHelper r1 = r6.mSecondaryOrientation
            int r1 = r1.c(r13)
            int r1 = r0 - r1
            r9 = r0
            r3 = r1
            goto L_0x01a3
        L_0x0182:
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x018d
            android.support.v7.widget.OrientationHelper r0 = r6.mSecondaryOrientation
            int r0 = r0.b()
            goto L_0x019a
        L_0x018d:
            int r0 = r15.e
            int r1 = r6.mSizePerSpan
            int r0 = r0 * r1
            android.support.v7.widget.OrientationHelper r1 = r6.mSecondaryOrientation
            int r1 = r1.b()
            int r0 = r0 + r1
        L_0x019a:
            android.support.v7.widget.OrientationHelper r1 = r6.mSecondaryOrientation
            int r1 = r1.c(r13)
            int r1 = r1 + r0
            r3 = r0
            r9 = r1
        L_0x01a3:
            int r0 = r6.mOrientation
            if (r0 != r10) goto L_0x01b0
            r0 = r6
            r1 = r13
            r2 = r3
            r3 = r4
            r4 = r9
            r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5)
            goto L_0x01b8
        L_0x01b0:
            r0 = r6
            r1 = r13
            r2 = r4
            r4 = r5
            r5 = r9
            r0.layoutDecoratedWithMargins(r1, r2, r3, r4, r5)
        L_0x01b8:
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x01c4
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            int r0 = r0.e
            r6.updateAllRemainingSpans(r0, r11)
            goto L_0x01cb
        L_0x01c4:
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            int r0 = r0.e
            r6.updateRemainingSpans(r15, r0, r11)
        L_0x01cb:
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            r6.recycle(r7, r0)
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            boolean r0 = r0.h
            if (r0 == 0) goto L_0x01ee
            boolean r0 = r13.isFocusable()
            if (r0 == 0) goto L_0x01ee
            boolean r0 = r14.b
            if (r0 == 0) goto L_0x01e6
            java.util.BitSet r0 = r6.mRemainingSpans
            r0.clear()
            goto L_0x01ee
        L_0x01e6:
            java.util.BitSet r0 = r6.mRemainingSpans
            int r1 = r15.e
            r2 = 0
            r0.set(r1, r2)
        L_0x01ee:
            r0 = 1
            r9 = 0
            goto L_0x004f
        L_0x01f2:
            if (r0 != 0) goto L_0x01f9
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            r6.recycle(r7, r0)
        L_0x01f9:
            android.support.v7.widget.LayoutState r0 = r6.mLayoutState
            int r0 = r0.e
            if (r0 != r2) goto L_0x0211
            android.support.v7.widget.OrientationHelper r0 = r6.mPrimaryOrientation
            int r0 = r0.b()
            int r0 = r6.getMinStart(r0)
            android.support.v7.widget.OrientationHelper r1 = r6.mPrimaryOrientation
            int r1 = r1.b()
            int r1 = r1 - r0
            goto L_0x0223
        L_0x0211:
            android.support.v7.widget.OrientationHelper r0 = r6.mPrimaryOrientation
            int r0 = r0.c()
            int r0 = r6.getMaxEnd(r0)
            android.support.v7.widget.OrientationHelper r1 = r6.mPrimaryOrientation
            int r1 = r1.c()
            int r1 = r0 - r1
        L_0x0223:
            if (r1 <= 0) goto L_0x022c
            int r0 = r8.b
            int r0 = java.lang.Math.min(r0, r1)
            return r0
        L_0x022c:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.fill(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.LayoutState, android.support.v7.widget.RecyclerView$State):int");
    }

    private FullSpanItem createFullSpanItemFromEnd(int i) {
        FullSpanItem fullSpanItem = new FullSpanItem();
        fullSpanItem.c = new int[this.mSpanCount];
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            fullSpanItem.c[i2] = i - this.mSpans[i2].b(i);
        }
        return fullSpanItem;
    }

    private FullSpanItem createFullSpanItemFromStart(int i) {
        FullSpanItem fullSpanItem = new FullSpanItem();
        fullSpanItem.c = new int[this.mSpanCount];
        for (int i2 = 0; i2 < this.mSpanCount; i2++) {
            fullSpanItem.c[i2] = this.mSpans[i2].a(i) - i;
        }
        return fullSpanItem;
    }

    private void attachViewToSpans(View view, LayoutParams layoutParams, LayoutState layoutState) {
        if (layoutState.e == 1) {
            if (layoutParams.b) {
                appendViewToAllSpans(view);
            } else {
                layoutParams.a.b(view);
            }
        } else if (layoutParams.b) {
            prependViewToAllSpans(view);
        } else {
            layoutParams.a.a(view);
        }
    }

    private void recycle(Recycler recycler, LayoutState layoutState) {
        int i;
        int i2;
        if (layoutState.a && !layoutState.i) {
            if (layoutState.b == 0) {
                if (layoutState.e == -1) {
                    recycleFromEnd(recycler, layoutState.g);
                } else {
                    recycleFromStart(recycler, layoutState.f);
                }
            } else if (layoutState.e == -1) {
                int maxStart = layoutState.f - getMaxStart(layoutState.f);
                if (maxStart < 0) {
                    i2 = layoutState.g;
                } else {
                    i2 = layoutState.g - Math.min(maxStart, layoutState.b);
                }
                recycleFromEnd(recycler, i2);
            } else {
                int minEnd = getMinEnd(layoutState.g) - layoutState.g;
                if (minEnd < 0) {
                    i = layoutState.f;
                } else {
                    i = Math.min(minEnd, layoutState.b) + layoutState.f;
                }
                recycleFromStart(recycler, i);
            }
        }
    }

    private void appendViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            this.mSpans[i].b(view);
        }
    }

    private void prependViewToAllSpans(View view) {
        for (int i = this.mSpanCount - 1; i >= 0; i--) {
            this.mSpans[i].a(view);
        }
    }

    private void layoutDecoratedWithMargins(View view, int i, int i2, int i3, int i4) {
        LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
        layoutDecorated(view, i + layoutParams.leftMargin, i2 + layoutParams.topMargin, i3 - layoutParams.rightMargin, i4 - layoutParams.bottomMargin);
    }

    private void updateAllRemainingSpans(int i, int i2) {
        for (int i3 = 0; i3 < this.mSpanCount; i3++) {
            if (!this.mSpans[i3].a.isEmpty()) {
                updateRemainingSpans(this.mSpans[i3], i, i2);
            }
        }
    }

    private int getMaxStart(int i) {
        int a = this.mSpans[0].a(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int a2 = this.mSpans[i2].a(i);
            if (a2 > a) {
                a = a2;
            }
        }
        return a;
    }

    private int getMinStart(int i) {
        int a = this.mSpans[0].a(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int a2 = this.mSpans[i2].a(i);
            if (a2 < a) {
                a = a2;
            }
        }
        return a;
    }

    /* access modifiers changed from: 0000 */
    public boolean areAllEndsEqual() {
        int b = this.mSpans[0].b(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpans[i].b(Integer.MIN_VALUE) != b) {
                return false;
            }
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean areAllStartsEqual() {
        int a = this.mSpans[0].a(Integer.MIN_VALUE);
        for (int i = 1; i < this.mSpanCount; i++) {
            if (this.mSpans[i].a(Integer.MIN_VALUE) != a) {
                return false;
            }
        }
        return true;
    }

    private int getMaxEnd(int i) {
        int b = this.mSpans[0].b(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int b2 = this.mSpans[i2].b(i);
            if (b2 > b) {
                b = b2;
            }
        }
        return b;
    }

    private int getMinEnd(int i) {
        int b = this.mSpans[0].b(i);
        for (int i2 = 1; i2 < this.mSpanCount; i2++) {
            int b2 = this.mSpans[i2].b(i);
            if (b2 < b) {
                b = b2;
            }
        }
        return b;
    }

    private void recycleFromStart(Recycler recycler, int i) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.mPrimaryOrientation.b(childAt) <= i) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b) {
                    int i2 = 0;
                    while (i2 < this.mSpanCount) {
                        if (this.mSpans[i2].a.size() != 1) {
                            i2++;
                        } else {
                            return;
                        }
                    }
                    for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                        this.mSpans[i3].e();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.e();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, recycler);
            } else {
                return;
            }
        }
    }

    private void recycleFromEnd(Recycler recycler, int i) {
        int childCount = getChildCount() - 1;
        while (childCount >= 0) {
            View childAt = getChildAt(childCount);
            if (this.mPrimaryOrientation.a(childAt) >= i) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                if (layoutParams.b) {
                    int i2 = 0;
                    while (i2 < this.mSpanCount) {
                        if (this.mSpans[i2].a.size() != 1) {
                            i2++;
                        } else {
                            return;
                        }
                    }
                    for (int i3 = 0; i3 < this.mSpanCount; i3++) {
                        this.mSpans[i3].d();
                    }
                } else if (layoutParams.a.a.size() != 1) {
                    layoutParams.a.d();
                } else {
                    return;
                }
                removeAndRecycleView(childAt, recycler);
                childCount--;
            } else {
                return;
            }
        }
    }

    private boolean preferLastSpan(int i) {
        if (this.mOrientation == 0) {
            return (i == -1) != this.mShouldReverseLayout;
        }
        return ((i == -1) == this.mShouldReverseLayout) == isLayoutRTL();
    }

    private Span getNextSpan(LayoutState layoutState) {
        int i;
        int i2;
        int i3 = -1;
        if (preferLastSpan(layoutState.e)) {
            i2 = this.mSpanCount - 1;
            i = -1;
        } else {
            i2 = 0;
            i3 = this.mSpanCount;
            i = 1;
        }
        Span span = null;
        if (layoutState.e == 1) {
            int i4 = Integer.MAX_VALUE;
            int b = this.mPrimaryOrientation.b();
            while (i2 != i3) {
                Span span2 = this.mSpans[i2];
                int b2 = span2.b(b);
                if (b2 < i4) {
                    span = span2;
                    i4 = b2;
                }
                i2 += i;
            }
            return span;
        }
        int i5 = Integer.MIN_VALUE;
        int c = this.mPrimaryOrientation.c();
        while (i2 != i3) {
            Span span3 = this.mSpans[i2];
            int a = span3.a(c);
            if (a > i5) {
                span = span3;
                i5 = a;
            }
            i2 += i;
        }
        return span;
    }

    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        return scrollBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        return scrollBy(i, recycler, state);
    }

    /* access modifiers changed from: private */
    public int calculateScrollDirectionForPosition(int i) {
        if (getChildCount() == 0) {
            return this.mShouldReverseLayout ? 1 : -1;
        }
        return (i < getFirstChildPosition()) != this.mShouldReverseLayout ? -1 : 1;
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        AnonymousClass2 r2 = new LinearSmoothScroller(recyclerView.getContext()) {
            public final PointF a(int i) {
                int access$400 = StaggeredGridLayoutManager.this.calculateScrollDirectionForPosition(i);
                if (access$400 == 0) {
                    return null;
                }
                if (StaggeredGridLayoutManager.this.mOrientation == 0) {
                    return new PointF((float) access$400, 0.0f);
                }
                return new PointF(0.0f, (float) access$400);
            }
        };
        r2.g = i;
        startSmoothScroll(r2);
    }

    public void scrollToPosition(int i) {
        if (!(this.mPendingSavedState == null || this.mPendingSavedState.a == i)) {
            this.mPendingSavedState.a();
        }
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.a();
        }
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = i2;
        requestLayout();
    }

    /* access modifiers changed from: 0000 */
    public int scrollBy(int i, Recycler recycler, State state) {
        int i2;
        int i3;
        ensureOrientationHelper();
        if (i > 0) {
            i3 = getLastChildPosition();
            i2 = 1;
        } else {
            i3 = getFirstChildPosition();
            i2 = -1;
        }
        this.mLayoutState.a = true;
        updateLayoutState(i3, state);
        setLayoutStateDirection(i2);
        LayoutState layoutState = this.mLayoutState;
        layoutState.c = i3 + layoutState.d;
        int abs = Math.abs(i);
        this.mLayoutState.b = abs;
        int fill = fill(recycler, this.mLayoutState, state);
        if (abs >= fill) {
            i = i < 0 ? -fill : fill;
        }
        this.mPrimaryOrientation.a(-i);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        return i;
    }

    private int getLastChildPosition() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }

    private int getFirstChildPosition() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    private int findFirstReferenceChildPosition(int i) {
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            int position = getPosition(getChildAt(i2));
            if (position >= 0 && position < i) {
                return position;
            }
        }
        return 0;
    }

    private int findLastReferenceChildPosition(int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            int position = getPosition(getChildAt(childCount));
            if (position >= 0 && position < i) {
                return position;
            }
        }
        return 0;
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    @Nullable
    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        int i2;
        if (getChildCount() == 0) {
            return null;
        }
        View findContainingItemView = findContainingItemView(view);
        if (findContainingItemView == null) {
            return null;
        }
        ensureOrientationHelper();
        resolveShouldLayoutReverse();
        int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i);
        if (convertFocusDirectionToLayoutDirection == Integer.MIN_VALUE) {
            return null;
        }
        LayoutParams layoutParams = (LayoutParams) findContainingItemView.getLayoutParams();
        boolean z = layoutParams.b;
        Span span = layoutParams.a;
        if (convertFocusDirectionToLayoutDirection == 1) {
            i2 = getLastChildPosition();
        } else {
            i2 = getFirstChildPosition();
        }
        updateLayoutState(i2, state);
        setLayoutStateDirection(convertFocusDirectionToLayoutDirection);
        LayoutState layoutState = this.mLayoutState;
        layoutState.c = layoutState.d + i2;
        this.mLayoutState.b = (int) (((float) this.mPrimaryOrientation.e()) * MAX_SCROLL_FACTOR);
        this.mLayoutState.h = true;
        this.mLayoutState.a = false;
        fill(recycler, this.mLayoutState, state);
        this.mLastLayoutFromEnd = this.mShouldReverseLayout;
        if (!z) {
            View a = span.a(i2, convertFocusDirectionToLayoutDirection);
            if (!(a == null || a == findContainingItemView)) {
                return a;
            }
        }
        if (preferLastSpan(convertFocusDirectionToLayoutDirection)) {
            for (int i3 = this.mSpanCount - 1; i3 >= 0; i3--) {
                View a2 = this.mSpans[i3].a(i2, convertFocusDirectionToLayoutDirection);
                if (a2 != null && a2 != findContainingItemView) {
                    return a2;
                }
            }
        } else {
            for (int i4 = 0; i4 < this.mSpanCount; i4++) {
                View a3 = this.mSpans[i4].a(i2, convertFocusDirectionToLayoutDirection);
                if (a3 != null && a3 != findContainingItemView) {
                    return a3;
                }
            }
        }
        return null;
    }

    private int convertFocusDirectionToLayoutDirection(int i) {
        if (i == 17) {
            return this.mOrientation == 0 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 33) {
            return this.mOrientation == 1 ? -1 : Integer.MIN_VALUE;
        }
        if (i == 66) {
            return this.mOrientation == 0 ? 1 : Integer.MIN_VALUE;
        }
        if (i == 130) {
            return this.mOrientation == 1 ? 1 : Integer.MIN_VALUE;
        }
        switch (i) {
            case 1:
                return -1;
            case 2:
                return 1;
            default:
                return Integer.MIN_VALUE;
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean updateAnchorFromPendingData(State state, AnchorInfo anchorInfo) {
        boolean z = false;
        if (state.c || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition < 0 || this.mPendingScrollPosition >= state.a()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        if (this.mPendingSavedState == null || this.mPendingSavedState.a == -1 || this.mPendingSavedState.c <= 0) {
            View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
            if (findViewByPosition != null) {
                anchorInfo.a = this.mShouldReverseLayout ? getLastChildPosition() : getFirstChildPosition();
                if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                    if (anchorInfo.c) {
                        anchorInfo.b = (this.mPrimaryOrientation.c() - this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.b(findViewByPosition);
                    } else {
                        anchorInfo.b = (this.mPrimaryOrientation.b() + this.mPendingScrollPositionOffset) - this.mPrimaryOrientation.a(findViewByPosition);
                    }
                    return true;
                } else if (this.mPrimaryOrientation.c(findViewByPosition) > this.mPrimaryOrientation.e()) {
                    anchorInfo.b = anchorInfo.c ? this.mPrimaryOrientation.c() : this.mPrimaryOrientation.b();
                    return true;
                } else {
                    int a = this.mPrimaryOrientation.a(findViewByPosition) - this.mPrimaryOrientation.b();
                    if (a < 0) {
                        anchorInfo.b = -a;
                        return true;
                    }
                    int c = this.mPrimaryOrientation.c() - this.mPrimaryOrientation.b(findViewByPosition);
                    if (c < 0) {
                        anchorInfo.b = c;
                        return true;
                    }
                    anchorInfo.b = Integer.MIN_VALUE;
                }
            } else {
                anchorInfo.a = this.mPendingScrollPosition;
                if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                    if (calculateScrollDirectionForPosition(anchorInfo.a) == 1) {
                        z = true;
                    }
                    anchorInfo.c = z;
                    anchorInfo.a();
                } else {
                    int i = this.mPendingScrollPositionOffset;
                    if (anchorInfo.c) {
                        anchorInfo.b = StaggeredGridLayoutManager.this.mPrimaryOrientation.c() - i;
                    } else {
                        anchorInfo.b = StaggeredGridLayoutManager.this.mPrimaryOrientation.b() + i;
                    }
                }
                anchorInfo.d = true;
            }
        } else {
            anchorInfo.b = Integer.MIN_VALUE;
            anchorInfo.a = this.mPendingScrollPosition;
        }
        return true;
    }

    private void updateRemainingSpans(Span span, int i, int i2) {
        int i3 = span.d;
        if (i == -1) {
            if (span.a() + i3 <= i2) {
                this.mRemainingSpans.set(span.e, false);
            }
            return;
        }
        if (span.b() - i3 >= i2) {
            this.mRemainingSpans.set(span.e, false);
        }
    }
}
