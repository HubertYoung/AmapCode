package com.autonavi.minimap.widget.draglistview;

import android.graphics.Point;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewConfiguration;

public class DragSortController extends SimpleFloatViewManager implements OnGestureListener, OnTouchListener {
    public static final int CLICK_REMOVE = 0;
    public static final int FLING_REMOVE = 1;
    public static final int MISS = -1;
    public static final int ON_DOWN = 0;
    public static final int ON_DRAG = 1;
    public static final int ON_LONG_PRESS = 2;
    private boolean mCanDrag;
    private int mClickRemoveHitPos;
    private int mClickRemoveId;
    private int mCurrX;
    private int mCurrY;
    private GestureDetector mDetector;
    private int mDragHandleId;
    private int mDragInitMode;
    private boolean mDragging;
    /* access modifiers changed from: private */
    public DragSortListView mDslv;
    private int mFlingHandleId;
    private int mFlingHitPos;
    private GestureDetector mFlingRemoveDetector;
    private OnGestureListener mFlingRemoveListener;
    /* access modifiers changed from: private */
    public float mFlingSpeed;
    private int mHitPos;
    /* access modifiers changed from: private */
    public boolean mIsRemoving;
    private int mItemX;
    private int mItemY;
    /* access modifiers changed from: private */
    public int mPositionX;
    /* access modifiers changed from: private */
    public boolean mRemoveEnabled;
    private int mRemoveMode;
    private boolean mSortEnabled;
    private int[] mTempLoc;
    private int mTouchSlop;

    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    public DragSortController(DragSortListView dragSortListView) {
        this(dragSortListView, 0, 0, 1);
    }

    public DragSortController(DragSortListView dragSortListView, int i, int i2, int i3) {
        this(dragSortListView, i, i2, i3, 0);
    }

    public DragSortController(DragSortListView dragSortListView, int i, int i2, int i3, int i4) {
        this(dragSortListView, i, i2, i3, i4, 0);
    }

