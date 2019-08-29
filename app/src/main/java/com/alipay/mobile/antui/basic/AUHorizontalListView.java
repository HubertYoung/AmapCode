package com.alipay.mobile.antui.basic;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.util.LongSparseArray;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.EdgeEffectCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnTouchModeChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.Scroller;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.utils.AuiLogger;
import com.alipay.sdk.util.h;
import com.uc.webview.export.extension.UCCore;

public class AUHorizontalListView extends AdapterView<ListAdapter> implements OnTouchModeChangeListener {
    private static final int CHECK_POSITION_SEARCH_DISTANCE = 20;
    private static final int INVALID_POINTER = -1;
    private static final int LAYOUT_FORCE_BOTTOM = 3;
    private static final int LAYOUT_FORCE_TOP = 1;
    private static final int LAYOUT_MOVE_SELECTION = 6;
    private static final int LAYOUT_NORMAL = 0;
    private static final int LAYOUT_SET_SELECTION = 2;
    private static final int LAYOUT_SPECIFIC = 4;
    private static final int LAYOUT_SYNC = 5;
    private static final String LOGTAG = "APHorizontalListView";
    private static final float MAX_SCROLL_FACTOR = 0.33f;
    private static final int MIN_SCROLL_PREVIEW_PIXELS = 10;
    private static final int NO_POSITION = -1;
    public static final int[] STATE_NOTHING = {0};
    private static final int SYNC_FIRST_POSITION = 1;
    private static final int SYNC_MAX_DURATION_MILLIS = 100;
    private static final int SYNC_SELECTED_POSITION = 0;
    private static final int TOUCH_MODE_DONE_WAITING = 2;
    private static final int TOUCH_MODE_DOWN = 0;
    private static final int TOUCH_MODE_DRAGGING = 3;
    private static final int TOUCH_MODE_FLINGING = 4;
    private static final int TOUCH_MODE_OFF = 1;
    private static final int TOUCH_MODE_ON = 0;
    private static final int TOUCH_MODE_OVERSCROLL = 5;
    private static final int TOUCH_MODE_REST = -1;
    private static final int TOUCH_MODE_TAP = 1;
    private static final int TOUCH_MODE_UNKNOWN = -1;
    private p mAccessibilityDelegate;
    private int mActivePointerId;
    /* access modifiers changed from: private */
    public ListAdapter mAdapter;
    private boolean mAreAllItemsSelectable;
    private final l mArrowScrollFocusResult;
    private boolean mBlockLayoutRequests;
    private SparseBooleanArray mCheckStates;
    LongSparseArray<Integer> mCheckedIdStates;
    private int mCheckedItemCount;
    private ChoiceMode mChoiceMode;
    private ContextMenuInfo mContextMenuInfo;
    /* access modifiers changed from: private */
    public boolean mDataChanged;
    private k mDataSetObserver;
    private boolean mDesiredFocusableInTouchModeState;
    private boolean mDesiredFocusableState;
    private boolean mDrawSelectorOnTop;
    private View mEmptyView;
    private EdgeEffectCompat mEndEdge;
    /* access modifiers changed from: private */
    public int mFirstPosition;
    private final int mFlingVelocity;
    /* access modifiers changed from: private */
    public boolean mHasStableIds;
    private boolean mInLayout;
    private boolean mIsAttached;
    private boolean mIsChildViewEnabled;
    final boolean[] mIsScrap;
    private boolean mIsVertical;
    /* access modifiers changed from: private */
    public int mItemCount;
    private int mItemMargin;
    private boolean mItemsCanFocus;
    private int mLastAccessibilityScrollEventFromIndex;
    private int mLastAccessibilityScrollEventToIndex;
    private int mLastScrollState;
    private int mLastTouchMode;
    private float mLastTouchPos;
    /* access modifiers changed from: private */
    public int mLayoutMode;
    private final int mMaximumVelocity;
    /* access modifiers changed from: private */
    public int mMotionPosition;
    /* access modifiers changed from: private */
    public boolean mNeedSync;
    /* access modifiers changed from: private */
    public int mNextSelectedPosition;
    /* access modifiers changed from: private */
    public long mNextSelectedRowId;
    /* access modifiers changed from: private */
    public int mOldItemCount;
    private int mOldSelectedPosition;
    private long mOldSelectedRowId;
    private OnScrollListener mOnScrollListener;
    private int mOverScroll;
    private final int mOverscrollDistance;
    private m mPendingCheckForKeyLongPress;
    private n mPendingCheckForLongPress;
    private o mPendingCheckForTap;
    private SavedState mPendingSync;
    private q mPerformClick;
    private final r mRecycler;
    private int mResurrectToPosition;
    private final Scroller mScroller;
    /* access modifiers changed from: private */
    public int mSelectedPosition;
    /* access modifiers changed from: private */
    public long mSelectedRowId;
    private int mSelectedStart;
    private t mSelectionNotifier;
    /* access modifiers changed from: private */
    public Drawable mSelector;
    private int mSelectorPosition;
    private final Rect mSelectorRect;
    private int mSpecificStart;
    private EdgeEffectCompat mStartEdge;
    private long mSyncHeight;
    private int mSyncMode;
    private int mSyncPosition;
    private long mSyncRowId;
    private final Rect mTempRect;
    private Rect mTouchFrame;
    /* access modifiers changed from: private */
    public int mTouchMode;
    /* access modifiers changed from: private */
    public Runnable mTouchModeReset;
    private float mTouchRemainderPos;
    private final int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public enum ChoiceMode {
        NONE,
        SINGLE,
        MULTIPLE
    }

    public class LayoutParams extends android.view.ViewGroup.LayoutParams {
        boolean forceAdd;
        long id = -1;
        int scrappedFromPosition;
        int viewType;

        public LayoutParams(int width, int height) {
            super(width, height);
            if (this.width == -1) {
                Log.w(AUHorizontalListView.LOGTAG, "Constructing LayoutParams with width FILL_PARENT does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -2;
            }
            if (this.height == -1) {
                Log.w(AUHorizontalListView.LOGTAG, "Constructing LayoutParams with height FILL_PARENT does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            if (this.width == -1) {
                Log.w(AUHorizontalListView.LOGTAG, "Inflation setting LayoutParams width to MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -1;
            }
            if (this.height == -1) {
                Log.w(AUHorizontalListView.LOGTAG, "Inflation setting LayoutParams height to MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams other) {
            super(other);
            if (this.width == -1) {
                Log.w(AUHorizontalListView.LOGTAG, "Constructing LayoutParams with width MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.width = -2;
            }
            if (this.height == -1) {
                Log.w(AUHorizontalListView.LOGTAG, "Constructing LayoutParams with height MATCH_PARENT - does not make much sense as the view might change orientation. Falling back to WRAP_CONTENT");
                this.height = -2;
            }
        }
    }

    public interface OnScrollListener {
        public static final int SCROLL_STATE_FLING = 2;
        public static final int SCROLL_STATE_IDLE = 0;
        public static final int SCROLL_STATE_TOUCH_SCROLL = 1;

        void onScroll(AUHorizontalListView aUHorizontalListView, int i, int i2, int i3);

        void onScrollStateChanged(AUHorizontalListView aUHorizontalListView, int i);
    }

    public interface RecyclerListener {
        void onMovedToScrapHeap(View view);
    }

    class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new s();
        long a;
        long b;
        int c;
        int d;
        int e;
        int f;
        SparseBooleanArray g;
        LongSparseArray<Integer> h;

        /* synthetic */ SavedState(Parcel x0, byte b2) {
            this(x0);
        }

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            this.a = in.readLong();
            this.b = in.readLong();
            this.c = in.readInt();
            this.d = in.readInt();
            this.e = in.readInt();
            this.f = in.readInt();
            this.g = in.readSparseBooleanArray();
            int N = in.readInt();
            if (N > 0) {
                this.h = new LongSparseArray<>();
                for (int i = 0; i < N; i++) {
                    this.h.put(in.readLong(), Integer.valueOf(in.readInt()));
                }
            }
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.a);
            out.writeLong(this.b);
            out.writeInt(this.c);
            out.writeInt(this.d);
            out.writeInt(this.e);
            out.writeInt(this.f);
            out.writeSparseBooleanArray(this.g);
            int N = this.h != null ? this.h.size() : 0;
            out.writeInt(N);
            for (int i = 0; i < N; i++) {
                out.writeLong(this.h.keyAt(i));
                out.writeInt(((Integer) this.h.valueAt(i)).intValue());
            }
        }

        public String toString() {
            return "TwoWayView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.a + " firstId=" + this.b + " viewStart=" + this.c + " height=" + this.e + " position=" + this.d + " checkState=" + this.g + h.d;
        }
    }

    public AUHorizontalListView(Context context) {
        this(context, null);
    }

    public AUHorizontalListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUHorizontalListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsScrap = new boolean[1];
        this.mNeedSync = false;
        this.mVelocityTracker = null;
        this.mLayoutMode = 0;
        this.mTouchMode = -1;
        this.mLastTouchMode = -1;
        this.mIsAttached = false;
        this.mContextMenuInfo = null;
        this.mOnScrollListener = null;
        this.mLastScrollState = 0;
        ViewConfiguration vc = ViewConfiguration.get(context);
        this.mTouchSlop = vc.getScaledTouchSlop();
        this.mMaximumVelocity = vc.getScaledMaximumFlingVelocity();
        this.mFlingVelocity = vc.getScaledMinimumFlingVelocity();
        this.mOverscrollDistance = getScaledOverscrollDistance(vc);
        this.mOverScroll = 0;
        this.mScroller = new Scroller(context);
        this.mIsVertical = true;
        this.mItemsCanFocus = false;
        this.mTempRect = new Rect();
        this.mArrowScrollFocusResult = new l(0);
        this.mSelectorPosition = -1;
        this.mSelectorRect = new Rect();
        this.mSelectedStart = 0;
        this.mResurrectToPosition = -1;
        this.mSelectedStart = 0;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mChoiceMode = ChoiceMode.NONE;
        this.mCheckedItemCount = 0;
        this.mCheckedIdStates = null;
        this.mCheckStates = null;
        this.mRecycler = new r(this);
        this.mDataSetObserver = null;
        this.mAreAllItemsSelectable = true;
        this.mStartEdge = null;
        this.mEndEdge = null;
        setClickable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setAlwaysDrawnWithCacheEnabled(false);
        setWillNotDraw(false);
        setClipToPadding(false);
        ViewCompat.setOverScrollMode(this, 1);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AUHorizontalListView, defStyle, 0);
        this.mDrawSelectorOnTop = a.getBoolean(1, false);
        Drawable d = a.getDrawable(2);
        if (d != null) {
            setSelector(d);
        }
        initOrientation();
        int choiceMode = a.getInt(0, -1);
        if (choiceMode >= 0) {
            setChoiceMode(ChoiceMode.values()[choiceMode]);
        }
        a.recycle();
        updateScrollbarsDirection();
    }

    private void initOrientation() {
        this.mIsVertical = false;
        updateScrollbarsDirection();
        resetState();
        this.mRecycler.b();
        requestLayout();
    }

    public void setItemMargin(int itemMargin) {
        if (this.mItemMargin != itemMargin) {
            this.mItemMargin = itemMargin;
            requestLayout();
        }
    }

    public int getItemMargin() {
        return this.mItemMargin;
    }

    public void setItemsCanFocus(boolean itemsCanFocus) {
        this.mItemsCanFocus = itemsCanFocus;
        if (!itemsCanFocus) {
            setDescendantFocusability(393216);
        }
    }

    public boolean getItemsCanFocus() {
        return this.mItemsCanFocus;
    }

    public void setOnScrollListener(OnScrollListener l) {
        this.mOnScrollListener = l;
        invokeOnItemScrollListener();
    }

    public void setRecyclerListener(RecyclerListener l) {
        this.mRecycler.b = l;
    }

    public void setDrawSelectorOnTop(boolean drawSelectorOnTop) {
        this.mDrawSelectorOnTop = drawSelectorOnTop;
    }

    public void setSelector(int resID) {
        setSelector(getResources().getDrawable(resID));
    }

    public void setSelector(Drawable selector) {
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = selector;
        selector.getPadding(new Rect());
        selector.setCallback(this);
        updateSelectorState();
    }

    public Drawable getSelector() {
        return this.mSelector;
    }

    public int getSelectedItemPosition() {
        return this.mNextSelectedPosition;
    }

    public long getSelectedItemId() {
        return this.mNextSelectedRowId;
    }

    public int getCheckedItemCount() {
        return this.mCheckedItemCount;
    }

    public boolean isItemChecked(int position) {
        if (this.mChoiceMode.compareTo(ChoiceMode.NONE) != 0 || this.mCheckStates == null) {
            return false;
        }
        return this.mCheckStates.get(position);
    }

    public int getCheckedItemPosition() {
        if (this.mChoiceMode.compareTo(ChoiceMode.SINGLE) == 0 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
            return this.mCheckStates.keyAt(0);
        }
        return -1;
    }

    public SparseBooleanArray getCheckedItemPositions() {
        if (this.mChoiceMode.compareTo(ChoiceMode.NONE) != 0) {
            return this.mCheckStates;
        }
        return null;
    }

    public long[] getCheckedItemIds() {
        if (this.mChoiceMode.compareTo(ChoiceMode.NONE) == 0 || this.mCheckedIdStates == null || this.mAdapter == null) {
            return new long[0];
        }
        LongSparseArray idStates = this.mCheckedIdStates;
        int count = idStates.size();
        long[] ids = new long[count];
        for (int i = 0; i < count; i++) {
            ids[i] = idStates.keyAt(i);
        }
        return ids;
    }

