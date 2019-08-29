package com.autonavi.minimap.ajx3.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewDebug.ExportedProperty;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import com.alipay.sdk.util.h;
import com.uc.webview.export.extension.UCCore;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SpringHorizontalScrollView extends FrameLayout {
    private static final int ANIMATED_SCROLL_GAP = 250;
    private static final int INVALID_POINTER = -1;
    private static final float MAX_SCROLL_FACTOR = 0.5f;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    public static final int OVER_SCROLL_ONLY_LEFT = 3;
    public static final int OVER_SCROLL_ONLY_RIGHT = 4;
    private static final String TAG = "SpringHorizontalScrollView";
    private int mActivePointerId;
    private boolean mCanClamped;
    private View mChildToScrollTo;
    @ExportedProperty(category = "layout")
    private boolean mFillViewport;
    private boolean mIsBeingDragged;
    private boolean mIsLayoutDirty;
    private int mLastMotionX;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mOverScrollMode;
    private int mOverflingDistance;
    private int mOverscrollDistance;
    private SavedState mSavedState;
    private SpringOverScroller mScroller;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public final SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public final SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int scrollOffsetFromStart;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollOffsetFromStart = parcel.readInt();
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollOffsetFromStart);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("SpringHorizontalScrollView.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" scrollPosition=");
            sb.append(this.scrollOffsetFromStart);
            sb.append(h.d);
            return sb.toString();
        }
    }

    private static int clamp(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        return i2 + i > i3 ? i3 - i2 : i;
    }

    /* access modifiers changed from: protected */
    public float getLeftFadingEdgeStrength() {
        return 0.0f;
    }

    /* access modifiers changed from: protected */
    public float getRightFadingEdgeStrength() {
        return 0.0f;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public SpringHorizontalScrollView(Context context) {
        this(context, null);
    }

    public SpringHorizontalScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842880);
    }

    public SpringHorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mTempRect = new Rect();
        this.mOverScrollMode = 1;
        this.mCanClamped = true;
        this.mIsLayoutDirty = true;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        initScrollView();
    }

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public void setOverScrollMode(int i) {
        if (i >= 0 && i <= 4) {
            this.mOverScrollMode = i;
        }
    }

    public int getMaxScrollAmount() {
        return (int) (((float) (getRight() - getLeft())) * MAX_SCROLL_FACTOR);
    }

    private void initScrollView() {
        this.mScroller = new SpringOverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
    }

    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("SpringHorizontalScrollView can host only one direct child");
        }
        super.addView(view);
    }

    public void addView(View view, int i) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("SpringHorizontalScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("SpringHorizontalScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    public void addView(View view, int i, LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("SpringHorizontalScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    private boolean canScroll() {
        boolean z = false;
        View childAt = getChildAt(0);
        if (childAt == null) {
            return false;
        }
        if (getWidth() < childAt.getWidth() + getPaddingLeft() + getPaddingRight()) {
            z = true;
        }
        return z;
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    public void setFillViewport(boolean z) {
        if (z != this.mFillViewport) {
            this.mFillViewport = z;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.mSmoothScrollingEnabled = z;
    }

    public void postInvalidateOnAnimation() {
        if (VERSION.SDK_INT >= 16) {
            super.postInvalidateOnAnimation();
        } else {
            postInvalidate();
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int i3;
        int i4;
        super.onMeasure(i, i2);
        if (this.mFillViewport && MeasureSpec.getMode(i) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                i4 = getPaddingLeft() + getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin;
                i3 = getPaddingTop() + getPaddingBottom() + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                i4 = getPaddingLeft() + getPaddingRight();
                i3 = getPaddingTop() + getPaddingBottom();
            }
            int measuredWidth = getMeasuredWidth() - i4;
            if (childAt.getMeasuredWidth() < measuredWidth) {
                childAt.measure(MeasureSpec.makeMeasureSpec(measuredWidth, UCCore.VERIFY_POLICY_QUICK), getChildMeasureSpec(i2, i3, layoutParams.height));
            }
        }
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        boolean z = false;
        int i = 66;
        if (canScroll()) {
            if (keyEvent.getAction() == 0) {
                int keyCode = keyEvent.getKeyCode();
                if (keyCode != 62) {
                    switch (keyCode) {
                        case 21:
                            if (keyEvent.isAltPressed()) {
                                z = fullScroll(17);
                                break;
                            } else {
                                z = arrowScroll(17);
                                break;
                            }
                        case 22:
                            if (keyEvent.isAltPressed()) {
                                z = fullScroll(66);
                                break;
                            } else {
                                z = arrowScroll(66);
                                break;
                            }
                    }
                } else {
                    if (keyEvent.isShiftPressed()) {
                        i = 17;
                    }
                    pageScroll(i);
                }
            }
            return z;
        } else if (!isFocused()) {
            return false;
        } else {
            View findFocus = findFocus();
            if (findFocus == this) {
                findFocus = null;
            }
            View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, 66);
            if (findNextFocus == null || findNextFocus == this || !findNextFocus.requestFocus(66)) {
                return false;
            }
            return true;
        }
    }

    private boolean inChild(int i, int i2) {
        if (getChildCount() <= 0) {
            return false;
        }
        int scrollX = getScrollX();
        View childAt = getChildAt(0);
        if (i2 < childAt.getTop() || i2 >= childAt.getBottom() || i < childAt.getLeft() - scrollX || i >= childAt.getRight() - scrollX) {
            return false;
        }
        return true;
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

    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if ((action == 2 && this.mIsBeingDragged) || super.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        switch (action & 255) {
            case 0:
                int x = (int) motionEvent.getX();
                if (inChild(x, (int) motionEvent.getY())) {
                    this.mLastMotionX = x;
                    this.mActivePointerId = motionEvent.getPointerId(0);
                    initOrResetVelocityTracker();
                    this.mVelocityTracker.addMovement(motionEvent);
                    this.mIsBeingDragged = !this.mScroller.isFinished();
                    break;
                } else {
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    break;
                }
            case 1:
            case 3:
                this.mIsBeingDragged = false;
                this.mActivePointerId = -1;
                if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, getScrollRange(), 0, 0)) {
                    postInvalidateOnAnimation();
                    break;
                }
                break;
            case 2:
                int i = this.mActivePointerId;
                if (i != -1) {
                    int findPointerIndex = motionEvent.findPointerIndex(i);
                    if (findPointerIndex != -1) {
                        int x2 = (int) motionEvent.getX(findPointerIndex);
                        if (Math.abs(x2 - this.mLastMotionX) > this.mTouchSlop) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionX = x2;
                            initVelocityTrackerIfNotExists();
                            this.mVelocityTracker.addMovement(motionEvent);
                            if (getParent() != null) {
                                getParent().requestDisallowInterceptTouchEvent(true);
                                break;
                            }
                        }
                    }
                }
                break;
            case 5:
                int actionIndex = motionEvent.getActionIndex();
                this.mLastMotionX = (int) motionEvent.getX(actionIndex);
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                break;
            case 6:
                onSecondaryPointerUp(motionEvent);
                try {
                    this.mLastMotionX = (int) motionEvent.getX(motionEvent.findPointerIndex(this.mActivePointerId));
                    break;
                } catch (Exception unused) {
                    break;
                }
        }
        return this.mIsBeingDragged;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        if (action != 6) {
            switch (action) {
                case 0:
                    if (getChildCount() != 0) {
                        boolean z = !this.mScroller.isFinished();
                        this.mIsBeingDragged = z;
                        if (z) {
                            ViewParent parent = getParent();
                            if (parent != null) {
                                parent.requestDisallowInterceptTouchEvent(true);
                            }
                        }
                        if (!this.mScroller.isFinished()) {
                            this.mScroller.abortAnimation();
                        }
                        this.mLastMotionX = (int) motionEvent.getX();
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        break;
                    } else {
                        return false;
                    }
                case 1:
                    if (this.mIsBeingDragged) {
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                        int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                        if (getChildCount() > 0) {
                            if (Math.abs(xVelocity) > this.mMinimumVelocity) {
                                fling(-xVelocity);
                            } else if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, getScrollRange(), 0, 0)) {
                                postInvalidateOnAnimation();
                            }
                        }
                        this.mActivePointerId = -1;
                        this.mIsBeingDragged = false;
                        recycleVelocityTracker();
                        break;
                    }
                    break;
                case 2:
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex != -1) {
                        int x = (int) motionEvent.getX(findPointerIndex);
                        int i = this.mLastMotionX - x;
                        if (!this.mIsBeingDragged && Math.abs(i) > this.mTouchSlop) {
                            ViewParent parent2 = getParent();
                            if (parent2 != null) {
                                parent2.requestDisallowInterceptTouchEvent(true);
                            }
                            this.mIsBeingDragged = true;
                            i = i > 0 ? i - this.mTouchSlop : i + this.mTouchSlop;
                        }
                        int i2 = i;
                        if (this.mIsBeingDragged) {
                            this.mLastMotionX = x;
                            getScrollX();
                            getScrollY();
                            int scrollRange = getScrollRange();
                            getOverScrollMode();
                            if (overScrollBy(i2, 0, getScrollX(), 0, scrollRange, 0, this.mOverscrollDistance, 0, true)) {
                                this.mVelocityTracker.clear();
                                break;
                            }
                        }
                    }
                    break;
                case 3:
                    if (this.mIsBeingDragged && getChildCount() > 0) {
                        if (this.mScroller.springBack(getScrollX(), getScrollY(), 0, getScrollRange(), 0, 0)) {
                            postInvalidateOnAnimation();
                        }
                        this.mActivePointerId = -1;
                        this.mIsBeingDragged = false;
                        recycleVelocityTracker();
                        break;
                    }
            }
        } else {
            onSecondaryPointerUp(motionEvent);
        }
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (action + 1 <= motionEvent.getPointerCount() && motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionX = (int) motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (!this.mScroller.isFinished()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            this.mCanClamped = false;
            setScrollX(i);
            setScrollY(i2);
            this.mCanClamped = true;
            invalidateParentIfNeeded();
            onScrollChanged(getScrollX(), getScrollY(), scrollX, scrollY);
            if (z) {
                this.mScroller.springBack(getScrollX(), getScrollY(), 0, getScrollRange(), 0, 0);
            }
        } else {
            super.scrollTo(i, i2);
        }
        awakenScrollBars();
    }

    private void invalidateParentIfNeeded() {
        if (isHardwareAccelerated() && (getParent() instanceof View)) {
            ((View) getParent()).invalidate();
        }
    }

    public CharSequence getAccessibilityClassName() {
        return SpringHorizontalScrollView.class.getName();
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        int scrollRange = getScrollRange();
        if (scrollRange > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (isEnabled() && getScrollX() > 0) {
                accessibilityNodeInfo.addAction(8192);
            }
            if (isEnabled() && getScaleY() < ((float) scrollRange)) {
                accessibilityNodeInfo.addAction(4096);
            }
        }
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setScrollable(getScrollRange() > 0);
        accessibilityEvent.setScrollX(getScrollX());
        accessibilityEvent.setScrollY(getScrollY());
        if (VERSION.SDK_INT >= 15) {
            accessibilityEvent.setMaxScrollX(getScrollRange());
            accessibilityEvent.setMaxScrollY(getScrollY());
            return;
        }
        try {
            Method method = accessibilityEvent.getClass().getMethod("setMaxScrollX", new Class[]{Integer.TYPE});
            Method method2 = accessibilityEvent.getClass().getMethod("setMaxScrollY", new Class[]{Integer.TYPE});
            method.setAccessible(true);
            method2.setAccessible(true);
            method.invoke(accessibilityEvent, new Object[]{Integer.valueOf(getScrollRange())});
            method2.invoke(accessibilityEvent, new Object[]{Integer.valueOf(getScrollY())});
        } catch (Exception unused) {
        }
    }

    private int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getWidth() - ((getWidth() - getPaddingLeft()) - getPaddingRight()));
        }
        return 0;
    }

    private View findFocusableViewInMyBounds(boolean z, int i, View view) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength() / 2;
        int i2 = i + horizontalFadingEdgeLength;
        int width = (i + getWidth()) - horizontalFadingEdgeLength;
        if (view == null || view.getLeft() >= width || view.getRight() <= i2) {
            return findFocusableViewInBounds(z, i2, width);
        }
        return view;
    }

    private View findFocusableViewInBounds(boolean z, int i, int i2) {
        ArrayList focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view2 = (View) focusables.get(i3);
            int left = view2.getLeft();
            int right = view2.getRight();
            if (i < right && left < i2) {
                boolean z3 = i < left && right < i2;
                if (view == null) {
                    view = view2;
                    z2 = z3;
                } else {
                    boolean z4 = (z && left < view.getLeft()) || (!z && right > view.getRight());
                    if (z2) {
                        if (z3) {
                            if (!z4) {
                            }
                        }
                    } else if (z3) {
                        view = view2;
                        z2 = true;
                    } else if (!z4) {
                    }
                    view = view2;
                }
            }
        }
        return view;
    }

    public boolean pageScroll(int i) {
        boolean z = i == 66;
        int width = getWidth();
        if (z) {
            this.mTempRect.left = getScrollX() + width;
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                if (this.mTempRect.left + width > childAt.getRight()) {
                    this.mTempRect.left = childAt.getRight() - width;
                }
            }
        } else {
            this.mTempRect.left = getScrollX() - width;
            if (this.mTempRect.left < 0) {
                this.mTempRect.left = 0;
            }
        }
        this.mTempRect.right = this.mTempRect.left + width;
        return scrollAndFocus(i, this.mTempRect.left, this.mTempRect.right);
    }

    public boolean fullScroll(int i) {
        boolean z = i == 66;
        int width = getWidth();
        this.mTempRect.left = 0;
        this.mTempRect.right = width;
        if (z && getChildCount() > 0) {
            this.mTempRect.right = getChildAt(0).getRight();
            this.mTempRect.left = this.mTempRect.right - width;
        }
        return scrollAndFocus(i, this.mTempRect.left, this.mTempRect.right);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) {
        int width = getWidth();
        int scrollX = getScrollX();
        int i4 = width + scrollX;
        boolean z = false;
        boolean z2 = i == 17;
        View findFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3);
        if (findFocusableViewInBounds == null) {
            findFocusableViewInBounds = this;
        }
        if (i2 < scrollX || i3 > i4) {
            doScrollX(z2 ? i2 - scrollX : i3 - i4);
            z = true;
        }
        if (findFocusableViewInBounds != findFocus()) {
            findFocusableViewInBounds.requestFocus(i);
        }
        return z;
    }

    public boolean arrowScroll(int i) {
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus == null || !isWithinDeltaOfScreen(findNextFocus, maxScrollAmount)) {
            if (i == 17 && getScrollX() < maxScrollAmount) {
                maxScrollAmount = getScrollX();
            } else if (i == 66 && getChildCount() > 0) {
                int right = getChildAt(0).getRight() - (getScrollX() + getWidth());
                if (right < maxScrollAmount) {
                    maxScrollAmount = right;
                }
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 66) {
                maxScrollAmount = -maxScrollAmount;
            }
            doScrollX(maxScrollAmount);
        } else {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            doScrollX(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            findNextFocus.requestFocus(i);
        }
        if (findFocus != null && findFocus.isFocused() && isOffScreen(findFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    private boolean isOffScreen(View view) {
        return !isWithinDeltaOfScreen(view, 0);
    }

    private boolean isWithinDeltaOfScreen(View view, int i) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.right + i >= getScrollX() && this.mTempRect.left - i <= getScrollX() + getWidth();
    }

    private void doScrollX(int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(i, 0);
                return;
            }
            scrollBy(i, 0);
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() != 0) {
            if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
                int max = Math.max(0, getChildAt(0).getWidth() - ((getWidth() - getPaddingRight()) - getPaddingLeft()));
                int scrollX = getScrollX();
                this.mScroller.startScroll(scrollX, getScrollY(), Math.max(0, Math.min(i + scrollX, max)) - scrollX, 0);
                postInvalidateOnAnimation();
            } else {
                if (!this.mScroller.isFinished()) {
                    this.mScroller.abortAnimation();
                }
                scrollBy(i, i2);
            }
            this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
        }
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - getScrollX(), i2 - getScrollY());
    }

    /* access modifiers changed from: protected */
    public int computeHorizontalScrollRange() {
        int width = (getWidth() - getPaddingLeft()) - getPaddingRight();
        if (getChildCount() == 0) {
            return width;
        }
        int right = getChildAt(0).getRight();
        int scrollX = getScrollX();
        int max = Math.max(0, right - width);
        if (scrollX < 0) {
            right -= scrollX;
        } else if (scrollX > max) {
            right += scrollX - max;
        }
        return right;
    }

    /* access modifiers changed from: protected */
    public void measureChild(View view, int i, int i2) {
        view.measure(MeasureSpec.makeMeasureSpec(0, 0), getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), view.getLayoutParams().height));
    }

    /* access modifiers changed from: protected */
    public void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        MarginLayoutParams marginLayoutParams = (MarginLayoutParams) view.getLayoutParams();
        view.measure(MeasureSpec.makeMeasureSpec(Math.max(0, MeasureSpec.getSize(i) - ((((getPaddingLeft() + getPaddingRight()) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2)), 0), getChildMeasureSpec(i3, getPaddingTop() + getPaddingBottom() + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    public void computeScroll() {
        if (this.mScroller.computeScrollOffset()) {
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (!(scrollX == currX && scrollY == currY)) {
                int scrollRange = getScrollRange();
                getOverScrollMode();
                overScrollBy(currX - scrollX, currY - scrollY, scrollX, scrollY, scrollRange, 0, this.mOverflingDistance, 0, false);
            }
            if (!awakenScrollBars()) {
                postInvalidateOnAnimation();
            }
        }
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (computeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(computeScrollDeltaToGetChildRectOnScreen, 0);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean z) {
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(computeScrollDeltaToGetChildRectOnScreen, 0);
            } else {
                smoothScrollBy(computeScrollDeltaToGetChildRectOnScreen, 0);
            }
        }
        return z2;
    }

    /* access modifiers changed from: protected */
    public int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int i;
        int i2;
        int i3 = 0;
        if (getChildCount() == 0) {
            return 0;
        }
        int width = getWidth();
        int scrollX = getScrollX();
        int i4 = scrollX + width;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (rect.left > 0) {
            scrollX += horizontalFadingEdgeLength;
        }
        if (rect.right < getChildAt(0).getWidth()) {
            i4 -= horizontalFadingEdgeLength;
        }
        if (rect.right > i4 && rect.left > scrollX) {
            if (rect.width() > width) {
                i2 = (rect.left - scrollX) + 0;
            } else {
                i2 = (rect.right - i4) + 0;
            }
            i3 = Math.min(i2, getChildAt(0).getRight() - i4);
        } else if (rect.left < scrollX && rect.right < i4) {
            if (rect.width() > width) {
                i = 0 - (i4 - rect.right);
            } else {
                i = 0 - (scrollX - rect.left);
            }
            i3 = Math.max(i, -getScrollX());
        }
        return i3;
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mIsLayoutDirty) {
            scrollToChild(view2);
        } else {
            this.mChildToScrollTo = view2;
        }
        super.requestChildFocus(view, view2);
    }

    /* access modifiers changed from: protected */
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        View view;
        if (i == 2) {
            i = 66;
        } else if (i == 1) {
            i = 17;
        }
        if (rect == null) {
            view = FocusFinder.getInstance().findNextFocus(this, null, i);
        } else {
            view = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (view != null && !isOffScreen(view)) {
            return view.requestFocus(i, rect);
        }
        return false;
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return scrollToChildRect(rect, z);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public boolean isLayoutRtl() {
        if (VERSION.SDK_INT < 17 || getLayoutDirection() == 1) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        int measuredWidth = getChildCount() > 0 ? getChildAt(0).getMeasuredWidth() : 0;
        this.mIsLayoutDirty = false;
        if (this.mChildToScrollTo != null && isViewDescendantOf(this.mChildToScrollTo, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (VERSION.SDK_INT >= 19 && !isLaidOut()) {
            int max = Math.max(0, measuredWidth - (((i3 - i) - getPaddingLeft()) - getPaddingRight()));
            if (this.mSavedState != null) {
                setScrollX(isLayoutRtl() ? max - this.mSavedState.scrollOffsetFromStart : this.mSavedState.scrollOffsetFromStart);
                this.mSavedState = null;
            } else if (isLayoutRtl()) {
                setScrollX(max - getScrollX());
            }
            if (getScrollX() > max) {
                setScrollX(max);
            } else if (getScrollX() < 0) {
                setScrollX(0);
            }
        }
        scrollTo(getScrollX(), getScrollY());
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (!(findFocus == null || this == findFocus || !isWithinDeltaOfScreen(findFocus, getRight() - getLeft()))) {
            findFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
            doScrollX(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
        }
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        ViewParent parent = view.getParent();
        if (!(parent instanceof ViewGroup) || !isViewDescendantOf((View) parent, view2)) {
            return false;
        }
        return true;
    }

    public void fling(int i) {
        if (getChildCount() > 0) {
            int width = (getWidth() - getPaddingRight()) - getPaddingLeft();
            boolean z = false;
            this.mScroller.fling(getScrollX(), getScrollY(), i, 0, 0, Math.max(0, getChildAt(0).getWidth() - width), 0, 0, width / 2, 0);
            if (i > 0) {
                z = true;
            }
            View findFocus = findFocus();
            View findFocusableViewInMyBounds = findFocusableViewInMyBounds(z, this.mScroller.getFinalX(), findFocus);
            if (findFocusableViewInMyBounds == null) {
                findFocusableViewInMyBounds = this;
            }
            if (findFocusableViewInMyBounds != findFocus) {
                findFocusableViewInMyBounds.requestFocus(z ? 66 : 17);
            }
            postInvalidateOnAnimation();
        }
    }

    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.mCanClamped) {
                i = clamp(i, (getWidth() - getPaddingRight()) - getPaddingLeft(), childAt.getWidth());
            }
            if (this.mCanClamped) {
                i2 = clamp(i2, (getHeight() - getPaddingBottom()) - getPaddingTop(), childAt.getHeight());
            }
            if (i != getScrollX() || i2 != getScrollY()) {
                super.scrollTo(i, i2);
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (getContext().getApplicationInfo().targetSdkVersion <= 18) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSavedState = savedState;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public Parcelable onSaveInstanceState() {
        if (getContext().getApplicationInfo().targetSdkVersion <= 18) {
            return super.onSaveInstanceState();
        }
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollOffsetFromStart = isLayoutRtl() ? -getScrollX() : getScrollX();
        return savedState;
    }

    /* access modifiers changed from: protected */
    public boolean overScrollBy(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, boolean z) {
        int i9 = this.mOverScrollMode;
        boolean z2 = true;
        boolean z3 = i9 == 0 || (i9 == 1 && (computeHorizontalScrollRange() > computeHorizontalScrollExtent()));
        boolean z4 = z3 || i9 == 3;
        boolean z5 = z3 || i9 == 4;
        int i10 = i3 + i;
        int i11 = z4 ? -i7 : 0;
        if (z5) {
            i5 += i7;
        }
        float f = 0.0f;
        if (i10 > i5) {
            if (!z5) {
                i10 = i5;
            } else if (this.mIsBeingDragged) {
                int width = getWidth() / 2;
                int i12 = (i5 + width) - i10;
                float f2 = ((float) i) * 0.35f;
                if (i12 > 0) {
                    f = (((float) i12) * 1.0f) / ((float) width);
                }
                i10 = i3 + ((int) (f2 * f));
            }
        } else if (i10 >= i11) {
            z2 = false;
        } else if (!z4) {
            i10 = i11;
        } else if (this.mIsBeingDragged) {
            int width2 = getWidth() / 2;
            int i13 = (i11 - width2) - i10;
            float f3 = ((float) i) * 0.35f;
            if (i13 < 0) {
                f = (((float) i13) * -1.0f) / ((float) width2);
            }
            i10 = i3 + ((int) (f3 * f));
        }
        onOverScrolled(i10, 0, z2, false);
        return z2;
    }
}
