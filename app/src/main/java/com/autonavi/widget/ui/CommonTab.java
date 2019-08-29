package com.autonavi.widget.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class CommonTab extends LinearLayout {
    private static final int DEFAULT_DIVIDE = 1;
    public static final int TAB_A = 0;
    private int mCurrentStyle;
    private Paint mDividePaint;
    private int mIndicatorLeft;
    private int mIndicatorRight;
    /* access modifiers changed from: private */
    public int mMaxTabWidth;
    /* access modifiers changed from: private */
    public erq mOnTabSelectedListener;
    private a mProperty;
    private Paint mSelectedIndicatorPaint;
    /* access modifiers changed from: private */
    public int mSelectedPosition;
    private final OnClickListener mTabClickListener;

    class TabView extends TextView {
        /* access modifiers changed from: private */
        public int mIndex;

        public TabView(Context context) {
            super(context);
        }

        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            if (CommonTab.this.mMaxTabWidth > 0 && getMeasuredWidth() > CommonTab.this.mMaxTabWidth) {
                super.onMeasure(MeasureSpec.makeMeasureSpec(CommonTab.this.mMaxTabWidth, UCCore.VERIFY_POLICY_QUICK), i2);
            }
        }

        public int getIndex() {
            return this.mIndex;
        }
    }

    static class a {
        public int a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;

        a() {
        }
    }

    public CommonTab(Context context, int i) {
        super(context);
        this.mSelectedPosition = -1;
        this.mIndicatorLeft = -1;
        this.mIndicatorRight = -1;
        this.mTabClickListener = new OnClickListener() {
            public final void onClick(View view) {
                int index = ((TabView) view).getIndex();
                CommonTab.this.setIndicatorPosition(view.getLeft(), view.getRight());
                CommonTab.this.setSelectTab(index);
                if (CommonTab.this.mSelectedPosition == index) {
                    if (CommonTab.this.mOnTabSelectedListener != null) {
                        CommonTab.this.mOnTabSelectedListener.b(index);
                    }
                } else if (CommonTab.this.mOnTabSelectedListener != null) {
                    CommonTab.this.mOnTabSelectedListener.a(index);
                }
            }
        };
        init(context, null, i);
    }

    public CommonTab(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CommonTab(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSelectedPosition = -1;
        this.mIndicatorLeft = -1;
        this.mIndicatorRight = -1;
        this.mTabClickListener = new OnClickListener() {
            public final void onClick(View view) {
                int index = ((TabView) view).getIndex();
                CommonTab.this.setIndicatorPosition(view.getLeft(), view.getRight());
                CommonTab.this.setSelectTab(index);
                if (CommonTab.this.mSelectedPosition == index) {
                    if (CommonTab.this.mOnTabSelectedListener != null) {
                        CommonTab.this.mOnTabSelectedListener.b(index);
                    }
                } else if (CommonTab.this.mOnTabSelectedListener != null) {
                    CommonTab.this.mOnTabSelectedListener.a(index);
                }
            }
        };
        init(context, attributeSet, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CommonTab);
        this.mCurrentStyle = i;
        if (obtainStyledAttributes != null) {
            this.mCurrentStyle = obtainStyledAttributes.getInt(R.styleable.CommonTab_tab_style, 0);
            obtainStyledAttributes.recycle();
        }
        setWillNotDraw(false);
        createTabProperty();
        this.mSelectedIndicatorPaint = new Paint();
        this.mSelectedIndicatorPaint.setColor(getResources().getColor(this.mProperty.d));
        this.mDividePaint = new Paint();
        this.mDividePaint.setColor(getResources().getColor(this.mProperty.e));
        setOrientation(0);
    }

    private void createTabProperty() {
        this.mProperty = new a();
        if (this.mCurrentStyle == 0) {
            this.mProperty.f = R.color.c_4;
            this.mProperty.c = R.drawable.tab_a_text_selector;
            this.mProperty.d = R.color.c_12;
            this.mProperty.e = R.color.c_13;
            this.mProperty.a = getResources().getDimensionPixelOffset(R.dimen.f_s_14);
            this.mProperty.b = getResources().getDimensionPixelOffset(R.dimen.tab_a_height);
            this.mProperty.g = getResources().getDimensionPixelOffset(R.dimen.tab_a_selected_height);
        }
    }

    public void setSelectTab(int i) {
        this.mSelectedPosition = i;
        int childCount = getChildCount();
        int i2 = 0;
        while (i2 < childCount) {
            getChildAt(i2).setSelected(i == i2);
            i2++;
        }
        View childAt = getChildAt(i);
        setIndicatorPosition(childAt.getLeft(), childAt.getRight());
    }

    /* access modifiers changed from: private */
    public void setIndicatorPosition(int i, int i2) {
        if (i != this.mIndicatorLeft || i2 != this.mIndicatorRight) {
            this.mIndicatorLeft = i;
            this.mIndicatorRight = i2;
            postInvalidate();
        }
    }

    private void updateIndicatorPosition() {
        int i;
        int i2;
        View childAt = getChildAt(this.mSelectedPosition);
        if (childAt == null || childAt.getWidth() <= 0) {
            i = -1;
            i2 = -1;
        } else {
            i = childAt.getLeft();
            i2 = childAt.getRight();
        }
        setIndicatorPosition(i, i2);
    }

    public void setOnTabSelectedListener(erq erq) {
        this.mOnTabSelectedListener = erq;
    }

    public void addTab(int i, CharSequence charSequence, boolean z) {
        TabView tabView = new TabView(getContext());
        tabView.mIndex = i;
        tabView.setFocusable(true);
        tabView.setOnClickListener(this.mTabClickListener);
        tabView.setText(charSequence);
        tabView.setTextColor(getResources().getColorStateList(this.mProperty.c));
        tabView.setBackgroundResource(this.mProperty.f);
        tabView.setSingleLine();
        tabView.setGravity(17);
        LayoutParams layoutParams = new LayoutParams(0, this.mProperty.b, 1.0f);
        layoutParams.gravity = 17;
        addView(tabView, layoutParams);
        if (z) {
            setSelectTab(i);
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

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        updateIndicatorPosition();
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.mIndicatorLeft >= 0 && this.mIndicatorRight > this.mIndicatorLeft) {
            canvas.drawRect((float) this.mIndicatorLeft, (float) (getHeight() - this.mProperty.g), (float) this.mIndicatorRight, (float) getHeight(), this.mSelectedIndicatorPaint);
        }
        canvas.drawRect((float) getLeft(), (float) (getHeight() - 1), (float) getRight(), (float) getHeight(), this.mDividePaint);
    }
}
