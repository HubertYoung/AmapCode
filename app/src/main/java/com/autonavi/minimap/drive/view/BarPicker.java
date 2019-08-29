package com.autonavi.minimap.drive.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.internal.view.SupportMenu;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ajx3.context.IAjxContext;
import com.autonavi.minimap.ajx3.core.EventInfo.Builder;
import com.autonavi.minimap.ajx3.dom.AjxDomNode;
import com.autonavi.minimap.ajx3.platform.ackor.Parcel;
import com.autonavi.minimap.ajx3.widget.property.BaseProperty;
import com.autonavi.minimap.ajx3.widget.view.ViewExtension;
import java.util.ArrayList;
import java.util.List;

public class BarPicker extends View implements ViewExtension {
    private static final int SELECTOR_ADJUSTMENT_DURATION_MILLIS = 800;
    private final int ANIMATION_FRAME_RATE;
    private final int ANIMATION_TIME_LENGTH;
    private final int BAR_ITEM_COUNT_PER_SCREEN;
    private List<c> datas;
    private int[] defaultLineColor;
    /* access modifiers changed from: private */
    public Handler handler;
    private boolean hasMeasured;
    private List<a> items;
    private Scroller mAdjustScroller;
    private IAjxContext mAjxContext;
    private Runnable mAniCallback;
    /* access modifiers changed from: private */
    public long mAniStartTimestamp;
    private RectF mBackgroundBound;
    private Paint mBackgroundPaint;
    private int mBarDiffHeight;
    private Paint mBarExcPaint;
    private int mBarMaxHeight;
    private int mBarMaxWidth;
    private int mBarMinHeight;
    private int mBarMinWidth;
    private Paint mBarPaint;
    private TextPaint mBarTopTextFocusPaint;
    private TextPaint mBarTopTextPaint;
    private int mBarWidth;
    private int mCallbackFocusItemIndex;
    private int mContentHeight;
    private int mContentWidth;
    private int mCurrentScrollOffset;
    private DecelerateInterpolator mDecelerateInterpolator;
    private float mDiffValueOfY;
    private int mDividerHeight;
    private Paint mDividerPaint;
    private int mDividerWidth;
    private Scroller mFlingScroller;
    private int mFocusItemIndex;
    private Paint mFocusTitlePaint;
    private Paint mForegroundPaint;
    private float[] mHorizonDividers;
    private int mItemBarSize;
    private int mItemWidth;
    private long mLastDownEventTime;
    private float mLastDownEventX;
    private float mLastDownOrMoveEventX;
    private RectF mLeftForegroundBound;
    private float mLeftOffset;
    private float mMaxValueOfY;
    private int mMaximumFlingVelocity;
    private int mMiddleWhiteHeight;
    private float mMinValueOfY;
    private int mMinimumFlingVelocity;
    private Paint mNormalTitlePaint;
    private b mOnScrollListener;
    private int mPreviousScrollerX;
    protected dkd mProperty;
    private RectF mRightForegroundBound;
    private float mRightOffset;
    private int mScrollState;
    private int mTitleHeight;
    private int mTouchSlop;
    private int mUnreachableLeftIndex;
    private int mUnreachableRightIndex;
    private Paint mUnreachableTitlePaint;
    private VelocityTracker mVelocityTracker;
    private int[] mVisibleEdge;
    private int maxOfVisible;
    private float offset;
    private boolean theViewParamsInited;
    private Vibrator vib;
    private VibrationEffect vibrationEffect;
    private RectF visiableArea;

    public static class a {
        public static final int a = (C0052a.a.length + 1);
        public static float o = 4.0f;
        int b;
        String c;
        PointF d;
        float e;
        float f;
        float g;
        int h;
        float i;
        RectF j;
        StaticLayout k;
        String l;
        int m;
        int n;
        private RectF[] p;
        private int q;
        private float r;
        private float s;
        private int t;
        private Paint u;

        /* renamed from: com.autonavi.minimap.drive.view.BarPicker$a$a reason: collision with other inner class name */
        public static class C0052a {
            public static final float[] a = {0.011993446f, 0.052356377f, 0.1288767f, 0.2486366f, 0.40998447f, 0.5901058f, 0.75144374f, 0.8711871f, 0.9476874f, 0.98802865f};
        }

