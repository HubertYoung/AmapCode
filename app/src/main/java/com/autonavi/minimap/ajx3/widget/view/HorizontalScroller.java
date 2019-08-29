package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout.LayoutParams;
import com.alipay.multimedia.js.image.H5ImageBuildUrlPlugin.Params;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.HorizontalScrollerProperty;
import com.autonavi.minimap.ajx3.widget.view.list.AjxListAdapter;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollBeginListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollEndListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollInertiaEnd;
import java.lang.ref.WeakReference;

@SuppressLint({"ViewConstructor"})
public class HorizontalScroller extends SpringHorizontalScrollView implements IScrollView, ViewExtension {
    private static int POSITION_LEFT = 0;
    private static int POSITION_MIDDLE = 1;
    private static int POSITION_RIGHT = 2;
    private Handler handler;
    /* access modifiers changed from: private */
    public boolean isFingerDrag = false;
    private boolean isFirstLayout = true;
    /* access modifiers changed from: private */
    public boolean mActionUpHandled = true;
    private IAjxContext mAjxContext;
    /* access modifiers changed from: private */
    public AjxListAdapter mAjxList;
    private boolean mIgnoreTouch = false;
    private AbsoluteLayout mInnerView;
    private boolean mIsFirstActionMove = true;
    private boolean mNeedScrollPosition = false;
    private int mPositionType = POSITION_LEFT;
    private HorizontalScrollerProperty mProperty;
    private float mScrollAccuracy = 100.0f;
    private ScrollBeginListener mScrollBeginListener;
    /* access modifiers changed from: private */
    public ScrollEndListener mScrollEndListener;
    /* access modifiers changed from: private */
    public ScrollInertiaEnd mScrollInertiaEnd;
    /* access modifiers changed from: private */
    public int mScrollOffsetWhenActionUp = -1;
    private boolean mScrollable = true;

    static class MyHandler extends Handler {
        private static final int MSG_ACTION_UP = 1000;
        private static final int MSG_DETECT_SCROLL_END = 1001;
        private static final int PERIOD_DETECT_SCROLL_END = 100;
        private static final int TIME_DELAY_ACTION_UP = 200;
        private boolean isScrollEndWithDecalerate = true;
        private int lastScrollOffsetWhenScrollInertiaEnd;
        private WeakReference<HorizontalScroller> weakReference;

        MyHandler(HorizontalScroller horizontalScroller) {
            this.weakReference = new WeakReference<>(horizontalScroller);
        }

        public void handleMessage(Message message) {
            HorizontalScroller horizontalScroller = (HorizontalScroller) this.weakReference.get();
            if (horizontalScroller != null) {
                if (message.what != 1001) {
                    if (message.what == 1000) {
                        int access$600 = horizontalScroller.computeHorizontalScrollOffset();
                        if (this.lastScrollOffsetWhenScrollInertiaEnd != access$600) {
                            boolean z = access$600 != horizontalScroller.mScrollOffsetWhenActionUp;
                            this.isScrollEndWithDecalerate = z;
                            horizontalScroller.invokeAttrDecelerate(z);
                            horizontalScroller.invokeScrollLeftToJs("", access$600);
                            if (horizontalScroller.mScrollEndListener != null) {
                                horizontalScroller.mScrollEndListener.onScrollEnd();
                            }
                            horizontalScroller.setActionUpHandled(true);
                            if (z && horizontalScroller.isFingerDrag && horizontalScroller.mActionUpHandled) {
                                horizontalScroller.track();
                            }
                        }
                    }
                } else if (!this.isScrollEndWithDecalerate) {
                    if (!hasMessages(1001)) {
                        this.isScrollEndWithDecalerate = true;
                    }
                } else {
                    removeMessages(1001);
                    int access$100 = horizontalScroller.computeHorizontalScrollOffset();
                    horizontalScroller.invokeAttrDecelerate(true);
                    horizontalScroller.invokeScrollLeftToJs("", access$100);
                    if (!(access$100 == horizontalScroller.mScrollOffsetWhenActionUp || horizontalScroller.mScrollInertiaEnd == null)) {
                        horizontalScroller.mScrollInertiaEnd.onScrollInertiaEnd();
                    }
                    this.lastScrollOffsetWhenScrollInertiaEnd = access$100;
                }
            }
        }
    }

    public HorizontalScroller(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.mAjxContext = iAjxContext;
        setHorizontalScrollBarEnabled(true);
        this.mProperty = new HorizontalScrollerProperty(this, this.mAjxContext);
        this.mInnerView = new AjxAbsoluteLayout(this.mAjxContext);
        this.mInnerView.setMotionEventSplittingEnabled(false);
        super.addView(this.mInnerView, 0, new LayoutParams(-1, -1));
    }

