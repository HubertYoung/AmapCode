package com.autonavi.minimap.route.common.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.autonavi.minimap.R;
import java.util.ArrayList;

public class CircleTabView extends LinearLayout {
    private static final float INIT_ANIMATION_TIME = 200.0f;
    private static final float MAX_ZOOM_SCALE = 0.1f;
    private static final float RESUME_ANIMATION_TIME = 50.0f;
    private static final float SCROLL_ANIMATION_TIME = 200.0f;
    private static final float WHOLE_ANIMATION_TIME = 450.0f;
    private static final float ZOOM_ANIMATION_TIME = 100.0f;
    /* access modifiers changed from: private */
    public int mAnimationPos;
    /* access modifiers changed from: private */
    public float mAnimationResumeLeftZoomPercent;
    /* access modifiers changed from: private */
    public float mAnimationResumeRightZoomPercent;
    /* access modifiers changed from: private */
    public float mAnimationZoomPercent;
    private Paint mBackgroundPaint;
    private Paint mBgTextPaint;
    private Paint mFrontTextPaint;
    private int mIndicatorLeft;
    private int mIndicatorRight;
    /* access modifiers changed from: private */
    public float mInitZoomPercent;
    /* access modifiers changed from: private */
    public boolean mInited;
    private ArrayList<egn> mItemList;
    /* access modifiers changed from: private */
    public int mMaxTabWidth;
    /* access modifiers changed from: private */
    public a mOnTabSelectedListener;
    protected b mProperty;
    private int mScreenWidth;
    private Paint mSelectedIndicatorPaint;
    /* access modifiers changed from: private */
    public int mSelectedPosition;
    private OnClickListener mTabClickListener;
    private int mTabWidth;
    private float maxScreenTabCount;
    private int screenTabCount;

    class TabView extends TextView {
        /* access modifiers changed from: private */
        public egn item;
        /* access modifiers changed from: private */
        public int mIndex;