        public a(int i2, String str, float f2, float f3, float f4, float f5, float f6, int i3, int i4, int i5, int i6, int i7, int i8) {
            this.b = i2;
            this.c = str;
            this.q = i4;
            this.f = f5;
            this.r = f6;
            this.g = f3;
            this.s = f4;
            this.h = i3;
            this.i = f2;
            this.t = i5;
            int round = Math.round((float) (i6 / 60));
            int round2 = Math.round((float) (round / 60));
            int i9 = round % 60;
            StringBuilder sb = new StringBuilder();
            if (round2 > 0) {
                sb.append(round2);
                sb.append("小时");
                if (i9 > 0) {
                    sb.append("\n");
                    sb.append(i9);
                    sb.append("分");
                }
            } else if (i9 > 0) {
                sb.append(i9);
                sb.append("分钟");
            }
            this.l = sb.toString();
            this.m = i7;
            this.n = i8;
            this.u = new Paint(1);
            this.u.setStyle(Style.STROKE);
            this.u.setColor(SupportMenu.CATEGORY_MASK);
            this.u.setStrokeWidth(3.0f);
            this.u.setPathEffect(new DashPathEffect(new float[]{3.0f, 3.0f}, 0.0f));
            this.d = new PointF();
            this.d.x = ((f5 - f3) / 2.0f) + f3;
            this.d.y = f6 - (((float) (i4 * 7)) / 10.0f);
            if (i2 == 1 || i2 == 2 || i2 == 3 || Float.isNaN(f2)) {
                this.p = new RectF[a];
                this.j = new RectF();
                this.e = (float) (i3 >> 1);
                float f7 = f5 + f3;
                float f8 = (float) i8;
                this.j.left = (f7 - f8) / 2.0f;
                this.j.right = (f7 + f8) / 2.0f;
                this.j.top = f2;
                this.j.bottom = (f6 - ((float) i4)) + ((float) i3);
            }
        }
    }

    public interface b {
    }

    public static class c implements Comparable<c> {
        int a;
        /* access modifiers changed from: 0000 */
        public float b;
        String c;

        public final /* bridge */ /* synthetic */ int compareTo(Object obj) {
            c cVar = (c) obj;
            if (this.b == cVar.b) {
                return 0;
            }
            return this.b > cVar.b ? 1 : -1;
        }

