package com.alipay.mobile.antui.tablelist;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.common.AUCheckIcon;

public class AUCheckBoxListItem extends AUAbsListItem {
    public AUCheckBoxListItem(Context context) {
        this(context, null);
    }

    public AUCheckBoxListItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AUCheckBoxListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSelfViews(context, attrs);
    }

    /* access modifiers changed from: protected */
    public int getImageVerticalMargin(Context context) {
        return getResources().getDimensionPixelOffset(R.dimen.AU_SPACE3);
    }

    /* access modifiers changed from: protected */
    public int getLeftImageSize(Context context) {
        return 0;
    }

    private void initSelfViews(Context context, AttributeSet attrs) {
        this.mLeftImageView.setVisibility(8);
        initCheckIcon();
        int iconState = 2;
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AUCheckIcon);
            iconState = array.getInt(1, 2);
            array.recycle();
        }
        setCheckstatus(iconState);
    }

    public AUCheckIcon getLeftCheckIcon() {
        return super.getLeftCheckIcon();
    }

    public void setCheckstatus(int status) {
        this.mLeftCheckIcon.setIconState(status);
    }

    public int getIconState() {
        return this.mLeftCheckIcon.getIconState();
    }
}
