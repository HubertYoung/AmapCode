package com.autonavi.miniapp.plugin.map.indoor.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.autonavi.miniapp.plugin.map.indoor.widget.MiniAppFloorWidgetView.IContainer;
import com.autonavi.minimap.R;

public class MiniAppFloorWidgetLayoutWithLocationTip extends RelativeLayout {
    private int mBetweenMargin;
    private MiniAppFloorWidgetView mFloorView;
    private int mFloorViewWidth;
    private int mInterPadding;
    private ImageView mLocationView;
    private Drawable mNormalLocatioNDrawable;
    private Drawable mNormalLocatioNDrawable_R;
    private int mPaddingTop;
    private Drawable mSmallLocationDrawable;
    private Drawable mSmallLocationDrawable_R;
    private boolean mTipInRight = false;

    public MiniAppFloorWidgetLayoutWithLocationTip(Context context) {
        super(context);
        init();
    }

    public MiniAppFloorWidgetLayoutWithLocationTip(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public MiniAppFloorWidgetLayoutWithLocationTip(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    public void init() {
        this.mNormalLocatioNDrawable = getResources().getDrawable(R.drawable.indoor_location);
        this.mSmallLocationDrawable = getResources().getDrawable(R.drawable.indoor_location_small);
        this.mNormalLocatioNDrawable_R = getResources().getDrawable(R.drawable.indoor_location_r);
        this.mSmallLocationDrawable_R = getResources().getDrawable(R.drawable.indoor_location_small_r);
        this.mFloorViewWidth = getResources().getDimensionPixelSize(R.dimen.map_container_btn_size);
        this.mBetweenMargin = getResources().getDimensionPixelSize(R.dimen.floor_widget_margin);
        this.mPaddingTop = getResources().getDimensionPixelSize(R.dimen.floor_widget_view_padding);
        this.mInterPadding = getResources().getDimensionPixelSize(R.dimen.floor_widget_inner_padding);
    }

    public void setFloorView(MiniAppFloorWidgetView miniAppFloorWidgetView, IContainer iContainer) {
        if (this.mFloorView != miniAppFloorWidgetView) {
            if (this.mFloorView != null) {
                this.mFloorView.setContainer(null);
            }
            this.mFloorView = miniAppFloorWidgetView;
            this.mFloorView.setContainer(iContainer);
            this.mFloorView.setBackgroundResource(R.drawable.icon_c_bg_single);
            this.mFloorView.setPadding(0, this.mPaddingTop, 0, this.mPaddingTop);
            addView(this.mFloorView, new LayoutParams(this.mFloorViewWidth, -2));
            this.mLocationView = new ImageView(getContext());
            addView(this.mLocationView, new LayoutParams(-2, -2));
        }
    }

    public void setTipInRight(boolean z) {
        if (this.mTipInRight != z) {
            this.mTipInRight = z;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        if (this.mFloorView == null || this.mNormalLocatioNDrawable == null || this.mSmallLocationDrawable == null) {
            super.onMeasure(i, i2);
            return;
        }
        for (int i3 = 0; i3 < getChildCount(); i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                childAt.measure(getChildMeasureSpec(i, getPaddingLeft() + getPaddingRight(), layoutParams.width), getChildMeasureSpec(i2, getPaddingTop() + getPaddingBottom(), layoutParams.height));
            }
        }
        setMeasuredDimension(resolveSize(this.mFloorView.getMeasuredWidth(), i), resolveSize(this.mFloorView.getMeasuredHeight(), i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        super.onLayout(z, i, i2, i3, i4);
        if (this.mFloorView != null && this.mNormalLocatioNDrawable != null && this.mSmallLocationDrawable != null) {
            if (this.mLocationView != null) {
                switch (this.mFloorView.mLocationType) {
                    case 0:
                        this.mLocationView.setVisibility(8);
                        break;
                    case 1:
                        this.mLocationView.setVisibility(0);
                        this.mLocationView.setImageDrawable(this.mTipInRight ? this.mSmallLocationDrawable_R : this.mSmallLocationDrawable);
                        if (this.mTipInRight) {
                            i7 = this.mFloorView.getMeasuredWidth() + this.mBetweenMargin;
                        } else {
                            i7 = this.mNormalLocatioNDrawable.getIntrinsicWidth() - this.mSmallLocationDrawable.getIntrinsicWidth();
                        }
                        if (this.mTipInRight) {
                            i8 = this.mSmallLocationDrawable.getIntrinsicWidth() + i7;
                        } else {
                            i8 = this.mNormalLocatioNDrawable.getIntrinsicWidth();
                        }
                        int intrinsicHeight = this.mInterPadding - (this.mSmallLocationDrawable.getIntrinsicHeight() / 2);
                        if (intrinsicHeight < 0) {
                            intrinsicHeight = 0;
                        }
                        this.mLocationView.layout(i7, intrinsicHeight, i8, this.mSmallLocationDrawable.getIntrinsicHeight() + intrinsicHeight);
                        break;
                    case 2:
                        this.mLocationView.setVisibility(0);
                        this.mLocationView.setImageDrawable(this.mTipInRight ? this.mNormalLocatioNDrawable_R : this.mNormalLocatioNDrawable);
                        int measuredWidth = this.mTipInRight ? this.mFloorView.getMeasuredWidth() + this.mBetweenMargin : 0;
                        if (this.mTipInRight) {
                            i9 = this.mNormalLocatioNDrawable.getIntrinsicWidth() + measuredWidth;
                        } else {
                            i9 = this.mNormalLocatioNDrawable.getIntrinsicWidth();
                        }
                        int paddingTop = ((this.mFloorView.mLocationHeight - this.mFloorView.itemOffset) + this.mFloorView.getPaddingTop()) - (this.mNormalLocatioNDrawable.getIntrinsicHeight() / 2);
                        this.mLocationView.layout(measuredWidth, paddingTop, i9, this.mNormalLocatioNDrawable.getIntrinsicHeight() + paddingTop);
                        break;
                    case 3:
                        this.mLocationView.setVisibility(0);
                        this.mLocationView.setImageDrawable(this.mTipInRight ? this.mSmallLocationDrawable_R : this.mSmallLocationDrawable);
                        if (this.mTipInRight) {
                            i10 = this.mFloorView.getMeasuredWidth() + this.mBetweenMargin;
                        } else {
                            i10 = this.mNormalLocatioNDrawable.getIntrinsicWidth() - this.mSmallLocationDrawable.getIntrinsicWidth();
                        }
                        if (this.mTipInRight) {
                            i11 = this.mSmallLocationDrawable.getIntrinsicWidth() + i10;
                        } else {
                            i11 = this.mNormalLocatioNDrawable.getIntrinsicWidth();
                        }
                        int measuredHeight = (this.mFloorView.getMeasuredHeight() - this.mInterPadding) - (this.mSmallLocationDrawable.getIntrinsicHeight() / 2);
                        int intrinsicHeight2 = this.mSmallLocationDrawable.getIntrinsicHeight() + measuredHeight;
                        if (intrinsicHeight2 > this.mFloorView.getMeasuredHeight()) {
                            measuredHeight = this.mFloorView.getMeasuredHeight() - this.mSmallLocationDrawable.getIntrinsicHeight();
                            intrinsicHeight2 = this.mSmallLocationDrawable.getIntrinsicHeight() + measuredHeight;
                        }
                        this.mLocationView.layout(i10, measuredHeight, i11, intrinsicHeight2);
                        break;
                }
            }
            if (this.mTipInRight) {
                i5 = 0;
            } else {
                i5 = this.mNormalLocatioNDrawable.getIntrinsicWidth() + this.mBetweenMargin;
            }
            if (this.mTipInRight) {
                i6 = this.mFloorView.getMeasuredWidth();
            } else {
                i6 = this.mFloorView.getMeasuredWidth() + i5;
            }
            this.mFloorView.layout(i5, 0, i6, this.mFloorView.getMeasuredHeight() + 0);
        }
    }

    public void clearLocationType() {
        if (this.mFloorView != null) {
            this.mFloorView.mLocationType = 0;
            this.mFloorView.setCurrentLocationFloor("");
        }
        if (this.mLocationView != null) {
            this.mLocationView.setVisibility(8);
        }
    }

    public void removeAllViews() {
        super.removeAllViews();
        this.mFloorView = null;
        this.mLocationView = null;
    }

    public boolean isFloorViewAdded() {
        return getChildCount() > 0;
    }
}
