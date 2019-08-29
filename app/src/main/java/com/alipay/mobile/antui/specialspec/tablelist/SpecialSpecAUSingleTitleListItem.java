package com.alipay.mobile.antui.specialspec.tablelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUTextView;
import com.alipay.mobile.antui.tablelist.AUSingleTitleListItem;

public class SpecialSpecAUSingleTitleListItem extends AUSingleTitleListItem {
    public SpecialSpecAUSingleTitleListItem(Context context) {
        super(context);
        init();
    }

    public SpecialSpecAUSingleTitleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpecialSpecAUSingleTitleListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mLeftTextView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.specialspec_au_list_text_size_title));
    }

    /* access modifiers changed from: protected */
    public void initRightControlSize() {
        AUTextView rightTextView = getRightTextView();
        if (rightTextView != null) {
            rightTextView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.specialspec_au_list_text_size_content));
        }
        super.initRightControlSize();
    }

    public void addLeftTitleLabel(View labelView) {
        addRightView(labelView);
        LayoutParams layoutParams = (LayoutParams) this.leftContainer.getLayoutParams();
        layoutParams.weight = 0.0f;
        layoutParams.width = -2;
        requestLayout();
    }

    public void addLeftTitleLabel(View labelView, LayoutParams layoutParams) {
        addRightView(labelView);
        labelView.setLayoutParams(layoutParams);
        LayoutParams leftTitleLayoutParams = (LayoutParams) this.leftContainer.getLayoutParams();
        leftTitleLayoutParams.weight = 0.0f;
        leftTitleLayoutParams.width = -2;
        requestLayout();
    }

    /* access modifiers changed from: protected */
    public int getImageVerticalMargin(Context context) {
        return context.getResources().getDimensionPixelOffset(R.dimen.specialspec_AU_SPACE2);
    }
}
