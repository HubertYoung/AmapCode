package com.autonavi.minimap.ajx3.widget.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class BaseEditText extends EditText {
    private EditTextHelper mEditTextHelper = new EditTextHelper(this);

    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BaseEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public BaseEditText(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public boolean bringPointIntoView(int i) {
        this.mEditTextHelper.bringPointIntoView(i);
        return super.bringPointIntoView(i);
    }

    public void scrollTo(int i, int i2) {
        super.scrollTo(i, this.mEditTextHelper.getScrollY(i2));
    }
}