    public DragSortController(DragSortListView dragSortListView, int i, int i2, int i3, int i4, int i5) {
        super(dragSortListView);
        this.mDragInitMode = 0;
        this.mSortEnabled = true;
        this.mRemoveEnabled = false;
        this.mIsRemoving = false;
        this.mHitPos = -1;
        this.mFlingHitPos = -1;
        this.mClickRemoveHitPos = -1;
        this.mTempLoc = new int[2];
        this.mDragging = false;
        this.mFlingSpeed = 500.0f;
        this.mFlingRemoveListener = new SimpleOnGestureListener() {
            public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (DragSortController.this.mRemoveEnabled && DragSortController.this.mIsRemoving) {
                    int width = DragSortController.this.mDslv.getWidth() / 5;
                    if (f > DragSortController.this.mFlingSpeed) {
                        if (DragSortController.this.mPositionX > (-width)) {
                            DragSortController.this.mDslv.stopDragWithVelocity(true, f);
                        }
                    } else if (f < (-DragSortController.this.mFlingSpeed) && DragSortController.this.mPositionX < width) {
                        DragSortController.this.mDslv.stopDragWithVelocity(true, f);
                    }
                    DragSortController.this.mIsRemoving = false;
                }
                return false;
            }
        };
        this.mDslv = dragSortListView;
        this.mDetector = new GestureDetector(dragSortListView.getContext(), this);
        this.mFlingRemoveDetector = new GestureDetector(dragSortListView.getContext(), this.mFlingRemoveListener);
        this.mFlingRemoveDetector.setIsLongpressEnabled(false);
        this.mTouchSlop = ViewConfiguration.get(dragSortListView.getContext()).getScaledTouchSlop();
        this.mDragHandleId = i;
        this.mClickRemoveId = i4;
        this.mFlingHandleId = i5;
        setRemoveMode(i3);
        setDragInitMode(i2);
    }

    public int getDragInitMode() {
        return this.mDragInitMode;
    }

    public void setDragInitMode(int i) {
        this.mDragInitMode = i;
    }

    public void setSortEnabled(boolean z) {
        this.mSortEnabled = z;
    }

    public boolean isSortEnabled() {
        return this.mSortEnabled;
    }

    public void setRemoveMode(int i) {
        this.mRemoveMode = i;
    }

    public int getRemoveMode() {
        return this.mRemoveMode;
    }

    public void setRemoveEnabled(boolean z) {
        this.mRemoveEnabled = z;
    }

    public boolean isRemoveEnabled() {
        return this.mRemoveEnabled;
    }

    public void setDragHandleId(int i) {
        this.mDragHandleId = i;
    }

    public void setFlingHandleId(int i) {
        this.mFlingHandleId = i;
    }

    public void setClickRemoveId(int i) {
        this.mClickRemoveId = i;
    }

    public boolean startDrag(int i, int i2, int i3) {
        int i4 = (!this.mSortEnabled || this.mIsRemoving) ? 0 : 12;
        if (this.mRemoveEnabled && this.mIsRemoving) {
            i4 = i4 | 1 | 2;
        }
        this.mDragging = this.mDslv.startDrag(i - this.mDslv.getHeaderViewsCount(), i4, i2, i3);
        return this.mDragging;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!this.mDslv.isDragEnabled() || this.mDslv.listViewIntercepted()) {
            return false;
        }
        this.mDetector.onTouchEvent(motionEvent);
        if (this.mRemoveEnabled && this.mDragging && this.mRemoveMode == 1) {
            this.mFlingRemoveDetector.onTouchEvent(motionEvent);
        }
        int action = motionEvent.getAction() & 255;
        if (action != 3) {
            switch (action) {
                case 0:
                    this.mCurrX = (int) motionEvent.getX();
                    this.mCurrY = (int) motionEvent.getY();
                    break;
                case 1:
                    if (this.mRemoveEnabled && this.mIsRemoving) {
                        if ((this.mPositionX >= 0 ? this.mPositionX : -this.mPositionX) > this.mDslv.getWidth() / 2) {
                            this.mDslv.stopDragWithVelocity(true, 0.0f);
                            break;
                        }
                    }
                    break;
            }
        }
        this.mIsRemoving = false;
        this.mDragging = false;
        return false;
    }

    public void onDragFloatView(View view, Point point, Point point2) {
        if (this.mRemoveEnabled && this.mIsRemoving) {
            this.mPositionX = point.x;
        }
    }

    public int startDragPosition(MotionEvent motionEvent) {
        return dragHandleHitPosition(motionEvent);
    }

    public int startFlingPosition(MotionEvent motionEvent) {
        if (this.mRemoveMode == 1) {
            return flingHandleHitPosition(motionEvent);
        }
        return -1;
    }

    public int dragHandleHitPosition(MotionEvent motionEvent) {
        return viewIdHitPosition(motionEvent, this.mDragHandleId);
    }

    public int flingHandleHitPosition(MotionEvent motionEvent) {
        return viewIdHitPosition(motionEvent, this.mFlingHandleId);
    }

    public int viewIdHitPosition(MotionEvent motionEvent, int i) {
        View view;
        int pointToPosition = this.mDslv.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY());
        int headerViewsCount = this.mDslv.getHeaderViewsCount();
        int footerViewsCount = this.mDslv.getFooterViewsCount();
        int count = this.mDslv.getCount();
        if (pointToPosition != -1 && pointToPosition >= headerViewsCount && pointToPosition < count - footerViewsCount) {
            DragSortListView dragSortListView = this.mDslv;
            View childAt = dragSortListView.getChildAt(pointToPosition - dragSortListView.getFirstVisiblePosition());
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            if (i == 0) {
                view = childAt;
            } else {
                view = childAt.findViewById(i);
            }
            if (view != null) {
                view.getLocationOnScreen(this.mTempLoc);
                if (rawX > this.mTempLoc[0] && rawY > this.mTempLoc[1] && rawX < this.mTempLoc[0] + view.getWidth() && rawY < this.mTempLoc[1] + view.getHeight()) {
                    this.mItemX = childAt.getLeft();
                    this.mItemY = childAt.getTop();
                    return pointToPosition;
                }
            }
        }
        return -1;
    }

    public boolean onDown(MotionEvent motionEvent) {
        if (this.mRemoveEnabled && this.mRemoveMode == 0) {
            this.mClickRemoveHitPos = viewIdHitPosition(motionEvent, this.mClickRemoveId);
        }
        this.mHitPos = startDragPosition(motionEvent);
        if (this.mHitPos != -1 && this.mDragInitMode == 0) {
            startDrag(this.mHitPos, ((int) motionEvent.getX()) - this.mItemX, ((int) motionEvent.getY()) - this.mItemY);
        }
        this.mIsRemoving = false;
        this.mCanDrag = true;
        this.mPositionX = 0;
        this.mFlingHitPos = startFlingPosition(motionEvent);
        return true;
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int x2 = (int) motionEvent2.getX();
        int y2 = (int) motionEvent2.getY();
        int i = x2 - this.mItemX;
        int i2 = y2 - this.mItemY;
        if (this.mCanDrag && !this.mDragging && !(this.mHitPos == -1 && this.mFlingHitPos == -1)) {
            if (this.mHitPos != -1) {
                if (this.mDragInitMode == 1 && Math.abs(y2 - y) > this.mTouchSlop && this.mSortEnabled) {
                    startDrag(this.mHitPos, i, i2);
                } else if (this.mDragInitMode != 0 && Math.abs(x2 - x) > this.mTouchSlop && this.mRemoveEnabled) {
                    this.mIsRemoving = true;
                    startDrag(this.mFlingHitPos, i, i2);
                }
            } else if (this.mFlingHitPos != -1) {
                if (Math.abs(x2 - x) > this.mTouchSlop && this.mRemoveEnabled) {
                    this.mIsRemoving = true;
                    startDrag(this.mFlingHitPos, i, i2);
                } else if (Math.abs(y2 - y) > this.mTouchSlop) {
                    this.mCanDrag = false;
                }
            }
        }
        return false;
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.mHitPos != -1 && this.mDragInitMode == 2 && startDrag(this.mHitPos, this.mCurrX - this.mItemX, this.mCurrY - this.mItemY)) {
            this.mDslv.performHapticFeedback(0);
        }
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (this.mRemoveEnabled && this.mRemoveMode == 0 && this.mClickRemoveHitPos != -1) {
            this.mDslv.removeItem(this.mClickRemoveHitPos - this.mDslv.getHeaderViewsCount());
        }
        return true;
    }
}
