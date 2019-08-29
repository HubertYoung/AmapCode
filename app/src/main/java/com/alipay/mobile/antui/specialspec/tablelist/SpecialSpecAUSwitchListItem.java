package com.alipay.mobile.antui.specialspec.tablelist;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.tablelist.AUSwitchListItem;

public class SpecialSpecAUSwitchListItem extends AUSwitchListItem {
    public SpecialSpecAUSwitchListItem(Context context) {
        super(context);
        init();
    }

    public SpecialSpecAUSwitchListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpecialSpecAUSwitchListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        this.mLeftTextView.setTextSize(0, (float) getResources().getDimensionPixelSize(R.dimen.specialspec_au_list_text_size_title));
    }
}