    public void setAjxList(AjxListAdapter ajxListAdapter) {
        this.mAjxList = ajxListAdapter;
    }

    public void postInvalidateOnAnimation() {
        if (!(this.mAjxContext == null || this.mAjxContext.getDomTree() == null)) {
            AjxView rootView = this.mAjxContext.getDomTree().getRootView();
            if (rootView != null) {
                if (VERSION.SDK_INT >= 16) {
                    rootView.postOnAnimation(new Runnable() {
                        public void run() {
                            HorizontalScroller.this.invalidate();
                            if (HorizontalScroller.this.mAjxList != null && HorizontalScroller.this.mAjxList.getRecyclerView() != null) {
                                HorizontalScroller.this.mAjxList.getRecyclerView().invalidate();
                            }
                        }
                    });
                    return;
                } else {
                    rootView.post(new Runnable() {
                        public void run() {
                            HorizontalScroller.this.invalidate();
                            if (HorizontalScroller.this.mAjxList != null) {
                                HorizontalScroller.this.mAjxList.getRecyclerView().invalidate();
                            }
                        }
                    });
                    return;
                }
            }
        }
        super.postInvalidateOnAnimation();
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollable) {
            return false;
        }
        if (getProperty() != null && !getProperty().couldHandleTouch()) {
            return false;
        }
        if (motionEvent != null && (motionEvent.getAction() & 255) == 0) {
            this.mScrollOffsetWhenActionUp = -1;
            this.mActionUpHandled = true;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    public void setIgnoreTouch(boolean z) {
        this.mIgnoreTouch = z;
    }

    public void updateScrollable(boolean z) {
        this.mScrollable = z;
    }

    public boolean isScrollable() {
        return this.mScrollable;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollable) {
            return false;
        }
        if ((getProperty() != null && !getProperty().couldHandleTouch()) || this.mIgnoreTouch) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 1:
            case 3:
                this.mIsFirstActionMove = true;
                this.mScrollOffsetWhenActionUp = computeHorizontalScrollOffset();
                invokeScrollLeftToJs("", this.mScrollOffsetWhenActionUp);
                getMyHandler().sendEmptyMessageDelayed(1000, 200);
                break;
            case 2:
                if (this.mIsFirstActionMove) {
                    this.mIsFirstActionMove = false;
                    if (this.mScrollBeginListener != null) {
                        this.mScrollBeginListener.onDragBegin();
                    }
                    this.mScrollOffsetWhenActionUp = -1;
                }
                setFingerDrag(true);
                setActionUpHandled(false);
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    /* access modifiers changed from: private */
    public void invokeScrollLeftToJs(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(DimensionUtils.pixelToStandardUnit((float) i));
        String sb2 = sb.toString();
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(this.mAjxContext.getDomTree().getNodeId(this)).addAttribute(HorizontalScrollerProperty.SCROLL_LEFT, sb2).addAttribute(HorizontalScrollerProperty.ATTR_SCROLL_LEFT, sb2).build());
    }

    public void setFingerDrag(boolean z) {
        this.isFingerDrag = z;
    }

    public void setActionUpHandled(boolean z) {
        this.mActionUpHandled = z;
    }

    public void setNeedScrollPosition(boolean z) {
        this.mNeedScrollPosition = z;
    }

