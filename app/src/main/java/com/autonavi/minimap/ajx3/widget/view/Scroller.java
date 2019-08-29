package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout.LayoutParams;
import com.autonavi.minimap.ajx3.context.AjxContextHandlerCallback;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.dom.AjxDomTree;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.property.HorizontalScrollerProperty;
import com.autonavi.minimap.ajx3.widget.property.ScrollerProperty;
import com.autonavi.minimap.ajx3.widget.view.list.AjxListAdapter;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollBeginListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollEndListener;
import com.autonavi.minimap.ajx3.widget.view.listener.ScrollListener.ScrollInertiaEnd;
import com.uc.webview.export.extension.UCCore;
import java.lang.ref.WeakReference;

@SuppressLint({"ViewConstructor"})
public class Scroller extends SpringScrollView implements AjxContextHandlerCallback, IScrollView, ViewExtension {
    private static int POSITION_BOTTOM = 2;
    private static int POSITION_MIDDLE = 1;
    private static int POSITION_TOP;
    /* access modifiers changed from: private */
    public boolean compensateDecelerate = false;
    /* access modifiers changed from: private */
    public Handler handler;
    /* access modifiers changed from: private */
    public int initialUpY;
    /* access modifiers changed from: private */
    public int initialY;
    private boolean isDecelerate = false;
    private boolean isFingerDrag = false;
    private IAjxContext mAjxContext;
    /* access modifiers changed from: private */
    public AjxListAdapter mAjxList;
    private boolean mIgnoreTouch = false;
    private AbsoluteLayout mInnerView;
    private boolean mIsFirstActionMove = true;
    private boolean mNeedScrollPosition = false;
    private int mPositionType = POSITION_TOP;
    private ScrollerProperty mProperty;
    private float mScrollAccuracy = -1.0f;
    private ScrollBeginListener mScrollBeginListener;
    /* access modifiers changed from: private */
    public ScrollEndListener mScrollEndListener;
    /* access modifiers changed from: private */
    public ScrollInertiaEnd mScrollInertiaEnd;
    private boolean mScrollable = true;
    boolean touchUP = false;

    static class MyHandler extends Handler {
        private static final int MSG_TIME_DELAY = 100;
        private static final int MSG_WHAT_CODE = 100;
        private static final int MSG_WHAT_END = 1001;
        private static final int MSG_WHAT_decelerate = 1002;
        private WeakReference<Scroller> weakReference;

        MyHandler(Scroller scroller) {
            this.weakReference = new WeakReference<>(scroller);
        }

        public void handleMessage(Message message) {
            if (message.what == 100) {
                Scroller scroller = (Scroller) this.weakReference.get();
                if (scroller != null) {
                    int computeVerticalScrollOffset = scroller.computeVerticalScrollOffset();
                    if (computeVerticalScrollOffset == scroller.initialY) {
                        scroller.onScrollIdle(computeVerticalScrollOffset);
                        return;
                    }
                    scroller.initialY = computeVerticalScrollOffset;
                    sendEmptyMessageDelayed(100, 100);
                }
            } else if (message.what == 1001) {
                Scroller scroller2 = (Scroller) this.weakReference.get();
                if (scroller2 != null) {
                    boolean z = scroller2.computeVerticalScrollOffset() != scroller2.initialUpY;
                    scroller2.invokeAttrDecelerate(z);
                    if (scroller2.mScrollEndListener != null) {
                        scroller2.mScrollEndListener.onScrollEnd();
                    }
                    if (!z && scroller2.compensateDecelerate && scroller2.mScrollInertiaEnd != null) {
                        scroller2.mScrollInertiaEnd.onScrollInertiaEnd();
                    }
                    scroller2.compensateDecelerate = false;
                    if (z) {
                        scroller2.handler.removeMessages(1002);
                        scroller2.handler.sendEmptyMessageDelayed(1002, 100);
                    }
                }
            } else {
                if (message.what == 1002) {
                    Scroller scroller3 = (Scroller) this.weakReference.get();
                    if (scroller3 != null && scroller3.touchUP) {
                        int computeVerticalScrollOffset2 = scroller3.computeVerticalScrollOffset();
                        if (computeVerticalScrollOffset2 != scroller3.initialUpY) {
                            scroller3.initialUpY = computeVerticalScrollOffset2;
                            scroller3.handler.removeMessages(1002);
                            sendEmptyMessageDelayed(1002, 100);
                        } else if (scroller3.mScrollInertiaEnd != null) {
                            scroller3.mScrollInertiaEnd.onScrollInertiaEnd();
                        }
                    }
                }
            }
        }
    }

