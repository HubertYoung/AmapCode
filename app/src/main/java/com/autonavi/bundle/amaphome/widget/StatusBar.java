package com.autonavi.bundle.amaphome.widget;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.autonavi.minimap.R;
import com.uc.webview.export.extension.UCCore;

public class StatusBar extends ViewGroup {
    private final ImageView mBizIcon;
    private final TextView mBizTv;

    public StatusBar(Context context, int i, CharSequence charSequence) {
        super(context);
        this.mBizIcon = new ImageView(context);
        this.mBizTv = new TextView(context);
        this.mBizTv.setTextSize(14.0f);
        this.mBizTv.setTextColor(-1);
        this.mBizTv.setEllipsize(TruncateAt.END);
        this.mBizTv.setSingleLine();
        addViewInLayout(this.mBizIcon, 0, generateDefaultLayoutParams(), true);
        addViewInLayout(this.mBizTv, 1, generateDefaultLayoutParams(), true);
        setBackgroundResource(R.drawable.statusbar_bg);
        setPadding(getResources().getDimensionPixelSize(R.dimen.statusbar_pading_left), getResources().getDimensionPixelSize(R.dimen.statusbar_pading_top), getResources().getDimensionPixelSize(R.dimen.statusbar_pading_right), getResources().getDimensionPixelSize(R.dimen.statusbar_pading_bottom));
        this.mBizIcon.setImageResource(i);
        this.mBizTv.setText(charSequence);
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int i, int i2) {
        int makeMeasureSpec = MeasureSpec.makeMeasureSpec(getResources().getDimensionPixelSize(R.dimen.statusbar_icon_size), UCCore.VERIFY_POLICY_QUICK);
        this.mBizIcon.measure(makeMeasureSpec, makeMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        this.mBizTv.measure(MeasureSpec.makeMeasureSpec((((size - paddingLeft) - paddingRight) - this.mBizIcon.getMeasuredWidth()) - getResources().getDimensionPixelSize(R.dimen.statusbar_margin), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec((size2 - paddingTop) - paddingBottom, Integer.MIN_VALUE));
        setMeasuredDimension(resolveSize(paddingLeft + this.mBizIcon.getMeasuredWidth() + getResources().getDimensionPixelSize(R.dimen.statusbar_margin) + this.mBizTv.getMeasuredWidth() + paddingRight, i), resolveSize(getResources().getDimensionPixelSize(R.dimen.statusbar_height), i2));
    }

    /* access modifiers changed from: protected */
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int paddingLeft = getPaddingLeft();
        getPaddingTop();
        getPaddingRight();
        getPaddingBottom();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.statusbar_margin);
        int measuredHeight = (getMeasuredHeight() / 2) - (this.mBizIcon.getMeasuredHeight() / 2);
        this.mBizIcon.layout(paddingLeft, measuredHeight, this.mBizIcon.getMeasuredWidth() + paddingLeft, this.mBizIcon.getMeasuredHeight() + measuredHeight);
        int measuredHeight2 = (getMeasuredHeight() / 2) - (this.mBizTv.getMeasuredHeight() / 2);
        this.mBizTv.layout(this.mBizIcon.getRight() + dimensionPixelSize, measuredHeight2, this.mBizIcon.getRight() + dimensionPixelSize + this.mBizTv.getMeasuredWidth(), this.mBizTv.getMeasuredHeight() + measuredHeight2);
    }
}