        public TabView(Context context) {
            super(context);
        }

        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (CircleTabView.this.mMaxTabWidth > 0 && getMeasuredWidth() > CircleTabView.this.mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(CircleTabView.this.mMaxTabWidth, 0), i2);
            }
        }

        public egn getRouteType() {
            return this.item;
        }

        public int getIndex() {
            return this.mIndex;
        }
    }

    class TabViewContainer extends RelativeLayout {
        public TabViewContainer(Context context) {
            super(context);
        }

        public TabViewContainer(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public TabViewContainer(Context context, AttributeSet attributeSet, int i) {
            super(context, attributeSet, i);
        }

        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (CircleTabView.this.mMaxTabWidth > 0 && getMeasuredWidth() > CircleTabView.this.mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(CircleTabView.this.mMaxTabWidth, 0), i2);
            }
        }
    }

    public interface a {
        void a(int i);
    }

    static class b {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public int h;

        b() {
        }
    }

    @TargetApi(21)
    public CircleTabView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mSelectedPosition = -1;
        this.screenTabCount = 6;
        this.maxScreenTabCount = 4.5f;
        this.mItemList = new ArrayList<>();
    }

    public CircleTabView(Context context, int i) {
        super(context);
        this.mSelectedPosition = -1;
        this.screenTabCount = 6;
        this.maxScreenTabCount = 4.5f;
        this.mItemList = new ArrayList<>();
    }

    public CircleTabView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleTabView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectedPosition = -1;
        this.screenTabCount = 6;
        this.maxScreenTabCount = 4.5f;
        this.mItemList = new ArrayList<>();
        this.mScreenWidth = ags.b(context).width();
        this.mSelectedPosition = -1;
        this.mIndicatorLeft = -1;
        this.mIndicatorRight = -1;
        this.mTabClickListener = createTabOnClickListener();
        init();
    }

    private void init() {
        setWillNotDraw(false);
        createTabProperty();
        this.mSelectedIndicatorPaint = new Paint();
        this.mSelectedIndicatorPaint.setColor(getResources().getColor(this.mProperty.e));
        this.mSelectedIndicatorPaint.setAntiAlias(true);
        this.mFrontTextPaint = new Paint();
        this.mFrontTextPaint.setColor(getResources().getColor(this.mProperty.c));
        this.mFrontTextPaint.setAntiAlias(true);
        this.mBgTextPaint = new Paint();
        this.mBgTextPaint.setColor(getResources().getColor(this.mProperty.d));
        this.mBgTextPaint.setAntiAlias(true);
        this.mBackgroundPaint = new Paint();
        this.mBackgroundPaint.setColor(getResources().getColor(R.color.c_4));
        this.mBackgroundPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        setOrientation(0);
    }

    /* access modifiers changed from: protected */
    public void createTabProperty() {
        this.mProperty = new b();
        this.mProperty.f = 0;
        this.mProperty.c = R.color.white;
        this.mProperty.d = R.color.black;
        this.mProperty.e = R.color.c_12;
        this.mProperty.a = agn.a(getContext(), 14.0f);
        this.mProperty.b = agn.a(getContext(), 40.0f);
        this.mProperty.g = agn.a(getContext(), 4.0f);
        this.mProperty.h = agn.a(getContext(), 26.0f);
    }

    @NonNull
    public OnClickListener createTabOnClickListener() {
        return new OnClickListener() {
            public final void onClick(View view) {
                TabView tabView = (TabView) view;
                int index = tabView.getIndex();
                if (CircleTabView.this.mSelectedPosition == index) {
                    if (CircleTabView.this.mOnTabSelectedListener != null) {
                        CircleTabView.this.mOnTabSelectedListener;
                        tabView.getRouteType();
                    }
                } else if (CircleTabView.this.mOnTabSelectedListener != null) {
                    a access$100 = CircleTabView.this.mOnTabSelectedListener;
                    tabView.getRouteType();
                    access$100.a(index);
                }
                CircleTabView.this.setSelectTab(index);
            }
        };
    }

    public boolean setSelectTab(int i) {
        if (this.mItemList == null || this.mItemList.size() == 0 || i > this.mItemList.size() || this.mSelectedPosition == i) {
            return false;
        }
        this.mSelectedPosition = i;
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            ViewGroup viewGroup = (ViewGroup) getChildAt(i2);
            boolean z = i == i2;
            ((TextView) viewGroup.getChildAt(0)).setTextColor(getResources().getColor(this.mProperty.d));
            viewGroup.setSelected(z);
            if (i == i2) {
                int measuredWidth = viewGroup.getMeasuredWidth();
                if (measuredWidth == 0) {
                    measuredWidth = this.mTabWidth;
                }
                float abs = Math.abs(((float) (agn.a(getContext(), 70.0f) / 2)) - (((float) measuredWidth) / 2.0f));
                float f = (float) i2;
                setIndicatorPosition((int) (((float) ((int) ((((float) this.mScreenWidth) / this.maxScreenTabCount) * f))) + abs), (int) (((float) ((int) ((((float) this.mScreenWidth) / this.maxScreenTabCount) * (f + 1.0f)))) - abs), true);
            }
            i2++;
        }
        return true;
    }

    public int getCurrentIndex() {
        return this.mSelectedPosition;
    }

    public ViewGroup getTabViewGoup(egn egn) {
        getChildCount();
        if (this.mItemList != null && !this.mItemList.isEmpty()) {
            for (int i = 0; i < this.mItemList.size(); i++) {
                if (egn == this.mItemList.get(i)) {
                    return (ViewGroup) getChildAt(i);
                }
            }
        }
        return null;
    }

    public egn getCurrentType() {
        if (this.mItemList == null || this.mItemList.size() <= 0 || this.mSelectedPosition >= this.mItemList.size() || this.mSelectedPosition < 0) {
            return null;
        }
        return this.mItemList.get(this.mSelectedPosition);
    }

    private void setIndicatorPosition(int i, int i2, boolean z) {
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder("setIndicatorPosition --> left: ");
        sb.append(i);
        sb.append("  right:");
        sb.append(i2);
        eao.f(name, sb.toString());
        if (this.mInited || !z) {
            if (!(i == this.mIndicatorLeft && i2 == this.mIndicatorRight)) {
                doMoveAnimation(i, i2);
            }
            return;
        }
        initAnimation(i, i2);
    }

    private void doMoveAnimation(int i, int i2) {
        final int i3 = i - this.mIndicatorLeft;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{WHOLE_ANIMATION_TIME, 0.0f});
        ofFloat.setDuration(450);
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                if (floatValue >= CircleTabView.RESUME_ANIMATION_TIME) {
                    float f = floatValue - CircleTabView.RESUME_ANIMATION_TIME;
                    if (f >= 300.0f) {
                        CircleTabView.this.mAnimationZoomPercent = ((CircleTabView.WHOLE_ANIMATION_TIME - floatValue) / CircleTabView.ZOOM_ANIMATION_TIME) * CircleTabView.MAX_ZOOM_SCALE;
                        CircleTabView.this.mAnimationPos = i3;
                    } else if (f <= CircleTabView.ZOOM_ANIMATION_TIME) {
                        CircleTabView.this.mAnimationZoomPercent = (f / CircleTabView.ZOOM_ANIMATION_TIME) * CircleTabView.MAX_ZOOM_SCALE;
                        CircleTabView.this.mAnimationPos = 0;
                    } else {
                        CircleTabView.this.mAnimationZoomPercent = CircleTabView.MAX_ZOOM_SCALE;
                        CircleTabView.this.mAnimationPos = (int) (((float) i3) * ((f - CircleTabView.ZOOM_ANIMATION_TIME) / 200.0f));
                    }
                } else {
                    float f2 = 0.0f;
                    if (Float.compare((float) CircleTabView.this.mAnimationPos, 0.0f) < 0) {
                        agn.a(CircleTabView.this.getContext(), 70.0f);
                        CircleTabView.this.mAnimationZoomPercent;
                    }
                    if (Float.compare((float) CircleTabView.this.mAnimationPos, 0.0f) > 0) {
                        agn.a(CircleTabView.this.getContext(), 70.0f);
                        CircleTabView.this.mAnimationZoomPercent;
                    }
                    if (floatValue >= 25.0f) {
                        floatValue = CircleTabView.RESUME_ANIMATION_TIME - floatValue;
                    }
                    float f3 = floatValue / CircleTabView.RESUME_ANIMATION_TIME;
                    CircleTabView.this.mAnimationResumeLeftZoomPercent = i3 >= 0 ? f3 * CircleTabView.MAX_ZOOM_SCALE : 0.0f;
                    CircleTabView circleTabView = CircleTabView.this;
                    if (i3 <= 0) {
                        f2 = f3 * CircleTabView.MAX_ZOOM_SCALE;
                    }
                    circleTabView.mAnimationResumeRightZoomPercent = f2;
                }
                CircleTabView.this.postInvalidate();
            }
        });
        ofFloat.start();
        this.mIndicatorLeft = i;
        this.mIndicatorRight = i2;
    }

    private void initAnimation(int i, int i2) {
        this.mIndicatorLeft = i;
        this.mIndicatorRight = i2;
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 200.0f});
        ofFloat.setDuration(200);
        ofFloat.addUpdateListener(new AnimatorUpdateListener() {
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                float floatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                CircleTabView.this.mInitZoomPercent = floatValue / 200.0f;
                if (Float.compare(floatValue, 200.0f) == 0) {
                    CircleTabView.this.mInited = true;
                }
                CircleTabView.this.postInvalidate();
            }
        });
        ofFloat.start();
    }

    private void updateIndicatorPosition() {
        int i;
        int i2;
        View childAt = getChildAt(this.mSelectedPosition);
        if (childAt == null || childAt.getWidth() <= 0) {
            float abs = Math.abs(((float) (agn.a(getContext(), 70.0f) / 2)) - (((float) ((int) (((float) this.mScreenWidth) / this.maxScreenTabCount))) / 2.0f));
            i = (int) (((float) ((int) ((((float) this.mScreenWidth) / this.maxScreenTabCount) * ((float) this.mSelectedPosition)))) + abs);
            i2 = (int) (((float) ((int) ((((float) this.mScreenWidth) / this.maxScreenTabCount) * (((float) this.mSelectedPosition) + 1.0f)))) - abs);
        } else {
            i = childAt.getLeft();
            i2 = childAt.getRight();
        }
        setIndicatorPosition(i, i2, false);
    }

    public void setOnTabSelectedListener(a aVar) {
        this.mOnTabSelectedListener = aVar;
    }

    public void setScreenTabCount(int i) {
        this.screenTabCount = i;
        if (((float) this.screenTabCount) < this.maxScreenTabCount) {
            this.maxScreenTabCount = (float) this.screenTabCount;
        }
    }

    public void setMaxScreenTabCount(float f) {
        this.maxScreenTabCount = f;
    }

    public int getTabCount() {
        return this.screenTabCount;
    }

    public float getMaxTabCount() {
        return this.maxScreenTabCount;
    }

    public int getTabIndex(egn egn) {
        if (this.mItemList == null || this.mItemList.size() == 0) {
            return 0;
        }
        for (int i = 0; i < this.mItemList.size(); i++) {
            if (compare(this.mItemList.get(i), egn)) {
                return i;
            }
        }
        return -1;
    }

    public void addTab(int i, egn egn, CharSequence charSequence, boolean z) {
        TabView tabView = new TabView(getContext());
        if (getTabIndex(egn) < 0) {
            this.mItemList.add(egn);
            tabView.mIndex = this.mItemList.size() - 1;
            tabView.item = egn;
            tabView.setFocusable(true);
            tabView.setOnClickListener(this.mTabClickListener);
            tabView.getPaint();
            tabView.setTextSize(1, 14.0f);
            tabView.setText(charSequence);
            tabView.setTextColor(getResources().getColor(this.mProperty.d));
            tabView.setBackgroundResource(this.mProperty.f);
            tabView.setSingleLine();
            tabView.setGravity(17);
            LayoutParams layoutParams = new LayoutParams((int) (((float) this.mScreenWidth) / this.maxScreenTabCount), this.mProperty.b, 0.0f);
            layoutParams.gravity = 80;
            TabViewContainer tabViewContainer = new TabViewContainer(getContext());
            addView(tabViewContainer, layoutParams);
            tabViewContainer.addView(tabView, layoutParams);
            if (z) {
                setSelectTab(i);
            }
        }
    }

    public void addTab(egn egn, CharSequence charSequence, boolean z) {
        if (egn != null && !TextUtils.isEmpty(charSequence)) {
            int i = 0;
            while (i < this.mItemList.size()) {
                if (!compare(egn, this.mItemList.get(i))) {
                    i++;
                } else {
                    return;
                }
            }
            this.mItemList.add(egn);
            TabView tabView = new TabView(getContext());
            tabView.mIndex = this.mItemList.size() - 1;
            tabView.item = egn;
            tabView.setFocusable(true);
            tabView.setOnClickListener(this.mTabClickListener);
            tabView.getPaint();
            tabView.setTextSize(1, 14.0f);
            tabView.setText(charSequence);
            tabView.setTextColor(getResources().getColor(this.mProperty.d));
            tabView.setBackgroundResource(this.mProperty.f);
            tabView.setSingleLine();
            tabView.setGravity(17);
            this.mTabWidth = (int) (((float) this.mScreenWidth) / this.maxScreenTabCount);
            LayoutParams layoutParams = new LayoutParams(this.mTabWidth, this.mProperty.b, 0.0f);
            layoutParams.gravity = 80;
            TabViewContainer tabViewContainer = new TabViewContainer(getContext());
            addView(tabViewContainer, layoutParams);
            tabViewContainer.addView(tabView, layoutParams);
            if (z) {
                setSelectTab(getTabIndex(egn));
                invalidate();
            }
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int childCount = getChildCount();
        int mode = MeasureSpec.getMode(i);
        if (childCount <= 1 || !(mode == 1073741824 || mode == Integer.MIN_VALUE)) {
            this.mMaxTabWidth = -1;
        } else if (childCount > 2) {
            this.mMaxTabWidth = (int) (((float) MeasureSpec.getSize(i)) * 0.4f);
        } else {
            this.mMaxTabWidth = MeasureSpec.getSize(i) / 2;
        }
        super.onMeasure(i, i2);
    }

    public void draw(Canvas canvas) {
        canvas.saveLayer(0.0f, 0.0f, (float) getRight(), (float) getBottom(), null, 31);
        super.draw(canvas);
        this.mSelectedIndicatorPaint.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
        drawCircle(canvas);
        this.mBackgroundPaint.setXfermode(new PorterDuffXfermode(Mode.DST_OVER));
        Canvas canvas2 = canvas;
        canvas2.drawRect(0.0f, 0.0f, (float) getRight(), (float) getBottom(), this.mBackgroundPaint);
        canvas2.saveLayer(0.0f, 0.0f, (float) getRight(), (float) getBottom(), null, 31);
        canvas.save();
    }

    private void drawCircle(Canvas canvas) {
        if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
            float f = 0.0f;
            float a2 = Float.compare((float) this.mAnimationPos, 0.0f) == 0 ? 0.0f : ((float) agn.a(getContext(), 25.0f)) * this.mAnimationZoomPercent;
            float f2 = (((float) (this.mProperty.b - this.mProperty.h)) / 2.0f) + a2;
            float f3 = (((float) (this.mProperty.b + this.mProperty.h)) / 2.0f) - a2;
            float a3 = Float.compare((float) this.mAnimationPos, 0.0f) >= 0 ? 0.0f : ((float) agn.a(getContext(), 70.0f)) * this.mAnimationZoomPercent;
            if (Float.compare((float) this.mAnimationPos, 0.0f) > 0) {
                f = ((float) agn.a(getContext(), 70.0f)) * this.mAnimationZoomPercent;
            }
            float a4 = ((((float) this.mIndicatorLeft) - ((float) this.mAnimationPos)) - a3) + (((float) agn.a(getContext(), 70.0f)) * this.mAnimationResumeLeftZoomPercent);
            float a5 = ((((float) this.mIndicatorRight) - ((float) this.mAnimationPos)) + f) - (((float) agn.a(getContext(), 70.0f)) * this.mAnimationResumeRightZoomPercent);
            String name = getClass().getName();
            StringBuilder sb = new StringBuilder("draw animation first --->mIndicatorLeft: ");
            sb.append(this.mIndicatorLeft);
            sb.append("mIndicatorRight");
            sb.append(this.mIndicatorRight);
            eao.e(name, sb.toString());
            String name2 = getClass().getName();
            StringBuilder sb2 = new StringBuilder("draw animation first --->left: ");
            sb2.append(a4);
            sb2.append("right");
            sb2.append(a5);
            eao.e(name2, sb2.toString());
            if (!this.mInited) {
                float a6 = ((float) agn.a(getContext(), 70.0f)) / 2.0f;
                a4 = (((float) this.mIndicatorLeft) + a6) - (this.mInitZoomPercent * a6);
                f2 = (((float) this.mProperty.b) / 2.0f) - ((((float) this.mProperty.h) * 0.4f) * this.mInitZoomPercent);
                f3 = (((float) this.mProperty.b) / 2.0f) + (((float) this.mProperty.h) * 0.4f * this.mInitZoomPercent);
                a5 = (a6 * this.mInitZoomPercent) + ((float) this.mIndicatorLeft) + a6;
                String name3 = getClass().getName();
                StringBuilder sb3 = new StringBuilder("init animation --->left: ");
                sb3.append(a4);
                sb3.append("right");
                sb3.append(a5);
                eao.e(name3, sb3.toString());
            }
            RectF rectF = new RectF(a4, f2, a5, f3);
            this.mSelectedIndicatorPaint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
            this.mSelectedIndicatorPaint.setColor(getResources().getColor(this.mProperty.c));
            canvas.drawRoundRect(rectF, (((float) this.mProperty.h) / 2.0f) * this.mInitZoomPercent, (((float) this.mProperty.h) / 2.0f) * this.mInitZoomPercent, this.mSelectedIndicatorPaint);
            this.mSelectedIndicatorPaint.setColor(getResources().getColor(this.mProperty.e));
            this.mSelectedIndicatorPaint.setXfermode(new PorterDuffXfermode(Mode.DST_ATOP));
            canvas.drawRoundRect(rectF, (((float) this.mProperty.h) / 2.0f) * this.mInitZoomPercent, (((float) this.mProperty.h) / 2.0f) * this.mInitZoomPercent, this.mSelectedIndicatorPaint);
        }
    }

    private boolean compare(egn egn, egn egn2) {
        if (egn == null || egn2 == null) {
            return false;
        }
        return egn.a(egn2);
    }
}
