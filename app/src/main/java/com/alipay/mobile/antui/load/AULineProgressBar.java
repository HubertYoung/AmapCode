package com.alipay.mobile.antui.load;

import android.content.Context;
import android.util.AttributeSet;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AUProgressBar;

public class AULineProgressBar extends AUProgressBar {
    public AULineProgressBar(Context context) {
        this(context, null);
    }

    public AULineProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 16842872);
    }

    public AULineProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setProgressDrawable(context.getResources().getDrawable(R.drawable.drawable_line_progress));
    }
}