    public Scroller(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        setVerticalScrollBarEnabled(true);
        this.mAjxContext = iAjxContext;
        this.mProperty = new ScrollerProperty(this, iAjxContext);
        this.mInnerView = new AjxAbsoluteLayout(iAjxContext);
        this.mInnerView.setMotionEventSplittingEnabled(false);
        super.addView(this.mInnerView, 0, new LayoutParams(-1, -1));
    }

    public void setContentSize(int i, int i2) {
        this.mInnerView.setMinimumWidth(i);
        this.mInnerView.setMinimumHeight(i2);
    }

    public void requestLayout() {
        super.requestLayout();
        if (!isShown() && this.mAjxContext != null) {
            this.mAjxContext.post(this, null, 0);
        }
    }

    public void handleCallback(Message message) {
        if (getLayoutParams() instanceof AbsoluteLayout.LayoutParams) {
            int i = ((AbsoluteLayout.LayoutParams) getLayoutParams()).x;
            int i2 = ((AbsoluteLayout.LayoutParams) getLayoutParams()).y;
            int i3 = ((AbsoluteLayout.LayoutParams) getLayoutParams()).width;
            int i4 = ((AbsoluteLayout.LayoutParams) getLayoutParams()).height;
            measure(MeasureSpec.makeMeasureSpec(i3, UCCore.VERIFY_POLICY_QUICK), MeasureSpec.makeMeasureSpec(i4, UCCore.VERIFY_POLICY_QUICK));
            layout(i, i2, i3 + i, i4 + i2);
        }
    }

    public void postInvalidateOnAnimation() {
        if (this.mAjxContext != null) {
            AjxDomTree domTree = this.mAjxContext.getDomTree();
            if (domTree != null && !domTree.isDestroy()) {
                AjxView rootView = this.mAjxContext.getDomTree().getRootView();
                if (rootView != null) {
                    if (VERSION.SDK_INT >= 16) {
                        rootView.postOnAnimation(new Runnable() {
                            public void run() {
                                Scroller.this.invalidate();
                                if (Scroller.this.mAjxList != null && Scroller.this.mAjxList.getRecyclerView() != null) {
                                    Scroller.this.mAjxList.getRecyclerView().invalidate();
                                }
                            }
                        });
                        return;
                    } else {
                        rootView.post(new Runnable() {
                            public void run() {
                                Scroller.this.invalidate();
                                if (Scroller.this.mAjxList != null) {
                                    Scroller.this.mAjxList.getRecyclerView().invalidate();
                                }
                            }
                        });
                        return;
                    }
                }
            }
        }
        super.postInvalidateOnAnimation();
    }

    public void setAjxList(AjxListAdapter ajxListAdapter) {
        this.mAjxList = ajxListAdapter;
    }