    public void setScrollAccuracy(float f) {
        this.mScrollAccuracy = f;
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.mScrollAccuracy > 0.0f) {
            this.mProperty.notifyPropertyListenerWithCompensation(HorizontalScrollerProperty.SCROLL_LEFT, DimensionUtils.pixelToStandardUnit((float) i), DimensionUtils.pixelToStandardUnit((float) i3), this.mScrollAccuracy);
        }
        setAttribute(HorizontalScrollerProperty.SCROLL_LEFT, Float.valueOf(DimensionUtils.pixelToStandardUnit((float) i)), false, true, false, true);
        if (this.isFingerDrag && this.mActionUpHandled) {
            track();
        }
        if (this.mNeedScrollPosition) {
            if (getScrollX() == 0) {
                if (this.mPositionType != POSITION_LEFT) {
                    invokeScrollLeftToJs("scrollbound", 0);
                    this.mPositionType = POSITION_LEFT;
                }
            } else if (((getScrollX() + getWidth()) - getPaddingLeft()) - getPaddingRight() == getChildAt(0).getWidth()) {
                if (this.mPositionType != POSITION_RIGHT) {
                    invokeScrollLeftToJs("scrollbound", getScrollX());
                    this.mPositionType = POSITION_RIGHT;
                }
            } else if (this.mPositionType != POSITION_MIDDLE) {
                invokeScrollLeftToJs("leavebound", getScrollX());
                this.mPositionType = POSITION_MIDDLE;
            }
        }
    }

    /* access modifiers changed from: private */
    public void track() {
        getMyHandler().removeMessages(1001);
        getMyHandler().sendEmptyMessageDelayed(1001, 100);
    }

    private Handler getMyHandler() {
        if (this.handler == null) {
            this.handler = new MyHandler(this);
        }
        return this.handler;
    }

    /* access modifiers changed from: private */
    public void invokeAttrDecelerate(boolean z) {
        this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mAjxContext.getDomTree().getNodeId(this)).addAttribute(HorizontalScrollerProperty.ATTR_DECELERATE, String.valueOf(z)).build());
    }

    public void setContentSize(int i, int i2) {
        this.mInnerView.setMinimumWidth(i);
        this.mInnerView.setMinimumHeight(i2);
    }

    public void addView(View view) {
        this.mInnerView.addView(view);
    }

    public void addView(View view, int i) {
        this.mInnerView.addView(view, i);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        this.mInnerView.addView(view, layoutParams);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        this.mInnerView.addView(view, i, layoutParams);
    }

    public void removeView(View view) {
        this.mInnerView.removeView(view);
    }

    public void removeAllViews() {
        this.mInnerView.removeAllViews();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mProperty.onDraw(canvas);
    }

    public void draw(Canvas canvas) {
        this.mProperty.beforeDraw(canvas);
        super.draw(canvas);
        this.mProperty.afterDraw(canvas);
    }

    public void bind(AjxDomNode ajxDomNode) {
        this.mProperty.bind(ajxDomNode);
    }

    public void updateDiffProperty() {
        this.mProperty.updateDiffProperty();
    }

    public void setSize(String str, float f, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateSize(str, f, z, z2, z3, z4);
    }

    public float getSize(String str) {
        return this.mProperty.getSize(str);
    }

    public void setStyle(int i, int i2, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateStyle(i, i2, obj, z, z2, z3, z4);
    }

    public Object getStyle(int i) {
        return this.mProperty.getStyle(i);
    }

    public void setAttribute(String str, Object obj, boolean z, boolean z2, boolean z3, boolean z4) {
        this.mProperty.updateAttribute(str, obj, z, z2, z3, z4);
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mInnerView.setOnClickListener(onClickListener);
    }

    public void setScrollBeginListener(ScrollBeginListener scrollBeginListener) {
        this.mScrollBeginListener = scrollBeginListener;
    }

    public void setScrollEndListener(ScrollEndListener scrollEndListener) {
        this.mScrollEndListener = scrollEndListener;
    }

    public void setScrollInertiaEnd(ScrollInertiaEnd scrollInertiaEnd) {
        this.mScrollInertiaEnd = scrollInertiaEnd;
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public View findTouchChild(float f, float f2) {
        return findChildViewUnder(f, f2);
    }

    private View findChildViewUnder(float f, float f2) {
        float scrollX = f + ((float) getScrollX());
        float scrollY = f2 + ((float) getScrollY());
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (scrollX >= ((float) childAt.getLeft()) && scrollX <= ((float) childAt.getRight()) && scrollY >= ((float) childAt.getTop()) && scrollY <= ((float) childAt.getBottom())) {
                return childAt;
            }
        }
        return null;
    }

    public ViewGroup.LayoutParams generateInnerDefaultLayoutParams() {
        return new AbsoluteLayout.LayoutParams(-2, -2, 0, 0);
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
        if (this.mAjxList != null && this.isFirstLayout) {
            setScrollX(parseScrollLeft(getAttribute(HorizontalScrollerProperty.SCROLL_LEFT)));
            this.isFirstLayout = false;
        }
    }

    private int parseScrollLeft(Object obj) {
        float f;
        if (obj instanceof Float) {
            f = ((Float) obj).floatValue();
        } else if (obj instanceof String) {
            String str = (String) obj;
            int indexOf = str.indexOf(Params.UNIT_PX);
            if (indexOf != -1) {
                str = str.substring(0, indexOf);
            }
            f = Float.valueOf(str).floatValue();
        } else {
            f = 0.0f;
        }
        return DimensionUtils.standardUnitToPixel(f);
    }

    public void updateOverflow(boolean z) {
        this.mInnerView.setClipChildren(z);
        setClipChildren(z);
    }
}
