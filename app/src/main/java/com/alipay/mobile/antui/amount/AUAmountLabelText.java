package com.alipay.mobile.antui.amount;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.alipay.mobile.antui.R;
import com.alipay.mobile.antui.basic.AULinearLayout;
import com.alipay.mobile.antui.basic.AUTextView;

public class AUAmountLabelText extends AULinearLayout {
    private AUAmountTextWatcher editChangedWatcher;
    private AUTextView mAmountText;

    public AUAmountLabelText(Context context) {
        super(context);
        init(context);
    }

    public AUAmountLabelText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public AUAmountLabelText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.au_amount_label_text, this, true);
        this.mAmountText = (AUTextView) findViewById(R.id.amount_text);
        setGravity(48);
        setOrientation(0);
        this.editChangedWatcher = new AUAmountTextWatcher(context, this.mAmountText, null);
        this.mAmountText.addTextChangedListener(this.editChangedWatcher);
    }

    public AUTextView getAmountText() {
        return this.mAmountText;
    }

    public void setText(CharSequence text) {
        this.mAmountText.setText(text);
    }
}
