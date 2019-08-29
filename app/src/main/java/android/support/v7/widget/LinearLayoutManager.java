package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutManager.Properties;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import java.util.List;

public class LinearLayoutManager extends LayoutManager implements ViewDropHandler {
    private static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo;
    private boolean mLastStackFromEnd;
    private LayoutState mLayoutState;
    int mOrientation;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState;
    int mPendingScrollPosition;
    int mPendingScrollPositionOffset;
    private boolean mRecycleChildrenOnDetach;
    private boolean mReverseLayout;
    boolean mShouldReverseLayout;
    private boolean mSmoothScrollbarEnabled;
    private boolean mStackFromEnd;

    class AnchorInfo {
        int a;
        int b;
        boolean c;

        AnchorInfo() {
        }

        /* access modifiers changed from: 0000 */
        public final void a() {
            this.b = this.c ? LinearLayoutManager.this.mOrientationHelper.c() : LinearLayoutManager.this.mOrientationHelper.b();
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("AnchorInfo{mPosition=");
            sb.append(this.a);
            sb.append(", mCoordinate=");
            sb.append(this.b);
            sb.append(", mLayoutFromEnd=");
            sb.append(this.c);
            sb.append('}');
            return sb.toString();
        }

        public final void a(View view) {
            if (this.c) {
                this.b = LinearLayoutManager.this.mOrientationHelper.b(view) + LinearLayoutManager.this.mOrientationHelper.a();
            } else {
                this.b = LinearLayoutManager.this.mOrientationHelper.a(view);
            }
            this.a = LinearLayoutManager.this.getPosition(view);
        }

        static /* synthetic */ boolean a(View view, State state) {
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            return !layoutParams.c.isRemoved() && layoutParams.c.getLayoutPosition() >= 0 && layoutParams.c.getLayoutPosition() < state.a();
        }
    }

    public static class LayoutChunkResult {
        public int a;
        public boolean b;
        public boolean c;
        public boolean d;

        protected LayoutChunkResult() {
        }
    }

    static class LayoutState {
        boolean a = true;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h = 0;
        boolean i = false;
        int j;
        List<ViewHolder> k = null;
        boolean l;

        LayoutState() {
        }

        /* access modifiers changed from: 0000 */
        public final boolean a(State state) {
            return this.d >= 0 && this.d < state.a();
        }

        /* access modifiers changed from: 0000 */
        public final View a(Recycler recycler) {
            if (this.k != null) {
                return a();
            }
            View b2 = recycler.b(this.d);
            this.d += this.e;
            return b2;
        }

        private View a() {
            int size = this.k.size();
            int i2 = 0;
            while (i2 < size) {
                View view = this.k.get(i2).itemView;
                LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
                if (layoutParams.c.isRemoved() || this.d != layoutParams.c.getLayoutPosition()) {
                    i2++;
                } else {
                    a(view);
                    return view;
                }
            }
            return null;
        }

        public final void a(View view) {
            View b2 = b(view);
            if (b2 == null) {
                this.d = -1;
            } else {
                this.d = ((LayoutParams) b2.getLayoutParams()).c.getLayoutPosition();
            }
        }

        private View b(View view) {
            int size = this.k.size();
            View view2 = null;
            int i2 = Integer.MAX_VALUE;
            for (int i3 = 0; i3 < size; i3++) {
                View view3 = this.k.get(i3).itemView;
                LayoutParams layoutParams = (LayoutParams) view3.getLayoutParams();
                if (view3 != view && !layoutParams.c.isRemoved()) {
                    int layoutPosition = (layoutParams.c.getLayoutPosition() - this.d) * this.e;
                    if (layoutPosition >= 0 && layoutPosition < i2) {
                        if (layoutPosition == 0) {
                            return view3;
                        }
                        view2 = view3;
                        i2 = layoutPosition;
                    }
                }
            }
            return view2;
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
        boolean c;

        public int describeContents() {
            return 0;
        }

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            boolean z = true;
            this.c = parcel.readInt() != 1 ? false : z;
        }

        public SavedState(SavedState savedState) {
            this.a = savedState.a;
            this.b = savedState.b;
            this.c = savedState.c;
        }

