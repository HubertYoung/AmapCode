package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import com.alipay.mobile.antui.R;

public class AUDividerListView extends AUListView {
    public AUDividerListView(Context context) {
        super(context);
        init(context, null);
    }

    public AUDividerListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AUDividerListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AUDividerListView);
            setDivider(getResources().getDrawable(array.getResourceId(0, R.drawable.drawable_divider_list_divider)));
            array.recycle();
        } else {
            setDivider(getResources().getDrawable(R.drawable.drawable_divider_list_divider));
        }
        setBackgroundResource(R.drawable.drawable_bg_top_bottom_line);
    }
}