        public c(int i, float f, String str) {
            this.a = i;
            this.b = f;
            this.c = str;
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof c)) {
                return false;
            }
            c cVar = (c) obj;
            if ((this.b != cVar.b || this.c != cVar.c) && (this.c == null || !this.c.equals(cVar.c))) {
                return false;
            }
            return true;
        }
    }

    public BarPicker(@NonNull IAjxContext iAjxContext) {
        super(iAjxContext.getNativeContext());
        this.BAR_ITEM_COUNT_PER_SCREEN = 7;
        this.handler = new Handler(Looper.getMainLooper());
        this.ANIMATION_FRAME_RATE = 25;
        this.ANIMATION_TIME_LENGTH = 600;
        this.defaultLineColor = new int[]{-14004737, -11681793};
        this.maxOfVisible = 0;
        this.mScrollState = 0;
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.mAniCallback = new Runnable() {
            public final void run() {
                long elapsedRealtime = SystemClock.elapsedRealtime() - BarPicker.this.mAniStartTimestamp;
                if (elapsedRealtime < 0 || elapsedRealtime >= 600) {
                    BarPicker.this.mAniStartTimestamp = 0;
                } else {
                    BarPicker.this.handler.postDelayed(this, 25);
                }
                BarPicker.this.invalidate();
            }
        };
        this.mAjxContext = iAjxContext;
        this.mProperty = new dkd(this, iAjxContext);
        initConst(iAjxContext.getNativeContext());
    }

    public BarPicker(Context context) {
        this(context, null);
    }

    public BarPicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public BarPicker(Context context, AttributeSet attributeSet, int i) {
        // Context context2 = context;
        super(context, attributeSet, i);
        this.BAR_ITEM_COUNT_PER_SCREEN = 7;
        this.handler = new Handler(Looper.getMainLooper());
        this.ANIMATION_FRAME_RATE = 25;
        this.ANIMATION_TIME_LENGTH = 600;
        this.defaultLineColor = new int[]{-14004737, -11681793};
        this.maxOfVisible = 0;
        this.mScrollState = 0;
        this.mDecelerateInterpolator = new DecelerateInterpolator();
        this.mAniCallback = new Runnable() {
            public final void run() {
                long elapsedRealtime = SystemClock.elapsedRealtime() - BarPicker.this.mAniStartTimestamp;
                if (elapsedRealtime < 0 || elapsedRealtime >= 600) {
                    BarPicker.this.mAniStartTimestamp = 0;
                } else {
                    BarPicker.this.handler.postDelayed(this, 25);
                }
                BarPicker.this.invalidate();
            }
        };
        initConst(context);
        initView(agn.a(context2, 32.0f), agn.a(context2, 44.0f), agn.a(context2, 72.0f), agn.a(context2, 28.0f), agn.a(context2, 4.0f), agn.a(context2, 1.0f), agn.a(context2, 110.0f), agn.a(context2, 13.0f), agn.a(context2, 10.0f), SupportMenu.CATEGORY_MASK, -16777216, agn.a(context2, 13.0f), agn.a(context2, 10.0f), SupportMenu.CATEGORY_MASK, -16777216, -7829368, agn.a(context2, 24.0f), agn.a(context2, 16.0f), 7);
    }

    private void initConst(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumFlingVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFlingScroller = new Scroller(getContext(), null, true);
        this.mAdjustScroller = new Scroller(getContext(), new DecelerateInterpolator(2.5f));
        this.mVisibleEdge = new int[2];
        this.visiableArea = new RectF();
        this.mBackgroundBound = new RectF();
        this.mLeftForegroundBound = new RectF();
        this.mRightForegroundBound = new RectF();
    }

    public void initView(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, int i18, int i19) {
        this.mItemBarSize = a.a;
        this.mBarWidth = i5;
        this.mDividerWidth = i6;
        this.mDividerHeight = i7;
        this.mBarMaxHeight = i3;
        this.mBarMinHeight = i4;
        this.mTitleHeight = i;
        this.mMiddleWhiteHeight = i2;
        this.mBarMaxWidth = i17;
        this.mBarMinWidth = i18;
        this.mContentHeight = this.mTitleHeight + this.mMiddleWhiteHeight + this.mBarMaxHeight;
        this.mBarDiffHeight = this.mBarMaxHeight - this.mBarMinHeight;
        this.mItemWidth = ((tt.a((Context) AMapAppGlobal.getApplication()) - getPaddingLeft()) - getPaddingRight()) / (i19 <= 0 ? 7 : i19);
        this.mFocusTitlePaint = new Paint();
        this.mFocusTitlePaint.setTextAlign(Align.CENTER);
        this.mFocusTitlePaint.setColor(i10);
        this.mFocusTitlePaint.setTextSize((float) i8);
        this.mFocusTitlePaint.setTypeface(getTypeFace());
        this.mFocusTitlePaint.setAntiAlias(true);
        this.mNormalTitlePaint = new Paint();
        this.mNormalTitlePaint.setTextAlign(Align.CENTER);
        this.mNormalTitlePaint.setColor(i11);
        float f = (float) i9;
        this.mNormalTitlePaint.setTextSize(f);
        this.mNormalTitlePaint.setTypeface(getTypeFace());
        this.mNormalTitlePaint.setAntiAlias(true);
        this.mBarTopTextPaint = new TextPaint();
        this.mBarTopTextPaint.setTextAlign(Align.CENTER);
        this.mBarTopTextPaint.setColor(i15);
        this.mBarTopTextPaint.setTextSize((float) i13);
        this.mBarTopTextPaint.setTypeface(getTypeFace());
        this.mBarTopTextPaint.setAntiAlias(true);
        this.mBarTopTextFocusPaint = new TextPaint();
        this.mBarTopTextFocusPaint.setTextAlign(Align.CENTER);
        this.mBarTopTextFocusPaint.setColor(i14);
        this.mBarTopTextFocusPaint.setTextSize((float) i12);
        this.mBarTopTextFocusPaint.setTypeface(getTypeFace());
        this.mBarTopTextFocusPaint.setAntiAlias(true);
        this.mUnreachableTitlePaint = new Paint();
        this.mUnreachableTitlePaint.setTextAlign(Align.CENTER);
        this.mUnreachableTitlePaint.setColor(i16);
        this.mUnreachableTitlePaint.setTextSize(f);
        this.mUnreachableTitlePaint.setAntiAlias(true);
        this.mDividerPaint = new Paint();
        this.mDividerPaint.setColor(-2955536);
        this.mDividerPaint.setStyle(Style.STROKE);
        this.mDividerPaint.setStrokeWidth((float) this.mDividerWidth);
        this.mDividerPaint.setAntiAlias(true);
        this.mDividerPaint.setPathEffect(new DashPathEffect(new float[]{8.0f, 16.0f}, 0.0f));
        this.mBarPaint = new Paint();
        Paint paint = this.mBarPaint;
        LinearGradient linearGradient = new LinearGradient(0.0f, (float) (getPaddingTop() + this.mMiddleWhiteHeight), 0.0f, (float) (getPaddingTop() + this.mMiddleWhiteHeight + this.mBarMaxHeight), this.defaultLineColor, null, TileMode.CLAMP);
        paint.setShader(linearGradient);
        this.mBarPaint.setAntiAlias(true);
        this.mBarExcPaint = new Paint();
        this.mBarExcPaint.setColor(-3355444);
        this.mBarExcPaint.setAntiAlias(true);
        this.visiableArea.left = (float) getPaddingLeft();
        this.visiableArea.top = (float) getPaddingTop();
        this.visiableArea.bottom = (float) (getPaddingTop() + this.mTitleHeight + this.mMiddleWhiteHeight + this.mBarMaxHeight);
        this.mBackgroundBound.bottom = (float) (this.mContentHeight + getPaddingTop());
        this.mBackgroundBound.top = 0.0f;
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setStyle(Style.FILL);
        this.mBackgroundPaint.setColor(-1);
        this.mForegroundPaint = new Paint();
        this.mForegroundPaint.setStyle(Style.FILL);
        this.mForegroundPaint.setColor(1442840575);
        this.theViewParamsInited = true;
    }

    private Typeface getTypeFace() {
        return erp.a((Context) AMapAppGlobal.getApplication()).a((String) "regular.ttf");
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int measuredWidth = getMeasuredWidth();
        super.onMeasure(i, i2);
        if (measuredWidth != getMeasuredWidth()) {
            if (this.theViewParamsInited) {
                initParamsAfterMeasure();
            }
            this.hasMeasured = true;
            processDataAfterMeasured();
        }
    }

    private void calDividerPoint() {
        int a2 = agn.a(getContext(), 12.0f);
        this.mHorizonDividers = new float[12];
        int paddingLeft = getPaddingLeft() + a2;
        int measuredWidth = (getMeasuredWidth() - getPaddingRight()) - a2;
        for (int i = 0; i < 3; i++) {
            int i2 = i << 2;
            this.mHorizonDividers[i2] = (float) paddingLeft;
            float f = (float) ((this.mContentHeight - this.mTitleHeight) - ((this.mBarMaxHeight >> 1) * i));
            this.mHorizonDividers[i2 + 1] = f;
            this.mHorizonDividers[i2 + 2] = (float) measuredWidth;
            this.mHorizonDividers[i2 + 3] = f;
        }
    }

    public void initParamsAfterMeasure() {
        if (getMeasuredHeight() != 0 && getMeasuredWidth() != 0) {
            this.mContentWidth = (getMeasuredWidth() - getPaddingRight()) - getPaddingLeft();
            this.visiableArea.right = (float) (getMeasuredWidth() - getPaddingRight());
            this.maxOfVisible = ((int) Math.floor((double) ((this.visiableArea.right - this.visiableArea.left) / ((float) (this.mItemWidth + this.mDividerWidth))))) + 2;
            calDividerPoint();
            this.mBackgroundBound.left = (float) ((getPaddingLeft() + ((this.mContentWidth - this.mItemWidth) >> 1)) - 1);
            this.mBackgroundBound.right = this.mBackgroundBound.left + ((float) this.mItemWidth) + 1.0f;
            RectF rectF = this.mLeftForegroundBound;
            this.mRightForegroundBound.top = 0.0f;
            rectF.top = 0.0f;
            RectF rectF2 = this.mLeftForegroundBound;
            float f = (float) (this.mTitleHeight + this.mMiddleWhiteHeight + this.mBarMaxHeight);
            this.mRightForegroundBound.bottom = f;
            rectF2.bottom = f;
            this.mLeftForegroundBound.left = (float) getPaddingLeft();
            this.mLeftForegroundBound.right = this.mBackgroundBound.left;
            this.mRightForegroundBound.left = this.mBackgroundBound.right;
            this.mRightForegroundBound.right = (float) (getMeasuredWidth() - getPaddingRight());
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (!isEnabled() || this.datas == null || this.mUnreachableRightIndex - this.mUnreachableLeftIndex <= 2) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getActionMasked()) {
            case 0:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!this.mFlingScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                    onScrollStateChange(0);
                } else if (!this.mAdjustScroller.isFinished()) {
                    this.mFlingScroller.forceFinished(true);
                    this.mAdjustScroller.forceFinished(true);
                }
                float x = motionEvent.getX();
                this.mLastDownEventX = x;
                this.mLastDownOrMoveEventX = x;
                this.mLastDownEventTime = motionEvent.getEventTime();
                break;
            case 1:
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumFlingVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity();
                if (Math.abs(xVelocity) > this.mMinimumFlingVelocity) {
                    onScrollStateChange(2);
                    fling(xVelocity);
                } else {
                    int x2 = (int) motionEvent.getX();
                    long eventTime = motionEvent.getEventTime() - this.mLastDownEventTime;
                    if (((int) Math.abs(((float) x2) - this.mLastDownEventX)) > this.mTouchSlop || eventTime >= ((long) ViewConfiguration.getTapTimeout())) {
                        z = ensureScrollWheelAdjusted(this.mFocusItemIndex);
                    } else {
                        z = click(x2);
                    }
                    if (!z) {
                        onScrollStateChange(0);
                    } else {
                        onScrollStateChange(2);
                    }
                }
                this.mVelocityTracker.recycle();
                this.mVelocityTracker = null;
                break;
            case 2:
                float x3 = motionEvent.getX();
                if (this.mScrollState == 1) {
                    scrollByOffset((float) ((int) (x3 - this.mLastDownOrMoveEventX)));
                    invalidate();
                    onScrollStateChange(this.mScrollState);
                } else if (((int) Math.abs(x3 - this.mLastDownEventX)) > this.mTouchSlop) {
                    onScrollStateChange(1);
                }
                this.mLastDownOrMoveEventX = x3;
                break;
        }
        return true;
    }

    private boolean click(int i) {
        int ceil = ((int) Math.ceil((double) (Math.abs(this.offset - ((float) i)) / ((float) (this.mItemWidth + this.mDividerWidth))))) - 1;
        if (this.mFocusItemIndex == ceil || ceil <= this.mUnreachableLeftIndex || ceil >= this.mUnreachableRightIndex) {
            return false;
        }
        return ensureScrollWheelAdjusted(ceil);
    }

    private void scrollByOffset(float f) {
        this.offset += f;
        this.offset = Math.max(Math.min(this.mLeftOffset, this.offset), this.mRightOffset);
        this.mVisibleEdge = findSuitEdgeInVisual(this.offset);
        int i = this.mFocusItemIndex;
        this.mFocusItemIndex = findFocuseItemIndex(this.offset, this.mLeftOffset);
        if (this.mScrollState != 0 && i != this.mFocusItemIndex) {
            vibrate(getContext());
        }
    }

    private void scrollByIndex(int i) {
        scrollByOffset((((float) (this.mContentWidth >> 1)) - ((float) ((i * (this.mItemWidth + this.mDividerWidth)) + (this.mItemWidth >> 1)))) - this.offset);
    }

    private void fling(int i) {
        this.mPreviousScrollerX = 0;
        if (i > 0) {
            this.mFlingScroller.fling(0, 0, i, 0, 0, Integer.MAX_VALUE, 0, 0);
        } else {
            this.mFlingScroller.fling(Integer.MAX_VALUE, 0, i, 0, 0, Integer.MAX_VALUE, 0, 0);
        }
        invalidate();
    }

    private boolean ensureScrollWheelAdjusted(int i) {
        float f = this.mLeftOffset - ((float) (((i - this.mUnreachableLeftIndex) - 1) * (this.mItemWidth + this.mDividerWidth)));
        if (f == this.offset) {
            return false;
        }
        this.mPreviousScrollerX = 0;
        this.mAdjustScroller.startScroll((int) this.offset, 0, (int) (f - this.offset), 0, 800);
        invalidate();
        return true;
    }

    private void onScrollStateChange(int i) {
        if (this.mScrollState != i || this.mFocusItemIndex != this.mCallbackFocusItemIndex) {
            this.mScrollState = i;
            this.mCallbackFocusItemIndex = this.mFocusItemIndex;
            callbackJS(i, this.mFocusItemIndex);
        }
    }

    public void computeScroll() {
        Scroller scroller = this.mFlingScroller;
        if (scroller.isFinished()) {
            scroller = this.mAdjustScroller;
            if (scroller.isFinished()) {
                if (this.mScrollState == 2) {
                    onScrollStateChange(0);
                }
                return;
            }
        }
        scroller.computeScrollOffset();
        int currX = scroller.getCurrX();
        if (this.mPreviousScrollerX == 0) {
            this.mPreviousScrollerX = scroller.getStartX();
        }
        scrollByOffset((float) (currX - this.mPreviousScrollerX));
        this.mPreviousScrollerX = currX;
        if (scroller == this.mFlingScroller && (Float.compare(this.offset, this.mLeftOffset) == 0 || Float.compare(this.offset, this.mRightOffset) == 0)) {
            this.mFlingScroller.forceFinished(true);
        }
        if (!scroller.isFinished()) {
            invalidate();
            onScrollStateChange(this.mScrollState);
        } else if (onScrollerFinished(scroller)) {
            onScrollStateChange(2);
        } else {
            onScrollStateChange(0);
        }
    }

    private boolean onScrollerFinished(Scroller scroller) {
        if (scroller == this.mFlingScroller) {
            return ensureScrollWheelAdjusted(this.mFocusItemIndex);
        }
        return false;
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.items != null) {
            canvas.save();
            canvas.clipRect(this.visiableArea.left, this.visiableArea.top, this.visiableArea.right, this.visiableArea.bottom);
            drawBackground(canvas);
            drawDashedDividers(canvas);
            canvas.save();
            canvas.translate(this.offset, 0.0f);
            drawBars(canvas, this.mVisibleEdge[0], this.mVisibleEdge[1]);
            canvas.restore();
            drawForeground(canvas);
            canvas.restore();
        }
    }

    private int[] findSuitEdgeInVisual(float f) {
        int max = (int) Math.max((f * -1.0f) / ((float) (this.mItemWidth + this.mDividerWidth)), 0.0f);
        return new int[]{max, Math.min(this.maxOfVisible + max, this.datas.size())};
    }

    private int findFocuseItemIndex(float f, float f2) {
        return Math.round(Math.abs(f - f2) / ((float) (this.mItemWidth + this.mDividerWidth))) + this.mUnreachableLeftIndex + 1;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x0056, code lost:
        if (r5 != Float.NaN) goto L_0x005a;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void drawBars(android.graphics.Canvas r21, int r22, int r23) {
        /*
            r20 = this;
            r0 = r20
            r1 = r21
            r2 = 1065353216(0x3f800000, float:1.0)
            r4 = r22
            r3 = r23
            r5 = 1065353216(0x3f800000, float:1.0)
        L_0x000c:
            if (r4 > r3) goto L_0x016a
            java.util.List<com.autonavi.minimap.drive.view.BarPicker$a> r6 = r0.items
            int r6 = r6.size()
            if (r4 >= r6) goto L_0x016a
            java.util.List<com.autonavi.minimap.drive.view.BarPicker$a> r6 = r0.items
            java.lang.Object r6 = r6.get(r4)
            com.autonavi.minimap.drive.view.BarPicker$a r6 = (com.autonavi.minimap.drive.view.BarPicker.a) r6
            int r7 = r6.b
            r8 = 3
            r9 = 1
            if (r7 == r9) goto L_0x0030
            if (r7 == r8) goto L_0x002b
            android.graphics.Paint r7 = r0.mBarExcPaint
            android.graphics.Paint r10 = r0.mNormalTitlePaint
            goto L_0x0060
        L_0x002b:
            android.graphics.Paint r7 = r0.mBarExcPaint
            android.graphics.Paint r10 = r0.mUnreachableTitlePaint
            goto L_0x0060
        L_0x0030:
            android.graphics.Paint r7 = r0.mBarPaint
            android.graphics.Paint r10 = r0.mNormalTitlePaint
            long r11 = r0.mAniStartTimestamp
            r13 = 0
            int r5 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1))
            if (r5 <= 0) goto L_0x0058
            long r11 = android.os.SystemClock.elapsedRealtime()
            long r13 = r0.mAniStartTimestamp
            long r11 = r11 - r13
            float r5 = (float) r11
            float r5 = r5 * r2
            r11 = 1142292480(0x44160000, float:600.0)
            float r5 = r5 / r11
            r11 = 0
            int r11 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r11 < 0) goto L_0x0058
            int r11 = (r5 > r2 ? 1 : (r5 == r2 ? 0 : -1))
            if (r11 > 0) goto L_0x0058
            r11 = 2143289344(0x7fc00000, float:NaN)
            int r11 = (r5 > r11 ? 1 : (r5 == r11 ? 0 : -1))
            if (r11 != 0) goto L_0x005a
        L_0x0058:
            r5 = 1065353216(0x3f800000, float:1.0)
        L_0x005a:
            android.view.animation.DecelerateInterpolator r11 = r0.mDecelerateInterpolator
            float r5 = r11.getInterpolation(r5)
        L_0x0060:
            int r11 = r0.mFocusItemIndex
            if (r4 != r11) goto L_0x006a
            android.graphics.Paint r10 = r0.mFocusTitlePaint
            android.text.TextPaint r11 = r0.mBarTopTextFocusPaint
        L_0x0068:
            r13 = r11
            goto L_0x006d
        L_0x006a:
            android.text.TextPaint r11 = r0.mBarTopTextPaint
            goto L_0x0068
        L_0x006d:
            int r11 = r0.mFocusItemIndex
            if (r4 != r11) goto L_0x0072
            goto L_0x0073
        L_0x0072:
            r9 = 0
        L_0x0073:
            if (r1 == 0) goto L_0x0164
            android.graphics.RectF r11 = r6.j
            r12 = 1073741824(0x40000000, float:2.0)
            if (r11 == 0) goto L_0x00fa
            int r11 = r6.b
            if (r11 == r8) goto L_0x00fa
            if (r9 == 0) goto L_0x009e
            android.graphics.RectF r9 = r6.j
            float r11 = r6.f
            float r14 = r6.g
            float r11 = r11 + r14
            int r14 = r6.m
            float r14 = (float) r14
            float r11 = r11 - r14
            float r11 = r11 / r12
            r9.left = r11
            android.graphics.RectF r9 = r6.j
            float r11 = r6.f
            float r14 = r6.g
            float r11 = r11 + r14
            int r14 = r6.m
            float r14 = (float) r14
            float r11 = r11 + r14
            float r11 = r11 / r12
            r9.right = r11
            goto L_0x00ba
        L_0x009e:
            android.graphics.RectF r9 = r6.j
            float r11 = r6.f
            float r14 = r6.g
            float r11 = r11 + r14
            int r14 = r6.n
            float r14 = (float) r14
            float r11 = r11 - r14
            float r11 = r11 / r12
            r9.left = r11
            android.graphics.RectF r9 = r6.j
            float r11 = r6.f
            float r14 = r6.g
            float r11 = r11 + r14
            int r14 = r6.n
            float r14 = (float) r14
            float r11 = r11 + r14
            float r11 = r11 / r12
            r9.right = r11
        L_0x00ba:
            android.graphics.RectF r9 = r6.j
            float r11 = r6.i
            r9.top = r11
            android.graphics.RectF r9 = r6.j
            float r11 = r9.top
            android.graphics.RectF r14 = r6.j
            float r14 = r14.bottom
            android.graphics.RectF r15 = r6.j
            float r15 = r15.top
            float r14 = r14 - r15
            float r15 = r2 - r5
            float r14 = r14 * r15
            float r11 = r11 + r14
            r9.top = r11
            r21.save()
            android.graphics.RectF r9 = r6.j
            float r9 = r9.left
            android.graphics.RectF r11 = r6.j
            float r11 = r11.top
            android.graphics.RectF r14 = r6.j
            float r14 = r14.right
            android.graphics.RectF r15 = r6.j
            float r15 = r15.bottom
            int r2 = r6.h
            float r2 = (float) r2
            float r15 = r15 - r2
            r1.clipRect(r9, r11, r14, r15)
            android.graphics.RectF r2 = r6.j
            float r9 = r6.e
            float r11 = r6.e
            r1.drawRoundRect(r2, r9, r11, r7)
            r21.restore()
        L_0x00fa:
            java.lang.String r2 = r6.c
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x011f
            java.lang.String r2 = r6.c
            android.graphics.PointF r7 = r6.d
            float r7 = r7.x
            android.graphics.PointF r9 = r6.d
            float r9 = r9.y
            float r11 = r10.descent()
            float r14 = r10.ascent()
            float r11 = r11 - r14
            float r11 = r11 / r12
            float r9 = r9 + r11
            float r11 = r10.descent()
            float r9 = r9 - r11
            r1.drawText(r2, r7, r9, r10)
        L_0x011f:
            int r2 = r6.b
            if (r2 == r8) goto L_0x0164
            int r2 = r6.b
            r7 = 2
            if (r2 == r7) goto L_0x0164
            java.lang.String r2 = r6.l
            boolean r2 = android.text.TextUtils.isEmpty(r2)
            if (r2 != 0) goto L_0x0164
            android.text.StaticLayout r2 = new android.text.StaticLayout
            java.lang.String r12 = r6.l
            float r7 = r6.f
            float r8 = r6.g
            float r7 = r7 - r8
            int r14 = (int) r7
            android.text.Layout$Alignment r15 = android.text.Layout.Alignment.ALIGN_NORMAL
            r16 = 1065353216(0x3f800000, float:1.0)
            r17 = 0
            r18 = 1
            r11 = r2
            r11.<init>(r12, r13, r14, r15, r16, r17, r18)
            r6.k = r2
            r21.save()
            android.graphics.PointF r2 = r6.d
            float r2 = r2.x
            float r7 = r6.i
            android.text.StaticLayout r8 = r6.k
            int r8 = r8.getHeight()
            float r8 = (float) r8
            float r7 = r7 - r8
            r1.translate(r2, r7)
            android.text.StaticLayout r2 = r6.k
            r2.draw(r1)
            r21.restore()
        L_0x0164:
            int r4 = r4 + 1
            r2 = 1065353216(0x3f800000, float:1.0)
            goto L_0x000c
        L_0x016a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.autonavi.minimap.drive.view.BarPicker.drawBars(android.graphics.Canvas, int, int):void");
    }

    private void drawDashedDividers(Canvas canvas) {
        if (this.mHorizonDividers != null && this.mHorizonDividers.length > 0 && this.mHorizonDividers.length % 4 == 0) {
            Path path = new Path();
            int length = this.mHorizonDividers.length / 4;
            for (int i = 0; i < length; i++) {
                int i2 = i << 2;
                path.moveTo(this.mHorizonDividers[i2], this.mHorizonDividers[i2 + 1]);
                path.lineTo(this.mHorizonDividers[i2 + 2], this.mHorizonDividers[i2 + 3]);
            }
            canvas.drawPath(path, this.mDividerPaint);
        }
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawRect(this.mBackgroundBound, this.mBackgroundPaint);
    }

    private void drawForeground(Canvas canvas) {
        canvas.drawRect(this.mLeftForegroundBound, this.mForegroundPaint);
        canvas.drawRect(this.mRightForegroundBound, this.mForegroundPaint);
    }

    private void feedInternal(List<c> list, int i, int i2, int i3) {
        reset();
        this.datas = list;
        this.mUnreachableLeftIndex = i2 - 1;
        this.mUnreachableRightIndex = list.size() - i3;
        if (i >= 0) {
            this.mFocusItemIndex = i;
        }
        if (processDataAfterMeasured()) {
            this.mAniStartTimestamp = SystemClock.elapsedRealtime();
            this.mAniCallback.run();
        }
    }

    private boolean processDataAfterMeasured() {
        if (!this.hasMeasured || this.datas == null || this.datas.size() == 0) {
            return false;
        }
        calcOffsetEdge(this.mUnreachableLeftIndex, this.mUnreachableRightIndex);
        scrollByIndex(this.mFocusItemIndex);
        calcAfterDataChanged();
        return true;
    }

    private void calcOffsetEdge(int i, int i2) {
        this.mLeftOffset = ((float) (this.mContentWidth >> 1)) - ((float) (((i + 1) * (this.mItemWidth + this.mDividerWidth)) + (this.mItemWidth >> 1)));
        this.mRightOffset = (this.mLeftOffset - ((float) (((i2 - i) - 2) * (this.mItemWidth + this.mDividerWidth)))) + 1.0f;
        StringBuilder sb = new StringBuilder("calcOffsetEdge = ");
        sb.append(this.mLeftOffset);
        sb.append("-");
        sb.append(this.mRightOffset);
    }

    private void calcAfterDataChanged() {
        int i;
        a aVar;
        ArrayList<c> arrayList = new ArrayList<>();
        arrayList.addAll(this.datas);
        this.mMaxValueOfY = 0.0f;
        this.mMinValueOfY = 2.14748365E9f;
        for (c cVar : arrayList) {
            if (cVar != null && cVar.a == 1) {
                this.mMaxValueOfY = Math.max(this.mMaxValueOfY, cVar.b);
                this.mMinValueOfY = Math.min(this.mMinValueOfY, cVar.b);
            }
        }
        this.mDiffValueOfY = this.mMaxValueOfY - this.mMinValueOfY;
        if (this.items == null) {
            this.items = new ArrayList();
        } else {
            this.items.clear();
        }
        float f = (float) ((this.mMiddleWhiteHeight + this.mBarMaxHeight) - this.mBarMinHeight);
        float[] fArr = new float[3];
        int i2 = 0;
        while (i2 < this.datas.size()) {
            c cVar2 = this.datas.get(i2);
            int i3 = i2 + 1;
            c cVar3 = i3 < this.datas.size() ? this.datas.get(i3) : null;
            float paddingLeft = (float) (((this.mDividerWidth + this.mItemWidth) * i2) + getPaddingLeft());
            float f2 = paddingLeft + ((float) this.mItemWidth);
            int i4 = cVar2.a;
            float f3 = Float.NaN;
            if (i2 == 0) {
                fArr[0] = Float.NaN;
                fArr[1] = trans2CanvasCoor(this.datas.get(i2));
            } else {
                fArr[0] = fArr[1];
                fArr[1] = fArr[2];
            }
            if (cVar3 != null) {
                f3 = trans2CanvasCoor(cVar3);
            }
            fArr[2] = f3;
            int a2 = this.datas.get(i2) != null ? (int) this.datas.get(i2).b : 0;
            switch (i4) {
                case 1:
                    i = i3;
                    aVar = new a(i4, cVar2.c, fArr[1], paddingLeft, this.visiableArea.top, f2, this.visiableArea.bottom, this.mBarWidth, this.mTitleHeight, this.mMiddleWhiteHeight, a2, this.mBarMaxWidth, this.mBarMinWidth);
                    break;
                case 2:
                    i = i3;
                    aVar = new a(i4, cVar2.c, f, paddingLeft, this.visiableArea.top, f2, this.visiableArea.bottom, this.mBarWidth, this.mTitleHeight, this.mMiddleWhiteHeight, a2, this.mBarMaxWidth, this.mBarMinWidth);
                    break;
                default:
                    i = i3;
                    aVar = new a(i4, cVar2.c, f, paddingLeft, this.visiableArea.top, f2, this.visiableArea.bottom, this.mBarWidth, this.mTitleHeight, this.mMiddleWhiteHeight, a2, this.mBarMaxWidth, this.mBarMinWidth);
                    break;
            }
            this.items.add(aVar);
            i2 = i;
        }
    }

    private void reset() {
        this.offset = 0.0f;
        this.datas = null;
        this.mAniStartTimestamp = 0;
        this.handler.removeCallbacks(this.mAniCallback);
    }

    /* access modifiers changed from: protected */
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        reset();
    }

    public void feedWithAnim(List<c> list, int i, int i2, int i3) {
        if (list != null && !list.isEmpty()) {
            feedInternal(list, i, i2, i3);
        }
    }

    @SuppressLint({"MissingPermission"})
    private void vibrate(Context context) {
        if (this.vib == null) {
            this.vib = (Vibrator) context.getSystemService("vibrator");
        }
        if (this.vib != null) {
            if (VERSION.SDK_INT >= 26) {
                if (this.vibrationEffect == null) {
                    this.vibrationEffect = VibrationEffect.createOneShot(20, 100);
                }
                if (this.vibrationEffect != null) {
                    this.vib.vibrate(this.vibrationEffect);
                    return;
                }
                return;
            }
            this.vib.vibrate(20);
        }
    }

    public BaseProperty getProperty() {
        return this.mProperty;
    }

    public Object getAttribute(String str) {
        return this.mProperty.getAttribute(str);
    }

    public void bindStrictly(long j) {
        this.mProperty.bindStrictly(j);
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

    private void callbackJS(int i, int i2) {
        if (this.mAjxContext != null) {
            StringBuilder sb = new StringBuilder("callbackJS =");
            sb.append(i);
            sb.append("-");
            sb.append(i2);
            AMapLog.d("RouteETDLog", sb.toString());
            Parcel parcel = new Parcel();
            parcel.writeInt(4);
            parcel.writeString("index");
            parcel.writeString(String.valueOf(i2));
            parcel.writeString("status");
            parcel.writeString(String.valueOf(i));
            Builder builder = new Builder();
            builder.setEventName("scroll").setNodeId(this.mAjxContext.getDomTree().getNodeId(this)).setAttribute(parcel);
            this.mAjxContext.invokeJsEvent(builder.build());
        }
    }

    private float trans2CanvasCoor(c cVar) {
        float f;
        if (cVar.a != 1) {
            return Float.NaN;
        }
        float f2 = (float) this.mMiddleWhiteHeight;
        if (this.mDiffValueOfY == 0.0f) {
            f = (float) (this.mBarDiffHeight >> 1);
        } else {
            f = (((float) this.mBarDiffHeight) * (this.mMaxValueOfY - cVar.b)) / this.mDiffValueOfY;
        }
        return f2 + f;
    }
}