        /* access modifiers changed from: 0000 */
        public final boolean a() {
            return this.a >= 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c ? 1 : 0);
        }
    }

    /* access modifiers changed from: 0000 */
    public void onAnchorReady(Recycler recycler, State state, AnchorInfo anchorInfo, int i) {
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        setOrientation(i);
        setReverseLayout(z);
        setAutoMeasureEnabled(true);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mReverseLayout = false;
        this.mShouldReverseLayout = false;
        this.mStackFromEnd = false;
        this.mSmoothScrollbarEnabled = true;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mPendingSavedState = null;
        this.mAnchorInfo = new AnchorInfo();
        Properties properties = getProperties(context, attributeSet, i, i2);
        setOrientation(properties.a);
        setReverseLayout(properties.c);
        setStackFromEnd(properties.d);
        setAutoMeasureEnabled(true);
    }

    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public void setRecycleChildrenOnDetach(boolean z) {
        this.mRecycleChildrenOnDetach = z;
    }

    public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.mRecycleChildrenOnDetach) {
            removeAndRecycleAllViews(recycler);
            recycler.a();
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            AccessibilityRecordCompat asRecord = AccessibilityEventCompat.asRecord(accessibilityEvent);
            asRecord.setFromIndex(findFirstVisibleItemPosition());
            asRecord.setToIndex(findLastVisibleItemPosition());
        }
    }

    public Parcelable onSaveInstanceState() {
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        if (getChildCount() > 0) {
            ensureLayoutState();
            boolean z = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
            savedState.c = z;
            if (z) {
                View childClosestToEnd = getChildClosestToEnd();
                savedState.b = this.mOrientationHelper.c() - this.mOrientationHelper.b(childClosestToEnd);
                savedState.a = getPosition(childClosestToEnd);
            } else {
                View childClosestToStart = getChildClosestToStart();
                savedState.a = getPosition(childClosestToStart);
                savedState.b = this.mOrientationHelper.a(childClosestToStart) - this.mOrientationHelper.b();
            }
        } else {
            savedState.a = -1;
        }
        return savedState;
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.mPendingSavedState = (SavedState) parcelable;
            requestLayout();
        }
    }

    public boolean canScrollHorizontally() {
        return this.mOrientation == 0;
    }

    public boolean canScrollVertically() {
        return this.mOrientation == 1;
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd != z) {
            this.mStackFromEnd = z;
            requestLayout();
        }
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public void setOrientation(int i) {
        if (i == 0 || i == 1) {
            assertNotInLayoutOrScroll(null);
            if (i != this.mOrientation) {
                this.mOrientation = i;
                this.mOrientationHelper = null;
                requestLayout();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("invalid orientation:".concat(String.valueOf(i)));
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

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (z != this.mReverseLayout) {
            this.mReverseLayout = z;
            requestLayout();
        }
    }

    public View findViewByPosition(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i - getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (getPosition(childAt) == i) {
                return childAt;
            }
        }
        return super.findViewByPosition(i);
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        AnonymousClass1 r2 = new LinearSmoothScroller(recyclerView.getContext()) {
            public final PointF a(int i) {
                return LinearLayoutManager.this.computeScrollVectorForPosition(i);
            }
        };
        r2.g = i;
        startSmoothScroll(r2);
    }

    public PointF computeScrollVectorForPosition(int i) {
        if (getChildCount() == 0) {
            return null;
        }
        boolean z = false;
        int i2 = 1;
        if (i < getPosition(getChildAt(0))) {
            z = true;
        }
        if (z != this.mShouldReverseLayout) {
            i2 = -1;
        }
        if (this.mOrientation == 0) {
            return new PointF((float) i2, 0.0f);
        }
        return new PointF(0.0f, (float) i2);
    }

    public void onLayoutChildren(Recycler recycler, State state) {
        int i;
        int i2;
        int i3;
        int i4;
        if (!(this.mPendingSavedState == null && this.mPendingScrollPosition == -1) && state.a() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (this.mPendingSavedState != null && this.mPendingSavedState.a()) {
            this.mPendingScrollPosition = this.mPendingSavedState.a;
        }
        ensureLayoutState();
        this.mLayoutState.a = false;
        resolveShouldLayoutReverse();
        AnchorInfo anchorInfo = this.mAnchorInfo;
        anchorInfo.a = -1;
        anchorInfo.b = Integer.MIN_VALUE;
        anchorInfo.c = false;
        this.mAnchorInfo.c = this.mShouldReverseLayout ^ this.mStackFromEnd;
        updateAnchorInfoForLayout(recycler, state, this.mAnchorInfo);
        int extraLayoutSpace = getExtraLayoutSpace(state);
        if (this.mLayoutState.j >= 0) {
            i = extraLayoutSpace;
            extraLayoutSpace = 0;
        } else {
            i = 0;
        }
        int b = extraLayoutSpace + this.mOrientationHelper.b();
        int f = i + this.mOrientationHelper.f();
        if (!(!state.c || this.mPendingScrollPosition == -1 || this.mPendingScrollPositionOffset == Integer.MIN_VALUE)) {
            View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
            if (findViewByPosition != null) {
                if (this.mShouldReverseLayout) {
                    i4 = (this.mOrientationHelper.c() - this.mOrientationHelper.b(findViewByPosition)) - this.mPendingScrollPositionOffset;
                } else {
                    i4 = this.mPendingScrollPositionOffset - (this.mOrientationHelper.a(findViewByPosition) - this.mOrientationHelper.b());
                }
                if (i4 > 0) {
                    b += i4;
                } else {
                    f -= i4;
                }
            }
        }
        onAnchorReady(recycler, state, this.mAnchorInfo, (!this.mAnchorInfo.c ? !this.mShouldReverseLayout : this.mShouldReverseLayout) ? 1 : -1);
        detachAndScrapAttachedViews(recycler);
        this.mLayoutState.l = this.mOrientationHelper.g() == 0;
        this.mLayoutState.i = state.c;
        if (this.mAnchorInfo.c) {
            updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.h = b;
            fill(recycler, this.mLayoutState, state, false);
            i3 = this.mLayoutState.b;
            int i5 = this.mLayoutState.d;
            if (this.mLayoutState.c > 0) {
                f += this.mLayoutState.c;
            }
            updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.h = f;
            this.mLayoutState.d += this.mLayoutState.e;
            fill(recycler, this.mLayoutState, state, false);
            i2 = this.mLayoutState.b;
            if (this.mLayoutState.c > 0) {
                int i6 = this.mLayoutState.c;
                updateLayoutStateToFillStart(i5, i3);
                this.mLayoutState.h = i6;
                fill(recycler, this.mLayoutState, state, false);
                i3 = this.mLayoutState.b;
            }
        } else {
            updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.h = f;
            fill(recycler, this.mLayoutState, state, false);
            i2 = this.mLayoutState.b;
            int i7 = this.mLayoutState.d;
            if (this.mLayoutState.c > 0) {
                b += this.mLayoutState.c;
            }
            updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.h = b;
            this.mLayoutState.d += this.mLayoutState.e;
            fill(recycler, this.mLayoutState, state, false);
            i3 = this.mLayoutState.b;
            if (this.mLayoutState.c > 0) {
                int i8 = this.mLayoutState.c;
                updateLayoutStateToFillEnd(i7, i2);
                this.mLayoutState.h = i8;
                fill(recycler, this.mLayoutState, state, false);
                i2 = this.mLayoutState.b;
            }
        }
        if (getChildCount() > 0) {
            if (this.mShouldReverseLayout ^ this.mStackFromEnd) {
                int fixLayoutEndGap = fixLayoutEndGap(i2, recycler, state, true);
                int i9 = i3 + fixLayoutEndGap;
                int fixLayoutStartGap = fixLayoutStartGap(i9, recycler, state, false);
                i3 = i9 + fixLayoutStartGap;
                i2 = i2 + fixLayoutEndGap + fixLayoutStartGap;
            } else {
                int fixLayoutStartGap2 = fixLayoutStartGap(i3, recycler, state, true);
                int i10 = i2 + fixLayoutStartGap2;
                int fixLayoutEndGap2 = fixLayoutEndGap(i10, recycler, state, false);
                i3 = i3 + fixLayoutStartGap2 + fixLayoutEndGap2;
                i2 = i10 + fixLayoutEndGap2;
            }
        }
        layoutForPredictiveAnimations(recycler, state, i3, i2);
        if (!state.c) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            OrientationHelper orientationHelper = this.mOrientationHelper;
            orientationHelper.b = orientationHelper.e();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
        this.mPendingSavedState = null;
    }

    private void updateAnchorInfoForLayout(Recycler recycler, State state, AnchorInfo anchorInfo) {
        if (!updateAnchorFromPendingData(state, anchorInfo) && !updateAnchorFromChildren(recycler, state, anchorInfo)) {
            anchorInfo.a();
            anchorInfo.a = this.mStackFromEnd ? state.a() - 1 : 0;
        }
    }

    private boolean updateAnchorFromChildren(Recycler recycler, State state, AnchorInfo anchorInfo) {
        boolean z = false;
        if (getChildCount() == 0) {
            return false;
        }
        View focusedChild = getFocusedChild();
        if (focusedChild != null && AnchorInfo.a(focusedChild, state)) {
            int a = LinearLayoutManager.this.mOrientationHelper.a();
            if (a >= 0) {
                anchorInfo.a(focusedChild);
            } else {
                anchorInfo.a = LinearLayoutManager.this.getPosition(focusedChild);
                if (anchorInfo.c) {
                    int c = (LinearLayoutManager.this.mOrientationHelper.c() - a) - LinearLayoutManager.this.mOrientationHelper.b(focusedChild);
                    anchorInfo.b = LinearLayoutManager.this.mOrientationHelper.c() - c;
                    if (c > 0) {
                        int b = LinearLayoutManager.this.mOrientationHelper.b();
                        int c2 = (anchorInfo.b - LinearLayoutManager.this.mOrientationHelper.c(focusedChild)) - (b + Math.min(LinearLayoutManager.this.mOrientationHelper.a(focusedChild) - b, 0));
                        if (c2 < 0) {
                            anchorInfo.b += Math.min(c, -c2);
                        }
                    }
                } else {
                    int a2 = LinearLayoutManager.this.mOrientationHelper.a(focusedChild);
                    int b2 = a2 - LinearLayoutManager.this.mOrientationHelper.b();
                    anchorInfo.b = a2;
                    if (b2 > 0) {
                        int c3 = (LinearLayoutManager.this.mOrientationHelper.c() - Math.min(0, (LinearLayoutManager.this.mOrientationHelper.c() - a) - LinearLayoutManager.this.mOrientationHelper.b(focusedChild))) - (a2 + LinearLayoutManager.this.mOrientationHelper.c(focusedChild));
                        if (c3 < 0) {
                            anchorInfo.b -= Math.min(b2, -c3);
                        }
                    }
                }
            }
            return true;
        } else if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        } else {
            View findReferenceChildClosestToEnd = anchorInfo.c ? findReferenceChildClosestToEnd(recycler, state) : findReferenceChildClosestToStart(recycler, state);
            if (findReferenceChildClosestToEnd == null) {
                return false;
            }
            anchorInfo.a(findReferenceChildClosestToEnd);
            if (!state.c && supportsPredictiveItemAnimations()) {
                if (this.mOrientationHelper.a(findReferenceChildClosestToEnd) >= this.mOrientationHelper.c() || this.mOrientationHelper.b(findReferenceChildClosestToEnd) < this.mOrientationHelper.b()) {
                    z = true;
                }
                if (z) {
                    anchorInfo.b = anchorInfo.c ? this.mOrientationHelper.c() : this.mOrientationHelper.b();
                }
            }
            return true;
        }
    }

    private int fixLayoutEndGap(int i, Recycler recycler, State state, boolean z) {
        int c = this.mOrientationHelper.c() - i;
        if (c <= 0) {
            return 0;
        }
        int i2 = -scrollBy(-c, recycler, state);
        int i3 = i + i2;
        if (z) {
            int c2 = this.mOrientationHelper.c() - i3;
            if (c2 > 0) {
                this.mOrientationHelper.a(c2);
                return c2 + i2;
            }
        }
        return i2;
    }

    private int fixLayoutStartGap(int i, Recycler recycler, State state, boolean z) {
        int b = i - this.mOrientationHelper.b();
        if (b <= 0) {
            return 0;
        }
        int i2 = -scrollBy(b, recycler, state);
        int i3 = i + i2;
        if (z) {
            int b2 = i3 - this.mOrientationHelper.b();
            if (b2 > 0) {
                this.mOrientationHelper.a(-b2);
                return i2 - b2;
            }
        }
        return i2;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        updateLayoutStateToFillEnd(anchorInfo.a, anchorInfo.b);
    }

    private void updateLayoutStateToFillEnd(int i, int i2) {
        this.mLayoutState.c = this.mOrientationHelper.c() - i2;
        this.mLayoutState.e = this.mShouldReverseLayout ? -1 : 1;
        this.mLayoutState.d = i;
        this.mLayoutState.f = 1;
        this.mLayoutState.b = i2;
        this.mLayoutState.g = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        updateLayoutStateToFillStart(anchorInfo.a, anchorInfo.b);
    }

    private void updateLayoutStateToFillStart(int i, int i2) {
        this.mLayoutState.c = i2 - this.mOrientationHelper.b();
        this.mLayoutState.d = i;
        this.mLayoutState.e = this.mShouldReverseLayout ? 1 : -1;
        this.mLayoutState.f = -1;
        this.mLayoutState.b = i2;
        this.mLayoutState.g = Integer.MIN_VALUE;
    }

    /* access modifiers changed from: protected */
    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    /* access modifiers changed from: 0000 */
    public void ensureLayoutState() {
        if (this.mLayoutState == null) {
            this.mLayoutState = createLayoutState();
        }
        if (this.mOrientationHelper == null) {
            this.mOrientationHelper = OrientationHelper.a(this, this.mOrientation);
        }
    }

    /* access modifiers changed from: 0000 */
    public LayoutState createLayoutState() {
        return new LayoutState();
    }

    public void scrollToPosition(int i) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.a = -1;
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        this.mPendingScrollPosition = i;
        this.mPendingScrollPositionOffset = i2;
        if (this.mPendingSavedState != null) {
            this.mPendingSavedState.a = -1;
        }
        requestLayout();
    }

    public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
        if (this.mOrientation == 1) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public int scrollVerticallyBy(int i, Recycler recycler, State state) {
        if (this.mOrientation == 0) {
            return 0;
        }
        return scrollBy(i, recycler, state);
    }

    public int computeHorizontalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    public int computeVerticalScrollOffset(State state) {
        return computeScrollOffset(state);
    }

    public int computeHorizontalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    public int computeVerticalScrollExtent(State state) {
        return computeScrollExtent(state);
    }

    public int computeHorizontalScrollRange(State state) {
        return computeScrollRange(state);
    }

    public int computeVerticalScrollRange(State state) {
        return computeScrollRange(state);
    }

    private int computeScrollOffset(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        return ScrollbarHelper.a(state, orientationHelper, findFirstVisibleChildClosestToStart, findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollExtent(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        return ScrollbarHelper.a(state, orientationHelper, findFirstVisibleChildClosestToStart, findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollRange(State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        ensureLayoutState();
        OrientationHelper orientationHelper = this.mOrientationHelper;
        View findFirstVisibleChildClosestToStart = findFirstVisibleChildClosestToStart(!this.mSmoothScrollbarEnabled, true);
        return ScrollbarHelper.b(state, orientationHelper, findFirstVisibleChildClosestToStart, findFirstVisibleChildClosestToEnd(!this.mSmoothScrollbarEnabled, true), this, this.mSmoothScrollbarEnabled);
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.mSmoothScrollbarEnabled = z;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    private void updateLayoutState(int i, int i2, boolean z, State state) {
        int i3;
        this.mLayoutState.l = this.mOrientationHelper.g() == 0;
        this.mLayoutState.h = getExtraLayoutSpace(state);
        this.mLayoutState.f = i;
        int i4 = -1;
        if (i == 1) {
            this.mLayoutState.h += this.mOrientationHelper.f();
            View childClosestToEnd = getChildClosestToEnd();
            LayoutState layoutState = this.mLayoutState;
            if (!this.mShouldReverseLayout) {
                i4 = 1;
            }
            layoutState.e = i4;
            this.mLayoutState.d = getPosition(childClosestToEnd) + this.mLayoutState.e;
            this.mLayoutState.b = this.mOrientationHelper.b(childClosestToEnd);
            i3 = this.mOrientationHelper.b(childClosestToEnd) - this.mOrientationHelper.c();
        } else {
            View childClosestToStart = getChildClosestToStart();
            this.mLayoutState.h += this.mOrientationHelper.b();
            LayoutState layoutState2 = this.mLayoutState;
            if (this.mShouldReverseLayout) {
                i4 = 1;
            }
            layoutState2.e = i4;
            this.mLayoutState.d = getPosition(childClosestToStart) + this.mLayoutState.e;
            this.mLayoutState.b = this.mOrientationHelper.a(childClosestToStart);
            i3 = (-this.mOrientationHelper.a(childClosestToStart)) + this.mOrientationHelper.b();
        }
        this.mLayoutState.c = i2;
        if (z) {
            this.mLayoutState.c -= i3;
        }
        this.mLayoutState.g = i3;
    }

    /* access modifiers changed from: 0000 */
    public int scrollBy(int i, Recycler recycler, State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        this.mLayoutState.a = true;
        ensureLayoutState();
        int i2 = i > 0 ? 1 : -1;
        int abs = Math.abs(i);
        updateLayoutState(i2, abs, true, state);
        int fill = this.mLayoutState.g + fill(recycler, this.mLayoutState, state, false);
        if (fill < 0) {
            return 0;
        }
        if (abs > fill) {
            i = i2 * fill;
        }
        this.mOrientationHelper.a(-i);
        this.mLayoutState.j = i;
        return i;
    }

    public void assertNotInLayoutOrScroll(String str) {
        if (this.mPendingSavedState == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    private void recycleChildren(Recycler recycler, int i, int i2) {
        if (i != i2) {
            if (i2 > i) {
                for (int i3 = i2 - 1; i3 >= i; i3--) {
                    removeAndRecycleViewAt(i3, recycler);
                }
                return;
            }
            while (i > i2) {
                removeAndRecycleViewAt(i, recycler);
                i--;
            }
        }
    }

    private void recycleViewsFromStart(Recycler recycler, int i) {
        if (i >= 0) {
            int childCount = getChildCount();
            if (this.mShouldReverseLayout) {
                int i2 = childCount - 1;
                for (int i3 = i2; i3 >= 0; i3--) {
                    if (this.mOrientationHelper.b(getChildAt(i3)) > i) {
                        recycleChildren(recycler, i2, i3);
                        return;
                    }
                }
                return;
            }
            for (int i4 = 0; i4 < childCount; i4++) {
                if (this.mOrientationHelper.b(getChildAt(i4)) > i) {
                    recycleChildren(recycler, 0, i4);
                    return;
                }
            }
        }
    }

    private void recycleViewsFromEnd(Recycler recycler, int i) {
        int childCount = getChildCount();
        if (i >= 0) {
            int d = this.mOrientationHelper.d() - i;
            if (this.mShouldReverseLayout) {
                for (int i2 = 0; i2 < childCount; i2++) {
                    if (this.mOrientationHelper.a(getChildAt(i2)) < d) {
                        recycleChildren(recycler, 0, i2);
                        return;
                    }
                }
                return;
            }
            int i3 = childCount - 1;
            for (int i4 = i3; i4 >= 0; i4--) {
                if (this.mOrientationHelper.a(getChildAt(i4)) < d) {
                    recycleChildren(recycler, i3, i4);
                    return;
                }
            }
        }
    }

    private void recycleByLayoutState(Recycler recycler, LayoutState layoutState) {
        if (layoutState.a && !layoutState.l) {
            if (layoutState.f == -1) {
                recycleViewsFromEnd(recycler, layoutState.g);
            } else {
                recycleViewsFromStart(recycler, layoutState.g);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public int fill(Recycler recycler, LayoutState layoutState, State state, boolean z) {
        int i = layoutState.c;
        if (layoutState.g != Integer.MIN_VALUE) {
            if (layoutState.c < 0) {
                layoutState.g += layoutState.c;
            }
            recycleByLayoutState(recycler, layoutState);
        }
        int i2 = layoutState.c + layoutState.h;
        LayoutChunkResult layoutChunkResult = new LayoutChunkResult();
        while (true) {
            if ((!layoutState.l && i2 <= 0) || !layoutState.a(state)) {
                break;
            }
            layoutChunkResult.a = 0;
            layoutChunkResult.b = false;
            layoutChunkResult.c = false;
            layoutChunkResult.d = false;
            layoutChunk(recycler, state, layoutState, layoutChunkResult);
            if (layoutChunkResult.b) {
                break;
            }
            layoutState.b += layoutChunkResult.a * layoutState.f;
            if (!layoutChunkResult.c || this.mLayoutState.k != null || !state.c) {
                layoutState.c -= layoutChunkResult.a;
                i2 -= layoutChunkResult.a;
            }
            if (layoutState.g != Integer.MIN_VALUE) {
                layoutState.g += layoutChunkResult.a;
                if (layoutState.c < 0) {
                    layoutState.g += layoutState.c;
                }
                recycleByLayoutState(recycler, layoutState);
            }
            if (z && layoutChunkResult.d) {
                break;
            }
        }
        return i - layoutState.c;
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
        View a = layoutState.a(recycler);
        if (a == null) {
            layoutChunkResult.b = true;
            return;
        }
        LayoutParams layoutParams = (LayoutParams) a.getLayoutParams();
        if (layoutState.k == null) {
            if (this.mShouldReverseLayout == (layoutState.f == -1)) {
                addView(a);
            } else {
                addView(a, 0);
            }
        } else {
            if (this.mShouldReverseLayout == (layoutState.f == -1)) {
                addDisappearingView(a);
            } else {
                addDisappearingView(a, 0);
            }
        }
        measureChildWithMargins(a, 0, 0);
        layoutChunkResult.a = this.mOrientationHelper.c(a);
        if (this.mOrientation == 1) {
            if (isLayoutRTL()) {
                i7 = getWidth() - getPaddingRight();
                i2 = i7 - this.mOrientationHelper.d(a);
            } else {
                i2 = getPaddingLeft();
                i7 = this.mOrientationHelper.d(a) + i2;
            }
            if (layoutState.f == -1) {
                i3 = layoutState.b;
                int i8 = i7;
                i4 = layoutState.b - layoutChunkResult.a;
                i = i8;
            } else {
                int i9 = layoutState.b;
                i3 = layoutState.b + layoutChunkResult.a;
                i = i7;
                i4 = i9;
            }
        } else {
            i4 = getPaddingTop();
            int d = this.mOrientationHelper.d(a) + i4;
            if (layoutState.f == -1) {
                i5 = d;
                i6 = layoutState.b - layoutChunkResult.a;
                i = layoutState.b;
            } else {
                int i10 = layoutState.b;
                i = layoutState.b + layoutChunkResult.a;
                i5 = d;
                i6 = i10;
            }
            i3 = i5;
        }
        layoutDecorated(a, i2 + layoutParams.leftMargin, layoutParams.topMargin + i4, i - layoutParams.rightMargin, i3 - layoutParams.bottomMargin);
        if (layoutParams.c.isRemoved() || layoutParams.c.isUpdated()) {
            layoutChunkResult.c = true;
        }
        layoutChunkResult.d = a.isFocusable();
    }

    /* access modifiers changed from: 0000 */
    public boolean shouldMeasureTwice() {
        return (getHeightMode() == 1073741824 || getWidthMode() == 1073741824 || !hasFlexibleChildInBothOrientations()) ? false : true;
    }

    /* access modifiers changed from: 0000 */
    public int convertFocusDirectionToLayoutDirection(int i) {
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

    private View getChildClosestToStart() {
        return getChildAt(this.mShouldReverseLayout ? getChildCount() - 1 : 0);
    }

    private View getChildClosestToEnd() {
        return getChildAt(this.mShouldReverseLayout ? 0 : getChildCount() - 1);
    }

    private View findFirstVisibleChildClosestToStart(boolean z, boolean z2) {
        if (this.mShouldReverseLayout) {
            return findOneVisibleChild(getChildCount() - 1, -1, z, z2);
        }
        return findOneVisibleChild(0, getChildCount(), z, z2);
    }

    private View findFirstVisibleChildClosestToEnd(boolean z, boolean z2) {
        if (this.mShouldReverseLayout) {
            return findOneVisibleChild(0, getChildCount(), z, z2);
        }
        return findOneVisibleChild(getChildCount() - 1, -1, z, z2);
    }

    private View findReferenceChildClosestToEnd(Recycler recycler, State state) {
        return this.mShouldReverseLayout ? findFirstReferenceChild(recycler, state) : findLastReferenceChild(recycler, state);
    }

    private View findReferenceChildClosestToStart(Recycler recycler, State state) {
        return this.mShouldReverseLayout ? findLastReferenceChild(recycler, state) : findFirstReferenceChild(recycler, state);
    }

    private View findFirstReferenceChild(Recycler recycler, State state) {
        return findReferenceChild(recycler, state, 0, getChildCount(), state.a());
    }

    private View findLastReferenceChild(Recycler recycler, State state) {
        return findReferenceChild(recycler, state, getChildCount() - 1, -1, state.a());
    }

    /* access modifiers changed from: 0000 */
    public View findReferenceChild(Recycler recycler, State state, int i, int i2, int i3) {
        ensureLayoutState();
        int b = this.mOrientationHelper.b();
        int c = this.mOrientationHelper.c();
        int i4 = i2 > i ? 1 : -1;
        View view = null;
        View view2 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3) {
                if (((LayoutParams) childAt.getLayoutParams()).c.isRemoved()) {
                    if (view2 == null) {
                        view2 = childAt;
                    }
                } else if (this.mOrientationHelper.a(childAt) < c && this.mOrientationHelper.b(childAt) >= b) {
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

    public int findFirstVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), false, true);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(0, getChildCount(), true, false);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    public int findLastVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, false, true);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View findOneVisibleChild = findOneVisibleChild(getChildCount() - 1, -1, true, false);
        if (findOneVisibleChild == null) {
            return -1;
        }
        return getPosition(findOneVisibleChild);
    }

    /* access modifiers changed from: 0000 */
    public View findOneVisibleChild(int i, int i2, boolean z, boolean z2) {
        ensureLayoutState();
        int b = this.mOrientationHelper.b();
        int c = this.mOrientationHelper.c();
        int i3 = i2 > i ? 1 : -1;
        View view = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int a = this.mOrientationHelper.a(childAt);
            int b2 = this.mOrientationHelper.b(childAt);
            if (a < c && b2 > b) {
                if (!z) {
                    return childAt;
                }
                if (a >= b && b2 <= c) {
                    return childAt;
                }
                if (z2 && view == null) {
                    view = childAt;
                }
            }
            i += i3;
        }
        return view;
    }

    public View onFocusSearchFailed(View view, int i, Recycler recycler, State state) {
        View view2;
        View view3;
        resolveShouldLayoutReverse();
        if (getChildCount() == 0) {
            return null;
        }
        int convertFocusDirectionToLayoutDirection = convertFocusDirectionToLayoutDirection(i);
        if (convertFocusDirectionToLayoutDirection == Integer.MIN_VALUE) {
            return null;
        }
        ensureLayoutState();
        if (convertFocusDirectionToLayoutDirection == -1) {
            view2 = findReferenceChildClosestToStart(recycler, state);
        } else {
            view2 = findReferenceChildClosestToEnd(recycler, state);
        }
        if (view2 == null) {
            return null;
        }
        ensureLayoutState();
        updateLayoutState(convertFocusDirectionToLayoutDirection, (int) (((float) this.mOrientationHelper.e()) * MAX_SCROLL_FACTOR), false, state);
        this.mLayoutState.g = Integer.MIN_VALUE;
        this.mLayoutState.a = false;
        fill(recycler, this.mLayoutState, state, true);
        if (convertFocusDirectionToLayoutDirection == -1) {
            view3 = getChildClosestToStart();
        } else {
            view3 = getChildClosestToEnd();
        }
        if (view3 == view2 || !view3.isFocusable()) {
            return null;
        }
        return view3;
    }

    private void logChildren() {
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            StringBuilder sb = new StringBuilder("item ");
            sb.append(getPosition(childAt));
            sb.append(", coord:");
            sb.append(this.mOrientationHelper.a(childAt));
        }
    }

    /* access modifiers changed from: 0000 */
    public void validateChildOrder() {
        new StringBuilder("validating child count ").append(getChildCount());
        if (getChildCount() > 0) {
            boolean z = false;
            int position = getPosition(getChildAt(0));
            int a = this.mOrientationHelper.a(getChildAt(0));
            if (this.mShouldReverseLayout) {
                int i = 1;
                while (i < getChildCount()) {
                    View childAt = getChildAt(i);
                    int position2 = getPosition(childAt);
                    int a2 = this.mOrientationHelper.a(childAt);
                    if (position2 < position) {
                        logChildren();
                        StringBuilder sb = new StringBuilder("detected invalid position. loc invalid? ");
                        if (a2 < a) {
                            z = true;
                        }
                        sb.append(z);
                        throw new RuntimeException(sb.toString());
                    } else if (a2 > a) {
                        logChildren();
                        throw new RuntimeException("detected invalid location");
                    } else {
                        i++;
                    }
                }
                return;
            }
            int i2 = 1;
            while (i2 < getChildCount()) {
                View childAt2 = getChildAt(i2);
                int position3 = getPosition(childAt2);
                int a3 = this.mOrientationHelper.a(childAt2);
                if (position3 < position) {
                    logChildren();
                    StringBuilder sb2 = new StringBuilder("detected invalid position. loc invalid? ");
                    if (a3 < a) {
                        z = true;
                    }
                    sb2.append(z);
                    throw new RuntimeException(sb2.toString());
                } else if (a3 < a) {
                    logChildren();
                    throw new RuntimeException("detected invalid location");
                } else {
                    i2++;
                }
            }
        }
    }

    public boolean supportsPredictiveItemAnimations() {
        return this.mPendingSavedState == null && this.mLastStackFromEnd == this.mStackFromEnd;
    }

    public void prepareForDrop(View view, View view2, int i, int i2) {
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        ensureLayoutState();
        resolveShouldLayoutReverse();
        int position = getPosition(view);
        int position2 = getPosition(view2);
        char c = position < position2 ? (char) 1 : 65535;
        if (this.mShouldReverseLayout) {
            if (c == 1) {
                scrollToPositionWithOffset(position2, this.mOrientationHelper.c() - (this.mOrientationHelper.a(view2) + this.mOrientationHelper.c(view)));
            } else {
                scrollToPositionWithOffset(position2, this.mOrientationHelper.c() - this.mOrientationHelper.b(view2));
            }
        } else if (c == 65535) {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.a(view2));
        } else {
            scrollToPositionWithOffset(position2, this.mOrientationHelper.b(view2) - this.mOrientationHelper.c(view));
        }
    }

    /* access modifiers changed from: protected */
    public int getExtraLayoutSpace(State state) {
        if (state.a != -1) {
            return this.mOrientationHelper.e();
        }
        return 0;
    }

    private void layoutForPredictiveAnimations(Recycler recycler, State state, int i, int i2) {
        Recycler recycler2 = recycler;
        State state2 = state;
        if (state2.d && getChildCount() != 0 && !state2.c && supportsPredictiveItemAnimations()) {
            List<ViewHolder> list = recycler2.d;
            int size = list.size();
            int position = getPosition(getChildAt(0));
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                ViewHolder viewHolder = list.get(i5);
                if (!viewHolder.isRemoved()) {
                    char c = 1;
                    if ((viewHolder.getLayoutPosition() < position) != this.mShouldReverseLayout) {
                        c = 65535;
                    }
                    if (c == 65535) {
                        i3 += this.mOrientationHelper.c(viewHolder.itemView);
                    } else {
                        i4 += this.mOrientationHelper.c(viewHolder.itemView);
                    }
                }
            }
            this.mLayoutState.k = list;
            if (i3 > 0) {
                updateLayoutStateToFillStart(getPosition(getChildClosestToStart()), i);
                this.mLayoutState.h = i3;
                this.mLayoutState.c = 0;
                this.mLayoutState.a((View) null);
                fill(recycler2, this.mLayoutState, state2, false);
            }
            if (i4 > 0) {
                updateLayoutStateToFillEnd(getPosition(getChildClosestToEnd()), i2);
                this.mLayoutState.h = i4;
                this.mLayoutState.c = 0;
                this.mLayoutState.a((View) null);
                fill(recycler2, this.mLayoutState, state2, false);
            }
            this.mLayoutState.k = null;
        }
    }

    private boolean updateAnchorFromPendingData(State state, AnchorInfo anchorInfo) {
        boolean z = false;
        if (state.c || this.mPendingScrollPosition == -1) {
            return false;
        }
        if (this.mPendingScrollPosition < 0 || this.mPendingScrollPosition >= state.a()) {
            this.mPendingScrollPosition = -1;
            this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
            return false;
        }
        anchorInfo.a = this.mPendingScrollPosition;
        if (this.mPendingSavedState != null && this.mPendingSavedState.a()) {
            anchorInfo.c = this.mPendingSavedState.c;
            if (anchorInfo.c) {
                anchorInfo.b = this.mOrientationHelper.c() - this.mPendingSavedState.b;
            } else {
                anchorInfo.b = this.mOrientationHelper.b() + this.mPendingSavedState.b;
            }
            return true;
        } else if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
            View findViewByPosition = findViewByPosition(this.mPendingScrollPosition);
            if (findViewByPosition == null) {
                if (getChildCount() > 0) {
                    if ((this.mPendingScrollPosition < getPosition(getChildAt(0))) == this.mShouldReverseLayout) {
                        z = true;
                    }
                    anchorInfo.c = z;
                }
                anchorInfo.a();
            } else if (this.mOrientationHelper.c(findViewByPosition) > this.mOrientationHelper.e()) {
                anchorInfo.a();
                return true;
            } else if (this.mOrientationHelper.a(findViewByPosition) - this.mOrientationHelper.b() < 0) {
                anchorInfo.b = this.mOrientationHelper.b();
                anchorInfo.c = false;
                return true;
            } else if (this.mOrientationHelper.c() - this.mOrientationHelper.b(findViewByPosition) < 0) {
                anchorInfo.b = this.mOrientationHelper.c();
                anchorInfo.c = true;
                return true;
            } else {
                anchorInfo.b = anchorInfo.c ? this.mOrientationHelper.b(findViewByPosition) + this.mOrientationHelper.a() : this.mOrientationHelper.a(findViewByPosition);
            }
            return true;
        } else {
            anchorInfo.c = this.mShouldReverseLayout;
            if (this.mShouldReverseLayout) {
                anchorInfo.b = this.mOrientationHelper.c() - this.mPendingScrollPositionOffset;
            } else {
                anchorInfo.b = this.mOrientationHelper.b() + this.mPendingScrollPositionOffset;
            }
            return true;
        }
    }
}