    public void setItemChecked(int position, boolean value) {
        boolean updateIds;
        if (this.mChoiceMode.compareTo(ChoiceMode.NONE) != 0) {
            if (this.mChoiceMode.compareTo(ChoiceMode.MULTIPLE) == 0) {
                boolean oldValue = this.mCheckStates.get(position);
                this.mCheckStates.put(position, value);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    if (value) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    } else {
                        this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                    }
                }
                if (oldValue != value) {
                    if (value) {
                        this.mCheckedItemCount++;
                    } else {
                        this.mCheckedItemCount--;
                    }
                }
            } else {
                if (this.mCheckedIdStates == null || !this.mAdapter.hasStableIds()) {
                    updateIds = false;
                } else {
                    updateIds = true;
                }
                if (value || isItemChecked(position)) {
                    this.mCheckStates.clear();
                    if (updateIds) {
                        this.mCheckedIdStates.clear();
                    }
                }
                if (value) {
                    this.mCheckStates.put(position, true);
                    if (updateIds) {
                        this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                    }
                    this.mCheckedItemCount = 1;
                } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                    this.mCheckedItemCount = 0;
                }
            }
            if (!this.mInLayout && !this.mBlockLayoutRequests) {
                this.mDataChanged = true;
                rememberSyncState();
                requestLayout();
            }
        }
    }

    public void clearChoices() {
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        this.mCheckedItemCount = 0;
    }

    public ChoiceMode getChoiceMode() {
        return this.mChoiceMode;
    }

    public void setChoiceMode(ChoiceMode choiceMode) {
        this.mChoiceMode = choiceMode;
        if (this.mChoiceMode.compareTo(ChoiceMode.NONE) != 0) {
            if (this.mCheckStates == null) {
                this.mCheckStates = new SparseBooleanArray();
            }
            if (this.mCheckedIdStates == null && this.mAdapter != null && this.mAdapter.hasStableIds()) {
                this.mCheckedIdStates = new LongSparseArray<>();
            }
        }
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (!(this.mAdapter == null || this.mDataSetObserver == null)) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
        }
        resetState();
        this.mRecycler.b();
        this.mAdapter = adapter;
        this.mDataChanged = true;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        if (this.mCheckStates != null) {
            this.mCheckStates.clear();
        }
        if (this.mCheckedIdStates != null) {
            this.mCheckedIdStates.clear();
        }
        if (this.mAdapter != null) {
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = adapter.getCount();
            this.mDataSetObserver = new k(this, 0);
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mRecycler.a(adapter.getViewTypeCount());
            this.mHasStableIds = adapter.hasStableIds();
            this.mAreAllItemsSelectable = adapter.areAllItemsEnabled();
            if (this.mChoiceMode.compareTo(ChoiceMode.NONE) != 0 && this.mHasStableIds && this.mCheckedIdStates == null) {
                this.mCheckedIdStates = new LongSparseArray<>();
            }
            int position = lookForSelectablePosition(0);
            setSelectedPositionInt(position);
            setNextSelectedPositionInt(position);
            if (this.mItemCount == 0) {
                checkSelectionChanged();
            }
        } else {
            this.mItemCount = 0;
            this.mHasStableIds = false;
            this.mAreAllItemsSelectable = true;
            checkSelectionChanged();
        }
        checkFocus();
        requestLayout();
    }

    public int getFirstVisiblePosition() {
        return this.mFirstPosition;
    }

    public int getLastVisiblePosition() {
        return (this.mFirstPosition + getChildCount()) - 1;
    }

    public int getCount() {
        return this.mItemCount;
    }

    public int getPositionForView(View view) {
        View child = view;
        while (true) {
            try {
                View v = (View) child.getParent();
                if (v.equals(this)) {
                    break;
                }
                child = v;
            } catch (ClassCastException e) {
                return -1;
            }
        }
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (getChildAt(i).equals(child)) {
                return this.mFirstPosition + i;
            }
        }
        return -1;
    }

    public void getFocusedRect(Rect r) {
        View view = getSelectedView();
        if (view == null || view.getParent() != this) {
            super.getFocusedRect(r);
            return;
        }
        view.getFocusedRect(r);
        offsetDescendantRectToMyCoords(view, r);
    }

    /* access modifiers changed from: protected */
    public void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        if (gainFocus && this.mSelectedPosition < 0 && !isInTouchMode()) {
            if (!this.mIsAttached && this.mAdapter != null) {
                this.mDataChanged = true;
                this.mOldItemCount = this.mItemCount;
                this.mItemCount = this.mAdapter.getCount();
            }
            resurrectSelection();
        }
        ListAdapter adapter = this.mAdapter;
        int closetChildIndex = -1;
        int closestChildStart = 0;
        if (!(adapter == null || !gainFocus || previouslyFocusedRect == null)) {
            previouslyFocusedRect.offset(getScrollX(), getScrollY());
            if (adapter.getCount() < getChildCount() + this.mFirstPosition) {
                this.mLayoutMode = 0;
                layoutChildren();
            }
            Rect otherRect = this.mTempRect;
            int minDistance = Integer.MAX_VALUE;
            int childCount = getChildCount();
            int firstPosition = this.mFirstPosition;
            for (int i = 0; i < childCount; i++) {
                if (adapter.isEnabled(firstPosition + i)) {
                    View other = getChildAt(i);
                    other.getDrawingRect(otherRect);
                    offsetDescendantRectToMyCoords(other, otherRect);
                    int distance = getDistance(previouslyFocusedRect, otherRect, direction);
                    if (distance < minDistance) {
                        minDistance = distance;
                        closetChildIndex = i;
                        if (this.mIsVertical) {
                            closestChildStart = other.getTop();
                        } else {
                            closestChildStart = other.getLeft();
                        }
                    }
                }
            }
        }
        if (closetChildIndex >= 0) {
            setSelectionFromOffset(this.mFirstPosition + closetChildIndex, closestChildStart);
        } else {
            requestLayout();
        }
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnTouchModeChangeListener(this);
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new k(this, 0);
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        this.mIsAttached = true;
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycler.b();
        getViewTreeObserver().removeOnTouchModeChangeListener(this);
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
        if (this.mPerformClick != null) {
            removeCallbacks(this.mPerformClick);
        }
        if (this.mTouchModeReset != null) {
            removeCallbacks(this.mTouchModeReset);
            this.mTouchModeReset.run();
        }
        this.mIsAttached = false;
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        int touchMode;
        super.onWindowFocusChanged(hasWindowFocus);
        if (isInTouchMode()) {
            touchMode = 0;
        } else {
            touchMode = 1;
        }
        if (!hasWindowFocus) {
            if (touchMode == 1) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
        } else if (!(touchMode == this.mLastTouchMode || this.mLastTouchMode == -1)) {
            if (touchMode == 1) {
                resurrectSelection();
            } else {
                hideSelector();
                this.mLayoutMode = 0;
                layoutChildren();
            }
        }
        this.mLastTouchMode = touchMode;
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        boolean needsInvalidate = false;
        if (this.mIsVertical && this.mOverScroll != scrollY) {
            onScrollChanged(getScrollX(), scrollY, getScrollX(), this.mOverScroll);
            this.mOverScroll = scrollY;
            needsInvalidate = true;
        } else if (!this.mIsVertical && this.mOverScroll != scrollX) {
            onScrollChanged(scrollX, getScrollY(), this.mOverScroll, getScrollY());
            this.mOverScroll = scrollX;
            needsInvalidate = true;
        }
        if (needsInvalidate) {
            invalidate();
            awakenScrollbarsInternal();
        }
    }

    @TargetApi(9)
    private boolean overScrollByInternal(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (VERSION.SDK_INT < 9) {
            return false;
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @TargetApi(9)
    public void setOverScrollMode(int mode) {
        if (VERSION.SDK_INT >= 9) {
            if (mode == 2) {
                this.mStartEdge = null;
                this.mEndEdge = null;
            } else if (this.mStartEdge == null) {
                Context context = getContext();
                this.mStartEdge = new EdgeEffectCompat(context);
                this.mEndEdge = new EdgeEffectCompat(context);
            }
            super.setOverScrollMode(mode);
        }
    }

    public int pointToPosition(int x, int y) {
        Rect frame = this.mTouchFrame;
        if (frame == null) {
            this.mTouchFrame = new Rect();
            frame = this.mTouchFrame;
        }
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            if (child.getVisibility() == 0) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollExtent() {
        int count = getChildCount();
        if (count == 0) {
            return 0;
        }
        int extent = count * 100;
        View child = getChildAt(0);
        int childTop = child.getTop();
        int childHeight = child.getHeight();
        if (childHeight > 0) {
            extent += (childTop * 100) / childHeight;
        }
        View child2 = getChildAt(count - 1);
        int childBottom = child2.getBottom();
        int childHeight2 = child2.getHeight();
        if (childHeight2 > 0) {
            return extent - (((childBottom - getHeight()) * 100) / childHeight2);
        }
        return extent;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollExtent() {
        int count = getChildCount();
        if (count == 0) {
            return 0;
        }
        int extent = count * 100;
        View child = getChildAt(0);
        int childLeft = child.getLeft();
        int childWidth = child.getWidth();
        if (childWidth > 0) {
            extent += (childLeft * 100) / childWidth;
        }
        View child2 = getChildAt(count - 1);
        int childRight = child2.getRight();
        int childWidth2 = child2.getWidth();
        if (childWidth2 > 0) {
            return extent - (((childRight - getWidth()) * 100) / childWidth2);
        }
        return extent;
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollOffset() {
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        if (firstPosition < 0 || childCount == 0) {
            return 0;
        }
        View child = getChildAt(0);
        int childTop = child.getTop();
        int childHeight = child.getHeight();
        if (childHeight > 0) {
            return Math.max((firstPosition * 100) - ((childTop * 100) / childHeight), 0);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollOffset() {
        int firstPosition = this.mFirstPosition;
        int childCount = getChildCount();
        if (firstPosition < 0 || childCount == 0) {
            return 0;
        }
        View child = getChildAt(0);
        int childLeft = child.getLeft();
        int childWidth = child.getWidth();
        if (childWidth > 0) {
            return Math.max((firstPosition * 100) - ((childLeft * 100) / childWidth), 0);
        }
        return 0;
    }

    /* access modifiers changed from: protected */
    public int computeVerticalScrollRange() {
        int result = Math.max(this.mItemCount * 100, 0);
        if (!this.mIsVertical || this.mOverScroll == 0) {
            return result;
        }
        return result + Math.abs((int) ((((float) this.mOverScroll) / ((float) getHeight())) * ((float) this.mItemCount) * 100.0f));
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollRange() {
        int result = Math.max(this.mItemCount * 100, 0);
        if (this.mIsVertical || this.mOverScroll == 0) {
            return result;
        }
        return result + Math.abs((int) ((((float) this.mOverScroll) / ((float) getWidth())) * ((float) this.mItemCount) * 100.0f));
    }

    public boolean showContextMenuForChild(View originalView) {
        int longPressPosition = getPositionForView(originalView);
        if (longPressPosition < 0) {
            return false;
        }
        long longPressId = this.mAdapter.getItemId(longPressPosition);
        boolean handled = false;
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            handled = listener.onItemLongClick(this, originalView, longPressPosition, longPressId);
        }
        if (handled) {
            return handled;
        }
        this.mContextMenuInfo = createContextMenuInfo(getChildAt(longPressPosition - this.mFirstPosition), longPressPosition, longPressId);
        return super.showContextMenuForChild(originalView);
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float pos;
        if (!this.mIsAttached) {
            return false;
        }
        switch (ev.getAction() & 255) {
            case 0:
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(ev);
                this.mScroller.abortAnimation();
                float x = ev.getX();
                float y = ev.getY();
                if (!this.mIsVertical) {
                    y = x;
                }
                this.mLastTouchPos = y;
                int motionPosition = findMotionRowOrColumn((int) this.mLastTouchPos);
                this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                this.mTouchRemainderPos = 0.0f;
                if (this.mTouchMode == 4) {
                    return true;
                }
                if (motionPosition < 0) {
                    return false;
                }
                this.mMotionPosition = motionPosition;
                this.mTouchMode = 0;
                return false;
            case 1:
            case 3:
                this.mActivePointerId = -1;
                this.mTouchMode = -1;
                recycleVelocityTracker();
                reportScrollStateChange(0);
                return false;
            case 2:
                if (this.mTouchMode != 0) {
                    return false;
                }
                initVelocityTrackerIfNotExists();
                this.mVelocityTracker.addMovement(ev);
                int index = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                if (index < 0) {
                    Log.e(LOGTAG, "onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did TwoWayView receive an inconsistent event stream?");
                    return false;
                }
                if (this.mIsVertical) {
                    pos = MotionEventCompat.getY(ev, index);
                } else {
                    pos = MotionEventCompat.getX(ev, index);
                }
                float diff = (pos - this.mLastTouchPos) + this.mTouchRemainderPos;
                int delta = (int) diff;
                this.mTouchRemainderPos = diff - ((float) delta);
                if (maybeStartScrolling(delta)) {
                    return true;
                }
                return false;
            default:
                return false;
        }
    }

    public boolean onTouchEvent(MotionEvent ev) {
        float velocity;
        float f;
        int i;
        int i2;
        int i3;
        float pos;
        float f2;
        if (!isEnabled()) {
            if (isClickable() || isLongClickable()) {
                return true;
            }
            return false;
        } else if (!this.mIsAttached) {
            return false;
        } else {
            boolean needsInvalidate = false;
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(ev);
            switch (ev.getAction() & 255) {
                case 0:
                    if (!this.mDataChanged) {
                        this.mVelocityTracker.clear();
                        this.mScroller.abortAnimation();
                        float x = ev.getX();
                        float y = ev.getY();
                        if (this.mIsVertical) {
                            f2 = y;
                        } else {
                            f2 = x;
                        }
                        this.mLastTouchPos = f2;
                        int motionPosition = pointToPosition((int) x, (int) y);
                        this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
                        this.mTouchRemainderPos = 0.0f;
                        if (!this.mDataChanged) {
                            if (this.mTouchMode != 4) {
                                if (this.mMotionPosition >= 0 && this.mAdapter.isEnabled(this.mMotionPosition)) {
                                    this.mTouchMode = 0;
                                    triggerCheckForTap();
                                }
                                this.mMotionPosition = motionPosition;
                                break;
                            } else {
                                this.mTouchMode = 3;
                                reportScrollStateChange(1);
                                findMotionRowOrColumn((int) this.mLastTouchPos);
                                return true;
                            }
                        }
                    }
                    break;
                case 1:
                    switch (this.mTouchMode) {
                        case 0:
                        case 1:
                        case 2:
                            int motionPosition2 = this.mMotionPosition;
                            View child = getChildAt(motionPosition2 - this.mFirstPosition);
                            float x2 = ev.getX();
                            float y2 = ev.getY();
                            boolean inList = this.mIsVertical ? x2 > ((float) getPaddingLeft()) && x2 < ((float) (getWidth() - getPaddingRight())) : y2 > ((float) getPaddingTop()) && y2 < ((float) (getHeight() - getPaddingBottom()));
                            if (child != null && !child.hasFocusable() && inList) {
                                if (this.mTouchMode != 0) {
                                    child.setPressed(false);
                                }
                                if (this.mPerformClick == null) {
                                    this.mPerformClick = new q(this, 0);
                                }
                                q performClick = this.mPerformClick;
                                performClick.a = motionPosition2;
                                performClick.a();
                                this.mResurrectToPosition = motionPosition2;
                                if (this.mTouchMode == 0 || this.mTouchMode == 1) {
                                    if (this.mTouchMode == 0) {
                                        cancelCheckForTap();
                                    } else {
                                        cancelCheckForLongPress();
                                    }
                                    this.mLayoutMode = 0;
                                    if (this.mDataChanged || !this.mAdapter.isEnabled(motionPosition2)) {
                                        this.mTouchMode = -1;
                                        updateSelectorState();
                                    } else {
                                        this.mTouchMode = 1;
                                        setPressed(true);
                                        positionSelector(this.mMotionPosition, child);
                                        child.setPressed(true);
                                        if (this.mSelector != null) {
                                            Drawable d = this.mSelector.getCurrent();
                                            if (d != null && (d instanceof TransitionDrawable)) {
                                                ((TransitionDrawable) d).resetTransition();
                                            }
                                        }
                                        if (this.mTouchModeReset != null) {
                                            removeCallbacks(this.mTouchModeReset);
                                        }
                                        this.mTouchModeReset = new j(this, child, performClick);
                                        postDelayed(this.mTouchModeReset, (long) ViewConfiguration.getPressedStateDuration());
                                    }
                                } else if (!this.mDataChanged && this.mAdapter.isEnabled(motionPosition2)) {
                                    performClick.run();
                                }
                            }
                            this.mTouchMode = -1;
                            updateSelectorState();
                            break;
                        case 3:
                            if (!contentFits()) {
                                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                                if (this.mIsVertical) {
                                    velocity = VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId);
                                } else {
                                    velocity = VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId);
                                }
                                if (Math.abs(velocity) < ((float) this.mFlingVelocity)) {
                                    this.mTouchMode = -1;
                                    reportScrollStateChange(0);
                                    break;
                                } else {
                                    this.mTouchMode = 4;
                                    reportScrollStateChange(2);
                                    Scroller scroller = this.mScroller;
                                    if (this.mIsVertical) {
                                        f = 0.0f;
                                    } else {
                                        f = velocity;
                                    }
                                    int i4 = (int) f;
                                    if (!this.mIsVertical) {
                                        velocity = 0.0f;
                                    }
                                    int i5 = (int) velocity;
                                    int i6 = this.mIsVertical ? 0 : Integer.MIN_VALUE;
                                    if (this.mIsVertical) {
                                        i = 0;
                                    } else {
                                        i = Integer.MAX_VALUE;
                                    }
                                    if (this.mIsVertical) {
                                        i2 = Integer.MIN_VALUE;
                                    } else {
                                        i2 = 0;
                                    }
                                    if (this.mIsVertical) {
                                        i3 = Integer.MAX_VALUE;
                                    } else {
                                        i3 = 0;
                                    }
                                    scroller.fling(0, 0, i4, i5, i6, i, i2, i3);
                                    this.mLastTouchPos = 0.0f;
                                    needsInvalidate = true;
                                    break;
                                }
                            }
                        case 5:
                            this.mTouchMode = -1;
                            reportScrollStateChange(0);
                            break;
                    }
                    cancelCheckForTap();
                    cancelCheckForLongPress();
                    setPressed(false);
                    if (!(this.mStartEdge == null || this.mEndEdge == null)) {
                        needsInvalidate |= this.mStartEdge.onRelease() | this.mEndEdge.onRelease();
                    }
                    recycleVelocityTracker();
                    break;
                case 2:
                    int index = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                    if (index >= 0) {
                        if (this.mIsVertical) {
                            pos = MotionEventCompat.getY(ev, index);
                        } else {
                            pos = MotionEventCompat.getX(ev, index);
                        }
                        if (this.mDataChanged) {
                            layoutChildren();
                        }
                        float diff = (pos - this.mLastTouchPos) + this.mTouchRemainderPos;
                        int delta = (int) diff;
                        this.mTouchRemainderPos = diff - ((float) delta);
                        switch (this.mTouchMode) {
                            case 0:
                            case 1:
                            case 2:
                                maybeStartScrolling(delta);
                                break;
                            case 3:
                            case 5:
                                this.mLastTouchPos = pos;
                                maybeScroll(delta);
                                break;
                        }
                    } else {
                        Log.e(LOGTAG, "onInterceptTouchEvent could not find pointer with id " + this.mActivePointerId + " - did TwoWayView receive an inconsistent event stream?");
                        return false;
                    }
                case 3:
                    cancelCheckForTap();
                    this.mTouchMode = -1;
                    reportScrollStateChange(0);
                    setPressed(false);
                    View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
                    if (motionView != null) {
                        motionView.setPressed(false);
                    }
                    if (!(this.mStartEdge == null || this.mEndEdge == null)) {
                        needsInvalidate = this.mStartEdge.onRelease() | this.mEndEdge.onRelease();
                    }
                    recycleVelocityTracker();
                    break;
            }
            if (needsInvalidate) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
            return true;
        }
    }

    public void onTouchModeChanged(boolean isInTouchMode) {
        if (isInTouchMode) {
            hideSelector();
            if (getWidth() > 0 && getHeight() > 0 && getChildCount() > 0) {
                layoutChildren();
            }
            updateSelectorState();
        } else if (this.mTouchMode == 5 && this.mOverScroll != 0) {
            this.mOverScroll = 0;
            finishEdgeGlows();
            invalidate();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return handleKeyEvent(keyCode, 1, event);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return handleKeyEvent(keyCode, repeatCount, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return handleKeyEvent(keyCode, 1, event);
    }

    public void sendAccessibilityEvent(int eventType) {
        if (eventType == 4096) {
            int firstVisiblePosition = getFirstVisiblePosition();
            int lastVisiblePosition = getLastVisiblePosition();
            if (this.mLastAccessibilityScrollEventFromIndex != firstVisiblePosition || this.mLastAccessibilityScrollEventToIndex != lastVisiblePosition) {
                this.mLastAccessibilityScrollEventFromIndex = firstVisiblePosition;
                this.mLastAccessibilityScrollEventToIndex = lastVisiblePosition;
            } else {
                return;
            }
        }
        super.sendAccessibilityEvent(eventType);
    }

    @TargetApi(14)
    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(AUHorizontalListView.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(AUHorizontalListView.class.getName());
        AccessibilityNodeInfoCompat infoCompat = new AccessibilityNodeInfoCompat(info);
        if (isEnabled()) {
            if (getFirstVisiblePosition() > 0) {
                infoCompat.addAction(8192);
            }
            if (getLastVisiblePosition() < getCount() - 1) {
                infoCompat.addAction(4096);
            }
        }
    }

    @TargetApi(16)
    public boolean performAccessibilityAction(int action, Bundle arguments) {
        int viewportSize;
        int viewportSize2;
        if (super.performAccessibilityAction(action, arguments)) {
            return true;
        }
        switch (action) {
            case 4096:
                if (!isEnabled() || getLastVisiblePosition() >= getCount() - 1) {
                    return false;
                }
                if (this.mIsVertical) {
                    viewportSize2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                } else {
                    viewportSize2 = (getWidth() - getPaddingLeft()) - getPaddingRight();
                }
                scrollListItemsBy(viewportSize2);
                return true;
            case 8192:
                if (!isEnabled() || this.mFirstPosition <= 0) {
                    return false;
                }
                if (this.mIsVertical) {
                    viewportSize = (getHeight() - getPaddingTop()) - getPaddingBottom();
                } else {
                    viewportSize = (getWidth() - getPaddingLeft()) - getPaddingRight();
                }
                scrollListItemsBy(-viewportSize);
                return true;
            default:
                return false;
        }
    }

    private boolean isViewAncestorOf(View child, View parent) {
        if (child == parent) {
            return true;
        }
        ViewParent theParent = child.getParent();
        if (!(theParent instanceof ViewGroup) || !isViewAncestorOf((View) theParent, parent)) {
            return false;
        }
        return true;
    }

    private void forceValidFocusDirection(int direction) {
        if (this.mIsVertical && direction != 33 && direction != 130) {
            throw new IllegalArgumentException("Focus direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN} for vertical orientation");
        } else if (!this.mIsVertical && direction != 17 && direction != 66) {
            throw new IllegalArgumentException("Focus direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT} for vertical orientation");
        }
    }

    private void forceValidInnerFocusDirection(int direction) {
        if (this.mIsVertical && direction != 17 && direction != 66) {
            throw new IllegalArgumentException("Direction must be one of {View.FOCUS_LEFT, View.FOCUS_RIGHT} for vertical orientation");
        } else if (!this.mIsVertical && direction != 33 && direction != 130) {
            throw new IllegalArgumentException("direction must be one of {View.FOCUS_UP, View.FOCUS_DOWN} for horizontal orientation");
        }
    }

    /* access modifiers changed from: 0000 */
    public boolean pageScroll(int direction) {
        forceValidFocusDirection(direction);
        boolean forward = false;
        int nextPage = -1;
        if (direction == 33 || direction == 17) {
            nextPage = Math.max(0, (this.mSelectedPosition - getChildCount()) - 1);
        } else if (direction == 130 || direction == 66) {
            nextPage = Math.min(this.mItemCount - 1, (this.mSelectedPosition + getChildCount()) - 1);
            forward = true;
        }
        if (nextPage < 0) {
            return false;
        }
        int position = lookForSelectablePosition(nextPage, forward);
        if (position < 0) {
            return false;
        }
        this.mLayoutMode = 4;
        this.mSpecificStart = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
        if (forward && position > this.mItemCount - getChildCount()) {
            this.mLayoutMode = 3;
        }
        if (!forward && position < getChildCount()) {
            this.mLayoutMode = 1;
        }
        setSelectionInt(position);
        invokeOnItemScrollListener();
        if (!awakenScrollbarsInternal()) {
            invalidate();
        }
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean fullScroll(int direction) {
        forceValidFocusDirection(direction);
        boolean moved = false;
        if (direction == 33 || direction == 17) {
            if (this.mSelectedPosition != 0) {
                int position = lookForSelectablePosition(0, true);
                if (position >= 0) {
                    this.mLayoutMode = 1;
                    setSelectionInt(position);
                    invokeOnItemScrollListener();
                }
                moved = true;
            }
        } else if ((direction == 130 || direction == 66) && this.mSelectedPosition < this.mItemCount - 1) {
            int position2 = lookForSelectablePosition(this.mItemCount - 1, true);
            if (position2 >= 0) {
                this.mLayoutMode = 3;
                setSelectionInt(position2);
                invokeOnItemScrollListener();
            }
            moved = true;
        }
        if (moved && !awakenScrollbarsInternal()) {
            awakenScrollbarsInternal();
            invalidate();
        }
        return moved;
    }

    private boolean handleFocusWithinItem(int direction) {
        forceValidInnerFocusDirection(direction);
        int numChildren = getChildCount();
        if (this.mItemsCanFocus && numChildren > 0 && this.mSelectedPosition != -1) {
            View selectedView = getSelectedView();
            if (selectedView != null && selectedView.hasFocus() && (selectedView instanceof ViewGroup)) {
                View currentFocus = selectedView.findFocus();
                View nextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) selectedView, currentFocus, direction);
                if (nextFocus != null) {
                    currentFocus.getFocusedRect(this.mTempRect);
                    offsetDescendantRectToMyCoords(currentFocus, this.mTempRect);
                    offsetRectIntoDescendantCoords(nextFocus, this.mTempRect);
                    if (nextFocus.requestFocus(direction, this.mTempRect)) {
                        return true;
                    }
                }
                View globalNextFocus = FocusFinder.getInstance().findNextFocus((ViewGroup) getRootView(), currentFocus, direction);
                if (globalNextFocus != null) {
                    return isViewAncestorOf(globalNextFocus, this);
                }
            }
        }
        return false;
    }

    private boolean arrowScroll(int direction) {
        forceValidFocusDirection(direction);
        try {
            this.mInLayout = true;
            boolean handled = arrowScrollImpl(direction);
            if (handled) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(direction));
            }
            return handled;
        } finally {
            this.mInLayout = false;
        }
    }

    private void handleNewSelectionChange(View selectedView, int direction, int newSelectedPosition, boolean newFocusAssigned) {
        int startViewIndex;
        View startView;
        int endViewIndex;
        View endView;
        forceValidFocusDirection(direction);
        if (newSelectedPosition == -1) {
            throw new IllegalArgumentException("newSelectedPosition needs to be valid");
        }
        int selectedIndex = this.mSelectedPosition - this.mFirstPosition;
        int nextSelectedIndex = newSelectedPosition - this.mFirstPosition;
        boolean topSelected = false;
        if (direction == 33 || direction == 17) {
            startViewIndex = nextSelectedIndex;
            endViewIndex = selectedIndex;
            startView = getChildAt(nextSelectedIndex);
            endView = selectedView;
            topSelected = true;
        } else {
            startViewIndex = selectedIndex;
            endViewIndex = nextSelectedIndex;
            startView = selectedView;
            endView = getChildAt(nextSelectedIndex);
        }
        int numChildren = getChildCount();
        if (startView != null) {
            startView.setSelected(!newFocusAssigned && topSelected);
            measureAndAdjustDown(startView, startViewIndex, numChildren);
        }
        if (endView != null) {
            endView.setSelected(!newFocusAssigned && !topSelected);
            measureAndAdjustDown(endView, endViewIndex, numChildren);
        }
    }

    private void measureAndAdjustDown(View child, int childIndex, int numChildren) {
        int oldHeight = child.getHeight();
        measureChild(child);
        if (child.getMeasuredHeight() != oldHeight) {
            relayoutMeasuredChild(child);
            int heightDelta = child.getMeasuredHeight() - oldHeight;
            for (int i = childIndex + 1; i < numChildren; i++) {
                getChildAt(i).offsetTopAndBottom(heightDelta);
            }
        }
    }

    private l arrowScrollFocused(int direction) {
        int searchPoint;
        int x;
        View newFocus;
        int end;
        forceValidFocusDirection(direction);
        View selectedView = getSelectedView();
        if (selectedView == null || !selectedView.hasFocus()) {
            if (direction == 130 || direction == 66) {
                int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
                int selectedStart = selectedView != null ? this.mIsVertical ? selectedView.getTop() : selectedView.getLeft() : start;
                searchPoint = Math.max(selectedStart, start);
            } else {
                if (this.mIsVertical) {
                    end = getHeight() - getPaddingBottom();
                } else {
                    end = getWidth() - getPaddingRight();
                }
                int selectedEnd = selectedView != null ? this.mIsVertical ? selectedView.getBottom() : selectedView.getRight() : end;
                searchPoint = Math.min(selectedEnd, end);
            }
            if (this.mIsVertical) {
                x = 0;
            } else {
                x = searchPoint;
            }
            int y = this.mIsVertical ? searchPoint : 0;
            this.mTempRect.set(x, y, x, y);
            newFocus = FocusFinder.getInstance().findNextFocusFromRect(this, this.mTempRect, direction);
        } else {
            newFocus = FocusFinder.getInstance().findNextFocus(this, selectedView.findFocus(), direction);
        }
        if (newFocus != null) {
            int positionOfNewFocus = positionOfNewFocus(newFocus);
            if (!(this.mSelectedPosition == -1 || positionOfNewFocus == this.mSelectedPosition)) {
                int selectablePosition = lookForSelectablePositionOnScreen(direction);
                boolean movingForward = direction == 130 || direction == 66;
                boolean movingBackward = direction == 33 || direction == 17;
                if (selectablePosition != -1 && ((movingForward && selectablePosition < positionOfNewFocus) || (movingBackward && selectablePosition > positionOfNewFocus))) {
                    return null;
                }
            }
            int focusScroll = amountToScrollToNewFocus(direction, newFocus, positionOfNewFocus);
            int maxScrollAmount = getMaxScrollAmount();
            if (focusScroll < maxScrollAmount) {
                newFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.a(positionOfNewFocus, focusScroll);
                return this.mArrowScrollFocusResult;
            } else if (distanceToView(newFocus) < maxScrollAmount) {
                newFocus.requestFocus(direction);
                this.mArrowScrollFocusResult.a(positionOfNewFocus, maxScrollAmount);
                return this.mArrowScrollFocusResult;
            }
        }
        return null;
    }

    public int getMaxScrollAmount() {
        return (int) (0.33f * ((float) getHeight()));
    }

    private int getArrowScrollPreviewLength() {
        return this.mItemMargin + Math.max(10, this.mIsVertical ? getVerticalFadingEdgeLength() : getHorizontalFadingEdgeLength());
    }

    private int positionOfNewFocus(View newFocus) {
        int numChildren = getChildCount();
        for (int i = 0; i < numChildren; i++) {
            if (isViewAncestorOf(newFocus, getChildAt(i))) {
                return this.mFirstPosition + i;
            }
        }
        throw new IllegalArgumentException("newFocus is not a child of any of the children of the list!");
    }

    private boolean arrowScrollImpl(int direction) {
        boolean needToRedraw;
        boolean z;
        forceValidFocusDirection(direction);
        if (getChildCount() <= 0) {
            return false;
        }
        View selectedView = getSelectedView();
        int selectedPos = this.mSelectedPosition;
        int nextSelectedPosition = lookForSelectablePositionOnScreen(direction);
        int amountToScroll = amountToScroll(direction, nextSelectedPosition);
        l focusResult = this.mItemsCanFocus ? arrowScrollFocused(direction) : null;
        if (focusResult != null) {
            nextSelectedPosition = focusResult.a();
            amountToScroll = focusResult.b();
        }
        if (focusResult != null) {
            needToRedraw = true;
        } else {
            needToRedraw = false;
        }
        if (nextSelectedPosition != -1) {
            if (focusResult != null) {
                z = true;
            } else {
                z = false;
            }
            handleNewSelectionChange(selectedView, direction, nextSelectedPosition, z);
            setSelectedPositionInt(nextSelectedPosition);
            setNextSelectedPositionInt(nextSelectedPosition);
            selectedView = getSelectedView();
            selectedPos = nextSelectedPosition;
            if (this.mItemsCanFocus && focusResult == null) {
                View focused = getFocusedChild();
                if (focused != null) {
                    focused.clearFocus();
                }
            }
            needToRedraw = true;
            checkSelectionChanged();
        }
        if (amountToScroll > 0) {
            if (!(direction == 33 || direction == 17)) {
                amountToScroll = -amountToScroll;
            }
            scrollListItemsBy(amountToScroll);
            needToRedraw = true;
        }
        if (this.mItemsCanFocus && focusResult == null && selectedView != null && selectedView.hasFocus()) {
            View focused2 = selectedView.findFocus();
            if (!isViewAncestorOf(focused2, this) || distanceToView(focused2) > 0) {
                focused2.clearFocus();
            }
        }
        if (nextSelectedPosition == -1 && selectedView != null && !isViewAncestorOf(selectedView, this)) {
            selectedView = null;
            hideSelector();
            this.mResurrectToPosition = -1;
        }
        if (!needToRedraw) {
            return false;
        }
        if (selectedView != null) {
            positionSelector(selectedPos, selectedView);
            this.mSelectedStart = selectedView.getTop();
        }
        if (!awakenScrollbarsInternal()) {
            invalidate();
        }
        invokeOnItemScrollListener();
        return true;
    }

    private int amountToScroll(int direction, int nextSelectedPosition) {
        int end;
        forceValidFocusDirection(direction);
        int numChildren = getChildCount();
        if (direction == 130 || direction == 66) {
            if (this.mIsVertical) {
                end = getHeight() - getPaddingBottom();
            } else {
                end = getWidth() - getPaddingRight();
            }
            int indexToMakeVisible = numChildren - 1;
            if (nextSelectedPosition != -1) {
                indexToMakeVisible = nextSelectedPosition - this.mFirstPosition;
            }
            int positionToMakeVisible = this.mFirstPosition + indexToMakeVisible;
            View viewToMakeVisible = getChildAt(indexToMakeVisible);
            int goalEnd = end;
            if (positionToMakeVisible < this.mItemCount - 1) {
                goalEnd -= getArrowScrollPreviewLength();
            }
            int viewToMakeVisibleStart = this.mIsVertical ? viewToMakeVisible.getTop() : viewToMakeVisible.getLeft();
            int viewToMakeVisibleEnd = this.mIsVertical ? viewToMakeVisible.getBottom() : viewToMakeVisible.getRight();
            if (viewToMakeVisibleEnd <= goalEnd) {
                return 0;
            }
            if (nextSelectedPosition != -1 && goalEnd - viewToMakeVisibleStart >= getMaxScrollAmount()) {
                return 0;
            }
            int amountToScroll = viewToMakeVisibleEnd - goalEnd;
            if (this.mFirstPosition + numChildren == this.mItemCount) {
                View lastChild = getChildAt(numChildren - 1);
                amountToScroll = Math.min(amountToScroll, (this.mIsVertical ? lastChild.getBottom() : lastChild.getRight()) - end);
            }
            return Math.min(amountToScroll, getMaxScrollAmount());
        }
        int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
        int indexToMakeVisible2 = 0;
        if (nextSelectedPosition != -1) {
            indexToMakeVisible2 = nextSelectedPosition - this.mFirstPosition;
        }
        int positionToMakeVisible2 = this.mFirstPosition + indexToMakeVisible2;
        View viewToMakeVisible2 = getChildAt(indexToMakeVisible2);
        int goalStart = start;
        if (positionToMakeVisible2 > 0) {
            goalStart += getArrowScrollPreviewLength();
        }
        int viewToMakeVisibleStart2 = this.mIsVertical ? viewToMakeVisible2.getTop() : viewToMakeVisible2.getLeft();
        int viewToMakeVisibleEnd2 = this.mIsVertical ? viewToMakeVisible2.getBottom() : viewToMakeVisible2.getRight();
        if (viewToMakeVisibleStart2 >= goalStart) {
            return 0;
        }
        if (nextSelectedPosition != -1 && viewToMakeVisibleEnd2 - goalStart >= getMaxScrollAmount()) {
            return 0;
        }
        int amountToScroll2 = goalStart - viewToMakeVisibleStart2;
        if (this.mFirstPosition == 0) {
            View firstChild = getChildAt(0);
            amountToScroll2 = Math.min(amountToScroll2, start - (this.mIsVertical ? firstChild.getTop() : firstChild.getLeft()));
        }
        return Math.min(amountToScroll2, getMaxScrollAmount());
    }

    private int amountToScrollToNewFocus(int direction, View newFocus, int positionOfNewFocus) {
        int end;
        forceValidFocusDirection(direction);
        newFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(newFocus, this.mTempRect);
        if (direction == 33 || direction == 17) {
            int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
            int newFocusStart = this.mIsVertical ? this.mTempRect.top : this.mTempRect.left;
            if (newFocusStart >= start) {
                return 0;
            }
            int amountToScroll = start - newFocusStart;
            return positionOfNewFocus > 0 ? amountToScroll + getArrowScrollPreviewLength() : amountToScroll;
        }
        if (this.mIsVertical) {
            end = getHeight() - getPaddingBottom();
        } else {
            end = getWidth() - getPaddingRight();
        }
        int newFocusEnd = this.mIsVertical ? this.mTempRect.bottom : this.mTempRect.right;
        if (newFocusEnd <= end) {
            return 0;
        }
        int amountToScroll2 = newFocusEnd - end;
        if (positionOfNewFocus < this.mItemCount - 1) {
            return amountToScroll2 + getArrowScrollPreviewLength();
        }
        return amountToScroll2;
    }

    private int distanceToView(View descendant) {
        int end;
        descendant.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(descendant, this.mTempRect);
        int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
        if (this.mIsVertical) {
            end = getHeight() - getPaddingBottom();
        } else {
            end = getWidth() - getPaddingRight();
        }
        int viewStart = this.mIsVertical ? this.mTempRect.top : this.mTempRect.left;
        int viewEnd = this.mIsVertical ? this.mTempRect.bottom : this.mTempRect.right;
        if (viewEnd < start) {
            return start - viewEnd;
        }
        if (viewStart > end) {
            return viewStart - end;
        }
        return 0;
    }

    private boolean handleKeyScroll(KeyEvent event, int count, int direction) {
        if (KeyEventCompat.hasNoModifiers(event)) {
            boolean handled = resurrectSelectionIfNeeded();
            if (handled) {
                return handled;
            }
            while (true) {
                int count2 = count;
                count = count2 - 1;
                if (count2 <= 0 || !arrowScroll(direction)) {
                    return handled;
                }
                handled = true;
            }
        } else if (KeyEventCompat.hasModifiers(event, 2)) {
            return resurrectSelectionIfNeeded() || fullScroll(direction);
        } else {
            return false;
        }
    }

    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean handleKeyEvent(int r11, int r12, android.view.KeyEvent r13) {
        /*
            r10 = this;
            r4 = 66
            r5 = 33
            r6 = 17
            r7 = 0
            r8 = 1
            android.widget.ListAdapter r9 = r10.mAdapter
            if (r9 == 0) goto L_0x0010
            boolean r9 = r10.mIsAttached
            if (r9 != 0) goto L_0x0012
        L_0x0010:
            r8 = r7
        L_0x0011:
            return r8
        L_0x0012:
            boolean r9 = r10.mDataChanged
            if (r9 == 0) goto L_0x0019
            r10.layoutChildren()
        L_0x0019:
            r2 = 0
            int r0 = r13.getAction()
            if (r0 == r8) goto L_0x0023
            switch(r11) {
                case 19: goto L_0x002a;
                case 20: goto L_0x003f;
                case 21: goto L_0x0057;
                case 22: goto L_0x006b;
                case 23: goto L_0x007f;
                case 62: goto L_0x009c;
                case 66: goto L_0x007f;
                case 92: goto L_0x00ca;
                case 93: goto L_0x0105;
                case 122: goto L_0x0140;
                case 123: goto L_0x015d;
                default: goto L_0x0023;
            }
        L_0x0023:
            if (r2 != 0) goto L_0x0011
            switch(r0) {
                case 0: goto L_0x017b;
                case 1: goto L_0x0181;
                case 2: goto L_0x01c2;
                default: goto L_0x0028;
            }
        L_0x0028:
            r8 = r7
            goto L_0x0011
        L_0x002a:
            boolean r4 = r10.mIsVertical
            if (r4 == 0) goto L_0x0034
            boolean r3 = r10.handleKeyScroll(r13, r12, r5)
        L_0x0032:
            r2 = r3
            goto L_0x0023
        L_0x0034:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r4 == 0) goto L_0x0023
            boolean r3 = r10.handleFocusWithinItem(r5)
            goto L_0x0032
        L_0x003f:
            boolean r4 = r10.mIsVertical
            if (r4 == 0) goto L_0x004a
            r4 = 130(0x82, float:1.82E-43)
            boolean r3 = r10.handleKeyScroll(r13, r12, r4)
            goto L_0x0032
        L_0x004a:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r4 == 0) goto L_0x0023
            r4 = 130(0x82, float:1.82E-43)
            boolean r3 = r10.handleFocusWithinItem(r4)
            goto L_0x0032
        L_0x0057:
            boolean r4 = r10.mIsVertical
            if (r4 != 0) goto L_0x0060
            boolean r3 = r10.handleKeyScroll(r13, r12, r6)
            goto L_0x0032
        L_0x0060:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r4 == 0) goto L_0x0023
            boolean r3 = r10.handleFocusWithinItem(r6)
            goto L_0x0032
        L_0x006b:
            boolean r5 = r10.mIsVertical
            if (r5 != 0) goto L_0x0074
            boolean r3 = r10.handleKeyScroll(r13, r12, r4)
            goto L_0x0032
        L_0x0074:
            boolean r5 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r5 == 0) goto L_0x0023
            boolean r3 = r10.handleFocusWithinItem(r4)
            goto L_0x0032
        L_0x007f:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r4 == 0) goto L_0x0023
            boolean r2 = r10.resurrectSelectionIfNeeded()
            if (r2 != 0) goto L_0x0023
            int r4 = r13.getRepeatCount()
            if (r4 != 0) goto L_0x0023
            int r4 = r10.getChildCount()
            if (r4 <= 0) goto L_0x0023
            r10.keyPressed()
            r3 = r8
            goto L_0x0032
        L_0x009c:
            boolean r9 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r9 == 0) goto L_0x00b3
            boolean r5 = r10.resurrectSelectionIfNeeded()
            if (r5 != 0) goto L_0x00b1
            boolean r5 = r10.mIsVertical
            if (r5 == 0) goto L_0x00ae
            r4 = 130(0x82, float:1.82E-43)
        L_0x00ae:
            r10.pageScroll(r4)
        L_0x00b1:
            r3 = r8
            goto L_0x0032
        L_0x00b3:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasModifiers(r13, r8)
            if (r4 == 0) goto L_0x00b1
            boolean r4 = r10.resurrectSelectionIfNeeded()
            if (r4 != 0) goto L_0x00b1
            boolean r4 = r10.mIsVertical
            if (r4 == 0) goto L_0x00c8
            r4 = r5
        L_0x00c4:
            r10.fullScroll(r4)
            goto L_0x00b1
        L_0x00c8:
            r4 = r6
            goto L_0x00c4
        L_0x00ca:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r4 == 0) goto L_0x00e7
            boolean r4 = r10.resurrectSelectionIfNeeded()
            if (r4 != 0) goto L_0x00e0
            boolean r4 = r10.mIsVertical
            if (r4 == 0) goto L_0x00e3
        L_0x00da:
            boolean r4 = r10.pageScroll(r5)
            if (r4 == 0) goto L_0x00e5
        L_0x00e0:
            r2 = r8
        L_0x00e1:
            goto L_0x0023
        L_0x00e3:
            r5 = r6
            goto L_0x00da
        L_0x00e5:
            r2 = r7
            goto L_0x00e1
        L_0x00e7:
            r4 = 2
            boolean r4 = android.support.v4.view.KeyEventCompat.hasModifiers(r13, r4)
            if (r4 == 0) goto L_0x0023
            boolean r4 = r10.resurrectSelectionIfNeeded()
            if (r4 != 0) goto L_0x00fe
            boolean r4 = r10.mIsVertical
            if (r4 == 0) goto L_0x0101
        L_0x00f8:
            boolean r4 = r10.fullScroll(r5)
            if (r4 == 0) goto L_0x0103
        L_0x00fe:
            r2 = r8
        L_0x00ff:
            goto L_0x0023
        L_0x0101:
            r5 = r6
            goto L_0x00f8
        L_0x0103:
            r2 = r7
            goto L_0x00ff
        L_0x0105:
            boolean r5 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r5 == 0) goto L_0x0122
            boolean r5 = r10.resurrectSelectionIfNeeded()
            if (r5 != 0) goto L_0x011d
            boolean r5 = r10.mIsVertical
            if (r5 == 0) goto L_0x0117
            r4 = 130(0x82, float:1.82E-43)
        L_0x0117:
            boolean r4 = r10.pageScroll(r4)
            if (r4 == 0) goto L_0x0120
        L_0x011d:
            r2 = r8
        L_0x011e:
            goto L_0x0023
        L_0x0120:
            r2 = r7
            goto L_0x011e
        L_0x0122:
            r5 = 2
            boolean r5 = android.support.v4.view.KeyEventCompat.hasModifiers(r13, r5)
            if (r5 == 0) goto L_0x0023
            boolean r5 = r10.resurrectSelectionIfNeeded()
            if (r5 != 0) goto L_0x013b
            boolean r5 = r10.mIsVertical
            if (r5 == 0) goto L_0x0135
            r4 = 130(0x82, float:1.82E-43)
        L_0x0135:
            boolean r4 = r10.fullScroll(r4)
            if (r4 == 0) goto L_0x013e
        L_0x013b:
            r2 = r8
        L_0x013c:
            goto L_0x0023
        L_0x013e:
            r2 = r7
            goto L_0x013c
        L_0x0140:
            boolean r4 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r4 == 0) goto L_0x0023
            boolean r4 = r10.resurrectSelectionIfNeeded()
            if (r4 != 0) goto L_0x0156
            boolean r4 = r10.mIsVertical
            if (r4 == 0) goto L_0x0159
        L_0x0150:
            boolean r4 = r10.fullScroll(r5)
            if (r4 == 0) goto L_0x015b
        L_0x0156:
            r2 = r8
        L_0x0157:
            goto L_0x0023
        L_0x0159:
            r5 = r6
            goto L_0x0150
        L_0x015b:
            r2 = r7
            goto L_0x0157
        L_0x015d:
            boolean r5 = android.support.v4.view.KeyEventCompat.hasNoModifiers(r13)
            if (r5 == 0) goto L_0x0023
            boolean r5 = r10.resurrectSelectionIfNeeded()
            if (r5 != 0) goto L_0x0175
            boolean r5 = r10.mIsVertical
            if (r5 == 0) goto L_0x016f
            r4 = 130(0x82, float:1.82E-43)
        L_0x016f:
            boolean r4 = r10.fullScroll(r4)
            if (r4 == 0) goto L_0x0178
        L_0x0175:
            r3 = r8
            goto L_0x0032
        L_0x0178:
            r3 = r7
            goto L_0x0032
        L_0x017b:
            boolean r8 = super.onKeyDown(r11, r13)
            goto L_0x0011
        L_0x0181:
            boolean r4 = r10.isEnabled()
            if (r4 == 0) goto L_0x0011
            boolean r4 = r10.isClickable()
            if (r4 == 0) goto L_0x01bf
            boolean r4 = r10.isPressed()
            if (r4 == 0) goto L_0x01bf
            int r4 = r10.mSelectedPosition
            if (r4 < 0) goto L_0x01bf
            android.widget.ListAdapter r4 = r10.mAdapter
            if (r4 == 0) goto L_0x01bf
            int r4 = r10.mSelectedPosition
            android.widget.ListAdapter r5 = r10.mAdapter
            int r5 = r5.getCount()
            if (r4 >= r5) goto L_0x01bf
            int r4 = r10.mSelectedPosition
            int r5 = r10.mFirstPosition
            int r4 = r4 - r5
            android.view.View r1 = r10.getChildAt(r4)
            if (r1 == 0) goto L_0x01ba
            int r4 = r10.mSelectedPosition
            long r5 = r10.mSelectedRowId
            r10.performItemClick(r1, r4, r5)
            r1.setPressed(r7)
        L_0x01ba:
            r10.setPressed(r7)
            goto L_0x0011
        L_0x01bf:
            r8 = r7
            goto L_0x0011
        L_0x01c2:
            boolean r8 = super.onKeyMultiple(r11, r12, r13)
            goto L_0x0011
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.basic.AUHorizontalListView.handleKeyEvent(int, int, android.view.KeyEvent):boolean");
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
        onScrollChanged(0, 0, 0, 0);
    }

    private void reportScrollStateChange(int newState) {
        if (newState != this.mLastScrollState && this.mOnScrollListener != null) {
            this.mLastScrollState = newState;
            this.mOnScrollListener.onScrollStateChanged(this, newState);
        }
    }

    private boolean maybeStartScrolling(int delta) {
        boolean isOverScroll;
        if (this.mOverScroll != 0) {
            isOverScroll = true;
        } else {
            isOverScroll = false;
        }
        if (Math.abs(delta) <= this.mTouchSlop && !isOverScroll) {
            return false;
        }
        if (isOverScroll) {
            this.mTouchMode = 5;
        } else {
            this.mTouchMode = 3;
        }
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        cancelCheckForLongPress();
        setPressed(false);
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(false);
        }
        reportScrollStateChange(1);
        return true;
    }

    private void maybeScroll(int delta) {
        if (this.mTouchMode == 3) {
            handleDragChange(delta);
        } else if (this.mTouchMode == 5) {
            handleOverScrollChange(delta);
        }
    }

    private void handleDragChange(int delta) {
        int motionIndex;
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        if (this.mMotionPosition >= 0) {
            motionIndex = this.mMotionPosition - this.mFirstPosition;
        } else {
            motionIndex = getChildCount() / 2;
        }
        int motionViewPrevStart = 0;
        View motionView = getChildAt(motionIndex);
        if (motionView != null) {
            motionViewPrevStart = this.mIsVertical ? motionView.getTop() : motionView.getLeft();
        }
        boolean atEdge = scrollListItemsBy(delta);
        View motionView2 = getChildAt(motionIndex);
        if (motionView2 != null) {
            int motionViewRealStart = this.mIsVertical ? motionView2.getTop() : motionView2.getLeft();
            if (atEdge) {
                updateOverScrollState(delta, (-delta) - (motionViewRealStart - motionViewPrevStart));
            }
        }
    }

    private void updateOverScrollState(int delta, int overscroll) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        if (this.mIsVertical) {
            i = 0;
        } else {
            i = overscroll;
        }
        if (this.mIsVertical) {
            i2 = overscroll;
        } else {
            i2 = 0;
        }
        if (this.mIsVertical) {
            i3 = 0;
        } else {
            i3 = this.mOverScroll;
        }
        if (this.mIsVertical) {
            i4 = this.mOverScroll;
        } else {
            i4 = 0;
        }
        if (this.mIsVertical) {
            i5 = 0;
        } else {
            i5 = this.mOverscrollDistance;
        }
        if (this.mIsVertical) {
            i6 = this.mOverscrollDistance;
        } else {
            i6 = 0;
        }
        overScrollByInternal(i, i2, i3, i4, 0, 0, i5, i6, true);
        if (Math.abs(this.mOverscrollDistance) == Math.abs(this.mOverScroll) && this.mVelocityTracker != null) {
            this.mVelocityTracker.clear();
        }
        int overscrollMode = ViewCompat.getOverScrollMode(this);
        if (overscrollMode == 0 || (overscrollMode == 1 && !contentFits())) {
            this.mTouchMode = 5;
            float pull = ((float) overscroll) / ((float) (this.mIsVertical ? getHeight() : getWidth()));
            if (delta > 0) {
                this.mStartEdge.onPull(pull);
                if (!this.mEndEdge.isFinished()) {
                    this.mEndEdge.onRelease();
                }
            } else if (delta < 0) {
                this.mEndEdge.onPull(pull);
                if (!this.mStartEdge.isFinished()) {
                    this.mStartEdge.onRelease();
                }
            }
            if (delta != 0) {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
    }

    private void handleOverScrollChange(int delta) {
        int delta2;
        int oldOverScroll = this.mOverScroll;
        int newOverScroll = oldOverScroll - delta;
        int overScrollDistance = -delta;
        if ((newOverScroll >= 0 || oldOverScroll < 0) && (newOverScroll <= 0 || oldOverScroll > 0)) {
            delta2 = 0;
        } else {
            overScrollDistance = -oldOverScroll;
            delta2 = delta + overScrollDistance;
        }
        if (overScrollDistance != 0) {
            updateOverScrollState(delta2, overScrollDistance);
        }
        if (delta2 != 0) {
            if (this.mOverScroll != 0) {
                this.mOverScroll = 0;
                ViewCompat.postInvalidateOnAnimation(this);
            }
            scrollListItemsBy(delta2);
            this.mTouchMode = 3;
            this.mMotionPosition = findClosestMotionRowOrColumn((int) this.mLastTouchPos);
            this.mTouchRemainderPos = 0.0f;
        }
    }

    private static int getDistance(Rect source, Rect dest, int direction) {
        int sX;
        int sY;
        int dX;
        int dY;
        switch (direction) {
            case 1:
            case 2:
                sX = source.right + (source.width() / 2);
                sY = source.top + (source.height() / 2);
                dX = dest.left + (dest.width() / 2);
                dY = dest.top + (dest.height() / 2);
                break;
            case 17:
                sX = source.left;
                sY = source.top + (source.height() / 2);
                dX = dest.right;
                dY = dest.top + (dest.height() / 2);
                break;
            case 33:
                sX = source.left + (source.width() / 2);
                sY = source.top;
                dX = dest.left + (dest.width() / 2);
                dY = dest.bottom;
                break;
            case 66:
                sX = source.right;
                sY = source.top + (source.height() / 2);
                dX = dest.left;
                dY = dest.top + (dest.height() / 2);
                break;
            case 130:
                sX = source.left + (source.width() / 2);
                sY = source.bottom;
                dX = dest.left + (dest.width() / 2);
                dY = dest.top;
                break;
            default:
                throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT, FOCUS_FORWARD, FOCUS_BACKWARD}.");
        }
        int deltaX = dX - sX;
        int i = dY - sY;
        return (i * i) + (deltaX * deltaX);
    }

    private int findMotionRowOrColumn(int motionPos) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return -1;
        }
        for (int i = 0; i < childCount; i++) {
            View v = getChildAt(i);
            if ((this.mIsVertical && motionPos <= v.getBottom()) || (!this.mIsVertical && motionPos <= v.getRight())) {
                return this.mFirstPosition + i;
            }
        }
        return -1;
    }

    private int findClosestMotionRowOrColumn(int motionPos) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int motionRow = findMotionRowOrColumn(motionPos);
        return motionRow == -1 ? (this.mFirstPosition + childCount) - 1 : motionRow;
    }

    @TargetApi(9)
    private int getScaledOverscrollDistance(ViewConfiguration vc) {
        if (VERSION.SDK_INT < 9) {
            return 0;
        }
        return vc.getScaledOverscrollDistance();
    }

    private boolean contentFits() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        if (childCount != this.mItemCount) {
            return false;
        }
        View first = getChildAt(0);
        View last = getChildAt(childCount - 1);
        if (this.mIsVertical) {
            if (first.getTop() < getPaddingTop() || last.getBottom() > getHeight() - getPaddingBottom()) {
                return false;
            }
            return true;
        } else if (first.getLeft() < getPaddingLeft() || last.getRight() > getWidth() - getPaddingRight()) {
            return false;
        } else {
            return true;
        }
    }

    private void updateScrollbarsDirection() {
        setHorizontalScrollBarEnabled(!this.mIsVertical);
        setVerticalScrollBarEnabled(this.mIsVertical);
    }

    private void triggerCheckForTap() {
        if (this.mPendingCheckForTap == null) {
            this.mPendingCheckForTap = new o(this, 0);
        }
        postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
    }

    private void cancelCheckForTap() {
        if (this.mPendingCheckForTap != null) {
            removeCallbacks(this.mPendingCheckForTap);
        }
    }

    /* access modifiers changed from: private */
    public void triggerCheckForLongPress() {
        if (this.mPendingCheckForLongPress == null) {
            this.mPendingCheckForLongPress = new n(this, 0);
        }
        this.mPendingCheckForLongPress.a();
        postDelayed(this.mPendingCheckForLongPress, (long) ViewConfiguration.getLongPressTimeout());
    }

    private void cancelCheckForLongPress() {
        if (this.mPendingCheckForLongPress != null) {
            removeCallbacks(this.mPendingCheckForLongPress);
        }
    }

    private boolean scrollListItemsBy(int incrementalDelta) {
        int paddingStart;
        int end;
        int size;
        int incrementalDelta2;
        int childCount = getChildCount();
        if (childCount == 0) {
            return true;
        }
        View first = getChildAt(0);
        int firstStart = this.mIsVertical ? first.getTop() : first.getLeft();
        View last = getChildAt(childCount - 1);
        int lastEnd = this.mIsVertical ? last.getBottom() : last.getRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        if (this.mIsVertical) {
            paddingStart = paddingTop;
        } else {
            paddingStart = paddingLeft;
        }
        int spaceBefore = paddingStart - firstStart;
        if (this.mIsVertical) {
            end = getHeight() - paddingBottom;
        } else {
            end = getWidth() - paddingRight;
        }
        int spaceAfter = lastEnd - end;
        if (this.mIsVertical) {
            size = (getHeight() - paddingBottom) - paddingTop;
        } else {
            size = (getWidth() - paddingRight) - paddingLeft;
        }
        if (incrementalDelta < 0) {
            incrementalDelta2 = Math.max(-(size - 1), incrementalDelta);
        } else {
            incrementalDelta2 = Math.min(size - 1, incrementalDelta);
        }
        int firstPosition = this.mFirstPosition;
        boolean cannotScrollDown = firstPosition == 0 && firstStart >= paddingStart && incrementalDelta2 >= 0;
        boolean cannotScrollUp = firstPosition + childCount == this.mItemCount && lastEnd <= end && incrementalDelta2 <= 0;
        if (!cannotScrollDown && !cannotScrollUp) {
            boolean inTouchMode = isInTouchMode();
            if (inTouchMode) {
                hideSelector();
            }
            int start = 0;
            int count = 0;
            boolean down = incrementalDelta2 < 0;
            if (!down) {
                int childrenEnd = end - incrementalDelta2;
                for (int i = childCount - 1; i >= 0; i--) {
                    View child = getChildAt(i);
                    if ((this.mIsVertical ? child.getTop() : child.getLeft()) <= childrenEnd) {
                        break;
                    }
                    start = i;
                    count++;
                    this.mRecycler.a(child, firstPosition + i);
                }
            } else {
                int childrenStart = (-incrementalDelta2) + paddingStart;
                for (int i2 = 0; i2 < childCount; i2++) {
                    View child2 = getChildAt(i2);
                    if ((this.mIsVertical ? child2.getBottom() : child2.getRight()) >= childrenStart) {
                        break;
                    }
                    count++;
                    this.mRecycler.a(child2, firstPosition + i2);
                }
            }
            this.mBlockLayoutRequests = true;
            if (count > 0) {
                detachViewsFromParent(start, count);
            }
            if (!awakenScrollbarsInternal()) {
                invalidate();
            }
            offsetChildren(incrementalDelta2);
            if (down) {
                this.mFirstPosition += count;
            }
            int absIncrementalDelta = Math.abs(incrementalDelta2);
            if (spaceBefore < absIncrementalDelta || spaceAfter < absIncrementalDelta) {
                fillGap(down);
            }
            if (!inTouchMode && this.mSelectedPosition != -1) {
                int childIndex = this.mSelectedPosition - this.mFirstPosition;
                if (childIndex >= 0 && childIndex < getChildCount()) {
                    positionSelector(this.mSelectedPosition, getChildAt(childIndex));
                }
            } else if (this.mSelectorPosition != -1) {
                int childIndex2 = this.mSelectorPosition - this.mFirstPosition;
                if (childIndex2 >= 0 && childIndex2 < getChildCount()) {
                    positionSelector(-1, getChildAt(childIndex2));
                }
            } else {
                this.mSelectorRect.setEmpty();
            }
            this.mBlockLayoutRequests = false;
            invokeOnItemScrollListener();
            return false;
        } else if (incrementalDelta2 != 0) {
            return true;
        } else {
            return false;
        }
    }

    @TargetApi(14)
    private final float getCurrVelocity() {
        if (VERSION.SDK_INT >= 14) {
            return this.mScroller.getCurrVelocity();
        }
        return 0.0f;
    }

    @TargetApi(5)
    private boolean awakenScrollbarsInternal() {
        if (VERSION.SDK_INT >= 5) {
            return super.awakenScrollBars();
        }
        return false;
    }

    public void computeScroll() {
        int pos;
        if (this.mScroller.computeScrollOffset()) {
            if (this.mIsVertical) {
                pos = this.mScroller.getCurrY();
            } else {
                pos = this.mScroller.getCurrX();
            }
            int diff = (int) (((float) pos) - this.mLastTouchPos);
            this.mLastTouchPos = (float) pos;
            boolean stopped = scrollListItemsBy(diff);
            if (stopped || this.mScroller.isFinished()) {
                if (stopped) {
                    if (ViewCompat.getOverScrollMode(this) != 2) {
                        if ((diff > 0 ? this.mStartEdge : this.mEndEdge).onAbsorb(Math.abs((int) getCurrVelocity()))) {
                            ViewCompat.postInvalidateOnAnimation(this);
                        }
                    }
                    this.mScroller.abortAnimation();
                }
                this.mTouchMode = -1;
                reportScrollStateChange(0);
                return;
            }
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void finishEdgeGlows() {
        if (this.mStartEdge != null) {
            this.mStartEdge.finish();
        }
        if (this.mEndEdge != null) {
            this.mEndEdge.finish();
        }
    }

    private boolean drawStartEdge(Canvas canvas) {
        if (this.mStartEdge.isFinished()) {
            return false;
        }
        if (this.mIsVertical) {
            return this.mStartEdge.draw(canvas);
        }
        int restoreCount = canvas.save();
        canvas.translate(0.0f, (float) ((getHeight() - getPaddingTop()) - getPaddingBottom()));
        canvas.rotate(270.0f);
        boolean draw = this.mStartEdge.draw(canvas);
        canvas.restoreToCount(restoreCount);
        return draw;
    }

    private boolean drawEndEdge(Canvas canvas) {
        if (this.mEndEdge.isFinished()) {
            return false;
        }
        int restoreCount = canvas.save();
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
        if (this.mIsVertical) {
            canvas.translate((float) (-width), (float) height);
            canvas.rotate(180.0f, (float) width, 0.0f);
        } else {
            canvas.translate((float) width, 0.0f);
            canvas.rotate(90.0f);
        }
        boolean draw = this.mEndEdge.draw(canvas);
        canvas.restoreToCount(restoreCount);
        return draw;
    }

    private void drawSelector(Canvas canvas) {
        if (!this.mSelectorRect.isEmpty()) {
            Drawable selector = this.mSelector;
            selector.setBounds(this.mSelectorRect);
            selector.draw(canvas);
        }
    }

    private void useDefaultSelector() {
        setSelector(getResources().getDrawable(17301602));
    }

    private boolean shouldShowSelector() {
        return (hasFocus() && !isInTouchMode()) || touchModeDrawsInPressedState();
    }

    /* access modifiers changed from: private */
    public void positionSelector(int position, View selected) {
        if (position != -1) {
            this.mSelectorPosition = position;
        }
        this.mSelectorRect.set(selected.getLeft(), selected.getTop(), selected.getRight(), selected.getBottom());
        boolean isChildViewEnabled = this.mIsChildViewEnabled;
        if (selected.isEnabled() != isChildViewEnabled) {
            this.mIsChildViewEnabled = !isChildViewEnabled;
            if (getSelectedItemPosition() != -1) {
                refreshDrawableState();
            }
        }
    }

    private void hideSelector() {
        if (this.mSelectedPosition != -1) {
            if (this.mLayoutMode != 4) {
                this.mResurrectToPosition = this.mSelectedPosition;
            }
            if (this.mNextSelectedPosition >= 0 && this.mNextSelectedPosition != this.mSelectedPosition) {
                this.mResurrectToPosition = this.mNextSelectedPosition;
            }
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectedStart = 0;
        }
    }

    private void setSelectedPositionInt(int position) {
        this.mSelectedPosition = position;
        this.mSelectedRowId = getItemIdAtPosition(position);
    }

    private void setSelectionInt(int position) {
        setNextSelectedPositionInt(position);
        boolean awakeScrollbars = false;
        int selectedPosition = this.mSelectedPosition;
        if (selectedPosition >= 0) {
            if (position == selectedPosition - 1) {
                awakeScrollbars = true;
            } else if (position == selectedPosition + 1) {
                awakeScrollbars = true;
            }
        }
        layoutChildren();
        if (awakeScrollbars) {
            awakenScrollbarsInternal();
        }
    }

    private void setNextSelectedPositionInt(int position) {
        this.mNextSelectedPosition = position;
        this.mNextSelectedRowId = getItemIdAtPosition(position);
        if (this.mNeedSync && this.mSyncMode == 0 && position >= 0) {
            this.mSyncPosition = position;
            this.mSyncRowId = this.mNextSelectedRowId;
        }
    }

    private boolean touchModeDrawsInPressedState() {
        switch (this.mTouchMode) {
            case 1:
            case 2:
                return true;
            default:
                return false;
        }
    }

    private void keyPressed() {
        if (isEnabled() && isClickable()) {
            Drawable selector = this.mSelector;
            Rect selectorRect = this.mSelectorRect;
            if (selector == null) {
                return;
            }
            if ((isFocused() || touchModeDrawsInPressedState()) && !selectorRect.isEmpty()) {
                View child = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                if (child != null) {
                    if (!child.hasFocusable()) {
                        child.setPressed(true);
                    } else {
                        return;
                    }
                }
                setPressed(true);
                boolean longClickable = isLongClickable();
                Drawable d = selector.getCurrent();
                if (d != null && (d instanceof TransitionDrawable)) {
                    if (longClickable) {
                        ((TransitionDrawable) d).startTransition(ViewConfiguration.getLongPressTimeout());
                    } else {
                        ((TransitionDrawable) d).resetTransition();
                    }
                }
                if (longClickable && !this.mDataChanged) {
                    if (this.mPendingCheckForKeyLongPress == null) {
                        this.mPendingCheckForKeyLongPress = new m(this, 0);
                    }
                    this.mPendingCheckForKeyLongPress.a();
                    postDelayed(this.mPendingCheckForKeyLongPress, (long) ViewConfiguration.getLongPressTimeout());
                }
            }
        }
    }

    private void updateSelectorState() {
        if (this.mSelector == null) {
            return;
        }
        if (shouldShowSelector()) {
            this.mSelector.setState(getDrawableState());
        } else {
            this.mSelector.setState(STATE_NOTHING);
        }
    }

    private void checkSelectionChanged() {
        if (this.mSelectedPosition != this.mOldSelectedPosition || this.mSelectedRowId != this.mOldSelectedRowId) {
            selectionChanged();
            this.mOldSelectedPosition = this.mSelectedPosition;
            this.mOldSelectedRowId = this.mSelectedRowId;
        }
    }

    private void selectionChanged() {
        if (getOnItemSelectedListener() != null) {
            if (this.mInLayout || this.mBlockLayoutRequests) {
                if (this.mSelectionNotifier == null) {
                    this.mSelectionNotifier = new t(this, 0);
                }
                post(this.mSelectionNotifier);
                return;
            }
            fireOnSelected();
            performAccessibilityActionsOnSelected();
        }
    }

    /* access modifiers changed from: private */
    public void fireOnSelected() {
        OnItemSelectedListener listener = getOnItemSelectedListener();
        if (listener != null) {
            int selection = getSelectedItemPosition();
            if (selection >= 0) {
                listener.onItemSelected(this, getSelectedView(), selection, this.mAdapter.getItemId(selection));
                return;
            }
            listener.onNothingSelected(this);
        }
    }

    /* access modifiers changed from: private */
    public void performAccessibilityActionsOnSelected() {
        if (getSelectedItemPosition() >= 0) {
            sendAccessibilityEvent(4);
        }
    }

    private int lookForSelectablePosition(int position) {
        return lookForSelectablePosition(position, true);
    }

    private int lookForSelectablePosition(int position, boolean lookDown) {
        int position2;
        ListAdapter adapter = this.mAdapter;
        if (adapter == null || isInTouchMode()) {
            return -1;
        }
        int itemCount = this.mItemCount;
        if (!this.mAreAllItemsSelectable) {
            if (lookDown) {
                position2 = Math.max(0, position);
                while (position2 < itemCount && !adapter.isEnabled(position2)) {
                    position2++;
                }
            } else {
                int position3 = Math.min(position, itemCount - 1);
                while (position2 >= 0 && !adapter.isEnabled(position2)) {
                    position3 = position2 - 1;
                }
            }
            if (position2 < 0 || position2 >= itemCount) {
                return -1;
            }
            return position2;
        } else if (position < 0 || position >= itemCount) {
            return -1;
        } else {
            return position;
        }
    }

    private int lookForSelectablePositionOnScreen(int direction) {
        int startPos;
        int startPos2;
        forceValidFocusDirection(direction);
        int firstPosition = this.mFirstPosition;
        ListAdapter adapter = getAdapter();
        if (direction == 130 || direction == 66) {
            if (this.mSelectedPosition != -1) {
                startPos = this.mSelectedPosition + 1;
            } else {
                startPos = firstPosition;
            }
            if (startPos >= adapter.getCount()) {
                return -1;
            }
            if (startPos < firstPosition) {
                startPos = firstPosition;
            }
            int lastVisiblePos = getLastVisiblePosition();
            for (int pos = startPos; pos <= lastVisiblePos; pos++) {
                if (adapter.isEnabled(pos) && getChildAt(pos - firstPosition).getVisibility() == 0) {
                    return pos;
                }
            }
        } else {
            int last = (getChildCount() + firstPosition) - 1;
            if (this.mSelectedPosition != -1) {
                startPos2 = this.mSelectedPosition - 1;
            } else {
                startPos2 = (getChildCount() + firstPosition) - 1;
            }
            if (startPos2 < 0 || startPos2 >= adapter.getCount()) {
                return -1;
            }
            if (startPos2 > last) {
                startPos2 = last;
            }
            for (int pos2 = startPos2; pos2 >= firstPosition; pos2--) {
                if (adapter.isEnabled(pos2) && getChildAt(pos2 - firstPosition).getVisibility() == 0) {
                    return pos2;
                }
            }
        }
        return -1;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        updateSelectorState();
    }

    /* access modifiers changed from: protected */
    public int[] onCreateDrawableState(int extraSpace) {
        if (this.mIsChildViewEnabled) {
            return super.onCreateDrawableState(extraSpace);
        }
        int enabledState = ENABLED_STATE_SET[0];
        int[] state = super.onCreateDrawableState(extraSpace + 1);
        int enabledPos = -1;
        int i = state.length - 1;
        while (true) {
            if (i < 0) {
                break;
            } else if (state[i] == enabledState) {
                enabledPos = i;
                break;
            } else {
                i--;
            }
        }
        if (enabledPos < 0) {
            return state;
        }
        System.arraycopy(state, enabledPos + 1, state, enabledPos, (state.length - enabledPos) - 1);
        return state;
    }

    /* access modifiers changed from: protected */
    public boolean canAnimate() {
        return super.canAnimate() && this.mItemCount > 0;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        boolean drawSelectorOnTop = this.mDrawSelectorOnTop;
        if (!drawSelectorOnTop) {
            drawSelector(canvas);
        }
        super.dispatchDraw(canvas);
        if (drawSelectorOnTop) {
            drawSelector(canvas);
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean needsInvalidate = false;
        if (this.mStartEdge != null) {
            needsInvalidate = drawStartEdge(canvas) | false;
        }
        if (this.mEndEdge != null) {
            needsInvalidate |= drawEndEdge(canvas);
        }
        if (needsInvalidate) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void requestLayout() {
        if (!this.mInLayout && !this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    public View getSelectedView() {
        if (this.mItemCount <= 0 || this.mSelectedPosition < 0) {
            return null;
        }
        return getChildAt(this.mSelectedPosition - this.mFirstPosition);
    }

    public void setSelection(int position) {
        setSelectionFromOffset(position, 0);
    }

    public void setSelectionFromOffset(int position, int offset) {
        if (this.mAdapter != null) {
            if (!isInTouchMode()) {
                position = lookForSelectablePosition(position);
                if (position >= 0) {
                    setNextSelectedPositionInt(position);
                }
            } else {
                this.mResurrectToPosition = position;
            }
            if (position >= 0) {
                this.mLayoutMode = 4;
                if (this.mIsVertical) {
                    this.mSpecificStart = getPaddingTop() + offset;
                } else {
                    this.mSpecificStart = getPaddingLeft() + offset;
                }
                if (this.mNeedSync) {
                    this.mSyncPosition = position;
                    this.mSyncRowId = this.mAdapter.getItemId(position);
                }
                requestLayout();
            }
        }
    }

    public void scrollBy(int offset) {
        scrollListItemsBy(offset);
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        boolean handled = super.dispatchKeyEvent(event);
        if (handled || getFocusedChild() == null || event.getAction() != 0) {
            return handled;
        }
        return onKeyDown(event.getKeyCode(), event);
    }

    /* access modifiers changed from: protected */
    public void dispatchSetPressed(boolean pressed) {
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int secondaryMeasureSpec;
        if (this.mSelector == null) {
            useDefaultSelector();
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int childWidth = 0;
        int childHeight = 0;
        this.mItemCount = this.mAdapter == null ? 0 : this.mAdapter.getCount();
        if (this.mItemCount > 0 && (widthMode == 0 || heightMode == 0)) {
            View child = obtainView(0, this.mIsScrap);
            if (this.mIsVertical) {
                secondaryMeasureSpec = widthMeasureSpec;
            } else {
                secondaryMeasureSpec = heightMeasureSpec;
            }
            measureScrapChild(child, 0, secondaryMeasureSpec);
            childWidth = child.getMeasuredWidth();
            childHeight = child.getMeasuredHeight();
            if (recycleOnMeasure()) {
                this.mRecycler.a(child, -1);
            }
        }
        if (widthMode == 0) {
            widthSize = getPaddingLeft() + getPaddingRight() + childWidth;
            if (this.mIsVertical) {
                widthSize += getVerticalScrollbarWidth();
            }
        }
        if (heightMode == 0) {
            heightSize = getPaddingTop() + getPaddingBottom() + childHeight;
            if (!this.mIsVertical && getChildCount() != 0) {
                heightSize += getHorizontalScrollbarHeight();
            }
        }
        if (this.mIsVertical && heightMode == Integer.MIN_VALUE) {
            heightSize = measureHeightOfChildren(widthMeasureSpec, 0, -1, heightSize, -1);
        }
        if (!this.mIsVertical && widthMode == Integer.MIN_VALUE) {
            widthSize = measureWidthOfChildren(heightMeasureSpec, 0, -1, widthSize, -1);
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        this.mInLayout = true;
        if (changed) {
            int childCount = getChildCount();
            for (int i = 0; i < childCount; i++) {
                getChildAt(i).forceLayout();
            }
            this.mRecycler.a();
        }
        layoutChildren();
        this.mInLayout = false;
        int width = ((r - l) - getPaddingLeft()) - getPaddingRight();
        int height = ((b - t) - getPaddingTop()) - getPaddingBottom();
        if (this.mStartEdge != null && this.mEndEdge != null) {
            if (this.mIsVertical) {
                this.mStartEdge.setSize(width, height);
                this.mEndEdge.setSize(width, height);
                return;
            }
            this.mStartEdge.setSize(height, width);
            this.mEndEdge.setSize(height, width);
        }
    }

    /* access modifiers changed from: private */
    public void layoutChildren() {
        int end;
        View selected;
        int left;
        if (getWidth() != 0 && getHeight() != 0) {
            boolean blockLayoutRequests = this.mBlockLayoutRequests;
            if (!blockLayoutRequests) {
                this.mBlockLayoutRequests = true;
                try {
                    invalidate();
                    if (this.mAdapter == null) {
                        resetState();
                        if (!blockLayoutRequests) {
                            this.mBlockLayoutRequests = false;
                            this.mDataChanged = false;
                            return;
                        }
                        return;
                    }
                    int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
                    if (this.mIsVertical) {
                        end = getHeight() - getPaddingBottom();
                    } else {
                        end = getWidth() - getPaddingRight();
                    }
                    int childCount = getChildCount();
                    int delta = 0;
                    View focusLayoutRestoreView = null;
                    View oldSelected = null;
                    View newSelected = null;
                    View oldFirstChild = null;
                    switch (this.mLayoutMode) {
                        case 1:
                        case 3:
                        case 4:
                        case 5:
                            break;
                        case 2:
                            int index = this.mNextSelectedPosition - this.mFirstPosition;
                            if (index >= 0 && index < childCount) {
                                newSelected = getChildAt(index);
                                break;
                            }
                        default:
                            int index2 = this.mSelectedPosition - this.mFirstPosition;
                            if (index2 >= 0 && index2 < childCount) {
                                oldSelected = getChildAt(index2);
                            }
                            oldFirstChild = getChildAt(0);
                            if (this.mNextSelectedPosition >= 0) {
                                delta = this.mNextSelectedPosition - this.mSelectedPosition;
                            }
                            newSelected = getChildAt(index2 + delta);
                            break;
                    }
                    boolean dataChanged = this.mDataChanged;
                    if (dataChanged) {
                        handleDataChanged();
                    }
                    if (this.mItemCount == 0) {
                        resetState();
                        if (!blockLayoutRequests) {
                            this.mBlockLayoutRequests = false;
                            this.mDataChanged = false;
                        }
                    } else if (this.mItemCount != this.mAdapter.getCount()) {
                        throw new IllegalStateException("The content of the adapter has changed but TwoWayView did not receive a notification. Make sure the content of your adapter is not modified from a background thread, but only from the UI thread. [in TwoWayView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                    } else {
                        setSelectedPositionInt(this.mNextSelectedPosition);
                        View focusLayoutRestoreDirectChild = null;
                        int firstPosition = this.mFirstPosition;
                        r recycleBin = this.mRecycler;
                        if (dataChanged) {
                            for (int i = 0; i < childCount; i++) {
                                recycleBin.a(getChildAt(i), firstPosition + i);
                            }
                        } else {
                            recycleBin.a(childCount, firstPosition);
                        }
                        View focusedChild = getFocusedChild();
                        if (focusedChild != null) {
                            if (!dataChanged) {
                                focusLayoutRestoreDirectChild = focusedChild;
                                focusLayoutRestoreView = findFocus();
                                if (focusLayoutRestoreView != null) {
                                    focusLayoutRestoreView.onStartTemporaryDetach();
                                }
                            }
                            requestFocus();
                        }
                        detachAllViewsFromParent();
                        switch (this.mLayoutMode) {
                            case 1:
                                this.mFirstPosition = 0;
                                selected = fillFromOffset(start);
                                adjustViewsStartOrEnd();
                                break;
                            case 2:
                                if (newSelected == null) {
                                    selected = fillFromMiddle(start, end);
                                    break;
                                } else {
                                    selected = fillFromSelection(this.mIsVertical ? newSelected.getTop() : newSelected.getLeft(), start, end);
                                    break;
                                }
                            case 3:
                                selected = fillBefore(this.mItemCount - 1, end);
                                adjustViewsStartOrEnd();
                                break;
                            case 4:
                                selected = fillSpecific(reconcileSelectedPosition(), this.mSpecificStart);
                                break;
                            case 5:
                                selected = fillSpecific(this.mSyncPosition, this.mSpecificStart);
                                break;
                            case 6:
                                selected = moveSelection(oldSelected, newSelected, delta, start, end);
                                break;
                            default:
                                if (childCount != 0) {
                                    if (this.mSelectedPosition < 0 || this.mSelectedPosition >= this.mItemCount) {
                                        if (this.mFirstPosition >= this.mItemCount) {
                                            selected = fillSpecific(0, start);
                                            break;
                                        } else {
                                            int offset = start;
                                            if (oldFirstChild != null) {
                                                offset = this.mIsVertical ? oldFirstChild.getTop() : oldFirstChild.getLeft();
                                            }
                                            selected = fillSpecific(this.mFirstPosition, offset);
                                            break;
                                        }
                                    } else {
                                        int offset2 = start;
                                        if (oldSelected != null) {
                                            offset2 = this.mIsVertical ? oldSelected.getTop() : oldSelected.getLeft();
                                        }
                                        selected = fillSpecific(this.mSelectedPosition, offset2);
                                        break;
                                    }
                                } else {
                                    setSelectedPositionInt(lookForSelectablePosition(0));
                                    selected = fillFromOffset(start);
                                    break;
                                }
                                break;
                        }
                        recycleBin.d();
                        if (selected != null) {
                            if (!this.mItemsCanFocus || !hasFocus() || selected.hasFocus()) {
                                positionSelector(-1, selected);
                            } else {
                                if (!((selected == focusLayoutRestoreDirectChild && focusLayoutRestoreView != null && focusLayoutRestoreView.requestFocus()) || selected.requestFocus())) {
                                    View focused = getFocusedChild();
                                    if (focused != null) {
                                        focused.clearFocus();
                                    }
                                    positionSelector(-1, selected);
                                } else {
                                    selected.setSelected(false);
                                    this.mSelectorRect.setEmpty();
                                }
                            }
                            if (this.mIsVertical) {
                                left = selected.getTop();
                            } else {
                                left = selected.getLeft();
                            }
                            this.mSelectedStart = left;
                        } else {
                            if (this.mTouchMode <= 0 || this.mTouchMode >= 3) {
                                this.mSelectedStart = 0;
                                this.mSelectorRect.setEmpty();
                            } else {
                                View child = getChildAt(this.mMotionPosition - this.mFirstPosition);
                                if (child != null) {
                                    positionSelector(this.mMotionPosition, child);
                                }
                            }
                            if (hasFocus() && focusLayoutRestoreView != null) {
                                focusLayoutRestoreView.requestFocus();
                            }
                        }
                        if (!(focusLayoutRestoreView == null || focusLayoutRestoreView.getWindowToken() == null)) {
                            focusLayoutRestoreView.onFinishTemporaryDetach();
                        }
                        this.mLayoutMode = 0;
                        this.mDataChanged = false;
                        this.mNeedSync = false;
                        setNextSelectedPositionInt(this.mSelectedPosition);
                        if (this.mItemCount > 0) {
                            checkSelectionChanged();
                        }
                        invokeOnItemScrollListener();
                        if (!blockLayoutRequests) {
                            this.mBlockLayoutRequests = false;
                            this.mDataChanged = false;
                        }
                    }
                } catch (Throwable th) {
                    if (!blockLayoutRequests) {
                        this.mBlockLayoutRequests = false;
                        this.mDataChanged = false;
                    }
                    throw th;
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public boolean recycleOnMeasure() {
        return true;
    }

    private void offsetChildren(int offset) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (this.mIsVertical) {
                child.offsetTopAndBottom(offset);
            } else {
                child.offsetLeftAndRight(offset);
            }
        }
    }

    private View moveSelection(View oldSelected, View newSelected, int delta, int start, int end) {
        View selected;
        int selectedPosition = this.mSelectedPosition;
        int oldSelectedStart = this.mIsVertical ? oldSelected.getTop() : oldSelected.getLeft();
        int oldSelectedEnd = this.mIsVertical ? oldSelected.getBottom() : oldSelected.getRight();
        if (delta > 0) {
            View oldSelected2 = makeAndAddView(selectedPosition - 1, oldSelectedStart, true, false);
            int itemMargin = this.mItemMargin;
            selected = makeAndAddView(selectedPosition, oldSelectedEnd + itemMargin, true, true);
            int selectedStart = this.mIsVertical ? selected.getTop() : selected.getLeft();
            int selectedEnd = this.mIsVertical ? selected.getBottom() : selected.getRight();
            if (selectedEnd > end) {
                int offset = Math.min(Math.min(selectedStart - start, selectedEnd - end), (end - start) / 2);
                if (this.mIsVertical) {
                    oldSelected2.offsetTopAndBottom(-offset);
                    selected.offsetTopAndBottom(-offset);
                } else {
                    oldSelected2.offsetLeftAndRight(-offset);
                    selected.offsetLeftAndRight(-offset);
                }
            }
            fillBefore(this.mSelectedPosition - 2, selectedStart - itemMargin);
            adjustViewsStartOrEnd();
            fillAfter(this.mSelectedPosition + 1, selectedEnd + itemMargin);
        } else if (delta < 0) {
            if (newSelected != null) {
                selected = makeAndAddView(selectedPosition, this.mIsVertical ? newSelected.getTop() : newSelected.getLeft(), true, true);
            } else {
                selected = makeAndAddView(selectedPosition, oldSelectedStart, false, true);
            }
            int selectedStart2 = this.mIsVertical ? selected.getTop() : selected.getLeft();
            int selectedEnd2 = this.mIsVertical ? selected.getBottom() : selected.getRight();
            if (selectedStart2 < start) {
                int offset2 = Math.min(Math.min(start - selectedStart2, end - selectedEnd2), (end - start) / 2);
                if (this.mIsVertical) {
                    selected.offsetTopAndBottom(offset2);
                } else {
                    selected.offsetLeftAndRight(offset2);
                }
            }
            fillBeforeAndAfter(selected, selectedPosition);
        } else {
            selected = makeAndAddView(selectedPosition, oldSelectedStart, true, true);
            int selectedStart3 = this.mIsVertical ? selected.getTop() : selected.getLeft();
            int selectedEnd3 = this.mIsVertical ? selected.getBottom() : selected.getRight();
            if (oldSelectedStart < start && selectedEnd3 < start + 20) {
                if (this.mIsVertical) {
                    selected.offsetTopAndBottom(start - selectedStart3);
                } else {
                    selected.offsetLeftAndRight(start - selectedStart3);
                }
            }
            fillBeforeAndAfter(selected, selectedPosition);
        }
        return selected;
    }

    /* access modifiers changed from: 0000 */
    public void confirmCheckedPositionsById() {
        this.mCheckStates.clear();
        int checkedIndex = 0;
        while (checkedIndex < this.mCheckedIdStates.size()) {
            long id = this.mCheckedIdStates.keyAt(checkedIndex);
            int lastPos = ((Integer) this.mCheckedIdStates.valueAt(checkedIndex)).intValue();
            if (id != this.mAdapter.getItemId(lastPos)) {
                int start = Math.max(0, lastPos - 20);
                int end = Math.min(lastPos + 20, this.mItemCount);
                boolean found = false;
                int searchPos = start;
                while (true) {
                    if (searchPos >= end) {
                        break;
                    } else if (id == this.mAdapter.getItemId(searchPos)) {
                        found = true;
                        this.mCheckStates.put(searchPos, true);
                        this.mCheckedIdStates.setValueAt(checkedIndex, Integer.valueOf(searchPos));
                        break;
                    } else {
                        searchPos++;
                    }
                }
                if (!found) {
                    this.mCheckedIdStates.delete(id);
                    checkedIndex--;
                    this.mCheckedItemCount--;
                }
            } else {
                this.mCheckStates.put(lastPos, true);
            }
            checkedIndex++;
        }
    }

    private void handleDataChanged() {
        if (!(this.mChoiceMode.compareTo(ChoiceMode.NONE) == 0 || this.mAdapter == null || !this.mAdapter.hasStableIds())) {
            confirmCheckedPositionsById();
        }
        this.mRecycler.c();
        int itemCount = this.mItemCount;
        if (itemCount > 0) {
            if (this.mNeedSync) {
                this.mNeedSync = false;
                this.mPendingSync = null;
                switch (this.mSyncMode) {
                    case 0:
                        if (isInTouchMode()) {
                            this.mLayoutMode = 5;
                            this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), itemCount - 1);
                            return;
                        }
                        int newPos = findSyncPosition();
                        if (newPos >= 0 && lookForSelectablePosition(newPos, true) == newPos) {
                            this.mSyncPosition = newPos;
                            if (this.mSyncHeight == ((long) getHeight())) {
                                this.mLayoutMode = 5;
                            } else {
                                this.mLayoutMode = 2;
                            }
                            setNextSelectedPositionInt(newPos);
                            return;
                        }
                    case 1:
                        this.mLayoutMode = 5;
                        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), itemCount - 1);
                        return;
                }
            }
            if (!isInTouchMode()) {
                int newPos2 = getSelectedItemPosition();
                if (newPos2 >= itemCount) {
                    newPos2 = itemCount - 1;
                }
                if (newPos2 < 0) {
                    newPos2 = 0;
                }
                int selectablePos = lookForSelectablePosition(newPos2, true);
                if (selectablePos >= 0) {
                    setNextSelectedPositionInt(selectablePos);
                    return;
                }
                int selectablePos2 = lookForSelectablePosition(newPos2, false);
                if (selectablePos2 >= 0) {
                    setNextSelectedPositionInt(selectablePos2);
                    return;
                }
            } else if (this.mResurrectToPosition >= 0) {
                return;
            }
        }
        this.mLayoutMode = 1;
        this.mSelectedPosition = -1;
        this.mSelectedRowId = Long.MIN_VALUE;
        this.mNextSelectedPosition = -1;
        this.mNextSelectedRowId = Long.MIN_VALUE;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mSelectorPosition = -1;
        checkSelectionChanged();
    }

    private int reconcileSelectedPosition() {
        int position = this.mSelectedPosition;
        if (position < 0) {
            position = this.mResurrectToPosition;
        }
        return Math.min(Math.max(0, position), this.mItemCount - 1);
    }

    /* access modifiers changed from: 0000 */
    public boolean resurrectSelection() {
        int selectedPosition;
        int childCount = getChildCount();
        if (childCount <= 0) {
            return false;
        }
        int selectedStart = 0;
        int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
        int end = this.mIsVertical ? getHeight() - getPaddingBottom() : getWidth() - getPaddingRight();
        int firstPosition = this.mFirstPosition;
        int toPosition = this.mResurrectToPosition;
        boolean down = true;
        if (toPosition < firstPosition || toPosition >= firstPosition + childCount) {
            if (toPosition >= firstPosition) {
                selectedPosition = (firstPosition + childCount) - 1;
                down = false;
                int i = childCount - 1;
                while (true) {
                    if (i < 0) {
                        break;
                    }
                    View child = getChildAt(i);
                    int childStart = this.mIsVertical ? child.getTop() : child.getLeft();
                    int childEnd = this.mIsVertical ? child.getBottom() : child.getRight();
                    if (i == childCount - 1) {
                        selectedStart = childStart;
                    }
                    if (childEnd <= end) {
                        selectedPosition = firstPosition + i;
                        selectedStart = childStart;
                        break;
                    }
                    i--;
                }
            } else {
                selectedPosition = firstPosition;
                int i2 = 0;
                while (true) {
                    if (i2 >= childCount) {
                        break;
                    }
                    View child2 = getChildAt(i2);
                    int childStart2 = this.mIsVertical ? child2.getTop() : child2.getLeft();
                    if (i2 == 0) {
                        selectedStart = childStart2;
                    }
                    if (childStart2 >= start) {
                        selectedPosition = firstPosition + i2;
                        selectedStart = childStart2;
                        break;
                    }
                    i2++;
                }
            }
        } else {
            selectedPosition = toPosition;
            View selected = getChildAt(selectedPosition - this.mFirstPosition);
            if (this.mIsVertical) {
                selectedStart = selected.getTop();
            } else {
                selectedStart = selected.getLeft();
            }
        }
        this.mResurrectToPosition = -1;
        this.mTouchMode = -1;
        reportScrollStateChange(0);
        this.mSpecificStart = selectedStart;
        int selectedPosition2 = lookForSelectablePosition(selectedPosition, down);
        if (selectedPosition2 < firstPosition || selectedPosition2 > getLastVisiblePosition()) {
            selectedPosition2 = -1;
        } else {
            this.mLayoutMode = 4;
            updateSelectorState();
            setSelectionInt(selectedPosition2);
            invokeOnItemScrollListener();
        }
        if (selectedPosition2 >= 0) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: 0000 */
    public boolean resurrectSelectionIfNeeded() {
        if (this.mSelectedPosition >= 0 || !resurrectSelection()) {
            return false;
        }
        updateSelectorState();
        return true;
    }

    private int getChildWidthMeasureSpec(LayoutParams lp) {
        if (!this.mIsVertical && lp.width == -2) {
            return MeasureSpec.makeMeasureSpec(0, 0);
        }
        if (this.mIsVertical) {
            return MeasureSpec.makeMeasureSpec((getWidth() - getPaddingLeft()) - getPaddingRight(), UCCore.VERIFY_POLICY_QUICK);
        }
        return MeasureSpec.makeMeasureSpec(lp.width, UCCore.VERIFY_POLICY_QUICK);
    }

    private int getChildHeightMeasureSpec(LayoutParams lp) {
        if (this.mIsVertical && lp.height == -2) {
            return MeasureSpec.makeMeasureSpec(0, 0);
        }
        if (!this.mIsVertical) {
            return MeasureSpec.makeMeasureSpec((getHeight() - getPaddingTop()) - getPaddingBottom(), UCCore.VERIFY_POLICY_QUICK);
        }
        return MeasureSpec.makeMeasureSpec(lp.height, UCCore.VERIFY_POLICY_QUICK);
    }

    private void measureChild(View child) {
        measureChild(child, (LayoutParams) child.getLayoutParams());
    }

    private void measureChild(View child, LayoutParams lp) {
        child.measure(getChildWidthMeasureSpec(lp), getChildHeightMeasureSpec(lp));
    }

    private void relayoutMeasuredChild(View child) {
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childLeft = getPaddingLeft();
        int childTop = child.getTop();
        child.layout(childLeft, childTop, childLeft + w, childTop + h);
    }

    private void measureScrapChild(View scrapChild, int position, int secondaryMeasureSpec) {
        int widthMeasureSpec;
        int heightMeasureSpec;
        LayoutParams lp = (LayoutParams) scrapChild.getLayoutParams();
        if (lp == null) {
            lp = generateDefaultLayoutParams();
            scrapChild.setLayoutParams(lp);
        }
        lp.viewType = this.mAdapter.getItemViewType(position);
        lp.forceAdd = true;
        if (this.mIsVertical) {
            widthMeasureSpec = secondaryMeasureSpec;
            heightMeasureSpec = getChildHeightMeasureSpec(lp);
        } else {
            widthMeasureSpec = getChildWidthMeasureSpec(lp);
            heightMeasureSpec = secondaryMeasureSpec;
        }
        scrapChild.measure(widthMeasureSpec, heightMeasureSpec);
    }

    private int measureHeightOfChildren(int widthMeasureSpec, int startPosition, int endPosition, int maxHeight, int disallowPartialChildPosition) {
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return paddingTop + paddingBottom;
        }
        int returnedHeight = paddingTop + paddingBottom;
        int itemMargin = this.mItemMargin;
        int prevHeightWithoutPartialChild = 0;
        if (endPosition == -1) {
            endPosition = adapter.getCount() - 1;
        }
        r recycleBin = this.mRecycler;
        boolean shouldRecycle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        int i = startPosition;
        while (i <= endPosition) {
            View child = obtainView(i, isScrap);
            measureScrapChild(child, i, widthMeasureSpec);
            if (i > 0) {
                returnedHeight += itemMargin;
            }
            if (shouldRecycle) {
                recycleBin.a(child, -1);
            }
            returnedHeight += child.getMeasuredHeight();
            if (returnedHeight < maxHeight) {
                if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                    prevHeightWithoutPartialChild = returnedHeight;
                }
                i++;
            } else if (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevHeightWithoutPartialChild <= 0 || returnedHeight == maxHeight) {
                return maxHeight;
            } else {
                return prevHeightWithoutPartialChild;
            }
        }
        return returnedHeight;
    }

    private int measureWidthOfChildren(int heightMeasureSpec, int startPosition, int endPosition, int maxWidth, int disallowPartialChildPosition) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        ListAdapter adapter = this.mAdapter;
        if (adapter == null) {
            return paddingLeft + paddingRight;
        }
        int returnedWidth = paddingLeft + paddingRight;
        int itemMargin = this.mItemMargin;
        int prevWidthWithoutPartialChild = 0;
        if (endPosition == -1) {
            endPosition = adapter.getCount() - 1;
        }
        r recycleBin = this.mRecycler;
        boolean shouldRecycle = recycleOnMeasure();
        boolean[] isScrap = this.mIsScrap;
        int i = startPosition;
        while (i <= endPosition) {
            View child = obtainView(i, isScrap);
            measureScrapChild(child, i, heightMeasureSpec);
            if (i > 0) {
                returnedWidth += itemMargin;
            }
            if (shouldRecycle) {
                recycleBin.a(child, -1);
            }
            returnedWidth += child.getMeasuredHeight();
            if (returnedWidth < maxWidth) {
                if (disallowPartialChildPosition >= 0 && i >= disallowPartialChildPosition) {
                    prevWidthWithoutPartialChild = returnedWidth;
                }
                i++;
            } else if (disallowPartialChildPosition < 0 || i <= disallowPartialChildPosition || prevWidthWithoutPartialChild <= 0 || returnedWidth == maxWidth) {
                return maxWidth;
            } else {
                return prevWidthWithoutPartialChild;
            }
        }
        return returnedWidth;
    }

    private View makeAndAddView(int position, int offset, boolean flow, boolean selected) {
        int top;
        int left;
        if (this.mIsVertical) {
            top = offset;
            left = getPaddingLeft();
        } else {
            top = getPaddingTop();
            left = offset;
        }
        if (!this.mDataChanged) {
            View activeChild = this.mRecycler.b(position);
            if (activeChild != null) {
                setupChild(activeChild, position, top, left, flow, selected, true);
                return activeChild;
            }
        }
        View child = obtainView(position, this.mIsScrap);
        setupChild(child, position, top, left, flow, selected, this.mIsScrap[0]);
        return child;
    }

    @TargetApi(11)
    private void setupChild(View child, int position, int top, int left, boolean flow, boolean selected, boolean recycled) {
        int childTop;
        int childLeft;
        boolean isSelected = selected && shouldShowSelector();
        boolean updateChildSelected = isSelected != child.isSelected();
        int touchMode = this.mTouchMode;
        boolean isPressed = touchMode > 0 && touchMode < 3 && this.mMotionPosition == position;
        boolean updateChildPressed = isPressed != child.isPressed();
        boolean needToMeasure = !recycled || updateChildSelected || child.isLayoutRequested();
        LayoutParams lp = (LayoutParams) child.getLayoutParams();
        if (lp == null) {
            lp = generateDefaultLayoutParams();
        }
        lp.viewType = this.mAdapter.getItemViewType(position);
        if (!recycled || lp.forceAdd) {
            lp.forceAdd = false;
            addViewInLayout(child, flow ? -1 : 0, lp, true);
        } else {
            attachViewToParent(child, flow ? -1 : 0, lp);
        }
        if (updateChildSelected) {
            child.setSelected(isSelected);
        }
        if (updateChildPressed) {
            child.setPressed(isPressed);
        }
        if (!(this.mChoiceMode.compareTo(ChoiceMode.NONE) == 0 || this.mCheckStates == null)) {
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(this.mCheckStates.get(position));
            } else if (getContext().getApplicationInfo().targetSdkVersion >= 11) {
                child.setActivated(this.mCheckStates.get(position));
            }
        }
        if (needToMeasure) {
            measureChild(child, lp);
        } else {
            cleanupLayoutState(child);
        }
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        if (!this.mIsVertical || flow) {
            childTop = top;
        } else {
            childTop = top - h;
        }
        if (this.mIsVertical || flow) {
            childLeft = left;
        } else {
            childLeft = left - w;
        }
        if (needToMeasure) {
            child.layout(childLeft, childTop, childLeft + w, childTop + h);
            return;
        }
        child.offsetLeftAndRight(childLeft - child.getLeft());
        child.offsetTopAndBottom(childTop - child.getTop());
    }

    /* access modifiers changed from: 0000 */
    public void fillGap(boolean down) {
        int end;
        int firstStart;
        int offset;
        int lastEnd;
        int offset2;
        try {
            int childCount = getChildCount();
            if (down) {
                int paddingStart = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
                if (this.mIsVertical) {
                    lastEnd = getChildAt(childCount - 1).getBottom();
                } else {
                    lastEnd = getChildAt(childCount - 1).getRight();
                }
                if (childCount > 0) {
                    offset2 = lastEnd + this.mItemMargin;
                } else {
                    offset2 = paddingStart;
                }
                fillAfter(this.mFirstPosition + childCount, offset2);
                correctTooHigh(getChildCount());
                return;
            }
            if (this.mIsVertical) {
                end = getHeight() - getPaddingBottom();
                firstStart = getChildAt(0).getTop();
            } else {
                end = getWidth() - getPaddingRight();
                firstStart = getChildAt(0).getLeft();
            }
            if (childCount > 0) {
                offset = firstStart - this.mItemMargin;
            } else {
                offset = end;
            }
            fillBefore(this.mFirstPosition - 1, offset);
            correctTooLow(getChildCount());
        } catch (Exception e) {
            AuiLogger.mtBizReport("AUHorizontalListView", "Fill_Gap_Exception");
        }
    }

    private View fillBefore(int pos, int nextOffset) {
        boolean isSelected;
        View selectedView = null;
        int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
        while (nextOffset > start && pos >= 0) {
            if (pos == this.mSelectedPosition) {
                isSelected = true;
            } else {
                isSelected = false;
            }
            View child = makeAndAddView(pos, nextOffset, false, isSelected);
            if (this.mIsVertical) {
                nextOffset = child.getTop() - this.mItemMargin;
            } else {
                nextOffset = child.getLeft() - this.mItemMargin;
            }
            if (isSelected) {
                selectedView = child;
            }
            pos--;
        }
        this.mFirstPosition = pos + 1;
        return selectedView;
    }

    private View fillAfter(int pos, int nextOffset) {
        View selectedView = null;
        int end = this.mIsVertical ? getHeight() - getPaddingBottom() : getWidth() - getPaddingRight();
        while (nextOffset < end && pos < this.mItemCount) {
            boolean selected = pos == this.mSelectedPosition;
            View child = makeAndAddView(pos, nextOffset, true, selected);
            if (this.mIsVertical) {
                nextOffset = child.getBottom() + this.mItemMargin;
            } else {
                nextOffset = child.getRight() + this.mItemMargin;
            }
            if (selected) {
                selectedView = child;
            }
            pos++;
        }
        return selectedView;
    }

    private View fillSpecific(int position, int offset) {
        int offsetBefore;
        int offsetAfter;
        boolean tempIsSelected = position == this.mSelectedPosition;
        View temp = makeAndAddView(position, offset, true, tempIsSelected);
        this.mFirstPosition = position;
        int itemMargin = this.mItemMargin;
        if (this.mIsVertical) {
            offsetBefore = temp.getTop() - itemMargin;
        } else {
            offsetBefore = temp.getLeft() - itemMargin;
        }
        View before = fillBefore(position - 1, offsetBefore);
        adjustViewsStartOrEnd();
        if (this.mIsVertical) {
            offsetAfter = temp.getBottom() + itemMargin;
        } else {
            offsetAfter = temp.getRight() + itemMargin;
        }
        View after = fillAfter(position + 1, offsetAfter);
        int childCount = getChildCount();
        if (childCount > 0) {
            correctTooHigh(childCount);
        }
        if (tempIsSelected) {
            return temp;
        }
        if (before != null) {
            return before;
        }
        return after;
    }

    private View fillFromOffset(int nextOffset) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mSelectedPosition);
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillAfter(this.mFirstPosition, nextOffset);
    }

    private View fillFromMiddle(int start, int end) {
        int size = end - start;
        int position = reconcileSelectedPosition();
        View selected = makeAndAddView(position, start, true, true);
        this.mFirstPosition = position;
        if (this.mIsVertical) {
            int selectedHeight = selected.getMeasuredHeight();
            if (selectedHeight <= size) {
                selected.offsetTopAndBottom((size - selectedHeight) / 2);
            }
        } else {
            int selectedWidth = selected.getMeasuredWidth();
            if (selectedWidth <= size) {
                selected.offsetLeftAndRight((size - selectedWidth) / 2);
            }
        }
        fillBeforeAndAfter(selected, position);
        correctTooHigh(getChildCount());
        return selected;
    }

    private void fillBeforeAndAfter(View selected, int position) {
        int offsetBefore;
        int offsetAfter;
        int itemMargin = this.mItemMargin;
        if (this.mIsVertical) {
            offsetBefore = selected.getTop() - itemMargin;
        } else {
            offsetBefore = selected.getLeft() - itemMargin;
        }
        fillBefore(position - 1, offsetBefore);
        adjustViewsStartOrEnd();
        if (this.mIsVertical) {
            offsetAfter = selected.getBottom() + itemMargin;
        } else {
            offsetAfter = selected.getRight() + itemMargin;
        }
        fillAfter(position + 1, offsetAfter);
    }

    private View fillFromSelection(int selectedTop, int start, int end) {
        int selectedPosition = this.mSelectedPosition;
        View selected = makeAndAddView(selectedPosition, selectedTop, true, true);
        int selectedStart = this.mIsVertical ? selected.getTop() : selected.getLeft();
        int selectedEnd = this.mIsVertical ? selected.getBottom() : selected.getRight();
        if (selectedEnd > end) {
            selected.offsetTopAndBottom(-Math.min(selectedStart - start, selectedEnd - end));
        } else if (selectedStart < start) {
            selected.offsetTopAndBottom(Math.min(start - selectedStart, end - selectedEnd));
        }
        fillBeforeAndAfter(selected, selectedPosition);
        correctTooHigh(getChildCount());
        return selected;
    }

    private void correctTooHigh(int childCount) {
        int lastEnd;
        if ((this.mFirstPosition + childCount) - 1 == this.mItemCount - 1 && childCount != 0) {
            View lastChild = getChildAt(childCount - 1);
            if (this.mIsVertical) {
                lastEnd = lastChild.getBottom();
            } else {
                lastEnd = lastChild.getRight();
            }
            int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
            int endOffset = (this.mIsVertical ? getHeight() - getPaddingBottom() : getWidth() - getPaddingRight()) - lastEnd;
            View firstChild = getChildAt(0);
            int firstStart = this.mIsVertical ? firstChild.getTop() : firstChild.getLeft();
            if (endOffset <= 0) {
                return;
            }
            if (this.mFirstPosition > 0 || firstStart < start) {
                if (this.mFirstPosition == 0) {
                    endOffset = Math.min(endOffset, start - firstStart);
                }
                offsetChildren(endOffset);
                if (this.mFirstPosition > 0) {
                    fillBefore(this.mFirstPosition - 1, (this.mIsVertical ? firstChild.getTop() : firstChild.getLeft()) - this.mItemMargin);
                    adjustViewsStartOrEnd();
                }
            }
        }
    }

    private void correctTooLow(int childCount) {
        int end;
        if (this.mFirstPosition == 0 && childCount != 0) {
            View first = getChildAt(0);
            int firstStart = this.mIsVertical ? first.getTop() : first.getLeft();
            int start = this.mIsVertical ? getPaddingTop() : getPaddingLeft();
            if (this.mIsVertical) {
                end = getHeight() - getPaddingBottom();
            } else {
                end = getWidth() - getPaddingRight();
            }
            int startOffset = firstStart - start;
            View last = getChildAt(childCount - 1);
            int lastEnd = this.mIsVertical ? last.getBottom() : last.getRight();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (startOffset <= 0) {
                return;
            }
            if (lastPosition < this.mItemCount - 1 || lastEnd > end) {
                if (lastPosition == this.mItemCount - 1) {
                    startOffset = Math.min(startOffset, lastEnd - end);
                }
                offsetChildren(-startOffset);
                if (lastPosition < this.mItemCount - 1) {
                    fillAfter(lastPosition + 1, this.mItemMargin + (this.mIsVertical ? last.getBottom() : last.getRight()));
                    adjustViewsStartOrEnd();
                }
            } else if (lastPosition == this.mItemCount - 1) {
                adjustViewsStartOrEnd();
            }
        }
    }

    private void adjustViewsStartOrEnd() {
        int delta;
        if (getChildCount() != 0) {
            View firstChild = getChildAt(0);
            if (this.mIsVertical) {
                delta = (firstChild.getTop() - getPaddingTop()) - this.mItemMargin;
            } else {
                delta = (firstChild.getLeft() - getPaddingLeft()) - this.mItemMargin;
            }
            if (delta < 0) {
                delta = 0;
            }
            if (delta != 0) {
                offsetChildren(-delta);
            }
        }
    }

    @TargetApi(14)
    private SparseBooleanArray cloneCheckStates() {
        if (this.mCheckStates == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 14) {
            return this.mCheckStates.clone();
        }
        SparseBooleanArray checkedStates = new SparseBooleanArray();
        for (int i = 0; i < this.mCheckStates.size(); i++) {
            checkedStates.put(this.mCheckStates.keyAt(i), this.mCheckStates.valueAt(i));
        }
        return checkedStates;
    }

    /* JADX WARNING: CFG modification limit reached, blocks count: 139 */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0061, code lost:
        if (r6 != false) goto L_0x0067;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0063, code lost:
        if (r11 != false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0065, code lost:
        if (r5 != false) goto L_0x004c;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0067, code lost:
        r4 = r4 - 1;
        r12 = r4;
        r11 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x006c, code lost:
        r12 = -1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int findSyncPosition() {
        /*
            r17 = this;
            r0 = r17
            int r9 = r0.mItemCount
            if (r9 != 0) goto L_0x0008
            r12 = -1
        L_0x0007:
            return r12
        L_0x0008:
            r0 = r17
            long r7 = r0.mSyncRowId
            r13 = -9223372036854775808
            int r13 = (r7 > r13 ? 1 : (r7 == r13 ? 0 : -1))
            if (r13 != 0) goto L_0x0014
            r12 = -1
            goto L_0x0007
        L_0x0014:
            r0 = r17
            int r12 = r0.mSyncPosition
            r13 = 0
            int r12 = java.lang.Math.max(r13, r12)
            int r13 = r9 + -1
            int r12 = java.lang.Math.min(r13, r12)
            long r13 = android.os.SystemClock.uptimeMillis()
            r15 = 100
            long r2 = r13 + r15
            r4 = r12
            r10 = r12
            r11 = 0
            r0 = r17
            android.widget.ListAdapter r1 = r0.mAdapter
            if (r1 != 0) goto L_0x004c
            r12 = -1
            goto L_0x0007
        L_0x0036:
            int r13 = r9 + -1
            if (r10 != r13) goto L_0x005d
            r6 = 1
        L_0x003b:
            if (r4 != 0) goto L_0x005f
            r5 = 1
        L_0x003e:
            if (r6 == 0) goto L_0x0042
            if (r5 != 0) goto L_0x006c
        L_0x0042:
            if (r5 != 0) goto L_0x0048
            if (r11 == 0) goto L_0x0061
            if (r6 != 0) goto L_0x0061
        L_0x0048:
            int r10 = r10 + 1
            r12 = r10
            r11 = 0
        L_0x004c:
            long r13 = android.os.SystemClock.uptimeMillis()
            int r13 = (r13 > r2 ? 1 : (r13 == r2 ? 0 : -1))
            if (r13 > 0) goto L_0x006c
            long r13 = r1.getItemId(r12)
            int r13 = (r13 > r7 ? 1 : (r13 == r7 ? 0 : -1))
            if (r13 != 0) goto L_0x0036
            goto L_0x0007
        L_0x005d:
            r6 = 0
            goto L_0x003b
        L_0x005f:
            r5 = 0
            goto L_0x003e
        L_0x0061:
            if (r6 != 0) goto L_0x0067
            if (r11 != 0) goto L_0x004c
            if (r5 != 0) goto L_0x004c
        L_0x0067:
            int r4 = r4 + -1
            r12 = r4
            r11 = 1
            goto L_0x004c
        L_0x006c:
            r12 = -1
            goto L_0x0007
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.mobile.antui.basic.AUHorizontalListView.findSyncPosition():int");
    }

    @TargetApi(16)
    private View obtainView(int position, boolean[] isScrap) {
        View child;
        isScrap[0] = false;
        View scrapView = this.mRecycler.c(position);
        if (scrapView != null) {
            return scrapView;
        }
        View scrapView2 = this.mRecycler.d(position);
        if (scrapView2 != null) {
            child = this.mAdapter.getView(position, scrapView2, this);
            if (child != scrapView2) {
                this.mRecycler.a(scrapView2, position);
            } else {
                isScrap[0] = true;
            }
        } else {
            child = this.mAdapter.getView(position, null, this);
        }
        if (ViewCompat.getImportantForAccessibility(child) == 0) {
            ViewCompat.setImportantForAccessibility(child, 1);
        }
        if (this.mHasStableIds) {
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (lp == null) {
                lp = generateDefaultLayoutParams();
            } else if (!checkLayoutParams(lp)) {
                lp = generateLayoutParams((android.view.ViewGroup.LayoutParams) lp);
            }
            lp.id = this.mAdapter.getItemId(position);
            child.setLayoutParams(lp);
        }
        if (this.mAccessibilityDelegate == null) {
            this.mAccessibilityDelegate = new p(this, 0);
        }
        ViewCompat.setAccessibilityDelegate(child, this.mAccessibilityDelegate);
        return child;
    }

    /* access modifiers changed from: 0000 */
    public void resetState() {
        this.mScroller.forceFinished(true);
        removeAllViewsInLayout();
        this.mSelectedStart = 0;
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mNeedSync = false;
        this.mPendingSync = null;
        this.mOldSelectedPosition = -1;
        this.mOldSelectedRowId = Long.MIN_VALUE;
        this.mOverScroll = 0;
        setSelectedPositionInt(-1);
        setNextSelectedPositionInt(-1);
        this.mSelectorPosition = -1;
        this.mSelectorRect.setEmpty();
        invalidate();
    }

    /* access modifiers changed from: private */
    public void rememberSyncState() {
        if (getChildCount() != 0) {
            this.mNeedSync = true;
            if (this.mSelectedPosition >= 0) {
                View child = getChildAt(this.mSelectedPosition - this.mFirstPosition);
                this.mSyncRowId = this.mNextSelectedRowId;
                this.mSyncPosition = this.mNextSelectedPosition;
                if (child != null) {
                    this.mSpecificStart = this.mIsVertical ? child.getTop() : child.getLeft();
                }
                this.mSyncMode = 0;
                return;
            }
            View child2 = getChildAt(0);
            ListAdapter adapter = getAdapter();
            if (this.mFirstPosition < 0 || this.mFirstPosition >= adapter.getCount()) {
                this.mSyncRowId = -1;
            } else {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            }
            this.mSyncPosition = this.mFirstPosition;
            if (child2 != null) {
                this.mSpecificStart = this.mIsVertical ? child2.getTop() : child2.getLeft();
            }
            this.mSyncMode = 1;
        }
    }

    private ContextMenuInfo createContextMenuInfo(View view, int position, long id) {
        return new AdapterContextMenuInfo(view, position, id);
    }

    @TargetApi(11)
    private void updateOnScreenCheckedViews() {
        int firstPos = this.mFirstPosition;
        int count = getChildCount();
        boolean useActivated = getContext().getApplicationInfo().targetSdkVersion >= 11;
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int position = firstPos + i;
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(this.mCheckStates.get(position));
            } else if (useActivated) {
                child.setActivated(this.mCheckStates.get(position));
            }
        }
    }

    public boolean performItemClick(View view, int position, long id) {
        boolean checked = true;
        boolean checkedStateChanged = false;
        if (this.mChoiceMode.compareTo(ChoiceMode.MULTIPLE) == 0) {
            if (this.mCheckStates.get(position, false)) {
                checked = false;
            }
            this.mCheckStates.put(position, checked);
            if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                if (checked) {
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                } else {
                    this.mCheckedIdStates.delete(this.mAdapter.getItemId(position));
                }
            }
            if (checked) {
                this.mCheckedItemCount++;
            } else {
                this.mCheckedItemCount--;
            }
            checkedStateChanged = true;
        } else if (this.mChoiceMode.compareTo(ChoiceMode.SINGLE) == 0) {
            if (!this.mCheckStates.get(position, false)) {
                this.mCheckStates.clear();
                this.mCheckStates.put(position, true);
                if (this.mCheckedIdStates != null && this.mAdapter.hasStableIds()) {
                    this.mCheckedIdStates.clear();
                    this.mCheckedIdStates.put(this.mAdapter.getItemId(position), Integer.valueOf(position));
                }
                this.mCheckedItemCount = 1;
            } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
                this.mCheckedItemCount = 0;
            }
            checkedStateChanged = true;
        }
        if (checkedStateChanged) {
            updateOnScreenCheckedViews();
        }
        return super.performItemClick(view, position, id);
    }

    /* access modifiers changed from: private */
    public boolean performLongPress(View child, int longPressPosition, long longPressId) {
        boolean handled = false;
        OnItemLongClickListener listener = getOnItemLongClickListener();
        if (listener != null) {
            handled = listener.onItemLongClick(this, child, longPressPosition, longPressId);
        }
        if (!handled) {
            this.mContextMenuInfo = createContextMenuInfo(child, longPressPosition, longPressId);
            handled = super.showContextMenuForChild(this);
        }
        if (handled) {
            performHapticFeedback(0);
        }
        return handled;
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateDefaultLayoutParams() {
        if (this.mIsVertical) {
            return new LayoutParams(-1, -2);
        }
        return new LayoutParams(-2, -1);
    }

    /* access modifiers changed from: protected */
    public LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        return new LayoutParams(lp);
    }

    /* access modifiers changed from: protected */
    public boolean checkLayoutParams(android.view.ViewGroup.LayoutParams lp) {
        return lp instanceof LayoutParams;
    }

    public android.view.ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    /* access modifiers changed from: protected */
    public ContextMenuInfo getContextMenuInfo() {
        return this.mContextMenuInfo;
    }

    public Parcelable onSaveInstanceState() {
        boolean haveChildren;
        SavedState ss = new SavedState(super.onSaveInstanceState());
        if (this.mPendingSync != null) {
            ss.a = this.mPendingSync.a;
            ss.b = this.mPendingSync.b;
            ss.c = this.mPendingSync.c;
            ss.d = this.mPendingSync.d;
            ss.e = this.mPendingSync.e;
        } else {
            if (getChildCount() <= 0 || this.mItemCount <= 0) {
                haveChildren = false;
            } else {
                haveChildren = true;
            }
            long selectedId = getSelectedItemId();
            ss.a = selectedId;
            ss.e = getHeight();
            if (selectedId >= 0) {
                ss.c = this.mSelectedStart;
                ss.d = getSelectedItemPosition();
                ss.b = -1;
            } else if (!haveChildren || this.mFirstPosition <= 0) {
                ss.c = 0;
                ss.b = -1;
                ss.d = 0;
            } else {
                View child = getChildAt(0);
                ss.c = this.mIsVertical ? child.getTop() : child.getLeft();
                int firstPos = this.mFirstPosition;
                if (firstPos >= this.mItemCount) {
                    firstPos = this.mItemCount - 1;
                }
                ss.d = firstPos;
                ss.b = this.mAdapter.getItemId(firstPos);
            }
            if (this.mCheckStates != null) {
                ss.g = cloneCheckStates();
            }
            if (this.mCheckedIdStates != null) {
                LongSparseArray idState = new LongSparseArray();
                int count = this.mCheckedIdStates.size();
                for (int i = 0; i < count; i++) {
                    idState.put(this.mCheckedIdStates.keyAt(i), this.mCheckedIdStates.valueAt(i));
                }
                ss.h = idState;
            }
            ss.f = this.mCheckedItemCount;
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = (long) ss.e;
        if (ss.a >= 0) {
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.a;
            this.mSyncPosition = ss.d;
            this.mSpecificStart = ss.c;
            this.mSyncMode = 0;
        } else if (ss.b >= 0) {
            setSelectedPositionInt(-1);
            setNextSelectedPositionInt(-1);
            this.mSelectorPosition = -1;
            this.mNeedSync = true;
            this.mPendingSync = ss;
            this.mSyncRowId = ss.b;
            this.mSyncPosition = ss.d;
            this.mSpecificStart = ss.c;
            this.mSyncMode = 1;
        }
        if (ss.g != null) {
            this.mCheckStates = ss.g;
        }
        if (ss.h != null) {
            this.mCheckedIdStates = ss.h;
        }
        this.mCheckedItemCount = ss.f;
        requestLayout();
    }

    public void setEmptyView(View emptyView) {
        super.setEmptyView(emptyView);
        this.mEmptyView = emptyView;
        updateEmptyStatus();
    }

    public void setFocusable(boolean focusable) {
        boolean empty;
        boolean z = true;
        ListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        this.mDesiredFocusableState = focusable;
        if (!focusable) {
            this.mDesiredFocusableInTouchModeState = false;
        }
        if (!focusable || empty) {
            z = false;
        }
        super.setFocusable(z);
    }

    public void setFocusableInTouchMode(boolean focusable) {
        boolean empty;
        boolean z = true;
        ListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            empty = true;
        } else {
            empty = false;
        }
        this.mDesiredFocusableInTouchModeState = focusable;
        if (focusable) {
            this.mDesiredFocusableState = true;
        }
        if (!focusable || empty) {
            z = false;
        }
        super.setFocusableInTouchMode(z);
    }

    /* access modifiers changed from: private */
    public void checkFocus() {
        boolean focusable;
        boolean z;
        boolean z2 = true;
        ListAdapter adapter = getAdapter();
        if (adapter == null || adapter.getCount() <= 0) {
            focusable = false;
        } else {
            focusable = true;
        }
        if (!focusable || !this.mDesiredFocusableInTouchModeState) {
            z = false;
        } else {
            z = true;
        }
        super.setFocusableInTouchMode(z);
        if (!focusable || !this.mDesiredFocusableState) {
            z2 = false;
        }
        super.setFocusable(z2);
        if (this.mEmptyView != null) {
            updateEmptyStatus();
        }
    }

    private void updateEmptyStatus() {
        if (this.mAdapter == null || this.mAdapter.isEmpty()) {
            if (this.mEmptyView != null) {
                this.mEmptyView.setVisibility(0);
                setVisibility(8);
            } else {
                setVisibility(0);
            }
            if (this.mDataChanged) {
                layout(getLeft(), getTop(), getRight(), getBottom());
                return;
            }
            return;
        }
        if (this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
        }
        setVisibility(0);
    }
}
