package com.autonavi.minimap.ajx3.widget.property;

import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.util.StringUtils;
import com.autonavi.minimap.ajx3.widget.AjxView;
import com.autonavi.minimap.ajx3.widget.view.HorizontalScroller;
import com.autonavi.minimap.ajx3.widget.view.Scroller;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import com.autonavi.minimap.ajx3.widget.view.list.AjxList;
import com.autonavi.minimap.ajx3.widget.view.list.PullToRefreshList;
import com.autonavi.minimap.ajx3.widget.view.viewpager.AjxViewPager;

class GestureAttribute {
    static final String CLICK = "click";
    private static final String CLICKABLE = "clickable";
    private static final String CLICK_TIME_INTER = "clicktimeinterval";
    private static final String CLICK_WITH_PRE_FIX = "onclick";
    private static final boolean DEBUG = false;
    private static final String EVENT_SPENETRATE = "eventspenetrate";
    private static final String GROUP_ID = "groupid";
    static final String LONG_PRESS = "longpress";
    private static final String LONG_PRESS_WITH_PRE_FIX = "onlongpress";
    private static final String MOVE_DIRECTION = "movedirection";
    static final String SCROLL_X_SPEED = "scrollXSpeed";
    static final String SCROLL_Y_SPEED = "scrollYSpeed";
    private static final String STOP_MOVING = "stopmoving";
    private static final String TAG = "Helper";
    static final String TOUCH_END = "touchend";
    private static final String TOUCH_END_WITH_PRE_FIX = "ontouchend";
    static final String TOUCH_MOVE = "touchmove";
    static final String TOUCH_START = "touchstart";
    private static final String TOUCH_START_WITH_PRE_FIX = "ontouchstart";
    /* access modifiers changed from: private */
    public IAjxContext mAjxContext;
    private CAPTURE_STATE mCapturehover = CAPTURE_STATE.NONE;
    private int mClickTimeInterval = -1;
    boolean mDragable = false;
    boolean mEventspenetrate = false;
    String mGroupId = "";
    boolean mHasClickListener = false;
    boolean mHasLongClickListener = false;
    boolean mHoverboundary = false;
    private boolean mIsClickable = false;
    private long mLastClickTime = 0;
    boolean mMoveDirection = false;
    boolean mNeedSyncTouchEnd;
    boolean mNeedSyncTouchStart;
    private OnClickListener mOnClickListener;
    private OnLongClickListener mOnLongClickListener;
    private boolean mStopMoving = false;
    final View mView;

    static class AnimationAttribute {
        float clientX;
        float clientY;
        float offsetScrollX;
        float offsetScrollY;
        float screenX;
        float screenY;
        float translateScrollX;
        float translateScrollY;

        AnimationAttribute() {
        }
    }

    enum CAPTURE_STATE {
        NONE,
        CONTENT,
        SELF_CONTENT,
        SELF
    }

    GestureAttribute(@NonNull IAjxContext iAjxContext, @NonNull View view) {
        this.mAjxContext = iAjxContext;
        this.mView = view;
    }

    /* access modifiers changed from: 0000 */
    public void setCapturehover(Object obj) {
        this.mCapturehover = CAPTURE_STATE.NONE;
        if (obj instanceof Integer) {
            int intValue = ((Integer) obj).intValue();
            if (intValue == 1056964730) {
                this.mCapturehover = CAPTURE_STATE.CONTENT;
            } else if (intValue == 1056964731) {
                this.mCapturehover = CAPTURE_STATE.SELF_CONTENT;
            } else if (intValue == 1056964729) {
                this.mCapturehover = CAPTURE_STATE.SELF;
            }
        }
    }