    public void setNeedScrollPosition(boolean z) {
        this.mNeedScrollPosition = z;
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
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mProperty.onLayout(z, i, i2, i3, i4);
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

    public void setIgnoreTouch(boolean z) {
        this.mIgnoreTouch = z;
    }

    public void updateScrollable(boolean z) {
        this.mScrollable = z;
    }

    public boolean isScrollable() {
        return this.mScrollable;
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollable) {
            return false;
        }
        if (getProperty() == null || getProperty().couldHandleTouch()) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mScrollable || this.mIgnoreTouch) {
            return false;
        }
        if (getProperty() != null && !getProperty().couldHandleTouch()) {
            return false;
        }
        switch (motionEvent.getAction()) {
            case 0:
                if (!this.isDecelerate) {
                    this.compensateDecelerate = false;
                    break;
                } else {
                    this.touchUP = false;
                    this.isDecelerate = false;
                    this.compensateDecelerate = true;
                    break;
                }
            case 1:
                this.mIsFirstActionMove = true;
                this.isDecelerate = true;
                this.touchUP = true;
                this.initialUpY = computeVerticalScrollOffset();
                if (this.handler == null) {
                    this.handler = new MyHandler(this);
                }
                this.handler.sendEmptyMessageDelayed(1001, 100);
                break;
            case 2:
                if (this.mIsFirstActionMove) {
                    this.touchUP = false;
                    this.mIsFirstActionMove = false;
                    this.isFingerDrag = true;
                    this.isDecelerate = false;
                    if (this.mScrollBeginListener != null) {
                        this.mScrollBeginListener.onDragBegin();
                        break;
                    }
                }
                break;
            case 3:
                this.mIsFirstActionMove = true;
                break;
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setScrollAccuracy(float f) {
        this.mScrollAccuracy = f;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mInnerView.setOnClickListener(onClickListener);
    }

    /* access modifiers changed from: protected */
    public void onScrollChanged(int i, int i2, int i3, int i4) {
        super.onScrollChanged(i, i2, i3, i4);
        if (this.mScrollAccuracy > 0.0f) {
            this.mProperty.notifyPropertyListenerWithCompensation("scrollTop", DimensionUtils.pixelToStandardUnit((float) i2), DimensionUtils.pixelToStandardUnit((float) i4), this.mScrollAccuracy);
        }
        setAttribute("scrollTop", Float.valueOf(DimensionUtils.pixelToStandardUnit((float) i2)), false, true, false, true);
        track();
        if (this.mNeedScrollPosition) {
            if (getScrollY() == 0) {
                if (this.mPositionType != POSITION_TOP) {
                    invokeScrollTopToJs("scrollbound", 0);
                    this.mPositionType = POSITION_TOP;
                }
            } else if (((getScrollY() + getHeight()) - getPaddingTop()) - getPaddingBottom() == getChildAt(0).getHeight()) {
                if (this.mPositionType != POSITION_BOTTOM) {
                    invokeScrollTopToJs("scrollbound", getScrollY());
                    this.mPositionType = POSITION_BOTTOM;
                }
            } else if (this.mPositionType != POSITION_MIDDLE) {
                invokeScrollTopToJs("leavebound", getScrollY());
                this.mPositionType = POSITION_MIDDLE;
            }
        }
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

    private void track() {
        if (this.handler == null) {
            this.handler = new MyHandler(this);
        }
        int computeVerticalScrollOffset = computeVerticalScrollOffset();
        if (this.initialY != computeVerticalScrollOffset) {
            this.initialY = computeVerticalScrollOffset;
            this.handler.removeMessages(100);
            this.handler.sendEmptyMessageDelayed(100, 100);
        }
    }

    /* access modifiers changed from: private */
    public void onScrollIdle(int i) {
        invokeScrollTopToJs("", i);
        this.isDecelerate = false;
        this.isFingerDrag = false;
    }

    private void invokeScrollTopToJs(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(DimensionUtils.pixelToStandardUnit((float) i));
        String sb2 = sb.toString();
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(this.mAjxContext.getDomTree().getNodeId(this)).addAttribute("_SCROLL_TOP", sb2).addAttribute("scrollTop", sb2).build());
    }

    /* access modifiers changed from: private */
    public void invokeAttrDecelerate(boolean z) {
        this.mAjxContext.invokeJsEvent(new Builder().setNodeId(this.mAjxContext.getDomTree().getNodeId(this)).addAttribute(HorizontalScrollerProperty.ATTR_DECELERATE, String.valueOf(z)).build());
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
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

    public void updateOverflow(boolean z) {
        this.mInnerView.setClipChildren(z);
        setClipChildren(z);
    }
}
