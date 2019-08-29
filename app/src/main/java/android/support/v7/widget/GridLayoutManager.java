package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.widget.LinearLayoutManager.LayoutChunkResult;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import com.uc.webview.export.extension.UCCore;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
    boolean a = false;
    public int b = -1;
    int[] c;
    View[] d;
    final SparseIntArray e = new SparseIntArray();
    final SparseIntArray f = new SparseIntArray();
    public SpanSizeLookup g = new DefaultSpanSizeLookup();
    final Rect h = new Rect();

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public final int a(int i) {
            return 1;
        }

        public final int a(int i, int i2) {
            return i % i2;
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        /* access modifiers changed from: 0000 */
        public int a = -1;
        /* access modifiers changed from: 0000 */
        public int b = 0;

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
    }

    public static abstract class SpanSizeLookup {
        final SparseIntArray a = new SparseIntArray();
        private boolean b = false;

        public abstract int a(int i);

        /* access modifiers changed from: 0000 */
        public final int b(int i, int i2) {
            if (!this.b) {
                return a(i, i2);
            }
            int i3 = this.a.get(i, -1);
            if (i3 != -1) {
                return i3;
            }
            int a2 = a(i, i2);
            this.a.put(i, a2);
            return a2;
        }

        /* JADX WARNING: Removed duplicated region for block: B:23:0x005a  */
        /* JADX WARNING: Removed duplicated region for block: B:31:0x006c A[RETURN] */
        /* JADX WARNING: Removed duplicated region for block: B:32:0x006d A[RETURN] */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int a(int r7, int r8) {
            /*
                r6 = this;
                int r0 = r6.a(r7)
                r1 = 0
                if (r0 != r8) goto L_0x0008
                return r1
            L_0x0008:
                boolean r2 = r6.b
                if (r2 == 0) goto L_0x0056
                android.util.SparseIntArray r2 = r6.a
                int r2 = r2.size()
                if (r2 <= 0) goto L_0x0056
                android.util.SparseIntArray r2 = r6.a
                int r2 = r2.size()
                int r2 = r2 + -1
                r3 = r2
                r2 = 0
            L_0x001e:
                if (r2 > r3) goto L_0x0032
                int r4 = r2 + r3
                int r4 = r4 >>> 1
                android.util.SparseIntArray r5 = r6.a
                int r5 = r5.keyAt(r4)
                if (r5 >= r7) goto L_0x002f
                int r2 = r4 + 1
                goto L_0x001e
            L_0x002f:
                int r3 = r4 + -1
                goto L_0x001e
            L_0x0032:
                int r2 = r2 + -1
                if (r2 < 0) goto L_0x0045
                android.util.SparseIntArray r3 = r6.a
                int r3 = r3.size()
                if (r2 >= r3) goto L_0x0045
                android.util.SparseIntArray r3 = r6.a
                int r2 = r3.keyAt(r2)
                goto L_0x0046
            L_0x0045:
                r2 = -1
            L_0x0046:
                if (r2 < 0) goto L_0x0056
                android.util.SparseIntArray r3 = r6.a
                int r3 = r3.get(r2)
                int r4 = r6.a(r2)
                int r3 = r3 + r4
                int r2 = r2 + 1
                goto L_0x0058
            L_0x0056:
                r2 = 0
                r3 = 0
            L_0x0058:
                if (r2 >= r7) goto L_0x0069
                int r4 = r6.a(r2)
                int r3 = r3 + r4
                if (r3 != r8) goto L_0x0063
                r3 = 0
                goto L_0x0066
            L_0x0063:
                if (r3 <= r8) goto L_0x0066
                r3 = r4
            L_0x0066:
                int r2 = r2 + 1
                goto L_0x0058
            L_0x0069:
                int r0 = r0 + r3
                if (r0 > r8) goto L_0x006d
                return r3
            L_0x006d:
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.GridLayoutManager.SpanSizeLookup.a(int, int):int");
        }

        public final int c(int i, int i2) {
            int a2 = a(i);
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < i; i5++) {
                int a3 = a(i5);
                i3 += a3;
                if (i3 == i2) {
                    i4++;
                    i3 = 0;
                } else if (i3 > i2) {
                    i4++;
                    i3 = a3;
                }
            }
            return i3 + a2 > i2 ? i4 + 1 : i4;
        }
    }

    public GridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        b(getProperties(context, attributeSet, i, i2).b);
    }

    public GridLayoutManager(Context context, int i) {
        super(context);
        b(i);
    }

    public GridLayoutManager(Context context, int i, int i2, boolean z) {
        super(context, i2, z);
        b(i);
    }

    public void setStackFromEnd(boolean z) {
        if (z) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    public int getRowCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 0) {
            return this.b;
        }
        if (state.a() <= 0) {
            return 0;
        }
        return a(recycler, state, state.a() - 1) + 1;
    }

    public int getColumnCountForAccessibility(Recycler recycler, State state) {
        if (this.mOrientation == 1) {
            return this.b;
        }
        if (state.a() <= 0) {
            return 0;
        }
        return a(recycler, state, state.a() - 1) + 1;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        android.view.ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.onInitializeAccessibilityNodeInfoForItem(view, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        int a2 = a(recycler, state, layoutParams2.c.getLayoutPosition());
        if (this.mOrientation == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(layoutParams2.a, layoutParams2.b, a2, 1, this.b > 1 && layoutParams2.b == this.b, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(CollectionItemInfoCompat.obtain(a2, 1, layoutParams2.a, layoutParams2.b, this.b > 1 && layoutParams2.b == this.b, false));
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        this.g.a.clear();
    }

    public void onItemsChanged(RecyclerView recyclerView) {
        this.g.a.clear();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        this.g.a.clear();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        this.g.a.clear();
    }

    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        this.g.a.clear();
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

    private void a() {
        int i;
        if (getOrientation() == 1) {
            i = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            i = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        a(i);
    }

    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int i3;
        int i4;
        if (this.c == null) {
            super.setMeasuredDimension(rect, i, i2);
        }
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int paddingTop = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            i4 = chooseSize(i2, rect.height() + paddingTop, getMinimumHeight());
            i3 = chooseSize(i, this.c[this.c.length - 1] + paddingLeft, getMinimumWidth());
        } else {
            i3 = chooseSize(i, rect.width() + paddingLeft, getMinimumWidth());
            i4 = chooseSize(i2, this.c[this.c.length - 1] + paddingTop, getMinimumHeight());
        }
        setMeasuredDimension(i3, i4);
    }

    private void a(int i) {
        this.c = a(this.c, this.b, i);
    }

    private static int[] a(int[] iArr, int i, int i2) {
        int i3;
        if (!(iArr != null && iArr.length == i + 1 && iArr[iArr.length - 1] == i2)) {
            iArr = new int[(i + 1)];
        }
        int i4 = 0;
        iArr[0] = 0;
        int i5 = i2 / i;
        int i6 = i2 % i;
        int i7 = 0;
        for (int i8 = 1; i8 <= i; i8++) {
            i4 += i6;
            if (i4 <= 0 || i - i4 >= i6) {
                i3 = i5;
            } else {
                i3 = i5 + 1;
                i4 -= i;
            }
            i7 += i3;
            iArr[i8] = i7;
        }
        return iArr;
    }

    /* access modifiers changed from: 0000 */
    public void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
        super.onAnchorReady(recycler, state, anchorInfo, i);
        a();
        if (state.a() > 0 && !state.c) {
            boolean z = i == 1;
            int b2 = b(recycler, state, anchorInfo.a);
            if (z) {
                while (b2 > 0 && anchorInfo.a > 0) {
                    anchorInfo.a--;
                    b2 = b(recycler, state, anchorInfo.a);
                }
            } else {
                int a2 = state.a() - 1;
                int i2 = anchorInfo.a;
                while (i2 < a2) {
                    int i3 = i2 + 1;
                    int b3 = b(recycler, state, i3);
                    if (b3 <= b2) {
                        break;
                    }
                    i2 = i3;
                    b2 = b3;
                }
                anchorInfo.a = i2;
            }
        }
        b();
    }

    private void b() {
        if (this.d == null || this.d.length != this.b) {
            this.d = new View[this.b];
        }
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        a();
        b();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        a();
        b();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    /* access modifiers changed from: 0000 */
    public View findReferenceChild(Recycler recycler, State state, int i, int i2, int i3) {
        ensureLayoutState();
        int b2 = this.mOrientationHelper.b();
        int c2 = this.mOrientationHelper.c();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3 && b(recycler, state, position) == 0) {
                if (((android.support.v7.widget.RecyclerView.LayoutParams) childAt.getLayoutParams()).c.isRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.a(childAt) < c2 && this.mOrientationHelper.b(childAt) >= b2) {
                    return childAt;
                } else {
                    if (view == null) {
                        view = childAt;
                    }
                }
            }
            i += i4;
        }
        return view != null ? view : view2;
    }

    /* access modifiers changed from: 0000 */
    public void layoutChunk(Recycler recycler, State state, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        boolean z;
        int i8;
        View view;
        int i9;
        LayoutParams layoutParams;
        int i10;
        Recycler recycler2 = recycler;
        State state2 = state;
        LayoutState layoutState2 = layoutState;
        LayoutChunkResult layoutChunkResult2 = layoutChunkResult;
        int h2 = this.mOrientationHelper.h();
        boolean z2 = true;
        boolean z3 = false;
        boolean z4 = h2 != 1073741824;
        int i11 = getChildCount() > 0 ? this.c[this.b] : 0;
        if (z4) {
            a();
        }
        boolean z5 = layoutState2.e == 1;
        int i12 = this.b;
        if (!z5) {
            i12 = b(recycler2, state2, layoutState2.d) + c(recycler2, state2, layoutState2.d);
        }
        int i13 = 0;
        while (i13 < this.b && layoutState2.a(state2) && i12 > 0) {
            int i14 = layoutState2.d;
            int c2 = c(recycler2, state2, i14);
            if (c2 <= this.b) {
                i12 -= c2;
                if (i12 < 0) {
                    break;
                }
                View a2 = layoutState2.a(recycler2);
                if (a2 == null) {
                    break;
                }
                this.d[i13] = a2;
                i13++;
            } else {
                StringBuilder sb = new StringBuilder("Item at position ");
                sb.append(i14);
                sb.append(" requires ");
                sb.append(c2);
                sb.append(" spans but GridLayoutManager has only ");
                sb.append(this.b);
                sb.append(" spans.");
                throw new IllegalArgumentException(sb.toString());
            }
        }
        if (i13 == 0) {
            layoutChunkResult2.b = true;
            return;
        }
        a(recycler2, state2, i13, z5);
        int i15 = 0;
        int i16 = 0;
        float f2 = 0.0f;
        while (i16 < i13) {
            View view2 = this.d[i16];
            if (layoutState2.k == null) {
                if (z5) {
                    addView(view2);
                } else {
                    addView(view2, z3);
                }
            } else if (z5) {
                addDisappearingView(view2);
            } else {
                addDisappearingView(view2, z3);
            }
            LayoutParams layoutParams2 = (LayoutParams) view2.getLayoutParams();
            int childMeasureSpec = getChildMeasureSpec(this.c[layoutParams2.a + layoutParams2.b] - this.c[layoutParams2.a], h2, z3 ? 1 : 0, this.mOrientation == 0 ? layoutParams2.height : layoutParams2.width, z3);
            int i17 = i15;
            int i18 = i13;
            int childMeasureSpec2 = getChildMeasureSpec(this.mOrientationHelper.e(), this.mOrientationHelper.g(), 0, this.mOrientation == z2 ? layoutParams2.height : layoutParams2.width, z2);
            if (this.mOrientation == z2) {
                layoutParams = layoutParams2;
                view = view2;
                i8 = h2;
                i10 = i17;
                z = z5;
                i9 = i18;
                a(view2, childMeasureSpec, childMeasureSpec2, layoutParams2.height == -1, false);
            } else {
                layoutParams = layoutParams2;
                view = view2;
                i8 = h2;
                z = z5;
                i10 = i17;
                i9 = i18;
                a(view, childMeasureSpec2, childMeasureSpec, layoutParams.width == -1, false);
            }
            View view3 = view;
            int c3 = this.mOrientationHelper.c(view3);
            i15 = c3 > i10 ? c3 : i10;
            float d2 = (((float) this.mOrientationHelper.d(view3)) * 1.0f) / ((float) layoutParams.b);
            if (d2 > f2) {
                f2 = d2;
            }
            i16++;
            i13 = i9;
            h2 = i8;
            z5 = z;
            z2 = true;
            z3 = false;
        }
        int i19 = i15;
        int i20 = i13;
        if (z4) {
            a(Math.max(Math.round(((float) this.b) * f2), i11));
            int i21 = 0;
            for (int i22 = 0; i22 < i20; i22++) {
                View view4 = this.d[i22];
                LayoutParams layoutParams3 = (LayoutParams) view4.getLayoutParams();
                int childMeasureSpec3 = getChildMeasureSpec(this.c[layoutParams3.a + layoutParams3.b] - this.c[layoutParams3.a], UCCore.VERIFY_POLICY_QUICK, 0, this.mOrientation == 0 ? layoutParams3.height : layoutParams3.width, false);
                int childMeasureSpec4 = getChildMeasureSpec(this.mOrientationHelper.e(), this.mOrientationHelper.g(), 0, this.mOrientation == 1 ? layoutParams3.height : layoutParams3.width, true);
                if (this.mOrientation == 1) {
                    a(view4, childMeasureSpec3, childMeasureSpec4, false, true);
                } else {
                    a(view4, childMeasureSpec4, childMeasureSpec3, false, true);
                }
                int c4 = this.mOrientationHelper.c(view4);
                if (c4 > i21) {
                    i21 = c4;
                }
            }
            i = -1;
            i19 = i21;
        } else {
            i = -1;
        }
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(i19, UCCore.VERIFY_POLICY_QUICK);
        for (int i23 = 0; i23 < i20; i23++) {
            View view5 = this.d[i23];
            if (this.mOrientationHelper.c(view5) != i19) {
                LayoutParams layoutParams4 = (LayoutParams) view5.getLayoutParams();
                int childMeasureSpec5 = getChildMeasureSpec(this.c[layoutParams4.a + layoutParams4.b] - this.c[layoutParams4.a], UCCore.VERIFY_POLICY_QUICK, 0, this.mOrientation == 0 ? layoutParams4.height : layoutParams4.width, false);
                if (this.mOrientation == 1) {
                    a(view5, childMeasureSpec5, makeMeasureSpec, true, true);
                } else {
                    a(view5, makeMeasureSpec, childMeasureSpec5, true, true);
                }
            }
        }
        int i24 = 0;
        layoutChunkResult2.a = i19;
        if (this.mOrientation == 1) {
            if (layoutState2.f == i) {
                int i25 = layoutState2.b;
                i2 = i25;
                i3 = i25 - i19;
            } else {
                int i26 = layoutState2.b;
                i3 = i26;
                i2 = i26 + i19;
            }
            i5 = 0;
            i4 = 0;
        } else if (layoutState2.f == i) {
            int i27 = layoutState2.b;
            i3 = 0;
            i2 = 0;
            i4 = i27;
            i5 = i27 - i19;
        } else {
            i5 = layoutState2.b;
            i4 = i5 + i19;
            i3 = 0;
            i2 = 0;
        }
        while (i24 < i20) {
            View view6 = this.d[i24];
            LayoutParams layoutParams5 = (LayoutParams) view6.getLayoutParams();
            if (this.mOrientation != 1) {
                i3 = getPaddingTop() + this.c[layoutParams5.a];
                i2 = this.mOrientationHelper.d(view6) + i3;
            } else if (isLayoutRTL()) {
                int paddingLeft = getPaddingLeft() + this.c[layoutParams5.a + layoutParams5.b];
                i6 = paddingLeft;
                i7 = paddingLeft - this.mOrientationHelper.d(view6);
                int i28 = i3;
                int i29 = i2;
                layoutDecorated(view6, i7 + layoutParams5.leftMargin, i28 + layoutParams5.topMargin, i6 - layoutParams5.rightMargin, i29 - layoutParams5.bottomMargin);
                if (!layoutParams5.c.isRemoved() || layoutParams5.c.isUpdated()) {
                    layoutChunkResult2.c = true;
                }
                layoutChunkResult2.d |= view6.isFocusable();
                i24++;
                i5 = i7;
                i3 = i28;
                i4 = i6;
                i2 = i29;
            } else {
                i5 = getPaddingLeft() + this.c[layoutParams5.a];
                i4 = this.mOrientationHelper.d(view6) + i5;
            }
            i7 = i5;
            i6 = i4;
            int i282 = i3;
            int i292 = i2;
            layoutDecorated(view6, i7 + layoutParams5.leftMargin, i282 + layoutParams5.topMargin, i6 - layoutParams5.rightMargin, i292 - layoutParams5.bottomMargin);
            if (!layoutParams5.c.isRemoved()) {
            }
            layoutChunkResult2.c = true;
            layoutChunkResult2.d |= view6.isFocusable();
            i24++;
            i5 = i7;
            i3 = i282;
            i4 = i6;
            i2 = i292;
        }
        Arrays.fill(this.d, null);
    }

    private void a(View view, int i, int i2, boolean z, boolean z2) {
        boolean z3;
        calculateItemDecorationsForChild(view, this.h);
        android.support.v7.widget.RecyclerView.LayoutParams layoutParams = (android.support.v7.widget.RecyclerView.LayoutParams) view.getLayoutParams();
        if (z || this.mOrientation == 1) {
            i = a(i, layoutParams.leftMargin + this.h.left, layoutParams.rightMargin + this.h.right);
        }
        if (z || this.mOrientation == 0) {
            i2 = a(i2, layoutParams.topMargin + this.h.top, layoutParams.bottomMargin + this.h.bottom);
        }
        if (z2) {
            z3 = shouldReMeasureChild(view, i, i2, layoutParams);
        } else {
            z3 = shouldMeasureChild(view, i, i2, layoutParams);
        }
        if (z3) {
            view.measure(i, i2);
        }
    }

    private static int a(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = MeasureSpec.getMode(i);
        if (mode == Integer.MIN_VALUE || mode == 1073741824) {
            return MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize(i) - i2) - i3), mode);
        }
        return i;
    }

    private void a(Recycler recycler, State state, int i, boolean z) {
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        if (z) {
            i2 = i;
            i3 = 0;
            i4 = 1;
        } else {
            i3 = i - 1;
            i2 = -1;
            i4 = -1;
        }
        if (this.mOrientation != 1 || !isLayoutRTL()) {
            i5 = 1;
        } else {
            i6 = this.b - 1;
            i5 = -1;
        }
        while (i3 != i2) {
            View view = this.d[i3];
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.b = c(recycler, state, getPosition(view));
            if (i5 != -1 || layoutParams.b <= 1) {
                layoutParams.a = i6;
            } else {
                layoutParams.a = i6 - (layoutParams.b - 1);
            }
            i6 += layoutParams.b * i5;
            i3 += i4;
        }
    }

    private void b(int i) {
        if (i != this.b) {
            this.a = true;
            if (i <= 0) {
                throw new IllegalArgumentException("Span count should be at least 1. Provided ".concat(String.valueOf(i)));
            }
            this.b = i;
            this.g.a.clear();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0097, code lost:
        if (r11 == (r15 > r12)) goto L_0x008e;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.view.View onFocusSearchFailed(android.view.View r20, int r21, android.support.v7.widget.RecyclerView.Recycler r22, android.support.v7.widget.RecyclerView.State r23) {
        /*
            r19 = this;
            r0 = r19
            android.view.View r1 = r19.findContainingItemView(r20)
            r2 = 0
            if (r1 != 0) goto L_0x000a
            return r2
        L_0x000a:
            android.view.ViewGroup$LayoutParams r3 = r1.getLayoutParams()
            android.support.v7.widget.GridLayoutManager$LayoutParams r3 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r3
            int r4 = r3.a
            int r5 = r3.a
            int r3 = r3.b
            int r5 = r5 + r3
            android.view.View r3 = super.onFocusSearchFailed(r20, r21, r22, r23)
            if (r3 != 0) goto L_0x0024
            return r2
        L_0x0024:
            r3 = r21
            int r3 = r0.convertFocusDirectionToLayoutDirection(r3)
            r7 = 1
            if (r3 != r7) goto L_0x002f
            r3 = 1
            goto L_0x0030
        L_0x002f:
            r3 = 0
        L_0x0030:
            boolean r8 = r0.mShouldReverseLayout
            if (r3 == r8) goto L_0x0036
            r3 = 1
            goto L_0x0037
        L_0x0036:
            r3 = 0
        L_0x0037:
            r8 = -1
            if (r3 == 0) goto L_0x0042
            int r3 = r19.getChildCount()
            int r3 = r3 - r7
            r9 = -1
            r10 = -1
            goto L_0x0049
        L_0x0042:
            int r3 = r19.getChildCount()
            r9 = r3
            r3 = 0
            r10 = 1
        L_0x0049:
            int r11 = r0.mOrientation
            if (r11 != r7) goto L_0x0055
            boolean r11 = r19.isLayoutRTL()
            if (r11 == 0) goto L_0x0055
            r11 = 1
            goto L_0x0056
        L_0x0055:
            r11 = 0
        L_0x0056:
            r8 = 0
            r12 = -1
        L_0x0058:
            if (r3 == r9) goto L_0x00b0
            android.view.View r13 = r0.getChildAt(r3)
            if (r13 == r1) goto L_0x00b0
            boolean r14 = r13.isFocusable()
            if (r14 == 0) goto L_0x00ad
            android.view.ViewGroup$LayoutParams r14 = r13.getLayoutParams()
            android.support.v7.widget.GridLayoutManager$LayoutParams r14 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r14
            int r15 = r14.a
            int r16 = r14.a
            int r17 = r14.b
            int r6 = r16 + r17
            if (r15 != r4) goto L_0x007f
            if (r6 != r5) goto L_0x007f
            return r13
        L_0x007f:
            if (r2 != 0) goto L_0x0082
            goto L_0x009b
        L_0x0082:
            int r16 = java.lang.Math.max(r15, r4)
            int r17 = java.lang.Math.min(r6, r5)
            int r7 = r17 - r16
            if (r7 <= r8) goto L_0x0090
        L_0x008e:
            r7 = 1
            goto L_0x009b
        L_0x0090:
            if (r7 != r8) goto L_0x009a
            if (r15 <= r12) goto L_0x0096
            r7 = 1
            goto L_0x0097
        L_0x0096:
            r7 = 0
        L_0x0097:
            if (r11 != r7) goto L_0x009a
            goto L_0x008e
        L_0x009a:
            r7 = 0
        L_0x009b:
            if (r7 == 0) goto L_0x00ad
            int r2 = r14.a
            int r6 = java.lang.Math.min(r6, r5)
            int r7 = java.lang.Math.max(r15, r4)
            int r6 = r6 - r7
            r12 = r2
            r8 = r6
            r2 = r13
        L_0x00ad:
            int r3 = r3 + r10
            r7 = 1
            goto L_0x0058
        L_0x00b0:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):android.view.View");
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && !this.a;
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        if (state.c) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                LayoutParams layoutParams = (LayoutParams) getChildAt(i).getLayoutParams();
                int layoutPosition = layoutParams.c.getLayoutPosition();
                this.e.put(layoutPosition, layoutParams.b);
                this.f.put(layoutPosition, layoutParams.a);
            }
        }
        super.onLayoutChildren(recycler, state);
        this.e.clear();
        this.f.clear();
        if (!state.c) {
            this.a = false;
        }
    }

    private int a(Recycler recycler, State state, int i) {
        if (!state.c) {
            return this.g.c(i, this.b);
        }
        int a2 = recycler.a(i);
        if (a2 == -1) {
            return 0;
        }
        return this.g.c(a2, this.b);
    }

    private int b(Recycler recycler, State state, int i) {
        if (!state.c) {
            return this.g.b(i, this.b);
        }
        int i2 = this.f.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int a2 = recycler.a(i);
        if (a2 == -1) {
            return 0;
        }
        return this.g.b(a2, this.b);
    }

    private int c(Recycler recycler, State state, int i) {
        if (!state.c) {
            return this.g.a(i);
        }
        int i2 = this.e.get(i, -1);
        if (i2 != -1) {
            return i2;
        }
        int a2 = recycler.a(i);
        if (a2 == -1) {
            return 1;
        }
        return this.g.a(a2);
    }
}