    /* access modifiers changed from: 0000 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean updateAttribute(java.lang.String r4, java.lang.Object r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -1964681502: goto L_0x00a6;
                case -1890998200: goto L_0x009c;
                case -1578593149: goto L_0x0090;
                case -1395358002: goto L_0x0086;
                case -1322349815: goto L_0x007c;
                case -819532484: goto L_0x0070;
                case -609212720: goto L_0x0065;
                case -231467102: goto L_0x005a;
                case 94750088: goto L_0x0050;
                case 143756103: goto L_0x0046;
                case 293429210: goto L_0x003a;
                case 364536720: goto L_0x002e;
                case 1885776923: goto L_0x0022;
                case 1918030874: goto L_0x0016;
                case 1947692911: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x00b1
        L_0x000b:
            java.lang.String r0 = "eventspenetrate"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 2
            goto L_0x00b2
        L_0x0016:
            java.lang.String r0 = "clicktimeinterval"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 14
            goto L_0x00b2
        L_0x0022:
            java.lang.String r0 = "ontouchend"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 11
            goto L_0x00b2
        L_0x002e:
            java.lang.String r0 = "touchmove"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 0
            goto L_0x00b2
        L_0x003a:
            java.lang.String r0 = "groupid"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 13
            goto L_0x00b2
        L_0x0046:
            java.lang.String r0 = "longpress"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 6
            goto L_0x00b2
        L_0x0050:
            java.lang.String r0 = "click"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 4
            goto L_0x00b2
        L_0x005a:
            java.lang.String r0 = "ontouchstart"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 9
            goto L_0x00b2
        L_0x0065:
            java.lang.String r0 = "stopmoving"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 3
            goto L_0x00b2
        L_0x0070:
            java.lang.String r0 = "touchend"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 10
            goto L_0x00b2
        L_0x007c:
            java.lang.String r0 = "onclick"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 5
            goto L_0x00b2
        L_0x0086:
            java.lang.String r0 = "movedirection"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 1
            goto L_0x00b2
        L_0x0090:
            java.lang.String r0 = "touchstart"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 8
            goto L_0x00b2
        L_0x009c:
            java.lang.String r0 = "onlongpress"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 7
            goto L_0x00b2
        L_0x00a6:
            java.lang.String r0 = "clickable"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x00b1
            r4 = 12
            goto L_0x00b2
        L_0x00b1:
            r4 = -1
        L_0x00b2:
            switch(r4) {
                case 0: goto L_0x0134;
                case 1: goto L_0x0125;
                case 2: goto L_0x010c;
                case 3: goto L_0x00fc;
                case 4: goto L_0x00f3;
                case 5: goto L_0x00f3;
                case 6: goto L_0x00ea;
                case 7: goto L_0x00ea;
                case 8: goto L_0x00e4;
                case 9: goto L_0x00e4;
                case 10: goto L_0x00de;
                case 11: goto L_0x00de;
                case 12: goto L_0x00c9;
                case 13: goto L_0x00c0;
                case 14: goto L_0x00b6;
                default: goto L_0x00b5;
            }
        L_0x00b5:
            return r1
        L_0x00b6:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x00bf
            java.lang.String r5 = (java.lang.String) r5
            r3.updateClickTimeInterval(r5)
        L_0x00bf:
            return r2
        L_0x00c0:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x00c8
            java.lang.String r5 = (java.lang.String) r5
            r3.mGroupId = r5
        L_0x00c8:
            return r2
        L_0x00c9:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x00d8
            java.lang.String r4 = r5.toString()
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r4)
            r3.mIsClickable = r4
            goto L_0x00da
        L_0x00d8:
            r3.mIsClickable = r1
        L_0x00da:
            r3.updateClickable()
            return r2
        L_0x00de:
            if (r5 == 0) goto L_0x00e1
            r1 = 1
        L_0x00e1:
            r3.mNeedSyncTouchEnd = r1
            return r2
        L_0x00e4:
            if (r5 == 0) goto L_0x00e7
            r1 = 1
        L_0x00e7:
            r3.mNeedSyncTouchStart = r1
            return r2
        L_0x00ea:
            if (r5 == 0) goto L_0x00ed
            r1 = 1
        L_0x00ed:
            r3.mHasLongClickListener = r1
            r3.updateLongClick()
            return r2
        L_0x00f3:
            if (r5 == 0) goto L_0x00f6
            r1 = 1
        L_0x00f6:
            r3.mHasClickListener = r1
            r3.updateClick()
            return r2
        L_0x00fc:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0109
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            if (r4 == 0) goto L_0x0109
            r1 = 1
        L_0x0109:
            r3.mStopMoving = r1
            return r2
        L_0x010c:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0119
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            if (r4 == 0) goto L_0x0119
            r1 = 1
        L_0x0119:
            r3.mEventspenetrate = r1
            r3.updateClickable()
            r3.updateClick()
            r3.updateLongClick()
            return r2
        L_0x0125:
            if (r5 == 0) goto L_0x0133
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0133
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            r3.mMoveDirection = r4
        L_0x0133:
            return r2
        L_0x0134:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x0141
            java.lang.String r5 = (java.lang.String) r5
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            if (r4 == 0) goto L_0x0141
            r1 = 1
        L_0x0141:
            r3.mDragable = r1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.GestureAttribute.updateAttribute(java.lang.String, java.lang.Object):boolean");
    }

    private void updateClickTimeInterval(String str) {
        this.mClickTimeInterval = StringUtils.parseInt(str);
    }

    /* access modifiers changed from: 0000 */
    public boolean couldInvokeClick() {
        if (this.mClickTimeInterval <= 0) {
            return true;
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (elapsedRealtime - this.mLastClickTime < ((long) this.mClickTimeInterval)) {
            return false;
        }
        this.mLastClickTime = elapsedRealtime;
        return true;
    }

    /* access modifiers changed from: 0000 */
    public boolean hasGroupId() {
        return !TextUtils.isEmpty(this.mGroupId);
    }

    private void updateClickable() {
        if (this.mEventspenetrate) {
            this.mView.setClickable(false);
        } else {
            this.mView.setClickable(this.mIsClickable);
        }
    }

    private void updateClick() {
        if (this.mEventspenetrate || !this.mHasClickListener) {
            this.mView.setOnClickListener(null);
            this.mView.setClickable(false);
            return;
        }
        if (this.mOnClickListener == null) {
            this.mOnClickListener = new OnClickListener() {
                public void onClick(View view) {
                    if (!GestureAttribute.this.mAjxContext.hasDestroy()) {
                        boolean z = false;
                        if (!(GestureAttribute.this.mAjxContext.getDomTree() == null || GestureAttribute.this.mAjxContext.getDomTree().isDestroy() || GestureAttribute.this.mAjxContext.getDomTree().getRootView() == null)) {
                            z = GestureAttribute.this.mAjxContext.getDomTree().getRootView().isTalkBackServiceEnable;
                        }
                        if (z && (GestureAttribute.this.mView instanceof ViewExtension)) {
                            BaseProperty access$100 = GestureAttribute.this.getCurrentProperty();
                            if (access$100 != null) {
                                long nodeId = access$100.getNodeId();
                                Parcel parcel = new Parcel();
                                parcel.writeBoolean(true);
                                parcel.writeDouble(0.0d);
                                parcel.writeDouble(0.0d);
                                parcel.writeDouble(0.0d);
                                parcel.writeDouble(0.0d);
                                parcel.writeString("");
                                Builder builder = new Builder();
                                builder.setEventName("click").setNodeId(nodeId).setHoverNodeId(nodeId).setTouch(parcel);
                                GestureAttribute.this.mAjxContext.invokeJsEvent(builder.build());
                            }
                        }
                    }
                }
            };
        }
        this.mView.setOnClickListener(this.mOnClickListener);
    }

    private void updateLongClick() {
        if (this.mEventspenetrate || !this.mHasLongClickListener) {
            this.mView.setOnLongClickListener(null);
            this.mView.setLongClickable(false);
            return;
        }
        this.mView.setLongClickable(true);
        if (this.mOnLongClickListener == null) {
            this.mOnLongClickListener = new OnLongClickListener() {
                public boolean onLongClick(View view) {
                    return true;
                }
            };
        }
        this.mView.setOnLongClickListener(this.mOnLongClickListener);
    }

    /* access modifiers changed from: 0000 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean updateAttrOffline(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            int r0 = r4.hashCode()
            r1 = 0
            r2 = 1
            switch(r0) {
                case -1964681502: goto L_0x0082;
                case -1890998200: goto L_0x0078;
                case -1578593149: goto L_0x006d;
                case -1322349815: goto L_0x0063;
                case -819532484: goto L_0x0057;
                case -609212720: goto L_0x004c;
                case -231467102: goto L_0x0041;
                case 94750088: goto L_0x0037;
                case 143756103: goto L_0x002d;
                case 364536720: goto L_0x0022;
                case 1885776923: goto L_0x0016;
                case 1947692911: goto L_0x000b;
                default: goto L_0x0009;
            }
        L_0x0009:
            goto L_0x008d
        L_0x000b:
            java.lang.String r0 = "eventspenetrate"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 1
            goto L_0x008e
        L_0x0016:
            java.lang.String r0 = "ontouchend"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 10
            goto L_0x008e
        L_0x0022:
            java.lang.String r0 = "touchmove"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 0
            goto L_0x008e
        L_0x002d:
            java.lang.String r0 = "longpress"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 5
            goto L_0x008e
        L_0x0037:
            java.lang.String r0 = "click"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 3
            goto L_0x008e
        L_0x0041:
            java.lang.String r0 = "ontouchstart"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 8
            goto L_0x008e
        L_0x004c:
            java.lang.String r0 = "stopmoving"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 2
            goto L_0x008e
        L_0x0057:
            java.lang.String r0 = "touchend"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 9
            goto L_0x008e
        L_0x0063:
            java.lang.String r0 = "onclick"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 4
            goto L_0x008e
        L_0x006d:
            java.lang.String r0 = "touchstart"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 7
            goto L_0x008e
        L_0x0078:
            java.lang.String r0 = "onlongpress"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 6
            goto L_0x008e
        L_0x0082:
            java.lang.String r0 = "clickable"
            boolean r4 = r4.equals(r0)
            if (r4 == 0) goto L_0x008d
            r4 = 11
            goto L_0x008e
        L_0x008d:
            r4 = -1
        L_0x008e:
            switch(r4) {
                case 0: goto L_0x00d8;
                case 1: goto L_0x00c8;
                case 2: goto L_0x00c1;
                case 3: goto L_0x00b8;
                case 4: goto L_0x00b8;
                case 5: goto L_0x00af;
                case 6: goto L_0x00af;
                case 7: goto L_0x00a9;
                case 8: goto L_0x00a9;
                case 9: goto L_0x00a3;
                case 10: goto L_0x00a3;
                case 11: goto L_0x0092;
                default: goto L_0x0091;
            }
        L_0x0091:
            return r1
        L_0x0092:
            boolean r4 = r5 instanceof java.lang.String
            if (r4 == 0) goto L_0x009d
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            r3.mIsClickable = r4
            goto L_0x009f
        L_0x009d:
            r3.mIsClickable = r1
        L_0x009f:
            r3.updateClickable()
            return r2
        L_0x00a3:
            if (r5 == 0) goto L_0x00a6
            r1 = 1
        L_0x00a6:
            r3.mNeedSyncTouchEnd = r1
            return r2
        L_0x00a9:
            if (r5 == 0) goto L_0x00ac
            r1 = 1
        L_0x00ac:
            r3.mNeedSyncTouchStart = r1
            return r2
        L_0x00af:
            if (r5 == 0) goto L_0x00b2
            r1 = 1
        L_0x00b2:
            r3.mHasLongClickListener = r1
            r3.updateLongClick()
            return r2
        L_0x00b8:
            if (r5 == 0) goto L_0x00bb
            r1 = 1
        L_0x00bb:
            r3.mHasClickListener = r1
            r3.updateClick()
            return r2
        L_0x00c1:
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            r3.mStopMoving = r4
            return r2
        L_0x00c8:
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            r3.mEventspenetrate = r4
            r3.updateClickable()
            r3.updateClick()
            r3.updateLongClick()
            return r2
        L_0x00d8:
            boolean r4 = com.autonavi.minimap.ajx3.util.StringUtils.parseBoolean(r5)
            r3.mDragable = r4
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.ajx3.widget.property.GestureAttribute.updateAttrOffline(java.lang.String, java.lang.String):boolean");
    }

    /* access modifiers changed from: 0000 */
    public void handleMotionEvent(MotionEvent motionEvent, AnimationAttribute animationAttribute, boolean z, int i) {
        if (motionEvent != null) {
            if (animationAttribute != null) {
                updateAnimationAttribute(animationAttribute, i);
            }
            if (!this.mStopMoving) {
                BaseProperty parentAttribute = getParentAttribute();
                if (parentAttribute != null && z) {
                    parentAttribute.mGestureAttribute.handleMotionEvent(motionEvent, animationAttribute, true, i);
                }
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public AnimationAttribute getAninationAttribute(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        AnimationAttribute animationAttribute = new AnimationAttribute();
        animationAttribute.clientX = f;
        animationAttribute.clientY = f2;
        animationAttribute.screenX = f3;
        animationAttribute.screenY = f4;
        animationAttribute.translateScrollX = f5;
        animationAttribute.translateScrollY = f6;
        animationAttribute.offsetScrollX = f7;
        animationAttribute.offsetScrollY = f8;
        return animationAttribute;
    }

    private void updateAnimationAttribute(AnimationAttribute animationAttribute, int i) {
        if (animationAttribute != null) {
            if (this.mMoveDirection) {
                if (i == 3 || i == 4) {
                    animationAttribute.offsetScrollX = 0.0f;
                    animationAttribute.translateScrollX = 0.0f;
                    if (this.mView instanceof HorizontalScroller) {
                        ((HorizontalScroller) this.mView).setIgnoreTouch(true);
                    }
                    if (this.mView instanceof AjxViewPager) {
                        ((AjxViewPager) this.mView).setIgnoreTouch(true);
                    }
                } else if (i == 1 || i == 2) {
                    animationAttribute.translateScrollY = 0.0f;
                    animationAttribute.offsetScrollY = 0.0f;
                    if (this.mView instanceof PullToRefreshList) {
                        ((AjxList) ((PullToRefreshList) this.mView).getRefreshableView()).disableTouch(1);
                    }
                    if (this.mView instanceof Scroller) {
                        ((Scroller) this.mView).setIgnoreTouch(true);
                    }
                }
            }
            BaseProperty currentProperty = getCurrentProperty();
            if (currentProperty != null) {
                BaseProperty baseProperty = currentProperty;
                baseProperty.updateAttribute("clientX", Float.valueOf(animationAttribute.clientX), false, true, false, true);
                baseProperty.updateAttribute("clientY", Float.valueOf(animationAttribute.clientY), false, true, false, true);
                baseProperty.updateAttribute("screenX", Float.valueOf(animationAttribute.screenX), false, true, false, true);
                baseProperty.updateAttribute("screenY", Float.valueOf(animationAttribute.screenY), false, true, false, true);
                baseProperty.updateAttribute("translateScrollX", Float.valueOf(animationAttribute.translateScrollX), false, false, false, true);
                baseProperty.updateAttribute("translateScrollY", Float.valueOf(animationAttribute.translateScrollY), false, false, false, true);
                baseProperty.updateAttribute("offsetScrollX", Float.valueOf(animationAttribute.offsetScrollX), false, false, false, true);
                baseProperty.updateAttribute("offsetScrollY", Float.valueOf(animationAttribute.offsetScrollY), false, false, false, true);
            }
        }
    }

    /* access modifiers changed from: 0000 */
    public void handlePress(boolean z) {
        boolean z2;
        CAPTURE_STATE capture_state = this.mCapturehover;
        boolean z3 = true;
        if (capture_state == CAPTURE_STATE.NONE) {
            z2 = true;
        } else {
            if (capture_state == CAPTURE_STATE.CONTENT) {
                z2 = true;
            } else if (capture_state != CAPTURE_STATE.SELF_CONTENT && capture_state == CAPTURE_STATE.SELF) {
                z2 = false;
            } else {
                z2 = false;
            }
            z3 = false;
        }
        if (this.mEventspenetrate) {
            z2 = false;
        }
        if (z3 && (this.mView instanceof ViewGroup)) {
            int childCount = ((ViewGroup) this.mView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) this.mView).getChildAt(i);
                if (childAt instanceof ViewExtension) {
                    BaseProperty property = ((ViewExtension) childAt).getProperty();
                    if (property != null) {
                        property.mGestureAttribute.handlePress(z);
                    }
                } else {
                    childAt.setPressed(z);
                }
            }
        }
        if (z2) {
            BaseProperty currentProperty = getCurrentProperty();
            if (currentProperty != null) {
                currentProperty.changeStyle(z ? 1 : 0);
            }
        }
    }

    /* access modifiers changed from: private */
    public BaseProperty getCurrentProperty() {
        if (this.mView instanceof ViewExtension) {
            return ((ViewExtension) this.mView).getProperty();
        }
        return null;
    }

    private BaseProperty getParentAttribute() {
        if (this.mView instanceof AjxView) {
            return null;
        }
        for (ViewParent parent = this.mView.getParent(); parent != null; parent = parent.getParent()) {
            if (parent instanceof ViewExtension) {
                BaseProperty property = ((ViewExtension) parent).getProperty();
                if (property != null) {
                    return property instanceof BodyProperty ? property : property;
                }
            }
        }
        return null;
    }
}
