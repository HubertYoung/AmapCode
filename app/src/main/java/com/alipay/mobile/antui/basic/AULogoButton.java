package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.iconfont.AUIconView;
import com.alipay.mobile.antui.utils.DensityUtil;

public class AULogoButton extends FrameLayout {
    private AUIconView mIconView;
    private AUTextView mTextView;

    public AULogoButton(Context context) {
        super(context);
        init(context, null);
    }

    public AULogoButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AULogoButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.au_logo_button, this, true);
        this.mTextView = (AUTextView) findViewById(R.id.logo_button_text);
        this.mIconView = (AUIconView) findViewById(R.id.logo_button_icon);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AULogoButton);
            if (a.hasValue(3)) {
                this.mTextView.setText(a.getString(3));
            }
            if (a.hasValue(0)) {
                this.mTextView.setTextSize(0, a.getDimension(0, (float) DensityUtil.dip2px(context, 12.0f)));
            }
            if (a.hasValue(1)) {
                ColorStateList color = a.getColorStateList(1);
                this.mTextView.setTextColor(color);
                this.mIconView.setIconfontColorStates(color);
            }
            a.recycle();
        }
    }

    public AUIconView getIconView() {
        return this.mIconView;
    }

    public void setText(CharSequence text) {
        this.mTextView.setText(text);
    }
}
