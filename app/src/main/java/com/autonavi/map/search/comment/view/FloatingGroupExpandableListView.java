package com.autonavi.map.search.comment.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;
import android.widget.ExpandableListView.OnGroupClickListener;
import com.autonavi.map.search.view.FloatingBaseExpandableListAdapter;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class FloatingGroupExpandableListView extends ExpandableListView {
    private static final int[] EMPTY_STATE_SET = new int[0];
    private static final int[] GROUP_EMPTY_STATE_SET = {16842921};
    private static final int[] GROUP_EXPANDED_EMPTY_STATE_SET = {16842920, 16842921};
    private static final int[] GROUP_EXPANDED_STATE_SET = {16842920};
    private static final int[][] GROUP_STATE_SETS = {EMPTY_STATE_SET, GROUP_EXPANDED_STATE_SET, GROUP_EMPTY_STATE_SET, GROUP_EXPANDED_EMPTY_STATE_SET};
    /* access modifiers changed from: private */
    public FloatingBaseExpandableListAdapter mAdapter;
    private Runnable mClearSelectorOnTapAction;
    private DataSetObserver mDataSetObserver;
    private boolean mDrawSelectorOnTop;
    /* access modifiers changed from: private */
    public boolean mFloatingGroupEnabled = true;
    /* access modifiers changed from: private */
    public int mFloatingGroupPosition;
    private int mFloatingGroupScrollY;
    /* access modifiers changed from: private */
    public View mFloatingGroupView;
    private GestureDetector mGestureDetector;
    private boolean mHandledByOnInterceptTouchEvent;
    private boolean mHandledByOnTouchEvent;
    private final Rect mIndicatorRect = new Rect();
    /* access modifiers changed from: private */
    public Runnable mOnClickAction;
    /* access modifiers changed from: private */
    public OnGroupClickListener mOnGroupClickListener;
    private a mOnScrollFloatingGroupListener;
    /* access modifiers changed from: private */
    public OnScrollListener mOnScrollListener;
    private Runnable mPositionSelectorOnTapAction;
    private Drawable mSelector;
    private boolean mSelectorEnabled;
    private int mSelectorPosition;
    private final Rect mSelectorRect = new Rect();
    private boolean mShouldPositionSelector;
    private Object mViewAttachInfo;
    private int mWidthMeasureSpec;

    public interface a {
    }

    public FloatingGroupExpandableListView(Context context) {
        super(context);
        init();
    }

    public FloatingGroupExpandableListView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public FloatingGroupExpandableListView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        super.setOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(AbsListView absListView, int i) {
                if (FloatingGroupExpandableListView.this.mOnScrollListener != null) {
                    FloatingGroupExpandableListView.this.mOnScrollListener.onScrollStateChanged(absListView, i);
                }
            }

            public void onScroll(AbsListView absListView, int i, int i2, int i3) {
                if (FloatingGroupExpandableListView.this.mOnScrollListener != null) {
                    FloatingGroupExpandableListView.this.mOnScrollListener.onScroll(absListView, i, i2, i3);
                }
                if (FloatingGroupExpandableListView.this.mFloatingGroupEnabled && FloatingGroupExpandableListView.this.mAdapter != null && FloatingGroupExpandableListView.this.mAdapter.getGroupCount() > 0 && i2 > 0) {
                    FloatingGroupExpandableListView.this.createFloatingGroupView(i);
                }
            }
        });
        this.mOnClickAction = new Runnable() {
            public final void run() {
                boolean z = true;
                if (FloatingGroupExpandableListView.this.mOnGroupClickListener != null) {
                    z = true ^ FloatingGroupExpandableListView.this.mOnGroupClickListener.onGroupClick(FloatingGroupExpandableListView.this, FloatingGroupExpandableListView.this.mFloatingGroupView, FloatingGroupExpandableListView.this.mFloatingGroupPosition, FloatingGroupExpandableListView.this.mAdapter.getGroupId(FloatingGroupExpandableListView.this.mFloatingGroupPosition));
                }
                if (z) {
                    if (FloatingGroupExpandableListView.this.mAdapter.isGroupExpanded(FloatingGroupExpandableListView.this.mFloatingGroupPosition)) {
                        FloatingGroupExpandableListView.this.collapseGroup(FloatingGroupExpandableListView.this.mFloatingGroupPosition);
                    } else {
                        FloatingGroupExpandableListView.this.expandGroup(FloatingGroupExpandableListView.this.mFloatingGroupPosition);
                    }
                    FloatingGroupExpandableListView.this.setSelectedGroup(FloatingGroupExpandableListView.this.mFloatingGroupPosition);
                }
            }
        };
        this.mPositionSelectorOnTapAction = new Runnable() {
            public final void run() {
                FloatingGroupExpandableListView.this.positionSelectorOnFloatingGroup();
                FloatingGroupExpandableListView.this.setPressed(true);
                if (FloatingGroupExpandableListView.this.mFloatingGroupView != null) {
                    FloatingGroupExpandableListView.this.mFloatingGroupView.setPressed(true);
                }
            }
        };
        this.mClearSelectorOnTapAction = new Runnable() {
            public final void run() {
                FloatingGroupExpandableListView.this.setPressed(false);
                if (FloatingGroupExpandableListView.this.mFloatingGroupView != null) {
                    FloatingGroupExpandableListView.this.mFloatingGroupView.setPressed(false);
                }
                FloatingGroupExpandableListView.this.invalidate();
            }
        };
        this.mGestureDetector = new GestureDetector(getContext(), new SimpleOnGestureListener() {
            public final void onLongPress(MotionEvent motionEvent) {
                if (FloatingGroupExpandableListView.this.mFloatingGroupView != null && !FloatingGroupExpandableListView.this.mFloatingGroupView.isLongClickable()) {
                    ExpandableListContextMenuInfo expandableListContextMenuInfo = new ExpandableListContextMenuInfo(FloatingGroupExpandableListView.this.mFloatingGroupView, ExpandableListView.getPackedPositionForGroup(FloatingGroupExpandableListView.this.mFloatingGroupPosition), FloatingGroupExpandableListView.this.mAdapter.getGroupId(FloatingGroupExpandableListView.this.mFloatingGroupPosition));
                    try {
                        ahv.a((Object) FloatingGroupExpandableListView.this, (String) "mContextMenuInfo", (Object) expandableListContextMenuInfo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    FloatingGroupExpandableListView.this.showContextMenu();
                }
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        unregisterDataSetObserver();
        registerDataSetObserver();
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        unregisterDataSetObserver();
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        this.mWidthMeasureSpec = i;
    }

    /* access modifiers changed from: protected */
    public void dispatchDraw(Canvas canvas) {
        try {
            if (VERSION.SDK_INT >= 14) {
                this.mSelectorPosition = ((Integer) ahv.a((Object) this, (String) "mSelectorPosition")).intValue();
            } else {
                this.mSelectorPosition = ((Integer) ahv.a((Object) this, (String) "mMotionPosition")).intValue();
            }
            this.mSelectorRect.set((Rect) ahv.a((Object) this, (String) "mSelectorRect"));
            if (!this.mDrawSelectorOnTop) {
                drawDefaultSelector(canvas);
            }
            super.dispatchDraw(canvas);
            if (this.mFloatingGroupEnabled && this.mFloatingGroupView != null) {
                if (!this.mDrawSelectorOnTop) {
                    drawFloatingGroupSelector(canvas);
                }
                canvas.save();
                canvas.clipRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
                if (this.mFloatingGroupView.getVisibility() == 0) {
                    drawChild(canvas, this.mFloatingGroupView, getDrawingTime());
                }
                drawFloatingGroupIndicator(canvas);
                canvas.restore();
                if (this.mDrawSelectorOnTop) {
                    drawDefaultSelector(canvas);
                    drawFloatingGroupSelector(canvas);
                }
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        if (action == 0 || action == 3) {
            this.mHandledByOnInterceptTouchEvent = false;
            this.mHandledByOnTouchEvent = false;
            this.mShouldPositionSelector = false;
        }
        if (!this.mHandledByOnInterceptTouchEvent && !this.mHandledByOnTouchEvent && this.mFloatingGroupView != null) {
            int[] iArr = new int[2];
            getLocationInWindow(iArr);
            if (new RectF((float) (iArr[0] + this.mFloatingGroupView.getLeft()), (float) (iArr[1] + this.mFloatingGroupView.getTop()), (float) (iArr[0] + this.mFloatingGroupView.getRight()), (float) (iArr[1] + this.mFloatingGroupView.getBottom())).contains(motionEvent.getRawX(), motionEvent.getRawY())) {
                if (this.mSelectorEnabled) {
                    switch (action) {
                        case 0:
                            this.mShouldPositionSelector = true;
                            removeCallbacks(this.mPositionSelectorOnTapAction);
                            postDelayed(this.mPositionSelectorOnTapAction, (long) ViewConfiguration.getTapTimeout());
                            break;
                        case 1:
                            positionSelectorOnFloatingGroup();
                            setPressed(true);
                            if (this.mFloatingGroupView != null) {
                                this.mFloatingGroupView.setPressed(true);
                            }
                            removeCallbacks(this.mClearSelectorOnTapAction);
                            postDelayed(this.mClearSelectorOnTapAction, (long) ViewConfiguration.getPressedStateDuration());
                            break;
                    }
                }
                if (this.mFloatingGroupView.dispatchTouchEvent(motionEvent)) {
                    this.mGestureDetector.onTouchEvent(motionEvent);
                    onInterceptTouchEvent(motionEvent);
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.mHandledByOnInterceptTouchEvent = super.onInterceptTouchEvent(motionEvent);
        return this.mHandledByOnInterceptTouchEvent;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.mHandledByOnTouchEvent = super.onTouchEvent(motionEvent);
        return this.mHandledByOnTouchEvent;
    }

    public void setSelector(Drawable drawable) {
        super.setSelector(new ColorDrawable(0));
        if (this.mSelector != null) {
            this.mSelector.setCallback(null);
            unscheduleDrawable(this.mSelector);
        }
        this.mSelector = drawable;
        this.mSelector.setCallback(this);
    }

    public void setDrawSelectorOnTop(boolean z) {
        super.setDrawSelectorOnTop(z);
        this.mDrawSelectorOnTop = z;
    }

    public void setAdapter(ExpandableListAdapter expandableListAdapter) {
        if (!(expandableListAdapter instanceof FloatingBaseExpandableListAdapter)) {
            throw new IllegalArgumentException("The adapter must be an instance of WrapperExpandableListAdapter");
        }
        setAdapter((FloatingBaseExpandableListAdapter) expandableListAdapter);
    }

    public void setAdapter(FloatingBaseExpandableListAdapter floatingBaseExpandableListAdapter) {
        super.setAdapter(floatingBaseExpandableListAdapter);
        unregisterDataSetObserver();
        this.mAdapter = floatingBaseExpandableListAdapter;
        registerDataSetObserver();
    }

    private void unregisterDataSetObserver() {
        if (this.mAdapter != null && this.mDataSetObserver != null) {
            this.mAdapter.unregisterDataSetObserver(this.mDataSetObserver);
            this.mDataSetObserver = null;
        }
    }

    private void registerDataSetObserver() {
        if (this.mAdapter != null && this.mDataSetObserver == null) {
            this.mDataSetObserver = new DataSetObserver() {
                public final void onChanged() {
                    FloatingGroupExpandableListView.this.mFloatingGroupView = null;
                }

                public final void onInvalidated() {
                    FloatingGroupExpandableListView.this.mFloatingGroupView = null;
                }
            };
            this.mAdapter.registerDataSetObserver(this.mDataSetObserver);
        }
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        super.setOnGroupClickListener(onGroupClickListener);
        this.mOnGroupClickListener = onGroupClickListener;
    }

    public void setFloatingGroupEnabled(boolean z) {
        this.mFloatingGroupEnabled = z;
    }

    public void setOnScrollFloatingGroupListener(a aVar) {
        this.mOnScrollFloatingGroupListener = aVar;
    }

    /* access modifiers changed from: private */
    public void createFloatingGroupView(int i) {
        int i2;
        this.mFloatingGroupView = null;
        this.mFloatingGroupPosition = getPackedPositionGroup(getExpandableListPosition(i));
        int i3 = 0;
        for (int i4 = 0; i4 < getChildCount(); i4++) {
            View childAt = getChildAt(i4);
            Object tag = childAt.getTag(R.id.fgelv_tag_changed_visibility);
            if ((tag instanceof Boolean) && ((Boolean) tag).booleanValue()) {
                childAt.setVisibility(0);
                childAt.setTag(R.id.fgelv_tag_changed_visibility, null);
            }
        }
        if (this.mFloatingGroupEnabled) {
            int flatListPosition = getFlatListPosition(getPackedPositionForGroup(this.mFloatingGroupPosition)) - i;
            if (flatListPosition >= 0 && flatListPosition < getChildCount()) {
                View childAt2 = getChildAt(flatListPosition);
                if (childAt2.getTop() < getPaddingTop()) {
                    if (childAt2.getTop() < getPaddingTop() && childAt2.getVisibility() == 0) {
                        childAt2.setVisibility(4);
                        childAt2.setTag(R.id.fgelv_tag_changed_visibility, Boolean.TRUE);
                    }
                } else {
                    return;
                }
            }
            if (this.mFloatingGroupPosition >= 0) {
                this.mFloatingGroupView = this.mAdapter.getGroupView(this.mFloatingGroupPosition, this.mAdapter.isGroupExpanded(this.mFloatingGroupPosition), this.mFloatingGroupView, this);
                if (!this.mFloatingGroupView.isClickable()) {
                    this.mSelectorEnabled = true;
                    this.mFloatingGroupView.setOnClickListener(new OnClickListener() {
                        public final void onClick(View view) {
                            FloatingGroupExpandableListView.this.postDelayed(FloatingGroupExpandableListView.this.mOnClickAction, (long) ViewConfiguration.getPressedStateDuration());
                        }
                    });
                } else {
                    this.mSelectorEnabled = false;
                }
                loadAttachInfo();
                setAttachInfo(this.mFloatingGroupView);
            }
            if (this.mFloatingGroupView != null) {
                LayoutParams layoutParams = this.mFloatingGroupView.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = generateDefaultLayoutParams();
                }
                int childMeasureSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getPaddingLeft() + getPaddingRight(), layoutParams.width);
                int i5 = layoutParams.height;
                if (i5 > 0) {
                    i2 = MeasureSpec.makeMeasureSpec(i5, UCCore.VERIFY_POLICY_QUICK);
                } else {
                    i2 = MeasureSpec.makeMeasureSpec(0, 0);
                }
                this.mFloatingGroupView.measure(childMeasureSpec, i2);
                int flatListPosition2 = getFlatListPosition(getPackedPositionForGroup(this.mFloatingGroupPosition + 1)) - i;
                if (flatListPosition2 >= 0 && flatListPosition2 < getChildCount()) {
                    View childAt3 = getChildAt(flatListPosition2);
                    if (childAt3 != null && childAt3.getTop() < getPaddingTop() + this.mFloatingGroupView.getMeasuredHeight() + getDividerHeight()) {
                        i3 = childAt3.getTop() - ((getPaddingTop() + this.mFloatingGroupView.getMeasuredHeight()) + getDividerHeight());
                    }
                }
                int paddingLeft = getPaddingLeft();
                int paddingTop = getPaddingTop() + i3;
                this.mFloatingGroupView.layout(paddingLeft, paddingTop, this.mFloatingGroupView.getMeasuredWidth() + paddingLeft, this.mFloatingGroupView.getMeasuredHeight() + paddingTop);
                this.mFloatingGroupScrollY = i3;
            }
        }
    }

    private void loadAttachInfo() {
        if (this.mViewAttachInfo == null) {
            try {
                this.mViewAttachInfo = ahv.a((Object) this, (String) "mAttachInfo");
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
    }

    private void setAttachInfo(View view) {
        if (view != null) {
            if (this.mViewAttachInfo != null) {
                try {
                    ahv.a(this.mViewAttachInfo, (String) "mAttachInfo", (Object) view);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e2) {
                    e2.printStackTrace();
                }
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    setAttachInfo(viewGroup.getChildAt(i));
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void positionSelectorOnFloatingGroup() {
        if (this.mShouldPositionSelector && this.mFloatingGroupView != null) {
            if (VERSION.SDK_INT >= 14) {
                invokeMethod("positionSelector", new Class[]{Integer.TYPE, View.class}, this, Integer.valueOf(getFlatListPosition(getPackedPositionForGroup(this.mFloatingGroupPosition))), this.mFloatingGroupView);
            } else {
                invokeMethod("positionSelector", new Class[]{View.class}, this, this.mFloatingGroupView);
            }
            invalidate();
        }
        this.mShouldPositionSelector = false;
        removeCallbacks(this.mPositionSelectorOnTapAction);
    }

    private void drawDefaultSelector(Canvas canvas) {
        int firstVisiblePosition = this.mSelectorPosition - getFirstVisiblePosition();
        if (firstVisiblePosition >= 0 && firstVisiblePosition < getChildCount() && this.mSelectorRect != null && !this.mSelectorRect.isEmpty()) {
            int flatListPosition = getFlatListPosition(getPackedPositionForGroup(this.mFloatingGroupPosition));
            if (this.mFloatingGroupView == null || this.mSelectorPosition != flatListPosition) {
                drawSelector(canvas);
            }
        }
    }

    private void drawFloatingGroupSelector(Canvas canvas) {
        if (this.mSelectorRect != null && !this.mSelectorRect.isEmpty() && this.mSelectorPosition == getFlatListPosition(getPackedPositionForGroup(this.mFloatingGroupPosition))) {
            this.mSelectorRect.set(this.mFloatingGroupView.getLeft(), this.mFloatingGroupView.getTop(), this.mFloatingGroupView.getRight(), this.mFloatingGroupView.getBottom());
            drawSelector(canvas);
        }
    }

    private void drawSelector(Canvas canvas) {
        canvas.save();
        canvas.clipRect(getPaddingLeft(), getPaddingTop(), getWidth() - getPaddingRight(), getHeight() - getPaddingBottom());
        if (isPressed()) {
            this.mSelector.setState(getDrawableState());
        } else {
            this.mSelector.setState(EMPTY_STATE_SET);
        }
        this.mSelector.setBounds(this.mSelectorRect);
        this.mSelector.draw(canvas);
        canvas.restore();
    }

    private void drawFloatingGroupIndicator(Canvas canvas) {
        try {
            Drawable drawable = (Drawable) ahv.a((Object) this, (String) "mGroupIndicator");
            if (drawable != null) {
                drawable.setState(GROUP_STATE_SETS[this.mAdapter.isGroupExpanded(this.mFloatingGroupPosition) | (this.mAdapter.getChildrenCount(this.mFloatingGroupPosition) > 0 ? (char) 2 : 0)]);
                int intValue = ((Integer) ahv.a((Object) this, (String) "mIndicatorLeft")).intValue();
                int intValue2 = ((Integer) ahv.a((Object) this, (String) "mIndicatorRight")).intValue();
                if (VERSION.SDK_INT >= 14) {
                    this.mIndicatorRect.set(intValue + getPaddingLeft(), this.mFloatingGroupView.getTop(), intValue2 + getPaddingLeft(), this.mFloatingGroupView.getBottom());
                } else {
                    this.mIndicatorRect.set(intValue, this.mFloatingGroupView.getTop(), intValue2, this.mFloatingGroupView.getBottom());
                }
                drawable.setBounds(this.mIndicatorRect);
                drawable.draw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean expandGroup(int i) {
        try {
            return super.expandGroup(i);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void expandAllGroup() {
        int groupCount = this.mAdapter.getGroupCount();
        for (int i = 0; i < groupCount; i++) {
            if (!isGroupExpanded(groupCount)) {
                expandGroup(i);
            }
        }
    }

    public void disableGroupExpand() {
        setOnGroupClickListener(new OnGroupClickListener() {
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long j) {
                return true;
            }
        });
    }

    public Object invokeMethod(String str, Class<?>[] clsArr, Object obj, Object... objArr) {
        try {
            return ahv.a(obj.getClass(), obj, str, clsArr, objArr);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
