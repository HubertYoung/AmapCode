package com.autonavi.minimap.route.run.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.SystemClock;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import com.amap.bundle.logs.AMapLog;
import com.amap.bundle.statistics.LogManager;
import com.autonavi.map.fragmentcontainer.page.DoNotUseTool;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;
import org.json.JSONObject;

public class PullSectionCustomView extends View implements OnGlobalLayoutListener {
    private static final boolean DEBUG = true;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    private static final int MIN_DISTANCE_FOR_FLING = 25;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static int SECTION_NUM = 2;
    private static final String TAG = "PullSectionCustomView";
    private static final Interpolator sInterpolator = new Interpolator() {
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    private float d = 0.0f;
    String ileage = "0";
    String ileageUnit = "里程(m)";
    private int mActivePointerId = -1;
    private Paint mArrowInPaint;
    private Paint mArrowMidPaint;
    private Paint mArrowOutPaint;
    private Paint mBkgPaint;
    private boolean mCanResonpse = false;
    private boolean mCanTouch = true;
    private int mCurItem;
    private float mDensity;
    private Bitmap mDownArrow;
    private Paint mDownArrowPaint;
    private RectF mDownRectF;
    private float mDsBottom;
    private float mDsLeft;
    private float mDsRight;
    private float mDsTop;
    private Paint mFgPaint;
    private boolean mFirstLayout = true;
    private Paint mFlashPaint;
    private int mFlingDistance;
    RectF mIleageR;
    RectF mIleageUnitR;
    private float mInitialMotionX;
    private float mInitialMotionY;
    private boolean mIsAddLog;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private float mLastMotionX;
    private float mLastMotionY;
    private Bitmap mLockDownBitmap;
    private int mLockLength;
    RectF mLockR;
    private RectF mLockRectF;
    private float mLockStartX;
    private float mLockStartY;
    private Bitmap mLockUpBitmap;
    private int mMaximumVelocity;
    private Rect mMeasureRect = new Rect();
    private int mMinimumVelocity;
    private a mOnDownListener;
    private int mOriginalHeight;
    private float mPercent;
    private float mPercentOnTouchDown;
    private RectF mPullZone;
    private int mRadius;
    private int mScrollState = 0;
    private Scroller mScroller;
    private boolean mScrolling;
    RectF mSpeedR;
    RectF mSpeedUnitR;
    private long mStartTime;
    private int mTouchSlop;
    private Typeface mType;
    private Bitmap mUpArrow;
    private RectF mUpRectF;
    private float mUsBottom;
    private float mUsLeft;
    private float mUsRight;
    private float mUsTop;
    RectF mUseTimeR;
    RectF mUseTimeUnitR;
    private VelocityTracker mVelocityTracker;
    private int screenHeight;
    private int screenWidth;
    String speed = "0'00\"";
    String speedUnit = "配速(min/km)";
    private Paint textPaint;
    String useTime = "00:00:00";
    String useTimeUnit = "用时";

    public static class LayoutParams extends android.view.ViewGroup.LayoutParams {
        private static final int[] b = {16842931};
        public int a;

        public LayoutParams() {
            super(-1, -1);
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, b);
            this.a = obtainStyledAttributes.getInteger(0, 0);
            obtainStyledAttributes.recycle();
        }
    }

    public interface a {
        void a();
    }

    public PullSectionCustomView(Context context) {
        super(context);
        init();
    }

