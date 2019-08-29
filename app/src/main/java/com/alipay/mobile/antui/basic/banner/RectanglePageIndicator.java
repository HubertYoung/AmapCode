package com.alipay.mobile.antui.basic.banner;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.api.AdapterCount;

public class RectanglePageIndicator extends LinearLayout implements PageIndicator {
    private int mBigWidth;
    private int mHeight;
    private int mLastPosition = -1;
    private int mSmallWidth;
    private ViewPager mViewPager;
    private int margin;
    private int realCount;
    private int selectColor;
    private int unSelectColor;

    public RectanglePageIndicator(Context context) {
        super(context);
        init(context);
    }

    public RectanglePageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        setGravity(17);
        this.margin = context.getResources().getDimensionPixelSize(R.dimen.au_indicator_margin);
        this.mHeight = context.getResources().getDimensionPixelSize(R.dimen.au_indicator_height);
        this.mSmallWidth = context.getResources().getDimensionPixelSize(R.dimen.au_indicator_small_width);
        this.mBigWidth = context.getResources().getDimensionPixelSize(R.dimen.au_indicator_big_width);
        this.selectColor = context.getResources().getColor(R.color.indicator_select_style_bright);
        this.unSelectColor = context.getResources().getColor(R.color.indicator_unselect_style_bright);
    }

    public void setViewPager(ViewPager view) {
        if (this.mViewPager != view) {
            if (view.getAdapter() == null) {
                throw new IllegalStateException("RectanglePageIndicator ViewPager does not have adapter instance.");
            }
            this.mViewPager = view;
            if (this.mViewPager.getAdapter() instanceof AdapterCount) {
                this.realCount = ((AdapterCount) this.mViewPager.getAdapter()).getRealCount();
            } else {
                this.realCount = this.mViewPager.getAdapter().getCount();
            }
            if (this.mViewPager != null && this.mViewPager.getAdapter() != null) {
                this.mLastPosition = -1;
                createIndicators();
                this.mViewPager.removeOnPageChangeListener(this);
                this.mViewPager.addOnPageChangeListener(this);
                onPageSelected(this.mViewPager.getCurrentItem());
            }
        }
    }

    public void setViewPager(ViewPager view, int initialPosition) {
    }

    private void createIndicators() {
        removeAllViews();
        if (this.realCount > 1) {
            int currentItem = this.mViewPager.getCurrentItem();
            int orientation = getOrientation();
            for (int i = 0; i < this.realCount; i++) {
                if (currentItem == i) {
                    addIndicator(orientation, RectDrawable.createBigRectDrawable(this.selectColor, this.mHeight, this.mBigWidth), true);
                } else {
                    addIndicator(orientation, RectDrawable.createSmallRectDrawable(this.unSelectColor, this.mHeight, this.mSmallWidth), false);
                }
            }
        }
    }

    private void addIndicator(int orientation, Drawable rectDrawable, boolean isSelect) {
        View indicator = new View(getContext());
        indicator.setBackgroundDrawable(rectDrawable);
        if (isSelect) {
            addView(indicator, this.mBigWidth, this.mHeight);
        } else {
            addView(indicator, this.mSmallWidth, this.mHeight);
        }
        setMargin(orientation, indicator);
    }

    public void setCurrentItem(int item) {
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
    }

    public void notifyDataSetChanged() {
    }

    public void onPageScrolled(int i, float v, int i1) {
    }

    public void onPageSelected(int position) {
        LayoutParams lp;
        LayoutParams lp2;
        if (this.realCount > 1 && this.mViewPager.getAdapter() != null) {
            int position2 = position % this.realCount;
            if (this.mLastPosition >= 0) {
                View currentIndicator = getChildAt(this.mLastPosition);
                if (currentIndicator != null) {
                    currentIndicator.setBackgroundDrawable(RectDrawable.createSmallRectDrawable(this.unSelectColor, this.mHeight, this.mSmallWidth));
                    if (currentIndicator.getLayoutParams() != null) {
                        lp2 = (LayoutParams) currentIndicator.getLayoutParams();
                    } else {
                        lp2 = new LayoutParams(this.mSmallWidth, this.mHeight);
                    }
                    setMargin(lp2);
                    lp2.width = this.mSmallWidth;
                    lp2.height = this.mHeight;
                    currentIndicator.setLayoutParams(lp2);
                }
            }
            View selectedIndicator = getChildAt(position2);
            if (selectedIndicator != null) {
                selectedIndicator.setBackgroundDrawable(RectDrawable.createBigRectDrawable(this.selectColor, this.mHeight, this.mBigWidth));
                if (selectedIndicator.getLayoutParams() != null) {
                    lp = (LayoutParams) selectedIndicator.getLayoutParams();
                } else {
                    lp = new LayoutParams(this.mBigWidth, this.mHeight);
                }
                setMargin(lp);
                lp.width = this.mBigWidth;
                lp.height = this.mHeight;
                selectedIndicator.setLayoutParams(lp);
            }
            this.mLastPosition = position2;
        }
    }

    private void setMargin(int orientation, View indicator) {
        LayoutParams lp = (LayoutParams) indicator.getLayoutParams();
        if (orientation == 0) {
            lp.leftMargin = this.margin;
            lp.rightMargin = this.margin;
        } else {
            lp.topMargin = this.margin;
            lp.bottomMargin = this.margin;
        }
        indicator.setLayoutParams(lp);
    }

    private void setMargin(LayoutParams lp) {
        if (getOrientation() == 0) {
            lp.leftMargin = this.margin;
            lp.rightMargin = this.margin;
            return;
        }
        lp.topMargin = this.margin;
        lp.bottomMargin = this.margin;
    }

    public void onPageScrollStateChanged(int i) {
    }

    public void setIndicatorStyleBrightOrDark(Context context, boolean isDark) {
        if (isDark) {
            this.selectColor = context.getResources().getColor(R.color.indicator_select_style_dark);
            this.unSelectColor = context.getResources().getColor(R.color.indicator_unselect_style_dark);
            return;
        }
        this.selectColor = context.getResources().getColor(R.color.indicator_select_style_bright);
        this.unSelectColor = context.getResources().getColor(R.color.indicator_unselect_style_bright);
    }

    public void setIndicatorColor(int selectColor2, int unSelectColor2) {
        this.selectColor = selectColor2;
        this.unSelectColor = unSelectColor2;
    }
}
