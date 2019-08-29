package com.autonavi.minimap.widget.draglistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Checkable;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DragSortListView extends ListView {
    private static final int DRAGGING = 4;
    public static final int DRAG_NEG_X = 2;
    public static final int DRAG_NEG_Y = 8;
    public static final int DRAG_POS_X = 1;
    public static final int DRAG_POS_Y = 4;
    private static final int DROPPING = 2;
    private static final int IDLE = 0;
    private static final int NO_CANCEL = 0;
    private static final int ON_INTERCEPT_TOUCH_EVENT = 2;
    private static final int ON_TOUCH_EVENT = 1;
    private static final int REMOVING = 1;
    private static final int STOPPED = 3;
    private static final int sCacheSize = 3;
    private AdapterWrapper mAdapterWrapper;
    private boolean mAnimate = false;
    /* access modifiers changed from: private */
    public boolean mBlockLayoutRequests = false;
    private MotionEvent mCancelEvent;
    private int mCancelMethod = 0;
    private HeightCache mChildHeightCache = new HeightCache(3);
    private float mCurrFloatAlpha = 1.0f;
    private int mDownScrollStartY;
    /* access modifiers changed from: private */
    public float mDownScrollStartYF;
    private int mDragDeltaX;
    /* access modifiers changed from: private */
    public int mDragDeltaY;
    /* access modifiers changed from: private */
    public float mDragDownScrollHeight;
    private float mDragDownScrollStartFrac = 0.33333334f;
    private boolean mDragEnabled = true;
    private int mDragFlags = 0;
    private DragListener mDragListener;
    private DragScroller mDragScroller;
    private DragSortTracker mDragSortTracker;
    private int mDragStartY;
    /* access modifiers changed from: private */
    public int mDragState = 0;
    /* access modifiers changed from: private */
    public float mDragUpScrollHeight;
    private float mDragUpScrollStartFrac = 0.33333334f;
    private DropAnimator mDropAnimator;
    private DropListener mDropListener;
    /* access modifiers changed from: private */
    public int mFirstExpPos = -1;
    private float mFloatAlpha = 1.0f;
    /* access modifiers changed from: private */
    public Point mFloatLoc = new Point();
    /* access modifiers changed from: private */
    public int mFloatPos = -1;
    private View mFloatView;
    /* access modifiers changed from: private */
    public int mFloatViewHeight;
    /* access modifiers changed from: private */
    public int mFloatViewHeightHalf;
    private FloatViewManager mFloatViewManager = null;
    /* access modifiers changed from: private */
    public int mFloatViewMid;
    private boolean mFloatViewOnMeasured = false;
    private boolean mIgnoreTouchEvent = false;
    private boolean mInTouchEvent = false;
    /* access modifiers changed from: private */
    public int mItemHeightCollapsed = 1;
    private boolean mLastCallWasIntercept = false;
    private int mLastX;
    /* access modifiers changed from: private */
    public int mLastY;
    private boolean mListViewIntercepted = false;
    /* access modifiers changed from: private */
    public float mMaxScrollSpeed = 0.5f;
    private DataSetObserver mObserver;
    private int mOffsetX;
    private int mOffsetY;
    private RemoveAnimator mRemoveAnimator;
    private RemoveListener mRemoveListener;
    /* access modifiers changed from: private */
    public float mRemoveVelocityX = 0.0f;
    private View[] mSampleViewTypes = new View[1];
    /* access modifiers changed from: private */
    public DragScrollProfile mScrollProfile = new DragScrollProfile() {
        public float getSpeed(float f, long j) {
            return DragSortListView.this.mMaxScrollSpeed * f;
        }
    };
    /* access modifiers changed from: private */
    public int mSecondExpPos = -1;
    private float mSlideFrac = 0.0f;
    private float mSlideRegionFrac = 0.25f;
    /* access modifiers changed from: private */
    public int mSrcPos = -1;
    private Point mTouchLoc = new Point();
    private boolean mTrackDragSort = false;
    private int mUpScrollStartY;
    /* access modifiers changed from: private */
    public float mUpScrollStartYF;
    /* access modifiers changed from: private */
    public boolean mUseRemoveVelocity;
    private int mWidthMeasureSpec = 0;
    private int mX;
    /* access modifiers changed from: private */
    public int mY;

    class AdapterWrapper extends BaseAdapter {
        private ListAdapter mAdapter;

        public AdapterWrapper(ListAdapter listAdapter) {
            this.mAdapter = listAdapter;
            this.mAdapter.registerDataSetObserver(new DataSetObserver(DragSortListView.this) {
                public void onChanged() {
                    AdapterWrapper.this.notifyDataSetChanged();
                }

                public void onInvalidated() {
                    AdapterWrapper.this.notifyDataSetInvalidated();
                }
            });
        }

        public ListAdapter getAdapter() {
            return this.mAdapter;
        }

        public long getItemId(int i) {
            return this.mAdapter.getItemId(i);
        }

        public Object getItem(int i) {
            return this.mAdapter.getItem(i);
        }

        public int getCount() {
            return this.mAdapter.getCount();
        }

        public boolean areAllItemsEnabled() {
            return this.mAdapter.areAllItemsEnabled();
        }

        public boolean isEnabled(int i) {
            return this.mAdapter.isEnabled(i);
        }

        public int getItemViewType(int i) {
            return this.mAdapter.getItemViewType(i);
        }

        public int getViewTypeCount() {
            return this.mAdapter.getViewTypeCount();
        }

        public boolean hasStableIds() {
            return this.mAdapter.hasStableIds();
        }

        public boolean isEmpty() {
            return this.mAdapter.isEmpty();
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            DragSortItemView dragSortItemView;
            DragSortItemView dragSortItemView2;
            if (view == null || !(view instanceof DragSortItemView)) {
                View view2 = this.mAdapter.getView(i, null, DragSortListView.this);
                if (view2 instanceof Checkable) {
                    dragSortItemView2 = new DragSortItemViewCheckable(DragSortListView.this.getContext());
                } else {
                    dragSortItemView2 = new DragSortItemView(DragSortListView.this.getContext());
                }
                dragSortItemView2.setLayoutParams(new LayoutParams(-1, -2));
                dragSortItemView2.addView(view2);
                dragSortItemView = dragSortItemView2;
            } else {
                dragSortItemView = (DragSortItemView) view;
                View childAt = dragSortItemView.getChildAt(0);
                View view3 = this.mAdapter.getView(i, childAt, DragSortListView.this);
                if (view3 != childAt) {
                    if (childAt != null) {
                        dragSortItemView.removeViewAt(0);
                    }
                    dragSortItemView.addView(view3);
                }
            }
            DragSortListView dragSortListView = DragSortListView.this;
            dragSortListView.adjustItem(i + dragSortListView.getHeaderViewsCount(), dragSortItemView, true);
            return dragSortItemView;
        }
    }

    public interface DragListener {
        void drag(int i, int i2);
    }

    public interface DragScrollProfile {
        float getSpeed(float f, long j);
    }

    class DragScroller implements Runnable {
        public static final int DOWN = 1;
        public static final int STOP = -1;
        public static final int UP = 0;
        private float dt;
        private int dy;
        private boolean mAbort;
        private long mCurrTime;
        private long mPrevTime;
        private float mScrollSpeed;
        private boolean mScrolling = false;
        private int scrollDir;
        private long tStart;

        public boolean isScrolling() {
            return this.mScrolling;
        }

        public int getScrollDir() {
            if (this.mScrolling) {
                return this.scrollDir;
            }
            return -1;
        }

        public DragScroller() {
        }

        public void startScrolling(int i) {
            if (!this.mScrolling) {
                this.mAbort = false;
                this.mScrolling = true;
                this.tStart = SystemClock.uptimeMillis();
                this.mPrevTime = this.tStart;
                this.scrollDir = i;
                DragSortListView.this.post(this);
            }
        }

        public void stopScrolling(boolean z) {
            if (z) {
                DragSortListView.this.removeCallbacks(this);
                this.mScrolling = false;
                return;
            }
            this.mAbort = true;
        }

        public void run() {
            if (this.mAbort) {
                this.mScrolling = false;
                return;
            }
            int firstVisiblePosition = DragSortListView.this.getFirstVisiblePosition();
            int lastVisiblePosition = DragSortListView.this.getLastVisiblePosition();
            int count = DragSortListView.this.getCount();
            int paddingTop = DragSortListView.this.getPaddingTop();
            int height = (DragSortListView.this.getHeight() - paddingTop) - DragSortListView.this.getPaddingBottom();
            int min = Math.min(DragSortListView.this.mY, DragSortListView.this.mFloatViewMid + DragSortListView.this.mFloatViewHeightHalf);
            int max = Math.max(DragSortListView.this.mY, DragSortListView.this.mFloatViewMid - DragSortListView.this.mFloatViewHeightHalf);
            if (this.scrollDir == 0) {
                View childAt = DragSortListView.this.getChildAt(0);
                if (childAt == null) {
                    this.mScrolling = false;
                    return;
                } else if (firstVisiblePosition == 0 && childAt.getTop() == paddingTop) {
                    this.mScrolling = false;
                    return;
                } else {
                    this.mScrollSpeed = DragSortListView.this.mScrollProfile.getSpeed((DragSortListView.this.mUpScrollStartYF - ((float) max)) / DragSortListView.this.mDragUpScrollHeight, this.mPrevTime);
                }
            } else {
                View childAt2 = DragSortListView.this.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (childAt2 == null) {
                    this.mScrolling = false;
                    return;
                } else if (lastVisiblePosition != count - 1 || childAt2.getBottom() > height + paddingTop) {
                    this.mScrollSpeed = -DragSortListView.this.mScrollProfile.getSpeed((((float) min) - DragSortListView.this.mDownScrollStartYF) / DragSortListView.this.mDragDownScrollHeight, this.mPrevTime);
                } else {
                    this.mScrolling = false;
                    return;
                }
            }
            this.mCurrTime = SystemClock.uptimeMillis();
            this.dt = (float) (this.mCurrTime - this.mPrevTime);
            this.dy = Math.round(this.mScrollSpeed * this.dt);
            if (this.dy >= 0) {
                this.dy = Math.min(height, this.dy);
                lastVisiblePosition = firstVisiblePosition;
            } else {
                this.dy = Math.max(-height, this.dy);
            }
            View childAt3 = DragSortListView.this.getChildAt(lastVisiblePosition - firstVisiblePosition);
            int top = childAt3.getTop() + this.dy;
            if (lastVisiblePosition == 0 && top > paddingTop) {
                top = paddingTop;
            }
            DragSortListView.this.mBlockLayoutRequests = true;
            DragSortListView.this.setSelectionFromTop(lastVisiblePosition, top - paddingTop);
            DragSortListView.this.layoutChildren();
            DragSortListView.this.invalidate();
            DragSortListView.this.mBlockLayoutRequests = false;
            DragSortListView.this.doDragFloatView(lastVisiblePosition, childAt3, false);
            this.mPrevTime = this.mCurrTime;
            DragSortListView.this.post(this);
        }
    }

    public interface DragSortListener extends DragListener, DropListener, RemoveListener {
    }

    class DragSortTracker {
        StringBuilder a = new StringBuilder();
        File b = new File(Environment.getExternalStorageDirectory(), "dslv_state.txt");
        private int mNumFlushes = 0;
        private int mNumInBuffer = 0;
        private boolean mTracking = false;

        public DragSortTracker() {
            if (!this.b.exists()) {
                try {
                    this.b.createNewFile();
                } catch (IOException e) {
                    e.getMessage();
                }
            }
        }

        public void startTracking() {
            this.a.append("<DSLVStates>\n");
            this.mNumFlushes = 0;
            this.mTracking = true;
        }

        public void appendState() {
            if (this.mTracking) {
                this.a.append("<DSLVState>\n");
                int childCount = DragSortListView.this.getChildCount();
                int firstVisiblePosition = DragSortListView.this.getFirstVisiblePosition();
                this.a.append("    <Positions>");
                for (int i = 0; i < childCount; i++) {
                    StringBuilder sb = this.a;
                    sb.append(firstVisiblePosition + i);
                    sb.append(",");
                }
                this.a.append("</Positions>\n");
                this.a.append("    <Tops>");
                for (int i2 = 0; i2 < childCount; i2++) {
                    StringBuilder sb2 = this.a;
                    sb2.append(DragSortListView.this.getChildAt(i2).getTop());
                    sb2.append(",");
                }
                this.a.append("</Tops>\n");
                this.a.append("    <Bottoms>");
                for (int i3 = 0; i3 < childCount; i3++) {
                    StringBuilder sb3 = this.a;
                    sb3.append(DragSortListView.this.getChildAt(i3).getBottom());
                    sb3.append(",");
                }
                this.a.append("</Bottoms>\n");
                StringBuilder sb4 = this.a;
                sb4.append("    <FirstExpPos>");
                sb4.append(DragSortListView.this.mFirstExpPos);
                sb4.append("</FirstExpPos>\n");
                StringBuilder sb5 = this.a;
                sb5.append("    <FirstExpBlankHeight>");
                sb5.append(DragSortListView.this.getItemHeight(DragSortListView.this.mFirstExpPos) - DragSortListView.this.getChildHeight(DragSortListView.this.mFirstExpPos));
                sb5.append("</FirstExpBlankHeight>\n");
                StringBuilder sb6 = this.a;
                sb6.append("    <SecondExpPos>");
                sb6.append(DragSortListView.this.mSecondExpPos);
                sb6.append("</SecondExpPos>\n");
                StringBuilder sb7 = this.a;
                sb7.append("    <SecondExpBlankHeight>");
                sb7.append(DragSortListView.this.getItemHeight(DragSortListView.this.mSecondExpPos) - DragSortListView.this.getChildHeight(DragSortListView.this.mSecondExpPos));
                sb7.append("</SecondExpBlankHeight>\n");
                StringBuilder sb8 = this.a;
                sb8.append("    <SrcPos>");
                sb8.append(DragSortListView.this.mSrcPos);
                sb8.append("</SrcPos>\n");
                StringBuilder sb9 = this.a;
                sb9.append("    <SrcHeight>");
                sb9.append(DragSortListView.this.mFloatViewHeight + DragSortListView.this.getDividerHeight());
                sb9.append("</SrcHeight>\n");
                StringBuilder sb10 = this.a;
                sb10.append("    <ViewHeight>");
                sb10.append(DragSortListView.this.getHeight());
                sb10.append("</ViewHeight>\n");
                StringBuilder sb11 = this.a;
                sb11.append("    <LastY>");
                sb11.append(DragSortListView.this.mLastY);
                sb11.append("</LastY>\n");
                StringBuilder sb12 = this.a;
                sb12.append("    <FloatY>");
                sb12.append(DragSortListView.this.mFloatViewMid);
                sb12.append("</FloatY>\n");
                this.a.append("    <ShuffleEdges>");
                for (int i4 = 0; i4 < childCount; i4++) {
                    StringBuilder sb13 = this.a;
                    sb13.append(DragSortListView.this.getShuffleEdge(firstVisiblePosition + i4, DragSortListView.this.getChildAt(i4).getTop()));
                    sb13.append(",");
                }
                this.a.append("</ShuffleEdges>\n");
                this.a.append("</DSLVState>\n");
                this.mNumInBuffer++;
                if (this.mNumInBuffer > 1000) {
                    flush();
                    this.mNumInBuffer = 0;
                }
            }
        }

        public void flush() {
            if (this.mTracking) {
                try {
                    FileWriter fileWriter = new FileWriter(this.b, this.mNumFlushes != 0);
                    fileWriter.write(this.a.toString());
                    this.a.delete(0, this.a.length());
                    fileWriter.flush();
                    fileWriter.close();
                    this.mNumFlushes++;
                } catch (IOException unused) {
                }
            }
        }

        public void stopTracking() {
            if (this.mTracking) {
                this.a.append("</DSLVStates>\n");
                flush();
                this.mTracking = false;
            }
        }
    }

    class DropAnimator extends SmoothAnimator {
        private int mDropPos;
        private float mInitDeltaX;
        private float mInitDeltaY;
        private int srcPos;

        public DropAnimator(float f, int i) {
            super(f, i);
        }

        public void onStart() {
            this.mDropPos = DragSortListView.this.mFloatPos;
            this.srcPos = DragSortListView.this.mSrcPos;
            DragSortListView.this.mDragState = 2;
            this.mInitDeltaY = (float) (DragSortListView.this.mFloatLoc.y - getTargetY());
            this.mInitDeltaX = (float) (DragSortListView.this.mFloatLoc.x - DragSortListView.this.getPaddingLeft());
        }

        private int getTargetY() {
            int access$1000 = (DragSortListView.this.mItemHeightCollapsed + DragSortListView.this.getDividerHeight()) / 2;
            View childAt = DragSortListView.this.getChildAt(this.mDropPos - DragSortListView.this.getFirstVisiblePosition());
            if (childAt == null) {
                cancel();
                return -1;
            } else if (this.mDropPos == this.srcPos) {
                return childAt.getTop();
            } else {
                if (this.mDropPos < this.srcPos) {
                    return childAt.getTop() - access$1000;
                }
                return (childAt.getBottom() + access$1000) - DragSortListView.this.mFloatViewHeight;
            }
        }

        public void onUpdate(float f, float f2) {
            int targetY = getTargetY();
            float paddingLeft = (float) (DragSortListView.this.mFloatLoc.x - DragSortListView.this.getPaddingLeft());
            float f3 = 1.0f - f2;
            if (f3 < Math.abs(((float) (DragSortListView.this.mFloatLoc.y - targetY)) / this.mInitDeltaY) || f3 < Math.abs(paddingLeft / this.mInitDeltaX)) {
                DragSortListView.this.mFloatLoc.y = targetY + ((int) (this.mInitDeltaY * f3));
                DragSortListView.this.mFloatLoc.x = DragSortListView.this.getPaddingLeft() + ((int) (this.mInitDeltaX * f3));
                DragSortListView.this.doDragFloatView(true);
            }
        }

        public void onStop() {
            DragSortListView.this.dropFloatView();
        }
    }

    public interface DropListener {
        void drop(int i, int i2);
    }

    public interface FloatViewManager {
        int getFloatViewHeightOffset();

        View onCreateFloatView(int i);

        void onDestroyFloatView(View view);

        void onDragFloatView(View view, Point point, Point point2);
    }

    static class HeightCache {
        private SparseIntArray mMap;
        private int mMaxSize;
        private ArrayList<Integer> mOrder;

        public HeightCache(int i) {
            this.mMap = new SparseIntArray(i);
            this.mOrder = new ArrayList<>(i);
            this.mMaxSize = i;
        }

        public void add(int i, int i2) {
            int i3 = this.mMap.get(i, -1);
            if (i3 != i2) {
                if (i3 != -1) {
                    this.mOrder.remove(Integer.valueOf(i));
                } else if (this.mMap.size() == this.mMaxSize) {
                    this.mMap.delete(this.mOrder.remove(0).intValue());
                }
                this.mMap.put(i, i2);
                this.mOrder.add(Integer.valueOf(i));
            }
        }

        public int get(int i) {
            return this.mMap.get(i, -1);
        }

        public void clear() {
            this.mMap.clear();
            this.mOrder.clear();
        }
    }

    class LiftAnimator extends SmoothAnimator {
        private float mFinalDragDeltaY;
        private float mInitDragDeltaY;

        public LiftAnimator(float f, int i) {
            super(f, i);
        }

        public void onStart() {
            this.mInitDragDeltaY = (float) DragSortListView.this.mDragDeltaY;
            this.mFinalDragDeltaY = (float) DragSortListView.this.mFloatViewHeightHalf;
        }

        public void onUpdate(float f, float f2) {
            if (DragSortListView.this.mDragState != 4) {
                cancel();
                return;
            }
            DragSortListView.this.mDragDeltaY = (int) ((this.mFinalDragDeltaY * f2) + ((1.0f - f2) * this.mInitDragDeltaY));
            DragSortListView.this.mFloatLoc.y = DragSortListView.this.mY - DragSortListView.this.mDragDeltaY;
            DragSortListView.this.doDragFloatView(true);
        }
    }

    class RemoveAnimator extends SmoothAnimator {
        private int mFirstChildHeight = -1;
        private int mFirstPos;
        private float mFirstStartBlank;
        private float mFloatLocX;
        private int mSecondChildHeight = -1;
        private int mSecondPos;
        private float mSecondStartBlank;

        public RemoveAnimator(float f, int i) {
            super(f, i);
        }

        public void onStart() {
            int i = -1;
            this.mFirstChildHeight = -1;
            this.mSecondChildHeight = -1;
            this.mFirstPos = DragSortListView.this.mFirstExpPos;
            this.mSecondPos = DragSortListView.this.mSecondExpPos;
            DragSortListView.this.mDragState = 1;
            this.mFloatLocX = (float) DragSortListView.this.mFloatLoc.x;
            if (DragSortListView.this.mUseRemoveVelocity) {
                float width = ((float) DragSortListView.this.getWidth()) * 2.0f;
                if (DragSortListView.this.mRemoveVelocityX == 0.0f) {
                    DragSortListView dragSortListView = DragSortListView.this;
                    if (this.mFloatLocX >= 0.0f) {
                        i = 1;
                    }
                    dragSortListView.mRemoveVelocityX = ((float) i) * width;
                    return;
                }
                float f = width * 2.0f;
                if (DragSortListView.this.mRemoveVelocityX < 0.0f) {
                    float f2 = -f;
                    if (DragSortListView.this.mRemoveVelocityX > f2) {
                        DragSortListView.this.mRemoveVelocityX = f2;
                        return;
                    }
                }
                if (DragSortListView.this.mRemoveVelocityX > 0.0f && DragSortListView.this.mRemoveVelocityX < f) {
                    DragSortListView.this.mRemoveVelocityX = f;
                }
                return;
            }
            DragSortListView.this.destroyFloatView();
        }

        public void onUpdate(float f, float f2) {
            float f3 = 1.0f - f2;
            int firstVisiblePosition = DragSortListView.this.getFirstVisiblePosition();
            View childAt = DragSortListView.this.getChildAt(this.mFirstPos - firstVisiblePosition);
            if (DragSortListView.this.mUseRemoveVelocity) {
                float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / 1000.0f;
                if (uptimeMillis != 0.0f) {
                    float access$1600 = DragSortListView.this.mRemoveVelocityX * uptimeMillis;
                    int width = DragSortListView.this.getWidth();
                    float f4 = ((float) (DragSortListView.this.mRemoveVelocityX > 0.0f ? 1 : -1)) * uptimeMillis;
                    float f5 = (float) width;
                    DragSortListView.this.mRemoveVelocityX = DragSortListView.this.mRemoveVelocityX + (f4 * f5);
                    this.mFloatLocX += access$1600;
                    DragSortListView.this.mFloatLoc.x = (int) this.mFloatLocX;
                    if (this.mFloatLocX < f5 && this.mFloatLocX > ((float) (-width))) {
                        this.mStartTime = SystemClock.uptimeMillis();
                        DragSortListView.this.doDragFloatView(true);
                        return;
                    }
                } else {
                    return;
                }
            }
            if (childAt != null) {
                if (this.mFirstChildHeight == -1) {
                    this.mFirstChildHeight = DragSortListView.this.getChildHeight(this.mFirstPos, childAt, false);
                    this.mFirstStartBlank = (float) (childAt.getHeight() - this.mFirstChildHeight);
                }
                int max = Math.max((int) (this.mFirstStartBlank * f3), 1);
                ViewGroup.LayoutParams layoutParams = childAt.getLayoutParams();
                layoutParams.height = this.mFirstChildHeight + max;
                childAt.setLayoutParams(layoutParams);
            }
            if (this.mSecondPos != this.mFirstPos) {
                View childAt2 = DragSortListView.this.getChildAt(this.mSecondPos - firstVisiblePosition);
                if (childAt2 != null) {
                    if (this.mSecondChildHeight == -1) {
                        this.mSecondChildHeight = DragSortListView.this.getChildHeight(this.mSecondPos, childAt2, false);
                        this.mSecondStartBlank = (float) (childAt2.getHeight() - this.mSecondChildHeight);
                    }
                    int max2 = Math.max((int) (f3 * this.mSecondStartBlank), 1);
                    ViewGroup.LayoutParams layoutParams2 = childAt2.getLayoutParams();
                    layoutParams2.height = this.mSecondChildHeight + max2;
                    childAt2.setLayoutParams(layoutParams2);
                }
            }
        }

        public void onStop() {
            DragSortListView.this.doRemoveItem();
        }
    }

    public interface RemoveListener {
        void remove(int i);
    }

    class SmoothAnimator implements Runnable {
        private float mA;
        private float mAlpha;
        private float mB = (this.mAlpha / ((this.mAlpha - 1.0f) * 2.0f));
        private float mC = (1.0f / (1.0f - this.mAlpha));
        private boolean mCanceled;
        private float mD;
        private float mDurationF;
        protected long mStartTime;

        public void onStart() {
        }

        public void onStop() {
        }

        public void onUpdate(float f, float f2) {
        }

        public SmoothAnimator(float f, int i) {
            this.mAlpha = f;
            this.mDurationF = (float) i;
            float f2 = 1.0f / ((this.mAlpha * 2.0f) * (1.0f - this.mAlpha));
            this.mD = f2;
            this.mA = f2;
        }

        public float transform(float f) {
            if (f < this.mAlpha) {
                return this.mA * f * f;
            }
            if (f < 1.0f - this.mAlpha) {
                return this.mB + (this.mC * f);
            }
            float f2 = f - 1.0f;
            return 1.0f - ((this.mD * f2) * f2);
        }

        public void start() {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mCanceled = false;
            onStart();
            DragSortListView.this.post(this);
        }

        public void cancel() {
            this.mCanceled = true;
        }

        public void run() {
            if (!this.mCanceled) {
                float uptimeMillis = ((float) (SystemClock.uptimeMillis() - this.mStartTime)) / this.mDurationF;
                if (uptimeMillis >= 1.0f) {
                    onUpdate(1.0f, 1.0f);
                    onStop();
                    return;
                }
                onUpdate(uptimeMillis, transform(uptimeMillis));
                DragSortListView.this.post(this);
            }
        }
    }

    private static int rotate(int i, int i2, int i3, int i4) {
        int i5 = i4 - i3;
        int i6 = i + i2;
        return i6 < i3 ? i6 + i5 : i6 >= i4 ? i6 - i5 : i6;
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public DragSortListView(Context context, AttributeSet attributeSet) {
        int i;
        // AttributeSet attributeSet2 = attributeSet;
        super(context, attributeSet);
        int i2 = 150;
        if (attributeSet2 != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet2, R.styleable.DragSortListView, 0, 0);
            this.mItemHeightCollapsed = Math.max(1, obtainStyledAttributes.getDimensionPixelSize(R.styleable.DragSortListView_collapsed_height, 1));
            this.mTrackDragSort = obtainStyledAttributes.getBoolean(R.styleable.DragSortListView_track_drag_sort, false);
            if (this.mTrackDragSort) {
                this.mDragSortTracker = new DragSortTracker();
            }
            this.mFloatAlpha = obtainStyledAttributes.getFloat(R.styleable.DragSortListView_float_alpha, this.mFloatAlpha);
            this.mCurrFloatAlpha = this.mFloatAlpha;
            this.mDragEnabled = obtainStyledAttributes.getBoolean(R.styleable.DragSortListView_drag_enabled, this.mDragEnabled);
            this.mSlideRegionFrac = Math.max(0.0f, Math.min(1.0f, 1.0f - obtainStyledAttributes.getFloat(R.styleable.DragSortListView_slide_shuffle_speed, 0.75f)));
            this.mAnimate = this.mSlideRegionFrac > 0.0f;
            setDragScrollStart(obtainStyledAttributes.getFloat(R.styleable.DragSortListView_drag_scroll_start, this.mDragUpScrollStartFrac));
            this.mMaxScrollSpeed = obtainStyledAttributes.getFloat(R.styleable.DragSortListView_max_drag_scroll_speed, this.mMaxScrollSpeed);
            int i3 = obtainStyledAttributes.getInt(R.styleable.DragSortListView_remove_animation_duration, 150);
            i = obtainStyledAttributes.getInt(R.styleable.DragSortListView_drop_animation_duration, 150);
            if (obtainStyledAttributes.getBoolean(R.styleable.DragSortListView_use_default_controller, true)) {
                boolean z = obtainStyledAttributes.getBoolean(R.styleable.DragSortListView_remove_enabled, false);
                int i4 = obtainStyledAttributes.getInt(R.styleable.DragSortListView_remove_mode, 1);
                boolean z2 = obtainStyledAttributes.getBoolean(R.styleable.DragSortListView_sort_enabled, true);
                int i5 = obtainStyledAttributes.getInt(R.styleable.DragSortListView_drag_start_mode, 0);
                int resourceId = obtainStyledAttributes.getResourceId(R.styleable.DragSortListView_drag_handle_id, 0);
                int resourceId2 = obtainStyledAttributes.getResourceId(R.styleable.DragSortListView_fling_handle_id, 0);
                int resourceId3 = obtainStyledAttributes.getResourceId(R.styleable.DragSortListView_click_remove_id, 0);
                int color = obtainStyledAttributes.getColor(R.styleable.DragSortListView_float_background_color, -16777216);
                DragSortController dragSortController = new DragSortController(this, resourceId, i5, i4, resourceId3, resourceId2);
                dragSortController.setRemoveEnabled(z);
                dragSortController.setSortEnabled(z2);
                dragSortController.setBackgroundColor(color);
                this.mFloatViewManager = dragSortController;
                setOnTouchListener(dragSortController);
            }
            obtainStyledAttributes.recycle();
            i2 = i3;
        } else {
            i = 150;
        }
        this.mDragScroller = new DragScroller();
        if (i2 > 0) {
            this.mRemoveAnimator = new RemoveAnimator(0.5f, i2);
        }
        if (i > 0) {
            this.mDropAnimator = new DropAnimator(0.5f, i);
        }
        this.mCancelEvent = MotionEvent.obtain(0, 0, 3, 0.0f, 0.0f, 0.0f, 0.0f, 0, 0.0f, 0.0f, 0, 0);
        this.mObserver = new DataSetObserver() {
            private void cancel() {
                if (DragSortListView.this.mDragState == 4) {
                    DragSortListView.this.cancelDrag();
                }
            }

            public void onChanged() {
                cancel();
            }

            public void onInvalidated() {
                cancel();
            }
        };
    }

    public void setFloatAlpha(float f) {
        this.mCurrFloatAlpha = f;
    }

    public float getFloatAlpha() {
        return this.mCurrFloatAlpha;
    }

    public void setMaxScrollSpeed(float f) {
        this.mMaxScrollSpeed = f;
    }

    public void setAdapter(ListAdapter listAdapter) {
        if (listAdapter != null) {
            this.mAdapterWrapper = new AdapterWrapper(listAdapter);
            listAdapter.registerDataSetObserver(this.mObserver);
            if (listAdapter instanceof DropListener) {
                setDropListener((DropListener) listAdapter);
            }
            if (listAdapter instanceof DragListener) {
                setDragListener((DragListener) listAdapter);
            }
            if (listAdapter instanceof RemoveListener) {
                setRemoveListener((RemoveListener) listAdapter);
            }
        } else {
            this.mAdapterWrapper = null;
        }
        super.setAdapter(this.mAdapterWrapper);
    }

    public ListAdapter getInputAdapter() {
        if (this.mAdapterWrapper == null) {
            return null;
        }
        return this.mAdapterWrapper.getAdapter();
    }

    private void drawDivider(int i, Canvas canvas) {
        int i2;
        int i3;
        Drawable divider = getDivider();
        int dividerHeight = getDividerHeight();
        if (divider != null && dividerHeight != 0) {
            ViewGroup viewGroup = (ViewGroup) getChildAt(i - getFirstVisiblePosition());
            if (viewGroup != null) {
                int paddingLeft = getPaddingLeft();
                int width = getWidth() - getPaddingRight();
                int height = viewGroup.getChildAt(0).getHeight();
                if (i > this.mSrcPos) {
                    i2 = viewGroup.getTop() + height;
                    i3 = dividerHeight + i2;
                } else {
                    int bottom = viewGroup.getBottom() - height;
                    int i4 = bottom - dividerHeight;
                    i3 = bottom;
                    i2 = i4;
                }
                canvas.save();
                canvas.clipRect(paddingLeft, i2, width, i3);
                divider.setBounds(paddingLeft, i2, width, i3);
                divider.draw(canvas);
                canvas.restore();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        float f;
        super.dispatchDraw(canvas);
        if (this.mDragState != 0) {
            if (this.mFirstExpPos != this.mSrcPos) {
                drawDivider(this.mFirstExpPos, canvas);
            }
            if (!(this.mSecondExpPos == this.mFirstExpPos || this.mSecondExpPos == this.mSrcPos)) {
                drawDivider(this.mSecondExpPos, canvas);
            }
        }
        if (this.mFloatView != null) {
            int width = this.mFloatView.getWidth();
            int height = this.mFloatView.getHeight();
            int i = this.mFloatLoc.x;
            int width2 = getWidth();
            if (i < 0) {
                i = -i;
            }
            if (i < width2) {
                float f2 = ((float) (width2 - i)) / ((float) width2);
                f = f2 * f2;
            } else {
                f = 0.0f;
            }
            canvas.save();
            canvas.translate((float) this.mFloatLoc.x, ((float) this.mFloatLoc.y) + (((float) this.mFloatViewManager.getFloatViewHeightOffset()) / 2.0f));
            canvas.clipRect(0, 0, width, height);
            Canvas canvas2 = canvas;
            canvas2.saveLayerAlpha(0.0f, 0.0f, (float) width, (float) height, (int) (this.mCurrFloatAlpha * 255.0f * f), 31);
            this.mFloatView.draw(canvas);
            canvas.restore();
            canvas.restore();
        }
    }

    /* access modifiers changed from: private */
    public int getItemHeight(int i) {
        View childAt = getChildAt(i - getFirstVisiblePosition());
        if (childAt != null) {
            return childAt.getHeight();
        }
        return calcItemHeight(i, getChildHeight(i));
    }

    private void printPosData() {
        StringBuilder sb = new StringBuilder("mSrcPos=");
        sb.append(this.mSrcPos);
        sb.append(" mFirstExpPos=");
        sb.append(this.mFirstExpPos);
        sb.append(" mSecondExpPos=");
        sb.append(this.mSecondExpPos);
    }

    /* access modifiers changed from: private */
    public int getShuffleEdge(int i, int i2) {
        int i3;
        int headerViewsCount = getHeaderViewsCount();
        int footerViewsCount = getFooterViewsCount();
        if (i <= headerViewsCount || i >= getCount() - footerViewsCount) {
            return i2;
        }
        int dividerHeight = getDividerHeight();
        int i4 = this.mFloatViewHeight - this.mItemHeightCollapsed;
        int childHeight = getChildHeight(i);
        int itemHeight = getItemHeight(i);
        if (this.mSecondExpPos <= this.mSrcPos) {
            if (i == this.mSecondExpPos && this.mFirstExpPos != this.mSecondExpPos) {
                i2 = i == this.mSrcPos ? (i2 + itemHeight) - this.mFloatViewHeight : (i2 + (itemHeight - childHeight)) - i4;
            } else if (i > this.mSecondExpPos && i <= this.mSrcPos) {
                i2 -= i4;
            }
        } else if (i > this.mSrcPos && i <= this.mFirstExpPos) {
            i2 += i4;
        } else if (i == this.mSecondExpPos && this.mFirstExpPos != this.mSecondExpPos) {
            i2 += itemHeight - childHeight;
        }
        if (i <= this.mSrcPos) {
            i3 = i2 + (((this.mFloatViewHeight - dividerHeight) - getChildHeight(i - 1)) / 2);
        } else {
            i3 = i2 + (((childHeight - dividerHeight) - this.mFloatViewHeight) / 2);
        }
        return i3;
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0081  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00d0  */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x00d6  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x00fd  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x010e  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean updatePositions() {
        /*
            r14 = this;
            int r0 = r14.getFirstVisiblePosition()
            int r1 = r14.mFirstExpPos
            int r2 = r1 - r0
            android.view.View r2 = r14.getChildAt(r2)
            if (r2 != 0) goto L_0x001b
            int r1 = r14.getChildCount()
            int r1 = r1 / 2
            int r1 = r1 + r0
            int r0 = r1 - r0
            android.view.View r2 = r14.getChildAt(r0)
        L_0x001b:
            int r0 = r2.getTop()
            int r2 = r2.getHeight()
            int r3 = r14.getShuffleEdge(r1, r0)
            int r4 = r14.getDividerHeight()
            int r5 = r14.mFloatViewMid
            if (r5 >= r3) goto L_0x004c
        L_0x002f:
            if (r1 < 0) goto L_0x004a
            int r1 = r1 + -1
            int r2 = r14.getItemHeight(r1)
            if (r1 != 0) goto L_0x003c
            int r0 = r0 - r4
            int r0 = r0 - r2
            goto L_0x006e
        L_0x003c:
            int r2 = r2 + r4
            int r0 = r0 - r2
            int r2 = r14.getShuffleEdge(r1, r0)
            int r5 = r14.mFloatViewMid
            if (r5 >= r2) goto L_0x0048
            r3 = r2
            goto L_0x002f
        L_0x0048:
            r0 = r2
            goto L_0x006e
        L_0x004a:
            r0 = r3
            goto L_0x006e
        L_0x004c:
            int r5 = r14.getCount()
        L_0x0050:
            if (r1 >= r5) goto L_0x004a
            int r6 = r5 + -1
            if (r1 != r6) goto L_0x0059
            int r0 = r0 + r4
            int r0 = r0 + r2
            goto L_0x006e
        L_0x0059:
            int r2 = r2 + r4
            int r0 = r0 + r2
            int r2 = r1 + 1
            int r6 = r14.getItemHeight(r2)
            int r7 = r14.getShuffleEdge(r2, r0)
            int r8 = r14.mFloatViewMid
            if (r8 < r7) goto L_0x006d
            r1 = r2
            r2 = r6
            r3 = r7
            goto L_0x0050
        L_0x006d:
            r0 = r7
        L_0x006e:
            int r2 = r14.getHeaderViewsCount()
            int r4 = r14.getFooterViewsCount()
            r5 = 0
            int r6 = r14.mFirstExpPos
            int r7 = r14.mSecondExpPos
            float r8 = r14.mSlideFrac
            boolean r9 = r14.mAnimate
            if (r9 == 0) goto L_0x00c7
            int r9 = r0 - r3
            int r9 = java.lang.Math.abs(r9)
            int r10 = r14.mFloatViewMid
            if (r10 >= r0) goto L_0x008e
            r13 = r3
            r3 = r0
            r0 = r13
        L_0x008e:
            float r10 = r14.mSlideRegionFrac
            r11 = 1056964608(0x3f000000, float:0.5)
            float r10 = r10 * r11
            float r9 = (float) r9
            float r10 = r10 * r9
            int r9 = (int) r10
            float r10 = (float) r9
            int r0 = r0 + r9
            int r9 = r3 - r9
            int r12 = r14.mFloatViewMid
            if (r12 >= r0) goto L_0x00b0
            int r3 = r1 + -1
            r14.mFirstExpPos = r3
            r14.mSecondExpPos = r1
            int r3 = r14.mFloatViewMid
            int r0 = r0 - r3
            float r0 = (float) r0
            float r0 = r0 * r11
            float r0 = r0 / r10
            r14.mSlideFrac = r0
            goto L_0x00cb
        L_0x00b0:
            int r0 = r14.mFloatViewMid
            if (r0 < r9) goto L_0x00c7
            r14.mFirstExpPos = r1
            int r0 = r1 + 1
            r14.mSecondExpPos = r0
            r0 = 1065353216(0x3f800000, float:1.0)
            int r9 = r14.mFloatViewMid
            int r3 = r3 - r9
            float r3 = (float) r3
            float r3 = r3 / r10
            float r3 = r3 + r0
            float r3 = r3 * r11
            r14.mSlideFrac = r3
            goto L_0x00cb
        L_0x00c7:
            r14.mFirstExpPos = r1
            r14.mSecondExpPos = r1
        L_0x00cb:
            int r0 = r14.mFirstExpPos
            r3 = 1
            if (r0 >= r2) goto L_0x00d6
            r14.mFirstExpPos = r2
            r14.mSecondExpPos = r2
            r1 = r2
            goto L_0x00ea
        L_0x00d6:
            int r0 = r14.mSecondExpPos
            int r9 = r14.getCount()
            int r9 = r9 - r4
            if (r0 < r9) goto L_0x00ea
            int r0 = r14.getCount()
            int r0 = r0 - r4
            int r1 = r0 + -1
            r14.mFirstExpPos = r1
            r14.mSecondExpPos = r1
        L_0x00ea:
            int r0 = r14.mFirstExpPos
            if (r0 != r6) goto L_0x00f8
            int r0 = r14.mSecondExpPos
            if (r0 != r7) goto L_0x00f8
            float r0 = r14.mSlideFrac
            int r0 = (r0 > r8 ? 1 : (r0 == r8 ? 0 : -1))
            if (r0 == 0) goto L_0x00f9
        L_0x00f8:
            r5 = 1
        L_0x00f9:
            int r0 = r14.mFloatPos
            if (r1 == r0) goto L_0x010e
            com.autonavi.minimap.widget.draglistview.DragSortListView$DragListener r0 = r14.mDragListener
            if (r0 == 0) goto L_0x010b
            com.autonavi.minimap.widget.draglistview.DragSortListView$DragListener r0 = r14.mDragListener
            int r4 = r14.mFloatPos
            int r4 = r4 - r2
            int r2 = r1 - r2
            r0.drag(r4, r2)
        L_0x010b:
            r14.mFloatPos = r1
            goto L_0x010f
        L_0x010e:
            r3 = r5
        L_0x010f:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.widget.draglistview.DragSortListView.updatePositions():boolean");
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mTrackDragSort) {
            this.mDragSortTracker.appendState();
        }
    }

    public void removeItem(int i) {
        this.mUseRemoveVelocity = false;
        removeItem(i, 0.0f);
    }

    public void removeItem(int i, float f) {
        if (this.mDragState == 0 || this.mDragState == 4) {
            if (this.mDragState == 0) {
                this.mSrcPos = getHeaderViewsCount() + i;
                this.mFirstExpPos = this.mSrcPos;
                this.mSecondExpPos = this.mSrcPos;
                this.mFloatPos = this.mSrcPos;
                View childAt = getChildAt(this.mSrcPos - getFirstVisiblePosition());
                if (childAt != null) {
                    childAt.setVisibility(4);
                }
            }
            this.mDragState = 1;
            this.mRemoveVelocityX = f;
            if (this.mInTouchEvent) {
                switch (this.mCancelMethod) {
                    case 1:
                        super.onTouchEvent(this.mCancelEvent);
                        break;
                    case 2:
                        super.onInterceptTouchEvent(this.mCancelEvent);
                        break;
                }
            }
            if (this.mRemoveAnimator != null) {
                this.mRemoveAnimator.start();
                return;
            }
            doRemoveItem(i);
        }
    }

    public void moveItem(int i, int i2) {
        if (this.mDropListener != null) {
            int count = getInputAdapter().getCount();
            if (i >= 0 && i < count && i2 >= 0 && i2 < count) {
                this.mDropListener.drop(i, i2);
            }
        }
    }

    public void cancelDrag() {
        if (this.mDragState == 4) {
            this.mDragScroller.stopScrolling(true);
            destroyFloatView();
            clearPositions();
            adjustAllItems();
            if (this.mInTouchEvent) {
                this.mDragState = 3;
                return;
            }
            this.mDragState = 0;
        }
    }

    private void clearPositions() {
        this.mSrcPos = -1;
        this.mFirstExpPos = -1;
        this.mSecondExpPos = -1;
        this.mFloatPos = -1;
    }

    /* access modifiers changed from: private */
    public void dropFloatView() {
        this.mDragState = 2;
        if (this.mDropListener != null && this.mFloatPos >= 0 && this.mFloatPos < getCount()) {
            int headerViewsCount = getHeaderViewsCount();
            this.mDropListener.drop(this.mSrcPos - headerViewsCount, this.mFloatPos - headerViewsCount);
        }
        destroyFloatView();
        adjustOnReorder();
        clearPositions();
        adjustAllItems();
        if (this.mInTouchEvent) {
            this.mDragState = 3;
        } else {
            this.mDragState = 0;
        }
    }

    /* access modifiers changed from: private */
    public void doRemoveItem() {
        doRemoveItem(this.mSrcPos - getHeaderViewsCount());
    }

    private void doRemoveItem(int i) {
        this.mDragState = 1;
        if (this.mRemoveListener != null) {
            this.mRemoveListener.remove(i);
        }
        destroyFloatView();
        adjustOnReorder();
        clearPositions();
        if (this.mInTouchEvent) {
            this.mDragState = 3;
        } else {
            this.mDragState = 0;
        }
    }

    private void adjustOnReorder() {
        int firstVisiblePosition = getFirstVisiblePosition();
        if (this.mSrcPos < firstVisiblePosition) {
            int i = 0;
            View childAt = getChildAt(0);
            if (childAt != null) {
                i = childAt.getTop();
            }
            setSelectionFromTop(firstVisiblePosition - 1, i - getPaddingTop());
        }
    }

    public boolean stopDrag(boolean z) {
        this.mUseRemoveVelocity = false;
        return stopDrag(z, 0.0f);
    }

    public boolean stopDragWithVelocity(boolean z, float f) {
        this.mUseRemoveVelocity = true;
        return stopDrag(z, f);
    }

    public boolean stopDrag(boolean z, float f) {
        if (this.mFloatView == null) {
            return false;
        }
        this.mDragScroller.stopScrolling(true);
        if (z) {
            removeItem(this.mSrcPos - getHeaderViewsCount(), f);
        } else if (this.mDropAnimator != null) {
            this.mDropAnimator.start();
        } else {
            dropFloatView();
        }
        if (this.mTrackDragSort) {
            this.mDragSortTracker.stopTracking();
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z = false;
        if (this.mIgnoreTouchEvent) {
            this.mIgnoreTouchEvent = false;
            return false;
        } else if (!this.mDragEnabled) {
            return super.onTouchEvent(motionEvent);
        } else {
            boolean z2 = this.mLastCallWasIntercept;
            this.mLastCallWasIntercept = false;
            if (!z2) {
                saveTouchCoords(motionEvent);
            }
            if (this.mDragState == 4) {
                onDragTouchEvent(motionEvent);
                z = true;
            } else {
                if (this.mDragState == 0 && super.onTouchEvent(motionEvent)) {
                    z = true;
                }
                int action = motionEvent.getAction() & 255;
                if (action == 1 || action == 3) {
                    doActionUpOrCancel();
                } else if (z) {
                    this.mCancelMethod = 1;
                }
            }
            return z;
        }
    }

    private void doActionUpOrCancel() {
        this.mCancelMethod = 0;
        this.mInTouchEvent = false;
        if (this.mDragState == 3) {
            this.mDragState = 0;
        }
        this.mCurrFloatAlpha = this.mFloatAlpha;
        this.mListViewIntercepted = false;
        this.mChildHeightCache.clear();
    }

    private void saveTouchCoords(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            this.mLastX = this.mX;
            this.mLastY = this.mY;
        }
        this.mX = (int) motionEvent.getX();
        this.mY = (int) motionEvent.getY();
        if (action == 0) {
            this.mLastX = this.mX;
            this.mLastY = this.mY;
        }
        this.mOffsetX = ((int) motionEvent.getRawX()) - this.mX;
        this.mOffsetY = ((int) motionEvent.getRawY()) - this.mY;
    }

    public boolean listViewIntercepted() {
        return this.mListViewIntercepted;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (!this.mDragEnabled) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        saveTouchCoords(motionEvent);
        this.mLastCallWasIntercept = true;
        int action = motionEvent.getAction() & 255;
        if (action == 0) {
            if (this.mDragState != 0) {
                this.mIgnoreTouchEvent = true;
                return true;
            }
            this.mInTouchEvent = true;
        }
        if (this.mFloatView != null) {
            z = true;
        } else {
            if (super.onInterceptTouchEvent(motionEvent)) {
                this.mListViewIntercepted = true;
                z = true;
            } else {
                z = false;
            }
            if (action == 1 || action == 3) {
                doActionUpOrCancel();
            } else if (z) {
                this.mCancelMethod = 1;
            } else {
                this.mCancelMethod = 2;
            }
        }
        if (action == 1 || action == 3) {
            this.mInTouchEvent = false;
        }
        return z;
    }

    public void setDragScrollStart(float f) {
        setDragScrollStarts(f, f);
    }

    public void setDragScrollStarts(float f, float f2) {
        if (f2 > 0.5f) {
            this.mDragDownScrollStartFrac = 0.5f;
        } else {
            this.mDragDownScrollStartFrac = f2;
        }
        if (f > 0.5f) {
            this.mDragUpScrollStartFrac = 0.5f;
        } else {
            this.mDragUpScrollStartFrac = f;
        }
        if (getHeight() != 0) {
            updateScrollStarts();
        }
    }

    private void continueDrag(int i, int i2) {
        this.mFloatLoc.x = i - this.mDragDeltaX;
        this.mFloatLoc.y = i2 - this.mDragDeltaY;
        doDragFloatView(true);
        int min = Math.min(i2, this.mFloatViewMid + this.mFloatViewHeightHalf);
        int max = Math.max(i2, this.mFloatViewMid - this.mFloatViewHeightHalf);
        int scrollDir = this.mDragScroller.getScrollDir();
        if (min > this.mLastY && min > this.mDownScrollStartY && scrollDir != 1) {
            if (scrollDir != -1) {
                this.mDragScroller.stopScrolling(true);
            }
            this.mDragScroller.startScrolling(1);
        } else if (max >= this.mLastY || max >= this.mUpScrollStartY || scrollDir == 0) {
            if (max >= this.mUpScrollStartY && min <= this.mDownScrollStartY && this.mDragScroller.isScrolling()) {
                this.mDragScroller.stopScrolling(true);
            }
        } else {
            if (scrollDir != -1) {
                this.mDragScroller.stopScrolling(true);
            }
            this.mDragScroller.startScrolling(0);
        }
    }

    private void updateScrollStarts() {
        int paddingTop = getPaddingTop();
        int height = (getHeight() - paddingTop) - getPaddingBottom();
        float f = (float) height;
        float f2 = (float) paddingTop;
        this.mUpScrollStartYF = (this.mDragUpScrollStartFrac * f) + f2;
        this.mDownScrollStartYF = ((1.0f - this.mDragDownScrollStartFrac) * f) + f2;
        this.mUpScrollStartY = (int) this.mUpScrollStartYF;
        this.mDownScrollStartY = (int) this.mDownScrollStartYF;
        this.mDragUpScrollHeight = this.mUpScrollStartYF - f2;
        this.mDragDownScrollHeight = ((float) (paddingTop + height)) - this.mDownScrollStartYF;
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        updateScrollStarts();
    }

    private void adjustAllItems() {
        int firstVisiblePosition = getFirstVisiblePosition();
        int lastVisiblePosition = getLastVisiblePosition();
        int min = Math.min(lastVisiblePosition - firstVisiblePosition, ((getCount() - 1) - getFooterViewsCount()) - firstVisiblePosition);
        for (int max = Math.max(0, getHeaderViewsCount() - firstVisiblePosition); max <= min; max++) {
            View childAt = getChildAt(max);
            if (childAt != null) {
                adjustItem(firstVisiblePosition + max, childAt, false);
            }
        }
    }

    private void adjustItem(int i) {
        View childAt = getChildAt(i - getFirstVisiblePosition());
        if (childAt != null) {
            adjustItem(i, childAt, false);
        }
    }

    /* access modifiers changed from: private */
    public void adjustItem(int i, View view, boolean z) {
        int i2;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (i == this.mSrcPos || i == this.mFirstExpPos || i == this.mSecondExpPos) {
            i2 = calcItemHeight(i, view, z);
        } else {
            i2 = -2;
        }
        if (i2 != layoutParams.height) {
            layoutParams.height = i2;
            view.setLayoutParams(layoutParams);
        }
        if (i == this.mFirstExpPos || i == this.mSecondExpPos) {
            if (i < this.mSrcPos) {
                ((DragSortItemView) view).setGravity(80);
            } else if (i > this.mSrcPos) {
                ((DragSortItemView) view).setGravity(48);
            }
        }
        int visibility = view.getVisibility();
        int i3 = 0;
        if (i == this.mSrcPos && this.mFloatView != null) {
            i3 = 4;
        }
        if (i3 != visibility) {
            view.setVisibility(i3);
        }
    }

    /* access modifiers changed from: private */
    public int getChildHeight(int i) {
        View view;
        if (i == this.mSrcPos) {
            return 0;
        }
        View childAt = getChildAt(i - getFirstVisiblePosition());
        if (childAt != null) {
            return getChildHeight(i, childAt, false);
        }
        int i2 = this.mChildHeightCache.get(i);
        if (i2 != -1) {
            return i2;
        }
        ListAdapter adapter = getAdapter();
        int itemViewType = adapter.getItemViewType(i);
        int viewTypeCount = adapter.getViewTypeCount();
        if (viewTypeCount != this.mSampleViewTypes.length) {
            this.mSampleViewTypes = new View[viewTypeCount];
        }
        if (itemViewType < 0 || itemViewType >= this.mSampleViewTypes.length) {
            view = adapter.getView(i, null, this);
        } else if (this.mSampleViewTypes[itemViewType] == null) {
            view = adapter.getView(i, null, this);
            this.mSampleViewTypes[itemViewType] = view;
        } else {
            view = adapter.getView(i, this.mSampleViewTypes[itemViewType], this);
        }
        int childHeight = getChildHeight(i, view, true);
        this.mChildHeightCache.add(i, childHeight);
        return childHeight;
    }

    /* access modifiers changed from: private */
    public int getChildHeight(int i, View view, boolean z) {
        if (i == this.mSrcPos) {
            return 0;
        }
        if (i >= getHeaderViewsCount() && i < getCount() - getFooterViewsCount()) {
            view = ((ViewGroup) view).getChildAt(0);
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            return layoutParams.height;
        }
        int height = view.getHeight();
        if (height == 0 || z) {
            measureItem(view);
            height = view.getMeasuredHeight();
        }
        return height;
    }

    private int calcItemHeight(int i, View view, boolean z) {
        return calcItemHeight(i, getChildHeight(i, view, z));
    }

    private int calcItemHeight(int i, int i2) {
        getDividerHeight();
        boolean z = this.mAnimate && this.mFirstExpPos != this.mSecondExpPos;
        int i3 = this.mFloatViewHeight - this.mItemHeightCollapsed;
        int i4 = (int) (this.mSlideFrac * ((float) i3));
        if (i != this.mSrcPos) {
            return i == this.mFirstExpPos ? z ? i2 + i4 : i2 + i3 : i == this.mSecondExpPos ? (i2 + i3) - i4 : i2;
        }
        if (this.mSrcPos == this.mFirstExpPos) {
            if (z) {
                return i4 + this.mItemHeightCollapsed;
            }
            return this.mFloatViewHeight;
        } else if (this.mSrcPos == this.mSecondExpPos) {
            return this.mFloatViewHeight - i4;
        } else {
            return this.mItemHeightCollapsed;
        }
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests) {
            super.requestLayout();
        }
    }

    private int adjustScroll(int i, View view, int i2, int i3) {
        int i4;
        int i5;
        int childHeight = getChildHeight(i);
        int height = view.getHeight();
        int calcItemHeight = calcItemHeight(i, childHeight);
        if (i != this.mSrcPos) {
            i4 = height - childHeight;
            i5 = calcItemHeight - childHeight;
        } else {
            i4 = height;
            i5 = calcItemHeight;
        }
        int i6 = this.mFloatViewHeight;
        if (!(this.mSrcPos == this.mFirstExpPos || this.mSrcPos == this.mSecondExpPos)) {
            i6 -= this.mItemHeightCollapsed;
        }
        if (i <= i2) {
            if (i > this.mFirstExpPos) {
                return 0 + (i6 - i5);
            }
            return 0;
        } else if (i == i3) {
            if (i <= this.mFirstExpPos) {
                return 0 + (i4 - i6);
            }
            return i == this.mSecondExpPos ? 0 + (height - calcItemHeight) : 0 + i4;
        } else if (i <= this.mFirstExpPos) {
            return 0 - i6;
        } else {
            if (i == this.mSecondExpPos) {
                return 0 - i5;
            }
            return 0;
        }
    }

    private void measureItem(View view) {
        int i;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-1, -2);
            view.setLayoutParams(layoutParams);
        }
        int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getListPaddingLeft() + getListPaddingRight(), layoutParams.width);
        if (layoutParams.height > 0) {
            i = MeasureSpec.makeMeasureSpec(layoutParams.height, UCCore.VERIFY_POLICY_QUICK);
        } else {
            i = MeasureSpec.makeMeasureSpec(0, 0);
        }
        view.measure(childMeasureSpec, i);
    }

    private void measureFloatView() {
        if (this.mFloatView != null) {
            measureItem(this.mFloatView);
            int measuredHeight = this.mFloatView.getMeasuredHeight() + this.mFloatViewManager.getFloatViewHeightOffset();
            if (measuredHeight < 0) {
                measuredHeight = 0;
            }
            this.mFloatViewHeight = measuredHeight;
            this.mFloatViewHeightHalf = this.mFloatViewHeight / 2;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        try {
            super.onMeasure(i, i2);
            if (this.mFloatView != null) {
                if (this.mFloatView.isLayoutRequested()) {
                    measureFloatView();
                }
                this.mFloatViewOnMeasured = true;
            }
            this.mWidthMeasureSpec = i;
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: protected */
    public void layoutChildren() {
        super.layoutChildren();
        if (this.mFloatView != null) {
            if (this.mFloatView.isLayoutRequested() && !this.mFloatViewOnMeasured) {
                measureFloatView();
            }
            this.mFloatView.layout(0, 0, this.mFloatView.getMeasuredWidth(), this.mFloatView.getMeasuredHeight());
            this.mFloatViewOnMeasured = false;
        }
    }

    /* access modifiers changed from: protected */
    public boolean onDragTouchEvent(MotionEvent motionEvent) {
        motionEvent.getAction();
        switch (motionEvent.getAction() & 255) {
            case 1:
                if (this.mDragState == 4) {
                    stopDrag(false);
                }
                doActionUpOrCancel();
                break;
            case 2:
                continueDrag((int) motionEvent.getX(), (int) motionEvent.getY());
                break;
            case 3:
                if (this.mDragState == 4) {
                    cancelDrag();
                }
                doActionUpOrCancel();
                break;
        }
        return true;
    }

    public boolean startDrag(int i, int i2, int i3, int i4) {
        if (!this.mInTouchEvent || this.mFloatViewManager == null) {
            return false;
        }
        View onCreateFloatView = this.mFloatViewManager.onCreateFloatView(i);
        if (onCreateFloatView == null) {
            return false;
        }
        return startDrag(i, onCreateFloatView, i2, i3, i4);
    }

    public boolean startDrag(int i, View view, int i2, int i3, int i4) {
        if (this.mDragState != 0 || !this.mInTouchEvent || this.mFloatView != null || view == null || !this.mDragEnabled) {
            return false;
        }
        if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        int headerViewsCount = i + getHeaderViewsCount();
        this.mFirstExpPos = headerViewsCount;
        this.mSecondExpPos = headerViewsCount;
        this.mSrcPos = headerViewsCount;
        this.mFloatPos = headerViewsCount;
        this.mDragState = 4;
        this.mDragFlags = 0;
        this.mDragFlags = i2 | this.mDragFlags;
        this.mFloatView = view;
        measureFloatView();
        this.mDragDeltaX = i3;
        this.mDragDeltaY = i4;
        this.mDragStartY = this.mY;
        this.mFloatLoc.x = this.mX - this.mDragDeltaX;
        this.mFloatLoc.y = this.mY - this.mDragDeltaY;
        View childAt = getChildAt(this.mSrcPos - getFirstVisiblePosition());
        if (childAt != null) {
            childAt.setVisibility(4);
        }
        if (this.mTrackDragSort) {
            this.mDragSortTracker.startTracking();
        }
        switch (this.mCancelMethod) {
            case 1:
                super.onTouchEvent(this.mCancelEvent);
                break;
            case 2:
                super.onInterceptTouchEvent(this.mCancelEvent);
                break;
        }
        requestLayout();
        return true;
    }

    /* access modifiers changed from: private */
    public void doDragFloatView(boolean z) {
        int firstVisiblePosition = getFirstVisiblePosition() + (getChildCount() / 2);
        View childAt = getChildAt(getChildCount() / 2);
        if (childAt != null) {
            doDragFloatView(firstVisiblePosition, childAt, z);
        }
    }

    /* access modifiers changed from: private */
    public void doDragFloatView(int i, View view, boolean z) {
        this.mBlockLayoutRequests = true;
        updateFloatView();
        int i2 = this.mFirstExpPos;
        int i3 = this.mSecondExpPos;
        boolean updatePositions = updatePositions();
        if (updatePositions) {
            adjustAllItems();
            setSelectionFromTop(i, (view.getTop() + adjustScroll(i, view, i2, i3)) - getPaddingTop());
            layoutChildren();
        }
        if (updatePositions || z) {
            invalidate();
        }
        this.mBlockLayoutRequests = false;
    }

    private void updateFloatView() {
        if (this.mFloatViewManager != null) {
            this.mTouchLoc.set(this.mX, this.mY);
            this.mFloatViewManager.onDragFloatView(this.mFloatView, this.mFloatLoc, this.mTouchLoc);
        }
        int i = this.mFloatLoc.x;
        int i2 = this.mFloatLoc.y;
        int paddingLeft = getPaddingLeft();
        if ((this.mDragFlags & 1) == 0 && i > paddingLeft) {
            this.mFloatLoc.x = paddingLeft;
        } else if ((this.mDragFlags & 2) == 0 && i < paddingLeft) {
            this.mFloatLoc.x = paddingLeft;
        }
        int headerViewsCount = getHeaderViewsCount();
        int footerViewsCount = getFooterViewsCount();
        int firstVisiblePosition = getFirstVisiblePosition();
        int lastVisiblePosition = getLastVisiblePosition();
        int paddingTop = getPaddingTop();
        if (firstVisiblePosition < headerViewsCount) {
            paddingTop = getChildAt((headerViewsCount - firstVisiblePosition) - 1).getBottom();
        }
        if ((this.mDragFlags & 8) == 0 && firstVisiblePosition <= this.mSrcPos) {
            paddingTop = Math.max(getChildAt(this.mSrcPos - firstVisiblePosition).getTop(), paddingTop);
        }
        int height = getHeight() - getPaddingBottom();
        if (lastVisiblePosition >= (getCount() - footerViewsCount) - 1) {
            height = getChildAt(((getCount() - footerViewsCount) - 1) - firstVisiblePosition).getBottom();
        }
        if ((this.mDragFlags & 4) == 0 && lastVisiblePosition >= this.mSrcPos) {
            height = Math.min(getChildAt(this.mSrcPos - firstVisiblePosition).getBottom(), height);
        }
        if (i2 < paddingTop) {
            this.mFloatLoc.y = paddingTop;
        } else if (i2 + this.mFloatViewHeight > height) {
            this.mFloatLoc.y = height - this.mFloatViewHeight;
        }
        this.mFloatViewMid = this.mFloatLoc.y + this.mFloatViewHeightHalf;
    }

    /* access modifiers changed from: private */
    public void destroyFloatView() {
        if (this.mFloatView != null) {
            this.mFloatView.setVisibility(8);
            if (this.mFloatViewManager != null) {
                this.mFloatViewManager.onDestroyFloatView(this.mFloatView);
            }
            this.mFloatView = null;
            invalidate();
        }
    }

    public void setFloatViewManager(FloatViewManager floatViewManager) {
        this.mFloatViewManager = floatViewManager;
    }

    public void setDragListener(DragListener dragListener) {
        this.mDragListener = dragListener;
    }

    public void setDragEnabled(boolean z) {
        this.mDragEnabled = z;
    }

    public boolean isDragEnabled() {
        return this.mDragEnabled;
    }

    public void setDropListener(DropListener dropListener) {
        this.mDropListener = dropListener;
    }

    public void setRemoveListener(RemoveListener removeListener) {
        this.mRemoveListener = removeListener;
    }

    public void setDragSortListener(DragSortListener dragSortListener) {
        setDropListener(dragSortListener);
        setDragListener(dragSortListener);
        setRemoveListener(dragSortListener);
    }

    public void setDragScrollProfile(DragScrollProfile dragScrollProfile) {
        if (dragScrollProfile != null) {
            this.mScrollProfile = dragScrollProfile;
        }
    }

    public void moveCheckState(int i, int i2) {
        int i3;
        int i4;
        SparseBooleanArray checkedItemPositions = getCheckedItemPositions();
        if (i2 < i) {
            i4 = i;
            i3 = i2;
        } else {
            i3 = i;
            i4 = i2;
        }
        int i5 = i4 + 1;
        int[] iArr = new int[checkedItemPositions.size()];
        int[] iArr2 = new int[checkedItemPositions.size()];
        int buildRunList = buildRunList(checkedItemPositions, i3, i5, iArr, iArr2);
        if (buildRunList != 1 || iArr[0] != iArr2[0]) {
            if (i < i2) {
                for (int i6 = 0; i6 != buildRunList; i6++) {
                    setItemChecked(rotate(iArr[i6], -1, i3, i5), true);
                    setItemChecked(rotate(iArr2[i6], -1, i3, i5), false);
                }
                return;
            }
            for (int i7 = 0; i7 != buildRunList; i7++) {
                setItemChecked(iArr[i7], false);
                setItemChecked(iArr2[i7], true);
            }
        }
    }

    public void removeCheckState(int i) {
        SparseBooleanArray checkedItemPositions = getCheckedItemPositions();
        if (checkedItemPositions.size() != 0) {
            int[] iArr = new int[checkedItemPositions.size()];
            int[] iArr2 = new int[checkedItemPositions.size()];
            int keyAt = checkedItemPositions.keyAt(checkedItemPositions.size() - 1) + 1;
            int buildRunList = buildRunList(checkedItemPositions, i, keyAt, iArr, iArr2);
            for (int i2 = 0; i2 != buildRunList; i2++) {
                if (iArr[i2] != i && (iArr2[i2] >= iArr[i2] || iArr2[i2] <= i)) {
                    setItemChecked(rotate(iArr[i2], -1, i, keyAt), true);
                }
                setItemChecked(rotate(iArr2[i2], -1, i, keyAt), false);
            }
        }
    }

    private static int buildRunList(SparseBooleanArray sparseBooleanArray, int i, int i2, int[] iArr, int[] iArr2) {
        int findFirstSetIndex = findFirstSetIndex(sparseBooleanArray, i, i2);
        if (findFirstSetIndex == -1) {
            return 0;
        }
        int keyAt = sparseBooleanArray.keyAt(findFirstSetIndex);
        int i3 = keyAt + 1;
        int i4 = keyAt;
        int i5 = 0;
        for (int i6 = findFirstSetIndex + 1; i6 < sparseBooleanArray.size(); i6++) {
            int keyAt2 = sparseBooleanArray.keyAt(i6);
            if (keyAt2 >= i2) {
                break;
            }
            if (sparseBooleanArray.valueAt(i6)) {
                if (keyAt2 == i3) {
                    i3++;
                } else {
                    iArr[i5] = i4;
                    iArr2[i5] = i3;
                    i5++;
                    i3 = keyAt2 + 1;
                    i4 = keyAt2;
                }
            }
        }
        if (i3 == i2) {
            i3 = i;
        }
        iArr[i5] = i4;
        iArr2[i5] = i3;
        int i7 = i5 + 1;
        if (i7 > 1 && iArr[0] == i) {
            int i8 = i7 - 1;
            if (iArr2[i8] == i) {
                iArr[0] = iArr[i8];
                i7--;
            }
        }
        return i7;
    }

    private static int findFirstSetIndex(SparseBooleanArray sparseBooleanArray, int i, int i2) {
        int size = sparseBooleanArray.size();
        int insertionIndexForKey = insertionIndexForKey(sparseBooleanArray, i);
        while (insertionIndexForKey < size && sparseBooleanArray.keyAt(insertionIndexForKey) < i2 && !sparseBooleanArray.valueAt(insertionIndexForKey)) {
            insertionIndexForKey++;
        }
        if (insertionIndexForKey == size || sparseBooleanArray.keyAt(insertionIndexForKey) >= i2) {
            return -1;
        }
        return insertionIndexForKey;
    }

    private static int insertionIndexForKey(SparseBooleanArray sparseBooleanArray, int i) {
        int size = sparseBooleanArray.size();
        int i2 = 0;
        while (size - i2 > 0) {
            int i3 = (i2 + size) >> 1;
            if (sparseBooleanArray.keyAt(i3) < i) {
                i2 = i3 + 1;
            } else {
                size = i3;
            }
        }
        return i2;
    }
}