    public PullSectionCustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public PullSectionCustomView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    @SuppressLint({"NewApi"})
    @TargetApi(21)
    public PullSectionCustomView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        init();
    }

    public void init() {
        setWillNotDraw(false);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.d = context.getResources().getDisplayMetrics().density;
        this.mFlingDistance = (int) (this.d * 25.0f);
        if (this.mType == null) {
            this.mType = erp.a(context).a((String) "regular.ttf");
        }
        initDisplayAreaSize();
        if (this.textPaint == null) {
            this.textPaint = new Paint();
        }
        this.mLockLength = (int) (getResources().getDisplayMetrics().density * 132.0f);
        initTextPaint(12.0f);
        initRectF();
        initLock();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    private void initTextPaint(float f, Typeface typeface) {
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize(f);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1));
        this.textPaint.setTypeface(typeface);
    }

    private void initTextPaint(float f) {
        initTextPaint(f, null);
    }

    public boolean isLockPulled() {
        return Float.compare(this.mPercent, 0.0f) > 0;
    }

    public void setIleage(String str) {
        this.ileage = str;
        checkText(str);
    }

    public void setUseTime(String str) {
        this.useTime = str;
        checkText(str);
    }

    public void setSpeed(String str) {
        this.speed = str;
        checkText(str);
    }

    public void setIleageUnit(String str) {
        this.ileageUnit = str;
    }

    public void checkText(String str) {
        if (str == null) {
            throw new IllegalArgumentException("argument can not be null!!!");
        }
        postInvalidate();
    }

    public void setmOnDownListener(a aVar) {
        this.mOnDownListener = aVar;
    }

    private void initRectF() {
        this.mIleageR = new RectF(0.0f, this.d * 27.0f, (float) this.screenWidth, this.d * 77.0f);
        this.mIleageUnitR = new RectF(0.0f, this.d * 88.0f, (float) this.screenWidth, this.d * 194.0f);
        this.mUseTimeR = new RectF(0.0f, this.d * 100.0f, ((float) (this.screenWidth / 2)) - (this.d * 30.0f), this.d * 307.0f);
        this.mUseTimeUnitR = new RectF(0.0f, this.d * 136.0f, ((float) (this.screenWidth / 2)) - (this.d * 30.0f), this.d * 355.0f);
        this.mSpeedR = new RectF(((float) (this.screenWidth / 2)) + (this.d * 30.0f), this.d * 100.0f, (float) this.screenWidth, this.d * 307.0f);
        this.mSpeedUnitR = new RectF(((float) (this.screenWidth / 2)) + (this.d * 30.0f), this.d * 136.0f, (float) this.screenWidth, this.d * 355.0f);
        this.mLockR = new RectF((float) ((this.screenWidth - this.mLockLength) / 2), this.d * 87.0f, (float) ((this.screenWidth + this.mLockLength) / 2), (float) this.screenHeight);
        this.mPullZone = new RectF(0.0f, 0.0f, (float) this.screenWidth, this.d * 182.0f);
    }

    private boolean initDisplayAreaSize() {
        int i = this.screenHeight;
        try {
            Activity activity = DoNotUseTool.getActivity();
            Point point = new Point();
            if (!(activity == null || activity.getWindowManager() == null)) {
                Display defaultDisplay = activity.getWindowManager().getDefaultDisplay();
                if (defaultDisplay != null) {
                    defaultDisplay.getSize(point);
                }
            }
            this.screenWidth = point.x;
            this.screenHeight = point.y;
        } catch (Exception unused) {
            this.screenWidth = getResources().getDisplayMetrics().widthPixels;
            this.screenHeight = getResources().getDisplayMetrics().heightPixels;
        }
        return i != this.screenHeight;
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mPercent == 1.0f) {
            i2 = MeasureSpec.makeMeasureSpec(this.screenHeight, UCCore.VERIFY_POLICY_QUICK);
        }
        super.onMeasure(i, i2);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.textPaint.setColor(getResources().getColor(R.color.c_27));
        canvas.drawRect(0.0f, 0.0f, (float) this.screenWidth, (float) ((int) Math.ceil((double) ((this.d * 167.0f) + ((((float) this.screenHeight) - (this.d * 167.0f)) * this.mPercent)))), this.textPaint);
        float f = (this.d * 55.0f) + (this.d * 42.0f * this.mPercent);
        this.textPaint.setTextSize(f);
        this.textPaint.setTypeface(this.mType);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1));
        float measureText = this.textPaint.measureText(this.ileage);
        float f2 = this.mIleageR.top + f + ((this.mIleageR.bottom - this.mIleageR.top) * this.mPercent);
        canvas.drawText(this.ileage, ((this.mIleageR.right - this.mIleageR.left) - measureText) / 2.0f, f2, this.textPaint);
        float f3 = (this.d * 13.0f) + (this.d * 3.0f * this.mPercent);
        this.textPaint.setTextSize(f3);
        this.textPaint.setTypeface(null);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1_d));
        float measureText2 = this.textPaint.measureText(this.ileageUnit);
        float f4 = this.mIleageUnitR.top + f3 + ((this.mIleageUnitR.bottom - this.mIleageUnitR.top) * this.mPercent);
        canvas.drawText(this.ileageUnit, ((this.mIleageUnitR.right - this.mIleageUnitR.left) - measureText2) / 2.0f, f4, this.textPaint);
        float f5 = (this.d * 30.0f) + (this.d * 4.0f * this.mPercent);
        this.textPaint.setTextSize(f5);
        this.textPaint.setTypeface(this.mType);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1));
        float measureText3 = this.textPaint.measureText(this.useTime);
        float f6 = (this.mUseTimeR.right - this.mUseTimeR.left) - (this.mPercent * 22.0f);
        if (measureText3 > f6) {
            measureText3 = f6;
        }
        canvas.drawText(this.useTime, this.mUseTimeR.left + (((this.mUseTimeR.right - this.mUseTimeR.left) - measureText3) / 2.0f) + (this.d * 11.0f * this.mPercent), this.mUseTimeR.top + f5 + ((this.mUseTimeR.bottom - this.mUseTimeR.top) * this.mPercent), this.textPaint);
        float f7 = (this.d * 13.0f) + (this.d * 3.0f * this.mPercent);
        this.textPaint.setTextSize(f7);
        this.textPaint.setTypeface(null);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1_d));
        canvas.drawText(this.useTimeUnit, this.mUseTimeUnitR.left + (((this.mUseTimeUnitR.right - this.mUseTimeUnitR.left) - this.textPaint.measureText(this.useTimeUnit)) / 2.0f) + (this.d * 11.0f * this.mPercent), this.mUseTimeUnitR.top + f7 + ((this.mUseTimeUnitR.bottom - this.mUseTimeUnitR.top) * this.mPercent), this.textPaint);
        float f8 = (this.d * 30.0f) + (this.d * 4.0f * this.mPercent);
        this.textPaint.setTextSize(f8);
        this.textPaint.setTypeface(this.mType);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1));
        float measureText4 = this.textPaint.measureText(this.speed);
        float f9 = (this.mSpeedR.right - this.mSpeedR.left) - (this.mPercent * 22.0f);
        if (measureText4 > f9) {
            measureText4 = f9;
        }
        canvas.drawText(this.speed, (this.mSpeedR.left + (((this.mSpeedR.right - this.mSpeedR.left) - measureText4) / 2.0f)) - ((this.d * 11.0f) * this.mPercent), this.mSpeedR.top + f8 + ((this.mSpeedR.bottom - this.mSpeedR.top) * this.mPercent), this.textPaint);
        float f10 = (this.d * 13.0f) + (this.d * 3.0f * this.mPercent);
        this.textPaint.setTextSize(f10);
        this.textPaint.setTypeface(null);
        this.textPaint.setColor(getResources().getColor(R.color.f_c_1_d));
        canvas.drawText(this.speedUnit, (this.mSpeedUnitR.left + (((this.mSpeedUnitR.right - this.mSpeedUnitR.left) - this.textPaint.measureText(this.speedUnit)) / 2.0f)) - ((this.d * 11.0f) * this.mPercent), this.mSpeedUnitR.top + f10 + ((this.mSpeedUnitR.bottom - this.mSpeedUnitR.top) * this.mPercent), this.textPaint);
        drawLockView(canvas);
    }

    private void initLock() {
        this.mBkgPaint = new Paint();
        this.mBkgPaint.setAntiAlias(true);
        this.mBkgPaint.setColor(getResources().getColor(R.color.c_27));
        this.mFgPaint = new Paint();
        this.mFgPaint.setAntiAlias(true);
        this.mFgPaint.setColor(getResources().getColor(R.color.c_2));
        this.mFlashPaint = new Paint();
        this.mFlashPaint.setAntiAlias(true);
        this.mFlashPaint.setColor(getResources().getColor(R.color.c_2));
        this.mDownArrowPaint = new Paint();
        this.mArrowOutPaint = new Paint();
        this.mArrowInPaint = new Paint();
        this.mArrowMidPaint = new Paint();
        this.mLockUpBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_lockb_upper);
        this.mLockDownBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_lockb_lower);
        this.mUpArrow = BitmapFactory.decodeResource(getResources(), R.drawable.pull_lock_up_arrow);
        this.mDownArrow = BitmapFactory.decodeResource(getResources(), R.drawable.pull_lock_down_arrow);
        this.mRadius = this.mLockLength / 4;
        this.mDensity = getResources().getDisplayMetrics().density;
        float width = ((float) this.mLockUpBitmap.getWidth()) / 3.0f;
        this.mUsLeft = (this.mDensity * 66.0f) - width;
        this.mUsRight = (this.mDensity * 66.0f) + width;
        float height = ((float) this.mLockUpBitmap.getHeight()) / 3.0f;
        this.mUsTop = ((this.mDensity * 66.0f) - height) - (this.mDensity * 2.0f);
        this.mUsBottom = ((this.mDensity * 66.0f) + height) - (this.mDensity * 2.0f);
        float width2 = ((float) this.mLockDownBitmap.getWidth()) / 3.0f;
        this.mDsLeft = (this.mDensity * 66.0f) - width2;
        this.mDsRight = (this.mDensity * 66.0f) + width2;
        this.mDsTop = this.mDensity * 66.0f;
        this.mDsBottom = (this.mDensity * 66.0f) + ((((float) this.mLockDownBitmap.getHeight()) / 3.0f) * 2.0f);
        this.mLockStartX = this.mLockR.left;
        this.mLockStartY = this.mLockR.top;
        this.mUsLeft += this.mLockStartX;
        this.mUsRight += this.mLockStartX;
        this.mUpRectF = new RectF(this.mUsLeft, this.mUsTop, this.mUsRight, this.mUsBottom);
        this.mDsLeft += this.mLockStartX;
        this.mDsRight += this.mLockStartX;
        this.mDownRectF = new RectF(this.mDsLeft, this.mDsTop, this.mDsRight, this.mDsBottom);
        this.mLockRectF = new RectF(this.mLockR.left, this.mLockR.top, this.mLockR.right, this.mLockR.top + ((float) this.mLockLength));
    }

    private void drawLockView(Canvas canvas) {
        this.mLockStartY = this.mLockR.top + (((this.mLockR.bottom - this.mLockR.top) - (this.d * 150.0f)) * this.mPercent);
        float f = this.mLockStartY + this.mUsTop;
        float f2 = this.mLockStartY + this.mUsBottom;
        float f3 = this.mLockStartY + this.mDsTop;
        float f4 = this.mLockStartY + this.mDsBottom;
        this.mLockRectF.set(this.mLockR.left, this.mLockStartY, this.mLockR.right, this.mLockStartY + ((float) this.mLockLength));
        canvas.drawCircle(this.mLockStartX + ((float) (this.mRadius * 2)), this.mLockStartY + ((float) (this.mRadius * 2)), (float) this.mRadius, this.mBkgPaint);
        float f5 = this.mPercent / 3.0f;
        float f6 = ((this.mUsRight - this.mUsLeft) * f5) / 2.0f;
        float f7 = f - ((((f2 - f) * f5) / 2.0f) * 2.0f);
        this.mUpRectF.set(this.mUsLeft - f6, f7, this.mUsRight + f6, f2);
        canvas.drawBitmap(this.mLockUpBitmap, null, this.mUpRectF, null);
        float f8 = ((this.mDsRight - this.mDsLeft) * f5) / 2.0f;
        this.mDownRectF.set(this.mDsLeft - f8, f3 - ((((f4 - f3) * f5) / 2.0f) * 3.0f), this.mDsRight + f8, f4);
        canvas.drawBitmap(this.mLockDownBitmap, null, this.mDownRectF, null);
        this.mDownArrowPaint.setAlpha((int) ((1.0f - this.mPercent) * 255.0f));
        canvas.drawBitmap(this.mDownArrow, (this.mLockStartX + ((float) (this.mLockLength / 2))) - ((float) (this.mDownArrow.getWidth() / 2)), f4 + (this.mDensity * 7.0f), this.mDownArrowPaint);
        if (this.mPercent > 0.97f) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (uptimeMillis - this.mStartTime < 2000) {
                this.mCanTouch = false;
                float f9 = (((float) (uptimeMillis - this.mStartTime)) % 500.0f) / 500.0f;
                if (f9 > 1.0f) {
                    f9 = 1.0f;
                } else if (f9 < 0.0f) {
                    f9 = 0.0f;
                }
                int height = this.mUpArrow.getHeight();
                float width = (this.mLockStartX + ((float) (this.mRadius * 2))) - ((float) (this.mUpArrow.getWidth() / 2));
                float f10 = (float) height;
                float f11 = (f7 - f10) - (this.mDensity * 7.0f);
                this.mArrowOutPaint.setAlpha((int) ((1.0f - f9) * 128.0f));
                canvas.drawBitmap(this.mUpArrow, width, f11 - ((2.0f + f9) * f10), this.mArrowOutPaint);
                this.mArrowMidPaint.setAlpha((int) (255.0f - (128.0f * f9)));
                canvas.drawBitmap(this.mUpArrow, width, f11 - ((1.0f + f9) * f10), this.mArrowMidPaint);
                this.mArrowInPaint.setAlpha((int) (255.0f * f9));
                canvas.drawBitmap(this.mUpArrow, width, f11 - (f10 * f9), this.mArrowInPaint);
                invalidate();
                return;
            }
            this.mCanTouch = true;
        }
    }

    private void completeScroll() {
        if (this.mScrolling) {
            this.mScroller.abortAnimation();
            getScrollX();
            getScrollY();
            this.mScroller.getCurrX();
            this.mScroller.getCurrY();
            setScrollState(0);
        }
        this.mScrolling = false;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        switch (motionEvent.getAction() & 255) {
            case 0:
                this.mIsAddLog = false;
                this.mPercentOnTouchDown = this.mPercent;
                if (this.mOnDownListener != null) {
                    this.mOnDownListener.a();
                }
                if (this.mScrollState != 2) {
                    completeScroll();
                    this.mCanResonpse = true;
                    float x = motionEvent.getX();
                    this.mInitialMotionX = x;
                    this.mLastMotionX = x;
                    float y = motionEvent.getY();
                    this.mInitialMotionY = y;
                    this.mLastMotionY = y;
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, 0);
                    if (!this.mPullZone.contains(this.mLastMotionX, this.mLastMotionY)) {
                        this.mCanResonpse = false;
                    } else {
                        this.mCanResonpse = true;
                    }
                    if (this.mPercent > 0.97f && this.mCanTouch) {
                        this.mStartTime = SystemClock.uptimeMillis();
                    }
                    invalidate();
                    break;
                } else {
                    this.mCanResonpse = false;
                    return false;
                }
            case 1:
                if (this.mCanResonpse && this.mIsBeingDragged) {
                    VelocityTracker velocityTracker = this.mVelocityTracker;
                    velocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                    int yVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
                    int bottom = getBottom();
                    getScrollY();
                    setCurrentItemInternal(determineTargetPage(bottom - getSectionHeight(0) > 0 ? 1 : 0, ((float) (bottom - getSectionHeight(0))) / ((float) getSectionHeight(1)), yVelocity, (int) (MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId)) - this.mInitialMotionY)), true, true, yVelocity);
                    this.mActivePointerId = -1;
                    endDrag();
                    break;
                }
            case 2:
                if (this.mCanResonpse) {
                    if (!this.mIsBeingDragged) {
                        int findPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                        float x2 = MotionEventCompat.getX(motionEvent, findPointerIndex);
                        float abs = Math.abs(x2 - this.mLastMotionX);
                        float y2 = MotionEventCompat.getY(motionEvent, findPointerIndex);
                        float abs2 = Math.abs(y2 - this.mLastMotionY);
                        if (abs2 > ((float) this.mTouchSlop) && abs2 > abs) {
                            this.mIsBeingDragged = true;
                            this.mLastMotionX = x2;
                            this.mLastMotionY = y2;
                            setScrollState(1);
                        }
                    }
                    if (this.mIsBeingDragged) {
                        float y3 = MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                        this.mLastMotionY = y3;
                        float scrollY = ((float) getScrollY()) + (y3 - this.mLastMotionY);
                        int sectionHeight = getSectionHeight(0);
                        int aboveHeight = getAboveHeight(SECTION_NUM - 1);
                        int i = (int) scrollY;
                        this.mLastMotionY += scrollY - ((float) i);
                        int bottom2 = getBottom();
                        int i2 = i + bottom2;
                        if (i2 < sectionHeight) {
                            if (!this.mScrolling) {
                                addLog(1);
                            }
                            i2 = sectionHeight;
                        } else if (i2 > aboveHeight) {
                            if (!this.mScrolling) {
                                addLog(0);
                            }
                            i2 = aboveHeight;
                        }
                        if (bottom2 >= sectionHeight && bottom2 <= aboveHeight) {
                            setBottom(i2);
                            setMeasuredDimension(getRight() - getLeft(), i2 - getTop());
                        }
                        String str = TAG;
                        StringBuilder sb = new StringBuilder("move=");
                        sb.append(y3);
                        sb.append(",bottom=");
                        sb.append(i2);
                        AMapLog.i(str, sb.toString());
                        this.mPercent = ((float) (i2 - getSectionHeight(0))) / ((float) getSectionHeight(1));
                        if (this.mPercent < 0.5f) {
                            this.mPullZone.set(0.0f, 0.0f, (float) this.screenWidth, this.d * 182.0f);
                        } else {
                            float f = this.mLockR.top + (((this.mLockR.bottom - this.mLockR.top) - (this.d * 150.0f)) * this.mPercent);
                            this.mPullZone.set(this.mLockR.left, f, this.mLockR.right, ((float) this.mLockLength) + f);
                        }
                        if (this.mPercent != 0.0f) {
                            if (this.mPercent == 1.0f) {
                                if (this.mCanTouch) {
                                    this.mStartTime = SystemClock.uptimeMillis();
                                }
                            }
                        }
                        getLayoutParams().height = i2;
                        requestLayout();
                        break;
                    }
                } else {
                    return true;
                }
                break;
            case 3:
                if (this.mCanResonpse && this.mIsBeingDragged) {
                    setCurrentItemInternal(this.mCurItem, true, true);
                    this.mActivePointerId = -1;
                    endDrag();
                    break;
                }
            case 5:
                if (this.mCanResonpse) {
                    int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
                    this.mLastMotionY = MotionEventCompat.getY(motionEvent, actionIndex);
                    this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, actionIndex);
                    break;
                } else {
                    return true;
                }
            case 6:
                if (this.mCanResonpse) {
                    onSecondaryPointerUp(motionEvent);
                    this.mLastMotionY = MotionEventCompat.getY(motionEvent, MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId));
                    break;
                } else {
                    return true;
                }
        }
        return true;
    }

    private int determineTargetPage(int i, float f, int i2, int i3) {
        if (Math.abs(i3) <= this.mFlingDistance || Math.abs(i2) <= this.mMinimumVelocity) {
            if (f >= 0.5f) {
                return 1;
            }
        } else if (i2 >= 0) {
            return 1;
        }
        return 0;
    }

    private void setScrollState(int i) {
        if (this.mScrollState != i) {
            this.mScrollState = i;
        }
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (MotionEventCompat.getPointerId(motionEvent, actionIndex) == this.mActivePointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = MotionEventCompat.getX(motionEvent, i);
            this.mActivePointerId = MotionEventCompat.getPointerId(motionEvent, i);
            if (this.mVelocityTracker != null) {
                this.mVelocityTracker.clear();
            }
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void onGlobalLayout() {
        this.screenHeight = ((View) getParent()).getHeight();
    }

    /* access modifiers changed from: protected */
    public boolean canScroll(View view, boolean z, int i, int i2, int i3) {
        View view2 = view;
        if (view2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view2;
            int scrollX = view2.getScrollX();
            int scrollY = view2.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i4 = i2 + scrollX;
                if (i4 >= childAt.getLeft() && i4 < childAt.getRight()) {
                    int i5 = i3 + scrollY;
                    if (i5 >= childAt.getTop() && i5 < childAt.getBottom()) {
                        if (canScroll(childAt, true, i, i4 - childAt.getLeft(), i5 - childAt.getTop())) {
                            return true;
                        }
                    }
                }
            }
        }
        if (!z || !ViewCompat.canScrollHorizontally(view2, -i)) {
            return false;
        }
        return true;
    }

    public void setCurrentItem(int i) {
        setCurrentItemInternal(i, !this.mFirstLayout, false);
    }

    public void setCurrentItem(int i, boolean z) {
        setCurrentItemInternal(i, z, false);
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentItemInternal(int i, boolean z, boolean z2) {
        setCurrentItemInternal(i, z, z2, 0);
    }

    /* access modifiers changed from: 0000 */
    public void setCurrentItemInternal(int i, boolean z, boolean z2, int i2) {
        if (i < 0) {
            i = 0;
        } else if (i >= SECTION_NUM) {
            i = SECTION_NUM - 1;
        }
        this.mCurItem = i;
        int aboveHeight = getAboveHeight(i);
        if (z) {
            smoothScrollTo(0, aboveHeight, i2);
            return;
        }
        getLayoutParams().height = aboveHeight;
        requestLayout();
        completeScroll();
    }

    /* access modifiers changed from: protected */
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    /* access modifiers changed from: 0000 */
    public void smoothScrollTo(int i, int i2, int i3) {
        int i4;
        int left = getLeft();
        int bottom = getBottom();
        int i5 = i - left;
        int i6 = i2 - bottom;
        if (i5 == 0 && i6 == 0) {
            completeScroll();
            setScrollState(0);
            return;
        }
        this.mScrolling = true;
        setScrollState(2);
        int abs = Math.abs(i3);
        if (abs > 0) {
            i4 = Math.round(Math.abs(((float) i6) / ((float) abs)) * 1000.0f) * 4;
        } else {
            i4 = (int) (((((float) Math.abs(i6)) / ((float) getSectionHeight(1))) + 1.0f) * 100.0f);
        }
        this.mScroller.startScroll(left, bottom, i5, i6, Math.min(i4, 600));
        invalidate();
    }

    private int getSectionHeight(int i) {
        if (i < 0 || i > SECTION_NUM - 1) {
            throw new IllegalArgumentException("参数不对!!");
        }
        int i2 = SECTION_NUM;
        if (this.mOriginalHeight == 0) {
            this.mOriginalHeight = getMeasuredHeight();
        }
        if (i2 != 2) {
            return 0;
        }
        switch (i) {
            case 0:
                return this.mOriginalHeight;
            case 1:
                return this.screenHeight - this.mOriginalHeight;
            default:
                return 0;
        }
    }

    private int getAboveHeight(int i) {
        if (i < 0 || i > SECTION_NUM - 1) {
            throw new IllegalArgumentException("参数不对!!");
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i + 1; i3++) {
            i2 += getSectionHeight(i3);
        }
        return i2;
    }

    public void computeScroll() {
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll();
            return;
        }
        int left = getLeft();
        int bottom = getBottom();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (left == currX && bottom == currY) {
            ViewCompat.postInvalidateOnAnimation(this);
            return;
        }
        int sectionHeight = getSectionHeight(0);
        int aboveHeight = getAboveHeight(SECTION_NUM - 1);
        int bottom2 = (currY - bottom) + getBottom();
        if (bottom2 < sectionHeight) {
            bottom2 = sectionHeight;
        } else if (bottom2 > aboveHeight) {
            bottom2 = aboveHeight;
        }
        if (currY < sectionHeight || currY > aboveHeight) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            setBottom(bottom2);
            setMeasuredDimension(getRight() - getLeft(), bottom2 - getTop());
        }
        String str = TAG;
        StringBuilder sb = new StringBuilder("computeScroll: still scrolling=");
        sb.append(currY);
        sb.append(",bottom=");
        sb.append(bottom2);
        AMapLog.i(str, sb.toString());
        this.mPercent = ((float) (currY - getSectionHeight(0))) / ((float) getSectionHeight(1));
        if (this.mPercent < 0.5f) {
            this.mPullZone.set(0.0f, 0.0f, (float) this.screenWidth, this.d * 182.0f);
        } else {
            float f = this.mLockR.top + (((this.mLockR.bottom - this.mLockR.top) - (this.d * 150.0f)) * this.mPercent);
            this.mPullZone.set(this.mLockR.left, f, this.mLockR.right, ((float) this.mLockLength) + f);
        }
        if (this.mPercent == 0.0f) {
            getLayoutParams().height = bottom2;
            requestLayout();
            addLog(1);
            return;
        }
        if (this.mPercent == 1.0f) {
            if (this.mCanTouch) {
                this.mStartTime = SystemClock.uptimeMillis();
            }
            getLayoutParams().height = bottom2;
            requestLayout();
            addLog(0);
        }
    }

    private void addLog(int i) {
        if (!this.mIsAddLog) {
            if ((this.mPercentOnTouchDown == 1.0f && i == 1) || (this.mPercentOnTouchDown == 0.0f && i == 0)) {
                this.mIsAddLog = true;
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("action", i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogManager.actionLogV2("P00269", "B010", jSONObject);
            }
        }
    }
}
