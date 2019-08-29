package com.autonavi.minimap.ajx3.widget.property;

import android.graphics.Matrix;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.htmcompat.AjxImageGetter.AjxImageSpan;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.DimensionUtils;
import com.autonavi.minimap.ajx3.util.LogRecorder;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.view.AjxAbsoluteLayout;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.Html;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class TouchHelper {
    private static final boolean DEBUG = false;
    private static Method GET_INVERSE_MATRIX = null;
    private static Method HAS_INVERSE_MATRIX = null;
    private static float[] LOCATION = null;
    private static final String METHOD_NAME_GET_INVERSE_MATRIX = "getInverseMatrix";
    private static final String METHOD_NAME_HAS_INVERSE_MATRIX = "hasIdentityMatrix";
    static final int MOVE_DIRECT_DOWN = 4;
    static final int MOVE_DIRECT_LEFT = 1;
    private static final int MOVE_DIRECT_NONE = 0;
    static final int MOVE_DIRECT_RIGHT = 2;
    static final int MOVE_DIRECT_UP = 3;
    private static final int MSG_LONG_PRESS_TIME_OUT = 9009;
    private static final String TAG = "Helper";
    private static final float[] TMP_MATRIX = new float[9];
    private static MotionEvent tmpMotionEvent;
    protected final IAjxContext mAjxContext;
    private float mAjxViewX;
    private float mAjxViewY;
    private GestureAttribute mClick;
    private View mClickableView = null;
    private MotionEvent mDownEvent;
    private float mDownMotionX;
    private float mDownMotionY;
    private View mDragableView;
    private ArrayList<Long> mForbidOwners = new ArrayList<>();
    private String mGroupId;
    private HorizontalScroller mHScroller;
    private HoverHelper mHoverHelper;
    private long mHoverId = -1;
    private boolean mIgnoreClick = false;
    /* access modifiers changed from: private */
    public boolean mIsBeingDragged = false;
    /* access modifiers changed from: private */
    public boolean mIsHandledLongPress = false;
    private boolean mIsTalkBackServiceEnable = false;
    private boolean mIsUpHandled = false;
    private AjxList mList = null;
    private View mListHeader = null;
    /* access modifiers changed from: private */
    public View mLongClickableView = null;
    private Handler mLongPressHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message message) {
            if (message.what == TouchHelper.MSG_LONG_PRESS_TIME_OUT) {
                removeMessages(TouchHelper.MSG_LONG_PRESS_TIME_OUT);
                if (TouchHelper.this.mIsBeingDragged) {
                    TouchHelper.this.mLongClickableView = null;
                }
                if (TouchHelper.this.mLongClickableView != null) {
                    if (TouchHelper.this.mTarget != null) {
                        TouchHelper.this.mAjxContext.invokeJsEvent(new Builder().setEventName("longpress").setNodeId(TouchHelper.this.mAjxContext.getDomTree().getNodeId(TouchHelper.this.mTarget.mView)).setAttribute(TouchHelper.this.mTouchDownParcel).build());
                    }
                    TouchHelper.this.mIsHandledLongPress = true;
                    TouchHelper.this.mLongClickableView = null;
                }
            }
        }
    };
    private MotionEventChangeListener mMotionEventChangeListener;
    private int mMoveDirection = 0;
    private float mOffsetX;
    private float mOffsetY;
    private float mPreX;
    private float mPreY;
    private PullToRefreshList mPullToRefresh = null;
    private AjxView mRoot;
    /* access modifiers changed from: private */
    public GestureAttribute mTarget;
    /* access modifiers changed from: private */
    public Parcel mTouchDownParcel;
    private boolean mTouchEnable = true;
    private View mTouchEndView;
    private float[] mTouchMovePosition = new float[4];
    private View mTouchStartView;
    private VelocityTracker mTracker;
    private Scroller mVScroller;
    private AjxViewPager mViewPager = null;

    interface MotionEventChangeListener {
        void onMotionEventChange(MotionEvent motionEvent);
    }

    private boolean couldPopUpMove(int i) {
        return true;
    }

    public TouchHelper(IAjxContext iAjxContext, AjxView ajxView, boolean z) {
        this.mAjxContext = iAjxContext;
        this.mRoot = ajxView;
        this.mIsTalkBackServiceEnable = z;
        this.mHoverHelper = new HoverHelper();
    }

    public void updateTalkBackServiceEnable(boolean z) {
        this.mIsTalkBackServiceEnable = z;
    }

    private void clear() {
        this.mLongClickableView = null;
        this.mClickableView = null;
        this.mDownMotionX = 0.0f;
        this.mDownMotionY = 0.0f;
        this.mIsBeingDragged = false;
        this.mIsHandledLongPress = false;
        this.mTouchDownParcel = null;
        this.mTouchStartView = null;
        this.mTouchEndView = null;
        this.mDragableView = null;
        this.mHScroller = null;
        this.mVScroller = null;
        this.mMoveDirection = 0;
        this.mTarget = null;
        this.mClick = null;
        this.mListHeader = null;
        this.mList = null;
        this.mPullToRefresh = null;
        this.mViewPager = null;
        this.mPreY = 0.0f;
        this.mPreX = 0.0f;
        this.mOffsetX = 0.0f;
        this.mOffsetY = 0.0f;
        if (this.mTracker != null) {
            this.mTracker.clear();
        }
        this.mTracker = null;
        this.mDownEvent = null;
        this.mHoverHelper.clear();
    }

    public float[] getTouchMovePosition() {
        return this.mTouchMovePosition;
    }

    public void setIgnoreClickFlag() {
        this.mIgnoreClick = true;
    }

    public boolean handleMotionEvent(MotionEvent motionEvent, ViewGroup viewGroup) {
        if (this.mMotionEventChangeListener != null) {
            this.mMotionEventChangeListener.onMotionEventChange(motionEvent);
        }
        if (motionEvent == null || this.mAjxContext == null || this.mAjxContext.hasDestroy()) {
            clear();
            return false;
        } else if (!this.mTouchEnable || forbidEvents()) {
            return true;
        } else {
            int action = motionEvent.getAction() & 255;
            if (action == 0) {
                clear();
                this.mIgnoreClick = false;
                if (this.mRoot.mIsFromPoiSimulate) {
                    this.mTarget = this.mRoot.getProperty().mGestureAttribute;
                    this.mDragableView = this.mRoot;
                    this.mTouchEndView = this.mRoot;
                    this.mTouchStartView = this.mRoot;
                } else {
                    findTarget(motionEvent, viewGroup);
                    if (this.mTarget != null) {
                        findHasEventView(this.mTarget);
                        if (this.mLongClickableView != null) {
                            this.mLongPressHandler.sendEmptyMessageDelayed(MSG_LONG_PRESS_TIME_OUT, (long) ViewConfiguration.getLongPressTimeout());
                        }
                        this.mAjxViewX = this.mAjxContext.getDomTree().getRootViewScreenX();
                        this.mAjxViewY = this.mAjxContext.getDomTree().getRootViewScreenY();
                    }
                }
                recoverViewTouch();
            }
            if (!(this.mTouchEndView == null && this.mTouchStartView == null && this.mClickableView == null && this.mLongClickableView == null && this.mDragableView == null && !this.mHoverHelper.needHandleHover())) {
                realHandleEvent(motionEvent);
            }
            if (!this.mRoot.mIsFromPoiSimulate && this.mTarget == null) {
                return false;
            }
            if (action == 1 || action == 3) {
                clear();
            }
            return true;
        }
    }

    private void realHandleEvent(MotionEvent motionEvent) {
        float f;
        float f2;
        float f3;
        MotionEvent motionEvent2 = motionEvent;
        int action = motionEvent.getAction() & 255;
        if (action != 6) {
            int i = 2;
            switch (action) {
                case 0:
                    this.mTouchMovePosition[0] = motionEvent.getRawX() - this.mAjxViewX;
                    this.mTouchMovePosition[1] = motionEvent.getRawY() - this.mAjxViewY;
                    this.mTouchMovePosition[2] = motionEvent.getRawX();
                    this.mTouchMovePosition[3] = motionEvent.getRawY();
                    if (this.mTarget != null) {
                        this.mRoot.getProperty().updateAttribute("scrollXSpeed", Integer.valueOf(0), false, true, true, true);
                        this.mRoot.getProperty().updateAttribute("scrollYSpeed", Integer.valueOf(0), false, true, true, true);
                    }
                    this.mIsUpHandled = false;
                    this.mDownMotionX = motionEvent.getRawX();
                    this.mDownMotionY = motionEvent.getRawY();
                    this.mIsBeingDragged = false;
                    if (!(this.mTouchStartView == null && this.mClickableView == null && this.mLongClickableView == null)) {
                        float pixelToStandardUnit = DimensionUtils.pixelToStandardUnit(motionEvent.getRawX() - this.mAjxViewX);
                        float pixelToStandardUnit2 = DimensionUtils.pixelToStandardUnit(motionEvent.getRawY() - this.mAjxViewY);
                        float pixelToStandardUnit3 = DimensionUtils.pixelToStandardUnit(motionEvent.getRawX());
                        float pixelToStandardUnit4 = DimensionUtils.pixelToStandardUnit(motionEvent.getRawY());
                        if (this.mTouchStartView != null) {
                            this.mAjxContext.getDomTree().getLinkageAnimatorManager().clearDisposableObserver();
                            f = pixelToStandardUnit;
                            f3 = pixelToStandardUnit4;
                            f2 = pixelToStandardUnit3;
                            invokeTouch("touchstart", (double) pixelToStandardUnit, (double) pixelToStandardUnit2, (double) pixelToStandardUnit3, (double) pixelToStandardUnit4, this.mTouchStartView);
                        } else {
                            f3 = pixelToStandardUnit4;
                            f2 = pixelToStandardUnit3;
                            f = pixelToStandardUnit;
                        }
                        if (!(this.mClickableView == null && this.mLongClickableView == null)) {
                            this.mTouchDownParcel = new Parcel();
                            this.mTouchDownParcel.writeBoolean(true);
                            this.mTouchDownParcel.writeDouble((double) f);
                            this.mTouchDownParcel.writeDouble((double) pixelToStandardUnit2);
                            this.mTouchDownParcel.writeDouble((double) f2);
                            this.mTouchDownParcel.writeDouble((double) f3);
                            this.mTouchDownParcel.writeString("");
                        }
                    }
                    this.mHoverHelper.handleDown();
                    if (this.mDragableView != null) {
                        MotionEvent motionEvent3 = motionEvent;
                        this.mDownEvent = MotionEvent.obtainNoHistory(motionEvent);
                        if (this.mTracker == null) {
                            this.mTracker = VelocityTracker.obtain();
                        } else {
                            this.mTracker.clear();
                        }
                        this.mTracker.addMovement(motionEvent3);
                        return;
                    }
                    break;
                case 1:
                case 3:
                    if (!this.mIsUpHandled) {
                        handleUP(motionEvent);
                    }
                    this.mIsUpHandled = false;
                    break;
                case 2:
                    this.mTouchMovePosition[0] = motionEvent.getRawX() - this.mAjxViewX;
                    this.mTouchMovePosition[1] = motionEvent.getRawY() - this.mAjxViewY;
                    this.mTouchMovePosition[2] = motionEvent.getRawX();
                    this.mTouchMovePosition[3] = motionEvent.getRawY();
                    int pointerId = motionEvent2.getPointerId(motionEvent.getActionIndex());
                    if (this.mIsTalkBackServiceEnable || pointerId == 0) {
                        if (!this.mIsBeingDragged) {
                            float rawY = motionEvent.getRawY() - this.mDownMotionY;
                            float rawX = motionEvent.getRawX() - this.mDownMotionX;
                            float scaledTouchSlop = (float) ViewConfiguration.get(this.mRoot.getContext()).getScaledTouchSlop();
                            if (Math.abs(rawY) > (this.mRoot.mIsFromPoiSimulate ? 0.0f : scaledTouchSlop) || Math.abs(rawX) > scaledTouchSlop) {
                                this.mIsBeingDragged = true;
                                this.mLongPressHandler.removeMessages(MSG_LONG_PRESS_TIME_OUT);
                                if (this.mLongClickableView != null) {
                                    this.mLongClickableView = null;
                                }
                                if (Math.abs(rawY) >= Math.abs(rawX)) {
                                    this.mMoveDirection = rawY > 0.0f ? 4 : 3;
                                } else {
                                    this.mMoveDirection = rawX > 0.0f ? 2 : 1;
                                }
                                if (this.mViewPager != null && this.mViewPager.needEatVerticalTouch() && (this.mMoveDirection == 4 || this.mMoveDirection == 3)) {
                                    if (rawX <= 0.0f) {
                                        i = 1;
                                    }
                                    this.mMoveDirection = i;
                                }
                                if (this.mDownEvent != null) {
                                    this.mPreY = this.mDownEvent.getRawY();
                                    this.mPreX = this.mDownEvent.getRawX();
                                    handleDrage(this.mDownEvent, false);
                                    this.mDownEvent = null;
                                }
                                this.mHoverHelper.handleDrag();
                            }
                        }
                        handleDrage(motionEvent2, true);
                        return;
                    }
            }
            return;
        }
        if (motionEvent2.getPointerId(motionEvent.getActionIndex()) == 0 && !this.mIsUpHandled) {
            handleUP(motionEvent);
            this.mIsUpHandled = true;
        }
    }

    private void recoverViewTouch() {
        if (this.mPullToRefresh != null) {
            ((AjxList) this.mPullToRefresh.getRefreshableView()).enableTouch(1);
        }
        if (this.mVScroller != null) {
            this.mVScroller.setIgnoreTouch(false);
        }
        if (this.mHScroller != null) {
            this.mHScroller.setIgnoreTouch(false);
        }
        if (this.mViewPager != null) {
            this.mViewPager.setIgnoreTouch(false);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:20:0x005b, code lost:
        if (r0.mVScroller == r0.mDragableView) goto L_0x0075;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0070, code lost:
        if (r0.mHScroller == r0.mDragableView) goto L_0x0075;
     */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00c2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void handleDrage(android.view.MotionEvent r18, boolean r19) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            android.view.View r2 = r0.mDragableView
            if (r2 == 0) goto L_0x00d1
            boolean r2 = r0.mIsBeingDragged
            if (r2 == 0) goto L_0x00d1
            float r2 = r18.getRawY()
            float r3 = r18.getRawX()
            float r4 = r0.mPreY
            float r4 = r2 - r4
            float r5 = r0.mPreX
            float r5 = r3 - r5
            r0.mPreY = r2
            r0.mPreX = r3
            float r2 = r0.mOffsetX
            float r2 = r2 + r5
            r0.mOffsetX = r2
            float r2 = r0.mOffsetY
            float r2 = r2 + r4
            r0.mOffsetY = r2
            android.view.View r2 = r0.mDragableView
            com.autonavi.minimap.ajx3.widget.view.ViewExtension r2 = (com.autonavi.minimap.ajx3.widget.view.ViewExtension) r2
            com.autonavi.minimap.ajx3.widget.property.BaseProperty r2 = r2.getProperty()
            if (r2 != 0) goto L_0x0035
            return
        L_0x0035:
            int r3 = r0.mMoveDirection
            r6 = 1
            r7 = 0
            if (r3 == r6) goto L_0x0060
            int r3 = r0.mMoveDirection
            r8 = 2
            if (r3 != r8) goto L_0x0041
            goto L_0x0060
        L_0x0041:
            int r3 = r0.mMoveDirection
            r8 = 3
            if (r3 == r8) goto L_0x004b
            int r3 = r0.mMoveDirection
            r8 = 4
            if (r3 != r8) goto L_0x0073
        L_0x004b:
            com.autonavi.minimap.ajx3.widget.view.Scroller r3 = r0.mVScroller
            if (r3 == 0) goto L_0x0073
            com.autonavi.minimap.ajx3.widget.view.Scroller r3 = r0.mVScroller
            boolean r3 = r3.isScrollable()
            if (r3 == 0) goto L_0x0073
            com.autonavi.minimap.ajx3.widget.view.Scroller r3 = r0.mVScroller
            android.view.View r8 = r0.mDragableView
            if (r3 != r8) goto L_0x005e
            goto L_0x0075
        L_0x005e:
            r6 = 0
            goto L_0x0075
        L_0x0060:
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r3 = r0.mHScroller
            if (r3 == 0) goto L_0x0073
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r3 = r0.mHScroller
            boolean r3 = r3.isScrollable()
            if (r3 == 0) goto L_0x0073
            com.autonavi.minimap.ajx3.widget.view.HorizontalScroller r3 = r0.mHScroller
            android.view.View r8 = r0.mDragableView
            if (r3 != r8) goto L_0x005e
            goto L_0x0075
        L_0x0073:
            r6 = 0
            r7 = 1
        L_0x0075:
            if (r7 != 0) goto L_0x0079
            if (r6 == 0) goto L_0x00c0
        L_0x0079:
            float r3 = r18.getRawX()
            float r6 = r0.mAjxViewX
            float r3 = r3 - r6
            float r9 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r3)
            float r3 = r18.getRawY()
            float r6 = r0.mAjxViewY
            float r3 = r3 - r6
            float r10 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r3)
            float r3 = r18.getRawX()
            float r11 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r3)
            float r3 = r18.getRawY()
            float r12 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r3)
            float r13 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r5)
            float r14 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r4)
            float r3 = r0.mOffsetX
            float r15 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r3)
            float r3 = r0.mOffsetY
            float r16 = com.autonavi.minimap.ajx3.util.DimensionUtils.pixelToStandardUnit(r3)
            com.autonavi.minimap.ajx3.widget.property.GestureAttribute r8 = r2.mGestureAttribute
            com.autonavi.minimap.ajx3.widget.property.GestureAttribute$AnimationAttribute r3 = r8.getAninationAttribute(r9, r10, r11, r12, r13, r14, r15, r16)
            com.autonavi.minimap.ajx3.widget.property.GestureAttribute r2 = r2.mGestureAttribute
            int r4 = r0.mMoveDirection
            r2.handleMotionEvent(r1, r3, r7, r4)
        L_0x00c0:
            if (r19 == 0) goto L_0x00d1
            android.view.VelocityTracker r2 = r0.mTracker
            if (r2 != 0) goto L_0x00cc
            android.view.VelocityTracker r2 = android.view.VelocityTracker.obtain()
            r0.mTracker = r2
        L_0x00cc:
            android.view.VelocityTracker r2 = r0.mTracker
            r2.addMovement(r1)
        L_0x00d1:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.TouchHelper.handleDrage(android.view.MotionEvent, boolean):void");
    }

    private void handleUP(MotionEvent motionEvent) {
        this.mLongPressHandler.removeMessages(MSG_LONG_PRESS_TIME_OUT);
        if (this.mLongClickableView != null) {
            this.mLongClickableView = null;
        }
        boolean z = true;
        if (this.mDragableView != null && this.mIsBeingDragged) {
            if (this.mTracker != null) {
                this.mTracker.computeCurrentVelocity(1000);
                int xVelocity = (int) this.mTracker.getXVelocity();
                int yVelocity = (int) this.mTracker.getYVelocity();
                boolean couldPopUpMove = couldPopUpMove(this.mMoveDirection);
                BaseProperty property = ((ViewExtension) this.mDragableView).getProperty();
                if (property != null && property.mGestureAttribute.mMoveDirection) {
                    if (this.mMoveDirection == 3 || this.mMoveDirection == 4) {
                        xVelocity = 0;
                    } else if (this.mMoveDirection == 1 || this.mMoveDirection == 2) {
                        yVelocity = 0;
                    }
                }
                if (xVelocity != 0 && couldPopUpMove) {
                    this.mRoot.getProperty().updateAttribute("scrollXSpeed", Integer.valueOf(xVelocity), false, true, true, true);
                }
                if (yVelocity != 0 && couldPopUpMove) {
                    this.mRoot.getProperty().updateAttribute("scrollYSpeed", Integer.valueOf(yVelocity), false, true, true, true);
                }
                this.mTracker.recycle();
                this.mTracker = null;
            }
            this.mAjxContext.getDomTree().getLinkageAnimatorManager().syncLinkageAnimators();
        }
        if (this.mTouchEndView != null) {
            float pixelToStandardUnit = DimensionUtils.pixelToStandardUnit(motionEvent.getRawX() - this.mAjxViewX);
            float pixelToStandardUnit2 = DimensionUtils.pixelToStandardUnit(motionEvent.getRawY() - this.mAjxViewY);
            float pixelToStandardUnit3 = DimensionUtils.pixelToStandardUnit(motionEvent.getRawX());
            float pixelToStandardUnit4 = DimensionUtils.pixelToStandardUnit(motionEvent.getRawY());
            this.mAjxContext.getDomTree().getLinkageAnimatorManager().clearDisposableObserver();
            invokeTouch("touchend", (double) pixelToStandardUnit, (double) pixelToStandardUnit2, (double) pixelToStandardUnit3, (double) pixelToStandardUnit4, this.mTouchEndView);
        }
        if (!(this.mIsBeingDragged || (motionEvent.getAction() & 255) == 3 || this.mClickableView == null || this.mIsHandledLongPress || this.mTarget == null)) {
            if ((this.mClick != null && !this.mClick.couldInvokeClick()) || this.mIgnoreClick) {
                z = false;
            }
            long nodeId = this.mAjxContext.getDomTree().getNodeId(this.mTarget.mView);
            if (!TextUtils.isEmpty(this.mGroupId)) {
                this.mHoverId = -1;
            } else if (this.mHoverId <= 0) {
                this.mHoverId = nodeId;
            }
            if (z) {
                Builder builder = new Builder();
                builder.setEventName("click").setNodeId(nodeId).setHoverNodeId(this.mHoverId).setTouch(this.mTouchDownParcel);
                if (this.mTarget.mView instanceof Html) {
                    AjxImageSpan targetImageSpan = ((Html) this.mTarget.mView).getTargetImageSpan(motionEvent);
                    if (targetImageSpan != null) {
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("imgid", targetImageSpan.id);
                            jSONObject.put("src", targetImageSpan.src);
                            builder.setContent(jSONObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!this.mIsTalkBackServiceEnable) {
                    this.mAjxContext.invokeJsEvent(builder.build());
                    LogRecorder.log(TAG, " click: ".concat(String.valueOf(nodeId)));
                }
            }
            Ajx.sStartTime = System.currentTimeMillis();
            if (this.mRoot != null && !TextUtils.isEmpty(this.mGroupId)) {
                this.mRoot.onNodeUnitId("", this.mGroupId);
            }
        }
        this.mHoverHelper.handleUp();
    }

    /* access modifiers changed from: 0000 */
    public void setOffsetY(float f) {
        this.mOffsetY = f;
    }

    /* access modifiers changed from: 0000 */
    public void setTouchEnable(boolean z) {
        this.mTouchEnable = z;
    }

    private void invokeTouch(String str, double d, double d2, double d3, double d4, View view) {
        long nodeId = this.mAjxContext.getDomTree().getNodeId(this.mTarget != null ? this.mTarget.mView : view);
        if (nodeId == -1 && this.mDragableView != null && "touchend".equals(str)) {
            nodeId = this.mAjxContext.getDomTree().getNodeId(this.mDragableView);
        }
        long nodeId2 = this.mAjxContext.getDomTree().getNodeId(view);
        Parcel parcel = new Parcel();
        parcel.writeBoolean(true);
        parcel.writeDouble(d);
        parcel.writeDouble(d2);
        parcel.writeDouble(d3);
        parcel.writeDouble(d4);
        parcel.writeString(getEventGestureName(str));
        this.mAjxContext.invokeJsEvent(new Builder().setEventName(str).setNodeId(nodeId).setAltNodeId(nodeId2).setTouch(parcel).build());
    }

    private String getEventGestureName(String str) {
        if (!"touchend".equals(str)) {
            return "";
        }
        String str2 = "click";
        if (this.mIsHandledLongPress) {
            str2 = "longpress";
        } else if (this.mIsBeingDragged) {
            str2 = "touchmove";
        }
        return str2;
    }

    private void findTarget(MotionEvent motionEvent, ViewGroup viewGroup) {
        initTmpEventIfNeeded(motionEvent);
        View clickView = getClickView(motionEvent.getX(), motionEvent.getY(), viewGroup);
        if (clickView instanceof ViewExtension) {
            ViewExtension viewExtension = (ViewExtension) clickView;
            if (viewExtension.getProperty() != null) {
                this.mTarget = viewExtension.getProperty().mGestureAttribute;
                if (this.mTarget == null || (clickView instanceof AjxAbsoluteLayout)) {
                    this.mTarget = getParentAttribute(clickView);
                }
                if (this.mTarget != null && this.mAjxContext.getDomTree().getNodeId(this.mTarget.mView) == -1) {
                    this.mTarget = getParentAttribute(this.mTarget.mView);
                }
                if (this.mTarget != null && this.mTarget.mEventspenetrate) {
                    this.mTarget = getNotEventSpenView(this.mTarget);
                    return;
                }
            }
        }
        if (clickView != null) {
            this.mTarget = getParentAttribute(clickView);
        }
        if (this.mTarget != null) {
        }
    }

    private void initTmpEventIfNeeded(MotionEvent motionEvent) {
        if (tmpMotionEvent == null) {
            tmpMotionEvent = MotionEvent.obtain(motionEvent);
        }
    }

    private void findHasEventView(GestureAttribute gestureAttribute) {
        this.mClick = null;
        this.mClickableView = null;
        this.mLongClickableView = null;
        this.mTouchStartView = null;
        this.mTouchEndView = null;
        this.mDragableView = null;
        if (gestureAttribute != null) {
            if (gestureAttribute.mHasClickListener) {
                this.mClickableView = gestureAttribute.mView;
                this.mClick = gestureAttribute;
            }
            if (gestureAttribute.mHasLongClickListener) {
                this.mLongClickableView = gestureAttribute.mView;
            }
            if (gestureAttribute.mNeedSyncTouchStart) {
                this.mTouchStartView = gestureAttribute.mView;
            }
            if (gestureAttribute.mNeedSyncTouchEnd) {
                this.mTouchEndView = gestureAttribute.mView;
            }
            if (gestureAttribute.mDragable) {
                this.mDragableView = gestureAttribute.mView;
            }
            if (this.mClickableView == null || this.mLongClickableView == null || this.mTouchStartView == null || this.mTouchEndView == null || this.mDragableView == null) {
                findEventViewFromParent(gestureAttribute);
            }
            View findHoverboundary = findHoverboundary(gestureAttribute);
            findGroupId(gestureAttribute.mView);
            if (TextUtils.isEmpty(this.mGroupId)) {
                this.mHoverId = findHoverNodeId(gestureAttribute.mView, -1);
            }
            this.mHoverHelper.prepare(findHoverboundary);
        }
    }

    private void findEventViewFromParent(GestureAttribute gestureAttribute) {
        if (!(gestureAttribute == null || gestureAttribute.mView == null)) {
            View parent = getParent(gestureAttribute.mView);
            while (parent != null && (this.mClickableView == null || this.mLongClickableView == null || this.mTouchStartView == null || this.mTouchEndView == null || this.mDragableView == null)) {
                if (parent instanceof ViewExtension) {
                    ViewExtension viewExtension = (ViewExtension) parent;
                    if (viewExtension.getProperty() != null) {
                        GestureAttribute gestureAttribute2 = viewExtension.getProperty().mGestureAttribute;
                        if (gestureAttribute2.mHasClickListener && !gestureAttribute2.mEventspenetrate && this.mClickableView == null) {
                            this.mClickableView = gestureAttribute2.mView;
                            this.mClick = gestureAttribute2;
                        }
                        if (gestureAttribute2.mHasLongClickListener && !gestureAttribute2.mEventspenetrate && this.mLongClickableView == null) {
                            this.mLongClickableView = gestureAttribute2.mView;
                        }
                        if (gestureAttribute2.mNeedSyncTouchStart && this.mTouchStartView == null) {
                            this.mTouchStartView = gestureAttribute2.mView;
                        }
                        if (gestureAttribute2.mNeedSyncTouchEnd && this.mTouchEndView == null) {
                            this.mTouchEndView = gestureAttribute2.mView;
                        }
                        if (gestureAttribute2.mDragable && this.mDragableView == null) {
                            this.mDragableView = gestureAttribute2.mView;
                        }
                    }
                }
                if (!(parent instanceof AjxView)) {
                    parent = getParent(parent);
                } else {
                    return;
                }
            }
        }
    }

    private View getTouchedViewFromEventSpenetrateView(float f, float f2, View view) {
        if (!(view instanceof ViewGroup)) {
            return null;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        if (childCount <= 0) {
            return null;
        }
        int scrollX = viewGroup.getScrollX();
        int scrollY = viewGroup.getScrollY();
        for (int i = childCount - 1; i >= 0; i--) {
            View childAt = viewGroup.getChildAt(i);
            float[] tempPoint = getTempPoint();
            tempPoint[0] = f;
            tempPoint[1] = f2;
            if (pointInView(tempPoint, childAt, scrollX, scrollY)) {
                float f3 = tempPoint[0];
                float f4 = tempPoint[1];
                if (!(childAt instanceof ViewExtension) || !((ViewExtension) childAt).getProperty().mGestureAttribute.mEventspenetrate) {
                    return childAt;
                }
                if (childAt instanceof ViewGroup) {
                    View touchedViewFromEventSpenetrateView = getTouchedViewFromEventSpenetrateView(f3, f4, childAt);
                    if (touchedViewFromEventSpenetrateView != null) {
                        return touchedViewFromEventSpenetrateView;
                    }
                } else {
                    continue;
                }
            }
        }
        return null;
    }

    private View getClickView(float f, float f2, ViewGroup viewGroup) {
        View view;
        while (true) {
            int childCount = viewGroup.getChildCount();
            int scrollX = viewGroup.getScrollX();
            int scrollY = viewGroup.getScrollY();
            for (int i = childCount - 1; i >= 0; i--) {
                View childAt = viewGroup.getChildAt(i);
                if (!(childAt instanceof ShadowView)) {
                    float[] tempPoint = getTempPoint();
                    boolean z = false;
                    tempPoint[0] = f;
                    tempPoint[1] = f2;
                    if (pointInView(tempPoint, childAt, scrollX, scrollY) && !(childAt instanceof AjxView)) {
                        float f3 = tempPoint[0];
                        float f4 = tempPoint[1];
                        if (childAt instanceof ViewExtension) {
                            z = ((ViewExtension) childAt).getProperty().mGestureAttribute.mEventspenetrate;
                        }
                        if (!z || getTouchedViewFromEventSpenetrateView(f3, f4, childAt) != null) {
                            if (!(childAt instanceof ViewGroup)) {
                                return childAt;
                            }
                            if (childAt instanceof AjxList) {
                                AjxList ajxList = (AjxList) childAt;
                                int i2 = (int) f3;
                                int i3 = (int) f4;
                                this.mListHeader = ajxList.getTouchedHeader(i2, i3);
                                if (this.mListHeader != null) {
                                    this.mList = ajxList;
                                    this.mList.setTouchInHeader(this.mListHeader);
                                    return ajxList.findTouchHeaderTarget(i2, i3, this.mListHeader);
                                } else if (ajxList.findTouchChild(f3, f4) == null) {
                                    return childAt;
                                } else {
                                    view = childAt;
                                }
                            } else if (childAt instanceof Scroller) {
                                Scroller scroller = (Scroller) childAt;
                                View findTouchChild = scroller.findTouchChild(f3, f4);
                                this.mVScroller = scroller;
                                if (findTouchChild == null) {
                                    return childAt;
                                }
                                view = childAt;
                            } else if (childAt instanceof HorizontalScroller) {
                                HorizontalScroller horizontalScroller = (HorizontalScroller) childAt;
                                View findTouchChild2 = horizontalScroller.findTouchChild(f3, f4);
                                this.mHScroller = horizontalScroller;
                                if (findTouchChild2 == null) {
                                    return childAt;
                                }
                                view = childAt;
                            } else {
                                if (childAt instanceof AjxViewPager) {
                                    AjxViewPager ajxViewPager = (AjxViewPager) childAt;
                                    this.mViewPager = ajxViewPager;
                                    View findTouchChild3 = ajxViewPager.findTouchChild(f3, f4);
                                    if (findTouchChild3 != null) {
                                        if (!(findTouchChild3 instanceof ViewGroup)) {
                                            return findTouchChild3;
                                        }
                                        view = findTouchChild3;
                                    }
                                } else if (childAt instanceof PullToRefreshList) {
                                    PullToRefreshList pullToRefreshList = (PullToRefreshList) childAt;
                                    this.mPullToRefresh = pullToRefreshList;
                                    if (pullToRefreshList.findTouchChild(f3, f4) == null) {
                                        return childAt;
                                    }
                                    view = childAt;
                                }
                                view = childAt;
                            }
                            viewGroup = (ViewGroup) view;
                            f2 = f4;
                            f = f3;
                        }
                    }
                }
            }
            return viewGroup;
        }
    }

    public static synchronized float[] getTempPoint() {
        float[] fArr;
        synchronized (TouchHelper.class) {
            try {
                if (LOCATION == null) {
                    LOCATION = new float[2];
                }
                fArr = LOCATION;
            }
        }
        return fArr;
    }

    public static boolean pointInView(float[] fArr, View view, int i, int i2) {
        if (fArr == null || fArr.length != 2) {
            return false;
        }
        fArr[0] = fArr[0] + ((float) (i - view.getLeft()));
        fArr[1] = fArr[1] + ((float) (i2 - view.getTop()));
        if (!hasIdentityMatrix(view)) {
            transform(getInverseMatrix(view), fArr);
        }
        if (fArr[0] < 0.0f || fArr[1] < 0.0f || fArr[0] >= ((float) view.getWidth()) || fArr[1] >= ((float) view.getHeight()) || view.getVisibility() != 0) {
            return false;
        }
        return true;
    }

    private View getParent(View view) {
        if (view == null) {
            return null;
        }
        if (view == this.mListHeader) {
            return this.mList;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof View) {
            return (View) parent;
        }
        return null;
    }

    private GestureAttribute getParentAttribute(View view) {
        for (ViewParent parent = view.getParent(); parent != null; parent = parent.getParent()) {
            if ((parent instanceof ViewExtension) && !(parent instanceof AjxAbsoluteLayout)) {
                if (parent instanceof AjxView) {
                    return null;
                }
                BaseProperty property = ((ViewExtension) parent).getProperty();
                if (!(property == null || this.mAjxContext.getDomTree().getNodeId((View) parent) == -1)) {
                    return property.mGestureAttribute;
                }
            }
        }
        return null;
    }

    private void findGroupId(View view) {
        while (true) {
            this.mGroupId = "";
            if (!(view instanceof AjxView)) {
                if (view instanceof ViewExtension) {
                    BaseProperty property = ((ViewExtension) view).getProperty();
                    if (!(property == null || property.mGestureAttribute == null || TextUtils.isEmpty(property.mGestureAttribute.mGroupId))) {
                        this.mGroupId = property.mGestureAttribute.mGroupId;
                        return;
                    }
                }
                ViewParent parent = view.getParent();
                if (parent instanceof View) {
                    view = (View) parent;
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private long findHoverNodeId(View view, long j) {
        while (!(view instanceof AjxViewPager) && !(view instanceof AjxView)) {
            if (view instanceof ViewExtension) {
                BaseProperty property = ((ViewExtension) view).getProperty();
                if (property != null) {
                    AjxDomNode node = property.getNode();
                    if (node != null) {
                        j = node.getId();
                        if (node.hasHoverStyle()) {
                            return j;
                        }
                    }
                }
            }
            ViewParent parent = view.getParent();
            if (parent instanceof AjxAbsoluteLayout) {
                return j;
            }
            if (!(parent instanceof View)) {
                return -1;
            }
            view = (View) parent;
        }
        return -1;
    }

    private View findHoverboundary(GestureAttribute gestureAttribute) {
        View hasHoverboundary = hasHoverboundary(gestureAttribute);
        GestureAttribute parentAttribute = getParentAttribute(gestureAttribute.mView);
        while (parentAttribute != null && hasHoverboundary == null) {
            hasHoverboundary = hasHoverboundary(parentAttribute);
            parentAttribute = getParentAttribute(parentAttribute.mView);
        }
        return hasHoverboundary;
    }

    private View hasHoverboundary(GestureAttribute gestureAttribute) {
        if (gestureAttribute.mHoverboundary) {
            return gestureAttribute.mView;
        }
        return null;
    }

    private GestureAttribute getNotEventSpenView(GestureAttribute gestureAttribute) {
        while (gestureAttribute != null) {
            if (!gestureAttribute.mEventspenetrate) {
                if (!(gestureAttribute.mView instanceof AjxView)) {
                    if (this.mAjxContext.getDomTree().getNodeId(gestureAttribute.mView) == -1) {
                        ViewParent parent = gestureAttribute.mView.getParent();
                        if (parent == null || !(parent instanceof ViewExtension)) {
                            break;
                        }
                        ViewExtension viewExtension = (ViewExtension) parent;
                        if (viewExtension.getProperty() == null) {
                            break;
                        }
                        gestureAttribute = viewExtension.getProperty().mGestureAttribute;
                    } else {
                        return gestureAttribute;
                    }
                } else {
                    return null;
                }
            } else {
                ViewParent parent2 = gestureAttribute.mView.getParent();
                if (parent2 == null || !(parent2 instanceof ViewExtension)) {
                    break;
                }
                ViewExtension viewExtension2 = (ViewExtension) parent2;
                if (viewExtension2.getProperty() == null) {
                    break;
                }
                gestureAttribute = viewExtension2.getProperty().mGestureAttribute;
            }
        }
        return null;
    }

    /* access modifiers changed from: 0000 */
    public void setMotionEventChangeListener(MotionEventChangeListener motionEventChangeListener) {
        this.mMotionEventChangeListener = motionEventChangeListener;
    }

    public boolean forbidEvents() {
        return this.mForbidOwners.size() > 0;
    }

    public void askForbidEvents(long j) {
        if (!this.mForbidOwners.contains(Long.valueOf(j))) {
            this.mForbidOwners.add(Long.valueOf(j));
        }
    }

    public void removeForbidEvents(long j) {
        if (this.mForbidOwners.contains(Long.valueOf(j))) {
            this.mForbidOwners.remove(Long.valueOf(j));
        }
    }

    private void dumpOwners() {
        StringBuilder sb = new StringBuilder();
        sb.append("size: ");
        sb.append(this.mForbidOwners.size());
        sb.append(" ; ");
        Iterator<Long> it = this.mForbidOwners.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
            sb.append(" , ");
        }
        new StringBuilder("  ... dump: ").append(sb.toString());
    }

    /* JADX WARNING: Can't wrap try/catch for region: R(10:2|3|4|(3:6|7|8)|9|10|(4:12|13|14|15)|16|18|19) */
    /* JADX WARNING: Missing exception handler attribute for start block: B:9:0x0018 */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x001c A[SYNTHETIC, Splitter:B:12:0x001c] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static synchronized android.graphics.Matrix getInverseMatrix(android.view.View r5) {
        /*
            java.lang.Class<com.autonavi.minimap.ajx3.widget.property.TouchHelper> r0 = com.autonavi.minimap.ajx3.widget.property.TouchHelper.class
            monitor-enter(r0)
            java.lang.reflect.Method r1 = GET_INVERSE_MATRIX     // Catch:{ all -> 0x002b }
            r2 = 0
            if (r1 != 0) goto L_0x0018
            java.lang.Class<android.view.View> r1 = android.view.View.class
            java.lang.String r3 = "getInverseMatrix"
            java.lang.Class[] r4 = new java.lang.Class[r2]     // Catch:{ Exception -> 0x0018 }
            java.lang.reflect.Method r1 = r1.getDeclaredMethod(r3, r4)     // Catch:{ Exception -> 0x0018 }
            GET_INVERSE_MATRIX = r1     // Catch:{ Exception -> 0x0018 }
            r3 = 1
            r1.setAccessible(r3)     // Catch:{ Exception -> 0x0018 }
        L_0x0018:
            java.lang.reflect.Method r1 = GET_INVERSE_MATRIX     // Catch:{ all -> 0x002b }
            if (r1 == 0) goto L_0x0028
            java.lang.reflect.Method r1 = GET_INVERSE_MATRIX     // Catch:{ Exception -> 0x0028 }
            java.lang.Object[] r2 = new java.lang.Object[r2]     // Catch:{ Exception -> 0x0028 }
            java.lang.Object r5 = r1.invoke(r5, r2)     // Catch:{ Exception -> 0x0028 }
            android.graphics.Matrix r5 = (android.graphics.Matrix) r5     // Catch:{ Exception -> 0x0028 }
            monitor-exit(r0)
            return r5
        L_0x0028:
            r5 = 0
            monitor-exit(r0)
            return r5
        L_0x002b:
            r5 = move-exception
            monitor-exit(r0)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.TouchHelper.getInverseMatrix(android.view.View):android.graphics.Matrix");
    }

    public static boolean hasIdentityMatrix(View view) {
        if (HAS_INVERSE_MATRIX == null) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod(METHOD_NAME_HAS_INVERSE_MATRIX, new Class[0]);
                HAS_INVERSE_MATRIX = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (Exception unused) {
            }
        }
        if (HAS_INVERSE_MATRIX != null) {
            try {
                return ((Boolean) HAS_INVERSE_MATRIX.invoke(view, new Object[0])).booleanValue();
            } catch (Exception unused2) {
            }
        }
        return false;
    }

    public static float[] transform(Matrix matrix, float[] fArr) {
        if (matrix == null || tmpMotionEvent == null) {
            return fArr;
        }
        tmpMotionEvent.setLocation(fArr[0], fArr[1]);
        tmpMotionEvent.transform(matrix);
        fArr[0] = tmpMotionEvent.getX();
        fArr[1] = tmpMotionEvent.getY();
        return fArr;
    }

    public static void transformIfNeeded(View view, MotionEvent motionEvent) {
        try {
            if (!hasIdentityMatrix(view)) {
                Matrix inverseMatrix = getInverseMatrix(view);
                if (inverseMatrix != null) {
                    motionEvent.transform(inverseMatrix);
                }
            }
        } catch (Exception unused) {
        }
    }
}
