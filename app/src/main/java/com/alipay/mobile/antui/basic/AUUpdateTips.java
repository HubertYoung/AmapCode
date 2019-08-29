package com.alipay.mobile.antui.basic;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;

public class AUUpdateTips extends AULinearLayout {
    private AUTextView tipText;

    public AUUpdateTips(Context context) {
        super(context);
        init(context);
    }

    public AUUpdateTips(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUUpdateTips(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_update_tips, this, true);
        this.tipText = (AUTextView) findViewById(R.id.tip_text);
        setOrientation(0);
        setGravity(17);
        setBackgroundColor(getResources().getColor(R.color.update_tips_background_color));
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.AU_HEIGHT7));
    }

    public void setTipText(CharSequence text) {
        this.tipText.setText(text);
    }
}
