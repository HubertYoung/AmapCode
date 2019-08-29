package com.alipay.mobile.antui.specialspec.tablelist;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.tablelist.AUDoubleTitleListItem;

public class SpecialSpecAUDoubleTitleListItem extends AUDoubleTitleListItem {
    public SpecialSpecAUDoubleTitleListItem(Context context) {
        super(context);
        init();
    }

    public SpecialSpecAUDoubleTitleListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpecialSpecAUDoubleTitleListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mLeftTextView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.specialspec_au_list_text_size_title));
    }

    public void initLeftSubText() {
        super.initLeftSubText();
        this.mLeftSubTextView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.specialspec_au_list_text_size_sub_title));
    }

    /* access modifiers changed from: protected */
    public void initTextImage() {
        super.initTextImage();
        this.mRightTextView.setTextSize((float) R.dimen.specialspec_au_list_text_size_content);
    }
}
